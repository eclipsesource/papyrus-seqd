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

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;

public class SequenceEditPartProvider extends AbstractEditPartProvider {

	@Override
	protected Class<? extends IGraphicalEditPart> getDiagramEditPartClass(View view) {
		if (ViewTypes.LIGHTWEIGHT_SEQUENCE_DIAGRAM.equals(view.getType())) {
			return LightweightSequenceDiagramEditPart.class;
		} else if (ViewTypes.SEQUENCE_DIAGRAM.equals(view.getType())) {
			return LightweightSequenceDiagramEditPart.class;
		}
		return null;
	}

	@Override
	protected Class<? extends IGraphicalEditPart> getEdgeEditPartClass(View view) {
		switch (view.getType()) {
			case ViewTypes.MESSAGE:
				return MessageEditPart.class;
			case ViewTypes.GENERAL_ORDERING:
				return GeneralOrderingEditPart.class;
		}

		return null;
	}

	@Override
	protected Class<? extends IGraphicalEditPart> getNodeEditPartClass(View view) {
		switch (view.getType()) {
			case ViewTypes.INTERACTION:
				return InteractionEditPart.class;
			case ViewTypes.LIFELINE_HEADER:
				return LifelineHeaderEditPart.class;
			case ViewTypes.LIFELINE_HEADER_COMPARTMENT:
				return LifelineHeaderCompartmentEditPart.class;
			case ViewTypes.LIFELINE_BODY:
				return LifelineBodyEditPart.class;
			case ViewTypes.LIFELINE_NAME:
				return LifelineNameEditPart.class;
			case ViewTypes.INTERACTION_NAME:
				return InteractionNameEditPart.class;
			case ViewTypes.INTERACTION_CONTENTS:
				return InteractionCompartmentEditPart.class;
			case ViewTypes.EXECUTION_SPECIFICATION:
				return ExecutionSpecificationEditPart.class;
			case ViewTypes.STATE_INVARIANT:
				return StateInvariantEditPart.class;
			case RepresentationKind.DESTRUCTION_SPECIFICATION_ID:
				return DestructionSpecificationEditPart.class;
		}

		return null;
	}

}
