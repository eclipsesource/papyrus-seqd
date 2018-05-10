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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers;

import static org.hamcrest.CoreMatchers.is;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matchers for assertions on GEF.
 *
 * @author Christian W. Damus
 */
public class GEFMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private GEFMatchers() {
		super();
	}

	/**
	 * Matcher for a rectangle.
	 *
	 * @param x
	 *            the expected x location
	 * @param y
	 *            the expected y location
	 * @param width
	 *            the expected width
	 * @param height
	 *            the expected height
	 *
	 * @return the rectangle matcher
	 */
	public static Matcher<Rectangle> isRect(int x, int y, int width, int height) {
		return isRect(is(x), is(y), is(width), is(height));
	}

	/**
	 * Matcher for a rectangle.
	 *
	 * @param x
	 *            matcher for the x location, or {@code null} if none
	 * @param y
	 *            matcher for the y location, or {@code null} if none
	 * @param width
	 *            matcher for the width, or {@code null} if none
	 * @param height
	 *            matcher for the height or {@code null} if none
	 *
	 * @return the rectangle matcher
	 */
	public static Matcher<Rectangle> isRect(Matcher<? super Integer> x, Matcher<? super Integer> y,
			Matcher<? super Integer> width, Matcher<? super Integer> height) {

		return new TypeSafeDiagnosingMatcher<Rectangle>() {
			@Override
			public void describeTo(Description description) {
				boolean appended = false;
				description.appendText("rectangle that ");

				SelfDescribing[] parts = { x, y, width, height };
				String[] names = { "x", "y", "width", "height" };
				for (int i = 0; i < parts.length; i++) {
					if (parts[i] == null) {
						continue;
					}

					if (appended) {
						description.appendText(" and ");
					}
					description.appendText(names[i]).appendText(" that ");
					description.appendDescriptionOf(parts[i]);
					appended = true;
				}

				if (!appended) {
					description.appendText("exists");
				}
			}

			@Override
			protected boolean matchesSafely(Rectangle item, Description mismatchDescription) {
				if ((x != null) && !x.matches(item.x())) {
					x.describeMismatch(item.x(), mismatchDescription);
					return false;
				}
				if ((y != null) && !y.matches(item.y())) {
					y.describeMismatch(item.y(), mismatchDescription);
					return false;
				}
				if ((width != null) && !width.matches(item.width())) {
					width.describeMismatch(item.width(), mismatchDescription);
					return false;
				}
				if ((height != null) && !height.matches(item.height())) {
					height.describeMismatch(item.height(), mismatchDescription);
					return false;
				}

				return true;
			}
		};
	}

	/**
	 * Matcher for a point.
	 *
	 * @param x
	 *            the expected x location
	 * @param y
	 *            the expected y location
	 *
	 * @return the point matcher
	 */
	public static Matcher<Point> isPoint(int x, int y) {
		return isPoint(is(x), is(y));
	}

	/**
	 * Matcher for a point.
	 *
	 * @param x
	 *            matcher for the x location, or {@code null} if none
	 * @param y
	 *            matcher for the y location, or {@code null} if none
	 *
	 * @return the point matcher
	 */
	public static Matcher<Point> isPoint(Matcher<? super Integer> x, Matcher<? super Integer> y) {

		return new TypeSafeDiagnosingMatcher<Point>() {
			@Override
			public void describeTo(Description description) {
				boolean appended = false;
				description.appendText("point that ");

				SelfDescribing[] parts = { x, y };
				String[] names = { "x", "y" };
				for (int i = 0; i < parts.length; i++) {
					if (parts[i] == null) {
						continue;
					}

					if (appended) {
						description.appendText(" and ");
					}
					description.appendText(names[i]).appendText(" that ");
					description.appendDescriptionOf(parts[i]);
					appended = true;
				}

				if (!appended) {
					description.appendText("exists");
				}
			}

			@Override
			protected boolean matchesSafely(Point item, Description mismatchDescription) {
				if ((x != null) && !x.matches(item.x())) {
					x.describeMismatch(item.x(), mismatchDescription);
					return false;
				}
				if ((y != null) && !y.matches(item.y())) {
					y.describeMismatch(item.y(), mismatchDescription);
					return false;
				}

				return true;
			}
		};
	}

	/**
	 * Matcher for a dimension.
	 *
	 * @param width
	 *            the expected width
	 * @param height
	 *            the expected height
	 *
	 * @return the dimension matcher
	 */
	public static Matcher<Dimension> isSize(int width, int height) {
		return isSize(is(width), is(height));
	}

	/**
	 * Matcher for a dimension.
	 *
	 * @param width
	 *            matcher for the width, or {@code null} if none
	 * @param height
	 *            matcher for the height or {@code null} if none
	 *
	 * @return the dimension matcher
	 */
	public static Matcher<Dimension> isSize(Matcher<? super Integer> width,
			Matcher<? super Integer> height) {

		return new TypeSafeDiagnosingMatcher<Dimension>() {
			@Override
			public void describeTo(Description description) {
				boolean appended = false;
				description.appendText("dimension that ");

				SelfDescribing[] parts = { width, height };
				String[] names = { "width", "height" };
				for (int i = 0; i < parts.length; i++) {
					if (parts[i] == null) {
						continue;
					}

					if (appended) {
						description.appendText(" and ");
					}
					description.appendText(names[i]).appendText(" that ");
					description.appendDescriptionOf(parts[i]);
					appended = true;
				}

				if (!appended) {
					description.appendText("exists");
				}
			}

			@Override
			protected boolean matchesSafely(Dimension item, Description mismatchDescription) {
				if ((width != null) && !width.matches(item.width())) {
					width.describeMismatch(item.width(), mismatchDescription);
					return false;
				}
				if ((height != null) && !height.matches(item.height())) {
					height.describeMismatch(item.height(), mismatchDescription);
					return false;
				}

				return true;
			}
		};
	}

}
