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
package org.eclipse.papyrus.uml.diagram.sequence.figure.border;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.OneLineBorder;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;

/**
 * <p>
 * A rectangle border, with the bottom-right corner optionally cut. This border can be used with a
 * {@link WrappingLabel}, in which case it will take the actual text location and size into account. For other
 * figures, the entire bounds of the figure will be taken into account (Typically, this means that the border
 * will take the entire width of its parent).
 * </p>
 * <p>
 * If the next sibling is a Compartment, you'll probably want to remove the {@link PositionConstants#TOP TOP}
 * {@link OneLineBorder} that is installed on that compartment by default, for best results.
 * </p>
 */
// Note:
// This border has been tested with our main use case, where the text is located on the top-left corner
// of a Node, and is combined with a MarginBorder (To add a left-padding).
// All combinations of Border and Alignments have *not* been implemented and will most likely cause
// issues.
// The FrameBorder currently supports custom left-padding (But not right-padding), and horizontal text
// alignments (Left/Center/Right)
// It partially supports wider line widths (up to a few pixels), but will start failing for bigger values.
public class FrameBorder extends AbstractBorder {

	private int lineWidth;

	private int cornerHeight;

	private int cornerWidth;

	/**
	 * <p>
	 * Builds a new FrameBorder with default values.
	 * </p>
	 * <p>
	 * The default values are <code>1 pixel</code> for the line width, and the corner is a 45° angle which
	 * height and width is half of the figure height.
	 * </p>
	 */
	public FrameBorder() {
		this(1, -1, -1);
	}

	/**
	 * @param lineWidth
	 *            The border line width, in pixels
	 * @param cornerHeight
	 *            The height of the bent corner, in pixels, or -1 for a default height. A value of 0 indicates
	 *            a rectangle frame.
	 * @param cornerWidth
	 *            The width of the bent corner, in pixels, or -1 for a default width. A value of 0 indicates a
	 *            rectangle frame.
	 */
	public FrameBorder(int lineWidth, int cornerHeight, int cornerWidth) {
		this.lineWidth = lineWidth;
		this.cornerHeight = cornerHeight;
		this.cornerWidth = cornerWidth;
	}

	@Override
	public Insets getInsets(IFigure figure) {
		// Reserve space on the right, so that the corner doesn't overlap with text
		// Reserve the line width on the bottom
		return new Insets(0, 0, lineWidth, lineWidth + getCornerWidth(figure));
	}

	private int getCornerHeight(IFigure figure) {
		return cornerHeight < 0 ? getFigureBounds(figure).height / 2 : cornerHeight;
	}

	private int getCornerWidth(IFigure figure) {
		return cornerWidth < 0 ? getFigureBounds(figure).height / 2 : cornerWidth;
	}

	private Rectangle getFigureBounds(IFigure figure) {
		Rectangle figureBounds = figure.getBounds().getCopy();

		return figureBounds;
	}

	private Rectangle getFigureTextBounds(IFigure figure) {
		Rectangle figureBounds = getFigureBounds(figure);
		if (figure instanceof WrappingLabel) {
			Rectangle textBounds = ((WrappingLabel)figure).getTextBounds();
			figureBounds.width = textBounds.width;
			figureBounds.translate(textBounds.getTopLeft());
			Insets insets = figure.getBorder().getInsets(figure);
			figureBounds.expand(insets.left, 0);
		}

		return figureBounds;
	}

	@Override
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		Rectangle figureBounds = getFigureTextBounds(figure);

		Polyline polyline = new Polyline();
		PointList list = new PointList();

		int yDelta = figureBounds.y + lineWidth; // Do not draw beyond the figure area

		int width = figureBounds.x + figureBounds.width + getCornerWidth(figure);
		int height = figureBounds.y + figureBounds.height - yDelta;

		list.addPoint(width, 0);
		list.addPoint(width, getCornerHeight(figure));
		list.addPoint(width - getCornerWidth(figure), height);
		list.addPoint(0, height);

		polyline.setLineWidth(lineWidth);

		polyline.setPoints(list);
		polyline.setSize(figureBounds.getSize());
		polyline.setLocation(new Point(0, 0));

		graphics.pushState();

		try {
			polyline.paint(graphics);
		} finally {
			graphics.popState();
		}
	}

}
