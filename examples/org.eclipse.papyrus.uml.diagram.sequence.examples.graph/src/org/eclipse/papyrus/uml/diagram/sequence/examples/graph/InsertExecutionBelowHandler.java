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

package org.eclipse.papyrus.uml.diagram.sequence.examples.graph;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.spi.CreationCommandImpl;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.uml2.uml.ExecutionSpecification;

/**
 * Handler for the example "Insert Execution Below" command that uses the <em>Logical Model</em> to insert an
 * execution specification below the selected element.
 *
 * @author Christian W. Damus
 */
public class InsertExecutionBelowHandler extends AbstractSequenceDiagramHandler {

	/**
	 * Initializes me.
	 */
	public InsertExecutionBelowHandler() {
		super();
	}

	@Override
	protected Optional<Command> getCommand(MElement<?> element) {
		CreationCommand<ExecutionSpecification> first = createExecutionBelow(element);
		Command result = first;

		int count = getCount();
		if (result != null && count > 1) {
			// Insert more even below the first one
			EditingDomain domain = getEditingDomain(element.getElement());
			Supplier<ExecutionSpecification> previous = first;
			for (int i = 1; i < count; i++) {
				Supplier<ExecutionSpecification> previousExec = previous;

				CreationCommand<ExecutionSpecification> next = new CreationCommandImpl<ExecutionSpecification>(
						ExecutionSpecification.class, domain) {
					@Override
					protected Command createCommand() {
						return Optional.ofNullable(previousExec.get())
								.flatMap(InsertExecutionBelowHandler.this::getLogicalModel)
								.map(InsertExecutionBelowHandler.this::createExecutionBelow)
								.map(Command.class::cast).orElse(UnexecutableCommand.INSTANCE);
					}
				};

				result = result.chain(next);
				previous = next;
			}
		}

		return Optional.ofNullable(result);
	}

	/**
	 * Obtain the creation command that creates an execution below the given {@code element}.
	 * 
	 * @param element
	 *            the element below which to create an execution
	 * @return the execution creation command
	 */
	CreationCommand<ExecutionSpecification> createExecutionBelow(MElement<?> element) {
		SequenceDiagramSwitch<CreationCommand<ExecutionSpecification>> commandSwitch = new SequenceDiagramSwitch<CreationCommand<ExecutionSpecification>>() {
			@Override
			public CreationCommand<ExecutionSpecification> caseMMessage(MMessage message) {
				Optional<MMessageEnd> end = chooseEnd(message);

				// Insert the execution below the chosed event
				Optional<MLifeline> lifeline = end.flatMap(MMessageEnd::getCovered);

				// The end could be a gate, which isn't on a lifeline. Or just not chosen
				return lifeline.map(ll -> ll.insertExecutionAfter(end.get(), 15, 45, null)).orElse(null);
			}

			@Override
			public CreationCommand<ExecutionSpecification> caseMExecution(MExecution execution) {
				// Easy
				Optional<? extends MElement<?>> finish = execution.getFinish();
				MElement<?> before = finish.isPresent() ? finish.get() : execution;
				return execution.getOwner().insertExecutionAfter(before, 15, 45, null);
			}
		};
		return commandSwitch.doSwitch(element);
	}

	/**
	 * Get the number of executions requested in the command invocation.
	 * 
	 * @return the number of executions to create, or {@code 1} if the parameter was not specified
	 */
	int getCount() {
		return Integer.parseInt(getParameter("count", "1")); //$NON-NLS-1$//$NON-NLS-2$
	}

	Optional<MMessageEnd> chooseEnd(MMessage message) {
		Predicate<MMessageEnd> coversLifeline = end -> end.getCovered().isPresent();
		Optional<MMessageEnd> send = message.getSend().filter(coversLifeline);
		Optional<MMessageEnd> recv = message.getReceive().filter(coversLifeline);
		Optional<MMessageEnd> result;

		if (!recv.isPresent()) {
			result = send;
		} else if (!send.isPresent()) {
			result = recv;
		} else {
			// Ask for which end lifeline
			ILabelProvider labels = new LabelProvider() {
				@Override
				public String getText(Object object) {
					MMessageEnd end = (MMessageEnd)object;
					return (end.isSend()) ? "Below Send Event" : "Below Receive Event";
				}
			};
			PopupMenu menu = new PopupMenu(Arrays.asList(send.get(), recv.get()), labels);
			result = menu.show(getControl()) ? Optional.ofNullable((MMessageEnd)menu.getResult())
					: Optional.empty();
		}

		return result;
	}
}
