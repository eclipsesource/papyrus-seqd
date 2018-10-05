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
import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DependencyContext;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.RemoveExecutionCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.SetOwnerCommand;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.ExecutionSpecification;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MExecution</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl#getStart
 * <em>Start</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl#getFinish
 * <em>Finish</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MExecutionImpl extends MElementImpl<ExecutionSpecification> implements MExecution {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MExecutionImpl() {
		super();
	}

	protected MExecutionImpl(MLifelineImpl owner, ExecutionSpecification execution) {
		super(owner, execution);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MEXECUTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MOccurrence<?>> getStart() {
		return Optional.ofNullable(getElement().getStart()).flatMap(getInteractionImpl()::getElement)
				.map(MOccurrence.class::cast);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MOccurrence<?>> getFinish() {
		return Optional.ofNullable(getElement().getFinish()).flatMap(getInteractionImpl()::getElement)
				.map(MOccurrence.class::cast);
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
	public Optional<Shape> getDiagramView() {
		return super.getDiagramView().map(Shape.class::cast);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command setOwner(MLifeline newOwner, OptionalInt yPosition) {
		// Avoid cycling through this execution again
		return DependencyContext.getDynamic()
				.apply(this, SetOwnerCommand.class, exec -> new SetOwnerCommand(exec, newOwner, yPosition)) //
				.orElse(null);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SequenceDiagramPackage.MEXECUTION__START:
				return getStart();
			case SequenceDiagramPackage.MEXECUTION__FINISH:
				return getFinish();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SequenceDiagramPackage.MEXECUTION__START:
				return getStart() != null;
			case SequenceDiagramPackage.MEXECUTION__FINISH:
				return getFinish() != null;
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
			case SequenceDiagramPackage.MEXECUTION___GET_OWNER:
				return getOwner();
			case SequenceDiagramPackage.MEXECUTION___GET_DIAGRAM_VIEW:
				return getDiagramView();
			case SequenceDiagramPackage.MEXECUTION___SET_OWNER__MLIFELINE_OPTIONALINT:
				return setOwner((MLifeline)arguments.get(0), (OptionalInt)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Command remove() {
		return new RemoveExecutionCommand(this, true);
	}

} // MExecutionImpl
