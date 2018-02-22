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

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.MessageAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.MessageFigure;

public class MessageEditPart extends ConnectionNodeEditPart {

	public MessageEditPart(View view) {
		super(view);
	}

	@Override
	protected Connection createConnectionFigure() {
		return new MessageFigure();
	}

	@Override
	protected ConnectionAnchor getSourceConnectionAnchor() {
		// TODO: The message may be connected to various elements (Including Gates, ExecutionSpecification,
		// Lost/Found...). We can't hard-code the anchoring mechanism here
		if (getSource() != null && getSource() instanceof NodeEditPart) {
			NodeEditPart editPart = (NodeEditPart)getSource();
			if (editPart instanceof LifelineEditPart) {
				LifelineFigure lifelineFigure = ((LifelineEditPart)editPart).getFigure();
				MessageAnchor sourceAnchor = new MessageAnchor(lifelineFigure.getBodyFigure(), null);
				sourceAnchor.setName("SourceAnchor"); //$NON-NLS-1$
				return sourceAnchor;
			}
		}
		return super.getSourceConnectionAnchor();
	}

	@Override
	protected ConnectionAnchor getTargetConnectionAnchor() {
		// TODO: The message may be connected to various elements (Including Gates, ExecutionSpecification,
		// Lost/Found...). We can't hard-code the anchoring mechanism here
		if (getTarget() != null && getTarget() instanceof NodeEditPart) {
			NodeEditPart editPart = (NodeEditPart)getTarget();
			if (editPart instanceof LifelineEditPart) {
				LifelineFigure lifelineFigure = ((LifelineEditPart)editPart).getFigure();
				MessageAnchor targetAnchor = new MessageAnchor(lifelineFigure.getBodyFigure(), null);
				targetAnchor.setName("TargetAnchor"); //$NON-NLS-1$
				return targetAnchor;
			}
		}
		return super.getTargetConnectionAnchor();
	}

}
