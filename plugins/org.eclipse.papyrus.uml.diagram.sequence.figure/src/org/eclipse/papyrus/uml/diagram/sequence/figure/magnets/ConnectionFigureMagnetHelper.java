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

import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.PointList;

/**
 * A convenient helper for management of magnets along the path of a connection figure.
 */
public class ConnectionFigureMagnetHelper extends MagnetHelper<Connection, PointList> {

	private PropertyChangeListener pointsListener;

	/**
	 * Initializes me with my {@code figure}.
	 *
	 * @param connection
	 *            my connection figure
	 * @param magnetManager
	 *            the magnet manager
	 * @param strength
	 *            the strength of magnets that I create
	 */
	public ConnectionFigureMagnetHelper(Connection connection, IMagnetManager magnetManager, int strength) {
		super(connection, magnetManager, strength);
	}

	/**
	 * Auto-register magnets at the specified points of my connection figure.
	 *
	 * @param endpoints
	 *            whether to create magnets at the endpoints
	 * @param midpoint
	 *            whether to create magnets at the midpoint of the connection path
	 * @see MagnetHelper#registerMagnet(java.util.function.Function)
	 */
	public ConnectionFigureMagnetHelper registerMagnets(boolean endpoints, boolean midpoint) {
		if (endpoints) {
			registerMagnet(PointList::getFirstPoint);
			registerMagnet(PointList::getLastPoint);
		}
		if (midpoint) {
			registerMagnet(PointList::getMidpoint);
		}

		return this;
	}

	@Override
	protected void addListeners(Connection figure, Runnable action) {
		// Recompute magnets on change of the points defining the connection path
		pointsListener = __ -> action.run();

		figure.addPropertyChangeListener(Connection.PROPERTY_POINTS, pointsListener);
	}

	@Override
	protected void removeListeners(Connection figure) {
		figure.removePropertyChangeListener(Connection.PROPERTY_POINTS, pointsListener);

		pointsListener = null;
	}

	@Override
	protected PointList getGeometry(Connection figure) {
		return figure.getPoints().getCopy();
	}

}
