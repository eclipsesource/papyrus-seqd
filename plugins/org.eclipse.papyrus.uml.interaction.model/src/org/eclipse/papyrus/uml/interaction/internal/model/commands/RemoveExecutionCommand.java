/*****************************************************************************
 * Copyright (c) 2018 Johannes Faltermeier and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommandImpl;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;

/**
 * An execution removal operation.
 *
 * @author Johannes Faltermeier
 */
public class RemoveExecutionCommand extends ModelCommand<MExecutionImpl> implements RemovalCommand {

	private RemovalCommand delegate;

	private boolean nudge;

	/**
	 * @param executionToRemove
	 *            the {@link MExecutionImpl} which should be deleted
	 * @param nudge
	 *            whether this command will nudge remaining diagram elements
	 */
	public RemoveExecutionCommand(MExecutionImpl executionToRemove, boolean nudge) {
		super(executionToRemove);
		this.nudge = nudge;
		command = createCommand();
	}

	@Override
	protected Command createCommand() {
		MExecutionImpl execution = getTarget();

		/* remove messages */
		List<RemovalCommand> allCommands = new ArrayList<>(3);
		removeMessageIfPresent(execution.getStart()).ifPresent(c -> allCommands.add(c));
		removeMessageIfPresent(execution.getFinish()).ifPresent(c -> allCommands.add(c));

		/* semantics */
		SemanticHelper semantics = semanticHelper();
		allCommands.add(semantics.deleteExecutionSpecification(execution.getElement()));

		delegate = new RemovalCommandImpl(getEditingDomain(), allCommands);

		/* diagram */
		DiagramHelper diagrams = diagramHelper();
		Command diagramsResultCommand = diagrams.deleteView(execution.getDiagramView().get());

		/* chain */
		return CompoundModelCommand.compose(getEditingDomain(), delegate, diagramsResultCommand);
	}

	private Optional<RemovalCommand> removeMessageIfPresent(Optional<MOccurrence<?>> occurrence) {
		if (occurrence.isPresent()) {
			MElement<?> owner = occurrence.get().getOwner();
			if (MMessageImpl.class.isInstance(owner)) {
				return Optional.of(new RemoveMessageCommand((MMessageImpl)owner, getTarget(), false));
			}
		}
		return Optional.empty();
	}

	@Override
	public Set<EObject> getElementsToRemove() {
		if (delegate == null) {
			return Collections.emptySet();
		}
		return delegate.getElementsToRemove();
	}

}
