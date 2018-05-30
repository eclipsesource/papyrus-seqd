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
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>MMessage</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getSend <em>Send</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getReceive <em>Receive</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessage()
 * @model
 * @generated
 */
public interface MMessage extends MElement<Message> {
	/**
	 * Returns the value of the '<em><b>Send</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Send</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Send</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessage_Send()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MMessageEnd&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MMessageEnd> getSend();

	/**
	 * Returns the value of the '<em><b>Receive</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receive</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Receive</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessage_Receive()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MMessageEnd&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MMessageEnd> getReceive();

	/**
	 * Returns the value of the '<em><b>Sender</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sender</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Sender</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessage_Sender()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MLifeline&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MLifeline> getSender();

	/**
	 * Returns the value of the '<em><b>Receiver</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receiver</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Receiver</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMMessage_Receiver()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MLifeline&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MLifeline> getReceiver();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated
	 */
	@Override
	MInteraction getOwner();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 *        dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.gmf.runtime.notation.Connector&gt;"
	 *        required="true"
	 * @generated
	 */
	@Override
	Optional<Connector> getDiagramView();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MMessageEnd&gt;"
	 *        required="true" endRequired="true"
	 * @generated
	 */
	Optional<MMessageEnd> getEnd(MessageEnd end);

} // MMessage
