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

package org.eclipse.papyrus.uml.diagram.sequence.figure.magnets;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.ToDoubleFunction;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.GroupRequest;

/**
 * A snap-to helper that snaps to magnets.
 */
public class SnapToMagnetsHelper extends SnapToHelper {

	private final IMagnetManager magnetManager;

	/**
	 * Initializes me with my edit-part and magnet manager.
	 *
	 * @param magnetManager
	 *            the magnet manager
	 */
	public SnapToMagnetsHelper(IMagnetManager magnetManager) {
		super();

		this.magnetManager = magnetManager;
	}

	@Override
	public int snapRectangle(Request request, int snapOrientation, PrecisionRectangle baseRect,
			PrecisionRectangle outRect) {

		int result = snapOrientation;

		// Don't snap multiple things
		if ((request instanceof GroupRequest) && (((GroupRequest)request).getEditParts().size() > 1)) {
			return result;
		}

		IFigure snapFigure = getSnapFigure(request);

		PrecisionRectangle inRect = baseRect.getPreciseCopy();
		PrecisionRectangle correction = new PrecisionRectangle();

		if ((result & HORIZONTAL) != 0) {
			double midY = (inRect.preciseY() + inRect.preciseBottom()) / 2.0;
			PrecisionPoint referenceLeft = new PrecisionPoint(inRect.preciseX(), midY);
			PrecisionPoint referenceRight = new PrecisionPoint(inRect.preciseRight(), midY);

			OptionalDouble xCorrection = getXCorrection(snapFigure, referenceLeft, referenceRight);

			if (xCorrection.isPresent()) {
				result &= ~HORIZONTAL;
				correction.setPreciseX(correction.preciseX() + xCorrection.getAsDouble());
			}
		}

		if ((result & VERTICAL) != 0) {
			double midX = (inRect.preciseX() + inRect.preciseRight()) / 2.0;
			PrecisionPoint referenceTop = new PrecisionPoint(midX, inRect.preciseY());
			PrecisionPoint referenceBottom = new PrecisionPoint(midX, inRect.preciseBottom());

			OptionalDouble yCorrection = getYCorrection(snapFigure, referenceTop, referenceBottom);

			if (yCorrection.isPresent()) {
				result &= ~VERTICAL;
				correction.setPreciseX(correction.preciseY() + yCorrection.getAsDouble());
			}
		}

		if ((result & WEST) != 0) {
			double midY = (inRect.preciseY() + inRect.preciseBottom()) / 2.0;
			PrecisionPoint referenceLeft = new PrecisionPoint(inRect.preciseX(), midY);
			OptionalDouble leftCorrection = getXCorrection(snapFigure, referenceLeft);

			if (leftCorrection.isPresent()) {
				result &= ~WEST;
				correction.setPreciseWidth(correction.preciseWidth() - leftCorrection.getAsDouble());
				correction.setPreciseX(correction.preciseX() + leftCorrection.getAsDouble());
			}
		}

		if ((result & EAST) != 0) {
			double midY = (inRect.preciseY() + inRect.preciseBottom()) / 2.0;
			PrecisionPoint referenceRight = new PrecisionPoint(inRect.preciseRight(), midY);
			OptionalDouble rightCorrection = getXCorrection(snapFigure, referenceRight);

			if (rightCorrection.isPresent()) {
				result &= ~EAST;
				correction.setPreciseWidth(correction.preciseWidth() + rightCorrection.getAsDouble());
			}
		}

		if ((result & NORTH) != 0) {
			double midX = (inRect.preciseX() + inRect.preciseRight()) / 2.0;
			PrecisionPoint referenceTop = new PrecisionPoint(midX, inRect.preciseY());
			OptionalDouble topCorrection = getYCorrection(snapFigure, referenceTop);

			if (topCorrection.isPresent()) {
				result &= ~NORTH;
				correction.setPreciseHeight(correction.preciseHeight() - topCorrection.getAsDouble());
				correction.setPreciseY(correction.preciseY() + topCorrection.getAsDouble());
			}
		}

		if ((result & SOUTH) != 0) {
			double midX = (inRect.preciseX() + inRect.preciseRight()) / 2.0;
			PrecisionPoint referenceBottom = new PrecisionPoint(midX, inRect.preciseBottom());
			OptionalDouble bottomCorrection = getYCorrection(snapFigure, referenceBottom);

			if (bottomCorrection.isPresent()) {
				result &= ~SOUTH;
				correction.setPreciseHeight(correction.preciseHeight() + bottomCorrection.getAsDouble());
			}
		}

		outRect.setPreciseX(outRect.preciseX() + correction.preciseX());
		outRect.setPreciseY(outRect.preciseY() + correction.preciseY());
		outRect.setPreciseWidth(outRect.preciseWidth() + correction.preciseWidth());
		outRect.setPreciseHeight(outRect.preciseHeight() + correction.preciseHeight());

		return result;
	}

