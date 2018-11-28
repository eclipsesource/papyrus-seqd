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
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.ElementRemovalCommandImpl;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;

/**
 * An lifeline removal operation.
 *
 * @author Johannes Faltermeier
 */
public class RemoveLifelineCommand extends ModelCommand<MLifelineImpl> implements RemovalCommand<Element> {

	private RemovalCommand<Element> delegate;

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

		List<RemovalCommand<Element>> removalCommands = new ArrayList<>(lifeline.getExecutions().size() + 1);

		/* collect non execution related messages */
		Set<MessageOccurrenceSpecification> messageEndsToDelete = new LinkedHashSet<>(
				lifeline.getElement().getCoveredBys().stream()//
						.filter(MessageOccurrenceSpecification.class::isInstance)//
						.map(MessageOccurrenceSpecification.class::cast)//
						.collect(Collectors.toSet()));

		/* remove executions (only first level, others will be removed by dependency */

		lifeline.getFirstLevelExecutions().forEach(e -> {
			e.getStart().ifPresent(o -> messageEndsToDelete.remove(o.getElement()));
			e.getFinish().ifPresent(o -> messageEndsToDelete.remove(o.getElement()));
			removalCommands.add(new RemoveExecutionCommand((MExecutionImpl)e, false));
		});

		/* remove non execution messages */
		messageEndsToDelete.stream()//
				.map(mos -> mos.getMessage())//
				.map(m -> lifeline.getInteraction().getMessage(m))//
				.filter(m -> m.isPresent())//
				.map(m -> m.get())//
				.collect(Collectors.toSet())//
				.forEach(m -> removalCommands.add(new RemoveMessageCommand((MMessageImpl)m, false)));

		/* semantics */
		SemanticHelper semantics = semanticHelper();
		removalCommands.add(semantics.deleteLifeline(lifeline.getElement()));

		delegate = new ElementRemovalCommandImpl(getEditingDomain(), removalCommands);

		/* diagram */
		DiagramHelper diagrams = diagramHelper();
		Command diagramsResultCommand = diagrams.deleteView(lifeline.getDiagramView().get());

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

	@Override
	public Collection<Element> getElementsToRemove() {
		if (delegate == null) {
			return Collections.emptySet();
		}
		return delegate.getElementsToRemove();
	}

	@Override
	public RemovalCommand<Element> chain(Command next) {
		return andThen(getTarget().getEditingDomain(), next);
	}

}
