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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageKind;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assume;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MLifeline</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getLeft() <em>Left</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getRight() <em>Right</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getOwner() <em>Get Owner</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getDiagramView() <em>Get Diagram
 * View</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#following(org.eclipse.papyrus.uml.interaction.model.MElement)
 * <em>Following</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutionOccurrence(org.eclipse.uml2.uml.ExecutionOccurrenceSpecification)
 * <em>Get Execution Occurrence</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecution(org.eclipse.uml2.uml.ExecutionSpecification)
 * <em>Get Execution</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, int, org.eclipse.uml2.uml.Element)
 * <em>Insert Execution After</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, int, org.eclipse.emf.ecore.EClass)
 * <em>Insert Execution After</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertMessageAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, org.eclipse.papyrus.uml.interaction.model.MLifeline, org.eclipse.uml2.uml.MessageSort, org.eclipse.uml2.uml.NamedElement)
 * <em>Insert Message After</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#elementAt(int) <em>Element At</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#nudgeHorizontally(int) <em>Nudge
 * Horizontally</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MLifelineTest extends MElementTest {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MLifelineTest.class);
	}

	/**
	 * Constructs a new MLifeline test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MLifelineTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this MLifeline test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected MLifeline getFixture() {
		return (MLifeline)fixture;
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
		int which;
		switch (getName()) {
			case "testNudgeHorizontally__int":
				which = 2; // The 'CenterLine' in this diagram
			default:
				which = 1; // The 'RightLine' in most diagrams
				break;
		}

		setFixture(interaction.getLifelines().get(which));
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

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getLeft() <em>Left</em>}' feature
	 * getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getLeft()
	 * @generated NOT
	 */
	public void testGetLeft() {
		assertThat(getFixture().getLeft(), isPresent(173));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getRight() <em>Right</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getRight()
	 * @generated NOT
	 */
	public void testGetRight() {
		assertThat(getFixture().getRight(), isPresent(257)); // 173 {left} + 88 {width}
	}

	@Override
	protected String getInteractionName() {
		switch (getName()) {
			case "testGetExecutionOccurrence__ExecutionOccurrenceSpecification":
				return "ExecutionSpecificationSideAnchors";
			case "testNudgeHorizontally__int":
				return "LifelineHeaderAnchor";
			default:
				return super.getInteractionName();
		}
	}

	@Override
	public void testGetElement() {
		super.testGetElement();
		assertThat(getFixture().getElement(), is(umlInteraction.getLifeline("RightLine")));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getOwner() <em>Get Owner</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getOwner()
	 * @generated NOT
	 */
	@Override
	public void testGetOwner() {
		super.testGetOwner();
		assertThat(getFixture().getOwner(), is(interaction));
	}

	@Override
	public void testFollowing() {
		super.testFollowing();

		assertThat(getFixture().following().get(), wraps(umlInteraction.getFragment("request-recv")));
	}

	@Override
	public void testGetTop() {
		assertThat(getFixture().getTop(), isPresent(25));
	}

	@Override
	public void testGetBottom() {
		// The lifeline header has a specified height
		assertThat(getFixture().getBottom(), isPresent(50));
	}

	@Override
	public void testVerticalDistance__MElement() {
		assertThat(getFixture().verticalDistance(getFixture().getOwner()), isAbsentInt());
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getDiagramView()
	 * @generated NOT
	 */
	@Override
	public void testGetDiagramView() {
		super.testGetDiagramView();
		assertThat(getFixture().getDiagramView().get().getType(), is("Shape_Lifeline_Header"));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#following(org.eclipse.papyrus.uml.interaction.model.MElement)
	 * <em>Following</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#following(org.eclipse.papyrus.uml.interaction.model.MElement)
	 * @generated NOT
	 */
	public void testFollowing__MElement() {
		Optional<MMessageEnd> end = interaction.getElement(umlInteraction.getFragment("request-recv"))
				.map(MMessageEnd.class::cast);
		Assume.assumeThat(end, isPresent());

		Optional<MElement<?>> following = getFixture().following(end.get());
		assertThat(following, isPresent(wraps(umlInteraction.getFragment("ActionExecutionSpecification1"))));
		following = getFixture().following(following.get());
		assertThat(following, isPresent(wraps(umlInteraction.getFragment("reply-send"))));
		following = getFixture().following(following.get());
		assertThat(following, not(isPresent()));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutionOccurrence(org.eclipse.uml2.uml.ExecutionOccurrenceSpecification)
	 * <em>Get Execution Occurrence</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutionOccurrence(org.eclipse.uml2.uml.ExecutionOccurrenceSpecification)
	 * @generated NOT
	 */
	public void testGetExecutionOccurrence__ExecutionOccurrenceSpecification() {
		ExecutionOccurrenceSpecification occurrence = (ExecutionOccurrenceSpecification)umlInteraction
				.getFragment("ActionExecutionSpecification1Start");
		assertThat(getFixture().getExecutionOccurrence(occurrence), isPresent(wraps(occurrence)));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecution(org.eclipse.uml2.uml.ExecutionSpecification)
	 * <em>Get Execution</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecution(org.eclipse.uml2.uml.ExecutionSpecification)
	 * @generated NOT
	 */
	public void testGetExecution__ExecutionSpecification() {
		ExecutionSpecification exec = (ExecutionSpecification)umlInteraction
				.getFragment("ActionExecutionSpecification1");
		assertThat(getFixture().getExecution(exec), isPresent(wraps(exec)));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, int, org.eclipse.uml2.uml.Element)
	 * <em>Insert Execution After</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement,
	 *      int, int, org.eclipse.uml2.uml.Element)
	 * @generated NOT
	 */
	public void testInsertExecutionAfter__MElement_int_int_Element() {
		MMessage reply = interaction.getMessages().get(1);
		Action action = findUMLElement("AnchorsModel::Foo::Lifecycle::doIt", Action.class);
		CreationCommand<ExecutionSpecification> command = getFixture().insertExecutionAfter(reply, 15, 50,
				action);

		assertThat(command, executable());
		ExecutionSpecification execSpec = create(command);
		MExecution exec = getFixture().getExecution(execSpec).get();

		assertThat(exec.getTop(), isPresent(240));
		assertThat(exec.getBottom(), isPresent(290));
		assertThat(exec.getElement(), instanceOf(ActionExecutionSpecification.class));
		assertThat(((ActionExecutionSpecification)exec.getElement()).getAction(), is(action));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, int, org.eclipse.emf.ecore.EClass)
	 * <em>Insert Execution After</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement,
	 *      int, int, org.eclipse.emf.ecore.EClass)
	 * @generated NOT
	 */
	public void testInsertExecutionAfter__MElement_int_int_EClass() {
		MMessage reply = interaction.getMessages().get(1);
		CreationCommand<ExecutionSpecification> command = getFixture().insertExecutionAfter(reply, 15, 50,
				UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION);

		assertThat(command, executable());
		ExecutionSpecification execSpec = create(command);
		MExecution exec = getFixture().getExecution(execSpec).get();

		assertThat(exec.getTop(), isPresent(240));
		assertThat(exec.getBottom(), isPresent(290));
		assertThat(exec.getElement(), instanceOf(ActionExecutionSpecification.class));

		// We didn't actually supply the action, as such
		assertThat(((ActionExecutionSpecification)exec.getElement()).getAction(), nullValue());
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertMessageAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, org.eclipse.papyrus.uml.interaction.model.MLifeline, org.eclipse.uml2.uml.MessageSort, org.eclipse.uml2.uml.NamedElement)
	 * <em>Insert Message After</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#insertMessageAfter(org.eclipse.papyrus.uml.interaction.model.MElement,
	 *      int, org.eclipse.papyrus.uml.interaction.model.MLifeline, org.eclipse.uml2.uml.MessageSort,
	 *      org.eclipse.uml2.uml.NamedElement)
	 * @generated NOT
	 */
	public void testInsertMessageAfter__MElement_int_MLifeline_MessageSort_NamedElement() {
		MLifeline receiver = interaction.getLifelines().get(0);
		MMessage reply = interaction.getMessages().get(1);
		Operation operation = findUMLElement("AnchorsModel::Foo::doIt", Operation.class);
		CreationCommand<Message> command = getFixture().insertMessageAfter(reply, 15, receiver,
				MessageSort.ASYNCH_CALL_LITERAL, operation);

		assertThat(command, executable());
		Message umlMessage = create(command);
		MMessage message = interaction.getMessage(umlMessage).get();

		assertThat(message.getTop(), isPresent(240));
		assertThat(message.getBottom(), isPresent(240));
		assertThat(umlMessage.getSignature(), is(operation));
		assertThat(umlMessage.getMessageSort(), is(MessageSort.ASYNCH_CALL_LITERAL));
		assertThat(umlMessage.getMessageKind(), is(MessageKind.COMPLETE_LITERAL));

		MessageEnd send = umlMessage.getSendEvent();
		assertThat(send, notNullValue());
		assertThat(send.getMessage(), is(umlMessage));
		assertThat(getFixture().getElement().getCoveredBys(), hasItem((InteractionFragment)send));

		MessageEnd recv = umlMessage.getReceiveEvent();
		assertThat(recv, notNullValue());
		assertThat(recv.getMessage(), is(umlMessage));
		assertThat(receiver.getElement().getCoveredBys(), hasItem((InteractionFragment)recv));

		// The edge connects the lifeline bodies (not the heads)
		Edge edge = message.getDiagramView().get();
		assertThat(edge.getSource(), notNullValue());
		assertThat(edge.getSource().getType(), containsString("Body"));
		assertThat(edge.getSource().getElement(), is(getFixture().getElement()));
		assertThat(edge.getTarget(), notNullValue());
		assertThat(edge.getTarget().getType(), containsString("Body"));
		assertThat(edge.getTarget().getElement(), is(receiver.getElement()));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#elementAt(int) <em>Element
	 * At</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#elementAt(int)
	 * @generated NOT
	 */
	public void testElementAt__int() {
		Optional<MElement<?>> element = getFixture().elementAt(60);
		element = getFixture().elementAt(100);
		assertThat(element, isPresent(instanceOf(MExecution.class)));

		element = getFixture().elementAt(450);
		assertThat(element, isPresent(instanceOf(MMessageEnd.class)));
		assertThat(((MessageEnd)element.get().getElement()).getName(), is("reply-send"));

		element = getFixture().elementAt(5);
		assertThat(element, not(isPresent()));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#nudgeHorizontally(int) <em>Nudge
	 * Horizontally</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#nudgeHorizontally(int)
	 * @generated NOT
	 */
	@SuppressWarnings("boxing")
	public void testNudgeHorizontally__int() {
		final Lifeline left = interaction.getElement().getLifeline("LeftLine");
		final Lifeline right = interaction.getElement().getLifeline("RightLine");
		final Lifeline centre = getFixture().getElement();
		final int leftX = interaction.getLifeline(left).get().getLeft().getAsInt();
		final int rightX = interaction.getLifeline(right).get().getLeft().getAsInt();
		final int centreX = interaction.getLifeline(centre).get().getLeft().getAsInt();

		Command nudge = getFixture().nudgeHorizontally(90);
		assertThat(nudge, executable());
		execute(nudge);

		// FIXME: API in the model for this
		assertThat("Centre line not moved", interaction.getLifeline(centre).get().getLeft().getAsInt(),
				is(centreX + 90));
		assertThat("Right line not moved", interaction.getLifeline(right).get().getLeft().getAsInt(),
				is(rightX + 90));
		// but this one doesn't move. It's dependet on the centre line by creation message, but it's
		// visually to the left, so isn't affected by the horizontal nudge (which has no semantics)
		assertThat("Left line moved", interaction.getLifeline(left).get().getLeft().getAsInt(), is(leftX));
	}

} // MLifelineTest
