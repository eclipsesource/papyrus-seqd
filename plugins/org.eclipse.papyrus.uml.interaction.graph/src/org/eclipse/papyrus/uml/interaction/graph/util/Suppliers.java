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

package org.eclipse.papyrus.uml.interaction.graph.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Additional functions for suppliers that the JDK missed.
 */
public class Suppliers {

	/**
	 * Not instantiable by clients.
	 */
	private Suppliers() {
		super();
	}

	/**
	 * Compose a function around a supplier.
	 * 
	 * @param supplier
	 *            the supplier
	 * @param function
	 *            a function to compute on the supplier
	 * @return the composed supplier
	 */
	public static <V, R> Supplier<R> compose(Supplier<V> supplier,
			Function<? super V, ? extends R> function) {
		return () -> function.apply(supplier.get());
	}

}
