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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.ElementRemovalCommandImpl;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.uml2.uml.Element;

/**
 * An execution removal operation.
 *
 * @author Johannes Faltermeier
 */
public class RemoveExecutionCommand extends ModelCommand<MExecutionImpl> implements RemovalCommand<Element> {

	private RemovalCommand<Element> delegate;

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
		List<RemovalCommand<Element>> removalCommands = new ArrayList<>(3);
		removeMessageIfPresent(execution.getStart()).ifPresent(c -> removalCommands.add(c));
		removeMessageIfPresent(execution.getFinish()).ifPresent(c -> removalCommands.add(c));

		/* semantics */
		SemanticHelper semantics = semanticHelper();
		removalCommands.add(semantics.deleteExecutionSpecification(execution.getElement()));

		delegate = new ElementRemovalCommandImpl(getEditingDomain(), removalCommands);

		/* diagram */
		DiagramHelper diagrams = diagramHelper();
		Command diagramsResultCommand = diagrams.deleteView(execution.getDiagramView().get());

		List<Command> allCommands = new ArrayList<>();
		/* nudge */
		if (nudge) {
			/* nudge before deletion, because otherwise we would have to recreate MInteraction */
			allCommands.add(new NudgeOnRemovalCommand(getEditingDomain(),
					(MInteractionImpl)getTarget().getInteraction(), getElementsToRemove()));
		}
		allCommands.add(delegate);
		allCommands.add(diagramsResultCommand);

		/* chain */
		return CompoundModelCommand.compose(getEditingDomain(), allCommands);
	}

	private Optional<RemovalCommand<Element>> removeMessageIfPresent(Optional<MOccurrence<?>> occurrence) {
		if (occurrence.isPresent()) {
			MElement<?> owner = occurrence.get().getOwner();
			if (MMessageImpl.class.isInstance(owner)) {
				return Optional.of(new RemoveMessageCommand((MMessageImpl)owner, getTarget(), false));
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<Element> getElementsToRemove() {
		if (delegate == null) {
			return Collections.emptySet();
		}
		return delegate.getElementsToRemove();
	}

}
