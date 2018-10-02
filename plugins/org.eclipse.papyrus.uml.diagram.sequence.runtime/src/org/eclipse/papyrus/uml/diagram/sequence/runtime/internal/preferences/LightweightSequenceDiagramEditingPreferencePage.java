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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.preferences.LightweightSequenceDiagramPreferences.CREATE_EXEC_FOR_SYNC_MESSAGE;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.preferences.LightweightSequenceDiagramPreferences.CREATE_REPLY_MESSAGE_FOR_SYNC_CALL;

import org.eclipse.gmf.runtime.diagram.ui.preferences.DiagramsPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
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
		autoCreateExecution = new BooleanFieldEditor(CREATE_EXEC_FOR_SYNC_MESSAGE,
				Messages.CreateExecutionAfterSyncMessageAutomaticallyLabel, getFieldEditorParent());
		autoCreateExecution.setPage(this);

		autoCreateReply = new BooleanFieldEditor(CREATE_REPLY_MESSAGE_FOR_SYNC_CALL,
				Messages.CreateReplyMessageAutomaticallyLabel, getFieldEditorParent());
		boolean isAutoCreateExecution = Activator.getDefault().getPreferences()
				.isAutoCreateExecutionForSyncMessage();
		autoCreateReply.setEnabled(isAutoCreateExecution, getFieldEditorParent());
		autoCreateReply.setPage(this);

		addField(autoCreateExecution);
		addField(autoCreateReply);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (autoCreateExecution == event.getSource() && event.getNewValue() instanceof Boolean) {
			boolean isAutoCreateExecution = ((Boolean)event.getNewValue()).booleanValue();
			autoCreateReply.setEnabled(isAutoCreateExecution, getFieldEditorParent());
		}
		super.propertyChange(event);
	}

}
