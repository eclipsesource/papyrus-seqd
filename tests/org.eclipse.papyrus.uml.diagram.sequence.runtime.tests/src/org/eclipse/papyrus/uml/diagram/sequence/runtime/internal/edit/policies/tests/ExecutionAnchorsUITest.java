/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager.MODIFIER_NO_SNAPPING;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.MessageEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@ModelResource("execution-busy.di")
@Maximized
public class ExecutionAnchorsUITest extends AbstractGraphicalEditPolicyUITest {

	@Rule
	public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture("Lifeline1")
	private Lifeline lifeline1;

	@AutoFixture
	private EditPart lifeline1EP;

	@AutoFixture("Lifeline2")
	private Lifeline lifeline2;

	@AutoFixture
	private EditPart lifeline2EP;

	@AutoFixture("Lifeline3")
	private Lifeline lifeline3;

	@AutoFixture
	private EditPart lifeline3EP;

	@AutoFixture("Lifeline4")
	private Lifeline lifeline4;

	@AutoFixture
	private EditPart lifeline4EP;

	@AutoFixture("request")
	private Message request;

	@AutoFixture
	private EditPart requestEP;

	@AutoFixture("m2")
	private Message m2;

	@AutoFixture
	private EditPart m2EP;

	@AutoFixture("m4")
	private Message m4;

	@AutoFixture
	private EditPart m4EP;

	@AutoFixture("reply")
	private Message reply;

	@AutoFixture
	private EditPart replyEP;

	@AutoFixture("Execution1")
	private ExecutionSpecification execution1;

	@AutoFixture
	private EditPart execution1EP;

	private Point request_source;

	private Point m2_source;

	private Point reply_source;

	private Point lifeline2_header_center;

	private Point lifeline1_header_center;

	private Point request_target;

	private Point m2_target;

	private Point reply_target;

	private Point m4_source;

	private Point m4_target;

	@Before
	public void findFixtures() {
		request_source = getSource(requestEP);
		request_target = getTarget(requestEP);

		m2_source = getSource(m2EP);
		m2_target = getTarget(m2EP);

		m4_source = getSource(m4EP);
		m4_target = getTarget(m4EP);

		reply_source = getSource(replyEP);
		reply_target = getTarget(replyEP);

		lifeline1_header_center = getBounds(lifeline1EP).getCenter();
		lifeline2_header_center = getBounds(lifeline2EP).getCenter();
	}

	@After
	public void checkAfter() {
		editor.undo();
		editor.forceRefresh();
		editor.flushDisplayEvents();
		assertEquals(request_source, getSource(requestEP));
		assertEquals(request_target, getTarget(requestEP));

		assertEquals(request_source, getSource(requestEP));
		assertEquals(request_target, getTarget(requestEP));

		assertEquals(m2_source, getSource(m2EP));
		assertEquals(m2_target, getTarget(m2EP));

		assertEquals(m4_source, getSource(m4EP));
		assertEquals(m4_target, getTarget(m4EP));
	}

	@Test
	public void moveLifeline2ToRight() {
		/* action */
		// move between Lifeline3 & Lifeline4
		Point end = getBounds(lifeline3EP).getRight().translate(30, 0);
		editor.with(editor.allowSemanticReordering(),
				() -> editor.moveSelection(lifeline2_header_center, end));

		/* check new state */
		Point new_lifeline2_header_center = getBounds(lifeline2EP).getCenter().getCopy();
		// Do not depend on good implementation for moving lifeline... (5 picels offset
		// at the time this test was written)
		int lifeline2Offset = new_lifeline2_header_center.x() - lifeline2_header_center.x;
		// Do not depend on good implementation for moving lifeline... (5 picels offset
		// at the time this test was written)

		Point new_request_target = getTarget(requestEP);
		// check the anchor has moved on the right place...
		assertThat(new_request_target, equalTo(request_target.getCopy().translate(lifeline2Offset, 0)));

		Point new_m2_source = getSource(m2EP);
		// check the anchor has moved on the right place...
		assertThat(new_m2_source, equalTo(m2_source.getCopy().translate(lifeline2Offset - EXEC_WIDTH, 0)));

		Point new_m4_target = getTarget(m4EP);
		// check the anchor has moved on the right place...
		assertThat(new_m4_target, equalTo(m4_target.getCopy().translate(lifeline2Offset, 0)));

		Point new_reply_source = getSource(replyEP);
		// check the anchor has moved on the right place...
		assertThat(new_reply_source, equalTo(reply_source.getCopy().translate(lifeline2Offset, 0)));
	}

	@Test
	public void moveLifeline1ToRight() {
		/* action */
		Point end = getBounds(lifeline2EP).getRight().translate(30, 0);
		editor.with(editor.allowSemanticReordering(),
				() -> editor.moveSelection(lifeline1_header_center, end));

		/* check new state */
		Point new_lifeline1_header_center = getBounds(lifeline1EP).getCenter().getCopy();
		int lifeline1Offset = new_lifeline1_header_center.x() - lifeline1_header_center.x;

		Point new_request_target = getTarget(requestEP);
		assertThat(new_request_target, equalTo(request_target.getCopy().translate(EXEC_WIDTH, 0)));

		Point new_m2_source = getSource(m2EP);
		assertThat(new_m2_source, equalTo(m2_source)); // no move

		Point new_m4_target = getTarget(m4EP);
		assertThat(new_m4_target, equalTo(m4_target)); // no move

		Point new_reply_source = getSource(replyEP);
		assertThat(new_reply_source, equalTo(reply_source.getCopy().translate(EXEC_WIDTH, 0)));
		Point new_reply_target = getTarget(replyEP);
		assertThat(new_reply_target, equalTo(reply_target.getCopy().translate(lifeline1Offset, 0)));
	}

	@Test
	public void checkAnchorOnNudge() {
		final int OFFSET = 25; // ensure no padding (20 above, 5 below)
		// message will be created below m4, in inverse direction (from exec1 to lifeline 4)
		Point mouseStart = m4_target.getCopy().translate(-2, OFFSET);
		Point mouseEnd = m4_source.getCopy().translate(0, OFFSET);

		MessageEditPart newMessage = (MessageEditPart)editor.with(editor.modifierKey(MODIFIER_NO_SNAPPING),
				() -> createConnection(SequenceElementTypes.Async_Message_Edge, mouseStart, mouseEnd));

		assertThat(getSource(newMessage), isPoint(m4_target.getCopy().translate(0, OFFSET)));
		assertThat(getTarget(newMessage), isPoint(mouseEnd));
	}

	@Test
	public void checkAnchorOnNudgeWithPadding() {
		final int OFFSET = 20; // ensure no padding (20 above, 5 below)
		// message will be created below m4, in inverse direction (from exec1 to lifeline 4)
		Point mouseStart = m4_target.getCopy().translate(-2, OFFSET);
		Point mouseEnd = m4_source.getCopy().translate(0, OFFSET);

		MessageEditPart newMessage = (MessageEditPart)editor.with(editor.modifierKey(MODIFIER_NO_SNAPPING),
				() -> createConnection(SequenceElementTypes.Async_Message_Edge, mouseStart, mouseEnd));

		// Element was adding a bit below due to padding
		assertThat(getSource(newMessage), isPoint(m4_target.getCopy().translate(0, 25)));
		assertThat(getTarget(newMessage), isPoint(mouseEnd.getCopy().translate(0, 5))); // offest from padding
	}
}
