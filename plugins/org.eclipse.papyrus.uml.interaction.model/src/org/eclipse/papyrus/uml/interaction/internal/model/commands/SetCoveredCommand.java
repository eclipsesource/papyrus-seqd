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
import static java.util.Collections.singletonList;
import static org.eclipse.papyrus.uml.interaction.model.util.Executions.executionShapeAt;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMapToInt;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.lessThan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl;
import org.eclipse.papyrus.uml.interaction.model.MDestruction;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.util.Lifelines;
import org.eclipse.papyrus.uml.interaction.model.util.Optionals;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.OperationOwner;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Assignment of the lifeline covered by an occurrence.
 */
public class SetCoveredCommand extends ModelCommandWithDependencies<MOccurrenceImpl<? extends Element>> {

	private final MLifeline lifeline;

	private final OptionalInt yPosition;

	// The element on the lifeline before which we're inserting our occurrence
	private final Optional<MElement<? extends Element>> nextOnLifeline;

	/**
	 * Initializes me.
	 *
	 * @param occurrence
	 */
	public SetCoveredCommand(MOccurrenceImpl<? extends Element> occurrence, MLifeline lifeline,
			OptionalInt yPosition) {
		super(occurrence);

		this.lifeline = lifeline;
		this.yPosition = yPosition;

		nextOnLifeline = Lifelines.elementAfterAbsolute(lifeline,
				yPosition.orElseGet(() -> occurrence.getTop().orElse(0)));
	}

	protected boolean isChangingLifeline() {
		Optional<MLifeline> covered = getTarget().getCovered();
		return !covered.isPresent() || (covered.get().getElement() != lifeline.getElement());
	}

	protected boolean isChangingPosition() {
		return yPosition.isPresent();
	}

	protected Optional<Shape> getLifelineBody() {
		return lifeline.getDiagramView().map(diagramHelper()::getLifelineBodyShape);
	}

	@Override
	protected Command doCreateCommand() {
		InteractionFragment fragment = (InteractionFragment)getTarget().getElement();

		Command result = isChangingLifeline()
				? semanticHelper().set(fragment, UMLPackage.Literals.INTERACTION_FRAGMENT__COVERED,
						// Set the entire covereds list to be just this lifeline
						// (message ends only cover one lifeline)
						singletonList(lifeline.getElement()))
				: IdentityCommand.INSTANCE;

		if ((getTarget() instanceof MDestruction) && lifeline.getDiagramView().isPresent()) {
			MDestruction destruction = (MDestruction)getTarget();
			Shape lifelineBody = diagramHelper().getLifelineBodyShape(lifeline.getDiagramView().get());
			Shape destructionView = (Shape)vertex(destruction).getDiagramView();
			Connector messageView = Optional.ofNullable(destruction.getOwner())
					.flatMap(MMessage::getDiagramView).orElse(null);

			int newYPosition = yPosition.orElseGet(() -> destruction.getTop().getAsInt());

			// Can't destroy the lifeline if it has occurrences following the place where
			// the destruction is to go
			if (!validateDestruction(newYPosition)) {
				return UnexecutableCommand.INSTANCE;
			}

			// Move the X shape
			result = chain(result, diagramHelper().reconnectDestructionOccurrenceShape(destructionView,
					messageView, lifelineBody, newYPosition));
		} else if ((getTarget() instanceof MMessageEnd) && isCreate((MMessageEnd)getTarget())) {
			MMessageEnd creation = (MMessageEnd)getTarget();
			int createPosition = yPosition.orElseGet(() -> creation.getTop().getAsInt());

			// Can't create the lifeline if it has occurrences preceding the place where the
			// creation receive end is to go
			if (!validateCreation(createPosition)) {
				return UnexecutableCommand.INSTANCE;
			}

			Connector messageView = Optional.ofNullable(creation.getOwner()).flatMap(MMessage::getDiagramView)
					.orElse(null);
			Shape lifelineHead = lifeline.getDiagramView().get();

			// First, un-create this lifeline, then make the new lifeline created
			result = chain(getTarget().getCovered().get().makeCreatedAt(OptionalInt.empty()), result);
			result = chain(result, lifeline.makeCreatedAt(OptionalInt.of(createPosition)));

			// And re-connect the message view to the new lifeline's head
			result = chain(result,
					defer(() -> reconnectTarget(messageView, lifelineHead, createPosition).orElse(null)));
		} else {
			// Handle a dependent execution
			result = dependencies(getTarget()).map(chaining(result)).orElse(result);

			// Handle message details
			if (getTarget() instanceof MMessageEnd) {
				result = handleMessage((MMessageEnd)getTarget()).map(chaining(result)).orElse(result);
			}
		}

		ensurePadding();

		return result;
	}

