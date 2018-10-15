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

package org.eclipse.papyrus.uml.interaction.internal.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.ModelCommand;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A command that replaces an {@link MExecutionOccurrence} by an {@link MMessageEnd}.
 */
public class ReplaceOccurrenceCommand extends ModelCommand<MExecutionOccurrenceImpl> {

	private final MMessageEnd end;

	/**
	 * Initializes me.
	 *
	 * @param target
	 *            the execution occurrence to be replaced
	 * @param end
	 *            the message end that replaces the occurrence
	 */
	public ReplaceOccurrenceCommand(MExecutionOccurrenceImpl target, MMessageEnd end) {
		super(target);

		this.end = end;
	}

	@Override
	protected Command createCommand() {
		Optional<MExecution> started = getTarget().getStartedExecution();
		Optional<MExecution> finished = getTarget().getFinishedExecution();

		// If this occurrence starts and finishes an execution, or if it
		// neither starts nor finishes an execution, then something's awry.
		// Likewise if the message end is not an occurrence (e.g., a gate)
		if ((started.isPresent() == finished.isPresent())
				|| !(end.getElement() instanceof OccurrenceSpecification)) {
			return UnexecutableCommand.INSTANCE;
		}

		// Moreover, it does not make sense to replace an execution start by a message send
		// nor an execution finish by a message receive
		if ((getTarget().isStart() != end.isReceive()) || (getTarget().isFinish() != end.isSend())) {
			return UnexecutableCommand.INSTANCE;
		}

		// First, delete me
		Command result = getTarget().remove();

		// Then, hook up the execution semantics
		result = started
				.map(exec -> SetCommand.create(getEditingDomain(), exec.getElement(),
						UMLPackage.Literals.EXECUTION_SPECIFICATION__START, end.getElement()))
				.map(chaining(result)).orElse(result);
		result = finished
				.map(exec -> SetCommand.create(getEditingDomain(), exec.getElement(),
						UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH, end.getElement()))
				.map(chaining(result)).orElse(result);

		// And the execution visuals
		Optional<Connector> messageView = end.getOwner().getDiagramView();
		result = messageView.map(connector -> {
			List<Command> visuals = new ArrayList<>(2);
			started.flatMap(MExecution::getDiagramView)
					.map(exec -> diagramHelper().reconnectTarget(connector, exec, 0)).ifPresent(visuals::add);
			finished.flatMap(MExecution::getDiagramView)
					.map(exec -> diagramHelper().reconnectSource(connector, exec, Integer.MAX_VALUE))
					.ifPresent(visuals::add);
			return visuals.stream();
		}).orElse(Stream.empty()).reduce(chaining()) //
				.map(chaining(result)).orElse(result);

		return result;
	}
}
