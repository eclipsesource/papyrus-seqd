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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.is;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.function.IntPredicate;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageEndpointEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.LightweightSeqDPrefs;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Integration test cases for the {@link MessageEndpointEditPolicy} class's message move behaviour by
 * drag-and-drop.
 *
 * @author Christian W. Damus
 */
@ModelResource("two-lifelines.di")
@Maximized
@RunWith(Parameterized.class)
public class MessageMoveUITest extends AbstractGraphicalEditPolicyUITest {

	@ClassRule
	public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs()
			.dontCreateExecutionsForSyncMessages();

	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	private static final int INITIAL_Y = 200;

	private final int sendX;

	private final int recvX;

	private final boolean moveDown;

	/**
	 * Initializes me.
	 * 
	 * @param rightToLeft
	 *            whether to draw messages from right to left (otherwise, from left to right)
	 * @param direction
	 *            texual indication of the {@code rightToLeft} parameter
	 * @param moveDown
	 *            whether to move messages down (otherwise, up)
	 * @param whichWay
	 *            textual indication of the {@code moveDown}
	 */
	public MessageMoveUITest(boolean rightToLeft, String direction, boolean moveDown, String whichWay) {

		super();

		if (rightToLeft) {
			sendX = LIFELINE_2_BODY_X;
			recvX = LIFELINE_1_BODY_X;
		} else {
			sendX = LIFELINE_1_BODY_X;
			recvX = LIFELINE_2_BODY_X;
		}

		this.moveDown = moveDown;
	}

	@Test
	public void moveAsyncMessage() {
		final int slopeY = INITIAL_Y + 80;

		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, INITIAL_Y),
				at(recvX, slopeY)); // always sloping down, of course

		assumeThat(messageEP, runs(sendX, INITIAL_Y, recvX, slopeY));

		int delta = moveDown ? 30 : -30;
		int x = sendX + (recvX - sendX) / 4;
		int grabY = INITIAL_Y + (slopeY - INITIAL_Y) / 4;
		int y = grabY + delta;

		editor.moveSelection(at(x, grabY), at(x, y));

		assertThat(messageEP, runs(sendX, INITIAL_Y + delta, recvX, slopeY + delta));
	}

	@Test
	public void moveSyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sendX, INITIAL_Y),
				at(recvX, INITIAL_Y));

		assumeThat(messageEP, runs(sendX, INITIAL_Y, recvX, INITIAL_Y));

		int delta = moveDown ? 30 : -30;
		int x = (sendX + recvX) / 2;
		int y = INITIAL_Y + delta;

		editor.moveSelection(at(x, INITIAL_Y), at(x, y));

		assertThat(messageEP, runs(sendX, y, recvX, y));
	}

	/**
	 * Per <a href="https://github.com/eclipsesource/papyrus-seqd/issues/26">Issue #26</a>, attempt to cross
	 * over another message and verify that it's just bumped out of the way.
	 */
	@Test
	public void attemptToMoveAsyncMessageAcrossAnother() {
		final int slopeY = INITIAL_Y + 80;
		final int otherY = moveDown ? INITIAL_Y + 80 + 30 : INITIAL_Y - 30;

		EditPart otherEP = null;

		// Be sure to create messages in top-down order to avoid nudging and
		// far enough apart to avoid padding
		if (!moveDown) {
			otherEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sendX, otherY),
					at(recvX, otherY));
		}

		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, INITIAL_Y),
				at(recvX, slopeY)); // always sloping down, of course
		assumeThat(messageEP, runs(sendX, INITIAL_Y, recvX, slopeY));

		// Be sure to create messages in top-down order to avoid nudging and
		// far enough apart to avoid padding
		if (moveDown) {
			otherEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sendX, otherY),
					at(recvX, otherY));
		}

		int delta = moveDown ? 60 : -60;
		int x = (sendX + recvX) / 2;
		int grabY = (INITIAL_Y + slopeY) / 2;
		int y = grabY + delta;

		editor.moveSelection(at(x, grabY), at(x, y));

		assertThat("Message should have moved", messageEP,
				runs(sendX, INITIAL_Y + delta, recvX, slopeY + delta));

		// Where do we expect the other message to have ended up?
		IntPredicate relationship = moveDown //
				? val -> val > slopeY + delta //
				: val -> val < INITIAL_Y + delta;

		PointList otherPoints = getPoints(otherEP);
		assertThat("Other message no longer horizontal", otherPoints.getLastPoint().y(),
				is(otherPoints.getFirstPoint().y()));
		assertTrue("Other message not bumped out of the way",
				relationship.test(otherPoints.getLastPoint().y()));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1} {3}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] { //
				{false, "left-to-right", false, "up" }, //
				{false, "left-to-right", true, "down" }, //
				{true, "right-to-left", false, "up" }, //
				{true, "right-to-left", true, "down" }, //
		});
	}

}
