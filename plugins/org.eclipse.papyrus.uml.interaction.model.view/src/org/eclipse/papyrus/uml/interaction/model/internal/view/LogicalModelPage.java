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

package org.eclipse.papyrus.uml.interaction.model.internal.view;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.NotifyingInternalEListImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.core.utils.JobBasedFuture;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * A page in the {@link LogicalModelView}, which presents the logical model of a
 * particular sequence diagram.
 *
 * @author Christian W. Damus
 */
public class LogicalModelPage extends Page implements IPageBookViewPage, IEditingDomainProvider,
		ISelectionProvider, IViewerProvider, IAdaptable {

	private final TransactionalEditingDomain editingDomain;
	private final AdapterFactory adapterFactory;
	private final Diagram sequenceDiagram;
	private final ResourceSetListener modelListener;

	private PropertySheetPage propertySheetPage;

	private TreeViewer selectionViewer;

	/**
	 * Initializes me.
	 *
	 */
	public LogicalModelPage(UmlGmfDiagramEditor editor) {
		super();

		editingDomain = editor.getEditingDomain();
		adapterFactory = ((AdapterFactoryEditingDomain) editingDomain).getAdapterFactory();
		sequenceDiagram = editor.getDiagram();
		modelListener = new ModelListener();
		editingDomain.addResourceSetListener(modelListener);
	}

	@Override
	public void createControl(Composite parent) {
		Tree tree = new Tree(parent, SWT.MULTI);
		selectionViewer = new TreeViewer(tree);

		selectionViewer.setUseHashlookup(true);
		selectionViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		selectionViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		// Create a fake resource to expose the interaction in the UI
		Resource resource = new ResourceImpl() {
			private EList<EObject> contents = new NotifyingInternalEListImpl<>(1);

			@Override
			public EList<EObject> getContents() {
				return contents;
			}
		};
		selectionViewer.setInput(resource);
		setInput();

		new AdapterFactoryTreeEditor(selectionViewer.getTree(), adapterFactory);

		getSite().setSelectionProvider(selectionViewer);
	}

	@Override
	public void dispose() {
		try {
			editingDomain.removeResourceSetListener(modelListener);

			if (propertySheetPage != null) {
				propertySheetPage.dispose();
			}
		} finally {
			super.dispose();
		}
	}

	private ListenableFuture<MInteraction> computeInteraction() {
		if (TransactionHelper.isDisposed(editingDomain)) {
			return Futures.immediateCancelledFuture();
		}

		JobBasedFuture<MInteraction> result = new JobBasedFuture<MInteraction>("Computing logical model") {
			@Override
			protected MInteraction compute(IProgressMonitor monitor) throws Exception {
				if (TransactionHelper.isDisposed(editingDomain)) {
					cancel(true);
					return null;
				}

				return TransactionUtil.runExclusive(editingDomain,
						new RunnableWithResult.Impl<MInteraction>() {
							@Override
							public void run() {
								setResult(MInteraction.getInstance(sequenceDiagram));
							}
						});
			}
		};
		result.schedule();
		return result;
	}

	private void setInput() {
		ListenableFuture<MInteraction> interaction = computeInteraction();
		Futures.addCallback(interaction, new FutureCallback<MInteraction>() {
			@Override
			public void onSuccess(MInteraction result) {
				Resource resource = (Resource) selectionViewer.getInput();
				resource.getContents().clear();

				if (result == null) {
					// Cancelled, but not calling onFailure(...). Okay
					return;
				}

				resource.getContents().add((EObject) result);

				if (!selectionViewer.getControl().isDisposed()) {
					// Preserve selection
					MElement<?> selected = getSelectedElement();
					if (selected != null) {
						// Get the one in the new interaction
						selected = result.getElement(selected.getElement()).orElse(null);
					}
					if (selected == null) {
						selected = result;
					}

					selectionViewer.refresh();
					selectionViewer.setSelection(new StructuredSelection(selected), true);
					selectionViewer.expandToLevel(2);
				}
			}

			@Override
			public void onFailure(Throwable t) {
				// TODO
			}
		}, getSite().getShell().getDisplay()::asyncExec);
	}

	MElement<?> getSelectedElement() {
		Object result = null;

		ISelection sel = getSelection();
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) sel;
			result = ssel.getFirstElement();
		}

		return (result instanceof MElement<?>) ? (MElement<?>) result : null;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		T result;

		if (adapter == IPropertySheetPage.class) {
			result = adapter.cast(getPropertySheetPage());
		} else {
			result = Platform.getAdapterManager().getAdapter(this, adapter);
		}

		return result;
	}

	@Override
	public void setFocus() {
		selectionViewer.getTree().setFocus();
	}

	@Override
	public Control getControl() {
		return selectionViewer.getControl();
	}

	@Override
	public Viewer getViewer() {
		return selectionViewer;
	}

	@Override
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionViewer.addSelectionChangedListener(listener);
	}

	@Override
	public ISelection getSelection() {
		return selectionViewer.getSelection();
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionViewer.removeSelectionChangedListener(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		selectionViewer.setSelection(selection, true);
	}

	protected void setSelectionToViewer(Collection<?> collection) {
		if ((collection != null) && !collection.isEmpty()) {
			getSite().getShell().getDisplay()
					.asyncExec(() -> setSelection(new StructuredSelection(collection.toArray())));
		}
	}

	protected IPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null) {
			propertySheetPage = new ExtendedPropertySheetPage((AdapterFactoryEditingDomain) editingDomain) {
				@Override
				public void setSelectionToViewer(List<?> selection) {
					LogicalModelPage.this.setSelectionToViewer(selection);
					LogicalModelPage.this.setFocus();
				}
			};

			propertySheetPage.setPropertySourceProvider(new AdapterFactoryContentProvider(adapterFactory));
		}

		return propertySheetPage;
	}

	//
	// Nested types
	//

	private class ModelListener extends ResourceSetListenerImpl {

		@Override
		public boolean isPostcommitOnly() {
			return true;
		}

		@Override
		public void resourceSetChanged(ResourceSetChangeEvent event) {
			setInput();
		}
	}
}
