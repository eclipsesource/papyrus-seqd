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

package org.eclipse.papyrus.uml.diagram.sequence.examples.graph;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MObject;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.uml2.uml.Element;

/**
 * Handler for the example "Insert Space Below" command that uses the <em>Logical Model</em> to compute the
 * commands to make space for a new message below a selected message.
 *
 * @author Christian W. Damus
 */
public class InsertSpaceBelowHandler extends AbstractSequenceDiagramHandler {

	/**
	 * Initializes me.
	 */
	public InsertSpaceBelowHandler() {
		super();
	}

	@Override
	protected Optional<Command> getCommand(MElement<?> element) {
		SequenceDiagramSwitch<Command> commandSwitch = new SequenceDiagramSwitch<Command>() {
			@Override
			public Command caseMMessage(MMessage message) {
				// Make space below the receive event
				Optional<MMessageEnd> receive = message.getReceive();

				// If this starts an execution specification, don't move the exec spec down.
				// We will instead be stretching it
				Optional<MExecution> exec = receive.flatMap(MMessageEnd::getStartedExecution);
				Optional<MElement<? extends Element>> toNudge = exec.flatMap(MElement::following);
				if (!toNudge.isPresent()) {
					toNudge = receive.flatMap(MElement::following);
				}

				return toNudge.map(e -> e.nudge(15)).orElse(UnexecutableCommand.INSTANCE);
			}

			@Override
			public Command defaultCase(MObject ignore) {
				return UnexecutableCommand.INSTANCE;
			}
		};

		return Optional.of(commandSwitch.doSwitch(element));
	}

}
