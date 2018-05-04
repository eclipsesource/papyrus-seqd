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

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;

/**
 * This is the {@code DeferredAddCommand} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class DeferredAddCommand extends CommandWrapper {

	private final EditingDomain domain;

	private final Supplier<? extends EObject> owner;

	private final EStructuralFeature feature;

	private final Collection<Supplier<?>> values;

	/**
	 * Initializes me.
	 */
	public DeferredAddCommand(EditingDomain domain, Supplier<? extends EObject> owner,
			EStructuralFeature feature, Collection<Supplier<?>> values) {

		super();

		this.domain = domain;
		this.owner = owner;
		this.feature = feature;
		this.values = values;
	}

	/**
	 * Initializes me.
	 */
	public DeferredAddCommand(EditingDomain domain, Supplier<? extends EObject> owner,
			EStructuralFeature feature, Supplier<?>... value) {

		this(domain, owner, feature, Arrays.asList(value));
	}

	/**
	 * Initializes me.
	 */
	public DeferredAddCommand(EObject owner, EStructuralFeature feature, Collection<Supplier<?>> values) {
		this(AdapterFactoryEditingDomain.getEditingDomainFor(owner), () -> owner, feature, values);
	}

	/**
	 * Initializes me.
	 */
	public DeferredAddCommand(EObject owner, EStructuralFeature feature, Supplier<?>... value) {
		this(owner, feature, Arrays.asList(value));
	}

	@Override
	protected Command createCommand() {
		EObject theOwner = owner.get();
		Collection<?> theValues = values.stream().map(Supplier::get).collect(Collectors.toList());
		return getSemanticHelper().add(theOwner, feature, theValues);
	}

	SemanticHelper getSemanticHelper() {
		return LogicalModelPlugin.getInstance().getSemanticHelper(domain);
	}

	@Override
	public Command chain(Command next) {
		return CompoundModelCommand.compose(domain, this, next);
	}
}
