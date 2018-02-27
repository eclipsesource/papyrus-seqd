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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Utilities for working with UML {@link Classifier}s.
 *
 * @author Christian W. Damus
 */
public class ClassifierUtil {

	/**
	 * Not instantiable by clients.
	 */
	private ClassifierUtil() {
		super();
	}

	/**
	 * Obtains a stream over the subtype hierarchy of a classifier.
	 * 
	 * @param root
	 *            the root classifier of the subtype hierarchy to cover
	 * @param metaclass
	 *            the type of classifier
	 * @return the subtype hierarchy, including (and starting with) the {@code root}, itself
	 */
	public static <T extends Classifier> Stream<T> subtypeHierarchy(T root, Class<T> metaclass) {
		return subtypeHierarchy(root, metaclass, new HashSet<T>());
	}

	private static <T extends Classifier> Stream<T> subtypeHierarchy(T root, Class<T> metaclass,
			Set<T> cycleProof) {

		if (!cycleProof.add(root)) {
			// Nothing to see, here
			return Stream.empty();
		}

		Stream<T> self = Stream.of(root);

		Stream<T> hierarchy = CrossReferenceUtil
				.invert(root, UMLPackage.Literals.GENERALIZATION__GENERAL, Generalization.class)
				.map(Generalization::getSpecific).filter(metaclass::isInstance).map(metaclass::cast)
				.flatMap(specific -> subtypeHierarchy(specific, metaclass, cycleProof));

		return Stream.concat(self, hierarchy);
	}
}
