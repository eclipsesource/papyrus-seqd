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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;

/**
 * Static utilities for working with EMF {@link Command}s.
 */
public class CommandUtil {

	/**
	 * Not instantiable by clients.
	 */
	private CommandUtil() {
		super();
	}

	/**
	 * Wrap a creation command to inject the first view that it creates into the given descriptor.
	 * 
	 * @param viewDescriptor
	 *            a view descriptor
	 * @param creationCommand
	 *            a creation command
	 * @return the wrapper, which may just be the original creation command if the view creation cannot be
	 *         found
	 */
	public static Command injectViewInto(ViewDescriptor viewDescriptor, Command creationCommand) {
		if (creationCommand instanceof CreationCommand<?>) {
			// Need to maintain the specific protocol
			return injectViewInto(viewDescriptor, (CreationCommand<?>)creationCommand);
		}

		// Spelunk in the creation command for the view creation
		CreationCommand<? extends View> viewCreation = getViewCreation(viewDescriptor, creationCommand);
		Command result = creationCommand;

		if (viewCreation != null) {
			Supplier<? extends View> viewSupplier = viewCreation;
			result = new CommandWrapper(creationCommand) {
				@Override
				public void execute() {
					super.execute();
					viewDescriptor.setView(viewSupplier.get());
				}
			};
		}

		return result;
	}

	private static CreationCommand<? extends View> getViewCreation(ViewDescriptor viewDescriptor,
			Command command) {
		CreationCommand<? extends View> result = null;

		@SuppressWarnings("unchecked")
		Class<? extends View> viewType = viewDescriptor.getViewKind();

		for (Iterator<Command> iter = iterate(command); iter.hasNext();) {
			Command next = iter.next();
			if (next instanceof CreationCommand<?>) {
				CreationCommand<?> nextCreation = (CreationCommand<?>)next;
				if (viewType.isAssignableFrom(nextCreation.getType())) {
					result = nextCreation.as(viewType);
					if (result != null) {
						break;
					}
				}
			}
		}

		return result;
	}

	/**
	 * Wrap a creation command to inject the first view that it creates into the given descriptor.
	 * 
	 * @param viewDescriptor
	 *            a view descriptor
	 * @param creationCommand
	 *            a creation command
	 * @return the wrapper, which may just be the original creation command if the view creation cannot be
	 *         found
	 */
	public static <T extends EObject> CreationCommand<T> injectViewInto(ViewDescriptor viewDescriptor,
			CreationCommand<T> creationCommand) {

		// Spelunk in the creation command for the view creation
		CreationCommand<? extends View> viewCreation = getViewCreation(viewDescriptor, creationCommand);
		CreationCommand<T> result = creationCommand;

		if (viewCreation != null) {
			Supplier<? extends View> viewSupplier = viewCreation;
			result = creationCommand.andThen(__ -> viewDescriptor.setView(viewSupplier.get()));
		}

		return result;
	}

	/**
	 * Iterate an EMF command structure, accounting for nesting both by {@linkplain CompoundCommand
	 * compounding} and by {@linkplain CommandWrapper wrapping}.
	 * 
	 * @param command
	 *            a command
	 * @return an iterator over all commands that comprise it
	 */
	@SuppressWarnings("serial")
	public static TreeIterator<Command> iterate(Command command) {
		return new AbstractTreeIterator<Command>(command, true) {
			@Override
			protected Iterator<Command> getChildren(@SuppressWarnings("hiding") Object object) {
				if (object instanceof CommandWrapper) {
					CommandWrapper wrapper = (CommandWrapper)object;
					Command wrapped = wrapper.getCommand();
					if ((wrapped == null) && wrapper.canExecute()) {
						// handle deferred creation of the wrapped command
						wrapped = wrapper.getCommand();
					}
					return (wrapped == null) ? Collections.emptyIterator()
							: Collections.singleton(wrapped).iterator();
				} else if (object instanceof CompoundCommand) {
					CompoundCommand compound = (CompoundCommand)object;
					return compound.getCommandList().iterator();
				} else {
					return Collections.emptyIterator();
				}
			}
		};
	}

}
