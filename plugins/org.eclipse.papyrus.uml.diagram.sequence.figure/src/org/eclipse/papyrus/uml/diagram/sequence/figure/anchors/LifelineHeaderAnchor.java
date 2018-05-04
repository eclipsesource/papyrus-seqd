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

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineHeaderFigure;

/**
 * An Anchor on the Lifeline header. The anchor is configured with a Height position relative to the top of
 * the header, and a side (Left or Right).
 */
public class LifelineHeaderAnchor extends AbstractConnectionAnchor {

	private int height;

	private LifelineHeaderFigure lifelineHeaderFigure;

	private int side;

	/**
	 * @param figure
	 * @param height
	 *            The y position of the anchor, relative to the top of the Lifeline Header
	 * @param side
	 *            The side of the anchor; either {@link PositionConstants#LEFT} or
	 *            {@link PositionConstants#RIGHT}
	 * @see PositionConstants
	 */
	public LifelineHeaderAnchor(LifelineHeaderFigure figure, int height, int side) {
		super(figure);
		this.height = height;
		this.lifelineHeaderFigure = figure;
		Assert.isTrue(side == PositionConstants.LEFT || side == PositionConstants.RIGHT);
		this.side = side;
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle header = lifelineHeaderFigure.getBounds().getCopy();
		// real height if smaller than header height, header height otherwise
		int realHeight = Math.min(height, header.height);
		Point location = new Point(0, realHeight);

		if (side == PositionConstants.LEFT) {
			location.translate(header.getTopLeft());
		} else {
			location.translate(header.getTopRight());
		}
		lifelineHeaderFigure.getParent().translateToAbsolute(location);
		return location;
	}

}
