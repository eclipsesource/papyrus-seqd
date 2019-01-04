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
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.sized;
import static org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes.LIFELINE_BODY;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gt;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gte;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.lt;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture.VisualID;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * Test cases for the {@code ExecutionSpecificationDragEditPolicy} class.
 */
@RunWith(Enclosed.class)
public class ExecutionSpecificationDragEditPolicyUITest {

	@ClassRule
	public static TestRule TOLERANCE = GEFMatchers.defaultTolerance(1);

	/**
	 * Initializes me.
	 */
	public ExecutionSpecificationDragEditPolicyUITest() {
		super();
	}

	//
	// Nested test suites
	//

	/**
	 * Basic move/resize drag-and-drop use cases.
	 */
	@ModelResource("kitchen-sink.di")
	@Maximized
	public static class Basic extends AbstractGraphicalEditPolicyUITest {
		@Rule
		public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

		@AutoFixture("exec1")
		private ExecutionSpecification exec;

		@AutoFixture
		private EditPart execEP;

		@AutoFixture("m1")
		private Message request;

		@AutoFixture
		private EditPart requestEP;

		@AutoFixture("m2")
		private Message reply;

		@AutoFixture
		private EditPart replyEP;

		@AutoFixture
		private ExecutionSpecification exec2;

		@AutoFixture
		private EditPart exec2EP;

		@AutoFixture("L1")
		private Lifeline l1;

		@AutoFixture
		@VisualID(LIFELINE_BODY)
		private EditPart l1EP;

		@AutoFixture("L2")
		private Lifeline l2;

		@AutoFixture
		@VisualID(LIFELINE_BODY)
		private EditPart l2EP;

		@AutoFixture
		private Message m3;

		@AutoFixture
		private EditPart m3EP;

