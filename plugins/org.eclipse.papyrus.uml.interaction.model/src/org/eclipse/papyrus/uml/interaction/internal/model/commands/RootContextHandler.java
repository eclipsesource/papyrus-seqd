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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.uml2.uml.Element;

/**
 * A handler of the topmost {@link DependencyContext command context} that captures post-processing commands
 * to decorate a command created by the client. The post-processing commands supported so far include
 * <ul>
 * <li>the {@link DeferredPaddingCommand deferred padding} for layout changes</li>
 * <li>the {@link MInteraction#sort() sorting} of interaction fragments to match visual changes</li>
 * </ul>
 * 
 * @param <C>
 *            the command type
 */
public final class RootContextHandler<C> implements Consumer<DependencyContext>, UnaryOperator<C> {

	// Room for the deferred padding command, sorting command, and user's command
	private final List<C> commands = new ArrayList<>(3);

	private final MInteractionImpl interaction;

	private final Function<Command, C> coercionFunction;

	private final BinaryOperator<C> combiner;

	/**
	 * Initializes me.
	 */
	private RootContextHandler(MElement<? extends Element> owner, Function<Command, C> coercionFunction,
			BinaryOperator<C> combiner) {
		super();

		this.interaction = ((MElementImpl<?>)owner).getInteractionImpl();
		this.coercionFunction = coercionFunction;
		this.combiner = combiner;
	}

	@Override
	public void accept(DependencyContext ctx) {
		append(ctx.defer(c -> DeferredPaddingCommand.get(c, interaction)));

		// If any of the computed commands requires sorting, it'll be in the context
		append(ctx.defer(c -> c.get(interaction, SortSemanticsCommand.class).orElse(null)));
	}

	private void append(Command command) {
		if (command != null) {
			commands.add(coercionFunction.apply(command));
		}
	}

	private void prepend(C command) {
		if (command != null) {
			commands.add(0, command);
		}
	}

	private void clear() {
		commands.clear();
	}

	@Override
	public C apply(C command) {
		C result = command;

		if (result != null) {
			prepend(command); // Do user command first, then padding and sorting
			result = commands.stream().reduce(combiner).get(); // Have at least the 'command'
			clear();
		}

		return result;
	}

	public static RootContextHandler<Command> create(MElement<? extends Element> owner) {
		return create(owner, Function.identity(), Command::chain);
	}

	public static <C> RootContextHandler<C> create(MElement<? extends Element> owner,
			Function<Command, C> coercionFunction, BinaryOperator<C> combiner) {

		return new RootContextHandler<C>(owner, coercionFunction, combiner);
	}

}
