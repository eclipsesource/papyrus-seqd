/**
 * Copyright (c) 2016 CEA LIST.
  *
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributors:
  *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.part;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Messages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;

public class ValidateAction extends Action {

	private IWorkbenchPage page;

	public ValidateAction(IWorkbenchPage page) {
		setText(Messages.ValidateActionMessage);
		this.page = page;
	}

	@Override
	public void run() {
		IWorkbenchPart workbenchPart = page.getActivePart();
		if (workbenchPart instanceof IDiagramWorkbenchPart) {
			final IDiagramWorkbenchPart part = (IDiagramWorkbenchPart) workbenchPart;
			try {
				new WorkspaceModifyDelegatingOperation(new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
						IWorkbenchPartSite site = part.getSite();
						if (site != null && site.getShell() != null) {
							MessageDialog.openError(site.getShell(), this.getClass().getName() + "not implemented yet", this.getClass().getName() + "not implemented yet"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}
				}).run(new NullProgressMonitor());
			} catch (Exception e) {
				Activator.log.error("Validation action failed", e); //$NON-NLS-1$
			}
		}
	}


}
