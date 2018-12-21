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

import static java.util.Collections.singleton;
import static org.eclipse.emf.ecore.util.EcoreUtil.isAncestor;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DeferredPaddingCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.DependencyContext;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.NudgeCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.RootContextHandler;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.NudgeKind;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommand;
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
	public final Command nudge(int deltaY) {
		return nudge(deltaY, NudgeKind.FOLLOWING);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command nudge(int deltaY, NudgeKind mode) {
		return new NudgeCommand(this, deltaY, mode);
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
	 * Obtain a command to remove my logical model self from the logical model.
	 * 
	 * @return my logical removal command
	 */
	protected <E extends Element, C extends RemovalCommand<E>> Command removeLogicalElement(Class<C> key,
			Supplier<? extends C> removalFactory) {

		return DependencyContext.getDynamic().apply(this, key, //
				__ -> removalFactory.get().chain(new DeleteCommand(getEditingDomain(), singleton(this)) {
					@Override
					protected void prepareCommand() {
						CompoundCommand removeCommand = new CompoundCommand(
								CompoundCommand.MERGE_COMMAND_ALL);
						for (Object next : getCollection()) {
							if (next instanceof EObject) {
								EObject object = (EObject)next;
								EObject owner = object.eContainer();
								EReference containment = object.eContainmentFeature();
								if (!FeatureMapUtil.isMany(owner, containment)) {
									removeCommand
											.append(new RemoveCommand(domain, owner, containment, object));
								} else {
									EList<?> list = (EList<?>)owner.eGet(containment);
									if (list instanceof MContainmentList<?>) {
										// Cannot use the public API to remove stuff
										list = ((MContainmentList<?>)list).mutableList();
									}
									removeCommand.append(new RemoveCommand(domain, list, object));
								}
							}
						}
						append(removeCommand.unwrap());
					}
				})).orElse(null);
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public boolean exists() {
		MInteraction interaction = getInteraction();

		// I am is still attached to the logical model
		return (interaction != null)
				// and my underlying UML element is still in that interaction
				&& isAncestor(interaction.getElement(), getElement());
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
		RootContextHandler<Command> rootCtx = RootContextHandler.create(this);

		// Avoid cycling through this element again if it has already created this command
		Command result = DependencyContext.getDynamic(rootCtx).apply(this, key, __ -> commandFactory.get()) //
				.orElse(null);

		return rootCtx.apply(result);
	}

	/**
	 * Similar to the {@link #withPadding(Class, Supplier)} API, create a new creation command, if necessary,
	 * for some dependency that is being tracked in the current {@link DependencyContext}. Additionally,
	 * ensure that such a command can add to the {@link DeferredPaddingCommand} for padding after the
	 * completion of the root command of the dependency context.
	 * 
	 * @param key
	 *            the kind of creation command to be created
	 * @param commandFactory
	 *            to create the command, if it is needed
	 * @return the creation command, or {@code null} if this dependency already has created a command
	 * @see #withPadding(Class, Supplier)
	 * @see #withDependencies(Class, Supplier)
	 */
	protected <R extends EObject> CreationCommand<R> createWithPadding(
			Class<? extends CreationCommand<R>> key, Supplier<? extends CreationCommand<R>> commandFactory) {

		RootContextHandler<Command> rootCtx = RootContextHandler.create(this);

		// Avoid cycling through this element again if it has already created this command
		CreationCommand<R> command = DependencyContext.getDynamic(rootCtx)
				.apply(this, key, __ -> commandFactory.get()) //
				.orElse(null);

		// This cast is safe by the implementation of Command::chain in CreationCommands
		@SuppressWarnings("unchecked")
		CreationCommand<R> result = (CreationCommand<R>)rootCtx.apply(command);
		return result;
	}

	/**
	 * Ensure that the semantic ordering of interaction fragments is reestablished as necessary after
	 * completion of a {@code command}, accounting for nested commands that would do the same and preventing
	 * redundant sorting.
	 * 
	 * @param command
	 *            a command to wrap with sorting, or {@code null} if it was elided by (e.g.) the
	 *            {@link #withDependencies(Class, Supplier)} mechanism
	 * @return the {@code command}, wrapped with sorting if necessary
	 */
	protected Command withSemanticSorting(Command command) {
		MInteraction interaction = getInteraction();
		DependencyContext.getDynamic().put(interaction, interaction.sort());
		return command;
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
			case SequenceDiagramPackage.MELEMENT___NUDGE__INT_NUDGEKIND:
				return nudge((Integer)arguments.get(0), (NudgeKind)arguments.get(1));
			case SequenceDiagramPackage.MELEMENT___REMOVE:
				return remove();
			case SequenceDiagramPackage.MELEMENT___PRECEDES__MELEMENT:
				return precedes((MElement<?>)arguments.get(0));
			case SequenceDiagramPackage.MELEMENT___EXISTS:
				return exists();
		}
		return super.eInvoke(operationID, arguments);
	}

} // MElementImpl
