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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.model.spi.ExecutionCreationCommandParameter;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>MLifeline</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutionOccurrences <em>Execution
 * Occurrences</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutions <em>Executions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMLifeline()
 * @model
 * @generated
 */
public interface MLifeline extends MElement<Lifeline> {
	/**
	 * Returns the value of the '<em><b>Execution Occurrences</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execution Occurrences</em>' containment reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Execution Occurrences</em>' containment reference list.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMLifeline_ExecutionOccurrences()
	 * @model containment="true" changeable="false"
	 * @generated
	 */
	List<MExecutionOccurrence> getExecutionOccurrences();

	/**
	 * Returns the value of the '<em><b>Executions</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.papyrus.uml.interaction.model.MExecution}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Executions</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Executions</em>' containment reference list.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMLifeline_Executions()
	 * @model containment="true" changeable="false" ordered="false"
	 * @generated
	 */
	List<MExecution> getExecutions();

	/**
	 * Returns the value of the '<em><b>Destruction</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destruction</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Destruction</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMLifeline_Destruction()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MDestruction&gt;"
	 *        required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Optional<MDestruction> getDestruction();

	/**
	 * Returns the value of the '<em><b>Left</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Left</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMLifeline_Left()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.OptionalInt" required="true"
	 *        transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	OptionalInt getLeft();

	/**
	 * Returns the value of the '<em><b>Right</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Right</em>' attribute.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMLifeline_Right()
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.OptionalInt" required="true"
	 *        transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	OptionalInt getRight();

	/**
	 * Returns the value of the '<em><b>Message Ends</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd}. <!-- begin-user-doc --> <!--
	 * end-user-doc --> <!-- begin-model-doc --> message ends covering this lifeline. A subset of the list of
	 * all {@link #getOccurrences() occurrences} <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Message Ends</em>' reference list.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMLifeline_MessageEnds()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	List<MMessageEnd> getMessageEnds();

	/**
	 * Returns the value of the '<em><b>Occurrences</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.papyrus.uml.interaction.model.MOccurrence}<code>&lt;? extends org.eclipse.uml2.uml.Element&gt;</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> all occurrences covering this
	 * lifeline. A derived union of the {@link #getMessageEnds() message ends} and owned
	 * {@link #getExecutionOccurrences() execution occurrences} <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Occurrences</em>' reference list.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMLifeline_Occurrences()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
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
	MInteraction getOwner();

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MElement&lt;?
	 *        extends org.eclipse.uml2.uml.Element&gt;&gt;" required="true" elementRequired="true"
	 * @generated
	 */
	Optional<MElement<? extends Element>> following(MElement<? extends Element> element);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MElement&lt;?
	 *        extends org.eclipse.uml2.uml.Element&gt;&gt;" required="true" elementRequired="true"
	 * @generated
	 */
	Optional<MElement<? extends Element>> preceding(MElement<? extends Element> element);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence&gt;"
	 *        required="true" occurrenceRequired="true"
	 * @generated
	 */
	Optional<MExecutionOccurrence> getExecutionOccurrence(ExecutionOccurrenceSpecification occurrence);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MExecution&gt;"
	 *        required="true" executionRequired="true"
	 * @generated
	 */
	Optional<MExecution> getExecution(ExecutionSpecification execution);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MDestruction&gt;"
	 *        required="true" destructionRequired="true"
	 * @generated
	 */
	Optional<MDestruction> getDestruction(DestructionOccurrenceSpecification destruction);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.CreationCommand&lt;org.eclipse.uml2.uml.ExecutionSpecification&gt;"
	 *        required="true" beforeRequired="true" offsetRequired="true" heightRequired="true"
	 *        specificationRequired="true"
	 * @generated
	 */
	CreationCommand<ExecutionSpecification> insertExecutionAfter(MElement<?> before, int offset, int height,
			Element specification);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.CreationCommand&lt;org.eclipse.uml2.uml.ExecutionSpecification&gt;"
	 *        required="true" beforeRequired="true" offsetRequired="true" heightRequired="true"
	 *        metaclassRequired="true"
	 * @generated
	 */
	CreationCommand<ExecutionSpecification> insertExecutionAfter(MElement<?> before, int offset, int height,
			EClass metaclass);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
	 * 
	 * @param before
	 *            The element after which to insert the message. If it is an interaction fragment on this
	 *            lifeline, then the new message’s send event is inserted after this element. If it is a
	 *            message, then the new message’s send event is inserted after the referenced message’s end
	 *            that covers this lifeline.
	 * @param offset
	 *            Vertical offset of the message from the element {@code before} it.
	 * @param receiver
	 *            The lifeline to receive the message. May be the same as is sending it (this lifeline).
	 * @param sort
	 *            The sort of message to create.
	 * @param signature
	 *            An optional message signature, either a signal or an operation. <!-- end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.CreationCommand&lt;org.eclipse.uml2.uml.Message&gt;"
	 *        required="true" beforeRequired="true" offsetRequired="true" receiverRequired="true"
	 *        sortRequired="true"
	 * @generated
	 */
	CreationCommand<Message> insertMessageAfter(MElement<?> before, int offset, MLifeline receiver,
			MessageSort sort, NamedElement signature);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
	 * 
	 * @param beforeSend
	 *            The element after which to insert the message send event. If it is an interaction fragment
	 *            on this lifeline, then the new message’s send event is inserted after this element. If it is
	 *            a message, then the new message’s send event is inserted after the referenced message’s end
	 *            that covers this lifeline.
	 * @param sendOffset
	 *            Vertical offset of the send event from the element before it, or from the lifeline head if
	 *            none.
	 * @param receiver
	 *            The lifeline to receive the message. May be the same as is sending it (this lifeline).
	 * @param beforeRecv
	 *            The element after which to insert the message receive event. If it is an interaction
	 *            fragment on the {@code receiver}, then the new message’s receive event is inserted after
	 *            this element. If it is a message, then the new message’s receive event is inserted after the
	 *            referenced message’s end that covers this lifeline.
	 * @param recvOffset
	 *            Vertical offset of the receive end from the element before it, or from the lifeline head if
	 *            none.
	 * @param sort
	 *            The sort of message to create.
	 * @param signature
	 *            An optional message signature, either a signal or an operation. <!-- end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.CreationCommand&lt;org.eclipse.uml2.uml.Message&gt;"
	 *        required="true" beforeSendRequired="true" sendOffsetRequired="true" receiverRequired="true"
	 *        beforeRecvRequired="true" recvOffsetRequired="true" sortRequired="true"
	 * @generated
	 */
	CreationCommand<Message> insertMessageAfter(MElement<?> beforeSend, int sendOffset, MLifeline receiver,
			MElement<?> beforeRecv, int recvOffset, MessageSort sort, NamedElement signature);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
	 * 
	 * @param beforeSend
	 *            The element after which to insert the message send event. If it is an interaction fragment
	 *            on this lifeline, then the new message’s send event is inserted after this element. If it is
	 *            a message, then the new message’s send event is inserted after the referenced message’s end
	 *            that covers this lifeline.
	 * @param sendOffset
	 *            Vertical offset of the send event from the element before it, or from the lifeline head if
	 *            none.
	 * @param receiver
	 *            The lifeline to receive the message. May be the same as is sending it (this lifeline).
	 * @param beforeRecv
	 *            The element after which to insert the message receive event. If it is an interaction
	 *            fragment on the {@code receiver}, then the new message’s receive event is inserted after
	 *            this element. If it is a message, then the new message’s receive event is inserted after the
	 *            referenced message’s end that covers this lifeline.
	 * @param recvOffset
	 *            Vertical offset of the receive end from the element before it, or from the lifeline head if
	 *            none.
	 * @param sort
	 *            The sort of message to create.
	 * @param signature
	 *            An optional message signature, either a signal or an operation.
	 * @param executionCreationConfig
	 *            The configuration specifying details about the creation of an execution associated for the
	 *            message. <!-- end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.CreationCommand&lt;org.eclipse.uml2.uml.Message&gt;"
	 *        required="true" beforeSendRequired="true" sendOffsetRequired="true" receiverRequired="true"
	 *        beforeRecvRequired="true" recvOffsetRequired="true" sortRequired="true"
	 *        executionCreationConfigDataType="org.eclipse.papyrus.uml.interaction.model.ExecutionCreationConfig"
	 * @generated
	 */
	CreationCommand<Message> insertMessageAfter(MElement<?> beforeSend, int sendOffset, MLifeline receiver,
			MElement<?> beforeRecv, int recvOffset, MessageSort sort, NamedElement signature,
			ExecutionCreationCommandParameter executionCreationCommandParameter);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
	 * 
	 * @param before
	 *            The element after which to insert the message. If it is an interaction fragment on this
	 *            lifeline, then the new message’s send event is inserted after this element. If it is a
	 *            message, then the new message’s send event is inserted after the referenced message’s end
	 *            that covers this lifeline.
	 * @param offset
	 *            Vertical offset of the message from the element {@code before} it.
	 * @param receiver
	 *            The lifeline to receive the message. May be the same as is sending it (this lifeline).
	 * @param sort
	 *            The sort of message to create.
	 * @param signature
	 *            An optional message signature, either a signal or an operation.
	 * @param executionCreationConfig
	 *            The configuration specifying details about the creation of an execution associated for the
	 *            message. <!-- end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.CreationCommand&lt;org.eclipse.uml2.uml.Message&gt;"
	 *        required="true" beforeRequired="true" offsetRequired="true" receiverRequired="true"
	 *        sortRequired="true"
	 *        executionCreationConfigDataType="org.eclipse.papyrus.uml.interaction.model.ExecutionCreationConfig"
	 * @generated
	 */
	CreationCommand<Message> insertMessageAfter(MElement<?> before, int offset, MLifeline receiver,
			MessageSort sort, NamedElement signature,
			ExecutionCreationCommandParameter executionCreationCommandParameter);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Query the element covering this
	 * lifeline that is at, or the nearest element before, a given y-coördinate {@code offset}.
	 * 
	 * @param offset
	 *            an offset in the y axis on the lifeline body (stem). Should be non-negative <!--
	 *            end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MElement&lt;?&gt;&gt;"
	 *        required="true" offsetRequired="true"
	 * @generated
	 */
	Optional<MElement<? extends Element>> elementAt(int offset);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Nudge the lifeline horizontally,
	 * along with all of the layout that depends on it.
	 * 
	 * @param deltaX
	 *            the distance by which to nudge the lifeline in the X axis (negative values nudging to the
	 *            left, of course) <!-- end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Command" required="true"
	 *        deltaXRequired="true"
	 * @generated
	 */
	Command nudgeHorizontally(int deltaX);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Obtain a command that makes the
	 * receiver created at some Y position in the diagram.
	 * 
	 * @param yPosition
	 *            The absolute Y position at which to show the receiver as created (where the create message
	 *            is received), or empty to revert to an uncreated lifeline. <!-- end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Command" required="true"
	 *        yPositionDataType="org.eclipse.papyrus.uml.interaction.model.OptionalInt"
	 *        yPositionRequired="true"
	 * @generated
	 */
	Command makeCreatedAt(OptionalInt yPosition);

} // MLifeline
