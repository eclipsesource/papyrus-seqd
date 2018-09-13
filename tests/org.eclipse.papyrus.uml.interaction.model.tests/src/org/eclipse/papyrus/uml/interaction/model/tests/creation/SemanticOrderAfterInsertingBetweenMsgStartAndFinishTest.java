package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("nls")
@ModelResource({"SemanticOrderAfterCreationTest.uml", "SemanticOrderAfterCreationTest.notation" })
public class SemanticOrderAfterInsertingBetweenMsgStartAndFinishTest {

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
	public void insertExecAfterSlopedMesgStart() {
		/* prepare: add sloped message */
		MMessage message2 = interaction().getMessages().get(1);
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		MLifeline lifeline3 = interaction().getLifelines().get(2);

		execute(lifeline2.insertMessageAfter(message2.getSend().get(), 30, //
				lifeline3, lifeline3, 400, //
				MessageSort.ASYNCH_CALL_LITERAL, null));

		/* setup */
		MMessage message3 = interaction().getMessages().get(2);
		CreationCommand<ExecutionSpecification> command = lifeline2.insertExecutionAfter(
				message3.getSend().get(), 30, 50, UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert */
		MMessageEnd msg1send = model.getElement(MMessageEnd.class, QN, "1s");
		MMessageEnd msg1receive = model.getElement(MMessageEnd.class, QN, "1r");
		MMessageEnd msg2send = model.getElement(MMessageEnd.class, QN, "2s");
		MMessageEnd msg2receive = model.getElement(MMessageEnd.class, QN, "2r");
		MMessageEnd msg3send = interaction().getMessages().get(2).getSend().get();
		MOccurrence<?> execution1Start = interaction().getLifelines().get(1).getExecutions().get(0).getStart()
				.get();
		MExecution execution1 = interaction().getLifelines().get(1).getExecutions().get(0);
		MOccurrence<?> execution1Finish = interaction().getLifelines().get(1).getExecutions().get(0)
				.getFinish().get();
		MMessageEnd msg3receive = interaction().getMessages().get(2).getReceive().get();

		List<MElement<? extends Element>> expectedOrder = Arrays.asList(msg1send, msg1receive, msg2send,
				msg2receive, msg3send, execution1Start, execution1, execution1Finish, msg3receive);
		EList<InteractionFragment> actualOrder = model.getInteraction().getFragments();
		assertEquals(expectedOrder.size(), actualOrder.size());
		for (int i = 0; i < actualOrder.size(); i++) {
			InteractionFragment interactionFragment = actualOrder.get(i);
			Optional<MElement<? extends InteractionFragment>> element = interaction()
					.getElement(interactionFragment);
			assertTrue(element.isPresent());
			assertSame(expectedOrder.get(i), element.get());
		}

	}

}
