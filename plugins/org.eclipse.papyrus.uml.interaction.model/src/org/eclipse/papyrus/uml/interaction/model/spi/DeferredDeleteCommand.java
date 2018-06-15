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
package org.eclipse.papyrus.uml.interaction.model.spi;

import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;

/**
 * This is the {@code DeferredDeleteCommand} type. Enjoy.
 *
 * @author Johannes Faltermeier
 */
public class DeferredDeleteCommand extends CommandWrapper {

	private EditingDomain domain;

	private Supplier<? extends EObject> toDelete;

	/**
	 * Initializes me.
	 */
	public DeferredDeleteCommand(EditingDomain domain, Supplier<? extends EObject> toDelete) {
		super();

		this.domain = domain;
		this.toDelete = toDelete;
	}

	@Override
	protected Command createCommand() {
		return getSemanticHelper().delete(toDelete.get());
	}

	SemanticHelper getSemanticHelper() {
		return LogicalModelPlugin.getInstance().getSemanticHelper(domain);
	}

	@Override
	public Command chain(Command next) {
		return CompoundModelCommand.compose(domain, this, next);
	}

}
