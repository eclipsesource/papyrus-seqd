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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.getSort;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;

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
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageFeedbackHelper.Mode;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.CreateRequestSwitch;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;

/**
 * This is the {@code LifelineBodyGraphicalNodeEditPolicy} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class LifelineBodyGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy implements ISequenceEditPolicy {

	/**
	 * Initializes me.
	 */
	public LifelineBodyGraphicalNodeEditPolicy() {
		super();
	}

	@Override
	@SuppressWarnings("hiding")
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		return new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateConnectionViewRequest(CreateConnectionViewRequest request) {
				Diagram diagram = ((View)getHost().getModel()).getDiagram();
				Lifeline lifeline = getHost().getAdapter(Lifeline.class);
				Interaction interaction = lifeline.getInteraction();
				MLifeline mLifeline = MInteraction.getInstance(interaction, diagram).getLifeline(lifeline)
						.get();

				IElementType messageType = request.getConnectionViewDescriptor().getElementAdapter()
						.getAdapter(IElementType.class);

				Point mouse = request.getLocation();
				Point location = getRelativeLocation(mouse);

				Optional<MElement<?>> before = mLifeline.elementAt(location.y());
				int offset = getOffsetFrom(location, mLifeline, before);

				Command result = new StartMessageCommand(mLifeline, mouse, before, offset,
						getSort(messageType));
				request.setStartCommand(result);
				return result;
			}
		}.doSwitch(request);
	}

	private int getOffsetFrom(Point relativeMouse, MLifeline lifeline, Optional<MElement<?>> before) {
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
				MInteraction interaction = sender.getInteraction();
				MLifeline receiver = interaction.getLifeline(getHost().getAdapter(Lifeline.class)).get();

				switch (start.sort) {
					case ASYNCH_CALL_LITERAL:
					case ASYNCH_SIGNAL_LITERAL:
						// These can slope down
						Point location = request.getLocation();
						int absoluteY = location.y();
						Point startLocation = start.location;
						// but don't require such pointer exactitude of the user
						if ((absoluteY < startLocation.y())
								|| !getLayoutConstraints().isAsyncMessageSlope(startLocation.preciseX(),
										startLocation.preciseY(), location.preciseX(), location.preciseY())) {

							absoluteY = startLocation.y();
							location.setY(absoluteY);
						}

						location = getRelativeLocation(location);

						Optional<MElement<?>> before = receiver.elementAt(location.y());
						int offset = getOffsetFrom(location, receiver, before);

						result = sender.insertMessageAfter(start.before.orElse(sender), start.offset,
								receiver, before.orElse(receiver), offset, start.sort, null);
						break;
					default:
						// Enforce a horizontal layout
						result = sender.insertMessageAfter(start.before.orElse(sender), start.offset,
								receiver, start.sort, null);
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
			feedbackHelper = new MessageFeedbackHelper(Mode.CREATE,
					MessageUtil.isSynchronousMessageConnection(request), true);
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
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		Command result = super.getReconnectSourceCommand(request);

		ConnectionEditPart connectionEP = (ConnectionEditPart)request.getConnectionEditPart();
		Connection connection = (Connection)connectionEP.getFigure();
		Point targetLocation = getLocation(connection.getTargetAnchor());

		// Apply constraints implemented in the feedback
		SetConnectionAnchorsCommand scaCommand = getSetConnectionAnchorsCommand(result);
		INodeEditPart target = (INodeEditPart)connectionEP.getTarget();
		ReconnectRequest targetRequest = new ReconnectRequest(REQ_RECONNECT_TARGET);
		targetRequest.setLocation(targetLocation);
		ConnectionAnchor newTargetAnchor = target.getTargetConnectionAnchor(targetRequest);
		scaCommand.setNewTargetTerminal(target.mapConnectionAnchorToTerminal(newTargetAnchor));

		return getSourceEnd(connectionEP).flatMap(src -> constrainReconnection(request, src)).orElse(result);
	}

	protected Optional<Command> constrainReconnection(ReconnectRequest request, MMessageEnd end) {

		Optional<Command> result = Optional.empty();

		// Disallow semantic reordering below the next element on the lifeline
		Optional<MLifeline> lifeline = end.getCovered();
		Optional<MElement<?>> successor = lifeline.flatMap(ll -> ll.following(end));
		if (successor.filter(above(request.getLocation().y())).isPresent()) {
			result = Optional.of(UnexecutableCommand.INSTANCE);
		} else {
			// And above the previous on the lifeline
			Optional<MElement<?>> predecessor = lifeline.flatMap(ll -> ll.preceding(end));
			if (predecessor.filter(below(request.getLocation().y())).isPresent()) {
				result = Optional.of(UnexecutableCommand.INSTANCE);
			}
		}

		return result;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		Command result = super.getReconnectTargetCommand(request);

		ConnectionEditPart connectionEP = (ConnectionEditPart)request.getConnectionEditPart();
		Connection connection = (Connection)connectionEP.getFigure();
		Point sourceLocation = getLocation(connection.getSourceAnchor());

		// Apply constraints implemented in the feedback
		SetConnectionAnchorsCommand scaCommand = getSetConnectionAnchorsCommand(result);
		INodeEditPart source = (INodeEditPart)connectionEP.getSource();
		ReconnectRequest sourceRequest = new ReconnectRequest(REQ_RECONNECT_SOURCE);
		sourceRequest.setLocation(sourceLocation);
		ConnectionAnchor newSourceAnchor = source.getSourceConnectionAnchor(sourceRequest);
		scaCommand.setNewSourceTerminal(source.mapConnectionAnchorToTerminal(newSourceAnchor));

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

	private static class StartMessageCommand extends Command {
		private final MLifeline sender;

		private final Point location;

		private final Optional<MElement<?>> before;

		private final int offset;

		private final MessageSort sort;

		/**
		 * Initializes me.
		 */
		StartMessageCommand(MLifeline sender, Point absoluteMouse, Optional<MElement<?>> before, int offset,
				MessageSort sort) {
			super();

			this.sender = sender;
			this.location = absoluteMouse;
			this.before = before;
			this.offset = offset;
			this.sort = sort;
		}

	}
}
