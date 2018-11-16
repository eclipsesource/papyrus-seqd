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
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.diagram.ui.figures.LabelLocator;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;

public class MessageLabelLocator extends LabelLocator {

	private LayoutConstraints layoutConstraints;

	public MessageLabelLocator(IFigure parent, Point offSet, int alignment,
			LayoutConstraints layoutConstraints) {
		super(parent, offSet, alignment);
		this.layoutConstraints = layoutConstraints;
	}

	@Override
	public void relocate(IFigure target) {
		PointList msgPoints = ((Connection)parent).getPoints();
		Point centerPoint = getReferencePoint();

		Dimension labelSize = target.getSize();
		Point center = centerPoint.getTranslated(-labelSize.width / 2, -labelSize.height / 2);
		Point defaultPosition = center.getTranslated(0, layoutConstraints.getYOffset(ViewTypes.MESSAGE_NAME));
		target.setLocation(defaultPosition);

		// move up until label doesn't intersect anymore
		while (msgPoints.intersects(target.getBounds())) {
			target.setLocation(target.getBounds().getLocation().getTranslated(0, -1));
		}
	}

}
