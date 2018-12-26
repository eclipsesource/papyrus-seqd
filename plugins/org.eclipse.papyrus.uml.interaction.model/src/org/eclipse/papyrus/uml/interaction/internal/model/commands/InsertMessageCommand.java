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

import static java.lang.Math.abs;
import static org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil.invertSingle;
import static org.eclipse.papyrus.uml.interaction.graph.util.Suppliers.compose;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.equalTo;
import static org.eclipse.uml2.uml.MessageSort.REPLY_LITERAL;
import static org.eclipse.uml2.uml.UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION;
import static org.eclipse.uml2.uml.UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH;
import static org.eclipse.uml2.uml.UMLPackage.Literals.EXECUTION_SPECIFICATION__START;
import static org.eclipse.uml2.uml.UMLPackage.Literals.INTERACTION__FRAGMENT;
import static org.eclipse.uml2.uml.UMLPackage.Literals.INTERACTION__MESSAGE;
import static org.eclipse.uml2.uml.UMLPackage.Literals.LIFELINE__COVERED_BY;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DeferredPaddingCommand.PaddingDirection;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MObjectImpl;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationParameters;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredAddCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredSetCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.ExecutionCreationCommandParameter;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.papyrus.uml.interaction.model.util.Executions;
import org.eclipse.papyrus.uml.interaction.model.util.Optionals;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Signal;

/**
 * A message creation operation.
 *
 * @author Christian W. Damus
 */
public class InsertMessageCommand extends ModelCommandWithDependencies.Creation<MLifelineImpl, Message> {

	private final MElement<?> beforeSend;

	private final int sendOffset;

	private final MLifeline receiver;

	private final MElement<?> beforeRecv;

	private final int recvOffset;

	private final MessageSort sort;

	private final NamedElement signature;

	private final boolean syncMessage;

	private ExecutionCreationCommandParameter executionCreationParameter;

	private OptionalInt sendYReference;

	private OptionalInt receiveYReference;

	/**
	 * Initializes me.
	 * 
	 * @param sender
	 *            the lifeline from which to create the message
	 * @param before
	 *            the element in the sequence after which to create the message send event
	 * @param offset
	 *            the offset from the reference element at which to create message send event
	 * @param receiver
	 *            the lifeline to receive the message
	 * @param sort
	 *            the message sort
	 * @param signature
	 *            the message signature, if any
	 */
	public InsertMessageCommand(MLifelineImpl sender, MElement<?> before, int offset, MLifeline receiver,
			MessageSort sort, NamedElement signature) {

		// This message is to be drawn horizontal, so anchor it on the receiver at an appropriate
		// offset according to the absolute vertical position of the sending end
		this(sender, before, offset, receiver, receiver, receiverOffset(before, offset, receiver), sort,
				signature);
	}

	/**
	 * Initializes me.
	 * 
	 * @param sender
	 *            the lifeline from which to create the message
	 * @param before
	 *            the element in the sequence after which to create the message send event
	 * @param offset
	 *            the offset from the reference element at which to create message send event
	 * @param receiver
	 *            the lifeline to receive the message
	 * @param sort
	 *            the message sort
	 * @param signature
	 *            the message signature, if any
	 * @param executionCreationConfig
	 *            the configuration for creating executions for the sync message to be created
	 */
	public InsertMessageCommand(MLifelineImpl sender, MElement<?> before, int offset, MLifeline receiver,
			MessageSort sort, NamedElement signature,
			ExecutionCreationCommandParameter executionCreationConfig) {

		this(sender, before, offset, receiver, receiver, receiverOffset(before, offset, receiver), sort,
				signature, executionCreationConfig);
	}

	/**
	 * Initializes me.
	 * 
	 * @param sender
	 *            the lifeline from which to create the message
	 * @param beforeSend
	 *            the element in the sequence after which to create the message send event
	 * @param sendOffset
	 *            the offset from the reference element at which to create message send event
	 * @param receiver
	 *            the lifeline to receive the message
	 * @param beforeRecv
	 *            the element in the sequence after which to create the message receive event
	 * @param recvOffset
	 *            the offset on the {@code receiver} lifeline of the message receive end. If the message
	 *            {@code sort} is not asynchronous, then this must be at the same absolute Y coördinate as the
	 *            sender {@code offset}
	 * @param sort
	 *            the message sort
	 * @param signature
	 *            the message signature, if any
	 */
	public InsertMessageCommand(MLifelineImpl sender, MElement<?> beforeSend, int sendOffset,
			MLifeline receiver, MElement<?> beforeRecv, int recvOffset, MessageSort sort,
			NamedElement signature) {

		this(sender, beforeSend, sendOffset, receiver, beforeRecv, recvOffset, sort, signature,
				new ExecutionCreationCommandParameter());
	}

