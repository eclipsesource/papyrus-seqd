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
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineHeaderFigure;

//TODO Support a side (Current it is always to the right side)
public class ExecutionSpecificationBorderItemLocator implements IBorderItemLocator {

	private IFigure parentExecutionSpecification;

	private Rectangle constraint;

	public ExecutionSpecificationBorderItemLocator(IFigure figure) {
		this.parentExecutionSpecification = figure;
	}

	@Override
	public void relocate(IFigure borderExecutionSpecification) {
		Point location = new Point(0, getConstraint().y);
		translateToInteraction(location);
		int width = borderExecutionSpecification.getBounds().width;

		location.translate(-width / 2., 0); // Translate to the right border of the parent

		Rectangle newBounds = getConstraint().getCopy();
		newBounds.setLocation(location);
		borderExecutionSpecification.setBounds(newBounds);
	}

	/**
	 * <p>
	 * The border execution specification coordinates are relative to this.figure (Which is also an execution
	 * specification). The result coordinates must be relative to the interaction (Lifeline's parent).
	 * </p>
	 * <p>
	 * This is required because neither the Lifeline nor the Execution Specification has an X/Y Layout
	 * Compartment (Only the interaction does).
	 * </p>
	 * 
	 * @param bounds
	 *            The bounds to be translated to the lifeline's parent coordinates system
	 */
	// TODO: Is there a way to make this more generic, e.g. by identifying the first non-null, non-delegating
	// layout?
	private void translateToInteraction(Translatable bounds) {
		IFigure parent = parentExecutionSpecification;
		while (parent != null) {
			parent.translateToParent(bounds);
			if (parent instanceof LifelineHeaderFigure) {
				parent.translateToParent(bounds);
				return;
			}
			parent = parent.getParent();
		}
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
		return proposedLocation; // TODO Shift x and limit to the height of the parent
	}

	@Override
	public int getCurrentSideOfParent() {
		return PositionConstants.NONE;
	}

}
