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

import org.eclipse.gmf.runtime.notation.Compartment;

/**
 * Encoding of the layout constraints for shapes, compartments, etc.
 *
 * @author Christian W. Damus
 */
public interface LayoutConstraints {

	/**
	 * Queries the x-coördinate offset of a shape compartment within its parent shape, accounting for name
	 * labels etc. that take up space but which size is not encoded in the notation.
	 * 
	 * @param shapeCompartment
	 *            a shape compartment
	 * @return its X offset within the parent shape
	 */
	int getXOffset(Compartment shapeCompartment);

	/**
	 * Queries the y-coördinate offset of a shape compartment within its parent shape, accounting for name
	 * labels etc. that take up space but which size is not encoded in the notation.
	 * 
	 * @param shapeCompartment
	 *            a shape compartment
	 * @return its Y offset within the parent shape
	 */
	int getYOffset(Compartment shapeCompartment);

}
