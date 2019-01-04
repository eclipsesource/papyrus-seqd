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

import static org.eclipse.draw2d.PositionConstants.LEFT;
import static org.eclipse.draw2d.PositionConstants.RIGHT;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser.AnchorKind;

public class ExecutionSpecificationSideAnchor extends AbstractConnectionAnchor implements ISideAnchor {

	private int side;

	private int height;

	public ExecutionSpecificationSideAnchor(IFigure figure, int height) {
		super(figure);
		this.side = RIGHT; // by default, anchors are located on the right.
		this.height = height;
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle header = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(header);

		Point location = new Point(0, height);
		if (side == LEFT) {
			location.translate(header.getTopLeft());
		} else {
			location.translate(header.getTopRight());
		}

		return location;
	}

	@Override
	public String getTerminal() {
		// no serialization of side, this is derived at runtime
		return AnchorParser.getInstance().getTerminal(AnchorKind.DISTANCE, height);
	}

	@Override
	public String toString() {
		return String.format("ExecAnchor(%s)", getTerminal()); //$NON-NLS-1$
	}

	@Override
	public void setConnectionSide(int side) {
		Assert.isTrue((side & PositionConstants.LEFT_CENTER_RIGHT) != 0);
		if (side != this.side) {
			this.side = side;
			fireAnchorMoved();
		}
	}

}
