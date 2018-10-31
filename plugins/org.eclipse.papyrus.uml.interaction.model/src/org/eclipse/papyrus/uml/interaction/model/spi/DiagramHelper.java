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

package org.eclipse.papyrus.uml.interaction.model.spi;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Protocol for a pluggable utility that provides creation of the visual elements of a sequence diagram. All
 * positions returned by and accepted by operations of this interface are absolute locations on the diagram
 * surface, not relative to the coördinate system of a parent view.
 *
 * @author Christian W. Damus
 */
public interface DiagramHelper {

	/**
	 * Get the interaction frame of a sequence {@code diagram}.
	 * 
	 * @param diagram
	 *            a sequence diagram
	 * @return its interaction frame
	 */
	Shape getInteractionFrame(Diagram diagram);

	/**
	 * Get the shape compartment of a {@code shape}.
	 * 
	 * @param shape
	 *            a shape view
	 * @return its shape compartment
	 */
	Compartment getShapeCompartment(Shape shape);

	/**
	 * Obtain a command to create a shape for the given {@code lifeline} as a child of an {@link interaction}
	 * diagram.
	 * 
	 * @param lifeline
	 *            a lifeline to be visualized in the {@code diagram}
	 * @param diagram
	 *            the diagram in which to create the {@code lifeline} shape
	 * @param xPosition
	 *            the horizontal position of the {@code lifeline} shape to create
	 * @param height
	 *            the vertical extent of the {@code lifeline} shape to create
	 * @return the lifeline shape creation command
	 */
	CreationCommand<Shape> createLifelineShape(Supplier<? extends Lifeline> lifeline, Diagram diagram,
			int xPosition, int height);

	/**
	 * Obtains the lifeline body shape from the context of the given view.
	 * 
	 * @param lifelineView
	 *            some view in the representation of a lifeline from which the body is to be obtained (could
	 *            even be the body shape)
	 * @return the lifeline body shape
	 */
	Shape getLifelineBodyShape(View lifelineView);

	/**
	 * Obtain a command to create a shape for the given {@code execution} specification as a child of a
	 * {@link lifeline} shape in the diagram.
	 * 
	 * @param execution
	 *            an execution specification to be visualized in the diagram
	 * @param lifeline
	 *            the lifeline shape in which to create the {@code execution} shape
	 * @param yPosition
	 *            the vertical position of the {@code execution} shape to create
	 * @param height
	 *            the vertical extent of the {@code execution} shape to create
	 * @return the execution shape creation command
	 */
	CreationCommand<Shape> createExecutionShape(Supplier<? extends ExecutionSpecification> execution,
			Shape lifeline, int yPosition, int height);

	/**
	 * Obtain a command to create a shape for the given {@code execution} specification as a child of another
	 * {@link ExecutionSpecification} shape in the diagram.
	 * 
	 * @param execution
	 *            an execution specification to be visualized in the diagram
	 * @param parentExecution
	 *            the execution shape in which to create the {@code execution} shape
	 * @param yPosition
	 *            the vertical position of the {@code execution} shape to create
	 * @param height
	 *            the vertical extent of the {@code execution} shape to create
	 * @return the execution shape creation command
	 */
	CreationCommand<Shape> createNestedExecutionShape(Supplier<? extends ExecutionSpecification> execution,
			Shape parentExecution, int yPosition, int height);

	/**
	 * Obtain a command to create a shape for the given {@code execution} specification as a child of a
	 * {@link lifeline} shape in the diagram.
	 * 
	 * @param execution
	 *            an execution specification to be visualized in the diagram
	 * @param lifeline
	 *            the lifeline shape in which to create the {@code execution} shape
	 * @param yPosition
	 *            the vertical position of the {@code execution} shape to create
	 * @param height
	 *            the vertical extent of the {@code execution} shape to create
	 * @return the execution shape creation command
	 */
	CreationCommand<Shape> createExecutionShape(Supplier<? extends ExecutionSpecification> execution,
			Shape lifelineBody, IntSupplier yPosition, int height);

	/**
	 * Obtain a command to create a shape for the given {@code destruction} specification as a child of a
	 * {@link lifeline} shape in the diagram.
	 * 
	 * @param destruction
	 *            a destruction specification to be visualized in the diagram
	 * @param lifeline
	 *            the lifeline shape in which to create the {@code execution} shape
	 * @param yPosition
	 *            the vertical position of the {@code execution} shape to create
	 * @return the destruction shape creation command
	 */
	CreationCommand<Shape> createDestructionOccurrenceShape(Supplier<? extends MessageEnd> destruction,
			Shape lifeline, int yPosition);

