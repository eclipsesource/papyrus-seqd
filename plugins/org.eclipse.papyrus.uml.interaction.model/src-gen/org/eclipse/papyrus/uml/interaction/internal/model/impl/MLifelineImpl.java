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

import static org.eclipse.papyrus.uml.interaction.graph.GraphPredicates.covers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.graph.GraphFunctions;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.InsertExecutionCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.InsertMessageCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.NudgeHorizontallyCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.RemoveLifelineCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MLifeline</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl#getExecutionOccurrences
 * <em>Execution Occurrences</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl#getExecutions
 * <em>Executions</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl#getLeft
 * <em>Left</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl#getRight
 * <em>Right</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MLifelineImpl extends MElementImpl<Lifeline> implements MLifeline {
	/**
	 * The cached value of the '{@link #getExecutionOccurrences() <em>Execution Occurrences</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExecutionOccurrences()
	 * @generated
	 * @ordered
	 */
	protected EList<MExecutionOccurrence> executionOccurrences;

	/**
	 * The cached value of the '{@link #getExecutions() <em>Executions</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExecutions()
	 * @generated
	 * @ordered
	 */
	protected EList<MExecution> executions;

	/**
	 * The default value of the '{@link #getLeft() <em>Left</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getLeft()
	 * @generated
	 * @ordered
	 */
	protected static final OptionalInt LEFT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRight() <em>Right</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getRight()
	 * @generated
	 * @ordered
	 */
	protected static final OptionalInt RIGHT_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MLifelineImpl() {
		super();
	}

	protected MLifelineImpl(MInteractionImpl owner, Lifeline lifeline) {
		super(owner, lifeline);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MLIFELINE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<MExecutionOccurrence> getExecutionOccurrences() {
		if (executionOccurrences == null) {
			executionOccurrences = new EObjectContainmentEList<>(MExecutionOccurrence.class, this,
					SequenceDiagramPackage.MLIFELINE__EXECUTION_OCCURRENCES);
		}
		return executionOccurrences;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<MExecution> getExecutions() {
		if (executions == null) {
			executions = new EObjectContainmentEList<>(MExecution.class, this,
					SequenceDiagramPackage.MLIFELINE__EXECUTIONS);
		}
		return executions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public OptionalInt getLeft() {
		return getVertex().map(layoutHelper()::getLeft).orElseGet(OptionalInt::empty);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public OptionalInt getRight() {
		return getVertex().map(layoutHelper()::getRight).orElseGet(OptionalInt::empty);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public MInteraction getOwner() {
		return (MInteraction)super.getOwner();
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
	public Optional<MElement<? extends Element>> following(MElement<? extends Element> element) {
		Vertex reference = getGraph().vertex(element.getElement());
		if (reference == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(GraphFunctions.successorOn(getElement()).apply(reference))
				.flatMap(v -> getInteraction().getElement(v.getInteractionElement()));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public CreationCommand<ExecutionSpecification> insertExecutionAfter(MElement<?> before, int offset,
			int height, Element specification) {
		return new InsertExecutionCommand(this, before, offset, height, specification);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public CreationCommand<ExecutionSpecification> insertExecutionAfter(MElement<?> before, int offset,
			int height, EClass metaclass) {
		return new InsertExecutionCommand(this, before, offset, height, metaclass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public CreationCommand<Message> insertMessageAfter(MElement<?> before, int offset, MLifeline receiver,
			MessageSort sort, NamedElement signature) {

		return new InsertMessageCommand(this, before, offset, receiver, sort, signature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CreationCommand<Message> insertMessageAfter(MElement<?> beforeSend, int sendOffset,
			MLifeline receiver, MElement<?> beforeRecv, int recvOffset, MessageSort sort,
			NamedElement signature) {

		return new InsertMessageCommand(this, beforeSend, sendOffset, receiver, beforeRecv, recvOffset, sort,
				signature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MElement<? extends Element>> elementAt(int offset) {
		int lifelineTop = layoutHelper().getTop(diagramHelper().getLifelineBodyShape(getShape(getElement())));

		int absoluteOffset = lifelineTop + offset;
		Predicate<MElement<?>> isAtOrAbove = e -> e.getTop().orElse(Integer.MAX_VALUE) <= absoluteOffset;

		Optional<? extends MElement<? extends Element>> result = getVertex().flatMap(vtx -> //
		vtx.successors().sequential().filter(covers(getElement())) //
				.map(Vertex::getInteractionElement) //
				.map(getInteraction()::getElement) //
				.filter(Optional::isPresent).map(Optional::get) //
				.filter(isAtOrAbove) //
				.reduce((a, b) -> b) // Get the last
		);

		return Optional.ofNullable(result.orElse(null));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command nudgeHorizontally(int deltaX) {
		return new NudgeHorizontallyCommand(this, deltaX);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command remove() {
		return new RemoveLifelineCommand(this, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SequenceDiagramPackage.MLIFELINE__EXECUTION_OCCURRENCES:
				return ((InternalEList<?>)getExecutionOccurrences()).basicRemove(otherEnd, msgs);
			case SequenceDiagramPackage.MLIFELINE__EXECUTIONS:
				return ((InternalEList<?>)getExecutions()).basicRemove(otherEnd, msgs);
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
			case SequenceDiagramPackage.MLIFELINE__EXECUTION_OCCURRENCES:
				return getExecutionOccurrences();
			case SequenceDiagramPackage.MLIFELINE__EXECUTIONS:
				return getExecutions();
			case SequenceDiagramPackage.MLIFELINE__LEFT:
				return getLeft();
			case SequenceDiagramPackage.MLIFELINE__RIGHT:
				return getRight();
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
			case SequenceDiagramPackage.MLIFELINE__EXECUTION_OCCURRENCES:
				return (executionOccurrences != null) && !executionOccurrences.isEmpty();
			case SequenceDiagramPackage.MLIFELINE__EXECUTIONS:
				return (executions != null) && !executions.isEmpty();
			case SequenceDiagramPackage.MLIFELINE__LEFT:
				return LEFT_EDEFAULT == null ? getLeft() != null : !LEFT_EDEFAULT.equals(getLeft());
			case SequenceDiagramPackage.MLIFELINE__RIGHT:
				return RIGHT_EDEFAULT == null ? getRight() != null : !RIGHT_EDEFAULT.equals(getRight());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SequenceDiagramPackage.MLIFELINE___GET_OWNER:
				return getOwner();
			case SequenceDiagramPackage.MLIFELINE___GET_DIAGRAM_VIEW:
				return getDiagramView();
			case SequenceDiagramPackage.MLIFELINE___FOLLOWING__MELEMENT:
				return following((MElement<? extends Element>)arguments.get(0));
			case SequenceDiagramPackage.MLIFELINE___GET_EXECUTION_OCCURRENCE__EXECUTIONOCCURRENCESPECIFICATION:
				return getExecutionOccurrence((ExecutionOccurrenceSpecification)arguments.get(0));
			case SequenceDiagramPackage.MLIFELINE___GET_EXECUTION__EXECUTIONSPECIFICATION:
				return getExecution((ExecutionSpecification)arguments.get(0));
			case SequenceDiagramPackage.MLIFELINE___INSERT_EXECUTION_AFTER__MELEMENT_INT_INT_ELEMENT:
				return insertExecutionAfter((MElement<?>)arguments.get(0), (Integer)arguments.get(1),
						(Integer)arguments.get(2), (Element)arguments.get(3));
			case SequenceDiagramPackage.MLIFELINE___INSERT_EXECUTION_AFTER__MELEMENT_INT_INT_ECLASS:
				return insertExecutionAfter((MElement<?>)arguments.get(0), (Integer)arguments.get(1),
						(Integer)arguments.get(2), (EClass)arguments.get(3));
			case SequenceDiagramPackage.MLIFELINE___INSERT_MESSAGE_AFTER__MELEMENT_INT_MLIFELINE_MESSAGESORT_NAMEDELEMENT:
				return insertMessageAfter((MElement<?>)arguments.get(0), (Integer)arguments.get(1),
						(MLifeline)arguments.get(2), (MessageSort)arguments.get(3),
						(NamedElement)arguments.get(4));
			case SequenceDiagramPackage.MLIFELINE___INSERT_MESSAGE_AFTER__MELEMENT_INT_MLIFELINE_MELEMENT_INT_MESSAGESORT_NAMEDELEMENT:
				return insertMessageAfter((MElement<?>)arguments.get(0), (Integer)arguments.get(1),
						(MLifeline)arguments.get(2), (MElement<?>)arguments.get(3), (Integer)arguments.get(4),
						(MessageSort)arguments.get(5), (NamedElement)arguments.get(6));
			case SequenceDiagramPackage.MLIFELINE___ELEMENT_AT__INT:
				return elementAt((Integer)arguments.get(0));
			case SequenceDiagramPackage.MLIFELINE___NUDGE_HORIZONTALLY__INT:
				return nudgeHorizontally((Integer)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	MExecutionImpl addExecution(ExecutionSpecification execution) {
		return getExecution(execution).map(MExecutionImpl.class::cast)
				.orElseGet(() -> addExecution(new MExecutionImpl(this, execution)));
	}

	MExecutionImpl addExecution(MExecutionImpl execution) {
		((InternalEList<MExecution>)getExecutions()).addUnique(execution);
		return execution;
	}

	@Override
	public Optional<MExecution> getExecution(ExecutionSpecification execution) {
		return getElement(execution, getExecutions());
	}

	MExecutionOccurrenceImpl addExecutionOccurrence(ExecutionOccurrenceSpecification occurrence) {
		return getExecutionOccurrence(occurrence).map(MExecutionOccurrenceImpl.class::cast)
				.orElseGet(() -> addExecutionOccurrence(new MExecutionOccurrenceImpl(this, occurrence)));
	}

	MExecutionOccurrenceImpl addExecutionOccurrence(MExecutionOccurrenceImpl occurrence) {
		((InternalEList<MExecutionOccurrence>)getExecutionOccurrences()).addUnique(occurrence);
		return occurrence;
	}

	@Override
	public Optional<MExecutionOccurrence> getExecutionOccurrence(
			ExecutionOccurrenceSpecification occurrence) {
		return getElement(occurrence, getExecutionOccurrences());
	}

} // MLifelineImpl
