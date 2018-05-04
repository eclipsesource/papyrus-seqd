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
package org.eclipse.papyrus.uml.diagram.sequence.figure;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;

/**
 * An {@link InteractionFigure}, also aggregating the header
 * 
 * @see HeaderFigure
 * @see InteractionFigure
 */
public class InteractionFigureGroup extends InteractionFigure {

	private HeaderFigure header;

	private GridLayout interactionLayout;

	public InteractionFigureGroup() {
		super();

		createLayout();
		createHeaderFigure();
	}

	private void createHeaderFigure() {
		header = new HeaderFigure();
		GridData gridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gridData.heightHint = 30;
		interactionLayout.setConstraint(header, gridData);
		PointList list = new PointList();
		list.addPoint(0, 0);
		list.addPoint(100, 0);
		list.addPoint(100, 20);
		list.addPoint(90, 30);
		list.addPoint(0, 30);
		list.addPoint(0, 0);
		header.setPoints(list);
		header.setSize(101, 31);
		header.setLocation(new Point(0, 0));
		this.add(header);
	}

	private void createLayout() {
		interactionLayout = new GridLayout();
		interactionLayout.numColumns = 1;
		interactionLayout.marginHeight = 0;
		interactionLayout.marginWidth = 0;
		interactionLayout.verticalSpacing = 5;
		// this.setLayoutManager(interactionLayout);
	}

	/**
	 * @return The header figure for the interaction. The text and icon can be set on that figure
	 */
	public HeaderFigure getHeader() {
		return this.header;
	}

	/**
	 * @return The content figure for the interaction. Children should be added in that figure
	 */
	public IFigure getContentFigure() {
		return this;
	}

	@Override
	public void add(IFigure figure, Object constraint, int index) {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		interactionLayout.setConstraint(figure, gridData);
		super.add(figure, gridData, index);
	}

}