	public InsertMessageCommand(MLifelineImpl sender, MElement<?> beforeSend, int sendOffset,
			MLifeline receiver, MElement<?> beforeRecv, int recvOffset, MessageSort sort,
			NamedElement signature, ExecutionCreationCommandParameter executionCreationCommandParameter) {

		super(sender, Message.class);

		checkInteraction(beforeSend);
		checkInteraction(beforeRecv);
		if (signature != null && !(signature instanceof Operation) && !(signature instanceof Signal)) {
			throw new IllegalArgumentException("signature is neither operation nor signal"); //$NON-NLS-1$
		}
		switch (sort) {
			case ASYNCH_CALL_LITERAL:
			case ASYNCH_SIGNAL_LITERAL:
				syncMessage = false;
				break;
			default:
				syncMessage = true;
				break;
		}

		this.beforeSend = beforeSend;
		this.sendOffset = sendOffset;
		this.receiver = receiver;
		this.beforeRecv = beforeRecv;
		this.recvOffset = recvOffset;
		this.sort = sort;
		this.signature = signature;
		this.executionCreationParameter = executionCreationCommandParameter;
	}

	@Override
	public String getLabel() {
		if (executionCreationParameter.isCreateExecution()) {
			return MessageFormat.format(LogicalModelPlugin.INSTANCE.getString("CreateMessageWithExecution"), //$NON-NLS-1$
					messageSortLabel(), executionTypeLabel());
		} else {
			return MessageFormat.format(LogicalModelPlugin.INSTANCE.getString("Create"), messageSortLabel()); //$NON-NLS-1$
		}
	}

	private String messageSortLabel() {
		switch (sort) {
			case ASYNCH_SIGNAL_LITERAL:
				return LogicalModelPlugin.INSTANCE.getString("SyncSignal"); //$NON-NLS-1$
			case SYNCH_CALL_LITERAL:
				return LogicalModelPlugin.INSTANCE.getString("SyncCall"); //$NON-NLS-1$
			case CREATE_MESSAGE_LITERAL:
				return LogicalModelPlugin.INSTANCE.getString("CreateMessage"); //$NON-NLS-1$
			case DELETE_MESSAGE_LITERAL:
				return LogicalModelPlugin.INSTANCE.getString("DeleteMessage"); //$NON-NLS-1$
			case REPLY_LITERAL:
				return LogicalModelPlugin.INSTANCE.getString("ReplyMessage"); //$NON-NLS-1$
			case ASYNCH_CALL_LITERAL:
			default:
				return LogicalModelPlugin.INSTANCE.getString("AsyncCall"); //$NON-NLS-1$
		}
	}

	private Object executionTypeLabel() {
		if (executionCreationParameter.getExecutionType().equals(ACTION_EXECUTION_SPECIFICATION)) {
			return LogicalModelPlugin.INSTANCE.getString("ActionExecutionSpecification"); //$NON-NLS-1$
		} else {
			return LogicalModelPlugin.INSTANCE.getString("BehaviorExecutionSpecification"); //$NON-NLS-1$
		}
	}

	/**
	 * Obtain my execution creation parameter.
	 * 
	 * @return my execution creation parameter
	 */
	public ExecutionCreationCommandParameter getExecutionCreationParameter() {
		return executionCreationParameter;
	}

	/**
	 * Obtain the receiver of the message that I create.
	 * 
	 * @return my receiver
	 */
	public MLifeline getReceiver() {
		return receiver;
	}

	/**
	 * Compute the offset on the receiver of horizontal message based on the sending {@code offset} from its
	 * reference point.
	 * 
	 * @param beforeSend
	 *            the sending reference point
	 * @param offset
	 *            the sending offset from the reference point
	 * @param receiver
	 *            the receiving lifeline
	 * @return the offset on the {@code receiver} of the receive end of the horizontal message
	 */
	private static int receiverOffset(MElement<?> beforeSend, int offset, MLifeline receiver) {
		LayoutHelper layout = LogicalModelPlugin.getInstance()
				.getLayoutHelper(((MObjectImpl<?>)beforeSend).getEditingDomain());
		int absoluteY;
		if (beforeSend instanceof MLifeline) {
			MLifeline sender = (MLifeline)beforeSend;
			int llTop = layout.getBottom(sender.getDiagramView().get());
			absoluteY = llTop + offset;
		} else {
			// For executions also the top to allow for messages to anchor within it
			absoluteY = beforeSend.getTop().orElse(0) + offset;
		}

		int llTop = layout.getBottom(receiver.getDiagramView().get());
		return absoluteY - llTop;
	}

