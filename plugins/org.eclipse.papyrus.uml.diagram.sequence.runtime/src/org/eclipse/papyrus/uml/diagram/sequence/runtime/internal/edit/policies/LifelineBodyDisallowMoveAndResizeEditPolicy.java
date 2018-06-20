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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;

/**
 * An edit policy that disallows move and resize the body of a Lifeline.
 */
@SuppressWarnings({"hiding", "rawtypes" })
public class LifelineBodyDisallowMoveAndResizeEditPolicy extends ResizableBorderItemPolicy {

	public LifelineBodyDisallowMoveAndResizeEditPolicy() {
		setResizeDirections(PositionConstants.NONE);
		setDragAllowed(false);
	}

	@Override
	protected void createResizeHandle(List handles, int direction) {
		// disallow resize
	}

	@Override
	protected void createMoveHandle(List handles) {
		// disallow move
	}

	@Override
	protected void createDragHandle(List handles, int direction) {
		// disallow drag
	}

}
