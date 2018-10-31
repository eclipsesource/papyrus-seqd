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

import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DeferredPaddingCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DependencyContext;
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
	 * @generated NOT
	 */
	@Override
	public boolean precedes(MElement<?> other) {
		boolean result = false;

		Optional<Vertex> myVertex = getVertex();
		if (myVertex.isPresent()) {
			@SuppressWarnings("unchecked")
			Optional<Vertex> otherVertex = as(Optional.of(other), MObjectImpl.class)
					.flatMap(MObjectImpl::getVertex);
			result = otherVertex.isPresent() && myVertex.get().precedes(otherVertex.get());
		}

		return result;
	}

	/**
	 * Create a new command, if necessary, for some dependency that is being tracked in the current
	 * {@link DependencyContext}.
	 * 
	 * @param key
	 *            the kind of command to be created
	 * @param commandFactory
	 *            to create the command, if it is needed
	 * @return the command, or {@code null} if this dependency already has created a command
	 * @see #withPadding(Class, Supplier) if the command needs to provide for padding on the insertion or
	 *      relocation of an element in the diagram
	 */
	protected <C extends Command> C withDependencies(Class<C> key, Supplier<? extends C> commandFactory) {
		// Avoid cycling through this element again if it has already created this command
		return DependencyContext.getDynamic().apply(this, key, __ -> commandFactory.get()) //
				.orElse(null);
	}

	/**
	 * Similar to the {@link #withDependencies(Class, Supplier)} API, create a new command, if necessary, for
	 * some dependency that is being tracked in the current {@link DependencyContext}. Additionally, ensure
	 * that such a command can add to the {@link DeferredPaddingCommand} for padding after the completion of
	 * the root command of the dependency context.
	 * 
	 * @param key
	 *            the kind of command to be created
	 * @param commandFactory
	 *            to create the command, if it is needed
	 * @return the command, or {@code null} if this dependency already has created a command
	 * @see #withDependencies(Class, Supplier)
	 */
	protected <C extends Command> Command withPadding(Class<C> key, Supplier<? extends C> commandFactory) {
		List<Command> compound = new ArrayList<>(2);

		Consumer<DependencyContext> getPadding = ctx -> compound.add(DeferredPaddingCommand.get(ctx, this));

		// Avoid cycling through this element again if it has already created this command
		Command result = DependencyContext.getDynamic(getPadding).apply(this, key, __ -> commandFactory.get()) //
				.orElse(null);
		if (result != null) {
			compound.add(0, result); // Do this first, then padding
			result = CompoundModelCommand.compose(getEditingDomain(), compound);
		}

		return result;
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
			case SequenceDiagramPackage.MELEMENT___PRECEDES__MELEMENT:
				return precedes((MElement<?>)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} // MElementImpl
