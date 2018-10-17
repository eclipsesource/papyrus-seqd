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
import org.eclipse.draw2d.PositionConstants;
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
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.NodeFigureMagnetHelper;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ExecutionSpecificationGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ExecutionSpecificationSemanticEditPolicy;
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

	private static final int[] MAGNET_POSITIONS = {PositionConstants.NORTH, PositionConstants.SOUTH };

	private NodeFigureMagnetHelper magnetHelper;

	public ExecutionSpecificationEditPart(View view) {
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
	protected NodeFigure createMainFigure() {
		NodeFigure nodeFigure = new ExecutionSpecificationFigure();
		return nodeFigure;
	}

	@Override
	protected NodeFigure createNodeFigure() {
		NodeFigure result = super.createNodeFigure();

		// Configure magnets
		magnetHelper = new NodeFigureMagnetHelper(result, getMagnetManager(), getMagnetStrength())
				.registerMagnets(MAGNET_POSITIONS);

		return result;
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableBorderItemPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new ExecutionSpecificationSemanticEditPolicy());
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

}
