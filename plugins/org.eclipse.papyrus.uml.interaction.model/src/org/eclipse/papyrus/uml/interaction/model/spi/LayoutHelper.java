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

package org.eclipse.papyrus.uml.interaction.model.spi;

import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;

/**
 * Protocol for a pluggable utility that provides access to and modification of the layout of a sequence
 * diagram. All positions returned by and accepted by operations of this interface are absolute locations on
 * the diagram surface, not relative to the coördinate system of a parent view.
 *
 * @author Christian W. Damus
 */
public interface LayoutHelper {

	/**
	 * Queries the top (y-coördinate) of the diagram visual element associated with a vertex in the graph.
	 * 
	 * @param v
	 *            a vertex in the semantic dependency graph
	 * @return its top position, if it has any
	 */
	OptionalInt getTop(Vertex v);

	/**
	 * Queries the top (y-coördinate) of a {@code shape} in the sequence diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @return its top position
	 */
	int getTop(Shape shape);

	/**
	 * Queries the bottom (y-coördinate) of the diagram visual element associated with a vertex in the graph.
	 * 
	 * @param v
	 *            a vertex in the semantic dependency graph
	 * @return its bottom position, if it has any
	 */
	OptionalInt getBottom(Vertex v);

	/**
	 * Queries the bottom (y-coördinate) of a {@code shape} in the sequence diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @return its bottom position
	 */
	int getBottom(Shape shape);

	/**
	 * Queries the vertical position (y-coördinate) of an {@code anchor} in the sequence diagram.
	 * 
	 * @param anchor
	 *            an anchor in the sequence diagram
	 * @param onShape
	 *            the shape on which the {@code anchor} attaches its edge
	 * @return its vertical position
	 */
	int getYPosition(Anchor anchor, Shape onShape);

	/**
	 * Obtains a command that sets the top (y-coördinate) of the diagram visual element associated with a
	 * vertex in the graph.
	 * 
	 * @param v
	 *            a vertex in the semantic dependency graph
	 * @param yPosition
	 *            the new top position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setTop(Vertex v, int yPosition);

	/**
	 * Obtains a command that sets the top (y-coördinate) of a {@code shape} in the sequence diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @param yPosition
	 *            the new top position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setTop(Shape shape, int yPosition);

	/**
	 * Obtains a command that sets the bottom (y-coördinate) of the diagram visual element associated with a
	 * vertex in the graph.
	 * 
	 * @param v
	 *            a vertex in the semantic dependency graph
	 * @param yPosition
	 *            the new bottom position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setBottom(Vertex v, int yPosition);

	/**
	 * Obtains a command that sets the bottom (y-coördinate) of a {@code shape} in the sequence diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @param yPosition
	 *            the new bottom position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setBottom(Shape shape, int yPosition);

	/**
	 * Obtains a command that sets the vertical position (y-coördinate) of an {@code anchor} in the sequence
	 * diagram.
	 * 
	 * @param anchor
	 *            an anchor in the sequence diagram
	 * @param onShape
	 *            the shape on which the {@code anchor} attaches its edge
	 * @param yPosition
	 *            the new vertical position to set for the {@code anchor}
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setYPosition(Anchor anchor, Shape onShape, int yPosition);

	/**
	 * Returns the bounds for a new representation, given the proposed bounds.
	 * 
	 * @Param eClass the metaclass of the semantic object to be presented
	 * @param proposedBounds
	 *            the bounds initially proposed for creation of the element
	 * @param container
	 *            container of the representation to create or move.
	 * @return the bounds for a new representation
	 */
	Bounds getNewBounds(EClass eClass, Bounds proposedBounds, Node container);

}