	@Override
	protected Command doCreateCommand() {
		if (!absoluteSendYReference().isPresent() || !absoluteReceiveYReference().isPresent()) {
			return bomb();
		}
		int absoluteSendY = absoluteSendYReference().getAsInt();
		int absoluteRecvY = absoluteReceiveYReference().getAsInt();

		Optional<MExecution> sendingExec = Executions.executionAt(getTarget(), absoluteSendY);
		Vertex sender = sendingExec.map(this::vertex).orElseGet(this::vertex);
		if (sender == null || sender.getDiagramView() == null) {
			return bomb();
		}

		// Receive: Is there actually an execution occurrence here?
		// In case of create or destruction messages, this should not connect to execution occurrences,
		// because they connect to lifeline-header/new destruction occurrence
		Optional<MExecution> receivingExec;
		switch (sort) {
			case CREATE_MESSAGE_LITERAL:
			case DELETE_MESSAGE_LITERAL:
				receivingExec = Optional.empty();
				break;
			default:
				receivingExec = Executions.executionAt(receiver, absoluteRecvY);
				break;
		}
		@SuppressWarnings("hiding")
		Vertex receiver = receivingExec.map(this::vertex).orElseGet(() -> vertex(this.receiver));
		if (receiver == null || receiver.getDiagramView() == null) {
			return bomb();
		}

		switch (sort) {
			case CREATE_MESSAGE_LITERAL:
				/* self-creation makes no sense */
				if (isSelfMessage()) {
					return bomb();
				}

				/* receiver must have no elements before */
				if (this.receiver.elementAt(recvOffset + relativeTopOfBefore()).isPresent()) {
					return bomb();
				}
				break;
			case DELETE_MESSAGE_LITERAL:
				/*
				 * receiver must have no element after. Use the sending location because deletes are
				 * synchronous (horizontal) except in the case of self-destruct, in which case the recv
				 * visually is later but semantically is at the send
				 */
				int absoluteDeleteY = absoluteSendY;
				List<MElement<? extends Element>> elementsBelow = new ArrayList<>();
				findElementsBelow(absoluteDeleteY, elementsBelow, this.receiver.getOccurrences().stream(),
						false);
				if (!elementsBelow.isEmpty()) {
					return bomb();
				}
				break;
			default:
				break;
		}

		// Determine the semantic elements after which to insert each message end
		List<MElement<? extends Element>> timeline = getTimeline(getTarget().getInteraction());
		Optional<MElement<? extends Element>> sendInsert = getInsertionPoint(timeline, MMessageEnd.class,
				absoluteSendY);
		Optional<MElement<? extends Element>> recvInsert = getInsertionPoint(timeline, MMessageEnd.class,
				absoluteRecvY);

		SemanticHelper semantics = semanticHelper();
		CreationParameters sendParams = endParams(
				() -> sendInsert.map(MElement::getElement).map(Element.class::cast).orElse(null));
		CreationCommand<MessageEnd> sendEvent = semantics.createMessageOccurrence(sendParams);
		CreationParameters recvParams = syncMessage ? CreationParameters.after(sendEvent)
				// If the insertion point is the same or unspecified, then follow the send event
				: !recvInsert.isPresent() || recvInsert.equals(sendInsert)
						? CreationParameters.after(sendEvent)
						// Otherwise, it is specific
						: endParams(() -> recvInsert.map(MElement::getElement).map(Element.class::cast)
								.orElse(null));
		CreationCommand<MessageEnd> recvEvent;
		switch (sort) {
			case DELETE_MESSAGE_LITERAL:
				/* receive event should be destruction occurrence */
				recvEvent = semantics.createDestructionOccurrence(recvParams);
				break;
			default:
				recvEvent = semantics.createMessageOccurrence(recvParams);
				break;
		}
		CreationParameters messageParams = CreationParameters.in(interaction(), INTERACTION__MESSAGE);
		CreationCommand<Message> resultCommand = setResult(
				semantics.createMessage(sendEvent, recvEvent, sort, signature, messageParams));

		// Create these elements in the interaction
		Command result = sendEvent.chain(recvEvent).chain(resultCommand);

		// Cover the appropriate lifelines
		result = result
				.chain(new DeferredAddCommand(getTarget().getElement(), LIFELINE__COVERED_BY, sendEvent));
		result = result
				.chain(new DeferredAddCommand(this.receiver.getElement(), LIFELINE__COVERED_BY, recvEvent));

		// And the diagram visualization
		Supplier<View> senderView = sender::getDiagramView;
		if (!sendingExec.isPresent()) {
			senderView = compose(senderView, diagramHelper()::getLifelineBodyShape);
		}
		Supplier<? extends View> receiverView;
		switch (sort) {
			case CREATE_MESSAGE_LITERAL:
				/* creation message should connect to lifeline header */
				receiverView = receiver::getDiagramView;
				break;

			case DELETE_MESSAGE_LITERAL:
				int destructionOffset = absoluteRecvY;

				/*
				 * a self-destruct message requires the gap for bending around and, because it's a synchronous
				 * message, has fixed size
				 */
				if (isSelfMessage()) {
					destructionOffset = absoluteSendY
							/* this is the minimum gap in self-messages */
							+ layoutConstraints().getMinimumHeight(ViewTypes.MESSAGE)
							+ (layoutConstraints().getMinimumHeight(ViewTypes.DESTRUCTION_SPECIFICATION) / 2);
				}

				/* create destruction occurrence */
				CreationCommand<Shape> destructionOccurrenceShape = diagramHelper()
						.createDestructionOccurrenceShape(recvEvent,
								diagramHelper().getLifelineBodyShape(receiver.getDiagramView()),
								destructionOffset);
				result = result.chain(destructionOccurrenceShape);
				receiverView = destructionOccurrenceShape;
				break;

			default:
				receiverView = receiver::getDiagramView;
				if (!receivingExec.isPresent()) {
					receiverView = compose(receiverView, diagramHelper()::getLifelineBodyShape);
				}
				break;
		}

		/* Make sure we keep enough distance to the elements before us */
		int additionalOffset = getAdditionalOffSet(timeline, //
				sendInsert, absoluteSendY, //
				recvInsert, absoluteRecvY);

		/*
		 * check if we can actually change the insertion offset or if we have to nudge the executions we are
		 * connecting to instead
		 */
		Optional<MElement<?>> nudgeToEnforcePadding = Optional.empty();
		int distanceToEnforcePadding = 0;
		if (additionalOffset > 0) {
			Set<MElement<?>> elementToNudge = new LinkedHashSet<>();
			validateOffsetChange(sendingExec, absoluteSendY, additionalOffset, elementToNudge);
			validateOffsetChange(receivingExec, absoluteRecvY, additionalOffset, elementToNudge);
			if (!elementToNudge.isEmpty()) {
				/* we need to to nudge instead of changing the insertion offset */
				distanceToEnforcePadding = additionalOffset;
				additionalOffset = 0;
				nudgeToEnforcePadding = timeline.stream()//
						.filter(elementToNudge::contains)//
						.findFirst();
			}
		}

		/* create message */
		int additionalOffsetSend = additionalOffset
				+ (sendingExec.isPresent() ? 0 : distanceToEnforcePadding);
		int additionalOffsetRecv = additionalOffset
				+ (receivingExec.isPresent() ? 0 : distanceToEnforcePadding);
		IntSupplier senderY = () -> absoluteSendY + additionalOffsetSend;
		IntSupplier receiverY = () -> absoluteRecvY + additionalOffsetRecv;
		CreationCommand<Connector> messageConnector = diagramHelper().createMessageConnector(resultCommand,
				senderView, senderY, receiverView, receiverY, this::replace);
		result = result.chain(messageConnector);

		int sendYPosition = absoluteSendY + additionalOffsetSend;
		int recvYPosition = absoluteRecvY + additionalOffsetRecv;

		if (isSelfMessage()) {
			/* Make additional room for the self message */
			recvYPosition = Math.max(recvYPosition,
					sendYPosition + layoutConstraints().getMinimumHeight(ViewTypes.MESSAGE));
		}

		switch (sort) {
			case CREATE_MESSAGE_LITERAL:
				// Chain in the reverse order to ensure that the message connector finds the
				// new lifeline head location
				result = chain(this.receiver.makeCreatedAt(OptionalInt.of(recvYPosition)), result);
				break;

			case DELETE_MESSAGE_LITERAL:
				break;

			case SYNCH_CALL_LITERAL:
				/* if there is no execution yet, create one for sync calls */
				if (!receivingExec.isPresent() && executionCreationParameter.isCreateExecution()) {
					result = result.chain(createAdditionalSyncMessageCommands(messageConnector, sendEvent,
							recvEvent, senderY, receiverY));
					/* Fall through to create nudge command, but extend nudge height by execMinHeight */
					recvYPosition += layoutConstraints().getMinimumHeight(ViewTypes.EXECUTION_SPECIFICATION);

					if (isSelfMessage() && executionCreationParameter.isCreateReply()) {
						/* Make additional room for the reply, too */
						recvYPosition += layoutConstraints().getMinimumHeight(ViewTypes.MESSAGE);
					}
				}

			default:
				// Now we have commands to add the message specification. But, first we must make
				// room for it in the diagram. Nudge the element that will follow the new receive event
				// or send event, whichever needs the more padding.
				// If inserting after the start occurrence of an execution specification,
				// then actually insert after the execution, itself, so that it can span
				// the new message end.
				ensurePadding(timeline, sendYPosition, absoluteSendY, recvYPosition, absoluteRecvY,
						additionalOffsetRecv);
				break;
		}

		if (nudgeToEnforcePadding.isPresent()) {
			result = result.chain(nudgeToEnforcePadding.get().nudge(distanceToEnforcePadding));
		}

		return result;
	}

