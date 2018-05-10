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

import static org.hamcrest.CoreMatchers.is;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matchers for assertions on notation.
 *
 * @author Christian W. Damus
 */
public class NotationMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private NotationMatchers() {
		super();
	}

	/**
	 * Matcher for a bounds.
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
	 * @return the bounds matcher
	 */
	public static Matcher<Bounds> isBounds(int x, int y, int width, int height) {
		return isBounds(is(x), is(y), is(width), is(height));
	}

	/**
	 * Matcher for a bounds.
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
	 * @return the bounds matcher
	 */
	public static Matcher<Bounds> isBounds(Matcher<? super Integer> x, Matcher<? super Integer> y,
			Matcher<? super Integer> width, Matcher<? super Integer> height) {

		return new TypeSafeDiagnosingMatcher<Bounds>() {
			@Override
			public void describeTo(Description description) {
				boolean appended = false;
				description.appendText("bounds that ");

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
			protected boolean matchesSafely(Bounds item, Description mismatchDescription) {
				if ((x != null) && !x.matches(item.getX())) {
					x.describeMismatch(item.getX(), mismatchDescription);
					return false;
				}
				if ((y != null) && !y.matches(item.getY())) {
					y.describeMismatch(item.getY(), mismatchDescription);
					return false;
				}
				if ((width != null) && !width.matches(item.getWidth())) {
					width.describeMismatch(item.getWidth(), mismatchDescription);
					return false;
				}
				if ((height != null) && !height.matches(item.getHeight())) {
					height.describeMismatch(item.getHeight(), mismatchDescription);
					return false;
				}

				return true;
			}
		};
	}

}
