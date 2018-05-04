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

package org.eclipse.papyrus.uml.interaction.graph.tests;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.eclipse.papyrus.uml.interaction.graph.GraphPredicates.hasTag;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;
import java.util.Set;

import org.eclipse.papyrus.uml.interaction.graph.Tag;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

/**
 * This is the {@code ComplexDependencyGraphTest} type. Enjoy.
 *
 * @author Christian W. Damus
 */
@ModelResource("codereview.uml")
public class ComplexDependencyGraphTest {

	@Rule
	public final ModelFixture model = new ModelFixture();

	/**
	 * Initializes me.
	 */
	public ComplexDependencyGraphTest() {
		super();
	}

	@Test
	public void complexGraphDependencies() {
		model.verifyGraph().verifyDependencies(from -> {
			switch (from) {
			case "in": // Incoming formal gate
				return asList("validate", "validate-exec", "handle_errors", "!window");
			case "post":
				return asList("proxy", "post-reply", "maybe-servlet");
			case "callback":
				return asList("handle_errors", "!servlet_destroyed");
			case "maybe-servlet":
				return asList("callback", "servlet_destroyed");
			case "callback-send":
				return asList("servlet_destroyed"); // Via general ordering
			default:
				return null;
			}
		}).print("Complex Graph");
	}

	@Test
	public void complexGraphGroups() {
		model.verifyGraph().verifyGroups(group -> {
			switch (group) {
			case "comments": // Lifeline
				// Interaction use can cover multiple lifelines so isn't grouped
				return asList("post-recv", "post-exec", "!post-reply-send", "!create-proxy-send",
						"!async-send", "!handle_errors");
			case "dwrServlet": // Another lifeline
				return asList("ajax-exec", "!maybe-servlet");
			case "validate-exec": // Execution specification
				return asList("post-send", "post-reply-recv", "validate-reply-send", "!validate-recv");
			case "async-exec": // Another execution specification
				// Combined fragment can cover multiple lifelines so isn't grouped
				return asList("!maybe-servlet");
			case "maybe-servlet": // Combined fragment
				return asList("maybe-servlet-opt", "!ajax-send", "!ajax-exec", "!async-exec");
			case "maybe-servlet-opt": // Interaction operand
				return asList("ajax-send", "ajax-recv", "errors-send", "errors-recv", //
						"ajax-exec", "!async-exec");
			default:
				return null;
			}
		});
	}

	@Test
	public void complexGraphLifelineCovers() {
		model.verifyGraph().verifyCovers(group -> {
			switch (group) {
			case "maybe-servlet": // Combined fragment
				return asList("proxy", "dwrServlet", "!comments", "!window");
			case "handle_errors": // Interaction use
				return asList("comments", "window", "!proxy", "!dwrServlet");
			default:
				return null;
			}
		});
	}

	@Test
	public void successorOnLifeline() {
		model.verifyGraph().verifySuccessorOn("comments", "async-send", "post-reply-send");
		model.verifyGraph().verifySuccessorOn("window", "handle-errors-send", "handle_errors");
		model.verifyGraph().verifySuccessorOn("window", "handle_errors", "callback-exec-finish");
	}

	@Test
	public void predecessorOnLifeline() {
		model.verifyGraph().verifyPredecessorOn("comments", "async-send", "create-proxy-send");
		model.verifyGraph().verifyPredecessorOn("window", "handle_errors", "handle-errors-send");
		model.verifyGraph().verifyPredecessorOn("window", "callback-exec-finish", "handle_errors");
	}

	@Test
	public void executionFinishTag() {
		Set<Vertex> finishes = model.graph().vertices().filter(hasTag(Tag.EXECUTION_FINISH))
				.collect(toSet());
		assertThat(finishes.size(), greaterThanOrEqualTo(5));
		finishes.forEach(f -> {
			Optional<Element> exec = f.predecessor(Tag.EXECUTION_FINISH).map(Vertex::getInteractionElement);
			assertThat(exec.isPresent(), is(true));
			assertThat(exec.get(), instanceOf(ExecutionSpecification.class));
			assertThat(((ExecutionSpecification) exec.get()).getFinish(), is(f.getInteractionElement()));
		});
	}

	@Test
	public void executionStartTag() {
		Set<Vertex> starts = model.graph().vertices().filter(hasTag(Tag.EXECUTION_START)).collect(toSet());
		assertThat(starts.size(), greaterThanOrEqualTo(5));
		starts.forEach(s -> {
			Optional<Element> exec = s.successor(Tag.EXECUTION_START).map(Vertex::getInteractionElement);
			assertThat(exec.isPresent(), is(true));
			assertThat(exec.get(), instanceOf(ExecutionSpecification.class));
			assertThat(((ExecutionSpecification) exec.get()).getStart(), is(s.getInteractionElement()));
		});
	}

	//
	// Test framework
	//

	static <N extends Number & Comparable<N>> Matcher<N> greaterThanOrEqualTo(N lowerBound) {
		return new CustomTypeSafeMatcher<N>("≥ " + lowerBound) {
			@Override
			protected boolean matchesSafely(N item) {
				return item.compareTo(lowerBound) >= 0;
			}
		};
	}
}
