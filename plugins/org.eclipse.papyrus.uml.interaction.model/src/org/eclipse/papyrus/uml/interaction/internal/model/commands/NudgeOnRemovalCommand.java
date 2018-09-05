/*****************************************************************************
 * Copyright (c) 2018 Johannes Faltermeier and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MessageSort;

/**
 * This command analyses the current graph and fills the white space created by the deletion of elements.
 */
@SuppressWarnings("boxing")
public class NudgeOnRemovalCommand extends ModelCommand<MInteractionImpl> {

	private static final int DEFAULT_VERTICAL_OFFSET = 10;

	private Set<MElement<? extends Element>> mElementsToRemove;

	private MInteraction interaction;

	/**
	 * Creates a new {@link NudgeCommand}.
	 * 
	 * @param editingDomain
	 *            the {@link EditingDomain}
	 * @param interaction
	 *            the {@link MInteractionImpl}
	 * @param elementsToRemove
	 *            the elements which will be removed
	 */
	public NudgeOnRemovalCommand(EditingDomain editingDomain, MInteractionImpl interaction,
			Collection<Element> elementsToRemove) {
		super(interaction);

		this.interaction = interaction;
		this.mElementsToRemove = getRemovedMElements(elementsToRemove);

		List<Command> nudgeCommands = new ArrayList<>();
		nudgeCommands.addAll(createVerticalFullLifelineNudgeCommands());
		nudgeCommands.addAll(createVerticalNudgeCommands());
		nudgeCommands.addAll(createHorizontalNudgeCommands());

		/* create the command */
		if (nudgeCommands.isEmpty()) {
			command = IdentityCommand.INSTANCE;
		} else {
			command = CompoundModelCommand.compose(editingDomain, nudgeCommands);
		}

	}

	/**
	 * Maps the removed {@link Element}s to their respective {@link MElement}. Filters out {@link MOccurrence}
	 * as they are currently just point values connected to other {@link MElement}s.
	 * 
	 * @param elementsToRemove
	 *            the removed {@link Element elements}
	 */
	private Set<MElement<? extends Element>> getRemovedMElements(Collection<Element> elementsToRemove) {
		return elementsToRemove.stream()//
				.map(interaction::getElement)//
				.filter(Optional::isPresent)//
				.map(Optional::get)//
				.filter(e -> !MOccurrence.class.isInstance(e))// TODO MGate special case once available
				.collect(Collectors.toSet());
	}

	/**
	 * When create messages are deleted we need to nudge full lifelines.
	 */
	private List<Command> createVerticalFullLifelineNudgeCommands() {
		List<Command> nudgeCommands = new ArrayList<>();

		List<MMessage> creationMessages = mElementsToRemove.stream()//
				.filter(MMessage.class::isInstance)//
				.map(MMessage.class::cast)//
				.filter(m -> m.getElement().getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL)
				.collect(Collectors.toList());

		for (MMessage message : creationMessages) {
			/* create message deletion should move created lifeline up again */
			Optional<MLifeline> sender = message.getSender();
			Optional<MLifeline> receiver = message.getReceiver();
			if (!sender.isPresent() || !receiver.isPresent()) {
				continue;
			}

			int defaultLifelineTop = getDefaultLifelineTop(receiver.get());
			int delta = receiver.get().getTop().orElse(0) - defaultLifelineTop;
			nudgeCommands.add(layoutHelper().setTop(vertex(receiver.get()), defaultLifelineTop));

			/*
			 * fix other tops on moved lifeline which may result in crooked ui by moving them down again
			 */
			receiver.get().getExecutions().stream()//
					.map(this::vertex)//
					.forEach(v -> nudgeCommands
							.add(layoutHelper().setTop(v, layoutHelper().getTop(v).orElse(0) + delta)));
			for (MMessage m : receiver.get().getInteraction().getMessages()) {
				m.getSender().ifPresent(l -> {
					if (l == receiver.get()) {
						Vertex v = vertex(m.getSend().get());
						nudgeCommands
								.add(layoutHelper().setTop(v, layoutHelper().getTop(v).orElse(0) + delta));
					}
				});
				m.getReceiver().ifPresent(l -> {
					if (l == receiver.get()) {
						Vertex v = vertex(m.getReceive().get());
						nudgeCommands
								.add(layoutHelper().setTop(v, layoutHelper().getTop(v).orElse(0) + delta));
					}
				});
			}
		}

		return nudgeCommands;
	}

