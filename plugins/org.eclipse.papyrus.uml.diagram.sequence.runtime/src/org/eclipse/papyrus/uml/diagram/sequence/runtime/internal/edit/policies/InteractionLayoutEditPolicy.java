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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.GeometryUtil.asBounds;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.GeometryUtil.asRectangle;
import static org.eclipse.papyrus.uml.service.types.utils.ElementUtil.isTypeOf;

import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.InteractionUtil;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Specific {@link LayoutEditPolicy} that relies on the logical model to draw feedback.
 */
public class InteractionLayoutEditPolicy extends XYLayoutEditPolicy implements ISequenceEditPolicy {

	private Shape feedback;

	@Override
	protected void showLayoutTargetFeedback(Request request) {
		super.showLayoutTargetFeedback(request); // super does nothing by default...

		if (request instanceof CreateRequest) {
			CreateRequest createRequest = (CreateRequest)request;

			Node view = (Node)getHost().getModel();
			Optional<Element> element = Optional.ofNullable(ViewUtil.resolveSemanticElement(view))
					.filter(Element.class::isInstance).map(Element.class::cast);
			Optional<Diagram> diagram = Optional.ofNullable(view).map(View::getDiagram);
			Optional<Interaction> interaction = InteractionUtil.getInteraction(element.orElse(null));
			if (!interaction.isPresent() || !diagram.isPresent()) {
				// do nothing
				return;
			}

			// Feed-back only supported, so far, for lifelines
			if (!(request instanceof CreateUnspecifiedTypeRequest)) {
				return;
			}

			@SuppressWarnings("unchecked")
			List<? extends IElementType> elementTypes = ((CreateUnspecifiedTypeRequest)createRequest)
					.getElementTypes();
			if (!elementTypes.stream().anyMatch(type -> isTypeOf(type, UMLElementTypes.LIFELINE))) {
				return;
			}

			// retrieve local position for the creation (request is absolute to the diagram, logical model
			// works with relative to parent)

			Point proposedLocation = createRequest.getLocation().getCopy();
			Dimension proposedSize = createRequest.getSize();
			if (proposedSize == null) {
				// This will be null until the user draws out a rect
				int minWidth = getMinimumWidth();
				int minHeight = getMinimumHeight();
				proposedSize = new Dimension(minWidth, minHeight);
			}

			translateFromAbsoluteToLayoutRelative(proposedLocation);
			Bounds proposed = bounds(proposedLocation, proposedSize);
			Bounds bounds = getLayoutHelper().getNewBounds(UMLPackage.Literals.LIFELINE, proposed, view);

			// retranslate to absolute
			Rectangle absoluteBounds = new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(),
					bounds.getHeight());

			translateFromLayoutRelativeToAbsolute(absoluteBounds);
			getFeedback().setBounds(absoluteBounds);
		}

	}

	/**
	 * Lazily creates and returns a <code>Shape</code> Figure for use as feedback.
	 */
	protected Shape getFeedback() {
		if (feedback == null) {
			feedback = new RectangleFigure();
			FigureUtilities.makeGhostShape(feedback);
			addFeedback(feedback);
		}
		return feedback;
	}

	@Override
	protected void eraseLayoutTargetFeedback(Request request) {
		super.eraseLayoutTargetFeedback(request);
		if (feedback != null) {
			removeFeedback(feedback);
			feedback = null;
		}
	}

	@Override
	protected Command createChangeConstraintCommand(ChangeBoundsRequest request, EditPart child,
			Object constraint) {

		Object newConstraint = constraint;

		if (RequestConstants.REQ_MOVE_CHILDREN.equals(request.getType())
				&& (constraint instanceof Rectangle)) {
			Node node = child.getAdapter(Node.class);
			if (node != null) {
				Rectangle rectangle = (Rectangle)constraint;
				Bounds bounds = asBounds(rectangle);
				LayoutHelper helper = Activator.getDefault().getLayoutHelper(getHost());
				bounds = helper.getAdjustedBounds(child.getAdapter(EObject.class), node, bounds);
				newConstraint = asRectangle(bounds);
			}
		}

		return super.createChangeConstraintCommand(request, child, newConstraint);
	}

	@Override
	public Point getRelativeLocation(Point location) {
		Point absolute = location.getCopy();
		Rectangle parentBounds = getParentBounds();
		Point insideLocation = absolute.translate(parentBounds.getLocation().negate());
		return insideLocation;
	}

	private Rectangle getParentBounds() {
		IGraphicalEditPart parent = (IGraphicalEditPart)getHost().getParent();
		return parent.getFigure().getBounds();
	}

	@Override
	protected Object getConstraintFor(CreateRequest request) {
		Object result = super.getConstraintFor(request);

		// The initial location of a lifeline is always at the top
		if ((result instanceof Rectangle) && (request instanceof CreateViewAndElementRequest)) {
			Rectangle rect = (Rectangle)result;
			CreateViewAndElementRequest create = (CreateViewAndElementRequest)request;

			IAdaptable elementAdapter = create.getViewAndElementDescriptor().getElementAdapter();
			IElementType type = elementAdapter.getAdapter(IElementType.class);
			Node container = (Node)((IGraphicalEditPart)getHost()).getNotationView();

			Bounds proposed = bounds(rect);
			Bounds bounds = getLayoutHelper().getNewBounds(type.getEClass(), proposed, container);
			set(rect, bounds);
		}

		return result;
	}

	static final Bounds bounds(Point location, Dimension size) {
		Bounds result = NotationFactory.eINSTANCE.createBounds();
		result.setX(location.x);
		result.setY(location.y);
		result.setWidth(size.width);
		result.setHeight(size.height);
		return result;
	}

	static final Bounds bounds(Rectangle rect) {
		return bounds(rect.getLocation(), rect.getSize());
	}

	private static final void set(Rectangle rect, Bounds bounds) {
		rect.setX(bounds.getX());
		rect.setY(bounds.getY());
		rect.setWidth(bounds.getWidth());
		rect.setHeight(bounds.getHeight());
	}
}
