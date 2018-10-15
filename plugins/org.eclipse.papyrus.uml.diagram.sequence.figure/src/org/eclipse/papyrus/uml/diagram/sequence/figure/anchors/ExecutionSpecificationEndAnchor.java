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
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser.AnchorKind;

public class ExecutionSpecificationEndAnchor extends AbstractConnectionAnchor implements IExecutionAnchor {

	private int side = PositionConstants.LEFT;

	public ExecutionSpecificationEndAnchor(IFigure figure) {
		super(figure);
	}

	@Override
	public void setConnectionSide(int side) {
		Assert.isTrue((side & PositionConstants.LEFT_CENTER_RIGHT) != 0);
		if (side != this.side) {
			this.side = side;
			fireAnchorMoved();
		}
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle body = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(body);

		Point location = new Point(0, 0);
		switch (side) {
			case PositionConstants.LEFT:
				location.translate(body.getBottomLeft()); // End + Left
				break;
			case PositionConstants.CENTER:
				location.translate(body.getBottom()); // End + Center
				break;
			case PositionConstants.RIGHT:
				location.translate(body.getBottomRight()); // End + Right
				break;
		}

		return location;
	}

	@Override
	public String getTerminal() {
		return AnchorParser.getInstance().getTerminal(AnchorKind.END);
	}

	@Override
	public String toString() {
		return String.format("ExecAnchor(%s)", getTerminal()); //$NON-NLS-1$
	}
}
