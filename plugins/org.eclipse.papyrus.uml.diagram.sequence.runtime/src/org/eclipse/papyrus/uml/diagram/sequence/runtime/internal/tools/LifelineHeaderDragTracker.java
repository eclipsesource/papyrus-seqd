/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.PrivateToolUtils.getAllowSemanticReorderingModifier;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils;

public class LifelineHeaderDragTracker extends DragEditPartsTrackerEx {

	public LifelineHeaderDragTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}

	@Override
	protected boolean isCloneActive() {
		return false;
	}

	@Override
	protected void updateTargetRequest() {
		super.updateTargetRequest();
		PrivateRequestUtils.setAllowSemanticReordering(getTargetRequest(),
				getCurrentInput().isModKeyDown(getAllowSemanticReorderingModifier()));
	}
}
