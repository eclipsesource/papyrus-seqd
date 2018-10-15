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
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;

/**
 * An implementation of the {@link CreationCommand} protocol as a wrapper around the actual creation/add
 * command.
 *
 * @author Christian W. Damus
 */
public class CreationCommandImpl<T extends EObject> extends CommandWrapper implements CreationCommand<T> {

	private final Class<? extends T> type;

	private final EditingDomain domain;

	public CreationCommandImpl(Class<? extends T> type, EditingDomain domain) {
		super();

		this.type = type;
		this.domain = domain;
	}

	public CreationCommandImpl(Class<? extends T> type, EditingDomain domain, Command command) {
		super(command);

		this.type = type;
		this.domain = domain;
	}

	public CreationCommandImpl(Class<? extends T> type, EditingDomain domain, String label) {
		super(label);

		this.type = type;
		this.domain = domain;
	}

	public CreationCommandImpl(Class<? extends T> type, EditingDomain domain, String label, Command command) {
		super(label, command);

		this.type = type;
		this.domain = domain;
	}

	public CreationCommandImpl(Class<? extends T> type, EditingDomain domain, String label,
			String description) {
		super(label, description);

		this.type = type;
		this.domain = domain;
	}

	public CreationCommandImpl(Class<? extends T> type, EditingDomain domain, String label,
			String description, Command command) {
		super(label, description, command);

		this.type = type;
		this.domain = domain;
	}

	@Override
	public T getNewObject() {
		Command delegate = getCommand();
		return delegate == null ? null
				: delegate.getResult().stream() //
						.filter(type::isInstance).map(type::cast).findFirst().orElse(null);
	}

	@Override
	public final Class<? extends T> getType() {
		return type;
	}

	@Override
	public Command chain(Command next) {
		return CompoundModelCommand.compose(domain, this, next);
	}
}