	private OptionalInt absoluteSendYReference() {
		OptionalInt yReference = getSendYReference();
		if (!yReference.isPresent()) {
			return OptionalInt.empty();
		}
		return OptionalInt.of(yReference.getAsInt() + sendOffset);
	}

	private OptionalInt absoluteReceiveYReference() {
		OptionalInt yReference = getReceiveYReference();
		if (!yReference.isPresent()) {
			return OptionalInt.empty();
		}
		return OptionalInt.of(yReference.getAsInt() + recvOffset);
	}

	private OptionalInt getSendYReference() {
		if (sendYReference == null) {
			Vertex sendReference = vertex(beforeSend);
			if (sendReference == null) {
				sendYReference = OptionalInt.empty();
			} else {
				sendYReference = beforeSend == getTarget() ? layoutHelper().getBottom(sendReference)
						// Reference from the top of an execution specification to allow
						// connections within its extent
						: layoutHelper().getTop(vertex(beforeSend));
			}
		}
		return sendYReference;
	}

	private OptionalInt getReceiveYReference() {
		if (receiveYReference == null) {
			Vertex receiveReference = vertex(beforeRecv);
			if (receiveReference == null) {
				receiveYReference = OptionalInt.empty();
			} else {
				receiveYReference = beforeRecv == this.receiver ? layoutHelper().getBottom(receiveReference)
						// Reference from the top of an execution specification to allow
						// connections within its extent
						: layoutHelper().getTop(vertex(beforeRecv));
			}
		}
		return receiveYReference;
	}

