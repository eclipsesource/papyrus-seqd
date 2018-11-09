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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.PrivateToolUtils.getAllowSemanticReorderingModifier;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.gef.ui.internal.tools.SelectConnectionEditPartTracker;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.PrivateRequestUtils;

/**
 * Specialized selection tracker for connections in the <em>Lightweight Sequence Diagram</em>.
 */
@SuppressWarnings("restriction")
public class SequenceConnectionSelectionTracker extends SelectConnectionEditPartTracker {

	private Request sourceRequest;

	/**
	 * Initializes me.
	 *
	 * @param owner
	 */
	public SequenceConnectionSelectionTracker(ConnectionEditPart owner) {
		super(owner);
	}

	@Override
	protected Request createSourceRequest() {
		// The superclass provides an accessor only for the target request
		sourceRequest = super.createSourceRequest();
		return sourceRequest;
	}

	@Override
	protected void updateSourceRequest() {
		super.updateSourceRequest();

		// All requests of interest for re-ordering have location

		PrivateRequestUtils.setAllowSemanticReordering(sourceRequest,
				getCurrentInput().isModKeyDown(getAllowSemanticReorderingModifier()));
	}

}
