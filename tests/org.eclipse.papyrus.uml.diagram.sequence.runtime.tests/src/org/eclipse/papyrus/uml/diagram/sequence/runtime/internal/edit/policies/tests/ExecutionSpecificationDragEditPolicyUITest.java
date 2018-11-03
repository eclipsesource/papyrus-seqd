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
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isBounded;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
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
		assertThat("Request message not moved", requestEP,
				runs(isPoint(requestSend), isPoint(requestRecv)));
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
	 * Verify that a move that would require semantic re-ordering is blocked without
	 * the keyboard modifier.
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
	 * Verify that a move that requires semantic re-ordering is allowed with the
	 * keyboard modifier.
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

	//
	// Nested types
	//

	@Before
	public void findFixtures() {
		exec = editor.getElement("kitchen-sink::Scenario::exec1", ExecutionSpecification.class);
		execEP = getChildByEObject(exec, editor.getDiagramEditPart(), false);

		request = editor.getElement("kitchen-sink::Scenario::m1", Message.class);
		requestEP = getChildByEObject(request, editor.getDiagramEditPart(), true);
		reply = editor.getElement("kitchen-sink::Scenario::m2", Message.class);
		replyEP = getChildByEObject(reply, editor.getDiagramEditPart(), true);
		exec2 = editor.getElement("kitchen-sink::Scenario::exec2", ExecutionSpecification.class);
		exec2EP = getChildByEObject(exec2, editor.getDiagramEditPart(), false);
	}

}
