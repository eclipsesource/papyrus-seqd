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

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.GraphicalTypeRegistry;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.RepresentationKind;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class SequenceGraphicalTypeRegistry extends GraphicalTypeRegistry {

	private static SequenceGraphicalTypeRegistry INSTANCE;

	public static final synchronized SequenceGraphicalTypeRegistry getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SequenceGraphicalTypeRegistry();
		}
		return INSTANCE;
	}

	private SequenceGraphicalTypeRegistry() {
		knownNodes.add(RepresentationKind.INTERACTION_ID);
		knownNodes.add(RepresentationKind.LIFELINE_ID);
		knownNodes.add(RepresentationKind.LIFELINE_HEADER_COMPARMENT_ID);

		knownEdges.add(RepresentationKind.MESSAGE_ID);
	}

	@Override
	public String getNodeGraphicalType(EObject domainElement, final String containerType) {
		UMLSwitch<String> t = new UMLSwitch<String>() {
			@Override
			public String caseInteraction(Interaction object) {
				return RepresentationKind.INTERACTION_ID;
			}

			@Override
			public String caseLifeline(Lifeline object) {
				return RepresentationKind.LIFELINE_ID;
			}

			@Override
			public String caseMessage(Message object) {
				return RepresentationKind.MESSAGE_ID;
			}

			@Override
			public String defaultCase(EObject object) {
				return UNDEFINED_TYPE;
			}
		};
		return t.doSwitch(domainElement);
	}

	public static final String getVisualID(String visualID) {
		return visualID;
	}

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

	public static Object getModelID(View view) {
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

}
