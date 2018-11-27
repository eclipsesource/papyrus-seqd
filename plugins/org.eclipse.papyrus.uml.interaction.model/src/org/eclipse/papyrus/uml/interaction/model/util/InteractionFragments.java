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

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
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

	public static Optional<MElement<? extends Element>> getObstacle(MOccurrence<? extends Element> occurrence,
			boolean movingUp, int newY) {

		Optional<MElement<? extends Element>> result = movingUp //
				? verticallyPreceding(normalizedUp(occurrence)) //
				: verticallyFollowing(normalizedDown(occurrence));

		Predicate<MElement<? extends Element>> collisionFilter = movingUp
				? below(newY - 1 /* need ≥, not > */)
				: above(newY + 1 /* need ≤, not < */);

		return result.filter(collisionFilter).map(InteractionFragments::resolveObstacle);
	}

	/**
	 * Resolve an {@code obstable} to an element that has a notation view for which layout constraints can
	 * actually be determined.
	 * 
	 * @param obstacle
	 *            an obstacle
	 * @return the {@code obstacle}, or some other element that is implied by it
	 */
	static MElement<? extends Element> resolveObstacle(MElement<? extends Element> obstacle) {
		return new SequenceDiagramSwitch<MElement<? extends Element>>() {
			@Override
			public MElement<? extends Element> caseMMessageEnd(MMessageEnd object) {
				if (!object.isStart() && !object.isFinish()) {
					return object.getOwner();
				}
				return null;
			}

			@Override
			public MElement<? extends Element> caseMExecutionOccurrence(MExecutionOccurrence object) {
				return object.getExecution().orElse(null);
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMOccurrence(MOccurrence<T> object) {
				return Optionals.elseMaybe(object.getStartedExecution(), object.getFinishedExecution())
						.orElse(null);
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMElement(MElement<T> object) {
				return object;
			}
		}.doSwitch(obstacle);
	}

	public static Optional<MElement<? extends Element>> getObstacle(MExecution execution, boolean movingUp,
			int newTop, int newBottom) {

		Optional<MElement<? extends Element>> result = movingUp //
				? verticallyPreceding(normalizedUp(execution)) //
				: verticallyFollowing(normalizedDown(execution));

		Predicate<MElement<? extends Element>> collisionFilter = movingUp
				? below(newTop - 1 /* need ≥, not > */)
				: above(newBottom + 1 /* need ≤, not < */);

		return result.filter(collisionFilter).map(InteractionFragments::resolveObstacle);
	}

	private static MElement<? extends Element> normalizedUp(MElement<? extends Element> obstacle) {
		return new SequenceDiagramSwitch<MElement<? extends Element>>() {

			@Override
			public MElement<? extends Element> caseMMessageEnd(MMessageEnd object) {
				// The real element in consideration is the message, and it begins at the send end
				return object.getOtherEnd().filter(MMessageEnd::isSend).map(this::doSwitch).orElse(null);
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
			public MElement<? extends Element> caseMExecution(MExecution object) {
				return object.getStart().<MElement<? extends Element>> map(this::doSwitch).orElse(null);
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMElement(MElement<T> object) {
				return object;
			}

		}.doSwitch(obstacle);
	}

	private static MElement<? extends Element> normalizedDown(MElement<? extends Element> obstacle) {
		return new SequenceDiagramSwitch<MElement<? extends Element>>() {

			@Override
			public MElement<? extends Element> caseMMessageEnd(MMessageEnd object) {
				// The real element in consideration is the message, and it finishes at the receiveend
				return object.getOtherEnd().filter(MMessageEnd::isReceive).map(this::doSwitch).orElse(null);
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMOccurrence(MOccurrence<T> object) {
				// The execution itself precedes any fragments that it spans
				Optional<MElement<? extends Element>> startedExecution = as(object.getStartedExecution(),
						MElement.class);
				return startedExecution.orElse(null);
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
		return flatMapToObj(index, i -> interaction.getElement(fragments.get(i - 1)));
	}

}
