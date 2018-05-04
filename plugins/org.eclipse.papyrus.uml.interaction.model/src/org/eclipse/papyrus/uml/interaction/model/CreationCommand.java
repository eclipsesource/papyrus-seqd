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

package org.eclipse.papyrus.uml.interaction.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;

/**
 * A specialized {@link Command} that creates an object in the model or diagram.
 * 
 * @param <T>
 *            the type of object created by the command
 * @author Christian W. Damus
 */
public interface CreationCommand<T extends EObject> extends Command, Supplier<T> {

	/**
	 * Obtains the object created by my execution. It would normally also be expected to be amongst the
	 * command's {@link Command#getResult()}.
	 * 
	 * @return the new object, or {@link null} if the command has not yet been executed or was undone
	 */
	T getNewObject();

	@Override
	default T get() {
		return getNewObject();
	}

	/**
	 * Obtain a view of myself that, after I am executed, follows up with the {@code next} command and returns
	 * my own result as its creation result.
	 * 
	 * @param domain
	 *            the contextual editing domain
	 * @param next
	 *            the next command to execute
	 * @return myself, with follow-up
	 */
	default CreationCommand<T> andThen(EditingDomain domain, Command next) {
		class AndThen extends CommandWrapper implements CreationCommand<T> {
			// Anticipate a small number of follow-ups
			private final List<Command> toCompose = new ArrayList<>(3);

			AndThen(Command followUp) {
				super();

				toCompose.add(followUp);
			}

			@Override
			protected Command createCommand() {
				Command result = CompoundModelCommand.compose(domain, CreationCommand.this, toCompose.get(0));
				for (int i = 1; i < toCompose.size(); i++) {
					result = result.chain(toCompose.get(i));
				}
				toCompose.clear();
				return result;
			}

			@Override
			public T getNewObject() {
				return CreationCommand.this.getNewObject();
			}

			@Override
			public CreationCommand<T> andThen(EditingDomain domain_, Command followUp) {
				toCompose.add(followUp);
				return this;
			}
		}

		return new AndThen(next);
	}
}
