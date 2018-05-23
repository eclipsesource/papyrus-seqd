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
 * Protocol for a group of vertices in the dependency graph.
 *
 * @author Christian W. Damus
 */
public interface Group<V extends Visitable<V>> extends Visitable<V> {

	/**
	 * Queries the kind of semantic grouping that I represent.
	 * 
	 * @return my kind
	 */
	GroupKind kind();

	/**
	 * Obtains a stream over the members of the group.
	 * 
	 * @return the group members
	 */
	Stream<V> members();

	/**
	 * Query the group kind of a {@code visitable} that may or may not be a {@link Group}.
	 * 
	 * @param visitable
	 *            a visitable graph element
	 * @return its kind, or {@link GroupKind#NONE} if it is not a {@link Group}
	 */
	static GroupKind kind(Visitable<?> visitable) {
		GroupKind result = null;

		if (visitable instanceof Group<?>) {
			result = ((Group<?>)visitable).kind();
		}

		return result == null ? GroupKind.NONE : result;
	}
}
