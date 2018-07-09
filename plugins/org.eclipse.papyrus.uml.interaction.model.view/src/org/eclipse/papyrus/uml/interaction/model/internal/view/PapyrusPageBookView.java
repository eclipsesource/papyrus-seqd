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
 *   Antonio Campesino - Split the class in a generic class (PapyrusPageBookView) 
 *                       and specific class.
 *****************************************************************************/

package org.eclipse.papyrus.uml.interaction.model.internal.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.sasheditor.editor.DefaultPageLifeCycleEventListener;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

/**
 * A view presenting the logical model in the (last) selected lightweight
 * sequence diagram.
 *
 * @author Christian W. Damus
 * @author Antonio Campesino - Generalizing the view (renamed to PapyrusPageBookView) and implementing dispose()
 */
public abstract class PapyrusPageBookView extends PageBookView {

	private final IPageLifeCycleEventsListener papyrusSubEditorListener = new PapyrusSubEditorListener();
	private final Map<IMultiDiagramEditor, List<PageRec>> multiDiagramPageRecords = new HashMap<>();

	private ISashWindowsContainer sashWindowsContainer;
	private IMultiDiagramEditor currentPapyrusEditor;

	/**
	 * Initializes me.
	 *
	 */
	public PapyrusPageBookView() {
		super();
	}

	@Override
	protected IPage createDefaultPage(PageBook book) {
		Page page = new Page() {

			private Label label;

			@Override
			public void setFocus() {
				// Pass
			}

			@Override
			public Control getControl() {
				return label;
			}

			@Override
			public void createControl(Composite parent) {
				label = new Label(parent, SWT.NONE);
				label.setText("No sequence diagram open.");
			}
		};
		initPage(page);
		page.createControl(book);
		return page;
	}

	protected abstract IPageBookViewPage doCreateSubPage(UmlGmfDiagramEditor diagramEditor);
	
	@Override
	protected final PageRec doCreatePage(IWorkbenchPart part) {
		if (part instanceof UmlGmfDiagramEditor) {
			UmlGmfDiagramEditor diagramEditor = (UmlGmfDiagramEditor) part;
			Diagram diagram = diagramEditor.getDiagram();
			if ((diagram != null) && isSubpageImportant((UmlGmfDiagramEditor)part, diagram)) {	
				IPageBookViewPage result = doCreateSubPage((UmlGmfDiagramEditor)part);
				initPage(result);
				result.createControl(getPageBook());
				return currentPapyrusEditor == null
						? new PageRec(part, result)
						: new SubPageRec(currentPapyrusEditor, part, result);
			}
		} 
		return null;				
	}

	protected abstract void doDestroySubPage(UmlGmfDiagramEditor part, IPage page);

	@Override
	protected final void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		if (part instanceof UmlGmfDiagramEditor)
			doDestroySubPage((UmlGmfDiagramEditor)part,pageRecord.page);
		pageRecord.page.dispose();
		pageRecord.dispose();
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		super.partOpened(part);
		partActivated(part);
	}
		
	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof IMultiDiagramEditor) {
			currentPapyrusEditor = (IMultiDiagramEditor) part;
			setSashWindowsContainer(part.getAdapter(ISashWindowsContainer.class));

			// This is the part that really activated
			part = currentPapyrusEditor.getActiveEditor();
		}

		basicPartActivated(part);
	}

	private void setSashWindowsContainer(ISashWindowsContainer container) {
		if (sashWindowsContainer != null) {
			sashWindowsContainer.removePageLifeCycleListener(papyrusSubEditorListener);
		}

		sashWindowsContainer = container;

		if (sashWindowsContainer != null) {
			sashWindowsContainer.addPageLifeCycleListener(papyrusSubEditorListener);
		}
	}

	void basicPartActivated(IWorkbenchPart part) {
		super.partActivated(part);
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		try {
			super.partDeactivated(part);
		} finally {
			if (part == currentPapyrusEditor) {
				setSashWindowsContainer(null);
				currentPapyrusEditor = null;
			}
		}
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		super.partClosed(part);

		if (part instanceof IMultiDiagramEditor) {
			List<PageRec> toDispose = new ArrayList<>(
					multiDiagramPageRecords.getOrDefault(part, Collections.emptyList()));
			toDispose.forEach(rec -> partClosed(rec.part));
		}
	}

	@Override
	protected IWorkbenchPart getBootstrapPart() {
		return getSite().getPage().getActiveEditor();
	}

	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		return (part instanceof IEditorPart);
	}
	
	protected boolean isSubpageImportant(UmlGmfDiagramEditor part, Diagram diagram) {
		return true;
	}

	@Override
	public void dispose() {
		if (sashWindowsContainer != null && !sashWindowsContainer.isDisposed())
			setSashWindowsContainer(null);
		super.dispose();
	}
	
	//
	// Nested types
	//

	class SubPageRec extends PageRec {

		IMultiDiagramEditor parent;

		SubPageRec(IMultiDiagramEditor editor, IWorkbenchPart part, IPage page) {
			super(part, page);

			parent = editor;

			multiDiagramPageRecords.computeIfAbsent(editor, __ -> new ArrayList<>()).add(this);
		}

		@Override
		public void dispose() {
			List<PageRec> records = multiDiagramPageRecords.get(parent);
			if (records != null) {
				records.remove(this);
				if (records.isEmpty()) {
					multiDiagramPageRecords.remove(parent);
				}
			}

			parent = null;

			super.dispose();
		}

	}

	class PapyrusSubEditorListener extends DefaultPageLifeCycleEventListener {

		@Override
		public void pageActivated(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage page) {
			if (page instanceof IEditorPage) {
				basicPartActivated(((IEditorPage) page).getIEditorPart());
			} else {
				basicPartActivated(currentPapyrusEditor);
			}
		}

		@Override
		public void pageDeactivated(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage page) {
			if (page instanceof IEditorPage) {
				partDeactivated(((IEditorPage) page).getIEditorPart());
			} 
		}

		@Override
		public void pageOpened(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage page) {
			if (page instanceof IEditorPage) {
				partOpened(((IEditorPage) page).getIEditorPart());
			}
		}

		@Override
		public void pageClosed(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage page) {
			if (page instanceof IEditorPage) {
				partClosed(((IEditorPage) page).getIEditorPart());
			}
		}

	}
}
