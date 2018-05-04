/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.figure.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Generic fixed position anchor that is allowed to attach to any side of the owner figure
 */
public class BorderAnchor extends AbstractConnectionAnchor {

	private int distance;

	private int side;

	/**
	 * @param figure
	 * @param distance
	 * @param side
	 *            One of {@link PositionConstants#NORTH}, {@link PositionConstants#SOUTH},
	 *            {@link PositionConstants#EAST}, {@link PositionConstants#WEST}
	 * @see PositionConstants
	 */
	public BorderAnchor(IFigure figure, int distance, int side) {
		super(figure);
		this.distance = distance;
		this.side = side;

		switch (side) {
			case PositionConstants.NORTH:
			case PositionConstants.SOUTH:
			case PositionConstants.EAST:
			case PositionConstants.WEST:
				break;
			default:
				throw new IllegalArgumentException("The anchor side must be one of NORTH, SOUTH, EAST, WEST");
		}
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle bounds = getOwner().getBounds().getCopy(); // TODO Consider Insets; otherwise we target
																// slightly outside the visible bounds
		getOwner().translateToAbsolute(bounds);

		Point result = new PrecisionPoint();
		switch (side) {
			case PositionConstants.NORTH:
				result.translate(distance, 0);
				result.translate(bounds.getTopLeft());
				break;
			case PositionConstants.SOUTH:
				result.translate(distance, 0);
				result.translate(bounds.getBottomLeft());
				break;
			case PositionConstants.EAST:
				result.translate(0, distance);
				result.translate(bounds.getTopRight());
				break;
			case PositionConstants.WEST:
				result.translate(0, distance);
				result.translate(bounds.getTopLeft());
				break;
		}

		return result;
	}

}
