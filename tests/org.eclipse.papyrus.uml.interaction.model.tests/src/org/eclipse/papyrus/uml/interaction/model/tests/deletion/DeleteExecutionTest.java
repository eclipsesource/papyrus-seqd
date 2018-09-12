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
package org.eclipse.papyrus.uml.interaction.model.tests.deletion;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"DeleteExecutionTesting.uml", "DeleteExecutionTesting.notation" })
@SuppressWarnings("boxing")
public class DeleteExecutionTest {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private MInteraction interaction;

	private int execution1OfLifeline1Top;

	private int execution2OfLifeline1Top;

	private int totalHeightExecution1Lifeline1;

	private int totalHeightExecution2Lifeline1;

	private int execution1OfLifeline2Top;

	private int execution2OfLifeline2Top;

	private int totalHeightExecution1Lifeline2;

	private int totalHeightExecution2Lifeline2;

	private int message1Top;

	private int message2Top;

	private int message3Top;

	private int message4Top;

	@Before
	public void before() {
		message1Top = messageAt(0).getTop().getAsInt();
		message2Top = messageAt(1).getTop().getAsInt();
		message3Top = messageAt(2).getTop().getAsInt();
		message4Top = messageAt(3).getTop().getAsInt();

		MLifeline lifeline1 = lifelineAt(0);
		MExecution execution1OfLifeline1 = lifeline1.getExecutions().get(0);
		MExecution execution2OfLifeline1 = lifeline1.getExecutions().get(1);
		execution1OfLifeline1Top = execution1OfLifeline1.getTop().getAsInt();
		execution2OfLifeline1Top = execution2OfLifeline1.getTop().getAsInt();
		// see DeleteExecutionTesting.notation:12 -- height = 40; y = 20
		totalHeightExecution1Lifeline1 = 40 + 20;
		// see DeleteExecutionTesting.notation:16 -- height = 40; y = 78
		totalHeightExecution2Lifeline1 = 40 + 78 - totalHeightExecution1Lifeline1;

		MLifeline lifeline2 = lifelineAt(1);
		MExecution execution1OfLifeline2 = lifeline2.getExecutions().get(0);
		MExecution execution2OfLifeline2 = lifeline2.getExecutions().get(1);
		execution1OfLifeline2Top = execution1OfLifeline2.getTop().getAsInt();
		execution2OfLifeline2Top = execution2OfLifeline2.getTop().getAsInt();
		// see DeleteExecutionTesting.notation:29 -- height = 30; y = 122
		totalHeightExecution1Lifeline2 = 30 + 122;
		// see DeleteExecutionTesting.notation:33 -- height = 36; y = 167
		totalHeightExecution2Lifeline2 = 36 + 167 - totalHeightExecution1Lifeline2;
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
	public void deletionOfFirstExecutionOfLifeline1() {
		/* setup */
		Command command = executionAt(0, 0).remove();

		/* act */
		execute(command);

		/*
		 * assert: Only one execution left on lifeline1 and all other executions and all messages moved up
		 */
		assertThat(lifelineAt(0).getExecutions().size(), is(1));
		assertThat(executionAt(0, 0).getTop().getAsInt(),
				is(execution2OfLifeline1Top - totalHeightExecution1Lifeline1));

		assertThat(lifelineAt(1).getExecutions().size(), is(2));
		assertThat(executionAt(1, 0).getTop().getAsInt(),
				is(execution1OfLifeline2Top - totalHeightExecution1Lifeline1));
		assertThat(executionAt(1, 1).getTop().getAsInt(),
				is(execution2OfLifeline2Top - totalHeightExecution1Lifeline1));

		assertThat(messageAt(0).getTop().getAsInt(), is(message1Top - totalHeightExecution1Lifeline1));
		assertThat(messageAt(1).getTop().getAsInt(), is(message2Top - totalHeightExecution1Lifeline1));
		assertThat(messageAt(2).getTop().getAsInt(), is(message3Top - totalHeightExecution1Lifeline1));
		assertThat(messageAt(3).getTop().getAsInt(), is(message4Top - totalHeightExecution1Lifeline1));
	}

	@Test
	public void deletionOfSecondExecutionOfLifeline1() {
		/* setup */
		Command command = executionAt(0, 1).remove();

		/* act */
		execute(command);

		/* assert only one execution left on lifeline1 */
		assertThat(lifelineAt(0).getExecutions().size(), is(1));
		assertThat(executionAt(0, 0).getTop().getAsInt(), is(execution1OfLifeline1Top));

		/* assert first execution hasn't been moved */
		assertThat(lifelineAt(1).getExecutions().size(), is(2));
		assertThat(executionAt(1, 0).getTop().getAsInt(),
				is(execution1OfLifeline2Top - totalHeightExecution2Lifeline1));
		assertThat(executionAt(1, 1).getTop().getAsInt(),
				is(execution2OfLifeline2Top - totalHeightExecution2Lifeline1));

		/* assert all other executions and all messages moved up */
		assertThat(messageAt(0).getTop().getAsInt(), is(message1Top - totalHeightExecution2Lifeline1));
		assertThat(messageAt(1).getTop().getAsInt(), is(message2Top - totalHeightExecution2Lifeline1));
		assertThat(messageAt(2).getTop().getAsInt(), is(message3Top - totalHeightExecution2Lifeline1));
		assertThat(messageAt(3).getTop().getAsInt(), is(message4Top - totalHeightExecution2Lifeline1));
	}

	@Test
	public void deletionOfFirstExecutionOfLifeline2() {
		/* setup */
		Command command = executionAt(1, 0).remove();

		/* act */
		execute(command);

		/* assert only one execution left on lifeline2 and both executions on lifeline1 */
		assertThat(lifelineAt(1).getExecutions().size(), is(1));
		assertThat(lifelineAt(0).getExecutions().size(), is(2));

		/* assert executions on lifeline1 haven't been moved */
		assertThat(executionAt(0, 0).getTop().getAsInt(), is(execution1OfLifeline1Top));
		assertThat(executionAt(0, 1).getTop().getAsInt(), is(execution2OfLifeline1Top));

		/* assert the remaining execution on lifeline2 moved up and all messages moved up */
		int pullUpDelta = totalHeightExecution1Lifeline2 - totalHeightExecution1Lifeline1
				- totalHeightExecution2Lifeline1;
		assertThat(executionAt(1, 0).getTop().getAsInt(), is(execution2OfLifeline2Top - pullUpDelta));
		assertThat(messageAt(0).getTop().getAsInt(), is(message1Top - pullUpDelta));
		assertThat(messageAt(1).getTop().getAsInt(), is(message2Top - pullUpDelta));
		assertThat(messageAt(2).getTop().getAsInt(), is(message3Top - pullUpDelta));
		assertThat(messageAt(3).getTop().getAsInt(), is(message4Top - pullUpDelta));
	}

	@Test
	public void deletionOfSecondExecutionOfLifeline2() {
		/* setup */
		Command command = executionAt(1, 1).remove();

		/* act */
		execute(command);

		/* assert only one execution left on lifeline2 and both executions on lifeline1 */
		assertThat(lifelineAt(1).getExecutions().size(), is(1));
		assertThat(lifelineAt(0).getExecutions().size(), is(2));

		/* assert executions on lifeline1 and remaining on lifeline2 haven't been moved */
		assertThat(executionAt(0, 0).getTop().getAsInt(), is(execution1OfLifeline1Top));
		assertThat(executionAt(0, 1).getTop().getAsInt(), is(execution2OfLifeline1Top));
		assertThat(executionAt(1, 0).getTop().getAsInt(), is(execution1OfLifeline2Top));

		/* assert all messages moved up */
		assertThat(messageAt(0).getTop().getAsInt(), is(message1Top - totalHeightExecution2Lifeline2));
		assertThat(messageAt(1).getTop().getAsInt(), is(message2Top - totalHeightExecution2Lifeline2));
		assertThat(messageAt(2).getTop().getAsInt(), is(message3Top - totalHeightExecution2Lifeline2));
		assertThat(messageAt(3).getTop().getAsInt(), is(message4Top - totalHeightExecution2Lifeline2));
	}

	private MExecution executionAt(int lifelineIndex, int executionIndex) {
		return lifelineAt(lifelineIndex).getExecutions().get(executionIndex);
	}

	protected MLifeline lifelineAt(int lifelineIndex) {
		return interaction().getLifelines().get(lifelineIndex);
	}

	private MMessage messageAt(int messageIndex) {
		return interaction().getMessages().get(messageIndex);
	}

}
