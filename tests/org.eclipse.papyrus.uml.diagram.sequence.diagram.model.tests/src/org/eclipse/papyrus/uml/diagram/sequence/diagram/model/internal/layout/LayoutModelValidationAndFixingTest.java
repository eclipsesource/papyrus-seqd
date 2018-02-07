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
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout;

import static org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout.LayoutModelTestUtils.assertSameContent;
import static org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout.LayoutModelTestUtils.getAllTenPrefs;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class LayoutModelValidationAndFixingTest {

	ModelWithFragmentsFixture fixture;

	@Before
	public void initFixture() {
		fixture = new ModelWithFragmentsFixture();
	}

	@Test
	public void testDetectingValidMessageOrder() {
		fixture.interaction.using(getAllTenPrefs());
		fixture.interaction.setPosition(0, 0, 100, 200);

		fixture.user.setPosition(5, 0, 5, 60);
		fixture.webBrowser.setPosition(15, 0, 15, 130);
		fixture.applicationServer.setPosition(25, 0, 25, 130);

		fixture.obtainResource.setPosition(5, 20, 15, 50);
		fixture.requestAccess.setPosition(15, 50, 25, 80);

		fixture.alternative.setPosition(5, 80, 35, 160);
		fixture.altRegion1.setPosition(5, 80, 35, 120);
		fixture.altRegion2.setPosition(5, 120, 35, 160);

		fixture.grantAccess.setPosition(15, 80, 25, 120);
		fixture.redirectRequest.setPosition(15, 120, 25, 160);

		assertTrue(fixture.interaction.areAllYRangesValid());
	}

	@Test
	public void testDetectingInvalidRangeWhereMessageIsPositionedBeforePreviousMessage() {
		fixture.interaction.using(getAllTenPrefs());
		fixture.interaction.setPosition(0, 0, 45, 170);

		fixture.user.setPosition(5, 0, 5, 60);
		fixture.webBrowser.setPosition(15, 0, 15, 130);
		fixture.applicationServer.setPosition(25, 0, 25, 130);

		// obtainResource appears after requestAccess (see Y ranges)
		fixture.obtainResource.setPosition(5, 50, 15, 80);
		fixture.requestAccess.setPosition(15, 20, 25, 50);

		fixture.alternative.setPosition(5, 80, 35, 160);
		fixture.altRegion1.setPosition(5, 80, 35, 120);
		fixture.altRegion2.setPosition(5, 120, 35, 160);

		fixture.grantAccess.setPosition(15, 80, 25, 120);
		fixture.redirectRequest.setPosition(15, 120, 25, 160);

		assertInvalidElementCanBeFixed(fixture.requestAccess);
	}

	@Test
	public void testDetectingInvalidRangeWhereFragmentStartsBeforePreviousMessage() {
		fixture.interaction.using(getAllTenPrefs());
		fixture.interaction.setPosition(0, 0, 45, 170);

		fixture.user.setPosition(5, 0, 5, 60);
		fixture.webBrowser.setPosition(15, 0, 15, 130);
		fixture.applicationServer.setPosition(25, 0, 25, 130);

		fixture.obtainResource.setPosition(5, 20, 15, 50);
		fixture.requestAccess.setPosition(15, 50, 25, 80);

		// fragment alternative starts before requestAccess
		fixture.alternative.setPosition(5, 40, 35, 160);
		fixture.altRegion1.setPosition(5, 80, 35, 120);
		fixture.altRegion2.setPosition(5, 120, 35, 160);

		fixture.grantAccess.setPosition(15, 80, 25, 120);
		fixture.redirectRequest.setPosition(15, 120, 25, 160);

		assertInvalidElementCanBeFixed(fixture.alternative);
	}

	@Test
	public void testDetectingInValidMessageStartingBeforeItsContainingRegion() {
		fixture.interaction.using(getAllTenPrefs());
		fixture.interaction.setPosition(0, 0, 45, 170);

		fixture.user.setPosition(5, 0, 5, 60);
		fixture.webBrowser.setPosition(15, 0, 15, 130);
		fixture.applicationServer.setPosition(25, 0, 25, 130);

		fixture.obtainResource.setPosition(5, 20, 15, 50);
		fixture.requestAccess.setPosition(15, 50, 25, 80);

		fixture.alternative.setPosition(5, 80, 35, 160);
		fixture.altRegion1.setPosition(5, 80, 35, 120);
		fixture.altRegion2.setPosition(5, 120, 35, 160);

		// grantAccess starts before its containing region
		fixture.grantAccess.setPosition(15, 70, 25, 120);
		fixture.redirectRequest.setPosition(15, 120, 25, 160);

		assertInvalidElementCanBeFixed(fixture.grantAccess);
	}

	@Test
	public void testDetectingInValidMessageStartingAfterItsContainingRegion() {
		fixture.interaction.using(getAllTenPrefs());
		fixture.interaction.setPosition(0, 0, 100, 200);

		fixture.user.setPosition(5, 0, 5, 60);
		fixture.webBrowser.setPosition(15, 0, 15, 130);
		fixture.applicationServer.setPosition(25, 0, 25, 130);

		fixture.obtainResource.setPosition(5, 20, 15, 50);
		fixture.requestAccess.setPosition(15, 50, 25, 80);

		fixture.alternative.setPosition(5, 80, 35, 160);
		fixture.altRegion1.setPosition(5, 80, 35, 120);
		fixture.altRegion2.setPosition(5, 120, 35, 160);

		// grantAccess starts after its containing region
		fixture.grantAccess.setPosition(15, 130, 25, 140);
		fixture.redirectRequest.setPosition(15, 140, 25, 160);

		assertInvalidElementCanBeFixed(fixture.grantAccess);
	}

	protected void assertInvalidElementCanBeFixed(MInteractionElement violatingElement) {
		assertFalse(fixture.interaction.areAllYRangesValid());
		assertSameContent(Arrays.asList(violatingElement), fixture.interaction.getElementsWithInvalidYRange());

		violatingElement.fixPosition();

		assertTrue(fixture.interaction.areAllYRangesValid());
	}
}
