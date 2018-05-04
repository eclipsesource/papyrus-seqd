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
}
