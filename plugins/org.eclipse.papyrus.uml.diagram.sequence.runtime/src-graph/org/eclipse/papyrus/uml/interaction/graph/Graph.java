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

import org.eclipse.gmf.runtime.notation.Diagram;
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

	static Graph compute(Interaction interaction, Diagram sequenceDiagram) {
		return new GraphImpl(interaction, sequenceDiagram);
	}
}
