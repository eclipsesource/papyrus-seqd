/*****************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts;

import java.util.Optional;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.uml2.uml.Element;

/**
 * A mix-in interface for edit-parts in the sequence diagram that need to access the {@link #getLayoutHelper()
 * layout helper} and the {@link #getLayoutConstraints() layout constraints}.
 */
public interface ISequenceEditPart extends IGraphicalEditPart {

	default LayoutHelper getLayoutHelper() {
		return Activator.getDefault().getLayoutHelper(getEditingDomain());
	}

	default LayoutConstraints getLayoutConstraints() {
		return Activator.getDefault().getLayoutConstraints(getEditingDomain());
	}

	default int getMinimumWidth() {
		return getLayoutConstraints().getMinimumWidth(getNotationView());
	}

	default int getMinimumHeight() {
		return getLayoutConstraints().getMinimumHeight(getNotationView());
	}

	default int getMinimumWidth(String modifier) {
		return getLayoutConstraints().getMinimumWidth(getNotationView(), modifier);
	}

	default int getMinimumHeight(String modifier) {
		return getLayoutConstraints().getMinimumHeight(getNotationView(), modifier);
	}

	default int getPaddingTop() {
		return getPadding(RelativePosition.TOP);
	}

	default int getPaddingBottom() {
		return getPadding(RelativePosition.BOTTOM);
	}

	default int getPaddingLeft() {
		return getPadding(RelativePosition.LEFT);
	}

	default int getPaddingRight() {
		return getPadding(RelativePosition.RIGHT);
	}

	default int getPadding(RelativePosition orientation) {
		return getLayoutConstraints().getPadding(orientation, getNotationView());
	}

	/**
	 * Compute a {@code location} relative in my host figure's coördinate space.
	 * 
	 * @param location
	 *            an absolute location in the diagram viewer coördinates
	 * @return the {@code location} in my host figure's coördinate space
	 */
	default Point getRelativeLocation(Point location) {
		Point result = location.getCopy();

		IFigure figure = getFigure();
		if (figure != null) {
			figure.translateToRelative(result);
			result.translate(figure.getBounds().getLocation().getNegated());
		}

		return result;
	}

	default MInteraction getInteraction() {
		View view = getNotationView();
		Diagram diagram = view.getDiagram();
		return MInteraction.getInstance(diagram);
	}

	default Optional<MElement<? extends Element>> getLogicalElement() {
		EObject semantic = resolveSemanticElement();
		return Optional.ofNullable(semantic).filter(Element.class::isInstance).map(Element.class::cast)
				.flatMap(getInteraction()::getElement);
	}

	default IMagnetManager getMagnetManager() {
		return IMagnetManager.get(this);
	}
}
