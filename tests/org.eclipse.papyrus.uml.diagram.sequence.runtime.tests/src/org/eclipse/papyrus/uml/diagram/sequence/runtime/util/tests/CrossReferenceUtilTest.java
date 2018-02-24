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

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.CustomMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.CrossReferenceUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@code CrossReferenceUtil} class.
 *
 * @author Christian W. Damus
 */
public class CrossReferenceUtilTest {

	private Resource resource;
	private Package model;
	private Class a;
	private Class b;
	private Class c;
	private Property p;

	@Test
	public void invert() {
		Set<Class> redefinitions = CrossReferenceUtil
				.invert(a, UMLPackage.Literals.CLASSIFIER__REDEFINED_CLASSIFIER, Class.class)
				.collect(Collectors.toSet());
		assertThat(redefinitions, hasItems(b, c));
	}

	@Test
	public void invertSingle() {
		Optional<Property> typedBy = CrossReferenceUtil.invertSingle(a,
				UMLPackage.Literals.TYPED_ELEMENT__TYPE, Property.class);
		assertThat(typedBy, CustomMatchers.isPresent(p));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		resource = new ResourceImpl(URI.createURI("test://CrossReferenceUtilTest"));
		model = UMLFactory.eINSTANCE.createPackage();
		resource.getContents().add(model);

		a = model.createOwnedClass("A", false);
		b = model.createOwnedClass("B", false);
		c = model.createOwnedClass("C", false);

		b.getRedefinedClassifiers().add(a);
		c.getRedefinedClassifiers().add(a);
		p = c.createOwnedAttribute("p", a);
	}

	@After
	public void destroyFixture() {
		resource.unload();
		resource.eAdapters().clear();
	}
}
