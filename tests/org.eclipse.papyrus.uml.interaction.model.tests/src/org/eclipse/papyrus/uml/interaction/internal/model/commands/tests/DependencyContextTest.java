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

package org.eclipse.papyrus.uml.interaction.internal.model.commands.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.papyrus.uml.interaction.internal.model.commands.DependencyContext;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

/**
 * Unit tests for the {@link DependencyContext} class.
 */
public class DependencyContextTest {

	private static final Object subject = new Object();

	private static final String HELLO = "Hello, world";

	/**
	 * Initializes me.
	 */
	public DependencyContextTest() {
		super();
	}

	@Test
	public void nullContext() {
		DependencyContext ctx = DependencyContext.get();

		assertThat("Cannot put", ctx.put(subject, HELLO), is(true));
		assertThat("Null context not empty", ctx.get(subject, String.class), isAbsent());
	}

	@Test
	public void putAndGet() {
		DependencyContext.getDynamic().withContext(ctx -> {
			assertThat("Cannot put", ctx.put(subject, HELLO), is(true));

			Optional<String> key = ctx.get(subject, String.class);
			assertThat("Key not retrieved", key, isPresent(is(HELLO)));

			return null;
		});
	}

	@Test
	public void apply() {
		DependencyContext.getDynamic().withContext(ctx -> {
			Optional<String> computed = ctx.apply(subject, "key", __ -> HELLO);
			assertThat("No result", computed, isPresent(is(HELLO)));

			Optional<String> key = ctx.get(subject, String.class);
			assertThat("Key not retrieved", key, isPresent(is("key")));

			Optional<String> newComputed = ctx.apply(subject, "key", __ -> HELLO);
			assertThat("Action computed again", newComputed, isAbsent());

			newComputed = ctx.apply(subject, "other", __ -> "Goodb-bye");
			assertThat("Action not computed again", newComputed, isPresent(is("Goodb-bye")));

			key = ctx.get(subject, String.class);
			assertThat("Original key superseded", key, isPresent(is("key")));

			return null;
		});
	}

	@Test
	public void getDynamic() {
		List<DependencyContext> recursion = new ArrayList<>(3);

		String result = DependencyContext.getDynamic().withContext(ctx -> {
			recursion.add(ctx);

			return DependencyContext.getDynamic().withContext(ctx1 -> {
				recursion.add(ctx1);

				return DependencyContext.getDynamic().withContext(ctx2 -> {
					recursion.add(ctx2);
					return HELLO;
				});
			});
		});

		List<DependencyContext> unique = recursion.stream().distinct().collect(Collectors.toList());
		assertThat("Multiple contexts created", unique.size(), is(1));
		assertThat("No actual context", unique.get(0), notNullValue());
		assertThat("Wrong result from recursion", result, is(HELLO));
	}

	@Test
	public void childContextInheritance() {
		DependencyContext.getDynamic().withContext(ctx -> {
			ctx.put(subject, HELLO);

			DependencyContext.ChildContext<String> nested = ctx.runNested(() -> {
				DependencyContext child = DependencyContext.get();

				assertThat("Same context", child, not(sameInstance(ctx)));

				return DependencyContext.get().get(subject, String.class).orElse(null);
			});

			assertThat("Context not inherited", nested.get(), is(HELLO));

			return null;
		});
	}

	@Test
	public void childContextCommit() {
		DependencyContext ctx = DependencyContext.getDynamic();

		DependencyContext.ChildContext<Void> nested = ctx.runNested(() -> {
			DependencyContext.get().put(subject, HELLO);
		});

		Optional<String> key = ctx.get(subject, String.class);
		assertThat("Parent context altered", key, isAbsent());

		nested.commit();

		key = ctx.get(subject, String.class);
		assertThat("Parent context not updated", key, isPresent(is(HELLO)));
	}

	@Test
	public void childContextContinuation1() {
		DependencyContext.getDynamic().withContext(ctx -> {
			DependencyContext.ChildContext<String> nested1 = ctx.runNested(() -> {
				return HELLO;
			});

			DependencyContext.ChildContext<Integer> nested2 = nested1.andThen(string -> {
				Integer result = string.length();
				return DependencyContext.get().apply(subject, result, __ -> result).orElse(0);
			});

			assertThat("Continuation not performed", nested2.get(), is(HELLO.length()));

			nested2.commit();

			Optional<Integer> key = ctx.get(subject, Integer.class);
			assertThat("Parent context not updated", key, isPresent(is(HELLO.length())));

			return null;
		});
	}

	@Test
	public void childContextContinuation2() {
		DependencyContext ctx = DependencyContext.getDynamic();

		DependencyContext.ChildContext<Void> nested1 = ctx.runNested(() -> {
			DependencyContext.get().put(subject, HELLO);
		});

		DependencyContext.ChildContext<Integer> nested2 = nested1.andThen(__ -> {
			return DependencyContext.get().get(subject, String.class).orElse("").length();
		});

		assertThat("Continuation not performed", nested2.get(), is(HELLO.length()));

		nested1.commit(); // This should have no effect

		Optional<String> key = ctx.get(subject, String.class);
		assertThat("Parent context altered", key, isAbsent());

		nested2.commit();

		key = ctx.get(subject, String.class);
		assertThat("Parent context not updated", key, isPresent(is(HELLO)));
	}

	//
	// Test framework
	//

	<T> Matcher<Optional<T>> isPresent(Matcher<? super T> valueMatcher) {
		return new TypeSafeDiagnosingMatcher<Optional<T>>(Optional.class) {
			@Override
			public void describeTo(Description description) {
				description.appendText("present optional that ");
				description.appendDescriptionOf(valueMatcher);
			}

			@Override
			protected boolean matchesSafely(Optional<T> item, Description mismatchDescription) {
				boolean result = false;

				if (!item.isPresent()) {
					mismatchDescription.appendText("optional is absent");
				} else {
					result = valueMatcher.matches(item.get());
					if (!result) {
						valueMatcher.describeMismatch(item.get(), mismatchDescription);
					}
				}

				return result;
			}
		};
	}

	<T> Matcher<Optional<T>> isAbsent() {
		return new TypeSafeDiagnosingMatcher<Optional<T>>(Optional.class) {
			@Override
			public void describeTo(Description description) {
				description.appendText("absent optional");
			}

			@Override
			protected boolean matchesSafely(Optional<T> item, Description mismatchDescription) {
				boolean result = !item.isPresent();

				if (!result) {
					mismatchDescription.appendText("optional is present: ");
					mismatchDescription.appendValue(item.get());
				}

				return result;
			}
		};
	}

}
