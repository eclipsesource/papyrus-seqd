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

import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.tools.AbstractTool.Input;

/**
 * A manager of {@link IMagnet}s.
 */
public class MagnetManager implements IMagnetManager {

	// If scale becomes a problem, we may need a quad-tree or some such
	private final List<IMagnet> magnets = new ArrayList<>();

	private Input currentInput;

	/**
	 * Initializes me.
	 */
	public MagnetManager() {
		super();
	}

	@Override
	public void addMagnet(IMagnet magnet) {
		magnets.add(magnet);
	}

	@Override
	public void removeMagnet(IMagnet magnet) {
		magnets.remove(magnet);
	}

	@Override
	public Stream<IMagnet> getMagnets(Point location, Predicate<? super IMagnet> excluding) {
		if (!isEnabled()) {
			return Stream.empty();
		}

		return magnets.stream().filter(excluding.negate()) //
				.filter(influences(location)) //
				.sorted(comparingDouble(m -> m.distance(location)));
	}

	@Override
	public Optional<IMagnet> getCapturingMagnet(Point location, Predicate<? super IMagnet> excluding) {
		// The first magnet is the nearest, and if it isn't capturing, then none is
		return getMagnets(location, excluding).findFirst().filter(captures(location));
	}

	@Override
	public boolean isEnabled() {
		Input input = getCurrentInput();
		return input == null || !input.isModKeyDown(MODIFIER_NO_SNAPPING);
	}

	/**
	 * @return the currentInput
	 */
	public Input getCurrentInput() {
		return currentInput;
	}

	/**
	 * @param currentInput
	 *            the currentInput to set
	 */
	public void setCurrentInput(Input currentInput) {
		this.currentInput = currentInput;
	}

	private Predicate<IMagnet> influences(Point location) {
		return magnet -> magnet.influences(location);
	}

	private Predicate<IMagnet> captures(Point location) {
		return magnet -> magnet.captures(location);
	}

	public static Optional<MagnetManager> get(Object object) {
		return Optional.ofNullable(IMagnetManager.get(object)).filter(MagnetManager.class::isInstance)
				.map(MagnetManager.class::cast);
	}
}
