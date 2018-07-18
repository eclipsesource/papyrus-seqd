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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConnectionBendpointEditPolicy;

/**
 * Connection bend-points edit-policy for messages, which do not support bendpoints.
 */
public class MessageBendpointsEditPolicy extends ConnectionBendpointEditPolicy {

	public MessageBendpointsEditPolicy() {
		super(); // Always oblique
	}

	@Override
	protected Command getCreateBendpointCommand(BendpointRequest request) {
		return null;
	}

	@Override
	protected void showCreateBendpointFeedback(BendpointRequest request) {
		// No bendpoints
	}
}
