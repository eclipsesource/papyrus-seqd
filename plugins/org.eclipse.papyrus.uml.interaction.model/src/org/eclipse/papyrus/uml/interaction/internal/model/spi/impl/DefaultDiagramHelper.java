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

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
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
			bounds.setX((width - execWidth) / 2); // Relative to the parent
			bounds.setY(layoutHelper().toRelativeY(result, lifelineBody, yPosition)); // Relative to parent
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
	public Command createMessageConnector(Supplier<Message> message, Supplier<? extends View> source,
			IntSupplier sourceY, Supplier<? extends View> target, IntSupplier targetY,
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

			// We need to have a bendpoints list, even if it's empty
			RelativeBendpoints bendpoints = (RelativeBendpoints)result
					.createBendpoints(NotationPackage.Literals.RELATIVE_BENDPOINTS);

			// Specific routing for self-messages
			if (selfMessage) {
				result.setRouting(Routing.RECTILINEAR_LITERAL);

				int xOffset = layoutHelper().getConstraints().getMinimumWidth(result);
				int yOffset = layoutHelper().getConstraints().getMinimumHeight(result);
				RelativeBendpoint bp1 = new RelativeBendpoint(0, 0, 0, -yOffset);
				RelativeBendpoint bp2 = new RelativeBendpoint(xOffset, 0, xOffset, -yOffset);
				RelativeBendpoint bp3 = new RelativeBendpoint(xOffset, yOffset, xOffset, 0);
				RelativeBendpoint bp4 = new RelativeBendpoint(0, yOffset, 0, 0);
				bendpoints.setPoints(Arrays.asList(bp1, bp2, bp3, bp4));
			}

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

		Command result = createMessage.chain(setSource).chain(setTarget);

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
						// Replace the execution occurrence start occurrence
						ExecutionSpecification exec = (ExecutionSpecification)source.get().getElement();
						replace = collisionHandler.apply(exec.getFinish(), message.get().getSendEvent());
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

		int xOffset = layoutHelper().getConstraints().getMinimumWidth(messageView);
		int yOffset = layoutHelper().getConstraints().getMinimumHeight(messageView);
		RelativeBendpoint bp1 = new RelativeBendpoint(0, 0, 0, -yOffset);
		RelativeBendpoint bp2 = new RelativeBendpoint(xOffset, 0, xOffset, -yOffset);
		RelativeBendpoint bp3 = new RelativeBendpoint(xOffset, yOffset, xOffset, 0);
		RelativeBendpoint bp4 = new RelativeBendpoint(0, yOffset, 0, 0);
		result = result.chain(SetCommand.create(editingDomain, messageView.getBendpoints(),
				NotationPackage.Literals.RELATIVE_BENDPOINTS__POINTS, Arrays.asList(bp1, bp2, bp3, bp4)));

		return result;
	}

	@Override
	public Command configureStraightMessageConnector(Message message, Connector messageView) {
		Command result = SetCommand.create(editingDomain, messageView,
				NotationPackage.Literals.ROUTING_STYLE__ROUTING, Routing.MANUAL_LITERAL);
		result = result.chain(SetCommand.create(editingDomain, messageView.getBendpoints(),
				NotationPackage.Literals.RELATIVE_BENDPOINTS__POINTS, Collections.EMPTY_LIST));
		return result;
	}

	@Override
	public Command deleteView(EObject diagramView) {
		return DeleteCommand.create(editingDomain, diagramView);
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
