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

import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.ifPresentElse;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.uml2.uml.Element;

/**
 * A framework for {@linkplain DependencyContext context} variables that record logical, semantic, and/or
 * visual containment changes pending in the model operation currently being computed.
 * 
 * @see DependencyContext
 */
abstract class PendingContainmentChangeData<P extends MElement<? extends Element>, C extends MElement<? extends Element>> {
	private final P parent;

	private final List<C> children = Lists.newArrayListWithExpectedSize(1);

	PendingContainmentChangeData(P parent, C child) {
		super();

		this.parent = parent;
		addChild(child);
	}

	private void addChild(C child) {
		children.add(child);
	}

	/**
	 * Queries the parent of the pending {@linkplain #getPendingChild() child element}.
	 * 
	 * @return the parent element
	 */
	public P getParent() {
		return parent;
	}

	/**
	 * Queries the element that is being assigned a {@linkplain #getParent() new parent}.
	 * 
	 * @return the child element
	 */
	public C getPendingChild() {
		return children.get(0);
	}

	/**
	 * Queries the elements that are being assigned a {@linkplain #getParent() new parent}.
	 * 
	 * @return the child elements
	 */
	public List<C> getPendingChildren() {
		return Collections.unmodifiableList(children);
	}

	/**
	 * Query the element that is in process of having the given element set as its new {@code parent}.
	 * 
	 * @param parent
	 *            an element that may or may not be getting a new child
	 * @return the pending child element
	 */
	static <P extends MElement<? extends Element>, C extends MElement<? extends Element>> Optional<C> getPendingChild(
			Class<? extends PendingContainmentChangeData<P, C>> relationshipType, P parent) {

		return DependencyContext.get().get(parent, relationshipType)
				.map(PendingContainmentChangeData::getPendingChild);
	}

	/**
	 * Query the elements that are in process of having the given element set as their new {@code parent}.
	 * 
	 * @param parent
	 *            an element that may or may not be getting a new child
	 * @return the pending child elements
	 */
	static <P extends MElement<? extends Element>, C extends MElement<? extends Element>> List<C> getPendingChildren(
			Class<? extends PendingContainmentChangeData<P, C>> relationshipType, P parent) {
		return DependencyContext.get().get(parent, relationshipType)
				.map(PendingContainmentChangeData::getPendingChildren).orElse(Collections.emptyList());
	}

	/**
	 * Query the element that is in process of being made the parent of a pending {@code child}.
	 * 
	 * @param child
	 *            an element that may or may not be getting a new parent
	 * @return the pending parent element
	 */
	static <P extends MElement<? extends Element>, C extends MElement<? extends Element>> Optional<P> getPendingParent(
			Class<? extends PendingContainmentChangeData<P, C>> relationshipType, C child) {
		return getPendingParentData(DependencyContext.get(), relationshipType, child)
				.map(PendingParentData::getPendingParent);
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	private static <P extends MElement<? extends Element>, C extends MElement<? extends Element>> Optional<PendingContainmentChangeData.PendingParentData<P, C>> getPendingParentData(
			DependencyContext ctx, Class<? extends PendingContainmentChangeData<P, C>> relationshipType,
			C child) {

		return (Optional)ctx.get(child, PendingParentData.class,
				data -> data.getRelationshipType() == relationshipType);
	}

	/**
	 * Record an element that is in process of having the given element set as its new {@code owner}.
	 * 
	 * @param owner
	 *            an element that is getting a new owned child
	 * @param pendingChild
	 *            an element that is getting the new {@code owner}
	 */
	static <P extends MElement<? extends Element>, C extends MElement<? extends Element>> void setPendingChild(
			Class<? extends PendingContainmentChangeData<P, C>> relationshipType, P parent, C pendingChild,
			BiFunction<P, C, PendingContainmentChangeData<P, C>> factory) {

		DependencyContext ctx = DependencyContext.get();

		@SuppressWarnings({"unchecked", "rawtypes" })
		Optional<PendingContainmentChangeData<P, C>> childData = (Optional)ctx.get(parent, relationshipType);

		ifPresentElse(childData, cd -> {
			cd.addChild(pendingChild);
			ctx.put(pendingChild, new PendingParentData<>(relationshipType, cd));
		}, () -> {
			PendingContainmentChangeData<P, C> cd = factory.apply(parent, pendingChild);
			ctx.put(parent, cd);
			ctx.put(pendingChild, new PendingParentData<>(relationshipType, cd));
		});
	}

	//
	// Nested types
	//

	private static final class PendingParentData<P extends MElement<? extends Element>, C extends MElement<? extends Element>> {
		private final Class<? extends PendingContainmentChangeData<P, C>> relationshipType;

		private final PendingContainmentChangeData<P, C> childData;

		PendingParentData(Class<? extends PendingContainmentChangeData<P, C>> relationshipType,
				PendingContainmentChangeData<P, C> childData) {
			super();

			this.relationshipType = relationshipType;
			this.childData = childData;
		}

		Class<? extends PendingContainmentChangeData<P, C>> getRelationshipType() {
			return relationshipType;
		}

		P getPendingParent() {
			return childData.getParent();
		}
	}

}
