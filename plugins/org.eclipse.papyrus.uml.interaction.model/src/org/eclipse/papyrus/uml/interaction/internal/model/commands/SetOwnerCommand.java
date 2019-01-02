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

import static java.util.Collections.singletonList;
import static org.eclipse.papyrus.uml.interaction.internal.model.commands.PendingNestedData.setPendingNested;
import static org.eclipse.papyrus.uml.interaction.internal.model.commands.PendingVerticalExtentData.affectedOccurrences;
import static org.eclipse.papyrus.uml.interaction.model.util.Executions.executionShapeAt;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.equalTo;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.lessThan;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.map;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.UnaryOperator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.papyrus.uml.interaction.model.util.Executions;
import org.eclipse.papyrus.uml.interaction.model.util.Lifelines;
import org.eclipse.papyrus.uml.interaction.model.util.Optionals;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Assignment of the owner of an element.
 */
public class SetOwnerCommand extends ModelCommandWithDependencies<MElementImpl<? extends Element>> {

	private final MElement<? extends Element> newOwner;

	private OptionalInt top;

	private OptionalInt bottom;

	// The element on the lifeline that we may need to nudge, if the new owner is a lifeline
	private final Optional<MElement<? extends Element>> nudgeElement;

	/**
	 * Initializes me.
	 *
	 * @param target
	 */
	public SetOwnerCommand(MElementImpl<? extends Element> element, MElement<? extends Element> newOwner,
			OptionalInt top, OptionalInt bottom) {

		super(element);

		this.newOwner = newOwner;
		this.top = top;
		this.bottom = bottom;

		nudgeElement = as(Optional.of(newOwner), MLifeline.class)
				.flatMap(lifeline -> lessThan(top, element.getTop())
						// Moving up? Nudge the previous element on the lifeline
						? Lifelines.elementBeforeAbsolute(lifeline,
								top.orElseGet(() -> element.getTop().orElse(0)))
						// Nudge the following element on the lifeline
						: Lifelines.elementAfterAbsolute(lifeline,
								bottom.orElseGet(() -> element.getBottom().orElse(0))));

		// ensure message starting the execution would still have some room
		fixLocation();

		// Publish this ownership change in the dependency context for other commands to find
		PendingChildData.setPendingChild(newOwner, element);
		// And vertical extent change
		PendingVerticalExtentData.setPendingVerticalExtent(element, this.top, this.bottom);
	}

	private void fixLocation() {
		MElement<?> target = getTarget();
		if (MExecution.class.isInstance(target)) {
			MExecution execution = MExecution.class.cast(target);
			boolean wasSelfMessage = wasStartingSelfMessage(execution);
			boolean isSelfMessage = isStartingSelfMessage(execution);

			if (!wasSelfMessage && isSelfMessage) {
				if (top.isPresent()) {
					// check if there is already a setCoveredCommand on the starting message
					if (!execution.getStart().filter(occ -> hasDependency(occ, SetCoveredCommand.class))
							.isPresent()) {
						top = OptionalInt.of(top.getAsInt()
								+ layoutHelper().getConstraints().getMinimumHeight(ViewTypes.MESSAGE));
					}
				}
				if (bottom.isPresent()) {
					if (!execution.getStart().filter(occ -> hasDependency(occ, SetCoveredCommand.class))
							.isPresent()) {
						bottom = OptionalInt.of(bottom.getAsInt()
								+ layoutHelper().getConstraints().getMinimumHeight(ViewTypes.MESSAGE));
					}
				} else {
					// bottom should still be set.
					// to compute it, compute original height + new top position
					if (top.isPresent()) {
						bottom = OptionalInt.of(top.getAsInt() + execution.getBottom().getAsInt()
								- execution.getTop().getAsInt());
					}
				}
			} else if (wasSelfMessage && !isSelfMessage) {
				if (top.isPresent()) {
					if (!execution.getStart().filter(occ -> hasDependency(occ, SetCoveredCommand.class))
							.isPresent()) {
						top = OptionalInt.of(top.getAsInt()
								- layoutHelper().getConstraints().getMinimumHeight(ViewTypes.MESSAGE));
					}
				}
				if (bottom.isPresent()) {
					if (!execution.getStart().filter(occ -> hasDependency(occ, SetCoveredCommand.class))
							.isPresent()) {
						bottom = OptionalInt.of(bottom.getAsInt()
								- layoutHelper().getConstraints().getMinimumHeight(ViewTypes.MESSAGE));
					}
				} else {
					bottom = OptionalInt.of(top.getAsInt() + execution.getBottom().getAsInt()
							- execution.getTop().getAsInt());
				}
			}
		}
	}

	protected boolean isChangingOwner() {
		MElement<?> owner = getTarget().getOwner();
		return owner.getElement() != newOwner.getElement();
	}

