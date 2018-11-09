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
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
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

	/**
	 * Obtain a comparator that sorts elements into their correct semantic order according to the timeline
	 * implied by the diagram.
	 * 
	 * @return the semantic comparator
	 */
	public static Comparator<MElement<? extends Element>> semantically() {
		// For most things, vertically is sufficient
		Comparator<MElement<? extends Element>> vertically = vertically();

		// However, there are tie-breakers for elements that are often expected to
		// to coincide in time (horizontally)
		return (a, b) -> {
			int result = 0; // Assume a tie

			// First, consider some special cases that aren't negotiable by the visuals.
			// Cases that don't apply result in a tie
			if ((a instanceof MMessageEnd) && (b instanceof MMessageEnd)) {
				result = specialCase((MMessageEnd)a, (MMessageEnd)b);
			} else if ((a instanceof MOccurrence<?>) && (b instanceof MExecution)) {
				result = specialCase((MOccurrence<?>)a, (MExecution)b);
			} else if ((b instanceof MOccurrence<?>) && (a instanceof MExecution)) {
				result = -specialCase((MOccurrence<?>)b, (MExecution)a);
			}

			if (result == 0) {
				result = vertically.compare(a, b);
			}

			return result;
		};
	}

	private static int specialCase(MMessageEnd a, MMessageEnd b) {
		int result = 0; // Start with a tie

		if (a.getOwner() == b.getOwner()) {
			// Send always precedes receive
			result = a.isSend() ? -1 : +1;
		}

		return result;
	}

	private static int specialCase(MOccurrence<?> a, MExecution b) {
		int result = 0; // Start with a tie

		if (b.getStart().filter(s -> s == a).isPresent()) {
			// Start always precedes execution
			result = -1;
		} else if (b.getFinish().filter(f -> f == a).isPresent()) {
			// Execution always precedes finish
			result = +1;
		}

		return result;
	}

}
