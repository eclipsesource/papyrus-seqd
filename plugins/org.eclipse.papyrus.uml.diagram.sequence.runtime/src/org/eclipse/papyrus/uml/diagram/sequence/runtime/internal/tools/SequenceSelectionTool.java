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
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools;

import org.eclipse.gmf.runtime.diagram.ui.services.palette.SelectionToolEx;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;

/**
 * Custom selection tool that integrates with the {@linkplain IMagnetManager magnet manager}.
 */
public class SequenceSelectionTool extends SelectionToolEx {

	private final MagnetSupport magnetSupport = new MagnetSupport(this::getCurrentViewer,
			super::getCurrentInput);

	/**
	 * Initializes me.
	 */
	public SequenceSelectionTool() {
		super();
	}

	@Override
	public void deactivate() {
		magnetSupport.deactivate();

		super.deactivate();
	}

	@Override
	protected Input getCurrentInput() {
		return magnetSupport.getCurrentInput();
	}

}
