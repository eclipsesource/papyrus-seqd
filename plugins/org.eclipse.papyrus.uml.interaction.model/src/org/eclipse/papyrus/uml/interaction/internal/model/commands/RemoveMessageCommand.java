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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MessageEnd;

/**
 * A message removal operation.
 *
 * @author Johannes Faltermeier
 */
public class RemoveMessageCommand extends ModelCommand<MMessageImpl> implements RemovalCommand<Element> {

	private RemovalCommand<Element> delegate;

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

		switch (getTarget().getElement().getMessageSort()) {
			case DELETE_MESSAGE_LITERAL:
				/* delete cross as well */
				MessageEnd destructionEnd = getTarget().getReceiveEnd().getElement();
				Optional<Shape> destructionShape = Optional.ofNullable(getGraph().vertex(destructionEnd))//
						.map(Vertex::getDiagramView)//
						.filter(Shape.class::isInstance)//
						.map(Shape.class::cast);
				if (destructionShape.isPresent()) {
					diagramsResultCommand = diagramsResultCommand
							.chain(diagrams.deleteView(destructionShape.get()));
				}
				break;
		}

		List<Command> allCommands = new ArrayList<>();
		/* nudge */
		if (nudge) {
			/* nudge before deletion, because otherwise we would have to recreate MInteraction */
			allCommands.add(new NudgeOnRemovalCommand(getEditingDomain(),
					(MInteractionImpl)getTarget().getInteraction(), getElementsToRemove()));
		}
		allCommands.add(delegate);
		allCommands.add(diagramsResultCommand);

		/* chain */
		return CompoundModelCommand.compose(getEditingDomain(), allCommands);
	}

	@Override
	public Collection<Element> getElementsToRemove() {
		if (delegate == null) {
			return Collections.emptySet();
		}
		return delegate.getElementsToRemove();
	}

}
