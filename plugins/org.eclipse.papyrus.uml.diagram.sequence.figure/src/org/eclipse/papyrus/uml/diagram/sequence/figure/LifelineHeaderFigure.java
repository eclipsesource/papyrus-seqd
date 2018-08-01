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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser.AnchorKind;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.LifelineHeaderAnchor;

public class LifelineHeaderFigure extends NodeFigure {

	private ConstrainedToolbarLayout layout;

	public LifelineHeaderFigure() {
		super();
		initFigure();
	}

	private void initFigure() {
		initLayout();
	}

	private void initLayout() {
		layout = new ConstrainedToolbarLayout(false);
		layout.setStretchMajorAxis(true);
		layout.setStretchMinorAxis(false);
		setLayoutManager(layout);
	}

	@Override
	public ConnectionAnchor getConnectionAnchor(String terminal) {
		if (terminal == null || terminal.isEmpty()) {
			return super.getConnectionAnchor(terminal);
		}

		AnchorParser anchorParser = AnchorParser.getInstance();
		try {
			AnchorKind anchorKind = anchorParser.getAnchorKind(terminal);
			if (anchorKind == AnchorKind.SIDE) {
				int height = anchorParser.getDistanceFromReference(terminal);
				int side = anchorParser.getSide(terminal);
				return createHeaderAnchor(height, side);
			}
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace(); // TODO Log
		}

		return super.getConnectionAnchor(terminal);
	}

	protected ConnectionAnchor createHeaderAnchor(int height, int side) {
		return new LifelineHeaderAnchor(this, height, side);
	}

	@Override
	public PointList getPolygonPoints() {
		return super.getPolygonPoints();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
		// This method would be used to retrieve an Anchor from a Request (Create, Reconnect). However,
		// The figure doesn't have enough info to find the right Anchor Kind.
		// We need a method with additional parameters.

		// Alternatively, we may return a generic anchor (Body + Header) and let the Edit Part decide whether
		// the request is valid
		throw new UnsupportedOperationException();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
		// This method would be used to retrieve an Anchor from a Request (Create, Reconnect). However,
		// The figure doesn't have enough info to find the right Anchor Kind.
		// We need a method with additional parameters.

		// For now, we return a generic anchor (Body + Header) and let the Edit Part decide whether
		// the request is valid
		PrecisionPoint pp = new PrecisionPoint(p);
		translateFromParent(pp);
		translateToRelative(pp);
		return createAnchor(pp);
	}

	@Override
	protected boolean useLocalCoordinates() {
		return true;
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
		RoundedRectangle rectangle = new RoundedRectangle();
		// by default, no rounded corners
		rectangle.setCornerDimensions(new Dimension(0, 0));
		rectangle.setBounds(r);
		rectangle.paintFigure(graphics);
	}

}
