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

import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineFigure;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Lifeline;

public class LifelineEditPart extends ShapeNodeEditPart {

	public LifelineEditPart(View view) {
		super(view);
	}

	@Override
	protected NodeFigure createNodeFigure() {
		return new LifelineFigure();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		getFigure().setText(getLifeline().getName());
	}

	@Override
	public LifelineFigure getFigure() {
		return (LifelineFigure)super.getFigure();
	}

	protected Lifeline getLifeline() {
		return (Lifeline)UMLUtil.resolveUMLElement(this);
	}

}
