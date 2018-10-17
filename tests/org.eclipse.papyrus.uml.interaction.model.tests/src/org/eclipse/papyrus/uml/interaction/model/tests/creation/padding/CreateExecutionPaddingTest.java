package org.eclipse.papyrus.uml.interaction.model.tests.creation.padding;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"two-lifelines.uml", "two-lifelines.notation" })
public class CreateExecutionPaddingTest {

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

	private MLifeline lifelineX(int index) {
		return interaction().getLifelines().get(index);
	}

	private MLifeline lifeline1() {
		return lifelineX(0);
	}

	private MLifeline lifeline2() {
		return lifelineX(1);
	}

	private MMessage message1() {
		return interaction().getMessages().get(0);
	}

	@Test
	public void onEmptyLifeline() {
		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(lifeline1(), 1, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(lifeline1().getBottom().getAsInt() + 10,
				lifeline1().getExecutions().get(0).getTop().getAsInt());
	}

	@Test
	public void afterSlopedMessageStart() {
		/* prepare */
		execute(lifeline1().insertMessageAfter(//
				lifeline1(), 30, //
				lifeline2(), lifeline2(), 200, //
				MessageSort.ASYNCH_CALL_LITERAL, null));
		int msgSendTop = message1().getSend().get().getTop().getAsInt();
		int msgReceiveTop = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(
				message1().getSend().get(), 1, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(msgSendTop, message1().getSend().get().getTop().getAsInt());
		assertEquals(msgSendTop + 10, lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(msgReceiveTop + 50, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void beforeSlopedMessageStart() {
		/* prepare */
		execute(lifeline1().insertMessageAfter(//
				lifeline1(), 30, //
				lifeline2(), lifeline2(), 200, //
				MessageSort.ASYNCH_CALL_LITERAL, null));
		int msgSendTop = message1().getSend().get().getTop().getAsInt();
		int msgReceiveTop = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(lifeline1(), 1, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(lifeline1().getBottom().getAsInt() + 10,
				lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(msgSendTop + 50 + 5, message1().getSend().get().getTop().getAsInt());
		assertEquals(msgReceiveTop + 50 + 5, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void afterSlopedMessageReceive() {
		/* prepare */
		execute(lifeline1().insertMessageAfter(//
				lifeline1(), 30, //
				lifeline2(), lifeline2(), 200, //
				MessageSort.ASYNCH_CALL_LITERAL, null));
		int msgSendTop = message1().getSend().get().getTop().getAsInt();
		int msgReceiveTop = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(
				message1().getReceive().get(), 1, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(msgSendTop, message1().getSend().get().getTop().getAsInt());
		assertEquals(msgReceiveTop, message1().getReceive().get().getTop().getAsInt());
		assertEquals(msgReceiveTop + 10, lifeline1().getExecutions().get(0).getTop().getAsInt());
	}

	@Test
	public void beforeSlopedMessageReceive() {
		/* prepare */
		execute(lifeline1().insertMessageAfter(//
				lifeline1(), 30, //
				lifeline2(), lifeline2(), 200, //
				MessageSort.ASYNCH_CALL_LITERAL, null));
		int msgSendTop = message1().getSend().get().getTop().getAsInt();
		int msgReceiveTop = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(lifeline2(), 190,
				50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(msgSendTop, message1().getSend().get().getTop().getAsInt());
		assertEquals(lifeline2().getBottom().getAsInt() + 190,
				lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(msgReceiveTop + 50 + 15, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void afterMessageStart() {
		/* prepare */
		execute(lifeline1().insertMessageAfter(lifeline1(), 30, lifeline2(), MessageSort.SYNCH_CALL_LITERAL,
				null));
		int msgSendTop = message1().getSend().get().getTop().getAsInt();
		int msgReceiveTop = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(
				message1().getSend().get(), 1, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(msgSendTop, message1().getSend().get().getTop().getAsInt());
		assertEquals(msgReceiveTop, message1().getReceive().get().getTop().getAsInt());
		assertEquals(msgSendTop + 10, lifeline1().getExecutions().get(0).getTop().getAsInt());
	}

	@Test
	public void afterMessageReceive() {
		/* prepare */
		execute(lifeline1().insertMessageAfter(lifeline1(), 30, lifeline2(), MessageSort.SYNCH_CALL_LITERAL,
				null));
		int msgSendTop = message1().getSend().get().getTop().getAsInt();
		int msgReceiveTop = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline2().insertExecutionAfter(
				message1().getReceive().get(), 1, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(msgSendTop, message1().getSend().get().getTop().getAsInt());
		assertEquals(msgReceiveTop, message1().getReceive().get().getTop().getAsInt());
		assertEquals(msgSendTop + 10, lifeline2().getExecutions().get(0).getTop().getAsInt());
	}

	@Test
	public void beforeMessageStart() {
		/* prepare */
		execute(lifeline1().insertMessageAfter(lifeline1(), 30, lifeline2(), MessageSort.SYNCH_CALL_LITERAL,
				null));
		int msgSendTop = message1().getSend().get().getTop().getAsInt();
		int msgReceiveTop = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(lifeline1(), 25,
				50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(lifeline1().getBottom().getAsInt() + 25,
				lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(msgSendTop + 70, message1().getSend().get().getTop().getAsInt());
		assertEquals(msgReceiveTop + 70, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void beforeMessageReceive() {
		/* prepare */
		execute(lifeline1().insertMessageAfter(lifeline1(), 30, lifeline2(), MessageSort.SYNCH_CALL_LITERAL,
				null));
		int msgSendTop = message1().getSend().get().getTop().getAsInt();
		int msgReceiveTop = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline2().insertExecutionAfter(lifeline2(), 25,
				50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		assertEquals(lifeline2().getBottom().getAsInt() + 25,
				lifeline2().getExecutions().get(0).getTop().getAsInt());
		assertEquals(msgSendTop + 70, message1().getSend().get().getTop().getAsInt());
		assertEquals(msgReceiveTop + 70, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void beforeExecution() {
		/* prepare */
		execute(lifeline1().insertExecutionAfter(lifeline1(), 30, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(lifeline1(), 25,
				50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		MExecution oldExec = lifeline1().getExecutions().get(0);
		MExecution newExec = lifeline1().getExecutions().get(1);
		assertEquals(newExec.getBottom().getAsInt() + 10, oldExec.getTop().getAsInt());
	}

	@Test
	public void afterExecution() {
		/* prepare */
		execute(lifeline1().insertExecutionAfter(lifeline1(), 30, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));

		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline1().insertExecutionAfter(
				lifeline1().getExecutions().get(0), 1, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		MExecution oldExec = lifeline1().getExecutions().get(0);
		MExecution newExec = lifeline1().getExecutions().get(1);
		assertEquals(oldExec.getBottom().getAsInt() + 10, newExec.getTop().getAsInt());
	}

}
