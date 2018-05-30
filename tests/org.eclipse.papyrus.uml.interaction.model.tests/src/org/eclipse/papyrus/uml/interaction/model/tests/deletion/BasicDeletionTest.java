package org.eclipse.papyrus.uml.interaction.model.tests.deletion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"DeletionTesting.uml", "DeletionTesting.notation" })
public class BasicDeletionTest {

	@Rule
	public final ModelFixture.Edit model = new ModelFixture.Edit();

	private MInteraction interaction;

	private MInteraction interaction() {
		if (interaction == null) {
			interaction = MInteraction.getInstance(model.getInteraction(), model.getSequenceDiagram().get());
		}
		return interaction;
	}

	private void executeAndAssertRemoval(RemovalCommand remove, MElement<?>... expectedElementsToBeRemoved) {
		if (!remove.canExecute()) {
			fail("Command not executable"); //$NON-NLS-1$
		}
		/* make sure no duplicates */
		Set<MElement<?>> exepcted = new LinkedHashSet<MElement<?>>(
				Arrays.asList(expectedElementsToBeRemoved));
		Set<EObject> elementsToRemove = remove.getElementsToRemove();
		assertEquals(exepcted.size(), elementsToRemove.size());
		for (MElement<?> mElement : exepcted) {
			assertTrue(elementsToRemove.contains(mElement.getElement()));
		}
		remove.execute();

		/* force reinit after change */
		interaction = null;

	}

	@Test
	public void message_notTriggeringExecution() {
		/* act */
		MMessage message = interaction().getMessages().get(1);
		executeAndAssertRemoval(message.remove(), //
				message, message.getSend().get(), message.getReceive().get());

		/* assert: only message should be deleted with its ends */
		assertEquals(1, interaction().getMessages().size());
		assertEquals(3, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(2).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(3, interaction().getLifelines().get(1).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(2).getExecutionOccurrences().size());

		// TODO executions 3 and 4 need to move up
	}

	@Test
	public void message_triggeringExecution() {
		/* act */
		MMessage message1 = interaction().getMessages().get(0);
		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);
		executeAndAssertRemoval(message1.remove(), //
				message1, //
				execution2, execution2.getStart().get(), execution2.getFinish().get());

		/*
		 * assert: message and execution on lifeline 2 should be deleted. the start of the execution on
		 * lifeline 1 is no message end any more
		 */
		assertEquals(1, interaction().getMessages().size());
		assertEquals(3, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(2).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(2).getExecutionOccurrences().size());

		// TODO nothing should move, as execution 1 is as big as execution 2
	}

	@Test
	public void execution_withMessageTriggeringOtherExecution() {
		/* act */
		MExecution execution1 = interaction().getLifelines().get(0).getExecutions().get(0);
		MMessage message1 = interaction().getMessages().get(0);
		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);
		executeAndAssertRemoval(execution1.remove(), //
				execution1, execution1.getStart().get(), execution1.getFinish().get(), //
				message1, //
				execution2, execution2.getStart().get(), execution2.getFinish().get());

		/*
		 * assert: besides execution, a message, and the execution triggered by this message should get
		 * deleted
		 */
		assertEquals(1, interaction().getMessages().size());
		assertEquals(3, interaction().getLifelines().size());
		assertEquals(0, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(2).getExecutions().size());
		assertEquals(0, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(2).getExecutionOccurrences().size());

		/* TODO remaining stuff should be moved up */
	}

	@Test
	public void execution_triggeredByMessage() {
		/* act */
		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);
		MMessage message1 = interaction().getMessages().get(0);
		executeAndAssertRemoval(execution2.remove(), //
				execution2, execution2.getStart().get(), execution2.getFinish().get(), //
				message1);

		/*
		 * assert
		 */
		assertEquals(1, interaction().getMessages().size());
		assertEquals(3, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(2).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(2).getExecutionOccurrences().size());

		/* TODO noting should be moved */
	}

	@Test
	public void execution_isolated() {
		/* act */
		MExecution execution3 = interaction().getLifelines().get(2).getExecutions().get(0);
		executeAndAssertRemoval(execution3.remove(), //
				execution3, execution3.getStart().get(), execution3.getFinish().get());

		/*
		 * assert
		 */
		assertEquals(2, interaction().getMessages().size());
		assertEquals(3, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(0, interaction().getLifelines().get(2).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(3, interaction().getLifelines().get(1).getExecutionOccurrences().size());
		assertEquals(0, interaction().getLifelines().get(2).getExecutionOccurrences().size());

		/* TODO last execution should be moved up */
	}

	@Test
	public void lifeline_executionTwoMessagesTriggeringOtherExecution() {
		/* act */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		MExecution execution1 = interaction().getLifelines().get(0).getExecutions().get(0);
		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);
		MMessage message1 = interaction().getMessages().get(0);
		MMessage message2 = interaction().getMessages().get(1);

		executeAndAssertRemoval(lifeline1.remove(), //
				lifeline1, //
				execution1, execution1.getStart().get(), execution1.getFinish().get(), //
				execution2, execution2.getStart().get(), execution2.getFinish().get(), //
				message1, //
				message2, message2.getSend().get(), message2.getReceive().get()); // message2 is not linked to
																					// an
																					// execution, thats why
																					// ends have to
																					// be mentioned

		/*
		 * assert
		 */
		assertEquals(0, interaction().getMessages().size());
		assertEquals(2, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutionOccurrences().size());

		/* TODO move lifelines to left and move execution occurences up */
	}

	@Test
	public void lifeline_twoMessagesTwoExecution() {
		/* act */
		MLifeline lifeline2 = interaction().getLifelines().get(1);

		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);
		MExecution execution4 = interaction().getLifelines().get(1).getExecutions().get(1);
		MMessage message1 = interaction().getMessages().get(0);
		MMessage message2 = interaction().getMessages().get(1);

		executeAndAssertRemoval(lifeline2.remove(), //
				lifeline2, //
				execution2, execution2.getStart().get(), execution2.getFinish().get(), //
				execution4, execution4.getStart().get(), execution4.getFinish().get(), //
				message1, //
				message2, message2.getSend().get(), message2.getReceive().get());

		/*
		 * assert
		 */
		assertEquals(0, interaction().getMessages().size());
		assertEquals(2, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutionOccurrences().size());

		/* TODO move lifelines to left and move execution occurences up */
	}
}
