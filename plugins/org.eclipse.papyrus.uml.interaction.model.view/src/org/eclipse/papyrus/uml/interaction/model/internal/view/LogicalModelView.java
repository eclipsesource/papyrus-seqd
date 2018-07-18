/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Antonio Campesino - Split the class in a generic class (PapyrusPageBookView) 
 *                       and specific class.
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.model.internal.view;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;

/**
 * A view presenting the logical model in the (last) selected lightweight
 * sequence diagram.
 * 
 * @author Christian W. Damus - Original implementation
 * @author Antonio Campesino - Split the class in a generic class (PapyrusPageBookView) and specific class. 
 *
 */
@SuppressWarnings("restriction")
public class LogicalModelView extends PapyrusPageBookView {
	@Override
	protected final IPageBookViewPage doCreateSubPage(UmlGmfDiagramEditor part) {
		return new LogicalModelPage(part);
	}

	@Override
	protected final void doDestroySubPage(UmlGmfDiagramEditor part, IPage page) {
		page.dispose();
	}

	protected boolean isSubpageImportant(UmlGmfDiagramEditor editor, Diagram diagram) {
		return ViewTypes.LIGHTWEIGHT_SEQUENCE_DIAGRAM.equals(diagram.getType());
	}

}
