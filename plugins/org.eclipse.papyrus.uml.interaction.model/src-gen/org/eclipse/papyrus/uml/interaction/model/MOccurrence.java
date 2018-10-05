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
package org.eclipse.papyrus.uml.interaction.model;

import java.util.Optional;

import org.eclipse.uml2.uml.Element;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>MOccurrence</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getCovered <em>Covered</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#isStart <em>Start</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getStartedExecution <em>Started
 * Execution</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#isFinish <em>Finish</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getFinishedExecution <em>Finished
 * Execution</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMOccurrence()
 * @model abstract="true"
 * @generated
 */
public interface MOccurrence<T extends Element> extends MElement<T> {
	/**
	 * Returns the value of the '<em><b>Covered</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Covered</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Covered</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMOccurrence_Covered()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MLifeline&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MLifeline> getCovered();

	/**
	 * Returns the value of the '<em><b>Start</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMOccurrence_Start()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isStart();

	/**
	 * Returns the value of the '<em><b>Started Execution</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Started Execution</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Started Execution</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMOccurrence_StartedExecution()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MExecution&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MExecution> getStartedExecution();

	/**
	 * Returns the value of the '<em><b>Finish</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Finish</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Finish</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMOccurrence_Finish()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isFinish();

	/**
	 * Returns the value of the '<em><b>Finished Execution</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Finished Execution</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Finished Execution</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMOccurrence_FinishedExecution()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MExecution&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MExecution> getFinishedExecution();

} // MOccurrence
