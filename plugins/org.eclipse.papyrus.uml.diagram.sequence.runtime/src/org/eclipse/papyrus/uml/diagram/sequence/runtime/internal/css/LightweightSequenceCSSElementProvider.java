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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.css;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.uml.diagram.css.dom.GMFUMLElementProvider;
import org.w3c.dom.Element;

/**
 * CSS element provider for the <em>Lightweight Sequence Diagram</em>.
 */
@SuppressWarnings("restriction")
class LightweightSequenceCSSElementProvider extends GMFUMLElementProvider {

	/**
	 * Initializes me.
	 */
	public LightweightSequenceCSSElementProvider() {
		super();
	}

	@Override
	public Element getElement(Object element, CSSEngine engine) {
		// Casts serve as their own assertions, with useful messages when failed
		return new LightweightSequenceElementAdapter((View)element, (ExtendedCSSEngine)engine);
	}

}
