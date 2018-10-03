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
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.isSynchronous;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.LightweightSeqDPrefs;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.MessageSort;
import org.junit.Before;
import org.junit.ClassRule;
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
public class SelfMessageReconnectionUITest extends AbstractGraphicalEditPolicyUITest {
	
	@ClassRule
	public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs().dontCreateExecutionsForSyncMessages();
	
	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	private static final int SEND_Y = 195;
	private static final int RECV_Y = SEND_Y + 20;

	private static final int SELF_MESSAGE_WIDTH = 40;

	private final MessageSort messageSort;
	private final ReconnectionMode reconnectionMode;

	private EditPart messageEP;

	/**
	 * Initializes me.
	 */
	public SelfMessageReconnectionUITest(MessageSort sort, ReconnectionMode mode) {
		super();

		messageSort = sort;
		reconnectionMode = mode;
	}

	@Test
	public void reconnectSelfMessage() {
		boolean async = !isSynchronous(messageSort);
		int grabX = LIFELINE_1_BODY_X;
		int grabY = RECV_Y;
		int dropX;
		int dropY;
		int delta = +10;

		switch (reconnectionMode) {
		case SLIDE_SEND_UP:
			grabY = SEND_Y;
			dropX = LIFELINE_1_BODY_X;
			delta = -10;
			dropY = SEND_Y + delta;
			break;
		case SLIDE_SEND_DOWN:
			grabY = SEND_Y;
			dropX = LIFELINE_1_BODY_X;
			dropY = SEND_Y + delta;
			break;
		case SLIDE_RECEIVE_UP:
			dropX = LIFELINE_1_BODY_X;
			delta = -10;
			dropY = RECV_Y + delta;
			break;
		case SLIDE_RECEIVE_DOWN:
			dropX = LIFELINE_1_BODY_X;
			dropY = RECV_Y + delta;
			break;
		case MOVE_UP:
			// Grab the spine of the message
			grabX = LIFELINE_1_BODY_X + SELF_MESSAGE_WIDTH;
			grabY = (SEND_Y + RECV_Y) / 2;
			dropX = grabX;
			delta = -10;
			dropY = grabY + delta;
			break;
		case MOVE_DOWN:
			// Grab the spine of the message
			grabX = LIFELINE_1_BODY_X + SELF_MESSAGE_WIDTH;
			grabY = (SEND_Y + RECV_Y) / 2;
			dropX = grabX;
			dropY = grabY + delta;
			break;
		case RECEIVE_ON_OTHER_LIFELINE:
			dropX = LIFELINE_2_BODY_X;
			dropY = RECV_Y;
			break;
		case RECEIVE_ON_SENDING_LIFELINE:
			grabX = LIFELINE_2_BODY_X - 5;
			grabY = SEND_Y + (isSynchronous(messageSort) ? 0 : 15);
			dropX = LIFELINE_1_BODY_X;
			dropY = SEND_Y; // The editor should enforce the minimum gap
			break;
		default:
			throw new IllegalStateException("Unsupported reconnection mode: " + reconnectionMode);
		}

		editor.moveSelection(at(grabX, grabY), at(dropX, dropY));

		switch (reconnectionMode) {
		case SLIDE_SEND_UP:
			if (async) {
				assertThat("Send end not moved as expected", messageEP, runs(sendX(), dropY, recvX(), RECV_Y, 2));
			} else {
				assertThat("Synchronous message was reshaped", messageEP, runs(sendX(), SEND_Y, recvX(), RECV_Y, 2));
			}
			break;
		case SLIDE_SEND_DOWN:
		case SLIDE_RECEIVE_UP:
			if (async) {
				assertThat("Minimum gap not maintained", messageEP, runs(sendX(), SEND_Y, recvX(), RECV_Y, 2));
			} else {
				assertThat("Synchronous message was reshaped", messageEP, runs(sendX(), SEND_Y, recvX(), RECV_Y, 2));
			}
			break;
		case SLIDE_RECEIVE_DOWN:
			if (async) {
				assertThat("Receive end not moved as expected", messageEP, runs(sendX(), SEND_Y, recvX(), dropY, 2));
			} else {
				assertThat("Synchronous message was reshaped", messageEP, runs(sendX(), SEND_Y, recvX(), RECV_Y, 2));
			}
			break;
		case MOVE_UP:
		case MOVE_DOWN:
			assertThat("Message not moved as expected", messageEP,
					runs(sendX(), SEND_Y + delta, recvX(), RECV_Y + delta, 2));
			break;
		case RECEIVE_ON_OTHER_LIFELINE: {
			assertThat("Lifeline and slope incorrect", messageEP, runs(sendX(), SEND_Y, recvX(),
					// Async messages may slope; others snap to horizontal
					async ? RECV_Y : SEND_Y, 2));

			Routing routing = getRouting(messageEP);
			assertThat("Lifeline is not oblique", routing, is(Routing.MANUAL_LITERAL));
			RelativeBendpoints bendpoints = getBendpoints(messageEP);
			assertThat("Message has bendpoints", (List<?>) bendpoints.getPoints(), not(hasItem(anything())));
			break;
		}
		case RECEIVE_ON_SENDING_LIFELINE: {
			assertThat("Self-message send and receive not separate by correct gap", messageEP,
					runs(sendX(), SEND_Y, recvX(), RECV_Y, 2));

			Routing routing = getRouting(messageEP);
			assertThat("Lifeline is not rectilinear", routing, is(Routing.RECTILINEAR_LITERAL));
			RelativeBendpoints bendpoints = getBendpoints(messageEP);
			assertThat("Wrong number of message bendpoints", bendpoints.getPoints().size(), is(4));
			break;
		}
		}
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}, {1}")
	public static Iterable<Object[]> parameters() {
		List<Object[]> result = new ArrayList<>();

		// Cross product of message sorts and reconnection modes
		for (MessageSort sort : Arrays.asList(MessageSort.SYNCH_CALL_LITERAL, MessageSort.ASYNCH_CALL_LITERAL)) {
			for (ReconnectionMode mode : ReconnectionMode.values()) {
				result.add(new Object[] { sort, mode });
			}
		}

		return result;
	}

