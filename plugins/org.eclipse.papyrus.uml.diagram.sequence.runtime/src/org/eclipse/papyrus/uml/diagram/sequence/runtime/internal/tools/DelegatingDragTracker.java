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

import java.util.Map;

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.widgets.Event;

/**
 * A simple delegating drag tracker.
 */
public class DelegatingDragTracker implements DragTracker {

	private final DragTracker delegate;

	/**
	 * Initializes me with my {@code delegate}.
	 *
	 * @param delegate
	 *            my delegate
	 */
	public DelegatingDragTracker(DragTracker delegate) {
		super();

		this.delegate = delegate;
	}

	protected final DragTracker getDelegate() {
		return delegate;
	}

	//
	// Delegation methods
	//

	@Override
	public void commitDrag() {
		delegate.commitDrag();
	}

	@Override
	public void activate() {
		delegate.activate();
	}

	@Override
	public void deactivate() {
		delegate.deactivate();
	}

	@Override
	public void focusGained(FocusEvent event, EditPartViewer viewer) {
		delegate.focusGained(event, viewer);
	}

	@Override
	public void focusLost(FocusEvent event, EditPartViewer viewer) {
		delegate.focusLost(event, viewer);
	}

	@Override
	public void keyDown(KeyEvent keyEvent, EditPartViewer viewer) {
		delegate.keyDown(keyEvent, viewer);
	}

	@Override
	public void keyTraversed(TraverseEvent event, EditPartViewer viewer) {
		delegate.keyTraversed(event, viewer);
	}

	@Override
	public void keyUp(KeyEvent keyEvent, EditPartViewer viewer) {
		delegate.keyUp(keyEvent, viewer);
	}

	@Override
	public void mouseDoubleClick(MouseEvent mouseEvent, EditPartViewer viewer) {
		delegate.mouseDoubleClick(mouseEvent, viewer);
	}

	@Override
	public void mouseDown(MouseEvent mouseEvent, EditPartViewer viewer) {
		delegate.mouseDown(mouseEvent, viewer);
	}

	@Override
	public void mouseDrag(MouseEvent mouseEvent, EditPartViewer viewer) {
		delegate.mouseDrag(mouseEvent, viewer);
	}

	@Override
	public void mouseHover(MouseEvent mouseEvent, EditPartViewer viewer) {
		delegate.mouseHover(mouseEvent, viewer);
	}

	@Override
	public void mouseMove(MouseEvent mouseEvent, EditPartViewer viewer) {
		delegate.mouseMove(mouseEvent, viewer);
	}

	@Override
	public void mouseUp(MouseEvent mouseEvent, EditPartViewer viewer) {
		delegate.mouseUp(mouseEvent, viewer);
	}

	@Override
	public void mouseWheelScrolled(Event event, EditPartViewer viewer) {
		delegate.mouseWheelScrolled(event, viewer);
	}

	@Override
	public void nativeDragFinished(DragSourceEvent event, EditPartViewer viewer) {
		delegate.nativeDragFinished(event, viewer);
	}

	@Override
	public void nativeDragStarted(DragSourceEvent event, EditPartViewer viewer) {
		delegate.nativeDragStarted(event, viewer);
	}

	@Override
	public void setEditDomain(EditDomain domain) {
		delegate.setEditDomain(domain);
	}

	@Override
	public void setViewer(EditPartViewer viewer) {
		delegate.setViewer(viewer);
	}

	@Override
	public void viewerEntered(MouseEvent mouseEvent, EditPartViewer viewer) {
		delegate.viewerEntered(mouseEvent, viewer);
	}

	@Override
	public void viewerExited(MouseEvent mouseEvent, EditPartViewer viewer) {
		delegate.viewerExited(mouseEvent, viewer);
	}

	@Override
	public void setProperties(Map properties) {
		delegate.setProperties(properties);
	}

}
