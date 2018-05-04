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
package org.eclipse.papyrus.uml.diagram.sequence.figure.layout;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;

/**
 * <p>
 * A Layout which stacks children vertically, and give them at least their preferred size.
 * </p>
 * <p>
 * A figure with this layout will expand or shrink automatically, until it reaches its layout constraint
 * (Which acts as its minimum size)
 * <p>
 */
public class AutoSizeLayout extends ConstrainedToolbarLayout {

	@Override
	public Dimension calculateMinimumSize(IFigure container, int wHint, int hHint) {
		// Never display scrollbars; always use as much space as required
		Dimension minSize = new Dimension(wHint, hHint);
		return minSize.union(getPreferredSize(container, wHint, hHint));
	}

}
