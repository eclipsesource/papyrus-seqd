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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MInteraction</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getDiagramView() <em>Get Diagram
 * View</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getElement(org.eclipse.uml2.uml.Element)
 * <em>Get Element</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifeline(org.eclipse.uml2.uml.Lifeline)
 * <em>Get Lifeline</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getMessage(org.eclipse.uml2.uml.Message)
 * <em>Get Message</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#addLifeline(int, int) <em>Add
 * Lifeline</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifelineAt(int) <em>Get Lifeline
 * At</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MInteractionTest extends MElementTest {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MInteractionTest.class);
	}

	/**
	 * Constructs a new MInteraction test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public MInteractionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this MInteraction test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected MInteraction getFixture() {
		return (MInteraction)fixture;
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
		setFixture(interaction);
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
	public void testGetOwner() {
		// Not super because it is expected to be null
		assertThat(getFixture().getOwner(), nullValue());
	}

	@Override
	public void testGetElement() {
		super.testGetElement();
		assertThat(getFixture().getElement(), is(umlInteraction));
	}

	@Override
	public void testFollowing() {
		super.testFollowing();

		assertThat(getFixture().following().get(), wraps(umlInteraction.getLifeline("LeftLine")));
	}

	@Override
	public void testGetTop() {
		assertThat(getFixture().getTop(), isAbsentInt());
	}

	@Override
	public void testGetBottom() {
		assertThat(getFixture().getBottom(), isAbsentInt());
	}

	@Override
	public void testVerticalDistance__MElement() {
		// Doesn't make sense for the overall interaction anyways
		assertThat(getFixture().verticalDistance(getFixture()), isAbsentInt());
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getDiagramView() <em>Get
	 * Diagram View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getDiagramView()
	 * @generated NOT
	 */
	@Override
	public void testGetDiagramView() {
		super.testGetDiagramView();
		assertThat(getFixture().getDiagramView().get().getType(), is("LightweightSequenceDiagram"));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getElement(org.eclipse.uml2.uml.Element)
	 * <em>Get Element</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getElement(org.eclipse.uml2.uml.Element)
	 * @generated NOT
	 */
	public void testGetElement__Element() {
		Optional<? extends MElement<?>> element = getFixture()
				.getElement(umlInteraction.getFragment("request-recv"));
		assertThat(element, isPresent(instanceOf(MMessageEnd.class)));

		element = getFixture().getElement(umlInteraction.getFragment("ActionExecutionSpecification1"));
		assertThat(element, isPresent(instanceOf(MExecution.class)));

		element = getFixture().getElement(umlInteraction.getLifeline("RightLine"));
		assertThat(element, isPresent(instanceOf(MLifeline.class)));

		element = getFixture().getElement(umlInteraction.getMessage("request"));
		assertThat(element, isPresent(instanceOf(MMessage.class)));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifeline(org.eclipse.uml2.uml.Lifeline)
	 * <em>Get Lifeline</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifeline(org.eclipse.uml2.uml.Lifeline)
	 * @generated NOT
	 */
	public void testGetLifeline__Lifeline() {
		Lifeline lifeline = umlInteraction.getLifeline("LeftLine");
		assertThat(getFixture().getLifeline(lifeline), isPresent(wraps(lifeline)));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getMessage(org.eclipse.uml2.uml.Message)
	 * <em>Get Message</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getMessage(org.eclipse.uml2.uml.Message)
	 * @generated NOT
	 */
	public void testGetMessage__Message() {
		Message message = umlInteraction.getMessage("reply");
		assertThat(getFixture().getMessage(message), isPresent(wraps(message)));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#addLifeline(int, int) <em>Add
	 * Lifeline</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#addLifeline(int, int)
	 * @generated NOT
	 */
	public void testAddLifeline__int_int() {
		MLifeline existing = getFixture().getLifelines().get(1);
		Bounds reference = getBounds(existing);

		CreationCommand<Lifeline> command = getFixture().addLifeline(450, 250);

		assertThat(command, executable());
		Lifeline uml = create(command);
		MLifeline lifeline = getFixture().getLifeline(uml).get();

		assertThat(uml.getName(), is("Lifeline1"));

		Bounds bounds = getBounds(lifeline);
		assertThat(bounds.getX(), is(450));
		assertThat(bounds.getY(), is(reference.getY()));
		assertThat(bounds.getWidth(), is(100));
		assertThat(bounds.getHeight(), is(28));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifelineAt(int) <em>Get
	 * Lifeline At</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifelineAt(int)
	 * @generated NOT
	 */
	public void testGetLifelineAt__int() {
		MLifeline left = getFixture().getLifeline(umlInteraction.getLifeline("LeftLine")).get();
		MLifeline right = getFixture().getLifeline(umlInteraction.getLifeline("RightLine")).get();

		assertThat("Direct hit on right not found", getFixture().getLifelineAt(237), isPresent(is(right)));
		assertThat("Between the lifelines not found", getFixture().getLifelineAt(197), isPresent(is(right)));
		assertThat("Direct hit on left not found	", getFixture().getLifelineAt(137), isPresent(is(left)));
		assertThat("Left of the leftmost not found", getFixture().getLifelineAt(52), isPresent(is(left)));
		assertThat("Right of the righmost was found", getFixture().getLifelineAt(437), not(isPresent()));
	}

} // MInteractionTest
