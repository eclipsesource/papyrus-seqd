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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
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
		Set<MMessage> messagesToRemove = new LinkedHashSet<>();
		getMessageIfPresent(execution.getStart()).ifPresent(m -> messagesToRemove.add(m));
		for (MMessage message : execution.getInteraction().getMessages()) {
			/* remove messages which have the deleted execution as a source or target */
			Optional<Connector> diagramView = message.getDiagramView();
			if (!diagramView.isPresent()) {
				continue;
			}
			if (diagramView.get().getSource() == execution.getDiagramView().orElse(null)) {
				messagesToRemove.add(message);
				continue;
			}
			if (diagramView.get().getTarget() == execution.getDiagramView().orElse(null)) {
				messagesToRemove.add(message);
				continue;
			}
		}
		getMessageIfPresent(execution.getFinish()).ifPresent(m -> messagesToRemove.add(m));

		List<RemovalCommand<Element>> removalCommands = new ArrayList<>(3);
		messagesToRemove.forEach(
				m -> removalCommands.add(new RemoveMessageCommand((MMessageImpl)m, getTarget(), false)));

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

	private Optional<MMessage> getMessageIfPresent(Optional<MOccurrence<?>> occurrence) {
		if (occurrence.isPresent()) {
			MElement<?> owner = occurrence.get().getOwner();
			if (MMessage.class.isInstance(owner)) {
				return Optional.of((MMessage)owner);
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
