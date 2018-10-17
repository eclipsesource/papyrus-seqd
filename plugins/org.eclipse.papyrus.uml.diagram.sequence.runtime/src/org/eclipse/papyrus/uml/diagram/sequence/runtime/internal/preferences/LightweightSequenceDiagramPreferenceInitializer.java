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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage;
import org.eclipse.gmf.runtime.diagram.ui.preferences.ConnectionsPreferencePage;
import org.eclipse.gmf.runtime.diagram.ui.preferences.DiagramsPreferencePage;
import org.eclipse.gmf.runtime.diagram.ui.preferences.PrintingPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;

/**
 * This is the {@code LightweightSequenceDiagramPreferenceInitializer} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class LightweightSequenceDiagramPreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * Initializes me.
	 */
	public LightweightSequenceDiagramPreferenceInitializer() {
		super();
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		DiagramsPreferencePage.initDefaults(store);
		AppearancePreferencePage.initDefaults(store);
		ConnectionsPreferencePage.initDefaults(store);
		PrintingPreferencePage.initDefaults(store);
		store.setDefault(LightweightSequenceDiagramPreferences.CREATE_REPLY_MESSAGE_FOR_SYNC_CALL, true);
		store.setDefault(LightweightSequenceDiagramPreferences.CREATE_EXEC_FOR_SYNC_MESSAGE, true);
		// null default for LightweightSequenceDiagramPreferences.SYNC_MESSAGE_EXECUTION_TYPE
	}

}
