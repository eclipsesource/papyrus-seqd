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

import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Encoding of the layout constraints for shapes, compartments, etc.
 *
 * @author Christian W. Damus
 */
public interface LayoutConstraints {

	public enum RelativePosition {
		TOP, BOTTOM, LEFT, RIGHT;
	}

	public interface Modifiers {

		public static String NO_MODIFIER = ""; //$NON-NLS-1$

		public static final String ARROW = "arrow"; //$NON-NLS-1$

		public static final String ANCHOR = "anchor"; //$NON-NLS-1$

		public static final String LINE = "line"; //$NON-NLS-1$
	}

	/**
	 * Queries the x-coördinate offset of a shape compartment within its parent shape, accounting for name
	 * labels etc. that take up space but which size is not encoded in the notation.
	 * 
	 * @param shapeCompartment
	 *            a shape compartment
	 * @return its X offset within the parent shape
	 */
	int getXOffset(Compartment shapeCompartment);

	/**
	 * Queries the x-coördinate offset of a view type
	 * 
	 * @param viewType
	 *            a view type.
	 * @return its X offset
	 */
	int getXOffset(String viewType);

	/**
	 * Queries the y-coördinate offset of a shape compartment within its parent shape, accounting for name
	 * labels etc. that take up space but which size is not encoded in the notation.
	 * 
	 * @param shapeCompartment
	 *            a shape compartment
	 * @return its Y offset within the parent shape
	 */
	int getYOffset(Compartment shapeCompartment);

	/**
	 * Queries the y-coördinate offset of a view type
	 * 
	 * @param viewType
	 *            a view type.
	 * @return its Y offset
	 */
	int getYOffset(String viewType);

	/**
	 * Queries the minimum height of a view.
	 * 
	 * @param view
	 *            a view.
	 * @return its minimum height.
	 */
	int getMinimumHeight(View view);

	/**
	 * Queries the minimum height of a view type.
	 * 
	 * @param viewType
	 *            a view type.
	 * @return its minimum height.
	 */
	int getMinimumHeight(String viewType);

	/**
	 * Queries the minimum height of a view with a modifier.
	 * 
	 * @param viewType
	 *            a view.
	 * @param modifier
	 *            a modifier allows to define sub-parts of a view.
	 * @return its minimum height.
	 */
	int getMinimumHeight(View view, String modifier);

	/**
	 * Queries the minimum height of a view type with a modifier.
	 * 
	 * @param viewType
	 *            a view type.
	 * @param modifier
	 *            a modifier allows to define sub-parts of a view.
	 * @return its minimum height.
	 */
	int getMinimumHeight(String viewType, String modifier);

	/**
	 * Queries the minimum width of a view.
	 * 
	 * @param view
	 *            a view.
	 * @return its minimum width.
	 */
	int getMinimumWidth(View view);

	/**
	 * Queries the minimum width of a view type.
	 * 
	 * @param viewType
	 *            a view type.
	 * @return its minimum width.
	 */
	int getMinimumWidth(String viewType);

	/**
	 * Queries the minimum width of a view with a {@link Modifiers modifier}.
	 * 
	 * @param view
	 *            a view.
	 * @param modifier
	 *            a {@link Modifiers modifier} allows to define sub-parts of a view.
	 * @return its minimum width.
	 */
	int getMinimumWidth(View view, String modifier);

	/**
	 * Queries the minimum width of a view with a {@link Modifiers modifier}..
	 * 
	 * @param viewType
	 *            a view type.
	 * @param modifier
	 *            a {@link Modifiers modifier} allows to define sub-parts of a view.
	 * @return its minimum width.
	 */
	int getMinimumWidth(String viewType, String modifier);

	/**
	 * Queries the padding of a view.
	 * 
	 * @param orientation
	 *            specifies whether the bottom, top, left, or right padding shall be returned.
	 * @param view
	 *            a view.
	 * @return its padding.
	 */
	int getPadding(RelativePosition orientation, View view);

	/**
	 * Queries the padding of a view.
	 * 
	 * @param orientation
	 *            specifies whether the bottom, top, left, or right padding shall be returned.
	 * @param viewType
	 *            a view type.
	 * @return its padding.
	 */
	int getPadding(RelativePosition orientation, String viewType);

	/**
	 * Obtain the minimal slope, as a percentage, of an asynchronous message slope that should be deemed
	 * intentionally asynchronous and so be drawn with that slope. This lets users draw horizontal message
	 * connections even with slightly inaccurate pointer manipulation.
	 * 
	 * @return the percentage slope threshold
	 */
	double getAsyncMessageSlopeThreshold();

	/**
	 * Queries whether the slope between two points is more than the
	 * {@linkplain #getAsyncMessageSlopeThreshold() threshold} that determines intentional asynchronous
	 * message slope. Note that there may be additional tolerances than just the slope threshold, especially
	 * for very short distances between the points.
	 * 
	 * @param x1,&nbsp;y1
	 *            one point
	 * @param y1,&nbsp;y2
	 *            the other point
	 * @return {@code true} if the points are deemed to be intentionally sloped; {@code false}, toerhwise
	 */
	boolean isAsyncMessageSlope(double x1, double y1, double x2, double y2);

	/**
	 * Query the strength of a magnet attached to the given {@code view}
	 * 
	 * @param view
	 *            a notation view in the diagram that supports magnets
	 * @return the magnet strength
	 */
	int getMagnetStrength(View view);

	/**
	 * Applies a {@link Modifiers modifier} to the given <code>viewType</code> and returns the resulting key.
	 * 
	 * @param modifier
	 *            the modifier.
	 * @param viewType
	 *            the view type.
	 * @return The key representing the view type with the given modifier applied.
	 */
	public static String applyModifier(String modifier, String viewType) {
		if (modifier == null || Modifiers.NO_MODIFIER == modifier) {
			return viewType;
		}
		return viewType + "_" + modifier; //$NON-NLS-1$
	}
}
