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

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test suite for the '<em><b>interaction</b></em>' package. <!-- end-user-doc -->
 * 
 * @generated
 */
public class SequenceDiagramTests extends TestSuite {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new SequenceDiagramTests("interaction Tests"); //$NON-NLS-1$
		suite.addTestSuite(MInteractionTest.class);
		suite.addTestSuite(MLifelineTest.class);
		suite.addTestSuite(MExecutionTest.class);
		suite.addTestSuite(MExecutionOccurrenceTest.class);
		suite.addTestSuite(MMessageEndTest.class);
		suite.addTestSuite(MMessageTest.class);
		suite.addTestSuite(MDestructionTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SequenceDiagramTests(String name) {
		super(name);
	}

} // SequenceDiagramTests
