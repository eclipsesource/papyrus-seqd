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

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;

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

}
