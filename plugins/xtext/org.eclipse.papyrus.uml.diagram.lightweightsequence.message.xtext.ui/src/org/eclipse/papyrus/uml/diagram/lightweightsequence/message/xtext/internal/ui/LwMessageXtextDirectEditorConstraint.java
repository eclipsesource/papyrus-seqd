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

package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.internal.ui;

import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceDiagramVisualIDQuarantine;

/**
 * Additional constraint for the lightweight sequence diagram message direct
 * editor.
 *
 * @author Christian W. Damus
 */
public class LwMessageXtextDirectEditorConstraint implements IDirectEditorConstraint {

	@Inject
	private SequenceDiagramVisualIDQuarantine quarantine;

	/**
	 * Initializes me.
	 */
	public LwMessageXtextDirectEditorConstraint() {
		super();
	}

	@Override
	public String getLabel() {
		return "Lightweight Sequence Diagram Message Editor Constraint";
	}

	@Override
	public boolean appliesTo(Object selection) {
		View view = Adapters.adapt(selection, View.class);
		Optional<String> viewType = Optional.ofNullable(view).map(View::getType);

		return viewType.map(quarantine::isMessageLabel).orElse(false);
	}

}
