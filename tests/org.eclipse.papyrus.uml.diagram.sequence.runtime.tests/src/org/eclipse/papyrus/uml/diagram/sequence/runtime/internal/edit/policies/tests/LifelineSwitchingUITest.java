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
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.where;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.isNear;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assume.assumeThat;

import java.util.Optional;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.DestructionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.ExecutionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.LightweightSeqDPrefs;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
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
 * Integration test cases for use cases in which occurrences are moved from one
 * lifeline to another.
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

	// Width of an execution specification
	private static final int EXEC_WIDTH = 10;

	// Vertical gap between send and receive of a self-message
	private static final int SELF_MESSAGE_HEIGHT = 20;

	private static final int INITIAL_Y = 145;

	protected final int sendX;

	protected final int recvX;

	protected final int newRecvX;

	protected final int mesgY;

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
					() -> editor.moveSelection(at(getGrabX(), mesgY), at(newRecvX, mesgY)));

			// Verify the new visuals
			mode.verify(messageEP, runs(sendX, mesgY, getNewRecvX(), mesgY));

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
			mode.verify(messageEP, runs(sendX, mesgY, getGrabX(), mesgY));

			// And the old semantics
			MMessage msg = getRequestMessage();
			mode.verify("Sender changed", msg.getSender(), is(getSender()));
			mode.verify("Receiver not reverted", msg.getReceiver(), not(getReceiver()));
			mode.verify("Wrong receiver", msg.getReceiver(), is(getOriginalReceiver()));
		}
	}

	public static class MessageNoReply extends MessageNoExecution {

		@ClassRule
		public static LightweightSeqDPrefs prefs = new LightweightSeqDPrefs()
				.dontCreateRepliesForSyncCalls();

		@Override
		protected void switchLifeline(VerificationMode mode) {
			super.switchLifeline(ASSUME);

			// Verify the new visuals
			mode.verify(getExecutionEditPart(),
					isBounded(isNear(getNewRecvX(), 5), isNear(mesgY), anything(), anything()));

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
			@SuppressWarnings("unchecked")
			Optional<EditPart> editPart = Optional.of(messageEP).map(ConnectionEditPart.class::cast)
					.map(ConnectionEditPart::getTarget).flatMap(lifeline -> lifeline.getChildren().stream()
							.filter(ExecutionSpecificationEditPart.class::isInstance).findFirst());
			return assuming(editPart, "No execution edit-part created");
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

	public static class DeleteMessage extends MessageNoExecution {

		@Override
		protected void switchLifeline(VerificationMode mode) {
			super.switchLifeline(mode);

			// Verify additional details of the new visuals
			EditPart destructionEP = ((ConnectionEditPart) messageEP).getTarget();
			mode.verify(destructionEP, instanceOf(DestructionSpecificationEditPart.class));
			EditPart newLifelineEP = verifying(mode, getBodyEditPart(getReceiver()),
					"New lifeline edit-part not found");
			mode.verify(destructionEP.getParent(), is(newLifelineEP));
		}

		@Override
		protected void undoSwitchLifeline(VerificationMode mode) {
			super.undoSwitchLifeline(mode);

			// Verify additional details of the old visuals
			EditPart destructionEP = ((ConnectionEditPart) messageEP).getTarget();
			mode.verify(destructionEP, instanceOf(DestructionSpecificationEditPart.class));
			EditPart oldLifelineEP = verifying(mode, getBodyEditPart(getOriginalReceiver()),
					"Old lifeline edit-part not found");
			mode.verify(destructionEP.getParent(), is(oldLifelineEP));
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
			return super.getGrabX() - (DESTRUCTION_WIDTH / 2);
		}

		@Override
		int getNewRecvX() {
			return super.getNewRecvX() - (DESTRUCTION_WIDTH / 2);
		}

		Optional<EditPart> getBodyEditPart(Optional<MLifeline> lifeline) {
			return as(lifeline.map(ll -> DiagramEditPartsUtil.getChildByEObject(ll.getElement(),
					editor.getDiagramEditPart(), false)), IGraphicalEditPart.class)
							.map(gep -> gep.getChildBySemanticHint(ViewTypes.LIFELINE_BODY));
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

			int execRight = getNewRecvX() + (EXEC_WIDTH / 2);

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
			mode.verify("Wrong coverage of m2 send",
					asserting(m2.getSend(), "m2 lost its send").getCovered(), is(getReceiver()));
			mode.verify("Coverage of m2 receive broken",
					asserting(m2.getReceive(), "m2 lost its recevive").getCovered(), is(getReceiver()));
			mode.verify("Wrong coverage of m3 send",
					asserting(m3.getSend(), "m3 lost its send").getCovered(), is(getReceiver()));
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
			mode.verify("m2 is not routed correctly", m2EP, runs(execRight, m2Y, getNewRecvX(), m2Y));
			MMessage m3 = requireMessage("m3");
			EditPart m3EP = requireEditPart(m3);
			mode.verify(m3EP, runs(execRight, m3Y, LIFELINE_4_BODY_X, m3Y));
			MMessage m4 = requireMessage("m4");
			EditPart m4EP = requireEditPart(m4);
			mode.verify(m4EP, runs(LIFELINE_4_BODY_X, m4Y, execRight, m4Y));

			// And the semantics
			mode.verify("Wrong coverage of m2 send",
					asserting(m2.getSend(), "m2 lost its send").getCovered(), is(getOriginalReceiver()));
			mode.verify("Coverage of m2 receive broken",
					asserting(m2.getReceive(), "m2 lost its recevive").getCovered(), is(getReceiver()));
			mode.verify("Wrong coverage of m3 send",
					asserting(m3.getSend(), "m3 lost its send").getCovered(), is(getOriginalReceiver()));
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
	}

	//
	// Test framework
	//

	@Before
	public void createMessage() {
		messageEP = createConnection(getMessageType(), at(sendX, mesgY), at(recvX, mesgY));

		assumeThat(messageEP, runs(sendX, mesgY, getGrabX(), mesgY));
	}

	IElementType getMessageType() {
		return SequenceElementTypes.Sync_Message_Edge;
	}

	int getGrabX() {
		return recvX;
	}

	int getNewRecvX() {
		return newRecvX;
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
		EditPart result = DiagramEditPartsUtil.getEditPartFromView(notationView,
				editor.getDiagramEditPart());
		assumeThat("No edit-part for " + element, result, notNullValue());
		return result;
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
