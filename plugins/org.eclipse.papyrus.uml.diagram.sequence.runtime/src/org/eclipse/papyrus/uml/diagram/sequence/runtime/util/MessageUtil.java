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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
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

	public static MessageSort getSort(IElementType messageType) {
		return new SequenceTypeSwitch<MessageSort>() {
			@Override
			public MessageSort caseAsyncMessage(IHintedType type) {
				return MessageSort.ASYNCH_CALL_LITERAL;
			}

			@Override
			public MessageSort caseSyncMessage(IHintedType type) {
				return MessageSort.SYNCH_CALL_LITERAL;
			}

			@Override
			public MessageSort caseReplyMessage(IHintedType type) {
				return MessageSort.REPLY_LITERAL;
			}

			@Override
			public MessageSort caseCreateMessage(IHintedType type) {
				return MessageSort.CREATE_MESSAGE_LITERAL;
			}

			@Override
			public MessageSort caseDeleteMessage(IHintedType type) {
				return MessageSort.DELETE_MESSAGE_LITERAL;
			}

			@Override
			public MessageSort caseMessage(IElementType type) {
				// Useful default for other message kinds
				return MessageSort.ASYNCH_CALL_LITERAL;
			}
		}.doSwitch(messageType);
	}

	public static boolean isSynchronousMessage(IElementType messageType) {
		return isSynchronous(getSort(messageType));
	}

	public static boolean isSynchronous(MessageSort messageSort) {
		// Is it *not* an asynchronous message type?
		switch (messageSort) {
			case ASYNCH_CALL_LITERAL:
			case ASYNCH_SIGNAL_LITERAL:
				return false;
			default:
				return true;
		}
	}

	public static boolean isMessage(IElementType type) {
		return ElementUtil.isTypeOf(type, UMLElementTypes.MESSAGE);
	}

	public static boolean isMessageConnection(CreateConnectionRequest request) {
		return isMessageConnection(request, MessageUtil::isMessage);
	}

	public static boolean isSynchronousMessageConnection(CreateConnectionRequest request) {
		return isMessageConnection(request, MessageUtil::isSynchronousMessage);
	}

	private static boolean isMessageConnection(CreateConnectionRequest request,
			Predicate<? super IElementType> typeTest) {
		boolean result = false;

		if (request instanceof CreateUnspecifiedTypeConnectionRequest) {
			CreateUnspecifiedTypeConnectionRequest unspecified = (CreateUnspecifiedTypeConnectionRequest)request;
			result = ((List<?>)unspecified.getAllRequests()).stream()
					.filter(CreateConnectionRequest.class::isInstance)
					.map(CreateConnectionRequest.class::cast)
					.anyMatch(req -> isMessageConnection(req, typeTest));
		} else if (request instanceof CreateConnectionViewRequest) {
			CreateConnectionViewRequest specified = (CreateConnectionViewRequest)request;
			Optional<IAdaptable> elementAdapter = Optional
					.ofNullable(specified.getConnectionViewDescriptor().getElementAdapter());
			result = elementAdapter.map(a -> a.getAdapter(IElementType.class)).filter(typeTest).isPresent();
		}

		return result;
	}
}
