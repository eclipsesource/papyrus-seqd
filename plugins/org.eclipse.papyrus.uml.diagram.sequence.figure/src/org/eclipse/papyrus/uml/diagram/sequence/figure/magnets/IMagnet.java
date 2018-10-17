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

import java.util.function.Predicate;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Protocol of a <em>magnet</em> that draws objects towards it (connection bendpoints and endpoints, shapes)
 * that are being dragged about by the mouse pointer.
 */
public interface IMagnet {

	/**
	 * Obtains the figure that owns me.
	 * 
	 * @return my owner
	 */
	IFigure getOwner();

	/**
	 * Obtain the magnet's strength, which is the square radius within which it captures the mouse pointer.
	 * 
	 * @return a positive strength, in pixels
	 */
	int getStrength();

	/**
	 * Queries whether a {@code location} is within my {@linkplain #getStrength() square radius}.
	 * 
	 * @param location
	 *            a location in absolute coördinates
	 * @return whether I capture the {@code location}
	 */
	default boolean captures(Point location) {
		Point self = getLocation();
		int strength = getStrength();
		int x = self.x - strength;
		int y = self.y - strength;
		int size = strength << 1;

		Rectangle.SINGLETON.setBounds(x, y, size, size);

		return Rectangle.SINGLETON.contains(location);
	}

	/**
	 * Query the location of the magnet, in absolute coördinates.
	 * 
	 * @return the location
	 */
	Point getLocation();

	/**
	 * Update (move) the location of the magnet.
	 * 
	 * @param location
	 *            the new location of the magnet
	 */
	void setLocation(Point location);

	/**
	 * Determine whether a {@code location} feels my magnetic influence. This does not necessarily mean that I
	 * {@link #captures(Point) capture} the mouse pointer from that {@code location}. My influence could
	 * extend beyond my {@link #getStrength() strength}.
	 * 
	 * @param location
	 *            a location
	 * @return whether the {@code location} feels my influence
	 * @see #getStrength()
	 */
	boolean influences(Point location);

	/**
	 * Compute my distance from a point.
	 * 
	 * @param location
	 *            a reference point
	 * @return my distance from it
	 */
	default double distance(Point location) {
		return getLocation().getDistance(location);
	}

	/**
	 * Obtain a magnet filter (often used for exclusion) that matches magnets having the given {@code owner}.
	 * 
	 * @param owner
	 *            the owner on which to filter magnets
	 * @return the magnet filter
	 * @see #getOwner()
	 */
	static Predicate<IMagnet> ownedBy(IFigure owner) {
		return m -> m.getOwner() == owner;
	}

	// TODO: API for showing and hiding feedback (with a secondary radius within which it is activated?)
}
