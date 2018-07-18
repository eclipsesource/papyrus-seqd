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

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import org.eclipse.draw2d.Cursors;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.CommandCreatedEvent;
import org.eclipse.swt.graphics.Cursor;

/**
 * Drag tracker handling the moving of a message, especially updating the cursor according to whether the move
 * can validly be effected.
 *
 * @author Christian W. Damus
 */
public class MessageMoveTracker extends DelegatingDragTracker {

	private final ConnectionEditPart owner;

	private final EventBus bus;

	private Cursor defaultCursor;

	private Cursor disabledCursor;

	private Command command;

	/**
	 * Initializes me.
	 *
	 * @param owner
	 */
	public MessageMoveTracker(ConnectionEditPart owner, DragTracker delegate, EventBus bus) {
		super(delegate);

		this.owner = owner;
		this.bus = bus;

		setDefaultCursor(owner.getFigure().getCursor());
		setDisabledCursor(Cursors.NO);
	}

	/**
	 * @param defaultCursor
	 *            the defaultCursor to set
	 */
	public void setDefaultCursor(Cursor defaultCursor) {
		this.defaultCursor = defaultCursor;
	}

	/**
	 * @param disabledCursor
	 *            the disabledCursor to set
	 */
	public void setDisabledCursor(Cursor disabledCursor) {
		this.disabledCursor = disabledCursor;
	}

	@Override
	public void activate() {
		bus.register(this);

		super.activate();

		refreshCursor();
	}

	@Override
	public void deactivate() {
		bus.unregister(this);
		this.command = null;

		super.deactivate();
		refreshCursor();
	}

	@Subscribe
	public void handleCommandCreated(CommandCreatedEvent event) {
		if (RequestConstants.REQ_CREATE_BENDPOINT.equals(event.getRequest().getType())) {
			this.command = event.getCommand();
			refreshCursor();
		}
	}

	protected void refreshCursor() {
		owner.getFigure().setCursor(command == null || command.canExecute() ? defaultCursor : disabledCursor);
	}
}
