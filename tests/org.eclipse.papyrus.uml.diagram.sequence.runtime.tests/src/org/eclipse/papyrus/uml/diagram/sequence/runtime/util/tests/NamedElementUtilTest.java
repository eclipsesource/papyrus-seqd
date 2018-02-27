/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelResource;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.NamedElementUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link NamedElementUtil} Class.
 *
 * @author Christian W. Damus
 */
@ModelResource("messages.uml")
public class NamedElementUtilTest {

	@Rule
	public ModelFixture model = new ModelFixture();

	/**
	 * Initializes me.
	 */
	public NamedElementUtilTest() {
		super();
	}

	@Test
	public void getQualifiedName_nonAncestor() {
		Operation operation = model.getElement("foo::Thing::doIt", Operation.class);
		Class foo = model.getElement("foo::Foo", Class.class);

		assertThat(NamedElementUtil.getQualifiedName(foo, operation), is("Foo"));
		assertThat(NamedElementUtil.getQualifiedName(operation, foo), is("Thing::doIt"));
	}

	@Test
	public void getQualifiedName_ancestor() {
		Parameter parameter = model.getElement("foo::Thing::doIt::text", Parameter.class);
		Class thing = model.getElement("foo::Thing", Class.class);

		assertThat(NamedElementUtil.getQualifiedName(thing, parameter), is("Thing"));
		assertThat(NamedElementUtil.getQualifiedName(parameter, thing), is("doIt::text"));
	}

	@Test
	public void getQualifiedName_lifelinePropertyFromMessage() {
		Property property = model.getElement("foo::Thing::ok", Property.class);
		Message reply = model.getMessage("whatsIt", "thing");

		assertThat(NamedElementUtil.getQualifiedName(property, reply), is("thing::ok"));
	}

	@Test
	public void getCommonNamespace_nonAncestor() {
		Operation operation = model.getElement("foo::Thing::doIt", Operation.class);
		Property property = model.getElement("foo::Thing::ok", Property.class);
		Class foo = model.getElement("foo::Foo", Class.class);

		assertCommonAncestor(operation, foo, model.getModel());
		assertCommonAncestor(operation, property, operation.getClass_());
	}

	@Test
	public void getCommonNamespace_ancestor() {
		Parameter parameter = model.getElement("foo::Thing::doIt::text", Parameter.class);
		Class thing = model.getElement("foo::Thing", Class.class);

		assertCommonAncestor(parameter, thing, thing);
	}

	@Test
	public void getCommonNamespace_none() {
		EcoreUtil.resolveAll(model.getModel());

		Class thing = model.getElement("foo::Thing", Class.class);
		PrimitiveType boolType = model.getElement("PrimitiveTypes::Boolean", PrimitiveType.class);

		assertThat(NamedElementUtil.getCommonNamespace(thing, boolType), nullValue());
	}

	//
	// Test framework
	//

	void assertCommonAncestor(NamedElement element1, NamedElement element2, NamedElement lca) {
		assertCommonAncestor(element1, element2, is(lca));
	}

	void assertCommonAncestor(NamedElement element1, NamedElement element2,
			Matcher<? super NamedElement> assertion) {
		assertThat(NamedElementUtil.getCommonNamespace(element1, element2), assertion);

		// And the reverse, that order doesn't affect the outcome
		assertThat(NamedElementUtil.getCommonNamespace(element2, element1), assertion);
	}
}
