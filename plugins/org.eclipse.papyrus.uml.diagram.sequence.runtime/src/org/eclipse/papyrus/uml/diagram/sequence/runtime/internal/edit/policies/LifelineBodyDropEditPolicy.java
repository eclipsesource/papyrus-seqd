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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils.getChangeBoundsRequest;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMap;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.map;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.mapToInt;

import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.util.Optionals;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;

/**
 * Edit policy for dropping elements onto the lifeline body.
 */
public class LifelineBodyDropEditPolicy extends DragDropEditPolicy implements ISequenceEditPolicy {

	/**
	 * Initializes me.
	 */
	public LifelineBodyDropEditPolicy() {
		super();
	}

	@Override
	protected Command getDropElementCommand(EObject element, DropObjectsRequest request) {
		Command result = null;

		if (element instanceof ExecutionSpecification) {
			Optional<MExecution> exec = Optionals
					.as(getInteraction().getElement((ExecutionSpecification)element), MExecution.class);
			return exec.map(e -> getDropExecutionCommand(e, request)).orElse(null);
		}

		return result;
	}

	/**
	 * Obtain a command that drops an {@code execution} specification onto the host lifeline.
	 * 
	 * @param execution
	 *            an execution specification to drop
	 * @param request
	 *            the drop request
	 * @return the command, or {@code null} if none
	 */
	protected Command getDropExecutionCommand(MExecution execution, DropObjectsRequest request) {
		Optional<MLifeline> lifeline = getHostLifeline();

		// FIXME: Must allow non-reordering changes
		if (!PrivateRequestUtils.isAllowSemanticReordering(request)) {
			// Block the operation if the semantic override modifier is not applied
			return UnexecutableCommand.INSTANCE;
		}

		Optional<Point> changeBounds = Optional.ofNullable(getChangeBoundsRequest(request))
				.map(ChangeBoundsRequest::getMoveDelta);
		OptionalInt deltaY = mapToInt(changeBounds, Point::y);
		OptionalInt top = flatMap(execution.getTop(), t -> map(deltaY, y -> t + y));
		OptionalInt bottom = flatMap(execution.getBottom(), b -> map(deltaY, y -> b + y));

		return lifeline.map(ll -> wrap(execution.setOwner(ll, top, bottom))).orElse(null);
	}

	protected Optional<MLifeline> getHostLifeline() {
		Optional<Lifeline> lifeline = as(Optional.ofNullable(getHostObject()), Lifeline.class);
		return lifeline.flatMap(getInteraction()::getLifeline);
	}
}
