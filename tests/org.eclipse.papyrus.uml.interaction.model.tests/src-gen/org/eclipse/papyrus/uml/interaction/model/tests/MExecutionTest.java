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

import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gt;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.lt;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;

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
	protected String getInteractionName() {
		switch (getName()) {
			case "testGetOccurrences":
				return "ExecutionSpecificationSideAnchors";
			default:
				return super.getInteractionName();
		}
	}

	@Override
	protected void initializeFixture() {
		/* remove test may remove execution -> avoid NPE */
		List<MExecution> executions = interaction.getLifelines().get(1).getExecutions();

		/* One of our tests moves it to the other lifeline */
		if (executions.isEmpty()) {
			executions = interaction.getLifelines().get(0).getExecutions();
		}

		if (executions.isEmpty()) {
			setFixture(null);
		} else {
			setFixture(executions.get(0));
		}
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
		// Note that this diagram has no interaction name label!
		// 12 {frame} + 5 {insets} + 25 {lifeline} + 25 {head} + 25 {y}
		assertThat(getFixture().getTop(), isPresent(92));
	}

	@Override
	public void testGetBottom() {
		assertThat(getFixture().getBottom(), isPresent(242)); // 92 {top} + 150 {height}
	}

	@Override
	public void testVerticalDistance__MElement() {
		// Distance from the bottom of the lifeline header is just the y position in the notation
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
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getOccurrences()
	 * <em>Occurrences</em>}' feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getOccurrences()
	 * @generated NOT
	 */
	public void testGetOccurrences() {
		List<MOccurrence<? extends Element>> occurrences = getFixture().getOccurrences();
		assertThat("Wrong number of occurrences", occurrences.size(), is(4));

		// The collection is unordered, except we do always put the start occurrence first
		// and the finish occurrence last
		assumeThat(getFixture().getStart(), isPresent());
		assertThat(occurrences.get(0), is(getFixture().getStart().get()));
		assumeThat(getFixture().getFinish(), isPresent());
		assertThat(occurrences.get(3), is(getFixture().getFinish().get()));

		assertThat(occurrences, hasItem(named("request-recv")));
		assertThat(occurrences, hasItem(named("reply-send")));
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

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#setOwner(org.eclipse.papyrus.uml.interaction.model.MLifeline, java.util.OptionalInt, java.util.OptionalInt)
	 * <em>Set Owner</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#setOwner(org.eclipse.papyrus.uml.interaction.model.MLifeline,
	 *      java.util.OptionalInt, java.util.OptionalInt)
	 * @generated NOT
	 */
	public void testSetOwner__MLifeline_OptionalInt_OptionalInt() {
		// Get the other lifeline
		MLifeline other = getFixture().getInteraction().getLifelines().stream()
				.filter(((Predicate<MLifeline>)getFixture().getOwner()::equals).negate()).findFirst()
				.orElseThrow(() -> new AssertionError("Only one lifeline"));
		String name = other.getName();

		Command setOwner = getFixture().setOwner(other, OptionalInt.empty(), OptionalInt.empty());
		assertThat(setOwner, executable());
		execute(setOwner);

		// The automatic reinitialization doesn't work because it's based on

		assertThat("Execution not moved", getFixture().getOwner(), named(name));
		assertThat("Start not moved", getFixture().getStart().flatMap(MOccurrence::getCovered),
				isPresent(named(name)));
		assertThat("Finish not moved", getFixture().getFinish().flatMap(MOccurrence::getCovered),
				isPresent(named(name)));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#createStart() <em>Create
	 * Start</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#createStart()
	 * @generated NOT
	 */
	public void testCreateStart() {
		MMessageEnd mEnd = getFixture().getStart().map(MMessageEnd.class::cast).get();
		Connector messageConnector = mEnd.getOwner().getDiagramView().get();
		MessageOccurrenceSpecification end = (MessageOccurrenceSpecification)mEnd.getElement();
		Lifeline covered = end.getCovered();

		CreationCommand<ExecutionOccurrenceSpecification> cmd = getFixture().createStart();
		assertThat(cmd, executable());
		execute(cmd);

		ExecutionOccurrenceSpecification start = cmd.getNewObject();
		assertThat("No start occurrence created", start, notNullValue());

		List<InteractionFragment> fragments = covered.getInteraction().getFragments();
		assertThat(fragments, hasItems(start, end));
		assertThat(covered.getCoveredBys(), hasItems(start, end));
		assertThat(getFixture().getElement().getStart(), is(start));
		assertThat(start.getExecution(), is(getFixture().getElement()));

		assertThat(fragments.indexOf(end), lt(fragments.indexOf(start)));
		assertThat(fragments.indexOf(start), lt(fragments.indexOf(fixture.getElement())));

		// The message end is still incoming at the beginning of the execution, so it
		// should be visually attached to it
		assertThat("Message not attached to execution", messageConnector.getTarget().getElement(),
				is(getFixture().getElement()));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#createFinish() <em>Create
	 * Finish</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#createFinish()
	 * @generated NOT
	 */
	public void testCreateFinish() {
		MMessageEnd mEnd = getFixture().getFinish().map(MMessageEnd.class::cast).get();
		Connector messageConnector = mEnd.getOwner().getDiagramView().get();
		MessageOccurrenceSpecification end = (MessageOccurrenceSpecification)mEnd.getElement();
		Lifeline covered = end.getCovered();

		CreationCommand<ExecutionOccurrenceSpecification> cmd = getFixture().createFinish();
		assertThat(cmd, executable());
		execute(cmd);

		ExecutionOccurrenceSpecification finish = cmd.getNewObject();
		assertThat("No finish occurrence created", finish, notNullValue());

		List<InteractionFragment> fragments = covered.getInteraction().getFragments();
		assertThat(fragments, hasItems(finish, end));
		assertThat(covered.getCoveredBys(), hasItems(finish, end));
		assertThat(getFixture().getElement().getFinish(), is(finish));
		assertThat(finish.getExecution(), is(getFixture().getElement()));

		assertThat(fragments.indexOf(end), gt(fragments.indexOf(finish)));
		assertThat(fragments.indexOf(finish), gt(fragments.indexOf(fixture.getElement())));

		// The message end is still incoming at the beginning of the execution, so it
		// should be visually attached to it
		assertThat("Message not attached to execution", messageConnector.getSource().getElement(),
				is(getFixture().getElement()));
	}

	@Override
	public void testRemove() {
		MExecution execution = getFixture();

		/* act */
		Command command = execution.remove();
		assertThat(command, executable());
		execute(command);

		/* assert execution with messages removed */
		/* assert logical representation */
		assertEquals(2, interaction.getLifelines().size());
		assertTrue(interaction.getLifelines().get(1).getExecutions().isEmpty());
		assertTrue(interaction.getMessages().isEmpty());

		/* assert semantics */
		assertEquals(2, umlInteraction.getLifelines().size());
		assertTrue(umlInteraction.getLifelines().get(1).getCoveredBys().isEmpty());
		assertTrue(umlInteraction.getFragments().isEmpty());
		assertTrue(umlInteraction.getMessages().isEmpty());

		/* assert diagram */
		assertTrue(sequenceDiagram.getEdges().isEmpty());
		Optional<Shape> lifeLineView = interaction.getLifelines().get(1).getDiagramView();
		assertTrue(lifeLineView.isPresent());
		assertFalse(findTypeInChildren(lifeLineView.get(), "Shape_Execution_Specification").isPresent());
	}

} // MExecutionTest
