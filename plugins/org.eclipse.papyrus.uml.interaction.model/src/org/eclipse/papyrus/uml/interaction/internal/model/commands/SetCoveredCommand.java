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
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelOrdering.vertically;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl;
import org.eclipse.papyrus.uml.interaction.model.MDestruction;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Assignment of the lifeline covered by an occurrence.
 */
public class SetCoveredCommand extends ModelCommandWithDependencies<MOccurrenceImpl<? extends Element>> {

	private final MLifeline lifeline;

	private final OptionalInt yPosition;

	/**
	 * Initializes me.
	 *
	 * @param target
	 */
	public SetCoveredCommand(MOccurrenceImpl<? extends Element> end, MLifeline lifeline,
			OptionalInt yPosition) {
		super(end);

		this.lifeline = lifeline;
		this.yPosition = yPosition;
	}

	protected boolean isChangingLifeline() {
		Optional<MLifeline> covered = getTarget().getCovered();
		return !covered.isPresent() || (covered.get().getElement() != lifeline.getElement());
	}

	protected boolean isChangingPosition() {
		return yPosition.isPresent() && !getTarget().getTop().equals(yPosition);
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
			Shape lifelineView = diagramHelper().getLifelineBodyShape(lifeline.getDiagramView().get());
			Shape destructionView = (Shape)vertex(destruction).getDiagramView();
			Connector messageView = Optional.ofNullable(destruction.getOwner())
					.flatMap(MMessage::getDiagramView).orElse(null);

			int newYPosition = yPosition.orElseGet(() -> destruction.getTop().getAsInt());

			// Move the X shape
			result = result.chain(diagramHelper().reconnectDestructionOccurrenceShape(destructionView,
					messageView, lifelineView, newYPosition));
		}

		// Handle a dependent execution
		result = dependencies(getTarget()).map(result::chain).orElse(result);

		return result;
	}

	protected Optional<Command> dependencies(MOccurrence<? extends Element> occurrence) {
		List<Command> result = new ArrayList<>();

		occurrence.getStartedExecution().map(exec -> exec.setOwner(lifeline, yPosition))
				.ifPresent(result::add);
		occurrence.getFinishedExecution()
				.map(exec -> exec.setOwner(lifeline,
						map(yPosition, y -> y - layoutHelper().getHeight(exec.getDiagramView().get()))))
				.ifPresent(result::add);

		as(Optional.of(occurrence), MMessageEnd.class)
				.filter(end -> isChangingLifeline() || isChangingPosition()).ifPresent(end -> {

					// Destructions are handled differently
					if (!(end instanceof MDestruction)) {
						MMessage message = end.getOwner();
						Optional<Connector> edge = message.getDiagramView();
						edge.ifPresent(connector -> {
							Shape lifelineBody = diagramHelper()
									.getLifelineBodyShape(lifeline.getDiagramView().get());
							int newYPosition = yPosition.orElseGet(() -> end.getTop().getAsInt());

							// Are we connecting to an execution specification?
							Optional<MExecution> execution = executionAt(lifeline, newYPosition);
							Shape newAttachedShape = execution.flatMap(MExecution::getDiagramView)
									.orElse(lifelineBody);

							if (end.isSend()) {
								result.add(diagramHelper().reconnectSource(connector, newAttachedShape,
										newYPosition));
							} else if (end.isReceive()) {
								result.add(diagramHelper().reconnectTarget(connector, newAttachedShape,
										newYPosition));
							} // else don't know what to do with it
						});
					}
				});

		return result.stream().reduce(Command::chain);
	}

	static Optional<MExecution> executionAt(MLifeline lifeline, int absoluteY) {
		// An execution that is above and below a reference location straddles it
		return lifeline.getExecutions().stream().filter(above(absoluteY).and(below(absoluteY)))
				.max(vertically());
	}
}
