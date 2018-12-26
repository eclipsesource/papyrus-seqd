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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests.VerificationMode.ASSERT;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests.VerificationMode.ASSUME;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isBounded;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.sized;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.where;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.isNear;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Optional;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.DestructionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.ExecutionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.LightweightSeqDPrefs;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MDestruction;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Element;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Integration test cases for use cases in which occurrences are moved from one lifeline to another.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("three-lifelines.di")
@Maximized
@RunWith(Enclosed.class)
public class LifelineSwitchingUITest extends AbstractGraphicalEditPolicyUITest {

	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	// Horizontal position of the third lifeline's body
	private static final int LIFELINE_3_BODY_X = 438;

	// Width of the destruction X shape
	private static final int DESTRUCTION_WIDTH = 20;

	// Vertical gap between send and receive of a self-message
	private static final int SELF_MESSAGE_HEIGHT = 20;

	// Width of a lifeline head
	private static final int LIFELINE_HEAD_WIDTH = 100;

	private static final int INITIAL_Y = 145;

	protected final int sendX;

	protected final int recvX;

	protected final int newRecvX;

	protected int mesgY;

	protected EditPart messageEP;

	/**
	 * Initializes me.
	 */
	public LifelineSwitchingUITest() {
		super();

		sendX = LIFELINE_1_BODY_X;
		recvX = LIFELINE_2_BODY_X;
		mesgY = INITIAL_Y;

		newRecvX = LIFELINE_3_BODY_X;
	}

	@RunWith(JUnit4.class)
	public static class MessageNoExecution extends LifelineSwitchingUITest {

		@ClassRule
		public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs()
				.dontCreateExecutionsForSyncMessages();

		@Test
		public void switchLifeline() {
			switchLifeline(ASSERT);
		}

		protected void switchLifeline(VerificationMode mode) {
			editor.with(editor.allowSemanticReordering(),
					() -> editor.moveSelection(at(getGrabX(), getGrabY()), at(getReleaseX(), getReleaseY())));

			// Verify the new visuals
			mode.verify(messageEP, runs(sendX, mesgY, getNewRecvX(), getNewRecvY()));

			// And the semantics
			MMessage msg = getRequestMessage();
			mode.verify("Sender changed", msg.getSender(), is(getSender()));
			mode.verify("Receiver not changed", msg.getReceiver(), not(getOriginalReceiver()));
			mode.verify("Wrong receiver", msg.getReceiver(), is(getReceiver()));
		}

		@Test
		public void undoSwitchLifeline() {
			switchLifeline(ASSUME);
			undoSwitchLifeline(ASSERT);
		}

		protected void undoSwitchLifeline(VerificationMode mode) {
			editor.undo();

			// Verify the old visuals
			mode.verify(messageEP, runs(sendX, mesgY, getGrabX(), getGrabY()));

			// And the old semantics
			MMessage msg = getRequestMessage();
			mode.verify("Sender changed", msg.getSender(), is(getSender()));
			mode.verify("Receiver not reverted", msg.getReceiver(), not(getReceiver()));
			mode.verify("Wrong receiver", msg.getReceiver(), is(getOriginalReceiver()));
		}

		protected void undoSwitchSelfLifeline(VerificationMode mode) {
			editor.undo();

			// Verify the old visuals
			mode.verify(messageEP, runs(sendX, mesgY, getGrabX(), mesgY));

			// And the old semantics
			MMessage msg = getRequestMessage();
			mode.verify("Sender changed", msg.getSender(), is(getSender()));
			mode.verify("Receiver not reverted", msg.getReceiver(), not(getReceiver()));
			mode.verify("Wrong receiver", msg.getReceiver(), is(getOriginalReceiver()));
		}
	}

	public static class MessageSelfNoExecution extends MessageNoExecution {

		@Override
		public int getReleaseX() {
			return sendX;
		}

		@Override
		public int getNewRecvX() {
			return sendX;
		}

		@Override
		public int getNewRecvY() {
			return mesgY + 20;
		}

		@Override
		Optional<MLifeline> getReceiver() {
			return getSender();
		}

	}

	public static class MessageNoReply extends MessageNoExecution {

		@ClassRule
		public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs().dontCreateRepliesForSyncCalls();

		@Override
		protected void switchLifeline(VerificationMode mode) {
			super.switchLifeline(ASSUME);

			// Verify the new visuals
			mode.verify(getExecutionEditPart(),
					isBounded(isNear(getNewRecvX(), 5), isNear(getNewRecvY()), anything(), anything()));

			// And the semantics
			MExecution exec = requireExecution();
			mode.verify("Owner not changed", exec.getOwner(), not(requireOriginalReceiver()));
			mode.verify("Wrong owner", exec.getOwner(), is(requireReceiver()));
			MOccurrence<?> finish = verifying(mode, exec.getFinish(), "No finish occurrence");
			mode.verify("Finish coverage lost", finish.getCovered().isPresent(), is(true));
			MLifeline covered = finish.getCovered().get();
			mode.verify("Finish coverage not changed", covered, not(requireOriginalReceiver()));
			mode.verify("Wrong finish coverage", covered, is(requireReceiver()));
		}

