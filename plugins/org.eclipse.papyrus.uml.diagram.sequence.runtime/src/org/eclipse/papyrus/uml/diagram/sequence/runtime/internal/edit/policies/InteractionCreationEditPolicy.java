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

import java.util.Optional;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.HeaderFigure;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MObject;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.uml2.uml.Element;

/**
 * {@link CreationEditPolicy} with delegation to the Logical model.
 */
public class InteractionCreationEditPolicy extends LogicalModelCreationEditPolicy {

	@Override
	public Command getCommand(Request request) {
		return super.getCommand(request);
	}

	@Override
	protected Optional<org.eclipse.emf.common.command.Command> getCreationCommand(MInteraction interaction,
			Element parentElement, View parentView, Point location, Dimension size, IElementType type) {

		// compute command for the given request
		SequenceDiagramSwitch<CreationCommand<? extends Element>> commandSwitch = new SequenceDiagramSwitch<CreationCommand<? extends Element>>() {

			@Override
			public CreationCommand<? extends Element> caseMInteraction(MInteraction object) {

				if (ElementUtil.isTypeOf(type, UMLElementTypes.LIFELINE)) {
					int xOffset = location.x();

					// Account for the header's margin to ensure that the lifeline is placed
					// exactly where the mouse cursor put it
					xOffset = xOffset - HeaderFigure.DEFAULT_MARGIN_WIDTH;

					return object.addLifeline(xOffset, size != null ? size.height : -1);
				}
				return super.caseMInteraction(object);
			}

			@Override
			public CreationCommand<? extends Element> defaultCase(MObject object) {
				return super.defaultCase(object);
			}
		};

		return Optional.ofNullable(commandSwitch.doSwitch(interaction));
	}

}
