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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.gmf.runtime.notation.util.NotationSwitch;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelResource;
import org.eclipse.papyrus.uml.interaction.graph.Edge;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.graph.Visitor;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.junit.Rule;
import org.junit.Test;

/**
 * This is the {@code BasicDependencyGraphTest} type. Enjoy.
 *
 * @author Christian W. Damus
 */
@ModelResource({ "basic.uml", "basic.notation" })
public class BasicDependencyGraphTest {

	@Rule
	public final ModelFixture model = new ModelFixture();

	/**
	 * Initializes me.
	 */
	public BasicDependencyGraphTest() {
		super();
	}

	@Test
	public void simpleGraph() {
		Graph graph = model.graph();

		System.out.println("Simple Graph");
		System.out.println("======================================");
		graph.initial().walkOutgoing(Visitor.printer(System.out));
		System.out.println("======================================");
	}

	@Test
	public void vertexPredecessorsAreUnique() {
		Vertex vtx = model.vertex("basic::Basic::B_destroyed");
		List<Vertex> predecessors = vtx.predecessors()
				.collect(Collectors.toList());

		assertThat("vertices not unique", predecessors.size(),
				is(new HashSet<>(predecessors).size()));
	}

	@Test
	public void vertexSuccessorsAreUnique() {
		Vertex vtx = model.vertex("basic::Basic::A");
		List<Vertex> successors = vtx.successors().collect(Collectors.toList());

		assertThat("vertices not unique", successors.size(),
				is(new HashSet<>(successors).size()));
	}

	@Test
	public void edgePredecessorsAreUnique() {
		Vertex vtx = model.vertex("basic::Basic::B_destroyed");
		Edge edge = vtx.incoming().get(0);
		List<Edge> predecessors = edge.predecessors()
				.collect(Collectors.toList());

		assertThat("edges not unique", predecessors.size(),
				is(new HashSet<>(predecessors).size()));
	}

	@Test
	public void edgeSuccessorsAreUnique() {
		Vertex vtx = model.vertex("basic::Basic::A");
		Edge edge = vtx.outgoing().get(0);
		List<Edge> successors = edge.successors().collect(Collectors.toList());

		assertThat("edges not unique", successors.size(),
				is(new HashSet<>(successors).size()));
	}

	@Test
	public void edgePredecessorsVisitationeUnique() {
		Vertex vtx = model.vertex("basic::Basic::B_destroyed");
		new EdgeUniqueness().assertPredecessorVisit(vtx);
	}

	@Test
	public void edgeSuccessorsVisitationUnique() {
		Vertex vtx = model.vertex("basic::Basic::make-send");
		new EdgeUniqueness().assertSuccessorVisit(vtx);
	}

	@Test
	public void precedes() {
		Vertex vtx1 = model.vertex("basic::Basic::make-send");
		Vertex vtx2 = model.vertex("basic::Basic::B");

		assertThat(vtx1.precedes(vtx2), is(true));
	}

	@Test
	public void succeeds() {
		Vertex vtx1 = model.graph()
				.vertex(model.getElement("basic::Basic::make-send"));
		Vertex vtx2 = model.graph().vertex(model.getElement("basic::Basic::B"));

		assertThat(vtx2.succeeds(vtx1), is(true));
	}

	@Test
	public void walkSuccessorVertices() {
		Vertex vtx = model.graph().vertex(model.getElement("basic::Basic::B"));
		int count = new VertexCounter().countSuccessors(vtx);
		assertThat(count, is(9));
	}

	@Test
	public void walkPredecessorVertices() {
		Vertex vtx = model.graph().vertex(model.getElement("basic::Basic::B"));
		int count = new VertexCounter().countPredecessors(vtx);
		assertThat(count, is(4));
	}

