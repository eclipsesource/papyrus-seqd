package org.eclipse.papyrus.uml.interaction.model.tests.deletion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"DeleteLifeline.uml", "DeleteLifeline.notation" })
@SuppressWarnings("boxing")
public class DeleteLifelineTest {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private int message1Top, message2Top, message3Top, message4Top, message5Top, message6Top, message7Top,
			message8Top, message9Top, message10Top, message11Top;

	private int execution1Top, execution2Top, execution3Top, execution4Top, execution5Top, execution6Top,
			execution7Top;

	private MInteraction interaction;

	@Before
	public void before() {
		message1Top = messageAt(0).getTop().getAsInt();
		message2Top = messageAt(1).getTop().getAsInt();
		message3Top = messageAt(2).getTop().getAsInt();
		message4Top = messageAt(3).getTop().getAsInt();
		message5Top = messageAt(4).getTop().getAsInt();
		message6Top = messageAt(5).getTop().getAsInt();
		message7Top = messageAt(6).getTop().getAsInt();
		message8Top = messageAt(7).getTop().getAsInt();
		message9Top = messageAt(8).getTop().getAsInt();
		message10Top = messageAt(9).getTop().getAsInt();
		message11Top = messageAt(10).getTop().getAsInt();

		execution1Top = executionAt(1, 0).getTop().getAsInt();
		execution2Top = executionAt(1, 1).getTop().getAsInt();
		execution3Top = executionAt(1, 2).getTop().getAsInt();
		execution4Top = executionAt(1, 3).getTop().getAsInt();
		execution5Top = executionAt(2, 0).getTop().getAsInt();
		execution6Top = executionAt(2, 1).getTop().getAsInt();
		execution7Top = executionAt(3, 0).getTop().getAsInt();

		assertEquals(Arrays.asList(122, 152, 166, 289, 504, 529, 618, 646, 686, 702, 742), //
				Arrays.asList(message1Top, message2Top, message3Top, message4Top, message5Top, message6Top,
						message7Top, message8Top, message9Top, message10Top, message11Top));

		assertEquals(Arrays.asList(152, 174, 224, 335, 548, 646, 702), //
				Arrays.asList(execution1Top, execution2Top, execution3Top, execution4Top, execution5Top,
						execution6Top, execution7Top));
	}

	private void execute(Command command) {
		assertTrue(command.canExecute());
		model.execute(command);
		/* force reinit after change */
		interaction = null;

		assertEquals(3, interaction().getLifelines().size());
	}

	@Test
	@Ignore
	public void deletionFirstLifeline() {
		/* setup */
		Command command = lifelineAt(0).remove();

		/* act */
		execute(command);

		/* check results */
		assertEquals("Lifeline2", lifelineAt(0).getElement().getName());

		assertTrue(lifelineAt(0).getExecutions().isEmpty());
	}

	@Test
	@Ignore
	public void deletionSecondLifeline() {
		/* setup */
		Command command = lifelineAt(1).remove();

		/* act */
		execute(command);

		/* check results */
	}

	@Test
	public void deletionThirdLifeline() {
		/* setup */
		Command command = lifelineAt(2).remove();

		/* act */
		execute(command);

		/* check results */
		assertEquals("Lifeline4", interaction().getLifelines().get(2).getName());//$NON-NLS-1$

		/* check executions on lifeline2 (with nested) */
		assertEquals(4, interaction().getLifelines().get(1).getExecutions().size());
		int exec1NewTop = 102;
		assertEquals(exec1NewTop, executionAt(1, 0).getTop().getAsInt());
		// delta between top of Execution1 and its nested exeuction should be kept
		assertEquals(exec1NewTop + execution2Top - execution1Top, executionAt(1, 1).getTop().getAsInt());
		assertEquals(exec1NewTop + execution3Top - execution1Top, executionAt(1, 2).getTop().getAsInt());
		assertEquals(exec1NewTop + execution4Top - execution1Top, executionAt(1, 3).getTop().getAsInt());

		/* check other elements */
		int exec7NewTop = 470;
		assertEquals(1, interaction().getLifelines().get(2).getExecutions().size());
		assertEquals(exec7NewTop, executionAt(2, 0).getTop().getAsInt());

		assertEquals(4, interaction().getMessages().size());
		assertEquals("msg10", messageAt(2).getElement().getName()); //$NON-NLS-1$
		assertEquals(exec7NewTop, messageAt(2).getTop().getAsInt());

		assertEquals("msg11", messageAt(3).getElement().getName());//$NON-NLS-1$
		assertEquals(exec7NewTop + 40, messageAt(3).getTop().getAsInt());
	}

	@Test
	public void deletionFourthLifeline() {
		/* setup */
		Command command = lifelineAt(3).remove();

		/* act */
		execute(command);

		/* check results */
		assertEquals(4, lifelineAt(1).getExecutions().size());
		assertEquals(2, lifelineAt(2).getExecutions().size());

		assertEquals(message1Top, interaction().getMessages().get(0).getTop().getAsInt());
		assertEquals(message9Top, interaction().getMessages().get(6).getTop().getAsInt());
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

	private MInteraction interaction() {
		if (interaction == null) {
			interaction = model.getMInteraction();
		}
		return interaction;
	}

}
