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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Element;

/**
 * Protocol for a vertex in the dependency graph.
 *
 * @author Christian W. Damus
 */
public interface Vertex extends Visitable<Vertex>, Taggable {
	/**
	 * Obtain the edges incoming to me.
	 *
	 * @return my unique, ordered, and unmodifiable incoming edges
	 */
	List<Edge> incoming();

	/**
	 * Obtain the edges outgoing from me.
	 *
	 * @return my unique, ordered, and unmodifiable outgoing edges
	 */
	List<Edge> outgoing();

	/**
	 * Obtains the interaction element that I represent in the dependency graph.
	 *
	 * @return my interaction element, or {@code null} if I represent only a notation
	 *         {@linkplain #getDiagramView() view}
	 * @see #getDiagramView()
	 */
	Element getInteractionElement();

	/**
	 * Obtains the diagram view that I represent in the dependency graph.
	 *
	 * @return my diagram view, or {@code null} if I represent only a semantic
	 *         {@linkplain #getInteractionElement() interaction element}
	 * @see #getInteractionElement()
	 */
	View getDiagramView();

	/**
	 * Walk a {@code visitor} over my incoming edges and their predecessors trasitively upstream.
	 *
	 * @param visitor
	 *            an edge visitor
	 */
	default void walkIncoming(Visitor<? super Edge> visitor) {
		incoming().stream().flatMap(in -> Stream.concat(Stream.of(in), in.predecessors())).distinct()
				.forEach(visitor::visit);
	}

	/**
	 * Walk a {@code visitor} over my outgoing edges and their successors trasitively downstream.
	 *
	 * @param visitor
	 *            an edge visitor
	 */
	default void walkOutgoing(Visitor<? super Edge> visitor) {
		outgoing().stream().flatMap(out -> Stream.concat(Stream.of(out), out.successors())).distinct()
				.forEach(visitor::visit);
	}

	/**
	 * Queries whether I precede an{@code other} vertex.
	 *
	 * @param other
	 *            a vertex
	 * @return whether I precede the {@code other} in the interaction
	 */
	default boolean precedes(Vertex other) {
		return successors().anyMatch(other::equals);
	}

	/**
	 * Queries whether I succeed an{@code other} vertex.
	 *
	 * @param other
	 *            a vertex
	 * @return whether I succeed the {@code other} in the interaction
	 */
	default boolean succeeds(Vertex other) {
		return predecessors().anyMatch(other::equals);
	}

	/**
	 * Query my predecessor via an edge that has the given {@code tag}.
	 * 
	 * @param tag
	 *            the edge tag to filter on
	 * @return my predecessor via the {@code tag}
	 * @throws NullPointerException
	 *             if the {@code tag} is {@code null}
	 */
	default Optional<Vertex> predecessor(Tag tag) {
		Objects.requireNonNull(tag);
		return incoming().stream().filter(GraphPredicates.hasTag(tag)) //
				.findAny().map(Edge::from);
	}

	/**
	 * Query my successor via an edge that has the given {@code tag}.
	 * 
	 * @param tag
	 *            the edge tag to filter on
	 * @return my successor via the {@code tag}
	 * @throws NullPointerException
	 *             if the {@code tag} is {@code null}
	 */
	default Optional<Vertex> successor(Tag tag) {
		Objects.requireNonNull(tag);
		return outgoing().stream().filter(GraphPredicates.hasTag(tag)) //
				.findAny().map(Edge::to);
	}

	@Override
	default void accept(Visitor<? super Vertex> visitor) {
		visitor.visit(this);
	}

	@Override
	default Stream<Vertex> immediateSuccessors() {
		return outgoing().stream().map(Edge::to);
	}

	@Override
	default Stream<Vertex> immediatePredecessors() {
		return incoming().stream().map(Edge::from);
	}

}
