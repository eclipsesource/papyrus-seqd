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
package org.eclipse.papyrus.uml.diagram.sequence.figure.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.sequence.figure.LifelineBodyFigure;

/**
 * An Anchor on the Lifeline body. The anchor is configured with a Distance position relative to the top of
 * the lifeline body.
 */
public class LifelineBodyAnchor extends AbstractConnectionAnchor {

	private final LifelineBodyFigure lifelinebodyFigure;

	private int distance;

	public LifelineBodyAnchor(LifelineBodyFigure lifelinebodyFigure, int distance) {
		super(lifelinebodyFigure);
		// We actually attach the anchor to the BodyFigure of the Lifeline
		// Note: this causes issues for policies/interaction, because the body is far away
		// from the Lifeline's Bounds. However, we're only interested in rendering here, and this works fine
		this.distance = distance;
		this.lifelinebodyFigure = lifelinebodyFigure;
	}

	public String getTerminal() {
		return String.valueOf(distance);
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle body = lifelinebodyFigure.getBounds().getCopy();
		lifelinebodyFigure.translateToAbsolute(body);
		int boundedHeight = Math.min(distance, body.height);
		int realHeight = (int)Math.round(boundedHeight * getScale(lifelinebodyFigure));
		Point location = new Point(0, realHeight);
		location.translate(body.getTopLeft());
		return location;
	}

	/**
	 * @return The scale applied to the given figure (i.e.: the zoom set by the user)
	 */
	public static double getScale(final IFigure figure) {
		final ScalableFigure scalableFigure = findParentFigureInstance(figure, ScalableFigure.class);
		return scalableFigure.getScale();
	}

	/**
	 * Returns the first parent figure of the given type recursively containing the given figure.
	 *
	 * @param figure
	 *            the figure
	 * @param parentFigureClass
	 *            the type of the parent figure that is looked for
	 * @return the parent figure if found, or <code>null</code> if the figure is not contained by any figure
	 *         of the given type
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IFigure> T findParentFigureInstance(final IFigure figure,
			final Class<T> parentFigureClass) {
		IFigure parent = figure.getParent();
		while (parent != null) {
			if (parentFigureClass.isAssignableFrom(parent.getClass())) {
				return (T)parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

}
