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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.StrictCompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;

/**
 * A composition of {@link Command}s that accounts for dependencies in the deferred calculation of changes to
 * the semantics and visualization of the <em>UML Interaction</em>.
 */
public class CompoundModelCommand extends StrictCompoundCommand {

	private static final Class<?> TRANSACTIONAL_EDITING_DOMAIN_CLASS;

	static {
		Class<?> tedClass = null;

		try {
			tedClass = ModelCommand.class.getClassLoader()
					.loadClass("org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain"); //$NON-NLS-1$
		} catch (Exception e) {
			// Not a problem: we don't have transaction support installed
			tedClass = Void.class;
		}

		TRANSACTIONAL_EDITING_DOMAIN_CLASS = tedClass;
	}

	/**
	 * Initializes me with the first two commands that I compose.
	 *
	 * @param first
	 *            the first composed command
	 * @param second
	 *            the second composed command
	 */
	CompoundModelCommand(Command first, Command second) {
		super();

		// The whole point of our compounds is that each step may have
		// side-effects and is required for calculation of the next step
		isPessimistic = true;

		append(first);
		append(second);
	}

	/**
	 * Compose two commands.
	 *
	 * @param editingDomain
	 *            the editing domain context inwhich the composed command will be executed, which may inform
	 *            the kind of composite that is created
	 * @param first
	 *            the first composed command
	 * @param second
	 *            the second composed command
	 * @param the
	 *            resulting composite, which may be optimized in such a way that it isn't literally a
	 *            {@link CompoundCommand} of any kind
	 */
	public static Command compose(EditingDomain editingDomain, Command first, Command second) {
		// Don't test executability because that can depend on execution of a previous command
		if (first == null || first instanceof UnexecutableCommand //
				|| second == null || second instanceof UnexecutableCommand) {
			return UnexecutableCommand.INSTANCE;
		}

		if (first instanceof CompoundModelCommand) {
			((CompoundModelCommand)first).append(second);
			return first;
		} else if (TRANSACTIONAL_EDITING_DOMAIN_CLASS.isInstance(editingDomain)) {
			return new TransactionalCompoundModelCommand(editingDomain, first, second);
		}

		return new CompoundModelCommand(first, second);
	}

	/**
	 * Compose multiple commands.
	 *
	 * @param editingDomain
	 *            the editing domain context inwhich the composed command will be executed, which may inform
	 *            the kind of composite that is created
	 * @param commands
	 *            the commands to compose
	 * @param the
	 *            resulting composite, which may be optimized in such a way that it isn't literally a
	 *            {@link CompoundCommand} of any kind
	 */
	public static Command compose(EditingDomain editingDomain, List<Command> commands) {
		if (commands.size() == 1) {
			return commands.get(0);
		} else if (commands.size() > 1) {
			Command result = CompoundModelCommand.compose(editingDomain, commands.get(0), commands.get(1));
			for (int i = 2; i < commands.size(); i++) {
				result = result.chain(commands.get(i));
			}
			return result;
		}
		return UnexecutableCommand.INSTANCE;
	}

	@Override
	public Command chain(Command next) {
		append(next);
		return this;
	}

	//
	// Nested types
	//

	/**
	 * A specialized {@link CompoundModelCommand} that is suitable for use in the context of a
	 * {@link TransactionalEditingDomain}, in which a transaction is required even just to
	 * {@linkplain #prepare() prepare} the command because that requires executing its constituents (and then
	 * undoing their effects).
	 */
	protected static class TransactionalCompoundModelCommand extends CompoundModelCommand {

		private final InternalTransactionalEditingDomain domain;

		protected TransactionalCompoundModelCommand(EditingDomain domain, Command first, Command second) {

			super(first, second);

			this.domain = (InternalTransactionalEditingDomain)domain;
		}

		@Override
		protected boolean prepare() {
			boolean result;

			Transaction transaction = null;

			try {
				Transaction active = domain.getActiveTransaction();
				if (active == null || active.isReadOnly()) {
					// Compute the preparation in an unprotected and silent transaction
					// because, as a pessimistic compound, we will just undo everything
					// immediately anyways. But, the transaction is needed nonetheless
					Map<Object, Object> options = new HashMap<>();
					options.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
					options.put(Transaction.OPTION_NO_NOTIFICATIONS, Boolean.TRUE);

					transaction = domain.startTransaction(false, options);
				}

				try {
					result = super.prepare();
				} finally {
					if (transaction != null) {
						transaction.commit();
					}
				}
			} catch (InterruptedException e) {
				// Assume that we shouldn't be executable because the user cancelled
				result = false;
			} catch (RollbackException e) {
				// Cannot happen in an unprotected transaction
				throw new Error("Impossible unprotected transaction roll-back", e); //$NON-NLS-1$
			}

			return result;
		}
	}

}
