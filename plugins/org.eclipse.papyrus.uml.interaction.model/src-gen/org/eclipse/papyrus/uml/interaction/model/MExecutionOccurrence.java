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

import java.util.Optional;

import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>MExecution Occurrence</b></em>'. <!--
 * end-user-doc -->
 *
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage#getMExecutionOccurrence()
 * @model
 * @generated
 */
public interface MExecutionOccurrence extends MOccurrence<ExecutionOccurrenceSpecification> {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated
	 */
	@Override
	MLifeline getOwner();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 *        dataType="org.eclipse.papyrus.uml.interaction.model.Optional&lt;org.eclipse.gmf.runtime.notation.IdentityAnchor&gt;"
	 *        required="true"
	 * @generated
	 */
	@Override
	Optional<IdentityAnchor> getDiagramView();

} // MExecutionOccurrence
