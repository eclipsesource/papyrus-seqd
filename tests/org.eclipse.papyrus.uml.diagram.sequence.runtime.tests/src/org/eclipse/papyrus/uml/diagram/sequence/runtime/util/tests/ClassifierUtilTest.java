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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.CustomMatchers.isPresent;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.ClassifierUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@code ClassifierUtil} class.
 *
 * @author Christian W. Damus
 */
public class ClassifierUtilTest {

	private Resource resource;
	private Package model;
	private Class a;
	private Class b;
	private Class c;
	private Class d;
	private Class e;
	private Class f;
	private Interface i;

	@Test
	public void subtypeHierarchy() {
		List<Classifier> subclasses = ClassifierUtil.subtypeHierarchy(a, Class.class)
				.collect(Collectors.toList());
		assertThat(subclasses, hasItems(b, c, d, e, f));
		assertThat(subclasses, not(hasItem(i)));
	}

	@Test
	public void subtypeHierarchy_root() {
		Optional<Class> root = ClassifierUtil.subtypeHierarchy(a, Class.class).findFirst();
		assertThat(root, isPresent(a));
	}

	@Test
	public void subtypeHierarchy_cycle() {
		f.createGeneralization(b); // Make a cycle

		List<Classifier> subclasses = ClassifierUtil.subtypeHierarchy(a, Class.class)
				.collect(Collectors.toList());
		assertThat(subclasses, hasItems(b, c, d, e, f));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		resource = new ResourceImpl(URI.createURI("test://ClassifierUtilTest"));
		model = UMLFactory.eINSTANCE.createPackage();
		resource.getContents().add(model);

		a = model.createOwnedClass("A", false);
		b = model.createOwnedClass("B", false);
		c = model.createOwnedClass("C", false);
		d = model.createOwnedClass("D", false);
		e = model.createOwnedClass("E", false);
		f = model.createOwnedClass("F", false);
		i = model.createOwnedInterface("I");

		b.createGeneralization(a);
		c.createGeneralization(a);
		d.createGeneralization(c);
		e.createGeneralization(c);
		f.createGeneralization(c);
		i.createGeneralization(a); // This isn't actually valid
	}

	@After
	public void destroyFixture() {
		resource.unload();
		resource.eAdapters().clear();
	}

}
