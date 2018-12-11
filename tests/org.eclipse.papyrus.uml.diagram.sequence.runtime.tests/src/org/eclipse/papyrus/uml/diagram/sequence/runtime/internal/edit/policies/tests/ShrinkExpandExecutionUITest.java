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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.is;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isRect;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isBounded;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gt;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.lt;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Integration test cases for shrinking and expanding execution specifications that have various attachments
 * of messages incoming and outgoing.
 *
 * @author Christian W. Damus
 */
@ModelResource("65-moving.di")
@Maximized
public class ShrinkExpandExecutionUITest extends AbstractGraphicalEditPolicyUITest {

	/** Reply messages don't end up exactly where the resize handle is. */
	@SuppressWarnings("hiding")
	@ClassRule
	public static final TestRule TOLERANCE = GEFMatchers.defaultTolerance(1);

	@Rule
	public final TestRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture
	private EditPart sync1;

	@AutoFixture
	private PointList sync1Geom;

	@AutoFixture
	private EditPart async1;

	@AutoFixture
	private PointList async1Geom;

	@AutoFixture
	private EditPart sync2;

	@AutoFixture
	private PointList sync2Geom;

	@AutoFixture
	private EditPart reply2;

	@AutoFixture
	private PointList reply2Geom;

	@AutoFixture
	private EditPart async2;

	@AutoFixture
	private PointList async2Geom;

	@AutoFixture
	private EditPart reply1;

	@AutoFixture
	private PointList reply1Geom;

	@AutoFixture
	private EditPart async3;

	@AutoFixture
	private PointList async3Geom;

	@AutoFixture
	private EditPart async4;

	@AutoFixture
	private PointList async4Geom;

	@AutoFixture
	private EditPart exec1;

	@AutoFixture
	private Rectangle exec1Geom;

	@AutoFixture
	private EditPart exec2;

	@AutoFixture
	private Rectangle exec2Geom;

	/**
	 * Initializes me.
	 */
	public ShrinkExpandExecutionUITest() {
		super();
	}

	@Test
	public void shrinkFromTop() {
		editor.select(exec2Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec2, PositionConstants.NORTH);
		int midWay = exec2Geom.getCenter().y;
		Point dropExec = new Point(grabExec.x, midWay);

		editor.drag(grabExec, dropExec);

		// Verify expected changes

		PointList expectedSync2Geom = sync2Geom.getCopy();
		expectedSync2Geom.translate(0, midWay - sync2Geom.getLastPoint().y);
		assertThat("Misattached request message", sync2,
				runs(isPoint(expectedSync2Geom.getFirstPoint()), isPoint(expectedSync2Geom.getLastPoint())));

		// Verify expected non-changes

		assertThat("Reply message changed", reply2,
				runs(isPoint(reply2Geom.getFirstPoint()), isPoint(reply2Geom.getLastPoint())));
	}

	@Test
	public void shrinkFromBottom() {
		editor.select(exec2Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec2, PositionConstants.SOUTH);
		int midWay = exec2Geom.getCenter().y;
		Point dropExec = new Point(grabExec.x, midWay);

		editor.drag(grabExec, dropExec);

		// Verify expected changes

		PointList expectedReply2Geom = reply2Geom.getCopy();
		expectedReply2Geom.translate(0, midWay - reply2Geom.getLastPoint().y);
		assertThat("Misattached reply message", reply2, runs(isPoint(expectedReply2Geom.getFirstPoint()),
				isPoint(expectedReply2Geom.getLastPoint())));

		// Verify expected non-changes

		assertThat("Request message changed", sync2,
				runs(isPoint(sync2Geom.getFirstPoint()), isPoint(sync2Geom.getLastPoint())));
	}

