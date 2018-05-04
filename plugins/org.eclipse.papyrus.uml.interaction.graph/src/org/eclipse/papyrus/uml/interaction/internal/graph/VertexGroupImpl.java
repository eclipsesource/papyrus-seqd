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

package org.eclipse.papyrus.uml.interaction.internal.graph;

import java.util.stream.Stream;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Edge;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Group;
import org.eclipse.papyrus.uml.interaction.graph.GroupKind;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.uml2.uml.Element;

/**
 * Implementation of a graph vertex that is a semantic {@linkplain Group grouping} of other vertices.
 *
 * @author Christian W. Damus
 */
public class VertexGroupImpl extends VertexImpl implements Group<Vertex> {

	private GroupKind kind = GroupKind.NONE;

	/**
	 * Initializes me.
	 */
	VertexGroupImpl(Graph graph, Element interactionElement, View diagramView) {
		super(graph, interactionElement, diagramView);
	}

	/**
	 * Initializes me.
	 */
	VertexGroupImpl(Graph graph, Element interactionElement) {
		this(graph, interactionElement, null);
	}

	/**
	 * Initializes me.
	 */
	VertexGroupImpl(Graph graph, View diagramView) {
		this(graph, null, diagramView);
	}

	@Override
	public final GroupKind kind() {
		return kind;
	}

	VertexGroupImpl setKind(GroupKind kind) {
		// Write once
		if (this.kind != GroupKind.NONE && this.kind != kind) {
			throw new IllegalStateException("kind already set"); //$NON-NLS-1$
		}
		this.kind = kind;
		return this;
	}

	@Override
	public Stream<Vertex> members() {
		GroupKind groupKind = kind();
		return outgoing().stream().map(EdgeImpl.class::cast) //
				.filter(e -> e.groupKind() == groupKind) //
				.map(Edge::to);
	}

	@Override
	protected String typeString() {
		return super.typeString() + "{G}"; //$NON-NLS-1$
	}
}
