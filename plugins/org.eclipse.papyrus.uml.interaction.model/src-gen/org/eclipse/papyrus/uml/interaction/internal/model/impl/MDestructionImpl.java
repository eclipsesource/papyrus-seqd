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
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MDestruction;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MDestruction</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class MDestructionImpl extends MMessageEndImpl implements MDestruction {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MDestructionImpl() {
		super();
	}

	protected MDestructionImpl(MMessageImpl owner, DestructionOccurrenceSpecification destruction) {
		super(owner, destruction);
	}

	protected MDestructionImpl(MLifelineImpl owner, DestructionOccurrenceSpecification destruction) {
		super(owner, destruction);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MDESTRUCTION;
	}

} // MDestructionImpl
