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

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.GroupKind;
import org.eclipse.papyrus.uml.interaction.graph.Tag;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Message;

/**
 * A vertical nudge operation. A nudge moves an element up or down and all of its dependents with it. It does
 * not reorder any elements in the sequence.
 *
 * @author Christian W. Damus
 */
public class NudgeCommand extends ModelCommand<MElementImpl<?>> {

	private final int deltaY;

	private boolean following;

	/**
	 * Initializes me.
	 * 
	 * @param element
	 *            the element to be nudged up or down
	 * @param deltaY
	 *            the distance by which to nudge the {@code element}
	 */
	public NudgeCommand(MElementImpl<? extends Element> element, int deltaY) {
		this(element, deltaY, true);
	}

	/**
	 * Initializes me.
	 * 
	 * @param element
	 *            the element to be nudged up or down
	 * @param deltaY
	 *            the distance by which to nudge the {@code element}
	 * @param nudgeFollowing
	 *            <code>true</code> if following elements should be nudged as well, <code>false</code> if only
	 *            the given elements should be moved
	 */
	public NudgeCommand(MElementImpl<? extends Element> element, int deltaY, boolean nudgeFollowing) {
		super(element);
		this.following = nudgeFollowing;
		this.deltaY = deltaY;
	}

	@Override
	protected Command createCommand() {
		if (deltaY == 0) {
			return IdentityCommand.INSTANCE;
		}

		// Note that a move up is just a negative move down
		MoveDownVisitor moveDown = new MoveDownVisitor(deltaY);

		Vertex vertex = vertex();
		if (vertex != null) {
			// Visit this vertex
			vertex.accept(moveDown);

			if (following) {
				// And all following
				getGraph().walkAfter(vertex, moveDown);
			}
		}

		return moveDown.getResult();
	}

	//
	// Nested types
	//

	/**
	 * <em>Semantic Graph</em> visitor that computes the new locations for the visualizations of elements in
	 * the graph below the one that was selected by the user.
	 *
	 * @author Christian W. Damus
	 */
	private class MoveDownVisitor extends CommandBuildingVisitor<Vertex> {

		private final int delta;

		MoveDownVisitor(int delta) {
			super();

			this.delta = delta;
		}

		@SuppressWarnings("boxing")
		@Override
		protected void process(Vertex vertex) {
			Element element = vertex.getInteractionElement();
			View view = vertex.getDiagramView();
			if (view instanceof Shape) {
				// Move the shape down
				Shape shape = (Shape)view;
				int top = layoutHelper().getTop(shape);
				chain(layoutHelper().setTop(shape, top + delta));
			} else if (view instanceof Connector && element instanceof Message) {
				// Move connector anchors down
				Connector connector = (Connector)view;
				if (connector.getSourceAnchor() instanceof IdentityAnchor
						&& connector.getTargetAnchor() instanceof IdentityAnchor) {
					IdentityAnchor source = (IdentityAnchor)connector.getSourceAnchor();
					Shape sourceOn = (Shape)connector.getSource();
					IdentityAnchor target = (IdentityAnchor)connector.getTargetAnchor();
					Shape targetOn = (Shape)connector.getTarget();

					Message message = (Message)element;
					int sourceY = layoutHelper().getYPosition(source, sourceOn);
					int targetY = layoutHelper().getYPosition(target, targetOn);

					chain(layoutHelper().setYPosition(source, sourceOn, sourceY + delta));

					Vertex targetVtx = vertex.graph().vertex(message.getReceiveEvent());
					if (targetVtx.hasTag(Tag.LIFELINE_CREATION)) {
						// Move the lifeline down
						Optional<Vertex> lifeline = targetVtx.group(GroupKind.LIFELINE)
								.map(Vertex.class::cast);
						Optional<Shape> shape = lifeline.map(Vertex::getDiagramView).map(Shape.class::cast);
						shape.ifPresent(s -> chain(
								layoutHelper().setTop(shape.get(), layoutHelper().getTop(s) + delta)));
					} else if (targetVtx.hasTag(Tag.LIFELINE_DESTRUCTION)) {
						// Move Destruction Shape
						Optional<Shape> shape = Optional.ofNullable(targetVtx.getDiagramView())
								.filter(Shape.class::isInstance).map(Shape.class::cast);
						shape.ifPresent(s -> {
							chain(layoutHelper().setTop(s, layoutHelper().getTop(shape.get()) + delta));
						});
					} else {
						// Ordinary message end. Move it down
						chain(layoutHelper().setYPosition(target, targetOn, targetY + delta));
					}
				}
			} else if (vertex.hasTag(Tag.EXECUTION_FINISH)) {
				// Stretch the execution specification, but only if we didn't nudge the top
				Optional<Vertex> exec = vertex.predecessor(Tag.EXECUTION_FINISH);
				if (!visited(exec.flatMap(v -> v.predecessor(Tag.EXECUTION_START)))) {
					Optional<Shape> shape = exec.map(Vertex::getDiagramView).map(Shape.class::cast);
					Optional<Size> sizeConstraint = shape.map(Shape::getLayoutConstraint)
							.filter(Size.class::isInstance).map(Size.class::cast);
					sizeConstraint.ifPresent(size -> chain(SetCommand.create(getEditingDomain(), size,
							NotationPackage.Literals.SIZE__HEIGHT, size.getHeight() + delta)));
				}
			} else if (view instanceof Diagram) {
				// Can't nudge the diagram, of course
				chain(UnexecutableCommand.INSTANCE);
			}
		}
	}

}
