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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;

import org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsPackage;
import org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider;

/**
 * <!-- begin-user-doc --> An adapter that propagates notifications for derived unions. <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsPackage
 * @generated
 */
public class DiagramAssistantsDerivedUnionAdapter extends AdapterImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static DiagramAssistantsPackage modelPackage;

	/**
	 * Creates an instance of the adapter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramAssistantsDerivedUnionAdapter() {
		if (modelPackage == null) {
			modelPackage = DiagramAssistantsPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> with the appropriate model class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param notification
	 *            a description of the change.
	 * @generated
	 */
	public void notifyChanged(Notification notification) {
		Object notifier = notification.getNotifier();
		if (notifier instanceof EObject) {
			EClass eClass = ((EObject)notifier).eClass();
			if (eClass.eContainer() == modelPackage) {
				notifyChanged(notification, eClass);
			}
		}
	}

	/**
	 * Calls <code>notifyXXXChanged</code> for the corresponding class of the model. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyChanged(Notification notification, EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DiagramAssistantsPackage.SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER:
				notifySequenceDiagramAssistantProviderChanged(notification, eClass);
				break;
		}
	}

	/**
	 * Does nothing; clients may override so that it does something. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @param derivedUnion
	 *            the derived union affected by the change.
	 * @generated
	 */
	public void notifyChanged(Notification notification, EClass eClass, EStructuralFeature derivedUnion) {
		// Do nothing.
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifySequenceDiagramAssistantProviderChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(SequenceDiagramAssistantProvider.class)) {
			case DiagramAssistantsPackage.SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
				notifyChanged(notification, eClass,
						AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ASSISTANT);
				break;
			case DiagramAssistantsPackage.SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
				notifyChanged(notification, eClass,
						AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ASSISTANT);
				break;
		}
	}

} // DiagramAssistantsDerivedUnionAdapter
