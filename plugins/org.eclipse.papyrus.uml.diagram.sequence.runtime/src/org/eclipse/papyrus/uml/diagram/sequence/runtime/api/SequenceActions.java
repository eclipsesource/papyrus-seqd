/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.api;

import java.util.List;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;

/**
 * <p>
 * High level API to manipulate the semantic elements for a Sequence Diagram: creation, deletion...
 * </p>
 * <p>
 * The API doesn't edit the model directly, but returns commands, which can then be chained and executed as
 * necessary. The returned commands may or may not be executable.
 * </p>
 * <p>
 * This API only considers the semantic model, as the graphics will be initially derived from the semantics.
 * Cosmetic changes (such as increasing the gap between elements) should be done in a separate step.
 * </p>
 */
// Supported actions:
// - Create
// - Delete
// - Reorder/Reparent (Nodes)
// - Retarget (Edges)
//
// Supported elements:
// - Lifeline
// - Action/Behavior ExecutionSpecification
// - Message
// - DurationConstraint
// - DestructionOccurrenceSpecification
// - InteractionUse
public interface SequenceActions {

	/**
	 * <p>
	 * Creates a new {@link Lifeline} on the given interaction
	 * </p>
	 * 
	 * @param interaction
	 *            The interaction in which the {@link Lifeline} will be created
	 */
	default ICommand createLifeline(Interaction interaction) {
		return createLifeline(interaction, null);
	}

	/**
	 * <p>
	 * Creates a new {@link Lifeline} at the given index in the interaction
	 * </p>
	 * 
	 * @param interaction
	 *            The interaction in which the {@link Lifeline} will be created
	 * @param insertBefore
	 *            The {@link Lifeline} before which the new {@link Lifeline} should be created, in the
	 *            {@link Interaction#getLifelines() lifelines list}. If null, the lifeline will be added at
	 *            the end.
	 */
	ICommand createLifeline(Interaction interaction, Lifeline insertBefore);

	ICommand createActionExecutionSpecification(Element element);

	ICommand createBehaviorExecutionSpecification(Element element);

	ICommand createDestruction(Lifeline lifeline);

	ICommand createInteractionUse(Interaction usedInteraction, List<Lifeline> coveredLifelines);

	/**
	 * <p>
	 * Creates a {@link Message}, as well as the required {@link MessageEnd MessageEnds}, from the given
	 * source to the given target element.
	 * </p>
	 * <p>
	 * Source and Target elements may be {@link Lifeline Lifelines}, {@link Gate Gates},
	 * {@link InteractionFragment InteractionFragments}...
	 * </p>
	 * <p>
	 * The message is inserted at the end of the source and target elements
	 * </p>
	 * 
	 * @param sourceElement
	 * @param targetElement
	 * @param messageSort
	 *            The kind of message to create
	 */
	default ICommand createMessage(Element sourceElement, Element targetElement, MessageSort messageSort) {
		return createMessage(sourceElement, targetElement, null, null, messageSort);
	}

	/**
	 * <p>
	 * Creates a {@link Message}, as well as the required {@link MessageEnd MessageEnds}, from the given
	 * source to the given target element.
	 * </p>
	 * <p>
	 * Source and Target elements may be {@link Lifeline Lifelines}, {@link Gate Gates},
	 * {@link InteractionFragment InteractionFragments}...
	 * </p>
	 * <p>
	 * The message is inserted before the given source and target elements (Or at the end if they are not
	 * specified). The <code>insertSourceBefore</code> corresponds to the position of the {@link Message}'s
	 * source {@link MessageEnd}, while the <code>insertTargetBefore</code> corresponds to the position of the
	 * {@link Message}'s target {@link MessageEnd}. A <code>null</code> value indicates that the message
	 * should be inserted at the end.
	 * </p>
	 * 
	 * @param sourceElement
	 * @param targetElement
	 * @param insertSourceBefore
	 * @param insertTargetBefore
	 * @param messageSort
	 */
	ICommand createMessage(Element sourceElement, Element targetElement, Element insertSourceBefore,
			Element insertTargetBefore, MessageSort messageSort);

	/**
	 * <p>
	 * Create a new {@link MessageSort#CREATE_MESSAGE}, as well as the associated {@link Lifeline}
	 * </p>
	 * 
	 * @param sourceElement
	 *            The source of the message
	 */
	ICommand createCreateMessage(Element sourceElement);

	/**
	 * <p>
	 * Create a new {@link MessageSort#CREATE_MESSAGE}
	 * </p>
	 * 
	 * @param sourceElement
	 *            The source of the message
	 * @param createdLifeline
	 *            The target of the message (i.e. the created lifeline)
	 */
	ICommand createCreateMessage(Element sourceElement, Lifeline createdLifeline);

	/**
	 * Create a new {@link DurationConstraint} between the given elements
	 * 
	 * @param source
	 *            The source element (Beginning of the {@link DurationConstraint})
	 * @param target
	 *            The target element (End of the {@link DurationConstraint})
	 */
	ICommand createDurationConstraint(Element source, Element target);

	/**
	 * <p>
	 * Reorder (and/or reparent) an {@link InteractionFragment}.
	 * </p>
	 * 
	 * @param fragment
	 *            The fragment to reorder or reparent
	 * @param newTarget
	 *            The new parent for the fragment ({@link Lifeline}, {@link ExecutionSpecification}, ...)
	 * @param index
	 *            The new position for the fragment
	 */
	ICommand moveFragment(InteractionFragment fragment, Element newTarget, Element insertBefore);

	/**
	 * <p>
	 * Change the source of a {@link Message}, by retargeting it to the given <code>newSource</code> element
	 * and inserting the message's source end before the given <code>insertSourceBefore</code> element.
	 * </p>
	 * 
	 * @param message
	 *            The message to retarget
	 * @param newSource
	 *            The new source element for the message
	 * @param insertSourceBefore
	 *            The element before which the message's source end should be inserted, or <code>null</code>
	 *            to add the message at the end.
	 */
	ICommand retargetMessageSource(Message message, Element newSource, Element insertSourceBefore);

	/**
	 * <p>
	 * Change the target of a {@link Message}, by retargeting it to the given <code>newTarget</code> element
	 * and inserting the message's target end before the given <code>insertTargetBefore</code> element.
	 * </p>
	 * 
	 * @param message
	 *            The message to retarget
	 * @param newtarget
	 *            The new target element for the message
	 * @param insertTargetBefore
	 *            The element before which the message's target end should be inserted, or <code>null</code>
	 *            to add the message at the end.
	 */
	ICommand retargetMessageTarget(Message message, Element newTarget, Element insertTargetBefore);

	/**
	 * <p>
	 * Tests whether the given element is a possible source for creating a {@link Message}. The message target
	 * is unspecified.
	 * </p>
	 * 
	 * @param sourceElement
	 *            The source element to test
	 * @param messageSort
	 *            The kind of message being created
	 * @param insertSourceBefore
	 *            The element before which the new message should be created
	 */
	boolean isPossibleMessageSource(Element sourceElement, MessageSort messageSort,
			Element insertSourceBefore);

	/**
	 * <p>
	 * Tests whether the given element is a possible source for creating a {@link DurationConstraint}. The
	 * constraint target is unspecified.
	 * </p>
	 * 
	 * @param sourceElement
	 *            The source element to test
	 */
	boolean isPossibleDurationConstraintSource(Element sourceElement);

	/**
	 * <p>
	 * Deletes an element, as well as all its dependent/contained elements
	 * </p>
	 * 
	 * @param elementToDelete
	 *            The element to delete
	 */
	ICommand delete(Element elementToDelete);
}
