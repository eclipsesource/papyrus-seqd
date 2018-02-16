/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.part;

import java.util.EventObject;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.Tool;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.common.core.service.ProviderChangeEvent;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.internal.parts.PaletteToolTransferDragSourceListener;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.gmf.util.OperationHistoryDirtyState;
import org.eclipse.papyrus.infra.gmfdiag.common.GmfMultiDiagramDocumentProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteContextMenuProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteViewer;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.uml.diagram.common.listeners.DropTargetListener;
import org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Papyrus editor integration for the sequence diagram.
 */
public class LightweightSequenceDiagramEditor extends UmlGmfDiagramEditor implements IGotoMarker, IProviderChangeListener {

	public static final String ID = "org.eclipse.papyrus.uml.diagram.sequence.runtime.part.UMLDiagramEditorID"; //$NON-NLS-1$

	public static final String CONTEXT_ID = "org.eclipse.papyrus.uml.diagram.sequence.runtime.diagramContext"; //$NON-NLS-1$

	private KeyHandler paletteKeyHandler = null;

	private MouseListener paletteMouseListener = null;

	private TransactionalEditingDomain editingDomain;

	private IDocumentProvider documentProvider;

	private OperationHistoryDirtyState dirtyState;

	/**
	 * The location of diagram icon in the plug-in
	 */
	private static final String DIAG_IMG_PATH = "icons/obj16/Diagram_LightweightSequence.gif"; //$NON-NLS-1$

	/**
	 * The image descriptor of the diagram icon
	 */
	private static final ImageDescriptor DIAG_IMG_DESC = AbstractUIPlugin
			.imageDescriptorFromPlugin(Activator.PLUGIN_ID, DIAG_IMG_PATH);

	public LightweightSequenceDiagramEditor(ServicesRegistry servicesRegistry, Diagram diagram)
			throws ServiceException {
		super(servicesRegistry, diagram);

		// adds a listener to the palette service, which reacts to palette customizations
		PapyrusPaletteService.getInstance().addProviderChangeListener(this);

		// Share the same editing provider
		editingDomain = servicesRegistry.getService(TransactionalEditingDomain.class);
		documentProvider = new GmfMultiDiagramDocumentProvider(editingDomain);

		// overrides editing domain created by super constructor
		setDocumentProvider(documentProvider);
	}

	@Override
	protected String getContextID() {
		return CONTEXT_ID;
	}

