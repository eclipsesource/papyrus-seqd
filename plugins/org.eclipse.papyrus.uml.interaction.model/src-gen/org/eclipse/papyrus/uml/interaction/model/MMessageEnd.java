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

import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.uml2.uml.MessageEnd;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>MMessage End</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isSend <em>Send</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isReceive <em>Receive</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOtherEnd <em>Other End</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessageEnd()
 * @model
 * @generated
 */
public interface MMessageEnd extends MOccurrence<MessageEnd> {
	/**
	 * Returns the value of the '<em><b>Send</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Send</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Send</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessageEnd_Send()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isSend();

	/**
	 * Returns the value of the '<em><b>Receive</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receive</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Receive</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessageEnd_Receive()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isReceive();

	/**
	 * Returns the value of the '<em><b>Other End</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Other End</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Other End</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessageEnd_OtherEnd()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MMessageEnd&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MMessageEnd> getOtherEnd();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated
	 */
	@Override
	MMessage getOwner();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 *        dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.gmf.runtime.notation.IdentityAnchor&gt;"
	 *        required="true"
	 * @generated
	 */
	@Override
	Optional<IdentityAnchor> getDiagramView();

} // MMessageEnd
