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

package org.eclipse.papyrus.uml.interaction.internal.model.spi.impl.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelAdapter;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link LogicalModelAdapter} class.
 *
 * @author Christian W. Damus
 */
@ModelResource("lifelines_layout.uml")
public class LogicalModelAdapterTest {

	@Rule
	public final ModelFixture.Edit model = new ModelFixture.Edit();

	private LogicalModelAdapter<MInteraction> adapter;

	/**
	 * Initializes me.
	 */
	public LogicalModelAdapterTest() {
		super();
	}

	/**
	 * Test that the adapter notifies creation of an already existing logical model when the handler is added.
	 */
	@Test
	public void onLogicalModelCreated_immediate() {
		MInteraction interaction = MInteraction.getInstance(model.getInteraction());
		AtomicReference<MInteraction> ref = new AtomicReference<>();

		adapter.onLogicalModelCreated(ref::set);

		assertThat("Handler not notified", ref.get(), is(interaction));
	}

	/**
	 * Test that the adapter notifies creation of a new logical model when it is created when the handler is
	 * added.
	 */
	@Test
	public void onLogicalModelCreated_new() {
		AtomicReference<MInteraction> ref = new AtomicReference<>();

		adapter.onLogicalModelCreated(ref::set);
		assumeThat("Logical model already existed", ref.get(), nullValue());

		MInteraction interaction = MInteraction.getInstance(model.getInteraction());
		assertThat("Handler not notified", ref.get(), is(interaction));
	}

	/**
	 * Test that the adapter notifies disposal of a logical model.
	 */
	@Test
	public void onLogicalModelDisposed() {
		MInteraction interaction = MInteraction.getInstance(model.getInteraction());
		AtomicReference<MInteraction> ref = new AtomicReference<>();

		adapter.onLogicalModelDisposed(ref::set);

		// This triggers disposal of the logical model
		interaction.getElement().setName("NewName");

		assertThat("Handler not notified", ref.get(), is(interaction));
	}

	//
	// Test framework
	//

	@Before
	public void createSUT() {
		adapter = new LogicalModelAdapter<>(model.getInteraction(), MInteraction.class);
	}

	@After
	public void destroySUT() {
		adapter = null;
	}

}
