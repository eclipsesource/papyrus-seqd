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

import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageFeedbackHelper.Mode;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.uml2.uml.Message;

/**
 * Endpoint edit-policy for management of message ends.
 */
public class MessageEndpointEditPolicy extends ConnectionEndpointEditPolicy {

	private FeedbackHelper feedbackHelper;

	/**
	 * Initializes me.
	 */
	public MessageEndpointEditPolicy() {
		super();
	}

	/**
	 * Constrain the alignment of ends as appropriate to the message sort.
	 */
	@Override
	protected FeedbackHelper getFeedbackHelper(ReconnectRequest request) {
		if (feedbackHelper == null) {
			Message message = (Message)((IGraphicalEditPart)getHost()).resolveSemanticElement();
			boolean synch = MessageUtil.isSynchronous(message.getMessageSort());
			boolean source = request.isMovingStartAnchor();

			feedbackHelper = new MessageFeedbackHelper(Mode.MOVE, synch, !source);
			feedbackHelper.setConnection(getConnection());
			feedbackHelper.setMovingStartAnchor(source);
		}
		return feedbackHelper;
	}

	@Override
	protected void eraseConnectionMoveFeedback(ReconnectRequest request) {
		super.eraseConnectionMoveFeedback(request);
		feedbackHelper = null;
	}
}
