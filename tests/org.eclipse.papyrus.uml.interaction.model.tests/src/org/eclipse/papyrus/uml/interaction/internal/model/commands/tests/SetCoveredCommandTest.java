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

package org.eclipse.papyrus.uml.interaction.internal.model.commands.tests;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.SetCoveredCommand;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.junit.Rule;
import org.junit.Test;

/**
 * Unit tests for the {@link SetCoveredCommand} class.
 */
@SuppressWarnings("nls")
@ModelResource({"message-signatures.uml", "message-signatures.notation" })
public class SetCoveredCommandTest {

	private static final String QN = "message-signatures::Scenario::interaction::%s";

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	@Test
	public void operationCallReceive() {
		MMessage mMessage = model.getElement(QN, "request");
		MLifeline c = model.getElement(QN, "c");

		Message message = mMessage.getElement();
		assumeThat("Message has no signature", message.getSignature(), notNullValue());
		assumeThat("Message has no arguments", message.getArguments(), hasItem(anything()));

		MMessageEnd receive = mMessage.getReceive().get();
		assumeThat("Unexpected message end coverage", receive.getCovered(), not(Optional.of(c)));

		Command setCovered = receive.setCovered(c, OptionalInt.empty());
		model.execute(setCovered);

		assertThat("Message has incompatible signature", message.getSignature(), nullValue());
		assertThat("Message has incompatible arguments", message.getArguments(), not(hasItem(anything())));
	}

}
