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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser.AnchorKind;

public class ExecutionSpecificationEndAnchor extends AbstractConnectionAnchor implements ISequenceAnchor {

	public ExecutionSpecificationEndAnchor(IFigure figure) {
		super(figure);
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle body = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(body);

		Point location = new Point(0, 0);
		location.translate(body.getBottom()); // End + Center

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
