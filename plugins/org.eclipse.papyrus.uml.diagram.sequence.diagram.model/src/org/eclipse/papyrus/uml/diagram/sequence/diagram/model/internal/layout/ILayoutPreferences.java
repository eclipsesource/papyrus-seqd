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
package org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout;

public interface ILayoutPreferences {

	int getDefaultHeightOfLifelineHead();

	int getPaddingTop(Class<? extends MElement> elementType);

	int getPaddingBottom(Class<? extends MElement> elementType);

	public default int getPaddingTop(MElement element) {
		return getPaddingTop(element.getClass());
	}

	public default int getPaddingBottom(MElement element) {
		return getPaddingBottom(element.getClass());
	}

	int getMessageHeight();

	int getDefaultWidth(Class<? extends MElement> elementType);

	public default int getDefaultWidth(MElement element) {
		return getDefaultWidth(element.getClass());
	}

	int getFragmentPaddingLeft();

	int getFragmentPaddingRight();

}