	private int getDefaultLifelineTop(MLifeline lifeline) {
		/* move lifeline header up to very top */
		int offsetY = constraints().getYOffset(ViewTypes.LIFELINE_HEADER);
		return lifeline.getDiagramView().map(v -> layoutHelper().toAbsoluteY(v, offsetY)).orElse(0);
	}

	private List<Command> createVerticalNudgeCommands() {
		List<Command> nudgeCommands = new ArrayList<>();
		/* find out the deleted range */
		int deletedTop = Integer.MAX_VALUE;
		int deletedBottom = Integer.MIN_VALUE;
		MElement<? extends Element> topMostDeletedElement = null;
		for (MElement<? extends Element> element : mElementsToRemove) {
			OptionalInt top = getTop(element);
			if (top.isPresent() && top.getAsInt() < deletedTop) {
				deletedTop = top.getAsInt();
				topMostDeletedElement = element;
			}
			OptionalInt bottom = element.getBottom();
			if (bottom.isPresent() && bottom.getAsInt() > deletedBottom) {
				deletedBottom = bottom.getAsInt();
			}
		}
		final int topFinal = deletedTop;
		final int bottomFinal = deletedBottom;

		/* put remaining elements in maps based on top/bottom values */
		Map<Integer, Set<MElement<? extends Element>>> topToElements = new LinkedHashMap<>();
		Map<Integer, Set<MElement<? extends Element>>> bottomToElements = new LinkedHashMap<>();
		for (MLifeline lifeline : interaction.getLifelines()) {
			for (MExecution execution : lifeline.getExecutions()) {
				putValuesInMaps(execution, topToElements, bottomToElements);
			}
		}
		for (MMessage message : interaction.getMessages()) {
			putValuesInMaps(message, topToElements, bottomToElements);
		}

		/* put bottom of lowest lifeline's header into bottomToElements map */
		Optional<MLifeline> lowestRelatedLifeline = getRelatedLifelineWithLowestTop(topMostDeletedElement);
		if (lowestRelatedLifeline.isPresent() && lowestRelatedLifeline.get().getTop().isPresent()) {
			int lifelineHeaderHeight = constraints().getMinimumHeight(ViewTypes.LIFELINE_HEADER);
			int bottom = lowestRelatedLifeline.get().getTop().getAsInt() + lifelineHeaderHeight;
			putValueInMap(bottom, lowestRelatedLifeline.get(), bottomToElements);
		}

		/* analyse the starting points of remaining elements in comparison with deleted range */

		/* find out if we have to move elements which start in the deleted range */
		if (shouldMoveUpperElements(topFinal, bottomFinal, topToElements, bottomToElements)) {
			Set<Integer> topsStartingAfter = topToElements.keySet().stream()//
					.filter(top -> top >= topFinal)//
					.collect(Collectors.toSet());
			Optional<Integer> min = topsStartingAfter.stream().min(Integer::compare);
			int delta = topFinal - min.orElse(topFinal) + additionalVerticalOffSet();
			List<MElement<? extends Element>> elementsToNudge = topsStartingAfter.stream()//
					.flatMap(key -> topToElements.get(key).stream())//
					.collect(Collectors.toList());
			for (int i = 0; i < elementsToNudge.size(); i++) {
				MElement<? extends Element> element = elementsToNudge.get(i);
				nudgeCommands.add(Create.nudgeCommand(getGraph(), getEditingDomain(), delta, delta, element));
			}
		}

		/* find out if we have to move elements which start after the deleted range */
		if (shouldMoveBottomElements(bottomFinal, topToElements)) {
			Set<Integer> topsBelowDeletion = topToElements.keySet().stream()//
					.filter(top -> top >= bottomFinal)//
					.collect(Collectors.toSet());
			Optional<Integer> firstTopBelowDeletion = topsBelowDeletion.stream().min(Integer::compare);

			/* if there is an undeleted element ending in deletion range use this as benchmark */
			Optional<Integer> benchmark = bottomToElements.keySet().stream()//
					.filter(bottom -> bottom >= topFinal && bottom <= bottomFinal)//
					.max(Integer::max);

			int delta;
			if (benchmark.isPresent()) {
				/* keep previous distance to deleted range and move below benchmark */
				delta = (benchmark.get()) - firstTopBelowDeletion.orElse(benchmark.get())
						+ (firstTopBelowDeletion.orElse(bottomFinal) - bottomFinal);
			} else {
				Optional<Integer> firstBottomAboveDeletion = bottomToElements.keySet().stream()
						.filter(bot -> bot <= topFinal).reduce(Integer::max);
				/*
				 * keep previous distance between delete range and next element and move below deleted range
				 */
				delta = firstBottomAboveDeletion.orElse(deletedTop) - bottomFinal
						+ additionalVerticalOffSet();
			}

			List<MElement<? extends Element>> elementsToNudge = topsBelowDeletion.stream()//
					.sorted(Comparator.naturalOrder())//
					.flatMap(key -> topToElements.get(key).stream())//
					.collect(Collectors.toList());
			for (int i = 0; i < elementsToNudge.size(); i++) {
				MElement<? extends Element> element = elementsToNudge.get(i);
				nudgeCommands.add(Create.nudgeCommand(getGraph(), getEditingDomain(), delta, delta, element));
			}
		}
		return nudgeCommands;
	}

