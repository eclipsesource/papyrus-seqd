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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.NotifyingInternalEListImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MObject;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;

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
	private final IDiagramGraphicalViewer diagramViewer;

	private final LogicalModelAdapter<MInteraction> logicalModelAdapter;
	private final ISelectionChangedListener diagramSelectionListener = this::diagramSelectionChanged;
	private final ISelectionChangedListener modelSelectionListener = this::modelSelectionChanged;

	private PropertySheetPage propertySheetPage;

	private TreeViewer selectionViewer;

	private boolean selectionLock; // lock out selection updates to prevent feedback
	private final AtomicReference<Job> interactionComputation = new AtomicReference<>();

	/**
	 * Initializes me.
	 *
	 */
	public LogicalModelPage(UmlGmfDiagramEditor editor) {
		super();

		editingDomain = editor.getEditingDomain();
		adapterFactory = ((AdapterFactoryEditingDomain) editingDomain).getAdapterFactory();
		sequenceDiagram = editor.getDiagram();
		diagramViewer = editor.getDiagramGraphicalViewer();
		diagramViewer.addSelectionChangedListener(diagramSelectionListener);
		logicalModelAdapter = new LogicalModelAdapter<>((Interaction) sequenceDiagram.getElement(),
				MInteraction.class);
	}

	@Override
	public void createControl(Composite parent) {
		Tree tree = new Tree(parent, SWT.MULTI);
		selectionViewer = new TreeViewer(tree);

		selectionViewer.setUseHashlookup(true);
		selectionViewer.setContentProvider(createContentProvider(adapterFactory));
		selectionViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		// Create a fake resource to expose the interaction in the UI
		Resource resource = new ResourceImpl() {
			private EList<EObject> contents = new NotifyingInternalEListImpl<>(1);

			@Override
			public EList<EObject> getContents() {
				return contents;
			}
		};
		logicalModelAdapter.onLogicalModelCreated(model -> setInput(resource, model));
		logicalModelAdapter.onLogicalModelDisposed(__ -> setInput());

		selectionViewer.setInput(resource);
		setInput();

		new AdapterFactoryTreeEditor(selectionViewer.getTree(), adapterFactory);

		getSite().setSelectionProvider(selectionViewer);
		selectionViewer.addSelectionChangedListener(modelSelectionListener);
	}

	@Override
	public void dispose() {
		try {
			selectionViewer.removeSelectionChangedListener(modelSelectionListener);
			diagramViewer.removeSelectionChangedListener(diagramSelectionListener);

			if (propertySheetPage != null) {
				propertySheetPage.dispose();
			}
		} finally {
			super.dispose();
		}
	}

	private ITreeContentProvider createContentProvider(AdapterFactory adapterFactory) {
		return new AdapterFactoryContentProvider(adapterFactory) {

			@Override
			public Object getParent(Object object) {
				if (object instanceof View) {
					View view = (View) object;
					EObject semantic = view.getElement();
					if (semantic != null) {
						MElement<?> element = (MElement<?>) EcoreUtil.getExistingAdapter(semantic,
								MObject.class);
						if ((element != null)
								&& element.getDiagramView().filter(view::equals).isPresent()) {
							// That's the parent
							return element;
						}
						// The containing view, then
						return view.eContainer();
					}
				}
				return super.getParent(object);
			}

		};
	}

	private void setInput() {
		Job computation = new Job("Computing logical model") {
			{
				setSystem(true);
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (!TransactionHelper.isDisposed(editingDomain)
						&& interactionComputation.compareAndSet(this, null)) {
					// I'm the last one standing. Compute the logical model. The adapter
					// will be notified if it's actually computed anew, to update the UI
					try {
						editingDomain.runExclusive(() -> MInteraction.getInstance(sequenceDiagram));
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					}
					return Status.OK_STATUS;
				} else {
					return Status.CANCEL_STATUS;
				}
			}
		};
		interactionComputation.set(computation);
		computation.schedule();
	}

	private void setInput(Resource input, MInteraction model) {
		Control control = selectionViewer.getControl();
		if (!control.isDisposed()) {
			control.getDisplay().asyncExec(() -> {
				if (!control.isDisposed()) {
					Resource resource = (Resource) selectionViewer.getInput();
					resource.getContents().clear();
					resource.getContents().add((EObject) model);

					selectionViewer.refresh();
					selectionViewer.expandToLevel(2);

					// Push selection from diagram
					diagramSelectionChanged(
							new SelectionChangedEvent(diagramViewer, diagramViewer.getSelection()));
				}
			});
		}
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
		if (collection != null) {
			Display display = getSite().getShell().getDisplay();
			if (display != Display.getCurrent()) {
				display.asyncExec(() -> setSelection(new StructuredSelection(collection.toArray())));
			} else {
				setSelection(new StructuredSelection(collection.toArray()));
			}
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

	private void diagramSelectionChanged(SelectionChangedEvent event) {
		withSelectionLock(() -> {
			// Get the selected edit part
			Optional<IGraphicalEditPart> editPart = Optional
					.ofNullable(event.getStructuredSelection().getFirstElement())
					.filter(IGraphicalEditPart.class::isInstance).map(IGraphicalEditPart.class::cast);

			// And its view
			Optional<View> view = editPart.map(IGraphicalEditPart::getNotationView);

			setSelectionToViewer(view.map(Collections::singleton).orElse(Collections.emptySet()));
		});
	}

	private void modelSelectionChanged(SelectionChangedEvent event) {
		withSelectionLock(() -> {
			// Get the selected model element or view
			Optional<?> first = Optional.ofNullable(event.getStructuredSelection().getFirstElement());
			Optional<MElement<?>> element = first.filter(MElement.class::isInstance)
					.map(MElement.class::cast);
			Optional<Element> uml = element.isPresent()
					? element.map(MElement::getElement)
					: first.filter(Element.class::isInstance).map(Element.class::cast);
			Optional<View> view = element.isPresent()
					? element.flatMap(MElement::getDiagramView).filter(View.class::isInstance)
							.map(View.class::cast)
					: first.filter(View.class::isInstance).map(View.class::cast);

			// Find the edit part
			Optional<EditPart> editPart = Optional.empty();
			if (view.isPresent()) {
				// Only attempt to select a selectable edit-part
				editPart = Optional.ofNullable(DiagramEditPartsUtil.getEditPartFromView(view.get(),
						diagramViewer.getRootEditPart())).filter(EditPart::isSelectable);
			} else if (uml.isPresent()) {
				Optional<IGraphicalEditPart> selected = ((List<?>) diagramViewer.getSelectedEditParts())
						.stream().filter(IGraphicalEditPart.class::isInstance)
						.map(IGraphicalEditPart.class::cast).findFirst();
				// Don't change the selection in the diagram if it represents the same element
				if (!selected.isPresent() || (selected.get().resolveSemanticElement() != uml.get())) {
					ISelection selection = uml.map(StructuredSelection::new).get();
					editPart = DiagramEditPartsUtil.getEditPartsFromSelection(selection, diagramViewer)
							.stream().findFirst();
				}
			}

			editPart.ifPresent(ep -> {
				diagramViewer.select(ep);
				// Flush for synchronous selection update to prevent feed-back
				diagramViewer.flush();
			});
		});
	}

	private void withSelectionLock(Runnable action) {
		if (selectionLock) {
			return;
		}

		selectionLock = true;
		try {
			action.run();
		} finally {
			selectionLock = false;
		}
	}

}
