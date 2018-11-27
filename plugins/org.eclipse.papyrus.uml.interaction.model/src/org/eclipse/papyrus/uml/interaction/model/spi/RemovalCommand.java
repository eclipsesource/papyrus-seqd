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

import static org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand.compose;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Command which is supposed to delete model element. It offers methods to access the elements marked for
 * deletion.
 */
public interface RemovalCommand<T extends EObject> extends Command, Supplier<Collection<T>> {

	/**
	 * @return the elements which are marked for removal when this command was created.
	 */
	Collection<T> getElementsToRemove();

	@Override
	default Collection<T> get() {
		return getElementsToRemove();
	}

	/**
	 * Obtain a view of myself that, after I am executed, follows up with the {@code next} command and returns
	 * my own result as its removal result.
	 * 
	 * @param domain
	 *            the contextual editing domain
	 * @param next
	 *            the next command to execute
	 * @return myself, with follow-up
	 */
	default RemovalCommand<T> andThen(EditingDomain domain, Command next) {
		class AndThen extends CommandWrapper implements RemovalCommand<T> {
			// Anticipate a small number of follow-ups
			private final List<Command> toCompose = new ArrayList<>(3);

			AndThen(Command followUp) {
				super();

				toCompose.add(followUp);
			}

			@Override
			protected Command createCommand() {
				Command result = compose(domain, RemovalCommand.this, toCompose.get(0));
				for (int i = 1; i < toCompose.size(); i++) {
					result = result.chain(toCompose.get(i));
				}
				toCompose.clear();
				return result;
			}

			@Override
			public RemovalCommand<T> andThen(EditingDomain domain_, Command followUp) {
				toCompose.add(followUp);
				return this;
			}

			@Override
			public RemovalCommand<T> chain(Command nextCommand) {
				toCompose.add(nextCommand);
				return this;
			}

			@Override
			public Collection<T> getElementsToRemove() {
				return RemovalCommand.this.getElementsToRemove();
			}
		}

		return new AndThen(next);
	}

	@Override
	RemovalCommand<T> chain(Command next);

}
