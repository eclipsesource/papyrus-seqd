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

package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.internal.ui;

import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessageRuntimeModule;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.MessageRule;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.ui.LwMessageUiModule;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.ui.internal.LwMessageActivator;

import com.google.inject.Module;

/**
 * A custom activator that supports sub-languages for selection of parsers
 * specific to request or reply messages.
 *
 * @author Christian W. Damus
 */
public class LwMessageCustomActivator extends LwMessageActivator {

	/** Grammar sub-identifier for request messages. */
	public static final String REQUEST_MESSAGE = ORG_ECLIPSE_PAPYRUS_UML_DIAGRAM_LIGHTWEIGHTSEQUENCE_MESSAGE_XTEXT_LWMESSAGE
			+ ".request"; //$NON-NLS-1$

	/** Grammar sub-identifier for reply messages. */
	public static final String REPLY_MESSAGE = ORG_ECLIPSE_PAPYRUS_UML_DIAGRAM_LIGHTWEIGHTSEQUENCE_MESSAGE_XTEXT_LWMESSAGE
			+ ".reply"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 *
	 */
	public LwMessageCustomActivator() {
		super();
	}

	@Override
	protected Module getRuntimeModule(String grammar) {
		switch (grammar) {
		case REQUEST_MESSAGE:
			return new LwMessageRuntimeModule(MessageRule.REQUEST);
		case REPLY_MESSAGE:
			return new LwMessageRuntimeModule(MessageRule.REPLY);
		default:
			return super.getRuntimeModule(grammar);
		}
	}

	@Override
	protected Module getUiModule(String grammar) {
		switch (grammar) {
		case REQUEST_MESSAGE:
			return new LwMessageUiModule(this, MessageRule.REQUEST);
		case REPLY_MESSAGE:
			return new LwMessageUiModule(this, MessageRule.REPLY);
		default:
			return super.getUiModule(grammar);
		}
	}
}
