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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Messages;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class UMLCreationWizard extends Wizard implements INewWizard {

	private IWorkbench workbench;

	protected IStructuredSelection selection;

	protected UMLCreationWizardPage diagramModelFilePage;

	protected UMLCreationWizardPage domainModelFilePage;

	protected Resource diagram;

	private boolean openNewlyCreatedDiagramEditor = true;

	public IWorkbench getWorkbench() {
		return workbench;
	}

	public IStructuredSelection getSelection() {
		return selection;
	}

	public final Resource getDiagram() {
		return diagram;
	}

	public final boolean isOpenNewlyCreatedDiagramEditor() {
		return openNewlyCreatedDiagramEditor;
	}

	public void setOpenNewlyCreatedDiagramEditor(boolean openNewlyCreatedDiagramEditor) {
		this.openNewlyCreatedDiagramEditor = openNewlyCreatedDiagramEditor;
	}

	@Override
	public void init(IWorkbench workbench2, IStructuredSelection selection2) {
		this.workbench = workbench2;
		this.selection = selection2;
		setWindowTitle(Messages.UMLCreationWizardTitle);
		setDefaultPageImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/wizban/NewUMLWizard.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		diagramModelFilePage = new UMLCreationWizardPage("DiagramModelFile", getSelection(), //$NON-NLS-1$
				"PapyrusUMLSequence_diagram"); //$NON-NLS-1$
		diagramModelFilePage.setTitle(Messages.UMLCreationWizard_DiagramModelFilePageTitle);
		diagramModelFilePage.setDescription(Messages.UMLCreationWizard_DiagramModelFilePageDescription);
		addPage(diagramModelFilePage);

		domainModelFilePage = new UMLCreationWizardPage("DomainModelFile", getSelection(), //$NON-NLS-1$
				"PapyrusUMLSequence") { //$NON-NLS-1$

			@Override
			public void setVisible(boolean visible) {
				if (visible) {
					String fileName = diagramModelFilePage.getFileName();
					fileName = fileName.substring(0,
							fileName.length() - ".PapyrusUMLSequence_diagram".length()); //$NON-NLS-1$
					setFileName(UMLDiagramEditorUtil.getUniqueFileName(getContainerFullPath(), fileName,
							"PapyrusUMLSequence")); //$NON-NLS-1$
				}
				super.setVisible(visible);
			}
		};
		domainModelFilePage.setTitle(Messages.UMLCreationWizard_DomainModelFilePageTitle);
		domainModelFilePage.setDescription(Messages.UMLCreationWizard_DomainModelFilePageDescription);
		addPage(domainModelFilePage);
	}

	@Override
	public boolean performFinish() {
		IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InterruptedException {
				diagram = UMLDiagramEditorUtil.createDiagram(diagramModelFilePage.getURI(),
						domainModelFilePage.getURI(), monitor);
				if (isOpenNewlyCreatedDiagramEditor() && diagram != null) {
					try {
						UMLDiagramEditorUtil.openDiagram(diagram);
					} catch (PartInitException e) {
						ErrorDialog.openError(getContainer().getShell(),
								Messages.UMLCreationWizardOpenEditorError, null, e.getStatus());
					}
				}
			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getContainer().getShell(), Messages.UMLCreationWizardCreationError,
						null, ((CoreException)e.getTargetException()).getStatus());
			} else {
				Activator.log.error("Error creating diagram", e.getTargetException()); //$NON-NLS-1$
			}
			return false;
		}
		return diagram != null;
	}
}
