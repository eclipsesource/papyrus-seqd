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

package org.eclipse.papyrus.uml.interaction.internal.model.spi.impl;

import static org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil.invertSingle;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.util.NotationSwitch;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil;
import org.eclipse.papyrus.uml.interaction.graph.util.Suppliers;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Default implementation of the {@link LayoutHelper} SPI.
 *
 * @author Christian W. Damus
 */
public class DefaultLayoutHelper implements LayoutHelper {

	static final int DEFAULT_X = ((Integer)NotationPackage.Literals.LOCATION__X.getDefaultValue()).intValue();

	static final int DEFAULT_Y = ((Integer)NotationPackage.Literals.LOCATION__Y.getDefaultValue()).intValue();

	static final int DEFAULT_WIDTH = ((Integer)NotationPackage.Literals.SIZE__WIDTH.getDefaultValue())
			.intValue();

	static final int DEFAULT_HEIGHT = ((Integer)NotationPackage.Literals.SIZE__HEIGHT.getDefaultValue())
			.intValue();

	static final int DEFAULT_TOP = DEFAULT_Y;

	static final int DEFAULT_BOTTOM = DEFAULT_TOP + DEFAULT_HEIGHT;

	static final int DEFAULT_LEFT = DEFAULT_X;

	static final int DEFAULT_RIGHT = DEFAULT_LEFT + DEFAULT_WIDTH;

	public static final Pattern IDENTITY_ANCHOR_PATTERN = Pattern
			.compile("(left;|right;|west;|east;)?(-?\\d+)"); //$NON-NLS-1$

	private static final Pattern EXEC_START_FINISH_ANCHOR_PATTERN = Pattern.compile("(start)|(end)"); //$NON-NLS-1$

	private final Supplier<LayoutConstraints> layoutConstraints;

	private final EditingDomain editingDomain;

	/**
	 * Initializes me with my constraints and contextual editing domain.
	 * 
	 * @param editingDomain
	 *            my editing domain
	 * @param layoutConstraints
	 *            my constraints supplier
	 */
	public DefaultLayoutHelper(EditingDomain editingDomain, Supplier<LayoutConstraints> layoutConstraints) {
		super();

		this.editingDomain = editingDomain;
		this.layoutConstraints = Suppliers.memoize(layoutConstraints);
	}

	@Override
	public OptionalInt getTop(Vertex v) {
		OptionalInt result = OptionalInt.empty();

		View view = v.getDiagramView();
		if (view instanceof Shape) {
			int top = getTop((Shape)view);
			result = (top == DEFAULT_TOP) ? OptionalInt.empty() : OptionalInt.of(top);
		} else if (view instanceof Edge) {
			// For anchors, which are points, the top is the bottom
			Edge edge = (Edge)view;
			// All edges in a sequence diagram slope down if they are not horizontal
			int anchorY = getYPosition(edge.getSourceAnchor(), (Shape)edge.getSource());

			if (anchorY == DEFAULT_BOTTOM) {
				result = OptionalInt.empty();
			} else {
				result = OptionalInt.of(anchorY);
			}
		} else {
			Anchor anchor = findAnchor(v);
			// For anchors, which are points, the top is the bottom
			if (anchor != null) {
				// But anchor position is relative to the attached shape
				int bottom = getYPosition(anchor, anchoredShape(anchor));

				if (bottom == DEFAULT_BOTTOM) {
					result = OptionalInt.empty();
				} else {
					result = OptionalInt.of(bottom);
				}
			} else if (v.getInteractionElement() instanceof OccurrenceSpecification) {
				// Maybe it's an execution occurrence start/finish?
				OccurrenceSpecification occurrence = (OccurrenceSpecification)v.getInteractionElement();
				Optional<ExecutionSpecification> exec = getExecution(occurrence);
				Optional<Vertex> execV = exec.map(v.graph()::vertex);
				result = execV.map(vtx -> occurrence == exec.get().getStart() ? getTop(vtx) : getBottom(vtx))
						.orElseGet(OptionalInt::empty);
			}
		}

		return result;
	}

