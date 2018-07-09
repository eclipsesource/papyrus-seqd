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
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
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
		knownNodes.add(ViewTypes.INTERACTION);
		knownNodes.add(ViewTypes.LIFELINE_HEADER);
		knownNodes.add(ViewTypes.LIFELINE_HEADER_COMPARTMENT);
		knownNodes.add(ViewTypes.LIFELINE_NAME);
		knownNodes.add(ViewTypes.LIFELINE_BODY);

		knownEdges.add(ViewTypes.MESSAGE);
	}

	@Override
	public String getNodeGraphicalType(EObject domainElement, final String containerType) {
		UMLSwitch<String> t = new UMLSwitch<String>() {
			@Override
			public String caseInteraction(Interaction object) {
				return ViewTypes.INTERACTION;
			}

			@Override
			public String caseLifeline(Lifeline object) {
				return ViewTypes.LIFELINE_HEADER;
			}

			@Override
			public String caseMessage(Message object) {
				return ViewTypes.MESSAGE;
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
			if (ViewTypes.LIGHTWEIGHT_SEQUENCE_DIAGRAM.equals(view.getType())) {
				return ViewTypes.SEQUENCE_DIAGRAM;
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
