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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isRect;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isBounded;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.LightweightSeqDPrefs;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Integration test cases for disconnection of messages from execution
 * specification start/finish.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("two-lifelines.di")
@Maximized
public class MessageExecutionUITest extends AbstractGraphicalEditPolicyUITest {

	@ClassRule
	public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs().createExecutionsForSyncMessages()
			.createRepliesForSyncCalls();

	// Horizontal position of the first lifeline's body
	private static final int LL1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LL2_BODY_X = 281;

	private static final int EXEC_WIDTH = 10;

	private EditPart requestEP;
	private Message request;
	private EditPart execEP;
	private ExecutionSpecification exec;
	private EditPart replyEP;
	private Message reply;

	/**
	 * Initializes me.
	 */
	public MessageExecutionUITest() {
		super();
	}

	@Test
	public void moveRequestMessage() {
		Point grabRequest = getMessageGrabPoint(requestEP);
		Point dropRequest = new Point(grabRequest.x, grabRequest.y - 50);
		Rectangle execBounds = getBounds(execEP);

		editor.moveSelection(grabRequest, dropRequest);

		assertThat("Request not moved", requestEP,
				runs(LL1_BODY_X, dropRequest.y, LL2_BODY_X - (EXEC_WIDTH / 2), dropRequest.y));

		int oldBottom = execBounds.bottom();
		execBounds.setY(dropRequest.y);
		execBounds.setHeight(oldBottom - execBounds.y);
		assertThat("Execution not moved/resized", execEP, isBounded(isRect(execBounds)));
	}

	@Test
	public void moveReplyMessage() {
		Point grabReply = getMessageGrabPoint(replyEP);
		Point dropReply = new Point(grabReply.x, grabReply.y + 50);
		Rectangle execBounds = getBounds(execEP);

		editor.moveSelection(grabReply, dropReply);

		assertThat("Reply not moved", replyEP,
				runs(LL2_BODY_X - (EXEC_WIDTH / 2), dropReply.y, LL1_BODY_X, dropReply.y));

		execBounds.setHeight(dropReply.y - execBounds.y);
		assertThat("Execution not moved or resized", execEP, isBounded(isRect(execBounds)));
	}

	@Test
	public void disconnectRequestMessage() {
		Point grabRequest = getMessageGrabPoint(requestEP);
		Point dropRequest = new Point(grabRequest.x, grabRequest.y - 50);
		Rectangle execBounds = getBounds(execEP);

		editor.with(editor.allowSemanticReordering(), () -> editor.moveSelection(grabRequest, dropRequest));

		assertThat("Request not moved", requestEP,
				runs(LL1_BODY_X, dropRequest.y, LL2_BODY_X, dropRequest.y));
		assertThat("Execution moved or resized", execEP, isBounded(isRect(execBounds)));
	}

	@Test
	public void disconnectReplyMessage() {
		Point grabReply = getMessageGrabPoint(replyEP);
		Point dropReply = new Point(grabReply.x, grabReply.y + 50);
		Rectangle execBounds = getBounds(execEP);

		editor.with(editor.allowSemanticReordering(), () -> editor.moveSelection(grabReply, dropReply));

		assertThat("Reply not moved", replyEP, runs(LL2_BODY_X, dropReply.y, LL1_BODY_X, dropReply.y));
		assertThat("Execution moved or resized", execEP, isBounded(isRect(execBounds)));
	}

	@Test
	public void moveExecutionStart() {
		// First, select the execution to activate selection handles
		editor.select(getCenter(execEP));

		Point grabStart = at(LL2_BODY_X, getTop(execEP));
		Point dropStart = new Point(grabStart.x, grabStart.y - 50);
		Point grabAt = getResizeHandleGrabPoint(execEP, PositionConstants.NORTH);
		Rectangle execBounds = getBounds(execEP);

		editor.drag(grabAt, dropStart);

		assertThat("Request message not moved", requestEP, runs(LL1_BODY_X, dropStart.y,
				LL2_BODY_X - (EXEC_WIDTH / 2), dropStart.y, RESIZE_TOLERANCE));

		int oldBottom = execBounds.bottom();
		execBounds.setY(dropStart.y);
		execBounds.setHeight(oldBottom - execBounds.y);
		assertThat("Execution not moved/resized", execEP, isBounded(isRect(execBounds, RESIZE_TOLERANCE)));
	}