		@Override
		protected void undoSwitchLifeline(VerificationMode mode) {
			super.undoSwitchLifeline(ASSUME);

			// Verify the old visuals
			mode.verify(getExecutionEditPart(),
					isBounded(isNear(getGrabX(), 5), isNear(mesgY), anything(), anything()));

			// And the old semantics
			MExecution exec = requireExecution();
			mode.verify("Owner not reverted", exec.getOwner(), is(requireOriginalReceiver()));
			MOccurrence<?> finish = verifying(mode, exec.getFinish(), "No finish occurrence");
			mode.verify("Finish coverage lost", finish.getCovered().isPresent(), is(true));
			MLifeline covered = finish.getCovered().get();
			mode.verify("Finish coverage not reverted", covered, is(requireOriginalReceiver()));
		}

		//
		// Test framework
		//

		EditPart getExecutionEditPart() {
			Optional<EditPart> editPart = Optional.of(messageEP).map(ConnectionEditPart.class::cast)
					.map(ConnectionEditPart::getTarget)
					.filter(ExecutionSpecificationEditPart.class::isInstance);
			return assuming(editPart, "No execution edit-part created");
		}

		@Override
		int getGrabX() {
			return super.getGrabX() - (EXEC_WIDTH / 2);
		}

		@Override
		int getNewRecvX() {
			return super.getNewRecvX() - (EXEC_WIDTH / 2);
		}

	}

	public static class MessageSelfNoReply extends MessageNoReply {

		@Override
		public int getReleaseX() {
			return sendX;
		}

		@Override
		public int getNewRecvX() {
			return sendX;
		}

		@Override
		public int getNewRecvY() {
			return mesgY + 20;
		}

		@Override
		Optional<MLifeline> getReceiver() {
			return getSender();
		}
	}

	public static class MessageWithReply extends MessageNoReply {

		@ClassRule
		public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs().createRepliesForSyncCalls();

		@Override
		protected void switchLifeline(VerificationMode mode) {
			super.switchLifeline(VerificationMode.ASSUME);

			// Verify the new visuals
			mode.verify(getReplyEditPart(),
					runs(isPoint(isNear(getNewRecvX()), anything()), isPoint(isNear(sendX), anything())));

			// And the semantics
			MMessage reply = getReplyMessage();
			mode.verify("Receiver changed", reply.getReceiver(), is(getSender()));
			mode.verify("Send not changed", reply.getSender(), not(getOriginalReceiver()));
			mode.verify("Wrong sender", reply.getSender(), is(getReceiver()));
		}

		@Override
		protected void undoSwitchLifeline(VerificationMode mode) {
			super.undoSwitchLifeline(VerificationMode.ASSUME);

			// Verify the old visuals
			mode.verify(getReplyEditPart(),
					runs(isPoint(isNear(getGrabX()), anything()), isPoint(isNear(sendX), anything())));

			// And the old semantics
			MMessage reply = getReplyMessage();
			mode.verify("Receiver changed", reply.getReceiver(), is(getSender()));
			mode.verify("Sender not reverted", reply.getSender(), not(getReceiver()));
			mode.verify("Wrong sender", reply.getSender(), is(getOriginalReceiver()));
		}

		//
		// Test framework
		//

		EditPart getReplyEditPart() {
			@SuppressWarnings("unchecked")
			Optional<EditPart> editPart = editor.getDiagramEditPart().getConnections().stream().skip(1)
					.findFirst();
			return assuming(editPart, "No execution edit-part created");
		}
	}

	public static class MessageSelfWithReply extends MessageNoExecution {

		@Override
		public int getReleaseX() {
			return sendX;
		}

		@Override
		public int getNewRecvY() {
			return mesgY + 20;
		}

		@Override
		Optional<MLifeline> getReceiver() {
			return getSender();
		}

		@Override
		MLifeline requireReceiver() {
			return requireLifeline("Lifeline1");
		}

		@ClassRule
		public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs().createRepliesForSyncCalls();

