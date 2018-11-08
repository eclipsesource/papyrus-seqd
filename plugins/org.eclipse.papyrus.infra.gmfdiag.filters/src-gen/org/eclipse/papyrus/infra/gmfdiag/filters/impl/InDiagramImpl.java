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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersPackage;
import org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram;
import org.eclipse.papyrus.infra.gmfdiag.filters.internal.operations.InDiagramOperations;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>In Diagram</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.filters.impl.InDiagramImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.filters.impl.InDiagramImpl#getFilter <em>Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.filters.impl.InDiagramImpl#getOwnedFilter <em>Owned
 * Filter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InDiagramImpl extends MinimalEObjectImpl.Container implements InDiagram {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFilter() <em>Filter</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter filter;

	/**
	 * The cached value of the '{@link #getOwnedFilter() <em>Owned Filter</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOwnedFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter ownedFilter;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected InDiagramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagramFiltersPackage.Literals.IN_DIAGRAM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		newName = newName == null ? NAME_EDEFAULT : newName;
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramFiltersPackage.IN_DIAGRAM__NAME,
					oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Filter getFilter() {
		if ((filter != null) && filter.eIsProxy()) {
			InternalEObject oldFilter = (InternalEObject)filter;
			filter = (Filter)eResolveProxy(oldFilter);
			if (filter != oldFilter) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							DiagramFiltersPackage.IN_DIAGRAM__FILTER, oldFilter, filter));
				}
			}
		}
		return filter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Filter basicGetFilter() {
		return filter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFilter(Filter newFilter) {
		Filter oldFilter = filter;
		filter = newFilter;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramFiltersPackage.IN_DIAGRAM__FILTER,
					oldFilter, filter));
		}
		Resource.Internal eInternalResource = eInternalResource();
		if ((eInternalResource == null) || !eInternalResource.isLoading()) {
			if ((ownedFilter != null) && (ownedFilter != newFilter)) {
				setOwnedFilter(null);
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Filter getOwnedFilter() {
		return ownedFilter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetOwnedFilter(Filter newOwnedFilter, NotificationChain msgs) {
		Filter oldOwnedFilter = ownedFilter;
		ownedFilter = newOwnedFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER, oldOwnedFilter, newOwnedFilter);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		Resource.Internal eInternalResource = eInternalResource();
		if ((eInternalResource == null) || !eInternalResource.isLoading()) {
			if (newOwnedFilter != null) {
				if (newOwnedFilter != filter) {
					setFilter(newOwnedFilter);
				}
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOwnedFilter(Filter newOwnedFilter) {
		if (newOwnedFilter != ownedFilter) {
			NotificationChain msgs = null;
			if (ownedFilter != null) {
				msgs = ((InternalEObject)ownedFilter).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER, null, msgs);
			}
			if (newOwnedFilter != null) {
				msgs = ((InternalEObject)newOwnedFilter).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER, null, msgs);
			}
			msgs = basicSetOwnedFilter(newOwnedFilter, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER, newOwnedFilter, newOwnedFilter));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean matches(Object input) {
		return InDiagramOperations.matches(this, input);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER:
				return basicSetOwnedFilter(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiagramFiltersPackage.IN_DIAGRAM__NAME:
				return getName();
			case DiagramFiltersPackage.IN_DIAGRAM__FILTER:
				if (resolve) {
					return getFilter();
				}
				return basicGetFilter();
			case DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER:
				return getOwnedFilter();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DiagramFiltersPackage.IN_DIAGRAM__NAME:
				setName((String)newValue);
				return;
			case DiagramFiltersPackage.IN_DIAGRAM__FILTER:
				setFilter((Filter)newValue);
				return;
			case DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER:
				setOwnedFilter((Filter)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DiagramFiltersPackage.IN_DIAGRAM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DiagramFiltersPackage.IN_DIAGRAM__FILTER:
				setFilter((Filter)null);
				return;
			case DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER:
				setOwnedFilter((Filter)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DiagramFiltersPackage.IN_DIAGRAM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DiagramFiltersPackage.IN_DIAGRAM__FILTER:
				return filter != null;
			case DiagramFiltersPackage.IN_DIAGRAM__OWNED_FILTER:
				return ownedFilter != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case DiagramFiltersPackage.IN_DIAGRAM___MATCHES__OBJECT:
				return matches(arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // InDiagramImpl
