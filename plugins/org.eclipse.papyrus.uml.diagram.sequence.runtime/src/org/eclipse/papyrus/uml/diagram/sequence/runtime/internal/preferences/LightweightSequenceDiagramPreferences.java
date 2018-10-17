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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.preferences;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.uml2.uml.UMLPackage;

public final class LightweightSequenceDiagramPreferences {

	public final static String CREATE_EXEC_FOR_SYNC_MESSAGE = "create-execution-for-sync-message"; //$NON-NLS-1$

	public final static String CREATE_REPLY_MESSAGE_FOR_SYNC_CALL = "create-reply-message-for-sync-call"; //$NON-NLS-1$

	public final static String SYNC_MESSAGE_EXECUTION_TYPE = "sync-message-execution-type"; //$NON-NLS-1$

	private IPreferenceStore preferenceStore;

	public LightweightSequenceDiagramPreferences(IPreferenceStore preferenceStore) {
		this.preferenceStore = preferenceStore;
	}

	public boolean isAutoCreateExecutionForSyncMessage() {
		return preferenceStore.getBoolean(CREATE_EXEC_FOR_SYNC_MESSAGE);
	}

	public boolean isAutoCreateReplyForSyncCall() {
		return preferenceStore.getBoolean(CREATE_REPLY_MESSAGE_FOR_SYNC_CALL);
	}

	public EClass getSyncMessageExecutionType() {
		String className = preferenceStore.getString(SYNC_MESSAGE_EXECUTION_TYPE);
		return (className == null) ? null : (EClass)UMLPackage.eINSTANCE.getEClassifier(className);
	}
}