	private boolean isSelfMessage() {
		return receiver.getElement() == getTarget().getElement();
	}

	private Command createAdditionalSyncMessageCommands(Supplier<Connector> messageConnector,
			Supplier<MessageEnd> sendEvent, CreationCommand<MessageEnd> receiveEvent, IntSupplier senderY,
			IntSupplier receiverY) {

		/* correct why positions if this is a self message */
		IntSupplier actualReceiverY;
		IntSupplier actualSenderY;
		if (isSelfMessage()) {
			actualReceiverY = () -> senderY.getAsInt()
					+ layoutConstraints().getMinimumHeight(ViewTypes.MESSAGE);
			actualSenderY = () -> senderY.getAsInt();
		} else {
			actualReceiverY = receiverY;
			actualSenderY = senderY;
		}

		/* for sync calls, we want to create an execution specification */
		List<Command> additionalCommands = new ArrayList<>();
		CreationCommand<ExecutionSpecification> execution;
		CreationParameters execParams = CreationParameters.in(interaction(), INTERACTION__FRAGMENT);
		execParams.setEClass(executionCreationParameter.getExecutionType());
		execParams.setInsertAfter(receiveEvent);
		execution = semanticHelper().createExecutionSpecification(null, execParams);
		Command setStart = new DeferredSetCommand(getEditingDomain(), execution,
				EXECUTION_SPECIFICATION__START, receiveEvent);
		additionalCommands.add(execution);
		additionalCommands.add(setStart);

		/* create the shape of the execution specification */
		CreationCommand<Shape> executionShape;
		View recShape = receiver.getDiagramView().orElse(null);
		int execMinHeight = layoutConstraints().getMinimumHeight(ViewTypes.EXECUTION_SPECIFICATION);
		Shape receiverBody = diagramHelper().getLifelineBodyShape(recShape);
		executionShape = diagramHelper().createExecutionShape(execution, receiverBody, actualReceiverY,
				execMinHeight);
		additionalCommands.add(executionShape);

		if (executionCreationParameter.isCreateReply()) {
			additionalCommands.addAll(createAutomaticReplyMessageCommands(sendEvent, execution,
					executionShape, () -> actualReceiverY.getAsInt() + execMinHeight,
					isSelfMessage()
							? () -> actualReceiverY.getAsInt() + execMinHeight
									+ layoutConstraints().getMinimumHeight(ViewTypes.MESSAGE)
							: () -> actualReceiverY.getAsInt() + execMinHeight));
		} else {
			CreationCommand<OccurrenceSpecification> finish = semanticHelper().createFinish(execution,
					CreationParameters.after(execution));
			DeferredAddCommand addFinish = new DeferredAddCommand(receiver.getElement(), LIFELINE__COVERED_BY,
					execution, finish);
			additionalCommands.add(finish);
			additionalCommands.add(addFinish);
		}

		/* and attach the request message to it */
		additionalCommands.add(diagramHelper().reconnectTarget(messageConnector, executionShape, () -> 0));

		return CompoundModelCommand.compose(getEditingDomain(), additionalCommands);
	}

