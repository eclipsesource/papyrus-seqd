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

package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext;

/**
 * Enumeration of message rules to parse.
 *
 * @author Christian W. Damus
 */
public enum MessageRule {
	/** The default rule: parse any sort of message. */
	DEFAULT,
	/** Parse a request message only. */
	REQUEST,
	/** Parse a reply message only. */
	REPLY;
}