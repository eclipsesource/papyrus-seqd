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

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Comparator;
import java.util.OptionalInt;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition;

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
	int getTop(Node shape);

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
	int getBottom(Node shape);

	/**
	 * Queries the height of a {@code shape}.
	 * 
	 * @param shape
	 *            a shape in the diagram
	 * @return its height
	 */
	int getHeight(Node shape);

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
	int getLeft(Node shape);

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
	int getRight(Node shape);

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
	Command setTop(Node shape, int yPosition);

	/**
	 * Obtains a deferred command that sets the top (y-coördinate) of the diagram visual element associated
	 * with a vertex in the graph.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @param yPosition
	 *            the future top position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setTop(Node shape, IntSupplier yPosition);

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
	Command setBottom(Node shape, int yPosition);

	/**
	 * Obtains a deferred command that sets the bottom (y-coördinate) of a {@code shape} in the sequence
	 * diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @param yPosition
	 *            the future bottom position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setBottom(Node shape, IntSupplier yPosition);

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
	 * Obtains a deferred command that sets the vertical position (y-coördinate) of an {@code anchor} in the
	 * sequence diagram.
	 * 
	 * @param anchor
	 *            an anchor in the sequence diagram
	 * @param onShape
	 *            the future shape on which the {@code anchor} will have attached its edge
	 * @param yPosition
	 *            the future vertical position to set for the {@code anchor}
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setYPosition(Anchor anchor, Supplier<? extends Shape> onShape, IntSupplier yPosition);

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
	Command setLeft(Node shape, int xPosition);

	/**
	 * Obtains a deferred command that sets the left (x-coördinate) of a {@code shape} in the sequence
	 * diagram.
	 * 
	 * @param shape
	 *            a shape in the sequence diagram
	 * @param xPosition
	 *            the future left position to set
	 * @return the command, which may not be executable but will not be {@code null}
	 */
	Command setLeft(Node shape, IntSupplier xPosition);

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
	int toAbsoluteX(Node shape, View parent, int x);

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
	default int toAbsoluteX(Node shape, int x) {
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
	int toRelativeX(Node shape, View parent, int x);

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
	default int toRelativeX(Node shape, int x) {
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
	int toAbsoluteY(Node shape, View parent, int y);

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
	default int toAbsoluteY(Node shape, int y) {
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
	int toRelativeY(Node shape, View parent, int y);

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
	default int toRelativeY(Node shape, int y) {
		EObject container = shape.eContainer();
		return (container instanceof View) ? toRelativeY(shape, (View)container, y) : y;
	}

	/**
	 * Obtain the layout constraints.
	 * 
	 * @return the pluggable layout constraints
	 */
	LayoutConstraints getConstraints();

	/**
	 * Obtain the font helper for label calculations.
	 * 
	 * @return the pluggable font helper
	 */
	FontHelper getFontHelper();

	/**
	 * Get the top of a view, whether it is a {@code Node} or an {@code Edge}.
	 * 
	 * @param view
	 *            a view
	 * @return its top, which in the case of an edge is the higher of the source or target anchor position
	 */
	default OptionalInt getTop(View view) {
		if (view instanceof Node) {
			return OptionalInt.of(getTop((Node)view));
		} else if (view instanceof Edge) {
			Edge edge = (Edge)view;
			int sourceY = getYPosition(edge.getSourceAnchor(), (Shape)edge.getSource());
			int targetY = getYPosition(edge.getTargetAnchor(), (Shape)edge.getTarget());
			return OptionalInt.of(min(sourceY, targetY));
		} else {
			return OptionalInt.empty();
		}
	}

	/**
	 * Get the bottom of a view, whether it is a {@code Node} or an {@code Edge}.
	 * 
	 * @param view
	 *            a view
	 * @return its bottom, which in the case of an edge is the lower of the source or target anchor position
	 */
	default OptionalInt getBottom(View view) {
		if (view instanceof Node) {
			return OptionalInt.of(getBottom((Node)view));
		} else if (view instanceof Edge) {
			Edge edge = (Edge)view;
			int sourceY = getYPosition(edge.getSourceAnchor(), (Shape)edge.getSource());
			int targetY = getYPosition(edge.getTargetAnchor(), (Shape)edge.getTarget());
			return OptionalInt.of(max(sourceY, targetY));
		} else {
			return OptionalInt.empty();
		}
	}

	/**
	 * Obtain a predicate that tests whether a view is above some absolute Y position.
	 * 
	 * @param yPosition
	 *            an absolute Y position
	 * @return the above predicate
	 */
	default Predicate<View> above(int yPosition) {
		return self -> getTop(self).orElse(Integer.MAX_VALUE) < yPosition;
	}

	/**
	 * Obtain a predicate that tests whether a view is below some absolute Y position.
	 * 
	 * @param yPosition
	 *            an absolute Y position
	 * @return the below predicate
	 */
	default Predicate<View> below(int yPosition) {
		return self -> getBottom(self).orElse(Integer.MIN_VALUE) > yPosition;
	}

	/**
	 * Obtain a vertical ordering of views, where views are ordered in ascending order of their top position
	 * with ties broken by which extends farther at its bottom.
	 * 
	 * @return a vertical ordering for notation views
	 */
	default Comparator<View> verticalOrdering() {
		return Comparator.comparingInt(__top()).thenComparingInt(__bottom());
	}

	// TODO: In Java 9 this should be private
	default ToIntFunction<View> __top() {
		// Sort things that we don't know their top to, well, the top
		return v -> getTop(v).orElse(Integer.MIN_VALUE);
	}

	// TODO: In Java 9 this should be private
	default ToIntFunction<View> __bottom() {
		// Sort things that we don't know their top to, well, the bottom
		return v -> getBottom(v).orElse(Integer.MAX_VALUE);
	}

	/**
	 * Queries the padding required between two views.
	 * 
	 * @param view1
	 *            a view
	 * @param view2
	 *            another view
	 * @return the padding required between them
	 */
	default OptionalInt getPadding(View view1, View view2) {
		LayoutConstraints constraints = getConstraints();

		switch (getRelativePosition(view1, view2)) {
			case ABOVE:
				return OptionalInt.of(constraints.getPadding(RelativePosition.BOTTOM, view1)
						+ constraints.getPadding(RelativePosition.TOP, view2));
			case BELOW:
				return OptionalInt.of(constraints.getPadding(RelativePosition.TOP, view1)
						+ constraints.getPadding(RelativePosition.BOTTOM, view2));
			case LEFT:
				return OptionalInt.of(constraints.getPadding(RelativePosition.RIGHT, view1)
						+ constraints.getPadding(RelativePosition.LEFT, view2));
			case RIGHT:
				return OptionalInt.of(constraints.getPadding(RelativePosition.LEFT, view1)
						+ constraints.getPadding(RelativePosition.RIGHT, view2));
			default:
				return OptionalInt.empty();
		}
	}

	/**
	 * Query the position of {@code view1} relative to {@code view2}.
	 * 
	 * @param view1
	 *            a view
	 * @param view2
	 *            another view
	 * @return the positional relationship of {@code view1} relative to {@code view2} (never {@code null})
	 */
	default RelativeRelationship getRelativePosition(View view1, View view2) {
		RelativeRelationship result = RelativeRelationship.OVERLAP;

		OptionalInt top1 = getTop(view1);
		OptionalInt bottom1 = getBottom(view1);
		OptionalInt top2 = getTop(view2);
		OptionalInt bottom2 = getBottom(view2);

		if (bottom1.isPresent() && top2.isPresent() && (bottom1.getAsInt() <= top2.getAsInt())) {
			result = RelativeRelationship.ABOVE;
		} else if (top1.isPresent() && bottom2.isPresent() && (top1.getAsInt() >= bottom2.getAsInt())) {
			result = RelativeRelationship.BELOW;
		}

		if (!(view1 instanceof Node) || !(view2 instanceof Node)) {
			// Edges only have vertical padding
			return result;
		}

		if (result != RelativeRelationship.OVERLAP) {
			// Priority to vertical layout concerns
			return result;
		}

		Node node1 = (Node)view1;
		Node node2 = (Node)view2;

		// Nodes can also be left or right of one another

		int left1 = getLeft(node1);
		int right1 = getRight(node1);
		int left2 = getLeft(node2);
		int right2 = getRight(node2);

		if (right1 <= left2) {
			result = RelativeRelationship.LEFT;
		} else if (left1 >= right2) {
			result = RelativeRelationship.RIGHT;
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * The relative positioning relationship between two views in the diagram.
	 */
	enum RelativeRelationship {
		ABOVE, BELOW, LEFT, RIGHT, OVERLAP;
	}

}
