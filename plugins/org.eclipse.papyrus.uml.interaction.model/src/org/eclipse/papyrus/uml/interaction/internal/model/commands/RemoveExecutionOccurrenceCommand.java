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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionOccurrenceImpl;

/**
 * A command to remove an execution occurrence.
 */
public class RemoveExecutionOccurrenceCommand extends ModelCommand<MExecutionOccurrenceImpl> {

	/**
	 * Initializes me.
	 *
	 * @param target
	 *            the execution occurrence to remove
	 */
	public RemoveExecutionOccurrenceCommand(MExecutionOccurrenceImpl target) {
		super(target);
	}

	@Override
	protected Command createCommand() {
		// Execution occurrences don't have any notation to worry about
		return DeleteCommand.create(getEditingDomain(), getTarget().getElement());
	}
}
