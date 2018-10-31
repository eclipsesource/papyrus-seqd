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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.handles;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.tools.ConnectionEndpointTracker;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequenceConnectionEndpointTracker;

/**
 * Custom connection end-point handle that enables semantic reordering control by modifier keys.
 */
public class SequenceConnectionEndpointHandle extends ConnectionEndpointHandle {

	/**
	 * Initializes me.
	 *
	 * @param owner
	 * @param endPoint
	 */
	public SequenceConnectionEndpointHandle(ConnectionEditPart owner, int endPoint) {
		super(owner, endPoint);
	}

	/**
	 * Initializes me.
	 *
	 * @param owner
	 * @param fixed
	 * @param endPoint
	 */
	public SequenceConnectionEndpointHandle(ConnectionEditPart owner, boolean fixed, int endPoint) {
		super(owner, fixed, endPoint);
	}

	@Override
	protected DragTracker createDragTracker() {
		ConnectionEndpointTracker result = null;

		if (!isFixed()) {
			result = new SequenceConnectionEndpointTracker((ConnectionEditPart)getOwner());
			if (getEndPoint() == ConnectionLocator.SOURCE) {
				result.setCommandName(RequestConstants.REQ_RECONNECT_SOURCE);
			} else {
				result.setCommandName(RequestConstants.REQ_RECONNECT_TARGET);
			}
			result.setDefaultCursor(getCursor());
		}

		return result;
	}

}
