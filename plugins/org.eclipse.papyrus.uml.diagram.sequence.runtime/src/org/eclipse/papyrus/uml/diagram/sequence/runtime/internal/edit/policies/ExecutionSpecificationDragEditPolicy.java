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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.isAllowSemanticReordering;
import static org.eclipse.papyrus.uml.interaction.internal.model.commands.DependencyContext.defer;
import static org.eclipse.papyrus.uml.interaction.model.util.InteractionFragments.getObstacle;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;

import java.util.Collections;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.LifelineBodyEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators.OnLineBorderItemLocator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequenceResizeTracker;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.NudgeKind;
import org.eclipse.papyrus.uml.interaction.model.util.Lifelines;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Drag (move/resize) edit-policy for execution specifications.
 */
public class ExecutionSpecificationDragEditPolicy extends ResizableBorderItemPolicy implements ISequenceEditPolicy {

	/**
	 * Initializes me.
	 */
	public ExecutionSpecificationDragEditPolicy() {
		super();
	}

	@Override
	public Command getCommand(Request request) {
		// Ensure padding, if required
		return withPadding(request, () -> super.getCommand(request));
	}

	protected MExecution getExecution() {
		Element uml = (Element)((IGraphicalEditPart)getHost()).resolveSemanticElement();
		return (MExecution)getInteraction().getElement(uml).orElse(null);
	}

	@Override
	protected Command getResizeCommand(ChangeBoundsRequest request, Node execShape, Rectangle newBounds) {
		MExecution execution = getExecution();

		OptionalInt top = execution.getTop();
		OptionalInt bottom = execution.getBottom();

		int absTop = getLayoutHelper().toAbsoluteY(execShape, newBounds.y());
		int absBottom = getLayoutHelper().toAbsoluteY(execShape, newBounds.bottom());

		org.eclipse.emf.common.command.Command result = null;
		if (top.isPresent() && (top.getAsInt() != absTop)) {
			// Check for semantic re-ordering if we're not connecting to a message
			if (!Lifelines.getMessageReceive(execution.getOwner(), absTop).isPresent()) {
				result = getNudgeObstacleCommand(request, execution, absTop < top.getAsInt(), absTop,
						absBottom);
			}

			result = chain(result, getMoveStartCommand(request, execution, absTop));
		}
		if (bottom.isPresent() && (bottom.getAsInt() != absBottom)) {
			// Check for semantic re-ordering if we're not connecting to a message
			if (!Lifelines.getMessageSend(execution.getOwner(), absBottom).isPresent()) {
				result = getNudgeObstacleCommand(request, execution, absBottom < bottom.getAsInt(), absTop,
						absBottom);
			}

			result = chain(result, getMoveFinishCommand(request, execution, absBottom));
		}

		return wrap(result);
	}

	private org.eclipse.emf.common.command.Command getNudgeObstacleCommand(ChangeBoundsRequest request,
			MExecution execution, boolean movingUp, int newTop, int newBottom) {

		org.eclipse.emf.common.command.Command result = null;

		// Check for semantic re-ordering
		if (!isAllowSemanticReordering(request)) {
			Optional<MElement<? extends Element>> obstacle = getObstacle(execution, movingUp, newTop,
					newBottom);

			// If there's an obstacle, bump it and everything following (preceding) out of the way
			result = obstacle.map(obs -> {
				// Both diagram views exist if we detected the obstacle
				OptionalInt padding = getLayoutHelper().getPadding(execution.getDiagramView().get(),
						(View)obs.getDiagramView().get());

				if (!padding.isPresent()) {
					return null;
				} else if (LogicalModelPredicates.spans(execution).test(obs)) {
					// In the case that the obstacle we're nudging spans the object we're
					// moving, there's nothing to nudge
					return null;
				} else if (LogicalModelPredicates.above(execution).test(obs)) {
					// Negative nudge to move it upwards
					return obs.nudge(newTop - obs.getBottom().getAsInt() - padding.getAsInt(),
							NudgeKind.PRECEDING);
				} else {
					// Positive nudge to move it downwards
					return obs.nudge(newBottom - obs.getTop().getAsInt() + padding.getAsInt());
				}
			}).orElse(null);
		}

		return result;
	}

