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
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition.LEFT;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition.RIGHT;
import static org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes.LIFELINE_HEADER;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.uml2.uml.Element;

/**
 * <p>
 * An edit policy for moving and resizing of the header of a Lifeline. This policy shows the East and West
 * handles to indicate selection, and allows resizing only from those.
 * </p>
 * <p>
 * This policy also constrains the Drag/Move behavior to the horizontal dimension.
 * </p>
 */
@SuppressWarnings("boxing")
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
		if (!PrivateRequestUtils.isAllowSemanticReordering(request)) {
			// if semantic reorder is forbidding, show feedback with regard to our neighbors
			getMaxMoveDelta().ifPresent(md -> {
				if (newRequest.getMoveDelta().x() < 0) {
					md.leftDelta.ifPresent(d -> {
						if (d > newRequest.getMoveDelta().x() || d > newRequest.getSizeDelta().width()) {
							correctMoveAndResizeDelta(newRequest, d);
						}
					});
				} else {
					md.rightDelta.ifPresent(d -> {
						if (d < newRequest.getMoveDelta().x() || d < newRequest.getSizeDelta().width()) {
							correctMoveAndResizeDelta(newRequest, d);
						}
					});
				}
			});
		}
		super.showChangeBoundsFeedback(newRequest);
	}

	private void correctMoveAndResizeDelta(ChangeBoundsRequest newRequest, Integer maxDelta) {
		int originalMoveX = newRequest.getMoveDelta().x();
		boolean isMove = newRequest.getMoveDelta().x() != 0;
		boolean isResize = newRequest.getSizeDelta().width() != 0;
		if (isMove && !isResize) {
			// move only
			newRequest.getMoveDelta().setX(maxDelta);
		} else if (isMove && isResize && maxDelta < newRequest.getSizeDelta().width()) {
			// increase size to the right (move and resize)
			newRequest.getMoveDelta().setX(maxDelta);
			int correctedWidth = newRequest.getSizeDelta().width() - maxDelta + originalMoveX;
			newRequest.getSizeDelta().setWidth(correctedWidth);
		} else if (!isMove && isResize && newRequest.getSizeDelta().width() > 0
				&& maxDelta < newRequest.getSizeDelta().width()) {
			// increase size to the left
			newRequest.getSizeDelta().setWidth(maxDelta);
		} else if (isMove && isResize
				&& (newRequest.getMoveDelta().x() + newRequest.getSizeDelta().width()) > 0) {
			// decrease size from the left to the right further than original right position
			// prevent this from happening to prevent semantic move
			newRequest.getSizeDelta().setWidth(-maxDelta);
			newRequest.getMoveDelta().setX(maxDelta);
		}
	}

	@Override
	protected Command getMoveCommand(ChangeBoundsRequest request) {
		if (PrivateRequestUtils.isAllowSemanticReordering(request)) {
			// Logic for issue #32 (Reorder lifeline) goes here.
			// For now simply delegate to regular move command
			return super.getMoveCommand(request);
		} else {
			return super.getMoveCommand(correctMoveOrResizeDelta(request));
		}
	}

	@Override
	protected Command getResizeCommand(ChangeBoundsRequest request) {
		// only support resizing so that it doesn't semantically reorder
		return super.getResizeCommand(correctMoveOrResizeDelta(request));
	}

	private ChangeBoundsRequest correctMoveOrResizeDelta(ChangeBoundsRequest request) {
		Optional<MoveDelta> maxMoveDelta = getMaxMoveDelta();
		if (!maxMoveDelta.isPresent()) {
			return null;
		}

		Optional<Integer> leftDelta = maxMoveDelta.get().leftDelta;
		Optional<Integer> rightDelta = maxMoveDelta.get().rightDelta;

		int moveDelta = request.getMoveDelta().x();
		if (moveDelta < 0 && leftDelta.isPresent()) {
			/* left */
			if (leftDelta.get() > moveDelta) {
				return createAdjustedChangeBoundsRequest(request, leftDelta.get());
			}
		} else if (moveDelta > 0 && rightDelta.isPresent()) {
			/* right */
			if (rightDelta.get() < moveDelta) {
				return createAdjustedChangeBoundsRequest(request, rightDelta.get());
			}
		} else if (request.getMoveDelta().x() == 0 && moveDelta < request.getSizeDelta().width()
				&& rightDelta.isPresent()) {
			return createAdjustedChangeBoundsRequest(request, rightDelta.get());
		} else if (request.getMoveDelta().x() == 0 && moveDelta > request.getSizeDelta().width()
				&& leftDelta.isPresent()) {
			return createAdjustedChangeBoundsRequest(request, leftDelta.get());
		}

		return request;
	}

	private ChangeBoundsRequest createAdjustedChangeBoundsRequest(ChangeBoundsRequest request, int x) {
		ChangeBoundsRequest newRequest = new ChangeBoundsRequest(request.getType());
		newRequest.setMoveDelta(request.getMoveDelta().getCopy());
		newRequest.setSizeDelta(request.getSizeDelta().getCopy());
		correctMoveAndResizeDelta(newRequest, x);
		return newRequest;
	}

	private Optional<MoveDelta> getMaxMoveDelta() {
		List<MLifeline> leftToRight = getInteraction().getLifelines().stream()//
				.sorted((ll1, ll2) -> {
					int ll1Left = ll1.getDiagramView().map(layoutHelper()::getLeft).orElse(-1);
					int ll2Left = ll2.getDiagramView().map(layoutHelper()::getLeft).orElse(-1);
					if (ll1Left == ll2Left) {
						return -1;
					}
					return ll1Left - ll2Left;
				})//
				.collect(Collectors.toList());

		Optional<MLifeline> lifeline = getLifeline();
		if (!lifeline.isPresent()) {
			return Optional.empty();
		}
		int index = leftToRight.indexOf(lifeline.get());
		if (index < 0) {
			return Optional.empty();
		}

		Optional<MLifeline> leftLl = Optional.ofNullable(index > 0 ? leftToRight.get(index - 1) : null);
		Optional<MLifeline> righttLl = Optional
				.ofNullable(index + 1 != leftToRight.size() ? leftToRight.get(index + 1) : null);

		int padding = layoutHelper().getConstraints().getPadding(LEFT, LIFELINE_HEADER)
				+ layoutHelper().getConstraints().getPadding(RIGHT, LIFELINE_HEADER);

		MoveDelta result = new MoveDelta();
		result.leftDelta = leftLl
				.map(left -> left.getRight().orElse(0) - lifeline.get().getLeft().orElse(0) + padding);
		result.rightDelta = righttLl
				.map(right -> right.getLeft().orElse(0) - lifeline.get().getRight().orElse(0) - padding);
		return Optional.of(result);
	}

	LayoutHelper layoutHelper() {
		return Activator.getDefault().getLayoutHelper(getEditingDomain());
	}

	TransactionalEditingDomain getEditingDomain() {
		EObject view = getHost().getAdapter(View.class);
		return (view == null) ? null : TransactionUtil.getEditingDomain(view);
	}

	IGraphicalEditPart getGraphicalHost() {
		return (IGraphicalEditPart)getHost();
	}

	MInteraction getInteraction() {
		return MInteraction.getInstance(getGraphicalHost().getNotationView().getDiagram());
	}

	Optional<MLifeline> getLifeline() {
		EObject element = getGraphicalHost().resolveSemanticElement();
		return as(getInteraction().getElement((Element)element), MLifeline.class);
	}

	private class MoveDelta {
		Optional<Integer> leftDelta = Optional.empty();

		Optional<Integer> rightDelta = Optional.empty();
	}
}
