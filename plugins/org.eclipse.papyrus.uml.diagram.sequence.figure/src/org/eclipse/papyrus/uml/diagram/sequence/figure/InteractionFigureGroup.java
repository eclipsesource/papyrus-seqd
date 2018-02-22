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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.swt.SWT;

/**
 * An {@link InteractionFigure}, also aggregating the compartment and header
 * 
 * @see HeaderFigure
 */
// Note: this is temporary, as we are currently using a single edit part for the entire interaction
// We'll probably switch to separate edit parts for header and compartment, as this would give
// use more flexibility for interacting with the various parts
public class InteractionFigureGroup extends InteractionFigure {

	private HeaderFigure header;

	private IFigure compartment;

	private GridLayout interactionLayout;

	public InteractionFigureGroup() {
		super();

		createLayout();
		createHeaderFigure();
		createCompartmentFigure();
	}

	private void createCompartmentFigure() {
		compartment = new Figure();
		GridData gridData2 = new GridData(SWT.FILL, SWT.TOP, true, true);
		interactionLayout.setConstraint(compartment, gridData2);
		ConstrainedToolbarLayout interactionComparmentLayout = new ConstrainedToolbarLayout(true);
		interactionComparmentLayout.setStretchMajorAxis(false);
		interactionComparmentLayout.setStretchMinorAxis(false);
		interactionComparmentLayout.setMinorAlignment(OrderedLayout.ALIGN_TOPLEFT);
		interactionComparmentLayout.setSpacing(5);
		compartment.setLayoutManager(interactionComparmentLayout);
		compartment.setSize(50, 100);
		this.add(compartment);
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
		this.setLayoutManager(interactionLayout);
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
		return this.compartment;
	}

}