	/**
	 * Obtain a command that moves a destruction occurrence shape to another lifeline and re-connects the
	 * delete message to it.
	 * 
	 * @param destructionView
	 *            a destruction occurrence shape to move
	 * @param messageView
	 *            the delete message view (or {@code null} for a spontaneous destruction)
	 * @param newLifeline
	 *            the lifeline to which to move the destruction view
	 * @param yPosition
	 *            the Y position on the lifeline at which to put the destruction view
	 * @return the command to effect the destruction re-connection
	 */
	Command reconnectDestructionOccurrenceShape(Shape destructionView, Connector messageView,
			Shape newLifeline, int yPosition);

	/**
	 * Obtain a command to create a shape for the given {@code message} as an edge of a {@link diagram}.
	 * 
	 * @param message
	 *            a message to be visualized in the diagram
	 * @param source
	 *            the source to which to attach the connector
	 * @param sourceY
	 *            the source y position
	 * @param target
	 *            the target to which to attach the connector
	 * @param targetY
	 *            the target Y position
	 * @param collisionHandler
	 *            an optional handler of collision between a message end and the start or finish occurrence of
	 *            an execution specification, providing an optional command to
	 *            {@linkplain Command#chain(Command) chain} to the result. May be {@code null}
	 * @return the message connector creation command
	 */
	CreationCommand<Connector> createMessageConnector(Supplier<Message> message, //
			Supplier<? extends View> source, IntSupplier sourceY, //
			Supplier<? extends View> target, IntSupplier targetY, //
			BiFunction<? super OccurrenceSpecification, ? super MessageEnd, Optional<Command>> collisionHandler);

	/**
	 * Configure the routing of a self-message view.
	 * 
	 * @param message
	 *            the self-message
	 * @param messageView
	 *            its diagram view
	 * @return the routing configuration command
	 */
	Command configureSelfMessageConnector(Message message, Connector messageView);

	/**
	 * Configure the routing of a straight (non-self) message view.
	 * 
	 * @param message
	 *            the message
	 * @param messageView
	 *            its diagram view
	 * @return the routing configuration command
	 */
	Command configureStraightMessageConnector(Message message, Connector messageView);

	/**
	 * Obtain a command that reconnects the source end of a {@code connector}.
	 * 
	 * @param connector
	 *            the connector to reconnect at its source end
	 * @param newSource
	 *            the new source-attached view for the connector
	 * @param yPosition
	 *            the Y position on the source shape at which to anchor the {@code connector}
	 * @return the command to effect the source end re-connection
	 */
	Command reconnectSource(Connector connector, Shape newSource, int yPosition);

	/**
	 * Obtain a deferred command that reconnects the future source end of a {@code connector}.
	 * 
	 * @param connector
	 *            the future connector to reconnect at its source end
	 * @param newSource
	 *            the future source-attached view for the connector
	 * @param yPosition
	 *            the future Y position on the source shape at which to anchor the {@code connector}
	 * @return the command to effect the source end re-connection
	 */
	Command reconnectSource(Supplier<? extends Connector> connector, Supplier<? extends Shape> newSource,
			IntSupplier yPosition);

	/**
	 * Obtain a command that reconnects the target end of a {@code connector}.
	 * 
	 * @param connector
	 *            the connector to reconnect at its target end
	 * @param newTarget
	 *            the new target-attached view for the connector
	 * @param yPosition
	 *            the Y position on the target shape at which to anchor the {@code connector}
	 * @return the command to effect the target end re-connection
	 */
	Command reconnectTarget(Connector connector, Shape newTarget, int yPosition);

	/**
	 * Obtain a deferred command that reconnects the future target end of a {@code connector}.
	 * 
	 * @param connector
	 *            the future connector to reconnect at its target end
	 * @param newTarget
	 *            the future target-attached view for the connector
	 * @param yPosition
	 *            the future Y position on the target shape at which to anchor the {@code connector}
	 * @return the command to effect the target end re-connection
	 */
	Command reconnectTarget(Supplier<? extends Connector> connector, Supplier<? extends Shape> newTarget,
			IntSupplier yPosition);

	/**
	 * Obtain a command to delete a given {@code connector}.
	 * 
	 * @param diagramView
	 *            the connector to delete
	 * @return the deletion command
	 */
	Command deleteView(EObject diagramView);

	/**
	 * Obtain a command that re-parents a view.
	 * 
	 * @param view
	 *            a view to reparent
	 * @param newParent
	 *            the new parent view to which to move the {@code view}
	 * @return a command to effect the re-parenting of the {@code view}
	 */
	Command reparentView(View view, View newParent);
}