		@Override
		protected void switchLifeline(VerificationMode mode) {
			super.switchLifeline(ASSERT);

			// Verify the new visuals
			mode.verify(getExecutionEditPart(), isBounded(isNear(getReleaseX(), 5), isNear(getNewRecvY()),
					is(EXEC_WIDTH), is(EXEC_HEIGHT)));

			// And the semantics
			MExecution exec = requireExecution();
			mode.verify("Owner not changed", exec.getOwner(), not(requireOriginalReceiver()));
			mode.verify("Wrong owner", exec.getOwner(), is(requireReceiver()));
			MOccurrence<?> finish = verifying(mode, exec.getFinish(), "No finish occurrence");
			mode.verify("Finish coverage lost", finish.getCovered().isPresent(), is(true));
			MLifeline covered = finish.getCovered().get();
			mode.verify("Finish coverage not changed", covered, not(requireOriginalReceiver()));
			mode.verify("Wrong finish coverage", covered, is(requireReceiver()));

			// check the reply
			MMessage msg = getReplyMessage();
			mode.verify("Sender changed", msg.getSender(), is(getSender()));
			mode.verify("Receiver not changed", msg.getReceiver(), not(getOriginalReceiver()));
			mode.verify("Wrong receiver", msg.getReceiver(), is(getReceiver()));
			mode.verify(getReplyEditPart(), runs(getNewRecvX(), getNewRecvY() + EXEC_HEIGHT, getReleaseX(),
					getNewRecvY() + EXEC_HEIGHT + SELF_MESSAGE_HEIGHT));

		}

		@Override
		protected void undoSwitchLifeline(VerificationMode mode) {
			super.undoSwitchLifeline(ASSERT);

			// Verify the old visuals
			mode.verify(getExecutionEditPart(),
					isBounded(isNear(getGrabX(), 5), isNear(mesgY), anything(), anything()));

			// And the old semantics
			MExecution exec = requireExecution();
			mode.verify("Owner not reverted", exec.getOwner(), is(requireOriginalReceiver()));
			MOccurrence<?> finish = verifying(mode, exec.getFinish(), "No finish occurrence");
			mode.verify("Finish coverage lost", finish.getCovered().isPresent(), is(true));
			MLifeline covered = finish.getCovered().get();
			mode.verify("Finish coverage not reverted", covered, is(requireOriginalReceiver()));
		}

		//
		// Test framework
		//

		EditPart getExecutionEditPart() {
			Optional<EditPart> editPart = Optional.of(messageEP).map(ConnectionEditPart.class::cast)
					.map(ConnectionEditPart::getTarget)
					.filter(ExecutionSpecificationEditPart.class::isInstance);
			return assuming(editPart, "No execution edit-part created");
		}

		@Override
		int getGrabX() {
			return super.getGrabX() - (EXEC_WIDTH / 2);
		}

		@Override
		int getNewRecvX() {
			return sendX + (EXEC_WIDTH / 2);
		}

		//
		// Test framework
		//

		EditPart getReplyEditPart() {
			@SuppressWarnings("unchecked")
			Optional<EditPart> editPart = editor.getDiagramEditPart().getConnections().stream().skip(1)
					.findFirst();
			return assuming(editPart, "No execution edit-part created");
		}
	}

	public static class MessageSelfToUnSelfWithReply extends MessageNoExecution {

		@ClassRule
		public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs().createRepliesForSyncCalls();

		@Override
		protected int getInitialSenderX() {
			return LIFELINE_1_BODY_X;
		}

		@Override
		protected int getInitialSenderY() {
			return mesgY;
		}

		@Override
		protected int getInitialReceiverX() {
			return LIFELINE_1_BODY_X;
		}

		@Override
		protected int getInitialReceiverY() {
			return mesgY + 20;
		}

		@Override
		public int getReleaseX() {
			return LIFELINE_2_BODY_X;
		}

		@Override
		int getGrabX() {
			return getInitialReceiverX() + (EXEC_WIDTH / 2);
		}

		@Override
		int getGrabY() {
			return getInitialReceiverY();
		}

		@Override
		public int getNewRecvY() {
			return mesgY;
		}

		@Override
		int getNewRecvX() {
			return LIFELINE_2_BODY_X - (EXEC_WIDTH / 2);
		}

		@Override
		MLifeline requireReceiver() {
			return requireLifeline("Lifeline2");
		}

		@Override
		Optional<MLifeline> getReceiver() {
			return getLifeline("Lifeline2");
		}

		@Override
		Optional<MLifeline> getOriginalReceiver() {
			return getLifeline("Lifeline1");
		}

		@Override
		Optional<MLifeline> getSender() {
			return getLifeline("Lifeline1");
		}

		@Override
		MLifeline requireOriginalReceiver() {
			return requireLifeline("Lifeline1");
		}

		@Override
		protected void switchLifeline(VerificationMode mode) {
			super.switchLifeline(ASSERT);

			// Verify the new visuals
			mode.verify(getExecutionEditPart(), isBounded(isNear(getReleaseX(), 5), isNear(getNewRecvY()),
					is(EXEC_WIDTH), is(EXEC_HEIGHT)));

			// And the semantics
			MExecution exec = requireExecution();
			mode.verify("Owner not changed", exec.getOwner(), not(requireOriginalReceiver()));
			mode.verify("Wrong owner", exec.getOwner(), is(requireReceiver()));
			MOccurrence<?> finish = verifying(mode, exec.getFinish(), "No finish occurrence");
			mode.verify("Finish coverage lost", finish.getCovered().isPresent(), is(true));
			MLifeline covered = finish.getCovered().get();
			mode.verify("Finish coverage not changed", covered, not(requireOriginalReceiver()));
			mode.verify("Wrong finish coverage", covered, is(requireReceiver()));

			// check the reply
			MMessage msg = getReplyMessage();
			mode.verify("Sender changed", msg.getSender(), is(getReceiver()));
			mode.verify("Wrong receiver", msg.getReceiver(), is(getSender()));
			mode.verify(getReplyEditPart(), runs(getNewRecvX(), getNewRecvY() + EXEC_HEIGHT,
					getInitialReceiverX(), getNewRecvY() + EXEC_HEIGHT));

		}

