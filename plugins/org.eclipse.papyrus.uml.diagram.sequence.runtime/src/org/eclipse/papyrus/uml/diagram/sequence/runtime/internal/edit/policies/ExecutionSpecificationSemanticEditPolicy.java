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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.Optionals.as;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.Optionals.mapPresent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;

/**
 * Semantic edit-policy for execution specifications, to add semantic consequences to otherwise graphical
 * edits.
 */
public class ExecutionSpecificationSemanticEditPolicy extends InteractionSemanticEditPolicy {

	/**
	 * Initializes me.
	 */
	public ExecutionSpecificationSemanticEditPolicy() {
		super();
	}

	@Override
	public Command getCommand(Request request) {
		List<Command> result = new ArrayList<>(2);
		result.add(super.getCommand(request));

		if (request instanceof ChangeBoundsRequest) {
			// Check whether we can replace a start/finish execution occurrence with
			// a message execution occurrence
			Optional<Command> replaceExecutionOccurrence = getReplaceExecutionOccurrenceCommand(
					(ChangeBoundsRequest)request);
			replaceExecutionOccurrence.ifPresent(result::add);
		}

		return result.stream().filter(Objects::nonNull).reduce(Command::chain).orElse(null);
	}

	Optional<Command> getReplaceExecutionOccurrenceCommand(ChangeBoundsRequest request) {
		// Get the resulting bounds
		Rectangle bounds = request.getTransformedRectangle(getAbsoluteExecutionBounds());
		Optional<MExecution> execution = getExecution();

		return execution.map(exec -> {
			List<ICommand> result = new ArrayList<>(2);

			Optional<MMessageEnd> startEnd = as(exec.getStart(), MExecutionOccurrence.class)
					.flatMap(occ -> findMessageEnd(occ, bounds.y()));
			Optional<MMessageEnd> finishEnd = as(exec.getFinish(), MExecutionOccurrence.class)
					.flatMap(occ -> findMessageEnd(occ, bounds.bottom()));

			startEnd.map(end -> replace(exec.getStart().get(), end)).ifPresent(result::add);
			finishEnd.map(end -> replace(exec.getFinish().get(), end)).ifPresent(result::add);
			return result.stream().filter(Objects::nonNull).reduce(ICommand::compose)
					.map(GMFtoGEFCommandWrapper::wrap).orElse(null);
		});
	}

	Rectangle getAbsoluteExecutionBounds() {
		IFigure figure = getGraphicalHost().getFigure();
		Rectangle result = figure.getBounds().getCopy();
		figure.getParent().translateToAbsolute(result);
		return result;
	}

	Optional<MExecution> getExecution() {
		EObject element = getGraphicalHost().resolveSemanticElement();
		return as(getInteraction().getElement((Element)element), MExecution.class);
	}

	Optional<MMessageEnd> findMessageEnd(MExecutionOccurrence execOccurrence, int absoluteY) {
		// Note that we only connect message receive to execution start or
		// message send to execution finish
		Function<MMessage, Optional<MMessageEnd>> endFunction = execOccurrence.getFinishedExecution()
				.isPresent() ? MMessage::getSend : MMessage::getReceive;
		Optional<MLifeline> lifeline = Optional.of(execOccurrence.getOwner());

		OptionalInt occurrenceY = OptionalInt.of(absoluteY);
		return mapPresent(lifeline.get().getInteraction().getMessages().stream().map(endFunction))
				.filter(end -> end.getCovered().equals(lifeline))
				.filter(end -> end.getTop().equals(occurrenceY)).findAny();
	}

	ICommand replace(MOccurrence<? extends Element> occurrence, MMessageEnd messageEnd) {
		return new AbstractTransactionalCommand(getGraphicalHost().getEditingDomain(),
				"Replace Execution Occurrence", null) { //$NON-NLS-1$

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {

				Optional<MExecution> startedExec = occurrence.getStartedExecution();
				Optional<MExecution> finishedExec = occurrence.getFinishedExecution();

				DestroyElementCommand.destroy(occurrence.getElement());

				// If it covers a lifeline, then it must be an occurrence specification
				startedExec.ifPresent(exec -> exec.getElement()
						.setStart((MessageOccurrenceSpecification)messageEnd.getElement()));
				finishedExec.ifPresent(exec -> exec.getElement()
						.setFinish((MessageOccurrenceSpecification)messageEnd.getElement()));

				return CommandResult.newOKCommandResult();
			}
		};
	}
}
