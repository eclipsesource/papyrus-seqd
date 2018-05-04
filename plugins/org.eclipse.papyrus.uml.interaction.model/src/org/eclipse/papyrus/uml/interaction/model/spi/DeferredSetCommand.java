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

import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;

/**
 * This is the {@code DeferredSetCommand} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class DeferredSetCommand extends CommandWrapper {

	private final EditingDomain domain;

	private final Supplier<? extends EObject> owner;

	private final EStructuralFeature feature;

	private final Supplier<?> value;

	/**
	 * Initializes me.
	 */
	public DeferredSetCommand(EditingDomain domain, Supplier<? extends EObject> owner,
			EStructuralFeature feature, Supplier<?> value) {

		super();

		this.domain = domain;
		this.owner = owner;
		this.feature = feature;
		this.value = value;
	}

	/**
	 * Initializes me.
	 */
	public DeferredSetCommand(EObject owner, EStructuralFeature feature, Supplier<?> value) {
		this(AdapterFactoryEditingDomain.getEditingDomainFor(owner), () -> owner, feature, value);
	}

	@Override
	protected Command createCommand() {
		EObject theOwner = owner.get();
		Object theValue = value.get();
		return getSemanticHelper().set(theOwner, feature, theValue);
	}

	SemanticHelper getSemanticHelper() {
		return LogicalModelPlugin.getInstance().getSemanticHelper(domain);
	}

	@Override
	public Command chain(Command next) {
		return CompoundModelCommand.compose(domain, this, next);
	}
}
