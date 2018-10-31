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

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.ExecutionSpecification;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MExecution Occurrence</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getOwner() <em>Get
 * Owner</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getDiagramView() <em>Get Diagram
 * View</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#replaceBy(org.eclipse.papyrus.uml.interaction.model.MMessageEnd)
 * <em>Replace By</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MExecutionOccurrenceTest extends MOccurrenceTest {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MExecutionOccurrenceTest.class);
	}

	/**
	 * Constructs a new MExecution Occurrence test case with the given name. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public MExecutionOccurrenceTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this MExecution Occurrence test case. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	@Override
	protected MExecutionOccurrence getFixture() {
		return (MExecutionOccurrence)fixture;
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
		setFixture(interaction.getLifelines().get(1).getExecutions().get(0).getStart().get());
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
	protected String getInteractionName() {
		return "ExecutionSpecificationSideAnchors";
	}

	@Override
	public void testGetElement() {
		super.testGetElement();
		assertThat(getFixture().getElement(),
				is(umlInteraction.getFragment("ActionExecutionSpecification1Start")));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getOwner() <em>Get
	 * Owner</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getOwner()
	 * @generated NOT
	 */
	@Override
	public void testGetOwner() {
		super.testGetOwner();
		assertThat(getFixture().getOwner().getElement().getName(), is("RightLine"));
	}

	@Override
	public void testFollowing() {
		super.testFollowing();

		assertThat(getFixture().following().get(),
				wraps(umlInteraction.getFragment("ActionExecutionSpecification1")));
	}

	@Override
	public void testGetTop() {
		// Note that this diagram has no interaction name label!
		// 12 {frame} + 5 {insets} + 25 {lifeline} + 25 {head} + 25 {execution y}
		assertThat(getFixture().getTop(), isPresent(92));
	}

	@Override
	public void testVerticalDistance__MElement() {
		MOccurrence<?> finish = getFixture().getStartedExecution().flatMap(MExecution::getFinish).get();
		assertThat(finish.verticalDistance(getFixture()), isPresent(80));
	}

	@Override
	public void testIsStart() {
		assertThat(getFixture().isStart(), is(true));

		Optional<MExecutionOccurrence> finish = interaction
				.getElement(umlInteraction.getFragment("ActionExecutionSpecification1Finish"))
				.map(MExecutionOccurrence.class::cast);
		assumeThat(finish, isPresent());

		assertThat(finish.get().isStart(), is(false));
	}

	@Override
	public void testGetStartedExecution() {
		ExecutionSpecification exec = (ExecutionSpecification)umlInteraction
				.getFragment("ActionExecutionSpecification1");
		assertThat(getFixture().getStartedExecution(), isPresent(wraps(exec)));
	}

	@Override
	public void testIsFinish() {

		Optional<MExecutionOccurrence> finish = interaction
				.getElement(umlInteraction.getFragment("ActionExecutionSpecification1Finish"))
				.map(MExecutionOccurrence.class::cast);
		assumeThat(finish, isPresent());

		assertThat(finish.get().isFinish(), is(true));
	}

	@Override
	public void testGetFinishedExecution() {
		Optional<MExecutionOccurrence> finish = interaction
				.getElement(umlInteraction.getFragment("ActionExecutionSpecification1Finish"))
				.map(MExecutionOccurrence.class::cast);
		assumeThat(finish, isPresent());

		ExecutionSpecification exec = (ExecutionSpecification)umlInteraction
				.getFragment("ActionExecutionSpecification1");
		assertThat(finish.get().getFinishedExecution(), isPresent(wraps(exec)));
	}

	@Override
	public void testGetExecution() {
		Optional<MExecution> started = getFixture().getStartedExecution();
		Optional<MExecution> finished = getFixture().getFinishedExecution();

		assertThat(getFixture().getExecution(),
				both(isPresent(MExecution.class)).and(either(is(started)).or(is(finished))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getDiagramView()
	 * <em>Get Diagram View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getDiagramView()
	 * @generated NOT
	 */
	@Override
	public void testGetDiagramView() {
		assertThat(getFixture().getDiagramView(), not(isPresent()));
		// TODO: Case of GeneralOrdering edge anchored on an execution occurrence
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#replaceBy(org.eclipse.papyrus.uml.interaction.model.MMessageEnd)
	 * <em>Replace By</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#replaceBy(org.eclipse.papyrus.uml.interaction.model.MMessageEnd)
	 * @generated NOT
	 */
	public void testReplaceBy__MMessageEnd() {
		MMessage message = getFixture().getInteraction().getMessages().get(0);
		MMessageEnd end = message.getReceive().get();
		ExecutionSpecification exec = getFixture().getElement().getExecution();

		Command replace = getFixture().replaceBy(end);
		assertThat("Replace command not executable", replace, executable());

		// Don't attempt to re-initialize the fixture
		execute(replace, false);

		assertThat("Execution occurrence not replaced", exec.getStart(), is(end.getElement()));
		assertThat("Occurrence not removed", getFixture().getElement().eContainer(), nullValue());
	}

	@Override
	public void testRemove() {
		Command remove = getFixture().remove();
		assertThat("Remove command not executable", remove, executable());

		ExecutionSpecification exec = getFixture().getElement().getExecution();

		// Don't attempt to re-initialize the fixture
		execute(remove, false);

		assertThat("Execution still has a start occurrence", exec.getStart(), nullValue());
		assertThat("Occurrence not removed", getFixture().getElement().eContainer(), nullValue());
	}

} // MExecutionOccurrenceTest
