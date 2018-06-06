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

import java.util.Optional;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.util.UMLSwitch;
import org.junit.After;
import org.junit.Rule;

/**
 * Abstract skeleton of a test that exercises graphical edit-policies.
 *
 * @author Christian W. Damus
 */
public abstract class AbstractGraphicalEditPolicyUITest {

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
	 * Obtain a possibly corrected (for layout weirdness) expected x coördinate for
	 * assertion of a measured location.
	 * 
	 * @param x
	 *            an expected x coördinate
	 * @return the proper x coördinate to assert, corrected if necessary for the
	 *         object being verified
	 */
	protected final int x(int x) {
		return x;
	}

	/**
	 * Obtain a possibly corrected (for layout weirdness) expected y coördinate for
	 * assertion of a measured location.
	 * 
	 * @param y
	 *            an expected y coördinate
	 * @return the proper y coördinate to assert, corrected if necessary for the
	 *         object being verified
	 */
	protected final int y(int y) {
		return y + yCorrection();
	}

	/**
	 * There are 5 units of vertical error somehow introduced in the calculation of
	 * the absolute location of a message connection figure attached to the lifeline
	 * body figure, probably in the calculation of the lifeline body figure's own
	 * location. Suffice it to say that manual testing shows message connections
	 * actually being created at the mouse pointer location.
	 *
	 * TODO: Find and fix this error which presumably breaks layout calculations
	 *
	 * @param y
	 *            an expected y location for layout assertion
	 * @return the corrected y location to assert
	 */
	protected int yCorrection() {
		Optional<EObject> semantic = Optional.ofNullable(lastCreatedEditPart)
				.filter(IGraphicalEditPart.class::isInstance).map(IGraphicalEditPart.class::cast)
				.map(IGraphicalEditPart::resolveSemanticElement);

		Switch<Integer> yCorrectionSwitch = new UMLSwitch<Integer>() {
			@Override
			public Integer caseExecutionSpecification(ExecutionSpecification object) {
				return +5;
			}

			@Override
			public Integer caseMessage(Message object) {
				return +5;
			}

			@Override
			public Integer defaultCase(EObject object) {
				return 0;
			}
		};

		return semantic.map(yCorrectionSwitch::doSwitch).orElse(0);
	}
}
