/*****************************************************************************
 * (c) Copyright 2016 Telefonaktiebolaget LM Ericsson
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Antonio Campesino (Ericsson) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.model.internal.view;

import org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;

public class GMFEditPartView extends GMFExplorerView {

	@Override
	protected IPageBookViewPage doCreateSubPage(UmlGmfDiagramEditor diagramEditor) {
		return new GMFEditPartPage(diagramEditor);
	}

	@Override
	protected void doDestroySubPage(UmlGmfDiagramEditor part, IPage page) {
		page.dispose();
	}

}