	protected boolean isChangingNesting() {
		return (getTarget() instanceof MExecution)
				&& PendingNestedData.getPendingNestingExecution((MExecution)getTarget()).isPresent();
	}

	protected boolean isChangingPosition() {
		return (top.isPresent() && !getTarget().getTop().equals(top))
				|| (bottom.isPresent() && !getTarget().getBottom().equals(bottom));
	}

	@Override
	protected Command doCreateCommand() {
		return new SequenceDiagramSwitch<Command>() {
			@Override
			public Command caseMExecution(MExecution execution) {
				Command result = createCommand(execution, (MLifeline)newOwner);

				ensurePadding();

				return result;
			}

			@Override
			public Command defaultCase(EObject object) {
				return bomb();
			}
		}.doSwitch((EObject)getTarget());
	}

	protected Command createCommand(MExecution execution, MLifeline lifeline) {
		InteractionFragment fragment = execution.getElement();

		Command result = isChangingOwner()
				? semanticHelper().set(fragment, UMLPackage.Literals.INTERACTION_FRAGMENT__COVERED,
						// Set the entire covereds list to be just this lifeline
						// (executions only cover one lifeline)
						singletonList(lifeline.getElement()))
				: IdentityCommand.INSTANCE;

		// Handle the notation before dependencies so that dependencies will see the notation update
		Optional<Shape> executionShape = execution.getDiagramView();
		Optional<Shape> lifelineHead = lifeline.getDiagramView();
		if (executionShape.isPresent() && lifelineHead.isPresent()) {
			Shape lifelineView = diagramHelper().getLifelineBodyShape(lifelineHead.get());
			int newTop = top.orElseGet(() -> execution.getTop().getAsInt());
			int newBottom = bottom.orElseGet(() -> execution.getBottom().getAsInt());

			if (isChangingPosition() || isChangingOwner() || isChangingNesting()) {
				// Handle new parent shape (moving to/from nested condition)
				Optional<Shape> nestingExecution = executionShapeAt(lifeline, newTop, equalTo(execution));
				Shape newParent = nestingExecution.orElse(lifelineView);
				result = chain(result, diagramHelper().reparentView(executionShape.get(), newParent));

				// Position it later, as the relative position of the execution may then be different
				// according to the new lifeline's creation position
				result = chain(result, layoutHelper().setTop(executionShape.get(), () -> newTop));
				result = chain(result, layoutHelper().setBottom(executionShape.get(), () -> newBottom));
			}
		}

		// Handle dependent occurrences and other elements
		result = dependencies(execution, lifeline).map(chaining(result)).orElse(result);

		return result;
	}

