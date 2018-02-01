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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

public class LifelineFigure extends Figure {

	private RectangleFigure bodyFigure;
	private WrappingLabel label;
	private RoundedRectangle headerFigure;
	private GridLayout layout;
	private GridData headerLayoutData;
	private GridData bodyGridData;

	public LifelineFigure() {
		super();
		initFigure();
	}

	private void initFigure() {
		initLayout();
		initHeaderFigure();
		initBodyFigure();
	}

	private void initLayout() {
		layout = new GridLayout(1, false);
		layout.marginWidth = 5;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		setLayoutManager(layout);
	}

	private void initBodyFigure() {
		bodyFigure = new RectangleFigure();
		getBodyFigure().setSize(1, 200);
		bodyGridData = new GridData(SWT.CENTER, SWT.TOP, false, false);
		layout.setConstraint(getBodyFigure(), bodyGridData);
		add(getBodyFigure());
	}

	private void initHeaderFigure() {
		headerFigure = new RoundedRectangle();
		headerFigure.setCornerDimensions(new Dimension(5, 5));
		add(headerFigure);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		headerFigure.setLayoutManager(layout);
		label = new WrappingLabel();
		label.setAlignment(PositionConstants.CENTER);
		headerLayoutData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		layout.setConstraint(label, headerLayoutData);
		headerFigure.add(label);
	}

	public void setText(String text) {
		label.setText(text);
	}

	public void setIcon(Image image) {
		label.setIcon(image);
	}

	public void setLifelineSize(int width, int height) {
		headerLayoutData.widthHint = width;
		bodyGridData.heightHint = height;
	}

	public RectangleFigure getBodyFigure() {
		return bodyFigure;
	}

}
