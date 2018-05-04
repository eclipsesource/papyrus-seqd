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

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;

/**
 * Predicates operating on graphs.
 *
 * @author Christian W. Damus
 */
public class GraphPredicates {

	/**
	 * Not instantiable by clients.
	 */
	private GraphPredicates() {
		super();
	}

	/**
	 * Obtain a predicate that matches objects that are vertices satisfying some predicate.
	 * 
	 * @param vertexPredicate
	 *            the vertex predicate
	 * @return the generic is-a-vertex-that predicate
	 */
	public static <T> Predicate<T> vertexThat(Predicate<? super Vertex> vertexPredicate) {
		return obj -> obj instanceof Vertex && vertexPredicate.test((Vertex)obj);
	}

	/**
	 * Obtain a predicate that matches vertices whose elements are an instance of the given Ecore class.
	 * 
	 * @param semanticElementClass
	 *            the interaction element Ecore class
	 * @return the vertex is-A predicate
	 */
	public static Predicate<Vertex> isA(EClass semanticElementClass) {
		return vertex -> semanticElementClass.isInstance(vertex.getInteractionElement());
	}

	/**
	 * Obtain a predicate that matches vertices whose elements are an instance of any of the given Ecore
	 * classes.
	 * 
	 * @param semanticElementClass
	 *            a possible interaction element Ecore class
	 * @param orSemanticElementClass
	 *            another possible interaction element Ecore class
	 * @param orMore
	 *            additional possible interaction element Ecore class
	 * @return the vertex is-A predicate
	 */
	public static Predicate<Vertex> isA(EClass semanticElementClass, EClass orSemanticElementClass,
			EClass... orMore) {

		int count = 2 + orMore.length;
		EClass[] eclasses = new EClass[count];
		eclasses[0] = semanticElementClass;
		eclasses[1] = orSemanticElementClass;
		if (orMore.length > 0) {
			System.arraycopy(orMore, 0, eclasses, 2, orMore.length);
		}

		return vertex -> {
			for (int i = 0; i < count; i++) {
				if (eclasses[i].isInstance(vertex.getInteractionElement())) {
					return true;
				}
			}
			return false;
		};
	}

	/**
	 * Obtain a predicate that matches vertices representing a given {@code element}.
	 * 
	 * @param element
	 *            the interaction element to match
	 * @return the vertex is predicate
	 */
	public static Predicate<Vertex> is(Element element) {
		return fragment -> fragment.getInteractionElement() == element;
	}

	/**
	 * Obtain a predicate that matches vertices whose elements cover a given {@code lifeline}.
	 * 
	 * @param lifeline
	 *            the contextual lifeline
	 * @return the vertex covers predicate
	 */
	public static Predicate<Vertex> covers(Lifeline lifeline) {
		return fragment -> fragment.group(GroupKind.LIFELINE).filter(vertexThat(is(lifeline))).isPresent()
				// This one's recursive
				|| fragment.group(GroupKind.EXECUTION).filter(vertexThat(covers(lifeline))).isPresent();
	}

	/**
	 * A predicate matching graph elements having a given {@code tag}.
	 * 
	 * @param tag
	 *            a tag
	 * @return the predicate
	 * @throws NullPointerException
	 *             if the {@code tag} is {@code null}
	 */
	public static Predicate<Taggable> hasTag(Tag tag) {
		Objects.requireNonNull(tag);
		return taggable -> taggable.hasTag(tag);
	}

	/**
	 * Obtain a predicate that determines whether some vertex <em>occurs before</em> a {@code reference}
	 * vertex in the graph.
	 * 
	 * @param reference
	 *            the reference vertex
	 * @return the occurs-before predicate
	 */
	public static Predicate<Vertex> before(Vertex reference) {
		Comparator<Vertex> ordering = reference.graph().interactionOrdering();
		return vertex -> reference.succeeds(vertex) || ordering.compare(reference, vertex) > 0;
	}

	/**
	 * Obtain a predicate that determines whether some vertex <em>occurs after</em> a {@code reference} vertex
	 * in the graph.
	 * 
	 * @param reference
	 *            the reference vertex
	 * @return the occurs-after predicate
	 */
	public static Predicate<Vertex> after(Vertex reference) {
		Comparator<Vertex> ordering = reference.graph().interactionOrdering();
		return vertex -> reference.precedes(vertex) || ordering.compare(reference, vertex) < 0;
	}

}
