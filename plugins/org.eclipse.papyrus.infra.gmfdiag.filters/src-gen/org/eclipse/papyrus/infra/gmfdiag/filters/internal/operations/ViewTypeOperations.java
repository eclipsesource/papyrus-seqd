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
package org.eclipse.papyrus.infra.gmfdiag.filters.internal.operations;

import java.util.Objects;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.filters.ViewType;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;

/**
 * <!-- begin-user-doc --> A static utility class that provides operations related to '<em><b>View
 * Type</b></em>' model objects. <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ViewTypeOperations {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ViewTypeOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public static boolean matches(ViewType viewType, Object input) {
		View view = PlatformHelper.getAdapter(input, View.class);
		return (view != null) && (viewType.getType() != null)
				&& Objects.equals(view.getType(), viewType.getType());
	}

} // ViewTypeOperations
