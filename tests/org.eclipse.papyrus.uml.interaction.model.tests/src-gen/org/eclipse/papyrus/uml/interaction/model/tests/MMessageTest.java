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

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MMessage</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getSend() <em>Send</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getReceive() <em>Receive</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getSender() <em>Sender</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getReceiver() <em>Receiver</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#isSynchronous() <em>Synchronous</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getOwner() <em>Get Owner</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getDiagramView() <em>Get Diagram
 * View</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getEnd(org.eclipse.uml2.uml.MessageEnd)
 * <em>Get End</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MMessageTest extends MElementTest {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MMessageTest.class);
	}

	/**
	 * Constructs a new MMessage test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MMessageTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this MMessage test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected MMessage getFixture() {
		return (MMessage)fixture;
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
		if (interaction.getMessages().isEmpty()) {
			setFixture(null);
		} else {
			setFixture(interaction.getMessages().get(0));
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
		assertThat(getFixture().getElement(), is(umlInteraction.getMessage("request")));
	}

	@Override
	public void testFollowing() {
		super.testFollowing();

		assertThat(getFixture().following().get(), wraps(umlInteraction.getFragment("request-recv")));
	}

	@Override
	public void testGetTop() {
		// Note that this diagram has no interaction name label!
		// 12 {frame} + 5 {insets} + 25 {lifeline} + 25 {head} + 25 {anchor}
		assertThat(getFixture().getTop(), isPresent(92));
	}

	@Override
	public void testGetBottom() {
		// Note that this diagram has no interaction name label!
		// 12 {frame} + 5 {insets} + 25 {lifeline} + 25 {head} + 25 {anchor}
		assertThat(getFixture().getBottom(), isPresent(92));
	}

	@Override
	public void testVerticalDistance__MElement() {
		assertThat(getFixture().verticalDistance(getFixture().getOwner()), isAbsentInt());
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getSend() <em>Send</em>}' feature
	 * getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getSend()
	 * @generated NOT
	 */
	public void testGetSend() {
		assertThat(getFixture().getSend(),
				isPresent(wraps((MessageEnd)umlInteraction.getFragment("request-send"))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getReceive() <em>Receive</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getReceive()
	 * @generated NOT
	 */
	public void testGetReceive() {
		assertThat(getFixture().getReceive(),
				isPresent(wraps((MessageEnd)umlInteraction.getFragment("request-recv"))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getSender() <em>Sender</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getSender()
	 * @generated NOT
	 */
	public void testGetSender() {
		assertThat(getFixture().getSender(), isPresent(wraps(umlInteraction.getLifeline("LeftLine"))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getReceiver() <em>Receiver</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getReceiver()
	 * @generated NOT
	 */
	public void testGetReceiver() {
		assertThat(getFixture().getReceiver(), isPresent(wraps(umlInteraction.getLifeline("RightLine"))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#isSynchronous()
	 * <em>Synchronous</em>}' feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#isSynchronous()
	 * @generated NOT
	 */
	public void testIsSynchronous() {
		getFixture().getElement().setMessageSort(MessageSort.SYNCH_CALL_LITERAL);
		assertThat("Call not synchronous", getFixture().isSynchronous(), is(true));

		getFixture().getElement().setMessageSort(MessageSort.ASYNCH_SIGNAL_LITERAL);
		assertThat("Signal message not asynchronous", getFixture().isSynchronous(), is(false));

		getFixture().getElement().setMessageSort(MessageSort.REPLY_LITERAL);
		assertThat("Reply message not synchronous", getFixture().isSynchronous(), is(true));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getOwner() <em>Get Owner</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getOwner()
	 * @generated NOT
	 */
	@Override
	public void testGetOwner() {
		super.testGetOwner();
		assertThat(getFixture().getOwner(), is(interaction));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getDiagramView()
	 * @generated NOT
	 */
	@Override
	public void testGetDiagramView() {
		super.testGetDiagramView();
		assertThat(getFixture().getDiagramView().get().getType(), is("Edge_Message"));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getEnd(org.eclipse.uml2.uml.MessageEnd)
	 * <em>Get End</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getEnd(org.eclipse.uml2.uml.MessageEnd)
	 * @generated NOT
	 */
	public void testGetEnd__MessageEnd() {
		MessageEnd send = (MessageEnd)umlInteraction.getFragment("request-send");
		assertThat(getFixture().getEnd(send), isPresent(wraps(send)));

		MessageEnd recv = (MessageEnd)umlInteraction.getFragment("request-recv");
		assertThat(getFixture().getEnd(recv), isPresent(wraps(recv)));
	}

	@Override
	public void testRemove() {
		MMessage requestMessage = getFixture();

		/* assert semantic preconditions */
		assertEquals(2, umlInteraction.getMessages().size());
		assertNotNull(umlInteraction.getMessage("request"));
		assertEquals(8, umlInteraction.getFragments().size());
		assertNotNull(umlInteraction.getFragment("request-send"));
		assertNotNull(umlInteraction.getFragment("request-recv"));
		assertEquals(2, umlInteraction.getLifelines().size());
		assertEquals(2, umlInteraction.getLifelines().get(0).getCoveredBys().size());
		assertEquals(6, umlInteraction.getLifelines().get(1).getCoveredBys().size());

		/* assert diagram preconditions */
		assertEquals(2, sequenceDiagram.getEdges().size());

		/* act */
		Command command = requestMessage.remove();
		assertThat(command, executable());
		execute(command);

		/* assert logical representation */
		assertEquals(0, interaction.getMessages().size());

		/* assert semantic representation */
		assertEquals(0, umlInteraction.getMessages().size());
		assertEquals(0, umlInteraction.getFragments().size());
		assertEquals(2, umlInteraction.getLifelines().size());
		assertEquals(0, umlInteraction.getLifelines().get(0).getCoveredBys().size());
		assertEquals(0, umlInteraction.getLifelines().get(1).getCoveredBys().size());

		/* assert diagram representation */
		assertEquals(0, sequenceDiagram.getEdges().size());
	}

} // MMessageTest
