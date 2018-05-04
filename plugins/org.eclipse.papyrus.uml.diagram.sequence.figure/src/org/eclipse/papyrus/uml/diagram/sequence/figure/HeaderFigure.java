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
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.figure;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.ScalablePolygonShape;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.swt.graphics.Image;

/**
 * Frame that contains a label.
 */
public class HeaderFigure extends ScalablePolygonShape {
	public static final int DEFAULT_MARGIN_WIDTH = 5;

	public static final int DEFAULT_MARGIN_HEIGHT = 5;

	private GridLayout layout;

	private WrappingLabel label;

	public HeaderFigure() {
		super();
		layout = new GridLayout();
		layout.marginHeight = DEFAULT_MARGIN_HEIGHT;
		layout.marginWidth = DEFAULT_MARGIN_WIDTH;
		setLayoutManager(layout);
		label = new WrappingLabel();
		add(label);
	}

	public void setText(String text) {
		label.setText(text);
	}

	public void setIcon(Image image) {
		label.setIcon(image);
	}
}
