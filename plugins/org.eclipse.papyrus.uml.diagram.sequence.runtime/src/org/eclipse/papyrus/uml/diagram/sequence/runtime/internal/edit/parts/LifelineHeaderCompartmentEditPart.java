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
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

public class LifelineHeaderCompartmentEditPart extends CompartmentEditPart {

	public LifelineHeaderCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	protected IFigure createFigure() {
		NodeFigure nodeFigure = new NodeFigure();
		ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout(false);
		layout.setStretchMajorAxis(true);
		layout.setStretchMinorAxis(true);
		nodeFigure.setLayoutManager(layout);
		return nodeFigure;
	}

}
