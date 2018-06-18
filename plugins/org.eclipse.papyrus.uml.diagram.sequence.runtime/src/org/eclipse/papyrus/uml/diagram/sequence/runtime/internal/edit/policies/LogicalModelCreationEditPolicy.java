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
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.InteractionUtil;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;

/**
 * {@link CreationEditPolicy} with delegation to the Logical model.
 */
public abstract class LogicalModelCreationEditPolicy extends CreationEditPolicy implements ISequenceEditPolicy {

	@Override
	protected Command getCreateElementAndViewCommand(CreateViewAndElementRequest request) {
		// do the delegation to logical model
		View view = (View)getHost().getModel();
		Optional<Element> element = Optional.ofNullable(ViewUtil.resolveSemanticElement(view))
				.filter(Element.class::isInstance).map(Element.class::cast);
		Optional<Diagram> diagram = Optional.ofNullable(view).map(View::getDiagram);
		Optional<Interaction> interaction = InteractionUtil.getInteraction(element.orElse(null));
		if (!interaction.isPresent() || !diagram.isPresent()) {
			return UnexecutableCommand.INSTANCE;
		}
		MInteraction mInteraction = MInteraction.getInstance(interaction.get(), diagram.get());

		// retrieve local position for the creation (request is absolute to the diagram, logical model works
		// with relative to parent)
		Point location = getRelativeLocation(request.getLocation());

		Optional<org.eclipse.emf.common.command.Command> result = getCreationCommand(mInteraction,
				element.get(), view, location, request.getSize(),
				(IElementType)request.getViewAndElementDescriptor().getCreateElementRequestAdapter()
						.getAdapter(IElementType.class));

		return result.map(this::wrap).orElse(UnexecutableCommand.INSTANCE);
	}

	protected abstract Optional<org.eclipse.emf.common.command.Command> getCreationCommand(
			MInteraction interaction, Element parentElement, View parentView, Point location, Dimension size,
			IElementType type);

	@Override
	protected Command getCreateCommand(CreateViewRequest request) {
		// no command, but no not execute also. May be required to test for creation in drop case?
		return null;
	}

}
