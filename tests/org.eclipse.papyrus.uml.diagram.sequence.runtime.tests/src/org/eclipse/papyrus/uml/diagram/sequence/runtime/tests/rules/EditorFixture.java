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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;
import org.junit.runner.Description;

/**
 * This is the {@code EditorFixture} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class EditorFixture extends ModelFixture.Edit {

	/** Default size of a shape to create or set. */
	public static final Dimension DEFAULT_SIZE = null;

	private IProject project;
	private IFile diFile;
	private IEditorPart editor;
	private boolean isMaximized;

	private int mouseButton = 1;
	private int modifierKeys = 0;

	/**
	 * Initializes me.
	 *
	 * @param testClass
	 * @param path
	 */
	public EditorFixture(Class<?> testClass, String path) {
		super(testClass, path);
	}

	/**
	 * Initializes me.
	 *
	 * @param path
	 */
	public EditorFixture(String path) {
		super(path);
	}

	/**
	 * Initializes me.
	 *
	 */
	public EditorFixture() {
		super();
	}

	//
	// Test fixture lifecycle
	//

	@Override
	protected String[] getPaths(Description description) {
		String[] result = super.getPaths(description);

		if ((result.length == 1) && result[0].endsWith(".di")) {
			String base = result[0].substring(0, result[0].lastIndexOf('.'));
			result = Stream.of(".di", ".uml", ".notation").map(ext -> base + ext).toArray(String[]::new);

			// Initialize the project resources
			for (String path : result) {
				getResourceURL(description, path);
			}
		} else {
			fail("Test does not specify exactly one DI resource.");
		}

		return result;
	}

	@Override
	protected void starting(Description description) {
		Optional<Maximized> maximized = getAnnotation(description, Maximized.class);
		maximized.ifPresent(max -> isMaximized = max.value());

		super.starting(description);

		try {
			// Open the diagram
			IPageManager pageManager = editor.getAdapter(IPageManager.class);
			Diagram diagram = requireSequenceDiagram();

			if (pageManager.isOpen(diagram)) {
				pageManager.selectPage(diagram);
			} else {
				pageManager.openPage(diagram);
			}
		} finally {
			flushDisplayEvents();
		}
	}

	@Override
	protected ResourceSet createResourceSet() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try {
			// Close the Welcome view
			IViewReference welcome = page.findViewReference("org.eclipse.ui.internal.introview");
			if (welcome != null) {
				page.hideView(welcome);
			}

			// Open the editor
			editor = IDE.openEditor(page, diFile);
			if (isMaximized) {
				page.setPartState(page.getReference(editor), IWorkbenchPage.STATE_MAXIMIZED);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
			fail("Failed to open Papyrus editor: " + e.getMessage());
		} finally {
			flushDisplayEvents();
		}

		EditingDomain editingDomain = editor.getAdapter(EditingDomain.class);
		return editingDomain.getResourceSet();
	}

	@Override
	protected URL getResourceURL(Description description, String path) {
		IProject project = getProject(description);
		IFile file = project.getFile(path);

		if (!file.exists()) {
			URL sourceURL = super.getResourceURL(description, path);

			try {
				try (InputStream input = sourceURL.openStream()) {
					file.create(input, true, null);
				}

				if ("di".equals(file.getFileExtension())) {
					// Remember it
					diFile = file;
				}
			} catch (Exception e) {
				e.printStackTrace();
				fail("Failed to create or access test resource: " + e.getMessage());
				return null; // Unreachable
			}
		}

		try {
			return new URL(URI.createPlatformResourceURI(file.getFullPath().toString(), true).toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to access test resource: " + e.getMessage());
			return null; // Unreachable
		}
	}

	protected IProject getProject(Description description) {
		if (project == null) {
			String projectName = description.getMethodName();
			if (projectName == null) {
				// It's a class rule, then
				projectName = description.getDisplayName();
				// Note that if there's no dot, this gets the substring from zero
				projectName = projectName.substring(projectName.lastIndexOf('.') + 1);
				// Strip out all non-letters
				projectName = projectName.replaceAll("\\P{L}", "");
			}
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

			try {
				if (!project.exists()) {
					project.create(null);
				}
				if (!project.isAccessible()) {
					project.open(null);
				}
			} catch (CoreException e) {
				e.printStackTrace();
				fail("Failed to create/open project: " + e.getMessage());
			}
		}

		return project;
	}

	@Override
	protected void finished(Description description) {
		super.finished(description);

		if (editor != null) {
			editor.getSite().getPage().closeEditor(editor, false);
		}
		if (project != null) {
			try {
				project.delete(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
				// Best effort
			}
		}
	}

	//
	// Test step operations
	//

	/**
	 * Create a new shape in the current diagram by automating the creation tool.
	 * Fails if the shape cannot be created or cannot be found in the diagram after
	 * creation.
	 *
	 * @param type
	 *            the type of shape to create
	 * @param location
	 *            the location (mouse pointer) at which to create the shape
	 * @param size
	 *            the size of the shape to create, or {@code null} for the default
	 *            size as would be created when just clicking in the diagram
	 * @return the newly created shape edit-part
	 */
	public EditPart createShape(IElementType type, Point location, Dimension size) {
		DiagramEditPart diagram = getDiagramEditPart();
		EditPartViewer viewer = diagram.getViewer();
		CreateUnspecifiedTypeRequest[] request = { null };

		AspectUnspecifiedTypeCreationTool tool = new AspectUnspecifiedTypeCreationTool(
				singletonList(type)) {

			@Override
			protected Request createTargetRequest() {
				Request result = super.createTargetRequest();
				if (result instanceof CreateUnspecifiedTypeRequest) {
					request[0] = (CreateUnspecifiedTypeRequest) result;
				}
				return result;
			}
		};

		Event mouse = new Event();
		mouse.display = editor.getSite().getShell().getDisplay();
		mouse.widget = viewer.getControl();
		mouse.x = location.x();
		mouse.y = location.y();

		viewer.getEditDomain().setActiveTool(tool);
		tool.setViewer(viewer);

		// Move the mouse to the click location
		mouse.type = SWT.MouseMove;
		tool.mouseMove(new MouseEvent(mouse), viewer);

		// Start the click
		mouse.button = mouseButton;
		mouse.stateMask = modifierKeys;
		mouse.type = SWT.MouseDown;
		tool.mouseDown(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		if (size == DEFAULT_SIZE) {
			// Just a click
			mouse.type = SWT.MouseUp;
			tool.mouseUp(new MouseEvent(mouse), viewer);
		} else {
			// Drag and release
			mouse.type = SWT.MouseMove;
			mouse.x = location.x() + size.width();
			mouse.y = location.y() + size.height();
			tool.mouseDrag(new MouseEvent(mouse), viewer);

			flushDisplayEvents();

			mouse.type = SWT.MouseUp;
			tool.mouseUp(new MouseEvent(mouse), viewer);
		}

		flushDisplayEvents();

		// Find the new edit-part
		assertThat("No unsepecified-type request", request[0], notNullValue());
		CreateViewAndElementRequest createRequest = (CreateViewAndElementRequest) request[0]
				.getRequestForType(type);
		assertThat("No specific create request", createRequest, notNullValue());
		View createdView = (View) createRequest.getViewAndElementDescriptor().getAdapter(View.class);
		assertThat("No view created", createdView, notNullValue());
		EditPart result = (EditPart) viewer.getEditPartRegistry().get(createdView);
		assertThat("New edit-part not found", result, notNullValue());
		return result;
	}

	/**
	 * Create a new connection in the current diagram by automating the creation
	 * tool. Fails if the connection cannot be created or cannot be found in the
	 * diagram after creation.
	 *
	 * @param type
	 *            the type of shape to create
	 * @param start
	 *            the location (mouse pointer) at which to start drawing the
	 *            connection
	 * @param finish
	 *            the location (mouse pointer) at which to finish drawing the
	 *            connection
	 * @return the newly created connection edit-part
	 */
	public EditPart createConnection(IElementType type, Point start, Point finish) {
		return drawConnection(type, start, finish, true);
	}

	EditPart drawConnection(IElementType type, Point start, Point finish, boolean complete) {
		DiagramEditPart diagram = getDiagramEditPart();
		EditPartViewer viewer = diagram.getViewer();
		CreateUnspecifiedTypeConnectionRequest[] request = { null };

		@SuppressWarnings("restriction")
		ConnectionCreationTool tool = new org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequenceConnectionCreationTool(
				singletonList(type)) {
			@Override
			protected CreateConnectionRequest createTargetRequest() {
				CreateConnectionRequest result = super.createTargetRequest();
				if (result instanceof CreateUnspecifiedTypeConnectionRequest) {
					request[0] = (CreateUnspecifiedTypeConnectionRequest) result;
				}
				return result;
			}
		};

		Event mouse = new Event();
		mouse.display = editor.getSite().getShell().getDisplay();
		mouse.widget = viewer.getControl();
		mouse.x = start.x();
		mouse.y = start.y();

		viewer.getEditDomain().setActiveTool(tool);
		tool.setViewer(viewer);

		// Move the mouse to the start location
		mouse.type = SWT.MouseMove;
		tool.mouseMove(new MouseEvent(mouse), viewer);

		// Click
		mouse.button = mouseButton;
		mouse.stateMask = modifierKeys;
		mouse.type = SWT.MouseDown;
		tool.mouseDown(new MouseEvent(mouse), viewer);
		mouse.type = SWT.MouseUp;
		tool.mouseUp(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		// Move and, if completing, click again
		mouse.type = SWT.MouseMove;
		mouse.button = 0;
		mouse.x = finish.x();
		mouse.y = finish.y();
		tool.mouseMove(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		if (complete) {
			mouse.button = mouseButton;
			mouse.stateMask = modifierKeys;
			mouse.type = SWT.MouseDown;
			tool.mouseDown(new MouseEvent(mouse), viewer);
			mouse.type = SWT.MouseUp;
			tool.mouseUp(new MouseEvent(mouse), viewer);

			flushDisplayEvents();
		}

		if (!complete) {
			return null;
		}

		// Find the new edit-part
		assertThat("No unsepecified-type request", request[0], notNullValue());
		CreateConnectionViewAndElementRequest createRequest = (CreateConnectionViewAndElementRequest) request[0]
				.getRequestForType(type);
		assertThat("No specific create request", createRequest, notNullValue());
		View createdView = (View) createRequest.getConnectionViewAndElementDescriptor()
				.getAdapter(View.class);
		assertThat("No view created", createdView, notNullValue());
		EditPart result = (EditPart) getDiagramEditPart().getViewer().getEditPartRegistry()
				.get(createdView);
		assertThat("New edit-part not found", result, notNullValue());
		return result;
	}

	/**
	 * Operate the mouse pointer as though to create a new connection in the current
	 * diagram, but do not click to complete it.
	 *
	 * @param type
	 *            the type of shape to create
	 * @param start
	 *            the location (mouse pointer) at which to start drawing the
	 *            connection
	 * @param finish
	 *            the location (mouse pointer) at which to hover the end of the
	 *            connection
	 */
	public void hoverConnection(IElementType type, Point start, Point finish) {
		drawConnection(type, start, finish, false);
	}

	/**
	 * Type the escape key in the selection tool on the current diagram.
	 */
	public void escape() {
		DiagramEditPart diagram = getDiagramEditPart();
		EditPartViewer viewer = diagram.getViewer();
		SelectionTool tool = createSelectionTool();

		Event key = new Event();
		key.display = editor.getSite().getShell().getDisplay();
		key.widget = viewer.getControl();
		key.character = SWT.ESC;

		viewer.getEditDomain().setActiveTool(tool);
		tool.setViewer(viewer);

		// Type the key
		key.type = SWT.KeyDown;
		tool.keyDown(new KeyEvent(key), viewer);
		tool.keyUp(new KeyEvent(key), viewer);

		flushDisplayEvents();
	}

	/**
	 * Select an object in the diagram and drag it to another location.
	 *
	 * @param start
	 *            the location (mouse pointer) at which to start dragging the
	 *            selection
	 * @param finish
	 *            the location (mouse pointer) at which to finish dragging the
	 *            selection
	 */
	public void moveSelection(Point start, Point finish) {
		DiagramEditPart diagram = getDiagramEditPart();
		EditPartViewer viewer = diagram.getViewer();

		SelectionTool tool = createSelectionTool();

		Event mouse = new Event();
		mouse.display = editor.getSite().getShell().getDisplay();
		mouse.widget = viewer.getControl();
		mouse.x = start.x();
		mouse.y = start.y();

		viewer.getEditDomain().setActiveTool(tool);
		tool.setViewer(viewer);

		// Move the mouse to the start location
		mouse.type = SWT.MouseMove;
		tool.mouseMove(new MouseEvent(mouse), viewer);

		// Click to select
		mouse.button = mouseButton;
		mouse.stateMask = modifierKeys;
		mouse.type = SWT.MouseDown;
		tool.mouseDown(new MouseEvent(mouse), viewer);
		mouse.type = SWT.MouseUp;
		tool.mouseUp(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		viewer.getEditDomain().setActiveTool(tool);
		tool.setViewer(viewer);

		// Mouse down to start dragging
		mouse.type = SWT.MouseDown;
		// button is still 1
		tool.mouseDown(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		// Drag in 5-pixel increments
		mouse.type = SWT.MouseMove;
		// button is still 1
		do {
			if (mouse.x < finish.x()) {
				mouse.x = min(mouse.x + 5, finish.x());
			} else if (mouse.x > finish.x()) {
				mouse.x = max(mouse.x - 5, finish.x());
			}
			if (mouse.y < finish.y()) {
				mouse.y = min(mouse.y + 5, finish.y());
			} else if (mouse.y > finish.y()) {
				mouse.y = max(mouse.y - 5, finish.y());
			}
			tool.mouseDrag(new MouseEvent(mouse), viewer);

			flushDisplayEvents();
		} while ((mouse.x != finish.x()) || (mouse.y != finish.y()));

		// Release
		mouse.type = SWT.MouseDown;
		// button is still 1
		mouse.type = SWT.MouseUp;
		tool.mouseUp(new MouseEvent(mouse), viewer);

		flushDisplayEvents();
	}

	@Override
	public void undo() {
		IOperationHistory history = editor.getSite().getService(IWorkbenchOperationSupport.class)
				.getOperationHistory();
		IUndoContext ctx = editor.getAdapter(IUndoContext.class);

		IUndoableOperation operation = history.getUndoOperation(ctx);

		assertThat("no command to undo", operation, notNullValue());
		assertThat("command is not undoable", operation.canUndo(), is(true));

		try {
			history.undo(ctx, new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			e.printStackTrace();
			fail("Undo failed: " + e.getLocalizedMessage());
		}

		flushDisplayEvents();
	}

	@Override
	public void redo() {
		IOperationHistory history = editor.getSite().getService(IWorkbenchOperationSupport.class)
				.getOperationHistory();
		IUndoContext ctx = editor.getAdapter(IUndoContext.class);

		IUndoableOperation operation = history.getRedoOperation(ctx);

		assertThat("no command to redo", operation, notNullValue());
		assertThat("command is not redoable", operation.canRedo(), is(true));

		try {
			history.redo(ctx, new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			e.printStackTrace();
			fail("Undo failed: " + e.getLocalizedMessage());
		}

		flushDisplayEvents();
	}

	//
	// Utilities
	//

	@Override
	public final EditingDomain getEditingDomain() {
		return getDiagramEditPart().getEditingDomain();
	}

	public final void flushDisplayEvents() {
		Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
			// Pass
		}
	}

	public void maximize() {
		IWorkbenchPage page = editor.getSite().getPage();
		page.setPartState(page.getReference(editor), IWorkbenchPage.STATE_MAXIMIZED);
		flushDisplayEvents();
	}

	public final Diagram requireSequenceDiagram() {
		return getSequenceDiagram().orElseGet(failOnAbsence("No sequence diagram in the test model."));
	}

	public DiagramEditPart getDiagramEditPart() {
		ISashWindowsContainer sashContainer = editor.getAdapter(ISashWindowsContainer.class);
		IPage page = IPageUtils.lookupModelPage(sashContainer, requireSequenceDiagram());
		return Optional.ofNullable(page).filter(IEditorPage.class::isInstance).map(IEditorPage.class::cast)
				.map(IEditorPage::getIEditorPart).filter(DiagramEditor.class::isInstance)
				.map(DiagramEditor.class::cast).map(DiagramEditor::getDiagramEditPart)
				.orElseGet(failOnAbsence("Sequence diagram not open."));
	}

	/**
	 * Obtain a fake supplier that just fails the test with the given
	 * {@code message} instead of supplying a result.
	 *
	 * @param message
	 *            the failure message
	 * @return the fake supplier
	 */
	private static <T> Supplier<T> failOnAbsence(String message) {
		return () -> {
			fail(message);
			return null;
		};
	}

	/**
	 * Create a point location (useful as a static import for test readability).
	 *
	 * @param x
	 *            the x coördinate
	 * @param y
	 *            the y coördinate
	 * @return the point
	 */
	public static Point at(int x, int y) {
		return new Point(x, y);
	}

	/**
	 * Create a size dimension (useful as a static import for test readability).
	 *
	 * @param width
	 *            the size width
	 * @param height
	 *            the the size height
	 * @return the size
	 */
	public static Dimension sized(int width, int height) {
		return new Dimension(width, height);
	}

	/**
	 * Run an {@code action} with {@code modifiers}.
	 *
	 * @param modifiers
	 *            the modifiers to apply to the {@code action}
	 * @param action
	 *            an action to run under the influence of the {@code modifiers}
	 */
	public final void with(Modifiers modifiers, Runnable action) {
		modifiers.apply();

		try {
			action.run();
		} finally {
			modifiers.unapply();
		}
	}

	/**
	 * Run an {@code action} computing a result with {@code modifiers}.
	 *
	 * @param modifiers
	 *            the modifiers to apply to the {@code action}
	 * @param action
	 *            an action to run under the influence of the {@code modifiers}
	 */
	public final <T> T with(Modifiers modifiers, Supplier<T> action) {
		final T result;

		modifiers.apply();

		try {
			result = action.get();
		} finally {
			modifiers.unapply();
		}

		return result;
	}

	/**
	 * Obtain modifiers applying a mouse button to mouse events.
	 *
	 * @param mouseButton
	 *            the mouse button
	 * @return the mouse button modifiers
	 */
	public Modifiers mouseButton(int mouseButton) {
		return ModifiersImpl.withInt( //
				() -> setMouseButton(mouseButton), //
				this::setMouseButton);
	}

	private int setMouseButton(int mouseButton) {
		int result = this.mouseButton;
		this.mouseButton = mouseButton;
		return result;
	}

	/**
	 * Obtain modifiers applying the option to allow semantic re-ordering (which is
	 * <tt>Command</tt> on Mac and <tt>Ctrl</tt> on other platforms).
	 *
	 * @return the modifier key modifiers
	 */
	public Modifiers allowSemanticReordering() {
		return modifierKey(Platform.OS_MACOSX.equals(Platform.getOS()) ? SWT.COMMAND : SWT.CTRL);
	}

	/**
	 * Obtain modifiers applying a modifier key to mouse and keyboard events.
	 *
	 * @param modifierKey
	 *            the modifier key code
	 * @return the modifier key modifiers
	 */
	public Modifiers modifierKey(int modifierKey) {
		return ModifiersImpl.withInt( //
				() -> setModifierKeys(this.modifierKeys | modifierKey), //
				this::setModifierKeys);
	}

	private int setModifierKeys(int modifierKeys) {
		int result = this.modifierKeys;
		this.modifierKeys = modifierKeys;
		return result;
	}

	/**
	 * Absence of modifiers, useful for clients that require some kind of modifiers
	 * instance, even if actual modifiers are not needed.
	 *
	 * @return a modifiers implementation that does nothing
	 */
	public Modifiers unmodified() {
		return ModifiersImpl.with(this::pass, this::pass);
	}

	private void pass() {
		// Pass
	}

	@SuppressWarnings("restriction")
	private SelectionTool createSelectionTool() {
		return new org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequenceSelectionTool();
	}

	//
	// Nested types
	//

	/**
	 * Protocol for modifiers rules for interaction gestures in the diagram.
	 */
	public interface Modifiers {
		/** Applies my modifiers. */
		void apply();

		/** Removes my modifiers. */
		void unapply();

		/**
		 * Wrap another {@code modifiers} around me.
		 *
		 * @param modifiers
		 *            other modifiers to apply around mine
		 * @return the composed modifiers
		 */
		default Modifiers and(Modifiers modifiers) {
			return ModifiersImpl.with( //
					() -> {
						modifiers.apply();
						this.apply();
					}, () -> {
						this.unapply();
						modifiers.unapply();
					});
		}
	}

	private static final class ModifiersImpl implements Modifiers {
		private final Runnable apply;
		private final Runnable unapply;

		private ModifiersImpl(Runnable apply, Runnable unapply) {
			super();

			this.apply = apply;
			this.unapply = unapply;
		}

		static Modifiers with(Runnable apply, Runnable unapply) {
			return new ModifiersImpl(apply, unapply);
		}

		static Modifiers withInt(IntSupplier apply, IntConsumer unapply) {
			final int[] holder = { 0 };
			return with(() -> holder[0] = apply.getAsInt(), () -> unapply.accept(holder[0]));
		}

		@Override
		public void apply() {
			apply.run();
		}

		@Override
		public void unapply() {
			unapply.run();
		}
	}
}
