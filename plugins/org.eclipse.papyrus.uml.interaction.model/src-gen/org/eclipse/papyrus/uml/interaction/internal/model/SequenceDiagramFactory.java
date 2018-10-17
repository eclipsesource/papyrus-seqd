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
 * 
 */
package org.eclipse.papyrus.uml.interaction.internal.model;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.papyrus.uml.interaction.model.MDestruction;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage
 * @generated
 */
public interface SequenceDiagramFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	SequenceDiagramFactory eINSTANCE = org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>MInteraction</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>MInteraction</em>'.
	 * @generated
	 */
	MInteraction createMInteraction();

	/**
	 * Returns a new object of class '<em>MLifeline</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>MLifeline</em>'.
	 * @generated
	 */
	MLifeline createMLifeline();

	/**
	 * Returns a new object of class '<em>MExecution</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>MExecution</em>'.
	 * @generated
	 */
	MExecution createMExecution();

	/**
	 * Returns a new object of class '<em>MExecution Occurrence</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>MExecution Occurrence</em>'.
	 * @generated
	 */
	MExecutionOccurrence createMExecutionOccurrence();

	/**
	 * Returns a new object of class '<em>MMessage End</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>MMessage End</em>'.
	 * @generated
	 */
	MMessageEnd createMMessageEnd();

	/**
	 * Returns a new object of class '<em>MMessage</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>MMessage</em>'.
	 * @generated
	 */
	MMessage createMMessage();

	/**
	 * Returns a new object of class '<em>MDestruction</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>MDestruction</em>'.
	 * @generated
	 */
	MDestruction createMDestruction();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	SequenceDiagramPackage getSequenceDiagramPackage();

} // SequenceDiagramFactory
