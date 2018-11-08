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
 */
package org.eclipse.papyrus.infra.gmfdiag.filters.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersFactory;
import org.eclipse.papyrus.infra.gmfdiag.filters.ViewType;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>View Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ViewTypeTest extends TestCase {

	/**
	 * The fixture for this View Type test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ViewType fixture = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ViewTypeTest.class);
	}

	/**
	 * Constructs a new View Type test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ViewTypeTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this View Type test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(ViewType fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this View Type test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ViewType getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DiagramFiltersFactory.eINSTANCE.createViewType());

		getFixture().setType("foo");
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

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object)
	 * @generated NOT
	 */
	public void testMatches__Object() {
		View view = NotationFactory.eINSTANCE.createNode();
		view.setType("foo");
		assertThat(getFixture().matches(view), is(true));
		view.setType("bar");
		assertThat(getFixture().matches(view), is(false));
		view.setType(null);
		assertThat(getFixture().matches(view), is(false));
	}

	public void testMatches__Object_adaptable() {
		View view = NotationFactory.eINSTANCE.createNode();
		view.setType("foo");

		IAdaptable adaptable = new IAdaptable() {

			@Override
			public <T> T getAdapter(Class<T> adapter) {
				return adapter.isInstance(view) ? adapter.cast(view) : null;
			}
		};

		assertThat(getFixture().matches(adaptable), is(true));
		view.setType("bar");
		assertThat(getFixture().matches(adaptable), is(false));
		view.setType(null);
		assertThat(getFixture().matches(adaptable), is(false));
	}

} // ViewTypeTest