		@Override
		protected void undoSwitchLifeline(VerificationMode mode) {
			super.undoSwitchLifeline(ASSERT);

			// Verify the old visuals
			mode.verify(getExecutionEditPart(), isBounded(isNear(getGrabX() - EXEC_WIDTH, 5),
					isNear(getGrabY()), is(EXEC_WIDTH), is(EXEC_HEIGHT)));

			// And the old semantics
			MExecution exec = requireExecution();
			mode.verify("Owner not reverted", exec.getOwner(), is(requireOriginalReceiver()));
			MOccurrence<?> finish = verifying(mode, exec.getFinish(), "No finish occurrence");
			mode.verify("Finish coverage lost", finish.getCovered().isPresent(), is(true));
			MLifeline covered = finish.getCovered().get();
			mode.verify("Finish coverage not reverted", covered, is(requireOriginalReceiver()));
		}

		//
		// Test framework
		//

		EditPart getExecutionEditPart() {
			Optional<EditPart> editPart = Optional.of(messageEP).map(ConnectionEditPart.class::cast)
					.map(ConnectionEditPart::getTarget)
					.filter(ExecutionSpecificationEditPart.class::isInstance);
			return assuming(editPart, "No execution edit-part created");
		}

		//
		// Test framework
		//

		EditPart getReplyEditPart() {
			@SuppressWarnings("unchecked")
			Optional<EditPart> editPart = editor.getDiagramEditPart().getConnections().stream().skip(1)
					.findFirst();
			return assuming(editPart, "No execution edit-part created");
		}
	}

	public static class DeleteMessage extends MessageNoExecution {

		@Override
		protected void switchLifeline(VerificationMode mode) {
			super.switchLifeline(mode);

			// Verify additional details of the new visuals
			EditPart destructionEP = ((ConnectionEditPart)messageEP).getTarget();
			mode.verify(destructionEP, instanceOf(DestructionSpecificationEditPart.class));
			EditPart newLifelineEP = verifying(mode, getBodyEditPart(getReceiver()),
					"New lifeline edit-part not found");
			mode.verify(destructionEP.getParent(), is(newLifelineEP));
		}

		@Override
		protected void undoSwitchLifeline(VerificationMode mode) {
			super.undoSwitchLifeline(mode);

			// Verify additional details of the old visuals
			EditPart destructionEP = ((ConnectionEditPart)messageEP).getTarget();
			mode.verify(destructionEP, instanceOf(DestructionSpecificationEditPart.class));
			EditPart oldLifelineEP = verifying(mode, getBodyEditPart(getOriginalReceiver()),
					"Old lifeline edit-part not found");
			mode.verify(destructionEP.getParent(), is(oldLifelineEP));
		}

		/**
		 * Verify that a destruction cannot be moved to a lifeline that would have occurrences following it
		 */
		@Test
		public void existingOccurrences() {
			// Create an occurrence above on Lifeline3. Don't automate the tool but just
			// force-create it because this isn't the point of the test
			EditPart ll3EP = assuming(getBodyEditPart(getLifeline("Lifeline3")), "Lifeline3 has no body");
			forceCreateNode(ll3EP, SequenceElementTypes.Behavior_Execution_Shape, //
					at(-EXEC_WIDTH / 2, 250), sized(EXEC_WIDTH, EXEC_WIDTH));

			// Creating the occurrence nudged our message
			mesgY = getTargetY(messageEP);

			// Attempt to move the destruction
			editor.with(editor.allowSemanticReordering(),
					() -> editor.moveSelection(at(getGrabX(), mesgY), at(newRecvX, mesgY)));

			// Verify that nothing changed
			assertThat(messageEP, runs(sendX, mesgY, getGrabX(), mesgY));
			EditPart destructionEP = ((ConnectionEditPart)messageEP).getTarget();
			assertThat(destructionEP, instanceOf(DestructionSpecificationEditPart.class));
			EditPart oldLifelineEP = asserting(getBodyEditPart(getOriginalReceiver()),
					"Old lifeline edit-part not found");
			assertThat(destructionEP.getParent(), is(oldLifelineEP));
			MDestruction destruction = asserting(
					as(getInteraction().getElement((Element)destructionEP.getAdapter(EObject.class)),
							MDestruction.class),
					"Not a logical destruction");
			assertThat(asserting(destruction.getCovered(), "No coverage").getName(), is("Lifeline2"));
		}

