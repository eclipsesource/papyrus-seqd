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
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.preferences.LightweightSequenceDiagramPreferences.CREATE_REPLY_MESSAGE_FOR_SYNC_CALL;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.preferences.LightweightSequenceDiagramPreferences.SYNC_MESSAGE_EXECUTION_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.rules.ExternalResource;

@SuppressWarnings("restriction")
public class LightweightSeqDPrefs extends ExternalResource {

	private List<Runnable> befores = new ArrayList<>();
	private List<Runnable> afters = new ArrayList<>();

	public LightweightSeqDPrefs dontCreateExecutionsForSyncMessages() {
		return with(CREATE_EXEC_FOR_SYNC_MESSAGE, false);
	}

	public LightweightSeqDPrefs createExecutionsForSyncMessages() {
		return with(CREATE_EXEC_FOR_SYNC_MESSAGE, true)
				// Force the execution metaclass instead of trying to automate selection from
				// the pop-up menu
				.with(SYNC_MESSAGE_EXECUTION_TYPE,
						UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION.getName());
	}

	public LightweightSeqDPrefs dontCreateRepliesForSyncCalls() {
		return createExecutionsForSyncMessages().with(CREATE_REPLY_MESSAGE_FOR_SYNC_CALL, false);
	}

	public LightweightSeqDPrefs createRepliesForSyncCalls() {
		return createExecutionsForSyncMessages().with(CREATE_REPLY_MESSAGE_FOR_SYNC_CALL, true);
	}

	public LightweightSeqDPrefs with(String prefKey, boolean value) {
		setPreference(prefKey, value, IPreferenceStore::getBoolean, IPreferenceStore::setValue);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, int value) {
		setPreference(prefKey, value, IPreferenceStore::getInt, IPreferenceStore::setValue);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, long value) {
		setPreference(prefKey, value, IPreferenceStore::getLong, IPreferenceStore::setValue);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, float value) {
		setPreference(prefKey, value, IPreferenceStore::getFloat, IPreferenceStore::setValue);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, double value) {
		setPreference(prefKey, value, IPreferenceStore::getDouble, IPreferenceStore::setValue);
		return this;
	}

	public LightweightSeqDPrefs with(String prefKey, String value) {
		setPreference(prefKey, value, IPreferenceStore::getString, IPreferenceStore::setValue);
		return this;
	}

	@Override
	protected void before() {
		befores.forEach(Runnable::run);
	}

	@Override
	protected void after() {
		afters.forEach(Runnable::run);
	}

	public void reset() {
		befores.clear();
		afters.clear();
	}

	public IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	@SuppressWarnings("unchecked")
	private <T> void setPreference(String prefKey, T value, BiFunction<IPreferenceStore, String, T> getter,
			TriConsumer<IPreferenceStore, String, T> setter) {
		IPreferenceStore store = getPreferenceStore();

		boolean[] wasDefault = { false };
		Object[] oldValue = { null };

		befores.add(() -> {
			wasDefault[0] = store.isDefault(prefKey);
			oldValue[0] = getter.apply(store, prefKey);
			setter.accept(store, prefKey, value);
		});
		afters.add(0, wasDefault[0] // Reverse order in case of multiple setting of same preference
				? () -> store.setToDefault(prefKey) //
				: () -> setter.accept(store, prefKey, (T) oldValue[0]));
	}

	/**
	 * Run some <em>ad hoc</em> runnable with the preferences that I specify.
	 *
	 * @param test
	 *            some fragment of a test scenario to run with my preferences if
	 *            effect
	 */
	public void run(Runnable test) {
		before();

		try {
			test.run();
		} finally {
			after();
		}
	}

	//
	// Nested types
	//

	@FunctionalInterface
	private static interface TriConsumer<T, U, V> {
		void accept(T t, U u, V v);
	}

}
