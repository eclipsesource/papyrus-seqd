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
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.lt;
import static org.eclipse.uml2.uml.util.UMLUtil.getQualifiedText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

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
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration regression tests for certain nudge scenarios involving asynchronous messages in
 * <a href="https://github.com/eclipsesource/papyrus-seqd/issues/432">Issue #432</a>.
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

	//
	// Test framework
	//

	/**
	 * None of our nudging operations changes the semantic order of the involved interaction fragments.
	 */
	@After
	public void assertFragmentsSemanticOrder() {
		Interaction interaction = editor.getInteraction();
		List<InteractionFragment> allFragments = interaction.getFragments();
		List<InteractionFragment> fragmentsOfInterest = Arrays.asList(_1s, _1r, _2s, _2r);

		for (int i = 1; i < fragmentsOfInterest.size(); i++) {
			InteractionFragment prev = fragmentsOfInterest.get(i - 1);
			InteractionFragment next = fragmentsOfInterest.get(i);

			int expectedOrder = fragmentsOfInterest.indexOf(next) - fragmentsOfInterest.indexOf(prev);
			int actualOrder = allFragments.indexOf(next) - allFragments.indexOf(prev);

			if (signum(actualOrder) != signum(expectedOrder)) {
				fail(String.format("Interaction fragments out of order: %s ≺ %s", getQualifiedText(next),
						getQualifiedText(prev)));
			}
		}
	}

}
