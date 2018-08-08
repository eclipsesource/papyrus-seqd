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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts;

import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.Modifiers.ARROW;

import com.google.common.eventbus.EventBus;

import java.util.Optional;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.notation.ArrowType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.MessageFigure;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LogicalModelElementSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageBendpointsEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageEndpointEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.MessageMoveTracker;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.UMLPackage;

public class MessageEditPart extends ConnectionNodeEditPart implements ISequenceEditPart {

	private final EventBus bus = new EventBus();

	public MessageEditPart(View view) {
		super(view);
	}

	@Override
	protected Connection createConnectionFigure() {
		MessageFigure messageFigure = new MessageFigure();
		updateArrowDecoration(messageFigure);
		refreshLineType(messageFigure);
		return messageFigure;
	}

	@Override
	protected void handleNotificationEvent(Notification notification) {
		super.handleNotificationEvent(notification);
		if (UMLPackage.Literals.MESSAGE__MESSAGE_SORT.equals(notification.getFeature())) {
			updateArrowDecoration();
			refreshLineType();
		}
	}

	protected void updateArrowDecoration() {
		updateArrowDecoration(getMessageFigure());
	}

	private void updateArrowDecoration(MessageFigure messageFigure) {
		final Message message = getMessage();
		final int arrowType;
		switch (message.getMessageSort()) {
			case SYNCH_CALL_LITERAL:
				arrowType = ArrowType.SOLID_ARROW;
				break;
			default:
				arrowType = ArrowType.OPEN_ARROW;
		}
		messageFigure.setTargetDecoration(getArrowDecoration(arrowType));
	}

	protected Message getMessage() {
		return (Message)resolveSemanticElement();
	}

	private MessageFigure getMessageFigure() {
		return (MessageFigure)getFigure();
	}

	@Override
	protected RotatableDecoration getArrowDecoration(int arrowType) {
		RotatableDecoration arrowDecoration = super.getArrowDecoration(arrowType);
		getNotationView().getType();
		setScale(arrowDecoration, getMinimumWidth(ARROW), getMinimumHeight(ARROW));
		return arrowDecoration;
	}

	private void setScale(RotatableDecoration decoration, int x, int y) {
		IMapMode mapMode = getMapMode();
		if (decoration instanceof PolygonDecoration) {
			((PolygonDecoration)decoration).setScale(mapMode.DPtoLP(x + getLineWidth()),
					mapMode.DPtoLP(y + getLineWidth()));
		} else if (decoration instanceof PolylineDecoration) {
			((PolylineDecoration)decoration).setScale(mapMode.DPtoLP(x + getLineWidth()),
					mapMode.DPtoLP(y + getLineWidth()));
		}
	}

	@Override
	protected int getLineType() {
		switch (getMessage().getMessageSort()) {
			case REPLY_LITERAL:
			case CREATE_MESSAGE_LITERAL:
				return Graphics.LINE_DOT;
			default:
				return Graphics.LINE_SOLID;
		}
	}

	@Override
	protected void refreshLineType() {
		refreshLineType(getMessageFigure());
	}

	private void refreshLineType(MessageFigure messageFigure) {
		messageFigure.setLineStyle(getLineType());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return super.getSourceConnectionAnchor(request);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return super.getTargetConnectionAnchor(request);
	}

	@Override
	public void showSourceFeedback(Request request) {
		super.showSourceFeedback(request);
	}

	@Override
	public void showTargetFeedback(Request request) {
		super.showTargetFeedback(request);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new LogicalModelElementSemanticEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new MessageEndpointEditPolicy(bus));
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new MessageBendpointsEditPolicy());
	}

	@Override
	protected void refreshRouterChange() {
		// No bendpoints in messages to refresh
	}

	@Override
	protected void refreshRoutingStyles() {
		// Routing styles are not supported because we do not support bendpoints
	}

	@Override
	public DragTracker getDragTracker(Request req) {
		return Optional.ofNullable(super.getDragTracker(req))
				.map(trk -> new MessageMoveTracker(this, trk, bus)).orElse(null);
	}
}
