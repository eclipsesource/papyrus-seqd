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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util;

import static org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil.invertSingle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.papyrus.uml.interaction.graph.GroupKind;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.graph.Visitor;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A computer of {@link MessageSequenceNumber}s based on the semantic dependency graph.
 *
 * @author Christian W. Damus
 */
class MessageSequencer implements Visitor<Vertex> {
	// Counting the next root sequence number
	private MessageSequenceNumber currentSequence = MessageSequenceNumber.EMPTY;

	private Map<ExecutionSpecification, MessageSequenceNumber> executions = new HashMap<>();

	private Map<ExecutionSpecification, Message> lastMessage = new HashMap<ExecutionSpecification, Message>();

	/**
	 * Initializes me.
	 */
	MessageSequencer() {
		super();
	}

	// TODO: Handle parallel combined fragments for parallel sequence names
	@Override
	public void visit(Vertex vertex) {
		Element element = vertex.getInteractionElement();
		if (element instanceof Message) {
			Message message = (Message)element;
			sequence(vertex, message);
		}
	}

	private void sequence(Vertex vertex, Message message) {
		final MessageSequenceNumber seq;

		if (currentSequence.isEmpty()) {
			// The first message is always 1 (with or without a name for parallelism)
			currentSequence = currentSequence.append(false);
			seq = currentSequence;
		} else {
			Optional<ExecutionSpecification> contextExec;
			switch (message.getMessageSort()) {
				case REPLY_LITERAL:
					// Find the exec that sent the request message
					Optional<Message> call = MessageUtil.getRequestMessage(message);
					contextExec = call.flatMap(msg -> findSendingOperationExec(vertex, msg));
					break;
				default:
					// Is this message sent by an execution of an operation call?
					contextExec = findSendingOperationExec(vertex, message);
					break;
			}

			seq = contextExec.map(exec -> {
				MessageSequenceNumber result;

				// Nested message
				Message previous = lastMessage.get(exec);
				if (previous == null) {
					// First nested message replying to this execution
					MessageSequenceNumber execSeq = executions.getOrDefault(exec, currentSequence);
					result = execSeq.append(false);
				} else {
					// Next nested message replying to this execution
					result = MessageSequenceNumber.get(previous).next(false);
				}
				lastMessage.put(exec, message);
				return result;
			})
					// Next root message
					.orElseGet(() -> currentSequence = currentSequence.next(false));
		}

		MessageSequenceNumber.sequence(message, seq);

		// If this is a call message that starts an execution specification, messages that it sends will be
		// based on this number
		Optional<ExecutionSpecification> started = findStartedOperationExec(message);
		started.ifPresent(exec -> executions.put(exec, seq));
	}

	private Optional<ExecutionSpecification> findSendingOperationExec(Vertex vertex, Message message) {
		Optional<MessageEnd> send = Optional.ofNullable(message.getSendEvent());
		Optional<Vertex> vSend = send.map(r -> vertex.graph().vertex(r));
		return findContainingExecution(vSend);
	}

	private Optional<ExecutionSpecification> findContainingExecution(Optional<Vertex> messageEnd) {
		return messageEnd.flatMap(v -> v.group(GroupKind.EXECUTION)).filter(Vertex.class::isInstance)
				.map(Vertex.class::cast).map(Vertex::getInteractionElement)
				.map(ExecutionSpecification.class::cast);
	}

	private Optional<ExecutionSpecification> findStartedOperationExec(Message message) {
		Optional<MessageEnd> recv = Optional.ofNullable(message.getReceiveEvent());
		return recv.flatMap(end -> invertSingle(end, UMLPackage.Literals.EXECUTION_SPECIFICATION__START,
				ExecutionSpecification.class));
	}

}
