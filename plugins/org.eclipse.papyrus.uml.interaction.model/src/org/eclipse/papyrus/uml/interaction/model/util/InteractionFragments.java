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

package org.eclipse.papyrus.uml.interaction.model.util;

import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.filter;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMapToObj;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.test;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;

/**
 * Various static utilities for working with interaction fragments in the <em>Logical Model</em>.
 */
public class InteractionFragments {

	/**
	 * Not instantiable by clients.
	 */
	private InteractionFragments() {
		super();
	}

	/**
	 * Find an obstacle in the way of moving an {@code occurrence} (being a point object) up or down in the
	 * interaction.
	 * 
	 * @param occurrence
	 *            an occurrence being moved
	 * @param movingUp
	 *            whether it is being moved up (otherwise down)
	 * @param newY
	 *            the new position to which it is being moved
	 * @return the obstacle blocking its move, that will need to be nudged out of the way
	 */
	public static Optional<MElement<? extends Element>> getObstacle(MOccurrence<? extends Element> occurrence,
			boolean movingUp, int newY) {

		return getObstacle(occurrence, movingUp, newY, newY);
	}

	/**
	 * Find an obstacle in the way of moving an {@code element} up or down in the interaction.
	 * 
	 * @param element
	 *            an element being moved
	 * @param movingUp
	 *            whether it is being moved up (otherwise down)
	 * @param newTop
	 *            the new top position that it will have after being moved
	 * @param newBottom
	 *            the new bottom position that it will have after being moved
	 * @return the obstacle blocking its move, that will need to be nudged out of the way
	 */
	public static Optional<MElement<? extends Element>> getObstacle(MElement<? extends Element> element,
			boolean movingUp, int newTop, int newBottom) {

		// Do we want to normalize message ends as messages or as themselves?
		boolean messageOnly = !(element instanceof MMessage) && !(element instanceof MOccurrence<?>);

		Optional<MElement<? extends Element>> result = movingUp //
				? verticallyPreceding(normalizedUp(element, messageOnly)) //
				: verticallyFollowing(normalizedDown(element, messageOnly));

		Predicate<MElement<? extends Element>> collisionFilter = movingUp
				? below(newTop - 1 /* need ≥, not > */)
				: above(newBottom + 1 /* need ≤, not < */);

		return result.filter(collisionFilter).map(resolveObstacle(element));
	}

	/**
	 * Obtain a function that resolves an obstable to an element that has a notation view for which layout
	 * constraints can actually be determined.
	 * 
	 * @param reference
	 *            the reference element for which the obstacle was computed as being in its way
	 * @return the obstacle resolution function
	 * @see #resolveObstacle(MElement, MElement)
	 */
	static UnaryOperator<MElement<? extends Element>> resolveObstacle(MElement<? extends Element> reference) {
		return obstacle -> resolveObstacle(obstacle, reference);
	}

