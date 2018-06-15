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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.ExecutionSpecificationFigure;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LogicalModelElementSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ResizableBorderItemPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators.ExecutionSpecificationBorderItemLocator;

/*
 * TODO: By default, border items are not resizable (Event when they are "BorderedBorderItems")
 * This also means that by default, it won't listen to its bounds, and won't refresh properly
 * when org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ResizableBorderItemPolicy is used.
 */
public class ExecutionSpecificationEditPart extends BorderedBorderItemEditPart {

	public ExecutionSpecificationEditPart(View view) {
		super(view);
	}

	@Override
	protected NodeFigure createMainFigure() {
		NodeFigure nodeFigure = new ExecutionSpecificationFigure();
		return nodeFigure;
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableBorderItemPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new LogicalModelElementSemanticEditPolicy());
	}

	@Override
	protected void refreshBounds() {
		super.refreshBounds();
		IBorderItemLocator locator = getBorderItemLocator();
		if (locator != null) {
			locator.relocate(getFigure());
		}
		// super.refreshBounds();
	}

	@Override
	public IBorderItemLocator getBorderItemLocator() {
		return super.getBorderItemLocator();
	}

	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		borderItemContainer.add(borderItemEditPart.getFigure(),
				new ExecutionSpecificationBorderItemLocator(getMainFigure()));
	}

}
