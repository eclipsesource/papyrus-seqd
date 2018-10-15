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

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.RemoveExecutionOccurrenceCommand;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MExecution Occurrence</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class MExecutionOccurrenceImpl extends MOccurrenceImpl<ExecutionOccurrenceSpecification> implements MExecutionOccurrence {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MExecutionOccurrenceImpl() {
		super();
	}

	protected MExecutionOccurrenceImpl(MLifelineImpl owner, ExecutionOccurrenceSpecification occurrence) {
		super(owner, occurrence);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MEXECUTION_OCCURRENCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public MLifeline getOwner() {
		return (MLifeline)super.getOwner();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<IdentityAnchor> getDiagramView() {
		return getAnchor(getElement());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command replaceBy(MMessageEnd messageEnd) {
		return new ReplaceOccurrenceCommand(this, messageEnd);
	}

	@Override
	public Optional<MLifeline> getCovered() {
		return Optional.of(getOwner());
	}

	@Override
	public Command remove() {
		return new RemoveExecutionOccurrenceCommand(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SequenceDiagramPackage.MEXECUTION_OCCURRENCE___GET_OWNER:
				return getOwner();
			case SequenceDiagramPackage.MEXECUTION_OCCURRENCE___GET_DIAGRAM_VIEW:
				return getDiagramView();
			case SequenceDiagramPackage.MEXECUTION_OCCURRENCE___REPLACE_BY__MMESSAGEEND:
				return replaceBy((MMessageEnd)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} // MExecutionOccurrenceImpl