	@Override
	public int getTop(Shape shape) {
		int result = DEFAULT_TOP;

		// if the shape is located by a border item locator, we may only have access to the bounds on figure,
		// no layout constraint on the shape (or at least not the location, only size)
		if (isLifelineBody(shape)) {
			// then the top position relative to the parent (header) is the bottom center part of the parent
			return getBottom((Shape)ViewUtil.getContainerView(shape));
		} else {
			LayoutConstraint constraint = shape.getLayoutConstraint();
			if (constraint != null) {
				result = getTopFunction().applyAsInt(constraint);
			}
		}

		if (result != DEFAULT_TOP) {
			// Its position is relative to the containing shape
			result = toAbsoluteY(shape, result);
		}

		return result;
	}

	@Override
	public int toAbsoluteX(Shape shape, View parent, int x) {
		EObject containerView = parent;
		int compartmentX = 0;
		if (containerView instanceof Compartment) {
			// It's in a shape compartment. Where is it in the parent shape?
			Compartment compartment = (Compartment)containerView;
			compartmentX = getConstraints().getXOffset(compartment);
			containerView = compartment.eContainer();
		}

		int result = x;

		if (containerView instanceof Shape) {
			int relativeLeft = getLeft((Shape)containerView);
			result = relativeLeft == DEFAULT_LEFT ? DEFAULT_LEFT : relativeLeft + compartmentX + result;
		}

		return result;
	}

	@Override
	public int toRelativeX(Shape shape, View parent, int x) {
		EObject containerView = parent;
		int compartmentX = 0;
		if (containerView instanceof Compartment) {
			// It's in a shape compartment. Where is it in the parent shape?
			Compartment compartment = (Compartment)containerView;
			compartmentX = getConstraints().getXOffset(compartment);
			containerView = compartment.eContainer();
		}

		int result = x;

		if (containerView instanceof Shape) {
			int relativeLeft = getLeft((Shape)containerView);
			result = relativeLeft == DEFAULT_LEFT ? DEFAULT_LEFT : result - compartmentX - relativeLeft;
		}

		return result;
	}

	@Override
	public int toAbsoluteY(Shape shape, View parent, int y) {
		EObject containerView = parent;
		int compartmentY = 0;
		if (containerView instanceof Compartment) {
			// It's in a shape compartment. Where is it in the parent shape?
			Compartment compartment = (Compartment)containerView;
			compartmentY = getConstraints().getYOffset(compartment);
			containerView = compartment.eContainer();
		}

		int result = y;

		if (containerView instanceof Shape) {
			int relativeTop = getTop((Shape)containerView);
			result = relativeTop == DEFAULT_TOP ? DEFAULT_TOP : relativeTop + compartmentY + result;
		}

		return result;
	}

	@Override
	public int toRelativeY(Shape shape, View parent, int y) {
		EObject containerView = parent;
		int compartmentY = 0;
		if (containerView instanceof Compartment) {
			// It's in a shape compartment. Where is it in the parent shape?
			Compartment compartment = (Compartment)containerView;
			compartmentY = getConstraints().getYOffset(compartment);
			containerView = compartment.eContainer();
		}

		int result = y;

		if (containerView instanceof Shape) {
			int relativeTop = getTop((Shape)containerView);
			result = relativeTop == DEFAULT_TOP ? DEFAULT_TOP : result - compartmentY - relativeTop;
		}

		return result;
	}

	private static boolean isLifelineBody(View view) {
		return ViewTypes.LIFELINE_BODY.equals(view.getType());
	}