	private Optional<MLifeline> getRelatedLifelineWithLowestTop(MElement<? extends Element> element) {
		if (element == null) {
			return Optional.empty();
		}

		final List<MLifeline> relatedLifelines = new ArrayList<>();

		if (element.getOwner() instanceof MLifeline) {
			relatedLifelines.add((MLifeline)element.getOwner());
		}

		if (element instanceof MMessage) {
			MMessage mMessage = (MMessage)element;
			mMessage.getSender().ifPresent(relatedLifelines::add);
			mMessage.getReceiver().ifPresent(relatedLifelines::add);
		}

		return relatedLifelines.stream().reduce((lowest, current) -> lowest.getTop()
				.orElse(Integer.MIN_VALUE) >= current.getTop().orElse(Integer.MIN_VALUE) ? lowest : current);
	}

	private LayoutConstraints constraints() {
		return layoutHelper().getConstraints();
	}

	/**
	 * Checks if an additional vertical offset is required.
	 */
	private int additionalVerticalOffSet() {
		if (mElementsToRemove.stream()//
				.anyMatch(MLifeline.class::isInstance)) {
			/* ensure there is a gap after the start of a lifeline */
			return DEFAULT_VERTICAL_OFFSET;
		}
		return 0;
	}

	private List<Command> createHorizontalNudgeCommands() {
		List<Command> nudgeCommands = new ArrayList<>();
		/* find the deleted lifelines and order all lifelines from left to right */
		Set<MLifeline> deletedLifelines = mElementsToRemove.stream()//
				.filter(MLifeline.class::isInstance)//
				.map(MLifeline.class::cast)//
				.collect(Collectors.toSet());

		List<MLifeline> lifelinesLeftToRight = interaction.getLifelines().stream()//
				.sorted((l1, l2) -> {
					return l1.getLeft().orElse(Integer.MAX_VALUE) - l2.getLeft().orElse(Integer.MAX_VALUE);
				}).collect(Collectors.toList());

		/* loop over lifelines from left to right and create nudge commands based on deleted lifelines */
		int delta = 0;
		MLifeline deleted = null;
		for (MLifeline lifeline : lifelinesLeftToRight) {
			if (deleted != null) {
				delta = deleted.getLeft().orElse(Integer.MAX_VALUE)
						- lifeline.getLeft().orElse(Integer.MAX_VALUE);
				deleted = null;
			}
			if (deletedLifelines.contains(lifeline)) {
				deleted = lifeline;
			}

			if (deleted == null && delta != 0) {
				nudgeCommands.add(lifeline.nudgeHorizontally(delta));
				delta = 0;
			}
		}
		return nudgeCommands;
	}

