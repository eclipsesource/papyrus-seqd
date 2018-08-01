/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.AbstractSequenceGraphicalNodeEditPolicy.StartMessageCommand;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.CreateRequestSwitch;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.uml2.uml.Lifeline;

/**
 * The {@code LifelineHeaderGraphicalNodeEditPolicy} type.
 *
 * @author Johannes Faltermeier
 */
public class LifelineHeaderGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy implements ISequenceEditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		return new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateConnectionViewRequest(CreateConnectionViewRequest request) {
				StartMessageCommand start = (StartMessageCommand)request.getStartCommand();
				org.eclipse.emf.common.command.Command result;

				MLifeline sender = start.sender;
				MInteraction interaction = sender.getInteraction();
				MLifeline receiver = interaction.getLifeline(getHost().getAdapter(Lifeline.class)).get();

				switch (start.sort) {
					case CREATE_MESSAGE_LITERAL:
						result = sender.insertMessageAfter(start.before.orElse(sender), start.offset,
								receiver, start.sort, null);
						break;
					default:
						result = UnexecutableCommand.INSTANCE;
						break;
				}

				return wrap(result);
			}
		}.doSwitch(request);
	}

}