	private List<Command> createAutomaticReplyMessageCommands(Supplier<MessageEnd> sendEvent,
			Supplier<ExecutionSpecification> execution, Supplier<Shape> executionShape, IntSupplier senderY,
			IntSupplier receiverY) {

		List<Command> commands = new ArrayList<>();
		CreationCommand<MessageEnd> replySend = semanticHelper()
				.createMessageOccurrence(CreationParameters.after(execution));
		DeferredAddCommand addReplySend = new DeferredAddCommand(receiver.getElement(), LIFELINE__COVERED_BY,
				execution, replySend);
		DeferredSetCommand setFinish = new DeferredSetCommand(getEditingDomain(), execution,
				EXECUTION_SPECIFICATION__FINISH, replySend);
		commands.add(replySend);
		commands.add(addReplySend);
		commands.add(setFinish);

		CreationCommand<MessageEnd> replyReceive = semanticHelper()
				.createMessageOccurrence(CreationParameters.after(replySend));
		DeferredAddCommand addReplyReceive = new DeferredAddCommand(getTarget().getElement(),
				LIFELINE__COVERED_BY, replyReceive);
		commands.add(replyReceive);
		commands.add(addReplyReceive);

		CreationParameters messageParams = CreationParameters.after(() -> sendEvent.get().getMessage());
		CreationCommand<Message> replyMessage = semanticHelper().createMessage(replySend, replyReceive,
				REPLY_LITERAL, null, messageParams);
		commands.add(replyMessage);

		Shape receiverShape = diagramHelper().getLifelineBodyShape(getTarget().getDiagramView().get());
		int execMinHeight = layoutConstraints().getMinimumHeight(ViewTypes.EXECUTION_SPECIFICATION);

		Command replyMessageView = diagramHelper().createMessageConnector(replyMessage, executionShape,
				() -> senderY.getAsInt(), () -> receiverShape, () -> receiverY.getAsInt(), null);
		commands.add(replyMessageView);

		return commands;
	}

	protected LayoutConstraints layoutConstraints() {
		return layoutHelper().getConstraints();
	}

	private void validateOffsetChange(Optional<MExecution> exec, int absoluteY, int additionalOffset,
			Set<MElement<?>> elementToNudge) {
		if (exec.isPresent()) {
			OptionalInt top = exec.get().getTop();
			if (top.isPresent() && (absoluteY == top.getAsInt())) {
				// we are connecting to the top of the execution
				// -> nudge start instead
				exec.get().getStart().ifPresent(elementToNudge::add);
			}

			// check if we have place on the execution
			OptionalInt bottom = exec.get().getBottom();
			if (bottom.isPresent() && ((bottom.getAsInt() - absoluteY - additionalOffset) < 0)) {
				// we would move over the end of the execution
				// -> nudge finish instead
				exec.get().getFinish().ifPresent(elementToNudge::add);
			}
		}
	}

	private void ensurePadding(List<MElement<? extends Element>> timeline, //
			int sendYPosition, int absoluteSendY, //
			int recvYPosition, int absoluteRecvY, //
			int additionalOffset) {

		Predicate<MOccurrence<?>> isExecStart = MOccurrence::isStart;
		Predicate<MOccurrence<?>> isExecFinish = MOccurrence::isFinish;
		Optional<? extends MElement<? extends Element>> recvNudge = getFollowingElement(timeline,
				getLatestElementBeforeY(beforeRecv, absoluteRecvY, timeline), absoluteRecvY);
		@SuppressWarnings("unchecked")
		Optional<MExecution> nudgedStarted = Optionals.as(recvNudge, MOccurrence.class)
				.flatMap(MOccurrence::getExecution);
		Optional<? extends MElement<? extends Element>> sendNudge = getFollowingElement(timeline,
				getLatestElementBeforeY(beforeSend, absoluteSendY, timeline), absoluteSendY);
		@SuppressWarnings("unchecked")
		Optional<MExecution> nudgedFinished = Optionals.as(sendNudge, MOccurrence.class)
				.flatMap(MOccurrence::getExecution);

		// The vertical extent of the message
		int messageHeight = abs(recvYPosition - sendYPosition) + additionalOffset;

		Predicate<MOccurrence<?>> startsNudged = isExecStart.and(
				occ -> occ.getStartedExecution().filter(equalTo(nudgedStarted.orElse(null))).isPresent());
		Predicate<MOccurrence<?>> finishesNudged = isExecFinish.and(
				occ -> occ.getFinishedExecution().filter(equalTo(nudgedFinished.orElse(null))).isPresent());

		// But, don't attempt any padding of an execution start/finish that we are connecting to
		Supplier<MMessageEnd> recvEnd = () -> getTarget().getInteraction().getMessage(this.get())
				.flatMap(MMessage::getReceive).filter(startsNudged.negate()).orElse(null);
		Supplier<MMessageEnd> sendEnd = () -> getTarget().getInteraction().getMessage(this.get())
				.flatMap(MMessage::getSend).filter(finishesNudged.negate()).orElse(null);

		// Only one of these will actually be effective (or neither, if something else overrides)
		// but we don't yet know which, so post both
		sendNudge.ifPresent(toNudge -> DeferredPaddingCommand.get(getTarget()).pad(sendEnd, () -> toNudge,
				messageHeight, PaddingDirection.DOWN));
		recvNudge.ifPresent(toNudge -> DeferredPaddingCommand.get(getTarget()).pad(recvEnd, () -> toNudge,
				messageHeight, PaddingDirection.DOWN));
	}

