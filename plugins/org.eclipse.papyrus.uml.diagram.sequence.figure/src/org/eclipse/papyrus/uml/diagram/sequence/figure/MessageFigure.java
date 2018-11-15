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
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.figure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ISideAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.MessageSourceAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.MessageTargetAnchor;

/**
 * Figure specific for messages (with a by default
 */
public class MessageFigure extends PolylineConnectionEx {

	private static final String ANCHOR_START_TERMINAL = "start"; //$NON-NLS-1$

	private static final String ANCHOR_END_TERMINAL = "end"; //$NON-NLS-1$

	@Override
	public ConnectionAnchor getConnectionAnchor(String terminal) {
		if (terminal == null || terminal.isEmpty()) {
			return super.getConnectionAnchor(terminal);
		}

		switch (terminal) {
			case ANCHOR_START_TERMINAL:
				return new MessageSourceAnchor(this);
			case ANCHOR_END_TERMINAL:
				return new MessageTargetAnchor(this);
		}

		return super.getConnectionAnchor(terminal);
	}

	@Override
	public void validate() {
		super.validate();

		MessageDirection direction = computeDirection();
		if (getSourceAnchor() instanceof ISideAnchor) {
			((ISideAnchor)getSourceAnchor()).setConnectionSide(direction.getExecutionSide(true));
		}
		if (getTargetAnchor() instanceof ISideAnchor) {
			((ISideAnchor)getTargetAnchor()).setConnectionSide(direction.getExecutionSide(false));
		}
	}

	MessageDirection computeDirection() {
		PointList points = getPoints();

		Point c = naiveCentroid(points);
		Point source = points.getFirstPoint();
		Point target = points.getLastPoint();

		if (source.x < c.x) {
			// It's either left-to-right or a right-side self-message
			return target.x >= c.x ? MessageDirection.LEFT_TO_RIGHT : MessageDirection.SELF_RIGHT;
		} else {
			// It's either right-to-left or a left-side self-message
			return target.x <= c.x ? MessageDirection.RIGHT_TO_LEFT : MessageDirection.SELF_LEFT;
		}
	}

	// We don't need a particularly accurate centroid, nor a more difficult "visual center"
	// because the user doesn't have the freedom to draw arbitrary paths: only either a
	// straight line segment or a rectangular self-message
	private Point naiveCentroid(PointList points) {
		Point result = new Point();
		Point scratch = new Point();
		int count = points.size();

		for (int i = 0; i < count; i++) {
			points.getPoint(scratch, i);
			result.setLocation(result.x + scratch.x, result.y + scratch.y);
		}

		result.x = result.x / count;
		result.y = result.y / count;

		return result;
	}

	//
	// Nested types
	//

	enum MessageDirection {
		/** Ordinary message from left to right. */
		LEFT_TO_RIGHT,
		/** Ordinary message from right to left. */
		RIGHT_TO_LEFT,
		/** Self-message on the right of the lifeline. */
		SELF_RIGHT,
		/** Self-message on the left side of the lifeline. */
		SELF_LEFT;

		int getExecutionSide(boolean sourceAnchor) {
			switch (this) {
				case LEFT_TO_RIGHT:
					return sourceAnchor ? PositionConstants.RIGHT : PositionConstants.LEFT;
				case RIGHT_TO_LEFT:
					return sourceAnchor ? PositionConstants.LEFT : PositionConstants.RIGHT;
				case SELF_RIGHT:
					return PositionConstants.RIGHT;
				case SELF_LEFT:
					return PositionConstants.LEFT;
				default:
					throw new IncompatibleClassChangeError("unknown enum value " + name()); //$NON-NLS-1$
			}
		}
	}

}