		//
		// Test framework
		//

		@Override
		IElementType getMessageType() {
			return SequenceElementTypes.Delete_Message_Edge;
		}

		@Override
		int getGrabX() {
			// Note that the message arrow actually penetrates to 1/4 depth; it does not attach
			// to the bounding box of the X shape
			return super.getGrabX() - (DESTRUCTION_WIDTH / 4);
		}

		@Override
		int getNewRecvX() {
			// Note that the message arrow actually penetrates to 1/4 depth; it does not attach
			// to the bounding box of the X shape
			return super.getNewRecvX() - (DESTRUCTION_WIDTH / 4);
		}
	}

	public static class CreateMessage extends MessageNoExecution {

		@Override
		protected void switchLifeline(VerificationMode mode) {
			EditPart lifeline2 = assuming(getHeadEditPart(getOriginalReceiver()), "No lifeline head");
			int createdTop = getTop(lifeline2);
			EditPart lifeline3 = assuming(getHeadEditPart(getReceiver()), "No lifeline head");
			int uncreatedTop = getTop(lifeline3);

			super.switchLifeline(mode);

			// Verify additional details of the new visuals
			mode.verify("Lifeline2 head not moved back to top", lifeline2,
					isBounded(anything(), is(uncreatedTop), anything(), anything()));
			mode.verify("Lifeline3 head not moved down for creation", lifeline3,
					isBounded(anything(), is(createdTop), anything(), anything()));
		}

		@Override
		protected void undoSwitchLifeline(VerificationMode mode) {
			// Recall that this part of the test is after moving the create message
			EditPart lifeline2 = assuming(getHeadEditPart(getOriginalReceiver()), "No lifeline head");
			int uncreatedTop = getTop(lifeline2);
			EditPart lifeline3 = assuming(getHeadEditPart(getReceiver()), "No lifeline head");
			int createdTop = getTop(lifeline3);

			super.undoSwitchLifeline(mode);

			// Verify additional details of the old visuals
			mode.verify("Lifeline2 head not moved back down for creation", lifeline2,
					isBounded(anything(), is(createdTop), anything(), anything()));
			mode.verify("Lifeline3 head not moved back to top", lifeline3,
					isBounded(anything(), is(uncreatedTop), anything(), anything()));
		}

		/**
		 * Verify that a creation cannot be moved to a lifeline that would have occurrences preceding it
		 */
		@Test
		public void existingOccurrences() {
			// Create an occurrence above on Lifeline3. Don't automate the tool but just
			// force-create it because this isn't the point of the test
			EditPart ll3EP = assuming(getBodyEditPart(getLifeline("Lifeline3")), "Lifeline3 has no body");
			forceCreateNode(ll3EP, SequenceElementTypes.Behavior_Execution_Shape, //
					at(-EXEC_WIDTH / 2, 10), sized(EXEC_WIDTH, EXEC_WIDTH));

			// Creating the occurrence nudged our message
			mesgY = getTargetY(messageEP);

			// And (possibly) other lifelines
			EditPart lifeline2 = assuming(getHeadEditPart(getOriginalReceiver()), "No lifeline head");
			int top2 = getTop(lifeline2);
			EditPart lifeline3 = assuming(getHeadEditPart(getReceiver()), "No lifeline head");
			int top3 = getTop(lifeline3);

			// Attempt to move the creation
			editor.with(editor.allowSemanticReordering(),
					() -> editor.moveSelection(at(getGrabX(), mesgY), at(newRecvX, mesgY)));

			// Verify that nothing changed
			assertThat(messageEP, runs(sendX, mesgY, getGrabX(), mesgY));
			assertThat(lifeline2, isBounded(anything(), is(top2), anything(), anything()));
			assertThat(lifeline3, isBounded(anything(), is(top3), anything(), anything()));
			MMessage message = assuming(
					as(getInteraction().getElement((Element)messageEP.getAdapter(EObject.class)),
							MMessage.class),
					"Not a logical message");
			MMessageEnd creation = assuming(message.getReceive(), "No creation end");
			assertThat(asserting(creation.getCovered(), "No coverage").getName(), is("Lifeline2"));
		}

