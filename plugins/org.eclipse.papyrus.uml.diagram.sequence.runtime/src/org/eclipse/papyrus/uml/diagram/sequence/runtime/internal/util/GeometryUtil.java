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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.NotationFactory;

/**
 * Utilities for working with GEF/GMF geometry.
 *
 * @author Christian W. Damus
 */
public class GeometryUtil {

	/**
	 * Not instantiable by clients.
	 */
	private GeometryUtil() {
		super();
	}

	/**
	 * Convert a GEF {@code rectangle} to a GMF bounds.
	 * 
	 * @param rectangle
	 *            a GEF rectangle
	 * @return an equivalent GMF bounds
	 */
	public static Bounds asBounds(Rectangle rectangle) {
		Bounds result = NotationFactory.eINSTANCE.createBounds();
		result.setX(rectangle.x());
		result.setY(rectangle.y());
		result.setWidth(rectangle.width());
		result.setHeight(rectangle.height());
		return result;
	}

	/**
	 * Convert a GMF {@code bounds} to a GEF rectangle.
	 * 
	 * @param bounds
	 *            a GMF bounds
	 * @return an equivalent GEF rectangle
	 */
	public static Rectangle asRectangle(Bounds bounds) {
		return new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
	}

	/**
	 * Compute the move delta between two rectangles.
	 * 
	 * @param from
	 *            the source rectangle
	 * @param to
	 *            the destination rectangle
	 * @return the difference between their {@linkplain Rectangle#getLocation() locations}
	 */
	public static Point getMoveDelta(Rectangle from, Rectangle to) {
		return new Point(to.x() - from.x(), to.y() - from.y());
	}

	/**
	 * Compute the size delta between two rectangles.
	 * 
	 * @param from
	 *            the source rectangle
	 * @param to
	 *            the destination rectangle
	 * @return the difference between their {@linkplain Rectangle#getSize() sizes}
	 */
	public static Dimension getSizeDelta(Rectangle from, Rectangle to) {
		return new Dimension(to.width() - from.width(), to.height() - from.height());
	}
}
