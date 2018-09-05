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

import org.eclipse.gef.Tool;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.PaletteFactory;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeConnectionTool;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;

/**
 * Custom palette factory that substitutes Papyrus tools with alternate implementations that support
 * integration with the {@linkplain IMagnetManager magnet manager}.
 */
public final class SequencePaletteFactory extends PaletteFactory.Adapter {
	private final PaletteFactory delegate;

	/**
	 * Initializes me with the default factory for tools described by my owning palette model entry.
	 *
	 * @param delegate
	 *            a palette factory delegate
	 */
	public SequencePaletteFactory(PaletteFactory delegate) {
		super();

		this.delegate = delegate;
	}

	@Override
	public Tool createTool(String toolId) {
		Tool result = delegate.createTool(toolId);

		if (result instanceof AspectUnspecifiedTypeConnectionTool) {
			AspectUnspecifiedTypeConnectionTool connectionTool = (AspectUnspecifiedTypeConnectionTool)result;
			result = new SequenceConnectionCreationTool(connectionTool.getElementTypes());
		}

		return result;
	}

	@Override
	public Object getTemplate(String templateId) {
		return delegate.getTemplate(templateId);
	}
}
