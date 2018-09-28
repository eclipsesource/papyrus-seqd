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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.feedbackThat;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isHorizontal;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineBodyGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.Figures;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Integration test cases for the {@link LifelineBodyGraphicalNodeEditPolicy}
 * class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("two-lifelines.di")
@Maximized
@RunWith(Parameterized.class)
public class LifelineBodyGraphicalNodeEditPolicyUITest extends AbstractGraphicalEditPolicyUITest {
	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	private final int sendX;

	private final int recvX;

	/**
	 * Initializes me.
	 */
	public LifelineBodyGraphicalNodeEditPolicyUITest(boolean rightToLeft, String direction) {
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
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 125), at(recvX, 125));

		assertThat(messageEP, runs(sendX, 125, recvX, 125, 2));
	}

	@Test
	public void createSlopedAsyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 125), at(recvX, 140));

		assertThat("Message should be sloped", messageEP, runs(sendX, 125, recvX, 140, 2));
	}

	@Test
	public void attemptBackwardSlopedAsyncMessage_allowed() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 140), at(recvX, 138));

		// The target to which the user draw the message should have priority over the
		// source
		assertThat("Message should be horizontal to the target", messageEP, runs(sendX, 140, recvX, 140, 2));
	}

	@Test(expected = AssertionError.class)
	public void attemptBackwardSlopedAsyncMessage_disallowed() {
		// causes assertion error as no edit part will be found since he creation is not
		// possible
		createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 140), at(recvX, 125));
	}

	@Test
	public void createCrossedAsyncMessages() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 150), at(recvX, 150));

		assumeThat(messageEP, runs(sendX, 150, recvX, 150, 2));

		messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 125), at(recvX, 175));

		assertThat("Message should be crossing", messageEP, runs(sendX, 125, recvX, 175, 2));
	}

	@Test
	public void attemptSlopedSyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sendX, 115), at(recvX, 130));

		assertThat("Message should be horizontal to receive location", messageEP, runs(sendX, 130, recvX, 130, 2));
	}

	@Test
	public void asyncMessageLessThanSlopeThreshold() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 115), at(recvX, 119));

		assertThat("Message should be horizontal", messageEP, isHorizontal());
	}

	@Test
	public void forwardSlopeFeedback() {
		editor.hoverConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 115), at(recvX, 130));

		try {
			assertThat("Connection feedback should be horizontal", editor.getDiagramEditPart(),
					feedbackThat(Figures.runs(sendX, 115, recvX, 130)));
		} finally {
			editor.escape();
		}
	}

	@Test
	public void backwardSlopeFeedback_allowed() {
		editor.hoverConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 130), at(recvX, 128));

		try {
			assertThat("Connection feedback should be horizontal", editor.getDiagramEditPart(),
					feedbackThat(Figures.runs(sendX, 130, recvX, 130)));
		} finally {
			editor.escape();
		}
	}

	@Test
	public void backwardSlopeFeedback_disallowed() {
		editor.hoverConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 130), at(recvX, 115));

		try {
			assertThat("Connection should show backward slope, but command not executable.",
					editor.getDiagramEditPart(), feedbackThat(Figures.runs(sendX, 130, recvX, 115)));
		} finally {
			editor.escape();
		}
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
}
