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

import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.InteractionFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.MagnetManager;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Interaction;

public class InteractionEditPart extends AbstractBorderedShapeEditPart {

	private final IMagnetManager magnetManager;

	public InteractionEditPart(View view) {
		super(view);

		this.magnetManager = new MagnetManager();
	}

	@Override
	protected NodeFigure createMainFigure() {
		return new InteractionFigure();
	}

	protected Interaction getInteraction() {
		return (Interaction)UMLUtil.resolveUMLElement(this);
	}

	public IMagnetManager getMagnetManager() {
		return magnetManager;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class key) {
		if (key == IMagnetManager.class) {
			return magnetManager;
		}
		return super.getAdapter(key);
	}
}
