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
package org.eclipse.papyrus.uml.diagram.sequence.figure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.BorderAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ExecutionSpecificationEndAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ExecutionSpecificationSideAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ExecutionSpecificationStartAnchor;

public class ExecutionSpecificationFigure extends NodeFigure {
	@Override
	protected boolean useLocalCoordinates() {
		return true;
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
		RectangleFigure rectangle = new RectangleFigure();
		rectangle.setBounds(r);
		rectangle.paintFigure(graphics);
	}

	@Override
	public ConnectionAnchor getConnectionAnchor(String terminal) {

		if (terminal == null || terminal.isEmpty()) {
			return super.getConnectionAnchor(terminal);
		}

		/* Start/End Anchors */

		switch (terminal) {
			case "start":
				return new ExecutionSpecificationStartAnchor(this);
			case "end":
				return new ExecutionSpecificationEndAnchor(this);
		}

		/* Side Anchors (For Messages) */

		try {
			if (terminal.startsWith("left;")) {
				int side = PositionConstants.LEFT;
				int height = Integer.parseInt(terminal.substring(5));
				return new ExecutionSpecificationSideAnchor(this, side, height);
			} else if (terminal.startsWith("right;")) {
				int side = PositionConstants.RIGHT;
				int height = Integer.parseInt(terminal.substring(6));
				return new ExecutionSpecificationSideAnchor(this, side, height);
			}

			/* Border Anchors (For Comments and Constraints) */

			else if (terminal.startsWith("north;")) {
				int side = PositionConstants.NORTH;
				int height = Integer.parseInt(terminal.substring(6));
				return new BorderAnchor(this, side, height);
			} else if (terminal.startsWith("south;")) {
				int side = PositionConstants.NORTH;
				int height = Integer.parseInt(terminal.substring(6));
				return new BorderAnchor(this, side, height);
			} else if (terminal.startsWith("east;")) {
				int side = PositionConstants.EAST;
				int height = Integer.parseInt(terminal.substring(5));
				return new BorderAnchor(this, side, height);
			} else if (terminal.startsWith("west;")) {
				int side = PositionConstants.WEST;
				int height = Integer.parseInt(terminal.substring(5));
				return new BorderAnchor(this, side, height);
			}
		} catch (NumberFormatException ex) {
			// Ignore
		}

		return super.getConnectionAnchor(terminal);
	}
}
