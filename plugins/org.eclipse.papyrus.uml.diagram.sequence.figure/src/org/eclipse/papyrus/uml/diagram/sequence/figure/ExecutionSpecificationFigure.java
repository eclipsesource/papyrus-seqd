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
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.AnchorParser.AnchorKind;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.BorderAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ExecutionSpecificationEndAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ExecutionSpecificationSideAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ExecutionSpecificationStartAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.figure.anchors.ISequenceAnchor;

public class ExecutionSpecificationFigure extends NodeFigure {
	@Override
	protected boolean useLocalCoordinates() {
		return true;
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
		RectangleFigure rectangle = new RectangleFigure();
		rectangle.setBounds(r);
		rectangle.paintFigure(graphics);
	}

	@Override
	public ConnectionAnchor getConnectionAnchor(String terminal) {
		if (terminal == null || terminal.isEmpty()) {
			return super.getConnectionAnchor(terminal);
		}

		AnchorParser anchorParser = AnchorParser.getInstance();
		try {
			AnchorKind anchorKind = anchorParser.getAnchorKind(terminal);
			switch (anchorKind) {
				//
				// Message anchors
				//
				case DISTANCE:
				case FIXED:
					return new ExecutionSpecificationSideAnchor(this, anchorParser.getDistance(terminal));
				case START:
					return new ExecutionSpecificationStartAnchor(this);
				case END:
					return new ExecutionSpecificationEndAnchor(this);
				//
				// Comment/constraint tether anchors
				//
				case BORDER:
					return new BorderAnchor(this, anchorParser.getBorder(terminal),
							anchorParser.getDistanceFromReference(terminal));
				default:
					throw new IllegalArgumentException("terminal: " + terminal); //$NON-NLS-1$
			}
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace(); // TODO Log
		}

		return super.getConnectionAnchor(terminal);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
		// We don't use fractional positioning of anchor
		PrecisionPoint pp = new PrecisionPoint(p);
		translateFromParent(pp);
		translateToRelative(pp);
		return createAnchor(pp);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
		// We don't use fractional positioning of anchor
		PrecisionPoint pp = new PrecisionPoint(p);
		translateFromParent(pp);
		translateToRelative(pp);
		return createAnchor(pp);
	}

	@Override
	protected ConnectionAnchor createAnchor(PrecisionPoint p) {
		if (p == null) {
			return createDefaultAnchor();
		}
		if (p.y() <= 1) {
			return new ExecutionSpecificationStartAnchor(this);
		}
		Rectangle rect = getBounds();
		if (p.y() >= (rect.height() - 1)) {
			return new ExecutionSpecificationEndAnchor(this);
		}
		if (p.x() <= (rect.width() / 2)) {
			return new ExecutionSpecificationSideAnchor(this, p.y());
		}
		if (p.x() > (rect.width() / 2)) {
			return new ExecutionSpecificationSideAnchor(this, p.y());
		}

		return super.createAnchor(p);
	}

	@Override
	public String getConnectionAnchorTerminal(ConnectionAnchor c) {
		if (c instanceof ISequenceAnchor) {
			return ((ISequenceAnchor)c).getTerminal();
		}
		return super.getConnectionAnchorTerminal(c);
	}
}
