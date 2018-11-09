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

import java.util.Optional;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.uml2.uml.Element;

/**
 * A {@linkplain DependencyContext context} variable that records ownership change in progress.
 * 
 * @see DependencyContext
 */
public final class PendingChildData {
	private final MElement<? extends Element> owner;

	private final MElement<? extends Element> child;

	private PendingChildData(MElement<? extends Element> owner, MElement<? extends Element> child) {
		super();

		this.owner = owner;
		this.child = child;
	}

	/**
	 * Queries the owner of the pending {@linkplain #getPendingChild() owned element}.
	 * 
	 * @return the owner element
	 */
	public MElement<? extends Element> getOwner() {
		return owner;
	}

	/**
	 * Queries the element that is being assigned a {@linkplain #getOwner() new owner}.
	 * 
	 * @return the owned element
	 */
	public MElement<? extends Element> getPendingChild() {
		return child;
	}

	/**
	 * Query the element that is in process of having the given element set as its new {@code owner}.
	 * 
	 * @param owner
	 *            an element that may or may not be getting a new owned child
	 * @return the pending child element
	 */
	public static Optional<MElement<? extends Element>> getPendingChild(MElement<? extends Element> owner) {
		return DependencyContext.get().get(owner, PendingChildData.class)
				.map(PendingChildData::getPendingChild);
	}

	/**
	 * Query the element that is in process of being made the owner of a pending {@code child}.
	 * 
	 * @param child
	 *            an element that may or may not be getting a new owner
	 * @return the pending owner element
	 */
	public static Optional<MElement<? extends Element>> getPendingOwner(MElement<? extends Element> child) {
		return DependencyContext.get().get(child, PendingOwnerData.class)
				.map(PendingOwnerData::getPendingOwner);
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
			DependencyContext ctx = DependencyContext.get();
			PendingChildData childData = new PendingChildData(owner, pendingChild);
			ctx.put(owner, childData);
			ctx.put(pendingChild, new PendingOwnerData(childData));
		}
	}

	//
	// Nested types
	//

	private static final class PendingOwnerData {
		private final PendingChildData childData;

		PendingOwnerData(PendingChildData childData) {
			super();

			this.childData = childData;
		}

		MElement<? extends Element> getPendingOwner() {
			return childData.getOwner();
		}
	}

}
