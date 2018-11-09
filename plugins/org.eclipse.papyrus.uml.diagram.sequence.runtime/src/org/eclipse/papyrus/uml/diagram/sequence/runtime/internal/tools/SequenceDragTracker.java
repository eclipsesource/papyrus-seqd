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
import org.eclipse.gef.commands.Command;
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

		// All requests of interest for re-ordering have location

		PrivateRequestUtils.setAllowSemanticReordering(getTargetRequest(),
				getCurrentInput().isModKeyDown(getAllowSemanticReorderingModifier()));
	}

	@Override
	protected Command getCommand() {
		if (getTargetEditPart() == null) {
			return null;
		}

		return super.getCommand();
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

	/**
	 * We don't do clone in this diagram (and {@code Ctrl} is used for semantic re-ordering override on Linux
	 * platform).
	 */
	@Override
	protected void setCloneActive(boolean cloneActive) {
		super.setCloneActive(false);
	}

}
