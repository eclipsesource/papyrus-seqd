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

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.CreateRequestSwitch;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.SequenceTypeSwitch;

/**
 * {@link SemanticEditPolicy} that delegates to the Interaction logical model to compute commands.
 */
public class InteractionSemanticEditPolicy extends LogicalModelElementSemanticEditPolicy {

	@Override
	protected Command getSemanticCommand(IEditCommandRequest request) {
		// TODO should not answer to anything except message reorient => delegated to logical model (or
		// graphical reorient?)

		@SuppressWarnings("hiding")
		Command result = new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateRelationshipRequest(CreateRelationshipRequest request) {
				return new SequenceTypeSwitch<Command>() {
					@Override
					public Command caseMessage(IElementType type) {
						if (request.getTarget() == null) {
							// Starting from the source. Easy
							return new ICommandProxy(IdentityCommand.INSTANCE);
						}

						// We don't need a separate semantic command that does anything
						// because the connection completion does everything
						return new ICommandProxy(IdentityCommand.INSTANCE);
					}
				}.doSwitch(request.getElementType());
			}

			@Override
			public Command caseEditCommandRequest(IEditCommandRequest request) {
				return InteractionSemanticEditPolicy.super.getSemanticCommand(request);
			}

		}.doSwitch(request);

		return result;
	}

}
