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

package org.eclipse.papyrus.uml.interaction.model.util;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

/**
 * Static utilities for APIs missing from the {@code Optional} class.
 */
public class Optionals {

	/**
	 * Not instantiable by clients.
	 */
	private Optionals() {
		super();
	}

	/**
	 * Test type and cast in one step.
	 * 
	 * @param optional
	 *            an optional
	 * @param asType
	 *            the type to try to cast
	 * @return the cast result
	 */
	public static <T, U> Optional<U> as(Optional<? super T> optional, Class<? extends U> asType) {
		return optional.filter(asType::isInstance).map(asType::cast);
	}

	/**
	 * Unwrap a stream of optionals as a stream of the present values.
	 * 
	 * @param stream
	 *            a stream of optionals
	 * @return a stream of the present values
	 */
	public static <T> Stream<T> mapPresent(Stream<Optional<T>> stream) {
		return stream.filter(Optional::isPresent).map(Optional::get);
	}

	/**
	 * Map an {@code optional} int under an integer-valued unary {@code operator}.
	 * 
	 * @param optional
	 *            an optional integer value
	 * @param operator
	 *            an operator to apply
	 * @return the optional {@code operator} result
	 */
	public static OptionalInt map(OptionalInt optional, IntUnaryOperator operator) {
		return optional.isPresent() ? OptionalInt.of(operator.applyAsInt(optional.getAsInt())) : optional;
	}
}
