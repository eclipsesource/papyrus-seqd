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

import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramFactory;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.RemovalCommand;
import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class SequenceDiagramPackageImpl extends EPackageImpl implements SequenceDiagramPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mInteractionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mLifelineEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mExecutionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mOccurrenceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mExecutionOccurrenceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mMessageEndEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mMessageEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType optionalEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType optionalIntEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType commandEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType creationCommandEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType eObjectEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType removalCommandEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SequenceDiagramPackageImpl() {
		super(eNS_URI, SequenceDiagramFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends.
	 * <p>
	 * This method is used to initialize {@link SequenceDiagramPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the
	 * package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SequenceDiagramPackage init() {
		if (isInited) {
			return (SequenceDiagramPackage)EPackage.Registry.INSTANCE
					.getEPackage(SequenceDiagramPackage.eNS_URI);
		}

		// Obtain or create and register package
		SequenceDiagramPackageImpl theSequenceDiagramPackage = (SequenceDiagramPackageImpl)(EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof SequenceDiagramPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new SequenceDiagramPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		UMLPackage.eINSTANCE.eClass();
		NotationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSequenceDiagramPackage.createPackageContents();

		// Initialize created meta-data
		theSequenceDiagramPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSequenceDiagramPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SequenceDiagramPackage.eNS_URI, theSequenceDiagramPackage);
		return theSequenceDiagramPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMElement() {
		return mElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMElement_Interaction() {
		return (EReference)mElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMElement_Element() {
		return (EReference)mElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMElement_Top() {
		return (EAttribute)mElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMElement_Bottom() {
		return (EAttribute)mElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMElement_Name() {
		return (EAttribute)mElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMElement__GetOwner() {
		return mElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMElement__GetDiagramView() {
		return mElementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMElement__VerticalDistance__MElement() {
		return mElementEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMElement__Following() {
		return mElementEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMElement__Nudge__int() {
		return mElementEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMElement__Remove() {
		return mElementEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMInteraction() {
		return mInteractionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMInteraction_Lifelines() {
		return (EReference)mInteractionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMInteraction_Messages() {
		return (EReference)mInteractionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMInteraction__GetDiagramView() {
		return mInteractionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMInteraction__GetElement__Element() {
		return mInteractionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMInteraction__GetLifeline__Lifeline() {
		return mInteractionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMInteraction__GetMessage__Message() {
		return mInteractionEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMInteraction__AddLifeline__int_int() {
		return mInteractionEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMInteraction__GetLifelineAt__int() {
		return mInteractionEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMLifeline() {
		return mLifelineEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMLifeline_ExecutionOccurrences() {
		return (EReference)mLifelineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMLifeline_Executions() {
		return (EReference)mLifelineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMLifeline_Left() {
		return (EAttribute)mLifelineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMLifeline_Right() {
		return (EAttribute)mLifelineEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__GetOwner() {
		return mLifelineEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__GetDiagramView() {
		return mLifelineEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__Following__MElement() {
		return mLifelineEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__GetExecutionOccurrence__ExecutionOccurrenceSpecification() {
		return mLifelineEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__GetExecution__ExecutionSpecification() {
		return mLifelineEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__InsertExecutionAfter__MElement_int_int_Element() {
		return mLifelineEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__InsertExecutionAfter__MElement_int_int_EClass() {
		return mLifelineEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__InsertMessageAfter__MElement_int_MLifeline_MessageSort_NamedElement() {
		return mLifelineEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__ElementAt__int() {
		return mLifelineEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMLifeline__NudgeHorizontally__int() {
		return mLifelineEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMExecution() {
		return mExecutionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMExecution_Start() {
		return (EAttribute)mExecutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMExecution_Finish() {
		return (EAttribute)mExecutionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMExecution__GetOwner() {
		return mExecutionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMExecution__GetDiagramView() {
		return mExecutionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMOccurrence() {
		return mOccurrenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMOccurrence_Covered() {
		return (EAttribute)mOccurrenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMOccurrence_StartedExecution() {
		return (EAttribute)mOccurrenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMOccurrence_FinishedExecution() {
		return (EAttribute)mOccurrenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMExecutionOccurrence() {
		return mExecutionOccurrenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMExecutionOccurrence__GetOwner() {
		return mExecutionOccurrenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMExecutionOccurrence__GetDiagramView() {
		return mExecutionOccurrenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMMessageEnd() {
		return mMessageEndEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMMessageEnd_Send() {
		return (EAttribute)mMessageEndEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMMessageEnd_Receive() {
		return (EAttribute)mMessageEndEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMMessageEnd_OtherEnd() {
		return (EAttribute)mMessageEndEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMMessageEnd__GetOwner() {
		return mMessageEndEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMMessageEnd__GetDiagramView() {
		return mMessageEndEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMMessage() {
		return mMessageEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMMessage_SendEnd() {
		return (EReference)mMessageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMMessage_ReceiveEnd() {
		return (EReference)mMessageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMMessage_Send() {
		return (EAttribute)mMessageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMMessage_Receive() {
		return (EAttribute)mMessageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMMessage_Sender() {
		return (EAttribute)mMessageEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMMessage_Receiver() {
		return (EAttribute)mMessageEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMMessage__GetOwner() {
		return mMessageEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMMessage__GetDiagramView() {
		return mMessageEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EOperation getMMessage__GetEnd__MessageEnd() {
		return mMessageEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getOptional() {
		return optionalEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getOptionalInt() {
		return optionalIntEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getCommand() {
		return commandEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getCreationCommand() {
		return creationCommandEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getEObject() {
		return eObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getRemovalCommand() {
		return removalCommandEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SequenceDiagramFactory getSequenceDiagramFactory() {
		return (SequenceDiagramFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		mElementEClass = createEClass(MELEMENT);
		createEReference(mElementEClass, MELEMENT__INTERACTION);
		createEReference(mElementEClass, MELEMENT__ELEMENT);
		createEAttribute(mElementEClass, MELEMENT__TOP);
		createEAttribute(mElementEClass, MELEMENT__BOTTOM);
		createEAttribute(mElementEClass, MELEMENT__NAME);
		createEOperation(mElementEClass, MELEMENT___GET_OWNER);
		createEOperation(mElementEClass, MELEMENT___GET_DIAGRAM_VIEW);
		createEOperation(mElementEClass, MELEMENT___VERTICAL_DISTANCE__MELEMENT);
		createEOperation(mElementEClass, MELEMENT___FOLLOWING);
		createEOperation(mElementEClass, MELEMENT___NUDGE__INT);
		createEOperation(mElementEClass, MELEMENT___REMOVE);

		mInteractionEClass = createEClass(MINTERACTION);
		createEReference(mInteractionEClass, MINTERACTION__LIFELINES);
		createEReference(mInteractionEClass, MINTERACTION__MESSAGES);
		createEOperation(mInteractionEClass, MINTERACTION___GET_DIAGRAM_VIEW);
		createEOperation(mInteractionEClass, MINTERACTION___GET_ELEMENT__ELEMENT);
		createEOperation(mInteractionEClass, MINTERACTION___GET_LIFELINE__LIFELINE);
		createEOperation(mInteractionEClass, MINTERACTION___GET_MESSAGE__MESSAGE);
		createEOperation(mInteractionEClass, MINTERACTION___ADD_LIFELINE__INT_INT);
		createEOperation(mInteractionEClass, MINTERACTION___GET_LIFELINE_AT__INT);

		mLifelineEClass = createEClass(MLIFELINE);
		createEReference(mLifelineEClass, MLIFELINE__EXECUTION_OCCURRENCES);
		createEReference(mLifelineEClass, MLIFELINE__EXECUTIONS);
		createEAttribute(mLifelineEClass, MLIFELINE__LEFT);
		createEAttribute(mLifelineEClass, MLIFELINE__RIGHT);
		createEOperation(mLifelineEClass, MLIFELINE___GET_OWNER);
		createEOperation(mLifelineEClass, MLIFELINE___GET_DIAGRAM_VIEW);
		createEOperation(mLifelineEClass, MLIFELINE___FOLLOWING__MELEMENT);
		createEOperation(mLifelineEClass,
				MLIFELINE___GET_EXECUTION_OCCURRENCE__EXECUTIONOCCURRENCESPECIFICATION);
		createEOperation(mLifelineEClass, MLIFELINE___GET_EXECUTION__EXECUTIONSPECIFICATION);
		createEOperation(mLifelineEClass, MLIFELINE___INSERT_EXECUTION_AFTER__MELEMENT_INT_INT_ELEMENT);
		createEOperation(mLifelineEClass, MLIFELINE___INSERT_EXECUTION_AFTER__MELEMENT_INT_INT_ECLASS);
		createEOperation(mLifelineEClass,
				MLIFELINE___INSERT_MESSAGE_AFTER__MELEMENT_INT_MLIFELINE_MESSAGESORT_NAMEDELEMENT);
		createEOperation(mLifelineEClass, MLIFELINE___ELEMENT_AT__INT);
		createEOperation(mLifelineEClass, MLIFELINE___NUDGE_HORIZONTALLY__INT);

		mExecutionEClass = createEClass(MEXECUTION);
		createEAttribute(mExecutionEClass, MEXECUTION__START);
		createEAttribute(mExecutionEClass, MEXECUTION__FINISH);
		createEOperation(mExecutionEClass, MEXECUTION___GET_OWNER);
		createEOperation(mExecutionEClass, MEXECUTION___GET_DIAGRAM_VIEW);

		mOccurrenceEClass = createEClass(MOCCURRENCE);
		createEAttribute(mOccurrenceEClass, MOCCURRENCE__COVERED);
		createEAttribute(mOccurrenceEClass, MOCCURRENCE__STARTED_EXECUTION);
		createEAttribute(mOccurrenceEClass, MOCCURRENCE__FINISHED_EXECUTION);

		mExecutionOccurrenceEClass = createEClass(MEXECUTION_OCCURRENCE);
		createEOperation(mExecutionOccurrenceEClass, MEXECUTION_OCCURRENCE___GET_OWNER);
		createEOperation(mExecutionOccurrenceEClass, MEXECUTION_OCCURRENCE___GET_DIAGRAM_VIEW);

		mMessageEndEClass = createEClass(MMESSAGE_END);
		createEAttribute(mMessageEndEClass, MMESSAGE_END__SEND);
		createEAttribute(mMessageEndEClass, MMESSAGE_END__RECEIVE);
		createEAttribute(mMessageEndEClass, MMESSAGE_END__OTHER_END);
		createEOperation(mMessageEndEClass, MMESSAGE_END___GET_OWNER);
		createEOperation(mMessageEndEClass, MMESSAGE_END___GET_DIAGRAM_VIEW);

		mMessageEClass = createEClass(MMESSAGE);
		createEReference(mMessageEClass, MMESSAGE__SEND_END);
		createEReference(mMessageEClass, MMESSAGE__RECEIVE_END);
		createEAttribute(mMessageEClass, MMESSAGE__SEND);
		createEAttribute(mMessageEClass, MMESSAGE__RECEIVE);
		createEAttribute(mMessageEClass, MMESSAGE__SENDER);
		createEAttribute(mMessageEClass, MMESSAGE__RECEIVER);
		createEOperation(mMessageEClass, MMESSAGE___GET_OWNER);
		createEOperation(mMessageEClass, MMESSAGE___GET_DIAGRAM_VIEW);
		createEOperation(mMessageEClass, MMESSAGE___GET_END__MESSAGEEND);

		// Create data types
		optionalEDataType = createEDataType(OPTIONAL);
		optionalIntEDataType = createEDataType(OPTIONAL_INT);
		commandEDataType = createEDataType(COMMAND);
		creationCommandEDataType = createEDataType(CREATION_COMMAND);
		eObjectEDataType = createEDataType(EOBJECT);
		removalCommandEDataType = createEDataType(REMOVAL_COMMAND);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have no affect
	 * on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		UMLPackage theUMLPackage = (UMLPackage)EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);
		NotationPackage theNotationPackage = (NotationPackage)EPackage.Registry.INSTANCE
				.getEPackage(NotationPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters
		ETypeParameter mElementEClass_T = addETypeParameter(mElementEClass, "T"); //$NON-NLS-1$
		ETypeParameter mOccurrenceEClass_T = addETypeParameter(mOccurrenceEClass, "T"); //$NON-NLS-1$
		addETypeParameter(optionalEDataType, "T"); //$NON-NLS-1$
		ETypeParameter creationCommandEDataType_T = addETypeParameter(creationCommandEDataType, "T"); //$NON-NLS-1$

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(theUMLPackage.getElement());
		mElementEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theUMLPackage.getElement());
		mOccurrenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObject());
		creationCommandEDataType_T.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(this.getMElement());
		EGenericType g2 = createEGenericType(theUMLPackage.getInteraction());
		g1.getETypeArguments().add(g2);
		mInteractionEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType(theUMLPackage.getLifeline());
		g1.getETypeArguments().add(g2);
		mLifelineEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType(theUMLPackage.getExecutionSpecification());
		g1.getETypeArguments().add(g2);
		mExecutionEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType(mOccurrenceEClass_T);
		g1.getETypeArguments().add(g2);
		mOccurrenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getMOccurrence());
		g2 = createEGenericType(theUMLPackage.getExecutionOccurrenceSpecification());
		g1.getETypeArguments().add(g2);
		mExecutionOccurrenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getMOccurrence());
		g2 = createEGenericType(theUMLPackage.getMessageEnd());
		g1.getETypeArguments().add(g2);
		mMessageEndEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType(theUMLPackage.getMessage());
		g1.getETypeArguments().add(g2);
		mMessageEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(mElementEClass, MElement.class, "MElement", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMElement_Interaction(), this.getMInteraction(), null, "interaction", null, 1, 1, //$NON-NLS-1$
				MElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(mElementEClass_T);
		initEReference(getMElement_Element(), g1, null, "element", null, 1, 1, MElement.class, IS_TRANSIENT, //$NON-NLS-1$
				IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getMElement_Top(), this.getOptionalInt(), "top", null, 1, 1, MElement.class, //$NON-NLS-1$
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getMElement_Bottom(), this.getOptionalInt(), "bottom", null, 1, 1, MElement.class, //$NON-NLS-1$
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getMElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, MElement.class, //$NON-NLS-1$
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);

		EOperation op = initEOperation(getMElement__GetOwner(), null, "getOwner", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMElement__GetDiagramView(), null, "getDiagramView", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(ecorePackage.getEObject());
		g2.setEUpperBound(g3);
		initEOperation(op, g1);

		op = initEOperation(getMElement__VerticalDistance__MElement(), this.getOptionalInt(),
				"verticalDistance", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getMElement__Following(), null, "following", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMElement());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		EGenericType g4 = createEGenericType(theUMLPackage.getElement());
		g3.setEUpperBound(g4);
		initEOperation(op, g1);

		op = initEOperation(getMElement__Nudge__int(), this.getCommand(), "nudge", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "deltaY", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEOperation(getMElement__Remove(), this.getRemovalCommand(), "remove", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(mInteractionEClass, MInteraction.class, "MInteraction", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMInteraction_Lifelines(), this.getMLifeline(), null, "lifelines", null, 0, -1, //$NON-NLS-1$
				MInteraction.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMInteraction_Messages(), this.getMMessage(), null, "messages", null, 0, -1, //$NON-NLS-1$
				MInteraction.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getMInteraction__GetDiagramView(), null, "getDiagramView", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(theNotationPackage.getDiagram());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMInteraction__GetElement__Element(), null, "getElement", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		ETypeParameter t1 = addETypeParameter(op, "E"); //$NON-NLS-1$
		g1 = createEGenericType(theUMLPackage.getElement());
		t1.getEBounds().add(g1);
		g1 = createEGenericType(t1);
		addEParameter(op, g1, "element", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMElement());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g4 = createEGenericType(t1);
		g3.setEUpperBound(g4);
		initEOperation(op, g1);

		op = initEOperation(getMInteraction__GetLifeline__Lifeline(), null, "getLifeline", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, theUMLPackage.getLifeline(), "lifeline", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMLifeline());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMInteraction__GetMessage__Message(), null, "getMessage", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, theUMLPackage.getMessage(), "message", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMMessage());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMInteraction__AddLifeline__int_int(), null, "addLifeline", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "xPosition", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEInt(), "height", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getCreationCommand());
		g2 = createEGenericType(theUMLPackage.getLifeline());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMInteraction__GetLifelineAt__int(), null, "getLifelineAt", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMLifeline());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(mLifelineEClass, MLifeline.class, "MLifeline", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMLifeline_ExecutionOccurrences(), this.getMExecutionOccurrence(), null,
				"executionOccurrences", null, 0, -1, MLifeline.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				!IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getMLifeline_Executions(), this.getMExecution(), null, "executions", null, 0, -1, //$NON-NLS-1$
				MLifeline.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMLifeline_Left(), this.getOptionalInt(), "left", null, 1, 1, MLifeline.class, //$NON-NLS-1$
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getMLifeline_Right(), this.getOptionalInt(), "right", null, 1, 1, MLifeline.class, //$NON-NLS-1$
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);

		initEOperation(getMLifeline__GetOwner(), this.getMInteraction(), "getOwner", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = initEOperation(getMLifeline__GetDiagramView(), null, "getDiagramView", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(theNotationPackage.getShape());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMLifeline__Following__MElement(), null, "following", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(theUMLPackage.getElement());
		g2.setEUpperBound(g3);
		addEParameter(op, g1, "element", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMElement());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g4 = createEGenericType(theUMLPackage.getElement());
		g3.setEUpperBound(g4);
		initEOperation(op, g1);

		op = initEOperation(getMLifeline__GetExecutionOccurrence__ExecutionOccurrenceSpecification(), null,
				"getExecutionOccurrence", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLPackage.getExecutionOccurrenceSpecification(), "occurrence", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMExecutionOccurrence());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMLifeline__GetExecution__ExecutionSpecification(), null, "getExecution", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theUMLPackage.getExecutionSpecification(), "execution", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMExecution());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMLifeline__InsertExecutionAfter__MElement_int_int_Element(), null,
				"insertExecutionAfter", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "before", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEInt(), "offset", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEInt(), "height", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLPackage.getElement(), "specification", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getCreationCommand());
		g2 = createEGenericType(theUMLPackage.getExecutionSpecification());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMLifeline__InsertExecutionAfter__MElement_int_int_EClass(), null,
				"insertExecutionAfter", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "before", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEInt(), "offset", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEInt(), "height", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theEcorePackage.getEClass(), "metaclass", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getCreationCommand());
		g2 = createEGenericType(theUMLPackage.getExecutionSpecification());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(
				getMLifeline__InsertMessageAfter__MElement_int_MLifeline_MessageSort_NamedElement(), null,
				"insertMessageAfter", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getMElement());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "before", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEInt(), "offset", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getMLifeline(), "receiver", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLPackage.getMessageSort(), "sort", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLPackage.getNamedElement(), "signature", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getCreationCommand());
		g2 = createEGenericType(theUMLPackage.getMessage());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMLifeline__ElementAt__int(), null, "elementAt", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEInt(), "offset", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMElement());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g4 = createEGenericType(theUMLPackage.getElement());
		g3.setEUpperBound(g4);
		initEOperation(op, g1);

		op = initEOperation(getMLifeline__NudgeHorizontally__int(), this.getCommand(), "nudgeHorizontally", 1, //$NON-NLS-1$
				1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "deltaX", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(mExecutionEClass, MExecution.class, "MExecution", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMOccurrence());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		initEAttribute(getMExecution_Start(), g1, "start", null, 1, 1, MExecution.class, IS_TRANSIENT, //$NON-NLS-1$
				IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMOccurrence());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		initEAttribute(getMExecution_Finish(), g1, "finish", null, 1, 1, MExecution.class, IS_TRANSIENT, //$NON-NLS-1$
				IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEOperation(getMExecution__GetOwner(), this.getMLifeline(), "getOwner", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = initEOperation(getMExecution__GetDiagramView(), null, "getDiagramView", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(theNotationPackage.getShape());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(mOccurrenceEClass, MOccurrence.class, "MOccurrence", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMLifeline());
		g1.getETypeArguments().add(g2);
		initEAttribute(getMOccurrence_Covered(), g1, "covered", null, 1, 1, MOccurrence.class, IS_TRANSIENT, //$NON-NLS-1$
				IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMExecution());
		g1.getETypeArguments().add(g2);
		initEAttribute(getMOccurrence_StartedExecution(), g1, "startedExecution", null, 1, 1, //$NON-NLS-1$
				MOccurrence.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMExecution());
		g1.getETypeArguments().add(g2);
		initEAttribute(getMOccurrence_FinishedExecution(), g1, "finishedExecution", null, 1, 1, //$NON-NLS-1$
				MOccurrence.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(mExecutionOccurrenceEClass, MExecutionOccurrence.class, "MExecutionOccurrence", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getMExecutionOccurrence__GetOwner(), this.getMLifeline(), "getOwner", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = initEOperation(getMExecutionOccurrence__GetDiagramView(), null, "getDiagramView", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(theNotationPackage.getIdentityAnchor());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(mMessageEndEClass, MMessageEnd.class, "MMessageEnd", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMMessageEnd_Send(), ecorePackage.getEBoolean(), "send", null, 1, 1, //$NON-NLS-1$
				MMessageEnd.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMMessageEnd_Receive(), ecorePackage.getEBoolean(), "receive", null, 1, 1, //$NON-NLS-1$
				MMessageEnd.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMMessageEnd());
		g1.getETypeArguments().add(g2);
		initEAttribute(getMMessageEnd_OtherEnd(), g1, "otherEnd", null, 1, 1, MMessageEnd.class, IS_TRANSIENT, //$NON-NLS-1$
				IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEOperation(getMMessageEnd__GetOwner(), this.getMMessage(), "getOwner", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = initEOperation(getMMessageEnd__GetDiagramView(), null, "getDiagramView", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(theNotationPackage.getIdentityAnchor());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(mMessageEClass, MMessage.class, "MMessage", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMMessage_SendEnd(), this.getMMessageEnd(), null, "sendEnd", null, 0, 1, //$NON-NLS-1$
				MMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMMessage_ReceiveEnd(), this.getMMessageEnd(), null, "receiveEnd", null, 0, 1, //$NON-NLS-1$
				MMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMMessageEnd());
		g1.getETypeArguments().add(g2);
		initEAttribute(getMMessage_Send(), g1, "send", null, 1, 1, MMessage.class, IS_TRANSIENT, IS_VOLATILE, //$NON-NLS-1$
				!IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMMessageEnd());
		g1.getETypeArguments().add(g2);
		initEAttribute(getMMessage_Receive(), g1, "receive", null, 1, 1, MMessage.class, IS_TRANSIENT, //$NON-NLS-1$
				IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMLifeline());
		g1.getETypeArguments().add(g2);
		initEAttribute(getMMessage_Sender(), g1, "sender", null, 1, 1, MMessage.class, IS_TRANSIENT, //$NON-NLS-1$
				IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMLifeline());
		g1.getETypeArguments().add(g2);
		initEAttribute(getMMessage_Receiver(), g1, "receiver", null, 1, 1, MMessage.class, IS_TRANSIENT, //$NON-NLS-1$
				IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEOperation(getMMessage__GetOwner(), this.getMInteraction(), "getOwner", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = initEOperation(getMMessage__GetDiagramView(), null, "getDiagramView", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(theNotationPackage.getConnector());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMMessage__GetEnd__MessageEnd(), null, "getEnd", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLPackage.getMessageEnd(), "end", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getOptional());
		g2 = createEGenericType(this.getMMessageEnd());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		// Initialize data types
		initEDataType(optionalEDataType, Optional.class, "Optional", !IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(optionalIntEDataType, OptionalInt.class, "OptionalInt", !IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(commandEDataType, Command.class, "Command", !IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(creationCommandEDataType, CreationCommand.class, "CreationCommand", !IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(eObjectEDataType, EObject.class, "EObject", !IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(removalCommandEDataType, RemovalCommand.class, "RemovalCommand", !IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // SequenceDiagramPackageImpl
