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
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.InteractionFigureGroup;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Interaction;

public class InteractionEditPart extends ShapeEditPart {

	public InteractionEditPart(View view) {
		super(view);
	}

	@Override
	public IFigure createFigure() {
		return new InteractionFigureGroup();
	}

	@Override
	public InteractionFigureGroup getFigure() {
		return (InteractionFigureGroup)super.getFigure();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		getFigure().getHeader().setText(getInteraction().getName());
	}

	@Override
	public IFigure getContentPane() {
		return getFigure().getContentFigure();
	}

	protected Interaction getInteraction() {
		return (Interaction)UMLUtil.resolveUMLElement(this);
	}

}
