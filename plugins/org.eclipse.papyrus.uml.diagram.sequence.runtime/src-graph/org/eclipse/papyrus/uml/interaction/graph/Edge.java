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

import java.util.stream.Stream;

/**
 * A directed edge in the dependency graph, connecting an a vertex with another that depends on it.
 *
 * @author Christian W. Damus
 */
public interface Edge extends Visitable<Edge> {

	/**
	 * Obtains my source (dependency) vertex.
	 * 
	 * @return my source vertex
	 */
	Vertex from();

	/**
	 * Obtains my target (dependent) vertex.
	 * 
	 * @return my target vertex
	 */
	Vertex to();

	@Override
	default void accept(Visitor<? super Edge> visitor) {
		visitor.visit(this);
	}

	@Override
	default Stream<Edge> immediateSuccessors() {
		return to().outgoing().stream();
	}

	@Override
	default Stream<Edge> immediatePredecessors() {
		return from().incoming().stream();
	}
}