	/**
	 * Create a command to update dependencies of the {@code execution}.
	 * 
	 * @param execution
	 *            the execution
	 * @param lifeline
	 *            the lifeline that is to be its owner (which could be its current owner)
	 * @return the dependencies command
	 */
	protected Optional<Command> dependencies(MExecution execution, MLifeline lifeline) {
		// Are we moving the execution or one of its start/finish occurrences? We're moving an
		// occurrence if one of them already has a SetCoveredCommand
		Optional<MOccurrence<?>> start = execution.getStart();
		Optional<MOccurrence<?>> finish = execution.getFinish();
		boolean movingExecution = !(start.isPresent() && hasDependency(start.get(), SetCoveredCommand.class))
				&& !(finish.isPresent() && hasDependency(finish.get(), SetCoveredCommand.class));

		Optional<Command> result = Optional.empty();

		// Compute not only currently spanned occurrences that will need updating, but
		// also future spanned occurrences
		if (movingExecution) {
			int deltaTop = map(this.top, t -> t - execution.getTop().getAsInt()).orElse(0);
			UnaryOperator<OptionalInt> topMapping = deltaTop == 0 ? UnaryOperator.identity()
					: top_ -> map(top_, t -> t + deltaTop);

			// Those occurrences that are *currently* spanned move with the execution, but those
			// that *will be* spanned do not; they only must be reattached per the step below
			result = execution.getOccurrences().stream()
					.map(occ -> occ.setCovered(lifeline, topMapping.apply(occ.getTop()))).reduce(chaining());

			if (isStartingSelfMessage(execution)) {
				// specific case of a self message starting the execution => it should also be moved of delta
				final Optional<Command> moveInitial = execution.getStart().map(MMessageEnd.class::cast)
						.flatMap(MMessageEnd::getOtherEnd)
						.map(occ -> occ.setCovered(lifeline, topMapping.apply(occ.getTop())));

				if (moveInitial.isPresent()) {
					if (result.isPresent()) {
						result.get().chain(moveInitial.get());
					} else {
						result = moveInitial;
					}
				}
			}

		} else if (isChangingPosition()) {
			if (!isChangingOwner()) {
				// We are reshaping it. Refresh nested executions, if necessary
				result = execution.getNestedExecutions().stream()
						.filter(PendingVerticalExtentData.spannedBy(execution).negate())
						.peek(nested -> setPendingNested(PendingNestedData.NO_EXECUTION, nested))
						.map(nested -> nested.setOwner(lifeline, nested.getTop(), nested.getBottom()))
						.filter(Objects::nonNull).reduce(chaining());
			} else {
				// we are moving and changing owner. Receive event of the finish message may also be move down
				// from some pixels because of becoming a self message.
				int deltaTop = map(this.top, t -> t - execution.getTop().getAsInt()).orElse(0);
				UnaryOperator<OptionalInt> topMapping = deltaTop == 0 ? UnaryOperator.identity()
						: top_ -> map(top_, t -> t + deltaTop);

				result = execution.getOccurrences().stream()
						.filter(occ -> !occ.equals(execution.getFinish().orElse(null)))
						.map(occ -> occ.setCovered(lifeline, topMapping.apply(occ.getTop())))
						.filter(Objects::nonNull).reduce(chaining());
				boolean isSelfMessage = isStartingSelfMessage(execution);
				boolean wasSelfMessage = wasStartingSelfMessage(execution);
				if (isSelfMessage && !wasSelfMessage) {
					UnaryOperator<OptionalInt> topFinishMapping = deltaTop == 0 ? UnaryOperator.identity()
							: top_ -> map(top_, t -> t + deltaTop
									+ layoutHelper().getConstraints().getMinimumHeight(ViewTypes.MESSAGE));
					final Optional<Command> last = execution.getFinish()
							.map(occ -> occ.setCovered(lifeline, topFinishMapping.apply(occ.getTop())));
					if (result.isPresent()) {
						if (last.isPresent()) {
							result.get().chain(last.get());
						}
					} else {
						result = last;
					}
				} else if (!isSelfMessage && wasSelfMessage) {
					UnaryOperator<OptionalInt> topFinishMapping = deltaTop == 0 ? UnaryOperator.identity()
							: top_ -> map(top_, t -> t// + deltaTop
									- layoutHelper().getConstraints().getMinimumHeight(ViewTypes.MESSAGE));
					final Optional<Command> last = execution.getFinish()
							.map(occ -> occ.setCovered(lifeline, topFinishMapping.apply(occ.getTop())));
					if (result.isPresent()) {
						if (last.isPresent()) {
							result.get().chain(last.get());
						}
					} else {
						result = last;
					}
				} else {
					UnaryOperator<OptionalInt> topFinishMapping = deltaTop == 0 ? UnaryOperator.identity()
							: top_ -> map(top_, t -> t + deltaTop);
					final Optional<Command> last = execution.getFinish()
							.map(occ -> occ.setCovered(lifeline, topFinishMapping.apply(occ.getTop())));
					result.map(cc -> last.map(l -> cc.chain(l))).orElseGet(() -> last);
				}
			}
		}

		// Note that nested executions will be handled implicitly by either their start or finish.
		Optional<Command> reattach = affectedOccurrences(execution).map(occ -> {
			// If our 'execution' is now nesting another, let it know that
			Optionals.elseMaybe(occ.getStartedExecution(), occ.getFinishedExecution())
					.ifPresent(nested -> PendingNestedData.setPendingNested(execution, nested));

			OptionalInt where = occ.getTop();
			return defer(() -> occ.setCovered(lifeline, where));
		}).reduce(chaining());

		result = Optionals.stream(result, reattach).reduce(chaining());

		return result;
	}

	private boolean isStartingSelfMessage(MExecution execution) {
		Optional<MLifeline> sendStartLL = Executions.getStartMessage(execution).flatMap(MMessage::getSend)
				.flatMap(MMessageEnd::getCovered);
		return as(Optional.of(newOwner), MLifeline.class).map(MElement::getElement)
				.equals(sendStartLL.map(MElement::getElement));
	}

	private boolean wasStartingSelfMessage(MExecution execution) {
		Optional<MLifeline> sendStartLL = Executions.getStartMessage(execution).flatMap(MMessage::getSend)
				.flatMap(MMessageEnd::getCovered);
		if (!sendStartLL.isPresent()) {
			return false;
		}
		return sendStartLL.equals(Executions.getStartMessage(execution).flatMap(MMessage::getReceive)
				.flatMap(MMessageEnd::getCovered));

	}

	protected void ensurePadding() {
		// From which element do we need to ensure padding?
		MElement<? extends Element> padFrom = getTarget();

		// Do we have an element that needs padding before it?
		MElement<? extends Element> nudge = nudgeElement.orElse(null);

		DeferredPaddingCommand.get(padFrom).pad(padFrom, nudge);
	}

}
