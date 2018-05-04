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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.part;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.uml.diagram.common.commands.CreateBehavioredClassifierDiagramCommand;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.RepresentationKind;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Define a command to create a new Sequence Diagram. This command is used by all UI (toolbar, outline,
 * creation wizards) to create a new Sequence Diagram.
 */
public class CreateLightweightSequenceDiagramCommand extends CreateBehavioredClassifierDiagramCommand {

	@Override
	protected String getDiagramNotationID() {
		return RepresentationKind.MODEL_ID;
	}

	@Override
	protected PreferencesHint getPreferenceHint() {
		return Activator.DIAGRAM_PREFERENCES_HINT;
	}

	@Override
	protected String getDefaultDiagramName() {
		return "LightweightSeq"; //$NON-NLS-1$
	}

	@Override
	protected EClass getBehaviorEClass() {
		return UMLPackage.eINSTANCE.getInteraction();
	}

	@Override
	protected void initializeDiagram(EObject object) {
		super.initializeDiagram(object);

		if (object instanceof Diagram) {
			Diagram diagram = (Diagram)object;
			// Make it "owned" by the interaction
			DiagramUtils.setOwner(diagram, diagram.getElement());
		}
	}
}
