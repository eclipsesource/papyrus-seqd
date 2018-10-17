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

import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.provider.IPapyrusElementProvider;
import org.eclipse.papyrus.uml.diagram.css.dom.GMFUMLElementProviderFactory;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;

/**
 * CSS element provider factory for the <em>Lightweight Sequence Diagram</em>.
 */
public class LightweightSequenceCSSElementProviderFactory extends GMFUMLElementProviderFactory {

	/**
	 * Initializes me.
	 */
	public LightweightSequenceCSSElementProviderFactory() {
		super();
	}

	@Override
	public boolean isProviderFor(CSSDiagram diagram) {
		return ViewTypes.LIGHTWEIGHT_SEQUENCE_DIAGRAM.equals(diagram.getType());
	}

	@Override
	public IPapyrusElementProvider createProvider(CSSDiagram diagram) {
		return new LightweightSequenceCSSElementProvider();
	}

}
