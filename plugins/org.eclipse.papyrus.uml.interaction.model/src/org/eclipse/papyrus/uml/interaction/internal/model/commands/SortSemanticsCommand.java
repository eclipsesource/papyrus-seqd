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
import org.eclipse.emf.common.util.BasicEList.UnmodifiableEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelOrdering;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;

/**
 * A command that establishes correct semantic ordering of the {@link Interaction#getFragments() fragments} of
 * the interaction according to the visual timeline.
 */
public class SortSemanticsCommand extends AbstractCommand {

	private MInteraction interaction;

	private EList<InteractionFragment> ownerList;

	private EList<InteractionFragment> value;

	/**
	 * Initializes me with the {@code interaction}
	 */
	public SortSemanticsCommand(MInteraction interaction) {
		super(LogicalModelPlugin.INSTANCE.getString("sortCommand.label"), //$NON-NLS-1$
				LogicalModelPlugin.INSTANCE.getString("sortCommand.desc")); //$NON-NLS-1$

		this.interaction = interaction;
		this.ownerList = interaction.getElement().getFragments();
	}

	@Override
	protected boolean prepare() {
		value = new UnmodifiableEList<>(ownerList.size(), ownerList.toArray());

		return true;
	}

	@Override
	public void execute() {
		Function<InteractionFragment, MElement<?>> logicalModelFunction = fragment -> interaction
				.getElement(fragment).orElse(null);
		Comparator<InteractionFragment> ordering = Comparator.comparing(logicalModelFunction,
				LogicalModelOrdering.semantically());

		ECollections.sort(ownerList, ordering);
	}

	protected void applyAndReverse() {
		EList<InteractionFragment> newValue = new UnmodifiableEList<>(ownerList.size(), ownerList.toArray());
		ECollections.setEList(ownerList, value);
		value = newValue;
	}

	@Override
	public void undo() {
		applyAndReverse();
	}

	@Override
	public void redo() {
		applyAndReverse();
	}

}
