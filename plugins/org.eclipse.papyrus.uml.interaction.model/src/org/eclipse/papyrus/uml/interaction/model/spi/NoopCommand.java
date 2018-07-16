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

package org.eclipse.papyrus.uml.interaction.model.spi;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.ModelCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;

/**
 * A no-op command that is always executable because it does nothing. This is to be preferred as an
 * alternative to the {@link IdentityCommand} for two reasons:
 * <ul>
 * <li>{@linkplain #chain(Command) chaining} further commands onto this optimizes the chain by simply
 * returning the chained command, the no-op being redundant</li>
 * <li>as a consequence of the previous, if the chained command is some kind of {@link ModelCommand} or
 * {@link CreationCommand} or other command that composes itself using the {@link CompoundModelCommand}, then
 * further chaining will allow that command to employ this more logical-model-friendly variant of the EMF
 * compound</li>
 * </ul>
 *
 * @author Christian W. Damus
 * @see CompoundModelCommand
 */
public class NoopCommand extends IdentityCommand {

	/**
	 * The shared instance of the no-op command.
	 */
	@SuppressWarnings("hiding")
	public static final NoopCommand INSTANCE = new NoopCommand();

	/**
	 * Initializes me.
	 */
	protected NoopCommand() {
		super();
	}

	@Override
	public final Command chain(Command command) {
		// We don't need to have the no-op command in the chain. This has the
		// benefit also of letting the 'command', if it is a ModelCommand,
		// compose itself further using the CompoundModelCommand
		return command;
	}
}
