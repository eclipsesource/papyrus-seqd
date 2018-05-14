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
 */
package org.eclipse.papyrus.uml.diagram.sequence.assistants.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.runtime.common.core.service.IProvider;

import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;

import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;

import org.eclipse.papyrus.uml.diagram.sequence.assistants.*;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsPackage
 * @generated
 */
public class DiagramAssistantsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static DiagramAssistantsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramAssistantsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DiagramAssistantsPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model. <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected DiagramAssistantsSwitch<Adapter> modelSwitch = new DiagramAssistantsSwitch<Adapter>() {
		@Override
		public Adapter caseSequenceDiagramAssistantProvider(SequenceDiagramAssistantProvider object) {
			return createSequenceDiagramAssistantProviderAdapter();
		}

		@Override
		public Adapter caseIProvider(IProvider object) {
			return createIProviderAdapter();
		}

		@Override
		public Adapter caseIModelingAssistantProvider(IModelingAssistantProvider object) {
			return createIModelingAssistantProviderAdapter();
		}

		@Override
		public Adapter caseModelingAssistantProvider(ModelingAssistantProvider object) {
			return createModelingAssistantProviderAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider
	 * <em>Sequence Diagram Assistant Provider</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will
	 * catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider
	 * @generated
	 */
	public Adapter createSequenceDiagramAssistantProviderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.gmf.runtime.common.core.service.IProvider <em>IProvider</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider
	 * @generated
	 */
	public Adapter createIProviderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider
	 * <em>IModeling Assistant Provider</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider
	 * @generated
	 */
	public Adapter createIModelingAssistantProviderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider <em>Modeling Assistant
	 * Provider</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider
	 * @generated
	 */
	public Adapter createModelingAssistantProviderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns
	 * null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // DiagramAssistantsAdapterFactory
