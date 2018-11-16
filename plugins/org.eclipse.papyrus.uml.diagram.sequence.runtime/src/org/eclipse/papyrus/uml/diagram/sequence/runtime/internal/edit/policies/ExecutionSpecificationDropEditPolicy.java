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

import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;

import java.util.Optional;

import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.uml2.uml.ExecutionSpecification;

/**
 * Edit policy for dropping elements onto an execution specification.
 */
public class ExecutionSpecificationDropEditPolicy extends LifelineBodyDropEditPolicy {

	/**
	 * Initializes me.
	 */
	public ExecutionSpecificationDropEditPolicy() {
		super();
	}

	@Override
	protected Optional<MLifeline> getHostLifeline() {
		Optional<ExecutionSpecification> execution = as(Optional.ofNullable(getHostObject()),
				ExecutionSpecification.class);
		return as(execution.flatMap(getInteraction()::getElement), MExecution.class)
				.map(MExecution::getOwner);
	}
}
