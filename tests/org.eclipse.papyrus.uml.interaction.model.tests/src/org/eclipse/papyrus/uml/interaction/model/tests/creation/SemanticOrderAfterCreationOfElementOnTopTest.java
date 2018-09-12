package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static org.eclipse.uml2.uml.UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("nls")
@ModelResource({"SemanticOrderAfterCreationTest.uml", "SemanticOrderAfterCreationTest.notation" })
public class SemanticOrderAfterCreationOfElementOnTopTest {

	private static final String QN = "343-test::Interaction::%s";

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
	@SuppressWarnings({"boxing" })
	public void addMessageOnTopFromLifeline2ToLifeline3() {
		/* setup */
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		MLifeline lifeline3 = interaction().getLifelines().get(2);
		CreationCommand<Message> command = lifeline2.insertMessageAfter(lifeline2, 10, lifeline3,
				MessageSort.SYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert: message ends of added message are sorted in before existing messages */
		MMessage createdMessage = model.getMMessage(command.get()).get();
		MMessageEnd createdSend = createdMessage.getSend().get();
		MMessageEnd createdReceive = createdMessage.getReceive().get();
		MMessageEnd msg1send = model.getElement(QN, "1s", MMessageEnd.class);
		MMessageEnd msg1receive = model.getElement(QN, "1r", MMessageEnd.class);
		MMessageEnd msg2send = model.getElement(QN, "2s", MMessageEnd.class);
		MMessageEnd msg2receive = model.getElement(QN, "2r", MMessageEnd.class);

		List<MMessageEnd> otherEnds = Arrays.asList(msg1send, msg1receive, msg2send, msg2receive);
		otherEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdSend, other), is(true)));
		otherEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdReceive, other), is(true)));
	}

	@Test
	@SuppressWarnings({"boxing" })
	public void addMessageAfterFirstExistingMessageFromLifeline2ToLifeline3() {
		/* setup */
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		MLifeline lifeline3 = interaction().getLifelines().get(2);
		CreationCommand<Message> command = lifeline2.insertMessageAfter(interaction.getMessages().get(0), 10,
				lifeline3, MessageSort.SYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* assert: message ends of added message are sorted in before existing messages */
		MMessage createdMessage = model.getMMessage(command.get()).get();
		MMessageEnd createdSend = createdMessage.getSend().get();
		MMessageEnd createdReceive = createdMessage.getReceive().get();
		MMessageEnd msg1send = model.getElement(QN, "1s", MMessageEnd.class);
		MMessageEnd msg1receive = model.getElement(QN, "1r", MMessageEnd.class);
		MMessageEnd msg2send = model.getElement(QN, "2s", MMessageEnd.class);
		MMessageEnd msg2receive = model.getElement(QN, "2r", MMessageEnd.class);

		List<MMessageEnd> endsBefore = Arrays.asList(msg1send, msg1receive);
		endsBefore.forEach(end -> assertThat(model.isSemanticallyBefore(end, createdSend), is(true)));
		endsBefore.forEach(end -> assertThat(model.isSemanticallyBefore(end, createdReceive), is(true)));

		List<MMessageEnd> endsAfter = Arrays.asList(msg2send, msg2receive);
		endsAfter.forEach(end -> assertThat(model.isSemanticallyBefore(createdSend, end), is(true)));
		endsAfter.forEach(end -> assertThat(model.isSemanticallyBefore(createdReceive, end), is(true)));
	}

	@Test
	public void addExecutionOnTopOfLifeline1() {
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		addAndAssertExecutionOnTopOfLifeline(lifeline1);
	}

	@Test
	public void addExecutionOnTopOfLifeline2() {
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		addAndAssertExecutionOnTopOfLifeline(lifeline2);
	}

	@Test
	public void addExecutionOnTopOfLifeline3() {
		MLifeline lifeline3 = interaction().getLifelines().get(2);
		addAndAssertExecutionOnTopOfLifeline(lifeline3);
	}

	@SuppressWarnings("boxing")
	private void addAndAssertExecutionOnTopOfLifeline(MLifeline lifeline) {
		/* setup */
		CreationCommand<ExecutionSpecification> command = lifeline.insertExecutionAfter(lifeline, 0, 20,
				ACTION_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert: message ends of added message are sorted in before existing messages */
		MExecution createdExecution = model.getMExecution(command.get()).get();
		MOccurrence<?> createdStart = createdExecution.getStart().get();
		MOccurrence<?> createdFinish = createdExecution.getFinish().get();
		MMessageEnd msg1send = model.getElement(QN, "1s", MMessageEnd.class);
		MMessageEnd msg1receive = model.getElement(QN, "1r", MMessageEnd.class);
		MMessageEnd msg2send = model.getElement(QN, "2s", MMessageEnd.class);
		MMessageEnd msg2receive = model.getElement(QN, "2r", MMessageEnd.class);

		List<MMessageEnd> otherEnds = Arrays.asList(msg1send, msg1receive, msg2send, msg2receive);
		otherEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdStart, other), is(true)));
		otherEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdFinish, other), is(true)));
	}

	@Test
	@SuppressWarnings({"boxing" })
	public void addMessageAndExecutionOnTopOfLifeline3() {
		/* first command to create message */
		/* setup */
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		MLifeline lifeline3 = interaction().getLifelines().get(2);
		CreationCommand<Message> command = lifeline2.insertMessageAfter(lifeline2, 20, lifeline3,
				MessageSort.SYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/* second command to create execution */
		/* setup */
		CreationCommand<ExecutionSpecification> command2 = lifeline3.insertExecutionAfter(lifeline3, 0, 20,
				ACTION_EXECUTION_SPECIFICATION);

		/* act */
		execute(command2);

		/* assert: message ends of added message are sorted in before existing messages */
		MMessage createdMessage = model.getMMessage(command.get()).get();
		MMessageEnd createdSend = createdMessage.getSend().get();
		MMessageEnd createdReceive = createdMessage.getReceive().get();

		MMessageEnd msg1send = model.getElement(QN, "1s", MMessageEnd.class);
		MMessageEnd msg1receive = model.getElement(QN, "1r", MMessageEnd.class);
		MMessageEnd msg2send = model.getElement(QN, "2s", MMessageEnd.class);
		MMessageEnd msg2receive = model.getElement(QN, "2r", MMessageEnd.class);

		List<MMessageEnd> otherEnds = Arrays.asList(msg1send, msg1receive, msg2send, msg2receive);
		otherEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdSend, other), is(true)));
		otherEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdReceive, other), is(true)));

		/*
		 * assert: message ends of added message and all other messages are sorted in after created execution
		 */
		MExecution createdExecution = model.getMExecution(command2.get()).get();
		MOccurrence<?> createdStart = createdExecution.getStart().get();
		MOccurrence<?> createdFinish = createdExecution.getFinish().get();

		List<MMessageEnd> createdEnds = Arrays.asList(createdSend, createdReceive);
		createdEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdStart, other), is(true)));
		createdEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdFinish, other), is(true)));
		otherEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdStart, other), is(true)));
		otherEnds.forEach(other -> assertThat(model.isSemanticallyBefore(createdFinish, other), is(true)));
	}

}
