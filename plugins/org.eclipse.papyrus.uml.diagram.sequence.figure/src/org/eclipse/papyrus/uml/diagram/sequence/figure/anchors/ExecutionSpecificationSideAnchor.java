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
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class ExecutionSpecificationSideAnchor extends AbstractConnectionAnchor {

	private int side;

	private int height;

	public ExecutionSpecificationSideAnchor(IFigure figure, int side, int height) {
		super(figure);
		Assert.isTrue(side == PositionConstants.LEFT || side == PositionConstants.RIGHT);

		this.side = side;
		this.height = height;
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle header = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(header);

		Point location = new Point(0, height);
		if (side == PositionConstants.LEFT) {
			location.translate(header.getTopLeft());
		} else {
			location.translate(header.getTopRight());
		}

		return location;
	}

}
