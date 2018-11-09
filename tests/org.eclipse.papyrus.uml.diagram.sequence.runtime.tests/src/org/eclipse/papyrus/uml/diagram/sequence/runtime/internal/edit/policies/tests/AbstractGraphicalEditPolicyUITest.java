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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.handles.ResizeHandle;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestRule;

/**
 * Abstract skeleton of a test that exercises graphical edit-policies.
 *
 * @author Christian W. Damus
 */
public abstract class AbstractGraphicalEditPolicyUITest {

	// Size of a resize handle figure
	private static final int RESIZE_HANDLE_SIZE = 7;

	/**
	 * Tolerance for assertion of locations based on the dragging of a resize
	 * handle.
	 */
	protected static final int RESIZE_TOLERANCE = (int) Math.floor(RESIZE_HANDLE_SIZE / 2.0);

	/** The width of an execution specification. */
	protected static final int EXEC_WIDTH = 10;

	/** Some Linux environments are off by 1 in a lot of test scenarios. */
	@ClassRule
	public static final TestRule TOLERANCE = GEFMatchers.defaultTolerance(1, Platform.OS_LINUX);

	@Rule
	public final EditorFixture editor = new EditorFixture();

	private EditPart lastCreatedEditPart;

	/**
	 * Initializes me.
	 */
	public AbstractGraphicalEditPolicyUITest() {
		super();
	}

	@After
	public void cleanUp() {
		lastCreatedEditPart = null;
	}

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
	protected EditPart createShape(IElementType type, Point location, Dimension size) {
		lastCreatedEditPart = editor.createShape(type, location, size);
		return lastCreatedEditPart;
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
	protected EditPart createConnection(IElementType type, Point start, Point finish) {
		lastCreatedEditPart = editor.createConnection(type, start, finish);
		return lastCreatedEditPart;
	}

	/**
	 * Query the last created edit-part.
	 *
	 * @return the last edit-part created by the test
	 */
	protected EditPart getLastCreatedEditPart() {
		return lastCreatedEditPart;
	}

	protected static Rectangle getBounds(EditPart editPart) {
		IFigure figure = ((GraphicalEditPart) editPart).getFigure();
		Rectangle result = figure.getBounds().getCopy();
		figure.getParent().translateToAbsolute(result);
		return result;
	}

	protected static int getTop(EditPart editPart) {
		return getBounds(editPart).y();
	}

	protected static int getBottom(EditPart editPart) {
		return getBounds(editPart).bottom();
	}

	protected static Point getCenter(EditPart editPart) {
		return getBounds(editPart).getCenter();
	}

	protected static IFigure getResizeHandle(EditPart editPart, int position) {
		((IGraphicalEditPart) editPart).getFigure();
		LayerManager manager = (LayerManager) editPart.getViewer().getEditPartRegistry()
				.get(LayerManager.ID);
		IFigure handleLayer = manager.getLayer(LayerConstants.HANDLE_LAYER);

		Rectangle bounds = getBounds(editPart);
		Point hitTarget;
		switch (position) {
		case PositionConstants.NORTH_WEST:
			hitTarget = bounds.getTopLeft();
			break;
		case PositionConstants.NORTH:
			hitTarget = bounds.getTop();
			break;
		case PositionConstants.NORTH_EAST:
			hitTarget = bounds.getTopRight();
			break;
		case PositionConstants.EAST:
			hitTarget = bounds.getRight();
			break;
		case PositionConstants.SOUTH_EAST:
			hitTarget = bounds.getBottomRight();
			break;
		case PositionConstants.SOUTH:
			hitTarget = bounds.getBottom();
			break;
		case PositionConstants.SOUTH_WEST:
			hitTarget = bounds.getBottomLeft();
			break;
		case PositionConstants.WEST:
			hitTarget = bounds.getLeft();
			break;
		default:
			throw new IllegalArgumentException("position : " + position);
		}

		@SuppressWarnings("unchecked")
		List<? extends IFigure> handles = handleLayer.getChildren();
		Optional<? extends IFigure> result = handles.stream().filter(ResizeHandle.class::isInstance)
				.filter(h -> h.getBounds().contains(hitTarget)).findAny();
		assertThat("Resize handle not found", result.isPresent(), is(true));
		return result.get();
	}

	protected static Point getResizeHandleGrabPoint(EditPart editPart, int position) {
		return getResizeHandle(editPart, position).getBounds().getCenter();
	}

	protected static Point getSource(EditPart connectionEditPart) {
		PointList points = getPoints(connectionEditPart);
		return points.getFirstPoint();
	}

	protected static PointList getPoints(EditPart connectionEditPart) {
		Connection connection = (Connection) ((ConnectionEditPart) connectionEditPart).getFigure();
		PointList result = connection.getPoints().getCopy();
		connection.getParent().translateToAbsolute(result);
		return result;
	}

	protected static int getSourceY(EditPart connectionEditPart) {
		return getSource(connectionEditPart).y();
	}

	protected static Point getTarget(EditPart connectionEditPart) {
		PointList points = getPoints(connectionEditPart);
		return points.getLastPoint();
	}

	protected static int getTargetY(EditPart connectionEditPart) {
		return getTarget(connectionEditPart).y();
	}

	protected EditPart findEditPart(String qualifiedName, boolean isMessage) {
		EObject element = editor.getElement(qualifiedName);
		assumeThat("No such element: " + qualifiedName, element, notNullValue());
		return DiagramEditPartsUtil.getChildByEObject(element, editor.getDiagramEditPart(), isMessage);
	}

}
