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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class LayoutModelWithoutFragmentsNavigationTest {

	ModelWithoutFragmentsFixture fixture;

	@Before
	public void initFixture() {
		fixture = new ModelWithoutFragmentsFixture();
	}

	@Test
	public void testThatEveryoneKnowsItsInteractionWithoutFragments() {
		for (MLifeline line : Arrays.asList(fixture.user, fixture.webBrowser, fixture.applicationServer,
				fixture.authenticationServer)) {
			assertSame(fixture.interaction, line.getInteraction(), line + " has wrong interaction");
		}
		for (MInteractionElement element : Arrays.asList(fixture.obtainResource, fixture.requestAccess,
				fixture.redirectRequest, fixture.authorize, fixture.permissionForm1, fixture.permissionForm2)) {
			assertSame(fixture.interaction, element.getInteraction(), element + " has wrong interaction");
		}
	}

	@Test
	public void testGettingNextInteractionElement() {
		assertSame(fixture.requestAccess, fixture.obtainResource.getNextElement().get());
		assertSame(fixture.redirectRequest, fixture.requestAccess.getNextElement().get());
		assertSame(fixture.authorize, fixture.redirectRequest.getNextElement().get());
		assertSame(fixture.permissionForm1, fixture.authorize.getNextElement().get());
		assertSame(fixture.permissionForm2, fixture.permissionForm1.getNextElement().get());
		assertEquals(Optional.empty(), fixture.permissionForm2.getNextElement());
	}

	@Test
	public void testGettingPreviousInteractionElement() {
		assertEquals(Optional.empty(), fixture.obtainResource.getPreviousElement());
		assertSame(fixture.obtainResource, fixture.requestAccess.getPreviousElement().get());
		assertSame(fixture.requestAccess, fixture.redirectRequest.getPreviousElement().get());
		assertSame(fixture.redirectRequest, fixture.authorize.getPreviousElement().get());
		assertSame(fixture.authorize, fixture.permissionForm1.getPreviousElement().get());
		assertSame(fixture.permissionForm1, fixture.permissionForm2.getPreviousElement().get());
	}

	@Test
	public void testGettingInteractionElementsBefore() {
		List<MMessage> expected = Arrays.asList(fixture.obtainResource, fixture.requestAccess, fixture.redirectRequest,
				fixture.authorize);
		assertSameContent(expected, fixture.permissionForm1.getElementsBefore());

		expected = Arrays.asList(fixture.obtainResource, fixture.requestAccess, fixture.redirectRequest);
		assertSameContent(expected, fixture.authorize.getElementsBefore());

		expected = Arrays.asList(fixture.obtainResource, fixture.requestAccess);
		assertSameContent(expected, fixture.redirectRequest.getElementsBefore());

		assertEquals(0, fixture.obtainResource.getElementsBefore().size());
	}

	@Test
	public void testGettingInteractionElementsAfter() {
		List<MMessage> expected = Arrays.asList(fixture.permissionForm2);
		assertSameContent(expected, fixture.permissionForm1.getElementsAfter());

		expected = Arrays.asList(fixture.permissionForm1, fixture.permissionForm2);
		assertSameContent(expected, fixture.authorize.getElementsAfter());

		expected = Arrays.asList(fixture.authorize, fixture.permissionForm1, fixture.permissionForm2);
		assertSameContent(expected, fixture.redirectRequest.getElementsAfter());

		assertEquals(0, fixture.permissionForm2.getElementsAfter().size());
	}

}
