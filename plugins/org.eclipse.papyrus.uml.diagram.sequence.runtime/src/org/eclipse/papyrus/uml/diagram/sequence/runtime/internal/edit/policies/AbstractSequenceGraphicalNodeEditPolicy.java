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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import static java.lang.Math.abs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.isAllowSemanticReordering;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.isForce;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.CommandUtil.injectViewInto;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.getSort;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMapToInt;
import static org.eclipse.uml2.uml.UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION;
import static org.eclipse.uml2.uml.UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SelectAndExecuteCommand;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnet;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Messages;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.ISequenceEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageFeedbackHelper.Mode;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.WeakEventBusDelegator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.CreateRequestSwitch;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.spi.ExecutionCreationCommandParameter;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;

/**
 * Abstract implementation of a graphical node edit policy supporting message connections in the sequence
 * diagram.
 *
 * @author Christian W. Damus
 */
public abstract class AbstractSequenceGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy implements ISequenceEditPolicy {

	private final EventBus bus = new EventBus();

	/**
	 * Initializes me.
	 */
	public AbstractSequenceGraphicalNodeEditPolicy() {
		super();
	}

	@Override
	public Command getCommand(Request request) {
		// Ensure padding, if required
		return withPadding(() -> super.getCommand(request));
	}

	@Override
	@SuppressWarnings("hiding")
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		return new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateConnectionViewRequest(CreateConnectionViewRequest request) {
				MLifeline lifeline = getLifeline().get();
				IElementType messageType = request.getConnectionViewDescriptor().getElementAdapter()
						.getAdapter(IElementType.class);

				Point mouse = request.getLocation();
				Point location = getRelativeLocation(mouse);

				AnchorDescriptor anchorDesc = computeAnchoring(location);
				StartMessageCommand result = new StartMessageCommand(bus, lifeline, mouse,
						anchorDesc.elementBefore, anchorDesc.offset, getSort(messageType));
				request.setStartCommand(result);
				return result;
			}
		}.doSwitch(request);
	}

	private AnchorDescriptor computeAnchoring(Point location) {
		return computeAnchoring(getLogicalElement(), location);
	}

	private AnchorDescriptor computeAnchoring(Optional<MElement<?>> self, Point location) {
		Optional<MLifeline> lifeline = self.flatMap(this::getLifeline);
		Optional<MElement<?>> before;
		int offset;

		// Are we connecting from an execution specification?
		if (self.isPresent() && self.get() != lifeline.orElse(null)) {
			// Connecting to something on the lifeline (e.g., execution)
			// so in that case the location is already relative to its top
			MElement<?> _self = self.get();
			if (_self instanceof MExecution) {
				// Measure from the start occurrence
				before = ((MExecution)_self).getStart().map(MElement.class::cast);
			} else {
				before = self;
			}
			offset = location.y();
		} else if (lifeline.isPresent()) {
			MLifeline _lifeline = lifeline.get();
			before = _lifeline.elementAt(location.y());
			offset = getOffsetFrom(location, _lifeline, before);
		} else {
			before = Optional.empty();
			offset = 0;
		}

		return new AnchorDescriptor(before, offset);
	}

	protected Optional<MLifeline> getLifeline() {
		return getLogicalElement().flatMap(this::getLifeline);
	}

	protected Optional<MLifeline> getLifeline(MElement<? extends Element> logicalElement) {
		SequenceDiagramSwitch<Optional<MLifeline>> lifelineSwitch = new SequenceDiagramSwitch<Optional<MLifeline>>() {
			@Override
			public Optional<MLifeline> caseMLifeline(MLifeline object) {
				return Optional.of(object);
			}

			@Override
			public Optional<MLifeline> caseMExecution(MExecution object) {
				return Optional.ofNullable(object.getOwner());
			}

			@Override
			public Optional<MLifeline> caseMMessageEnd(MMessageEnd object) {
				return object.getCovered();
			}
		};

		return lifelineSwitch.doSwitch(logicalElement);
	}

	private int getOffsetFrom(Point relativeMouse, MLifeline lifeline,
			Optional<? extends MElement<?>> before) {
		int result = relativeMouse.y();

		if (before.isPresent()) {
			// We know the top exists because that's how we found the 'before' element
			int relativeTopOfBefore = before.get().getTop().getAsInt()
					- getLayoutHelper().getBottom(lifeline.getDiagramView().get());
			result = result - relativeTopOfBefore;
		} // else it will be relative to the top of the lifeline body line

		return result;
	}

	@Override
	@SuppressWarnings("hiding")
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		return new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateConnectionViewRequest(CreateConnectionViewRequest request) {
				StartMessageCommand start = (StartMessageCommand)request.getStartCommand();
				CreationCommand<Message> result;

				MLifeline sender = start.sender;
				MLifeline receiver = getLifeline().get();
				boolean selfMessage = sender.getElement() == receiver.getElement();

				MElement<?> startBefore = start.before.orElse(sender);
				int startOffset = start.offset;
				Point startLocation = start.location;
				Point location = request.getLocation();

				// Snap the point to the nearest magnet, if any
				Optional<IMagnet> magnet = getMagnetManager().getCapturingMagnet(location,
						IMagnet.ownedBy(getHostFigure()));
				magnet.map(IMagnet::getLocation).ifPresent(location::setLocation);

				int absoluteY = location.y();

				if (!MessageUtil.isSynchronous(start.sort) || selfMessage) {
					// These can slope down
					// but don't require such pointer exactitude of the user
					if ((absoluteY < startLocation.y())
							|| !getLayoutConstraints().isAsyncMessageSlope(startLocation.preciseX(),
									startLocation.preciseY(), location.preciseX(), location.preciseY())) {
						// user attempts an async slope up
						if (startLocation.y() - absoluteY < getLayoutConstraints()
								.getAsyncMessageSlopeThreshold()) {
							// create horizontally of below threshold
							absoluteY = startLocation.y();
							location.setY(absoluteY);
						} else {
							// disallow if slope up too big
							return UnexecutableCommand.INSTANCE;
						}
					}

					location = getRelativeLocation(location);

					AnchorDescriptor anchorDesc = computeAnchoring(location);

					if (MessageUtil.isSynchronousCall(start.sort) && selfMessage && shouldCreateExecution()) {
						MElement<?> _startBefore = startBefore;
						int _startOffset = startOffset;
						return createSelectionCommand(getAvailableExecutionTypes()
								.map(execType -> sender.insertMessageAfter(_startBefore, _startOffset,
										receiver, anchorDesc.elementBefore.orElse(receiver),
										anchorDesc.offset, start.sort, null,
										new ExecutionCreationCommandParameter(true, shouldCreateReply(),
												execType)))
								.map(cmd -> injectViewInto(request.getConnectionViewDescriptor(), cmd))
								.collect(Collectors.toList()));
					} else {
						result = injectViewInto(request.getConnectionViewDescriptor(),
								sender.insertMessageAfter(startBefore, startOffset, receiver,
										anchorDesc.elementBefore.orElse(receiver), anchorDesc.offset,
										start.sort, null));
					}
				} else {
					startLocation = startLocation.getCopy();

					Optional<IMagnet> otherMagnet = getMagnetManager()
							.getCapturingMagnet(startLocation, IMagnet.ownedBy(getHostFigure()))
							.filter(__ -> !magnet.isPresent()); // But not if the target is on a magnet
					otherMagnet.map(IMagnet::getLocation).ifPresent(startLocation::setLocation);

					// Enforce a horizontal layout (subject to magnet constraints, with the
					// target magnet having precendence)
					if (startLocation.y() != absoluteY || otherMagnet.isPresent()) {
						if (!otherMagnet.isPresent()) {
							// Update the source to match the target
							startLocation.setY(absoluteY);
						}

						// Recompute the connection source
						ISequenceEditPart sourceEP = (ISequenceEditPart)request.getSourceEditPart();
						Point relative = sourceEP.getRelativeLocation(startLocation);
						AnchorDescriptor newSourceAnchorDesc = computeAnchoring(sourceEP.getLogicalElement(),
								relative);

						startBefore = newSourceAnchorDesc.elementBefore.orElse(sender);
						startOffset = newSourceAnchorDesc.offset;
					}

					if (MessageUtil.isSynchronousCall(start.sort) && shouldCreateExecution()
					// But not if we are binding sensibly to an execution specification
							&& !getExecutionStart(receiver, request.getLocation()).isPresent()) {

						MElement<?> _startBefore = startBefore;
						int _startOffset = startOffset;
						return createSelectionCommand(getAvailableExecutionTypes()
								.map(execType -> sender.insertMessageAfter(_startBefore, _startOffset,
										receiver, start.sort, null,
										new ExecutionCreationCommandParameter(true, shouldCreateReply(),
												execType)))
								.map(cmd -> injectViewInto(request.getConnectionViewDescriptor(), cmd))
								.collect(Collectors.toList()));
					} else {
						result = injectViewInto(request.getConnectionViewDescriptor(), sender
								.insertMessageAfter(startBefore, startOffset, receiver, start.sort, null));
					}

					result = injectViewInto(request.getConnectionViewDescriptor(),
							sender.insertMessageAfter(startBefore, startOffset, receiver, start.sort, null));
				}

				return wrap(result);
			}
		}.doSwitch(request);
	}

	protected Stream<EClass> getAvailableExecutionTypes() {
		EClass executionType = Activator.getDefault().getPreferences().getSyncMessageExecutionType();

		return (executionType != null) ? Stream.of(executionType)
				: Stream.of(ACTION_EXECUTION_SPECIFICATION, BEHAVIOR_EXECUTION_SPECIFICATION);
	}

	protected Command createSelectionCommand(CreationCommand<?>... commands) {
		return createSelectionCommand(Arrays.asList(commands));
	}

	protected Command createSelectionCommand(
			List<? extends org.eclipse.emf.common.command.Command> commands) {
		if (commands.isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		} else if (commands.size() == 1) {
			return wrap(commands.get(0));
		}

		Shell shell = Display.getCurrent().getActiveShell();
		List<Command> wrappedCommands = commands.stream().map(this::wrap).collect(Collectors.toList());
		SelectAndExecuteCommand selectAndExecuteCommand = new SelectAndExecuteCommand(
				Messages.CreateSynchronousMessagePopupCommandLabel, shell, wrappedCommands);
		return wrap(new GMFtoEMFCommandWrapper(selectAndExecuteCommand));
	}

	/**
	 * Message connections may never slope upwards (backwards in time).
	 */
	@Override
	protected FeedbackHelper getFeedbackHelper(CreateConnectionRequest request) {
		if (!MessageUtil.isMessageConnection(request)) {
			return super.getFeedbackHelper(request);
		}

		if (feedbackHelper == null) {
			MessageFeedbackHelper helper = new MessageFeedbackHelper(Mode.CREATE,
					MessageUtil.isSynchronousMessageConnection(request), getMagnetManager(),
					getLayoutConstraints());
			helper.setEventBus(bus);

			feedbackHelper = helper;

			Point p = request.getLocation();
			connectionFeedback = createDummyConnection(request);
			connectionFeedback.setConnectionRouter(getDummyConnectionRouter(request));
			connectionFeedback.setSourceAnchor(getSourceConnectionAnchor(request));
			feedbackHelper.setConnection(connectionFeedback);
			addFeedback(connectionFeedback);
			feedbackHelper.update(null, p);
		}

		return feedbackHelper;
	}

	@Override
	protected void showCreationFeedback(CreateConnectionRequest request) {
		super.showCreationFeedback(request);
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ConnectionEditPart connectionEP = (ConnectionEditPart)request.getConnectionEditPart();
		Optional<Message> _message = Optional.of(connectionEP).map(ConnectionEditPart::resolveSemanticElement)
				.filter(Message.class::isInstance).map(Message.class::cast);
		Optional<MLifeline> lifeline = getLifeline();

		if (!_message.isPresent() || !lifeline.isPresent()) {
			// Only know how to reconnect messages on lifelines (so far)
			return UnexecutableCommand.INSTANCE;
		}

		// This is known to exist because we're manipulating an existing message in the diagram
		MMessage message = getInteraction().getMessage(_message.get()).get();
		OptionalInt yPosition = OptionalInt.of(request.getLocation().y());

		// If the message didn't have a send end, we wouldn't be reconnecting it
		MMessageEnd sourceEnd = message.getSend().get();
		org.eclipse.emf.common.command.Command createFinish = null;
		if (sourceEnd.getFinishedExecution().isPresent() && isAllowSemanticReordering(request)
				&& !isAttachingToOtherLifeline(sourceEnd)) {
			// Disconnect the message end from the execution that it finishes
			createFinish = sourceEnd.getFinishedExecution().get().createFinish();
		}
		org.eclipse.emf.common.command.Command setCovered = sourceEnd.setCovered(lifeline.get(), yPosition);
		Command result = wrap(chain(createFinish, setCovered));

		result = constrainReconnection(request, sourceEnd).orElse(result);

		return result;
	}

	protected boolean isAttachingToOtherLifeline(MMessageEnd end) {
		return getLifeline().isPresent() && !end.getCovered().equals(getLifeline());
	}

	protected Optional<Command> constrainReconnection(ReconnectRequest request, MMessageEnd end) {
		// Disallow semantic reordering below the next element on the lifeline.
		// But if this is a self-message, then don't worry about crossing over the
		// other end, because it's also moving
		Optional<MLifeline> lifeline = getLifeline(); // The lifeline under the pointer
		boolean selfMessage = end.getOtherEnd().flatMap(MMessageEnd::getCovered).equals(lifeline);
		boolean wasSelfMessage = end.getOtherEnd().flatMap(MMessageEnd::getCovered).equals(end.getCovered());

		// Don't allow reconnection to another lifeline unless user requests it with modifier key
		if (!isForce(request) && getLifeline().isPresent() && !end.getCovered().equals(getLifeline())//
				&& !PrivateRequestUtils.isAllowSemanticReordering(request) //
				&& isAttachingToOtherLifeline(end)) {

			return Optional.of(bomb());
		}

		Optional<? extends MElement<?>> successor = lifeline.flatMap(
				ll -> ll.following(selfMessage && end.isSend() ? end.getOtherEnd().orElse(end) : end));
		// To allow connection to an execution specification on another lifeline, we must
		// compare Y positions to the start occurrence of the execution or the finish,
		// never the execution itself
		if (successor.filter(MExecution.class::isInstance).isPresent()) {
			successor = successor.map(MExecution.class::cast).flatMap(MExecution::getFinish);
		}
		if (!isAllowSemanticReordering(request)
				&& successor.filter(above(request.getLocation().y())).isPresent()) {
			return Optional.of(bomb());
		} else {
			// And above the previous on the lifeline (accounting for self-message)
			Optional<? extends MElement<?>> predecessor = lifeline.flatMap(
					ll -> ll.preceding(selfMessage && end.isReceive() ? end.getOtherEnd().orElse(end) : end));
			if (predecessor.filter(MExecution.class::isInstance).isPresent()) {
				predecessor = predecessor.map(MExecution.class::cast).flatMap(MExecution::getStart);
			}
			if (!isAllowSemanticReordering(request)
					&& predecessor.filter(below(request.getLocation().y())).isPresent()) {
				return Optional.of(bomb());
			}
		}

		// Also, if this is a self-message, then we have a minimal gap to maintain or if it's
		// synchronous then the gap is fixed. Unless, of course, we're moving both ends of
		// the message (which is the force mode of the request)
		if (selfMessage && wasSelfMessage && !isForce(request)) {
			if (MessageUtil.isSynchronous(end.getOwner().getElement().getMessageSort())) {
				return Optional.of(bomb());
			} else {
				OptionalInt otherY = end.getOtherEnd().map(MElement::getTop).orElse(OptionalInt.empty());
				if (otherY.isPresent()
						&& (abs(request.getLocation().y() - otherY.getAsInt()) < getLayoutConstraints()
								.getMinimumHeight(ViewTypes.MESSAGE))) {
					return Optional.of(bomb());
				}
			}
		}

		return Optional.empty();
	}

	static Command bomb() {
		return UnexecutableCommand.INSTANCE;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ConnectionEditPart connectionEP = (ConnectionEditPart)request.getConnectionEditPart();
		Optional<Message> _message = Optional.of(connectionEP).map(ConnectionEditPart::resolveSemanticElement)
				.filter(Message.class::isInstance).map(Message.class::cast);
		Optional<MLifeline> lifeline = getLifeline();

		if (!_message.isPresent() || !lifeline.isPresent()) {
			// Only know how to reconnect messages on lifelines (so far)
			return UnexecutableCommand.INSTANCE;
		}

		// This is known to exist because we're manipulating an existing message in the diagram
		MMessage message = getInteraction().getMessage(_message.get()).get();
		int y = request.getLocation().y();
		if (!isForce(request) && MessageUtil.isSynchronous(message.getElement().getMessageSort())
		// Synchronous message that is not a self-message
				&& message.getSender().isPresent()
				&& (lifeline.get().getElement() != message.getSender().get().getElement())) {
			// Doesn't matter where the mouse pointer is
			y = flatMapToInt(message.getSend(), MElement::getTop).orElse(y);
		}
		OptionalInt yPosition = OptionalInt.of(y);

		// If the message didn't have a receive end, we wouldn't be reconnecting it
		MMessageEnd targetEnd = message.getReceive().get();
		org.eclipse.emf.common.command.Command createStart = null;
		if (targetEnd.getStartedExecution().isPresent() && isAllowSemanticReordering(request)
				&& !isAttachingToOtherLifeline(targetEnd)) {
			// Disconnect the message end from the execution that it starts
			createStart = targetEnd.getStartedExecution().get().createStart();
		}
		// Then, set coverage
		org.eclipse.emf.common.command.Command setCovered = targetEnd.setCovered(lifeline.get(), yPosition);
		Command result = wrap(chain(createStart, setCovered));

		result = constrainReconnection(request, targetEnd).orElse(result);

		if (result.canExecute()) {
			// If we're still good and we're changing a self-message to a non-self-message,
			// then straighten the message connection
			Optional<MLifeline> oldLifeline = targetEnd.getCovered();

			boolean wasSelfMessage = targetEnd.getOtherEnd().flatMap(MMessageEnd::getCovered)
					.equals(oldLifeline);
			boolean isNowSelfMessage = targetEnd.getOtherEnd().flatMap(MMessageEnd::getCovered)
					.equals(lifeline);

			if (wasSelfMessage && !isNowSelfMessage) {
				// Change the routing to oblique and remove any bendpoints
				Connector connector = (Connector)connectionEP.getNotationView();
				org.eclipse.emf.common.command.Command straighten = getDiagramHelper()
						.configureStraightMessageConnector(message.getElement(), connector);
				result = result.chain(wrap(straighten));
			} else if (!wasSelfMessage && isNowSelfMessage) {
				// Change the routing to rectilinear and add requisite bendpoints
				Connector connector = (Connector)connectionEP.getNotationView();
				org.eclipse.emf.common.command.Command bend = getDiagramHelper()
						.configureSelfMessageConnector(message.getElement(), connector);
				result = result.chain(wrap(bend));
			}
		}

		return result;
	}

	protected boolean shouldCreateExecution() {
		return Activator.getDefault().getPreferences().isAutoCreateExecutionForSyncMessage();
	}

	protected boolean shouldCreateReply() {
		return Activator.getDefault().getPreferences().isAutoCreateReplyForSyncCall();
	}

	private Optional<MExecutionOccurrence> getExecutionStart(MLifeline lifeline, Point location) {
		OptionalInt y = OptionalInt.of(location.y());
		return as(Optional.of(lifeline)
				.flatMap(ll -> ll.getExecutions().stream()
						.filter(exec -> exec.getDiagramView().isPresent() && exec.getTop().equals(y))
						.findFirst())
				.flatMap(MExecution::getStart), MExecutionOccurrence.class);
	}

	//
	// Nested types
	//

	/**
	 * A pseudo-command tracking the details of the start location and anchoring of a message to be created.
	 */
	class StartMessageCommand extends Command {
		private final MLifeline sender;

		private final MessageSort sort;

		private Point location;

		private Optional<MElement<?>> before;

		private int offset;

		/**
		 * Initializes me.
		 */
		StartMessageCommand(EventBus bus, MLifeline sender, Point absoluteMouse, Optional<MElement<?>> before,
				int offset, MessageSort sort) {

			this.sender = sender;
			this.location = absoluteMouse;
			this.before = before;
			this.offset = offset;
			this.sort = sort;

			bus.register(new LocationUpdateHandler(bus, this));
		}

		MLifeline sender() {
			return sender;
		}

		MessageSort sort() {
			return sort;
		}

		Optional<MElement<?>> before() {
			return before;
		}

		int offset() {
			return offset;
		}

		Point location() {
			return location;
		}

		void updateLocation(Point absoluteLocation) {
			if (!absoluteLocation.equals(this.location)) {
				this.location = absoluteLocation;
				Point relativeLocation = getRelativeLocation(absoluteLocation);

				AnchorDescriptor anchor = computeAnchoring(relativeLocation);
				before = anchor.elementBefore;
				offset = anchor.offset;
			}
		}
	}

	/**
	 * A description of the anchoring, in <em>Logical Model</em> terms, of an end of a message to a lifeline
	 * or an execution specification on a lifeline.
	 */
	private static class AnchorDescriptor {
		final Optional<MElement<?>> elementBefore;

		final int offset;

		AnchorDescriptor(Optional<MElement<?>> elementBefore, int offset) {
			super();

			this.elementBefore = elementBefore;
			this.offset = offset;
		}
	}

	/**
	 * Event handler for location updates posted on the event bus, which forwards them to a
	 * {@link StartMessageCommand} as long as that command is still viable. This weak reference indirection is
	 * required because the lifecycle of the {@link StartMessageCommand} is uncontrolled; there is no point in
	 * GEF at which it is or can be disposed.
	 */
	private static final class LocationUpdateHandler extends WeakEventBusDelegator<StartMessageCommand> {

		LocationUpdateHandler(EventBus bus, StartMessageCommand delegate) {
			super(bus, delegate);
		}

		@Subscribe
		public void updateLocation(Point location) {
			StartMessageCommand delegate = get();
			if (delegate != null) {
				delegate.updateLocation(location);
			}
		}
	}
}