	/**
	 * Returns the top of a given {@link MElement}. This may increase the top value for some {@link MElement}s
	 * based on whether child elements may be placed directly at this coordinate or not.
	 */
	private OptionalInt getTop(MElement<? extends Element> element) {
		if (element instanceof MLifeline) {
			View shape = ((MLifeline)element).getDiagramView().orElse(null);
			int lifelineTop = layoutHelper()//
					.getTop(diagramHelper().getLifelineBodyShape(shape));
			return OptionalInt.of(lifelineTop);
		}
		return element.getTop();
	}

	/** Whether elements in the deleted range have to be moved up. */
	private boolean shouldMoveUpperElements(final int topFinal, final int bottomFinal,
			Map<Integer, Set<MElement<? extends Element>>> topToElements,
			Map<Integer, Set<MElement<? extends Element>>> bottomToElements) {

		/*
		 * this is the case if the top-most element in this range starts after the deletion range and it is
		 * not anchored by an element ending in the range but starting before
		 */
		Optional<Integer> topElementStartingInRange = topToElements.keySet().stream()//
				.filter(top -> top > topFinal && top <= bottomFinal)// starting in range
				.sorted(Integer::min).findFirst();
		if (!topElementStartingInRange.isPresent()) {
			return false;
		} else {
			/* now check for anchor. element has to end in range and must be before our top element */
			Optional<Integer> anchor = bottomToElements.keySet().stream()//
					.filter(bottom -> bottom >= topFinal && bottom < bottomFinal)// ending in range
					.filter(bottom -> bottom < topElementStartingInRange.get())//
					.findAny();
			return !anchor.isPresent();
		}
	}

	/** Whether elements starting below the deleted range need to be moved up. */
	private boolean shouldMoveBottomElements(final int bottomFinal,
			Map<Integer, Set<MElement<? extends Element>>> topToElements) {
		/*
		 * this is NOT the case if we have an element which starts in or before the deleted range and ends
		 * after the deleted range. In all other cases we want to move.
		 */
		return !topToElements.keySet().stream()//
				.filter(top -> top <= bottomFinal)// starts in or before range
				.flatMap(key -> topToElements.get(key).stream())//
				.filter(e -> e.getBottom().orElse(bottomFinal) > bottomFinal)// ends after range
				.findAny()//
				.isPresent();
	}

	private void putValuesInMaps(MElement<? extends Element> element,
			Map<Integer, Set<MElement<? extends Element>>> topToElements,
			Map<Integer, Set<MElement<? extends Element>>> bottomToElements) {
		if (mElementsToRemove.contains(element)) {
			return;
		}

		Integer top = getTop(element).orElse(Integer.MAX_VALUE);
		Integer bottom = element.getBottom().orElse(Integer.MIN_VALUE);
		putValueInMap(top, element, topToElements);
		putValueInMap(bottom, element, bottomToElements);
	}

	protected void putValueInMap(Integer value, MElement<? extends Element> element,
			Map<Integer, Set<MElement<? extends Element>>> map) {
		if (!map.containsKey(value)) {
			map.put(value, new LinkedHashSet<>());
		}
		map.get(value).add(element);
	}

}
