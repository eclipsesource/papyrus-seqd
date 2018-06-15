/*****************************************************************************
 * Copyright (c) 2018 Johannes Faltermeier and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;

/**
 * A message removal operation.
 *
 * @author Johannes Faltermeier
 */
public class RemoveMessageCommand extends ModelCommand<MMessageImpl> implements RemovalCommand {

	private RemovalCommand delegate;

	private MElement<?> trigger;

	private boolean nudge;

	/**
	 * Constructs a new {@link RemoveMessageCommand}.
	 * 
	 * @param messageToRemove
	 *            message to remove
	 * @param nudge
	 *            whether this command will nudge remaining diagram elements
	 */
	public RemoveMessageCommand(MMessageImpl messageToRemove, boolean nudge) {
		this(messageToRemove, null, nudge);
	}

	/**
	 * Constructs a new {@link RemoveMessageCommand}.
	 * 
	 * @param messageToRemove
	 *            message to remove
	 * @param trigger
	 *            the (also deleted) parent element that triggers this deletion
	 */
	public RemoveMessageCommand(MMessageImpl messageToRemove, MElement<?> trigger, boolean nudge) {
		super(messageToRemove);
		this.nudge = nudge;
		this.trigger = trigger;
		command = createCommand();
	}

	@Override
	protected Command createCommand() {
		/*
		 * deletion of a message that triggers an execution specification should delete the execution
		 * specification. vice versa the deletion of an execution specification should delete all related
		 * fragments. so in this case we will delegate the deletion to the execution specification remove
		 * command. Do not attempt to delete an element which triggered our own deletion.
		 */
		Optional<MMessageEnd> receive = getTarget().getReceive();
		if (receive.isPresent()) {
			Optional<MExecution> startedExecution = receive.get().getStartedExecution();
			if (startedExecution.isPresent() && startedExecution.get() != trigger) {
				delegate = new RemoveExecutionCommand((MExecutionImpl)startedExecution.get(), nudge);
				return delegate;
			}
		}

		/* semantics */
		SemanticHelper semantics = semanticHelper();
		delegate = semantics.deleteMessage(getTarget().getElement());

		/* diagram */
		DiagramHelper diagrams = diagramHelper();
		Command diagramsResultCommand = diagrams.deleteView(getTarget().getDiagramView().get());

		/* chain */
		return delegate.chain(diagramsResultCommand);
	}

	@Override
	public Collection<EObject> getElementsToRemove() {
		if (delegate == null) {
			return Collections.emptySet();
		}
		return delegate.getElementsToRemove();
	}

}
