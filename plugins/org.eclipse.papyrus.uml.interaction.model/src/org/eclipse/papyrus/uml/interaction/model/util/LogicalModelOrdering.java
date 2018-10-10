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

import java.util.Comparator;
import java.util.function.ToIntFunction;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.uml2.uml.Element;

/**
 * Static comparators for ordering of the <em>Logical Model</em>.
 */
public class LogicalModelOrdering {

	/**
	 * Not instantiable by clients.
	 */
	private LogicalModelOrdering() {
		super();
	}

	/**
	 * Obtain a comparator that sorts elements vertically, from top to bottom.
	 * 
	 * @return the vertical comparator
	 */
	public static Comparator<MElement<? extends Element>> vertically() {
		return Comparator.comparingInt(top()).thenComparingInt(bottom());
	}

	private static ToIntFunction<MElement<? extends Element>> top() {
		// Sort things that we don't know their top to, well, the top
		return el -> el.getTop().orElse(Integer.MIN_VALUE);
	}

	private static ToIntFunction<MElement<? extends Element>> bottom() {
		// Sort things that we don't know their bottom to, well, the bottom
		return el -> el.getBottom().orElse(Integer.MAX_VALUE);
	}

}
