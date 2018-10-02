/******************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.internal.model.impl;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	static {
		NLS.initializeMessages("messages", Messages.class); //$NON-NLS-1$
	}

	private Messages() {
	}

	public static String Create;

	public static String AsyncCall;

	public static String SyncSignal;

	public static String SyncCall;

	public static String CreateMessage;

	public static String DeleteMessage;

	public static String ReplyMessage;

	public static String CreateMessageWithExecution;

	public static String ActionExecutionSpecification;

	public static String BehaviorExecutionSpecification;

}
