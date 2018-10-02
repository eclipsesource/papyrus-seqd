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

import com.google.common.eventbus.EventBus;

import java.util.Optional;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineBodyFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ISequenceAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnet;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;

/**
 * Feedback helper for messages that does not allow them to slope upwards and that ensures horizontality of
 * synchronous messages.
 */
class MessageFeedbackHelper extends FeedbackHelper {
	private final Mode mode;

	private final boolean synchronous;

	private final IMagnetManager magnetManager;

	private EventBus bus;

	// In the case of moving the message, where it is grabbed
	private Point grabbedAt;

	private LayoutConstraints layoutConstraints;

	/**
	 * Initializes me.
	 *
	 * @param mode
	 *            the feedback mode
	 * @param synchronous
	 *            whether the message is synchronous, requiring a horizontal alignment
	 * @param magnetManager
	 *            the contextual magnet manager
	 */
	MessageFeedbackHelper(Mode mode, boolean synchronous, IMagnetManager magnetManager,
			LayoutConstraints layoutConstraints) {
		super();

		this.mode = mode;
		this.synchronous = synchronous;
		this.magnetManager = magnetManager;
		this.layoutConstraints = layoutConstraints;

		setMovingStartAnchor(mode.isMovingSource());
	}

	void setGrabbedAt(Point point) {
		this.grabbedAt = point.getCopy();
	}

	@Override
	public void update(ConnectionAnchor _anchor, Point p) {
		ConnectionAnchor[] anchor = {_anchor };
		ConnectionAnchor[] other = {getOtherAnchor() };

		if ((other[0] instanceof ISequenceAnchor) && (anchor[0] instanceof ISequenceAnchor)) {
			Point thisLocation = getLocation(anchor[0]);
			Point otherLocation = getLocation(other[0]);

			if (mode == Mode.MOVE_BOTH) {
				// We maintain the slope anyways, so synchronicity doesn't matter
				int deltaY = p.y() - grabbedAt.y();
				if (deltaY != 0) {
					thisLocation.translate(0, deltaY);
					otherLocation.translate(0, deltaY);
					grabbedAt.translate(0, deltaY); // Prepare the next drag increment

					// Snap each point to the nearest magnet, if any, with priority to the
					// receiving end
					int magnetDelta = 0;
					Optional<IMagnet> magnet = magnetManager.getCapturingMagnet(thisLocation,
							IMagnet.ownedBy(getConnection()));
					if (magnet.isPresent()) {
						magnetDelta = magnet.get().getLocation().y() - thisLocation.y();
					} else {
						magnet = magnetManager.getCapturingMagnet(otherLocation,
								IMagnet.ownedBy(getConnection()));
						if (magnet.isPresent()) {
							magnetDelta = magnet.get().getLocation().y() - otherLocation.y();
						}
					}
					if (magnetDelta != 0) {
						// Additional adjustment
						thisLocation.translate(0, magnetDelta);
						otherLocation.translate(0, magnetDelta);
						grabbedAt.translate(0, magnetDelta);
					}

					recreateAnchor(anchor, thisLocation);
					recreateOtherAnchor(other, otherLocation);
					updateOtherAnchor(other);
				}
			} else {
				// Snap the point to the nearest magnet, if any
				Optional<IMagnet> magnet = magnetManager.getCapturingMagnet(p,
						IMagnet.ownedBy(getConnection()));
				magnet.map(IMagnet::getLocation).ifPresent(p::setLocation);

				// Don't permit the message to go back in time if it's asynchronous
				int delta = mode.isMovingTarget() ? p.y() - otherLocation.y() : otherLocation.y() - p.y();
				boolean selfMessage = onSameLifeline(anchor[0], other[0]);
				if ((synchronous && !selfMessage) || (delta < 0)) {
					// Constrain the message to horizontal
					switch (mode) {
						case CREATE:
							if (!synchronous && delta < 0) {
								/*
								 * do not move an async message send up, because it can't be moved down since
								 * we allow down-sloping. If slope up is small, allows horizontal creation
								 * though
								 */
								if (Math.abs(delta) < layoutConstraints.getAsyncMessageSlopeThreshold()) {
									thisLocation.setY(otherLocation.y());
									recreateAnchor(anchor, thisLocation);
								}
							} else {
								// Bring the other end along (subject to magnet constraints)
								otherLocation.setY(p.y());
							}

							Optional<IMagnet> otherMagnet = magnetManager.getCapturingMagnet(otherLocation,
									IMagnet.ownedBy(getConnection()));
							otherMagnet.map(IMagnet::getLocation).ifPresent(m -> {
								// Don't move this end if the other is stuck to a magnet
								int dy = otherLocation.y() - m.y();
								otherLocation.setLocation(m);
								p.translate(0, -dy);
								recreateAnchor(anchor, p);
							});

							recreateOtherAnchor(other, otherLocation);
							updateOtherAnchor(other);
							break;
						case MOVE_SOURCE:
						case MOVE_TARGET:
							// Force it horizontal
							p.setY(otherLocation.y());
							recreateAnchor(anchor, p);
							break;
						default:
							// MOVE_BOTH is handled separately
							break;
					}
				}
			}

			if (bus != null) {
				if (isMovingStartAnchor()) {
					bus.post(thisLocation);
				} else {
					bus.post(otherLocation);
				}
			}
		}
		super.update(anchor[0], p);
	}

	private ConnectionAnchor getOtherAnchor() {
		Connection connection = getConnection();
		return mode.isMovingTarget() ? connection.getSourceAnchor() : connection.getTargetAnchor();
	}

	private boolean onSameLifeline(ConnectionAnchor a, ConnectionAnchor b) {
		// Find the lifelines
		IFigure llA = getLifelineBody(a);
		IFigure llB = getLifelineBody(b);

		return llA == llB;
	}

	private IFigure getLifelineBody(ConnectionAnchor anchor) {
		IFigure result;

		// Find the lifelines
		for (result = anchor.getOwner(); result != null; result = result.getParent()) {
			if (result instanceof LifelineBodyFigure) {
				break;
			}
		}

		return result;
	}

	static Point getLocation(ConnectionAnchor anchor) {
		IFigure owner = anchor.getOwner();
		Point ownerOrigin = owner.getBounds().getLocation().getCopy();
		owner.getParent().translateToAbsolute(ownerOrigin);
		return anchor.getLocation(ownerOrigin);
	}

	private void recreateAnchor(ConnectionAnchor[] anchor, Point p) {
		NodeFigure owner = (NodeFigure)anchor[0].getOwner();
		anchor[0] = mode.isMovingTarget() ? owner.getTargetConnectionAnchorAt(p)
				: owner.getSourceConnectionAnchorAt(p);
	}

	private void recreateOtherAnchor(ConnectionAnchor[] anchor, Point p) {
		NodeFigure owner = (NodeFigure)anchor[0].getOwner();
		anchor[0] = mode.isMovingTarget() ? owner.getSourceConnectionAnchorAt(p)
				: owner.getTargetConnectionAnchorAt(p);
	}

	void updateOtherAnchor(ConnectionAnchor[] other) {
		if (mode.isMovingTarget()) {
			getConnection().setSourceAnchor(other[0]);
		} else {
			getConnection().setTargetAnchor(other[0]);
		}
	}

	/**
	 * Assign a bus to which I post source location updates.
	 * 
	 * @param bus
	 *            the event bus
	 */
	void setEventBus(EventBus bus) {
		this.bus = bus;
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
