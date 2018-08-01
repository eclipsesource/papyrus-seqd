/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts;

import static org.eclipse.gmf.runtime.notation.NotationPackage.Literals.LOCATION__Y;
import static org.eclipse.gmf.runtime.notation.NotationPackage.Literals.SIZE__HEIGHT;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.DestructionSpecificationFigure;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LogicalModelElementSemanticEditPolicy;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;

public class DestructionSpecificationEditPart extends AbstractBorderItemEditPart {

	public DestructionSpecificationEditPart(View view) {
		super(view);
	}

	@Override
	protected NodeFigure createNodeFigure() {
		return new DestructionSpecificationFigure();
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new LogicalModelElementSemanticEditPolicy());
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
