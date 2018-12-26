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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
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
import org.junit.rules.TestRule;

/**
 * UI test for moving executions with self start / finish messages.
 */
@ModelResource("execution-self.di")
@Maximized
public class ExecutionWithSelfMoveUITest extends AbstractGraphicalEditPolicyUITest {

	@ClassRule
	public static TestRule tolerance = GEFMatchers.defaultTolerance(1);

	@Rule
	public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture("Lifeline1")
	private Lifeline lifeline1;

	@AutoFixture
	private EditPart lifeline1EP;

	@AutoFixture("request")
	private Message request;

	@AutoFixture
	private EditPart requestEP;

	@AutoFixture("reply")
	private Message reply;

	@AutoFixture
	private EditPart replyEP;

	@AutoFixture("Execution1")
	private ExecutionSpecification execution1;

	@AutoFixture
	private EditPart execution1EP;

	private Rectangle execution1Rect;

	private PointList replyPoints;

	private PointList requestPoints;

	@Before
	public void updateFixtures() {
		execution1Rect = getBounds(execution1EP);
		requestPoints = getPoints(requestEP).getCopy();
		replyPoints = getPoints(replyEP).getCopy();
	}

	@Test
	public void moveDownExecution() {
		int delta = +50;

		Point grabAt = execution1Rect.getCenter();
		Point dropAt = execution1Rect.getCenter().translate(0, delta);
		editor.moveSelection(grabAt, dropAt);

		// check result
		Rectangle exec1EPNew = execution1Rect.getCopy().translate(new Point(0, delta));
		EditPart exec1EP = findEditPart("execution-self::Interaction1::Execution1", false);
		assertThat(getBounds(exec1EP), equalTo(exec1EPNew));
		PointList newRequestPoints = requestPoints.getCopy();
		newRequestPoints.translate(0, delta);
		assertThat(requestEP, runs(exec1EPNew.getCenter().x(), exec1EPNew.getTop().y() - 20,
				exec1EPNew.getTopRight().x(), exec1EPNew.getTopRight().y()));

		PointList newReplyPoints = replyPoints.getCopy();
		newReplyPoints.translate(0, delta);
		assertThat(replyEP, runs(exec1EPNew.getBottomRight().x(), exec1EPNew.getBottomRight().y(),
				exec1EPNew.getCenter().x(), exec1EPNew.getBottom().y() + 20));
	}

	@Test
	public void moveUpExecution() {
		int delta = -50;

		Point grabAt = execution1Rect.getCenter();
		Point dropAt = execution1Rect.getCenter().translate(0, delta);
		editor.moveSelection(grabAt, dropAt);

		// check result
		Rectangle exec1EPNew = execution1Rect.getCopy().translate(new Point(0, delta));
		EditPart exec1EP = findEditPart("execution-self::Interaction1::Execution1", false);
		assertThat(getBounds(exec1EP), equalTo(exec1EPNew));
		PointList newRequestPoints = requestPoints.getCopy();
		newRequestPoints.translate(0, delta);
		assertThat(requestEP, runs(exec1EPNew.getCenter().x(), exec1EPNew.getTop().y() - 20,
				exec1EPNew.getTopRight().x(), exec1EPNew.getTopRight().y()));

		PointList newReplyPoints = replyPoints.getCopy();
		newReplyPoints.translate(0, delta);
		assertThat(replyEP, runs(exec1EPNew.getBottomRight().x(), exec1EPNew.getBottomRight().y(),
				exec1EPNew.getCenter().x(), exec1EPNew.getBottom().y() + 20));
	}

}
