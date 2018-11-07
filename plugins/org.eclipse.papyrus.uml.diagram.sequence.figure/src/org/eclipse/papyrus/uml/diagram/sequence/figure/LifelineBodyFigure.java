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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser.AnchorKind;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.LifelineBodyAnchor;
import org.eclipse.swt.SWT;

public class LifelineBodyFigure extends NodeFigure {

	private static Rectangle LINEBOUNDS = Rectangle.SINGLETON;

	private static final int TOLERANCE = 5;

	private int defaultAnchorDistance;

	@Override
	protected void paintFigure(Graphics graphics) {
		Polyline line = new Polyline();
		Rectangle r = getBounds().getCopy();
		line.addPoint(r.getTop());
		line.addPoint(r.getBottom());
		line.setLineWidth(r.width());
		line.setLineStyle(SWT.LINE_DASH);
		line.paintFigure(graphics);
	}

	@Override
	public boolean containsPoint(int x, int y) {
		LINEBOUNDS.setBounds(getBounds());
		LINEBOUNDS.expand(TOLERANCE, 0);
		boolean contains = LINEBOUNDS.contains(x, y);
		return contains;
	}

	@Override
	public ConnectionAnchor getConnectionAnchor(String terminal) {
		if (terminal == null || terminal.isEmpty()) {
			return super.getConnectionAnchor(terminal);
		}

		AnchorParser anchorParser = AnchorParser.getInstance();
		try {
			AnchorKind anchorKind = anchorParser.getAnchorKind(terminal);
			if (anchorKind == AnchorKind.DISTANCE) { // Body anchor
				int height = anchorParser.getDistance(terminal);
				return createBodyAnchor(height);
			}
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace(); // TODO Log
		}

		return super.getConnectionAnchor(terminal);
	}

	@Override
	public String getConnectionAnchorTerminal(ConnectionAnchor c) {
		if (c instanceof LifelineBodyAnchor) {
			return ((LifelineBodyAnchor)c).getTerminal();
		}
		return super.getConnectionAnchorTerminal(c);
	}

	@Override
	protected ConnectionAnchor createDefaultAnchor() {
		return new LifelineBodyAnchor(this, defaultAnchorDistance);
	}

	@Override
	protected ConnectionAnchor createAnchor(PrecisionPoint p) {
		if (p == null) {
			return createDefaultAnchor();
		}
		return new LifelineBodyAnchor(this, p.y);
	}

	protected ConnectionAnchor createBodyAnchor(int height) {
		return new LifelineBodyAnchor(this, height);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
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

	public int getDefaultAnchorDistance() {
		return defaultAnchorDistance;
	}

	public void setDefaultAnchorDistance(int distance) {
		this.defaultAnchorDistance = distance;
	}

}
