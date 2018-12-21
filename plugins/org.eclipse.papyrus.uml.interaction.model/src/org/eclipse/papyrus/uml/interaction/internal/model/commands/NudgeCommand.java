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

import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.mapToObj;

import java.util.HashSet;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.GroupKind;
import org.eclipse.papyrus.uml.interaction.graph.Tag;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.NudgeKind;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;

/**
 * A vertical nudge operation. A nudge moves an element up or down and all of its dependents with it. It does
 * not reorder any elements in the sequence.
 *
 * @author Christian W. Damus
 */
public class NudgeCommand extends ModelCommandWithDependencies<MElementImpl<?>> {

	private final int deltaY;

	private NudgeKind mode;

	/**
	 * Initializes me.
	 * 
	 * @param element
	 *            the element to be nudged up or down
	 * @param deltaY
	 *            the distance by which to nudge the {@code element}
	 */
	public NudgeCommand(MElementImpl<? extends Element> element, int deltaY) {
		this(element, deltaY, NudgeKind.FOLLOWING);
	}

	/**
	 * Initializes me.
	 * 
	 * @param element
	 *            the element to be nudged up or down
	 * @param deltaY
	 *            the distance by which to nudge the {@code element}
	 * @param mode
	 *            the consequential nudges to compute, also (include following/preceding elements or only the
	 *            original {@code element} to be nudged)
	 */
	public NudgeCommand(MElementImpl<? extends Element> element, int deltaY, NudgeKind mode) {
		super(element);

		this.mode = mode;
		this.deltaY = deltaY;
	}

	@Override
	protected Command doCreateCommand() {
		if (deltaY == 0) {
			return IdentityCommand.INSTANCE;
		}

		// Note that a move up is just a negative move down
		MoveDownVisitor moveDown = new MoveDownVisitor(deltaY);

		Vertex vertex = vertex();
		if (vertex != null) {
			// Visit this vertex
			vertex.accept(moveDown);

			switch (mode) {
				case FOLLOWING:
					// And all following
					getGraph().walkAfter(vertex, moveDown);
					break;
				case PRECEDING:
					// And all the preceding, except for elements that cannot be nudged
					Predicate<Vertex> visitorFilter = this::nudgeByConsequence;

					// And executions whose start ends are being nudged but not their
					// bottoms (so we stretch them)
					Predicate<Vertex> topStretching = this::isExecutionStretchingAtTop;
					visitorFilter = visitorFilter.and(topStretching.negate());

					getGraph().walkBefore(vertex, visitorFilter, moveDown);
					break;
				default:
					// Nothing further
					break;
			}
		}

		return moveDown.getResult();
	}

	private boolean nudgeByConsequence(Vertex vertex) {
		// Don't attempt to nudge lifeline heads and the diagram, itself, in the precedents of a nudge
		return !(vertex.getInteractionElement() instanceof Lifeline) //
				&& !(vertex.getInteractionElement() instanceof Interaction);
	}

