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

package org.eclipse.papyrus.uml.interaction.model.util.tests;

import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Rule;
import org.junit.Test;

/**
 * Unit tests for the {@link LogicalModelPredicates} class.
 */
@SuppressWarnings({"boxing", "nls" })
@ModelResource({"AnchorsModel.di", "AnchorsModel.uml", "AnchorsModel.notation" })
public class LogicalModelPredicatesTest {

	private static final String QN = "AnchorsModel::ExecutionSpecificationAnchors::%s";

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	@Test
	public void above_int() {
		MElement<?> requestRecv = model.getElement(QN, "request-recv");
		assertThat(above(150).test(requestRecv), is(true));
		assertThat(above(50).test(requestRecv), is(false));
	}

	@Test
	public void above_MElement() {
		MElement<?> requestRecv = model.getElement(QN, "request-recv");
		MElement<?> replySend = model.getElement(QN, "reply-send");
		assertThat(above(replySend).test(requestRecv), is(true));
		assertThat(above(requestRecv).test(replySend), is(false));
	}

	@Test
	public void below_int() {
		MElement<?> requestRecv = model.getElement(QN, "request-recv");
		assertThat(below(50).test(requestRecv), is(true));
		assertThat(below(150).test(requestRecv), is(false));
	}

	@Test
	public void below_MElement() {
		MElement<?> requestRecv = model.getElement(QN, "request-recv");
		MElement<?> replySend = model.getElement(QN, "reply-send");
		assertThat(below(requestRecv).test(replySend), is(true));
		assertThat(below(replySend).test(requestRecv), is(false));
	}

}
