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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

/**
 * {@link SemanticEditPolicy} that delegates to the Interaction logical model to compute commands.
 */
public class InteractionSemanticEditPolicy extends SemanticEditPolicy {

	@Override
	protected Command getSemanticCommand(IEditCommandRequest request) {
		// TODO should not answer to anything except message reorient => delegated to logical model (or
		// graphical reorient?)
		return null;
	}

	@Override
	public Command getCommand(Request request) {
		return super.getCommand(request);
	}
}
