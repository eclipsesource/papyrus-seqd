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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.isNear;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.LightweightSeqDPrefs;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Integration test cases for the execution start/finish replacement behaviour
 * when snapping to message ends.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("one-exec.di")
@Maximized
public class ExecutionSnappingUITest extends AbstractGraphicalEditPolicyUITest {

	@ClassRule
	public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs()
			.dontCreateExecutionsForSyncMessages();

	// Horizontal position of the first lifeline's body
	private static final int LL1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LL2_BODY_X = 281;

	private static final boolean ABOVE = true;
	private static final boolean BELOW = false;

	private static final int EXEC_START_Y = 165;
	private static final int EXEC_HEIGHT = 82;
	private static final int EXEC_FINISH_Y = EXEC_START_Y + EXEC_HEIGHT;

	private EditPart execEP;
	private ExecutionSpecification exec;

	/**
	 * Initializes me.
	 */
	public ExecutionSnappingUITest() {
		super();
	}

	@Test
	public void snapStartToSyncCallReceive() {
		int msgY = EXEC_START_Y - 30;
		EditPart messageEP = editor.createConnection(SequenceElementTypes.Sync_Message_Edge,
				at(LL1_BODY_X, msgY), at(LL2_BODY_X, msgY));
		msgY = getTargetY(messageEP); // Find where it actually ended up

		// The top of the execution snaps to the message end. Offset by one to click on
		// the execution figure's edge, accounting also for nudging on message creation
		int execTop = getTop(execEP);
		editor.moveSelection(at(LL2_BODY_X, execTop + 1), at(LL2_BODY_X, withinMagnet(msgY, BELOW)));

		execTop = getTop(execEP);

		assertThat("Execution specification did not snap to message end", execTop, isNear(msgY));

		// The message receive event starts the execution
		Message message = (Message) messageEP.getAdapter(EObject.class);
		assertThat("Execution not started by message end", exec.getStart(), is(message.getReceiveEvent()));

		// The message send and receive both are still semantically before the execution
		assertThat("Message ends out of order", message.getSendEvent(),
				editor.semanticallyPrecedes(message.getReceiveEvent()));
		assertThat("Execution out of order", exec, editor.semanticallyFollows(message.getReceiveEvent()));
	}

	@Test
	@Ignore("Exec start should only bind to request receive")
	public void snapStartToSyncCallSend() {
		int msgY = EXEC_START_Y - 30;
		EditPart messageEP = editor.createConnection(SequenceElementTypes.Sync_Message_Edge,
				at(LL2_BODY_X, msgY), at(LL1_BODY_X, msgY));
		msgY = getSourceY(messageEP); // Find where it actually ended up

		// The top of the execution snaps to the message end. Offset by one to click on
		// the execution figure's edge, accounting also for nudging on message creation
		int execTop = getTop(execEP);
		editor.moveSelection(at(LL2_BODY_X, execTop + 1), at(LL2_BODY_X, withinMagnet(msgY, BELOW)));

		execTop = getTop(execEP);

		assertThat("Execution specification did not snap to message end", execTop, isNear(msgY));

		// The message receive event *does not* start the execution
		Message message = (Message) messageEP.getAdapter(EObject.class);
		assertThat("Execution is started by message send", exec.getStart(), not(message.getSendEvent()));
		assertThat("Execution start is lost", exec.getStart(),
				instanceOf(ExecutionOccurrenceSpecification.class));
	}

	@Test
	@Ignore("Exec finish should only bind to reply send")
	public void snapFinishToReplyReceive() {
		int msgY = EXEC_FINISH_Y + 20;
		EditPart messageEP = editor.createConnection(SequenceElementTypes.Reply_Message_Edge,
				at(LL1_BODY_X, msgY), at(LL2_BODY_X, msgY));

		// Find where things actually ended up
		msgY = getTargetY(messageEP);
		int execBottom = getBottom(execEP);

		// The bottom of the execution snaps to the message end. Offset by one to click
		// on the execution figure's edge
		editor.moveSelection(at(LL2_BODY_X, execBottom - 1), at(LL2_BODY_X, withinMagnet(msgY, ABOVE)));

		execBottom = getBottom(execEP);

		assertThat("Execution specification did not snap to message end", execBottom, isNear(msgY));

		// The message receive event *does not* finish the execution
		Message message = (Message) messageEP.getAdapter(EObject.class);
		assertThat("Execution is finished by message receive", exec.getFinish(),
				not(message.getReceiveEvent()));
		assertThat("Execution finish is lost", exec.getFinish(),
				instanceOf(ExecutionOccurrenceSpecification.class));
	}

	@Test
	public void snapFinishToReplySend() {
		int msgY = EXEC_FINISH_Y + 20;
		EditPart messageEP = editor.createConnection(SequenceElementTypes.Reply_Message_Edge,
				at(LL2_BODY_X, msgY), at(LL1_BODY_X, msgY));

		// Find where things actually ended up
		msgY = getTargetY(messageEP);
		int execBottom = getBottom(execEP);

		// The bottom of the execution snaps to the message end. Offset by one to click
		// on the execution figure's edge
		editor.moveSelection(at(LL2_BODY_X, execBottom - 1), at(LL2_BODY_X, withinMagnet(msgY, ABOVE)));

		execBottom = getBottom(execEP);

		assertThat("Execution specification did not snap to message end", execBottom, isNear(msgY));

		// The message receive event starts the execution
		Message message = (Message) messageEP.getAdapter(EObject.class);
		assertThat("Execution not finished by message end", exec.getFinish(), is(message.getSendEvent()));

		// The message send and receive both are still semantically after the execution
		assertThat("Message ends out of order", message.getSendEvent(),
				editor.semanticallyPrecedes(message.getReceiveEvent()));
		assertThat("Execution out of order", exec, editor.semanticallyPrecedes(message.getSendEvent()));
	}

	//
	// Test framework
	//

	@Before
	public void getExecutionSpecification() {
		exec = getInteraction().getLifelines().get(1).getExecutions().get(0).getElement();
		execEP = DiagramEditPartsUtil.getChildByEObject(exec, editor.getDiagramEditPart(), false);
		assumeThat("Execution specification not found in diagram", execEP, notNullValue());
	}

	protected MInteraction getInteraction() {
		return editor.getSequenceDiagram().map(MInteraction::getInstance).orElseThrow(
				() -> new AssertionError("No sequence diagram and hence no valid logical model"));
	}

	int withinMagnet(int y, boolean above) {
		return above ? y - 9 : y + 9;
	}

}
