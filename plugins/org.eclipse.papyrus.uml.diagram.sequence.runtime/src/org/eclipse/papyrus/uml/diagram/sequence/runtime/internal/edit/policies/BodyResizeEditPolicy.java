/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * <p>
 * An edit policy to resize the body of a Lifeline. This policy shows the North and South handles to indicate
 * selection, and allows resizing only from the South handle.
 * </p>
 * <p>
 * This policy also disables the Drag/Move behavior, as the Body always has a fixed position on the Lifeline
 * header.
 * </p>
 */
@SuppressWarnings({"hiding", "rawtypes" })
public class BodyResizeEditPolicy extends ResizableBorderItemPolicy {

	public BodyResizeEditPolicy() {
		// We can only resize via the South resize handle. However, we'll still display the North handle
		setResizeDirections(PositionConstants.SOUTH);
		setDragAllowed(false);
	}

	@Override
	protected void createResizeHandle(List handles, int direction) {
		if ((getResizeDirections() & direction) != 0) {
			// Display & enable the South handle
			super.createResizeHandle(handles, direction);
		} else if (PositionConstants.NORTH == direction) {
			// Display the North Handle (Without enabling it) to indicate selection
			createDragHandle(handles, direction);
		}
	}

	@Override
	protected void createMoveHandle(List handles) {
		return;
	}

	@Override
	protected void createDragHandle(List handles, int direction) {
		if (PositionConstants.NORTH == direction) {
			// Disable everything except the North handle (It's not enabled, but still displayed to
			// indicate selection)
			super.createDragHandle(handles, direction);
		}
	}

	@Override
	public void showSourceFeedback(Request request) {
		if (REQ_RESIZE.equals(request.getType()) && request instanceof ChangeBoundsRequest) {
			ChangeBoundsRequest cbRequest = (ChangeBoundsRequest)request;
			if (cbRequest.getResizeDirection() == PositionConstants.SOUTH) {
				Point originalMoveDelta = cbRequest.getMoveDelta();
				Dimension sizeDelta = cbRequest.getSizeDelta();

				// The default ResizeTracker will try to enforce a default figure minSize of 5x5
				// This causes a weird feedback that isn't aligned on the current body figure.
				// The one-line feedback (with a width-delta of 0) is not great either. So we'll
				// keep the 5px-wide rectangle, but move it to be centered around the body figure
				if (sizeDelta.width() > 0) {
					Point moveDelta = originalMoveDelta.getCopy();
					moveDelta.x -= sizeDelta.width() / 2;
					try {
						cbRequest.setMoveDelta(moveDelta);
						super.showSourceFeedback(cbRequest);
						return;
					} finally {
						// Make sure we only change this for the feedback; restore the correct value
						cbRequest.setMoveDelta(originalMoveDelta);
					}
				}
			}
		}
		super.showSourceFeedback(request);
	}

}