	@SuppressWarnings("boxing")
	private ToIntFunction<EObject> getTopFunction() {
		NotationSwitch topSwitch = new NotationSwitch() {
			@Override
			public Object caseLocation(Location location) {
				return location.eIsSet(NotationPackage.Literals.LOCATION__Y) ? location.getY() : DEFAULT_TOP;
			}

			@Override
			public Object caseIdentityAnchor(IdentityAnchor anchor) {
				int result = DEFAULT_TOP;
				String id = anchor.getId();

				if (id != null) {
					Matcher m = IDENTITY_ANCHOR_PATTERN.matcher(id);
					if (m.matches()) {
						result = Integer.parseInt(m.group(2));
					} else {
						m = EXEC_START_FINISH_ANCHOR_PATTERN.matcher(id);
						if (m.matches()) {
							if (m.group(1) != null) {
								// It's the start anchor
								result = 0;
							} else {
								// It's the finish anchor
								LayoutConstraint layout = anchoredShape(anchor).getLayoutConstraint();
								if (layout instanceof Size) {
									// Yes, this too could be DEFAULT_BOTTOM!
									result = ((Size)layout).getHeight();
								}
							}
						}
					}
				}

				return result;
			}

			@Override
			public Object defaultCase(EObject object) {
				return DEFAULT_TOP;
			}
		};

		return object -> (Integer)topSwitch.doSwitch(object);
	}

	@Override
	public OptionalInt getBottom(Vertex v) {
		OptionalInt result = OptionalInt.empty();

		View view = v.getDiagramView();
		if (view instanceof Shape) {
			int bottom = getBottom((Shape)view);
			result = (bottom == DEFAULT_BOTTOM) ? OptionalInt.empty() : OptionalInt.of(bottom);
		} else if (view instanceof Edge) {
			Edge edge = (Edge)view;
			// All edges in a sequence diagram slope down if they are not horizontal
			int anchorY = getYPosition(edge.getTargetAnchor(), (Shape)edge.getTarget());

			if (anchorY == DEFAULT_BOTTOM) {
				result = OptionalInt.empty();
			} else {
				result = OptionalInt.of(anchorY);
			}
		} else {
			Anchor anchor = findAnchor(v);
			if (anchor != null) {
				// But anchor position is relative to the attached shape
				int bottom = getYPosition(anchor, anchoredShape(anchor));

				if (bottom == DEFAULT_BOTTOM) {
					result = OptionalInt.empty();
				} else {
					result = OptionalInt.of(bottom);
				}
			} else if (v.getInteractionElement() instanceof OccurrenceSpecification) {
				// Maybe it's an execution occurrence start/finish?
				OccurrenceSpecification occurrence = (OccurrenceSpecification)v.getInteractionElement();
				Optional<ExecutionSpecification> exec = getExecution(occurrence);
				Optional<Vertex> execV = exec.map(v.graph()::vertex);
				result = execV.map(vtx -> occurrence == exec.get().getStart() ? getTop(vtx) : getBottom(vtx))
						.orElseGet(OptionalInt::empty);
			}
		}

		return result;
	}

	/**
	 * Get the execution specification, if any, that is started or finished by an {@code occurrence}
	 * specification.
	 * 
	 * @param occurrence
	 *            an occurrence specification
	 * @return the execution that it starts or finishes, if any
	 */
	static Optional<ExecutionSpecification> getExecution(OccurrenceSpecification occurrence) {
		Optional<ExecutionSpecification> result = invertSingle(occurrence,
				UMLPackage.Literals.EXECUTION_SPECIFICATION__START, ExecutionSpecification.class);
		if (!result.isPresent()) {
			result = invertSingle(occurrence, UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH,
					ExecutionSpecification.class);
		}
		return result;
	}

	@Override
	public int getBottom(Shape shape) {
		int result = DEFAULT_BOTTOM;

		LayoutConstraint constraint = shape.getLayoutConstraint();
		if (constraint != null) {
			result = getBottomFunction().applyAsInt(constraint);
		}

		if (result != DEFAULT_BOTTOM) {
			// Its position is relative to the containing shape
			result = toAbsoluteY(shape, result);
		}

		return result;
	}

