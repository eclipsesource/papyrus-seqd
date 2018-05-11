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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.preferences;

import org.eclipse.papyrus.infra.gmfdiag.preferences.pages.DiagramPreferencePage;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.RepresentationKind;

/**
 * This is the {@code LightweightSequenceDiagramPreferencePage} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class LightweightSequenceDiagramPreferencePage extends DiagramPreferencePage {

	/**
	 * Initializes me.
	 */
	public LightweightSequenceDiagramPreferencePage() {
		super();

		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setPreferenceKey(RepresentationKind.MODEL_ID);
	}

}
