/*****************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.preferences.LightweightSequenceDiagramPreferences.CREATE_EXEC_FOR_SYNC_MESSAGE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.junit.rules.ExternalResource;

@SuppressWarnings("restriction")
public class LightweightSeqDPrefs extends ExternalResource {

	private List<String> preferenceValues = new ArrayList<>();

	public LightweightSeqDPrefs dontCreateExecutionsForSyncMessages() {
		return with(CREATE_EXEC_FOR_SYNC_MESSAGE, false);
	}

	public LightweightSeqDPrefs createExecutionsForSyncMessages() {
		return with(CREATE_EXEC_FOR_SYNC_MESSAGE, true);
	}

	public LightweightSeqDPrefs with(String prefKey, boolean value) {
		getPreferenceStore().setValue(prefKey, value);
		this.preferenceValues.add(prefKey);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, int value) {
		getPreferenceStore().setValue(prefKey, value);
		this.preferenceValues.add(prefKey);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, long value) {
		getPreferenceStore().setValue(prefKey, value);
		this.preferenceValues.add(prefKey);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, float value) {
		getPreferenceStore().setValue(prefKey, value);
		this.preferenceValues.add(prefKey);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, double value) {
		getPreferenceStore().setValue(prefKey, value);
		this.preferenceValues.add(prefKey);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, String value) {
		getPreferenceStore().setValue(prefKey, value);
		this.preferenceValues.add(prefKey);
		return this;
	}

	@Override
	protected void after() {
		reset();
	}

	public void reset() {
		for (String key : preferenceValues) {
			getPreferenceStore().setToDefault(key);
		}
	}

	public IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

}
