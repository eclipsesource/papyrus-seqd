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

import static java.lang.Integer.MAX_VALUE;
import static org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil.invertSingle;
import static org.eclipse.papyrus.uml.interaction.graph.util.Suppliers.compose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
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
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A message creation operation.
 *
 * @author Christian W. Damus
 */
public class InsertMessageCommand extends ModelCommand<MLifelineImpl> implements CreationCommand<Message> {

	private final MElement<?> beforeSend;

	private final int sendOffset;

	private final MLifeline receiver;

	private final MElement<?> beforeRecv;

	private final int recvOffset;

	private final MessageSort sort;

	private final NamedElement signature;

	private final boolean syncMessage;

	private CreationCommand<Message> resultCommand;

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

		super(sender);

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
	public Message getNewObject() {
		return (resultCommand == null) ? null : resultCommand.getNewObject();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Command createCommand() {
		Vertex sendReference = vertex(beforeSend);
		if (sendReference == null) {
			return UnexecutableCommand.INSTANCE;
		}
		Vertex recvReference = vertex(beforeRecv);
		if (recvReference == null) {
			return UnexecutableCommand.INSTANCE;
		}

		// Send: Is there actually an execution occurrence here?
		int llTopSend = layoutHelper().getBottom(getTarget().getDiagramView().get());
		int whereSend = beforeSend == getTarget() ? sendOffset
				// For executions also the top to allow for messages to anchor within it
				: beforeSend.getTop().orElse(llTopSend) - llTopSend + sendOffset;
		Optional<MExecution> sendingExec = getTarget().elementAt(whereSend).flatMap(this::getExecution)
				.filter(exec -> //
				((exec.getBottom().orElse(-1) - llTopSend) >= whereSend) //
						&& ((exec.getTop().orElse(MAX_VALUE) - llTopSend) <= whereSend));
		Vertex sender = sendingExec.map(this::vertex).orElseGet(this::vertex);
		if (sender == null || sender.getDiagramView() == null) {
			return UnexecutableCommand.INSTANCE;
		}

		// Receive: Is there actually an execution occurrence here?
		// In case of create or destruction messages, this should not connect to execution occurrences,
		// because they connect to lifeline-header/new destruction occurrence
		int llTopRecv = layoutHelper().getBottom(receiver.getDiagramView().get());
		int whereRecv = beforeRecv == receiver ? recvOffset
				// For executions also the top to allow for messages to anchor within it
				: beforeRecv.getTop().orElse(llTopRecv) - llTopRecv + recvOffset;
		Optional<MExecution> receivingExec;
		switch (sort) {
			case CREATE_MESSAGE_LITERAL:
			case DELETE_MESSAGE_LITERAL:
				receivingExec = Optional.empty();
				break;
			default:
				receivingExec = this.receiver.elementAt(whereRecv).flatMap(this::getExecution).filter(exec -> //
				((exec.getBottom().orElse(-1) - llTopRecv) >= whereRecv) //
						&& ((exec.getTop().orElse(MAX_VALUE) - llTopRecv) <= whereRecv));
				break;
		}
		@SuppressWarnings("hiding")
		Vertex receiver = receivingExec.map(this::vertex).orElseGet(() -> vertex(this.receiver));
		if (receiver == null || receiver.getDiagramView() == null) {
			return UnexecutableCommand.INSTANCE;
		}

		OptionalInt sendReferenceY = beforeSend == getTarget() ? layoutHelper().getBottom(sendReference)
				// Reference from the top of an execution specification to allow
				// connections within its extent
				: layoutHelper().getTop(sendReference);
		if (!sendReferenceY.isPresent()) {
			return UnexecutableCommand.INSTANCE;
		}
		OptionalInt recvReferenceY = beforeRecv == this.receiver ? layoutHelper().getBottom(recvReference)
				// Reference from the top of an execution specification to allow
				// connections within its extent
				: layoutHelper().getTop(recvReference);
		if (!recvReferenceY.isPresent()) {
			return UnexecutableCommand.INSTANCE;
		}

		int absoluteSendY = sendReferenceY.getAsInt() + sendOffset;
		int absoluteRecvY = recvReferenceY.getAsInt() + recvOffset;

		switch (sort) {
			case CREATE_MESSAGE_LITERAL:
				/* self-creation makes no sense */
				if (this.receiver == this.getTarget()) {
					return UnexecutableCommand.INSTANCE;
				}

				/* receiver must have no elements before */
				if (this.receiver.elementAt(recvOffset + relativeTopOfBefore()).isPresent()) {
					return UnexecutableCommand.INSTANCE;
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
				findElementsBelow(absoluteDeleteY, elementsBelow,
						getTarget().getInteraction().getMessages().stream(), false);
				if (!elementsBelow.isEmpty()) {
					return UnexecutableCommand.INSTANCE;
				}
				findElementsBelow(absoluteDeleteY, elementsBelow, this.receiver.getExecutions().stream(),
						false);
				if (!elementsBelow.isEmpty()) {
					return UnexecutableCommand.INSTANCE;
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
		CreationParameters messageParams = CreationParameters.in(getTarget().getInteraction().getElement(),
				UMLPackage.Literals.INTERACTION__MESSAGE);
		resultCommand = semantics.createMessage(sendEvent, recvEvent, sort, signature, messageParams);

		// Create these elements in the interaction
		Command result = sendEvent.chain(recvEvent).chain(resultCommand);

		// Cover the appropriate lifelines
		result = result.chain(new DeferredAddCommand(getTarget().getElement(),
				UMLPackage.Literals.LIFELINE__COVERED_BY, sendEvent));
		result = result.chain(new DeferredAddCommand(this.receiver.getElement(),
				UMLPackage.Literals.LIFELINE__COVERED_BY, recvEvent));

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
				if (this.receiver.getElement() == this.getTarget().getElement()) {
					destructionOffset = absoluteSendY
							/* this is the minimum gap in self-messages */
							+ layoutHelper().getConstraints().getMinimumHeight(ViewTypes.MESSAGE)
							+ (layoutHelper().getConstraints()
									.getMinimumHeight(ViewTypes.DESTRUCTION_SPECIFICATION) / 2);
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
			Set<MElement<?>> elementToNudge = new LinkedHashSet<MElement<?>>();
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
		result = result.chain(diagramHelper().createMessageConnector(resultCommand, //
				senderView, () -> sendReferenceY.getAsInt() + sendOffset + additionalOffsetSend, //
				receiverView, () -> recvReferenceY.getAsInt() + recvOffset + additionalOffsetRecv, //
				this::replace));

		switch (sort) {
			case CREATE_MESSAGE_LITERAL:
				List<Command> commands = new ArrayList<>(3);

				View receiverShape = (this.receiver).getDiagramView().orElse(null);
				int receiverBodyTop = layoutHelper()
						.getTop(diagramHelper().getLifelineBodyShape(receiverShape));
				int receiverLifelineTop = this.receiver.getTop().orElse(0);
				/* first make room to move created lifeline down by nudging all required elements down */
				int creationMessageTop = recvReferenceY.getAsInt() + recvOffset;
				List<MElement<? extends Element>> elementsToNudge = new ArrayList<>();

				int movedReceiverlifelineTop = creationMessageTop
						- ((receiverBodyTop - receiverLifelineTop) / 2);

				int receiverDeltaY = movedReceiverlifelineTop - receiverLifelineTop + additionalOffsetRecv;
				int receiverDeltaYFinal = receiverDeltaY;

				/* collect elements below */
				findElementsBelow(creationMessageTop, elementsToNudge,
						getTarget().getInteraction().getMessages().stream(), true);
				findElementsBelow(creationMessageTop, elementsToNudge,
						getTarget().getInteraction().getLifelines().stream()//
								.filter(m -> m.getTop().orElse(0) < creationMessageTop)//
								.flatMap(l -> l.getExecutions().stream()),
						true);
				Collections.sort(elementsToNudge,
						Comparator.comparingInt(e -> ((MElementImpl<? extends Element>)e).getTop().orElse(0))
								.reversed());
				List<Command> nudgeCommands = new ArrayList<>(elementsToNudge.size());
				for (int i = 0; i < elementsToNudge.size(); i++) {
					MElement<? extends Element> element = elementsToNudge.get(i);
					nudgeCommands.add(Create.nudgeCommand(getGraph(), getEditingDomain(), receiverDeltaYFinal,
							0, element));
				}

				if (!nudgeCommands.isEmpty()) {
					commands.add(CompoundModelCommand.compose(getEditingDomain(), nudgeCommands));
				}

				/* finally move lifeline */
				commands.add(Create.nudgeCommand(getGraph(), getEditingDomain(), receiverDeltaYFinal, 0,
						this.receiver));

				/* build final command */
				result = CompoundModelCommand.compose(getEditingDomain(), commands).chain(result);
				break;

			case DELETE_MESSAGE_LITERAL:
				break;

			default:
				// Now we have commands to add the message specification. But, first we must make
				// room for it in the diagram. Nudge the element that will follow the new receive event
				int spaceRequired = 2 * sendOffset;
				// If inserting after the start occurrence of an execution specification,
				// then actually insert after the execution, itself, so that it can span
				// the new message end
				Optional<Command> makeSpace = createNudgeCommandForFollowingElements(timeline, spaceRequired, //
						sendInsert, sendingExec,
						sendReferenceY.getAsInt() + sendOffset + additionalOffsetSend, //
						recvInsert, receivingExec,
						recvReferenceY.getAsInt() + recvOffset + additionalOffsetRecv);
				if (makeSpace.isPresent()) {
					result = makeSpace.get().chain(result);
				}
				break;
		}

		if (nudgeToEnforcePadding.isPresent()) {
			result = result.chain(nudgeToEnforcePadding.get().nudge(distanceToEnforcePadding));
		}

		return result;
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

	@SuppressWarnings("unchecked")
	private Optional<Command> createNudgeCommandForFollowingElements(
			List<MElement<? extends Element>> timeline, int spaceRequired,
			Optional<MElement<? extends Element>> sendInsert, //
			Optional<MExecution> sendingExec, int sendYPosition, //
			Optional<MElement<? extends Element>> recvInsert, //
			Optional<MExecution> receivingExec, int recvYPosition) {

		if (sendingExec.isPresent() || receivingExec.isPresent()) {
			return Optional.empty();
		}

		/* check if we are too close to following elements on send and/or receive side */
		int additionalSendNudge = missingPadding(sendInsert, sendYPosition);
		int additionalRecvNudge = missingPadding(recvInsert, recvYPosition);
		int minNudge = Math.max(0, Math.max(additionalSendNudge, additionalRecvNudge));

		MElement<?> distanceFrom = (MElement<?>)Optional.of(beforeSend).filter(MOccurrence.class::isInstance)
				.map(MOccurrence.class::cast).flatMap(MOccurrence::getStartedExecution).orElse(beforeSend);
		Optional<Command> makeSpace = getFollowingElement(timeline, distanceFrom, sendYPosition).map(el -> {
			OptionalInt distance = el.verticalDistance(distanceFrom);
			if (distance.isPresent()) {
				return el.nudge(Math.max(minNudge, spaceRequired - distance.getAsInt()));
			} else {
				return null;
			}
		});

		/*
		 * check if we did create a nudge command based on measured distance and the required space. if not,
		 * but we need to enforce padding, create it based on insertion point
		 */
		if (!makeSpace.isPresent() && minNudge > 0) {
			MElement<? extends Element> toNudge = additionalSendNudge > additionalRecvNudge ? sendInsert.get()
					: recvInsert.get();
			return Optional.of(toNudge.nudge(minNudge));
		}
		return makeSpace;
	}

	private Optional<MElement<? extends Element>> getFollowingElement(
			List<MElement<? extends Element>> fragments, MElement<?> distanceFrom, int yPosition) {
		int index = fragments.indexOf(distanceFrom);
		if (index == -1) {
			if (distanceFrom instanceof MLifeline) {
				// get the first element which comes after
				return fragments.stream()//
						.filter(e -> {
							return e.getTop().orElse(yPosition) >= yPosition;
						})//
						.findFirst();
			}
			return getTarget().following(distanceFrom);
		}
		if (index + 1 < fragments.size()) {
			return Optional.of(fragments.get(index + 1));
		}
		return getTarget().following(distanceFrom);
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
		int reqPadding = layoutHelper().getConstraints().getPadding(RelativePosition.BOTTOM,
				diagramView.get())
				+ layoutHelper().getConstraints().getPadding(RelativePosition.TOP, ViewTypes.MESSAGE);
		if (curPadding < reqPadding) {
			return reqPadding - curPadding;
		} else {
			return 0;
		}
	}

	/**
	 * Returns for how many pixels the {@link MElement element after me} has to be nudged in order to fulfill
	 * padding requirements.
	 * 
	 * @param elementAfterMe
	 *            the element following me
	 * @param myYPosition
	 *            my insertion position
	 * @return distance required to fulfil
	 */
	private int missingPadding(Optional<MElement<? extends Element>> elementAfterMe, int myYPosition) {
		if (!elementAfterMe.isPresent()) {
			return 0;
		}
		MElement<? extends Element> insertionPoint = elementAfterMe.get();
		Optional<View> diagramView = getDiagramView(insertionPoint);
		if (!diagramView.isPresent()) {
			return 0;
		}
		int curPadding = insertionPoint.getTop().orElse(0) - myYPosition;
		int reqPadding = layoutHelper().getConstraints().getPadding(RelativePosition.BOTTOM,
				ViewTypes.MESSAGE)
				+ layoutHelper().getConstraints().getPadding(RelativePosition.TOP, diagramView.get());
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

	private static void findElementsBelow(int yPosition, List<MElement<? extends Element>> elementsBelow,
			Stream<?> stream, boolean useTop) {
		stream//
				.filter(MElementImpl.class::isInstance).map(MElementImpl.class::cast).filter(m -> {
					if (useTop) {
						return m.getTop().orElse(0) >= yPosition;
					} else {
						return m.getBottom().orElse(0) >= yPosition;
					}
				})//
				.collect(Collectors.toCollection(() -> elementsBelow));
	}

	private CreationParameters endParams(Supplier<? extends EObject> insertionPoint) {
		CreationParameters result = CreationParameters.in(getTarget().getInteraction().getElement(),
				UMLPackage.Literals.INTERACTION__FRAGMENT);
		result.setInsertBefore(insertionPoint);
		return result;
	}

	private Optional<Command> replace(OccurrenceSpecification occurrence, MessageEnd msgEnd) {
		Optional<ExecutionSpecification> execSpec = getExecution(occurrence);
		return execSpec.map(exec -> {
			boolean isStart = exec.getStart() == occurrence;

			// The message end was inserted already in the right index of the
			// fragments list, relative to the execution specification. So,
			// just delete the execution occurrence that is being replaced
			Command result = DeleteCommand.create(getEditingDomain(), occurrence);

			if (isStart) {
				result = result.chain(SetCommand.create(getEditingDomain(), exec,
						UMLPackage.Literals.EXECUTION_SPECIFICATION__START, msgEnd));
			} else {
				result = result.chain(SetCommand.create(getEditingDomain(), exec,
						UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH, msgEnd));
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
		Optional<ExecutionSpecification> result = invertSingle(occurrence,
				UMLPackage.Literals.EXECUTION_SPECIFICATION__START, ExecutionSpecification.class);
		if (!result.isPresent()) {
			result = invertSingle(occurrence, UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH,
					ExecutionSpecification.class);
		}
		return result;
	}

}
