/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.AbstractSequenceGraphicalNodeEditPolicy.StartMessageCommand;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.CreateRequestSwitch;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * The {@code LifelineHeaderGraphicalNodeEditPolicy} type.
 *
 * @author Johannes Faltermeier
 */
public class LifelineHeaderGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy implements ISequenceEditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		return new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateConnectionViewRequest(
					@SuppressWarnings("hiding") CreateConnectionViewRequest request) {

				StartMessageCommand start = (StartMessageCommand)request.getStartCommand();
				org.eclipse.emf.common.command.Command result;

				MLifeline sender = start.sender();
				MInteraction interaction = sender.getInteraction();
				MLifeline receiver = interaction.getLifeline(getHost().getAdapter(Lifeline.class)).get();

				switch (start.sort()) {
					case CREATE_MESSAGE_LITERAL:
						result = sender.insertMessageAfter(start.before().orElse(sender), start.offset(),
								receiver, start.sort(), null);
						break;
					default:
						result = UnexecutableCommand.INSTANCE;
						break;
				}

				return wrap(result);
			}
		}.doSwitch(request);
	}

	/**
	 * Lifeline heads can never be the source of a message or general ordering.
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		EObject semanticElement = null;

		if (request.getConnectionEditPart() instanceof IGraphicalEditPart) {
			semanticElement = ((IGraphicalEditPart)request.getConnectionEditPart()).resolveSemanticElement();
		}

		if (semanticElement == null) {
			return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
		}

		return new UMLSwitch<Command>() {
			@Override
			public Command caseMessage(Message object) {
				return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
			}

			@Override
			public Command caseGeneralOrdering(GeneralOrdering object) {
				return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
			}

			@Override
			public Command defaultCase(EObject object) {
				return LifelineHeaderGraphicalNodeEditPolicy.super.getReconnectSourceCommand(request);
			}
		}.doSwitch(semanticElement);
	}

	/**
	 * Lifeline heads can never be the target of a general ordering or a message that isn't a create message.
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		EObject semanticElement = null;

		if (request.getConnectionEditPart() instanceof IGraphicalEditPart) {
			semanticElement = ((IGraphicalEditPart)request.getConnectionEditPart()).resolveSemanticElement();
		}

		if (semanticElement == null) {
			return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
		}

		return new UMLSwitch<Command>() {
			@Override
			public Command caseMessage(Message object) {
				if (object.getMessageSort() != MessageSort.CREATE_MESSAGE_LITERAL) {
					return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
				}

				return null; // Up to the default case
			}

			@Override
			public Command caseGeneralOrdering(GeneralOrdering object) {
				return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
			}

			@Override
			public Command defaultCase(EObject object) {
				return LifelineHeaderGraphicalNodeEditPolicy.super.getReconnectTargetCommand(request);
			}
		}.doSwitch(semanticElement);
	}
}
