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

package org.eclipse.papyrus.uml.interaction.tests.rules;

import static java.util.stream.Collectors.toList;
import static org.eclipse.papyrus.uml.interaction.graph.GraphFunctions.lifelines;
import static org.eclipse.papyrus.uml.interaction.graph.GraphFunctions.predecessorOn;
import static org.eclipse.papyrus.uml.interaction.graph.GraphFunctions.successorOn;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Group;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.graph.Visitor;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.NamedElement;

/**
 * This is the {@code GraphAssertion} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class GraphAssertion {
	public static final boolean DEBUG_PRINT = Boolean.getBoolean("DEBUG_TESTS");

	private final Graph graph;

	private final Map<String, Vertex> verticesByName = new HashMap<>();

	/**
	 * Initializes me.
	 */
	public GraphAssertion(Graph graph) {
		super();

		this.graph = graph;

		index();
	}

	private void index() {
		Visitor<Vertex> indexer = vertex -> {
			Element element = vertex.getInteractionElement();
			if (element instanceof NamedElement) {
				NamedElement named = (NamedElement) element;
				if (named.getName() != null) {
					verticesByName.put(named.getName(), vertex);
				}
			}
		};

		indexer.walkSuccessors(graph.initial());
	}

	public GraphAssertion verifyDependencies(
			Function<? super String, ? extends Collection<String>> successorsFunction) {

		Function<String, Collection<String>> expectation = memoize(successorsFunction);
		for (Map.Entry<String, Vertex> from : verticesByName.entrySet()) {
			Collection<String> expected = expectation.apply(from.getKey());
			if (expected != null) {
				for (String next : expected) {
					if (next.startsWith("!")) {
						next = next.substring(1);
						Vertex to = verticesByName.get(next);
						assertThat(String.format("%s ≻ %s", next, from.getKey()),
								to.succeeds(from.getValue()), is(false));
					} else {
						Vertex to = verticesByName.get(next);
						assertThat(String.format("%s ⊁ %s", next, from.getKey()),
								to.succeeds(from.getValue()), is(true));
					}
				}
			}
		}

		return this;
	}

	public GraphAssertion verifyGroups(
			Function<? super String, ? extends Collection<String>> membersFunction) {

		Function<String, Collection<String>> expectation = memoize(membersFunction);
		for (Map.Entry<String, Vertex> from : verticesByName.entrySet()) {
			Collection<String> expected = expectation.apply(from.getKey());
			if (expected != null) {
				assertThat("not a group: " + from.getKey(), from.getValue(), instanceOf(Group.class));

				@SuppressWarnings("unchecked")
				Group<Vertex> group = (Group<Vertex>) from.getValue();

				for (String next : expected) {
					if (next.startsWith("!")) {
						next = next.substring(1);
						Vertex to = verticesByName.get(next);
						assertThat(String.format("%s ∈ %s", next, from.getKey()),
								group.members().anyMatch(to::equals), is(false));
					} else {
						Vertex to = verticesByName.get(next);
						assertThat(String.format("%s ∉ %s", next, from.getKey()),
								group.members().anyMatch(to::equals), is(true));
					}
				}
			}
		}

		return this;
	}

	public GraphAssertion verifyCovers(
			Function<? super String, ? extends Collection<String>> lifelinesFunction) {

		Function<String, Collection<String>> expectation = memoize(lifelinesFunction);
		for (Map.Entry<String, Vertex> from : verticesByName.entrySet()) {
			Collection<String> expected = expectation.apply(from.getKey());
			if (expected != null) {
				List<Vertex> covered = lifelines().apply(from.getValue()).collect(toList());

				for (String next : expected) {
					if (next.startsWith("!")) {
						next = next.substring(1);
						Vertex to = verticesByName.get(next);
						assertThat(String.format("%s covers %s", from.getKey(), next), covered.contains(to),
								is(false));
					} else {
						Vertex to = verticesByName.get(next);
						assertThat(String.format("%s does not cover %s", from.getKey(), next),
								covered.contains(to), is(true));
					}
				}
			}
		}

		return this;
	}

	public GraphAssertion verifySuccessorOn(String lifeline, String vertex, String successor) {
		Vertex vLifeline = verticesByName.get(lifeline);
		Vertex vVertex = verticesByName.get(vertex);
		Vertex vSuccessor = verticesByName.get(successor);
		assertThat(String.format("%s ⊁ %s on %s", successor, vertex, lifeline),
				successorOn((Lifeline) vLifeline.getInteractionElement()).apply(vVertex), is(vSuccessor));
		return this;
	}

	public GraphAssertion verifyPredecessorOn(String lifeline, String vertex, String predecessor) {
		Vertex vLifeline = verticesByName.get(lifeline);
		Vertex vVertex = verticesByName.get(vertex);
		Vertex vPredecessor = verticesByName.get(predecessor);
		assertThat(String.format("%s ⊀ %s on %s", predecessor, vertex, lifeline),
				predecessorOn((Lifeline) vLifeline.getInteractionElement()).apply(vVertex),
				is(vPredecessor));
		return this;
	}

	public GraphAssertion print(String title) {
		if (DEBUG_PRINT) {
			System.out.println(title);
			System.out.println("======================================");
			graph.initial().walkOutgoing(Visitor.printer(System.out));
			System.out.println("======================================");
		}

		return this;
	}

	static <F, T> Function<F, T> memoize(Function<? super F, ? extends T> function) {
		Map<F, T> memo = new HashMap<>();
		return input -> memo.computeIfAbsent(input, function);
	}
}
