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

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;

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
	 * @return the message connector creation command
	 */
	Command createMessageConnector(Supplier<Message> message, //
			Supplier<? extends View> source, IntSupplier sourceY, //
			Supplier<? extends View> target, IntSupplier targetY);

	/**
	 * Obtain a command to delete a given {@code connector}.
	 * 
	 * @param diagramView
	 *            the connector to delete
	 * @return the deletion command
	 */
	Command deleteView(EObject diagramView);

	public interface ViewTypes {
		public final static String SEQUENCE_DIAGRAM = "Sequence_Diagram"; //$NON-NLS-1$

		public final static String LIGHTWEIGHT_SEQUENCE_DIAGRAM = "LightweightSequenceDiagram"; //$NON-NLS-1$

		public final static String INTERACTION = "Shape_Interaction"; //$NON-NLS-1$

		public final static String LIFELINE_HEADER = "Shape_Lifeline_Header"; //$NON-NLS-1$

		public final static String LIFELINE_HEADER_COMPARTMENT = "Compartment_Lifeline_Header"; //$NON-NLS-1$

		public final static String LIFELINE_BODY = "Shape_Lifeline_Body"; //$NON-NLS-1$

		public static final String LIFELINE_NAME = "Label_Lifeline_Name"; //$NON-NLS-1$

		public static final String INTERACTION_NAME = "Label_Interaction_Name"; //$NON-NLS-1$

		public static final String MESSAGE = "Edge_Message"; //$NON-NLS-1$

		public static final String MESSAGE_NAME = "Edge_Message_Name"; //$NON-NLS-1$

		public static final String INTERACTION_CONTENTS = "Interaction_Contents"; //$NON-NLS-1$

		public static final String EXECUTION_SPECIFICATION = "Shape_Execution_Specification"; //$NON-NLS-1$

		public static final String GENERAL_ORDERING = "Edge_General_Ordering"; //$NON-NLS-1$

		public static final String STATE_INVARIANT = "Shape_State_Invariant"; //$NON-NLS-1$

	}

}
