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
package org.eclipse.papyrus.uml.interaction.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Nudge Kind</b></em>',
 * and utility methods for working with them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getNudgeKind()
 * @model
 * @generated
 */
public enum NudgeKind implements Enumerator {
	/**
	 * The '<em><b>Element Only</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> Nudge only the element and no others. <!-- end-model-doc -->
	 * 
	 * @see #ELEMENT_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	ELEMENT_ONLY(0, "elementOnly", "elementOnly"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Following</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> Nudge the element and all following (below) it in the trace. <!-- end-model-doc -->
	 * 
	 * @see #FOLLOWING_VALUE
	 * @generated
	 * @ordered
	 */
	FOLLOWING(1, "following", "following"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Preceding</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> Nudge the element and all preceding (above) it in the trace. <!-- end-model-doc -->
	 * 
	 * @see #PRECEDING_VALUE
	 * @generated
	 * @ordered
	 */
	PRECEDING(2, "preceding", "preceding"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Element Only</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> Nudge only the element and no others. <!-- end-model-doc -->
	 * 
	 * @see #ELEMENT_ONLY
	 * @model name="elementOnly"
	 * @generated
	 * @ordered
	 */
	public static final int ELEMENT_ONLY_VALUE = 0;

	/**
	 * The '<em><b>Following</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> Nudge the element and all following (below) it in the trace. <!-- end-model-doc -->
	 * 
	 * @see #FOLLOWING
	 * @model name="following"
	 * @generated
	 * @ordered
	 */
	public static final int FOLLOWING_VALUE = 1;

	/**
	 * The '<em><b>Preceding</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> Nudge the element and all preceding (above) it in the trace. <!-- end-model-doc -->
	 * 
	 * @see #PRECEDING
	 * @model name="preceding"
	 * @generated
	 * @ordered
	 */
	public static final int PRECEDING_VALUE = 2;

	/**
	 * An array of all the '<em><b>Nudge Kind</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	private static final NudgeKind[] VALUES_ARRAY = new NudgeKind[] {ELEMENT_ONLY, FOLLOWING, PRECEDING, };

	/**
	 * A public read-only list of all the '<em><b>Nudge Kind</b></em>' enumerators. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<NudgeKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Nudge Kind</b></em>' literal with the specified literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NudgeKind get(String literal) {
		for (NudgeKind result : VALUES_ARRAY) {
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Nudge Kind</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NudgeKind getByName(String name) {
		for (NudgeKind result : VALUES_ARRAY) {
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Nudge Kind</b></em>' literal with the specified integer value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NudgeKind get(int value) {
		switch (value) {
			case ELEMENT_ONLY_VALUE:
				return ELEMENT_ONLY;
			case FOLLOWING_VALUE:
				return FOLLOWING;
			case PRECEDING_VALUE:
				return PRECEDING;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private NudgeKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
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
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // NudgeKind
