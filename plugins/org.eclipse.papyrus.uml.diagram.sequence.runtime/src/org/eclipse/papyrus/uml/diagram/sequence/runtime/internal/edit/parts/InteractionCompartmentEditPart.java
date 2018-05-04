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
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.layout.FreeFormLayoutEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.InteractionCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.InteractionLayoutEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.InteractionSemanticEditPolicy;

public class InteractionCompartmentEditPart extends ShapeCompartmentEditPart {

	public InteractionCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	protected IFigure createFigure() {
		// Hide the border; we don't want a complete horizontal line on top (Because the Interaction Label has
		// its own border that doesn't take all width)
		IFigure result = super.createFigure();
		result.setBorder(null);
		return result;
	}
	
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new InteractionCreationEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new InteractionSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new InteractionLayoutEditPolicy());
	}

	@Override
	protected LayoutManager getLayoutManager() {
		FreeFormLayoutEx layout = new FreeFormLayoutEx();
		layout.setPositiveCoordinates(true);
		return layout;
	}

}
