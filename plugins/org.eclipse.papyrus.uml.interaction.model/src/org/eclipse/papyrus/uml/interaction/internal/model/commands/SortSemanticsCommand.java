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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelOrdering;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A command that establishes correct semantic ordering of the {@link Interaction#getFragments() fragments} of
 * the interaction according to the visual timeline.
 */
public class SortSemanticsCommand extends ModelCommand<MInteractionImpl> {

	/**
	 * Initializes me with the {@code interaction} whose fragments I sort.
	 */
	public SortSemanticsCommand(MInteractionImpl interaction) {
		super(interaction);
	}

	@Override
	protected boolean prepare() {
		// Don't create the command, yet. We can always do something
		return true;
	}

	@Override
	public void execute() {
		if (super.prepare()) {
			super.execute();
		}
	}

	@Override
	public void redo() {
		// We have to compute the command again in case we were first executed
		// for calculation of a pessimistic compound's executability and that
		// was only a portion of the overall operation
		command = null;
		execute();
	}

	@SuppressWarnings("boxing")
	@Override
	protected Command createCommand() {
		Interaction owner = getTarget().getElement();
		List<InteractionFragment> fragments = owner.getFragments();

		// Compute the sorted order of the fragments
		Function<InteractionFragment, MElement<?>> logicalModelFunction = fragment -> getTarget()
				.getElement(fragment).orElse(null);
		Comparator<InteractionFragment> ordering = Comparator.comparing(logicalModelFunction,
				LogicalModelOrdering.semantically());

		AtomicInteger counter = new AtomicInteger(0);
		Map<InteractionFragment, Integer> indexMap = fragments.stream().sorted(ordering).sequential()
				.collect(Collectors.toMap(Function.identity(), __ -> counter.getAndIncrement()));

		// Build the sort command
		CompoundCommand result = new CompoundCommand(
				LogicalModelPlugin.INSTANCE.getString("sortCommand.label"), //$NON-NLS-1$
				LogicalModelPlugin.INSTANCE.getString("sortCommand.desc")); //$NON-NLS-1$

		for (int i = 0; i < fragments.size(); i++) {
			InteractionFragment next = fragments.get(i);
			int index = indexMap.get(next).intValue();

			if (index != i) {
				// Fragment isn't in its correct place
				result.append(MoveCommand.create(getEditingDomain(), owner,
						UMLPackage.Literals.INTERACTION__FRAGMENT, next, index));
			}
		}

		return result.isEmpty() ? /* Nothing to do */ IdentityCommand.INSTANCE : result;
	}

}
