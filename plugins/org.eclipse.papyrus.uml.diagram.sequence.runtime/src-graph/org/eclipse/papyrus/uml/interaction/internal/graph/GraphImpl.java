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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;

/**
 * Default implementation of the dependency graph.
 *
 * @author Christian W. Damus
 */
public class GraphImpl implements Graph {

	private final Map<Element, VertexImpl> vertices = new ConcurrentHashMap<>();

	private final VertexImpl initial;

	private final Function<Element, VertexImpl> vertexFactory;

	/**
	 * Initializes me.
	 */
	public GraphImpl(Interaction interaction, Diagram sequenceDiagram) {
		super();

		this.initial = new VertexImpl(interaction, sequenceDiagram);
		vertices.put(interaction, initial);

		if (sequenceDiagram == null) {
			vertexFactory = VertexImpl::new;
		} else {
			vertexFactory = element -> new VertexImpl(element,
					getViewOf(element, sequenceDiagram).orElse(null));
		}

		new GraphComputer(this).compute();
	}

	@Override
	public VertexImpl initial() {
		return initial;
	}

	@Override
	public final VertexImpl vertex(Element element) {
		return vertices.computeIfAbsent(element, vertexFactory);
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
}
