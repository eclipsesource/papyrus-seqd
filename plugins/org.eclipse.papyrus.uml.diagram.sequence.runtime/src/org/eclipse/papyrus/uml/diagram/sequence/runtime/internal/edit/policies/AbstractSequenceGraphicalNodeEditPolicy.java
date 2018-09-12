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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.isForce;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.getSort;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.Iterator;
import java.util.Optional;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnet;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.ISequenceEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageFeedbackHelper.Mode;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.WeakEventBusDelegator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.CreateRequestSwitch;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
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

				MElement<?> startBefore = start.before.orElse(sender);
				int startOffset = start.offset;
				Point startLocation = start.location;
				Point location = request.getLocation();

				// Snap the point to the nearest magnet, if any
				Optional<IMagnet> magnet = getMagnetManager().getCapturingMagnet(location);
				magnet.map(IMagnet::getLocation).ifPresent(location::setLocation);

				int absoluteY = location.y();

				switch (start.sort) {
					case ASYNCH_CALL_LITERAL:
					case ASYNCH_SIGNAL_LITERAL:
						// These can slope down
						// but don't require such pointer exactitude of the user
						if ((absoluteY < startLocation.y())
								|| !getLayoutConstraints().isAsyncMessageSlope(startLocation.preciseX(),
										startLocation.preciseY(), location.preciseX(), location.preciseY())) {

							absoluteY = startLocation.y();
							location.setY(absoluteY);
						}

						location = getRelativeLocation(location);

						AnchorDescriptor anchorDesc = computeAnchoring(location);
						result = sender.insertMessageAfter(startBefore, startOffset, receiver,
								anchorDesc.elementBefore.orElse(receiver), anchorDesc.offset, start.sort,
								null);
						break;
					default:
						startLocation = startLocation.getCopy();

						Optional<IMagnet> otherMagnet = getMagnetManager().getCapturingMagnet(startLocation)
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
							AnchorDescriptor newSourceAnchorDesc = computeAnchoring(
									sourceEP.getLogicalElement(), relative);

							startBefore = newSourceAnchorDesc.elementBefore.orElse(sender);
							startOffset = newSourceAnchorDesc.offset;
						}

						result = sender.insertMessageAfter(startBefore, startOffset, receiver, start.sort,
								null);
						break;
				}

				return wrap(result);
			}
		}.doSwitch(request);
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
					MessageUtil.isSynchronousMessageConnection(request), getMagnetManager());
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

	@SuppressWarnings("boxing")
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		Command result = super.getReconnectSourceCommand(request);
		ConnectionEditPart connectionEP = (ConnectionEditPart)request.getConnectionEditPart();
		Optional<Message> message = Optional.of(connectionEP).map(ConnectionEditPart::resolveSemanticElement)
				.filter(Message.class::isInstance).map(Message.class::cast);

		// Of course, we don't mess with the other end of an asynchronous message
		if (!isForce(request)
				&& message.map(Message::getMessageSort).map(MessageUtil::isSynchronous).orElse(false)) {

			// Need feedback constraints in addition to semantic constraints
			Connection connection = (Connection)connectionEP.getFigure();
			Point targetLocation = getLocation(connection.getTargetAnchor());

			// Apply constraints implemented in the feedback
			SetConnectionAnchorsCommand scaCommand = getSetConnectionAnchorsCommand(result);
			INodeEditPart target = (INodeEditPart)connectionEP.getTarget();
			ReconnectRequest targetRequest = new ReconnectRequest(REQ_RECONNECT_TARGET);
			targetRequest.setLocation(targetLocation);
			ConnectionAnchor newTargetAnchor = target.getTargetConnectionAnchor(targetRequest);
			scaCommand.setNewTargetTerminal(target.mapConnectionAnchorToTerminal(newTargetAnchor));
		}

		return getSourceEnd(connectionEP).flatMap(src -> constrainReconnection(request, src)).orElse(result);
	}

	protected Optional<Command> constrainReconnection(ReconnectRequest request, MMessageEnd end) {

		Optional<Command> result = Optional.empty();

		// Disallow semantic reordering below the next element on the lifeline
		Optional<MLifeline> lifeline = end.getCovered();
		Optional<? extends MElement<?>> successor = lifeline.flatMap(ll -> ll.following(end));
		if (successor.filter(above(request.getLocation().y())).isPresent()) {
			result = Optional.of(UnexecutableCommand.INSTANCE);
		} else {
			// And above the previous on the lifeline
			Optional<? extends MElement<?>> predecessor = lifeline.flatMap(ll -> ll.preceding(end));
			if (predecessor.filter(below(request.getLocation().y())).isPresent()) {
				result = Optional.of(UnexecutableCommand.INSTANCE);
			}
		}

		return result;
	}

	@SuppressWarnings("boxing")
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		Command result = super.getReconnectTargetCommand(request);
		ConnectionEditPart connectionEP = (ConnectionEditPart)request.getConnectionEditPart();
		Optional<Message> message = Optional.of(connectionEP).map(ConnectionEditPart::resolveSemanticElement)
				.filter(Message.class::isInstance).map(Message.class::cast);

		// Of course, we don't mess with the other end of an asynchronous message
		if (!isForce(request)
				&& message.map(Message::getMessageSort).map(MessageUtil::isSynchronous).orElse(false)) {

			// Need feedback constraints in addition to semantic constraints
			Connection connection = (Connection)connectionEP.getFigure();
			Point sourceLocation = getLocation(connection.getSourceAnchor());

			// Apply constraints implemented in the feedback
			SetConnectionAnchorsCommand scaCommand = getSetConnectionAnchorsCommand(result);
			INodeEditPart source = (INodeEditPart)connectionEP.getSource();
			ReconnectRequest sourceRequest = new ReconnectRequest(REQ_RECONNECT_SOURCE);
			sourceRequest.setLocation(sourceLocation);
			ConnectionAnchor newSourceAnchor = source.getSourceConnectionAnchor(sourceRequest);
			scaCommand.setNewSourceTerminal(source.mapConnectionAnchorToTerminal(newSourceAnchor));
		}

		return getTargetEnd(connectionEP).flatMap(tgt -> constrainReconnection(request, tgt)).orElse(result);
	}

	private Point getLocation(ConnectionAnchor anchor) {
		IFigure owner = anchor.getOwner();
		Point ownerOrigin = owner.getBounds().getLocation();
		owner.getParent().translateToAbsolute(ownerOrigin);
		return anchor.getLocation(ownerOrigin);
	}

	SetConnectionAnchorsCommand getSetConnectionAnchorsCommand(Command command) {
		CompositeCommand composite = (CompositeCommand)((ICommandProxy)command).getICommand();
		for (Iterator<?> iter = composite.iterator(); iter.hasNext();) {
			Object next = iter.next();
			if (next instanceof SetConnectionAnchorsCommand) {
				return (SetConnectionAnchorsCommand)next;
			}
		}
		return null;
	}

	Optional<MMessageEnd> getSourceEnd(ConnectionEditPart connection) {
		return getEnd(connection, true);
	}

	Optional<MMessageEnd> getTargetEnd(ConnectionEditPart connection) {
		return getEnd(connection, false);
	}

	Optional<MMessageEnd> getEnd(ConnectionEditPart connection, boolean source) {
		Diagram diagram = connection.getNotationView().getDiagram();
		Optional<Message> message = Optional.ofNullable(connection.resolveSemanticElement())
				.filter(Message.class::isInstance).map(Message.class::cast);
		Optional<MInteraction> interaction = message
				.map(msg -> MInteraction.getInstance(msg.getInteraction(), diagram));
		return interaction.flatMap(in -> in.getMessage(message.get()))
				.flatMap(source ? MMessage::getSend : MMessage::getReceive);
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
