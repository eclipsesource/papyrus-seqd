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

package org.eclipse.papyrus.uml.interaction.internal.model.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MObject;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;

/**
 * Abstract implementation of a logical model element.
 *
 * @author Christian W. Damus
 */
public abstract class MObjectImpl<T extends Element> extends MinimalEObjectImpl.Container implements MObject, Adapter {

	private MElement<?> owner;

	private T element;

	/**
	 * Initializes me.
	 */
	protected MObjectImpl() {
		super();
	}

	/**
	 * Initializes me.
	 */
	protected MObjectImpl(MElement<?> owner, T element) {
		this();

		setOwner(owner);
		setElement(element);
	}

	protected void dispose() {
		getInteractionImpl().dispose();
	}

	final void setOwner(MElement<?> owner) {
		assert this.owner == null : "attempt to overwrite write-once property"; //$NON-NLS-1$
		assert this instanceof MInteraction || owner != null : "owner is required"; //$NON-NLS-1$
		this.owner = owner;
	}

	public MElement<?> getOwner() {
		assert this.owner != null : "no owner"; //$NON-NLS-1$
		return owner;
	}

	final void setElement(T element) {
		assert this.element == null : "attempt to overwrite write-once property"; //$NON-NLS-1$
		assert element != null : "element is required"; //$NON-NLS-1$
		this.element = element;

		element.eAdapters().add(this);
	}

	public T getElement() {
		assert this.element != null : "no element"; //$NON-NLS-1$
		return element;
	}

	Optional<InteractionFragment> getFragment() {
		return Optional.ofNullable(element).filter(InteractionFragment.class::isInstance)
				.map(InteractionFragment.class::cast);
	}

	public Optional<? extends EObject> getDiagramView() {
		return getVertex().map(Vertex::getDiagramView);
	}

	public MInteraction getInteraction() {
		return getInteractionImpl();
	}

	public final MInteractionImpl getInteractionImpl() {
		if (this instanceof MInteractionImpl) {
			return (MInteractionImpl)this;
		}

		MInteraction result = getOwner().getInteraction();
		if (!(result instanceof MInteractionImpl)) {
			throw new IllegalStateException("no interaction"); //$NON-NLS-1$
		}

		return (MInteractionImpl)result;
	}

	Graph getGraph() {
		return getInteractionImpl().getGraph();
	}

	AdapterFactory getAdapterFactory() {
		return getInteractionImpl().getAdapterFactory();
	}

	Optional<Vertex> getVertex() {
		return Optional.ofNullable(getGraph().vertex(getElement()));
	}

	final <E extends Element, M extends MElement<E>> Optional<M> getElement(E uml, Collection<M> elements) {
		return elements.stream().filter(m -> m.getElement() == uml).findAny();
	}

	@SafeVarargs
	final <E extends Element, M extends MElement<E>> Optional<M> getElement(E uml, Optional<M>... elements) {
		return Stream.of(elements).filter(Optional::isPresent).map(Optional::get)
				.filter(m -> m.getElement() == uml).findAny();
	}

	protected final Shape getShape(Element interactionElement) {
		return getView(interactionElement, Shape.class).orElse(null);
	}

	protected final Connector getConnector(Element interactionElement) {
		return getView(interactionElement, Connector.class).orElse(null);
	}

	protected final <V extends View> Optional<V> getView(Element interactionElement, Class<V> type) {
		return Optional.ofNullable(getGraph().vertex(interactionElement)).map(Vertex::getDiagramView)
				.filter(type::isInstance).map(type::cast);
	}

	protected final IdentityAnchor getSourceAnchor(Element interactionElement) {
		return getAnchor(interactionElement, true, IdentityAnchor.class).orElse(null);
	}

	protected final IdentityAnchor getTargetAnchor(Element interactionElement) {
		return getAnchor(interactionElement, false, IdentityAnchor.class).orElse(null);
	}

	protected final <A extends Anchor> Optional<A> getAnchor(Element interactionElement, boolean source,
			Class<A> type) {
		return getView(interactionElement, Connector.class)
				.map(c -> source ? c.getSourceAnchor() : c.getTargetAnchor()).filter(type::isInstance)
				.map(type::cast);
	}

	protected final LayoutHelper layoutHelper() {
		return LogicalModelPlugin.getInstance().getLayoutHelper(getEditingDomain());
	}

	protected final DiagramHelper diagramHelper() {
		return LogicalModelPlugin.getInstance().getDiagramHelper(getEditingDomain());
	}

	public EditingDomain getEditingDomain() {
		return getInteractionImpl().getEditingDomain();
	}

	//
	// Adapter prototocol
	//

	@Override
	public boolean isAdapterForType(Object type) {
		return type == MObject.class || type == MObjectImpl.class;
	}

	@Override
	public void notifyChanged(Notification msg) {
		if (!msg.isTouch()) {
			dispose();
		}
	}

	@Override
	public Notifier getTarget() {
		return getElement();
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// Don't need to track this separately
	}

	//
	// Object protocol
	//

	@Override
	public String toString() {
		IItemLabelProvider labels = (IItemLabelProvider)getAdapterFactory().adapt(element,
				IItemLabelProvider.class);
		return (labels == null) ? super.toString() : labels.getText(element);
	}
}
