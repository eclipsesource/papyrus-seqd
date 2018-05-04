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

package org.eclipse.papyrus.uml.interaction.graph;

import java.util.function.Predicate;

import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.InteractionOperand;

/**
 * An enumeration of kinds of semantic {@linkplain Group grouping}.
 *
 * @author Christian W. Damus
 */
public enum GroupKind {
	/** The not-a-group kind. */
	NONE,
	/**
	 * A group of vertices representing the interaction fragments covered by a particular lifeline. If the
	 * group is a {@link Vertex}, then its {@linkplain Vertex#getInteractionElement() interaction element} is
	 * that lifeline.
	 */
	LIFELINE,
	/**
	 * A group of vertices representing the message ends sent and received during an
	 * {@link ExecutionSpecification} and also nested executions. If the group is a {@link Vertex}, then its
	 * {@linkplain Vertex#getInteractionElement() interaction element} is that execution specification.
	 */
	EXECUTION,
	/**
	 * A group of vertices representing the interaction operands of a {@link CombinedFragment}. If the group
	 * is a {@link Vertex}, then its {@linkplain Vertex#getInteractionElement() interaction element} is that
	 * combined fragment.
	 */
	COMBINED_FRAGMENT,
	/**
	 * A group of vertices representing the interaction fragments that occur within the context of an
	 * {@link InteractionOperand}, including nested combined fragments. If the group is a {@link Vertex}, then
	 * its {@linkplain Vertex#getInteractionElement() interaction element} is that interaction operand.
	 */
	OPERAND;

	/**
	 * Obtains a predicate to match groups of my {@link Group#kind() kind}.
	 * 
	 * @return the group kind predicate
	 */
	public Predicate<? super Group<?>> predicate() {
		return group -> group.kind() == this;
	}

}
