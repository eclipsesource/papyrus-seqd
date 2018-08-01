/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.interaction.model.spi;

import java.util.Collection;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationParameters;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Protocol for a pluggable utility that provides creation and modification of the semantics of an UML
 * interaction.
 *
 * @author Christian W. Damus
 */
public interface SemanticHelper {

	//
	// Generic commands
	//

	/**
	 * Obtain a command that appends {@code values} to a {@code feature} of an {@code owner} object.
	 * 
	 * @param owner
	 *            the owner of a {@code feature}
	 * @param feature
	 *            the feature of the {@code owner}
	 * @param values
	 *            values to append to the {@code feature} of the {@code owner}
	 * @return the command
	 */
	Command add(EObject owner, EStructuralFeature feature, Collection<?> values);

	/**
	 * Obtain a command that appends a {@code value} to a {@code feature} of an {@code owner} object.
	 * 
	 * @param owner
	 *            the owner of a {@code feature}
	 * @param feature
	 *            the feature of the {@code owner}
	 * @param value
	 *            a value to insert in the {@code feature} of the {@code owner}
	 * @return the command
	 */
	Command add(EObject owner, EStructuralFeature feature, Object value);

	/**
	 * Obtain a command that sets a {@code value} in a {@code feature} of an {@code owner} object.
	 * 
	 * @param owner
	 *            the owner of a {@code feature}
	 * @param feature
	 *            the feature of the {@code owner}
	 * @param value
	 *            a value to set in the {@code feature} of the {@code owner}
	 * @return the command
	 */
	Command set(EObject owner, EStructuralFeature feature, Object value);

	/**
	 * Obtain a command that will remove an EObject from its container and any resource.
	 * 
	 * @param toDelete
	 *            the object to delete
	 * @return the command
	 */
	Command delete(EObject toDelete);

	/**
	 * Obtain a command that inserts {@code values} after some other in a {@code feature} of an {@code owner}
	 * object. If the {@code feature} does not actually have this reference element, then the new elements are
	 * just appended.
	 * 
	 * @param owner
	 *            the owner of a {@code feature}
	 * @param feature
	 *            the feature of the {@code owner}
	 * @param before
	 *            an element after which to insert the new {@code values}, if it is in the {@code feature}
	 * @param values
	 *            values to insert in the {@code feature} of the {@code owner}
	 * @return the command
	 */
	Command insertAfter(EObject owner, EStructuralFeature feature, Object before, Collection<?> values);

	/**
	 * Obtain a command that inserts a {@code value} after some other in a {@code feature} of an {@code owner}
	 * object. If the {@code feature} does not actually have this reference element, then the new element is
	 * just appended.
	 * 
	 * @param owner
	 *            the owner of a {@code feature}
	 * @param feature
	 *            the feature of the {@code owner}
	 * @param before
	 *            an element after which to insert the new {@code value}, if it is in the {@code feature}
	 * @param value
	 *            a value to append to the {@code feature} of the {@code owner}
	 * @return the command
	 */
	Command insertAfter(EObject owner, EStructuralFeature feature, Object before, Object value);

	/**
	 * Obtain a command that inserts {@code objects} after some other in the same containment as that other,
	 * if possible. Otherwise, they are appended to the best containment available.
	 * 
	 * @param before
	 *            an element after which to insert the new {@code object}
	 * @param objects
	 *            objects to insert after the reference element
	 * @return the command
	 */
	Command insertAfter(EObject before, Collection<? extends EObject> objects);

	/**
	 * Obtain a command that inserts a {@code value} after some other in the same containment as that other,
	 * if possible. Otherwise, it is appended to the best containment available.
	 * 
	 * @param before
	 *            an element after which to insert the new {@code object}
	 * @param object
	 *            an object to insert after the reference element
	 * @return the command
	 */
	Command insertAfter(EObject before, EObject object);

	/**
	 * Obtain a command that inserts {@code values} before some other in a {@code feature} of an {@code owner}
	 * object. If the {@code feature} does not actually have this reference element, then the new elements are
	 * just appended.
	 * 
	 * @param owner
	 *            the owner of a {@code feature}
	 * @param feature
	 *            the feature of the {@code owner}
	 * @param after
	 *            an element before which to insert the new {@code values}, if it is in the {@code feature}
	 * @param values
	 *            values to insert in the {@code feature} of the {@code owner}
	 * @return the command
	 */
	Command insertBefore(EObject owner, EStructuralFeature feature, Object after, Collection<?> values);

	/**
	 * Obtain a command that inserts a {@code value} before some other in a {@code feature} of an
	 * {@code owner} object. If the {@code feature} does not actually have this reference element, then the
	 * new element is just appended.
	 * 
	 * @param owner
	 *            the owner of a {@code feature}
	 * @param feature
	 *            the feature of the {@code owner}
	 * @param after
	 *            an element before which to insert the new {@code value}, if it is in the {@code feature}
	 * @param value
	 *            a value to append to the {@code feature} of the {@code owner}
	 * @return the command
	 */
	Command insertBefore(EObject owner, EStructuralFeature feature, Object after, Object value);

