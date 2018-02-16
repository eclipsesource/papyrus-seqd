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

/**
 * ID for the various representation for the sequence diagram.
 */
public class RepresentationKind {

	public final static String MODEL_ID = "LightweightSequenceDiagram"; //$NON-NLS-1$

	public final static String DIAGRAM_ID = "Sequence_Diagram"; //$NON-NLS-1$

	public final static String INTERACTION_ID = "Shape_Interaction"; //$NON-NLS-1$

	public final static String LIFELINE_ID = "Shape_Lifeline"; //$NON-NLS-1$

	public final static String LIFELINE_HEADER_ID = "Shape_Lifeline_Header"; //$NON-NLS-1$

	public final static String LIFELINE_HEADER_COMPARMENT_ID = "Compartment_Lifeline_Header"; //$NON-NLS-1$

	public static final String LIFELINE_NAME_ID = "Label_Lifeline_Name"; //$NON-NLS-1$

	public static final String INTERACTION_NAME_ID = "Label_Interaction_Name"; //$NON-NLS-1$

	public static final String ASYNC_MESSAGE_ID = "Edge_AsyncMessage"; //$NON-NLS-1$

	public static final String ASYNC_MESSAGE_NAME_ID = "Edge_AsyncMessage_Name"; //$NON-NLS-1$


	private RepresentationKind() {
		// constants class.
	}

}
