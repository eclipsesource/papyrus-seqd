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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.papyrus.uml.interaction.graph.Edge;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Group;
import org.eclipse.papyrus.uml.interaction.graph.GroupKind;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;

/**
 * Default implementation of an edge in the dependency graph.
 *
 * @author Christian W. Damus
 */
public class EdgeImpl extends TaggableImpl<EdgeImpl> implements Edge {
	private final VertexImpl from;

	private final VertexImpl to;

	private GroupKind groupKind = GroupKind.NONE;

	EdgeImpl(VertexImpl from, VertexImpl to) {
		super();

		checkForCycle(Objects.requireNonNull(from), Objects.requireNonNull(to));

		this.from = from;
		this.to = to;

		from.addOutgoing(this);
		to.addIncoming(this);
	}

	@Override
	public Vertex from() {
		return from;
	}

	@Override
	public Vertex to() {
		return to;
	}

	@Override
	public Graph graph() {
		return from.graph();
	}

	/**
	 * Query the kind of group for which I represent my {@link #to() to} vertex's membership in my
	 * {@link #from() from} group.
	 * 
	 * @return the group kind, or {@link GroupKind#NONE NONE} if I am not a grouping edge
	 */
	public GroupKind groupKind() {
		return groupKind;
	}

	EdgeImpl setGroupKind(GroupKind kind) {
		// Write once
		if (this.groupKind != GroupKind.NONE && this.groupKind != kind) {
			throw new IllegalStateException("kind already set"); //$NON-NLS-1$
		}
		this.groupKind = kind;
		return this;
	}

	/**
	 * We don't group edges.
	 * 
	 * @return {@link Stream#empty()}
	 */
	@Override
	public Stream<Group<Edge>> groups() {
		return Stream.empty();
	}

	void destroy() {
		from.removeOutgoing(this);
		to.removeIncoming(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + from.hashCode();
		result = (prime * result) + to.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Edge)) {
			return false;
		}
		Edge other = (Edge)obj;
		if (!from.equals(other.from())) {
			return false;
		}
		if (!to.equals(other.to())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", from(), to()); //$NON-NLS-1$
	}

	private static void checkForCycle(Vertex from, Vertex to) {
		if (from.equals(to)) {
			throw new IllegalArgumentException("trivial dependency cycle on " + from); //$NON-NLS-1$
		}
		if (to.precedes(from)) {
			// Find the path. Dumb algorithm, but it's an error case
			List<Vertex> path = new ArrayList<>();
			for (Vertex next = to; next != from;) {
				path.add(next);

				if (next.immediateSuccessors().anyMatch(from::equals)) {
					// Last step
					next = from;
				} else {
					// We already know that this successor exists
					next = next.immediateSuccessors().filter(succ -> succ.precedes(from)).findFirst().get();
				}
			}

			// Finish the path
			path.add(from);

			throw new IllegalArgumentException("dependency cycle: " + path); //$NON-NLS-1$
		}
	}
}
