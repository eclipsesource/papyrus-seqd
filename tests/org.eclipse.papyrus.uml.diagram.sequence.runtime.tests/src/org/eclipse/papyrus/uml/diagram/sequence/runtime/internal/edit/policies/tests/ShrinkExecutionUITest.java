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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Integration test cases for shrinking execution specifications that have
 * various attachments of messages incoming and outgoing.
 *
 * @author Christian W. Damus
 */
@ModelResource("65-moving.di")
@Maximized
public class ShrinkExecutionUITest extends AbstractGraphicalEditPolicyUITest {

	/** Reply messages don't end up exactly where the resize handle is. */
	@ClassRule
	public static final TestRule TOLERANCE = GEFMatchers.defaultTolerance(1);

	private EditPart sync1;
	private PointList sync1Geom;
	private EditPart async1;
	private PointList async1Geom;
	private EditPart sync2;
	private PointList sync2Geom;
	private EditPart reply2;
	private PointList reply2Geom;
	private EditPart async2;
	private PointList async2Geom;
	private EditPart reply1;
	private PointList reply1Geom;

	private EditPart exec1;
	private Rectangle exec1Geom;
	private EditPart exec2;
	private Rectangle exec2Geom;

	/**
	 * Initializes me.
	 */
	public ShrinkExecutionUITest() {
		super();
	}

	@Test
	public void shrinkFromTop() {
		editor.select(exec2Geom.getCenter());

		Point grabExec = getResizeHandleGrabPoint(exec2, PositionConstants.NORTH);
		int midWay = exec2Geom.getCenter().y;
		Point dropExec = new Point(grabExec.x, midWay);

		editor.moveSelection(grabExec, dropExec);

		// Verify expected changes

		PointList expectedSync2Geom = sync2Geom.getCopy();
		expectedSync2Geom.translate(0, midWay - sync2Geom.getLastPoint().y);
		assertThat("Misattached request message", sync2, runs(isPoint(expectedSync2Geom.getFirstPoint()),
				isPoint(expectedSync2Geom.getLastPoint())));

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

		editor.moveSelection(grabExec, dropExec);

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

		editor.with(editor.allowSemanticReordering(), () -> editor.moveSelection(grabExec, dropExec));

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

		editor.with(editor.allowSemanticReordering(), () -> editor.moveSelection(grabExec, dropExec));

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

	//
	// Test framework
	//

	@Before
	public void findFixtures() {
		sync1 = find("sync1", true);
		sync1Geom = getPoints(sync1);
		async1 = find("async1", true);
		async1Geom = getPoints(async1);
		sync2 = find("sync2", true);
		sync2Geom = getPoints(sync2);
		reply2 = find("reply2", true);
		reply2Geom = getPoints(reply2);
		async2 = find("async2", true);
		async2Geom = getPoints(async2);
		reply1 = find("reply1", true);
		reply1Geom = getPoints(reply1);

		exec1 = find("exec1", false);
		exec1Geom = getBounds(exec1);
		exec2 = find("exec2", false);
		exec2Geom = getBounds(exec2);
	}

	protected EditPart find(String name, boolean isMessage) {
		return findEditPart("lightweight::DoIt::" + name, isMessage);
	}
}
