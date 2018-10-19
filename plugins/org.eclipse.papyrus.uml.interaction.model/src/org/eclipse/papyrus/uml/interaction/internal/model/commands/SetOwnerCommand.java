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
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.util.Lifelines;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Assignment of the owner of an element.
 */
public class SetOwnerCommand extends ModelCommandWithDependencies<MElementImpl<? extends Element>> {

	private final MElement<? extends Element> newOwner;

	private final OptionalInt top;

	private final OptionalInt bottom;

	// The element on the lifeline before which we're inserting our element, if the new owner is a lifeline
	private final Optional<MElement<? extends Element>> nextOnLifeline;

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

		nextOnLifeline = as(Optional.of(newOwner), MLifeline.class).flatMap(lifeline -> Lifelines
				.elementAfterAbsolute(lifeline, top.orElseGet(() -> element.getTop().orElse(0))));
	}

	protected boolean isChangingOwner() {
		MElement<?> owner = getTarget().getOwner();
		return owner.getElement() != newOwner.getElement();
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

				if (isChangingOwner()) {
					ensurePadding();
				}

				return result;
			}

			@Override
			public Command defaultCase(EObject object) {
				return UnexecutableCommand.INSTANCE;
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

			if (isChangingOwner()) {
				// Move the execution shape
				result = chain(result, diagramHelper().reparentView(executionShape.get(), lifelineView));
			}

			if (isChangingPosition() || isChangingOwner()) {
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

	protected Optional<Command> dependencies(MExecution execution, MLifeline lifeline) {
		List<Command> result = new ArrayList<>();

		// Occurrences spanned by the execution, including its start and finish. They move
		// according to the execution, maintaining their relative position. Note that
		// nested executions will be handled implicitly by either their start or finish
		execution.getOccurrences().stream().map(occ -> occ.setCovered(lifeline, occ.getTop()))
				// occ -> new SetCoveredCommand((MOccurrenceImpl<? extends Element>)occ, lifeline,
				// occ.getTop()))
				.filter(Objects::nonNull).forEach(result::add);

		return result.stream().reduce(chaining());
	}

	protected void ensurePadding() {
		MElement<? extends Element> element = getTarget();
		// From which element do we need to ensure padding?
		MElement<? extends Element> padFrom = element instanceof MMessageEnd
				? ((MMessageEnd)element).getOwner()
				: element;

		// Do we have an element that needs padding before it?
		MElement<? extends Element> nudge = nextOnLifeline.orElse(null);

		DeferredPaddingCommand.get(element).padFrom(padFrom).nudge(nudge);
	}

}
