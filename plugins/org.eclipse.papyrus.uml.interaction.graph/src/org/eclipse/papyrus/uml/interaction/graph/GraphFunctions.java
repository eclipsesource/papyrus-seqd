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
import static org.eclipse.papyrus.uml.interaction.graph.GraphPredicates.covers;
import static org.eclipse.papyrus.uml.interaction.graph.GraphPredicates.isA;

import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Functions operating on graphs.
 *
 * @author Christian W. Damus
 */
public class GraphFunctions {

	/**
	 * Not instantiable by clients.
	 */
	private GraphFunctions() {
		super();
	}

	/**
	 * Obtain a function that computes the lifelines covered by a vertex, either directly or indirectly via an
	 * execution specification.
	 * 
	 * @return the covered lifelines function
	 */
	public static Function<Vertex, Stream<Vertex>> lifelines() {
		return fragment -> Stream.concat(
				// Directly covered lifelines
				fragment.immediatePredecessors().filter(isA(UMLPackage.Literals.LIFELINE)),
				// Lifelines indirectly covered via execution specifications
				fragment.immediatePredecessors().filter(isA(UMLPackage.Literals.EXECUTION_SPECIFICATION))
						.flatMap(lifelines()));
	}

	/**
	 * Obtain a function that computes the predecessor of some vertex on a given {@code lifeline}.
	 * 
	 * @param lifeline
	 *            the contextual lifeline
	 * @return the predecessor function
	 */
	public static Function<Vertex, Vertex> predecessorOn(Lifeline lifeline) {
		return fragment -> fragment.graph().vertices().filter(covers(lifeline)).filter(before(fragment))
				.max(fragment.graph().interactionOrdering()).orElse(null);
	}

	/**
	 * Obtain a function that computes the successor of some vertex on a given {@code lifeline}.
	 * 
	 * @param lifeline
	 *            the contextual lifeline
	 * @return the successor function
	 */
	public static Function<Vertex, Vertex> successorOn(Lifeline lifeline) {
		return fragment -> fragment.graph().vertices().filter(covers(lifeline)).filter(after(fragment))
				.findFirst().orElse(null);
	}
}