	@Override
	public int getYPosition(Anchor anchor, Shape anchoredOn) {
		// Anchors are point locations, so the bottom is the top is the Y position.
		// But anchor position is relative to the attached shape
		int relativeTop = getTop(anchoredOn);
		if (relativeTop == DEFAULT_TOP) {
			return DEFAULT_BOTTOM;
		} else {
			int result = getBottomFunction().applyAsInt(anchor);
			if (result != DEFAULT_BOTTOM) {
				result = relativeTop + result;
			}
			return result;
		}
	}

	@Override
	public OptionalInt getLeft(Vertex v) {
		OptionalInt result = OptionalInt.empty();

		View view = v.getDiagramView();
		if (view instanceof Shape) {
			int top = getLeft((Shape)view);
			result = (top == DEFAULT_LEFT) ? OptionalInt.empty() : OptionalInt.of(top);
		} // left and right are not interesting for edges and anchors as far as layout is concerned

		return result;
	}

	@Override
	public int getLeft(Shape shape) {
		int result = DEFAULT_LEFT;

		// if the shape is located by a border item locator, we may only have access to the bounds on figure,
		// no layout constraint on the shape (or at least not the location, only size)
		if (isLifelineBody(shape)) {
			// then the left position relative to the parent (header) is the center of the width of the parent
			Shape head = (Shape)ViewUtil.getContainerView(shape);
			int headRight = getRight(head);
			int headLeft = getLeft(head);
			if ((headLeft != DEFAULT_LEFT) && (headRight != DEFAULT_RIGHT)) {
				return (headRight + headLeft) / 2;
			}
		} else {
			LayoutConstraint constraint = shape.getLayoutConstraint();
			if (constraint != null) {
				result = getLeftFunction().applyAsInt(constraint);
			}
		}

		if (result != DEFAULT_LEFT) {
			// Its position is relative to the containing shape
			result = toAbsoluteX(shape, result);
		}

		return result;
	}

	@Override
	public OptionalInt getRight(Vertex v) {
		OptionalInt result = OptionalInt.empty();

		View view = v.getDiagramView();
		if (view instanceof Shape) {
			int right = getRight((Shape)view);
			result = (right == DEFAULT_RIGHT) ? OptionalInt.empty() : OptionalInt.of(right);
		} // left and right are not interesting for edges and anchors as far as layout is concerned

		return result;
	}

	@Override
	public int getRight(Shape shape) {
		int result = DEFAULT_RIGHT;

		if (isLifelineBody(shape)) {
			// The lifeline body has no real width: its right is its left
			result = getLeft(shape);
		} else {
			LayoutConstraint constraint = shape.getLayoutConstraint();
			if (constraint != null) {
				result = getRightFunction().applyAsInt(constraint);
			}
		}

		if (result != DEFAULT_RIGHT) {
			// Its position is relative to the containing shape
			result = toAbsoluteX(shape, result);
		}

		return result;
	}

	@SuppressWarnings("boxing")
	private ToIntFunction<EObject> getLeftFunction() {
		NotationSwitch leftSwitch = new NotationSwitch() {
			@Override
			public Object caseLocation(Location location) {
				return location.eIsSet(NotationPackage.Literals.LOCATION__X) ? location.getX() : DEFAULT_LEFT;
			}

			// left and right are not interesting for anchors as far as layout is concerned

			@Override
			public Object defaultCase(EObject object) {
				return DEFAULT_LEFT;
			}
		};

		return object -> (Integer)leftSwitch.doSwitch(object);
	}