	protected Optional<Command> dependencies(MOccurrence<? extends Element> occurrence) {
		List<Command> result = new ArrayList<>();
		Consumer<Command> commandSink = cmd -> Optional.ofNullable(cmd).ifPresent(result::add);

		boolean isDisconnectingMessageEnd = Optionals
				.elseMaybe(
						occurrence.getStartedExecution()
								.filter(x -> hasDependency(x, CreateExecutionOccurrenceCommand.Start.class)),
						occurrence.getFinishedExecution()
								.filter(x -> hasDependency(x, CreateExecutionOccurrenceCommand.Finish.class)))
				.isPresent();
		boolean isThisMessageEndBeingDisconnected = isDisconnectingMessageEnd
				&& (occurrence instanceof MMessageEnd);

		if (!isThisMessageEndBeingDisconnected) {
			// Either we aren't disconnecting the message end, or it's not a message
			// end that we're moving
			occurrence.getStartedExecution()
					.map(exec -> exec.setOwner(lifeline, yPosition, OptionalInt.empty()))
					.ifPresent(result::add);
			occurrence.getFinishedExecution()
					.map(exec -> exec.setOwner(lifeline, OptionalInt.empty(), yPosition))
					.ifPresent(result::add);
		}

		// Destructions are handled differently
		if ((occurrence instanceof MMessageEnd) && !(occurrence instanceof MDestruction)) {
			MMessageEnd end = (MMessageEnd)occurrence;
			if (isChangingLifeline() || isChangingPosition()) {
				collectMessageDependencies(end, isDisconnectingMessageEnd, commandSink);
			}
		} else if (occurrence instanceof MExecutionOccurrence) {
			MExecutionOccurrence execOcc = (MExecutionOccurrence)occurrence;
			if (isChangingLifeline() || isChangingPosition()) {
				collectExecutionOccurrenceDependencies(execOcc, commandSink);
			}
		}

		return result.stream().reduce(chaining());
	}

