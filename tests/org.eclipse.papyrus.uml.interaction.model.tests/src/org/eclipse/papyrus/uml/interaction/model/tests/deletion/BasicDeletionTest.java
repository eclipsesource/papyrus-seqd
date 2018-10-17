package org.eclipse.papyrus.uml.interaction.model.tests.deletion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.IntSupplier;

import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"DeletionTesting.uml", "DeletionTesting.notation" })
public class BasicDeletionTest {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private MInteraction interaction;

	private static void assertTop(int top, MElement<?> element) {
		assertEquals(top, element.getTop().orElseGet(fail("Top missing"))); //$NON-NLS-1$
	}

	private static void assertTopLeft(int top, int left, MLifeline element) {
		assertTop(top, element);
		assertEquals(left, element.getLeft().orElseGet(fail("Left missing"))); //$NON-NLS-1$
	}

	private static IntSupplier fail(String msg) {
		return () -> {
			Assert.fail(msg);
			return 0;
		};
	}

	private MInteraction interaction() {
		if (interaction == null) {
			interaction = model.getMInteraction();
		}
		return interaction;
	}

	private void executeAndAssertRemoval(RemovalCommand<Element> remove,
			MElement<?>... expectedElementsToBeRemoved) {
		if (!remove.canExecute()) {
			Assert.fail("Command not executable"); //$NON-NLS-1$
		}
		/* make sure no duplicates */
		Set<MElement<?>> exepcted = new LinkedHashSet<MElement<?>>(
				Arrays.asList(expectedElementsToBeRemoved));
		Collection<Element> elementsToRemove = remove.getElementsToRemove();
		assertEquals(exepcted.size(), elementsToRemove.size());
		for (MElement<?> mElement : exepcted) {
			assertTrue(elementsToRemove.contains(mElement.getElement()));
		}
		remove.execute();

		/* force reinit after change */
		interaction = null;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void message_notTriggeringExecution() {
		/* setup */
		MMessage message = interaction().getMessages().get(1);
		MExecution executionBeforeMessage = interaction.getLifelines().get(1).getExecutions().get(0);
		MExecution executionAfterMessage = interaction.getLifelines().get(1).getExecutions().get(1);

		final int spaceToPreserve = executionAfterMessage.getTop().getAsInt()
				- message.getBottom().getAsInt();
		/* act */
		executeAndAssertRemoval((RemovalCommand<Element>)message.remove(), //
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

		/* the space after the deleted message and the next element has been preserved */

		assertEquals(spaceToPreserve,
				executionAfterMessage.getTop().getAsInt() - executionBeforeMessage.getBottom().getAsInt());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void message_triggeringExecution() {
		/* setup */
		MMessage message1 = interaction().getMessages().get(0);
		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);

		MExecution execution1 = interaction().getLifelines().get(0).getExecutions().get(0);
		int diff = execution2.getBottom().getAsInt() - execution1.getBottom().getAsInt();

		int message2TopExpected = interaction().getMessages().get(1).getTop().getAsInt() - diff;
		int execution3TopExpected = interaction().getLifelines().get(1).getExecutions().get(1).getTop()
				.getAsInt() - diff;

		/* act */
		executeAndAssertRemoval((RemovalCommand<Element>)message1.remove(), //
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

		/* execution 1 slightly smaller than deleted, following message and execution should move up */
		assertTop(message2TopExpected, interaction().getMessages().get(0));
		assertTop(execution3TopExpected, interaction().getLifelines().get(1).getExecutions().get(0));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void execution_withMessageTriggeringOtherExecution() {
		/* setup */
		MExecution execution1 = interaction().getLifelines().get(0).getExecutions().get(0);
		MMessage message1 = interaction().getMessages().get(0);
		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);

		MExecution execution4 = interaction().getLifelines().get(2).getExecutions().get(0);
		MMessage message2 = interaction().getMessages().get(1);
		int execution4NewTop = execution1.getTop().getAsInt();
		int execution4NewBottom = execution4NewTop
				+ (execution4.getBottom().getAsInt() - execution4.getTop().getAsInt());

		int diff = message2.getTop().getAsInt() - execution2.getBottom().getAsInt();
		int message2NewTop = execution4NewBottom + diff;
		int execution3NewTop = message2NewTop
				+ (interaction().getLifelines().get(1).getExecutions().get(1).getTop().getAsInt()
						- message2.getBottom().getAsInt());

		/* act */
		executeAndAssertRemoval((RemovalCommand<Element>)execution1.remove(), //
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

		/* execution on right should be moved up to where the deleted area was started. */
		/* message and execution below should keep distance to new preceeding element */
		assertTop(execution4NewTop, interaction().getLifelines().get(2).getExecutions().get(0));
		assertTop(message2NewTop, interaction().getMessages().get(0));
		assertTop(execution3NewTop, interaction().getLifelines().get(1).getExecutions().get(0));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void execution_triggeredByMessage() {
		/* setup */
		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);
		MMessage message1 = interaction().getMessages().get(0);

		MExecution execution1 = interaction().getLifelines().get(0).getExecutions().get(0);
		int diff = execution2.getBottom().getAsInt() - execution1.getBottom().getAsInt();

		int message2TopExpected = interaction().getMessages().get(1).getTop().getAsInt() - diff;
		int execution3TopExpected = interaction().getLifelines().get(1).getExecutions().get(1).getTop()
				.getAsInt() - diff;

		/* act */
		executeAndAssertRemoval((RemovalCommand<Element>)execution2.remove(), //
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

		/* execution 1 slightly smaller than deleted, following message and execution should move up */
		assertTop(message2TopExpected, interaction().getMessages().get(0));
		assertTop(execution3TopExpected, interaction().getLifelines().get(1).getExecutions().get(0));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void execution_isolated() {
		/* setup */
		MExecution execution3 = interaction().getLifelines().get(2).getExecutions().get(0);

		/* act */
		executeAndAssertRemoval((RemovalCommand<Element>)execution3.remove(), //
				execution3, execution3.getStart().get(), execution3.getFinish().get());

		/* assert */
		assertEquals(2, interaction().getMessages().size());
		assertEquals(3, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(0, interaction().getLifelines().get(2).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(3, interaction().getLifelines().get(1).getExecutionOccurrences().size());
		assertEquals(0, interaction().getLifelines().get(2).getExecutionOccurrences().size());

		/* no moves */
	}

	@SuppressWarnings("unchecked")
	@Test
	public void lifeline_executionTwoMessagesTriggeringOtherExecution() {
		/* setup */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		MExecution execution1 = interaction().getLifelines().get(0).getExecutions().get(0);
		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);
		MMessage message1 = interaction().getMessages().get(0);
		MMessage message2 = interaction().getMessages().get(1);

		int lifelineTop = lifeline1.getTop().getAsInt();
		int diff = interaction().getLifelines().get(2).getLeft().getAsInt()
				- interaction().getLifelines().get(1).getLeft().getAsInt();
		int lifeline2NewLeft = lifeline1.getLeft().getAsInt();
		int lifeline3NewLeft = lifeline2NewLeft + diff;

		MExecution execution4 = interaction().getLifelines().get(2).getExecutions().get(0);
		int execution4NewTop = execution1.getTop().getAsInt();
		int execution4NewBottom = execution4NewTop
				+ (execution4.getBottom().getAsInt() - execution4.getTop().getAsInt());

		int execution3Top = interaction().getLifelines().get(1).getExecutions().get(1).getTop().getAsInt();
		/* execution 4 newBottom + old gap to message 2 */
		int execution3NewTop = execution4NewBottom + (execution3Top - message2.getBottom().getAsInt());

		/* act */
		executeAndAssertRemoval((RemovalCommand<Element>)lifeline1.remove(), //
				lifeline1, //
				execution1, execution1.getStart().get(), execution1.getFinish().get(), //
				execution2, execution2.getStart().get(), execution2.getFinish().get(), //
				message1, //
				message2, message2.getSend().get(), message2.getReceive().get()); // message2 is not linked to
																					// an
																					// execution, thats why
																					// ends have to
																					// be mentioned

		/* assert */
		assertEquals(0, interaction().getMessages().size());
		assertEquals(2, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutionOccurrences().size());

		/* executions move up, lifelines move to left */
		assertTop(execution4NewTop, interaction().getLifelines().get(1).getExecutions().get(0));
		assertTop(execution3NewTop, interaction().getLifelines().get(0).getExecutions().get(0));
		assertTopLeft(lifelineTop, lifeline2NewLeft, interaction().getLifelines().get(0));
		assertTopLeft(lifelineTop, lifeline3NewLeft, interaction().getLifelines().get(1));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void lifeline_twoMessagesTwoExecution() {
		/* setup */
		MLifeline lifeline2 = interaction().getLifelines().get(1);

		MExecution execution2 = interaction().getLifelines().get(1).getExecutions().get(0);
		MExecution execution4 = interaction().getLifelines().get(1).getExecutions().get(1);
		MMessage message1 = interaction().getMessages().get(0);
		MMessage message2 = interaction().getMessages().get(1);

		int lifelineTop = lifeline2.getTop().getAsInt();
		int lifeline3NewLeft = lifeline2.getLeft().getAsInt();

		int execution1Top = interaction().getLifelines().get(0).getExecutions().get(0).getTop().getAsInt();
		int execution3Top = interaction().getLifelines().get(2).getExecutions().get(0).getTop().getAsInt();

		/* act */
		executeAndAssertRemoval((RemovalCommand<Element>)lifeline2.remove(), //
				lifeline2, //
				execution2, execution2.getStart().get(), execution2.getFinish().get(), //
				execution4, execution4.getStart().get(), execution4.getFinish().get(), //
				message1, //
				message2, message2.getSend().get(), message2.getReceive().get());

		/* assert */
		assertEquals(0, interaction().getMessages().size());
		assertEquals(2, interaction().getLifelines().size());
		assertEquals(1, interaction().getLifelines().get(0).getExecutions().size());
		assertEquals(1, interaction().getLifelines().get(1).getExecutions().size());
		assertEquals(2, interaction().getLifelines().get(0).getExecutionOccurrences().size());
		assertEquals(2, interaction().getLifelines().get(1).getExecutionOccurrences().size());

		/* right lifeline move to left where deleted was */
		assertTop(execution1Top, interaction().getLifelines().get(0).getExecutions().get(0));
		assertTop(execution3Top, interaction().getLifelines().get(1).getExecutions().get(0));
		assertTopLeft(lifelineTop, lifeline3NewLeft, interaction().getLifelines().get(1));
	}
}
