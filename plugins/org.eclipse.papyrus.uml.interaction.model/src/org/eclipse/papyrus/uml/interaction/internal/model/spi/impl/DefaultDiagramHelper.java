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

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
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
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Default implementation of the {@link DiagramHelper} SPI.
 *
 * @author Christian W. Damus
 */
public class DefaultDiagramHelper implements DiagramHelper {

	@SuppressWarnings("unused")
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
	public CreationCommand<Shape> createLifelineShape(Supplier<? extends Lifeline> lifeline, Diagram diagram,
			int xPosition, int height) {

		// TODO: The Logical Model is required to have no dependencies on the diagram editor,
		// but this is usually the responsibility of a View Provider. That should be okay ...

		Node frame = (Node)ViewUtil.getChildBySemanticHint(diagram, "Shape_Interaction");
		Node compartment = (Node)ViewUtil.getChildBySemanticHint(frame, "Interaction_Contents");

		Supplier<Shape> shape = () -> {
			Shape result = NotationFactory.eINSTANCE.createShape();
			result.setType("Shape_Lifeline_Header");
			result.setElement(lifeline.get());

			Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			Bounds proposedBounds = NotationFactory.eINSTANCE.createBounds();
			proposedBounds.setX(xPosition);
			proposedBounds.setHeight(height);
			bounds = layoutHelper().getNewBounds(UMLPackage.Literals.LIFELINE, proposedBounds, compartment);

			result.setLayoutConstraint(bounds);

			// create compartment for name
			Shape headerCompartment = (Shape)result.createChild(NotationPackage.Literals.SHAPE);
			headerCompartment.setType("Compartment_Lifeline_Header");
			Shape nameLabel = (Shape)result.createChild(NotationPackage.Literals.SHAPE);
			nameLabel.setType("Label_Lifeline_Name");

			Shape body = (Shape)result.createChild(NotationPackage.Literals.SHAPE);
			body.setType("Shape_Lifeline_Body");
			Size bodySize = NotationFactory.eINSTANCE.createSize();
			bodySize.setWidth(2);
			bodySize.setHeight(150);
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
			case "Shape_Lifeline_Body":
				// That's it
				return (Shape)lifelineView;
			case "Shape_Lifeline_Header":
				return (Shape)ViewUtil.getChildBySemanticHint(lifelineView, "Shape_Lifeline_Body");
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
			int execWidth = 10;

			Shape result = NotationFactory.eINSTANCE.createShape();
			result.setType("Shape_Execution_Specification");
			result.setElement(execution.get());

			Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			bounds.setX((width - execWidth) / 2);
			bounds.setY(yPosition - layoutHelper().getTop(lifelineBody)); // Always relative to parent
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
	public Command createMessageConnector(Supplier<Message> message, Supplier<? extends View> source,
			IntSupplier sourceY, Supplier<? extends View> target, IntSupplier targetY) {

		// TODO: The Logical Model is required to have no dependencies on the diagram editor,
		// but this is usually the responsibility of a View Provider. That should be okay ...

		Supplier<Connector> connector = () -> {
			Connector result = NotationFactory.eINSTANCE.createConnector();
			result.setType("Edge_Message");
			result.setElement(message.get());

			IdentityAnchor sourceAnchor = (IdentityAnchor)result
					.createSourceAnchor(NotationPackage.Literals.IDENTITY_ANCHOR);
			sourceAnchor
					.setId(Integer.toString(sourceY.getAsInt() - layoutHelper().getTop((Shape)source.get())));

			IdentityAnchor targetAnchor = (IdentityAnchor)result
					.createTargetAnchor(NotationPackage.Literals.IDENTITY_ANCHOR);
			targetAnchor
					.setId(Integer.toString(targetY.getAsInt() - layoutHelper().getTop((Shape)target.get())));

			result.createBendpoints(NotationPackage.Literals.RELATIVE_BENDPOINTS);

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

		return createMessage.chain(setSource).chain(setTarget);
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

}
