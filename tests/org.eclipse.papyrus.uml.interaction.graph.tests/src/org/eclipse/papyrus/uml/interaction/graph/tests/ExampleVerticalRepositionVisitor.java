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

package org.eclipse.papyrus.uml.interaction.graph.tests;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.gmf.runtime.notation.util.NotationSwitch;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.graph.Visitor;

/**
 * An example graph visitor that repositions diagram views vertically by some
 * delta.
 *
 * @author Christian W. Damus
 */
public class ExampleVerticalRepositionVisitor extends NotationSwitch
		implements Visitor<Vertex> {

	private final Object doneToken = new Object();

	private final int deltaY;

	ExampleVerticalRepositionVisitor(int deltaY) {
		super();

		this.deltaY = deltaY;
	}

	@Override
	public void visit(Vertex element) {
		View view = element.getDiagramView();

		if (view != null) {
			doSwitch(view);
		}
	}

	@Override
	public Object caseShape(Shape shape) {
		// This is just an example, so we don't account for default x,y
		// positions
		// nor relative positioning in the coördinate space of a parent view
		LayoutConstraint constraint = shape.getLayoutConstraint();

		if (constraint != null) {
			// Try to move it
			doSwitch(constraint);
		}

		// In any case, there's nothing more general to do with this shape
		return doneToken;
	}

	@Override
	public Object caseLocation(Location location) {
		location.setY(location.getY() + deltaY);
		return doneToken;
	}

	@Override
	public Object caseEdge(Edge edge) {
		doSwitch(edge.getBendpoints());

		return doneToken;
	}

	@Override
	public Object caseRelativeBendpoints(RelativeBendpoints bendpoints) {
		// This is just an example for testing, so not trying to be correct

		@SuppressWarnings("unchecked")
		List<RelativeBendpoint> list = bendpoints.getPoints();
		list = list.stream().map(rbp -> new RelativeBendpoint( //
				rbp.getSourceX(), rbp.getSourceY() + deltaY, // source
				rbp.getTargetX(), rbp.getTargetY() + deltaY)) // target
				.collect(Collectors.toList());
		bendpoints.setPoints(list);

		return doneToken;
	}
}
