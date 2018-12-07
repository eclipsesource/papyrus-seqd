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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.papyrus.commands.wrappers.GEFCommandWrapper;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DependencyContext;

/**
 * The topmost command provided by a GEF {@link EditPolicy}. It wraps a dynamically provided command with an
 * optional kill-switch provided by the {@code DependencyContext}.
 */
public final class TopCommand extends GEFCommandWrapper {
	// Capture the current dependency context
	private DependencyContext ctx = DependencyContext.get();

	private Command delegate;

	private Supplier<? extends Command> killSwitchSupplier;

	private boolean prepared;

	private boolean canExecute;

	/**
	 * Initializes me.
	 */
	private TopCommand() {
		super();
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean canExecute() {
		if (!prepared) {
			canExecute = ctx.withContext(super::canExecute);
			prepared = true;
		}
		return canExecute;
	}

	@Override
	public void execute() {
		ctx.withContext(super::execute);
	}

	@Override
	protected Command createCommand() {
		Command result = delegate;

		if ((result != null) && result.canExecute() && (killSwitchSupplier != null)) {
			Command killSwitch = killSwitchSupplier.get();
			if (killSwitch != null) {
				result = result.chain(killSwitch);
			}
		}

		return result;
	}

	void setDelegate(Command delegate) {
		this.delegate = delegate;
	}

	void setKillSwitchSupplier(Supplier<? extends Command> killSwitchSupplier) {
		this.killSwitchSupplier = killSwitchSupplier;
	}

	static Factory factory(Supplier<? extends Command> delegateSupplier) {
		return new Factory(delegateSupplier);
	}

	//
	// Nested types
	//

	static class Factory implements Supplier<TopCommand>, Consumer<Supplier<? extends Command>> {

		private final TopCommand product = new TopCommand();

		private final Supplier<? extends Command> commandSupplier;

		private Factory(Supplier<? extends Command> commandSupplier) {
			super();

			this.commandSupplier = commandSupplier;
		}

		@Override
		public TopCommand get() {
			Command supplied = commandSupplier.get();
			if (supplied == null) {
				// Don't provide anything
				return null;
			}

			// Inject the delegate
			product.setDelegate(supplied);

			return product;
		}

		@Override
		public void accept(Supplier<? extends Command> supplier) {
			product.setKillSwitchSupplier(supplier);
		}

		Consumer<Supplier<? extends org.eclipse.emf.common.command.Command>> adapt(
				Function<org.eclipse.emf.common.command.Command, Command> transform) {

			return emf -> this.accept(() -> transform.apply(emf.get()));
		}
	}
}
