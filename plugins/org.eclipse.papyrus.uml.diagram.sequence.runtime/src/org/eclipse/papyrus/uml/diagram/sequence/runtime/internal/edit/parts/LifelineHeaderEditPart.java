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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineHeaderFigure;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.InteractionSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineHeaderGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineHeaderResizeEditPolicy;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;

/**
 * Figure representing the header of the lifeline, e.g. the part that usually contains the name of the
 * lifeline, and potential stereotypes/additional information.
 */
public class LifelineHeaderEditPart extends AbstractBorderedShapeEditPart {

	public LifelineHeaderEditPart(View view) {
		super(view);
	}

	@Override
	public Command getCommand(Request _request) {
		if ("connection end".equals(_request.getType())) {
			toString();
		}
		Command command = super.getCommand(_request);
		return command;
	}

	@Override
	public void setLayoutConstraint(EditPart child, IFigure childFigure, Object constraint) {
		super.setLayoutConstraint(child, childFigure, constraint);
	}

	@Override
	protected LifelineHeaderFigure createMainFigure() {
		return new LifelineHeaderFigure();
	}

	@Override
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		return super.getContentPaneFor(editPart);
	}

	@Override
	public IFigure getContentPane() {
		return super.getContentPane();
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();

		ResizableEditPolicy resizePolicy = new LifelineHeaderResizeEditPolicy();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, resizePolicy);
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new InteractionSemanticEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new LifelineHeaderGraphicalNodeEditPolicy());
	}

	@Override
	public void installEditPolicy(Object key, EditPolicy editPolicy) {
		super.installEditPolicy(key, editPolicy);
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
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connEditPart) {
		return super.getSourceConnectionAnchor(connEditPart);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connEditPart) {
		return super.getTargetConnectionAnchor(connEditPart);
	}

	protected Lifeline getLifeline() {
		Element element = UMLUtil.resolveUMLElement(this);
		if (Lifeline.class.isInstance(element)) {
			return Lifeline.class.cast(element);
		}
		return null;
	}

	public LifelineHeaderFigure getLifelineHeaderFigure() {
		return (LifelineHeaderFigure)getMainFigure();
	}

	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		super.addChildVisual(childEditPart, index);
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
	}

	@Override
	protected void refreshTransparency() {
		super.refreshTransparency();
	}

	@Override
	protected void refreshBounds() {
		super.refreshBounds();
	}

	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		if (LifelineBodyEditPart.class.isInstance(borderItemEditPart)) {
			borderItemContainer.add(borderItemEditPart.getFigure(),
					new LifelineBodyBorderItemLocator(getMainFigure()));
		} else {
			borderItemContainer.add(borderItemEditPart.getFigure(), new BorderItemLocator(getMainFigure()));
		}
	}

	public static class LifelineBodyBorderItemLocator extends BorderItemLocator {

		public LifelineBodyBorderItemLocator(IFigure parentFigure) {
			super(parentFigure);
			setPreferredSideOfParent(PositionConstants.SOUTH);
		}

		@Override
		public void relocate(IFigure borderItem) {
			Rectangle bounds = getParentFigure().getBounds();
			Point pos = new Point();
			Rectangle constraint = getConstraint().getCopy();
			pos.x = bounds.x + ((bounds.width - constraint.width) / 2);
			pos.y = bounds.y + bounds.height;

			borderItem.setBounds(new Rectangle(pos, constraint.getSize()));
		}

	}

}