	@SuppressWarnings("boxing")
	private ToIntFunction<EObject> getBottomFunction() {
		NotationSwitch bottomSwitch = new NotationSwitch() {
			@Override
			public Object caseLocation(Location location) {
				return location.eIsSet(NotationPackage.Literals.LOCATION__Y) ? location.getY()
						: DEFAULT_BOTTOM;
			}

			@Override
			public Object caseBounds(Bounds bounds) {
				return bounds.eIsSet(NotationPackage.Literals.LOCATION__Y)
						&& bounds.eIsSet(NotationPackage.Literals.SIZE__HEIGHT)
								? bounds.getY() + bounds.getHeight()
								: DEFAULT_BOTTOM;
			}

			@Override
			public Object caseIdentityAnchor(IdentityAnchor anchor) {
				int result = DEFAULT_BOTTOM;
				String id = anchor.getId();

				if (id != null) {
					Matcher m = IDENTITY_ANCHOR_PATTERN.matcher(id);
					if (m.matches()) {
						result = Integer.parseInt(m.group(2));
					} else {
						m = EXEC_START_FINISH_ANCHOR_PATTERN.matcher(id);
						if (m.matches()) {
							if (m.group(1) != null) {
								// It's the start anchor
								result = 0;
							} else {
								// It's the finish anchor
								LayoutConstraint layout = anchoredShape(anchor).getLayoutConstraint();
								if (layout instanceof Size) {
									// Yes, this too could be DEFAULT_BOTTOM!
									result = ((Size)layout).getHeight();
								}
							}
						}
					}
				}

				return result;
			}

			@Override
			public Object defaultCase(EObject object) {
				return DEFAULT_BOTTOM;
			}
		};

		return object -> (Integer)bottomSwitch.doSwitch(object);
	}

	@SuppressWarnings("boxing")
	private ToIntFunction<EObject> getRightFunction() {
		NotationSwitch rightSwitch = new NotationSwitch() {
			@Override
			public Object caseLocation(Location location) {
				return location.eIsSet(NotationPackage.Literals.LOCATION__X) ? location.getX()
						: DEFAULT_RIGHT;
			}

			@Override
			public Object caseBounds(Bounds bounds) {
				return bounds.eIsSet(NotationPackage.Literals.LOCATION__X)
						&& bounds.eIsSet(NotationPackage.Literals.SIZE__WIDTH)
								? bounds.getX() + bounds.getWidth()
								: DEFAULT_RIGHT;
			}

			// left and right are not interesting for anchors as far as layout is concerned

			@Override
			public Object defaultCase(EObject object) {
				return DEFAULT_RIGHT;
			}
		};

		return object -> (Integer)rightSwitch.doSwitch(object);
	}

	protected Anchor findAnchor(Vertex vertex) {
		// The connectors supported in the diagram are messages and general orderings,
		// so there aren't many possibilities to consider
		return new UMLSwitch<Anchor>() {
			@Override
			public Anchor caseMessageEnd(MessageEnd messageEnd) {
				Anchor result = null;

				Message message = messageEnd.getMessage();
				if (message != null) {
					Vertex messageVtx = vertex.graph().vertex(message);
					if (messageVtx != null) {
						View messageView = messageVtx.getDiagramView();
						if (messageView instanceof Edge) {
							Edge messageEdge = (Edge)messageView;
							result = messageEnd.isSend() ? messageEdge.getSourceAnchor()
									: messageEdge.getTargetAnchor();
						}
					}
				}

				return result;
			}

			@Override
			public Anchor caseOccurrenceSpecification(OccurrenceSpecification occurrence) {
				Anchor result = null;
				Edge ordering = null;
				boolean isSource = false;

				// Priority is to message anchors (this case is switched before the MessageEnd case
				// for MessageOccurrenceSpecifications)
				if (!(occurrence instanceof MessageEnd) || ((MessageEnd)occurrence).getMessage() == null) {
					// All edges incoming or outgoing this object are necessarily anchored at the
					// same place, because its location in the diagram has meaning and is unique
					if (!occurrence.getToBefores().isEmpty()) {
						for (GeneralOrdering next : occurrence.getToBefores()) {
							Vertex vtx = vertex.graph().vertex(next);
							if (vtx != null && vtx.getDiagramView() instanceof Edge) {
								ordering = (Edge)vtx.getDiagramView();
								break;
							}
						}
					} else if (!occurrence.getToAfters().isEmpty()) {
						for (GeneralOrdering next : occurrence.getToAfters()) {
							Vertex vtx = vertex.graph().vertex(next);
							if (vtx != null && vtx.getDiagramView() instanceof Edge) {
								ordering = (Edge)vtx.getDiagramView();
								isSource = true;
								break;
							}
						}
					}
				}

				if (ordering != null) {
					return isSource ? ordering.getSourceAnchor() : ordering.getTargetAnchor();
				}

				return result;
			}
		}.doSwitch(vertex.getInteractionElement());
	}

