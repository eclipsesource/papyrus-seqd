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

import org.eclipse.draw2d.ConnectionAnchor;

/**
 * Protocol for anchors in sequence diagrams, for messages, general orderings, comment tethers, etc.
 *
 * @author Christian W. Damus
 */
public interface ISequenceAnchor extends ConnectionAnchor {

	/**
	 * Query my terminal string for identification in the notation model.
	 * 
	 * @return my terminal
	 */
	String getTerminal();

}
