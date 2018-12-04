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

package org.eclipse.papyrus.uml.interaction.graph;

import static org.eclipse.papyrus.uml.interaction.graph.GraphPredicates.after;
import static org.eclipse.papyrus.uml.interaction.graph.GraphPredicates.before;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.internal.graph.GraphImpl;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;

/**
 * A dependency graph. Mutability of the graph, its vertices and edges, is implementation-dependent but is to
 * be assumed to be immutable unless documented otherwise on a particular implementation.
 *
 * @author Christian W. Damus
 */
public interface Graph {
	/**
	 * Obtains the initial vertex in the dependency graph. This is usually a vertex
	 * {@linkplain Vertex#getInteractionElement() representing} the {@linkplain Interaction interaction} and
	 * also {@linkplain Vertex#getDiagramView() representing} the {@linkplain Diagram sequence diagram}. It
	 * will never have any {@link Vertex#incoming() incoming edges}.
	 *
	 * @return the initial vertex of the dependency graph
	 * @see Vertex#getInteractionElement()
	 * @see Interaction
	 * @see Vertex#getDiagramView()
	 * @see Diagram
	 */
	Vertex initial();

	/**
	 * Obtains the canonical vertex in this graph for the given {@code element}.
	 *
	 * @param element
	 *            an interaction element
	 * @return its vertex
	 */
	Vertex vertex(Element element);

	/**
	 * Obtains the canonical vertex in this graph for the given diagram notation {@code view}.
	 *
	 * @param view
	 *            a notation view in the diagram
	 * @return its corresponding vertex
	 */
	Optional<Vertex> vertex(View view);

	/**
	 * Obtain a comparator that sorts vertices according to the overall interaction ordering, with a stable
	 * but arbitrary relative ordering of parallel occurrences.
	 * 
	 * @return the interaction ordering comparator
	 */
	Comparator<Vertex> interactionOrdering();

	/**
	 * Obtain a stream over my vertices in {@linkplain #interactionOrdering() interaction order}.
	 * 
	 * @return my vertices, in interaction order
	 * @see #interactionOrdering()
	 */
	default Stream<Vertex> vertices() {
		Predicate<Vertex> filter = Vertex::exists;

		return Stream.concat(Stream.of(initial()).filter(filter),
				// Always the interaction first, and the rest sorted after
				initial().successors().filter(filter).sorted(interactionOrdering()));
	}

	/**
	 * Obtain a stream over my vertices of the given interaction element {@code type}, in
	 * {@linkplain #interactionOrdering() interaction order}.
	 * 
	 * @type the Ecore type of the interaction elements' which vertices to obtain
	 * @return my vertices, in interaction order
	 * @see #interactionOrdering()
	 */
	default Stream<Vertex> vertices(EClass type) {
		Predicate<Vertex> typeFilter = v -> type.isInstance(v.getInteractionElement());
		typeFilter = typeFilter.and(Vertex::exists);

		return Stream.concat(Stream.of(initial()).filter(typeFilter),
				// Always the interaction first, and the rest sorted after
				initial().successors().filter(typeFilter).sorted(interactionOrdering()));
	}

	/**
	 * Obtain the next vertex following a given {@code vertex} in interaction order.
	 * 
	 * @param vertex
	 *            a vertex
	 * @return the next vertex, if any
	 */
	default Optional<Vertex> following(Vertex vertex) {
		return vertices().filter(after(vertex)).findFirst();
	}

	/**
	 * Walk a visitor over my vertices, in {@linkplain #interactionOrdering() interaction order}.
	 * 
	 * @param visitor
	 *            the visitor to process my vertices
	 */
	default void walkVertices(Visitor<? super Vertex> visitor) {
		vertices().forEach(v -> v.accept(visitor));
	}

	/**
	 * Walk a visitor over my vertices, in {@linkplain #interactionOrdering() interaction order}.
	 * 
	 * @type the Ecore type of the interaction elements' which vertices to walk
	 * @param visitor
	 *            the visitor to process my vertices
	 */
	default void walkVertices(EClass type, Visitor<? super Vertex> visitor) {
		vertices(type).forEach(v -> v.accept(visitor));
	}

