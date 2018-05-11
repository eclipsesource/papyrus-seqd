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
package org.eclipse.papyrus.infra.gmfdiag.filters.impl;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.filters.FiltersPackage;

import org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersFactory;
import org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersPackage;
import org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram;
import org.eclipse.papyrus.infra.gmfdiag.filters.ViewType;

import org.eclipse.uml2.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DiagramFiltersPackageImpl extends EPackageImpl implements DiagramFiltersPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inDiagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass viewTypeEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DiagramFiltersPackageImpl() {
		super(eNS_URI, DiagramFiltersFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link DiagramFiltersPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DiagramFiltersPackage init() {
		if (isInited)
			return (DiagramFiltersPackage)EPackage.Registry.INSTANCE
					.getEPackage(DiagramFiltersPackage.eNS_URI);

		// Obtain or create and register package
		DiagramFiltersPackageImpl theDiagramFiltersPackage = (DiagramFiltersPackageImpl)(EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof DiagramFiltersPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new DiagramFiltersPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		FiltersPackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDiagramFiltersPackage.createPackageContents();

		// Initialize created meta-data
		theDiagramFiltersPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDiagramFiltersPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DiagramFiltersPackage.eNS_URI, theDiagramFiltersPackage);
		return theDiagramFiltersPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInDiagram() {
		return inDiagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInDiagram_Filter() {
		return (EReference)inDiagramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInDiagram_OwnedFilter() {
		return (EReference)inDiagramEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getViewType() {
		return viewTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getViewType_Type() {
		return (EAttribute)viewTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DiagramFiltersFactory getDiagramFiltersFactory() {
		return (DiagramFiltersFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		inDiagramEClass = createEClass(IN_DIAGRAM);
		createEReference(inDiagramEClass, IN_DIAGRAM__FILTER);
		createEReference(inDiagramEClass, IN_DIAGRAM__OWNED_FILTER);

		viewTypeEClass = createEClass(VIEW_TYPE);
		createEAttribute(viewTypeEClass, VIEW_TYPE__TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		FiltersPackage theFiltersPackage = (FiltersPackage)EPackage.Registry.INSTANCE
				.getEPackage(FiltersPackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE
				.getEPackage(TypesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		inDiagramEClass.getESuperTypes().add(theFiltersPackage.getFilter());
		viewTypeEClass.getESuperTypes().add(theFiltersPackage.getFilter());

		// Initialize classes, features, and operations; add parameters
		initEClass(inDiagramEClass, InDiagram.class, "InDiagram", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInDiagram_Filter(), theFiltersPackage.getFilter(), null, "filter", null, 1, 1, //$NON-NLS-1$
				InDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getInDiagram_OwnedFilter(), theFiltersPackage.getFilter(), null, "ownedFilter", null, //$NON-NLS-1$
				0, 1, InDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(viewTypeEClass, ViewType.class, "ViewType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getViewType_Type(), theTypesPackage.getString(), "type", null, 1, 1, ViewType.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				!IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// subsets
		createSubsetsAnnotations();
	}

	/**
	 * Initializes the annotations for <b>subsets</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createSubsetsAnnotations() {
		String source = "subsets"; //$NON-NLS-1$	
		addAnnotation(getInDiagram_OwnedFilter(), source, new String[] {},
				new URI[] {URI.createURI(eNS_URI).appendFragment("//InDiagram/filter") //$NON-NLS-1$
				});
	}

} //DiagramFiltersPackageImpl
