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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.RepresentationKind;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.factories.SequenceDiagramViewFactory;

public class SequenceViewProvider extends CustomAbstractViewProvider {

	public SequenceViewProvider() {
		initDiagramType();
		initGraphicalTypeRegistry();
	}

	protected void initDiagramType() {
		diagramType = RepresentationKind.MODEL_ID;
	}

	protected void initGraphicalTypeRegistry() {
		this.registry = SequenceGraphicalTypeRegistry.getInstance();
	}

	@Override
	protected Class<?> getDiagramViewClass(IAdaptable semanticAdapter, String diagramKind) {
		return SequenceDiagramViewFactory.class;
	}

	@Override
	protected boolean provides(CreateNodeViewOperation operation) {
		return super.provides(operation);
	}
}
