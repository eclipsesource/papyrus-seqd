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

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils;

/**
 * A resize tracker with support for keyboard modifiers that request sequence-diagram-specific alternative
 * behaviors.
 */
public class SequenceResizeTracker extends ResizeTracker {

	/**
	 * Initializes me.
	 *
	 * @param owner
	 *            my owning edit-part
	 * @param direction
	 *            the resize direction mask
	 */
	public SequenceResizeTracker(GraphicalEditPart owner, int direction) {
		super(owner, direction);
	}

	@Override
	protected Request createSourceRequest() {
		ChangeBoundsRequest result = new ChangeBoundsRequest(REQ_RESIZE) {
			@Override
			public void setCenteredResize(boolean value) {
				// We don't do centered resizing in this diagram
			}

			@Override
			public boolean isCenteredResize() {
				// We don't do centered resizing in this diagram
				return false;
			}
		};

		result.setResizeDirection(getResizeDirection());

		return result;
	}

	@Override
	protected void updateSourceRequest() {
		super.updateSourceRequest();

		// All requests of interest for re-ordering have location

		PrivateRequestUtils.setAllowSemanticReordering(getSourceRequest(),
				getCurrentInput().isModKeyDown(getAllowSemanticReorderingModifier()));
	}
}
