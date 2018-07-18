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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;

/**
 * Event signalling the creation of a command (or not) in response to an request.
 *
 * @author Christian W. Damus
 */
public final class CommandCreatedEvent {

	private final Request request;

	private final Command command;

	/**
	 * Initializes me with the command that was created and the request that engendered it.
	 * 
	 * @param request
	 *            the request (may not be {@code null})
	 * @param command
	 *            the command created for it (may be {@code null})
	 */
	public CommandCreatedEvent(Request request, Command command) {
		super();

		this.request = request;
		this.command = command;
	}

	/**
	 * Queries the request for which a {@link #getCommand() command} was or was not created.
	 * 
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * Queries the command created for the {@link #getRequest() request}.
	 * 
	 * @return the command, or {@code null} if no command was created
	 */
	public Command getCommand() {
		return command;
	}
}
