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

import java.util.OptionalInt;

import org.eclipse.gmf.runtime.notation.View;

/**
 * Protocol for a pluggable utility that provides font names and metrics of those fonts for the views in a
 * diagram.
 *
 * @author Christian W. Damus
 */
public interface FontHelper {

	/**
	 * Queries the name of the font configured for a {@code view}. The result should never be {@code null} in
	 * an installation with UI (SWT) awareness, because the frameworks provide for a default font. But in a
	 * non-UI context it may be {@code null}.
	 * 
	 * @param view
	 *            a view in the diagram
	 * @return its font name, if any can be determined
	 */
	String getFontName(View view);

	/**
	 * Queries the height of the font configured for a {@code view}, in diagram measurement units. Note that
	 * this may depend on the particular font metrics and the text of the {@code view}. The result should
	 * always be {@link OptionalInt#isPresent() present} in an installation with UI (SWT) awareness, because
	 * the frameworks provide for a default font height. But in a non-UI context it may be absent.
	 * 
	 * @param view
	 *            a view in the diagram
	 * @return its font height
	 */
	OptionalInt getFontHeight(View view);

}
