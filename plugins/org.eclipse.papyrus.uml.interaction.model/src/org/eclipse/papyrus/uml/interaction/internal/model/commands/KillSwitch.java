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

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;

/**
 * A command that will be unexecutable if, when queried for {@link Command#canExecute()}, its condition
 * testing the model and/or diagram visualization evaluates {@code true}.
 */
public final class KillSwitch extends CommandWrapper {

	private final BooleanSupplier condition;

	private boolean enabled = true;

	/**
	 * Initializes me.
	 */
	private KillSwitch(BooleanSupplier condition) {
		super();

		this.condition = condition;
	}

	@Override
	protected Command createCommand() {
		return (enabled && !condition.getAsBoolean()) ? UnexecutableCommand.INSTANCE
				: IdentityCommand.INSTANCE;
	}

	void disable() {
		enabled = false;
	}

	/**
	 * Register a kill-switch {@code condition} for the given {@code context}ual object.
	 * 
	 * @param context
	 *            an object (usually a model element or notation element) for which to register a kill switch
	 * @param condition
	 *            a condition that, if the kill-switch is {@link #cancel(Object) still registered} at the time
	 *            of its execution and it evaluates {@code true}, causes the kill switch to be unexecutable,
	 *            resulting in overall failure of the operation
	 * @see #cancel(Object)
	 */
	public static void register(Object context, BooleanSupplier condition) {
		DependencyContext ctx = DependencyContext.get();

		CompoundCommand killSwitches = getKillSwitches(ctx);
		ctx.run(context, KillSwitch.class, () -> killSwitches.append(new KillSwitch(condition)));
	}

	/**
	 * Cancel and remove a previously {@code #register(Object, BooleanSupplier) registered} kill switch.
	 * 
	 * @param context
	 *            an object (usually a model element or notation element) for which to cancel a kill switch
	 * @see #register(Object, BooleanSupplier)
	 */
	public static void cancel(Object context) {
		DependencyContext ctx = DependencyContext.get();
		ctx.remove(context, KillSwitch.class).ifPresent(KillSwitch::disable);
	}

	private static CompoundCommand getKillSwitches(DependencyContext ctx) {
		return ctx.get(KillSwitch.class, CompoundCommand.class,
				(Supplier<CompoundCommand>)CompoundCommand::new);
	}

	static Optional<? extends Command> check(DependencyContext ctx) {
		Predicate<CompoundCommand> empty = CompoundCommand::isEmpty;
		Optional<CompoundCommand> result = ctx.get(KillSwitch.class, CompoundCommand.class);
		return result.filter(empty.negate());
	}
}
