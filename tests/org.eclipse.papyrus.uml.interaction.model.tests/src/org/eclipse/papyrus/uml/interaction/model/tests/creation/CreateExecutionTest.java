/*****************************************************************************
 * Copyright (c) 2018 Philip Langer and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static org.eclipse.uml2.uml.UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"CreateExecutionTesting.uml", "CreateExecutionTesting.notation" })
@SuppressWarnings("boxing")
public class CreateExecutionTest {

	private static final int EXECUTION_HEIGHT = 20;

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private MInteraction interaction;

	private int message1Top;

	private int message2Top;

	private int message3Top;

	private int message4Top;

	@Before
	public void before() {
		message1Top = interaction().getMessages().get(0).getTop().getAsInt();
		message2Top = interaction().getMessages().get(1).getTop().getAsInt();
		message3Top = interaction().getMessages().get(2).getTop().getAsInt();
		message4Top = interaction().getMessages().get(3).getTop().getAsInt();
	}

	private MInteraction interaction() {
		if (interaction == null) {
			interaction = model.getMInteraction();
		}
		return interaction;
	}

	private void execute(Command command) {
		model.execute(command);
		/* force reinit after change */
		interaction = null;
	}

	@Test
	public void creationExecutionOnTopOfLifeline1() {
		/* setup */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		CreationCommand<ExecutionSpecification> command = lifeline1.insertExecutionAfter(lifeline1, 10,
				EXECUTION_HEIGHT, ACTION_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert: All messages moved down */
		assertThat(interaction().getMessages().get(0).getTop().getAsInt(),
				is(message1Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(1).getTop().getAsInt(),
				is(message2Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(2).getTop().getAsInt(),
				is(message3Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(3).getTop().getAsInt(),
				is(message4Top + EXECUTION_HEIGHT));
	}

	@Test
	public void creationExecutionOnTopOfLifeline2() {
		/* setup */
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		CreationCommand<ExecutionSpecification> command = lifeline2.insertExecutionAfter(lifeline2, 0,
				EXECUTION_HEIGHT, ACTION_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert: All messages moved down */
		assertThat(interaction().getMessages().get(0).getTop().getAsInt(),
				is(message1Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(1).getTop().getAsInt(),
				is(message2Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(2).getTop().getAsInt(),
				is(message3Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(3).getTop().getAsInt(),
				is(message4Top + EXECUTION_HEIGHT));
	}

	@Test
	public void creationExecutionAfterMessage1OnLifeline1() {
		/* setup */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		CreationCommand<ExecutionSpecification> command = lifeline1.insertExecutionAfter(
				interaction().getMessages().get(0), 0, EXECUTION_HEIGHT, ACTION_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert: message 1 didn't move, message 2, 3 and 4 moved down */
		assertThat(interaction().getMessages().get(0).getTop().getAsInt(), is(message1Top));

		assertThat(interaction().getMessages().get(1).getTop().getAsInt(),
				is(message2Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(2).getTop().getAsInt(),
				is(message3Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(3).getTop().getAsInt(),
				is(message4Top + EXECUTION_HEIGHT));
	}

	@Test
	public void creationExecutionAfterMessage1OnLifeline2() {
		/* setup */
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		CreationCommand<ExecutionSpecification> command = lifeline2.insertExecutionAfter(
				interaction().getMessages().get(0), 0, EXECUTION_HEIGHT, ACTION_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert: message 1 didn't move, message 2, 3 and 4 moved down */
		assertThat(interaction().getMessages().get(0).getTop().getAsInt(), is(message1Top));

		assertThat(interaction().getMessages().get(1).getTop().getAsInt(),
				is(message2Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(2).getTop().getAsInt(),
				is(message3Top + EXECUTION_HEIGHT));
		assertThat(interaction().getMessages().get(3).getTop().getAsInt(),
				is(message4Top + EXECUTION_HEIGHT));
	}
}