	private boolean isExecutionStretchingAtTop(Vertex vertex) {
		boolean result = false;

		if (vertex.getInteractionElement() instanceof ExecutionSpecification) {
			// We are stretching this if we will nudge the top but not the bottom
			result = (mode == NudgeKind.PRECEDING)
					&& vertex.successor(Tag.EXECUTION_FINISH).filter(vertex()::precedes).isPresent();
		}

		return result;
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

		private Set<Shape> movingShapes = new HashSet<>();

		MoveDownVisitor(int delta) {
			super();

			this.delta = delta;
		}

		@Override
		protected void process(Vertex vertex) {
			Element element = vertex.getInteractionElement();
			View view = vertex.getDiagramView();
			if (view instanceof Shape) {
				// Move the shape down
				Shape shape = (Shape)view;
				if (!isShapeOnMovingShape(shape)) {
					int top = layoutHelper().getTop(shape);
					chain(layoutHelper().setTop(shape, top + delta));
				}

				// Whether we moved it, or it's moving indirectly, it's moving
				movingShapes.add(shape);
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

					boolean sourceSkipped = skipMove(sourceOn);
					if (!sourceSkipped) {
						chain(layoutHelper().setYPosition(source, sourceOn, sourceY + delta));
					}

					Vertex targetVtx = vertex.graph().vertex(message.getReceiveEvent());
					boolean targetSkipped = skipMove(targetOn);
					if (targetVtx.hasTag(Tag.LIFELINE_CREATION) && !targetSkipped) {
						// Move the lifeline down
						Optional<Vertex> lifeline = targetVtx.group(GroupKind.LIFELINE)
								.map(Vertex.class::cast);
						// Record that we're moving this
						lifeline.map(Vertex::getDiagramView).map(diagramHelper()::getLifelineBodyShape)
								.ifPresent(movingShapes::add);
						Optional<Shape> shape = lifeline.map(Vertex::getDiagramView).map(Shape.class::cast);
						shape.ifPresent(s -> chain(
								layoutHelper().setTop(shape.get(), layoutHelper().getTop(s) + delta)));
					} else if (targetVtx.hasTag(Tag.LIFELINE_DESTRUCTION) && !targetSkipped) {
						// Move Destruction Shape if it's not moved implicitly by the lifeline it's on
						Optional<Shape> shape = Optional.ofNullable(targetVtx.getDiagramView())
								.filter(Shape.class::isInstance).map(Shape.class::cast);
						shape.ifPresent(s -> {
							chain(layoutHelper().setTop(s, layoutHelper().getTop(shape.get()) + delta));
						});
					} else if (!targetSkipped) {
						// Ordinary message end. Move it down
						chain(layoutHelper().setYPosition(target, targetOn, targetY + delta));
					} else if (sourceSkipped) {
						// If we skipped moving both ends because they are attached to things that
						// are moving, that means we are implicitly moved.
						// Ensure that the nudge does not end up unexecutable by not producing any
						// delegate command, so drop in a no-op now
						chain(IdentityCommand.INSTANCE);
					}
				}
			} else if (vertex.hasTag(Tag.EXECUTION_START) && !isMessageEndNudgeCommand(element)) {
				// Stretch the execution specification, but only if we aren't nudging the bottom
				Optional<Vertex> exec = vertex.successor(Tag.EXECUTION_START);
				if (/* Not already visited */ !visited(exec)
						&& /* Not to be visited */ (mode != NudgeKind.FOLLOWING)) {

					exec.map(Vertex::getDiagramView).map(Shape.class::cast).ifPresent(shape -> {
						LayoutHelper layout = layoutHelper();
						int top = layout.getTop(shape);
						int bottom = layout.getBottom(shape);

						// Move the top accordingly
						chain(layout.setTop(shape, top + delta));
						// But pin the bottom where it is, to effect the stretch
						chain(layout.setBottom(shape, bottom - delta));

						// And things attached to this shape that are not supposed to be moving
						// need to be nudged in the opposite direction
						spannedVerticesNotNudged(exec.get()).map(this::nudgeBack).forEach(this::chain);
					});
				}
			} else if (vertex.hasTag(Tag.EXECUTION_FINISH) && !isMessageEndNudgeCommand(element)) {
				// Stretch the execution specification, but only if we didn't nudge the top
				Optional<Vertex> exec = vertex.predecessor(Tag.EXECUTION_FINISH);
				if (/* Not already visited */ !visited(exec)
						&& /* Not to be visited */ (mode != NudgeKind.PRECEDING)) {

					exec.map(Vertex::getDiagramView).map(Shape.class::cast).ifPresent(shape -> {
						LayoutHelper layout = layoutHelper();
						int bottom = layout.getBottom(shape);

						// Move the bottom to effect the stretch
						chain(layout.setBottom(shape, bottom + delta));
					});
				}
			} else if (view instanceof Diagram) {
				// Can't nudge the diagram, of course
				chain(bomb());
			} else if (isMessageEndNudgeCommand(element)) {
				/*
				 * If this is an explicit nudge command for a message end, make sure that this end is nudged,
				 * but not the opposite end.
				 */
				Optional<MMessageEnd> end = as(getTarget().getInteraction().getElement(element),
						MMessageEnd.class);
				Optional<Connector> connector = end.map(MMessageEnd::getOwner)//
						.map(MMessage::getDiagramView)//
						.filter(Optional::isPresent).map(Optional::get);
				if (connector.isPresent()) {
					MMessageEnd end_ = end.get();
					IdentityAnchor anchor = (IdentityAnchor)(end_.isSend() ? connector.get().getSourceAnchor()
							: connector.get().getTargetAnchor());
					Shape anchorOn = (Shape)(end_.isSend() ? connector.get().getSource()
							: connector.get().getTarget());
					int targetY = layoutHelper().getYPosition(anchor, anchorOn);
					chain(layoutHelper().setYPosition(anchor, anchorOn, targetY + delta));
				}
			}
		}

