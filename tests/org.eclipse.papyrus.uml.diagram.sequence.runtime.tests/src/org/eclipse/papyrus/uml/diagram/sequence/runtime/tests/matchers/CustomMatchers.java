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

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Optional;

import org.eclipse.uml2.uml.NamedElement;
import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Various Hamcrest matchers not provided by Hamcrest.
 *
 * @author Christian W. Damus
 */
public class CustomMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private CustomMatchers() {
		super();
	}

	public static Matcher<NamedElement> nameThat(Matcher<? super String> matcher) {
		return new FeatureMatcher<NamedElement, String>(matcher, "name", "name") {
			@Override
			protected String featureValueOf(NamedElement actual) {
				return actual.getName();
			}
		};
	}

	public static Matcher<NamedElement> isNamed(String name) {
		return nameThat(is(name));
	}

	public static <T> Matcher<Optional<T>> isAbsent() {
		return not(isPresent(anything()));
	}

	public static <T> Matcher<Optional<T>> isPresent(T object) {
		return isPresent(is(object));
	}

	public static <T> Matcher<Optional<T>> isPresent(Matcher<? super T> matcher) {
		return new TypeSafeDiagnosingMatcher<Optional<T>>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("has value that ");
				description.appendDescriptionOf(matcher);
			}

			@Override
			protected boolean matchesSafely(Optional<T> item, Description failure) {
				boolean result = item.isPresent();

				if (!result) {
					failure.appendText("not present");
				} else {
					result = matcher.matches(item.get());
					if (!result) {
						matcher.describeMismatch(item.get(), failure);
					}
				}

				return result;
			}
		};
	}
}