	@Test
	public void detachShrinkFromTop() {
		editor.select(exec1Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec1, PositionConstants.NORTH);
		int midWayBetweenReply2Async2 = (reply2Geom.getLastPoint().y + async2Geom.getLastPoint().y) / 2;
		Point dropExec = new Point(grabExec.x, midWayBetweenReply2Async2);

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabExec, dropExec));

		// Verify expected changes

		assertThat("Misattached request message", sync1, runs(isPoint(sync1Geom.getFirstPoint()),
				isPoint(sync1Geom.getLastPoint().getTranslated(EXEC_WIDTH / 2, 0))));

		assertThat("Misattached incoming message", async1, runs(isPoint(async1Geom.getFirstPoint()),
				isPoint(async1Geom.getLastPoint().getTranslated(EXEC_WIDTH / 2, 0))));
		assertThat("Misattached outgoing message", sync2,
				runs(isPoint(sync2Geom.getFirstPoint().getTranslated(-EXEC_WIDTH / 2, 0)),
						isPoint(sync2Geom.getLastPoint())));
		assertThat("Misattached incoming message", reply2, runs(isPoint(reply2Geom.getFirstPoint()),
				isPoint(reply2Geom.getLastPoint().getTranslated(-EXEC_WIDTH / 2, 0))));

		// Verify expected non-changes

		assertThat("Incoming message changed", async2,
				runs(isPoint(async2Geom.getFirstPoint()), isPoint(async2Geom.getLastPoint())));
		assertThat("Reply message changed", reply1,
				runs(isPoint(reply1Geom.getFirstPoint()), isPoint(reply1Geom.getLastPoint())));
	}

	@Test
	public void detachShrinkFromBottom() {
		editor.select(exec1Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec1, PositionConstants.SOUTH);
		int midWayBetweenAsync1Sync2 = (async1Geom.getLastPoint().y + sync2Geom.getFirstPoint().y) / 2;
		Point dropExec = new Point(grabExec.x, midWayBetweenAsync1Sync2);

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabExec, dropExec));

		// Verify expected changes

		assertThat("Misattached reply message", reply1,
				runs(isPoint(reply1Geom.getFirstPoint().getTranslated(EXEC_WIDTH / 2, 0)),
						isPoint(reply1Geom.getLastPoint())));

		assertThat("Misattached incoming message", async2, runs(isPoint(async2Geom.getFirstPoint()),
				isPoint(async2Geom.getLastPoint().getTranslated(EXEC_WIDTH / 2, 0))));
		assertThat("Misattached incoming message", reply2, runs(isPoint(reply2Geom.getFirstPoint()),
				isPoint(reply2Geom.getLastPoint().getTranslated(-EXEC_WIDTH / 2, 0))));
		assertThat("Misattached outgoing message", sync2,
				runs(isPoint(sync2Geom.getFirstPoint().getTranslated(-EXEC_WIDTH / 2, 0)),
						isPoint(sync2Geom.getLastPoint())));

		// Verify expected non-changes

		assertThat("Incoming message changed", async1,
				runs(isPoint(async1Geom.getFirstPoint()), isPoint(async1Geom.getLastPoint())));
		assertThat("Request message changed", sync1,
				runs(isPoint(sync1Geom.getFirstPoint()), isPoint(sync1Geom.getLastPoint())));
	}

	@Test
	public void detachExpandFromTop() {
		editor.select(exec2Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec2, PositionConstants.NORTH);
		int midWayBetweenAsync1Async3 = (async1Geom.getLastPoint().y + async3Geom.getLastPoint().y) / 2;
		Point dropExec = new Point(grabExec.x, midWayBetweenAsync1Async3);

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabExec, dropExec));

		// Verify expected changes

		Point expectedAsync3Recv = async3Geom.getLastPoint().getTranslated(-EXEC_WIDTH / 2, 0);
		assertThat("Misattached encompassed message", async3,
				runs(isPoint(async3Geom.getFirstPoint()), isPoint(expectedAsync3Recv)));

		// Verify expected non-changes

		assertThat("Request message changed", sync2, // This was attached to the corner
				runs(isPoint(sync2Geom.getFirstPoint()), isPoint(sync2Geom.getLastPoint())));
	}

	@Test
	public void detachExpandFromBottom() {
		editor.select(exec1Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec1, PositionConstants.SOUTH);
		int belowAsync4 = async4Geom.getLastPoint().y + 20;
		Point dropExec = new Point(grabExec.x, belowAsync4);

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabExec, dropExec));

		// Verify expected changes

		Point expectedAsync4Send = async4Geom.getFirstPoint().getTranslated(-EXEC_WIDTH / 2, 0);
		assertThat("Misattached encompassed message", async4,
				runs(isPoint(expectedAsync4Send), isPoint(async4Geom.getLastPoint())));

		// Verify expected non-changes

		assertThat("Reply message changed", reply1, // This was attached to the corner
				runs(isPoint(reply1Geom.getFirstPoint()), isPoint(reply1Geom.getLastPoint())));
	}

	@Test
	public void expandFromTop() {
		editor.select(exec2Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec2, PositionConstants.NORTH);
		int midWayBetweenAsync1Async3 = (async1Geom.getLastPoint().y + async3Geom.getLastPoint().y) / 2;
		Point dropExec = new Point(grabExec.x, midWayBetweenAsync1Async3);

		editor.drag(grabExec, dropExec);

		// Verify expected changes

		PointList newAsync3Geom = getPoints(async3);

		assertThat("Preceding message not bumped out of the way", async3,
				runs(isPoint(is(async3Geom.getFirstPoint().x()), lt(dropExec.y())),
						isPoint(is(async3Geom.getLastPoint().x()), lt(dropExec.y()))));

		assertThat("Misshapen bumped message", newAsync3Geom.getLastPoint().y(),
				is(newAsync3Geom.getFirstPoint().y()));

		Point async1Send = async1Geom.getFirstPoint();
		Point async1Recv = async1Geom.getLastPoint();
		assertThat("Other earlier message not nudged also", async1,
				runs(isPoint(is(async1Send.x()), lt(async1Send.y())), //
						isPoint(is(async1Recv.x()), lt(async1Recv.y()))));

		// Verify expected non-changes

		assertThat("Unrelated message changed", async2,
				runs(isPoint(async2Geom.getFirstPoint()), isPoint(async2Geom.getLastPoint())));
	}

	@Test
	public void expandFromBottom() {
		editor.select(exec1Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec1, PositionConstants.SOUTH);
		int belowAsync4 = async4Geom.getLastPoint().y + 20;
		Point dropExec = new Point(grabExec.x, belowAsync4);

		editor.drag(grabExec, dropExec);

		// Verify expected changes

		PointList newAsync4Geom = getPoints(async4);

		assertThat("Following message not bumped out of the way", async4,
				runs(isPoint(is(async4Geom.getFirstPoint().x()), gt(dropExec.y())),
						isPoint(is(async4Geom.getLastPoint().x()), gt(dropExec.y()))));

		assertThat("Misshapen bumped message", newAsync4Geom.getLastPoint().y(),
				is(newAsync4Geom.getFirstPoint().y()));

		// Verify expected non-changes

		assertThat("Request message changed", sync2,
				runs(isPoint(sync2Geom.getFirstPoint()), isPoint(sync2Geom.getLastPoint())));
		assertThat("Incoming message changed", async2,
				runs(isPoint(async2Geom.getFirstPoint()), isPoint(async2Geom.getLastPoint())));
	}

	@Test
	public void attemptExpandToHalfCoverExec() {
		Point where = exec2Geom.getBottom().getTranslated(0, 15);
		EditPart newExec = createShape(SequenceElementTypes.Behavior_Execution_Shape, where, null);

		editor.select(exec2Geom.getCenter());
		Point grabAt = getResizeHandleGrabPoint(exec2, PositionConstants.SOUTH);
		Point dropAt = getBounds(newExec).getCenter();

		// Even allow-reordering isn't permitted
		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

		assertThat("Execution was resized", exec2, isBounded(isRect(exec2Geom)));
	}

	@Test
	public void attemptShrinkToHalfCoverExec() {
		Point where = exec2Geom.getCenter().getTranslated(0, -10);
		createShape(SequenceElementTypes.Behavior_Execution_Shape, where, null);

		exec2Geom = getBounds(exec2); // Refresh geometry
		editor.select(exec2Geom.getBottom().getTranslated(0, -5));
		Point grabAt = getResizeHandleGrabPoint(exec2, PositionConstants.SOUTH);
		Point dropAt = exec2Geom.getCenter().getTranslated(0, -10);

		// Even allow-reordering isn't permitted
		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

		assertThat("Execution was resized", exec2, isBounded(isRect(exec2Geom)));
	}

}
