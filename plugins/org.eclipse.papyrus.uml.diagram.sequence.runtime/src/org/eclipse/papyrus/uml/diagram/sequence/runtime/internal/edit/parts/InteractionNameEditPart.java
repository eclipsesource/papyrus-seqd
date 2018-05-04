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

import java.util.Objects;

import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TextCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.label.ILabelDelegate;
import org.eclipse.gmf.runtime.diagram.ui.label.WrappingLabelDelegate;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.border.FrameBorder;
import org.eclipse.uml2.uml.NamedElement;

public class InteractionNameEditPart extends TextCompartmentEditPart {

	private WrappingLabel labelFigure;

	public InteractionNameEditPart(View view) {
		super(view);
	}

	@Override
	protected IFigure createFigure() {
		labelFigure = new WrappingLabel() {
			@Override
			public Dimension getMaximumSize() {
				// Make sure the label is never stretched vertically (We still need horizontal stretching to
				// properly support centering)
				Dimension maximumSize = super.getMaximumSize();
				Dimension preferredSize = getPreferredSize().getCopy();
				return maximumSize.getCopy().setHeight(preferredSize.height);
			}
		};
		labelFigure.setBorder(new CompoundBorder(new FrameBorder(), new MarginBorder(new Insets(5))));
		return labelFigure;
	}

	@Override
	protected ILabelDelegate createLabelDelegate() {
		ILabelDelegate newLabelDelegate = null;
		if (labelFigure != null) {
			newLabelDelegate = new WrappingLabelDelegate(labelFigure);
			newLabelDelegate.setTextJustification(PositionConstants.LEFT);
			newLabelDelegate.setAlignment(PositionConstants.LEFT);
			newLabelDelegate.setTextAlignment(PositionConstants.LEFT);
		}
		return newLabelDelegate;
	}

	@Override
	protected void refreshLabel() {
		super.refreshLabel();
		// border.setLabel(getLabelText());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	String currentText;

	@Override
	protected String getLabelText() {
		// TODO remove override to get normal parser working
		// return super.getLabelText();

		EObject element = resolveSemanticElement();
		if (NamedElement.class.isInstance(element)) {
			String newText = NamedElement.class.cast(element).getName();
			if (!Objects.equals(currentText, newText)) {
				currentText = newText;

				// By default, changing the text will only repaint the label figure inside the insets
				// The border depends on the text size, so we need to force a full refresh (Including the
				// margin/border)
				// Note: we only do that for the text, because if the font changes, GMF will already take care
				// or repainting the figure
				labelFigure.revalidate();
				labelFigure.repaint();
			}
			return newText;
		}
		return null;
	}

}