	/**
	 * Returns the latest element (wrt <code>timeline</code>) starting from <code>referenceElement</code> that
	 * is still graphically before the specified <code>yPosition</code>.
	 */
	protected MElement<?> getLatestElementBeforeY(MElement<?> referenceElement, int yPosition,
			List<MElement<? extends Element>> timeline) {
		/* timeline after element before send or entire list, if element before send isn't included */
		List<MElement<?>> elementsLaterThanBeforeSend = timeline
				.subList(timeline.indexOf(referenceElement) + 1, timeline.size());
		return elementsLaterThanBeforeSend.stream() //
				.filter(isGraphicallyBefore(yPosition)) //
				.reduce(last(timeline)) //
				.orElse(referenceElement);
	}

	protected Predicate<? super MElement<?>> isGraphicallyBefore(int yPosition) {
		return isGraphicallyBefore(OptionalInt.of(yPosition));
	}

	protected Predicate<? super MElement<?>> isGraphicallyBefore(OptionalInt yPosition) {
		return e -> e.getBottom().orElse(Integer.MAX_VALUE) < yPosition.orElse(Integer.MAX_VALUE);
	}

	protected BinaryOperator<MElement<? extends Element>> last(List<MElement<? extends Element>> timeline) {
		return (e1, e2) -> timeline.indexOf(e1) > timeline.indexOf(e1) ? e1 : e2;
	}

	private Optional<? extends MElement<? extends Element>> getFollowingElement(
			List<MElement<? extends Element>> fragments, MElement<?> distanceFrom, int yPosition) {

		Optional<? extends MElement<? extends Element>> result;

		int index = fragments.indexOf(distanceFrom);
		if (index == -1) {
			if (distanceFrom instanceof MLifeline) {
				// get the first element which comes after
				result = fragments.stream()//
						.filter(e -> {
							return e.getTop().orElse(yPosition) >= yPosition;
						})//
						.findFirst();
			} else {
				result = getTarget().following(distanceFrom);
			}
		} else if (index + 1 < fragments.size()) {
			result = Optional.of(fragments.get(index + 1));
		} else {
			result = getTarget().following(distanceFrom);
		}

		Optional<MOccurrence<? extends Element>> execFinish = Optionals.as(result, MExecution.class)
				.filter(above(yPosition).and(below(yPosition))).flatMap(MExecution::getFinish);
		result = Optionals.elseMaybe(execFinish, result);

		return result;
	}

	private int getAdditionalOffSet(List<MElement<? extends Element>> timeline, //
			Optional<MElement<? extends Element>> elementAfterSend, int absoluteSendY, //
			Optional<MElement<? extends Element>> elementAfterRecv, int absoluteRecvY) {
		int additionalOffsetSend = getAdditionalOffset(timeline, elementAfterSend, absoluteSendY, beforeSend);
		int additionalOffsetReceive = getAdditionalOffset(timeline, elementAfterRecv, absoluteRecvY,
				beforeRecv);
		return Math.max(additionalOffsetSend, additionalOffsetReceive);
	}

	private int getAdditionalOffset(List<MElement<? extends Element>> timeline,
			Optional<MElement<? extends Element>> elementAfterMe, int absoluteY, MElement<?> before) {
		MElement<? extends Element> elementBeforeMe = getElementBeforeMe(timeline, elementAfterMe, before);
		Optional<View> diagramView = getDiagramView(elementBeforeMe);
		if (!diagramView.isPresent()) {
			return 0;
		}
		int curPadding = absoluteY - elementBeforeMe.getBottom().orElse(0);
		int reqPadding = layoutConstraints().getPadding(RelativePosition.BOTTOM, diagramView.get())
				+ layoutConstraints().getPadding(RelativePosition.TOP, ViewTypes.MESSAGE);
		if (curPadding < reqPadding) {
			return reqPadding - curPadding;
		} else {
			return 0;
		}
	}

	/**
	 * Returns the element before me based on the element after me.
	 */
	private MElement<? extends Element> getElementBeforeMe(List<MElement<? extends Element>> timeline,
			Optional<MElement<? extends Element>> elementAfterMe, MElement<?> before) {

		MElement<? extends Element> fragmentBeforeMe = getFragmentBeforeMe(timeline, elementAfterMe, before);

		/* for create messages we want to use the lifeline as a reference element */
		if (fragmentBeforeMe instanceof MMessageEnd) {
			MMessage message = MMessageEnd.class.cast(fragmentBeforeMe).getOwner();
			if (message.getElement().getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL) {
				Optional<MLifeline> createdLifeline = message.getReceiver();
				if (createdLifeline.isPresent()) {
					return createdLifeline.get();
				}
			}
		}

		return fragmentBeforeMe;
	}

