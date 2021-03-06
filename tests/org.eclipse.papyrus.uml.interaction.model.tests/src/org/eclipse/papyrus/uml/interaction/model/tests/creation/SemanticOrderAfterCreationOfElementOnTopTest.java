package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static org.eclipse.uml2.uml.UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION;
import static org.hamcrest.CoreMatchers.everyItem;
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
		MMessageEnd msg1send = model.getElement(MMessageEnd.class, QN, "1s");
		MMessageEnd msg1receive = model.getElement(MMessageEnd.class, QN, "1r");
		MMessageEnd msg2send = model.getElement(MMessageEnd.class, QN, "2s");
		MMessageEnd msg2receive = model.getElement(MMessageEnd.class, QN, "2r");

		List<MMessageEnd> otherEnds = Arrays.asList(msg1send, msg1receive, msg2send, msg2receive);
		assertThat(otherEnds, everyItem(model.isSemanticallyAfter(createdSend)));
		assertThat(otherEnds, everyItem(model.isSemanticallyAfter(createdReceive)));
	}

	@Test
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
		MMessageEnd msg1send = model.getElement(MMessageEnd.class, QN, "1s");
		MMessageEnd msg1receive = model.getElement(MMessageEnd.class, QN, "1r");
		MMessageEnd msg2send = model.getElement(MMessageEnd.class, QN, "2s");
		MMessageEnd msg2receive = model.getElement(MMessageEnd.class, QN, "2r");

		List<MMessageEnd> endsBefore = Arrays.asList(msg1send, msg1receive);
		assertThat(endsBefore, everyItem(model.isSemanticallyBefore(createdSend)));
		assertThat(endsBefore, everyItem(model.isSemanticallyBefore(createdReceive)));

		List<MMessageEnd> endsAfter = Arrays.asList(msg2send, msg2receive);
		assertThat(endsAfter, everyItem(model.isSemanticallyAfter(createdSend)));
		assertThat(endsAfter, everyItem(model.isSemanticallyAfter(createdReceive)));
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
		MMessageEnd msg1send = model.getElement(MMessageEnd.class, QN, "1s");
		MMessageEnd msg1receive = model.getElement(MMessageEnd.class, QN, "1r");
		MMessageEnd msg2send = model.getElement(MMessageEnd.class, QN, "2s");
		MMessageEnd msg2receive = model.getElement(MMessageEnd.class, QN, "2r");

		List<MMessageEnd> otherEnds = Arrays.asList(msg1send, msg1receive, msg2send, msg2receive);
		assertThat(otherEnds, everyItem(model.isSemanticallyAfter(createdStart)));
		assertThat(otherEnds, everyItem(model.isSemanticallyAfter(createdFinish)));
	}

	@Test
	public void addMessageAndExecutionOnTopOfLifeline3() {
		/* first command to create message */
		/* setup */
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		MLifeline lifeline3 = interaction().getLifelines().get(2);
		CreationCommand<Message> command = lifeline2.insertMessageAfter(lifeline2, 20, lifeline3,
				MessageSort.SYNCH_CALL_LITERAL, null);

		/* act */
		execute(command);

		/*
		 * second command to create execution. The first command invalidated the logical model, so we need to
		 * find lifeline3 again
		 */
		lifeline3 = interaction().getLifelines().get(2);
		CreationCommand<ExecutionSpecification> command2 = lifeline3.insertExecutionAfter(lifeline3, 0, 20,
				ACTION_EXECUTION_SPECIFICATION);

		/* act */
		execute(command2);

		/* assert: message ends of added message are sorted in before existing messages */
		MMessage createdMessage = model.getMMessage(command.get()).get();
		MMessageEnd createdSend = createdMessage.getSend().get();
		MMessageEnd createdReceive = createdMessage.getReceive().get();

		MMessageEnd msg1send = model.getElement(MMessageEnd.class, QN, "1s");
		MMessageEnd msg1receive = model.getElement(MMessageEnd.class, QN, "1r");
		MMessageEnd msg2send = model.getElement(MMessageEnd.class, QN, "2s");
		MMessageEnd msg2receive = model.getElement(MMessageEnd.class, QN, "2r");

		List<MMessageEnd> otherEnds = Arrays.asList(msg1send, msg1receive, msg2send, msg2receive);
		assertThat(otherEnds, everyItem(model.isSemanticallyAfter(createdSend)));
		assertThat(otherEnds, everyItem(model.isSemanticallyAfter(createdReceive)));

		/*
		 * assert: message ends of added message and all other messages are sorted in after created execution
		 */
		MExecution createdExecution = model.getMExecution(command2.get()).get();
		MOccurrence<?> createdStart = createdExecution.getStart().get();
		MOccurrence<?> createdFinish = createdExecution.getFinish().get();

		List<MMessageEnd> createdEnds = Arrays.asList(createdSend, createdReceive);
		assertThat(createdEnds, everyItem(model.isSemanticallyAfter(createdStart)));
		assertThat(createdEnds, everyItem(model.isSemanticallyAfter(createdFinish)));
		assertThat(otherEnds, everyItem(model.isSemanticallyAfter(createdStart)));
		assertThat(otherEnds, everyItem(model.isSemanticallyAfter(createdFinish)));
	}

}
