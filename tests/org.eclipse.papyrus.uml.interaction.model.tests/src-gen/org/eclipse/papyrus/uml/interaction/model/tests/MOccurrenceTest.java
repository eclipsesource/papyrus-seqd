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

import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MOccurrence</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getCovered() <em>Covered</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getStartedExecution() <em>Started
 * Execution</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getFinishedExecution() <em>Finished
 * Execution</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class MOccurrenceTest extends MElementTest {

	/**
	 * Constructs a new MOccurrence test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public MOccurrenceTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this MOccurrence test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected MOccurrence<?> getFixture() {
		return (MOccurrence<?>)fixture;
	}

	@Override
	public void testGetBottom() {
		// These are point objects
		assertThat(getFixture().getBottom(), is(getFixture().getTop()));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getCovered() <em>Covered</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MOccurrence#getCovered()
	 * @generated NOT
	 */
	public void testGetCovered() {
		Lifeline covered = ((InteractionFragment)getFixture().getElement()).getCovereds().get(0);
		assertThat(getFixture().getCovered(), isPresent(wraps(covered)));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getStartedExecution()
	 * <em>Started Execution</em>}' feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MOccurrence#getStartedExecution()
	 * @generated NOT
	 */
	public void testGetStartedExecution() {
		fail("Must be tested by subclasses.");
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getFinishedExecution()
	 * <em>Finished Execution</em>}' feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MOccurrence#getFinishedExecution()
	 * @generated NOT
	 */
	public void testGetFinishedExecution() {
		fail("Must be tested by subclasses.");
	}

} // MOccurrenceTest
