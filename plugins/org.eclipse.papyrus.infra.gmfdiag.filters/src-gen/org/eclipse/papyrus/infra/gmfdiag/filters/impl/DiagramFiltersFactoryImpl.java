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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersFactory;
import org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersPackage;
import org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram;
import org.eclipse.papyrus.infra.gmfdiag.filters.ViewType;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class DiagramFiltersFactoryImpl extends EFactoryImpl implements DiagramFiltersFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static DiagramFiltersFactory init() {
		try {
			DiagramFiltersFactory theDiagramFiltersFactory = (DiagramFiltersFactory)EPackage.Registry.INSTANCE
					.getEFactory(DiagramFiltersPackage.eNS_URI);
			if (theDiagramFiltersFactory != null) {
				return theDiagramFiltersFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DiagramFiltersFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramFiltersFactoryImpl() {
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
			case DiagramFiltersPackage.IN_DIAGRAM:
				return createInDiagram();
			case DiagramFiltersPackage.VIEW_TYPE:
				return createViewType();
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
	public InDiagram createInDiagram() {
		InDiagramImpl inDiagram = new InDiagramImpl();
		return inDiagram;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ViewType createViewType() {
		ViewTypeImpl viewType = new ViewTypeImpl();
		return viewType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DiagramFiltersPackage getDiagramFiltersPackage() {
		return (DiagramFiltersPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DiagramFiltersPackage getPackage() {
		return DiagramFiltersPackage.eINSTANCE;
	}

} // DiagramFiltersFactoryImpl
