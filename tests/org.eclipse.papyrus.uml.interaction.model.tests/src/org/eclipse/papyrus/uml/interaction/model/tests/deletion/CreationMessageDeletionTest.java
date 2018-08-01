package org.eclipse.papyrus.uml.interaction.model.tests.deletion;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"CreateMessageDeletionTesting.uml", "CreateMessageDeletionTesting.notation" })
public class CreationMessageDeletionTest {

	@Rule
	public final ModelFixture.Edit model = new ModelFixture.Edit();

	private MInteraction interaction;

	private int execution4Top;

	private int execution2Top;

	private int execution1Top;

	private int message2Top;

	private int message1Top;

	private int message4Top;

	private int message5Top;

	private int message3Top;

	private int lifeline2Top;

	private int lifeline1Top;

	private int lifeline3Top;

	@Before
	public void before() {
		lifeline1Top = interaction().getLifelines().get(0).getTop().getAsInt();
		lifeline2Top = interaction().getLifelines().get(1).getTop().getAsInt();
		lifeline3Top = interaction().getLifelines().get(2).getTop().getAsInt();

		message3Top = interaction().getMessages().get(0).getTop().getAsInt();
		message5Top = interaction().getMessages().get(1).getTop().getAsInt();
		message4Top = interaction().getMessages().get(2).getTop().getAsInt();
		message1Top = interaction().getMessages().get(3).getTop().getAsInt();
		message2Top = interaction().getMessages().get(4).getTop().getAsInt();

		execution1Top = interaction().getLifelines().get(0).getExecutionOccurrences().get(0).getTop()
				.getAsInt();
		execution2Top = interaction().getLifelines().get(1).getExecutionOccurrences().get(0).getTop()
				.getAsInt();
		execution4Top = interaction().getLifelines().get(2).getExecutionOccurrences().get(0).getTop()
				.getAsInt();
	}

	private MInteraction interaction() {
		if (interaction == null) {
			interaction = MInteraction.getInstance(model.getInteraction(), model.getSequenceDiagram().get());
		}
		return interaction;
	}

	private void execute(Command remove) {
		if (!remove.canExecute()) {
			Assert.fail("Command not executable"); //$NON-NLS-1$
		}
		remove.execute();
		/* force reinit after change */
		interaction = null;
	}

	@Test
	public void message_oneCreation() {
		/* setup */
		MMessage toDelete = interaction().getMessages().get(4);

		/* act */
		execute(toDelete.remove());

		/* assert: Message2 is deleted */
		/* assert: Lifeline1, Lifeline2, Message1 should not move */
		/* assert: Lifeline3 moved to top */
		/* assert: other elements nudged up */
		assertEquals(4, interaction().getMessages().size());

		assertEquals(lifeline1Top, interaction().getLifelines().get(0).getTop().getAsInt());
		assertEquals(lifeline2Top, interaction().getLifelines().get(1).getTop().getAsInt());
		assertEquals(message1Top, interaction().getMessages().get(3).getTop().getAsInt());

		assertEquals(lifeline1Top, interaction().getLifelines().get(2).getTop().getAsInt());

		int delta = message3Top - message2Top;
		assertEquals(message3Top - delta, interaction().getMessages().get(0).getTop().getAsInt());
		assertEquals(message5Top - delta, interaction().getMessages().get(1).getTop().getAsInt());
		assertEquals(message4Top - delta, interaction().getMessages().get(2).getTop().getAsInt());
		assertEquals(message3Top - delta, interaction().getMessages().get(0).getBottom().getAsInt());
		assertEquals(message5Top - delta, interaction().getMessages().get(1).getBottom().getAsInt());
		assertEquals(message4Top - delta, interaction().getMessages().get(2).getBottom().getAsInt());
		assertEquals(execution1Top - delta,
				interaction().getLifelines().get(0).getExecutionOccurrences().get(0).getTop().getAsInt());
		assertEquals(execution2Top - delta,
				interaction().getLifelines().get(1).getExecutionOccurrences().get(0).getTop().getAsInt());
		assertEquals(execution4Top - delta,
				interaction().getLifelines().get(2).getExecutionOccurrences().get(0).getTop().getAsInt());
	}

	@Test
	public void message_nestedCreation() {
		/* setup */
		MMessage toDelete = interaction().getMessages().get(3);

		/* act */
		execute(toDelete.remove());

		/* assert: Message1 is deleted */
		/* assert: Lifeline1 should not move */
		/* assert: Lifeline2 moved to top */
		/* assert: other elements nudged up */
		assertEquals(4, interaction().getMessages().size());

		assertEquals(lifeline1Top, interaction().getLifelines().get(0).getTop().getAsInt());

		assertEquals(lifeline1Top, interaction().getLifelines().get(1).getTop().getAsInt());

		int delta = message2Top - message1Top;
		assertEquals(lifeline3Top - delta, interaction().getLifelines().get(2).getTop().getAsInt());
		assertEquals(message3Top - delta, interaction().getMessages().get(0).getTop().getAsInt());
		assertEquals(message5Top - delta, interaction().getMessages().get(1).getTop().getAsInt());
		assertEquals(message4Top - delta, interaction().getMessages().get(2).getTop().getAsInt());
		assertEquals(message2Top - delta, interaction().getMessages().get(3).getTop().getAsInt());
		assertEquals(message3Top - delta, interaction().getMessages().get(0).getBottom().getAsInt());
		assertEquals(message5Top - delta, interaction().getMessages().get(1).getBottom().getAsInt());
		assertEquals(message4Top - delta, interaction().getMessages().get(2).getBottom().getAsInt());
		// TODO CREATE MESSAGE IS ANCHORED ON HEADER NOT ON BODY
		// assertEquals(message2Top - delta, interaction().getMessages().get(3).getBottom().getAsInt());
		assertEquals(execution1Top - delta,
				interaction().getLifelines().get(0).getExecutionOccurrences().get(0).getTop().getAsInt());
		assertEquals(execution2Top - delta,
				interaction().getLifelines().get(1).getExecutionOccurrences().get(0).getTop().getAsInt());
		assertEquals(execution4Top - delta,
				interaction().getLifelines().get(2).getExecutionOccurrences().get(0).getTop().getAsInt());
	}

}
