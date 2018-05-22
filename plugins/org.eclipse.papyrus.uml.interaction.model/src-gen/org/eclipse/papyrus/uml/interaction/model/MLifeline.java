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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Shape;
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
	 * @model containment="true" changeable="false"
	 * @generated
	 */
	List<MExecution> getExecutions();

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

} // MLifeline