	/**
	 * Walk a visitor over my vertices that come after the given {@code reference} vertex, in
	 * {@linkplain #interactionOrdering() interaction order}.
	 * 
	 * @param reference
	 *            the starting vertex (which is <em>not</em> visited)
	 * @param visitor
	 *            the visitor to process my vertices
	 */
	default void walkAfter(Vertex reference, Visitor<? super Vertex> visitor) {
		walkAfter(reference, null, visitor);
	}

	/**
	 * Walk a visitor over my vertices that come after the given {@code reference} vertex, in
	 * {@linkplain #interactionOrdering() interaction order}, that match a given predicate.
	 * 
	 * @param reference
	 *            the starting vertex (which is <em>not</em> visited)
	 * @param shouldVisit
	 *            a predicate matching vertices that should be visited
	 * @param visitor
	 *            the visitor to process my vertices
	 */
	default void walkAfter(Vertex reference, Predicate<? super Vertex> shouldVisit,
			Visitor<? super Vertex> visitor) {

		Predicate<Vertex> filter = after(reference);
		if (shouldVisit != null) {
			filter = filter.and(shouldVisit);
		}

		vertices().filter(filter).forEach(v -> v.accept(visitor));
	}

	/**
	 * Walk a visitor over my vertices that come before the given {@code reference} vertex, in reverse
	 * {@linkplain #interactionOrdering() interaction order}.
	 * 
	 * @param reference
	 *            the starting vertex (which is <em>not</em> visited)
	 * @param visitor
	 *            the visitor to process my vertices
	 */
	default void walkBefore(Vertex reference, Visitor<? super Vertex> visitor) {
		// We need to turn the vertices around for reverse interaction order
		walkBefore(reference, null, visitor);
	}

	/**
	 * Walk a visitor over my vertices that come before the given {@code reference} vertex, in reverse
	 * {@linkplain #interactionOrdering() interaction order}, that match a given predicate.
	 * 
	 * @param reference
	 *            the starting vertex (which is <em>not</em> visited)
	 * @param shouldVisit
	 *            a predicate matching vertices that should be visited
	 * @param visitor
	 *            the visitor to process my vertices
	 */
	default void walkBefore(Vertex reference, Predicate<? super Vertex> shouldVisit,
			Visitor<? super Vertex> visitor) {

		Predicate<Vertex> filter = before(reference);
		if (shouldVisit != null) {
			filter = filter.and(shouldVisit);
		}

		// We need to turn the vertices around for reverse interaction order
		List<Vertex> toVisit = vertices().filter(filter).collect(Collectors.toList());
		Collections.reverse(toVisit);
		toVisit.forEach(v -> v.accept(visitor));
	}

	default Consumer<Element> update(Element context) {
		return GraphImpl.update(this, context);
	}

	/**
	 * Compute the <em>Dependency Graph</em> of the given {@code interaction}. If a diagram is provided, then
	 * the resultant graph will have
	 * <ul>
	 * <li>a {@linkplain Vertex#getDiagramView() view} associated with each {@link Vertex} for which the
	 * {@linkplain Vertex#getInteractionElement() interaction element} has a visualization in the diagram</li>
	 * <li>a distinct {@link Vertex} for each view of an element that is visualized more than once in the
	 * diagram</li>
	 * <li>as appropriate, a {@link Vertex} for each {@link org.eclipse.gmf.runtime.notation.View View} in the
	 * diagram that does not have a corresponding interaction element</li>
	 * </ul>
	 * 
	 * @param interaction
	 *            an interaction (required)
	 * @param sequenceDiagram
	 *            an optional sequence diagram in which the {@code interaction} is visualized
	 * @return the dependency graph
	 * @throws NullPointerException
	 *             if the {@code interaction} is {@code null}
	 */
	static Graph compute(Interaction interaction, Diagram sequenceDiagram) {
		return GraphImpl.getInstance(interaction, sequenceDiagram);
	}
}