	@Override
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
		PaletteRoot paletteRoot;
		if (existingPaletteRoot == null) {
			paletteRoot = PapyrusPaletteService.getInstance().createPalette(this, getDefaultPaletteContent());
		} else {
			PapyrusPaletteService.getInstance().updatePalette(existingPaletteRoot, this,
					getDefaultPaletteContent());
			paletteRoot = existingPaletteRoot;
		}
		applyCustomizationsToPalette(paletteRoot);
		return paletteRoot;
	}

	@Override
	protected PreferencesHint getPreferencesHint() {
		return Activator.DIAGRAM_PREFERENCES_HINT;
	}

	@Override
	public String getContributorId() {
		return Activator.PLUGIN_ID;
	}

	/**
	
	 */
	@Override
	protected final IDocumentProvider getDocumentProvider(IEditorInput input) {
		return documentProvider;
	}

	@Override
	public TransactionalEditingDomain getEditingDomain() {
		return editingDomain;
	}

	@Override
	protected final void setDocumentProvider(IEditorInput input) {
		// Already set in the constructor
	}

	@Override
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	
	 */
	@Override
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/**
	
	 */
	@Override
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		// Nothing
	}

	@Override
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getGraphicalViewer().getSelection());
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		DiagramEditorContextMenuProvider provider = new DiagramEditorContextMenuProvider(this,
				getDiagramGraphicalViewer());
		getDiagramGraphicalViewer().setContextMenu(provider);
		getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU, provider,
				getDiagramGraphicalViewer());
	}

	@Override
	protected TransactionalEditingDomain createEditingDomain() {
		// Already configured
		return editingDomain;
	}

	@Override
	protected void configureDiagramEditDomain() {
		super.configureDiagramEditDomain();
		getDiagramEditDomain().getDiagramCommandStack().addCommandStackListener(new CommandStackListener() {

			@Override
			public void commandStackChanged(EventObject event) {
				if (Display.getCurrent() == null) {
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							firePropertyChange(IEditorPart.PROP_DIRTY);
						}
					});
				} else {
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		// The saving of the resource is done by the CoreMultiDiagramEditor
		getDirtyState().saved();
	}

	protected OperationHistoryDirtyState getDirtyState() {
		if (dirtyState == null) {
			dirtyState = OperationHistoryDirtyState.newInstance(getUndoContext(), getOperationHistory());
		}
		return dirtyState;
	}

	@Override
	protected void setUndoContext(IUndoContext context) {
		if (dirtyState != null) {
			dirtyState.dispose();
			dirtyState = null;
		}

		super.setUndoContext(context);
	}

	@Override
	public boolean isDirty() {
		return getDirtyState().isDirty();
	}

	@Override
	public void providerChanged(ProviderChangeEvent event) {
		// update the palette if the palette service has changed
		if (PapyrusPaletteService.getInstance().equals(event.getSource())) {
			PapyrusPaletteService.getInstance().updatePalette(getPaletteViewer().getPaletteRoot(), this,
					getDefaultPaletteContent());
		}
	}

	@Override
	public void dispose() {
		// remove palette service listener
		// remove preference listener
		PapyrusPaletteService.getInstance().removeProviderChangeListener(this);

		if (dirtyState != null) {
			dirtyState.dispose();
			dirtyState = null;
		}

		if (titleImage != null) {
			titleImage.dispose();
			titleImage = null;
		}

		super.dispose();
	}

	protected PaletteViewer getPaletteViewer() {
		return getEditDomain().getPaletteViewer();
	}

	@Override
	protected PaletteViewer constructPaletteViewer() {
		return new PapyrusPaletteViewer();
	}

	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		getEditDomain().setPaletteRoot(createPaletteRoot(null));
		return new PaletteViewerProvider(getEditDomain()) {

			/**
			 * Override to provide the additional behavior for the tools. Will intialize with a
			 * PaletteEditPartFactory that has a TrackDragger that understand how to handle the
			 * mouseDoubleClick event for shape creation tools. Also will initialize the palette with a
			 * defaultTool that is the SelectToolEx that undestands how to handle the enter key which will
			 * result in the creation of the shape also.
			 */
			@Override
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);

				// customize menu...
				viewer.setContextMenu(new PapyrusPaletteContextMenuProvider(viewer));

				viewer.getKeyHandler().setParent(getPaletteKeyHandler());
				viewer.getControl().addMouseListener(getPaletteMouseListener());

				// Add a transfer drag target listener that is supported on
				// palette template entries whose template is a creation tool.
				// This will enable drag and drop of the palette shape creation
				// tools.
				viewer.addDragSourceListener(new PaletteToolTransferDragSourceListener(viewer));
				viewer.setCustomizer(createPaletteCustomizer());
			}

			@Override
			public PaletteViewer createPaletteViewer(Composite parent) {
				PaletteViewer pViewer = constructPaletteViewer();
				pViewer.createControl(parent);
				configurePaletteViewer(pViewer);
				hookPaletteViewer(pViewer);
				return pViewer;
			}

			/**
			 * @return Palette Key Handler for the palette
			 */
			private KeyHandler getPaletteKeyHandler() {

				if (paletteKeyHandler == null) {

					paletteKeyHandler = new KeyHandler() {

						/**
						 * Processes a <i>key released </i> event. This method is called by the Tool whenever
						 * a key is released, and the Tool is in the proper state. Override to support
						 * pressing the enter key to create a shape or connection (between two selected
						 * shapes)
						 * 
						 * @param event
						 *            the KeyEvent
						 * @return <code>true</code> if KeyEvent was handled in some way
						 */
						@Override
						public boolean keyReleased(KeyEvent event) {

							if (event.keyCode == SWT.Selection) {

								Tool tool = getPaletteViewer().getActiveTool().createTool();

								if (toolSupportsAccessibility(tool)) {

									tool.keyUp(event, getDiagramGraphicalViewer());

									// deactivate current selection
									getPaletteViewer().setActiveTool(null);

									return true;
								}

							}
							return super.keyReleased(event);
						}

					};

				}
				return paletteKeyHandler;
			}

			/**
			 * @return Palette Mouse listener for the palette
			 */
			private MouseListener getPaletteMouseListener() {

				if (paletteMouseListener == null) {

					paletteMouseListener = new MouseListener() {

						/**
						 * Flag to indicate that the current active tool should be cleared after a mouse
						 * double-click event.
						 */
						private boolean clearActiveTool = false;

						/**
						 * Override to support double-clicking a palette tool entry to create a shape or
						 * connection (between two selected shapes).
						 * 
						 * @see MouseListener#mouseDoubleClick(MouseEvent)
						 */
						@Override
						public void mouseDoubleClick(MouseEvent e) {
							Tool tool = getPaletteViewer().getActiveTool().createTool();

							if (toolSupportsAccessibility(tool)) {

								tool.setViewer(getDiagramGraphicalViewer());
								tool.setEditDomain(getDiagramGraphicalViewer().getEditDomain());
								tool.mouseDoubleClick(e, getDiagramGraphicalViewer());

								// Current active tool should be deactivated,
								// but if it is down here it will get
								// reactivated deep in GEF palette code after
								// receiving mouse up events.
								clearActiveTool = true;
							}
						}

						@Override
						public void mouseDown(MouseEvent e) {
							// do nothing
						}

						@Override
						public void mouseUp(MouseEvent e) {
							// Deactivate current active tool here if a
							// double-click was handled.
							if (clearActiveTool) {
								getPaletteViewer().setActiveTool(null);
								clearActiveTool = false;
							}

						}
					};

				}
				return paletteMouseListener;
			}

		};
	}

	@Override
	public GraphicalViewer getGraphicalViewer() {
		return super.getGraphicalViewer();
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();

		// Enable Drop
		getDiagramGraphicalViewer().addDropTargetListener(
				new DropTargetListener(getDiagramGraphicalViewer(), LocalSelectionTransfer.getTransfer()) {

					@Override
					protected Object getJavaObject(TransferData data) {
						// It is usual for the transfer data not to be set because it is available locally
						return LocalSelectionTransfer.getTransfer().getSelection();
					}

					@Override
					protected TransactionalEditingDomain getTransactionalEditingDomain() {
						return getEditingDomain();
					}
				});

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (getSite().getPage().getActiveEditor() instanceof IMultiDiagramEditor) {
			IMultiDiagramEditor editor = (IMultiDiagramEditor)getSite().getPage().getActiveEditor();
			// If not the active editor, ignore selection changed.
			if (this.equals(editor.getActiveEditor())) {
				updateActions(getSelectionActions());
				super.selectionChanged(part, selection);
			} else {
				super.selectionChanged(part, selection);
			}
		} else {
			super.selectionChanged(part, selection);
		}
		// from
		// org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor.selectionChanged(IWorkbenchPart,
		// ISelection)
		if (part == this) {
			rebuildStatusLine();
		}
	}

	/** The editor splitter. */
	private Composite splitter;

	private Image titleImage;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(LabelInternationalization.getInstance().getDiagramLabel(getDiagram()));
		titleImage = DIAG_IMG_DESC.createImage();
		setTitleImage(titleImage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInput(IEditorInput input) {
		try {
			// Provide an URI with fragment in order to reuse the same Resource
			// and set the diagram to the fragment.
			URIEditorInput uriInput = new URIEditorInput(EcoreUtil.getURI(getDiagram()));
			doSetInput(uriInput, true);
		} catch (CoreException x) {
			String title = "Problem opening"; //$NON-NLS-1$
			String msg = "Cannot open input element:"; //$NON-NLS-1$
			Shell shell = getSite().getShell();
			ErrorDialog.openError(shell, title, msg, x.getStatus());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createGraphicalViewer(Composite parent) {
		splitter = parent;
		super.createGraphicalViewer(parent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		splitter.setFocus();
		super.setFocus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEditingDomainID() {
		return "org.eclipse.papyrus.uml.diagram.sequence.EditingDomain"; //$NON-NLS-1$
	}

}
