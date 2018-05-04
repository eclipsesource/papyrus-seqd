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
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts;

import org.eclipse.gmf.runtime.notation.Diagram;

/**
 * ID for the various representation for the sequence diagram.
 */
public class RepresentationKind {

	/**
	 * The name of the representation in the architecture model.
	 */
	public final static String NAME = "Lightweight Sequence Diagram"; //$NON-NLS-1$

	/**
	 * The ID of the notation {@link Diagram} element
	 */
	public final static String MODEL_ID = "LightweightSequenceDiagram"; //$NON-NLS-1$

	/*
	 * Unused?
	 */
	public final static String DIAGRAM_ID = "Sequence_Diagram"; //$NON-NLS-1$

	public final static String INTERACTION_ID = "Shape_Interaction"; //$NON-NLS-1$

	public final static String LIFELINE_HEADER_ID = "Shape_Lifeline_Header"; //$NON-NLS-1$

	public final static String LIFELINE_HEADER_COMPARTMENT_ID = "Compartment_Lifeline_Header"; //$NON-NLS-1$

	public final static String LIFELINE_BODY_ID = "Shape_Lifeline_Body"; //$NON-NLS-1$

	public static final String LIFELINE_NAME_ID = "Label_Lifeline_Name"; //$NON-NLS-1$

	public static final String INTERACTION_NAME_ID = "Label_Interaction_Name"; //$NON-NLS-1$

	public static final String MESSAGE_ID = "Edge_Message"; //$NON-NLS-1$

	public static final String MESSAGE_NAME_ID = "Edge_Message_Name"; //$NON-NLS-1$

	public static final String INTERACTION_COMPARTMENT_ID = "Interaction_Contents"; //$NON-NLS-1$

	public static final String EXECUTION_SPECIFICATION_ID = "Shape_Execution_Specification"; //$NON-NLS-1$

	public static final String GENERAL_ORDERING_ID = "Edge_General_Ordering"; //$NON-NLS-1$

	public static final String STATE_INVARIANT_ID = "Shape_State_Invariant"; //$NON-NLS-1$

	private RepresentationKind() {
		// constants class.
	}

}
