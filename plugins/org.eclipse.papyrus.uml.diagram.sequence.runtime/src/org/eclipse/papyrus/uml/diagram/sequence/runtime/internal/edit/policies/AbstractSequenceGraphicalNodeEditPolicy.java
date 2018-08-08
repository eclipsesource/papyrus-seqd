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

import java.util.Iterator;
import java.util.Optional;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
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
				Command result = new StartMessageCommand(lifeline, mouse, anchorDesc.elementBefore,
						anchorDesc.offset, getSort(messageType));
				request.setStartCommand(result);
				return result;
			}
		}.doSwitch(request);
	}

	private AnchorDescriptor computeAnchoring(Point location) {
		Optional<MElement<?>> self = getLogicalElement();
		Optional<MLifeline> lifeline = getLifeline();

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

	protected MInteraction getInteraction() {
		View view = ((IGraphicalEditPart)getHost()).getNotationView();
		Diagram diagram = view.getDiagram();
		return MInteraction.getInstance(diagram);
	}

	protected Optional<MElement<? extends Element>> getLogicalElement() {
		EObject semantic = ((IGraphicalEditPart)getHost()).resolveSemanticElement();
		return Optional.ofNullable(semantic).filter(Element.class::isInstance).map(Element.class::cast)
				.flatMap(getInteraction()::getElement);
	}

	protected Optional<MLifeline> getLifeline() {
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

		return getLogicalElement().flatMap(lifelineSwitch::doSwitch);
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

						AnchorDescriptor anchorDesc = computeAnchoring(location);
						result = sender.insertMessageAfter(start.before.orElse(sender), start.offset,
								receiver, anchorDesc.elementBefore.orElse(receiver), anchorDesc.offset,
								start.sort, null);
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
					MessageUtil.isSynchronousMessageConnection(request));
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

		if (!isForce(request)) {
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

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		Command result = super.getReconnectTargetCommand(request);
		ConnectionEditPart connectionEP = (ConnectionEditPart)request.getConnectionEditPart();

		if (!isForce(request)) {
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

	static class StartMessageCommand extends Command {
		final MLifeline sender;

		final Point location;

		final Optional<MElement<?>> before;

		final int offset;

		final MessageSort sort;

		/**
		 * Initializes me.
		 */
		StartMessageCommand(MLifeline sender, Point absoluteMouse, Optional<MElement<?>> before, int offset,
				MessageSort sort) {

			this.sender = sender;
			this.location = absoluteMouse;
			this.before = before;
			this.offset = offset;
			this.sort = sort;
		}

	}

	private static class AnchorDescriptor {
		final Optional<MElement<?>> elementBefore;

		final int offset;

		AnchorDescriptor(Optional<MElement<?>> elementBefore, int offset) {
			super();

			this.elementBefore = elementBefore;
			this.offset = offset;
		}

	}
}
