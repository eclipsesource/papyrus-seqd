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

package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.GraphPredicates;
import org.eclipse.papyrus.uml.interaction.graph.Group;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A horizontal nudge operation. A nudge moves a lifeline right or left and all of its dependents with it. It
 * does not reorder any elements in the sequence.
 *
 * @author Christian W. Damus
 */
public class NudgeHorizontallyCommand extends ModelCommand<MLifelineImpl> {

	private final int deltaX;

	/**
	 * Initializes me.
	 * 
	 * @param lifeline
	 *            the lifeline to be nudged right or left
	 * @param deltaX
	 *            the distance by which to nudge the {@code lifeline}
	 */
	public NudgeHorizontallyCommand(MLifelineImpl lifeline, int deltaX) {
		super(lifeline);

		this.deltaX = deltaX;
	}

	@Override
	protected Command createCommand() {
		if (deltaX == 0) {
			return IdentityCommand.INSTANCE;
		}

		// Note that a move left is just a negative move right
		MoveRightVisitor moveRight = new MoveRightVisitor(deltaX);

		Vertex vertex = vertex();
		if (vertex != null) {
			// Visit this vertex
			vertex.accept(moveRight);

			// All lifelines to the right of the one we're nudging
			List<Vertex> lifelines = vertex.graph().initial().immediateSuccessors()
					.filter(GraphPredicates.isA(UMLPackage.Literals.LIFELINE)).sequential()
					.collect(Collectors.toList());
			int referencePoint = lifelines.indexOf(vertex);
			lifelines.subList(0, referencePoint).forEach(moveRight::markVisited);
			lifelines.subList(referencePoint + 1, lifelines.size()).forEach(moveRight::visit);

			// And all following (skipping lifelines already visited or marked as such)
			getGraph().walkAfter(vertex, moveRight);
		}

		return moveRight.getResult();
	}

	//
	// Nested types
	//

	/**
	 * <em>Semantic Graph</em> visitor that computes the new locations for the visualizations of elements in
	 * the graph to the right of the one that was selected by the user.
	 *
	 * @author Christian W. Damus
	 */
	private class MoveRightVisitor extends CommandBuildingVisitor<Vertex> {

		private final int delta;

		MoveRightVisitor(int delta) {
			super();

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
						int left = layoutHelper().getLeft(shape);
						chain(layoutHelper().setLeft(shape, left + delta));
					}
					break;
			}
		}
	}

}
