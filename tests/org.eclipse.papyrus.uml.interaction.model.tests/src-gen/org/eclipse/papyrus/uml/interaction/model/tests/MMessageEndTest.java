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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Optional;

import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MMessage End</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isSend() <em>Send</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isReceive() <em>Receive</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOtherEnd() <em>Other End</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOwner() <em>Get Owner</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getDiagramView() <em>Get Diagram
 * View</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MMessageEndTest extends MOccurrenceTest {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MMessageEndTest.class);
	}

	/**
	 * Constructs a new MMessage End test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public MMessageEndTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this MMessage End test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected MMessageEnd getFixture() {
		return (MMessageEnd)fixture;
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
		setFixture(interaction.getMessages().get(0).getReceive().get());
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
		assertThat(getFixture().getElement(), is(umlInteraction.getFragment("request-recv")));
	}

	@Override
	public void testFollowing() {
		super.testFollowing();

		assertThat(getFixture().following().get(),
				wraps(umlInteraction.getFragment("ActionExecutionSpecification1")));
	}

	@Override
	public void testGetTop() {
		// 12 {frame} + 30 {title} + 25 {lifeline} + 25 {head} + 25 {anchor}
		assertThat(getFixture().getTop(), isPresent(117));
	}

	@Override
	public void testVerticalDistance__MElement() {
		assertThat(getFixture().verticalDistance(getFixture().getOwner()), isPresent(0));
		assertThat(getFixture().verticalDistance(getFixture().getOtherEnd().get()), isPresent(0));
	}

	@Override
	public void testGetStartedExecution() {
		ExecutionSpecification exec = (ExecutionSpecification)umlInteraction
				.getFragment("ActionExecutionSpecification1");
		assertThat(getFixture().getStartedExecution(), isPresent(wraps(exec)));
	}

	@Override
	public void testGetFinishedExecution() {
		Message reply = umlInteraction.getMessage("reply");
		MMessageEnd replySend = interaction.getMessage(reply).get().getSend().get();
		ExecutionSpecification exec = (ExecutionSpecification)umlInteraction
				.getFragment("ActionExecutionSpecification1");
		assertThat(replySend.getFinishedExecution(), isPresent(wraps(exec)));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isSend() <em>Send</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isSend()
	 * @generated NOT
	 */
	public void testIsSend() {
		assertThat(getFixture().isSend(), is(false));

		Optional<MMessageEnd> other = getFixture().getOtherEnd();
		assumeThat(other, isPresent());
		assertThat(other.get().isSend(), is(true));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isReceive() <em>Receive</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isReceive()
	 * @generated NOT
	 */
	public void testIsReceive() {
		assertThat(getFixture().isReceive(), is(true));

		Optional<MMessageEnd> other = getFixture().getOtherEnd();
		assumeThat(other, isPresent());
		assertThat(other.get().isReceive(), is(false));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOtherEnd() <em>Other
	 * End</em>}' feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOtherEnd()
	 * @generated NOT
	 */
	public void testGetOtherEnd() {
		assertThat(getFixture().getOtherEnd(),
				isPresent(wraps(getFixture().getElement().oppositeEnd().get(0))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOwner() <em>Get Owner</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOwner()
	 * @generated NOT
	 */
	@Override
	public void testGetOwner() {
		super.testGetOwner();
		assertThat(getFixture().getOwner().getElement().getName(), is("request"));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getDiagramView() <em>Get
	 * Diagram View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getDiagramView()
	 * @generated NOT
	 */
	@Override
	public void testGetDiagramView() {
		assertThat(getFixture().getDiagramView(), isPresent(instanceOf(IdentityAnchor.class)));
	}

} // MMessageEndTest
