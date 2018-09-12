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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.MessageSort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Integration test cases for the creation of self messages.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("one-exec.di")
@Maximized
@RunWith(Parameterized.class)
public class SelfMessageCreationTest extends AbstractGraphicalEditPolicyUITest {
	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	private final boolean onExec;
	private final MessageSort messageSort;
	private final int messageX;

	/**
	 * Initializes me.
	 */
	public SelfMessageCreationTest(MessageSort sort, boolean onExec) {
		super();

		messageSort = sort;
		this.onExec = onExec;
		messageX = onExec ? LIFELINE_2_BODY_X : LIFELINE_1_BODY_X;
	}

	@Test
	public void createSelfMessage() {
		IElementType type = SequenceElementTypes.getMessageType(messageSort);

		EditPart messageEP;

		try {
			messageEP = createConnection(type, at(messageX, 185), at(messageX, 185));
		} catch (AssertionError e) {
			// Expected for create messages
			messageEP = null;
		}

		switch (messageSort) {
		case CREATE_MESSAGE_LITERAL:
			assertThat("Self-creation was permitted", messageEP, nullValue());
			break;
		case DELETE_MESSAGE_LITERAL:
			// These have a slightly different shape, owing to connection to
			// the destruction occurrence specification
			assertThat(messageEP, runs(LIFELINE_1_BODY_X, 185, //
					// Account for the attachment to the side of the destruction occurrence
					// specification and extra space made for the destruction
					LIFELINE_1_BODY_X + 10, 210, //
					2));
			break;
		default:
			assertThat(messageEP, runs(x(onExec), 185, x(onExec), 205, 2));
			break;
		}
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}, execution={1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] { //
				{ MessageSort.SYNCH_CALL_LITERAL, false }, //
				{ MessageSort.SYNCH_CALL_LITERAL, true }, //
				{ MessageSort.ASYNCH_CALL_LITERAL, false }, //
				{ MessageSort.ASYNCH_CALL_LITERAL, true }, //
				{ MessageSort.REPLY_LITERAL, false }, //
				{ MessageSort.REPLY_LITERAL, true }, //
				{ MessageSort.CREATE_MESSAGE_LITERAL, false }, //
				{ MessageSort.DELETE_MESSAGE_LITERAL, false }, //
		});
	}

	/**
	 * Compute an adjusted {@code x} coördinate based whether the message is on an
	 * execution specification or on the lifeline stem.
	 *
	 * @param onExec
	 *            whether the message is on an execution specification
	 *
	 * @return the adjusted X coördinate
	 */
	int x(boolean onExec) {
		return messageX + (onExec ? 5 : 0);
	}
}
