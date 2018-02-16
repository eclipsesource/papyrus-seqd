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

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.providers.ValidationDecoratorProvider;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.RepresentationKind;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.VisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.part.LightweightSequenceDiagramEditor;

/**
 * @generated
 */
public class SequenceValidationDecoratorProvider extends ValidationDecoratorProvider {

	/**
	 * @generated
	 */
	@Override
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		EditPart editPart = decoratorTarget.getAdapter(EditPart.class);
		if (editPart instanceof GraphicalEditPart || editPart instanceof AbstractConnectionEditPart) {
			Object model = editPart.getModel();
			if ((model instanceof View)) {
				View view = (View)model;
				if (!(view instanceof Edge) && !view.isSetElement()) {
					return;
				}
			}
			EditDomain ed = editPart.getViewer().getEditDomain();
			if (!(ed instanceof DiagramEditDomain)) {
				return;
			}
			if (((DiagramEditDomain)ed).getEditorPart() instanceof LightweightSequenceDiagramEditor) {
				decoratorTarget.installDecorator(KEY, new StatusDecorator(decoratorTarget));
			}
		}
	}

	/**
	 * @generated
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (!(operation instanceof CreateDecoratorsOperation)) {
			return false;
		}
		IDecoratorTarget decoratorTarget = ((CreateDecoratorsOperation)operation).getDecoratorTarget();
		View view = decoratorTarget.getAdapter(View.class);
		return view != null && RepresentationKind.MODEL_ID.equals(VisualIDRegistry.getModelID(view));
	}
}
