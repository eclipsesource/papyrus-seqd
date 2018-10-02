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

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.SWT;

/**
 * Protocol for management of {@link IMagnet}s on a shape in the diagram editor to which connection ends snap.
 * Note that we do not use this for snapping of shapes, as GEF already provides support for that via the
 * {@link SnapToHelper} API which does not deal with connections but only shapes.
 */
public interface IMagnetManager {

	/**
	 * Key modifier for ignoring snap while dragging. It's <tt>CTRL</tt> on Mac and <tt>ALT</tt> on all other
	 * platforms, as defined in the GEF {@link AbstractTool} class.
	 */
	int MODIFIER_NO_SNAPPING = Platform.OS_MACOSX.equals(Platform.getOS()) ? SWT.CTRL : SWT.ALT;

	/**
	 * Add a {@code magnet} to the diagram editor.
	 * 
	 * @param magnet
	 *            the magnet to add
	 */
	void addMagnet(IMagnet magnet);

	/**
	 * Remove a {@code magnet} from the diagram editor.
	 * 
	 * @param magnet
	 *            the magnet to remove
	 */
	void removeMagnet(IMagnet magnet);

	/**
	 * Find the magnets within whose influence the given {@code location} lies, in increasing order of
	 * distance from that location (and so decreasing order of influence).
	 * 
	 * @param location
	 *            a location in the diagram, in absolute coördinates
	 * @return the magnets whose influence is felt at that {@code location}
	 */
	default Stream<IMagnet> getMagnets(Point location) {
		return getMagnets(location, __ -> false);
	}

	/**
	 * Find the magnet that captures a {@code location}.
	 * 
	 * @param location
	 *            a location, in absolute coördinates
	 * @return the magnet that captures the {@code location}
	 */
	default Optional<IMagnet> getCapturingMagnet(Point location) {
		return getCapturingMagnet(location, __ -> false);
	}

	/**
	 * Find the magnets within whose influence the given {@code location} lies, with exclusions, in increasing
	 * order of distance from that location (and so decreasing order of influence).
	 * 
	 * @param location
	 *            a location in the diagram, in absolute coördinates
	 * @param excluding
	 *            an exclusion filter for magnets that should not be considered, for example because they are
	 *            on a shape being moved that should not snap to itself
	 * @return the magnets whose influence is felt at that {@code location}
	 */
	Stream<IMagnet> getMagnets(Point location, Predicate<? super IMagnet> excluding);

	/**
	 * Find the magnet that captures a {@code location}, with exclusions.
	 * 
	 * @param location
	 *            a location, in absolute coördinates
	 * @param excluding
	 *            an exclusion filter for magnets that should not be considered, for example because they are
	 *            on a shape being moved that should not snap to itself
	 * @return the magnet that captures the {@code location}
	 */
	Optional<IMagnet> getCapturingMagnet(Point location, Predicate<? super IMagnet> excluding);

	/**
	 * Queries whether the magnet manager is currently enabled. When it is not enabled, then snapping
	 * connections to magnets does not happen.
	 * 
	 * @return whether the magnet manager is enabled
	 */
	boolean isEnabled();

	/**
	 * Get the magnet manager for an {@code object}, usually an edit-part or figure. There should be exactly
	 * one magnet manager for the diagram, maintained by a root-ish edit-part or figure, which always exists.
	 * 
	 * @param object
	 *            an object in the diagram
	 * @return its magnet manager. Never {@code null}
	 * @throws IllegalStateException
	 *             if the {@code object} does not trace to some magnet manager
	 */
	static IMagnetManager get(Object object) {
		IMagnetManager result = Adapters.adapt(object, IMagnetManager.class);

		if (result == null) {
			throw new IllegalStateException("object has no magnet manager: " + object); //$NON-NLS-1$
		}

		return result;
	}

}
