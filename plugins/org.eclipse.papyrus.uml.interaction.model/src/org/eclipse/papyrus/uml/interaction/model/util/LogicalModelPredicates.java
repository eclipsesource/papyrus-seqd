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

import java.util.function.Predicate;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.uml2.uml.Element;

/**
 * Static predicates for use with the <em>Logical Model</em>.
 */
public class LogicalModelPredicates {

	/**
	 * Not instantiable by clients.
	 */
	private LogicalModelPredicates() {
		super();
	}

	public static Predicate<MElement<? extends Element>> above(int y) {
		return self -> self.getTop().orElse(Integer.MAX_VALUE) < y;
	}

	public static Predicate<MElement<? extends Element>> above(MElement<?> other) {
		return above(other.getTop().orElse(Integer.MIN_VALUE));
	}

	public static Predicate<MElement<? extends Element>> below(int y) {
		return self -> self.getBottom().orElse(Integer.MIN_VALUE) > y;
	}

	public static Predicate<MElement<? extends Element>> below(MElement<?> other) {
		return below(other.getBottom().orElse(Integer.MAX_VALUE));
	}

}
