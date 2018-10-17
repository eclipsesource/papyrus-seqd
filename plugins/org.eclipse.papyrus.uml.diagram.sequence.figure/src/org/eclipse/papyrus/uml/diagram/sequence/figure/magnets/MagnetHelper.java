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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.gef.Disposable;

/**
 * Framework for management of magnets on a figure with some abstract geometry.
 * 
 * @param <T>
 *            the kind of figure that I assist
 * @param <G>
 *            the kind of geometry that the figure has
 */
public abstract class MagnetHelper<T extends IFigure, G extends Translatable> implements Disposable {

	private final T figure;

	private final IMagnetManager magnetManager;

	private final int magnetStrength;

	private final List<MagnetRecord> magnets = new ArrayList<>(2);

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
	public MagnetHelper(T figure, IMagnetManager magnetManager, int strength) {
		super();

		this.figure = figure;
		this.magnetManager = magnetManager;
		this.magnetStrength = strength;

		addListeners(figure, () -> updateMagnets(figure));
	}

	@Override
	public void dispose() {
		removeListeners(figure);

		magnets.forEach(Disposable::dispose);
		magnets.clear();
	}

	protected abstract void addListeners(@SuppressWarnings("hiding") T figure, Runnable action);

	protected abstract void removeListeners(@SuppressWarnings("hiding") T figure);

	public void registerMagnet(Function<? super G, ? extends Point> magnetFunction) {
		magnets.add(new MagnetRecord(magnetFunction));
	}

	private void updateMagnets(@SuppressWarnings("hiding") T figure) {
		G geometry = getGeometry(figure);
		figure.getParent().translateToAbsolute(geometry);

		magnets.forEach(rec -> rec.update(geometry));
	}

	/**
	 * Obtain a mutable copy of the geometry of a {@code figure}, ready to transform into absolute
	 * coördinates.
	 * 
	 * @param figure
	 *            a figure
	 * @return a mutable copy of its geometry
	 */
	protected abstract G getGeometry(@SuppressWarnings("hiding") T figure);

	//
	// Nested types
	//

	private class MagnetRecord implements Disposable {
		final Function<? super G, ? extends Point> magnetFunction;

		final DefaultMagnet magnet;

		MagnetRecord(Function<? super G, ? extends Point> magnetFunction) {
			super();

			this.magnetFunction = magnetFunction;
			this.magnet = new DefaultMagnet(figure, magnetStrength);

			magnetManager.addMagnet(magnet);
		}

		@Override
		public void dispose() {
			magnetManager.removeMagnet(magnet);
		}

		void update(G geometry) {
			magnet.setLocation(magnetFunction.apply(geometry));
		}
	}
}
