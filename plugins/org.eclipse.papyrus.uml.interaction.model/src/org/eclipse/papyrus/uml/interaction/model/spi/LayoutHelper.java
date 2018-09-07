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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
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
	 * Queries the height of a {@code shape}.
	 * 
	 * @param shape
	 *            a shape in the diagram
	 * @return its height
	 */
	int getHeight(Shape shape);

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
	 * Queries the left (x-coördinate) of the diagram visual element associated with a vertex in the graph.
	 * 
	 * @param v
	 *            a vertex in the semantic dependency graph
	 * @return its left position, if it has any
	 */
	OptionalInt getLeft(Vertex v);

	/**
	 * Queries the left (x-coördinate) of a {@code shape} in the sequence diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @return its left position
	 */
	int getLeft(Shape shape);

	/**
	 * Queries the right (x-coördinate) of the diagram visual element associated with a vertex in the graph.
	 * 
	 * @param v
	 *            a vertex in the semantic dependency graph
	 * @return its right position, if it has any
	 */
	OptionalInt getRight(Vertex v);

	/**
	 * Queries the right (x-coördinate) of a {@code shape} in the sequence diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @return its right position
	 */
	int getRight(Shape shape);

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
	 * Obtains a command that sets the left (x-coördinate) of the diagram visual element associated with a
	 * vertex in the graph.
	 * 
	 * @param v
	 *            a vertex in the semantic dependency graph
	 * @param xPosition
	 *            the new left position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setLeft(Vertex v, int xPosition);

	/**
	 * Obtains a command that sets the left (x-coördinate) of a {@code shape} in the sequence diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @param xPosition
	 *            the new left position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setLeft(Shape shape, int xPosition);

	/**
	 * Returns the bounds for a new representation, given the proposed bounds.
	 * 
	 * @param eClass
	 *            the metaclass of the semantic object to be presented
	 * @param proposedBounds
	 *            the bounds initially proposed for creation of the element
	 * @param container
	 *            container of the representation to create or move.
	 * @return the bounds for a new representation
	 */
	Bounds getNewBounds(EClass eClass, Bounds proposedBounds, Node container);

	/**
	 * Computes the bounds for an existing representation, given the proposed bounds.
	 * 
	 * @param semanticObject
	 *            the semantic object to be presented
	 * @param view
	 *            the visual representation of the semantic object
	 * @param proposedBounds
	 *            the bounds initially proposed for the representation (being moved/resized/etc.)
	 * @return the adjusted bounds for the representation
	 */
	Bounds getAdjustedBounds(EObject semanticObject, Node view, Bounds proposedBounds);

	/**
	 * Obtain an {@code x} coördinate in absolute space from one that is relative to the {@code parent} of a
	 * {@code shape}.
	 * 
	 * @param shape
	 *            a shape
	 * @param parent
	 *            its parent view (shape or compartment, usually)
	 * @param x
	 *            an X position in relative coördinate space of the {@code parent}
	 * @return the corresponding position in absolute coördinate space
	 */
	int toAbsoluteX(Shape shape, View parent, int x);

	/**
	 * Obtain an {@code x} coördinate in absolute space from one that is relative to the parent of a
	 * {@code shape}, if it has one.
	 * 
	 * @param shape
	 *            a shape
	 * @param x
	 *            an X position in relative coördinate space of the {@code shape}'s parent
	 * @return the corresponding position in absolute coördinate space, or the relative {@code x} if none
	 */
	default int toAbsoluteX(Shape shape, int x) {
		EObject container = shape.eContainer();
		return (container instanceof View) ? toAbsoluteX(shape, (View)container, x) : x;
	}

	/**
	 * Obtain an {@code x} coördinate in the space of the parent of a {@code shape}, from one that is in
	 * absolute space.
	 * 
	 * @param shape
	 *            a shape
	 * @param parent
	 *            its parent view (shape or compartment, usually)
	 * @param x
	 *            an X position in absolute coördinate space
	 * @return the corresponding position in the {@code parent}'s space
	 */
	int toRelativeX(Shape shape, View parent, int x);

	/**
	 * Obtain an {@code x} coördinate in the space of the parent of a {@code shape}, if it has one, from one
	 * that is in absolute space.
	 * 
	 * @param shape
	 *            a shape
	 * @param x
	 *            an X position in absolute coördinate space
	 * @return the corresponding position in the {@code shape}'s parent's space, or the absolute {@code x} if
	 *         none
	 * @see #toRelativeX(Shape, View, int)
	 */
	default int toRelativeX(Shape shape, int x) {
		EObject container = shape.eContainer();
		return (container instanceof View) ? toRelativeX(shape, (View)container, x) : x;
	}

	/**
	 * Obtain a {@code y} coördinate in absolute space from one that is relative to the {@code parent} of a
	 * {@code shape}.
	 * 
	 * @param shape
	 *            a shape
	 * @param parent
	 *            its parent view (shape or compartment, usually)
	 * @param y
	 *            a Y position in relative coördinate space of the {@code parent}
	 * @return the corresponding position in absolute coördinate space
	 */
	int toAbsoluteY(Shape shape, View parent, int y);

	/**
	 * Obtain a {@code y} coördinate in absolute space from one that is relative to the parent of a
	 * {@code shape}, if it has one.
	 * 
	 * @param shape
	 *            a shape
	 * @param x
	 *            a Y position in relative coördinate space of the {@code shape}'s parent
	 * @return the corresponding position in absolute coördinate space, or the relative {@code x} if none
	 */
	default int toAbsoluteY(Shape shape, int y) {
		EObject container = shape.eContainer();
		return (container instanceof View) ? toAbsoluteY(shape, (View)container, y) : y;
	}

	/**
	 * Obtain a {@code y} coördinate in the space of the {@code parent} of a {@code shape}, from one that is
	 * in absolute space.
	 * 
	 * @param shape
	 *            a shape
	 * @param parent
	 *            its parent view (shape or compartment, usually)
	 * @param y
	 *            a Y position in absolute coördinate space
	 * @return the corresponding position in the {@code parent}'s space
	 */
	int toRelativeY(Shape shape, View parent, int y);

	/**
	 * Obtain a {@code y} coördinate in the space of the parent of a {@code shape}, if it has one, from one
	 * that is in absolute space.
	 * 
	 * @param shape
	 *            a shape
	 * @param y
	 *            a Y position in absolute coördinate space
	 * @return the corresponding position in the {@code shape}'s parent's space, or the absolute {@code y} if
	 *         none
	 * @see #toRelativeY(Shape, View, int)
	 */
	default int toRelativeY(Shape shape, int y) {
		EObject container = shape.eContainer();
		return (container instanceof View) ? toRelativeY(shape, (View)container, y) : y;
	}

	/**
	 * Obtain the layout constraints.
	 * 
	 * @return the pluggable layout constraints
	 */
	LayoutConstraints getConstraints();

}
