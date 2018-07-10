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

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ISequenceAnchor;

/**
 * Feedback helper for messages that does not allow them to slope upwards and that ensures horizontality of
 * synchronous messages.
 */
class MessageFeedbackHelper extends FeedbackHelper {
	private final Mode mode;

	private final boolean synchronous;

	// In the case of moving the message, where it is grabbed
	private Point grabbedAt;

	/**
	 * Initializes me.
	 *
	 * @param mode
	 *            the feedback mode
	 * @param synchronous
	 *            whether the message is synchronous, requiring a horizontal alignment
	 */
	MessageFeedbackHelper(Mode mode, boolean synchronous) {
		super();

		this.mode = mode;
		this.synchronous = synchronous;

		setMovingStartAnchor(mode.isMovingSource());
	}

	void setGrabbedAt(Point point) {
		this.grabbedAt = point.getCopy();
	}

	@Override
	public void update(ConnectionAnchor _anchor, Point p) {
		ConnectionAnchor anchor = _anchor;
		ConnectionAnchor other = getOtherAnchor();

		if ((other instanceof ISequenceAnchor) && (anchor instanceof ISequenceAnchor)) {
			ISequenceAnchor otherAnchor = (ISequenceAnchor)other;

			Point thisLocation = getLocation(anchor);
			Point otherLocation = getLocation(otherAnchor);

			if (mode == Mode.MOVE_BOTH) {
				// We maintain the slope anyways, so synchronicity doesn't matter
				int deltaY = p.y() - grabbedAt.y();
				if (deltaY != 0) {
					thisLocation.translate(0, deltaY);
					anchor = recreateAnchor(anchor, thisLocation);

					if (!isMovingStartAnchor()) {
						// We've moved the source and now the target, so update
						// our reference point for the next drag increment
						grabbedAt.translate(0, deltaY);
					}
				}
			} else if (synchronous) {
				// Constrain the message to horizontal
				switch (mode) {
					case CREATE:
						// Force it horizontal
						p.setY(otherLocation.y());
						anchor = recreateAnchor(anchor, p);
						break;
					case MOVE_SOURCE:
					case MOVE_TARGET:
						// Bring the other end along
						otherLocation.setY(p.y());
						other = recreateOtherAnchor(otherAnchor, otherLocation);
						if (mode.isMovingTarget()) {
							getConnection().setSourceAnchor(other);
						} else {
							getConnection().setTargetAnchor(other);
						}
						break;
					default:
						// MOVE_BOTH is handled separately
						break;
				}
			} else {
				// Don't permit the message to go back in time
				int delta = mode.isMovingTarget() ? p.y() - otherLocation.y() : otherLocation.y() - p.y();

				if (delta < 0) {
					switch (mode) {
						case CREATE:
							// Force it horizontal
							p.setY(otherLocation.y());
							anchor = recreateAnchor(anchor, p);
							break;
						case MOVE_SOURCE:
						case MOVE_TARGET:
							// Bring the other end along
							otherLocation.setY(p.y());
							other = recreateOtherAnchor(otherAnchor, otherLocation);
							if (mode.isMovingTarget()) {
								getConnection().setSourceAnchor(other);
							} else {
								getConnection().setTargetAnchor(other);
							}
							break;
						default:
							// MOVE_BOTH is handled separately
							break;
					}
				} else if (synchronous && (delta > 0)) {
					switch (mode) {
						case CREATE:
							// Force it horizontal
							p.setY(otherLocation.y());
							anchor = recreateAnchor(anchor, p);
							break;
						case MOVE_SOURCE:
						case MOVE_TARGET:
							// Let the new slope of the asynchronous message be applied
							break;
						default:
							// MOVE_BOTH is handled separately
							break;
					}
				}
			}
		}

		super.update(anchor, p);
	}

	private ConnectionAnchor getOtherAnchor() {
		Connection connection = getConnection();
		return mode.isMovingTarget() ? connection.getSourceAnchor() : connection.getTargetAnchor();
	}

	static Point getLocation(ConnectionAnchor anchor) {
		IFigure owner = anchor.getOwner();
		Point ownerOrigin = owner.getBounds().getLocation().getCopy();
		owner.getParent().translateToAbsolute(ownerOrigin);
		return anchor.getLocation(ownerOrigin);
	}

	private ConnectionAnchor recreateAnchor(ConnectionAnchor anchor, Point p) {
		NodeFigure owner = (NodeFigure)anchor.getOwner();
		return mode.isMovingTarget() ? owner.getTargetConnectionAnchorAt(p)
				: owner.getSourceConnectionAnchorAt(p);
	}

	private ConnectionAnchor recreateOtherAnchor(ConnectionAnchor anchor, Point p) {
		NodeFigure owner = (NodeFigure)anchor.getOwner();
		return mode.isMovingTarget() ? owner.getSourceConnectionAnchorAt(p)
				: owner.getTargetConnectionAnchorAt(p);
	}

	//
	// Nested types
	//

	/**
	 * Enumeration of feedback modes.
	 */
	enum Mode {
		/** Feedback is presented for creation of a new message. */
		CREATE,
		/** Feedback is presented for the moving of the source end of a message. */
		MOVE_SOURCE,
		/** Feedback is presented for the moving of the target end of a message. */
		MOVE_TARGET,
		/** Feedback is presented for the moving of both ends of a message. */
		MOVE_BOTH;

		boolean isMovingSource() {
			return this == MOVE_SOURCE || this == MOVE_BOTH;
		}

		boolean isMovingTarget() {
			return this == CREATE || this == MOVE_TARGET || this == MOVE_BOTH;
		}
	}
}