	/**
	 * Collect commands for dependencies of a message {@code end}.
	 * 
	 * @param end
	 *            the message end being moved
	 * @param isDisconnecting
	 *            whether the {@code end} is being disconnected from the start/finish of an execution
	 *            specification
	 * @param commandSink
	 *            accumulator of dependency commands (accepts {@code null} values)
	 */
	private void collectMessageDependencies(MMessageEnd end, boolean isDisconnecting,
			Consumer<? super Command> commandSink) {

		MMessage message = end.getOwner();
		Optional<Connector> edge = message.getDiagramView();
		if (!edge.isPresent()) {
			// nothing to do for the visuals
			return;
		}

		Connector connector = edge.get();
		Shape lifelineBody = getLifelineBody().get();
		int newYPosition = yPosition.orElseGet(() -> end.getTop().getAsInt());

		// Handle attached execution specification, unless we're detaching from a message end
		Optional<MExecutionOccurrence> replacement = isDisconnecting ? Optional.empty()
				: end.isSend() ? getExecutionFinish(newYPosition) : getExecutionStart(newYPosition);
		if (replacement.isPresent()) {
			// Replace the start/finish occurrence by this message end
			replacement.map(occ -> occ.replaceBy(end)).ifPresent(commandSink);
		} else {
			// Ensure connection to an appropriate execution specification
			Supplier<Shape> newAttachedShape = () -> {
				// Are we connected to an execution that will be moving with us?
				// If so, no reattachment will be necessary
				Optional<MExecution> exec = (end.isFinish() || end.isStart()) //
						? end.getExecution()
						: Optional.empty();
				Optional<Shape> executionShape = exec.flatMap(MExecution::getDiagramView);
				if (!executionShape.isPresent()) {
					// Are we connecting to an execution specification?
					executionShape = executionShapeAt(lifeline, newYPosition);
				}
				return executionShape.orElse(lifelineBody);
			};

			if (end.isSend()) {
				commandSink.accept(defer(
						() -> reconnectSource(connector, newAttachedShape.get(), newYPosition).orElse(null)));
				end.getOtherEnd().flatMap(this::handleSelfMessageChange).ifPresent(commandSink);
			} else if (end.isReceive()) {
				commandSink.accept(defer(
						() -> reconnectTarget(connector, newAttachedShape.get(), newYPosition).orElse(null)));
				end.getOtherEnd().flatMap(this::handleSelfMessageChange).ifPresent(commandSink);
			} // else don't know what to do with it
		}

		// Handle the opposite end if the message is of a synchronous (strictly horizontal) sort
		// or if it would slope backwards
		MMessageEnd other = end.getOtherEnd().orElse(null);
		if (yPosition.isPresent() && (other != null)
				&& (message.isSynchronous() || (other.isReceive() && lessThan(other.getTop(), yPosition)))) {

			Optional<MLifeline> otherCovered = other.getCovered();
			// Track this end
			otherCovered.map(ll -> other.setCovered(ll, yPosition)).ifPresent(commandSink);
		}
	}

	Optional<Command> handleSelfMessageChange(MMessageEnd otherEnd) {
		Optional<Command> result;

		// If the message end opposite one being moved to this lifeline is already on that lifeline,
		// then we now have a self-message and that needs to be re-shaped
		if (isChangingLifeline()
				&& (otherEnd.getCovered().map(MLifeline::getElement).orElse(null) == lifeline.getElement())) {

			MMessage message = otherEnd.getOwner();

			// We know the message connector exists because we wouldn't be here, otherwise
			Connector connector = message.getDiagramView().get();

			// Move the receive end down if necessary (could be a sloped async message that
			// already has enough room)
			// TODO: Use a nudge to make space?
			int currentYPosition = otherEnd.getTop().getAsInt();
			int height = layoutHelper().getConstraints().getMinimumHeight(connector);
			OptionalInt oppositeY = flatMapToInt(otherEnd.getOtherEnd(), MMessageEnd::getTop);
			if (oppositeY.isPresent() && (height < abs(currentYPosition - oppositeY.getAsInt()))) {
				// Async message already has enough of a vertical gap
				height = 0;
			}
			// Is there an execution specification here to attach to?
			Shape lifelineBody = getLifelineBody().get();

			Command bend;
			if (otherEnd.isSend()) {
				// We're reconnecting the source end, but we need to maintain the gap
				int newYPosition = currentYPosition - height;
				Shape newAttachShape = executionShapeAt(lifeline, newYPosition).orElse(lifelineBody);
				bend = reconnectSource(connector, newAttachShape, currentYPosition)
						.orElse(IdentityCommand.INSTANCE);
			} else {
				int newYPosition = currentYPosition + height;
				Shape newAttachShape = executionShapeAt(lifeline, newYPosition).orElse(lifelineBody);
				bend = reconnectTarget(connector, newAttachShape, newYPosition)
						.orElse(IdentityCommand.INSTANCE);
			}

			// And bend the connector around
			bend = chain(bend,
					diagramHelper().configureSelfMessageConnector(message.getElement(), connector));
			result = Optional.ofNullable(bend);
		} else {
			result = Optional.empty();
		}

		return result;
	}

