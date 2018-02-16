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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Edge;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.common.util.UML2Util.QualifiedTextProvider;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Default implementation of a vertex in the dependency graph.
 *
 * @author Christian W. Damus
 */
public class VertexImpl implements Vertex {

	private static QualifiedTextProvider TEXT_PROVIDER = new VertexTextProvider();

	private final List<Edge> incoming = new ArrayList<>(3);

	private final List<Edge> outgoing = new ArrayList<>(3);

	private final Element interactionElement;

	private final View diagramView;

	/**
	 * Initializes me.
	 */
	VertexImpl(Element interactionElement, View diagramView) {
		super();

		this.interactionElement = interactionElement;
		this.diagramView = diagramView;

		if ((this.interactionElement == null) && (this.diagramView == null)) {
			throw new IllegalArgumentException("vertex must represent something"); //$NON-NLS-1$
		}
	}

	/**
	 * Initializes me.
	 */
	VertexImpl(Element interactionElement) {
		this(Objects.requireNonNull(interactionElement), null);
	}

	/**
	 * Initializes me.
	 */
	VertexImpl(View diagramView) {
		this(null, Objects.requireNonNull(diagramView));
	}

	@Override
	public List<Edge> incoming() {
		return Collections.unmodifiableList(this.incoming);
	}

	@Override
	public List<Edge> outgoing() {
		return Collections.unmodifiableList(this.outgoing);
	}

	@Override
	public Element getInteractionElement() {
		return interactionElement;
	}

	@Override
	public View getDiagramView() {
		return diagramView;
	}

	void addIncoming(EdgeImpl edge) {
		incoming.add(edge);
	}

	void removeIncoming(EdgeImpl edge) {
		incoming.remove(edge);
	}

	void addOutgoing(EdgeImpl edge) {
		outgoing.add(edge);
	}

	void removeOutgoing(EdgeImpl edge) {
		outgoing.remove(edge);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((diagramView == null) ? 0 : diagramView.hashCode());
		result = (prime * result) + ((interactionElement == null) ? 0 : interactionElement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Vertex)) {
			return false;
		}
		Vertex other = (Vertex)obj;
		if (diagramView == null) {
			if (other.getDiagramView() != null) {
				return false;
			}
		} else if (!diagramView.equals(other.getDiagramView())) {
			return false;
		}
		if (interactionElement == null) {
			if (other.getInteractionElement() != null) {
				return false;
			}
		} else if (!interactionElement.equals(other.getInteractionElement())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		Element element = getInteractionElement();
		String type = element.eClass().getName();
		String name = UML2Util.getQualifiedText(element, TEXT_PROVIDER);
		if (getDiagramView() == null) {
			return String.format("%s(%s)", type, name); //$NON-NLS-1$
		}
		String viewType = getDiagramView().getType();
		if (viewType.startsWith(type)) {
			viewType = "*" + viewType.substring(type.length()); //$NON-NLS-1$
		}
		return String.format("%s(%s, %s)", type, name, viewType); //$NON-NLS-1$
	}

	//
	// Nested types
	//

	private static final class VertexTextProvider extends UMLUtil.QualifiedTextProvider {

		@Override
		public String getText(EObject eObject) {
			String result = null;

			if (eObject == null) {
				result = "<null>"; //$NON-NLS-1$
			} else {
				if (eObject instanceof View) {
					result = ((View)eObject).getType();
				}

				if (result == null) {
					result = super.getText(eObject);
				}

				if (result == null) {
					result = (eObject instanceof View) ? "<view>" : "<unnamed>"; //$NON-NLS-1$//$NON-NLS-2$
				}
			}

			return result;
		}
	}
}
