/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelOrdering.vertically;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMapToInt;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.map;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition;
import org.eclipse.papyrus.uml.interaction.model.util.Optionals;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MessageSort;

/**
 * A command that makes a lifeline be created at some Y coördinate in the sequence.
 */
public class SetLifelineCreationCommand extends ModelCommand<MLifelineImpl> {

	private final OptionalInt yPosition;

	public SetLifelineCreationCommand(MLifelineImpl target, OptionalInt yPosition) {
		super(target);

		this.yPosition = yPosition;
	}

	@Override
	protected Command createCommand() {
		Shape lifelineHead = getTarget().getDiagramView().orElse(null);
		Shape lifelineBody = diagramHelper().getLifelineBodyShape(lifelineHead);
		int lifelineBodyTop = layoutHelper().getTop(lifelineBody);
		int lifelineHeadTop = layoutHelper().getTop(lifelineHead);

		// Move the lifeline to the requested location, or else the default top location of the diagram
		int movedLifelineTop = map(yPosition, y -> y - ((lifelineBodyTop - lifelineHeadTop) / 2))
				.orElseGet(() -> defaultTop(lifelineHead));

		int deltaTop = movedLifelineTop - layoutHelper().getTop(lifelineHead);

		// A command to set the top of the lifeline
		Command result = Create.nudgeCommand(getGraph(), getEditingDomain(), deltaTop, 0, getTarget());

		// Nudge up/down the second and subsequent occurrences on the lifeline.
		// Don't nudge execution occurrences because we nudge the executions, themselves.
		// And likewise don't nudge message ends and nested executions spanned by executions
		// that we nudge
		Stream<? extends MElement<? extends Element>> moveable = Stream.concat(//
				getTarget().getExecutions().stream(), // TODO: Filter nested executions
				getTarget().getMessageEnds().stream().filter(end -> !end.getExecution().isPresent()
						// TODO: When start/finish message ends attach to the execution, then remove these
						|| end.getStartedExecution().isPresent() || end.getFinishedExecution().isPresent()));
		Stream<? extends MElement<? extends Element>> toNudge = moveable.filter(isCreation().negate())
				.sorted(vertically().reversed());
		List<? extends MElement<? extends Element>> elements = toNudge.collect(Collectors.toList());
		toNudge = elements.stream();
		Optional<Command> nudges = toNudge //
				.map(elem -> (Command)new NudgeCommand((MElementImpl<? extends Element>)elem, -deltaTop,
						false))
				.reduce(chaining());

		result = nudges.map(chaining(result)).orElse(result);

		// Finally, we may need to nudge elements to make space for the new position of the lifeline
		// head or to remove space for a create message that was removed
		Optional<Command> nudgeForPadding = getNudgeForPadding(lifelineHead, lifelineBodyTop + deltaTop);
		result = nudgeForPadding.map(chaining(result)).orElse(result);

		return result;
	}

	protected Predicate<MElement<? extends Element>> isCreation() {
		return elem -> (elem instanceof MMessageEnd) && ((MMessageEnd)elem).isReceive()
				&& (((MMessageEnd)elem).getOwner().getElement()
						.getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL);
	}

	protected int defaultTop(Shape lifelineHead) {
		return layoutHelper().toAbsoluteY(lifelineHead,
				layoutHelper().getConstraints().getYOffset(lifelineHead.getType()));
	}

	protected Optional<Command> getNudgeForPadding(View lifelineHead, int newLifelineHeadBottom) {
		if (yPosition.isPresent()) {
			// Do we need to make room for the lifeline head?
			Optional<MOccurrence<? extends Element>> firstOccurrence = getTarget().getOccurrences().stream()
					.filter(isCreation().negate()) // But not the creation event (in case of re-orient)
					.sorted(vertically()).findFirst();
			OptionalInt firstOccurrenceTop = flatMapToInt(firstOccurrence, MElement::getTop);

			if (!firstOccurrenceTop.isPresent()) {
				// Nothing to worry about
				return Optional.empty();
			}

			int gap = firstOccurrenceTop.getAsInt() - newLifelineHeadBottom;

			MElement<? extends Element> elementToPad = getElementToPad(firstOccurrence.get());
			OptionalInt elementPadding = flatMapToInt(as(elementToPad.getDiagramView(), View.class),
					view -> OptionalInt
							.of(layoutHelper().getConstraints().getPadding(RelativePosition.TOP, view)));
			int lifelinePadding = layoutHelper().getConstraints().getPadding(RelativePosition.BOTTOM,
					lifelineHead);

			int requiredPadding = Optionals.map(elementPadding, pad -> pad + lifelinePadding)
					.orElse(lifelinePadding);

			if (requiredPadding > gap) {
				return Optional.ofNullable(elementToPad.nudge(requiredPadding - gap));
			}
		}

		return Optional.empty();
	}

	protected MElement<? extends Element> getElementToPad(MElement<? extends Element> element) {
		return new SequenceDiagramSwitch<MElement<? extends Element>>() {
			@Override
			public MElement<? extends Element> caseMMessageEnd(MMessageEnd object) {
				return object.getOwner();
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMElement(MElement<T> object) {
				return object;
			}
		}.doSwitch(element);
	}
}
