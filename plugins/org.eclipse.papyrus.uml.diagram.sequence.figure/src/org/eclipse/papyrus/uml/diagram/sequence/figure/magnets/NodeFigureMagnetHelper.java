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

package org.eclipse.papyrus.uml.diagram.sequence.figure.magnets;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * A convenient helper for management of magnets around the perimeter of a node figure.
 */
public class NodeFigureMagnetHelper extends MagnetHelper<IFigure, Rectangle> {

	private FigureListener figureListener;

	private AncestorListener ancestorListener;

	/**
	 * Initializes me with my {@code figure}.
	 *
	 * @param figure
	 *            my figure
	 * @param magnetManager
	 *            the magnet manager
	 * @param strength
	 *            the strength of magnets that I create
	 */
	public NodeFigureMagnetHelper(IFigure figure, IMagnetManager magnetManager, int strength) {
		super(figure, magnetManager, strength);
	}

	/**
	 * Auto-register magnets at the specified cardinal points around the bounds of my figure.
	 *
	 * @param positions
	 *            the cardinal positions (NSEW directions from the {@link PositionConstants}) at which to
	 *            register magnets
	 * @see PositionConstants
	 * @see MagnetHelper#registerMagnet(java.util.function.Function)
	 */
	public NodeFigureMagnetHelper registerMagnets(int... positions) {
		for (int i = 0; i < positions.length; i++) {
			switch (positions[i]) {
				case PositionConstants.NORTH_WEST:
					registerMagnet(Rectangle::getTopLeft);
					break;
				case PositionConstants.NORTH:
					registerMagnet(Rectangle::getTop);
					break;
				case PositionConstants.NORTH_EAST:
					registerMagnet(Rectangle::getTopRight);
					break;
				case PositionConstants.EAST:
					registerMagnet(Rectangle::getRight);
					break;
				case PositionConstants.SOUTH_EAST:
					registerMagnet(Rectangle::getBottomRight);
					break;
				case PositionConstants.SOUTH:
					registerMagnet(Rectangle::getBottom);
					break;
				case PositionConstants.SOUTH_WEST:
					registerMagnet(Rectangle::getBottomLeft);
					break;
				case PositionConstants.WEST:
					registerMagnet(Rectangle::getLeft);
					break;
			}
		}

		return this;
	}

	@Override
	protected void addListeners(IFigure figure, Runnable action) {
		// Recompute magnets on direct or indirect move of the figure
		figureListener = __ -> action.run();
		ancestorListener = new AncestorListener.Stub() {
			@Override
			public void ancestorMoved(IFigure __) {
				action.run();
			}
		};

		figure.addFigureListener(figureListener);
		figure.addAncestorListener(ancestorListener);
	}

	@Override
	protected void removeListeners(IFigure figure) {
		figure.removeAncestorListener(ancestorListener);
		figure.removeFigureListener(figureListener);

		ancestorListener = null;
		figureListener = null;
	}

	@Override
	protected Rectangle getGeometry(IFigure figure) {
		return figure.getBounds().getCopy();
	}
}
