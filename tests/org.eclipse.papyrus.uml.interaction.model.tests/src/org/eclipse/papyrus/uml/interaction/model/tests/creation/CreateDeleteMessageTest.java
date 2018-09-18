/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.interaction.model.tests.creation;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;

/**
 * Regression tests for creation of delete messages.
 */
@ModelResource({"SEQD2.uml", "SEQD2.notation" })
public class CreateDeleteMessageTest {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	/**
	 * Initializes me.
	 */
	public CreateDeleteMessageTest() {
		super();
	}

	/**
	 * Regression test for Issue #354, illegal cycle in the dependency graph when creating a delete message.
	 */
	@Test
	public void createDeleteMessage() {
		MLifeline ll1 = model.getMInteraction().getLifelines().get(0);
		MLifeline ll2 = model.getMInteraction().getLifelines().get(1);
		MMessage msg3 = model.getMInteraction().getMessages().get(2);

		CreationCommand<Message> command = ll1.insertMessageAfter(msg3.getSend().get(), 30, ll2,
				MessageSort.DELETE_MESSAGE_LITERAL, null);
		model.execute(command);
		MMessage delete;
		try {
			delete = model.getMMessage(command.get()).orElse(null);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
			delete = null; // Unreachable
		}

		assertThat("No delete message", delete, notNullValue());
		assertThat("Not a delete message", delete.getElement().getMessageSort(),
				is(MessageSort.DELETE_MESSAGE_LITERAL));
		model.execute(SetCommand.create(model.getEditingDomain(), delete.getElement().getReceiveEvent(),
				UMLPackage.Literals.NAMED_ELEMENT__NAME, "destroy"));

		// Verify the Lifeline2 dependency graph
		model.verifyGraph().verifyDependencies(from -> {
			switch (from) {
				case "Lifeline2":
					return asList("b", "c", "d", "e", "f", "destroy");
				case "destroy":
					// This is the cycle that we don't want
					return asList("!Lifeline2");
				default:
					return null;
			}
		}).print("Delete Message Dependencies");
	}

}
