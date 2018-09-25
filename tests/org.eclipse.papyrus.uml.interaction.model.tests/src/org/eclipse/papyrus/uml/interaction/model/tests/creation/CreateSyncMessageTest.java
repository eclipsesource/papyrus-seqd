/*****************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static org.eclipse.uml2.uml.UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION;
import static org.eclipse.uml2.uml.UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.instanceOf;
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
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("boxing")
@ModelResource({"CreateSyncMessageTesting.uml", "CreateSyncMessageTesting.notation" })
public class CreateSyncMessageTest {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private MInteraction interaction;

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
	public void creationSyncMessageWithExecutionButWithoutReplyMessage() {
		/* setup */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		CreationCommand<Message> command = lifeline1.insertMessageAfter(lifeline1, 20, lifeline2,
				MessageSort.SYNCH_CALL_LITERAL, null, false, BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert: created message and execution with own finish */
		assertThat(interaction().getMessages().size(), is(1));
		lifeline1 = interaction().getLifelines().get(0);
		lifeline2 = interaction().getLifelines().get(1);
		assertThat(lifeline2.getExecutions().size(), is(1));

		MMessage message = interaction().getMessages().get(0);
		MExecution execution = interaction().getLifelines().get(1).getExecutions().get(0);

		assertThat(lifeline1.getElement().getCoveredBys().size(), is(1));
		assertThat(lifeline1.getElement().getCoveredBys().get(0), is(message.getSend().get().getElement()));
		assertThat(message.getReceive().get(), is(execution.getStart().get()));
		assertThat(execution.getFinish().get().getElement(),
				instanceOf(ExecutionOccurrenceSpecification.class));

		/* assert: message receive and execution have same top */
		assertThat(message.getTop().getAsInt(), is(execution.getTop().getAsInt()));
	}

	@Test
	public void creationSyncMessageWithExecutionAndWithReplyMessage() {
		/* setup */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		CreationCommand<Message> command = lifeline1.insertMessageAfter(lifeline1, 20, lifeline2,
				MessageSort.SYNCH_CALL_LITERAL, null, true, BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command);

		/* assert: created message, execution, and reply message */
		assertThat(interaction().getMessages().size(), is(2));
		lifeline1 = interaction().getLifelines().get(0);
		lifeline2 = interaction().getLifelines().get(1);
		assertThat(lifeline2.getExecutions().size(), is(1));

		MMessage message = interaction().getMessages().get(0);
		MMessage replyMessage = interaction().getMessages().get(1);
		MExecution execution = interaction().getLifelines().get(1).getExecutions().get(0);

		assertThat(lifeline1.getElement().getCoveredBys().size(), is(2));
		assertThat(lifeline1.getElement().getCoveredBys().get(0), is(message.getSend().get().getElement()));
		assertThat(lifeline1.getElement().getCoveredBys().get(1),
				is(replyMessage.getReceive().get().getElement()));
		assertThat(message.getReceive().get(), is(execution.getStart().get()));
		assertThat(replyMessage.getElement().getMessageSort(), is(MessageSort.REPLY_LITERAL));
		assertThat(replyMessage.getSend(), is(execution.getFinish()));
		assertThat(lifeline2.getElement().getCoveredBys().size(), is(3));
		assertThat(lifeline2.getElement().getCoveredBys().get(0),
				is(message.getReceive().get().getElement()));
		assertThat(lifeline2.getElement().getCoveredBys().get(1),
				instanceOf(BehaviorExecutionSpecification.class));
		assertThat(lifeline2.getElement().getCoveredBys().get(2),
				is(replyMessage.getSend().get().getElement()));

		/* assert: message and execution have same top */
		assertThat(message.getTop().getAsInt(), is(execution.getTop().getAsInt()));

		/* assert: reply message top is execution bottom */
		assertThat(replyMessage.getTop().getAsInt(), is(execution.getBottom().getAsInt()));
	}

	@Test
	public void semanticOrderAfterCreatingTwoSyncMessageWithExecution() {
		/* setup */
		MLifeline lifeline1 = interaction().getLifelines().get(0);
		MLifeline lifeline2 = interaction().getLifelines().get(1);
		CreationCommand<Message> command1 = lifeline1.insertMessageAfter(lifeline1, 50, lifeline2,
				MessageSort.SYNCH_CALL_LITERAL, null, true, BEHAVIOR_EXECUTION_SPECIFICATION);

		/* act */
		execute(command1);

		/* add second message BEFORE first in the opposite direction */
		lifeline1 = interaction().getLifelines().get(0);
		lifeline2 = interaction().getLifelines().get(1);
		CreationCommand<Message> command2 = lifeline2.insertMessageAfter(lifeline2, 10, lifeline1,
				MessageSort.SYNCH_CALL_LITERAL, null, true, ACTION_EXECUTION_SPECIFICATION);

		/* act */
		execute(command2);

		lifeline1 = interaction().getLifelines().get(0);
		lifeline2 = interaction().getLifelines().get(1);

		Message message1 = command1.get();
		Message message2 = command2.get();
		MMessage mMessage1 = (MMessage)interaction().getElement(message1).get();
		MMessage mMessage2 = (MMessage)interaction().getElement(message2).get();

		MMessageEnd msg1Send = mMessage1.getSend().get();
		MMessageEnd msg1Receive = mMessage1.getReceive().get();
		MMessageEnd msg2Send = mMessage2.getSend().get();
		MMessageEnd msg2Receive = mMessage2.getReceive().get();

		MExecution executionOnLifeline1 = lifeline1.getExecutions().get(0);
		MOccurrence<?> replyMessageSendOnLifeline1 = executionOnLifeline1.getFinish().get();
		MMessage replyMessageOfExecutionOnLifeline1 = (MMessage)replyMessageSendOnLifeline1.getOwner();
		MOccurrence<?> replyMessageReceiveOnLifeline2 = replyMessageOfExecutionOnLifeline1.getReceive().get();

		MExecution executionOnLifeline2 = lifeline2.getExecutions().get(0);
		MOccurrence<?> replyMessageSendOnLifeline2 = executionOnLifeline2.getFinish().get();
		MMessage replyMessageOfExecutionOnLifeline2 = (MMessage)replyMessageSendOnLifeline2.getOwner();
		MOccurrence<?> replyMessageReceiveOnLifeline1 = replyMessageOfExecutionOnLifeline2.getReceive().get();

		/* assert the following order: */
		// message2Send
		// message2Receive
		// replyMessageSendOnLifeline1
		// replyMessageReceiveOnLifeline2
		// message1Send
		// message1Receive
		// replyMessageSendOnLifeline2
		// replyMessageReceiveOnLifeline1
		List<MOccurrence<? extends Element>> order = Arrays.asList(msg2Send, msg2Receive,
				replyMessageSendOnLifeline1, replyMessageReceiveOnLifeline2, msg1Send, msg1Receive,
				replyMessageSendOnLifeline2, replyMessageReceiveOnLifeline1);

		for (MOccurrence<? extends Element> element : order) {
			List<MOccurrence<? extends Element>> remaining = order.subList(order.indexOf(element) + 1,
					order.size());
			assertThat(remaining, everyItem(model.isSemanticallyAfter(element)));
		}
	}

}
