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

import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelOrdering.vertically;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.map;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MessageSort;

/**
 * A command that makes a lifeline be created at some Y coördinate in the sequence.
 */
public class SetLifelineCreationCommand extends ModelCommandWithDependencies<MLifelineImpl> {

	private final OptionalInt yPosition;

	public SetLifelineCreationCommand(MLifelineImpl target, OptionalInt yPosition) {
		super(target);

		this.yPosition = yPosition;
	}

	@Override
	protected Command doCreateCommand() {
		Shape lifelineHead = getTarget().getDiagramView().orElse(null);
		Shape lifelineBody = diagramHelper().getLifelineBodyShape(lifelineHead);
		int lifelineBodyTop = layoutHelper().getTop(lifelineBody);
		int lifelineHeadTop = layoutHelper().getTop(lifelineHead);

		// Move the lifeline to the requested location, or else the default top location of the diagram
		int movedLifelineTop = map(yPosition, y -> y - ((lifelineBodyTop - lifelineHeadTop) / 2))
				.orElseGet(() -> defaultTop(lifelineHead));

		int deltaTop = movedLifelineTop - layoutHelper().getTop(lifelineHead);

		// A command to set the top of the lifeline
		Command result = Create.nudgeCommand(getGraph(), getEditingDomain(), deltaTop, 0, getTarget());

		// Nudge up/down the second and subsequent occurrences on the lifeline.
		// Don't nudge execution occurrences because we nudge the executions, themselves.
		// And likewise don't nudge message ends and nested executions spanned by executions
		// that we nudge
		Stream<? extends MElement<? extends Element>> moveable = Stream.concat(//
				getTarget().getExecutions().stream(), // TODO: Filter nested executions
				getTarget().getMessageEnds().stream().filter(end -> !end.getExecution().isPresent()));
		Stream<? extends MElement<? extends Element>> toNudge = moveable.filter(isCreation().negate())
				.sorted(vertically().reversed());
		Optional<Command> nudges = toNudge //
				.map(elem -> (Command)new NudgeCommand((MElementImpl<? extends Element>)elem, -deltaTop,
						false))
				.reduce(chaining());

		result = nudges.map(chaining(result)).orElse(result);

		// Finally, we may need to nudge elements to make space for the new position of the lifeline
		// head or to remove space for a create message that was removed
		// Do we need to make room for the lifeline head?
		Optional<MOccurrence<? extends Element>> firstOccurrence = getTarget().getOccurrences().stream()
				.filter(isCreation().negate()) // But not the creation event (in case of re-orient)
				.sorted(vertically()).findFirst();
		firstOccurrence.ifPresent(pad -> DeferredPaddingCommand.get(pad).pad(getTarget(), pad));

		return result;
	}

	protected Predicate<MElement<? extends Element>> isCreation() {
		return elem -> (elem instanceof MMessageEnd) && ((MMessageEnd)elem).isReceive()
				&& (((MMessageEnd)elem).getOwner().getElement()
						.getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL);
	}

	protected int defaultTop(Shape lifelineHead) {
		return layoutHelper().toAbsoluteY(lifelineHead,
				layoutHelper().getConstraints().getYOffset(lifelineHead.getType()));
	}

}
