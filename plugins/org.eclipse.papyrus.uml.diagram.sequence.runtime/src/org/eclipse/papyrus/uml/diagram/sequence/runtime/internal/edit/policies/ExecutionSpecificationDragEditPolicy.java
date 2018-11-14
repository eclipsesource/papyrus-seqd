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
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.filter;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMapToInt;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMapToObj;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.mapToInt;

import java.util.Collections;
import java.util.List;
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
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.LifelineBodyEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators.OnLineBorderItemLocator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequenceResizeTracker;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.InteractionFragment;
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
		return withPadding(() -> super.getCommand(request));
	}

	protected MExecution getExecution() {
		Element uml = (Element)((IGraphicalEditPart)getHost()).resolveSemanticElement();
		return (MExecution)getInteraction().getElement(uml).orElse(null);
	}

	@Override
	protected Command getResizeCommand(ChangeBoundsRequest request, Node execShape, Rectangle newBounds) {
		org.eclipse.emf.common.command.Command result = null;

		MExecution execution = getExecution();

		OptionalInt top = execution.getTop();
		OptionalInt bottom = execution.getBottom();

		int absTop = getLayoutHelper().toAbsoluteY(execShape, newBounds.y());
		int absBottom = getLayoutHelper().toAbsoluteY(execShape, newBounds.bottom());

		if (top.isPresent() && (top.getAsInt() != absTop)) {
			result = getMoveStartCommand(request, execution, absTop);
		}
		if (bottom.isPresent() && (bottom.getAsInt() != absBottom)) {
			result = chain(result, getMoveFinishCommand(request, execution, absBottom));
		}

		return wrap(result);
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
		org.eclipse.emf.common.command.Command result = null;

		MExecution execution = getExecution();

		// Check for semantic re-ordering
		if (!isAllowSemanticReordering(request)
				&& impliesFragmentReordering(execution, execShape, newBounds)) {
			return UnexecutableCommand.INSTANCE;
		}

		OptionalInt top = execution.getTop();
		OptionalInt bottom = execution.getBottom();

		int absTop = getLayoutHelper().toAbsoluteY(execShape, newBounds.y());
		int absBottom = getLayoutHelper().toAbsoluteY(execShape, newBounds.bottom());

		if (top.isPresent() && (top.getAsInt() != absTop) && bottom.isPresent()
				&& (bottom.getAsInt() != absBottom)) {

			result = getMoveExecutionCommand(request, execution, absTop, absBottom);
		}

		return wrap(result);
	}

	protected boolean impliesFragmentReordering(MExecution execution, Node execShape, Rectangle newBounds) {
		MInteraction interaction = execution.getInteraction();
		List<InteractionFragment> fragments = interaction.getElement().getFragments();

		// Get the element above that is being moved and could be re-ordered
		Optional<? extends MElement<? extends Element>> normalizedStart = execution.getStart();
		Optional<MMessageEnd> startEnd = as(normalizedStart, MMessageEnd.class);
		if (startEnd.filter(MMessageEnd::isReceive).isPresent()) {
			// Take the send end
			normalizedStart = startEnd.flatMap(MMessageEnd::getOtherEnd);
		}
		OptionalInt startIndex = filter(
				mapToInt(normalizedStart, start -> fragments.indexOf(start.getElement())), i -> i > 0);
		Optional<? extends MElement<? extends Element>> above = flatMapToObj(startIndex,
				i -> interaction.getElement(fragments.get(i - 1)));

		// Get the element below that is being moved and could be re-ordered
		Optional<? extends MElement<? extends Element>> normalizedFinish = execution.getFinish();
		Optional<MMessageEnd> finishEnd = as(normalizedFinish, MMessageEnd.class);
		if (finishEnd.filter(MMessageEnd::isSend).isPresent()) {
			// Take the receive end
			normalizedFinish = finishEnd.flatMap(MMessageEnd::getOtherEnd);
		}
		OptionalInt finishIndex = filter(
				mapToInt(normalizedFinish, finish -> fragments.indexOf(finish.getElement())),
				i -> i < (fragments.size() - 1));
		Optional<? extends MElement<? extends Element>> below = flatMapToObj(finishIndex,
				i -> interaction.getElement(fragments.get(i + 1)));

		int absNewTop = getLayoutHelper().toAbsoluteY(execShape, newBounds.y());
		int absNewBottom = getLayoutHelper().toAbsoluteY(execShape, newBounds.bottom());
		return filter(flatMapToInt(above, MElement::getBottom), b -> b >= absNewTop).isPresent()
				|| filter(flatMapToInt(below, MElement::getTop), t -> t <= absNewBottom).isPresent();
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
			getHostFigure().getParent().translateToAbsolute(rect);

			rect.translate(moveDelta);
			if (sizeDelta != null) {
				rect.resize(sizeDelta);
			}

			// And bring it into the lifeline's coördinate space
			Rectangle lifelineBounds = onLifeline.getBounds().getCopy();
			onLifeline.getParent().translateToAbsolute(lifelineBounds);
			rect.translate(lifelineBounds.getLocation().getNegated());

			IFigure borderItemfigure = borderItemEP.getFigure();
			Rectangle realLocation = locator.getValidLocation(rect.getCopy(), borderItemfigure);
			onLifeline.translateToAbsolute(realLocation);

			feedback.translateToRelative(realLocation);
			feedback.setBounds(realLocation);
		}
	}

}
