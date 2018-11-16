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

package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.List;
import java.util.Optional;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.uml2.uml.Element;

/**
 * A {@linkplain DependencyContext context} variable that records ownership change in progress.
 * 
 * @see DependencyContext
 */
public final class PendingChildData extends PendingContainmentChangeData<MElement<? extends Element>, MElement<? extends Element>> {

	private PendingChildData(MElement<? extends Element> owner, MElement<? extends Element> child) {
		super(owner, child);
	}

	/**
	 * Query the element that is in process of having the given element set as its new {@code owner}.
	 * 
	 * @param owner
	 *            an element that may or may not be getting a new owned child
	 * @return the pending child element
	 */
	public static Optional<MElement<? extends Element>> getPendingChild(MElement<? extends Element> owner) {
		return PendingContainmentChangeData.getPendingChild(PendingChildData.class, owner);
	}

	/**
	 * Query the elements that are in process of having the given element set as their new {@code owner}.
	 * 
	 * @param owner
	 *            an element that may or may not be getting a new owned child
	 * @return the owned elements
	 */
	public static List<MElement<? extends Element>> getPendingChildren(MElement<? extends Element> owner) {
		return PendingContainmentChangeData.getPendingChildren(PendingChildData.class, owner);
	}

	/**
	 * Query the element that is in process of being made the owner of a pending {@code child}.
	 * 
	 * @param child
	 *            an element that may or may not be getting a new owner
	 * @return the pending owner element
	 */
	public static Optional<MElement<? extends Element>> getPendingOwner(MElement<? extends Element> child) {
		return PendingContainmentChangeData.getPendingParent(PendingChildData.class, child);
	}

	/**
	 * Record an element that is in process of having the given element set as its new {@code owner}.
	 * 
	 * @param owner
	 *            an element that is getting a new owned child
	 * @param pendingChild
	 *            an element that is getting the new {@code owner}
	 */
	static void setPendingChild(MElement<? extends Element> owner, MElement<? extends Element> pendingChild) {
		if (owner.getElement() != pendingChild.getOwner().getElement()) {
			PendingContainmentChangeData.setPendingChild(PendingChildData.class, owner, pendingChild,
					PendingChildData::new);

			if (pendingChild instanceof MExecution) {
				// Its nested executions also get the new owner lifeline
				((MExecution)pendingChild).getNestedExecutions()
						.forEach(nested -> setPendingChild(owner, nested));
			}
		}
	}

}
