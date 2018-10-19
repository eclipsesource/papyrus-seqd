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

package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationParameters;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredAddCommand;
import org.eclipse.papyrus.uml.interaction.model.util.Optionals;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A command to create the start or finish occurrence of an execution. It is not executable if the start or
 * finish (respectively) is already an execution occurrence specification; it is valid only in the case of
 * that role being played by a message occurrence specification.
 */
public class CreateExecutionOccurrenceCommand extends ModelCommandWithDependencies.Creation<MExecutionImpl, ExecutionOccurrenceSpecification> {

	private final boolean isFinish;

	/**
	 * Initializes me.
	 *
	 * @param target
	 *            the execution in which to create the start or finish occurrence
	 * @param isFinish
	 *            whether I create the finish occurrence (otherwise the start)
	 */
	public CreateExecutionOccurrenceCommand(MExecutionImpl target, boolean isFinish) {
		super(target, ExecutionOccurrenceSpecification.class);

		this.isFinish = isFinish;
	}

	public boolean isFinish() {
		return isFinish;
	}

	@Override
	protected Command doCreateCommand() {
		Optional<MOccurrence<?>> previous = isFinish ? getTarget().getFinish() : getTarget().getStart();
		if (previous.filter(MExecutionOccurrence.class::isInstance).isPresent()) {
			return UnexecutableCommand.INSTANCE;
		}

		// Create the execution occurrence in the interaction
		CreationCommand<ExecutionOccurrenceSpecification> result;
		if (isFinish) {
			CreationParameters creationParameters = CreationParameters.after(getTarget().getElement());
			result = setResult(semanticHelper().createFinish(getTarget()::getElement, creationParameters)
					.as(ExecutionOccurrenceSpecification.class));
		} else {
			CreationParameters creationParameters = CreationParameters.before(getTarget().getElement());
			result = setResult(semanticHelper().createStart(getTarget()::getElement, creationParameters)
					.as(ExecutionOccurrenceSpecification.class));
		}

		// And cover the lifeline
		result = result.chain(new DeferredAddCommand(getTarget().getOwner().getElement(),
				UMLPackage.Literals.LIFELINE__COVERED_BY, result));

		// Dependencies: disconnect the previous occurrence specification
		Optional<MMessageEnd> end = Optionals.as(previous, MMessageEnd.class);
		Optional<Connector> messageConnector = end.map(MMessageEnd::getOwner)
				.flatMap(MMessage::getDiagramView);
		Optional<Shape> lifelineBody = getTarget().getOwner().getDiagramView()
				.map(diagramHelper()::getLifelineBodyShape);
		if (lifelineBody.isPresent()) {
			// Reconnect this message end to the lifeline body
			result = messageConnector.map(mc -> {
				MMessageEnd theEnd = end.get(); // Known to exist by connector
				int y = theEnd.getTop().orElse(0);
				return theEnd.isSend() ? diagramHelper().reconnectSource(mc, lifelineBody.get(), y)
						: diagramHelper().reconnectTarget(mc, lifelineBody.get(), y);
			}).map(result::chain).orElse(result);
		}

		return result;
	}

	static Predicate<CreateExecutionOccurrenceCommand> filterFinish() {
		return c -> c.isFinish();
	}

	static Predicate<CreateExecutionOccurrenceCommand> filterStart() {
		return filterFinish().negate();
	}
}