		/**
		 * Verify that a create message can be reoriented to a lifeline head with the same outcome as the
		 * lifeline body.
		 */
		@Test
		public void switchToLifelineHead() {
			EditPart lifeline2 = assuming(getHeadEditPart(getOriginalReceiver()), "No lifeline head");
			int createdTop = getTop(lifeline2);
			EditPart lifeline3 = assuming(getHeadEditPart(getReceiver()), "No lifeline head");
			int uncreatedTop = getTop(lifeline3);

			editor.with(editor.allowSemanticReordering(),
					() -> editor.moveSelection(at(getGrabX(), mesgY), getBounds(lifeline3).getCenter()));

			// Verify the new visuals
			assertThat(messageEP, runs(sendX, mesgY, getNewRecvX(), mesgY));

			// And the semantics
			MMessage msg = getRequestMessage();
			assertThat("Sender changed", msg.getSender(), is(getSender()));
			assertThat("Receiver not changed", msg.getReceiver(), not(getOriginalReceiver()));
			assertThat("Wrong receiver", msg.getReceiver(), is(getReceiver()));

			// Verify additional details of the new visuals
			assertThat("Lifeline2 head not moved back to top", lifeline2,
					isBounded(anything(), is(uncreatedTop), anything(), anything()));
			assertThat("Lifeline3 head not moved down for creation", lifeline3,
					isBounded(anything(), is(createdTop), anything(), anything()));
		}

		//
		// Test framework
		//

		@Override
		int getGrabX() {
			return (super.getGrabX() - (LIFELINE_HEAD_WIDTH / 2)) + 1;
		}

		@Override
		int getNewRecvX() {
			return (super.getNewRecvX() - (LIFELINE_HEAD_WIDTH / 2)) + 1;
		}

		@Override
		IElementType getMessageType() {
			return SequenceElementTypes.Create_Message_Edge;
		}
	}

	@ModelResource("execution-busy.di")
	public static class ExecutionSpanningOccurrences extends MessageNoExecution {

		private static final int LIFELINE_4_BODY_X = 596;

		private final int m2Y = 173;

		private final int m3Y = 198;

		private final int m4Y = 223;

		@Override
		protected void switchLifeline(VerificationMode mode) {
			super.switchLifeline(mode);

			// Note that the new receive X is the *left* side of the execution
			int execRight = getNewRecvX() + EXEC_WIDTH;

			// Verify visuals of messages spanned by the execution
			MMessage m2 = requireMessage("m2");
			EditPart m2EP = requireEditPart(m2);
			mode.verify("m2 not a self-message", m2EP,
					runs(execRight, m2Y, execRight, m2Y + SELF_MESSAGE_HEIGHT));
			MMessage m3 = requireMessage("m3");
			EditPart m3EP = requireEditPart(m3);
			mode.verify(m3EP, runs(execRight, m3Y, LIFELINE_4_BODY_X, m3Y));
			MMessage m4 = requireMessage("m4");
			EditPart m4EP = requireEditPart(m4);
			mode.verify(m4EP, runs(LIFELINE_4_BODY_X, m4Y, execRight, m4Y));

			// And the semantics
			mode.verify("Wrong coverage of m2 send", asserting(m2.getSend(), "m2 lost its send").getCovered(),
					is(getReceiver()));
			mode.verify("Coverage of m2 receive broken",
					asserting(m2.getReceive(), "m2 lost its recevive").getCovered(), is(getReceiver()));
			mode.verify("Wrong coverage of m3 send", asserting(m3.getSend(), "m3 lost its send").getCovered(),
					is(getReceiver()));
			mode.verify("Wrong coverage of m4 receive",
					asserting(m4.getReceive(), "m4 lost its receive").getCovered(), is(getReceiver()));
		}

		@Override
		protected void undoSwitchLifeline(VerificationMode mode) {
			super.undoSwitchLifeline(mode);

			int execRight = LIFELINE_2_BODY_X + (EXEC_WIDTH / 2);

			// Verify old visuals of messages spanned by the execution
			MMessage m2 = requireMessage("m2");
			EditPart m2EP = requireEditPart(m2);
			mode.verify("m2 is not routed correctly", m2EP, runs(execRight, m2Y, LIFELINE_3_BODY_X, m2Y));
			MMessage m3 = requireMessage("m3");
			EditPart m3EP = requireEditPart(m3);
			mode.verify(m3EP, runs(execRight, m3Y, LIFELINE_4_BODY_X, m3Y));
			MMessage m4 = requireMessage("m4");
			EditPart m4EP = requireEditPart(m4);
			mode.verify(m4EP, runs(LIFELINE_4_BODY_X, m4Y, execRight, m4Y));

			// And the semantics
			mode.verify("Wrong coverage of m2 send", asserting(m2.getSend(), "m2 lost its send").getCovered(),
					is(getOriginalReceiver()));
			mode.verify("Coverage of m2 receive broken",
					asserting(m2.getReceive(), "m2 lost its recevive").getCovered(), is(getReceiver()));
			mode.verify("Wrong coverage of m3 send", asserting(m3.getSend(), "m3 lost its send").getCovered(),
					is(getOriginalReceiver()));
			mode.verify("Wrong coverage of m4 receive",
					asserting(m4.getReceive(), "m4 lost its receive").getCovered(),
					is(getOriginalReceiver()));
		}

		//
		// Test framework
		//

