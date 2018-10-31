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
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.spannedBy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Stack;
import java.util.function.Predicate;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.InsertNestedExecutionCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.RemoveExecutionCommand;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.SetOwnerCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;
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
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl#getOccurrences
 * <em>Occurrences</em>}</li>
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
	public List<MOccurrence<? extends Element>> getOccurrences() {
		EList<MOccurrence<? extends Element>> result = new UniqueEList.FastCompare<>();

		getOwner().getOccurrences().stream().filter(spannedBy(this)).forEach(result::add);

		getStart().ifPresent(start -> {
			if (!result.contains(start)) {
				result.add(0, start);
			} else {
				result.move(0, start);
			}
		});

		getFinish().ifPresent(finish -> {
			if (!result.contains(finish)) {
				result.add(finish);
			} else {
				result.move(result.size() - 1, finish);
			}
		});

		return ECollections.unmodifiableEList(result);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public MLifelineImpl getOwner() {
		return (MLifelineImpl)super.getOwner();
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
	public CreationCommand<ExecutionSpecification> insertNestedExecutionAfter(MElement<?> before, int offset,
			int height, Element specification) {
		return new InsertNestedExecutionCommand(this, before, offset, height, specification);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public CreationCommand<ExecutionSpecification> insertNestedExecutionAfter(MElement<?> before, int offset,
			int height, EClass metaclass) {
		return new InsertNestedExecutionCommand(this, before, offset, height, metaclass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MElement<? extends Element>> elementAt(int offset) {
		int top = layoutHelper().getTop(getShape(getElement()));
		int absoluteOffset = top + offset;

		Predicate<MElement<?>> isAtOrAbove = e -> e.getBottom().orElse(Integer.MAX_VALUE) <= absoluteOffset;

		Optional<? extends MElement<? extends Element>> result = getVertex().flatMap(vtx -> //
		vtx.successors().sequential().filter(covers(getOwner().getElement())) //
				.map(Vertex::getInteractionElement) //
				.map(getInteraction()::getElement) //
				.filter(Optional::isPresent).map(Optional::get) //
				.filter(isAtOrAbove)//
				.reduce((a, b) -> b)// last element
		);

		return Optional.ofNullable(result.orElse(null));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@SuppressWarnings("boxing")
	@Override
	public List<MExecution> getNestedExecutions() {
		List<MExecution> nestedExecutions = new ArrayList<>();
		// browse all executions on lifeline after the start of this execution and before the end of this
		// execution

		List<MOccurrence<?>> occurences = getOwner().getOccurrenceSpecifications();

		int start = getStart().map(occ -> occurences.indexOf(occ)).orElse(-1);
		int finish = getFinish().map(occ -> occurences.indexOf(occ)).orElse(-1);
		if (start != -1 && finish != -1 && finish - start > 1) {
			List<MOccurrence<?>> executionOccurences = occurences.subList(start, finish);

			Stack<MExecution> stack = new Stack<>();
			for (MOccurrence<?> occurence : executionOccurences) {
				// check if it starts a new occurrence. If yes, adds it on top of the stack
				// if it finishes, removes the finished execution from the stack
				occurence.getFinishedExecution().ifPresent(__ -> {
					if (!stack.isEmpty()) {
						stack.pop();
					}
				});
				if (occurence.getStartedExecution().isPresent()) {
					MExecution execution = occurence.getStartedExecution().get();
					// if current owner => this, then this execution should be added to the nested executions
					if (!stack.empty() && stack.peek() == this) {
						nestedExecutions.add(execution);
					}
					stack.push(execution);
				}
			}
		}
		return nestedExecutions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command setOwner(MLifeline newOwner, OptionalInt yPosition) {
		// Avoid cycling through this execution again
		return withPadding(SetOwnerCommand.class, () -> new SetOwnerCommand(this, newOwner, yPosition));
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
			case SequenceDiagramPackage.MEXECUTION__OCCURRENCES:
				return getOccurrences();
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
			case SequenceDiagramPackage.MEXECUTION__OCCURRENCES:
				return !getOccurrences().isEmpty();
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
			case SequenceDiagramPackage.MEXECUTION___INSERT_NESTED_EXECUTION_AFTER__MELEMENT_INT_INT_ELEMENT:
				return insertNestedExecutionAfter((MElement<?>)arguments.get(0), (Integer)arguments.get(1),
						(Integer)arguments.get(2), (Element)arguments.get(3));
			case SequenceDiagramPackage.MEXECUTION___INSERT_NESTED_EXECUTION_AFTER__MELEMENT_INT_INT_ECLASS:
				return insertNestedExecutionAfter((MElement<?>)arguments.get(0), (Integer)arguments.get(1),
						(Integer)arguments.get(2), (EClass)arguments.get(3));
			case SequenceDiagramPackage.MEXECUTION___ELEMENT_AT__INT:
				return elementAt((Integer)arguments.get(0));
			case SequenceDiagramPackage.MEXECUTION___GET_NESTED_EXECUTIONS:
				return getNestedExecutions();
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
