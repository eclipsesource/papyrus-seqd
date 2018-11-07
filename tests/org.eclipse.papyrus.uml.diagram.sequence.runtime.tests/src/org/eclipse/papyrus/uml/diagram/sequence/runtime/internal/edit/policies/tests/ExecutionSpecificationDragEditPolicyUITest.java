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

import static org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil.getChildByEObject;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.is;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isRect;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isAt;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isBounded;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gt;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gte;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for the {@code ExecutionSpecificationDragEditPolicy} class.
 *
 * @author Christian W. Damus
 */
@ModelResource("kitchen-sink.di")
@Maximized
public class ExecutionSpecificationDragEditPolicyUITest extends AbstractGraphicalEditPolicyUITest {

	@SuppressWarnings("hiding")
	@ClassRule
	public static TestRule TOLERANCE = GEFMatchers.defaultTolerance(1);

	private ExecutionSpecification exec;

	private EditPart execEP;

	private Message request;

	private EditPart requestEP;

	private Message reply;

	private EditPart replyEP;

	private ExecutionSpecification exec2;

	private EditPart exec2EP;

	private Lifeline l1;

	private EditPart l1EP;

	private Lifeline l2;

	private EditPart l2EP;

	private Message m3;

	private EditPart m3EP;

	/**
	 * Initializes me.
	 */
	public ExecutionSpecificationDragEditPolicyUITest() {
		super();
	}

	@Test
	public void moveExecutionUp() {
		moveExecution(-50);
	}

	void moveExecution(int delta) {
		Point requestSend = getSource(requestEP);
		Point requestRecv = getTarget(requestEP);
		Point replySend = getSource(replyEP);
		Point replyRecv = getTarget(replyEP);
		Rectangle execBounds = getBounds(execEP);

		// First, select the execution to activate selection handles
		Point grabAt = getCenter(execEP);
		editor.select(grabAt);

		Point dropAt = new Point(grabAt.x(), grabAt.y() + delta);
		editor.drag(grabAt, dropAt);

		execBounds.translate(0, delta);
		requestSend.translate(0, delta);
		requestRecv.translate(0, delta);
		replySend.translate(0, delta);
		replyRecv.translate(0, delta);

		assertThat("Execution not moved", execEP, isBounded(isRect(execBounds)));
		assertThat("Request message not moved", requestEP, runs(isPoint(requestSend), isPoint(requestRecv)));
		assertThat("Reply message not moved", replyEP, runs(isPoint(replySend), isPoint(replyRecv)));
	}

	@Test
	public void moveExecutionDown() {
		// Move the execution close enough to the next one below that it must be nudged
		moveExecution(35);

		Rectangle execBounds = getBounds(execEP);

		int padding = 10;
		assertThat("Next execution not nudged", exec2EP,
				isBounded(anything(), is(execBounds.bottom() + padding), anything(), anything()));
	}

	/**
	 * Verify that a move that would require semantic re-ordering is blocked without the keyboard modifier.
	 */
	@Test
	public void attemptMoveWithSemanticReordering() {
		int delta = 60;
		Rectangle execBounds = getBounds(execEP);

		// First, select the execution to activate selection handles
		Point grabAt = getCenter(execEP);
		editor.select(grabAt);

		Point dropAt = new Point(grabAt.x(), grabAt.y() + delta);
		editor.drag(grabAt, dropAt);

		assertThat("Execution moved", execEP, isBounded(isRect(execBounds)));
	}

	/**
	 * Verify that a move that requires semantic re-ordering is allowed with the keyboard modifier.
	 */
	@Test
	public void moveWithSemanticReordering() {
		int delta = 60;
		Rectangle execBounds = getBounds(execEP);

		// First, select the execution to activate selection handles
		Point grabAt = getCenter(execEP);
		editor.select(grabAt);

		Point dropAt = new Point(grabAt.x(), grabAt.y() + delta);
		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

		execBounds.translate(0, delta);
		assertThat("Execution not moved", execEP, isBounded(isRect(execBounds)));
	}

	/**
	 * Verify that a change of lifeline is blocked without the keyboard modifier.
	 */
	@Test
	public void attemptChangeLifeline() {
		int l1X = getBounds(l1EP).getCenter().x();
		int l2X = getBounds(l2EP).getCenter().x();
		int exec2Top = getTop(exec2EP);

		// First, select the execution to activate selection handles
		Point grabAt = getCenter(exec2EP);
		editor.select(grabAt);

		Point dropAt = new Point(l2X, grabAt.y());
		editor.drag(grabAt, dropAt);
		// Drag-and-drop creates a new edit-part
		exec2EP = getChildByEObject(exec2, editor.getDiagramEditPart(), false);

		assertThat("Execution was moved", exec2EP, isAt(l1X - (EXEC_WIDTH / 2), exec2Top));
	}

	/**
	 * Verify drag and drop of execution specification to another lifeline.
	 */
	@Test
	public void changeLifeline() {
		int l2X = getBounds(l2EP).getCenter().x();
		int exec2Top = getTop(exec2EP);

		// First, select the execution to activate selection handles
		Point grabAt = getCenter(exec2EP);
		editor.select(grabAt);

		Point dropAt = new Point(l2X, grabAt.y());
		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));
		// Drag-and-drop creates a new edit-part
		exec2EP = getChildByEObject(exec2, editor.getDiagramEditPart(), false);

		assertThat("Execution not moved", exec2EP, isAt(l2X - (EXEC_WIDTH / 2), exec2Top));

		PointList m3Points = getPoints(m3EP);
		assertThat("Not a self-message", m3Points.size(), gt(2));

		Point m3Send = m3Points.getFirstPoint();
		Point m3Recv = m3Points.getLastPoint();
		assertThat(m3Send, isPoint(is(l2X + (EXEC_WIDTH / 2)), anything()));
		assertThat(m3Recv, isPoint(is(l2X + (EXEC_WIDTH / 2)), gte(m3Send.y() + 20)));
	}

	//
	// Nested types
	//

	@Before
	public void findFixtures() {
		exec = getElement("exec1", ExecutionSpecification.class);
		execEP = getChildByEObject(exec, editor.getDiagramEditPart(), false);

		request = getElement("m1", Message.class);
		requestEP = getChildByEObject(request, editor.getDiagramEditPart(), true);
		reply = getElement("m2", Message.class);
		replyEP = getChildByEObject(reply, editor.getDiagramEditPart(), true);
		exec2 = getElement("exec2", ExecutionSpecification.class);
		exec2EP = getChildByEObject(exec2, editor.getDiagramEditPart(), false);

		l1 = getElement("L1", Lifeline.class);
		l1EP = getChildByEObject(l1, editor.getDiagramEditPart(), false);
		l2 = getElement("L2", Lifeline.class);
		l2EP = getChildByEObject(l2, editor.getDiagramEditPart(), false);
		m3 = getElement("m3", Message.class);
		m3EP = getChildByEObject(m3, editor.getDiagramEditPart(), true);
	}

	protected <T extends NamedElement> T getElement(String name, Class<T> type) {
		return editor.getElement("kitchen-sink::Scenario::" + name, type);
	}
}
