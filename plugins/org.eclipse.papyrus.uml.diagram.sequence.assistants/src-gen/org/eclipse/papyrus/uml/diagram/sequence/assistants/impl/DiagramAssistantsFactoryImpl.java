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
package org.eclipse.papyrus.uml.diagram.sequence.assistants.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.uml.diagram.sequence.assistants.*;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class DiagramAssistantsFactoryImpl extends EFactoryImpl implements DiagramAssistantsFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static DiagramAssistantsFactory init() {
		try {
			DiagramAssistantsFactory theDiagramAssistantsFactory = (DiagramAssistantsFactory)EPackage.Registry.INSTANCE
					.getEFactory(DiagramAssistantsPackage.eNS_URI);
			if (theDiagramAssistantsFactory != null) {
				return theDiagramAssistantsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DiagramAssistantsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramAssistantsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DiagramAssistantsPackage.SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER:
				return createSequenceDiagramAssistantProvider();
			default:
				throw new IllegalArgumentException(
						"The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SequenceDiagramAssistantProvider createSequenceDiagramAssistantProvider() {
		SequenceDiagramAssistantProviderImpl sequenceDiagramAssistantProvider = new SequenceDiagramAssistantProviderImpl();
		return sequenceDiagramAssistantProvider;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramAssistantsPackage getDiagramAssistantsPackage() {
		return (DiagramAssistantsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DiagramAssistantsPackage getPackage() {
		return DiagramAssistantsPackage.eINSTANCE;
	}

} // DiagramAssistantsFactoryImpl
