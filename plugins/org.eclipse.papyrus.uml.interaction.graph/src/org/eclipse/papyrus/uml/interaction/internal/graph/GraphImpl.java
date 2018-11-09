/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.interaction.internal.graph;

import static java.lang.System.identityHashCode;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.GroupKind;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Default implementation of the dependency graph.
 *
 * @author Christian W. Damus
 */
public class GraphImpl implements Graph {

	private final Map<Element, VertexImpl> vertices = new ConcurrentHashMap<>();

	private final VertexImpl initial;

	private final Function<Element, VertexImpl> vertexFactory;

	private Comparator<Vertex> interactionOrdering;

	/**
	 * Initializes me.
	 * 
	 * @param interaction
	 *            the interaction model (required)
	 * @param sequenceDiagram
	 *            an optional sequence diagram on the {@code interaction}
	 * @throws NullPointerException
	 *             if the {@code interaction} is {@code null}
	 */
	GraphImpl(Interaction interaction, Diagram sequenceDiagram) {
		super();

		this.initial = new VertexImpl(this, requireNonNull(interaction), sequenceDiagram);
		vertices.put(interaction, initial);

		if (sequenceDiagram == null) {
			vertexFactory = new VertexFactory()::doSwitch;
		} else {
			vertexFactory = new VertexFactoryWithDiagram(sequenceDiagram)::doSwitch;
		}

		new GraphComputer(this).compute();
	}

	/**
	 * Gets the graph of the given {@code interaction}, optionally accounting for a diagram.
	 * 
	 * @param interaction
	 *            the interaction model (required)
	 * @param sequenceDiagram
	 *            an optional sequence diagram on the {@code interaction}
	 * @throws NullPointerException
	 *             if the {@code interaction} is {@code null}
	 */
	public static GraphImpl getInstance(Interaction interaction, Diagram sequenceDiagram) {
		CacheAdapter cache = CacheAdapter.getCacheAdapter(interaction);
		CacheKey key = new CacheKey(sequenceDiagram);

		GraphImpl result = (GraphImpl)cache.get(interaction, key);
		if (result == null) {
			result = new GraphImpl(interaction, sequenceDiagram);
			cache.put(interaction, key, result);
		}

		return result;
	}

	/**
	 * Create an incremental updater of the given {@code graph} that accepts new elements created in a
	 * specific {@code context}.
	 * 
	 * @param graph
	 *            an existing graph
	 * @param context
	 *            an element within the {@code graph} in which context some new element(s) have been created
	 * @return the updater that accepts the new elements
	 */
	public static Consumer<Element> update(Graph graph, Element context) {
		return new GraphComputer((GraphImpl)graph).createUpdater(context);
	}

	@Override
	public VertexImpl initial() {
		return initial;
	}

	@Override
	public final VertexImpl vertex(Element element) {
		return vertices.computeIfAbsent(element, vertexFactory);
	}

	@Override
	public Comparator<Vertex> interactionOrdering() {
		return interactionOrdering;
	}

	void setInteractionOrdering(Comparator<Vertex> ordering) {
		this.interactionOrdering = ordering;
	}

	/**
	 * Obtains the visualization of an {@code element} in the context of a {@code diagram}.
	 * 
	 * @param element
	 *            an interaction element
	 * @param diagram
	 *            a sequence diagram
	 * @return the possible iew of the {@code element} in the {@code diagram}
	 */
	protected Optional<View> getViewOf(Element element, Diagram diagram) {
		// The View::element reference doesn't have an opposite, so this is more efficient
		return CacheAdapter.getCacheAdapter(element).getNonNavigableInverseReferences(element).stream()
				.filter(setting -> setting.getEStructuralFeature() == NotationPackage.Literals.VIEW__ELEMENT)
				.map(EStructuralFeature.Setting::getEObject).map(View.class::cast)
				.filter(view -> view.getDiagram() == diagram).findAny();
	}

	//
	// Nested types
	//

	private static final class CacheKey {
		private final Diagram sequenceDiagram;

		CacheKey(Diagram sequenceDiagram) {
			super();

			this.sequenceDiagram = sequenceDiagram;
		}

		@Override
		public int hashCode() {
			return sequenceDiagram == null ? 0 : identityHashCode(sequenceDiagram);
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof CacheKey && ((CacheKey)obj).sequenceDiagram == this.sequenceDiagram;
		}
	}

	private class VertexFactory extends UMLSwitch<VertexImpl> {

		VertexFactory() {
			super();
		}

		@Override
		public VertexImpl caseLifeline(Lifeline lifeline) {
			return new VertexGroupImpl(GraphImpl.this, lifeline).setKind(GroupKind.LIFELINE);
		}

		@Override
		public VertexImpl caseExecutionSpecification(ExecutionSpecification execution) {
			return new VertexGroupImpl(GraphImpl.this, execution).setKind(GroupKind.EXECUTION);
		}

		@Override
		public VertexImpl caseCombinedFragment(CombinedFragment combined) {
			return new VertexGroupImpl(GraphImpl.this, combined).setKind(GroupKind.COMBINED_FRAGMENT);
		}

		@Override
		public VertexImpl caseInteractionOperand(InteractionOperand operand) {
			return new VertexGroupImpl(GraphImpl.this, operand).setKind(GroupKind.OPERAND);
		}

		@Override
		public VertexImpl caseElement(Element element) {
			return new VertexImpl(GraphImpl.this, element);
		}
	}

	private class VertexFactoryWithDiagram extends UMLSwitch<VertexImpl> {
		private final Diagram sequenceDiagram;

		VertexFactoryWithDiagram(Diagram sequenceDiagram) {
			super();

			this.sequenceDiagram = sequenceDiagram;
		}

		@Override
		public VertexImpl caseLifeline(Lifeline lifeline) {
			return new VertexGroupImpl(GraphImpl.this, lifeline, view(lifeline)).setKind(GroupKind.LIFELINE);
		}

		@Override
		public VertexImpl caseExecutionSpecification(ExecutionSpecification execution) {
			return new VertexGroupImpl(GraphImpl.this, execution, view(execution))
					.setKind(GroupKind.EXECUTION);
		}

		@Override
		public VertexImpl caseCombinedFragment(CombinedFragment combined) {
			return new VertexGroupImpl(GraphImpl.this, combined, view(combined))
					.setKind(GroupKind.COMBINED_FRAGMENT);
		}

		@Override
		public VertexImpl caseInteractionOperand(InteractionOperand operand) {
			return new VertexGroupImpl(GraphImpl.this, operand, view(operand)).setKind(GroupKind.OPERAND);
		}

		@Override
		public VertexImpl caseElement(Element element) {
			return new VertexImpl(GraphImpl.this, element, view(element));
		}

		private View view(Element element) {
			return getViewOf(element, sequenceDiagram).orElse(null);
		}
	}
}
