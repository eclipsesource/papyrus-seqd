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
import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>MElement</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getInteraction <em>Interaction</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getElement <em>Element</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getDiagramView <em>Diagram View</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getTop <em>Top</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getBottom <em>Bottom</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMElement()
 * @model abstract="true"
 * @extends MObject
 * @generated
 */
public interface MElement<T extends Element> extends MObject {
	/**
	 * Returns the value of the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interaction</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Interaction</em>' reference.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMElement_Interaction()
	 * @model resolveProxies="false" required="true" transient="true" changeable="false" volatile="true"
	 *        derived="true"
	 * @generated
	 */
	MInteraction getInteraction();

	/**
	 * Returns the value of the '<em><b>Element</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Element</em>' reference.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMElement_Element()
	 * @model resolveProxies="false" required="true" transient="true" changeable="false" volatile="true"
	 *        derived="true"
	 * @generated
	 */
	T getElement();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram View</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @model kind="operation" dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;? extends
	 *        org.eclipse.emf.ecore.EObject&gt;" required="true"
	 * @generated
	 */
	Optional<? extends EObject> getDiagramView();

	/**
	 * Returns the value of the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Top</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Top</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMElement_Top()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.OptionalInt" required="true"
	 *        transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	OptionalInt getTop();

	/**
	 * Returns the value of the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bottom</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Bottom</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMElement_Bottom()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.OptionalInt" required="true"
	 *        transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	OptionalInt getBottom();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMElement_Name()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated
	 */
	MElement<?> getOwner();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.OptionalInt" required="true"
	 *        otherRequired="true"
	 * @generated
	 */
	OptionalInt verticalDistance(MElement<?> other);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MElement&lt;?
	 *        extends org.eclipse.uml2.uml.Element&gt;&gt;" required="true"
	 * @generated
	 */
	Optional<MElement<? extends Element>> following();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Command" required="true"
	 *        deltaYRequired="true"
	 * @generated
	 */
	Command nudge(int deltaY);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Command" required="true"
	 * @generated
	 */
	Command remove();

} // MElement
