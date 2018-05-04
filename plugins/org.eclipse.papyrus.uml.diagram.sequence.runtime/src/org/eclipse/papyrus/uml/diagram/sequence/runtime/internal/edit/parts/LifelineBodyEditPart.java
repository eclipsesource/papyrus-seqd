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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineBodyFigure;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.BodyResizeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.InteractionSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators.OnLineBorderItemLocator;

/**
 * Edit Part that manages the body (the <i>line</i>) of the lifeline.
 */
public class LifelineBodyEditPart extends BorderedBorderItemEditPart {

	/**
	 * The minimum width of a LifelineBody figure, in pixels
	 */
	public static final int MIN_WIDTH = 1;

	/**
	 * The minimum height of a LifelineBody figure, in pixels
	 */
	public static final int MIN_HEIGHT = 20;

	/**
	 * Constructor.
	 *
	 * @param view
	 *            the view model.
	 */
	public LifelineBodyEditPart(View view) {
		super(view);
	}

	@Override
	protected NodeFigure createMainFigure() {
		LifelineBodyFigure fig = new LifelineBodyFigure();
		return fig;
	}

	@Override
	protected NodeFigure createNodeFigure() {
		return new BorderedNodeFigure(createMainFigure()) {
			@Override
			public boolean containsPoint(int x, int y) {
				// By default, BorderedNodeFigure will delegate to their border items,
				// but not to their main figure (Which may provide a custom containsPoint(), especially when
				// the main figure is a 1px-wide line...)
				return super.containsPoint(x, y) || getMainFigure().containsPoint(x, y);
			}
		};
	}

	@Override
	protected void refreshBounds() {
		if (getBorderItemLocator() != null) {
			int x = ((Integer)getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X()))
					.intValue();
			int y = ((Integer)getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y()))
					.intValue();
			Point loc = new Point(x, y);

			// Change the width: this is the Line width; not the BoundsWidth (We don't want to resize the body
			// horizontally)
			int width = Math.max(MIN_WIDTH,
					((Integer)getStructuralFeatureValue(NotationPackage.eINSTANCE.getLineStyle_LineWidth()))
							.intValue());
			int height = Math.max(MIN_HEIGHT,
					((Integer)getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height()))
							.intValue());
			Dimension size = new Dimension(width, height);

			getBorderItemLocator().setConstraint(new Rectangle(loc, size));
			getBorderItemLocator().relocate(getFigure());
		} else {
			super.refresh();
		}
	}

	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);
		if (event.getNotifier() == getNotationView()
				&& NotationPackage.Literals.LINE_STYLE__LINE_WIDTH == event.getFeature()) {
			refreshBounds();
		}
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new LifelineCreationEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new InteractionSemanticEditPolicy());

		ResizableEditPolicy resizePolicy = new BodyResizeEditPolicy();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, resizePolicy);
	}

	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		borderItemContainer.add(borderItemEditPart.getFigure(), new OnLineBorderItemLocator(getMainFigure()));
	}
}
