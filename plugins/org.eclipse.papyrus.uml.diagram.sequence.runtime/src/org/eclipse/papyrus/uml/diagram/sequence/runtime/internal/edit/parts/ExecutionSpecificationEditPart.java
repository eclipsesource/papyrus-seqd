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

import static org.eclipse.gmf.runtime.notation.NotationPackage.Literals.LOCATION__Y;
import static org.eclipse.gmf.runtime.notation.NotationPackage.Literals.SIZE__HEIGHT;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.ExecutionSpecificationFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.DefaultMagnet;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ExecutionSpecificationGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.InteractionSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ResizableBorderItemPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators.ExecutionSpecificationBorderItemLocator;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;

/*
 * TODO: By default, border items are not resizable (Event when they are "BorderedBorderItems")
 * This also means that by default, it won't listen to its bounds, and won't refresh properly
 * when org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ResizableBorderItemPolicy is used.
 */
public class ExecutionSpecificationEditPart extends BorderedBorderItemEditPart implements ISequenceEditPart {

	private DefaultMagnet startMagnet;

	private DefaultMagnet finishMagnet;

	public ExecutionSpecificationEditPart(View view) {
		super(view);
	}

	@Override
	public void deactivate() {
		IMagnetManager mgr = IMagnetManager.get(this);

		if (startMagnet != null) {
			mgr.removeMagnet(startMagnet);
			startMagnet = null;
		}
		if (finishMagnet != null) {
			mgr.removeMagnet(finishMagnet);
			finishMagnet = null;
		}

		super.deactivate();
	}

	@Override
	protected NodeFigure createMainFigure() {
		NodeFigure nodeFigure = new ExecutionSpecificationFigure();
		return nodeFigure;
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableBorderItemPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new InteractionSemanticEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new ExecutionSpecificationGraphicalNodeEditPolicy());
	}

	@Override
	protected void refreshBounds() {
		super.refreshBounds();
		IBorderItemLocator locator = getBorderItemLocator();
		if (locator != null) {
			locator.relocate(getFigure());
		}

		updateMagnets();
		// super.refreshBounds();
	}

	@Override
	public IBorderItemLocator getBorderItemLocator() {
		return super.getBorderItemLocator();
	}

	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		borderItemContainer.add(borderItemEditPart.getFigure(),
				new ExecutionSpecificationBorderItemLocator(getMainFigure()));
	}

	@Override
	protected void handleNotificationEvent(Notification notification) {
		super.handleNotificationEvent(notification);
		refreshLifelineIfHeightOrPositionHasChanged(notification);
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
		return SIZE__HEIGHT == notification.getFeature() || LOCATION__Y == notification.getFeature();
	}

	private void refreshLifeline(MLifeline lifeline) {
		lifeline.getDiagramView().ifPresent(this::refreshEditPartOfShape);
	}

	private void refreshEditPartOfShape(Shape shape) {
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

	protected void updateMagnets() {
		IMagnetManager mgr = IMagnetManager.get(this);

		Rectangle rect = getFigure().getBounds().getCopy();
		getFigure().getParent().translateToAbsolute(rect);

		Point start = rect.getTop();
		Point finish = rect.getBottom();

		startMagnet = createOrUpdate(mgr, startMagnet, start);
		finishMagnet = createOrUpdate(mgr, finishMagnet, finish);
	}

	private DefaultMagnet createOrUpdate(IMagnetManager mgr, DefaultMagnet magnet, Point location) {
		DefaultMagnet result = magnet;

		if (result != null) {
			// Update it
			result.setLocation(location);
		} else {
			result = new DefaultMagnet(getLayoutConstraints().getMagnetStrength(getNotationView()));
			result.setLocation(location);
			mgr.addMagnet(result);
		}

		return result;
	}
}
