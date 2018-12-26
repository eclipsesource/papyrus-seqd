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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.locators;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.ObliqueRouter;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouter;

/**
 * Specific router used for
 */
@SuppressWarnings("restriction")
public class SelfMessageRouter extends ObliqueRouter implements OrthogonalRouter {

	private int space;

	public SelfMessageRouter(int space) {
		this.space = space;
	}

	@Override
	protected PointList calculateBendPoints(Connection conn) {
		ConnectionAnchor source = conn.getSourceAnchor();
		ConnectionAnchor target = conn.getTargetAnchor();

		PointList points = new PointList(4);

		if (source == null || target == null) {
			return points;
		}

		Point sourceLoc = source.getLocation(source.getReferencePoint());
		Point targetLoc = target.getLocation(target.getReferencePoint());

		// ensure the segment stays vertical and not oblique.
		int sourceX = sourceLoc.x();
		int targetX = targetLoc.x();
		int vSegmentX = Math.min(sourceX + space(), targetX + space());

		points.addPoint(sourceLoc);
		points.addPoint(new Point(vSegmentX, sourceLoc.y()));
		points.addPoint(new Point(vSegmentX, targetLoc.y()));
		points.addPoint(targetLoc);

		return points;
	}

	private int space() {
		return space;
	}

}
