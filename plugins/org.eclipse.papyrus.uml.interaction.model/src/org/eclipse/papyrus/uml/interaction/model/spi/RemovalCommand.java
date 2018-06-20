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

import java.util.Collection;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

/**
 * Command which is supposed to delete model element. It offers methods to access the elements marked for
 * deletion.
 */
public interface RemovalCommand<T extends EObject> extends Command, Supplier<Collection<T>> {

	/**
	 * @return the elements which are marked for removal when this command was created.
	 */
	Collection<T> getElementsToRemove();

	@Override
	default Collection<T> get() {
		return getElementsToRemove();
	}

}
