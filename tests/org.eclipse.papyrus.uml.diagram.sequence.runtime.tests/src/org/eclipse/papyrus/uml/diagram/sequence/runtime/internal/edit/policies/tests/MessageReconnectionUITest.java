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
import static org.junit.Assume.assumeThat;

import java.util.Arrays;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineBodyGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Integration test cases for the {@link LifelineBodyGraphicalNodeEditPolicy}
 * class's message re-connection behaviour.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("two-lifelines.di")
@Maximized
@RunWith(Parameterized.class)
public class MessageReconnectionUITest extends AbstractGraphicalEditPolicyUITest {
	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	private static final int INITIAL_Y = 145;

	private final int sendX;

	private final int recvX;

	private final boolean moveSource;

	private final boolean moveDown;

	/**
	 * Initializes me.
	 */
	public MessageReconnectionUITest(boolean rightToLeft, String direction, boolean moveSource,
			String whichEnd, boolean moveDown, String whichWay) {

		super();

		if (rightToLeft) {
			sendX = LIFELINE_2_BODY_X;
			recvX = LIFELINE_1_BODY_X;
		} else {
			sendX = LIFELINE_1_BODY_X;
			recvX = LIFELINE_2_BODY_X;
		}

		this.moveSource = moveSource;
		this.moveDown = moveDown;
	}

	@Test
	public void moveAsyncEnd() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, INITIAL_Y),
				at(recvX, INITIAL_Y));

		assumeThat(messageEP, runs(sendX, INITIAL_Y, recvX, INITIAL_Y));

		int delta = moveDown ? 30 : -30;
		int x = moveSource ? sendX : recvX;
		int y = INITIAL_Y + delta;

		int expectedSendY;
		int expectedRecvY;

		if (moveSource) {
			if (moveDown) {
				expectedSendY = y;
				expectedRecvY = y;
			} else {
				expectedSendY = y;
				expectedRecvY = INITIAL_Y;
			}
		} else {
			if (moveDown) {
				expectedSendY = INITIAL_Y;
				expectedRecvY = y;
			} else {
				expectedSendY = y;
				expectedRecvY = y;
			}
		}

		editor.moveSelection(at(x, INITIAL_Y), at(x, y));

		assertThat(messageEP, runs(sendX, expectedSendY, recvX, expectedRecvY));
	}

	@Test
	public void moveSyncEnd() {
		EditPart messageEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sendX, INITIAL_Y),
				at(recvX, INITIAL_Y));

		assumeThat(messageEP, runs(sendX, INITIAL_Y, recvX, INITIAL_Y));

		int delta = moveDown ? 30 : -30;
		int x = moveSource ? sendX : recvX;
		int y = INITIAL_Y + delta;

		editor.moveSelection(at(x, INITIAL_Y), at(x, y));

		assertThat(messageEP, runs(sendX, y, recvX, y));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}, {3}, {5}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] { //
				{ false, "left-to-right", false, "target", false, "up" }, //
				{ false, "left-to-right", false, "target", true, "down" }, //
				{ false, "left-to-right", true, "source", false, "up" }, //
				{ false, "left-to-right", true, "source", true, "down" }, //
				{ true, "right-to-left", false, "target", false, "up" }, //
				{ true, "right-to-left", false, "target", true, "down" }, //
				{ true, "right-to-left", true, "source", false, "up" }, //
				{ true, "right-to-left", true, "source", true, "down" }, //
		});
	}

}
