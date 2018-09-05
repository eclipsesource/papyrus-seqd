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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.tools.AbstractTool.Input;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.MagnetManager;

/**
 * Support for integration of palette tools with the {@linkplain IMagnetManager magnet manager}.
 */
class MagnetSupport {
	private final Supplier<EditPartViewer> viewerSupplier;

	private final Supplier<Input> basicInputSupplier;

	private Optional<MagnetManager> magnetManager = Optional.empty();

	private Input currentInput;

	/**
	 * Initializes me with my viewer supplier and basic input supplier.
	 *
	 * @param viewSupplier
	 *            supplier of the contextual edit-part viewer
	 * @param basicInputSupplier
	 *            basic supplier of the tool's input
	 */
	MagnetSupport(Supplier<EditPartViewer> viewerSupplier, Supplier<Input> basicInputSupplier) {
		super();

		this.viewerSupplier = viewerSupplier;
		this.basicInputSupplier = basicInputSupplier;
	}

	/**
	 * Called upon deactivation of my owner tool.
	 */
	void deactivate() {
		magnetManager.ifPresent(mgr -> mgr.setCurrentInput(null));
		magnetManager = Optional.empty();
		currentInput = null;
	}

	/**
	 * Called by my owner tool to get its current input.
	 */
	Input getCurrentInput() {
		if (currentInput == null) {
			currentInput = basicInputSupplier.get();
		}

		if (!magnetManager.isPresent()) {
			magnetManager = Optional.ofNullable(viewerSupplier.get()).map(EditPartViewer::getRootEditPart)
					.map(RootEditPart::getContents).flatMap(MagnetManager::get);
			magnetManager.ifPresent(mgr -> mgr.setCurrentInput(currentInput));
		}

		return currentInput;
	}

}
