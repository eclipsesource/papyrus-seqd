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

package org.eclipse.papyrus.uml.interaction.internal.model.spi.impl;

import java.util.OptionalInt;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.spi.FontHelper;

/**
 * A default implementation of the {@link FontHelper} protocol that doesn't understand anything about font
 * metrics because it has no access to the SWT or other graphics toolkit APIs.
 *
 * @author Christian W. Damus
 */
public class DefaultFontHelper implements FontHelper {

	/**
	 * Initializes me.
	 */
	public DefaultFontHelper() {
		super();
	}

	@Override
	public String getFontName(View view) {
		return null;
	}

	@Override
	public OptionalInt getFontHeight(View view) {
		return OptionalInt.of(11);
	}

}
