/**
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrus.uml.interaction.model.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.papyrus.uml.interaction.model.MExecution;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MExecution</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getStart() <em>Start</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getFinish() <em>Finish</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getOwner() <em>Get Owner</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getDiagramView() <em>Get Diagram
 * View</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MExecutionTest extends MElementTest {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MExecutionTest.class);
	}

	/**
	 * Constructs a new MExecution test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public MExecutionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this MExecution test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected MExecution getFixture() {
		return (MExecution)fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void initializeFixture() {
		setFixture(interaction.getLifelines().get(1).getExecutions().get(0));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * @generated NOT
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	public void testGetElement() {
		super.testGetElement();
		assertThat(getFixture().getElement(),
				is(umlInteraction.getFragment("ActionExecutionSpecification1")));
	}

	@Override
	public void testFollowing() {
		super.testFollowing();

		assertThat(getFixture().following().get(), wraps(umlInteraction.getFragment("reply-send")));
	}

	@Override
	public void testGetTop() {
		assertThat(getFixture().getTop(), isPresent(75));
	}

	@Override
	public void testGetBottom() {
		assertThat(getFixture().getBottom(), isPresent(225));
	}

	@Override
	public void testVerticalDistance__MElement() {
		// The lifeline header has a specified height
		assertThat(getFixture().verticalDistance(getFixture().getOwner()), isPresent(25));
		assertThat(getFixture().verticalDistance(getFixture().getStart().get()), isPresent(0));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getStart() <em>Start</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getStart()
	 * @generated NOT
	 */
	public void testGetStart() {
		assertThat(getFixture().getStart(), isPresent(wraps(umlInteraction.getFragment("request-recv"))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getFinish() <em>Finish</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getFinish()
	 * @generated NOT
	 */
	public void testGetFinish() {
		assertThat(getFixture().getFinish(), isPresent(wraps(umlInteraction.getFragment("reply-send"))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getOwner() <em>Get Owner</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getOwner()
	 * @generated NOT
	 */
	@Override
	public void testGetOwner() {
		super.testGetOwner();
		assertThat(getFixture().getOwner().getElement().getName(), is("RightLine"));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getDiagramView()
	 * @generated NOT
	 */
	@Override
	public void testGetDiagramView() {
		super.testGetDiagramView();
		assertThat(getFixture().getDiagramView().get().getType(), is("Shape_Execution_Specification"));
	}

} // MExecutionTest
