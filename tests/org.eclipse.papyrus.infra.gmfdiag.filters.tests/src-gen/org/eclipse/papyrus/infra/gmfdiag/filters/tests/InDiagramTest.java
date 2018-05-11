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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.gmfdiag.filters.DiagramFiltersFactory;
import org.eclipse.papyrus.infra.gmfdiag.filters.InDiagram;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>In
 * Diagram</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class InDiagramTest extends TestCase {

	private Filter mockFilter;

	/**
	 * The fixture for this In Diagram test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	protected InDiagram fixture = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InDiagramTest.class);
	}

	/**
	 * Constructs a new In Diagram test case with the given name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InDiagramTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this In Diagram test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	protected void setFixture(InDiagram fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this In Diagram test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InDiagram getFixture() {
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
		setFixture(DiagramFiltersFactory.eINSTANCE.createInDiagram());

		mockFilter = mock(Filter.class, withSettings().extraInterfaces(InternalEObject.class));
		getFixture().setFilter(mockFilter);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object)
	 * <em>Matches</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object)
	 * @generated NOT
	 */
	public void testMatches__Object() {
		Diagram diagram = NotationFactory.eINSTANCE.createDiagram();
		Node node = diagram.createChild(NotationPackage.Literals.NODE).createChild(NotationPackage.Literals.NODE);
		getFixture().matches(node);

		verify(mockFilter).matches(diagram);
	}

	public void testMatches__Object_adaptable() {
		Diagram diagram = NotationFactory.eINSTANCE.createDiagram();
		Node node = diagram.createChild(NotationPackage.Literals.NODE).createChild(NotationPackage.Literals.NODE);

		IAdaptable adaptable = new IAdaptable() {

			@Override
			public <T> T getAdapter(Class<T> adapter) {
				return adapter.isInstance(node) ? adapter.cast(node) : null;
			}
		};

		getFixture().matches(adaptable);

		verify(mockFilter).matches(diagram);
	}

} // InDiagramTest
