package org.eclipse.papyrus.uml.interaction.model.tests.creation.padding;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"two-lifelines.uml", "two-lifelines.notation" })
public class CreateMessagePaddingTest {

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
		return messageX(0);
	}

	private MMessage message2() {
		return messageX(1);
	}

	private MMessage messageX(int index) {
		return interaction().getMessages().get(index);
	}

	private LayoutHelper layoutHelper() {
		return LogicalModelPlugin.INSTANCE.getLayoutHelper(model.getEditingDomain());
	}

	private int lifelineBodyTop(MLifeline lifeline) {
		return layoutHelper().getBottom((Node)model.vertex(lifeline.getElement()).getDiagramView());
	}

	@Test
	public void empty() {
		/* setup */
		CreationCommand<Message> command = lifeline1().insertMessageAfter(lifeline1(), 1, lifeline2(),
				MessageSort.SYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(lifeline1().getBottom().getAsInt() + 25, message1().getSend().get().getTop().getAsInt());
		assertEquals(lifeline1().getBottom().getAsInt() + 25,
				message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void empty_creation() {
		/* setup */
		int lifelineTop = lifeline1().getTop().getAsInt();
		int lifelineBottom = lifeline1().getBottom().getAsInt();

		CreationCommand<Message> command = lifeline1().insertMessageAfter(lifeline1(), 1, lifeline2(),
				MessageSort.CREATE_MESSAGE_LITERAL, null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(lifelineTop, lifeline1().getTop().getAsInt());
		assertEquals(lifeline1().getBottom().getAsInt() + 25, message1().getSend().get().getTop().getAsInt());
		assertEquals(lifeline1().getBottom().getAsInt() + 25,
				message1().getReceive().get().getTop().getAsInt());

		int expectedLifeline2Top = lifelineBottom + 25 - ((lifelineBottom - lifelineTop) / 2);
		assertEquals(expectedLifeline2Top, lifeline2().getTop().getAsInt());
	}

	@Test
	public void additionalInsertionOffSet_send() {
		/* prepare */
		execute(lifeline1().insertExecutionAfter(lifeline1(), 30, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));
		int execution1Bottom = lifeline1().getExecutions().get(0).getBottom().getAsInt();

		/* setup */
		CreationCommand<Message> command = lifeline1().insertMessageAfter(
				lifeline1().getExecutions().get(0).getFinish().get(), 5, lifeline2(),
				MessageSort.ASYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(execution1Bottom + 25, message1().getSend().get().getTop().getAsInt());
		assertEquals(execution1Bottom + 25, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void additionalInsertionOffSet_recv() {
		/* prepare */
		execute(lifeline2().insertExecutionAfter(lifeline2(), 30, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));
		int execution1Bottom = lifeline2().getExecutions().get(0).getBottom().getAsInt();

		/* setup */
		CreationCommand<Message> command = lifeline1().insertMessageAfter(lifeline1(), 85, lifeline2(),
				MessageSort.ASYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(execution1Bottom + 25, message1().getSend().get().getTop().getAsInt());
		assertEquals(execution1Bottom + 25, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void paddingNudge_send() {
		/* prepare */
		execute(lifeline1().insertExecutionAfter(lifeline1(), 30, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));
		int lifelineBottom = lifeline1().getBottom().getAsInt();
		int execution1Top = lifeline1().getExecutions().get(0).getTop().getAsInt();

		/* setup */
		CreationCommand<Message> command = lifeline1().insertMessageAfter(lifeline1(), 25, lifeline2(),
				MessageSort.ASYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(execution1Top + 25, lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(lifelineBottom + 25, message1().getSend().get().getTop().getAsInt());
		assertEquals(lifelineBottom + 25, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void paddingNudge_recv() {
		/* prepare */
		execute(lifeline2().insertExecutionAfter(lifeline2(), 30, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));
		int lifelineBottom = lifeline2().getBottom().getAsInt();
		int execution1Top = lifeline2().getExecutions().get(0).getTop().getAsInt();

		/* setup */
		CreationCommand<Message> command = lifeline1().insertMessageAfter(lifeline1(), 25, lifeline2(),
				MessageSort.ASYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(execution1Top + 25, lifeline2().getExecutions().get(0).getTop().getAsInt());
		assertEquals(lifelineBottom + 25, message1().getSend().get().getTop().getAsInt());
		assertEquals(lifelineBottom + 25, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void connectToExecutionStart() {
		/* prepare */
		execute(lifeline1().insertExecutionAfter(lifeline1(), 30, 50,
				UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));
		int execution1Top = lifeline1().getExecutions().get(0).getTop().getAsInt();
		int execution1Bottom = lifeline1().getExecutions().get(0).getBottom().getAsInt();

		/* setup. Note that a message can only be received, not sent, at an execution start */
		CreationCommand<Message> command = lifeline2().insertMessageAfter(lifeline2(),
				/* calculate offset of the top of the execution from the lifeline2 head */
				execution1Top - lifelineBodyTop(lifeline2()), lifeline1(), MessageSort.ASYNCH_CALL_LITERAL,
				null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(execution1Top, lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(execution1Bottom, lifeline1().getExecutions().get(0).getBottom().getAsInt());

		assertEquals(execution1Top, message1().getSend().get().getTop().getAsInt());
		assertEquals(execution1Top, message1().getReceive().get().getTop().getAsInt());
	}

	@Test
	public void paddingBetweenExecutions_insertAtBottom() {
		/* prepare */
		execute(lifeline1().insertExecutionAfter(lifeline1(), //
				30, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));
		execute(lifeline1().insertExecutionAfter(lifeline1().getExecutions().get(0), //
				10, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));

		int execution1Top = lifeline1().getExecutions().get(0).getTop().getAsInt();
		int execution1Bottom = lifeline1().getExecutions().get(0).getBottom().getAsInt();
		int execution2Top = lifeline1().getExecutions().get(1).getTop().getAsInt();
		int execution2Bottom = lifeline1().getExecutions().get(1).getBottom().getAsInt();

		/* setup */
		CreationCommand<Message> command = lifeline1().insertMessageAfter(
				lifeline1().getExecutions().get(0).getFinish().get(), //
				0, lifeline2(), MessageSort.ASYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		assertEquals(execution1Bottom, message1().getSend().get().getTop().getAsInt());
		assertEquals(execution1Bottom, message1().getReceive().get().getTop().getAsInt());

		assertEquals(execution1Top, lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(execution1Bottom, lifeline1().getExecutions().get(0).getBottom().getAsInt());

		assertEquals(execution2Top, lifeline1().getExecutions().get(1).getTop().getAsInt());
		assertEquals(execution2Bottom, lifeline1().getExecutions().get(1).getBottom().getAsInt());
	}

	@Test
	public void increasePaddingBetweenExecutions_insertAtTop() {
		/* prepare */
		execute(lifeline1().insertExecutionAfter(lifeline1(), //
				30, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));
		execute(lifeline1().insertExecutionAfter(lifeline1().getExecutions().get(0), //
				10, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));

		int execution1Top = lifeline1().getExecutions().get(0).getTop().getAsInt();
		int execution1Bottom = lifeline1().getExecutions().get(0).getBottom().getAsInt();
		int execution2Top = lifeline1().getExecutions().get(1).getTop().getAsInt();
		int execution2Bottom = lifeline1().getExecutions().get(1).getBottom().getAsInt();

		/* setup. Note that a message can only be received, not sent, at an execution start */
		CreationCommand<Message> command = lifeline2().insertMessageAfter(lifeline2(), //
				/* calculate offset of the top of the execution from the lifeline2 head */
				execution2Top - lifelineBodyTop(lifeline2()), //
				lifeline1(), MessageSort.ASYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		assertEquals(execution1Top, lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(execution1Bottom, lifeline1().getExecutions().get(0).getBottom().getAsInt());

		assertEquals(execution2Top + 15, message1().getSend().get().getTop().getAsInt());
		assertEquals(execution2Top + 15, message1().getReceive().get().getTop().getAsInt());

		assertEquals(execution2Top + 15, lifeline1().getExecutions().get(1).getTop().getAsInt());
		assertEquals(execution2Bottom + 15, lifeline1().getExecutions().get(1).getBottom().getAsInt());
	}

	@Test
	public void nudgeAsyncSlopedMessageStartOnOwnReceiveEnd() {
		/* prepare */
		int lifelineTop = model.getLifelineBodyTop(lifeline1());
		execute(lifeline2().insertMessageAfter(lifeline2(), 25, //
				lifeline1(), lifeline1(), 50, //
				MessageSort.ASYNCH_CALL_LITERAL, null));

		int msg1Send = message1().getSend().get().getTop().getAsInt();
		int msg1Recv = message1().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<Message> command = lifeline1().insertMessageAfter(lifeline1(), 10, lifeline2(),
				MessageSort.ASYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(lifelineTop + 25, message2().getSend().get().getTop().getAsInt());
		assertEquals(lifelineTop + 25, message2().getReceive().get().getTop().getAsInt());

		assertEquals(msg1Recv + 25, message1().getReceive().get().getTop().getAsInt());
		assertEquals(msg1Send + 25, message1().getSend().get().getTop().getAsInt());
	}

	@Test
	public void connectToExecutionWithAttachedMessage() {
		/* prepare */
		execute(lifeline1().insertExecutionAfter(lifeline1(), //
				10, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION));
		execute(lifeline1().insertMessageAfter(lifeline1().getExecutions().get(0).getFinish().get(), 0,
				lifeline2(), MessageSort.SYNCH_CALL_LITERAL, null));
		execute(lifeline2().insertMessageAfter(message1().getReceive().get(), 30, lifeline1(),
				MessageSort.ASYNCH_CALL_LITERAL, null));

		int executionTop = lifeline1().getExecutions().get(0).getTop().getAsInt();
		int executionBottom = lifeline1().getExecutions().get(0).getBottom().getAsInt();

		int msg1send = message1().getSend().get().getTop().getAsInt();
		int msg1recv = message1().getReceive().get().getTop().getAsInt();

		int msg2send = message2().getSend().get().getTop().getAsInt();
		int msg2recv = message2().getReceive().get().getTop().getAsInt();

		/* setup */
		CreationCommand<Message> command = lifeline2().insertMessageAfter(
				lifeline1().getExecutions().get(0).getStart().get(), 0, lifeline1(),
				MessageSort.SYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert */
		assertEquals(50, lifeline1().getExecutions().get(0).getBottom().getAsInt()
				- lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(executionTop + 15, lifeline1().getExecutions().get(0).getTop().getAsInt());
		assertEquals(executionBottom + 15, lifeline1().getExecutions().get(0).getBottom().getAsInt());

		assertEquals(msg1send + 15, message1().getSend().get().getTop().getAsInt());
		assertEquals(msg1recv + 15, message1().getReceive().get().getTop().getAsInt());

		assertEquals(msg2send + 15, message2().getSend().get().getTop().getAsInt());
		assertEquals(msg2recv + 15, message2().getReceive().get().getTop().getAsInt());

	}

}
