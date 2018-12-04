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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramFactory;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MDestruction;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.NudgeKind;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class SequenceDiagramFactoryImpl extends EFactoryImpl implements SequenceDiagramFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static SequenceDiagramFactory init() {
		try {
			SequenceDiagramFactory theSequenceDiagramFactory = (SequenceDiagramFactory)EPackage.Registry.INSTANCE
					.getEFactory(SequenceDiagramPackage.eNS_URI);
			if (theSequenceDiagramFactory != null) {
				return theSequenceDiagramFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SequenceDiagramFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SequenceDiagramFactoryImpl() {
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
			case SequenceDiagramPackage.MINTERACTION:
				return (EObject)createMInteraction();
			case SequenceDiagramPackage.MLIFELINE:
				return (EObject)createMLifeline();
			case SequenceDiagramPackage.MEXECUTION:
				return (EObject)createMExecution();
			case SequenceDiagramPackage.MEXECUTION_OCCURRENCE:
				return (EObject)createMExecutionOccurrence();
			case SequenceDiagramPackage.MMESSAGE_END:
				return (EObject)createMMessageEnd();
			case SequenceDiagramPackage.MMESSAGE:
				return (EObject)createMMessage();
			case SequenceDiagramPackage.MDESTRUCTION:
				return (EObject)createMDestruction();
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
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SequenceDiagramPackage.NUDGE_KIND:
				return createNudgeKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException(
						"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SequenceDiagramPackage.NUDGE_KIND:
				return convertNudgeKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException(
						"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MInteraction createMInteraction() {
		MInteractionImpl mInteraction = new MInteractionImpl();
		return mInteraction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MLifeline createMLifeline() {
		MLifelineImpl mLifeline = new MLifelineImpl();
		return mLifeline;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MExecution createMExecution() {
		MExecutionImpl mExecution = new MExecutionImpl();
		return mExecution;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MExecutionOccurrence createMExecutionOccurrence() {
		MExecutionOccurrenceImpl mExecutionOccurrence = new MExecutionOccurrenceImpl();
		return mExecutionOccurrence;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MMessageEnd createMMessageEnd() {
		MMessageEndImpl mMessageEnd = new MMessageEndImpl();
		return mMessageEnd;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MMessage createMMessage() {
		MMessageImpl mMessage = new MMessageImpl();
		return mMessage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MDestruction createMDestruction() {
		MDestructionImpl mDestruction = new MDestructionImpl();
		return mDestruction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NudgeKind createNudgeKindFromString(EDataType eDataType, String initialValue) {
		NudgeKind result = NudgeKind.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + initialValue //$NON-NLS-1$
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertNudgeKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SequenceDiagramPackage getSequenceDiagramPackage() {
		return (SequenceDiagramPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SequenceDiagramPackage getPackage() {
		return SequenceDiagramPackage.eINSTANCE;
	}

} // SequenceDiagramFactoryImpl
