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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.InteractionModelBuilder;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>MInteraction</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifelines <em>Lifelines</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getMessages <em>Messages</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMInteraction()
 * @model
 * @generated
 */
public interface MInteraction extends MElement<Interaction> {

	/**
	 * Obtains the logical representation of an UML {@code interaction} as visualized in a sequence diagram.
	 * 
	 * @param interaction
	 *            an interaction in the underlying UML model
	 * @param sequenceDiagram
	 *            a sequence diagram visualizing it, or {@code null} if not available or needed
	 * @return the logical interaction model
	 */
	static MInteraction getInstance(Interaction interaction, Diagram sequenceDiagram) {
		// Do we already have an instance?
		MInteraction result = (MInteraction)EcoreUtil.getExistingAdapter(interaction, MObject.class);
		if (result == null) {
			result = InteractionModelBuilder.getInstance(interaction, sequenceDiagram).build();
		}
		return result;
	}

	/**
	 * Obtains the logical representation of the semantics of a sequence diagram and the UML interaction that
	 * it presents.
	 * 
	 * @param sequenceDiagram
	 *            a sequence diagram visualizing it
	 * @return the logical interaction model
	 */
	static MInteraction getInstance(Diagram sequenceDiagram) {
		return getInstance((Interaction)sequenceDiagram.getElement(), sequenceDiagram);
	}

	/**
	 * Obtains the logical representation of the semantics only of an UML {@code interaction}.
	 * 
	 * @param interaction
	 *            an interaction in the underlying UML model
	 * @return the logical interaction model
	 */
	static MInteraction getInstance(Interaction interaction) {
		// Do we already have an instance?
		MInteraction result = (MInteraction)EcoreUtil.getExistingAdapter(interaction, MObject.class);
		if (result == null) {
			result = InteractionModelBuilder.getInstance(interaction).build();
		}
		return result;
	}

	/**
	 * Returns the value of the '<em><b>Lifelines</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.papyrus.uml.interaction.model.MLifeline}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lifelines</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lifelines</em>' containment reference list.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMInteraction_Lifelines()
	 * @model containment="true" changeable="false"
	 * @generated
	 */
	List<MLifeline> getLifelines();

	/**
	 * Returns the value of the '<em><b>Messages</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.papyrus.uml.interaction.model.MMessage}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Messages</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Messages</em>' containment reference list.
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMInteraction_Messages()
	 * @model containment="true" changeable="false"
	 * @generated
	 */
	List<MMessage> getMessages();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 *        dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.gmf.runtime.notation.Diagram&gt;"
	 *        required="true"
	 * @generated
	 */
	@Override
	Optional<Diagram> getDiagramView();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MElement&lt;?
	 *        extends E&gt;&gt;" required="true" elementRequired="true"
	 * @generated
	 */
	<E extends Element> Optional<MElement<? extends E>> getElement(E element);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MLifeline&gt;"
	 *        required="true" lifelineRequired="true"
	 * @generated
	 */
	Optional<MLifeline> getLifeline(Lifeline lifeline);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MMessage&gt;"
	 *        required="true" messageRequired="true"
	 * @generated
	 */
	Optional<MMessage> getMessage(Message message);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
	 * 
	 * @param offset
	 *            Horizontal offset of the new lifeline from right side of the last existing lifeline or the
	 *            left edge of the interaction frame if this is the first lifeline.
	 * @param height
	 *            Height of the lifeline shape to create, or {@code -1} for the default. <!-- end-model-doc
	 *            -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.CreationCommand&lt;org.eclipse.uml2.uml.Lifeline&gt;"
	 *        required="true" offsetRequired="true" heightRequired="true"
	 * @generated
	 */
	CreationCommand<Lifeline> addLifeline(int xPosition, int height);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Queries the existing lifeline,
	 * if any, at or nearest to the left of a given x offset in the interaction.
	 * 
	 * @param offset
	 *            an x offset in the lifeline that is contained by or nearest to the right of the lifeline to
	 *            be retrieved <!-- end-model-doc -->
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.papyrus.uml.interaction.model.MLifeline&gt;"
	 *        required="true" offsetRequired="true"
	 * @generated
	 */
	Optional<MLifeline> getLifelineAt(int offset);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Returns the bottommost element,
	 * such as a message end, contained by this interaction. <!-- end-model-doc -->
	 * 
	 * @model kind="operation" dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;? extends
	 *        org.eclipse.papyrus.uml.interaction.model.MElement&lt;? extends
	 *        org.eclipse.uml2.uml.Element&gt;&gt;" required="true"
	 * @generated
	 */
	Optional<? extends MElement<? extends Element>> getBottommostElement();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Obtain a command that sorts the
	 * interaction's fragments in time order. <!-- end-model-doc -->
	 * 
	 * @model dataType="org.eclipse.papyrus.uml.interaction.model.Command" required="true"
	 * @generated
	 */
	Command sort();

} // MInteraction
