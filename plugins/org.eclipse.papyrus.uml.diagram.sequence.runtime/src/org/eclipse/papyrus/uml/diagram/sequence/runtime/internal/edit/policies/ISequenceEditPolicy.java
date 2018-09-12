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

import java.util.Optional;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.OperationToGEFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.ISequenceEditPart;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.uml2.uml.Element;

/**
 * A mix-in interface for edit-policies in the sequence diagram that need to create executable commands in a
 * transactional editing domain.
 */
public interface ISequenceEditPolicy extends EditPolicy {

	/**
	 * Wrap an EMF command in a GEF command that interacts properly with the contextual
	 * {@link #getEditingDomain() editing domain} when executed.
	 * 
	 * @param emfCommand
	 *            an EMF command to wrap
	 * @return the transactional GEF command
	 */
	default Command wrap(org.eclipse.emf.common.command.Command emfCommand) {
		TransactionalEditingDomain domain = __getEditingDomain(this);
		return (domain == null) ? UnexecutableCommand.INSTANCE
				: OperationToGEFCommandWrapper.wrap(new EMFCommandOperation(domain, emfCommand));
	}

	default DiagramHelper getDiagramHelper() {
		return Activator.getDefault().getDiagramHelper(__getEditingDomain(this));
	}

	default LayoutHelper getLayoutHelper() {
		return Activator.getDefault().getLayoutHelper(__getEditingDomain(this));
	}

	default LayoutConstraints getLayoutConstraints() {
		return Activator.getDefault().getLayoutConstraints(__getEditingDomain(this));
	}

	// This should be a private 'getEditingDomain' method in Java 9
	static TransactionalEditingDomain __getEditingDomain(ISequenceEditPolicy __this) {
		EObject view = __this.getHost().getAdapter(View.class);
		return (view == null) ? null : TransactionUtil.getEditingDomain(view);
	}

	// This should be a private 'getHostFigure' method in Java 9
	static IFigure __getHostFigure(ISequenceEditPolicy __this) {
		EditPart host = __this.getHost();
		return (host instanceof GraphicalEditPart) ? ((GraphicalEditPart)host).getFigure() : null;
	}

	// This should be a private 'getHostView' method in Java 9
	static View __getHostView(ISequenceEditPolicy __this) {
		EditPart host = __this.getHost();
		return (host instanceof IGraphicalEditPart) ? ((IGraphicalEditPart)host).getNotationView() : null;
	}

	/**
	 * Compute a {@code location} relative in my host figure's coördinate space.
	 * 
	 * @param location
	 *            an absolute location in the diagram viewer coördinates
	 * @return the {@code location} in my host figure's coördinate space
	 */
	default Point getRelativeLocation(Point location) {
		return Optional.of(getHost()).filter(ISequenceEditPart.class::isInstance)
				.map(ISequenceEditPart.class::cast).map(seq -> seq.getRelativeLocation(location))
				.orElseGet(() -> {
					Point result = location.getCopy();

					IFigure figure = __getHostFigure(this);
					if (figure != null) {
						figure.translateToRelative(result);
						result.translate(figure.getBounds().getLocation().getNegated());
					}

					return result;
				});
	}

	default int getMinimumWidth() {
		return getLayoutConstraints().getMinimumWidth(__getHostView(this));
	}

	default int getMinimumHeight() {
		return getLayoutConstraints().getMinimumHeight(__getHostView(this));
	}

	default MInteraction getInteraction() {
		return Optional.of(getHost()).filter(ISequenceEditPart.class::isInstance)
				.map(ISequenceEditPart.class::cast).map(ISequenceEditPart::getInteraction).orElse(null);
	}

	default Optional<MElement<? extends Element>> getLogicalElement() {
		return Optional.of(getHost()).filter(ISequenceEditPart.class::isInstance)
				.map(ISequenceEditPart.class::cast).flatMap(ISequenceEditPart::getLogicalElement);
	}

	default IMagnetManager getMagnetManager() {
		return IMagnetManager.get(getHost());
	}
}
