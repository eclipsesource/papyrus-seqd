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

import static org.eclipse.gmf.runtime.notation.NotationPackage.Literals.IDENTITY_ANCHOR__ID;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.Modifiers.ARROW;

import com.google.common.eventbus.EventBus;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.ConnectionLayerEx;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.notation.ArrowType;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.MessageFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.ConnectionFigureMagnetHelper;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LogicalModelElementSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageBendpointsEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageEndpointEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators.SelfMessageRouter;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.MessageMoveTracker;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequenceConnectionSelectionTracker;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.UMLPackage;

@SuppressWarnings("restriction")
public class MessageEditPart extends ConnectionNodeEditPart implements ISequenceEditPart {

	private final EventBus bus = new EventBus();

	private ConnectionFigureMagnetHelper magnetHelper;

	private SelfMessageRouter selfMessageRouter;

	public MessageEditPart(View view) {
		super(view);
	}

	@Override
	public void deactivate() {
		if (magnetHelper != null) {
			magnetHelper.dispose();
			magnetHelper = null;
		}

		super.deactivate();
	}

	@Override
	protected Connection createConnectionFigure() {
		MessageFigure messageFigure = new MessageFigure();
		updateArrowDecoration(messageFigure);
		refreshLineType(messageFigure);

		magnetHelper = new ConnectionFigureMagnetHelper(messageFigure, getMagnetManager(),
				getMagnetStrength()).registerMagnets(true, false);

		return messageFigure;
	}

	@Override
	protected void handleNotificationEvent(Notification notification) {
		super.handleNotificationEvent(notification);
		if (UMLPackage.Literals.MESSAGE__MESSAGE_SORT.equals(notification.getFeature())) {
			updateArrowDecoration();
			refreshLineType();
		}
		refreshLifelineIfHeightOrPositionHasChanged(notification);
	}

	@Override
	protected void refreshBendpoints() {
		// nothing to refresh, as bendpoints are not used
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

	private void refreshLifelineIfHeightOrPositionHasChanged(Notification notification) {
		if (concernsAncestor(notification) && isHeightOrYPositionChange(notification)) {
			MInteraction.getInstance(this.getDiagramView()).getLifelines().stream()
					.forEach(this::refreshLifeline);
		}
	}

	private boolean concernsAncestor(Notification notification) {
		Object notifier = notification.getNotifier();
		return notifier instanceof EObject && EcoreUtil.isAncestor(getNotationView(), (EObject)notifier);
	}

	protected boolean isHeightOrYPositionChange(Notification notification) {
		return notification.getNotifier() instanceof IdentityAnchor
				&& IDENTITY_ANCHOR__ID == notification.getFeature();
	}

	private void refreshLifeline(MLifeline lifeline) {
		lifeline.getDiagramView().ifPresent(this::refreshEditPartOfShape);
	}

	private void refreshEditPartOfShape(Shape shape) {
		if(getViewer() == null) {
			return;
		}
		Object lifelineEditPart = getViewer().getEditPartRegistry().get(shape);
		if (lifelineEditPart instanceof EditPart) {
			EditPart editPart = (EditPart)lifelineEditPart;
			List<?> lifelineEditpartChildren = editPart.getChildren();
			Stream<EditPart> childEditParts = lifelineEditpartChildren.stream()
					.filter(EditPart.class::isInstance).map(EditPart.class::cast);
			childEditParts.forEach(EditPart::refresh);
			editPart.refresh();
		}
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
		removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new LogicalModelElementSemanticEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new MessageEndpointEditPolicy(bus));
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new MessageBendpointsEditPolicy());
	}

	@Override
	protected void refreshRouterChange() {
		// No bendpoints in messages to refresh
	}

	@Override
	protected void handlePropertyChangeEvent(PropertyChangeEvent event) {
		// Avoid calling super, as it only handles the refresh of the routing property. The super method
		// refreshes the router based on the routing style of the notation model. In the case of feedback, the
		// router may be different (getting from a self message to a normal message for example). So refresh
		// should be avoided in this case, and connection router shall be updated on notation model change
		// only. The feedback router is handled in the feedback related methods.
	}

	@Override
	protected void installRouter() {
		ConnectionLayer cLayer = (ConnectionLayer)getLayer(LayerConstants.CONNECTION_LAYER);
		RoutingStyle style = (RoutingStyle)((View)getModel())
				.getStyle(NotationPackage.Literals.ROUTING_STYLE);

		if (style != null && cLayer instanceof ConnectionLayerEx) {

			ConnectionLayerEx cLayerEx = (ConnectionLayerEx)cLayer;
			Routing routing = style.getRouting();
			if (Routing.MANUAL_LITERAL == routing) {
				getConnectionFigure().setConnectionRouter(cLayerEx.getObliqueRouter());
			} else if (Routing.RECTILINEAR_LITERAL == routing) {
				getConnectionFigure().setConnectionRouter(getSelfMessageRouter());
			} else if (Routing.TREE_LITERAL == routing) {
				getConnectionFigure().setConnectionRouter(cLayerEx.getTreeRouter());
			}

		}

		refreshRouterChange();
	}

	private ConnectionRouter getSelfMessageRouter() {
		if (selfMessageRouter == null) {
			int minimumWidth = Activator.getDefault().getLayoutConstraints(getEditingDomain())
					.getMinimumWidth(ViewTypes.MESSAGE);
			selfMessageRouter = new SelfMessageRouter(minimumWidth);
		}
		return selfMessageRouter;
	}

	@Override
	protected void refreshRoutingStyles() {
		// Routing styles are not supported because we do not support bendpoints
	}

	@Override
	public DragTracker getDragTracker(Request req) {
		return new MessageMoveTracker(this, new SequenceConnectionSelectionTracker(this), bus);
	}
}
