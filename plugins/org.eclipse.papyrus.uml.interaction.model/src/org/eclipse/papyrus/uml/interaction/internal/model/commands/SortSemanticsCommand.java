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
import java.util.function.Function;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelOrdering;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;

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

	@Override
	protected Command createCommand() {
		// Assume initially that the fragments are already in order
		Command result = IdentityCommand.INSTANCE;

		Interaction owner = getTarget().getElement();
		boolean[] sortNeeded = {false };

		@SuppressWarnings("serial")
		EList<InteractionFragment> fragments = new BasicEList<InteractionFragment>(owner.getFragments()) {
			@Override
			protected void didChange() {
				sortNeeded[0] = true;
			}
		};

		// Compute the sorted order of the fragments
		Function<InteractionFragment, MElement<?>> logicalModelFunction = fragment -> getTarget()
				.getElement(fragment).orElse(null);
		Comparator<InteractionFragment> ordering = Comparator.comparing(logicalModelFunction,
				LogicalModelOrdering.semantically());
		ECollections.sort(fragments, ordering);

		if (sortNeeded[0]) {
			// Build the sort command
			result = new AbstractCommand(LogicalModelPlugin.INSTANCE.getString("sortCommand.label"), //$NON-NLS-1$
					LogicalModelPlugin.INSTANCE.getString("sortCommand.desc")) { //$NON-NLS-1$

				private EList<InteractionFragment> ownerList;

				private EList<InteractionFragment> oldValue;

				@Override
				protected boolean prepare() {
					ownerList = owner.getFragments();
					oldValue = ECollections.newBasicEList(ownerList);

					return true;
				}

				@Override
				public void execute() {
					ECollections.setEList(ownerList, fragments);
				}

				@Override
				public void undo() {
					ECollections.setEList(ownerList, oldValue);
				}

				@Override
				public void redo() {
					execute();
				}

			};
		}

		return result;
	}

}
