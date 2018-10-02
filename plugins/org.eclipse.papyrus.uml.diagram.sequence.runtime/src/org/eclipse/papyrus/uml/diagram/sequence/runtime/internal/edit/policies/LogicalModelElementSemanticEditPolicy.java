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

import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.OperationToGEFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.InteractionUtil;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;

/**
 * {@link SemanticEditPolicy} for {@link MElement logical elements}.
 */
public class LogicalModelElementSemanticEditPolicy extends SemanticEditPolicy {

	@Override
	protected Command getSemanticCommand(IEditCommandRequest request) {
		if (request instanceof DestroyRequest) {
			View view = (View)getHost().getModel();
			Optional<Element> element = Optional.ofNullable(ViewUtil.resolveSemanticElement(view))
					.filter(Element.class::isInstance).map(Element.class::cast);
			if (!element.isPresent()) {
				return UnexecutableCommand.INSTANCE;
			}
			Optional<Diagram> diagram = Optional.ofNullable(view).map(View::getDiagram);
			Optional<Interaction> interaction = InteractionUtil.getInteraction(element.get());
			if (!interaction.isPresent() || !diagram.isPresent()) {
				return UnexecutableCommand.INSTANCE;
			}
			MInteraction mInteraction = MInteraction.getInstance(interaction.get(), diagram.get());
			Optional<MElement<? extends Element>> mElement = mInteraction.getElement(element.get());
			if (!mElement.isPresent()) {
				return UnexecutableCommand.INSTANCE;
			}
			/* delegate to logical model */
			org.eclipse.emf.common.command.Command emfCommand = mElement.get().remove();
			return OperationToGEFCommandWrapper.wrap(
					new EMFCommandOperation(TransactionUtil.getEditingDomain(interaction.get()), emfCommand));
		}
		return super.getSemanticCommand(request);
	}

	IGraphicalEditPart getGraphicalHost() {
		return (IGraphicalEditPart)getHost();
	}

	MInteraction getInteraction() {
		return MInteraction.getInstance(getGraphicalHost().getNotationView().getDiagram());
	}

}
