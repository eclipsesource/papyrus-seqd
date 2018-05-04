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

package org.eclipse.papyrus.uml.diagram.sequence.examples.graph;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interaction;

/**
 * This is the {@code InteractionPropertyTester} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class InteractionPropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		switch (property) {
			case "isInInteraction": //$NON-NLS-1$
				return isInInteraction((EObject)receiver) == asBoolean(expectedValue);
			default:
				return false;
		}
	}

	static boolean asBoolean(Object expectedValue) {
		if (expectedValue == null) {
			// Default when not specified is true
			return true;
		} else if (expectedValue instanceof Boolean) {
			return ((Boolean)expectedValue).booleanValue();
		} else if (expectedValue instanceof String) {
			return Boolean.parseBoolean((String)expectedValue);
		} else {
			return false;
		}
	}

	boolean isInInteraction(EObject object) {
		for (EObject next = object.eContainer(); next != null; next = next.eContainer()) {
			if (next instanceof Interaction) {
				return true;
			}
		}
		return false;
	}
}
