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

import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.elseMaybe;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMapToObj;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.map;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.mapToInt;

import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MObjectImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.uml2.uml.Element;

/**
 * Static utilities for working with <em>Logical Model</em> lifelines.
 */
public class Lifelines {

	/**
	 * Not instantiable by clients.
	 */
	private Lifelines() {
		super();
	}

	public static Optional<Shape> getBody(MLifeline lifeline) {
		return lifeline.getDiagramView().map(diagramHelper(lifeline)::getLifelineBodyShape);
	}

	static EditingDomain editingDomain(MElement<?> element) {
		return ((MObjectImpl<?>)element).getEditingDomain();
	}

	static DiagramHelper diagramHelper(MElement<?> element) {
		return LogicalModelPlugin.INSTANCE.getDiagramHelper(editingDomain(element));
	}

	static LayoutHelper layoutHelper(MElement<?> element) {
		return LogicalModelPlugin.INSTANCE.getLayoutHelper(editingDomain(element));
	}

	public static Optional<MElement<? extends Element>> elementAtAbsolute(MLifeline lifeline, int y) {
		OptionalInt bodyTop = mapToInt(getBody(lifeline), layoutHelper(lifeline)::getTop);
		OptionalInt relativeY = map(bodyTop, top -> y - top);
		return flatMapToObj(relativeY, lifeline::elementAt);
	}

	public static Optional<MElement<? extends Element>> elementAfterAbsolute(MLifeline lifeline, int y) {
		return elseMaybe(elementAtAbsolute(lifeline, y).flatMap(elem -> lifeline.following(elem)),
				// Otherwise, the first element on the lifeline (if any and it's below this point)
				() -> lifeline.following().filter(below(y)));
	}

	public static Optional<MElement<? extends Element>> elementBeforeAbsolute(MLifeline lifeline, int y) {
		// Note that the "element at" is often actually above; that's what element-at means
		return elementAtAbsolute(lifeline, y)
				.flatMap(elem -> elseMaybe(Optional.of(elem).filter(above(y)), lifeline.preceding(elem)));
	}

}
