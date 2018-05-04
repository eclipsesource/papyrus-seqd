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

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.StateInvariantFigure;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ResizableBorderItemPolicy;

public class StateInvariantEditPart extends AbstractBorderItemEditPart {

	public StateInvariantEditPart(View view) {
		super(view);
	}

	@Override
	protected NodeFigure createNodeFigure() {
		NodeFigure nodeFigure = new StateInvariantFigure();
		return nodeFigure;
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableBorderItemPolicy());
	}

	@Override
	protected void refreshBounds() {
		super.refreshBounds();
		IBorderItemLocator locator = getBorderItemLocator();
		if (locator != null) {
			locator.relocate(getFigure());
		}
	}

}
