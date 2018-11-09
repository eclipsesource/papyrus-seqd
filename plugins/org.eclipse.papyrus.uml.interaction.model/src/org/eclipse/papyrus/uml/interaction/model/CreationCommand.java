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
import java.util.function.Consumer;
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
	 * Query the type of thing that I create.
	 * 
	 * @return my created type
	 */
	Class<? extends T> getType();

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
	 * Obtain myself as a cast to the given {@code subtype} of my result {@link #getType() type}.
	 * 
	 * @param subtype
	 *            a subtype of my {@link #getType() type}
	 * @return the cast, or {@code null} if the {@code subtype} is not compatible with me
	 * @see #getType()
	 */
	default <U extends EObject> CreationCommand<U> as(Class<U> subtype) {
		final class Cast extends Wrapper<U> {
			@SuppressWarnings("unchecked")
			Cast() {
				// This cast is checked externally, below
				super((CreationCommand<U>)CreationCommand.this, WRAP_TOKEN);
			}

			@Override
			public Class<? extends U> getType() {
				return subtype;
			}

			@Override
			public U getNewObject() {
				return subtype.cast(CreationCommand.this.getNewObject());
			}
		}

		// If the subtype is a class, then we can enforce conformance. Otherwise,
		// it's an interface, so the cast may or may not work at run-time
		if (subtype.isInterface() || getType().isAssignableFrom(subtype)) {
			return new Cast();
		}

		return null;
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
		class AndThen extends Wrapper<T> {
			// Anticipate a small number of follow-ups
			private final List<Command> toCompose = new ArrayList<>(3);

			AndThen(Command followUp) {
				super(CreationCommand.this);

				toCompose.add(followUp);
			}

			@Override
			protected Command createCommand() {
				Command result = Compound.compose(domain, CreationCommand.this, toCompose.get(0));
				for (int i = 1; i < toCompose.size(); i++) {
					result = result.chain(toCompose.get(i));
				}
				toCompose.clear();
				return result;
			}

			@Override
			public CreationCommand<T> andThen(EditingDomain domain_, Command followUp) {
				toCompose.add(followUp);
				return this;
			}

			@Override
			public CreationCommand<T> chain(Command nextCommand) {
				toCompose.add(nextCommand);
				return this;
			}
		}

		return new AndThen(next);
	}

	/**
	 * Obtain a view of myself that, after I am executed, processes my result and then returns it.
	 * 
	 * @param domain
	 *            the contextual editing domain
	 * @param sideEffect
	 *            some processor of the result to produce side-effects
	 * @return myself, with side-effects
	 */
	default CreationCommand<T> andThen(Consumer<? super T> sideEffect) {
		class AndThen extends Wrapper<T> {
			AndThen() {
				super(CreationCommand.this, WRAP_TOKEN);
			}

			@Override
			public void execute() {
				super.execute();
				sideEffect.accept(getNewObject());
			}

		}

		return new AndThen();
	}

	@Override
	CreationCommand<T> chain(Command next);

	//
	// Nested types
	//

	/**
	 * Base for wrappers around creation commands.
	 *
	 * @param <T>
	 *            the wrapped creation-command product type
	 */
	class Wrapper<T extends EObject> extends CommandWrapper implements CreationCommand<T> {

		static final Object WRAP_TOKEN = new Object();

		private final CreationCommand<? extends T> outer;

		public Wrapper(CreationCommand<? extends T> outer) {
			super();

			this.outer = outer;
		}

		Wrapper(CreationCommand<? extends T> outer, Object wrapToken) {
			super(outer);
			assert wrapToken == WRAP_TOKEN;

			this.outer = outer;
		}

		@SuppressWarnings("unchecked")
		@Override
		public CreationCommand<T> getCommand() {
			// This cast is safe by construction
			return (CreationCommand<T>)super.getCommand();
		}

		@Override
		public String getLabel() {
			return outer.getLabel();
		}

		@Override
		public String getDescription() {
			return outer.getDescription();
		}

		@Override
		public Class<? extends T> getType() {
			return outer.getType();
		}

		@Override
		public T getNewObject() {
			return outer.getNewObject();
		}

		@Override
		public CreationCommand<T> chain(Command nextCommand) {
			return new Compound<>(this, nextCommand);
		}

		//
		// Nested types
		//

		/**
		 * Creation-command analogue of the {@link CompoundModelCommand} API. The first command in the
		 * compound is a {@link CreationCommand}.
		 *
		 * @param <T>
		 *            the composed creation-command product type
		 */
		static class Compound<T extends EObject> extends CompoundModelCommand implements CreationCommand<T> {

			/**
			 * Initializes me.
			 *
			 * @param first
			 * @param second
			 */
			Compound(CreationCommand<T> first, Command second) {
				super(first, second);
			}

			@SuppressWarnings("unchecked")
			private CreationCommand<T> getCreationCommand() {
				// This cast is safe by construction
				return (CreationCommand<T>)commandList.get(0);
			}

			@Override
			public String getLabel() {
				return getCreationCommand().getLabel();
			}

			@Override
			public String getDescription() {
				return getCreationCommand().getDescription();
			}

			@Override
			public Class<? extends T> getType() {
				return getCreationCommand().getType();
			}

			@Override
			public T getNewObject() {
				return getCreationCommand().getNewObject();
			}

			@Override
			public CreationCommand<T> chain(Command next) {
				super.chain(next);
				return this;
			}

			static <T extends EObject> CreationCommand<T> compose(EditingDomain editingDomain,
					CreationCommand<T> first, Command second) {
				if (first instanceof CompoundModelCommand) {
					((CompoundModelCommand)first).append(second);
					return first;
				} else if (isTransactional(editingDomain)) {
					return new Transactional<>(editingDomain, first, second);
				}

				return new Compound<>(first, second);
			}

			//
			// Nested types
			//

			/**
			 * Creation-command analogue of the {@link TransactionalCompoundModelCommand} API. The first
			 * command in the compound is a {@link CreationCommand}.
			 *
			 * @param <T>
			 *            the composed creation-command product type
			 */
			static class Transactional<T extends EObject> extends TransactionalCompoundModelCommand implements CreationCommand<T> {

				Transactional(EditingDomain editingDomain, CreationCommand<T> first, Command second) {
					super(editingDomain, first, second);
				}

				@SuppressWarnings("unchecked")
				private CreationCommand<T> getCreationCommand() {
					// This cast is safe by construction
					return (CreationCommand<T>)commandList.get(0);
				}

				@Override
				public String getLabel() {
					return getCreationCommand().getLabel();
				}

				@Override
				public String getDescription() {
					return getCreationCommand().getDescription();
				}

				@Override
				public Class<? extends T> getType() {
					return getCreationCommand().getType();
				}

				@Override
				public T getNewObject() {
					return getCreationCommand().getNewObject();
				}

				@Override
				public CreationCommand<T> chain(Command next) {
					super.chain(next);
					return this;
				}

			}

		}

	}

}
