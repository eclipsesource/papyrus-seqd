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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts;

import static org.eclipse.gmf.runtime.notation.NotationPackage.Literals.LINE_STYLE__LINE_WIDTH;
import static org.eclipse.gmf.runtime.notation.NotationPackage.Literals.LOCATION__X;
import static org.eclipse.gmf.runtime.notation.NotationPackage.Literals.LOCATION__Y;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.Modifiers.ANCHOR;

import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineBodyFigure;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.InteractionSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineBodyDisallowMoveAndResizeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineBodyGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.SequenceDiagramPopupBarEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators.OnLineBorderItemLocator;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MessageSort;

/**
 * Edit Part that manages the body (the <i>line</i>) of the lifeline.
 */
public class LifelineBodyEditPart extends BorderedBorderItemEditPart implements ISequenceEditPart {

	public LifelineBodyEditPart(View view) {
		super(view);
	}

	@Override
	protected NodeFigure createMainFigure() {
		LifelineBodyFigure fig = new LifelineBodyFigure();
		fig.setDefaultAnchorDistance(getMinimumHeight(ANCHOR));
		return fig;
	}

	@Override
	protected NodeFigure createNodeFigure() {
		return new BorderedNodeFigure(createMainFigure()) {
			/**
			 * By default, BorderedNodeFigure will delegate to their border items, but not to their main
			 * figure, which may provide a custom containsPoint(), especially when the main figure is a
			 * 1px-wide line...
			 */
			@Override
			public boolean containsPoint(int x, int y) {
				return super.containsPoint(x, y) || getMainFigure().containsPoint(x, y);
			}
		};
	}

	@Override
	protected void refreshBounds() {
		if (getBorderItemLocator() != null) {
			int width = Math.max(getMinimumWidth(), getIntAttributeValue(LINE_STYLE__LINE_WIDTH));
			Dimension size = new Dimension(width, computeLifelineHeight());
			Point location = new Point(getIntAttributeValue(LOCATION__X), getIntAttributeValue(LOCATION__Y));
			getBorderItemLocator().setConstraint(new Rectangle(location, size));
			getBorderItemLocator().relocate(getFigure());
		} else {
			super.refresh();
		}
	}

	protected int computeLifelineHeight() {
		Shape shape = getShape();
		Element element = (Element)shape.getElement();

		MInteraction mInteraction = getMInteraction();

		/* if we have a destruction spec on this lifeline, end there */
		Optional<MLifeline> mLifeline = mInteraction.getElement(element).filter(MLifeline.class::isInstance)
				.map(MLifeline.class::cast);
		if (mLifeline.isPresent()) {
			Optional<MMessage> end = mInteraction.getMessages().stream()//
					.filter(m -> m.getElement().getMessageSort() == MessageSort.DELETE_MESSAGE_LITERAL)//
					.filter(m -> m.getReceiver().isPresent())//
					.filter(m -> m.getReceiver().get() == mLifeline.get())//
					.findFirst();
			if (end.isPresent()) {
				return end.get().getTop().orElse(0) - getLayoutHelper().getTop(shape);
			}
		}

		/* else go to bottom */
		Optional<Integer> bottomMostElementY = mInteraction.getBottommostElement().map(MElement::getBottom)
				.map(OptionalInt::getAsInt);
		int endOfLifelineY = Math.max(bottomMostElementY.orElse(Integer.valueOf(-1)).intValue(),
				getMinimumHeight());
		return getLayoutHelper().toRelativeY(shape, endOfLifelineY + getPaddingBottom());
	}

	private MInteraction getMInteraction() {
		return MInteraction.getInstance(this.getDiagramView());
	}

	private Shape getShape() {
		return (Shape)getNotationView();
	}

	private int getIntAttributeValue(EAttribute feature) {
		return ((Integer)getStructuralFeatureValue(feature)).intValue();
	}

	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);
		if (event.getNotifier() == getNotationView() && LINE_STYLE__LINE_WIDTH == event.getFeature()) {
			refreshBounds();
		}
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new LifelineCreationEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new InteractionSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.POPUPBAR_ROLE, new SequenceDiagramPopupBarEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new LifelineBodyGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new LifelineBodyDisallowMoveAndResizeEditPolicy());
	}

	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		borderItemContainer.add(borderItemEditPart.getFigure(), new OnLineBorderItemLocator(getMainFigure()));
	}
}
