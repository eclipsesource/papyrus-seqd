/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.service.visualtype.AbstractVisualTypeProvider;

public class SequenceVisualTypeProvider extends AbstractVisualTypeProvider {

	private SequenceGraphicalTypeRegistry registry;

	public SequenceVisualTypeProvider() {
		super();
		registry = SequenceGraphicalTypeRegistry.getInstance();
	}

	@Override
	public IElementType getElementType(Diagram diagram, String viewType) {
		IElementType result = null;

		try {
			result = SequenceElementTypes.getElementType(viewType);
		} catch (NumberFormatException e) {
			// Not supported by this diagram
		}

		return result;
	}

	@Override
	public String getNodeType(View parentView, EObject element) {
		String parentVID = SequenceGraphicalTypeRegistry.getVisualID(parentView);
		return registry.getNodeGraphicalType(element, parentVID);
	}

	@Override
	public String getLinkType(Diagram diagram, EObject element) {
		return registry.getEdgeGraphicalType(element);
	}
}
