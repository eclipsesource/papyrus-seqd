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
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.figure;

import org.eclipse.draw2d.PolylineConnection;

/**
 * Figure specific for messages (with a by default  
 */
public class MessageFigure extends PolylineConnection {
	
	public MessageFigure() {
		setTargetDecoration(new RightArrowDecoration());
	}

}
