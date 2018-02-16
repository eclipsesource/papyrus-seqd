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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.factories;

import org.eclipse.gmf.runtime.diagram.ui.view.factories.optimal.StandardDiagramViewFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;

public class SequenceDiagramViewFactory extends StandardDiagramViewFactory {

	@Override
	protected MeasurementUnit getMeasurementUnit() {
		return MeasurementUnit.PIXEL_LITERAL;
	}

	@Override
	protected Diagram createDiagramView() {
		Diagram diagram = super.createDiagramView();
		DiagramVersioningUtils.stampCurrentVersion(diagram);
		return diagram;
	}

}
