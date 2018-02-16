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
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.VisualIDRegistry;

public class SequenceVisualTypeProvider extends AbstractVisualTypeProvider {

	/**
	 * @generated
	 */
	public SequenceVisualTypeProvider() {
		super();
	}

	/**
	 * @generated
	 */
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

	/**
	 * @generated
	 */
	@Override
	public String getNodeType(View parentView, EObject element) {
		return VisualIDRegistry.getNodeVisualID(parentView, element);
	}

	/**
	 * @generated
	 */
	@Override
	public String getLinkType(Diagram diagram, EObject element) {
		return VisualIDRegistry.getLinkWithClassVisualID(element);
	}
}
