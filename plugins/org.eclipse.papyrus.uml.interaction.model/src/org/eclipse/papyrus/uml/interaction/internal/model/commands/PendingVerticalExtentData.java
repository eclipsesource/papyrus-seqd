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

import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.verticallyBetween;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.max;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.min;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.stream;

import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates;
import org.eclipse.papyrus.uml.interaction.model.util.Optionals;
import org.eclipse.uml2.uml.Element;

/**
 * A {@linkplain DependencyContext context} variable that records the pending vertical extent of an element
 * being resized.
 * 
 * @see DependencyContext
 */
public final class PendingVerticalExtentData {
	private final MElement<? extends Element> element;

	private OptionalInt top;

	private OptionalInt bottom;

	private PendingVerticalExtentData(MElement<? extends Element> element) {
		super();

		this.element = element;
	}

	/**
	 * Queries the element being resized.
	 * 
	 * @return the element
	 */
	public MElement<? extends Element> getElement() {
		return element;
	}

	/**
	 * Queries the pending top of the {@link #getElement() element}.
	 * 
	 * @return the pending top
	 */
	public OptionalInt getPendingTop() {
		return top.isPresent() ? top : element.getTop();
	}

	/**
	 * Queries the pending bottom of the {@link #getElement() element}.
	 * 
	 * @return the pending bottom
	 */
	public OptionalInt getPendingBottom() {
		return bottom.isPresent() ? bottom : element.getBottom();
	}

	/**
	 * Queries whether an {@code element} is being moved.
	 * 
	 * @param element
	 *            an element
	 * @return {@code true} if it is being moved; {@code false}, otherwise, for example if it is being
	 *         reshaped (only top or bottom being moved but not both) or not being changed at all
	 */
	public boolean isMoving() {
		return top.isPresent() && bottom.isPresent();
	}

	/**
	 * Query the pending top of an {@code element}.
	 * 
	 * @param element
	 *            an element that may be in the process of being resized
	 * @return its pending top
	 */
	public static OptionalInt getPendingTop(MElement<? extends Element> element) {
		return DependencyContext.get().get(element, PendingVerticalExtentData.class)
				.map(PendingVerticalExtentData::getPendingTop).orElseGet(element::getTop);
	}

	/**
	 * Query the pending bottom of an {@code element}.
	 * 
	 * @param element
	 *            an element that may be in the process of being resized
	 * @return its pending bottom
	 */
	public static OptionalInt getPendingBottom(MElement<? extends Element> element) {
		return DependencyContext.get().get(element, PendingVerticalExtentData.class)
				.map(PendingVerticalExtentData::getPendingBottom).orElseGet(element::getBottom);
	}

	/**
	 * Record an {@code element} that is in process of being resized.
	 * 
	 * @param element
	 *            an element that is being resized
	 * @param pendingTop
	 *            its pending top
	 * @param pendingBottom
	 *            its pending bottom
	 */
	static void setPendingVerticalExtent(MElement<? extends Element> element, OptionalInt pendingTop,
			OptionalInt pendingBottom) {

		if (pendingTop.isPresent() || pendingBottom.isPresent()) {
			PendingVerticalExtentData data = DependencyContext.get().get(element,
					PendingVerticalExtentData.class, () -> new PendingVerticalExtentData(element));
			data.top = pendingTop;
			data.bottom = pendingBottom;
		}
	}

	/**
	 * Queries whether an {@code element} is {@linkplain #isMoving() being moved}.
	 * 
	 * @param element
	 *            an element
	 * @return {@code true} if it is being moved; {@code false}, otherwise, for example if it is being
	 *         reshaped (only top or bottom being moved but not both) or not being changed at all
	 * @see #isMoving()
	 */
	public static boolean isMoving(MElement<? extends Element> element) {
		return DependencyContext.get().get(element, PendingVerticalExtentData.class)
				.map(PendingVerticalExtentData::isMoving).orElse(Boolean.FALSE).booleanValue();
	}

	/**
	 * Obtain a predicate that tests whether an element currently spans or will span a given absolute Y
	 * position.
	 * 
	 * @param absoluteY
	 *            the absolute Y coördinate to test
	 * @return the spanning predicate
	 */
	public static <T extends MElement<? extends Element>> Predicate<T> spans(int absoluteY) {
		return el -> {
			OptionalInt top = getPendingTop(el);
			OptionalInt bottom = getPendingBottom(el);

			return top.isPresent() && bottom.isPresent() && (top.getAsInt() <= absoluteY)
					&& (bottom.getAsInt() >= absoluteY);
		};
	}

	/**
	 * Query all occurrences affected by the re-shaping of an {@code execution}. This includes
	 * <ul>
	 * <li>the {@link MExecution#getStart() start} and {@link MExecution#getFinish() finish} occurrences</li>
	 * <li>occurrences currently {@linkplain MExecution#getOccurrences() spanned} by the
	 * {@code execution}</li>
	 * <li>occurrences that will {@linkplain #spans(int) become spanned} by the {@code execution}</li>
	 * </ul>
	 * 
	 * @param execution
	 *            an execution that is being resized/moved
	 * @return all occurrences affected by the pending resize/move operation
	 */
	public static Stream<MOccurrence<? extends Element>> affectedOccurrences(MExecution execution) {
		OptionalInt top = min(getPendingTop(execution), execution.getTop());
		OptionalInt bottom = max(getPendingBottom(execution), execution.getBottom());

		if (!top.isPresent() || !bottom.isPresent()) {
			// Doesn'y make sense; can't determine any spanned occurrences
			return Stream.empty();
		}

		Stream<MOccurrence<? extends Element>> bracketing = stream(execution.getStart(),
				execution.getFinish());
		Stream<MOccurrence<? extends Element>> spannedNow = execution.getOccurrences().stream();

		MLifeline lifeline = Optionals.as(PendingChildData.getPendingOwner(execution), MLifeline.class)
				.orElse(execution.getOwner());
		Stream<MOccurrence<? extends Element>> futureSpanned = lifeline.getOccurrences().stream()
				.filter(verticallyBetween(top.getAsInt(), bottom.getAsInt()));

		return Stream.concat(Stream.concat(bracketing, spannedNow), futureSpanned).distinct();
	}

	/**
	 * Obtain a predicate that queries whether an element is spanned by the given potentially {@code spanning}
	 * element at the time of the query's evaluation, based on future knowledge of vertical extents.
	 * 
	 * @param spanning
	 *            an element that may or may not span other elements
	 * @return the is-spanned-by-baesd-on-future-vertical-extends predicate
	 */
	public static Predicate<MElement<? extends Element>> spannedBy(MElement<? extends Element> spanning) {
		return LogicalModelPredicates.verticallyBetween( //
				() -> getPendingTop(spanning).orElse(Integer.MAX_VALUE), //
				() -> getPendingBottom(spanning).orElse(Integer.MIN_VALUE));
	}
}