	/**
	 * Collect commands for dependencies of an execution {@code occurrence}.
	 * 
	 * @param occurrence
	 *            the execution occurrence being moved
	 * @param commandSink
	 *            accumulator of dependency commands (accepts {@code null} values)
	 */
	private void collectExecutionOccurrenceDependencies(MExecutionOccurrence occurrence,
			Consumer<? super Command> commandSink) {

		boolean shouldReplace = (occurrence.isStart() || occurrence.isFinish())
				&& !hasDependency(occurrence.getExecution().get(), CreateExecutionOccurrenceCommand.class);
		if (shouldReplace) {
			// Replace it by a message end? But not if we exist because of separation
			// of a message end from the execution in the first place
			Optional<MMessageEnd> end = getMessageEnd(yPosition);
			end.map(occurrence::replaceBy).ifPresent(commandSink);
		}
	}

	/**
	 * Update details of a message as necessary for changing coverage of an {@code end}.
	 * 
	 * @param end
	 *            a message end
	 * @return the message details command
	 */
	Optional<Command> handleMessage(MMessageEnd end) {
		Optional<Command> result = Optional.empty();

		Message message = end.getOwner().getElement();
		NamedElement signature = message.getSignature();

		if (end.isReceive() && (signature != null)) {
			Optional<OperationOwner> operationOwner = Optional.empty();
			Optional<org.eclipse.uml2.uml.Class> receptionOwner = Optional.empty();

			switch (end.getOwner().getElement().getMessageSort()) {
				case SYNCH_CALL_LITERAL:
				case ASYNCH_CALL_LITERAL:
					// Expecting an operation signature
					operationOwner = as(Optional.ofNullable(lifeline.getElement().getRepresents())
							.map(TypedElement::getType), OperationOwner.class);
					break;
				case ASYNCH_SIGNAL_LITERAL:
					// Expecting a signal signature
					receptionOwner = as(Optional.ofNullable(lifeline.getElement().getRepresents())
							.map(TypedElement::getType), org.eclipse.uml2.uml.Class.class);
					break;
				default:
					// No concerns about the signature
					break;
			}

			boolean unsetSignature = false;

			if (operationOwner.isPresent() && (signature instanceof Operation)) {
				// Operation must match
				unsetSignature = !((Classifier)operationOwner.get()).getAllOperations().contains(signature);
			} else if (receptionOwner.isPresent() && (signature instanceof Signal)) {
				// There is no getAllReceptions() operation in the Class metaclass
				unsetSignature = !receptionOwner.get().getMembers().stream()
						.filter(Reception.class::isInstance).map(Reception.class::cast)
						.filter(reception -> reception.getSignal() != null)
						.anyMatch(reception -> reception.getSignal().conformsTo((Signal)signature));
			}

			if (unsetSignature) {
				Command unsetSignatureCommand = SetCommand.create(getEditingDomain(), message,
						UMLPackage.Literals.MESSAGE__SIGNATURE, SetCommand.UNSET_VALUE);
				if (!message.getArguments().isEmpty()) {
					unsetSignatureCommand = unsetSignatureCommand.chain(SetCommand.create(getEditingDomain(),
							message, UMLPackage.Literals.MESSAGE__ARGUMENT, SetCommand.UNSET_VALUE));
				}
				result = Optional.of(unsetSignatureCommand);
			}
		}

		return result;
	}

	/**
	 * Query whether a destruction occurrence may validly be put at the given Y position on the destination
	 * lifeline.
	 * 
	 * @param y
	 *            the Y position of the proposed destruction, in absolute coördinates
	 * @return whether the destruction should be allowed there
	 */
	protected boolean validateDestruction(int y) {
		return !lifeline.getOccurrences().stream().anyMatch(below(y));
	}

