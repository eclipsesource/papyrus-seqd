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
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.papyrus.uml.diagram.sequence.figure.DestructionSpecificationFigure;

public class DestructionSpecificationAnchor extends AbstractConnectionAnchor {

	/** Specifies the horizontal distance to the destruction shape's center. */
	private static final int OFFSET = 5;

	public DestructionSpecificationAnchor(DestructionSpecificationFigure owner) {
		super(owner);
	}

	@Override
	public Point getLocation(Point reference) {
		final Point center = getCenter();
		int direction = center.getPosition(reference);
		switch (direction) {
			case PositionConstants.EAST:
				return center.getTranslated(OFFSET, 0);
			case PositionConstants.WEST:
				return center.getTranslated(-OFFSET, 0);
			default:
				return center;
		}
	}

	private Point getCenter() {
		final Point p = getOwner().getBounds().getCenter();
		getOwner().translateToAbsolute(p);
		return p;
	}

}
