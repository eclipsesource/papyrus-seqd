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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.getSort;

import java.util.Optional;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.CreateRequestSwitch;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;

/**
 * This is the {@code LifelineBodyGraphicalNodeEditPolicy} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class LifelineBodyGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy implements ISequenceEditPolicy {

	/**
	 * Initializes me.
	 */
	public LifelineBodyGraphicalNodeEditPolicy() {
		super();
	}

	@Override
	@SuppressWarnings("hiding")
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		return new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateConnectionViewRequest(CreateConnectionViewRequest request) {
				Diagram diagram = ((View)getHost().getModel()).getDiagram();
				Lifeline lifeline = getHost().getAdapter(Lifeline.class);
				Interaction interaction = lifeline.getInteraction();
				MLifeline mLifeline = MInteraction.getInstance(interaction, diagram).getLifeline(lifeline)
						.get();

				IElementType messageType = request.getConnectionViewDescriptor().getElementAdapter()
						.getAdapter(IElementType.class);
				Point location = request.getLocation();
				Optional<MElement<?>> before = mLifeline.elementAt(location.y());

				IFigure lifelineBodyFigure = getHostFigure();

				Point locationToBody = location.getCopy();
				lifelineBodyFigure.translateToRelative(locationToBody);
				locationToBody.translate(lifelineBodyFigure.getBounds().getTop().getNegated());

				int offset = locationToBody.y();

				if (before.isPresent()) {
					// We know the top exists because that's how we found the 'before' element
					offset = offset - before.get().getTop().getAsInt();
				} else {
					// It will be relative to the lifeline head
					offset = offset - getLayoutHelper().getBottom(mLifeline.getDiagramView().get());
				}

				Command result = new StartMessageCommand(mLifeline, before, offset, getSort(messageType));
				request.setStartCommand(result);
				return result;
			}
		}.doSwitch(request);
	}

	@Override
	@SuppressWarnings("hiding")
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		return new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateConnectionViewRequest(CreateConnectionViewRequest request) {
				StartMessageCommand start = (StartMessageCommand)request.getStartCommand();
				MLifeline sender = start.sender;
				MInteraction interaction = sender.getInteraction();
				MLifeline receiver = interaction.getLifeline(getHost().getAdapter(Lifeline.class)).get();

				CreationCommand<Message> result = sender.insertMessageAfter(start.before.orElse(sender),
						start.offset, receiver, start.sort, null);

				return wrap(result);
			}
		}.doSwitch(request);
	}

	//
	// Nested types
	//

	private static class StartMessageCommand extends Command {
		private final MLifeline sender;

		private final Optional<MElement<?>> before;

		private final int offset;

		private final MessageSort sort;

		/**
		 * Initializes me.
		 */
		StartMessageCommand(MLifeline sender, Optional<MElement<?>> before, int offset, MessageSort sort) {
			super();

			this.sender = sender;
			this.before = before;
			this.offset = offset;
			this.sort = sort;
		}

	}
}
