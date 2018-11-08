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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util;

import org.eclipse.uml2.uml.Parameter;

/**
 * This is the {@code OperationUtil} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class OperationUtil {

	/**
	 * Not instantiable by clients.
	 */
	private OperationUtil() {
		super();
	}

	/**
	 * Queries whether a {@code parameter} is an input to an operation.
	 * 
	 * @param parameter
	 *            an operation parameter
	 * @return whether it is an in or in-out parameter
	 */
	public static boolean isInput(Parameter parameter) {
		switch (parameter.getDirection()) {
			case IN_LITERAL:
			case INOUT_LITERAL:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Queries whether a {@code parameter} is an output from an operation.
	 * 
	 * @param parameter
	 *            an operation parameter
	 * @return whether it is an out, in-out, or return parameter
	 */
	public static boolean isOutput(Parameter parameter) {
		switch (parameter.getDirection()) {
			case OUT_LITERAL:
			case INOUT_LITERAL:
			case RETURN_LITERAL:
				return true;
			default:
				return false;
		}
	}

}
