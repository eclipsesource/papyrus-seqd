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

package org.eclipse.papyrus.uml.diagram.sequence.figure.anchors;

/**
 * Protocol for connection anchors on execution specifications.
 */
public interface IExecutionAnchor extends ISequenceAnchor {
	/**
	 * Set the side of the execution on which the message connection is attached (whether incoming or
	 * outgoing). This is a dynamic property of the layout; not a component of the
	 * {@link ISequenceAnchor#getTerminal() terminal} specification.
	 * 
	 * @param side
	 *            the message attachment side
	 */
	void setConnectionSide(int side);
}
