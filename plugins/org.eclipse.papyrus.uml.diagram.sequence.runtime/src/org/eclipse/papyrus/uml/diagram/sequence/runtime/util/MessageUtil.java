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

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Utilities (extension methods) for working with {@link Message}s.
 *
 * @author Christian W. Damus
 */
public class MessageUtil {

	private static final Set<MessageSort> CALL_SORTS = EnumSet.of( //
			MessageSort.ASYNCH_CALL_LITERAL, MessageSort.SYNCH_CALL_LITERAL);

	/**
	 * Not instantiable by clients.
	 */
	private MessageUtil() {
		super();
	}

	/**
	 * Gets the request message that a {@code reply} message is answering.
	 * 
	 * @param reply
	 *            a reply message
	 * @return the call message received by the start of an execution specification finished by the sending of
	 *         the {@code reply}, or {@code null} otherwise
	 * @throws IllegalArgumentException
	 *             if the message is not a reply
	 */
	public static Optional<Message> getRequestMessage(Message reply) {
		if (reply.getMessageSort() != MessageSort.REPLY_LITERAL) {
			throw new IllegalArgumentException("not a reply message"); //$NON-NLS-1$
		}

		if (reply.getSendEvent() == null) {
			return Optional.empty();
		}

		Optional<ExecutionSpecification> exec = CrossReferenceUtil.invertSingle(reply.getSendEvent(),
				UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH, ExecutionSpecification.class);

		return exec.map(ExecutionSpecification::getStart).filter(MessageEnd.class::isInstance)
				.map(MessageEnd.class::cast).map(MessageEnd::getMessage)
				.filter(msg -> CALL_SORTS.contains(msg.getMessageSort()));
	}

}
