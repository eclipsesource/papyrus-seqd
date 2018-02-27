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

package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.scoping;

import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Qualified name converter for UML, using the specific {@link NamedElement}
 * semantics.
 *
 * @author Christian W. Damus
 */
public class UMLQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	/**
	 * Initializes me.
	 */
	public UMLQualifiedNameProvider() {
		super();
	}

	public QualifiedName qualifiedName(NamedElement element) {
		// UML explicitly allows null names
		String result = element.getQualifiedName();

		return result == null ? QualifiedName.EMPTY : getConverter().toQualifiedName(result);
	}
}
