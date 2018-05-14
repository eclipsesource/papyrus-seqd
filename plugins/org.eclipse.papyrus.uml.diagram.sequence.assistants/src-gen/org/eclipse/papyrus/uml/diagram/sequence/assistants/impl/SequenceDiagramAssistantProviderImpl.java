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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl;

import org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsPackage;
import org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider;

import org.eclipse.papyrus.uml.diagram.sequence.assistants.internal.operations.SequenceDiagramAssistantProviderOperations;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sequence Diagram Assistant
 * Provider</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class SequenceDiagramAssistantProviderImpl extends ModelingAssistantProviderImpl implements SequenceDiagramAssistantProvider {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SequenceDiagramAssistantProviderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagramAssistantsPackage.Literals.SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelTypesOnSource(IAdaptable source) {
		return SequenceDiagramAssistantProviderOperations.getRelTypesOnSource(this, source);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelTypesOnTarget(IAdaptable target) {
		return SequenceDiagramAssistantProviderOperations.getRelTypesOnTarget(this, target);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		return SequenceDiagramAssistantProviderOperations.getRelTypesOnSourceAndTarget(this, source, target);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case DiagramAssistantsPackage.SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE:
				return getRelTypesOnSource((IAdaptable)arguments.get(0));
			case DiagramAssistantsPackage.SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE:
				return getRelTypesOnTarget((IAdaptable)arguments.get(0));
			case DiagramAssistantsPackage.SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE:
				return getRelTypesOnSourceAndTarget((IAdaptable)arguments.get(0),
						(IAdaptable)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} // SequenceDiagramAssistantProviderImpl
