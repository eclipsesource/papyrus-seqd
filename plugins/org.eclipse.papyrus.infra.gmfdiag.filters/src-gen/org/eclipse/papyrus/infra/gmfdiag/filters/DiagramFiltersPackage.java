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
package org.eclipse.papyrus.infra.gmfdiag.filters;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.filters.FiltersPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersFactory
 * @model kind="package"
 * @generated
 */
public interface DiagramFiltersPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "filters"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/Papyrus/2018/common/diagram/filters"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "diagfilt"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DiagramFiltersPackage eINSTANCE = org.eclipse.papyrus.infra.gmfdiag.filters.impl.DiagramFiltersPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.filters.impl.InDiagramImpl <em>In Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.impl.InDiagramImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.impl.DiagramFiltersPackageImpl#getInDiagram()
	 * @generated
	 */
	int IN_DIAGRAM = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_DIAGRAM__NAME = FiltersPackage.FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_DIAGRAM__FILTER = FiltersPackage.FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owned Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_DIAGRAM__OWNED_FILTER = FiltersPackage.FILTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>In Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_DIAGRAM_FEATURE_COUNT = FiltersPackage.FILTER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_DIAGRAM___MATCHES__OBJECT = FiltersPackage.FILTER___MATCHES__OBJECT;

	/**
	 * The number of operations of the '<em>In Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_DIAGRAM_OPERATION_COUNT = FiltersPackage.FILTER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.filters.impl.ViewTypeImpl <em>View Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.impl.ViewTypeImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.impl.DiagramFiltersPackageImpl#getViewType()
	 * @generated
	 */
	int VIEW_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_TYPE__NAME = FiltersPackage.FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_TYPE__TYPE = FiltersPackage.FILTER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>View Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_TYPE_FEATURE_COUNT = FiltersPackage.FILTER_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_TYPE___MATCHES__OBJECT = FiltersPackage.FILTER___MATCHES__OBJECT;

	/**
	 * The number of operations of the '<em>View Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_TYPE_OPERATION_COUNT = FiltersPackage.FILTER_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram <em>In Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>In Diagram</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram
	 * @generated
	 */
	EClass getInDiagram();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getFilter()
	 * @see #getInDiagram()
	 * @generated
	 */
	EReference getInDiagram_Filter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getOwnedFilter <em>Owned Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Owned Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getOwnedFilter()
	 * @see #getInDiagram()
	 * @generated
	 */
	EReference getInDiagram_OwnedFilter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.filters.ViewType <em>View Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View Type</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.ViewType
	 * @generated
	 */
	EClass getViewType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.filters.ViewType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.ViewType#getType()
	 * @see #getViewType()
	 * @generated
	 */
	EAttribute getViewType_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DiagramFiltersFactory getDiagramFiltersFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.filters.impl.InDiagramImpl <em>In Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.filters.impl.InDiagramImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.filters.impl.DiagramFiltersPackageImpl#getInDiagram()
		 * @generated
		 */
		EClass IN_DIAGRAM = eINSTANCE.getInDiagram();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IN_DIAGRAM__FILTER = eINSTANCE.getInDiagram_Filter();

		/**
		 * The meta object literal for the '<em><b>Owned Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IN_DIAGRAM__OWNED_FILTER = eINSTANCE.getInDiagram_OwnedFilter();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.filters.impl.ViewTypeImpl <em>View Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.filters.impl.ViewTypeImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.filters.impl.DiagramFiltersPackageImpl#getViewType()
		 * @generated
		 */
		EClass VIEW_TYPE = eINSTANCE.getViewType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIEW_TYPE__TYPE = eINSTANCE.getViewType_Type();

	}

} //DiagramFiltersPackage
