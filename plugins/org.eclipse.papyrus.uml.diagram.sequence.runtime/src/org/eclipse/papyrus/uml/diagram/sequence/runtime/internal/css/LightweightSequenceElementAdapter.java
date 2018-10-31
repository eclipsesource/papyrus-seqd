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

import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.uml.diagram.css.dom.GMFUMLElementAdapter;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;

/**
 * CSS element adapter for the <em>Lightweight Sequence Diagram</em>.
 */
@SuppressWarnings("restriction")
class LightweightSequenceElementAdapter extends GMFUMLElementAdapter {

	private InternalTransactionalEditingDomain editingDomain;

	/**
	 * Initializes me.
	 *
	 * @param view
	 * @param engine
	 */
	public LightweightSequenceElementAdapter(View view, ExtendedCSSEngine engine) {
		super(view, engine);

		editingDomain = (InternalTransactionalEditingDomain)TransactionUtil.getEditingDomain(view);
	}

	@Override
	public void dispose() {
		editingDomain = null;
		super.dispose();
	}

	@Override
	public void notationPropertyChanged() {
		if (shouldNotify()) {
			super.notationPropertyChanged();
		}
	}

	@Override
	public void semanticPropertyChanged() {
		if (shouldNotify()) {
			super.semanticPropertyChanged();
		}
	}

	boolean shouldNotify() {
		// Don't refresh the diagram if we're preparing a pessimistic compound command,
		// which involves executing commands which will immediately be undone
		Transaction activeTransaction = (editingDomain == null) ? null : editingDomain.getActiveTransaction();
		return (activeTransaction == null) || !Boolean.TRUE
				.equals(activeTransaction.getOptions().get(CompoundModelCommand.OPTION_COMMAND_PREPARATION));
	}
}
