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
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TextCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.label.ILabelDelegate;
import org.eclipse.gmf.runtime.diagram.ui.label.WrappingLabelDelegate;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.NamedElement;

public class LifelineNameEditPart extends TextCompartmentEditPart {

	public LifelineNameEditPart(View view) {
		super(view);
	}

	@Override
	protected IFigure createFigure() {
		IFigure label = new WrappingLabel();
		return label;
	}

	@Override
	protected ILabelDelegate createLabelDelegate() {
		WrappingLabel label = (WrappingLabel)getFigure();
		ILabelDelegate newLabelDelegate = null;
		if (label != null) {
			newLabelDelegate = new WrappingLabelDelegate(label);
			newLabelDelegate.setTextJustification(PositionConstants.CENTER);
			newLabelDelegate.setAlignment(PositionConstants.CENTER);
			newLabelDelegate.setTextAlignment(PositionConstants.CENTER);
		}
		return newLabelDelegate;
	}

	@Override
	protected String getLabelText() {
		// TODO remove override to get normal parser working
		// return super.getLabelText();

		EObject element = resolveSemanticElement();
		if (NamedElement.class.isInstance(element)) {
			return NamedElement.class.cast(element).getName();
		}
		return null;
	}

}
