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

import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.Modifiers.ARROW;

import org.eclipse.draw2d.Connection;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.GeneralOrderingFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.RightArrowDecoration;

public class GeneralOrderingEditPart extends ConnectionNodeEditPart implements ISequenceEditPart {

	public GeneralOrderingEditPart(View view) {
		super(view);
	}

	@Override
	protected Connection createConnectionFigure() {
		GeneralOrderingFigure connection = new GeneralOrderingFigure();
		RightArrowDecoration targetDecoration = new RightArrowDecoration();
		targetDecoration.setScale(getMinimumWidth(ARROW), getMinimumHeight(ARROW));
		connection.setTargetDecoration(targetDecoration);
		return connection;
	}

}
