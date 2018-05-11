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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.OperationToGEFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;

/**
 * A mix-in interface for edit-policies in the sequence diagram that need to create executable commands in a
 * transactional editing domain.
 */
public interface ISequenceEditPolicy extends EditPolicy {

	/**
	 * Wrap an EMF command in a GEF command that interacts properly with the contextual
	 * {@link #getEditingDomain() editing domain} when executed.
	 * 
	 * @param emfCommand
	 *            an EMF command to wrap
	 * @return the transactional GEF command
	 */
	default Command wrap(org.eclipse.emf.common.command.Command emfCommand) {
		TransactionalEditingDomain domain = __getEditingDomain(this);
		return (domain == null) ? UnexecutableCommand.INSTANCE
				: OperationToGEFCommandWrapper.wrap(new EMFCommandOperation(domain, emfCommand));
	}

	default LayoutHelper getLayoutHelper() {
		return Activator.getDefault().getLayoutHelper(__getEditingDomain(this));
	}

	// This should be a private 'getEditingDomain' method in Java 9
	static TransactionalEditingDomain __getEditingDomain(ISequenceEditPolicy __this) {
		EObject view = __this.getHost().getAdapter(View.class);
		return (view == null) ? null : TransactionUtil.getEditingDomain(view);
	}
}
