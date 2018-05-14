/**
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.sequence.assistants;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsPackage
 * @generated
 */
public interface DiagramAssistantsFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	DiagramAssistantsFactory eINSTANCE = org.eclipse.papyrus.uml.diagram.sequence.assistants.impl.DiagramAssistantsFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Sequence Diagram Assistant Provider</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Sequence Diagram Assistant Provider</em>'.
	 * @generated
	 */
	SequenceDiagramAssistantProvider createSequenceDiagramAssistantProvider();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	DiagramAssistantsPackage getDiagramAssistantsPackage();

} // DiagramAssistantsFactory