		@Override
		public void createMessage() {
			// The connections all exist already. Just locate the request message
			MMessage request = requireMessage("request");
			messageEP = requireEditPart(request);
			assumeThat(messageEP, runs(sendX, mesgY, getGrabX(), mesgY));
		}

		@Override
		int getGrabX() {
			return super.getGrabX() - (EXEC_WIDTH / 2);
		}

		@Override
		int getNewRecvX() {
			return super.getNewRecvX() - (EXEC_WIDTH / 2);
		}

	}

	@RunWith(JUnit4.class)
	public static class ExecutionSwitchLifeline extends LifelineSwitchingUITest {

		@ClassRule
		public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs().createRepliesForSyncCalls();

		@Test
		public void switchLifeline() {
			switchLifeline(ASSERT);
		}

		protected void switchLifeline(VerificationMode mode) {
			editor.with(editor.allowSemanticReordering(),
					() -> editor.moveSelection(at(getGrabX(), getGrabY()), at(getReleaseX(), getReleaseY())));

			// Verify the new visuals
			mode.verify(messageEP, runs(sendX, mesgY, getNewRecvX(), getNewRecvY()));

			// And the semantics
			MMessage msg = getRequestMessage();
			mode.verify("Sender changed", msg.getSender(), is(getSender()));
			mode.verify("Receiver not changed", msg.getReceiver(), not(getOriginalReceiver()));
			mode.verify("Wrong receiver", msg.getReceiver(), is(getReceiver()));

			mode.verify(getExecutionEditPart(), isBounded(isNear(getReleaseX(), 5), isNear(getNewRecvY()),
					is(EXEC_WIDTH), is(EXEC_HEIGHT)));

			// And the semantics
			MExecution exec = requireExecution();
			mode.verify("Owner not changed", exec.getOwner(), not(requireOriginalReceiver()));
			mode.verify("Wrong owner", exec.getOwner(), is(requireReceiver()));
			MOccurrence<?> finish = verifying(mode, exec.getFinish(), "No finish occurrence");
			mode.verify("Finish coverage lost", finish.getCovered().isPresent(), is(true));
			MLifeline covered = finish.getCovered().get();
			mode.verify("Finish coverage not changed", covered, not(requireOriginalReceiver()));
			mode.verify("Wrong finish coverage", covered, is(requireReceiver()));

			// check the reply
			MMessage reply = getReplyMessage();
			mode.verify("Sender changed", reply.getSender(), is(getSender()));
			mode.verify("Receiver not changed", reply.getReceiver(), not(getOriginalReceiver()));
			mode.verify("Wrong receiver", reply.getReceiver(), is(getReceiver()));
			mode.verify(getReplyEditPart(), runs(getNewRecvX(), getNewRecvY() + EXEC_HEIGHT, getReleaseX(),
					getNewRecvY() + EXEC_HEIGHT + SELF_MESSAGE_HEIGHT));
		}

		@Override
		public void createMessage() {
			messageEP = createConnection(getMessageType(), at(getInitialSenderX(), getInitialSenderY()),
					at(getInitialReceiverX(), getInitialReceiverY()));
		}

		@Override
		int getNewRecvX() {
			return sendX + EXEC_WIDTH / 2;
		}

		@Override
		int getNewRecvY() {
			return mesgY + 20;
		}

		@Override
		int getGrabX() {
			return super.getGrabX();
		}

		@Override
		int getGrabY() {
			return mesgY + EXEC_HEIGHT / 2;
		}

		@Override
		public int getReleaseY() {
			return getGrabY();
		}

		@Override
		public int getReleaseX() {
			return sendX;
		}

		@Override
		Optional<MLifeline> getReceiver() {
			return getLifeline("Lifeline1");
		}

		@Override
		MLifeline requireReceiver() {
			return requireLifeline("Lifeline1");
		}

		//
		// Test framework
		//

		EditPart getExecutionEditPart() {
			Optional<EditPart> editPart = Optional.of(messageEP).map(ConnectionEditPart.class::cast)
					.map(ConnectionEditPart::getTarget)
					.filter(ExecutionSpecificationEditPart.class::isInstance);
			return assuming(editPart, "No execution edit-part created");
		}

		EditPart getReplyEditPart() {
			@SuppressWarnings("unchecked")
			Optional<EditPart> editPart = editor.getDiagramEditPart().getConnections().stream().skip(1)
					.findFirst();
			return assuming(editPart, "No execution edit-part created");
		}

	}
	//
	// Test framework
	//

	@Before
	public void createMessage() {
		messageEP = createConnection(getMessageType(), at(getInitialSenderX(), getInitialSenderY()),
				at(getInitialReceiverX(), getInitialReceiverY()));

		if (moveTarget()) {
			assumeThat(messageEP, runs(getInitialSenderX(), getInitialSenderY(), getGrabX(), getGrabY()));
		} else {
			assumeThat(messageEP, runs(getGrabX(), getGrabY(), getInitialReceiverX(), getInitialReceiverY()));
		}

	}

