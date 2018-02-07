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

public class LayoutModelGapDetectionAndFixingTest {

	ModelWithFragmentsFixture fixture;

	@Before
	public void initFixture() {
		fixture = new ModelWithFragmentsFixture();
	}

	@Test
	public void testDetectingThatNoGapExists() {
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

		assertTrue(fixture.interaction.isLayoutFreeOfGaps());
	}

	@Test
	public void testDetectingAndFixingGapBetweenMessages() {
		fixture.interaction.using(getAllTenPrefs());
		fixture.interaction.setPosition(0, 0, 100, 200);

		fixture.user.setPosition(5, 0, 5, 60);
		fixture.webBrowser.setPosition(15, 0, 15, 130);
		fixture.applicationServer.setPosition(25, 0, 25, 130);

		fixture.obtainResource.setPosition(5, 20, 15, 50);
		// here is a gap of 10 units
		fixture.requestAccess.setPosition(15, 60, 25, 80);

		fixture.alternative.setPosition(5, 80, 35, 160);
		fixture.altRegion1.setPosition(5, 80, 35, 120);
		fixture.altRegion2.setPosition(5, 120, 35, 160);

		fixture.grantAccess.setPosition(15, 80, 25, 120);
		fixture.redirectRequest.setPosition(15, 120, 25, 160);

		assertFalse(fixture.interaction.isLayoutFreeOfGaps());
		assertSameContent(Arrays.asList(fixture.requestAccess),
				fixture.interaction.getElementsWithGapToPreviousElement());

		fixture.requestAccess.fixPosition();

		assertTrue(fixture.interaction.isLayoutFreeOfGaps());
	}

	@Test
	public void testDetectingAndFixingGapWithinFragments() {
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

		fixture.grantAccess.setPosition(15, 90, 25, 120);
		fixture.redirectRequest.setPosition(15, 125, 25, 160);

		assertFalse(fixture.interaction.isLayoutFreeOfGaps());
		assertSameContent(Arrays.asList(fixture.grantAccess, fixture.redirectRequest),
				fixture.interaction.getElementsWithGapToPreviousElement());

		fixture.grantAccess.fixPosition();
		// redirectRequest doesn't have to be fixed as it is adjusted as a consequence,
		// as all following elements after the element to be fixed are adjusted
		// automatically

		assertTrue(fixture.interaction.isLayoutFreeOfGaps());
	}

}
