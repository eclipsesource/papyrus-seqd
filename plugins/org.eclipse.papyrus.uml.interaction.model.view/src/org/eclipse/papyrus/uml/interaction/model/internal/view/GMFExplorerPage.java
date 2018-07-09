/*****************************************************************************
 * (c) Copyright 2016 Telefonaktiebolaget LM Ericsson
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Antonio Campesino (Ericsson) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.model.internal.view;

import java.util.EventObject;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;

public abstract class GMFExplorerPage extends Page implements IPageBookViewPage, ISelectionProvider, IAdaptable {

	private TreeViewer explorerViewer;

	private EditPartViewer editPartViewer;	
	private ISelectionChangedListener diagramSelectionListener = this::diagramSelectionChanged;
	
	private CommandStack commandStack;
	private CommandStackListener commandStackListener = this::commandStackChanged;
	
	private PropertySheetPage propertySheetPage;

	public GMFExplorerPage(GraphicalEditor editor) {
		super();

		editPartViewer = (GraphicalViewer)editor.getAdapter(GraphicalViewer.class);
		editPartViewer.addSelectionChangedListener(diagramSelectionListener);		
	}

	public EditPartViewer getEditPartViewer() {
		return editPartViewer;
	}
	
	protected abstract ILabelProvider getExplorerLabelProvider();
	protected abstract IContentProvider getExplorerContentProvider();
	
	protected abstract EditPart getEditPart(Object obj);
	protected abstract Object getInputObject(GraphicalEditPart graphicalEP);
	protected abstract Object getSelectedObject(GraphicalEditPart graphicalEP);

	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == IPropertySheetPage.class) {
			return (T)getPropertySheetPage();
		} else {
			return Platform.getAdapterManager().getAdapter(this, adapter);
		}
	}
	
	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		explorerViewer.addSelectionChangedListener(listener);
	}

	@Override
	public ISelection getSelection() {
		return explorerViewer.getSelection();
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		explorerViewer.removeSelectionChangedListener(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		explorerViewer.setSelection(selection,true);
	}

	@Override
	public void createControl(Composite parent) {
		explorerViewer = new TreeViewer(createExplorerTree(parent));
		explorerViewer.setContentProvider(getExplorerContentProvider());
		explorerViewer.setLabelProvider(getExplorerLabelProvider());				
		explorerViewer.setInput(new Object[] {getInputObject((GraphicalEditPart)editPartViewer.getRootEditPart())});
		explorerViewer.addSelectionChangedListener(new ISelectionChangedListener() {			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection)event.getSelection();
				Object obj = sel.getFirstElement();
				if (obj != null) {
					EditPart ep = getEditPart(obj);
					if (ep != null) {
						if (ep.getSelected() == EditPart.SELECTED_NONE)
							editPartViewer.select(ep);
					}
				}				
			}
		});
		
		ISelection sel = editPartViewer.getSelection();
		if (sel instanceof IStructuredSelection) {
			Object obj = getSelectedObject((GraphicalEditPart)((IStructuredSelection) sel).getFirstElement());			
			explorerViewer.setSelection(new StructuredSelection(obj),true);
		}
		getSite().setSelectionProvider(explorerViewer);
	}

	@Override
	public Control getControl() {
		return explorerViewer.getTree();
	}

	@Override
	public void setFocus() {
		Viewer viewer = explorerViewer;
		if (viewer != null && !viewer.getControl().isDisposed()) {
			viewer.getControl().setFocus();
		}
	}

	public void commandStackChanged(EventObject event) {
		if (explorerViewer != null)
			explorerViewer.refresh();
	}
	
	public void diagramSelectionChanged(SelectionChangedEvent event) {	
		ISelection selection = event.getSelection();
		if  (!(selection instanceof IStructuredSelection))
			return;

		Object selectedobject = ((IStructuredSelection) selection).getFirstElement();
		if (selectedobject == null)
			return;

		if (selectedobject instanceof GraphicalEditPart) {
			selectEditPart((GraphicalEditPart) selectedobject);
			return;
		}

		selectEditPart(null);
	}
	
	private void selectEditPart(GraphicalEditPart graphicalEP) {
		if (graphicalEP != null) {
			if (graphicalEP.getModel() instanceof EObject)
				hookCommandStackListener((EObject)graphicalEP.getModel());

			Object sel = getSelectedObject(graphicalEP);
			Object input = getInputObject(graphicalEP);
			
			if (explorerViewer != null) {
				if (!(explorerViewer.getInput() instanceof Object[]) || ((Object[])explorerViewer.getInput())[0] != input) 
					explorerViewer.setInput(new Object[] {input});
				IStructuredSelection curSel = (IStructuredSelection)explorerViewer.getSelection(); 
				if (curSel.getFirstElement() == null || curSel.getFirstElement() != sel) {
					explorerViewer.reveal(sel);
					explorerViewer.setSelection(new StructuredSelection(sel), true);
				}
			}
		} else {
			if (explorerViewer != null) {
				explorerViewer.setInput(null);
			}
		}
	}

	private void hookCommandStackListener(EObject eObj) {
		if (commandStack != null)
			return;
		
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(eObj);
		if (editingDomain == null)
			return;
		
		commandStack = editingDomain.getCommandStack();
		commandStack.addCommandStackListener(commandStackListener);
		explorerViewer.refresh();
	}
	
	private void unhookCommandStackListener() {
		if (commandStack == null)
			return;
		
		commandStack.removeCommandStackListener(commandStackListener);		
		commandStack = null;
	}

	protected Tree createExplorerTree(Composite parent) {
		Tree tree = new Tree(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		return tree;
	}

	@Override
	public void dispose() {
		if (editPartViewer != null) {
			editPartViewer.removeSelectionChangedListener(diagramSelectionListener);
			editPartViewer = null;
		}
		
		unhookCommandStackListener();
		super.dispose();
	}

	protected IPropertySource createPropertySource(Object object) {
		return new GMFExplorerPropertySource(object);
	}
	
	protected IPropertySheetPage getPropertySheetPage() {
		propertySheetPage = new PropertySheetPageEx();
		propertySheetPage.setPropertySourceProvider(new IPropertySourceProvider() {			
			@Override
			public IPropertySource getPropertySource(Object object) {
				return createPropertySource(object);
			}
		});
		return propertySheetPage;
	}
	
	private static class PropertySheetPageEx extends PropertySheetPage {
		public PropertySheetPageEx() {
			super();
			setSorter(new PropertySheetSorter() {
				@Override
				public void sort(IPropertySheetEntry[] entries) {
				}				
			});
		}
	}
}