	protected Shape anchoredShape(Anchor anchor) {
		// Get the anchored edge
		Optional<Edge> edge = CrossReferenceUtil.invertSingle(anchor,
				NotationPackage.Literals.EDGE__SOURCE_ANCHOR, Edge.class);
		if (edge.isPresent()) {
			return edge.map(Edge::getSource).map(Shape.class::cast).orElse(null);
		}
		edge = CrossReferenceUtil.invertSingle(anchor, NotationPackage.Literals.EDGE__TARGET_ANCHOR,
				Edge.class);
		return edge.map(Edge::getTarget).map(Shape.class::cast).orElse(null);
	}

	@Override
	public Command setTop(Vertex v, int yPosition) {
		Command result = UnexecutableCommand.INSTANCE;

		View view = v.getDiagramView();
		if (view instanceof Shape) {
			result = setTop((Shape)view, yPosition);
		} else if (view instanceof Edge) {
			Edge edge = (Edge)view;
			// All edges in a sequence diagram slope down if they are not horizontal
			Anchor anchor = edge.getSourceAnchor();

			// But anchor position is relative to the attached shape
			result = setYPosition(anchor, (Shape)edge.getSource(), yPosition);
		} else {
			Anchor anchor = findAnchor(v);
			if (anchor != null) {
				// But anchor position is relative to the attached shape
				result = setYPosition(anchor, anchoredShape(anchor), yPosition);
			}
		}

		return result;
	}

	@Override
	@SuppressWarnings("boxing")
	public Command setTop(Shape shape, int yPosition) {
		Command result = UnexecutableCommand.INSTANCE;
		if (shape.getLayoutConstraint() instanceof Location) {
			// Compute relative position
			int relativeY = toRelativeY(shape, yPosition);
			result = SetCommand.create(editingDomain, shape.getLayoutConstraint(),
					NotationPackage.Literals.LOCATION__Y, relativeY);
		}
		return result;
	}

	@Override
	public Command setYPosition(Anchor anchor, Shape onShape, int yPosition) {
		Command result = UnexecutableCommand.INSTANCE;
		if (anchor instanceof IdentityAnchor) {
			String id = ((IdentityAnchor)anchor).getId();
			Matcher m = IDENTITY_ANCHOR_PATTERN.matcher(id);
			if (m.matches()) {
				// But anchor position is relative to the attached shape
				int anchorPos = yPosition - getTop(onShape);
				String newID = m.replaceFirst("$1" + Integer.toString(anchorPos)); //$NON-NLS-1$
				result = SetCommand.create(editingDomain, anchor,
						NotationPackage.Literals.IDENTITY_ANCHOR__ID, newID);
			} else {
				m = EXEC_START_FINISH_ANCHOR_PATTERN.matcher(id);
				if (m.matches()) {
					// Adjust the execution specification to effect the move
					if (m.group(1) != null) {
						// The top end
						result = setTop(onShape, yPosition);
					} else {
						// The bottom end
						result = setBottom(onShape, yPosition);
					}
				} else {
					return UnexecutableCommand.INSTANCE;
				}
			}
		}
		return result;
	}