	/**
	 * Resolve an {@code obstable} to an element that has a notation view for which layout constraints can
	 * actually be determined.
	 * 
	 * @param obstacle
	 *            an obstacle
	 * @param reference
	 *            the reference element for which the {@code obstacle} was computed as being in its way
	 * @return the {@code obstacle}, or some other element that is implied by it
	 */
	private static MElement<? extends Element> resolveObstacle(MElement<? extends Element> obstacle,
			MElement<? extends Element> reference) {
		Predicate<MElement<? extends Element>> spansReference = LogicalModelPredicates.spans(reference);
		Predicate<MElement<? extends Element>> notSpanning = spansReference.negate();

		return new SequenceDiagramSwitch<MElement<? extends Element>>() {
			@Override
			public MElement<? extends Element> caseMMessageEnd(MMessageEnd object) {
				return Optional.ofNullable(object.getOwner()).filter(notSpanning).orElse(null);
			}

			@Override
			public MElement<? extends Element> caseMExecutionOccurrence(MExecutionOccurrence object) {
				return object.getExecution().filter(notSpanning).orElse(null);
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMOccurrence(MOccurrence<T> object) {
				// Does the execution span the reference element? If so, only nudge the actual occurrence
				return Optionals.elseMaybe(object.getStartedExecution().filter(notSpanning),
						object.getFinishedExecution().filter(notSpanning)).orElse(null);
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMElement(MElement<T> object) {
				return object;
			}
		}.doSwitch(obstacle);
	}

	private static MElement<? extends Element> normalizedUp(MElement<? extends Element> obstacle,
			boolean messageOnly) {
		return new SequenceDiagramSwitch<MElement<? extends Element>>() {

			@Override
			public MElement<? extends Element> caseMMessageEnd(MMessageEnd object) {
				return messageOnly
						// The real element to consider is the message, and it begins at the send end
						? object.getOtherEnd().filter(MMessageEnd::isSend).map(this::doSwitch).orElse(null)
						: null;
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMOccurrence(MOccurrence<T> object) {
				// The finish occurrence follows all fragments that the execution spans
				Optional<MExecution> finishedExecution = object.getFinishedExecution();
				if (test(finishedExecution, verticallyPreceding(object), LogicalModelPredicates::equal)) {
					// Execution doesn't span anything
					return finishedExecution.get().getStart().orElse(null);
				}
				return null;
			}

			@Override
			public MElement<? extends Element> caseMMessage(MMessage object) {
				// The message's upper extremity is the send event
				return object.getSend().map(this::doSwitch).orElse(null);
			}

			@Override
			public MElement<? extends Element> caseMExecution(MExecution object) {
				return object.getStart().<MElement<? extends Element>> map(this::doSwitch).orElse(null);
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMElement(MElement<T> object) {
				return object;
			}

		}.doSwitch(obstacle);
	}

	private static MElement<? extends Element> normalizedDown(MElement<? extends Element> obstacle,
			boolean messageOnly) {
		return new SequenceDiagramSwitch<MElement<? extends Element>>() {

			@Override
			public MElement<? extends Element> caseMMessageEnd(MMessageEnd object) {
				return messageOnly
						// The real element to consider is the message, and it finishes at the receive end
						? object.getOtherEnd().filter(MMessageEnd::isReceive).map(this::doSwitch).orElse(null)
						: null;
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMOccurrence(MOccurrence<T> object) {
				// The execution itself precedes any fragments that it spans
				Optional<MElement<? extends Element>> startedExecution = as(object.getStartedExecution(),
						MElement.class);
				return startedExecution.orElse(null);
			}

			@Override
			public MElement<? extends Element> caseMMessage(MMessage object) {
				// The message's lower extremity is the receive event
				return object.getReceive().map(this::doSwitch).orElse(null);
			}

			@Override
			public MElement<? extends Element> caseMExecution(MExecution object) {
				return object.getFinish().<MElement<? extends Element>> map(this::doSwitch).orElse(null);
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMElement(MElement<T> object) {
				return object;
			}

		}.doSwitch(obstacle);
	}

	/**
	 * Obtains the next interaction fragment in the vertical timeline that happens after a given
	 * {@code fragment}. This is distinct from the {@linkplain MElement#following() causally following}
	 * element, which may occur later in time.
	 * 
	 * @param fragment
	 *            an interaction fragment
	 * @return the next fragment in the timeline
	 * @see MElement#following()
	 */
	public static Optional<MElement<? extends Element>> verticallyFollowing(
			MElement<? extends Element> fragment) {

		MInteraction interaction = fragment.getInteraction();
		List<InteractionFragment> fragments = interaction.getElement().getFragments();

		OptionalInt index = filter(OptionalInt.of(fragments.indexOf(fragment.getElement())),
				i -> i + 1 < fragments.size());
		return flatMapToObj(index, i -> interaction.getElement(fragments.get(i + 1)));
	}

	/**
	 * Obtains the previous interaction fragment in the vertical timeline that happens before a given
	 * {@code fragment}. This is distinct from the {@linkplain MElement#precedes(MElement) causally preceding}
	 * element, which may occur earlier in time.
	 * 
	 * @param fragment
	 *            an interaction fragment
	 * @return the previous fragment in the timeline
	 * @see MElement#precedes(MElement)
	 */
	public static Optional<MElement<? extends Element>> verticallyPreceding(
			MElement<? extends Element> fragment) {

		MInteraction interaction = fragment.getInteraction();
		List<InteractionFragment> fragments = interaction.getElement().getFragments();

		OptionalInt index = filter(OptionalInt.of(fragments.indexOf(fragment.getElement())), i -> i > 0);
		Optional<MElement<? extends Element>> result = flatMapToObj(index,
				i -> interaction.getElement(fragments.get(i - 1)));

		// Take the start of an execution instead of the execution, itself
		return Optionals.elseMaybe( //
				result.filter(MExecution.class::isInstance).map(MExecution.class::cast)
						.flatMap(MExecution::getStart),
				result);
	}

}
