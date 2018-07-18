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
 *   Antonio Campesino (Ericsson AB) - Extracted to its own class  
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Group;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;

/**
 * <em>Semantic Graph</em> visitor that computes the new locations for the visualizations of elements in the
 * graph to the right of the one that was selected by the user.
 *
 * @author Christian W. Damus
 */
class MoveRightVisitor extends CommandBuildingVisitor<Vertex> {

	/**
	 * 
	 */
	private ModelCommand<?> horizontallyCommand;

	private final int delta;

	MoveRightVisitor(ModelCommand<?> horizontallyCommand, int delta) {
		super();
		this.horizontallyCommand = horizontallyCommand;

		this.delta = delta;
	}

	@Override
	protected void process(Vertex vertex) {
		// Other kinds of elements don't have to move because they are anchored
		// to one of these
		switch (Group.kind(vertex)) {
			case LIFELINE:
			case COMBINED_FRAGMENT:
				View view = vertex.getDiagramView();
				if (view instanceof Shape) {
					// Move the shape down
					Shape shape = (Shape)view;
					int left = this.horizontallyCommand.layoutHelper().getLeft(shape);
					chain(this.horizontallyCommand.layoutHelper().setLeft(shape, left + delta));
				}
				break;
		}
	}
}
