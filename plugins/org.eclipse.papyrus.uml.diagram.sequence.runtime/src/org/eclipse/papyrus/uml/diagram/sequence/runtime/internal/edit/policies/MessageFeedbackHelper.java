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

	private final boolean isTarget;

	/**
	 * Initializes me.
	 *
	 * @param mode
	 *            the feedback mode
	 * @param synchronous
	 *            whether the message is synchronous, requiring a horizontal alignment
	 * @param isTarget
	 *            whether it is the target end that is being placed 9else the source
	 */
	MessageFeedbackHelper(Mode mode, boolean synchronous, boolean isTarget) {
		super();

		this.mode = mode;
		this.synchronous = synchronous;
		this.isTarget = isTarget;
	}

	@Override
	public void update(ConnectionAnchor _anchor, Point p) {
		ConnectionAnchor anchor = _anchor;
		ConnectionAnchor other = getOtherAnchor();

		if ((other instanceof ISequenceAnchor) && (anchor instanceof ISequenceAnchor)) {
			ISequenceAnchor otherAnchor = (ISequenceAnchor)other;

			Point otherLocation = getLocation(otherAnchor);

			if (synchronous) {
				// Constrain the message to horizontal
				switch (mode) {
					case CREATE:
						// Force it horizontal
						p.setY(otherLocation.y());
						anchor = recreateAnchor(anchor, p);
						break;
					case MOVE:
						// Bring the other end along
						otherLocation.setY(p.y());
						other = recreateOtherAnchor(otherAnchor, otherLocation);
						if (isTarget) {
							getConnection().setSourceAnchor(other);
						} else {
							getConnection().setTargetAnchor(other);
						}
						break;
				}
			} else {
				// Don't permit the message to go back in time
				int delta = isTarget ? p.y() - otherLocation.y() : otherLocation.y() - p.y();

				if (delta < 0) {
					switch (mode) {
						case CREATE:
							// Force it horizontal
							p.setY(otherLocation.y());
							anchor = recreateAnchor(anchor, p);
							break;
						case MOVE:
							// Bring the other end along
							otherLocation.setY(p.y());
							other = recreateOtherAnchor(otherAnchor, otherLocation);
							if (isTarget) {
								getConnection().setSourceAnchor(other);
							} else {
								getConnection().setTargetAnchor(other);
							}
							break;
					}
				} else if (synchronous && (delta > 0)) {
					switch (mode) {
						case CREATE:
							// Force it horizontal
							p.setY(otherLocation.y());
							anchor = recreateAnchor(anchor, p);
							break;
						case MOVE:
							// Let the new slope of the asynchronous message be applied
							break;
					}
				}
			}
		}

		super.update(anchor, p);
	}

	private ConnectionAnchor getOtherAnchor() {
		Connection connection = getConnection();
		return isTarget ? connection.getSourceAnchor() : connection.getTargetAnchor();
	}

	private Point getLocation(ConnectionAnchor anchor) {
		IFigure owner = anchor.getOwner();
		Point ownerOrigin = owner.getBounds().getLocation();
		owner.getParent().translateToAbsolute(ownerOrigin);
		return anchor.getLocation(ownerOrigin);
	}

	private ConnectionAnchor recreateAnchor(ConnectionAnchor anchor, Point p) {
		NodeFigure owner = (NodeFigure)anchor.getOwner();
		return isTarget ? owner.getTargetConnectionAnchorAt(p) : owner.getSourceConnectionAnchorAt(p);
	}

	private ConnectionAnchor recreateOtherAnchor(ConnectionAnchor anchor, Point p) {
		NodeFigure owner = (NodeFigure)anchor.getOwner();
		return isTarget ? owner.getSourceConnectionAnchorAt(p) : owner.getTargetConnectionAnchorAt(p);
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
		/** Feedback is presented for the moving of an end of a message. */
		MOVE;
	}
}
