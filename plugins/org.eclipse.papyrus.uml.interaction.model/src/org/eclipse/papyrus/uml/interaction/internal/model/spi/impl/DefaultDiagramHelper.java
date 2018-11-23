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

import static org.eclipse.papyrus.uml.interaction.graph.util.Suppliers.compose;

import java.util.Collections;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationParameters;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredCreateCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredSetCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Default implementation of the {@link DiagramHelper} SPI.
 *
 * @author Christian W. Damus
 */
public class DefaultDiagramHelper implements DiagramHelper {

	private final EditingDomain editingDomain;

	private final Supplier<SemanticHelper> semanticHelper;

	private final Supplier<LayoutHelper> layoutHelper;

	/**
	 * Initializes me with my contextual editing domain and a supplier of the {@link layoutHelper} that I use
	 * for calculating positioning of* views in the diagram.
	 * 
	 * @param editingDomain
	 *            my editing domain
	 * @param semanticHelper
	 *            the semantic-helper supplier
	 * @param layoutHelper
	 *            the layout-helper supplier
	 */
	public DefaultDiagramHelper(EditingDomain editingDomain, Supplier<SemanticHelper> semanticHelper,
			Supplier<LayoutHelper> layoutHelper) {
		super();

		this.editingDomain = editingDomain;
		this.semanticHelper = semanticHelper;
		this.layoutHelper = layoutHelper;
	}

	@Override
	public Shape getInteractionFrame(Diagram diagram) {
		return (Shape)ViewUtil.getChildBySemanticHint(diagram, ViewTypes.INTERACTION);
	}

