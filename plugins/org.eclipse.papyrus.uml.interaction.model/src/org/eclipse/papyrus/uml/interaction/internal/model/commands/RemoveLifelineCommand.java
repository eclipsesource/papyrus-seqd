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
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommandImpl;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;

/**
 * An lifeline removal operation.
 *
 * @author Johannes Faltermeier
 */
public class RemoveLifelineCommand extends ModelCommand<MLifelineImpl> implements RemovalCommand {

	private RemovalCommand delegate;

	private boolean nudge;

	/**
	 * @param target
	 *            the {@link MLifelineImpl} to delete
	 * @param nudge
	 *            whether this command will nudge remaining diagram elements
	 */
	public RemoveLifelineCommand(MLifelineImpl target, boolean nudge) {
		super(target);
		this.nudge = nudge;
		command = createCommand();
	}

	@Override
	protected Command createCommand() {
		MLifelineImpl lifeline = getTarget();

		List<RemovalCommand> allCommands = new ArrayList<>(lifeline.getExecutions().size() + 1);

		/* collect non execution related messages */
		Set<MessageOccurrenceSpecification> messageEndsToDelete = new LinkedHashSet<MessageOccurrenceSpecification>(
				lifeline.getElement().getCoveredBys().stream()//
						.filter(MessageOccurrenceSpecification.class::isInstance)//
						.map(MessageOccurrenceSpecification.class::cast)//
						.collect(Collectors.toSet()));

		/* remove executions */
		lifeline.getExecutions().forEach(e -> {
			e.getStart().ifPresent(o -> messageEndsToDelete.remove(o.getElement()));
			e.getFinish().ifPresent(o -> messageEndsToDelete.remove(o.getElement()));
			allCommands.add(new RemoveExecutionCommand((MExecutionImpl)e, false));
		});

		/* remove non execution messages */
		messageEndsToDelete.stream()//
				.map(mos -> mos.getMessage())//
				.map(m -> lifeline.getInteraction().getMessage(m))//
				.filter(m -> m.isPresent())//
				.map(m -> m.get())//
				.collect(Collectors.toSet())//
				.forEach(m -> allCommands.add(new RemoveMessageCommand((MMessageImpl)m, false)));

		/* semantics */
		SemanticHelper semantics = semanticHelper();
		allCommands.add(semantics.deleteLifeline(lifeline.getElement()));

		delegate = new RemovalCommandImpl(getEditingDomain(), allCommands);

		/* diagram */
		DiagramHelper diagrams = diagramHelper();
		Command diagramsResultCommand = diagrams.deleteView(lifeline.getDiagramView().get());

		/* chain */
		return CompoundModelCommand.compose(getEditingDomain(), delegate, diagramsResultCommand);
	}

	@Override
	public Collection<EObject> getElementsToRemove() {
		if (delegate == null) {
			return Collections.emptySet();
		}
		return delegate.getElementsToRemove();
	}

}