	@Override
	public Command setBottom(Vertex v, int yPosition) {
		Command result = UnexecutableCommand.INSTANCE;

		View view = v.getDiagramView();
		if (view instanceof Shape) {
			result = setBottom((Shape)view, yPosition);
		} else if (view instanceof Edge) {
			Edge edge = (Edge)view;
			// All edges in a sequence diagram slope down if they are not horizontal
			Anchor anchor = edge.getTargetAnchor();

			// But anchor position is relative to the attached shape
			result = setYPosition(anchor, (Shape)edge.getTarget(), yPosition);
		} else {
			Anchor anchor = findAnchor(v);
			if (anchor != null) {
				// But anchor position is relative to the attached shape
				result = setYPosition(anchor, anchoredShape(anchor), yPosition);
			}
		}

		return result;
	}

	@Override
	@SuppressWarnings("boxing")
	public Command setBottom(Shape shape, int yPosition) {
		Command result = UnexecutableCommand.INSTANCE;
		if (shape.getLayoutConstraint() instanceof Size) {
			int top = getTop(shape);
			result = SetCommand.create(editingDomain, shape.getLayoutConstraint(),
					NotationPackage.Literals.SIZE__HEIGHT, yPosition - top);
		}
		return result;
	}

	@Override
	public Command setLeft(Vertex v, int xPosition) {
		Command result = UnexecutableCommand.INSTANCE;

		View view = v.getDiagramView();
		if (isLifelineBody(view)) {
			// Cannot set the position of this independently of the head
		} else if (view instanceof Shape) {
			result = setLeft((Shape)view, xPosition);
		} // left and right are not interesting for edges and anchors as far as layout is concerned

		return result;
	}

	@Override
	@SuppressWarnings("boxing")
	public Command setLeft(Shape shape, int xPosition) {
		Command result = UnexecutableCommand.INSTANCE;
		if (shape.getLayoutConstraint() instanceof Location) {
			// Compute relative position
			int relativeX = toRelativeX(shape, xPosition);
			result = SetCommand.create(editingDomain, shape.getLayoutConstraint(),
					NotationPackage.Literals.LOCATION__X, relativeX);
		}
		return result;
	}

	@Override
	public Bounds getNewBounds(EClass eClass, Bounds proposedBounds, Node container) {
		// TODO: Implement new bounds optimization
		Bounds result = EcoreUtil.copy(proposedBounds);

		if (eClass.getEPackage() == UMLPackage.eINSTANCE) {
			switch (eClass.getClassifierID()) {
				case UMLPackage.LIFELINE:
					// TODO the magic number 25 is referenced from
					// org.eclipse.papyrus.uml.interaction.internal.model.commands.NudgeOnRemovalCommand.getDefaultLifelineTop(MLifeline)
					result.setY(25);
					result.setWidth(100);
					result.setHeight(28);
					break;
			}
		}

		return result;
	}

	@Override
	public Bounds getAdjustedBounds(EObject semanticObject, Node view, Bounds proposedBounds) {
		return new UMLSwitch<Bounds>() {
			@Override
			public Bounds caseLifeline(Lifeline lifeline) {
				// Moving or resizing lifeline heads is constrained to the horizontal dimension
				Optional<Bounds> currentBounds = coerce(view.getLayoutConstraint(), Bounds.class);
				return currentBounds.map(bounds -> {
					Bounds result = EcoreUtil.copy(proposedBounds);
					result.setY(bounds.getY());
					result.setHeight(bounds.getHeight());
					return result;
				}).orElse(null);
			}

			@Override
			public Bounds defaultCase(EObject object) {
				return proposedBounds;
			}
		}.doSwitch(semanticObject);
	}

	/**
	 * Attempt to cast an object as an instance of some other type.
	 * 
	 * @param object
	 *            an object
	 * @param type
	 *            the desired type
	 * @return the {@code object} as the requested {@code type}, if appropriate
	 */
	protected static <T> Optional<T> coerce(Object object, Class<T> type) {
		return Optional.ofNullable(object).filter(type::isInstance).map(type::cast);
	}

	@Override
	public LayoutConstraints getConstraints() {
		return layoutConstraints.get();
	}
}
