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

				Point mouse = request.getLocation();
				Point location = getRelativeLocation(mouse);

				Optional<MElement<?>> before = mLifeline.elementAt(location.y());
				int offset = getOffsetFrom(location, mLifeline, before);

				Command result = new StartMessageCommand(mLifeline, mouse, before, offset,
						getSort(messageType));
				request.setStartCommand(result);
				return result;
			}
		}.doSwitch(request);
	}

	private int getOffsetFrom(Point relativeMouse, MLifeline lifeline, Optional<MElement<?>> before) {
		int result = relativeMouse.y();

		if (before.isPresent()) {
			// We know the top exists because that's how we found the 'before' element
			int relativeTopOfBefore = before.get().getTop().getAsInt()
					- getLayoutHelper().getBottom(lifeline.getDiagramView().get());
			result = result - relativeTopOfBefore;
		} // else it will be relative to the top of the lifeline body line

		return result;
	}

	@Override
	@SuppressWarnings("hiding")
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		return new CreateRequestSwitch<Command>() {
			@Override
			public Command caseCreateConnectionViewRequest(CreateConnectionViewRequest request) {
				StartMessageCommand start = (StartMessageCommand)request.getStartCommand();
				CreationCommand<Message> result;

				MLifeline sender = start.sender;
				MInteraction interaction = sender.getInteraction();
				MLifeline receiver = interaction.getLifeline(getHost().getAdapter(Lifeline.class)).get();

				switch (start.sort) {
					case ASYNCH_CALL_LITERAL:
					case ASYNCH_SIGNAL_LITERAL:
						// These can slope down
						Point location = request.getLocation();
						int absoluteY = location.y();
						Point startLocation = start.location;
						// but don't require such pointer exactitude of the user
						if ((absoluteY < startLocation.y())
								|| !getLayoutConstraints().isAsyncMessageSlope(startLocation.preciseX(),
										startLocation.preciseY(), location.preciseX(), location.preciseY())) {

							absoluteY = startLocation.y();
							location.setY(absoluteY);
						}

						location = getRelativeLocation(location);

						Optional<MElement<?>> before = receiver.elementAt(location.y());
						int offset = getOffsetFrom(location, receiver, before);

						result = sender.insertMessageAfter(start.before.orElse(sender), start.offset,
								receiver, before.orElse(receiver), offset, start.sort, null);
						break;
					default:
						// Enforce a horizontal layout
						result = sender.insertMessageAfter(start.before.orElse(sender), start.offset,
								receiver, start.sort, null);
						break;
				}

				return wrap(result);
			}
		}.doSwitch(request);
	}

	//
	// Nested types
	//

	private static class StartMessageCommand extends Command {
		private final MLifeline sender;

		private final Point location;

		private final Optional<MElement<?>> before;

		private final int offset;

		private final MessageSort sort;

		/**
		 * Initializes me.
		 */
		StartMessageCommand(MLifeline sender, Point absoluteMouse, Optional<MElement<?>> before, int offset,
				MessageSort sort) {
			super();

			this.sender = sender;
			this.location = absoluteMouse;
			this.before = before;
			this.offset = offset;
			this.sort = sort;
		}

	}
}
