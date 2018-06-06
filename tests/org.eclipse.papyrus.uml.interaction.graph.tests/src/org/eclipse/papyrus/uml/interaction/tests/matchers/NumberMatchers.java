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

package org.eclipse.papyrus.uml.interaction.tests.matchers;

import static java.lang.Math.abs;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Matchers for numeric values.
 *
 * @author Christian W. Damus
 */
public class NumberMatchers {

	private static AtomicReference<Double> standardTolerance = new AtomicReference<>(1.0);

	/**
	 * Not instantiable by clients.
	 */
	private NumberMatchers() {
		super();
	}

	/**
	 * Obtain a matcher asserting that a number is near to some expected quantity (a
	 * fuzzy match).
	 *
	 * @param expected
	 *            the expected value
	 * @param tolerance
	 *            a non-negative tolerance for error
	 * @return the fuzzy matcher
	 */
	public static <N extends Number & Comparable<N>> Matcher<N> isNear(N expected, N tolerance) {
		String message = String.format("%s ± %s", expected, requireNonNegative(tolerance));

		if ((expected instanceof Double) || (expected instanceof Float)) {
			return NumberMatchers.<N>isNear(message,
					nearDouble(expected.doubleValue(), tolerance.doubleValue()));
		}

		return NumberMatchers.<N>isNear(message, nearLong(expected.longValue(), tolerance.longValue()));
	}

	private static <N extends Number & Comparable<N>> N requireNonNegative(N value) {
		// Assume not (we don't work with BigDecimals etc.)
		N result = null;

		if ((value instanceof Double) || (value instanceof Float)) {
			result = value.doubleValue() >= 0.0 ? value : null;
		} else if ((value instanceof Long) || (value instanceof Integer) || (value instanceof Short)
				|| (value instanceof Byte)) {
			result = value.intValue() >= 0 ? value : null;
		}

		if (result == null) {
			throw new IllegalArgumentException("negative tolerance " + value);
		}

		return result;
	}

	private static <N extends Number & Comparable<N>> Matcher<N> isNear(String message,
			Predicate<? super N> predicate) {

		return new BaseMatcher<N>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(message);
			}

			@Override
			public boolean matches(Object item) {
				@SuppressWarnings("unchecked")
				N actual = (N) item;
				return predicate.test(actual);
			}
		};
	}

	private static <N extends Number & Comparable<N>> Predicate<N> nearDouble(double expected,
			double tolerance) {
		return actual -> abs(actual.doubleValue() - expected) <= tolerance;
	}

	private static <N extends Number & Comparable<N>> Predicate<N> nearLong(long expected, long tolerance) {
		return actual -> abs(actual.longValue() - expected) <= tolerance;
	}

	/**
	 * Obtain a matcher asserting that a number is within the
	 * {@linkplain #getStandardTolerance() standard tolerance} of some expected
	 * quantity.
	 *
	 * @param expected
	 *            the expected value
	 * @return the fuzzy matcher
	 */
	public static <N extends Number & Comparable<N>> Matcher<N> isNear(N expected) {

		if ((expected instanceof Double) || (expected instanceof Float)) {
			String message = String.format("%s ± %s", expected, getStandardTolerance());
			return NumberMatchers.<N>isNear(message,
					nearDouble(expected.doubleValue(), getStandardTolerance()));
		}

		String message = String.format("%s ± %s", expected, (long) getStandardTolerance());
		return NumberMatchers.<N>isNear(message,
				nearLong(expected.longValue(), (long) getStandardTolerance()));
	}

	/**
	 * Queries the standard tolerance for fuzzy assertions, especially for such
	 * common tasks as verifying geometries in diagrams that can be off according to
	 * HW/SW platform variability. The default standard tolerance is one
	 * (<tt>1.0</tt>).
	 *
	 * @return the current standard tolerance
	 *
	 * @see #setStandardTolerance(double)
	 */
	public static double getStandardTolerance() {
		return standardTolerance.get();
	}

	/**
	 * Changes the standard tolerance for fuzzy assertions, especially for such
	 * common tasks as verifying geometries in diagrams that can be off according to
	 * HW/SW platform variability.
	 *
	 * @param tolerance
	 *            the new current standard tolerance
	 * @return the previous value of the standard tolerance
	 *
	 * @see #getStandardTolerance()
	 */
	public static double setStandardTolerance(double tolerance) {
		return standardTolerance.getAndSet(requireNonNegative(tolerance));
	}
}
