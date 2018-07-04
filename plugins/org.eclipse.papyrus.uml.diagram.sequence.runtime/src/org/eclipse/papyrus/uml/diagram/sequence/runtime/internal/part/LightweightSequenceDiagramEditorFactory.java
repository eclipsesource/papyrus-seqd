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

import org.eclipse.papyrus.infra.gmfdiag.common.GmfEditorFactory;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper.ViewTypes;

/**
 * The editor factory to launch the sequence diagram.
 */
public class LightweightSequenceDiagramEditorFactory extends GmfEditorFactory {

	/**
	 * Instantiates a new sequence diagram editor factory.
	 */
	public LightweightSequenceDiagramEditorFactory() {
		super(LightweightSequenceDiagramEditor.class, ViewTypes.LIGHTWEIGHT_SEQUENCE_DIAGRAM);
	}
}
