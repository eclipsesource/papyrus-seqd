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
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.figure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.BorderAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.layout.AutoSizeLayout;

/**
 * Figure displayed for the interaction, as the main frame.
 */
public class InteractionFigure extends NodeFigure {

	/**
	 * Constructor.
	 */
	public InteractionFigure() {
		setLayoutManager(new AutoSizeLayout());
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		RoundedRectangle r = new RoundedRectangle();
		r.setBounds(getBounds());
		r.paintFigure(graphics);
	}

	@Override
	protected boolean useLocalCoordinates() {
		return true;
	}

	@Override
	public ConnectionAnchor getConnectionAnchor(String terminal) {
		final String distanceStr;
		final int side;
		if (terminal.startsWith("north;")) {
			distanceStr = terminal.substring(6);
			side = PositionConstants.NORTH;
		} else if (terminal.startsWith("south;")) {
			distanceStr = terminal.substring(6);
			side = PositionConstants.SOUTH;
		} else if (terminal.startsWith("east;")) {
			distanceStr = terminal.substring(5);
			side = PositionConstants.EAST;
		} else if (terminal.startsWith("west;")) {
			distanceStr = terminal.substring(5);
			side = PositionConstants.WEST;
		} else {
			return super.getConnectionAnchor(terminal);
		}

		try {
			int distance = Integer.parseInt(distanceStr);
			return new BorderAnchor(this, distance, side);
		} catch (NumberFormatException ex) {
			return super.getConnectionAnchor(terminal);
		}
	}

}
