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
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 115),
				at(recvX, 115));

		assertThat(messageEP, runs(sendX, 115, recvX, 115, 2));
	}

	@Test
	public void createSlopedAsyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 115),
				at(recvX, 130));

		assertThat("Message should be sloped", messageEP, runs(sendX, 115, recvX, 130, 2));
	}

	@Test
	public void attemptBackwardSlopedAsyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 130),
				at(recvX, 115));

		assertThat("Message should be horizontal", messageEP, runs(sendX, 130, recvX, 130, 2));
	}

	@Test
	public void createCrossedAsyncMessages() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 130),
				at(recvX, 130));

		assumeThat(messageEP, runs(sendX, 130, recvX, 130, 2));

		messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 115),
				at(recvX, 145));

		assertThat("Message should be crossing", messageEP, runs(sendX, 115, recvX, 145, 2));
	}

	@Test
	public void attemptSlopedSyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(sendX, 115),
				at(recvX, 130));

		assertThat("Message should be horizontal", messageEP, runs(sendX, 115, recvX, 115, 2));
	}

	@Test
	public void asyncMessageLessThanSlopeThreshold() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 115),
				at(recvX, 119));

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
	public void backwardSlopeFeedback() {
		editor.hoverConnection(SequenceElementTypes.Async_Message_Edge, at(sendX, 130), at(recvX, 115));

		try {
			assertThat("Connection feedback should be horizontal", editor.getDiagramEditPart(),
					feedbackThat(Figures.isHorizontal()));
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