	protected org.eclipse.emf.common.command.Command getMoveStartCommand(ChangeBoundsRequest request,
			MExecution execution, int y) {

		org.eclipse.emf.common.command.Command result = null;

		Optional<MOccurrence<?>> start = execution.getStart();

		Supplier<? extends OccurrenceSpecification> umlStart;
		if (isAllowSemanticReordering(request) && start.filter(MMessageEnd.class::isInstance).isPresent()) {
			// Disconnect the message from the execution specification
			CreationCommand<ExecutionOccurrenceSpecification> createStart = execution.createStart();
			umlStart = createStart;
			result = createStart;
		} else {
			umlStart = as(start.map(MOccurrence::getElement), OccurrenceSpecification.class)::get;
		}

		// Move the execution start in any case
		result = chain(result, defer(move(execution, umlStart, y)));

		return result;
	}

	protected org.eclipse.emf.common.command.Command getMoveFinishCommand(ChangeBoundsRequest request,
			MExecution execution, int y) {

		org.eclipse.emf.common.command.Command result = null;

		Optional<MOccurrence<?>> finish = execution.getFinish();

		Supplier<? extends OccurrenceSpecification> umlFinish;
		if (isAllowSemanticReordering(request) && finish.filter(MMessageEnd.class::isInstance).isPresent()) {
			// Disconnect the message from the execution specification
			CreationCommand<ExecutionOccurrenceSpecification> createFinish = execution.createFinish();
			umlFinish = createFinish;
			result = createFinish;
		} else {
			umlFinish = as(finish.map(MOccurrence::getElement), OccurrenceSpecification.class)::get;
		}

		// Move the execution finish in any case
		result = chain(result, defer(move(execution, umlFinish, y)));

		return result;
	}

	protected Supplier<? extends org.eclipse.emf.common.command.Command> move(MExecution execution,
			Supplier<? extends OccurrenceSpecification> occurrence, int y) {
		return () -> {
			OccurrenceSpecification umlOccurrence = occurrence.get();
			MOccurrence<?> mOccurrence = (execution.getElement().getStart() == umlOccurrence)
					? execution.getStart().get()
					: execution.getFinish().get();
			return mOccurrence.setCovered(execution.getOwner(), OptionalInt.of(y));
		};
	}

	@Override
	protected ResizeTracker getResizeTracker(int direction) {
		return new SequenceResizeTracker((GraphicalEditPart)getHost(), direction);
	}

	@Override
	protected Command getMoveCommand(ChangeBoundsRequest request, Node execShape, Rectangle newBounds) {
		MExecution execution = getExecution();

		OptionalInt top = execution.getTop();
		OptionalInt bottom = execution.getBottom();

		int absTop = getLayoutHelper().toAbsoluteY(execShape, newBounds.y());
		int absBottom = getLayoutHelper().toAbsoluteY(execShape, newBounds.bottom());

		org.eclipse.emf.common.command.Command result = null;

		// Check for semantic re-ordering if we're not connecting to a message
		if (!Lifelines.getMessageReceive(execution.getOwner(), absTop).isPresent()
				&& !Lifelines.getMessageSend(execution.getOwner(), absBottom).isPresent()) {

			result = getNudgeObstacleCommand(request, execution, request.getMoveDelta().y() < 0, absTop,
					absBottom);
		}

		if (top.isPresent() && (top.getAsInt() != absTop) && bottom.isPresent()
				&& (bottom.getAsInt() != absBottom)) {

			result = chain(result, getMoveExecutionCommand(request, execution, absTop, absBottom));
		}

		return wrap(result);
	}

