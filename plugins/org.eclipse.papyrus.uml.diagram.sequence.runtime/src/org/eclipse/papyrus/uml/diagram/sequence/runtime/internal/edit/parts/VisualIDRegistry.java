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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.GraphicalTypeRegistry;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @deprecated use {@link GraphicalTypeRegistry} instead.
 */
@Deprecated
public class VisualIDRegistry {

	public static String getVisualID(View view) {
		if (view instanceof Diagram) {
			if (RepresentationKind.MODEL_ID.equals(view.getType())) {
				return RepresentationKind.DIAGRAM_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		return getVisualID(view.getType());
	}

	public static String getModelID(View view) {
		View containerView = view;
		View diagram = containerView.getDiagram();
		while (containerView != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			containerView = (View)containerView.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	public static String getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		return RepresentationKind.DIAGRAM_ID;
	}

	public static String getVisualID(String type) {
		return type;
	}

	public static String getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		String containerModelID = VisualIDRegistry.getModelID(containerView);
		if (!RepresentationKind.MODEL_ID.equals(containerModelID)) {
			return ""; //$NON-NLS-1$
		}
		String containerVisualID;
		if (RepresentationKind.MODEL_ID.equals(containerModelID)) {
			containerVisualID = VisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = RepresentationKind.DIAGRAM_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
				case RepresentationKind.DIAGRAM_ID:
					if (UMLPackage.eINSTANCE.getInteraction().isSuperTypeOf(domainElement.eClass())) {
						return RepresentationKind.INTERACTION_ID;
					}
					break;
				default:
					return ""; //$NON-NLS-1$
			}
		}
		return ""; //$NON-NLS-1$
	}

	public static String getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass())) {
			MessageSort sort = ((Message)domainElement).getMessageSort();
			switch (sort) {
				case ASYNCH_CALL_LITERAL:
					return RepresentationKind.ASYNC_MESSAGE_ID;
				default:
					return RepresentationKind.ASYNC_MESSAGE_ID;
			}
		}
		return ""; //$NON-NLS-1$
	}
}
