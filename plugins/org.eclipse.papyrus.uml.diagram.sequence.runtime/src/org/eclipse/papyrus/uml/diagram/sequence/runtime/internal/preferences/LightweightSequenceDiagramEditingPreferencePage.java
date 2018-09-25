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

import org.eclipse.gmf.runtime.diagram.ui.preferences.DiagramsPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Messages;

public class LightweightSequenceDiagramEditingPreferencePage extends DiagramsPreferencePage {

	private BooleanFieldEditor autoCreateExecution;

	private BooleanFieldEditor autoCreateReply;

	public LightweightSequenceDiagramEditingPreferencePage() {
		super();
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		autoCreateExecution = new BooleanFieldEditor(
				LightweightSequenceDiagramPreferences.AUTO_CREATE_EXEC_AFTER_SYNC_MESSAGE,
				Messages.CreateExecutionAfterSyncMessageAutomaticallyLabel, getFieldEditorParent());
		autoCreateExecution.setPage(this);

		autoCreateReply = new BooleanFieldEditor(
				LightweightSequenceDiagramPreferences.AUTO_CREATE_REPLY_MESSAGE,
				Messages.CreateReplyMessageAutomaticallyLabel, getFieldEditorParent());
		autoCreateReply.setPage(this);

		addField(autoCreateExecution);
		addField(autoCreateReply);
	}

}
