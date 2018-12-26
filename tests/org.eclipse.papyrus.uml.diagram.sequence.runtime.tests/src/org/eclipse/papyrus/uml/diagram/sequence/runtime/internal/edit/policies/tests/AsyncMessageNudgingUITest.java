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

import static java.lang.Integer.signum;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gt;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.lt;
import static org.eclipse.uml2.uml.util.UMLUtil.getQualifiedText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import com.google.common.collect.Ordering;

import java.util.Arrays;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration regression tests for certain nudge scenarios involving asynchronous messages in
 * <a href="https://github.com/eclipsesource/papyrus-seqd/issues/432">Issue #432</a> and
 * <a href="https://github.com/eclipsesource/papyrus-seqd/issues/425">Issue #425</a>.
 */
@ModelResource("AsyncMessages.di")
@Maximized
public class AsyncMessageNudgingUITest extends AbstractGraphicalEditPolicyUITest {

	@Rule
	public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture
	private EditPart asyncMessage1;

	@AutoFixture
	private PointList asyncMessage1Geom;

	@AutoFixture
	private EditPart asyncMessage2;

	@AutoFixture
	private PointList asyncMessage2Geom;

	@AutoFixture
	private MessageOccurrenceSpecification _1s;

	@AutoFixture
	private MessageOccurrenceSpecification _1r;

	@AutoFixture
	private MessageOccurrenceSpecification _2s;

	@AutoFixture
	private MessageOccurrenceSpecification _2r;

	private List<InteractionFragment> fragmentOrder;

	/**
	 * Initializes me.
	 */
	public AsyncMessageNudgingUITest() {
		super();
	}

	/**
	 * Test nudging a sloped message up not above another sloped message, but so that it would be trying to
	 * split that message (and verify that the other message is appropriately nudged out of the way).
	 */
	@Test
	public void nudgeAsync2UpABit() {
		// This would drop the sending end of message 2 within the vertical span of message 1
		Point grabAt = asyncMessage2Geom.getMidpoint();
		Point dropAt = new Point(grabAt.x(), asyncMessage1Geom.getLastPoint().y());

		editor.moveSelection(grabAt, dropAt);
		autoFixtures.refresh();

		assertThat("AsyncMessage1 not bumped", asyncMessage1Geom.getLastPoint(),
				isPoint(anything(), lt(asyncMessage2Geom.getFirstPoint().y())));
	}

	/**
	 * Test nudging a sloped message up fully above another sloped message (and verify that the other message
	 * is appropriately nudged out of the way).
	 */
	@Test
	public void nudgeAsync2AboveAsync1() {
		int async2Height = asyncMessage2Geom.getLastPoint().y() - asyncMessage2Geom.getFirstPoint().y();

		// This would drop the receiving end of message 2 above the sending end of message 1
		Point grabAt = asyncMessage2Geom.getMidpoint();
		Point dropAt = new Point(grabAt.x(), asyncMessage1Geom.getFirstPoint().y() - (async2Height / 2) - 10);

		editor.moveSelection(grabAt, dropAt);
		autoFixtures.refresh();

		assertThat("AsyncMessage1 not bumped", asyncMessage1Geom.getLastPoint(),
				isPoint(anything(), lt(asyncMessage2Geom.getFirstPoint().y())));
	}

	/**
	 * Test that moving the send event of an overlapped message down will nudge fragments instead of
	 * re-ordering them.
	 */
	@Test
	@ModelResource("AsyncMessages2.di")
	public void nudgeSendEventDown() {
		editor.select(asyncMessage1Geom.getMidpoint());
		Point grabAt = getConnectionHandleGrabPoint(asyncMessage1, SOURCE_END);
		// This would drop the send event mid-way between message 2 send and message 1 receive
		Point dropAt = new Point(asyncMessage1Geom.getFirstPoint().x(),
				(asyncMessage1Geom.getLastPoint().y() + asyncMessage2Geom.getFirstPoint().y()) / 2);

		editor.drag(grabAt, dropAt);
		autoFixtures.refresh();

		assertThat("AsyncMessage2 not bumped", asyncMessage2Geom.getFirstPoint(),
				isPoint(anything(), gt(asyncMessage1Geom.getFirstPoint().y())));
		assertThat("Messages not still overlapped", asyncMessage1Geom.getLastPoint(),
				isPoint(anything(), gt(asyncMessage2Geom.getFirstPoint().y())));
	}

	/**
	 * Test that moving the receive event of an overlapped message up will nudge fragments instead of
	 * re-ordering them.
	 */
	@Test
	@ModelResource("AsyncMessages2.di")
	public void nudgeReceiveEventUp() {
		editor.select(asyncMessage2Geom.getMidpoint());
		Point grabAt = getConnectionHandleGrabPoint(asyncMessage2, TARGET_END);
		// This would drop the receive event mid-way between message 2 send and message 1 receive
		Point dropAt = new Point(asyncMessage2Geom.getLastPoint().x(),
				(asyncMessage1Geom.getLastPoint().y() + asyncMessage2Geom.getFirstPoint().y()) / 2);

		editor.drag(grabAt, dropAt);
		autoFixtures.refresh();

		assertThat("AsyncMessage1 not bumped", asyncMessage1Geom.getLastPoint(),
				isPoint(anything(), lt(asyncMessage2Geom.getLastPoint().y())));
		assertThat("Messages not still overlapped", asyncMessage1Geom.getLastPoint(),
				isPoint(anything(), gt(asyncMessage2Geom.getFirstPoint().y())));
	}

	//
	// Test framework
	//

	/**
	 * Capture the initial fragment order, to verify afterwards that it is unchanged.
	 */
	@Before
	public void snapshotFragmentOrder() {
		fragmentOrder = Arrays.asList(_1s, _1r, _2s, _2r);
		fragmentOrder.sort(Ordering.explicit(editor.getInteraction().getFragments()));
	}

	/**
	 * None of our nudging operations changes the semantic order of the involved interaction fragments.
	 */
	@After
	public void assertFragmentsSemanticOrder() {
		Interaction interaction = editor.getInteraction();
		List<InteractionFragment> allFragments = interaction.getFragments();

		for (int i = 1; i < fragmentOrder.size(); i++) {
			InteractionFragment prev = fragmentOrder.get(i - 1);
			InteractionFragment next = fragmentOrder.get(i);

			int expectedOrder = fragmentOrder.indexOf(next) - fragmentOrder.indexOf(prev);
			int actualOrder = allFragments.indexOf(next) - allFragments.indexOf(prev);

			if (signum(actualOrder) != signum(expectedOrder)) {
				fail(String.format("Interaction fragments out of order: %s ≺ %s", getQualifiedText(next),
						getQualifiedText(prev)));
			}
		}
	}

}
