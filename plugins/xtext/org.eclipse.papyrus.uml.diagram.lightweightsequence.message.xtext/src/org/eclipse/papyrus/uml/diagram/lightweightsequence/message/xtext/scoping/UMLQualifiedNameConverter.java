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
import org.eclipse.xtext.naming.IQualifiedNameConverter.DefaultImpl;

/**
 * Qualified name converter for UML, implementing the UML convention for the
 * namespace separator.
 *
 * @author Christian W. Damus
 */
public class UMLQualifiedNameConverter extends DefaultImpl {

	/**
	 * Initializes me.
	 */
	public UMLQualifiedNameConverter() {
		super();
	}

	@Override
	public String getDelimiter() {
		return NamedElement.SEPARATOR;
	}
}
