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

import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.common.util.CacheAdapter;

/**
 * A collection of static cross-referencing utilities for UML models, using the {@link CacheAdapter}.
 *
 * @author Christian W. Damus
 */
public class CrossReferenceUtil {

	/**
	 * Not instantiable by clients.
	 */
	private CrossReferenceUtil() {
		super();
	}

	public static <T> Stream<T> invert(EObject referent, EReference reference, Class<T> ownerType) {
		return invert(referent, reference).map(EStructuralFeature.Setting::getEObject)
				.filter(ownerType::isInstance).map(ownerType::cast);
	}

	public static <T> Optional<T> invertSingle(EObject referent, EReference reference, Class<T> ownerType) {
		return invert(referent, reference, ownerType).findAny();
	}

	private static Stream<EStructuralFeature.Setting> invert(EObject referent, EReference reference) {
		CacheAdapter cache = CacheAdapter.getCacheAdapter(referent);
		if (cache == null) {
			return Stream.empty();
		}

		return cache.getInverseReferences(referent).stream()
				.filter(setting -> setting.getEStructuralFeature() == reference);
	}
}
