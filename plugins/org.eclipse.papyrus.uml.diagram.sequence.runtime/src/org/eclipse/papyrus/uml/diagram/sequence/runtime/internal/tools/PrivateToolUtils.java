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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools;

import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.Tool;
import org.eclipse.swt.SWT;

/**
 * Utilities for working with private {@link Tool}s and/or private details of {@code Tool}s.
 */
class PrivateToolUtils {

	private static final int ALLOW_SEMANTIC_REORDERING_MODIFIER = Platform.OS_MACOSX.equals(Platform.getOS())
			? SWT.COMMAND
			: SWT.CTRL;

	/**
	 * Not instantiable by clients.
	 */
	private PrivateToolUtils() {
		super();
	}

	/**
	 * Query the platform-specific keyboard modifier that enables semantic re-ordering in drag gestures in the
	 * diagram.
	 * 
	 * @return the semantic reordering modifier
	 */
	static int getAllowSemanticReorderingModifier() {
		return ALLOW_SEMANTIC_REORDERING_MODIFIER;
	}

}
