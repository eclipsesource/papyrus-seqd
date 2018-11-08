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

import org.eclipse.papyrus.infra.filters.Filter;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>In Diagram</b></em>'. <!--
 * end-user-doc --> <!-- begin-model-doc --> Filters the contextual diagram. What that diagram is depends on
 * the context in which the filter is used. <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getFilter <em>Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getOwnedFilter <em>Owned Filter</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersPackage#getInDiagram()
 * @model
 * @generated
 */
public interface InDiagram extends Filter {
	/**
	 * Returns the value of the '<em><b>Filter</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filter</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc --> <!-- begin-model-doc --> The filter to apply to the contextual diagram. For
	 * example, a {@link ViewType} filter. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Filter</em>' reference.
	 * @see #setFilter(Filter)
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersPackage#getInDiagram_Filter()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Filter getFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getFilter
	 * <em>Filter</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Filter</em>' reference.
	 * @see #getFilter()
	 * @generated
	 */
	void setFilter(Filter value);

	/**
	 * Returns the value of the '<em><b>Owned Filter</b></em>' containment reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getFilter() <em>Filter</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Filter</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc --> <!-- begin-model-doc --> If the {@link #getFilter() filter} is not referenced,
	 * the owned filter to apply to the contextual diagram. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Owned Filter</em>' containment reference.
	 * @see #setOwnedFilter(Filter)
	 * @see org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersPackage#getInDiagram_OwnedFilter()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Filter getOwnedFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram#getOwnedFilter
	 * <em>Owned Filter</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Owned Filter</em>' containment reference.
	 * @see #getOwnedFilter()
	 * @generated
	 */
	void setOwnedFilter(Filter value);

} // InDiagram
