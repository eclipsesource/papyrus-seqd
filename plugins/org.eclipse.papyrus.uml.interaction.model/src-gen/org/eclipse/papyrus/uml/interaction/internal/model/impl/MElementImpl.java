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
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.NudgeCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MElement</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl#getInteraction
 * <em>Interaction</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl#getElement
 * <em>Element</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl#getDiagramView <em>Diagram
 * View</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl#getTop <em>Top</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl#getBottom
 * <em>Bottom</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MElementImpl<T extends Element> extends MObjectImpl<T> implements MElement<T> {
	/**
	 * The default value of the '{@link #getTop() <em>Top</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getTop()
	 * @generated
	 * @ordered
	 */
	protected static final OptionalInt TOP_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getBottom() <em>Bottom</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getBottom()
	 * @generated
	 * @ordered
	 */
	protected static final OptionalInt BOTTOM_EDEFAULT = null;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MElementImpl() {
		super();
	}

	protected MElementImpl(MElement<?> owner, T element) {
		super(owner, element);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public MInteraction getInteraction() {
		return super.getInteraction();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public T getElement() {
		return super.getElement();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<? extends EObject> getDiagramView() {
		return super.getDiagramView();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public OptionalInt getTop() {
		return getVertex().map(layoutHelper()::getTop).orElseGet(OptionalInt::empty);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public OptionalInt getBottom() {
		return getVertex().map(layoutHelper()::getBottom).orElseGet(OptionalInt::empty);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getName() {
		Element element = getElement();
		if (element instanceof NamedElement) {
			return ((NamedElement)element).getName();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public MElement<?> getOwner() {
		return super.getOwner();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public OptionalInt verticalDistance(MElement<?> other) {
		OptionalInt myTop = getTop();
		OptionalInt otherBottom = ((MElementImpl<?>)other).getBottom();
		return myTop.isPresent() && otherBottom.isPresent()
				? OptionalInt.of(myTop.getAsInt() - otherBottom.getAsInt())
				: OptionalInt.empty();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MElement<? extends Element>> following() {
		Optional<Vertex> result = getVertex().flatMap(vtx -> vtx.graph().following(vtx));
		return result.map(Vertex::getInteractionElement).flatMap(getInteraction()::getElement);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command nudge(int deltaY) {
		return new NudgeCommand(this, deltaY);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command remove() {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SequenceDiagramPackage.MELEMENT__INTERACTION:
				return getInteraction();
			case SequenceDiagramPackage.MELEMENT__ELEMENT:
				return getElement();
			case SequenceDiagramPackage.MELEMENT__TOP:
				return getTop();
			case SequenceDiagramPackage.MELEMENT__BOTTOM:
				return getBottom();
			case SequenceDiagramPackage.MELEMENT__NAME:
				return getName();
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
			case SequenceDiagramPackage.MELEMENT__INTERACTION:
				return getInteraction() != null;
			case SequenceDiagramPackage.MELEMENT__ELEMENT:
				return getElement() != null;
			case SequenceDiagramPackage.MELEMENT__TOP:
				return TOP_EDEFAULT == null ? getTop() != null : !TOP_EDEFAULT.equals(getTop());
			case SequenceDiagramPackage.MELEMENT__BOTTOM:
				return BOTTOM_EDEFAULT == null ? getBottom() != null : !BOTTOM_EDEFAULT.equals(getBottom());
			case SequenceDiagramPackage.MELEMENT__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
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
			case SequenceDiagramPackage.MELEMENT___GET_OWNER:
				return getOwner();
			case SequenceDiagramPackage.MELEMENT___GET_DIAGRAM_VIEW:
				return getDiagramView();
			case SequenceDiagramPackage.MELEMENT___VERTICAL_DISTANCE__MELEMENT:
				return verticalDistance((MElement<?>)arguments.get(0));
			case SequenceDiagramPackage.MELEMENT___FOLLOWING:
				return following();
			case SequenceDiagramPackage.MELEMENT___NUDGE__INT:
				return nudge((Integer)arguments.get(0));
			case SequenceDiagramPackage.MELEMENT___REMOVE:
				return remove();
		}
		return super.eInvoke(operationID, arguments);
	}

} // MElementImpl