		private boolean isMessageEndNudgeCommand(Element element) {
			if (element != NudgeCommand.this.getTarget().getElement()) {
				return false; // nudge command was created for different element
			}
			return element instanceof MessageEnd;
		}

		/**
		 * Only move message ends when anchored on a shape that isn't already moving, as the assumption is
		 * that it just follows the shape it's anchored on in that case.
		 */
		private boolean skipMove(Shape shape) {
			// Either I have direct knowledge of this shape moving
			return movingShapes.contains(shape)
					// Or it will be moving but we just haven't visited it, yet
					|| willBeMoving(shape)
					// Or the context has a nudge command for this shape
					|| hasDependency(shape, NudgeCommand.class)
					// Or the shape is (recursively) on a shape being nudged
					|| isShapeOnMovingShape(shape);
		}

		/**
		 * Is a shape on (a child of) a shape that is already being nudged, and so doesn't need to be nudged,
		 * itself?
		 * 
		 * @param shape
		 *            a shape to be nudged (perhaps)
		 * @return whether it is indirectly being nudged by being a child of a shape being nudged
		 */
		private boolean isShapeOnMovingShape(Shape shape) {
			return movingShapes.contains(shape.eContainer());
		}

		private boolean willBeMoving(Shape shape) {
			boolean result;

			// If we can't find a vertex, assume it won't be moving
			Optional<Vertex> vertex = vertex().graph().vertex(shape);

			switch (mode) {
				case PRECEDING:
					// The simple case is a shape that will be moved in the normal way
					Predicate<Vertex> willMove = vertex()::succeeds;
					// Or, look for an execution specification that is being stretched at the top,
					// which implicitly is a move even though we aren't nudging the execution, itself
					willMove = willMove.or(NudgeCommand.this::isExecutionStretchingAtTop);

					result = vertex.map(willMove::test).orElse(Boolean.FALSE).booleanValue();
					break;
				default:
					result = false;
			}

			return result;
		}

		/**
		 * Obtain the vertices spanned by an {@code execution} vertex that are neither
		 * <ul>
		 * <li>also being nudged nor</li>
		 * <li>the vertex encapsulating the start or finish occurrence nor</li>
		 * <li>ahead of our original vertex being nudged in the direction that we're iterating the graph</li>
		 * </ul>
		 * 
		 * @param execution
		 *            the vertex of an execution specification
		 * @return the spanned vertices that are not already or going to be visited
		 */
		private Stream<Vertex> spannedVerticesNotNudged(Vertex execution) {
			Stream<Vertex> result;

			if (!execution.isGroup()) {
				result = Stream.empty();
			} else {
				// Not the start or finish, though, which are pinned to the ends of the
				// execution and so not independently nudgeable (at least, not fir our purpose)
				Predicate<Vertex> isExec = execution::equals;
				Predicate<Vertex> isStart = v -> v.successor(Tag.EXECUTION_START).filter(isExec).isPresent();
				Predicate<Vertex> isFinish = v -> v.predecessor(Tag.EXECUTION_FINISH).filter(isExec)
						.isPresent();
				Predicate<Vertex> isBeingNudged = this::visited;
				isBeingNudged = isBeingNudged.or(this::willBeVisited);

				result = execution.toGroup().members() //
						.filter(isBeingNudged.or(isStart).or(isFinish).negate());
			}

			return result;
		}

		/**
		 * Query whether a vertex will be visited later on in the current nudge calculation.
		 * 
		 * @param vertex
		 *            a vertex
		 * @return whether it will be visited
		 */
		private boolean willBeVisited(Vertex vertex) {
			switch (mode) {
				case PRECEDING:
					return vertex.precedes(vertex());
				case FOLLOWING:
					return vertex.succeeds(vertex());
				default:
					return false;
			}
		}

		/**
		 * Create a command to nudge a {@code vertex} back against the prevailing nudge direction.
		 * 
		 * @param vertex
		 *            a vertex to back-nudge
		 * @return the command
		 */
		private Command nudgeBack(Vertex vertex) {
			OptionalInt top = layoutHelper().getTop(vertex);
			return mapToObj(top, y -> layoutHelper().setTop(vertex, y - delta)).orElse(bomb());
		}

	}

}
