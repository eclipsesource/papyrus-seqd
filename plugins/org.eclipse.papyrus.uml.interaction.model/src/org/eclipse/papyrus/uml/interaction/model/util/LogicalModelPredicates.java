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

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
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

	public static Predicate<MElement<? extends Element>> verticallyBetween(int top, int bottom) {
		return above(bottom).and(below(top));
	}

	public static Predicate<MElement<? extends Element>> where(EStructuralFeature feature, Object value) {
		return elem -> Objects.equals(((EObject)elem).eGet(feature), value);
	}

	public static Predicate<MElement<? extends Element>> spannedBy(MElement<? extends Element> extent) {
		OptionalInt top = extent.getTop();
		OptionalInt bottom = extent.getBottom();

		if (!top.isPresent() || !bottom.isPresent()) {
			// Doesn't span anything
			return __ -> false;
		}

		// Widen the search by 1 to include elements at the top or bottom (above/below are exclusive)
		Predicate<MElement<?>> belowTop = below(top.getAsInt() - 1);
		Predicate<MElement<?>> aboveBottom = above(bottom.getAsInt() + 1);
		return belowTop.and(aboveBottom);
	}

	public static Predicate<MElement<? extends Element>> spans(MElement<? extends Element> element) {
		OptionalInt top = element.getTop();
		OptionalInt bottom = element.getBottom();

		if (!top.isPresent() || !bottom.isPresent()) {
			// Not spannable
			return __ -> false;
		}

		// Widen the search by 1 to include elements at the top or bottom (above/below are exclusive)
		Predicate<MElement<? extends Element>> above = above(top.getAsInt() + 1);
		Predicate<MElement<? extends Element>> below = below(bottom.getAsInt() - 1);
		return above.and(below);
	}

}
