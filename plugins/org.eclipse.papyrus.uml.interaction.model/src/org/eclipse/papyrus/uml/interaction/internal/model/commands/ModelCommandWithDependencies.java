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
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.uml2.uml.Element;

/**
 * Partial implementation of a mutation operation on the logical model.
 *
 * @author Christian W. Damus
 * @param <T>
 *            the target of the operation
 */
public abstract class ModelCommandWithDependencies<T extends MElementImpl<?>> extends ModelCommand<T> {

	private final DependencyContext deps = DependencyContext.get();

	/**
	 * Initializes me.
	 * 
	 * @param target
	 *            the logical model element on which I operate
	 */
	public ModelCommandWithDependencies(T target) {
		super(target);
	}

	@Override
	protected final Command createCommand() {
		return deps.withContext(() -> doCreateCommand());
	}

	protected abstract Command doCreateCommand();

	//
	// Nested types
	//

	public static abstract class Creation<T extends MElementImpl<?>, U extends Element> extends ModelCommandWithDependencies<T> implements CreationCommand<U> {
		private final Class<? extends U> type;

		private CreationCommand<U> resultCommand;

		/**
		 * Initializes me.
		 *
		 * @param target
		 *            the logical model element on which I operate
		 * @param type
		 *            the type of UML element that I create
		 */
		public Creation(T target, Class<? extends U> type) {
			super(target);

			this.type = type;
		}

		@Override
		public CreationCommand<U> chain(Command next) {
			return andThen(getEditingDomain(), next);
		}

		@Override
		public Class<? extends U> getType() {
			return type;
		}

		@Override
		public U getNewObject() {
			return (resultCommand == null) ? null : resultCommand.get();
		}

		protected CreationCommand<U> setResult(CreationCommand<U> resultCommand) {
			this.resultCommand = resultCommand;
			return resultCommand;
		}

	}

}
