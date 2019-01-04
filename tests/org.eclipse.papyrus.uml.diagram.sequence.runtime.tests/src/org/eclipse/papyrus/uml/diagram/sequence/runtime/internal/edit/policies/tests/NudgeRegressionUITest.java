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
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gt;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.lt;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration regression tests for certain nudge scenarios in
 * <a href="https://github.com/eclipsesource/papyrus-seqd/issues/26">Issue #26 Fragment padding</a>.
 */
@ModelResource("Nudging.di")
@Maximized
public class NudgeRegressionUITest extends AbstractGraphicalEditPolicyUITest {

	@Rule
	public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture
	private EditPart exec1;

	@AutoFixture
	private Rectangle exec1Geom;

	@AutoFixture
	private EditPart exec2;

	@AutoFixture
	private Rectangle exec2Geom;

	@AutoFixture
	private EditPart exec3;

	@AutoFixture
	private Rectangle exec3Geom;

	@AutoFixture
	private EditPart async1;

	@AutoFixture
	private PointList async1Geom;

	@AutoFixture
	private EditPart async2;

	@AutoFixture
	private PointList async2Geom;

	@AutoFixture
	private EditPart request1;

	@AutoFixture
	private PointList request1Geom;

	/**
	 * Initializes me.
	 */
	public NudgeRegressionUITest() {
		super();
	}

	@Test
	public void moveExecUp() {
		Point grabAt = exec1Geom.getCenter();
		Point dropAt = exec1Geom.getCenter().getTranslated(0, -35);

		int x = async1Geom.getLastPoint().x(); // This should not change

		editor.moveSelection(grabAt, dropAt);
		autoFixtures.refresh();

		int y = exec1Geom.y(); // This will have changed

		assertThat("async1 not bumped", async1Geom.getLastPoint(), isPoint(is(x), lt(y)));
	}

	@Test
	public void attemptMoveExecUpTooFar() {
		Point grabAt = exec1Geom.getCenter();
		// This is too far, would bump async1 into the lifeline head
		Point dropAt = async1Geom.getLastPoint().getTranslated(0, 5);

		PointList expectedAsync1 = async1Geom.getCopy();
		Rectangle expectedExec1 = exec1Geom.getCopy();

		editor.moveSelection(grabAt, dropAt);
		autoFixtures.refresh();

		assertThat("exec1 was moved", exec1Geom, equalTo(expectedExec1));
		// PointList does not implement equals!
		assertThat("async1 was bumped", async1Geom.toIntArray(), equalTo(expectedAsync1.toIntArray()));
	}

	@Test
	public void stretchExecDown() {
		editor.select(exec2Geom.getCenter());
		Point grabAt = getResizeHandleGrabPoint(exec2, PositionConstants.SOUTH);
		Point dropAt = exec1Geom.getBottom().getTranslated(0, +25);

		int top = exec1Geom.y(); // This should not change

		editor.drag(grabAt, dropAt);
		autoFixtures.refresh();

		assertThat("exec1 moved", exec1Geom.y(), is(top));
		assertThat("exec1 not stretched", exec1Geom.bottom(), gt(exec2Geom.bottom()));
	}

	@Test
	public void moveMessageUp() {
		Point grabAt = async2Geom.getMidpoint();
		Point dropAt = new Point(grabAt.x(), exec3Geom.getCenter().y());

		int height = exec3Geom.height();
		int y = exec3Geom.bottom();

		editor.moveSelection(grabAt, dropAt);
		autoFixtures.refresh();

		int msgY = async2Geom.getLastPoint().y();

		assertThat("message not moved", msgY, lt(y));
		assertThat("execution not bumped", exec3Geom.bottom(), lt(msgY));
		assertThat("execution reshaped", exec3Geom.height(), is(height));
	}

	@Test
	public void moveNestedExecUp() {
		Point grabAt = exec2Geom.getCenter();
		Point dropAt = exec2Geom.getCenter().getTranslated(0, -50);

		int x = request1Geom.getLastPoint().x(); // This should not change

		editor.moveSelection(grabAt, dropAt);
		autoFixtures.refresh();

		int y = exec2Geom.y(); // This will have changed

		assertThat("async1 not bumped", async1Geom.getLastPoint(), isPoint(anything(), lt(y)));
		assertThat("request1 not bumped", request1Geom.getLastPoint(), isPoint(is(x), lt(y)));
	}

	//
	// Test framework
	//

	/**
	 * Verify that nothing done in the test resulted in sloping message connections.
	 */
	@After
	public void assertAllMessagesHorizontal() {
		@SuppressWarnings("unchecked")
		List<ConnectionEditPart> connections = editor.getDiagramEditPart().getConnections();
		connections.forEach(
				c -> assertThat("Message is not horizontal", c, GEFMatchers.EditParts.isHorizontal()));
	}

}
