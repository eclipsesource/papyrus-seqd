package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"CreateMessageTestingB.uml", "CreateMessageTestingB.notation" })
public class CreateMessageTestB {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private MInteraction interaction;

	private int lifeline1Top;

	private int lifeline2Top;

	private int lifeline3Top;

	private int message1Top;

	private int message2Top;

	private int lifeline2Header;

	private int message3Top;

	private int message4Top;

	private int message5Top;

	private int lifeline4Top;

	private int lifeline4Header;

	@Before
	public void before() {
		lifeline1Top = interaction().getLifelines().get(0).getTop().getAsInt();
		lifeline2Top = interaction().getLifelines().get(1).getTop().getAsInt();
		lifeline3Top = interaction().getLifelines().get(2).getTop().getAsInt();
		lifeline4Top = interaction().getLifelines().get(3).getTop().getAsInt();

		lifeline2Header = model.getLifelineBodyTop(interaction().getLifelines().get(1)) - lifeline2Top;
		lifeline4Header = model.getLifelineBodyTop(interaction().getLifelines().get(3)) - lifeline4Top;

		message1Top = interaction().getMessages().get(0).getTop().getAsInt();
		message2Top = interaction().getMessages().get(4).getTop().getAsInt();
		message3Top = interaction().getMessages().get(1).getTop().getAsInt();
		message4Top = interaction().getMessages().get(2).getTop().getAsInt();
		message5Top = interaction().getMessages().get(3).getTop().getAsInt();
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
	public void creationMessage1() {
		/* setup */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		CreationCommand<Message> command = lifeline1.insertMessageAfter(lifeline1, 10, lifeline2,
				MessageSort.CREATE_MESSAGE_LITERAL, null);

		/* act */
		execute(command);

		/* assert: Create Message created */
		/* assert: Lifeline1, Lifeline4 should not move */
		/* assert: Lifeline2 moved down */
		/* assert: other elements nudged down */
		assertEquals(6, interaction().getMessages().size());
		assertEquals(MessageSort.CREATE_MESSAGE_LITERAL,
				interaction().getMessages().get(5).getElement().getMessageSort());
		assertEquals(model.getLifelineBodyTop(interaction().getLifelines().get(0)) + 10,
				interaction().getMessages().get(5).getTop().getAsInt());

		assertEquals(lifeline1Top, interaction().getLifelines().get(0).getTop().getAsInt());
		assertEquals(lifeline4Top, interaction().getLifelines().get(3).getTop().getAsInt());

		int nudgedLifeline2Top = interaction().getMessages().get(5).getTop().getAsInt()
				- (lifeline2Header / 2);
		assertEquals(nudgedLifeline2Top, interaction().getLifelines().get(1).getTop().getAsInt());

		int delta = nudgedLifeline2Top - lifeline2Top;
		assertEquals(lifeline3Top + delta, interaction().getLifelines().get(2).getTop().getAsInt());
		assertEquals(message1Top + delta, interaction().getMessages().get(0).getTop().getAsInt());
		assertEquals(message2Top + delta, interaction().getMessages().get(4).getTop().getAsInt());
		assertEquals(message3Top + delta, interaction().getMessages().get(1).getTop().getAsInt());
		assertEquals(message4Top + delta, interaction().getMessages().get(2).getTop().getAsInt());
		assertEquals(message5Top + delta, interaction().getMessages().get(3).getTop().getAsInt());
		assertEquals(message1Top + delta, interaction().getMessages().get(0).getBottom().getAsInt());
		assertEquals(message2Top + delta, interaction().getMessages().get(4).getBottom().getAsInt());
		assertEquals(message3Top + delta, interaction().getMessages().get(1).getBottom().getAsInt());
		assertEquals(message4Top + delta, interaction().getMessages().get(2).getBottom().getAsInt());
		assertEquals(message5Top + delta, interaction().getMessages().get(3).getBottom().getAsInt());
	}

	@Test
	public void creationMessage2() {
		/* setup */
		MLifeline lifeline3 = interaction().getLifelines().get(2);
		MLifeline lifeline4 = interaction().getLifelines().get(3);
		CreationCommand<Message> command = lifeline3.insertMessageAfter(lifeline3, 10, lifeline4,
				MessageSort.CREATE_MESSAGE_LITERAL, null);

		/* act */
		execute(command);
		/* assert: Create Message created */
		/* assert: Lifeline1, Lifeline2, Lifeline3, Message1, Messag2 should not move */
		/* assert: Lifeline4 moved down */
		/* assert: other elements nudged down */
		assertEquals(6, interaction().getMessages().size());
		assertEquals(MessageSort.CREATE_MESSAGE_LITERAL,
				interaction().getMessages().get(5).getElement().getMessageSort());
		assertEquals(model.getLifelineBodyTop(interaction().getLifelines().get(2)) + 10,
				interaction().getMessages().get(5).getTop().getAsInt());

		assertEquals(lifeline1Top, interaction().getLifelines().get(0).getTop().getAsInt());
		assertEquals(lifeline2Top, interaction().getLifelines().get(1).getTop().getAsInt());
		assertEquals(lifeline3Top, interaction().getLifelines().get(2).getTop().getAsInt());
		assertEquals(message1Top, interaction().getMessages().get(0).getTop().getAsInt());
		assertEquals(message2Top, interaction().getMessages().get(4).getTop().getAsInt());
		assertEquals(message1Top, interaction().getMessages().get(0).getBottom().getAsInt());
		assertEquals(message2Top, interaction().getMessages().get(4).getBottom().getAsInt());

		int nudgedLifeline4Top = interaction().getMessages().get(5).getTop().getAsInt()
				- (lifeline4Header / 2);
		assertEquals(nudgedLifeline4Top, interaction().getLifelines().get(3).getTop().getAsInt());

		int delta = nudgedLifeline4Top - lifeline2Top;
		assertEquals(lifeline4Top + delta, interaction().getLifelines().get(3).getTop().getAsInt());
		assertEquals(message3Top + delta, interaction().getMessages().get(1).getTop().getAsInt());
		assertEquals(message4Top + delta, interaction().getMessages().get(2).getTop().getAsInt());
		assertEquals(message5Top + delta, interaction().getMessages().get(3).getTop().getAsInt());
		assertEquals(message3Top + delta, interaction().getMessages().get(1).getBottom().getAsInt());
		assertEquals(message4Top + delta, interaction().getMessages().get(2).getBottom().getAsInt());
		assertEquals(message5Top + delta, interaction().getMessages().get(3).getBottom().getAsInt());
	}

}