	private MElement<? extends Element> getFragmentBeforeMe(List<MElement<? extends Element>> timeline,
			Optional<MElement<? extends Element>> elementAfterMe, MElement<?> before) {

		if (!elementAfterMe.isPresent()) {
			if (before instanceof MLifeline) {
				Optional<MElement<? extends Element>> after = getLastElementOnThisLifeline(timeline, before);
				return after.orElse(before);
			}
			return before;
		}

		/* get the element based on timeline */
		MElement<? extends Element> insertionPoint = elementAfterMe.get();
		int index = timeline.indexOf(insertionPoint) - 1;
		if (index >= 0 && index < timeline.size()) {
			/* there is an earlier element in the timline */
			MElement<? extends Element> element = timeline.get(index);
			if (element instanceof MExecution) {
				Optional<MOccurrence<?>> start = MExecution.class.cast(element).getStart();
				if (start.isPresent()) {
					return start.get();
				}
			}
			return element;
		} else if (index == -2 && before instanceof MLifeline) {
			/*
			 * we could not find an element in the timeline. since our fallback element is a lifeline and we
			 * want to return fragment preferably, get the last fragment in this lifeline
			 */
			Optional<MElement<? extends Element>> after = getLastElementOnThisLifeline(timeline, before);
			return after.orElse(before);
		} else if (index == -1 && insertionPoint instanceof MOccurrence) {
			@SuppressWarnings("unchecked")
			Optional<MLifeline> covered = MOccurrence.class.cast(insertionPoint).getCovered();
			if (covered.isPresent()) {
				return covered.get();
			}
		}

		/* fallback */
		return before;
	}

	private Optional<MElement<? extends Element>> getLastElementOnThisLifeline(
			List<MElement<? extends Element>> timeline, MElement<?> before) {
		Set<InteractionFragment> covered = new LinkedHashSet<>(
				MLifeline.class.cast(before).getElement().getCoveredBys());
		Optional<MElement<? extends Element>> after = timeline.stream()//
				.filter(e -> covered.contains(e.getElement()))//
				.reduce((a, b) -> b);
		return after;
	}

	private int relativeTopOfBefore() {
		return beforeSend.getTop().getAsInt() - layoutHelper().getBottom(getTarget().getDiagramView().get());
	}

	private CreationParameters endParams(Supplier<? extends EObject> insertionPoint) {
		CreationParameters result = CreationParameters.in(interaction(), INTERACTION__FRAGMENT);
		result.setInsertBefore(insertionPoint);
		return result;
	}

	protected Interaction interaction() {
		return getTarget().getInteraction().getElement();
	}

	private Optional<Command> replace(OccurrenceSpecification occurrence, MessageEnd msgEnd) {
		Optional<ExecutionSpecification> execSpec = getExecution(occurrence);
		return execSpec.map(exec -> {
			boolean isStart = exec.getStart() == occurrence;

			// The message end was inserted already in the right index of the
			// fragments list, relative to the execution specification. So,
			// just delete the execution occurrence that is being replaced
			Command result = DeleteCommand.create(getEditingDomain(), occurrence);

			// It does not make sense to replace an execution start by a message send
			// nor an execution finish by a message receive
			if (isStart) {
				result = msgEnd.isReceive()
						? result.chain(SetCommand.create(getEditingDomain(), exec,
								EXECUTION_SPECIFICATION__START, msgEnd))
						: bomb();
			} else {
				result = msgEnd.isSend()
						? result.chain(SetCommand.create(getEditingDomain(), exec,
								EXECUTION_SPECIFICATION__FINISH, msgEnd))
						: bomb();
			}

			return result;
		});
	}

	Optional<MExecution> getExecution(MElement<? extends Element> interactionElement) {
		Optional<MExecution> result = Optional.empty();

		if (interactionElement instanceof MExecution) {
			// It already is an execution
			result = Optional.of((MExecution)interactionElement);
		} else if (interactionElement instanceof MOccurrence<?>) {
			MOccurrence<?> occurrence = (MOccurrence<?>)interactionElement;
			result = occurrence.getStartedExecution();
			if (!result.isPresent()) {
				result = occurrence.getFinishedExecution();
			}
		}

		return result;
	}

	/**
	 * Query the {@link ExecutionSpecification} that is started or finished by an {@code occurrence}
	 * specification.
	 * 
	 * @param occurrence
	 *            an occurrence in the interaction
	 * @return the started or finished execution
	 */
	Optional<ExecutionSpecification> getExecution(OccurrenceSpecification occurrence) {
		Optional<ExecutionSpecification> result = invertSingle(occurrence, EXECUTION_SPECIFICATION__START,
				ExecutionSpecification.class);
		if (!result.isPresent()) {
			result = invertSingle(occurrence, EXECUTION_SPECIFICATION__FINISH, ExecutionSpecification.class);
		}
		return result;
	}

}
