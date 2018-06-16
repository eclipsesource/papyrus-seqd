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

import static java.util.Collections.singletonList;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.SelectionToolEx;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeConnectionTool;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.junit.runner.Description;

/**
 * This is the {@code EditorFixture} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class EditorFixture extends ModelFixture {

	/** Default size of a shape to create or set. */
	public static final Dimension DEFAULT_SIZE = null;

	private IProject project;
	private IFile diFile;
	private IEditorPart editor;
	private boolean isMaximized;

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
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getMethodName());

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

		@SuppressWarnings("unchecked")
		Set<EditPart> originalEditParts = new HashSet<EditPart>(viewer.getEditPartRegistry().values());

		AspectUnspecifiedTypeCreationTool tool = new AspectUnspecifiedTypeCreationTool(singletonList(type));

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
		mouse.button = 1;
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
		@SuppressWarnings("unchecked")
		Set<EditPart> newEditParts = new HashSet<EditPart>(viewer.getEditPartRegistry().values());
		newEditParts.removeAll(originalEditParts);
		while (newEditParts.removeIf(ep -> newEditParts.contains(ep.getParent()))) {
			// Keep only the topmost new edit-parts (that aren't nested in other new
			// edit-parts)
		}

		return newEditParts.stream().findFirst().orElseGet(failOnAbsence("New edit-part not found"));
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
		DiagramEditPart diagram = getDiagramEditPart();
		EditPartViewer viewer = diagram.getViewer();

		@SuppressWarnings("unchecked")
		Set<EditPart> originalEditParts = new HashSet<EditPart>(viewer.getEditPartRegistry().values());

		AspectUnspecifiedTypeConnectionTool tool = new AspectUnspecifiedTypeConnectionTool(
				singletonList(type));

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
		mouse.button = 1;
		mouse.type = SWT.MouseDown;
		tool.mouseDown(new MouseEvent(mouse), viewer);
		mouse.type = SWT.MouseUp;
		tool.mouseUp(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		// Move and click again
		mouse.type = SWT.MouseMove;
		mouse.button = 0;
		mouse.x = finish.x();
		mouse.y = finish.y();
		tool.mouseMove(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		mouse.button = 1;
		mouse.type = SWT.MouseDown;
		tool.mouseDown(new MouseEvent(mouse), viewer);
		mouse.type = SWT.MouseUp;
		tool.mouseUp(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		// Find the new edit-part
		@SuppressWarnings("unchecked")
		Set<EditPart> newEditParts = new HashSet<EditPart>(viewer.getEditPartRegistry().values());
		newEditParts.removeAll(originalEditParts);
		while (newEditParts.removeIf(ep -> !(ep instanceof ConnectionEditPart))) {
			// Keep only the topmost new edit-parts (that aren't nested in other new
			// edit-parts)
		}

		return newEditParts.stream().findFirst()
				.orElseGet(failOnAbsence("New connection edit-part not found"));
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

		SelectionTool tool = new SelectionToolEx();

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
		mouse.button = 1;
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

		// Drag
		mouse.type = SWT.MouseMove;
		// button is still 1
		mouse.x = finish.x();
		mouse.y = finish.y();
		tool.mouseDrag(new MouseEvent(mouse), viewer);

		flushDisplayEvents();

		// Release
		mouse.type = SWT.MouseDown;
		// button is still 1
		mouse.type = SWT.MouseUp;
		tool.mouseUp(new MouseEvent(mouse), viewer);

		flushDisplayEvents();
	}

	//
	// Utilities
	//

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

}
