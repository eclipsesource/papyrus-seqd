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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.FigureUtils;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.BorderItemResizableEditPolicy;

public class ResizableBorderItemPolicy extends BorderItemResizableEditPolicy {

	/*
	 * By default, the resize command is delegated to the parent (e.g.
	 * XYLayoutEditPolicy#createChangeConstraintCommand) However, the Lifeline doesn't have a
	 * "resize children" policy. We might add it later (So that the Lifeline gets a chance to resize itself
	 * and/or to limit the height of the action spec), but for now, it is simpler to just implement it here,
	 * directly in the border item (ExecutionSpecificationEditPart)
	 */
	@Override
	protected Command getResizeCommand(ChangeBoundsRequest request) {
		IFigure figure = getHostFigure();
		if (figure == null) {
			return super.getResizeCommand(request);
		}

		View shapeView = NotationHelper.findView(getHost());
		Integer x = (Integer)ViewUtil.getStructuralFeatureValue(shapeView,
				NotationPackage.eINSTANCE.getLocation_X());
		Integer y = (Integer)ViewUtil.getStructuralFeatureValue(shapeView,
				NotationPackage.eINSTANCE.getLocation_Y());
		Integer width = (Integer)ViewUtil.getStructuralFeatureValue(shapeView,
				NotationPackage.eINSTANCE.getSize_Width());
		Integer height = (Integer)ViewUtil.getStructuralFeatureValue(shapeView,
				NotationPackage.eINSTANCE.getSize_Height());

		Rectangle bounds = new Rectangle(x.intValue(), y.intValue(), width.intValue(), height.intValue());

		// apply transformation with scaling to get new bounds (missing from getTrasnformedRectangle)
		double scale = FigureUtils.getScale(getHostFigure());
		Dimension scaledSizeDelta = request.getSizeDelta().getCopy();
		scaledSizeDelta.performScale(1 / scale);
		Point scaledMovedDelta = request.getMoveDelta().getCopy();
		scaledMovedDelta.performScale(1 / scale);

		Rectangle newBounds = bounds.translate(scaledMovedDelta).resize(scaledSizeDelta);

		return getSetBoundsCommand(request, (Node)shapeView, newBounds);
	}

	/**
	 * Get a command to change the bounds of the given execution specification shape.
	 * 
	 * @param request
	 *            the change-bounds request
	 * @param execShape
	 *            the execution specification shape to change
	 * @param newBounds
	 *            the new bounds requested for the execution specification
	 * @return the command
	 */
	protected Command getSetBoundsCommand(ChangeBoundsRequest request, Node execShape, Rectangle newBounds) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart)getHost()).getEditingDomain();
		ICommand result = new SetBoundsCommand(editingDomain,
				DiagramUIMessages.SetLocationCommand_Label_Resize, new EObjectAdapter(execShape), newBounds);
		return new ICommandProxy(result);
	}

}
