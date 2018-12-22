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
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration regression tests for certain reconnection scenarios involving asynchronous messages in
 * <a href="https://github.com/eclipsesource/papyrus-seqd/issues/425">Issue #425</a>.
 */
@ModelResource("AsyncMessages2.di")
@Maximized
public class AsyncMessageReconnectionUITest extends AbstractGraphicalEditPolicyUITest {

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
	public AsyncMessageReconnectionUITest() {
		super();
	}

	/**
	 * Test that moving the send event of an overlapped message down with the keyboard modifier will re-order
	 * fragments instead of nudging them.
	 */
	@Test
	public void reorderSendEventDown() {
		editor.select(asyncMessage1Geom.getMidpoint());
		Point grabAt = getConnectionHandleGrabPoint(asyncMessage1, SOURCE_END);
		// This would drop the send event mid-way between message 2 send and message 1 receive
		Point dropAt = new Point(asyncMessage1Geom.getFirstPoint().x(),
				(asyncMessage1Geom.getLastPoint().y() + asyncMessage2Geom.getFirstPoint().y()) / 2);

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));
		autoFixtures.refresh();

		assertThat("AsyncMessage2 bumped", asyncMessage2Geom.getFirstPoint(),
				isPoint(anything(), lt(asyncMessage1Geom.getFirstPoint().y())));
		assertThat("Messages not fully overlapped", asyncMessage1Geom.getLastPoint(),
				isPoint(anything(), lt(asyncMessage2Geom.getLastPoint().y())));

		verifyFragmentsSemanticOrder(_2s, _1s, _1r, _2r);
	}

	/**
	 * Test that moving the receive event of an overlapped message up with the keyboard modifier will re-order
	 * fragments instead of nudging them.
	 */
	@Test
	public void reorderReceiveEventUp() {
		editor.select(asyncMessage2Geom.getMidpoint());
		Point grabAt = getConnectionHandleGrabPoint(asyncMessage2, TARGET_END);
		// This would drop the receive event mid-way between message 2 send and message 1 receive
		Point dropAt = new Point(asyncMessage2Geom.getLastPoint().x(),
				(asyncMessage1Geom.getLastPoint().y() + asyncMessage2Geom.getFirstPoint().y()) / 2);

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));
		autoFixtures.refresh();

		assertThat("AsyncMessage1 bumped", asyncMessage1Geom.getLastPoint(),
				isPoint(anything(), gt(asyncMessage2Geom.getLastPoint().y())));
		assertThat("Messages not fully overlapped", asyncMessage2Geom.getFirstPoint(),
				isPoint(anything(), gt(asyncMessage1Geom.getFirstPoint().y())));

		verifyFragmentsSemanticOrder(_1s, _2s, _2r, _1r);
	}

	//
	// Test framework
	//

	/**
	 * Verify that a bunch of interaction {@code fragments} are in the order specified.
	 * 
	 * @param fragments
	 *            interaction fragments in their expected model order
	 */
	void verifyFragmentsSemanticOrder(InteractionFragment... fragments) {
		Interaction interaction = editor.getInteraction();
		List<InteractionFragment> allFragments = interaction.getFragments();
		List<InteractionFragment> fragmentsOfInterest = Arrays.asList(fragments);

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
