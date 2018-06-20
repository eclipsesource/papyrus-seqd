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

import java.util.Optional;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Element;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Unit tests for the {@link LogicalModelPredicates} class.
 */
@ModelResource({"AnchorsModel.di", "AnchorsModel.uml", "AnchorsModel.notation" })
public class LogicalModelPredicatesTest {

	@Rule
	public final ModelFixture model = new ModelFixture.Edit();

	private MInteraction interaction;

	@Test
	public void above_int() {
		MElement<?> requestRecv = getElement("request-recv");
		assertThat(above(150).test(requestRecv), is(true));
		assertThat(above(50).test(requestRecv), is(false));
	}

	@Test
	public void above_MElement() {
		MElement<?> requestRecv = getElement("request-recv");
		MElement<?> replySend = getElement("reply-send");
		assertThat(above(replySend).test(requestRecv), is(true));
		assertThat(above(requestRecv).test(replySend), is(false));
	}

	@Test
	public void below_int() {
		MElement<?> requestRecv = getElement("request-recv");
		assertThat(below(50).test(requestRecv), is(true));
		assertThat(below(150).test(requestRecv), is(false));
	}

	@Test
	public void below_MElement() {
		MElement<?> requestRecv = getElement("request-recv");
		MElement<?> replySend = getElement("reply-send");
		assertThat(below(requestRecv).test(replySend), is(true));
		assertThat(below(replySend).test(requestRecv), is(false));
	}

	//
	// Test framework
	//

	@Before
	public void setup() {
		interaction = MInteraction.getInstance(model.getInteraction(), model.getSequenceDiagram().get());
	}

	public <T extends MElement<? extends Element>> T getElement(String name, Class<T> type) {
		String qName = String.format("AnchorsModel::ExecutionSpecificationAnchors::%s", name);

		Element element = model.getElement(qName);
		return Optional.ofNullable(element).flatMap(interaction::getElement).filter(type::isInstance)
				.map(type::cast).orElseThrow(() -> new AssertionError("no such element: " + name));
	}

	@SuppressWarnings("unchecked")
	public <T extends MElement<? extends Element>> T getElement(String name) {
		return (T)getElement(name, MElement.class);
	}
}
