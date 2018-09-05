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

import org.eclipse.draw2d.geometry.Point;

/**
 * A simple implementation of the {@linkplain IMagnet magnet} protocol.
 */
public class DefaultMagnet implements IMagnet {

	private final int strength;

	private Point location;

	/**
	 * Initializes me with my {@code strength}.
	 * 
	 * @param strength
	 *            my strength
	 */
	public DefaultMagnet(int strength) {
		super();

		this.strength = strength;
	}

	@Override
	public final int getStrength() {
		return strength;
	}

	@Override
	public void setLocation(Point location) {
		this.location = location;
	}

	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public boolean influences(@SuppressWarnings("hiding") Point location) {
		return (this.location != null) && (this.location.getDistance(location) <= 1.5 * strength);
	}

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		return String.format("DefaultMagnet at (%s, %s), strength %s", //$NON-NLS-1$
				location.preciseX(), location.preciseY(), strength);
	}
}
