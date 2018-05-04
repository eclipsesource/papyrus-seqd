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
package org.eclipse.papyrus.uml.interaction.internal.model.impl;

import static java.util.Collections.singleton;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.AddLifelineCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MInteraction</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl#getLifelines
 * <em>Lifelines</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl#getMessages
 * <em>Messages</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MInteractionImpl extends MElementImpl<Interaction> implements MInteraction {

	/**
	 * The cached value of the '{@link #getLifelines() <em>Lifelines</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLifelines()
	 * @generated
	 * @ordered
	 */
	protected EList<MLifeline> lifelines;

	/**
	 * The cached value of the '{@link #getMessages() <em>Messages</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMessages()
	 * @generated
	 * @ordered
	 */
	protected EList<MMessage> messages;

	private final AdapterFactory adapterFactory = new UMLItemProviderAdapterFactory();

	private Graph graph;

	private EditingDomain editingDomain;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MInteractionImpl() {
		super();
	}

	protected MInteractionImpl(Interaction interaction) {
		super(null, interaction);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MINTERACTION;
	}

	@Override
	protected void dispose() {
		// Detach all logical model elements from the underlying UML
		for (Iterator<EObject> iter = EcoreUtil.getAllContents(singleton(getElement())); iter.hasNext();) {
			iter.next().eAdapters().removeIf(a -> a.isAdapterForType(MObject.class));
		}

		// Remove all item providers that we installed for toString()
		((IDisposable)adapterFactory).dispose();
	}

	@Override
	protected final AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}

	@Override
	public Graph getGraph() {
		return graph;
	}

	@Override
	public EditingDomain getEditingDomain() {
		if (editingDomain == null) {
			editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(getElement());
		}

		return editingDomain;
	}

	@Override
	public MElement<?> getOwner() {
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public List<MLifeline> getLifelines() {
		if (lifelines == null) {
			lifelines = new MContainmentList<>(MLifeline.class, this,
					SequenceDiagramPackage.MINTERACTION__LIFELINES);
		}
		return lifelines;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public List<MMessage> getMessages() {
		if (messages == null) {
			messages = new MContainmentList<>(MMessage.class, this,
					SequenceDiagramPackage.MINTERACTION__MESSAGES);
		}
		return messages;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<Diagram> getDiagramView() {
		return super.getDiagramView().map(Diagram.class::cast);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public <E extends Element> Optional<MElement<? extends E>> getElement(E element) {
		@SuppressWarnings("unchecked")
		MElement<? extends E> result = (MElement<? extends E>)EcoreUtil.getExistingAdapter(element,
				MObject.class);
		return Optional.ofNullable(result);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SequenceDiagramPackage.MINTERACTION__LIFELINES:
				return ((InternalEList<?>)getLifelines()).basicRemove(otherEnd, msgs);
			case SequenceDiagramPackage.MINTERACTION__MESSAGES:
				return ((InternalEList<?>)getMessages()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SequenceDiagramPackage.MINTERACTION__LIFELINES:
				return getLifelines();
			case SequenceDiagramPackage.MINTERACTION__MESSAGES:
				return getMessages();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SequenceDiagramPackage.MINTERACTION__LIFELINES:
				return (lifelines != null) && !lifelines.isEmpty();
			case SequenceDiagramPackage.MINTERACTION__MESSAGES:
				return (messages != null) && !messages.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SequenceDiagramPackage.MINTERACTION___GET_DIAGRAM_VIEW:
				return getDiagramView();
			case SequenceDiagramPackage.MINTERACTION___GET_ELEMENT__ELEMENT:
				return getElement((Element)arguments.get(0));
			case SequenceDiagramPackage.MINTERACTION___GET_LIFELINE__LIFELINE:
				return getLifeline((Lifeline)arguments.get(0));
			case SequenceDiagramPackage.MINTERACTION___GET_MESSAGE__MESSAGE:
				return getMessage((Message)arguments.get(0));
			case SequenceDiagramPackage.MINTERACTION___ADD_LIFELINE__INT_INT:
				return addLifeline((Integer)arguments.get(0), (Integer)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

	MInteractionImpl withGraph(Graph newGraph) {
		this.graph = newGraph;
		return this;
	}

	MLifelineImpl addLifeline(Lifeline lifeline) {
		return getLifeline(lifeline).map(MLifelineImpl.class::cast)
				.orElseGet(() -> addLifeline(new MLifelineImpl(this, lifeline)));
	}

	MLifelineImpl addLifeline(MLifelineImpl lifeline) {
		((InternalEList<MLifeline>)getLifelines()).addUnique(lifeline);
		return lifeline;
	}

	@Override
	public Optional<MLifeline> getLifeline(Lifeline lifeline) {
		return getElement(lifeline, getLifelines());
	}

	MMessageImpl addMessage(Message message) {
		return getMessage(message).map(MMessageImpl.class::cast)
				.orElseGet(() -> addMessage(new MMessageImpl(this, message)));
	}

	MMessageImpl addMessage(MMessageImpl message) {
		((InternalEList<MMessage>)getMessages()).addUnique(message);
		return message;
	}

	@Override
	public Optional<MMessage> getMessage(Message message) {
		return getElement(message, getMessages());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public CreationCommand<Lifeline> addLifeline(int xPosition, int height) {
		return new AddLifelineCommand(this, xPosition, height);
	}

} // MInteractionImpl
