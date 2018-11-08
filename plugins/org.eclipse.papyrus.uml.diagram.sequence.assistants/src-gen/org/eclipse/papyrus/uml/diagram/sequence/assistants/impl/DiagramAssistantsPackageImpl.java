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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.papyrus.infra.filters.FiltersPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsFactory;
import org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsPackage;
import org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider;
import org.eclipse.uml2.types.TypesPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class DiagramAssistantsPackageImpl extends EPackageImpl implements DiagramAssistantsPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass sequenceDiagramAssistantProviderEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DiagramAssistantsPackageImpl() {
		super(eNS_URI, DiagramAssistantsFactory.eINSTANCE);
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
	 * This method is used to initialize {@link DiagramAssistantsPackage#eINSTANCE} when that field is
	 * accessed. Clients should not invoke it directly. Instead, they should simply access that field to
	 * obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DiagramAssistantsPackage init() {
		if (isInited) {
			return (DiagramAssistantsPackage)EPackage.Registry.INSTANCE
					.getEPackage(DiagramAssistantsPackage.eNS_URI);
		}

		// Obtain or create and register package
		Object registeredDiagramAssistantsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DiagramAssistantsPackageImpl theDiagramAssistantsPackage = registeredDiagramAssistantsPackage instanceof DiagramAssistantsPackageImpl
				? (DiagramAssistantsPackageImpl)registeredDiagramAssistantsPackage
				: new DiagramAssistantsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		AssistantPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		FiltersPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDiagramAssistantsPackage.createPackageContents();

		// Initialize created meta-data
		theDiagramAssistantsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDiagramAssistantsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DiagramAssistantsPackage.eNS_URI, theDiagramAssistantsPackage);
		return theDiagramAssistantsPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSequenceDiagramAssistantProvider() {
		return sequenceDiagramAssistantProviderEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DiagramAssistantsFactory getDiagramAssistantsFactory() {
		return (DiagramAssistantsFactory)getEFactoryInstance();
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
		sequenceDiagramAssistantProviderEClass = createEClass(SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER);
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
		AssistantPackage theAssistantPackage = (AssistantPackage)EPackage.Registry.INSTANCE
				.getEPackage(AssistantPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		sequenceDiagramAssistantProviderEClass.getESuperTypes()
				.add(theAssistantPackage.getModelingAssistantProvider());

		// Initialize classes, features, and operations; add parameters
		initEClass(sequenceDiagramAssistantProviderEClass, SequenceDiagramAssistantProvider.class,
				"SequenceDiagramAssistantProvider", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// duplicates
		createDuplicatesAnnotations();
	}

	/**
	 * Initializes the annotations for <b>duplicates</b>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void createDuplicatesAnnotations() {
		String source = "duplicates"; //$NON-NLS-1$
		addAnnotation(sequenceDiagramAssistantProviderEClass, source, new String[] {});
	}

} // DiagramAssistantsPackageImpl
