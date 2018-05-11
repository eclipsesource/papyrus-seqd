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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusConnectionHandleEditPolicy;

/**
 * Custom connection handle edit policy for the lightweight sequence diagram.
 */
public class SequenceDiagramConnectionHandleEditPolicy extends PapyrusConnectionHandleEditPolicy {

	/**
	 * Initializes me.
	 */
	public SequenceDiagramConnectionHandleEditPolicy() {
		super();
	}

}
