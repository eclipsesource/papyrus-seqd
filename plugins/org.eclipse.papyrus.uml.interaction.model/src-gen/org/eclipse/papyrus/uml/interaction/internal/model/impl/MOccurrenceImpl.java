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

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.papyrus.uml.interaction.graph.GraphPredicates;
import org.eclipse.papyrus.uml.interaction.graph.Tag;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MOccurrence</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl#getCovered
 * <em>Covered</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl#getStartedExecution
 * <em>Started Execution</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl#getFinishedExecution
 * <em>Finished Execution</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MOccurrenceImpl<T extends Element> extends MElementImpl<T> implements MOccurrence<T> {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MOccurrenceImpl() {
		super();
	}

	protected MOccurrenceImpl(MElement<?> owner, T element) {
		super(owner, element);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MOCCURRENCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MLifeline> getCovered() {
		Optional<Lifeline> result = getFragment()
				.map(f -> f.getCovereds().isEmpty() ? null : f.getCovereds().get(0));
		return result.flatMap(lifeline -> getInteraction().getLifeline(lifeline));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MExecution> getStartedExecution() {
		return getExecution(Tag.EXECUTION_START);
	}

	private Optional<MExecution> getExecution(Tag tag) {
		Function<Vertex, Optional<Vertex>> tagFunction = tag == Tag.EXECUTION_START
				? vtx -> vtx.successor(tag)
				: vtx -> vtx.predecessor(tag);
		return getVertex().filter(GraphPredicates.hasTag(tag)).flatMap(tagFunction)
				.map(Vertex::getInteractionElement).filter(ExecutionSpecification.class::isInstance)
				.map(ExecutionSpecification.class::cast)
				.flatMap(exec -> getCovered().flatMap(ll -> ll.getExecution(exec)));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MExecution> getFinishedExecution() {
		return getExecution(Tag.EXECUTION_FINISH);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SequenceDiagramPackage.MOCCURRENCE__COVERED:
				return getCovered();
			case SequenceDiagramPackage.MOCCURRENCE__STARTED_EXECUTION:
				return getStartedExecution();
			case SequenceDiagramPackage.MOCCURRENCE__FINISHED_EXECUTION:
				return getFinishedExecution();
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
			case SequenceDiagramPackage.MOCCURRENCE__COVERED:
				return getCovered() != null;
			case SequenceDiagramPackage.MOCCURRENCE__STARTED_EXECUTION:
				return getStartedExecution() != null;
			case SequenceDiagramPackage.MOCCURRENCE__FINISHED_EXECUTION:
				return getFinishedExecution() != null;
		}
		return super.eIsSet(featureID);
	}

	protected Optional<IdentityAnchor> getAnchor(OccurrenceSpecification occurrence) {
		// Any general ordering will do because they all have to anchor at the same point,
		// as an occurrence specification is a point element in the diagram which location
		// has real meaning and is unique
		Optional<IdentityAnchor> result = Optional.empty();

		if (!occurrence.getToBefores().isEmpty()) {
			result = occurrence.getToBefores().stream().map(this::getTargetAnchor).findAny();
		} else if (!occurrence.getToAfters().isEmpty()) {
			result = occurrence.getToAfters().stream().map(this::getSourceAnchor).findAny();
		}

		return result;
	}

} // MOccurrenceImpl
