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

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequenceResizeTracker;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DependencyContext;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
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
		// Provide a dependency context for all command construction
		return DependencyContext.getDynamic().withContext(() -> super.getCommand(request));
	}

	protected MExecution getExecution() {
		Element uml = (Element)((IGraphicalEditPart)getHost()).resolveSemanticElement();
		return (MExecution)getInteraction().getElement(uml).orElse(null);
	}

	@Override
	protected Command getSetBoundsCommand(ChangeBoundsRequest request, Node execShape, Rectangle newBounds) {
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
}
