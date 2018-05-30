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

package org.eclipse.papyrus.uml.interaction.model;

import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;

/**
 * Command which is supposed to delete model element. It offers methods to access the elements marked for
 * deletion.
 */
public interface RemovalCommand extends Command, Supplier<Set<EObject>> {

	/**
	 * @return the elements which are marked for removal when this command was created.
	 */
	Set<EObject> getElementsToRemove();

	@Override
	default Set<EObject> get() {
		return getElementsToRemove();
	}

	/**
	 * Unexecutable command which implements {@link RemovalCommand}.
	 */
	static class UnexecutableRemovalCommand extends CommandWrapper implements RemovalCommand {

		/**
		 * The singleton instance.
		 */
		public static final RemovalCommand INSTANCE = new UnexecutableRemovalCommand();

		private UnexecutableRemovalCommand() {
			super(UnexecutableCommand.INSTANCE);
		}

		@Override
		public Set<EObject> getElementsToRemove() {
			return Collections.emptySet();
		}

	}

}
