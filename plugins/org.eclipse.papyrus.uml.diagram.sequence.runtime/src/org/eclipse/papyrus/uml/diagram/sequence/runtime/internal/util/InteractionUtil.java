/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interaction;

/**
 * Util class for {@link Interaction}
 */
public final class InteractionUtil {

	private InteractionUtil() {
		// no instance
	}

	/**
	 * Returns the interaction from the specified object, browsing all super elements to find the containing
	 * interaction.
	 * 
	 * @param object
	 *            the object contained in an interaction.
	 * @return the interaction or an empty optional if object is not an interaction nor contained in an
	 *         interaction
	 */
	public static Optional<Interaction> getInteraction(EObject object) {
		for (EObject next = object; next != null; next = next.eContainer()) {
			if (Interaction.class.isInstance(next)) {
				return Optional.of(Interaction.class.cast(next));
			}
		}
		return Optional.empty();
	}
}
