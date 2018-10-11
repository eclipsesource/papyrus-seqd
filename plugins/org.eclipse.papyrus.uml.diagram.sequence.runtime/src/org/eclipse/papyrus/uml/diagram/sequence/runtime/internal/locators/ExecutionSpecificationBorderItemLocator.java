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

//TODO Support a side (Current it is always to the right side)
public class ExecutionSpecificationBorderItemLocator implements IBorderItemLocator {

	private IFigure parentExecutionSpecification;

	private Rectangle constraint;

	public ExecutionSpecificationBorderItemLocator(IFigure figure) {
		this.parentExecutionSpecification = figure;
	}

	@Override
	public void relocate(IFigure borderExecutionSpecification) {
		Rectangle parentExecution = parentExecutionSpecification.getBounds().getCopy();

		Point location = new Point(parentExecution.getRight().x() - getConstraint().width / 2,
				parentExecution.getTop().y + getConstraint().y);

		Rectangle newBounds = getConstraint().getCopy();
		newBounds.setLocation(location);
		borderExecutionSpecification.setBounds(newBounds);
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
		Rectangle parentExecution = parentExecutionSpecification.getBounds().getCopy();
		Point location = new Point(parentExecution.getRight().x - getConstraint().width / 2,
				parentExecution.getTop().y + getConstraint().y);
		Rectangle newBounds = getConstraint().getCopy();
		newBounds.setLocation(location);
		return newBounds;
	}

	@Override
	public int getCurrentSideOfParent() {
		return PositionConstants.NONE;
	}

}
