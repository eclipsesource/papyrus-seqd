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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.PrivateToolUtils.getAllowSemanticReorderingModifier;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.papyrus.infra.gmfdiag.common.snap.PapyrusDragEditPartsTrackerEx;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.LifelineBodyEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils;

/**
 * A drag tracker for the sequence diagram that injects custom keyboard modifier information into the requests
 * that it produces.
 */
public class SequenceDragTracker extends PapyrusDragEditPartsTrackerEx {

	/**
	 * Initializes me.
	 *
	 * @param sourceEditPart
	 */
	public SequenceDragTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}

	@Override
	protected void updateTargetRequest() {
		super.updateTargetRequest();

		ChangeBoundsRequest request = (ChangeBoundsRequest)getTargetRequest();
		if (!"".isEmpty() && !isTargetLocked()) {
			EditPart editPart = null;
			if (getCurrentViewer() != null) {
				editPart = getCurrentViewer().findObjectAtExcluding(getLocation(), getExclusionSet());
			}
			if (editPart instanceof LifelineBodyEditPart) {
				EditPart sourceLifeline = getLifelineBodyEditPart(getSourceEditPart());
				if (sourceLifeline != null && sourceLifeline != editPart) {
					// It's a drop
					request.setType(RequestConstants.REQ_DROP);
				}
			}
		}

		// All requests of interest for re-ordering have location

		PrivateRequestUtils.setAllowSemanticReordering(getTargetRequest(),
				getCurrentInput().isModKeyDown(getAllowSemanticReorderingModifier()));
	}

	protected EditPart getLifelineBodyEditPart(EditPart editPart) {
		EditPart result = null;

		for (EditPart next = editPart; (result == null) && (next != null); next = next.getParent()) {
			if (next instanceof LifelineBodyEditPart) {
				result = next;
			}
		}

		return result;
	}
}
