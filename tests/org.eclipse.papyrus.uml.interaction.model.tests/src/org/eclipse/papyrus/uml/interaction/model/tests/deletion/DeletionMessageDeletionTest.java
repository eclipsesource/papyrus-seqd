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

@ModelResource({"DeletionMessageDeletionTesting.uml", "DeletionMessageDeletionTesting.notation" })
public class DeletionMessageDeletionTest {

	@Rule
	public final ModelFixture.Edit model = new ModelFixture.Edit();

	private MInteraction interaction;

	private int lifeline1Top;

	private int lifeline2Top;

	private int lifeline3Top;

	private int lifeline4Top;

	private int message1Top;

	private int message2Top;

	private int message3Top;

	private int message4Top;

	private int message5Top;

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

	@Before
	public void before() {
		lifeline1Top = interaction().getLifelines().get(0).getTop().getAsInt();
		lifeline2Top = interaction().getLifelines().get(1).getTop().getAsInt();
		lifeline3Top = interaction().getLifelines().get(2).getTop().getAsInt();
		lifeline4Top = interaction().getLifelines().get(3).getTop().getAsInt();

		message1Top = interaction().getMessages().get(0).getTop().getAsInt();
		message2Top = interaction().getMessages().get(4).getTop().getAsInt();
		message3Top = interaction().getMessages().get(1).getTop().getAsInt();
		message4Top = interaction().getMessages().get(2).getTop().getAsInt();
		message5Top = interaction().getMessages().get(3).getTop().getAsInt();
	}

	@Test
	public void delete() {
		/* setup */
		MMessage toDelete = interaction().getMessages().get(0);

		int delta = message2Top - message1Top;

		/* act */
		execute(toDelete.remove());

		/* assert */
		/* Message 1 deleted */
		/* L1, L2, L4 do not move */
		/* M2, L3, M3, M4, M5 all move up */
		assertEquals(4, interaction().getMessages().size());

		assertEquals(lifeline1Top, interaction().getLifelines().get(0).getTop().getAsInt());
		assertEquals(lifeline2Top, interaction().getLifelines().get(1).getTop().getAsInt());
		assertEquals(lifeline4Top, interaction().getLifelines().get(3).getTop().getAsInt());

		assertEquals(lifeline3Top - delta, interaction().getLifelines().get(2).getTop().getAsInt());

		assertEquals(message2Top - delta, interaction().getMessages().get(3).getTop().getAsInt());
		assertEquals(message3Top - delta, interaction().getMessages().get(0).getTop().getAsInt());
		assertEquals(message4Top - delta, interaction().getMessages().get(1).getTop().getAsInt());
		assertEquals(message5Top - delta, interaction().getMessages().get(2).getTop().getAsInt());
		// TODO anchor is header not body
		// assertEquals(message2Top - delta, interaction().getMessages().get(3).getBottom().getAsInt());
		assertEquals(message3Top - delta, interaction().getMessages().get(0).getBottom().getAsInt());
		assertEquals(message4Top - delta, interaction().getMessages().get(1).getBottom().getAsInt());
		assertEquals(message5Top - delta, interaction().getMessages().get(2).getBottom().getAsInt());

	}

}
