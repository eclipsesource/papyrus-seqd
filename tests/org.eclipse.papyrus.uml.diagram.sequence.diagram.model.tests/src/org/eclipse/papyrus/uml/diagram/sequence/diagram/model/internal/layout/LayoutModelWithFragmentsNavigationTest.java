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
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class LayoutModelWithFragmentsNavigationTest {

	ModelWithFragmentsFixture fixture;

	@Before
	public void initFixture() {
		fixture = new ModelWithFragmentsFixture();
	}

	@Test
	public void testThatEveryoneKnowsItsInteractionWithFragments() {
		for (MLifeline line : Arrays.asList(fixture.user, fixture.webBrowser, fixture.applicationServer)) {
			assertSame(fixture.interaction, line.getInteraction(), line + " has wrong interaction");
		}
		for (MInteractionElement element : Arrays.asList(fixture.obtainResource, fixture.requestAccess,
				fixture.alternative, fixture.grantAccess, fixture.redirectRequest)) {
			assertSame(fixture.interaction, element.getInteraction(), element + " has wrong interaction");
		}
		for (MRegion region : Arrays.asList(fixture.altRegion1, fixture.altRegion2)) {
			assertSame(fixture.interaction, region.getInteraction());
		}
	}

	@Test
	public void testGettingNextInteractionElement() {
		assertSame(fixture.requestAccess, fixture.obtainResource.getNextElement().get());
		assertSame(fixture.alternative, fixture.requestAccess.getNextElement().get());
		assertSame(Optional.empty(), fixture.alternative.getNextElement());
	}

	@Test
	public void testGettingPreviousInteractionElement() {
		assertSame(fixture.requestAccess, fixture.alternative.getPreviousElement().get());
		assertSame(fixture.obtainResource, fixture.requestAccess.getPreviousElement().get());
		assertSame(Optional.empty(), fixture.obtainResource.getPreviousElement());
	}

	@Test
	public void testGettingRelatedElementsOfLifeline() {
		assertSameContent(Arrays.asList(fixture.obtainResource), fixture.user.getRelatedElements());
		// "grantAccess" and "redirectRequest" are not included because they are grouped
		// in the fragment "alternative"
		assertSameContent(Arrays.asList(fixture.obtainResource, fixture.requestAccess, fixture.alternative),
				fixture.webBrowser.getRelatedElements());
	}

}
