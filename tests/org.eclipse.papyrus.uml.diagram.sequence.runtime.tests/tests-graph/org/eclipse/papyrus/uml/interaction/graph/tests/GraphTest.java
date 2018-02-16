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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.graph.EdgeImpl;
import org.eclipse.papyrus.uml.interaction.internal.graph.GraphComputer;
import org.eclipse.papyrus.uml.interaction.internal.graph.GraphImpl;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Test;

/**
 * Specific unit tests for the dependency graph implementation not covered by
 * the scenario-oriented tests.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
public class GraphTest {

	private Interaction interaction = UMLFactory.eINSTANCE.createInteraction();
	private GraphImpl graph = new GraphImpl(interaction, null);
	private GraphComputer fixture = new GraphComputer(graph);

	/**
	 * Initializes me.
	 */
	public GraphTest() {
		super();
	}

	@Test(expected = IllegalArgumentException.class)
	public void selfCycle() {
		fixture.edge(graph.initial(), graph.initial().getInteractionElement());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cycle() {
		Lifeline ll1 = interaction.createLifeline("LL1");
		Lifeline ll2 = interaction.createLifeline("LL2");

		fixture.edge(graph.initial(), ll1);
		fixture.edge(graph.vertex(ll1), ll2);
		fixture.edge(graph.vertex(ll2), interaction);
	}

	@Test
	public void duplicateEdge() {
		Lifeline ll1 = interaction.createLifeline("LL1");

		EdgeImpl e1 = fixture.edge(graph.initial(), ll1);
		assertThat(fixture.edge(graph.initial(), ll1), sameInstance(e1));
		assertThat(fixture.edge(graph.initial(), ll1), sameInstance(e1));

		assertThat(graph.initial().outgoing(), hasItem(e1));
		assertThat(graph.initial().outgoing().size(), is(1));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void equals_edge() {
		Lifeline ll1 = interaction.createLifeline("LL1");
		Lifeline ll2 = interaction.createLifeline("LL2");

		EdgeImpl e1 = fixture.edge(graph.initial(), ll1);
		EdgeImpl e2 = fixture.edge(graph.initial(), ll2);

		assertThat(e1.equals(e1), is(true));
		assertThat(e1.equals(null), is(false));
		assertThat(e1.equals(e1.from()), is(false));
		assertThat(e1.equals(e2), is(false));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void equals_vertex() {
		Lifeline ll1 = interaction.createLifeline("LL1");
		Lifeline ll2 = interaction.createLifeline("LL2");

		Vertex v1 = graph.vertex(ll1);
		Vertex v2 = graph.vertex(ll2);

		assertThat(v1.equals(v1), is(true));
		assertThat(v1.equals(null), is(false));
		assertThat(v1.equals(v1.getInteractionElement()), is(false));
		assertThat(v1.equals(v2), is(false));
	}
}