	protected org.eclipse.emf.common.command.Command getMoveExecutionCommand(ChangeBoundsRequest request,
			MExecution execution, int top, int bottom) {

		org.eclipse.emf.common.command.Command result = null;

		// TODO: Handle changing lifeline
		MLifeline newOwner = execution.getOwner();

		if (isAllowSemanticReordering(request)) {
			// TODO: handle semantic reordering
		}

		result = chain(result, execution.setOwner(newOwner, OptionalInt.of(top), OptionalInt.of(bottom)));

		return result;
	}

	@Override
	protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
		Point moveDelta = request.getMoveDelta();
		Dimension sizeDelta = request.getSizeDelta();

		if ((sizeDelta != null) && ((sizeDelta.width != 0) || (sizeDelta.height != 0))) {
			// If there's a resize involved, then don't support dropping on another lifeline
			showChangeBoundsFeedback(request.getMoveDelta(), sizeDelta);
			return;
		}

		LifelineBodyEditPart thisLifeline = null;
		for (EditPart parent = getHost().getParent(); (thisLifeline == null)
				&& (parent != null); parent = parent.getParent()) {
			if (parent instanceof LifelineBodyEditPart) {
				thisLifeline = (LifelineBodyEditPart)parent;
			}
		}
		if (thisLifeline == null) {
			showChangeBoundsFeedback(moveDelta, sizeDelta);
			return;
		}

		Point pointer = request.getLocation();
		EditPart dropLifeline = ((GraphicalViewer)getHost().getViewer()).findObjectAtExcluding(pointer,
				Collections.singleton(thisLifeline.getFigure()), LifelineBodyEditPart.class::isInstance);
		if (!(dropLifeline instanceof LifelineBodyEditPart)) {
			// Not dropping on some other lifeline
			showChangeBoundsFeedback(moveDelta, sizeDelta);
			return;
		}

		// Create a border-item locator for the lifeline we're dropping on
		IFigure dropLifelineFigure = ((LifelineBodyEditPart)dropLifeline).getFigure();
		IBorderItemLocator borderItemLocator = new OnLineBorderItemLocator(dropLifelineFigure);

		// Constrain the lifeline drop to the original Y location
		moveDelta = moveDelta.getCopy();
		moveDelta.setY(0);

		showChangeBoundsFeedback(moveDelta, null, dropLifelineFigure, borderItemLocator);
	}

	protected void showChangeBoundsFeedback(Point moveDelta, Dimension sizeDelta) {
		IBorderItemEditPart borderItemEP = (IBorderItemEditPart)getHost();
		IBorderItemLocator borderItemLocator = borderItemEP.getBorderItemLocator();

		if (borderItemLocator != null) {
			showChangeBoundsFeedback(moveDelta, sizeDelta,
					((GraphicalEditPart)getHost().getParent()).getFigure(), borderItemLocator);
		}
	}

	protected void showChangeBoundsFeedback(Point moveDelta, Dimension sizeDelta, IFigure onLifeline,
			IBorderItemLocator locator) {

		if (locator != null) {
			IBorderItemEditPart borderItemEP = (IBorderItemEditPart)getHost();
			IFigure feedback = getDragSourceFeedbackFigure();
			PrecisionRectangle rect = new PrecisionRectangle(getInitialFeedbackBounds().getCopy());

			// translate to absolute to integrate the delta with scaling
			getHostFigure().translateToAbsolute(rect);
			rect.translate(moveDelta);
			if (sizeDelta != null) {
				rect.resize(sizeDelta);
			}
			// return to the relative dimensions
			getHostFigure().translateToRelative(rect);

			IFigure borderItemfigure = borderItemEP.getFigure();
			// valid location is computed on a relative base from the body figure
			rect.translate(getHostFigure().getParent().getBounds().getLocation().negate());
			Rectangle realLocation = locator.getValidLocation(rect.getCopy(), borderItemfigure);

			// feedback bounds should be in the absolute coordinates
			getHostFigure().translateToAbsolute(realLocation);

			feedback.translateToRelative(realLocation);
			feedback.setBounds(realLocation);
		}
	}

}