		/**
		 * Initializes me.
		 */
		public Basic() {
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
		 * Per <a href="https://github.com/eclipsesource/papyrus-seqd/issues/26">Issue #26</a>, attempt to
		 * cross over another execution and verify that it's just bumped out of the way.
		 */
		@Test
		public void attemptMoveExecutionAcrossAnother() {
			int delta = 60;
			Rectangle execBounds = getBounds(execEP);
			Rectangle exec2Bounds = getBounds(exec2EP);

			// First, select the execution to activate selection handles
			Point grabAt = getCenter(execEP);
			editor.select(grabAt);

			Point dropAt = new Point(grabAt.x(), grabAt.y() + delta);
			editor.drag(grabAt, dropAt);
			execBounds.translate(0, delta);

			assertThat("Execution not moved", execEP, isBounded(isRect(execBounds)));

			assertThat("Other execution not bumped out of the way", exec2EP,
					isAt(GEFMatchers.is(exec2Bounds.x()), gt(execBounds.bottom())));

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

	}

	/**
	 * Basic move/resize drag-and-drop use cases.
	 */
	@ModelResource("65-moving.di")
	@Maximized
	public static class WithMessagesAttached extends AbstractGraphicalEditPolicyUITest {
		@Rule
		public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

		@ClassRule
		public static TestRule tolerance = GEFMatchers.defaultTolerance(1);

		@AutoFixture("exec1")
		private ExecutionSpecification exec;

		@AutoFixture
		private EditPart execEP;

		@AutoFixture
		private Message async1;

		@AutoFixture
		private EditPart async1EP;

		@AutoFixture
		private Message async2;

		@AutoFixture
		private EditPart async2EP;

		@AutoFixture
		private Message async3;

		@AutoFixture
		private EditPart async3EP;

		@AutoFixture
		private ExecutionSpecification exec2;

		@AutoFixture
		private EditPart exec2EP;

		@AutoFixture
		private Message sync2;

		@AutoFixture
		private EditPart sync2EP;

		@AutoFixture
		private Message reply2;

		@AutoFixture
		private EditPart reply2EP;

		/**
		 * Initializes me.
		 */
		public WithMessagesAttached() {
			super();
		}

		@Test
		public void moveExecutionDown() {
			int delta = 15;

			PointList async1Geom = getPoints(async1EP);
			PointList async2Geom = getPoints(async2EP);
			PointList async3Geom = getPoints(async3EP);
			Rectangle exec2Geom = getBounds(exec2EP);

			// First, select the execution to activate selection handles
			Point grabAt = getCenter(execEP);
			editor.select(grabAt);

			Point dropAt = new Point(grabAt.x(), grabAt.y() + delta);
			editor.drag(grabAt, dropAt);

			async1Geom.translate(0, delta);
			async2Geom.translate(0, delta);
			async3Geom.translate(0, delta);
			exec2Geom.translate(0, delta);

			assertThat("Async message 1 was changed", async1EP,
					runs(isPoint(async1Geom.getFirstPoint()), isPoint(async1Geom.getLastPoint())));
			assertThat("Async message 2 was changed", async2EP,
					runs(isPoint(async2Geom.getFirstPoint()), isPoint(async2Geom.getLastPoint())));
			assertThat("Async message 3 was changed", async3EP,
					runs(isPoint(async3Geom.getFirstPoint()), isPoint(async3Geom.getLastPoint())));
			assertThat("Execution started by sync message 2 not moved", exec2EP,
					isAt(isPoint(exec2Geom.getLocation())));
		}

		@Test
		public void moveExecutionToSpanMessage() {
			int delta = -75;

			PointList sync2Geom = getPoints(sync2EP);
			PointList async3Geom = getPoints(async3EP);
			PointList reply2Geom = getPoints(reply2EP);

			// First, select the execution to activate selection handles
			Point grabAt = getCenter(exec2EP);
			editor.select(grabAt);

			Point dropAt = new Point(grabAt.x(), grabAt.y() + delta);
			editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

			sync2Geom.translate(0, delta);
			// async3 doesn't move up, but adjusts its receive end to attach to the execution
			async3Geom.setPoint(async3Geom.getLastPoint().getTranslated(-EXEC_WIDTH / 2, 0), 1);
			reply2Geom.translate(0, delta);

			assertThat("Request message 2 was not moved", sync2EP,
					runs(isPoint(sync2Geom.getFirstPoint()), isPoint(sync2Geom.getLastPoint())));
			assertThat("Reply message 2 was not moved", reply2EP,
					runs(isPoint(reply2Geom.getFirstPoint()), isPoint(reply2Geom.getLastPoint())));
			assertThat("Async message 3 not properly attached to execution", async3EP,
					runs(isPoint(async3Geom.getFirstPoint()), isPoint(async3Geom.getLastPoint())));
		}

		@Test
		public void attemptExecutionToSpanMessage() {
			int delta = -75;

			PointList async3Geom = getPoints(async3EP);
			Rectangle exec2Geom = getBounds(exec2EP);

			// First, select the execution to activate selection handles
			Point grabAt = getCenter(exec2EP);
			editor.select(grabAt);

			Point dropAt = new Point(grabAt.x(), grabAt.y() + delta);
			editor.drag(grabAt, dropAt);

			assertThat("Execution not moved up", exec2EP, isAt(is(exec2Geom.x()), lt(exec2Geom.y())));
			exec2Geom = getBounds(exec2EP); // Refresh it

			PointList newAsync3Geom = getPoints(async3EP);

			assertThat("Async message not bumped out of the way", async3EP,
					runs(isPoint(is(async3Geom.getFirstPoint().x()), lt(exec2Geom.y())),
							isPoint(is(async3Geom.getLastPoint().x()), lt(exec2Geom.y()))));
			assertThat("Async message 3 is misshapen", newAsync3Geom.getLastPoint().y(),
					is(newAsync3Geom.getFirstPoint().y()));
		}

	}

	/**
	 * Basic move/resize drag-and-drop use cases.
	 */
	@ModelResource("exec-around-delete.di")
	@Maximized
	public static class AroundDestruction extends AbstractGraphicalEditPolicyUITest {
		@Rule
		public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

		@AutoFixture
		private ExecutionSpecification exec;

		@AutoFixture
		private EditPart execEP;

		@AutoFixture("Lifeline1")
		private Lifeline lifeline1;

		@AutoFixture
		private EditPart lifeline1EP;

		/**
		 * Initializes me.
		 */
		public AroundDestruction() {
			super();
		}

		@Test
		public void attemptToSurroundDestruction() {
			Rectangle execGeom = getBounds(execEP);

			// First, select the execution to activate selection handles
			Point grabAt = new Point(execGeom.getCenter().x(), 150);
			editor.select(grabAt);

			Point dropAt = new Point(getBounds(lifeline1EP).getCenter().x(), grabAt.y());

			// Try to do this with the allow-semantic-reordering override
			editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

			// But it still isn't allowed because the destruction constraint is absolute

			assertThat("Execution was moved", execEP, isAt(isPoint(execGeom.getLocation())));
		}

	}

	/**
	 * Tests for drawing a nesting execution out from under its nested executions.
	 */
	@ModelResource("two-lifelines.di")
	@Maximized
	public static class ResizeNesting extends AbstractGraphicalEditPolicyUITest {
		@Rule
		public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

		@AutoFixture("Lifeline1")
		private Lifeline lifeline1;

		@AutoFixture
		private EditPart lifeline1EP;

		@AutoFixture
		private Rectangle lifelineGeom;

		@AutoFixture(value = "Execution1", optional = true)
		private EditPart nestingEP;

		@AutoFixture(optional = true)
		private Rectangle nestingGeom;

		@AutoFixture(value = "Execution2", optional = true)
		private EditPart nestedEP;

		@AutoFixture(optional = true)
		private Rectangle nestedGeom;

		/**
		 * Initializes me.
		 */
		public ResizeNesting() {
			super();
		}

		@Test
		public void drawUpBottomToExposeNested() {
			// First, select the nesting execution to activate selection handles
			editor.select(nestingGeom.getBottom().getTranslated(0, -20));

			Point grabAt = getResizeHandleGrabPoint(nestingEP, PositionConstants.SOUTH);
			Point dropAt = nestedGeom.getTop().getTranslated(0, -25);

			// Now, draw up the bottom from under the nested execution
			editor.drag(grabAt, dropAt);
			Rectangle expectedGeom = nestedGeom.getTranslated(-EXEC_WIDTH / 2, 0);

			// The operation rebuilds edit-parts
			autoFixtures.refresh();

			assertThat("Formerly nested execution misaligned", nestedEP, isBounded(isRect(expectedGeom)));
		}

		@Test
		public void drawDownTopToExposeNested() {
			// First, select the nesting execution to activate selection handles
			editor.select(nestingGeom.getTop().getTranslated(0, 20));

			Point grabAt = getResizeHandleGrabPoint(nestingEP, PositionConstants.NORTH);
			Point dropAt = nestedGeom.getBottom().getTranslated(0, 25);

			// Now, draw down the top from under the nested execution
			editor.drag(grabAt, dropAt);
			Rectangle expectedGeom = nestedGeom.getTranslated(-EXEC_WIDTH / 2, 0);

			// The operation rebuilds edit-parts
			autoFixtures.refresh();

			assertThat("Formerly nested execution misaligned", nestedEP, isBounded(isRect(expectedGeom)));
		}

		//
		// Test fixtures
		//

		@Before
		public void createExecutions() {
			int x = lifelineGeom.getCenter().x();

			nestingEP = editor.createShape(SequenceElementTypes.Behavior_Execution_Shape, at(x, 150),
					sized(0, 200));
			nestedEP = editor.createShape(SequenceElementTypes.Behavior_Execution_Shape, at(x, 300),
					null /* default size */);

			nestingGeom = getBounds(nestingEP);
			nestedGeom = getBounds(nestedEP);
		}
	}

	/**
	 * Tests for bumping executions by stretching executions below them upwards.
	 */
	@ModelResource("one-exec.di")
	@Maximized
	public static class BumpingUpwards extends AbstractGraphicalEditPolicyUITest {
		@Rule
		public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

		@AutoFixture("Execution1")
		private EditPart exec1EP;

		@AutoFixture
		private Rectangle exec1Geom;

		private EditPart exec2EP;

		private Rectangle exec2Geom;

		/**
		 * Initializes me.
		 */
		public BumpingUpwards() {
			super();
		}

		@Test
		public void stretchUpExecToBumpOther() {
			// First, select the new execution to activate selection handles
			editor.select(exec2Geom.getCenter());

			Point grabAt = getResizeHandleGrabPoint(exec2EP, PositionConstants.NORTH);
			Point dropAt = grabAt.getTranslated(0, -50);

			// Now, stretch up the execution below
			editor.drag(grabAt, dropAt);

			// Bump up 20 + 10 for padding
			Rectangle newExec1Geom = exec1Geom.getTranslated(0, -30);

			Rectangle newExec2Geom = exec2Geom.getTranslated(0, -50);
			newExec2Geom.setHeight(exec2Geom.height() + 50);

			assertThat("Execution above not bumped up", exec1EP, isBounded(isRect(newExec1Geom)));

			assertThat("Execution not stretched correctly", exec2EP, isBounded(isRect(newExec2Geom)));
		}

		/**
		 * Verify that bumping up is limited by the position of the lifeline head.
		 */
		@Test
		public void attemptStretchUpExecTooFar() {
			// First, select the new execution to activate selection handles
			editor.select(exec2Geom.getCenter());

			Point grabAt = getResizeHandleGrabPoint(exec2EP, PositionConstants.NORTH);
			Point dropAt = grabAt.getTranslated(0, -100);

			// Now, stretch up the execution below
			editor.drag(grabAt, dropAt);

			assertThat("Execution above was moved", exec1EP, isBounded(isRect(exec1Geom)));

			assertThat("Execution below was stretched", exec2EP, isBounded(isRect(exec2Geom)));
		}

		//
		// Test fixtures
		//

		@Before
		public void createExecutionBelow() {
			Point where = exec1Geom.getBottom().getTranslated(0, 30);

			exec2EP = editor.createShape(SequenceElementTypes.Behavior_Execution_Shape, where, null);
			exec2Geom = getBounds(exec2EP);
		}
	}

}