	/**
	 * Query whether a message {@code end} is the receiving end of a <em>create</em> message.
	 * 
	 * @param end
	 *            a message end
	 * @return whether it is creating a lifeline
	 */
	protected boolean isCreate(MMessageEnd end) {
		return end.isReceive()
				&& end.getOwner().getElement().getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL;
	}

	/**
	 * Query whether a creation occurrence may validly be put at the given Y position on the destination
	 * lifeline.
	 * 
	 * @param y
	 *            the Y position of the proposed creation, in absolute coördinates
	 * @return whether the creation should be allowed there
	 */
	protected boolean validateCreation(int y) {
		return !lifeline.getOccurrences().stream().anyMatch(above(y));
	}

	protected void ensurePadding() {
		// From which element do we need to ensure padding?
		MElement<? extends Element> padFrom = getTarget();

		// Do we have an element that needs padding before it?
		MElement<? extends Element> nudge = nextOnLifeline.orElse(null);

		DeferredPaddingCommand.get(padFrom).pad(padFrom, nudge);
	}

	/**
	 * Find an execution occurrence that starts an execution at the given absolute {@code y} position on my
	 * lifeline.
	 * 
	 * @param y
	 *            an absolute Y position
	 * @return an execution occurrence at that place on my lifeline
	 */
	protected Optional<MExecutionOccurrence> getExecutionStart(int y) {
		return getExecutionOccurrence(y, MExecution::getStart);
	}

	/**
	 * Find an execution occurrence that finishes an execution at the given absolute {@code y} position on my
	 * lifeline.
	 * 
	 * @param y
	 *            an absolute Y position
	 * @return an execution occurrence at that place on my lifeline
	 */
	protected Optional<MExecutionOccurrence> getExecutionFinish(int y) {
		return getExecutionOccurrence(y, MExecution::getFinish);
	}

	private Optional<MExecutionOccurrence> getExecutionOccurrence(int y,
			Function<MExecution, Optional<MOccurrence<?>>> occurrenceFunction) {
		OptionalInt y_ = OptionalInt.of(y);
		return as(lifeline.getExecutions().stream() //
				.map(occurrenceFunction).filter(Optional::isPresent).map(Optional::get)
				// Note that the 'top' of the finish occurrence is the bottom of the execution
				.filter(occ -> occ.getTop().equals(y_)).findFirst(), MExecutionOccurrence.class);
	}

	/**
	 * Find a message end at the given absolute {@code y} position on my lifeline.
	 * 
	 * @param y
	 *            an absolute Y position
	 * @return a message end at that place on my lifeline
	 */
	protected Optional<MMessageEnd> getMessageEnd(OptionalInt y) {
		return lifeline.getMessageEnds().stream() //
				// Not already starting or finishing an execution
				.filter(end -> !end.isStart() && !end.isFinish())
				// Note that the 'top' of the message end is its Y position (same as bottom)
				.filter(end -> end.getTop().equals(y)).findFirst();
	}

	/**
	 * Obtain a command to reconnect the source of a {@code connector} if we don't already have a source
	 * reconnection command for it.
	 */
	protected Optional<Command> reconnectSource(Connector connector, Shape newSource, int sourceY) {
		return DependencyContext.get().apply(connector, ReconnectKind.SOURCE,
				c -> diagramHelper().reconnectSource(c, newSource, sourceY));
	}

	/**
	 * Obtain a command to reconnect the target of a {@code connector} if we don't already have a target
	 * reconnection command for it.
	 */
	protected Optional<Command> reconnectTarget(Connector connector, Shape newTarget, int targetY) {
		return DependencyContext.get().apply(connector, ReconnectKind.TARGET,
				c -> diagramHelper().reconnectTarget(c, newTarget, targetY));
	}

	//
	// Nested types
	//

	/**
	 * Enumeration of dependency-context keys for edge reconnection commands, to avoid redundant command
	 * calculation and/or overriding of higher-priority command computations.
	 */
	private static enum ReconnectKind {
		SOURCE, TARGET;
	}
}
