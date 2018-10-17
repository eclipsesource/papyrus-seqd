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

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>MExecution</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getStart <em>Start</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getFinish <em>Finish</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getOccurrences <em>Occurrences</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMExecution()
 * @model
 * @generated
 */
public interface MExecution extends MElement<ExecutionSpecification> {
	/**
	 * Returns the value of the '<em><b>Start</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc --> <!-- begin-model-doc --> the starting occurrence, a subset of all of the spanned
	 * {@link #getOccurrences() occurrences} <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Start</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMExecution_Start()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MOccurrence&lt;?
	 *        extends org.eclipse.uml2.uml.Element&gt;&gt;" required="true" transient="true"
	 *        changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MOccurrence<? extends Element>> getStart();

	/**
	 * Returns the value of the '<em><b>Finish</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Finish</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc --> <!-- begin-model-doc --> the finishing occurrence, a subset of all of the spanned
	 * {@link #getOccurrences() occurrences} <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Finish</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMExecution_Finish()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MOccurrence&lt;?
	 *        extends org.eclipse.uml2.uml.Element&gt;&gt;" required="true" transient="true"
	 *        changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MOccurrence<? extends Element>> getFinish();

	/**
	 * Returns the value of the '<em><b>Occurrences</b></em>' attribute list. The list contents are of type
	 * {@link java.util.Optional}<code>&lt;org.eclipse.papyrus.uml.interaction.model.MOccurrence&lt;? extends org.eclipse.uml2.uml.Element&gt;&gt;</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> list of all occurrences on its
	 * lifeline that are spanned by the execution, including the {@link #getStart() start} and
	 * {@link #getFinish() finish} <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Occurrences</em>' attribute list.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMExecution_Occurrences()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MOccurrence&lt;?
	 *        extends org.eclipse.uml2.uml.Element&gt;&gt;" lower="2" transient="true" changeable="false"
	 *        volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	List<MOccurrence<? extends Element>> getOccurrences();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated
	 */
	@Override
	MLifeline getOwner();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 *        dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.gmf.runtime.notation.Shape&gt;"
	 *        required="true"
	 * @generated
	 */
	@Override
	Optional<Shape> getDiagramView();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
	 * 
	 * @param yPosition
	 *            the absolute Y position at which to place the top of the execution on the lifeline, or empty
	 *            to maintain the current Y position. <!-- end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Command" required="true"
	 *        newOwnerRequired="true"
	 *        yPositionDataType="org.eclipse.papyrus.uml.interaction.model.OptionalInt"
	 *        yPositionRequired="true"
	 * @generated
	 */
	Command setOwner(MLifeline newOwner, OptionalInt yPosition);

} // MExecution
