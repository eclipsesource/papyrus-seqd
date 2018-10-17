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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes.Lifeline_Shape;
import static org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes.LIFELINE_NAME;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TextCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.label.ILabelDelegate;
import org.eclipse.gmf.runtime.diagram.ui.label.WrappingLabelDelegate;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.parsers.ParserUtil;

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
	public IParser getParser() {
		if (parser == null) {
			parser = ParserUtil.getParser(Lifeline_Shape, getParserElement(), this, LIFELINE_NAME);
		}
		return parser;
	}

	protected EObject getParserElement() {
		return resolveSemanticElement();
	}

}
