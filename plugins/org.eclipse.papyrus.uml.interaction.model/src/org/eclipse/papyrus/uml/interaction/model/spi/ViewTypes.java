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
package org.eclipse.papyrus.uml.interaction.model.spi;

/**
 * IDs of the various representations for the lightweight sequence diagram.
 */
public final class ViewTypes {
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

	public static final String DESTRUCTION_SPECIFICATION = "Shape_Destruction_Specification"; //$NON-NLS-1$

	private ViewTypes() {
		// prevent instantiation for class only holding constants.
	}
}
