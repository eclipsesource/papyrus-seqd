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

import java.util.Optional;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.model.CreationParameters;

/**
 * This is the {@code DeferredCreateCommand} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class DeferredCreateCommand<T extends EObject> extends CreationCommandImpl<T> {

	private final CreationParameters parameters;

	private final Supplier<? extends T> object;

	/**
	 * Initializes me.
	 */
	public DeferredCreateCommand(EditingDomain domain, Class<T> type, CreationParameters parameters) {
		this(type, domain, parameters, defaultCreate(type, parameters));
	}

	/**
	 * Initializes me.
	 */
	public DeferredCreateCommand(Class<T> type, EditingDomain domain, CreationParameters parameters,
			Supplier<? extends T> object) {

		super(type, domain);

		this.parameters = parameters;
		this.object = memoize(object);
	}

	@Override
	protected Command createCommand() {
		EObject container = parameters.getContainer();
		if (container == null) {
			return UnexecutableCommand.INSTANCE;
		}
		EReference containment = parameters.getContainment();
		if (containment == null) {
			return UnexecutableCommand.INSTANCE;
		}

		T created = object.get();

		SemanticHelper helper = parameters.getSemanticHelper();
		EObject insertAt = parameters.getInsertionPoint();
		return (insertAt != null) //
				? parameters.isInsertBefore() //
						? helper.insertBefore(container, containment, insertAt, created) //
						: helper.insertAfter(container, containment, insertAt, created) //
				: helper.add(container, containment, created);
	}

	@Override
	public T getNewObject() {
		return getCommand() == null ? null : object.get();
	}

	private static <T> Supplier<T> defaultCreate(Class<T> type, CreationParameters parameters) {
		return () -> Optional.ofNullable(parameters.getEClass()) //
				.filter(c -> !c.isAbstract() && !c.isInterface()) //
				.map(EcoreUtil::create) //
				.map(type::cast).orElse(null);
	}

	private static <T> Supplier<T> memoize(Supplier<? extends T> supplier) {
		return new Supplier<T>() {
			private AtomicMarkableReference<T> result = new AtomicMarkableReference<>(null, false);

			@Override
			public T get() {
				if (result.isMarked()) {
					return result.getReference();
				}
				T memo = supplier.get();
				result.compareAndSet(null, memo, false, true);
				return result.getReference();
			}
		};
	}
}
