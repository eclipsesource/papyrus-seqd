/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import java.util.function.Supplier;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.AbstractPopupBarTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.PopupBarTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusPopupBarEditPolicy;
import org.eclipse.swt.widgets.Control;

/**
 * Custom popup bar edit policy for the lightweight sequence diagram.
 */
public class SequenceDiagramPopupBarEditPolicy extends PapyrusPopupBarEditPolicy {

	/** Y postion offset from shape where the balloon top begins. */
	static private int ACTION_WIDTH_HGT = 30; // As in the superclass

	private Point activatedAt;

	private boolean forceHideAfterDelay;

	/**
	 * Initializes me.
	 */
	public SequenceDiagramPopupBarEditPolicy() {
		super();
	}

	@Override
	protected void showDiagramAssistant(Point referencePoint) {
		boolean didNotExist = !isDiagramAssistantShowing();

		super.showDiagramAssistant(referencePoint);

		if (didNotExist && isDiagramAssistantShowing()) {
			setActivatedAt(referencePoint);

			// We want to show it near the mouse because that's where the child should be created
			Point location = new Point(referencePoint);
			getHostFigure().getParent().translateToAbsolute(location);

			IFigure balloon = getBalloon();
			balloon.getParent().translateToRelative(location);

			// shift the ballon so it is above the cursor. Add some extra space to account
			// for connection handles popping up, too
			location.y -= ACTION_WIDTH_HGT * 2;

			adjustToFitInViewport(location);
			balloon.setLocation(location);
		}
	}

	@Override
	public boolean isDiagramAssistantShowing() {
		IFigure balloon = getBalloon();
		return (balloon != null) && (balloon.getParent() != null);
	}

	protected void setActivatedAt(Point location) {
		this.activatedAt = location;
	}

	protected Point getActivatedAt() {
		return activatedAt;
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		forceHideAfterDelay = getIsInstalledOnSurface();
		try {
			super.mouseMoved(me);
		} finally {
			forceHideAfterDelay = false;
		}
	}

	@Override
	protected void hideDiagramAssistant() {
		if (forceHideAfterDelay) {
			// The user has to move the mouse in order to reach the popup bar,
			// so don't just hide it immediately.
			hideDiagramAssistantAfterDelay(getAppearanceDelay());
		} else {
			super.hideDiagramAssistant();
		}
	}

	@Override
	protected AbstractPopupBarTool createPopupBarTool(CreateRequest request) {
		return new DefaultTool(getHost(), this::getActivatedAt, request);
	}

	@Override
	protected AbstractPopupBarTool createPopupBarTool(IElementType elementType) {
		return new DefaultTool(getHost(), this::getActivatedAt, elementType);
	}

	/**
	 * Uses the balloon location passed in and its size to determine if the balloon will appear outside the
	 * viewport. If so, the balloon location will be modified accordingly.
	 *
	 * @param balloonLocation
	 *            the suggested balloon location passed in and potentially modified when this method completes
	 */
	private void adjustToFitInViewport(Point balloonLocation) {
		Control control = getHost().getViewer().getControl();
		if (control instanceof FigureCanvas) {
			Rectangle viewportRect = ((FigureCanvas)control).getViewport().getClientArea();
			Rectangle balloonRect = new Rectangle(balloonLocation, getBalloon().getSize());

			int yDiff = viewportRect.y - balloonRect.y;
			if (yDiff > 0) {
				// balloon is above the viewport, shift down
				balloonLocation.translate(0, yDiff);
			}
			int xDiff = balloonRect.right() - viewportRect.right();
			if (xDiff > 0) {
				// balloon is to the right of the viewport, shift left
				balloonLocation.translate(-xDiff, 0);
			}
		}
	}

	//
	// Nested types
	//

	protected static class DefaultTool extends PopupBarTool {

		private static final int Y_OFFSET = 32; // Y_OFFSET in the superclass is not visible

		private final Supplier<Point> activatedAt;

		public DefaultTool(EditPart epHost, Supplier<Point> activatedAt, CreateRequest theRequest) {
			super(epHost, theRequest);

			this.activatedAt = activatedAt;
		}

		public DefaultTool(EditPart epHost, Supplier<Point> activatedAt, IElementType elementType) {
			super(epHost, elementType);

			this.activatedAt = activatedAt;
		}

		/**
		 * We don't fall back on creating the element only if a nested view cannot be created.
		 */
		@Override
		protected Command getCommand() {
			Request request = this.getTargetRequest();

			if (request instanceof CreateRequest) {
				Point location = activatedAt.get();
				if (location == null) {
					location = this.getCurrentInput().getMouseLocation();
					location.y += Y_OFFSET;
				}
				((CreateRequest)request).setLocation(location);
			}

			EditPart target = getHost().getTargetEditPart(request);
			if (target == null) {
				target = getHost();
			}

			Command result = target.getCommand(request);

			// Be safe about the return: never null, but instead unexecutable
			if (result == null) {
				result = UnexecutableCommand.INSTANCE;
			}

			return result;
		}
	}

}
