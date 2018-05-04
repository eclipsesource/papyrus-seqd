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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;

public class StateInvariantFigure extends NodeFigure {
	@Override
	protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
		RoundedRectangle rectangle = new RoundedRectangle();
		rectangle.setBounds(r);
		// If the figure is correct, height < width
		// However, if this isn't correct, this will lead to weird display; so let's double check
		int arcSize = Math.min(r.height, r.width);
		rectangle.setCornerDimensions(new Dimension(arcSize, arcSize));
		rectangle.paintFigure(graphics);
	}
}
