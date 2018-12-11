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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageFeedbackHelper.getLocation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.forwardParameters;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.getOriginalMouseLocation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.getOriginalSourceLocation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.getOriginalTargetLocation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.setForce;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.setOriginalMouseLocation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.setOriginalSourceLocation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.setOriginalTargetLocation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.setUpdatedSourceLocation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.setUpdatedTargetLocation;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.handles.ConnectionHandle;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnet;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageFeedbackHelper.Mode;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.handles.SequenceConnectionEndpointHandle;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.CommandCreatedEvent;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DependencyContext;
import org.eclipse.papyrus.uml.interaction.model.MDestruction;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Message;

/**
 * Endpoint edit-policy for management of message ends.
 */
public class MessageEndpointEditPolicy extends ConnectionEndpointEditPolicy implements ISequenceEditPolicy {

	private final EventBus bus;

	// Last known mouse location on the connection in case of move gesture
	private Point lastMouseLocation;

	// Keeping track of original anchors for move feed-back (which moves both anchors)
	private ConnectionAnchor originalSource;

	private ConnectionAnchor originalTarget;

	private MessageFeedbackHelper feedbackHelper;

	/**
	 * Initializes me with my event bus.
	 * 
	 * @param bus
	 *            an event bus on which to publish commands that I create
	 */
	public MessageEndpointEditPolicy(EventBus bus) {
		super();

		this.bus = bus;
	}

	@Override
	public Command getCommand(Request request) {
		// Provide a dependency context for all command construction
		return DependencyContext.getDynamic().withContext(() -> {
			Command result;

			if (REQ_CREATE_BENDPOINT.equals(request.getType())) {
				result = getMoveConnectionCommand((BendpointRequest)request);
			} else {
				result = super.getCommand(request);
			}

			bus.post(new CommandCreatedEvent(request, result));

			return result;
		});
	}

	/**
	 * Constrain the alignment of ends as appropriate to the message sort.
	 */
	@Override
	protected FeedbackHelper getFeedbackHelper(ReconnectRequest request) {
		if (feedbackHelper == null) {
			Message message = (Message)((IGraphicalEditPart)getHost()).resolveSemanticElement();
			boolean synch = MessageUtil.isSynchronous(message.getMessageSort());
			boolean source = request.isMovingStartAnchor();
			feedbackHelper = new MessageFeedbackHelper(source ? Mode.MOVE_SOURCE : Mode.MOVE_TARGET, synch,
					getMagnetManager(), getLayoutConstraints());
			feedbackHelper.setConnection(getConnection());
		}
		return feedbackHelper;
	}

	@Override
	public void showSourceFeedback(Request request) {
		if (REQ_CREATE_BENDPOINT.equals(request.getType())) {
			showConnectionMoveFeedback((BendpointRequest)request);
		} else {
			super.showSourceFeedback(request);
		}
	}

	@Override
	public void showTargetFeedback(Request request) {
		if (REQ_SELECTION.equals(request.getType()) || REQ_SELECTION_HOVER.equals(request.getType())) {
			// Capture the mouse location in case of initiation of a move by dragging
			lastMouseLocation = ((LocationRequest)request).getLocation().getCopy();
		}
		super.showTargetFeedback(request);
	}

	@Override
	protected void eraseConnectionMoveFeedback(ReconnectRequest request) {
		super.eraseConnectionMoveFeedback(request);
		lastMouseLocation = null;
		feedbackHelper = null;
	}

	protected void showConnectionMoveFeedback(BendpointRequest request) {
		if (originalSource == null) {
			originalSource = getConnection().getSourceAnchor();
			Point sourceLocation = getLocation(originalSource);
			setOriginalSourceLocation(request, sourceLocation);
		}
		setUpdatedSourceLocation(request, getLocation(getConnection().getSourceAnchor()));

		if (originalTarget == null) {
			originalTarget = getConnection().getTargetAnchor();
			Point targetLocation = getLocation(originalTarget);
			setOriginalTargetLocation(request, targetLocation);
		}
		setUpdatedTargetLocation(request, getLocation(getConnection().getTargetAnchor()));

		FeedbackHelper helper = getFeedbackHelper(request);
		helper.setMovingStartAnchor(false);
		helper.update(getConnection().getTargetAnchor(), request.getLocation());
	}

	@Override
	public void eraseSourceFeedback(Request request) {
		if (REQ_CREATE_BENDPOINT.equals(request.getType())) {
			eraseConnectionMoveFeedback((BendpointRequest)request);
		} else {
			super.eraseSourceFeedback(request);
		}
	}

	/**
	 * @param request
	 */
	protected void eraseConnectionMoveFeedback(BendpointRequest request) {
		if (originalSource == null) {
			return;
		}

		getConnection().setSourceAnchor(originalSource);
		getConnection().setTargetAnchor(originalTarget);

		lastMouseLocation = null;
		originalSource = null;
		originalTarget = null;
		feedbackHelper = null;
	}

	protected FeedbackHelper getFeedbackHelper(BendpointRequest request) {
		if (feedbackHelper == null) {
			Message message = (Message)((IGraphicalEditPart)getHost()).resolveSemanticElement();
			boolean synch = MessageUtil.isSynchronous(message.getMessageSort());

			feedbackHelper = new MessageFeedbackHelper(Mode.MOVE_BOTH, synch, getMagnetManager(),
					getLayoutConstraints());
			feedbackHelper.setConnection(getConnection());

			Point grabbedAt = lastMouseLocation;
			if (grabbedAt == null) {
				grabbedAt = request.getLocation();
			}

			feedbackHelper.setGrabbedAt(grabbedAt);

			// Record it also in the request for eventual retrieval for the move command
			setOriginalMouseLocation(request, grabbedAt);
		}
		return feedbackHelper;
	}

