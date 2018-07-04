/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.factories;

import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.optimal.ShapeViewFactory;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper.ViewTypes;

/**
 * View factory for the interaction frame, especially in a new diagram.
 *
 * @author Christian W. Damus
 */
public class InteractionFrameViewFactory extends ShapeViewFactory {

	/**
	 * Initializes me.
	 */
	public InteractionFrameViewFactory() {
		super();
	}

	@Override
	protected Shape createNode() {
		Shape result = super.createNode();

		DecorationNode nameLabel = NotationFactory.eINSTANCE.createDecorationNode();
		nameLabel.setType(ViewTypes.INTERACTION_NAME);
		ViewUtil.insertChildView(result, nameLabel, ViewUtil.APPEND, true);

		Compartment contents = NotationFactory.eINSTANCE.createCompartment();
		contents.setType(ViewTypes.INTERACTION_CONTENTS);
		ViewUtil.insertChildView(result, contents, ViewUtil.APPEND, true);

		return result;
	}

	@Override
	protected LayoutConstraint createLayoutConstraint() {
		Bounds result = NotationFactory.eINSTANCE.createBounds();

		// FIXME: These are just to match the sample models used so far in development
		result.setX(37);
		result.setY(12);
		result.setWidth(600);
		result.setHeight(450);
		// END

		return result;
	}

	@Override
	protected void initializeFromPreferences(View view) {
		// Don't initialize fonts and colours; leave that to CSS styling
	}
}