	protected boolean moveTarget() {
		return true;
	}

	protected int getInitialReceiverY() {
		return mesgY;
	}

	protected int getInitialReceiverX() {
		return recvX;
	}

	protected int getInitialSenderY() {
		return mesgY;
	}

	protected int getInitialSenderX() {
		return sendX;
	}

	public int getReleaseX() {
		return newRecvX;
	}

	public int getReleaseY() {
		return mesgY;
	}

	IElementType getMessageType() {
		return SequenceElementTypes.Sync_Message_Edge;
	}

	int getGrabX() {
		return recvX;
	}

	int getGrabY() {
		return mesgY;
	}

	int getNewRecvX() {
		return newRecvX;
	}

	int getNewRecvY() {
		return mesgY;
	}

	MInteraction getInteraction() {
		return assuming(editor.getSequenceDiagram().map(MInteraction::getInstance), "No logical model");
	}

	MMessage getRequestMessage() {
		return getInteraction().getMessages().get(0);
	}

	MMessage getReplyMessage() {
		return getInteraction().getMessages().get(1);
	}

	Optional<MLifeline> getLifeline(String name) {
		return getInteraction().getLifelines().stream()
				.filter(where(SequenceDiagramPackage.Literals.MELEMENT__NAME, name)).findAny();
	}

	MLifeline requireLifeline(String name) {
		return asserting(getLifeline(name), "No such lifeline: " + name);
	}

	Optional<MLifeline> getSender() {
		return getLifeline("Lifeline1");
	}

	MLifeline requireSender() {
		return requireLifeline("Lifeline1");
	}

	Optional<MLifeline> getReceiver() {
		return getLifeline("Lifeline3");
	}

	MLifeline requireReceiver() {
		return requireLifeline("Lifeline3");
	}

	Optional<MLifeline> getOriginalReceiver() {
		return getLifeline("Lifeline2");
	}

	MLifeline requireOriginalReceiver() {
		return requireLifeline("Lifeline2");
	}

	Optional<MExecution> getExecution() {
		return getRequestMessage().getReceive().flatMap(MOccurrence::getStartedExecution);
	}

	MExecution requireExecution() {
		return asserting(getExecution(), "No logical model execution");
	}

	Optional<MMessage> getMessage(String name) {
		return getInteraction().getMessages().stream()
				.filter(where(SequenceDiagramPackage.Literals.MELEMENT__NAME, name)).findAny();
	}

	MMessage requireMessage(String name) {
		return asserting(getMessage(name), "No such message: " + name);
	}

	EditPart requireEditPart(MElement<? extends Element> element) {
		View notationView = assuming(as(element.getDiagramView(), View.class),
				"No notation view for " + element);
		EditPart result = DiagramEditPartsUtil.getEditPartFromView(notationView, editor.getDiagramEditPart());
		assumeThat("No edit-part for " + element, result, notNullValue());
		return result;
	}

	EditPart forceCreateNode(EditPart parent, IElementType type, Point location, Dimension size) {
		CreateViewAndElementRequest create = new CreateViewAndElementRequest(type,
				editor.getDiagramEditPart().getDiagramPreferencesHint());
		create.setLocation(location);
		create.setSize(size);

		EditPart target = parent.getTargetEditPart(create);
		if (target == null) {
			target = parent;
		}

		Command command = target.getCommand(create);
		assumeThat("Creation command not executable", command.canExecute(), is(true));
		editor.getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack().execute(command);

		editor.flushDisplayEvents();

		View createdView = (View)create.getViewAndElementDescriptor().getAdapter(View.class);
		assumeThat("No view created", createdView, notNullValue());

		EditPart result = (EditPart)editor.getDiagramEditPart().getViewer().getEditPartRegistry()
				.get(createdView);
		assumeThat("No edit-part registered for created view", result, notNullValue());
		return result;
	}

	Optional<EditPart> getHeadEditPart(Optional<MLifeline> lifeline) {
		return lifeline.map(ll -> DiagramEditPartsUtil.getChildByEObject(ll.getElement(),
				editor.getDiagramEditPart(), false));
	}

	Optional<EditPart> getBodyEditPart(Optional<MLifeline> lifeline) {
		return as(getHeadEditPart(lifeline), IGraphicalEditPart.class)
				.map(gep -> gep.getChildBySemanticHint(ViewTypes.LIFELINE_BODY));
	}

	static <T> T assuming(Optional<T> value, String message) {
		return value.orElseThrow(() -> new AssumptionViolatedException(message));
	}

	static <T> T asserting(Optional<T> value, String message) {
		return value.orElseThrow(() -> new AssertionError(message));
	}

	static <T> T verifying(VerificationMode mode, Optional<T> value, String message) {
		switch (mode) {
			case ASSERT:
				return asserting(value, message);
			case ASSUME:
				return assuming(value, message);
			default:
				return value.orElse(null);
		}
	}
}