	@Override
	public Compartment getShapeCompartment(Shape shape) {
		Compartment result;

		switch (shape.getType()) {
			case ViewTypes.INTERACTION:
				result = (Compartment)ViewUtil.getChildBySemanticHint(shape, ViewTypes.INTERACTION_CONTENTS);
				break;
			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public CreationCommand<Shape> createLifelineShape(Supplier<? extends Lifeline> lifeline, Diagram diagram,
			int xPosition, int height) {

		// TODO: The Logical Model is required to have no dependencies on the diagram editor,
		// but this is usually the responsibility of a View Provider. That should be okay ...

		Node compartment = getShapeCompartment(getInteractionFrame(diagram));

		Supplier<Shape> shape = () -> {
			Shape result = NotationFactory.eINSTANCE.createShape();
			result.setType(ViewTypes.LIFELINE_HEADER);
			result.setElement(lifeline.get());

			Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			Bounds proposedBounds = NotationFactory.eINSTANCE.createBounds();
			proposedBounds.setX(layoutHelper().toRelativeX(result, compartment, xPosition));
			proposedBounds.setHeight(height);
			bounds = layoutHelper().getNewBounds(UMLPackage.Literals.LIFELINE, proposedBounds, compartment);

			result.setLayoutConstraint(bounds);

			// create compartment for name
			Shape headerCompartment = (Shape)result.createChild(NotationPackage.Literals.SHAPE);
			headerCompartment.setType(ViewTypes.LIFELINE_HEADER_COMPARTMENT);
			Shape nameLabel = (Shape)result.createChild(NotationPackage.Literals.SHAPE);
			nameLabel.setType(ViewTypes.LIFELINE_NAME);

			Shape body = (Shape)result.createChild(NotationPackage.Literals.SHAPE);
			body.setType(ViewTypes.LIFELINE_BODY);
			Size bodySize = NotationFactory.eINSTANCE.createSize();
			bodySize.setWidth(layoutHelper().getConstraints().getMinimumWidth(ViewTypes.LIFELINE_BODY));
			bodySize.setHeight(layoutHelper().getConstraints().getMinimumHeight(ViewTypes.LIFELINE_BODY));
			body.setLayoutConstraint(bodySize);

			return result;
		};

		CreationParameters parameters = CreationParameters.in(compartment,
				NotationPackage.Literals.VIEW__PERSISTED_CHILDREN);
		return new DeferredCreateCommand<>(Shape.class, editingDomain, parameters, shape);
	}

	@Override
	public Shape getLifelineBodyShape(View lifelineView) {
		if (lifelineView == null || lifelineView.getType() == null) {
			return null;
		}

		switch (lifelineView.getType()) {
			case ViewTypes.LIFELINE_BODY:
				// That's it
				return (Shape)lifelineView;
			case ViewTypes.LIFELINE_HEADER:
				return (Shape)ViewUtil.getChildBySemanticHint(lifelineView, ViewTypes.LIFELINE_BODY);
			default:
				return getLifelineBodyShape(ViewUtil.getViewContainer(lifelineView));
		}
	}

	@Override
	public CreationCommand<Shape> createExecutionShape(Supplier<? extends ExecutionSpecification> execution,
			Shape lifelineBody, int yPosition, int height) {
		return createExecutionShape(execution, lifelineBody, () -> yPosition, height);
	}

	@Override
	public CreationCommand<Shape> createExecutionShape(Supplier<? extends ExecutionSpecification> execution,
			Shape lifelineBody, IntSupplier yPosition, int height) {

		// TODO: The Logical Model is required to have no dependencies on the diagram editor,
		// but this is usually the responsibility of a View Provider. That should be okay ...

		Supplier<Shape> shape = () -> {
			LayoutConstraint llBounds = lifelineBody.getLayoutConstraint();
			int width = 0;
			if (Size.class.isInstance(llBounds)) {
				width = Size.class.cast(llBounds).getWidth();
			}
			int execWidth = layoutHelper().getConstraints()
					.getMinimumWidth(ViewTypes.EXECUTION_SPECIFICATION);

			Shape result = NotationFactory.eINSTANCE.createShape();
			result.setType(ViewTypes.EXECUTION_SPECIFICATION);
			result.setElement(execution.get());

			Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			// x and y relative to the parent
			bounds.setX((width - execWidth) / 2);
			bounds.setY(layoutHelper().toRelativeY(result, lifelineBody, yPosition.getAsInt()));
			bounds.setWidth(execWidth);
			bounds.setHeight(height);
			result.setLayoutConstraint(bounds);

			return result;
		};

		CreationParameters parameters = CreationParameters.in(lifelineBody,
				NotationPackage.Literals.VIEW__PERSISTED_CHILDREN);
		return new DeferredCreateCommand<>(Shape.class, editingDomain, parameters, shape);
	}

	@Override
	public CreationCommand<Shape> createNestedExecutionShape(
			Supplier<? extends ExecutionSpecification> execution, Shape parentExecution, int yPosition,
			int height) {
		Supplier<Shape> shape = () -> {
			LayoutConstraint llBounds = parentExecution.getLayoutConstraint();
			int width = 0;
			if (Size.class.isInstance(llBounds)) {
				width = Size.class.cast(llBounds).getWidth();
			}
			int execWidth = layoutHelper().getConstraints()
					.getMinimumWidth(ViewTypes.EXECUTION_SPECIFICATION);

			Shape result = NotationFactory.eINSTANCE.createShape();
			result.setType(ViewTypes.EXECUTION_SPECIFICATION);
			result.setElement(execution.get());

			Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			bounds.setX((width - execWidth) / 2); // Relative to the parent
			bounds.setY(yPosition); // Relative to parent (already the case for yPosition)
			bounds.setWidth(execWidth);
			bounds.setHeight(height);
			result.setLayoutConstraint(bounds);

			return result;
		};

		CreationParameters parameters = CreationParameters.in(parentExecution,
				NotationPackage.Literals.VIEW__PERSISTED_CHILDREN);
		return new DeferredCreateCommand<>(Shape.class, editingDomain, parameters, shape);
	}

	@Override
	public CreationCommand<Shape> createDestructionOccurrenceShape(Supplier<? extends MessageEnd> destruction,
			Shape lifelineBody, int yPosition) {

		// TODO: The Logical Model is required to have no dependencies on the diagram editor,
		// but this is usually the responsibility of a View Provider. That should be okay ...

		Supplier<Shape> shape = () -> {
			LayoutConstraint llBounds = lifelineBody.getLayoutConstraint();
			int width = 0;
			if (Size.class.isInstance(llBounds)) {
				width = Size.class.cast(llBounds).getWidth();
			}
			int execWidth = layoutHelper().getConstraints()
					.getMinimumWidth(ViewTypes.DESTRUCTION_SPECIFICATION);

			Shape result = NotationFactory.eINSTANCE.createShape();
			result.setType(ViewTypes.DESTRUCTION_SPECIFICATION);
			result.setElement(destruction.get());

			Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			bounds.setX((width - execWidth) / 2); // Relative to the parent
			bounds.setY(layoutHelper().toRelativeY(result, lifelineBody, yPosition - (execWidth / 2)));
			bounds.setWidth(execWidth);
			bounds.setHeight(execWidth);
			result.setLayoutConstraint(bounds);

			return result;
		};

		CreationParameters parameters = CreationParameters.in(lifelineBody,
				NotationPackage.Literals.VIEW__PERSISTED_CHILDREN);
		return new DeferredCreateCommand<>(Shape.class, editingDomain, parameters, shape);
	}

	@Override
	public Command reconnectDestructionOccurrenceShape(Shape destructionView, Connector messageView,
			Shape newLifeline, int yPosition) {

		Command result = null;

		if (newLifeline != destructionView.eContainer()) {
			// Move the X shape. Note that the new lifeline may be at a different Y position
			// due to creation message, so we need to set again the absolute Y position of
			// the X shape
			result = reparentView(destructionView, newLifeline);
		}

		Command placeDestruction = layoutHelper().setTop(destructionView,
				yPosition - layoutHelper().getHeight(destructionView) / 2);
		result = result == null ? placeDestruction : result.chain(placeDestruction);

		// The message remains anchored to the X shape

		return result;
	}

	@Override
	public CreationCommand<Connector> createMessageConnector(Supplier<Message> message,
			Supplier<? extends View> source, IntSupplier sourceY, Supplier<? extends View> target,
			IntSupplier targetY,
			BiFunction<? super OccurrenceSpecification, ? super MessageEnd, Optional<Command>> collisionHandler) {

		// TODO: The Logical Model is required to have no dependencies on the diagram editor,
		// but this is usually the responsibility of a View Provider. That should be okay ...

		Supplier<Connector> connector = () -> {
			Connector result = NotationFactory.eINSTANCE.createConnector();
			result.setType(ViewTypes.MESSAGE);
			Message semanticMessage = message.get();
			result.setElement(semanticMessage);

			Shape sourceView = (Shape)source.get();
			Shape targetView = (Shape)target.get();

			// Is this a self-message? Note that in the case of gates or lost/found
			// messages, one or both ends may not trace to a lifeline
			Lifeline sourceLL = getLifeline(sourceView);
			Lifeline targetLL = getLifeline(targetView);
			boolean selfMessage = (sourceLL != null) && (sourceLL == targetLL);
			boolean syncMessage = semanticMessage.getMessageSort() != MessageSort.ASYNCH_CALL_LITERAL
					&& semanticMessage.getMessageSort() != MessageSort.ASYNCH_SIGNAL_LITERAL;

			AnchorFactory anchorFactory = new AnchorFactory(result, layoutHelper());

			int sourceDistance = sourceY.getAsInt() - layoutHelper().getTop(sourceView);
			anchorFactory.builderFor(sourceView).from(sourceView).to(targetView).at(sourceDistance)
					.sourceEnd().build();

			int targetDistance = targetY.getAsInt()
					- layoutHelper().getTop(ViewTypes.DESTRUCTION_SPECIFICATION.equals(targetView.getType())
							? (Shape)targetView.eContainer() // Calculate relative to the lifeline
							: targetView);
			if (selfMessage) {
				// A self message is fixed at the minimal gap if it is of a synchronous sort
				int minTargetDistance = sourceDistance
						+ layoutHelper().getConstraints().getMinimumHeight(result);
				targetDistance = syncMessage ? minTargetDistance
						: Math.max(targetDistance, minTargetDistance);
			}
			anchorFactory.builderFor(targetView).from(sourceView).to(targetView).at(targetDistance)
					.targetEnd().build();

			// Specific routing for self-messages
			if (selfMessage) {
				result.setRouting(Routing.RECTILINEAR_LITERAL);
			}

			RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
			result.setBendpoints(bendpoints);

			// create a decoration node to be seen by CSS rules
			DecorationNode nameLabel = (DecorationNode)result
					.createChild(NotationPackage.Literals.DECORATION_NODE);
			nameLabel.setType(ViewTypes.MESSAGE_NAME);
			return result;
		};

		CreationParameters parameters = CreationParameters.in(compose(source, View::getDiagram),
				NotationPackage.Literals.DIAGRAM__PERSISTED_EDGES);
		DeferredCreateCommand<Connector> createMessage = new DeferredCreateCommand<>(Connector.class,
				editingDomain, parameters, connector);

		// These must implemented as undoable commands because they have side-effects via opposites
		DeferredSetCommand setSource = new DeferredSetCommand(editingDomain, createMessage,
				NotationPackage.Literals.EDGE__SOURCE, source);
		DeferredSetCommand setTarget = new DeferredSetCommand(editingDomain, createMessage,
				NotationPackage.Literals.EDGE__TARGET, target);

		CreationCommand<Connector> result = createMessage.chain(setSource).chain(setTarget);

		if (collisionHandler != null) {
			CommandWrapper deferredCollisionHandler = new CommandWrapper() {
				@Override
				protected Command createCommand() {
					// Do we have any collision of message end with execution start/finish?
					Connector created = connector.get();
					Optional<Command> replace = Optional.empty();
					if (AnchorFactory.isExecutionSpecificationStart(created.getTargetAnchor())) {
						// Replace the execution occurrence start occurrence
						ExecutionSpecification exec = (ExecutionSpecification)target.get().getElement();
						replace = collisionHandler.apply(exec.getStart(), message.get().getReceiveEvent());
					} else if (AnchorFactory.isExecutionSpecificationFinish(created.getSourceAnchor())) {
						// Replace the execution occurrence finish occurrence
						ExecutionSpecification exec = (ExecutionSpecification)source.get().getElement();
						replace = collisionHandler.apply(exec.getFinish(), message.get().getSendEvent());
					} else if (AnchorFactory.isExecutionSpecificationStart(created.getSourceAnchor())) {
						// Replace the execution occurrence start occurrence, if permitted
						ExecutionSpecification exec = (ExecutionSpecification)source.get().getElement();
						replace = collisionHandler.apply(exec.getStart(), message.get().getSendEvent());
					} else if (AnchorFactory.isExecutionSpecificationFinish(created.getTargetAnchor())) {
						// Replace the execution occurrence finish occurrence, if permitted
						ExecutionSpecification exec = (ExecutionSpecification)target.get().getElement();
						replace = collisionHandler.apply(exec.getFinish(), message.get().getReceiveEvent());
					}

					// If we don't have a replace command, we still need something to execute
					return replace.orElse(IdentityCommand.INSTANCE);
				}
			};
			result = result.chain(deferredCollisionHandler);
		}

		return result;
	}

	@Override
	public Command configureSelfMessageConnector(Message message, Connector messageView) {
		Command result = SetCommand.create(editingDomain, messageView,
				NotationPackage.Literals.ROUTING_STYLE__ROUTING, Routing.RECTILINEAR_LITERAL);

		// Compute the target anchor
		Shape source = (Shape)messageView.getSource();
		AnchorFactory anchorFactory = new AnchorFactory(messageView, layoutHelper());
		int sourceDistance = layoutHelper().getYPosition(messageView.getSourceAnchor(), source)
				- layoutHelper().getTop(source);
		int targetDistance = sourceDistance + layoutHelper().getConstraints().getMinimumHeight(messageView);
		String id = anchorFactory.builderFor(source).from(source).to(source).at(targetDistance).targetEnd()
				.computeIdentity();
		result = result.chain(SetCommand.create(editingDomain, messageView.getTargetAnchor(),
				NotationPackage.Literals.IDENTITY_ANCHOR__ID, id));

		return result;
	}

	@Override
	public Command configureStraightMessageConnector(Message message, Connector messageView) {
		Command result = SetCommand.create(editingDomain, messageView,
				NotationPackage.Literals.ROUTING_STYLE__ROUTING, Routing.MANUAL_LITERAL);
		if (messageView.getBendpoints() != null) {
			result = result.chain(SetCommand.create(editingDomain, messageView.getBendpoints(),
					NotationPackage.Literals.RELATIVE_BENDPOINTS__POINTS, Collections.EMPTY_LIST));
		}
		return result;
	}

	@Override
	public Command reconnectSource(Connector connector, Shape newSource, int yPosition) {
		Command result = (connector.getSource() == newSource) ? IdentityCommand.INSTANCE
				: SetCommand.create(editingDomain, connector, NotationPackage.Literals.EDGE__SOURCE,
						newSource);

		// Anchor the connector onto the shape
		int yOffset = yPosition - layoutHelper().getTop(newSource);
		IdentityAnchor anchor = (IdentityAnchor)connector.getSourceAnchor();
		String newID = new AnchorFactory(connector, layoutHelper()).builderFor(newSource).from(newSource)
				.to((Shape)connector.getTarget()).sourceEnd().at(yOffset).computeIdentity();
		result = result.chain(SetCommand.create(editingDomain, anchor,
				NotationPackage.Literals.IDENTITY_ANCHOR__ID, newID));

		// if message changes from self message to standard, or from standard to self, the routing style must
		// be updated.
		// compute the future value of self message
		Lifeline sourceLL = getLifeline(newSource);
		Lifeline targetLL = getLifeline(connector.getTarget());
		boolean selfMessage = (sourceLL != null) && (sourceLL == targetLL);

		Routing newRouting = Routing.MANUAL_LITERAL;
		if (selfMessage) {
			newRouting = Routing.RECTILINEAR_LITERAL;
		}

		// should force?
		if (newRouting != connector.getRouting()) {
			result.chain(SetCommand.create(editingDomain, connector,
					NotationPackage.Literals.ROUTING_STYLE__ROUTING, newRouting));
		}

		return result;
	}

	@Override
	public Command reconnectSource(Supplier<? extends Connector> connector,
			Supplier<? extends Shape> newSource, IntSupplier yPosition) {

		return new CommandWrapper() {
			@Override
			protected Command createCommand() {
				return reconnectSource(connector.get(), newSource.get(), yPosition.getAsInt());
			}
		};
	}

	@Override
	public Command reconnectTarget(Connector connector, Shape newTarget, int yPosition) {
		Command result = (connector.getTarget() == newTarget) ? IdentityCommand.INSTANCE
				: SetCommand.create(editingDomain, connector, NotationPackage.Literals.EDGE__TARGET,
						newTarget);

		// Anchor the connector onto the shape
		int yOffset = yPosition - layoutHelper().getTop(newTarget);
		IdentityAnchor anchor = (IdentityAnchor)connector.getTargetAnchor();
		String newID = new AnchorFactory(connector, layoutHelper()).builderFor(newTarget)
				.from((Shape)connector.getSource()).to(newTarget).targetEnd().at(yOffset).computeIdentity();
		result = result.chain(SetCommand.create(editingDomain, anchor,
				NotationPackage.Literals.IDENTITY_ANCHOR__ID, newID));

		// if message changes from self message to standard, or from standard to self, the routing style must
		// be updated.
		// compute the future value of self message
		Lifeline sourceLL = getLifeline(connector.getSource());
		Lifeline targetLL = getLifeline(newTarget);
		boolean selfMessage = (sourceLL != null) && (sourceLL == targetLL);

		Routing newRouting = Routing.MANUAL_LITERAL;
		if (selfMessage) {
			newRouting = Routing.RECTILINEAR_LITERAL;
		}
		// should force?
		if (newRouting != connector.getRouting()) {
			result.chain(SetCommand.create(editingDomain, connector,
					NotationPackage.Literals.ROUTING_STYLE__ROUTING, newRouting));
		}

		return result;
	}

	@Override
	public Command reconnectTarget(Supplier<? extends Connector> connector,
			Supplier<? extends Shape> newTarget, IntSupplier yPosition) {

		return new CommandWrapper() {
			@Override
			protected Command createCommand() {
				return reconnectTarget(connector.get(), newTarget.get(), yPosition.getAsInt());
			}
		};
	}

	@Override
	public Command deleteView(EObject diagramView) {
		return DeleteCommand.create(editingDomain, diagramView);
	}

	@SuppressWarnings("boxing")
	@Override
	public Command reparentView(View view, View newParent) {
		if (view.eContainer() == newParent) {
			// Nothing to do
			return IdentityCommand.INSTANCE;
		}

		// First record the original containment by removing the view
		Command result = RemoveCommand.create(editingDomain, view);
		result = result.chain(AddCommand.create(editingDomain, newParent,
				NotationPackage.Literals.VIEW__PERSISTED_CHILDREN, view));

		if (ViewTypes.EXECUTION_SPECIFICATION.equals(view.getType()) && (view instanceof Node)
				&& (newParent instanceof Node)) {
			// Ensure the correct horizontal alignment
			Bounds bounds = (Bounds)((Node)view).getLayoutConstraint();

			if (ViewTypes.EXECUTION_SPECIFICATION.equals(newParent.getType())) {
				// Center on the parent's right edge
				Bounds parentBounds = (Bounds)((Node)newParent).getLayoutConstraint();
				result = result
						.chain(SetCommand.create(editingDomain, bounds, NotationPackage.Literals.LOCATION__X,
								parentBounds.getWidth() - (bounds.getWidth() / 2)));
			} else if (ViewTypes.LIFELINE_BODY.equals(newParent.getType())) {
				// Center on the lifeline stem
				result = result.chain(SetCommand.create(editingDomain, bounds,
						NotationPackage.Literals.LOCATION__X, -bounds.getWidth() / 2));
			}
		}

		return result;
	}

	protected final SemanticHelper semanticHelper() {
		return semanticHelper.get();
	}

	protected final LayoutHelper layoutHelper() {
		return layoutHelper.get();
	}

	private Lifeline getLifeline(View end) {
		Lifeline result = null;

		for (View view = end; view != null && result == null; view = ViewUtil.getContainerView(view)) {
			EObject semantic = ViewUtil.resolveSemanticElement(view);
			if (semantic instanceof Lifeline) {
				result = (Lifeline)semantic;
			}
		}

		return result;
	}
}