	@Test
	public void moveExecutionFinish() {
		// First, select the execution to activate selection handles
		editor.select(getCenter(execEP));

		Point grabFinish = at(LL2_BODY_X, getBottom(execEP));
		Point dropFinish = new Point(grabFinish.x, grabFinish.y + 50);
		Point grabAt = getResizeHandleGrabPoint(execEP, PositionConstants.SOUTH);
		Rectangle execBounds = getBounds(execEP);

		editor.drag(grabAt, dropFinish);

		assertThat("Reply message not moved", replyEP, runs(LL2_BODY_X - (EXEC_WIDTH / 2), dropFinish.y,
				LL1_BODY_X, dropFinish.y, RESIZE_TOLERANCE));

		execBounds.setHeight(dropFinish.y - execBounds.y);
		assertThat("Execution not moved or resized", execEP,
				isBounded(isRect(execBounds, RESIZE_TOLERANCE)));
	}

	@Test
	public void disconnectExecutionStart() {
		// First, select the execution to activate selection handles
		editor.select(getCenter(execEP));

		Point grabStart = at(LL2_BODY_X, getTop(execEP));
		Point dropStart = new Point(grabStart.x, grabStart.y - 50);
		Point grabAt = getResizeHandleGrabPoint(execEP, PositionConstants.NORTH);
		Rectangle execBounds = getBounds(execEP);
		int msgY = getSourceY(requestEP);

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropStart));

		assertThat("Request message moved", requestEP,
				runs(LL1_BODY_X, msgY, LL2_BODY_X - (EXEC_WIDTH / 2), msgY, RESIZE_TOLERANCE));

		int oldBottom = execBounds.bottom();
		execBounds.setY(dropStart.y);
		execBounds.setHeight(oldBottom - execBounds.y);
		assertThat("Execution not moved/resized", execEP, isBounded(isRect(execBounds, RESIZE_TOLERANCE)));
	}

	@Ignore("The Ctrl key modifier for semantic reordering conflicts with GEF standard behavior on Linux")
	@Test
	public void disconnectExecutionFinish() {
		// First, select the execution to activate selection handles
		editor.select(getCenter(execEP));

		Point grabFinish = at(LL2_BODY_X, getBottom(execEP));
		Point dropFinish = new Point(grabFinish.x, grabFinish.y + 50);
		Point grabAt = getResizeHandleGrabPoint(execEP, PositionConstants.SOUTH);
		Rectangle execBounds = getBounds(execEP);
		int msgY = getSourceY(replyEP);

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropFinish));

		assertThat("Reply message moved", replyEP,
				runs(LL2_BODY_X - (EXEC_WIDTH / 2), msgY, LL1_BODY_X, msgY, RESIZE_TOLERANCE));

		execBounds.setHeight(dropFinish.y - execBounds.y);
		assertThat("Execution not moved or resized", execEP,
				isBounded(isRect(execBounds, RESIZE_TOLERANCE)));
	}

	//
	// Test framework
	//

	@Before
	public void createSyncMessage() {
		requestEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(LL1_BODY_X, 200),
				at(LL2_BODY_X, 200));
		assumeThat("Request message not created", requestEP, notNullValue());

		request = (Message) requestEP.getAdapter(EObject.class);

		exec = ((OccurrenceSpecification) request.getReceiveEvent()).getCovered().getCoveredBys().stream()
				.filter(ExecutionSpecification.class::isInstance).map(ExecutionSpecification.class::cast)
				.findFirst().orElse(null);
		execEP = (exec == null)
				? null
				: DiagramEditPartsUtil.getChildByEObject(exec, editor.getDiagramEditPart(), false);
		assumeThat("Execution not created", execEP, notNullValue());

		reply = ((MessageEnd) exec.getFinish()).getMessage();
		replyEP = (reply == null)
				? null
				: DiagramEditPartsUtil.getChildByEObject(reply, editor.getDiagramEditPart(), true);
		assumeThat("Reply message not created", replyEP, notNullValue());
	}

	static Point getMessageGrabPoint(EditPart editPart) {
		Connection connection = (Connection) ((ConnectionEditPart) editPart).getFigure();
		return connection.getPoints().getMidpoint();
	}
}
