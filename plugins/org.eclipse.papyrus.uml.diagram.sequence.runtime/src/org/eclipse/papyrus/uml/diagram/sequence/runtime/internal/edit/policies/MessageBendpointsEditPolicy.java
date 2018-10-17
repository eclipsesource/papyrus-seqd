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

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConnectionBendpointEditPolicy;
import org.eclipse.gmf.runtime.notation.Edge;

/**
 * Connection bend-points edit-policy for messages, which do not support bendpoints. Except in the case of
 * self-messages, in which the bendpoints cannot be moved anyways (the user can only slide the receive event).
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

	@SuppressWarnings("rawtypes")
	@Override
	protected List createManualHandles() {
		return Collections.EMPTY_LIST;
	}

	@Override
	protected void showMoveBendpointFeedback(BendpointRequest request) {
		// We don't support moving bendpoints; only the ends
	}

	@Override
	protected Command getBendpointsChangedCommand(Connection connection, Edge edge) {
		return UnexecutableCommand.INSTANCE;
	}
}
