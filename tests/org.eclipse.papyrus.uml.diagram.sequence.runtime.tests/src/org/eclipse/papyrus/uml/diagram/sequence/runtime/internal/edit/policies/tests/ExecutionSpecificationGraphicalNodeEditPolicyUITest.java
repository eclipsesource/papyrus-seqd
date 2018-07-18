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
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ExecutionSpecificationGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Integration test cases for the
 * {@link ExecutionSpecificationGraphicalNodeEditPolicy} class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("one-exec.di")
@Maximized
@RunWith(Parameterized.class)
public class ExecutionSpecificationGraphicalNodeEditPolicyUITest extends AbstractGraphicalEditPolicyUITest {
	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	private static final boolean SENDER = true; // source = true
	private static final boolean RECEIVER = !SENDER;

	private final int sendX;

	private final int recvX;

	/**
	 * Initializes me.
	 */
	public ExecutionSpecificationGraphicalNodeEditPolicyUITest(boolean rightToLeft, String direction) {
		super();

		if (rightToLeft) {
			sendX = LIFELINE_2_BODY_X;
			recvX = LIFELINE_1_BODY_X;
		} else {
			sendX = LIFELINE_1_BODY_X;
			recvX = LIFELINE_2_BODY_X;
		}
	}

	@Test
	public void createAsyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 185),
				at(recvX, 185));

		assertThat(messageEP, runs(x(SENDER), 185, x(RECEIVER), 185, 2));
	}

	@Test
	public void createSyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sendX, 185),
				at(recvX, 185));

		assertThat(messageEP, runs(x(SENDER), 185, x(RECEIVER), 185, 2));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] { //
				{ false, "left-to-right" }, //
				{ true, "right-to-left" }, //
		});
	}

	/**
	 * Compute an adjusted {@code x} coördinate based the test message's
	 * directionality and whether that end is at an execution specification or a
	 * lifeline stem.
	 *
	 * @param source
	 *            whether this is the source end of the message
	 *
	 * @return the adjusted X coördinate
	 */
	int x(boolean source) {
		if (sendX > recvX) {
			// Right-to-left message
			if (source) {
				// Edge of the execution specification
				return sendX - 5;
			} else {
				return recvX;
			}
		} else {
			// Left-to-right message
			if (source) {
				return sendX;
			} else {
				// Edge of the execution specification
				return recvX - 5;
			}
		}
	}
}
