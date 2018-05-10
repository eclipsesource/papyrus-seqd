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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.GeometryUtil.asBounds;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.GeometryUtil.asRectangle;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.GeometryUtil.getMoveDelta;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.GeometryUtil.getSizeDelta;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;

/**
 * <p>
 * An edit policy for moving and resizing of the header of a Lifeline. This policy shows the East and West
 * handles to indicate selection, and allows resizing only from those.
 * </p>
 * <p>
 * This policy also constrains the Drag/Move behavior to the horizontal dimension.
 * </p>
 */
public class LifelineHeaderResizeEditPolicy extends ResizableShapeEditPolicy {

	public LifelineHeaderResizeEditPolicy() {
		setResizeDirections(PositionConstants.EAST_WEST);
	}

	@Override
	protected void createResizeHandle(@SuppressWarnings({"rawtypes", "hiding" }) List handles,
			int direction) {
		if ((getResizeDirections() & direction) != 0) {
			// Display & enable the South handle
			super.createResizeHandle(handles, direction);
		}
	}

	@Override
	protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
		Rectangle current = getHostFigure().getBounds();
		Rectangle rectangle = request.getTransformedRectangle(current);
		Bounds bounds = asBounds(rectangle);
		bounds = Activator.getDefault().getLayoutHelper(getHost()).getAdjustedBounds(
				getHost().getAdapter(EObject.class), getHost().getAdapter(Node.class), bounds);
		rectangle = asRectangle(bounds);

		// Create the minimal new request that the super implementation needs
		ChangeBoundsRequest newRequest = new ChangeBoundsRequest(request.getType());
		newRequest.setMoveDelta(getMoveDelta(current, rectangle));
		newRequest.setSizeDelta(getSizeDelta(current, rectangle));
		super.showChangeBoundsFeedback(newRequest);
	}
}
