package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case for issue #388.
 */
@ModelResource({"two-lifelines.uml", "two-lifelines.notation" })
public class CreateSelfMessageNudgeTest {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private MInteraction interaction;

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
	public void testStretchOfExecSpec_selfMsgWithoutReceiveInfo() {
		/* adapt model */
		execute(interaction().getLifelines().get(0).insertExecutionAfter(//
				interaction().getLifelines().get(0), //
				250, //
				50, //
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));

		/* setup */
		int execTop = interaction().getLifelines().get(0).getExecutions().get(0).getTop().getAsInt();
		CreationCommand<Message> command = interaction().getLifelines().get(1).insertMessageAfter(//
				interaction().getLifelines().get(1), //
				260, //
				interaction().getLifelines().get(1), //
				MessageSort.SYNCH_CALL_LITERAL, //
				null);

		/* act */
		execute(command);

		/* assert */
		/* top not moved */
		assertEquals(execTop, interaction().getLifelines().get(0).getExecutions().get(0).getTop().getAsInt());
		/* exec bottom to message bottom should be 50 (height of exec) */
		assertEquals(50, interaction().getLifelines().get(0).getExecutions().get(0).getBottom().getAsInt()
				- interaction().getMessages().get(0).getBottom().getAsInt());
	}

	@Test
	public void testStretchOfExecSpec_selfMsgWithReceiveInfo() {
		/* adapt model */
		execute(interaction().getLifelines().get(0).insertExecutionAfter(//
				interaction().getLifelines().get(0), //
				250, //
				50, //
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));

		/* setup */
		int execTop = interaction().getLifelines().get(0).getExecutions().get(0).getTop().getAsInt();
		CreationCommand<Message> command = interaction().getLifelines().get(1).insertMessageAfter(//
				interaction().getLifelines().get(1), //
				260, //
				interaction().getLifelines().get(1), //
				interaction().getLifelines().get(1), //
				290, //
				MessageSort.ASYNCH_CALL_LITERAL, //
				null);

		/* act */
		execute(command);

		/* assert */
		/* top not moved */
		assertEquals(execTop, interaction().getLifelines().get(0).getExecutions().get(0).getTop().getAsInt());

		/* exec bottom to message bottom should be 50 (height of exec) */
		assertEquals(50, interaction().getLifelines().get(0).getExecutions().get(0).getBottom().getAsInt()
				- interaction().getMessages().get(0).getBottom().getAsInt());
	}

}
