/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;

public class DestructionSpecificationFigure extends NodeFigure {

	@Override
	protected boolean useLocalCoordinates() {
		return true;
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		graphics.pushState();
		graphics.setLineWidth(2);
		graphics.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
		graphics.drawLine(bounds.x, bounds.y + bounds.height, bounds.x + bounds.width, bounds.y);
		graphics.popState();
	}

}
