package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"CreateMessageTesting.uml", "CreateMessageTesting.notation" })
public class CreateMessageTest {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private MInteraction interaction;

	private int lifeline1Top;

	private int lifeline2Top;

	private int lifeline3Top;

	private int message1Top;

	private int message2Top;

	private int execution1Top;

	private int execution2Top;

	private int execution3Top;

	private int lifeline2Header;

	private int lifeline3Header;

	private int message3Top;

	@Before
	public void before() {
		lifeline1Top = interaction().getLifelines().get(0).getTop().getAsInt();
		lifeline2Top = interaction().getLifelines().get(1).getTop().getAsInt();
		lifeline3Top = interaction().getLifelines().get(2).getTop().getAsInt();

		lifeline2Header = model.getLifelineBodyTop(interaction().getLifelines().get(1)) - lifeline2Top;
		lifeline3Header = model.getLifelineBodyTop(interaction().getLifelines().get(2)) - lifeline3Top;

		message1Top = interaction().getMessages().get(0).getTop().getAsInt();
		message2Top = interaction().getMessages().get(1).getTop().getAsInt();
		if (interaction().getMessages().size() == 3) {
			message3Top = interaction().getMessages().get(2).getTop().getAsInt();
		}

		execution1Top = interaction().getLifelines().get(0).getExecutionOccurrences().get(0).getTop()
				.getAsInt();
		execution2Top = interaction().getLifelines().get(1).getExecutionOccurrences().get(0).getTop()
				.getAsInt();
		execution3Top = interaction().getLifelines().get(2).getExecutionOccurrences().get(0).getTop()
				.getAsInt();
	}

	private void execute(Command command) {
		model.execute(command);
		/* force reinit after change */
		interaction = null;
	}

	private MInteraction interaction() {
		if (interaction == null) {
			interaction = model.getMInteraction();
		}
		return interaction;
	}

	@Test
	public void creationMessage() {
		/* setup */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		CreationCommand<Message> command = lifeline1.insertMessageAfter(lifeline1, 10, lifeline2,
				MessageSort.CREATE_MESSAGE_LITERAL, null);

		/* act */
		execute(command);

		/* assert: Create Message created */
		/* assert: Lifeline1, Lifeline3 should not move */
		/* assert: Lifeline2 moved down */
		/* assert: other elements nudged down */
		assertEquals(3, interaction().getMessages().size());
		assertEquals(MessageSort.CREATE_MESSAGE_LITERAL,
				interaction().getMessages().get(2).getElement().getMessageSort());

		/*
		 * we created this message sending from the start of an execution, so if that execution was nudged
		 * down, then the message will have followed. Therefore, assert the message location based on the
		 * sending execution
		 */
		MExecution exec = lifeline1.getExecutions().get(0);
		assertEquals(exec.getTop().getAsInt(), interaction().getMessages().get(2).getTop().getAsInt());

		assertEquals(lifeline1Top, interaction().getLifelines().get(0).getTop().getAsInt());
		assertEquals(lifeline3Top, interaction().getLifelines().get(2).getTop().getAsInt());

		/* we drew the creation message to the middle of the side of the lifeline head */
		int nudgedLifeline2Top = model.getLifelineBodyTop(interaction().getLifelines().get(0)) + 10 //
				- (lifeline2Header / 2);
		assertEquals(nudgedLifeline2Top, interaction().getLifelines().get(1).getTop().getAsInt());

		int delta = nudgedLifeline2Top - lifeline2Top;
		assertEquals(message1Top + delta, interaction().getMessages().get(0).getTop().getAsInt());
		assertEquals(message2Top + delta, interaction().getMessages().get(1).getTop().getAsInt());
		assertEquals(execution1Top + delta,
				interaction().getLifelines().get(0).getExecutionOccurrences().get(0).getTop().getAsInt());
		assertEquals(execution2Top + delta,
				interaction().getLifelines().get(1).getExecutionOccurrences().get(0).getTop().getAsInt());
		assertEquals(execution3Top + delta,
				interaction().getLifelines().get(2).getExecutionOccurrences().get(0).getTop().getAsInt());
	}

	@Test
	public void nestedCreationMessage() {
		/* adapt model */
		CreationCommand<Message> command = interaction().getLifelines().get(0).insertMessageAfter(
				interaction().getLifelines().get(0), 5, interaction().getLifelines().get(1),
				MessageSort.CREATE_MESSAGE_LITERAL, null);
		execute(command);

		/* reinit before */
		before();

		/* setup */
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		MLifeline lifeline3 = interaction().getLifelines().get(2);
		command = lifeline2.insertMessageAfter(lifeline2, 5, lifeline3, MessageSort.CREATE_MESSAGE_LITERAL,
				null);

		/* act */
		execute(command);

		/* assert: Create Message created */
		/* assert: Lifeline1, Lifeline2, First CreateMessage should not move */
		/* assert: Lifeline3 moved down */
		/* assert: other elements nudged down */
		assertEquals(4, interaction().getMessages().size());
		assertEquals(MessageSort.CREATE_MESSAGE_LITERAL,
				interaction().getMessages().get(3).getElement().getMessageSort());
		assertEquals(model.getLifelineBodyTop(interaction().getLifelines().get(1)) + 5,
				interaction().getMessages().get(3).getTop().getAsInt());

		assertEquals(lifeline1Top, interaction().getLifelines().get(0).getTop().getAsInt());
		assertEquals(lifeline2Top, interaction().getLifelines().get(1).getTop().getAsInt());
		assertEquals(message3Top, interaction().getMessages().get(2).getTop().getAsInt());

		int nudgedLifeline3Top = interaction().getMessages().get(3).getTop().getAsInt()
				- (lifeline3Header / 2);
		assertEquals(nudgedLifeline3Top, interaction().getLifelines().get(2).getTop().getAsInt());

		int delta = nudgedLifeline3Top - lifeline3Top;
		assertEquals(message1Top + delta, interaction().getMessages().get(0).getTop().getAsInt());
		assertEquals(message2Top + delta, interaction().getMessages().get(1).getTop().getAsInt());
		assertEquals(execution1Top + delta,
				interaction().getLifelines().get(0).getExecutionOccurrences().get(0).getTop().getAsInt());
		assertEquals(execution2Top + delta,
				interaction().getLifelines().get(1).getExecutionOccurrences().get(0).getTop().getAsInt());
		assertEquals(execution3Top + delta,
				interaction().getLifelines().get(2).getExecutionOccurrences().get(0).getTop().getAsInt());
	}
}
