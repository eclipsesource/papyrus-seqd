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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isBounded;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.ExecutionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.MessageEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.LightweightSeqDPrefs;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Integration test cases for the creation of self messages.
 *
 * @author Christian W. Damus
 */
@ModelResource("one-exec.di")
@Maximized
@RunWith(Parameterized.class)
public class SelfMessageCreationUITest extends AbstractGraphicalEditPolicyUITest {

	@ClassRule
	public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs()
			.dontCreateExecutionsForSyncMessages();

	@ClassRule
	public static TestRule tolerance = GEFMatchers.defaultTolerance(1);

	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	// Vertical position at whhich to send the self-message
	private static final int Y_POSITION = 195;

	private static final int MINIMUM_SPAN = 20;

	private static final int CUSTOM_SPAN = 55;

	private final CreationMode mode;

	private final MessageSort messageSort;

	private final int messageX; // Where to draw the message

	private final int messageY; // Where to complete the message

	/**
	 * Initializes me.
	 */
	public SelfMessageCreationUITest(MessageSort sort, CreationMode mode) {
		super();

		messageSort = sort;
		this.mode = mode;
		messageX = mode == CreationMode.ON_EXECUTION ? LIFELINE_2_BODY_X : LIFELINE_1_BODY_X;
		messageY = Y_POSITION + (mode.isTall() ? CUSTOM_SPAN : 0);

	}

	@Test
	public void createSelfMessage() {
		if (mode == CreationMode.WITH_EXECUTION) {
			new LightweightSeqDPrefs().createExecutionsForSyncMessages().run(this::doCreateSelfMessage);
		} else {
			doCreateSelfMessage();
		}
	}

	private void doCreateSelfMessage() {
		IElementType type = SequenceElementTypes.getMessageType(messageSort);

		EditPart messageEP;

		try {
			messageEP = createConnection(type, at(messageX, Y_POSITION), at(messageX, messageY));
		} catch (AssertionError e) {
			// Expected for create messages
			messageEP = null;
		}

		switch (messageSort) {
			case CREATE_MESSAGE_LITERAL:
				assertThat("Self-creation was permitted", messageEP, nullValue());
				break;
			case DELETE_MESSAGE_LITERAL:
				if (mode == CreationMode.AROUND_OCCURRENCE) {
					// This isn't permitted
					assertThat("Delete message created around an existing occurrence", messageEP,
							nullValue());
				} else {
					// These have a slightly different shape, owing to connection to
					// the destruction occurrence specification
					assertThat(messageEP, runs(LIFELINE_1_BODY_X, 195, //
							// Account for the attachment to the side of the destruction occurrence
							// specification and extra space made for the destruction
							LIFELINE_1_BODY_X + 5, 225, //
							2));
				}
				break;
			default:
				if (messageEP == null) {
					assumeThat("Should fail to get a message edit-part only for create message", messageSort,
							equalTo(MessageSort.CREATE_MESSAGE_LITERAL));
					return; // Unreachable
				}

				if (mode != CreationMode.WITH_EXECUTION) {
					assertThat(messageEP, runs(x(), top(), x(), bottom(), 2));
				} else {
					// messageEP is the sync message.
					assertThat(messageEP, runs(x(), top(), x() + 5, bottom()));

					// then find the execution linked to this message
					assertThat(messageEP, instanceOf(MessageEditPart.class));
					EditPart executionEP = ((MessageEditPart)messageEP).getTarget();
					assertThat(executionEP, instanceOf(ExecutionSpecificationEditPart.class));
					assertThat(executionEP, isBounded(x() - 5, bottom(), 10, 40)); // default sizes
					ConnectionEditPart replyEP = (ConnectionEditPart)((ExecutionSpecificationEditPart)executionEP)
							.getSourceConnections().get(0);
					EObject reply = ((View)replyEP.getModel()).getElement();
					assertThat(reply, instanceOf(Message.class));
					assertThat(((Message)reply).getMessageSort(), equalTo(MessageSort.REPLY_LITERAL));
					assertThat(replyEP, runs(x() + 5, bottom() + 40, x(), bottom() + 40 + MINIMUM_SPAN));
				}

				break;
		}
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}, {1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] { //
				{MessageSort.SYNCH_CALL_LITERAL, CreationMode.ON_LIFELINE }, //
				{MessageSort.SYNCH_CALL_LITERAL, CreationMode.ON_EXECUTION }, //
				{MessageSort.SYNCH_CALL_LITERAL, CreationMode.ON_LIFELINE_TALL }, //
				{MessageSort.SYNCH_CALL_LITERAL, CreationMode.WITH_EXECUTION }, //
				{MessageSort.ASYNCH_CALL_LITERAL, CreationMode.ON_LIFELINE }, //
				{MessageSort.ASYNCH_CALL_LITERAL, CreationMode.ON_EXECUTION }, //
				{MessageSort.ASYNCH_CALL_LITERAL, CreationMode.ON_LIFELINE_TALL }, //
				{MessageSort.REPLY_LITERAL, CreationMode.ON_LIFELINE }, //
				{MessageSort.REPLY_LITERAL, CreationMode.ON_EXECUTION }, //
				{MessageSort.CREATE_MESSAGE_LITERAL, CreationMode.ON_LIFELINE }, //
				{MessageSort.DELETE_MESSAGE_LITERAL, CreationMode.ON_LIFELINE }, //
				{MessageSort.DELETE_MESSAGE_LITERAL, CreationMode.AROUND_OCCURRENCE }, //
		});
	}

	/**
	 * If a test needs it, set up an existing occurrence around which to draw the self-message.
	 */
	@Before
	public void createOccurrence() {
		if (mode == CreationMode.AROUND_OCCURRENCE) {
			int insideY = Y_POSITION + 7;
			editor.createConnection(SequenceElementTypes.Async_Message_Edge, //
					at(LIFELINE_2_BODY_X, insideY), at(LIFELINE_1_BODY_X, insideY));
		}
	}

	/**
	 * Compute an adjusted {@code x} coördinate based whether the message is on an execution specification or
	 * on the lifeline stem.
	 *
	 * @return the adjusted X coördinate
	 */
	int x() {
		return messageX + (mode == CreationMode.ON_EXECUTION ? 5 : 0);
	}

	/**
	 * Compute an adjusted {@code y} coördinate of the send event.
	 *
	 * @return the adjusted Y coördinate
	 */
	int top() {
		return Y_POSITION;
	}

	/**
	 * Compute an adjusted {@code y} coördinate of the send event.
	 *
	 * @return the adjusted Y coördinate
	 */
	int bottom() {
		int gap = MINIMUM_SPAN;
		if ((mode == CreationMode.ON_LIFELINE_TALL) && !MessageUtil.isSynchronous(messageSort)) {
			gap = CUSTOM_SPAN;
		}
		return Y_POSITION + gap;
	}

	//
	// Nested types
	//

	enum CreationMode {
		// Create the self-message on a lifeline
		ON_LIFELINE,
		// Create the self-message on an execution specification
		ON_EXECUTION,
		// Draw a tall self-message shape (with a gap, not just in one place)
		ON_LIFELINE_TALL,
		// Draw a self-message around an existing execution occurrence
		AROUND_OCCURRENCE,
		// Create an execution occurrence for the sync call message
		WITH_EXECUTION;

		@Override
		public String toString() {
			return name().toLowerCase().replace('_', '-');
		}

		boolean isTall() {
			return (this == ON_LIFELINE_TALL) || (this == AROUND_OCCURRENCE);
		}
	}
}