	/**
	 * Obtain the figure, if any, to which a request pertains (this being the figure of the request's
	 * edit-part).
	 * 
	 * @param request
	 *            a request
	 * @return its figure to snap, or {@code null} if none
	 */
	IFigure getSnapFigure(Request request) {
		IFigure result = null;

		if (request instanceof GroupRequest) {
			@SuppressWarnings("unchecked")
			List<? extends EditPart> editParts = ((GroupRequest)request).getEditParts();
			if (!editParts.isEmpty() && (editParts.get(0) instanceof GraphicalEditPart)) {
				result = ((GraphicalEditPart)editParts.get(0)).getFigure();
			}
		}

		return result;
	}

	private OptionalDouble getXCorrection(IFigure snapFigure, PrecisionPoint a, PrecisionPoint b) {
		return getCorrection(snapFigure, a, b, Point::preciseX);
	}

	private OptionalDouble getYCorrection(IFigure snapFigure, PrecisionPoint a, PrecisionPoint b) {
		return getCorrection(snapFigure, a, b, Point::preciseY);
	}

	@SuppressWarnings("boxing")
	private OptionalDouble getCorrection(IFigure snapFigure, PrecisionPoint a, PrecisionPoint b,
			ToDoubleFunction<Point> coord) {

		// Don't snap a figure to itself
		Optional<IMagnet> aMagnet = magnetManager.getCapturingMagnet(a, IMagnet.ownedBy(snapFigure));
		Optional<IMagnet> bMagnet = magnetManager.getCapturingMagnet(b, IMagnet.ownedBy(snapFigure));

		// Which magnet is closer?
		OptionalDouble result;
		Optional<Double> aDistance = aMagnet.map(m -> m.distance(a));
		Optional<Double> bDistance = bMagnet.map(m -> m.distance(b));

		if (aDistance.isPresent() && bDistance.isPresent()) {
			result = (aDistance.get().compareTo(bDistance.get()) > 0)
					? OptionalDouble
							.of(coord.applyAsDouble(bMagnet.get().getLocation()) - coord.applyAsDouble(b))
					: OptionalDouble
							.of(coord.applyAsDouble(aMagnet.get().getLocation()) - coord.applyAsDouble(a));
		} else if (bDistance.isPresent()) {
			result = OptionalDouble
					.of(coord.applyAsDouble(bMagnet.get().getLocation()) - coord.applyAsDouble(b));
		} else if (aDistance.isPresent()) {
			result = OptionalDouble
					.of(coord.applyAsDouble(aMagnet.get().getLocation()) - coord.applyAsDouble(a));
		} else {
			result = OptionalDouble.empty();
		}

		return result;
	}

	private OptionalDouble getXCorrection(IFigure snapFigure, PrecisionPoint reference) {
		return getCorrection(snapFigure, reference, Point::preciseX);
	}

	private OptionalDouble getYCorrection(IFigure snapFigure, PrecisionPoint reference) {
		return getCorrection(snapFigure, reference, Point::preciseY);
	}

	private OptionalDouble getCorrection(IFigure snapFigure, PrecisionPoint reference,
			ToDoubleFunction<Point> coord) {

		// Don't snap a figure to itself
		Optional<IMagnet> magnet = magnetManager.getCapturingMagnet(reference, IMagnet.ownedBy(snapFigure));

		OptionalDouble result;

		if (magnet.isPresent()) {
			result = OptionalDouble
					.of(coord.applyAsDouble(magnet.get().getLocation()) - coord.applyAsDouble(reference));
		} else {
			result = OptionalDouble.empty();
		}

		return result;
	}

}
