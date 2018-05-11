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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;

/**
 * An EMF-style switch over the {@link IElementType}s supported by the Sequence Diagram. It adds specific
 * handling for {@link #nullCase() null} inputs for safety and convenience.
 */
public abstract class SequenceTypeSwitch<V> {

	/**
	 * Initializes me.
	 */
	public SequenceTypeSwitch() {
		super();
	}

	/**
	 * Switch on the given {@code type}, calling the most specific appropriate case(s), to compute a result.
	 * 
	 * @param type
	 *            an element type on which to switch
	 * @return the result of the first applicable case, in reverse specificity order, to return a
	 *         non-{@code null} result
	 */
	public V doSwitch(Object type) {
		if (type == null) {
			return nullCase();
		}

		if (!(type instanceof IElementType)) {
			return defaultCase(type);
		}

		return doSwitch((IElementType)type);
	}

	protected V doSwitch(IElementType type) {
		V result = _null();

		if (result == null && ElementUtil.isTypeOf(type, UMLDIElementTypes.MESSAGE_ASYNCH_EDGE)) {
			result = caseAsyncMessage((IHintedType)type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLDIElementTypes.MESSAGE_SYNCH_EDGE)) {
			result = caseSyncMessage((IHintedType)type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLDIElementTypes.MESSAGE_REPLY_EDGE)) {
			result = caseReplyMessage((IHintedType)type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLDIElementTypes.MESSAGE_CREATE_EDGE)) {
			result = caseCreateMessage((IHintedType)type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLDIElementTypes.MESSAGE_DELETE_EDGE)) {
			result = caseDeleteMessage((IHintedType)type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLDIElementTypes.MESSAGE_FOUND_EDGE)) {
			result = caseFoundMessage((IHintedType)type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLDIElementTypes.MESSAGE_LOST_EDGE)) {
			result = caseLostMessage((IHintedType)type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLElementTypes.MESSAGE)) {
			result = caseMessage(type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLElementTypes.ACTION_EXECUTION_SPECIFICATION)) {
			result = caseActionExecutionSpecification(type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLElementTypes.BEHAVIOR_EXECUTION_SPECIFICATION)) {
			result = caseBehaviorExecutionSpecification(type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLElementTypes.EXECUTION_SPECIFICATION)) {
			result = caseExecutionSpecification(type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLElementTypes.LIFELINE)) {
			result = caseLifeline(type);
		}
		if (result == null && ElementUtil.isTypeOf(type, UMLElementTypes.INTERACTION)) {
			result = caseInteraction(type);
		}
		if (result == null) {
			result = defaultCase(type);
		}

		return result;
	}

	private static final <V> V _null() {
		return null;
	}

	//
	// Switch cases
	//

	/**
	 * Handle the async message type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseAsyncMessage(IHintedType type) {
		return null;
	}

	/**
	 * Handle the sync message type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseSyncMessage(IHintedType type) {
		return null;
	}

	/**
	 * Handle the reply message type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseReplyMessage(IHintedType type) {
		return null;
	}

	/**
	 * Handle the create message type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseCreateMessage(IHintedType type) {
		return null;
	}

	/**
	 * Handle the delete message type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseDeleteMessage(IHintedType type) {
		return null;
	}

	/**
	 * Handle the found message type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseFoundMessage(IHintedType type) {
		return null;
	}

	/**
	 * Handle the lost message type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseLostMessage(IHintedType type) {
		return null;
	}

	/**
	 * Handle a message type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseMessage(IElementType type) {
		return null;
	}

	/**
	 * Handle the action execution specification type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseActionExecutionSpecification(IElementType type) {
		return null;
	}

	/**
	 * Handle the behavior execution specification type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseBehaviorExecutionSpecification(IElementType type) {
		return null;
	}

	/**
	 * Handle an execution specification type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseExecutionSpecification(IElementType type) {
		return null;
	}

	/**
	 * Handle the lifeline type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseLifeline(IElementType type) {
		return null;
	}

	/**
	 * Handle the interaction type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseInteraction(IElementType type) {
		return null;
	}

	/**
	 * Handle a hinted type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseHintedType(IHintedType type) {
		return null;
	}

	/**
	 * Handle an element type.
	 * 
	 * @param type
	 *            the element type
	 */
	public V caseElementType(IElementType type) {
		return null;
	}

	/**
	 * Handle anything else.
	 * 
	 * @param object
	 *            the object
	 */
	public V defaultCase(Object object) {
		return null;
	}

	/**
	 * Handle a {@code null} input.
	 */
	public V nullCase() {
		return null;
	}
}