	/**
	 * Obtain a command that inserts {@code objects} before some other in the same containment as that other,
	 * if possible. Otherwise, they are appended to the best containment available.
	 * 
	 * @param after
	 *            an element before which to insert the new {@code object}
	 * @param objects
	 *            objects to insert before the reference element
	 * @return the command
	 */
	Command insertBefore(EObject after, Collection<? extends EObject> objects);

	/**
	 * Obtain a command that inserts a {@code value} before some other in the same containment as that other,
	 * if possible. Otherwise, it is appended to the best containment available.
	 * 
	 * @param after
	 *            an element before which to insert the new {@code object}
	 * @param object
	 *            an object to insert before the reference element
	 * @return the command
	 */
	Command insertBefore(EObject after, EObject object);

	//
	// Commands specific to UML Interaction semantics
	//

	/**
	 * Create a new lifeline.
	 * 
	 * @param parameters
	 *            creation parameters. The {@link CreationParameters#getEClass() eClass} parameter is fixed to
	 *            {@link Lifeline}
	 * @return the lifeline creation command
	 */
	CreationCommand<Lifeline> createLifeline(CreationParameters parameters);

	/**
	 * Create a new execution specification that represents execution of the given {@link specification}
	 * {@link Action} or {@link Behavior}.
	 * 
	 * @param specification
	 *            an {@link Action} or {@link Behavior} that is executed, or {@code null} for an unspecified
	 *            execution
	 * @param parameters
	 *            additional creation parameters. The {@link CreationParameters#getEClass() eClass} parameter
	 *            will be determined by the type of the {@code specification}
	 * @return the execution specification creation command
	 */
	CreationCommand<ExecutionSpecification> createExecutionSpecification(Element specification,
			CreationParameters parameters);

	/**
	 * Create a new start occurrence for the given {@code execution} specification.
	 * 
	 * @param execution
	 *            a deferred execution specification that the new occurrence is to start
	 * @param parameters
	 *            additional creation parameters
	 * @return a starting occurrence for the {@code execution} specification, properly linked to it
	 */
	CreationCommand<OccurrenceSpecification> createStart(Supplier<ExecutionSpecification> execution,
			CreationParameters parameters);

	/**
	 * Create a new finish occurrence for the given {@code execution} specification.
	 * 
	 * @param execution
	 *            a deferred execution specification that the new occurrence is to finish
	 * @param parameters
	 *            additional creation parameters
	 * @return a finishing occurrence for the {@code execution} specification, properly linked to it
	 */
	CreationCommand<OccurrenceSpecification> createFinish(Supplier<ExecutionSpecification> execution,
			CreationParameters parameters);

	/**
	 * Create a new message occurrence specification.
	 * 
	 * @param parameters
	 *            creation parameters. The {@link CreationParameters#getEClass() eClass} parameter is fixed to
	 *            {@link MessageOccurrenceSpecification}
	 * @return the message occurrence creation command
	 */
	CreationCommand<MessageEnd> createMessageOccurrence(CreationParameters parameters);

	/**
	 * Create a new destruction occurrence specification.
	 * 
	 * @param parameters
	 *            creation parameters. The {@link CreationParameters#getEClass() eClass} parameter is fixed to
	 *            {@link DestructionOccurrenceSpecification}
	 * @return the message occurrence creation command
	 */
	CreationCommand<MessageEnd> createDestructionOccurrence(CreationParameters parameters);

	/**
	 * Create a new message.
	 * 
	 * @param sendEvent
	 *            the send event
	 * @param recvEvent
	 *            the receive event
	 * @param sort
	 *            the message sort
	 * @param signature
	 *            the message signature, if any
	 * @param parameters
	 *            additional creation parameters
	 * @return the message creation command
	 */
	CreationCommand<Message> createMessage(Supplier<? extends MessageEnd> sendEvent,
			Supplier<? extends MessageEnd> recvEvent, MessageSort sort, NamedElement signature,
			CreationParameters messageParams);

	/**
	 * Deletes an existing message.
	 * 
	 * @param message
	 *            the message to delete
	 * @return the delete command
	 */
	RemovalCommand<Element> deleteMessage(Message message);

	/**
	 * Delete an existing execution specification.
	 * 
	 * @param execution
	 *            the execution to delete
	 * @return the delete command
	 */
	RemovalCommand<Element> deleteExecutionSpecification(ExecutionSpecification execution);

	/**
	 * Delete an existing lifeline.
	 * 
	 * @param lifeline
	 *            the lifeline to delete
	 * @return the delete command
	 */
	RemovalCommand<Element> deleteLifeline(Lifeline lifeline);

}
