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

/**
 * A semantic tag that can be applied to a {@link Vertex} or an {@code Edge} in the {@link Graph}.
 *
 * @author Christian W. Damus
 */
public enum Tag {
	/**
	 * Tags a {@link Vertex} that is the creation of a lifeline or an {@code Edge} that links a message end
	 * vertex to the lifeline that it creates.
	 */
	LIFELINE_CREATION,

	/**
	 * Tags a {@link Vertex} that is the destruction of a lifeline.
	 */
	LIFELINE_DESTRUCTION,
	/**
	 * Tags a {@link Vertex} that is an execution start occurrence or an {@code Edge} that links an execution
	 * occurrence vertex to the execution specification vertex that it starts.
	 */
	EXECUTION_START,
	/**
	 * Tags a {@link Vertex} that is an execution finish occurrence or an {@code Edge} that links an execution
	 * specification vertex to its finish vertex.
	 */
	EXECUTION_FINISH;

	static {
		if (values().length > 16) {
			throw new Error("Too many values for size of TaggableImpl tags field"); //$NON-NLS-1$
		}
	}
}