	/**
	 * Obtain a command to move the message connection according to the {@code request}ed location.
	 * 
	 * @param request
	 *            the bendpoint request (as we use the create bendpoint gesture to grab and move)
	 * @return the move command
	 */
	protected Command getMoveConnectionCommand(BendpointRequest request) {
		CompoundCommand result = new CompoundCommand();

		Point grabbedAt = getOriginalMouseLocation(request);
		int deltaY = grabbedAt == null ? 0 : request.getLocation().y() - grabbedAt.y();
		if (deltaY != 0) {
			ConnectionEditPart connection = (ConnectionEditPart)getHost();
			EditPart source = connection.getSource();
			EditPart target = connection.getTarget();

			// Don't modify the originals because we get this command many times
			Point sourceLocation = getOriginalSourceLocation(request).getCopy();
			Point targetLocation = getOriginalTargetLocation(request).getCopy();
			sourceLocation.translate(0, deltaY);
			targetLocation.translate(0, deltaY);

			int magnetDelta = 0;
			// Target-end magnets have precedence
			Optional<IMagnet> magnet = getMagnetManager().getCapturingMagnet(targetLocation,
					IMagnet.ownedBy(getHostFigure()));
			if (magnet.isPresent()) {
				Point newTargetLocation = targetLocation.getCopy();
				newTargetLocation.setLocation(magnet.get().getLocation());
				magnetDelta = newTargetLocation.y() - targetLocation.y();
			} else {
				magnet = getMagnetManager().getCapturingMagnet(sourceLocation,
						IMagnet.ownedBy(getHostFigure()));
				if (magnet.isPresent()) {
					Point newSourceLocation = sourceLocation.getCopy();
					newSourceLocation.setLocation(magnet.get().getLocation());
					magnetDelta = newSourceLocation.y() - sourceLocation.y();
				}
			}

			if (magnetDelta != 0) {
				deltaY = deltaY + magnetDelta;
				sourceLocation.translate(0, magnetDelta);
				targetLocation.translate(0, magnetDelta);
			}

			ReconnectRequest sourceReq = new ReconnectRequest(REQ_RECONNECT_SOURCE);
			sourceReq.setTargetEditPart(source);
			sourceReq.setConnectionEditPart(connection);
			sourceReq.setLocation(sourceLocation);
			forwardParameters(sourceReq, request);
			setForce(sourceReq, true);

			// In case the result of the drag moves the source end to a different edit-part
			// (for example, a different execution specification)
			source = retargetRequest(source, sourceReq);
			Command updateSource = source.getCommand(sourceReq);
			Command updateTarget;

			if (isDestruction(target)) {
				// Move the destruction shape, not the connector end
				Element element = (Element)target.getAdapter(EObject.class);
				MDestruction destruction = ((MDestruction)getInteraction().getElement(element).get());

				OptionalInt y = destruction.getTop(); // Top is bottom, both being the centre of the X shape
				OptionalInt newY = y.isPresent() ? OptionalInt.of(y.getAsInt() + deltaY) : y;

				updateTarget = wrap(destruction.setCovered(destruction.getCovered().get(), newY));
			} else {
				// Move the connector end
				ReconnectRequest targetReq = new ReconnectRequest(REQ_RECONNECT_TARGET);
				targetReq.setTargetEditPart(target);
				targetReq.setConnectionEditPart(connection);
				targetReq.setLocation(targetLocation);
				forwardParameters(targetReq, request);
				setForce(targetReq, true);

				// In case the result of the drag moves the target end to a different edit-part
				target = retargetRequest(target, targetReq);
				updateTarget = target.getCommand(targetReq);
			}

			// Never update just one end
			if (updateSource != null && updateTarget != null) {
				result.add(updateSource);
				result.add(updateTarget);
			}
		}

		return result.unwrap();
	}

	private boolean isDestruction(EditPart editPart) {
		boolean result = false;

		Object model = editPart.getModel();
		if (model instanceof Node) {
			result = ViewTypes.DESTRUCTION_SPECIFICATION.equals(((Node)model).getType());
		}

		return result;
	}

	/**
	 * Recompute the target of a re-connection request in case the connection end is being dragged to a
	 * different edit-part than where it connects to currently.
	 * 
	 * @param editPart
	 *            the original connection source/target edit-part that is the intended target edit-part of the
	 *            {@code request}
	 * @param request
	 *            the request to be sent
	 * @return the best available edit-part to which to target the {@code request}, which if different to the
	 *         original {@code editPart} will already be set as the {@code request}'s
	 *         {@link ReconnectRequest#setTargetEditPart(EditPart) target}
	 */
	protected EditPart retargetRequest(EditPart editPart, ReconnectRequest request) {
		EditPart result = editPart;

		EditPart resolvedTarget = editPart.getViewer().findObjectAtExcluding(request.getLocation(),
				Collections.singleton(request.getConnectionEditPart()),
				ep -> ep.getTargetEditPart(request) != null);
		if (resolvedTarget != null && resolvedTarget != editPart) {
			// Ask this one for the command
			request.setTargetEditPart(resolvedTarget);
			result = resolvedTarget;
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List createSelectionHandles() {
		List<ConnectionHandle> result = new ArrayList<>(2);
		ConnectionEditPart host = (ConnectionEditPart)getHost();
		result.add(new SequenceConnectionEndpointHandle(host, ConnectionLocator.SOURCE));
		result.add(new SequenceConnectionEndpointHandle(host, ConnectionLocator.TARGET));
		return result;
	}
}