	@Test
	public void nudgeMessageDown() {
		// First, record old locations of all things
		Map<Element, XY> locations = mapLocations();

		ExampleVerticalRepositionVisitor nudge = new ExampleVerticalRepositionVisitor(
				10);
		Message createMessage = model.getElement("basic::Basic::make",
				Message.class);
		Connector view = (Connector) model.graph().vertex(createMessage)
				.getDiagramView();

		// Nudge the view down
		nudge.doSwitch(view);

		// And all the consequences, too
		nudge.walkSuccessors(model.graph().vertex(createMessage));

		// Get new locations
		Map<Element, XY> newLocations = mapLocations();

		// Don't proceed if the create message, itself, didn't actually move
		assumeThat(newLocations.get(createMessage.getSendEvent()),
				is(locations.get(createMessage.getSendEvent()).plus(0, 10)));

		// Of course, the interaction didn't move
		Interaction interaction = model.getInteraction();
		assertThat(newLocations.get(interaction),
				is(locations.get(interaction)));

		// The A lifeline didn't move, because we moved a message that it
		// initiated
		Lifeline llA = model.getElement("basic::Basic::A", Lifeline.class);
		assertThat(newLocations.get(llA), is(locations.get(llA)));

		// And then check the consequents
		StringBuilder problems = new StringBuilder();
		for (Element next : newLocations.keySet()) {
			// Everything but the interaction and lifeline A should have moved
			// down 10
			if ((next != llA) && (next != interaction)) {
				XY expected = locations.get(next).plus(0, 10);
				XY actual = newLocations.get(next);
				if (!Objects.equals(actual, expected)) {
					String format = "%s ≠ %s: %s";
					if (problems.length() > 0) {
						format = "%n" + format;
					}
					problems.append(
							String.format(format, actual, expected, next));
				}
			}
		}

		assertThat(problems.toString(), is(""));
	}

	//
	// Test framework
	//

	Map<Element, XY> mapLocations() {
		Map<Element, XY> result = new LinkedHashMap<>();

		NotationSwitch locationSwitch = new NotationSwitch() {
			@Override
			public Object caseShape(Shape shape) {
				LayoutConstraint constraint = shape.getLayoutConstraint();
				if (constraint != null) {
					return doSwitch(constraint);
				}
				return null;
			}

			@Override
			public Object caseLocation(Location location) {
				return new XY(location.getX(), location.getY());
			}

			@Override
			public Object caseEdge(org.eclipse.gmf.runtime.notation.Edge edge) {
				if (edge.getElement() instanceof Message) {
					Message message = (Message) edge.getElement();
					RelativeBendpoints bends = (RelativeBendpoints) edge
							.getBendpoints();
					@SuppressWarnings("unchecked")
					List<RelativeBendpoint> points = bends.getPoints();

					XY source = new XY(points.get(0).getSourceX(),
							points.get(0).getSourceY());
					XY target = new XY(points.get(1).getSourceX(),
							points.get(1).getSourceY());

					result.put(message.getSendEvent(), source);
					result.put(message.getReceiveEvent(), target);

					// Consider the message as a whole to be at the source end
					return source;
				}

				// There should be only messages
				return null;
			}
		};

		for (Iterator<EObject> iter = model.getSequenceDiagram().get()
				.eAllContents(); iter.hasNext();) {

			EObject view = iter.next();
			if (view instanceof View) {
				EObject element = ((View) view).getElement();
				if (element instanceof Element) {
					Element uml = (Element) element;
					if (!result.containsKey(uml)) {
						XY location = (XY) locationSwitch.doSwitch(view);
						if (location != null) {
							result.put(uml, location);
						}
					}
				}
			}
		}

		return result;
	}

	//
	// Nested types
	//

	class VertexCounter implements Visitor<Vertex> {
		int count;

		@Override
		public void visit(Vertex vertex) {
			assertThat(vertex, notNullValue());
			assertThat(vertex.getInteractionElement(), notNullValue());
			count++;
		}

		int countPredecessors(Vertex v) {
			count = 0;
			walkPredecessors(v);
			return count;
		}

		int countSuccessors(Vertex v) {
			count = 0;
			walkSuccessors(v);
			return count;
		}
	}

	class EdgeUniqueness implements Visitor<Edge> {
		private Set<Edge> seen = new HashSet<>();

		@Override
		public void visit(Edge edge) {
			assertThat(edge, notNullValue());
			assertThat("non-unique edge visit", seen.add(edge), is(true));
		}

		void assertPredecessorVisit(Edge e) {
			walkPredecessors(e);
		}

		void assertSuccessorVisit(Edge e) {
			walkSuccessors(e);
		}

		void assertPredecessorVisit(Vertex v) {
			v.walkIncoming(this);
		}

		void assertSuccessorVisit(Vertex v) {
			v.walkOutgoing(this);
		}
	}

	static final class XY {
		private final int x;
		private final int y;

		XY(int x, int y) {
			super();

			this.x = x;
			this.y = y;
		}

		public int x() {
			return x;
		}

		public int y() {
			return y;
		}

		public XY plus(int deltaX, int deltaY) {
			return new XY(x + deltaX, y + deltaY);
		}

		@Override
		public int hashCode() {
			int result = (31 * 1) + x;
			result = (31 * result) + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			return (obj != null) && (obj.getClass() == XY.class)
					&& eq((XY) obj);
		}

		private boolean eq(XY other) {
			return (other.x == x) && (other.y == y);
		}

		@Override
		public String toString() {
			return String.format("(%d, %d)", x, y);
		}
	}
}
