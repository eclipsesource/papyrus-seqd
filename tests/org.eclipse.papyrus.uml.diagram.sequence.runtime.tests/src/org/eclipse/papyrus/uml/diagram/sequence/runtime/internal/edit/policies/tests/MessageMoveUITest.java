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

import java.util.Arrays;
import java.util.function.IntPredicate;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.MessageEndpointEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.LightweightSeqDPrefs;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;
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
	public static final LightweightSeqDPrefs prefs = new LightweightSeqDPrefs()
			.dontCreateExecutionsForSyncMessages();

	@ClassRule
	public static final TestRule tolerance = GEFMatchers.defaultTolerance(1);

	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	private static final int INITIAL_Y = 200;

	// See DefaultLayoutConstriants ViewTypes.LIFELINE_HEADER
	private static final int LIFELINE_HEADER_WIDTH = 100;

	private final int sendX;

	private final int recvX;

	private final boolean moveDown;

	private boolean rightToLeft;

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

		this.rightToLeft = rightToLeft;
		this.moveDown = moveDown;
	}

	@Test
	public void moveAsyncMessage() {
		final int slopeY = INITIAL_Y + 80;

		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, INITIAL_Y),
				at(recvX, slopeY)); // always sloping down, of course

		// Get actual geometry
		int sX = getSource(messageEP).x();
		int rX = getTarget(messageEP).x();
		int sY = getSourceY(messageEP);
		int rY = getTargetY(messageEP);

		int delta = moveDown ? 30 : -30;
		int x = sX + (rX - sX) / 4;
		int grabY = sY + (rY - sY) / 4;
		int y = grabY + delta;

		editor.moveSelection(at(x, grabY), at(x, y));

		assertThat(messageEP, runs(sX, sY + delta, rX, rY + delta));
	}

	@Test
	public void moveSyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sendX, INITIAL_Y),
				at(recvX, INITIAL_Y));

		// Get actual geometry
		int sX = getSource(messageEP).x();
		int rX = getTarget(messageEP).x();
		int rY = getTargetY(messageEP);

		int delta = moveDown ? 30 : -30;
		int x = (sX + rX) / 2;
		int y = rY + delta;

		editor.moveSelection(at(x, rY), at(x, y));

		assertThat(messageEP, runs(sX, y, rX, y));
	}

	@Test
	public void moveCreateMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Create_Message_Edge, at(sendX, INITIAL_Y),
				at(recvX, INITIAL_Y));

		// recvX must be shifted by the half of lifeline header width to the right or to the left
		int recAnchorX = LIFELINE_HEADER_WIDTH / 2;
		if (rightToLeft) {
			recAnchorX *= -1;
		}

		// Get actual geometry
		int sX = getSource(messageEP).x();
		int rX = getTarget(messageEP).x();
		int rY = getTargetY(messageEP);

		int delta = moveDown ? 30 : -30;
		int x = (sX + rX) / 2;
		int y = rY + delta;

		editor.moveSelection(at(x, rY), at(x, y));

		assertThat(messageEP, runs(sendX, y, recvX - recAnchorX, y));
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

		// Get actual geometry
		int sX = getSource(messageEP).x();
		int rX = getTarget(messageEP).x();
		int sY = getSourceY(messageEP);
		int rY = getTargetY(messageEP);

		// Be sure to create messages in top-down order to avoid nudging and
		// far enough apart to avoid padding
		if (moveDown) {
			otherEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sX, otherY),
					at(rX, otherY));
		}

		int delta = moveDown ? 60 : -60;
		int x = (sX + rX) / 2;
		int grabY = (sY + rY) / 2;
		int y = grabY + delta;

		editor.moveSelection(at(x, grabY), at(x, y));

		assertThat("Message should have moved", messageEP, runs(sX, sY + delta, rX, rY + delta));

		// Where do we expect the other message to have ended up?
		IntPredicate relationship = moveDown //
				? val -> val > rY + delta //
				: val -> val < sY + delta;

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
