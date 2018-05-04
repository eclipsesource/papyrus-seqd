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

import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.swt.SWT;

/**
 * Figure specific for messages (with a by default
 */
public class GeneralOrderingFigure extends PolylineConnectionEx {

	public GeneralOrderingFigure() {
		setTargetDecoration(new RightArrowDecoration());
		setLineStyle(SWT.LINE_CUSTOM);
		setLineDash(new int[] {7, 5 });
	}

}
