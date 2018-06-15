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

/**
 * Encoding of the layout constraints for shapes, compartments, etc.
 *
 * @author Christian W. Damus
 */
public interface LayoutConstraints {

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
	 * Queries the y-coördinate offset of a shape compartment within its parent shape, accounting for name
	 * labels etc. that take up space but which size is not encoded in the notation.
	 * 
	 * @param shapeCompartment
	 *            a shape compartment
	 * @return its Y offset within the parent shape
	 */
	int getYOffset(Compartment shapeCompartment);

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
}
