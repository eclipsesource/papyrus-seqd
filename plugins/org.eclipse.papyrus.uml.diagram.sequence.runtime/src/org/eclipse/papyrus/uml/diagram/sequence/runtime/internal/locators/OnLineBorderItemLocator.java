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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;

public class OnLineBorderItemLocator implements IBorderItemLocator {

	private Rectangle constraint;

	private IFigure headerBodyFigure;

	public OnLineBorderItemLocator(IFigure headerBodyFigure) {
		this.headerBodyFigure = headerBodyFigure;
	}

	@Override
	public void relocate(IFigure figureToRelocate) {
		Rectangle body = headerBodyFigure.getBounds().getCopy();

		Point location = new Point(body.x - getConstraint().width / 2, body.y + getConstraint().y);

		Rectangle newBounds = getConstraint().getCopy();
		newBounds.setLocation(location);
		figureToRelocate.setBounds(newBounds);
	}

	private Rectangle getConstraint() {
		return this.constraint;
	}

	@Override
	public void setConstraint(Rectangle constraint) {
		this.constraint = constraint;
	}

	@Override
	public Rectangle getValidLocation(Rectangle proposedLocation, IFigure borderItem) {
		Rectangle body = headerBodyFigure.getBounds().getCopy();
		Point location = new Point(body.x - getConstraint().width / 2, body.y + getConstraint().y);
		Rectangle newBounds = getConstraint().getCopy();
		newBounds.setLocation(location);
		return newBounds;
	}

	@Override
	public int getCurrentSideOfParent() {
		return PositionConstants.NONE;
	}

}
