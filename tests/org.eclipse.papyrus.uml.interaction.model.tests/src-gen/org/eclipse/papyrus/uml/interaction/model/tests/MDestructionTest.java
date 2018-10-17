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
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.papyrus.uml.interaction.model.MDestruction;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MMessage;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MDestruction</b></em>'. <!-- end-user-doc
 * -->
 * 
 * @generated
 */
@SuppressWarnings("restriction")
public class MDestructionTest extends MMessageEndTest {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MDestructionTest.class);
	}

	/**
	 * Constructs a new MDestruction test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public MDestructionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this MDestruction test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected MDestruction getFixture() {
		return (MDestruction)fixture;
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
		return "LifelineBodyAnchors";
	}

	@Override
	protected void initializeFixture() {
		List<MMessage> messages = interaction.getMessages();
		setFixture(messages.get(messages.size() - 1).getReceive().get());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	@Override
	public void testGetElement() {
		assertThat(getFixture().getElement(), is(umlInteraction.getFragment("deleted")));
	}

	@Override
	public void testFollowing() {
		// Of course it's the last
		assertThat(getFixture().following(), not(isPresent()));
	}

	@Override
	public void testGetTop() {
		// Note that because this is a message end, we report where the message intersects
		// the lifeline, which is actually the center of the X shape
		assertThat(getFixture().getTop(), isPresent(292));
	}

	@Override
	public void testVerticalDistance__MElement() {
		assertThat(getFixture().verticalDistance(getFixture().getOwner()), isPresent(0));
		assertThat(getFixture().verticalDistance(getFixture().getOtherEnd().get()), isPresent(0));
	}

	@Override
	public void testIsStart() {
		assertThat(getFixture().isStart(), is(false));
	}

	@Override
	public void testGetStartedExecution() {
		assertThat(getFixture().getStartedExecution(), not(isPresent()));
	}

	@Override
	public void testIsFinish() {
		assertThat(getFixture().isFinish(), is(false));
	}

	@Override
	public void testGetFinishedExecution() {
		assertThat(getFixture().getFinishedExecution(), not(isPresent()));
	}

	@Override
	public void testGetExecution() {
		assertThat(getFixture().getExecution(), not(isPresent()));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isSend() <em>Send</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isSend()
	 * @generated NOT
	 */
	@Override
	public void testIsSend() {
		assertThat(getFixture().isSend(), is(false));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isReceive() <em>Receive</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isReceive()
	 * @generated NOT
	 */
	@Override
	public void testIsReceive() {
		assertThat(getFixture().isReceive(), is(true));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOtherEnd() <em>Other
	 * End</em>}' feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOtherEnd()
	 * @generated NOT
	 */
	@Override
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
		assertThat(getFixture().getOwner().getElement().getName(), is("delete"));
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

	@Override
	public void testPrecedes__MElement() {
		for (Iterator<?> iter = ((EObject)getFixture().getInteraction()).eAllContents(); iter.hasNext();) {
			Object next = iter.next();
			if ((next != getFixture()) && (next instanceof MElement<?>)) {
				assertThat("precedes " + next, getFixture().precedes((MElement<?>)next), is(false));
			}
		}
	}

} // MDestructionTest