	@Before
	public void createMessage() {
		IElementType type = SequenceElementTypes.getMessageType(messageSort);
		switch (reconnectionMode) {
		default: // the majority of cases are self-messages
			// Drop connection at the same Y coördinate; the editor adds the internal gap
			messageEP = createConnection(type, at(LIFELINE_1_BODY_X, SEND_Y), at(LIFELINE_1_BODY_X, SEND_Y));
			break;
		case RECEIVE_ON_SENDING_LIFELINE:
			int receiveY = SEND_Y + (isSynchronous(messageSort) ? 0 : 15);
			messageEP = createConnection(type, at(LIFELINE_1_BODY_X, SEND_Y), at(LIFELINE_2_BODY_X, receiveY));
			break;
		}
	}

	/**
	 * Compute an adjusted {@code x} coördinate from which the message is expected
	 * to be sent, based whether the send end is on an execution specification or on
	 * the lifeline stem.
	 *
	 * @return the adjusted send end X coördinate
	 */
	int sendX() {
		return LIFELINE_1_BODY_X;
	}

	/**
	 * Compute an adjusted {@code x} coördinate at which the message is expected to
	 * be received, based whether the receive end is on an execution specification
	 * or on the lifeline stem.
	 *
	 * @return the adjusted receive end X coördinate
	 */
	int recvX() {
		switch (reconnectionMode) {
		default: // the majority of cases are self-messages
			return LIFELINE_1_BODY_X;
		case RECEIVE_ON_OTHER_LIFELINE:
			return LIFELINE_2_BODY_X - 5;
		}
	}

	Routing getRouting(EditPart editPart) {
		return ((Connector) editPart.getModel()).getRouting();
	}

	RelativeBendpoints getBendpoints(EditPart editPart) {
		return Optional.ofNullable(((Connector) editPart.getModel()).getBendpoints())
				.filter(RelativeBendpoints.class::isInstance).map(RelativeBendpoints.class::cast).orElse(null);
	}

	//
	// Nested types
	//

	enum ReconnectionMode {
		/** Slide the sending end of a self-message down on the same lifeline. */
		SLIDE_SEND_DOWN,
		/** Slide the sending end of a self-message up on the same lifeline. */
		SLIDE_SEND_UP,
		/** Slide the receiving end of a self-message down on the same lifeline. */
		SLIDE_RECEIVE_DOWN,
		/** Slide the receiving end of a self-message up on the same lifeline. */
		SLIDE_RECEIVE_UP,
		/** Grab the self-message and move it down on the same lifeline. */
		MOVE_DOWN,
		/** Grab the self-message abd move it up on the same lifeline. */
		MOVE_UP,
		/** Re-connect a receiving end of a self-message to some other lifeline. */
		RECEIVE_ON_OTHER_LIFELINE,
		/**
		 * Re-connect the receiving end of a message between two lifelines to the
		 * sending lifeline, to make it a self-message.
		 */
		RECEIVE_ON_SENDING_LIFELINE;
	}
}
