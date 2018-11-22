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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;
import org.eclipse.uml2.uml.Element;

/**
 * Basic implementation of a {@link RemovalCommand}.
 */
public class ElementRemovalCommandImpl extends CommandWrapper implements RemovalCommand<Element> {

	private Set<Element> toRemove = new LinkedHashSet<>();

	private EditingDomain domain;

	/**
	 * Creates an empty removal command, which wraps the given command.
	 */
	public ElementRemovalCommandImpl(EditingDomain domain, Command command) {
		super(command);
		this.domain = domain;
	}

	/**
	 * Creates a new removal command. It is expected that the given command will lead to the deletion of the
	 * given object once executed.
	 */
	public ElementRemovalCommandImpl(EditingDomain domain, Command command, Element object) {
		super(command);
		this.domain = domain;
		toRemove.add(object);
	}

	/**
	 * Creates a removal command which combines the result of the given removal commands.
	 */
	public ElementRemovalCommandImpl(EditingDomain domain,
			List<? extends RemovalCommand<? extends Element>> commands) {
		super(CompoundModelCommand.compose(domain, new ArrayList<Command>(commands)));
		this.domain = domain;
		toRemove.addAll(commands.stream()//
				.flatMap(rm -> rm.getElementsToRemove().stream())//
				.collect(Collectors.toSet()));
	}

	@Override
	public Collection<Element> getElementsToRemove() {
		return Collections.unmodifiableSet(toRemove);
	}

	@Override
	public RemovalCommand<Element> chain(Command next) {
		return andThen(domain, next);
	}

}
