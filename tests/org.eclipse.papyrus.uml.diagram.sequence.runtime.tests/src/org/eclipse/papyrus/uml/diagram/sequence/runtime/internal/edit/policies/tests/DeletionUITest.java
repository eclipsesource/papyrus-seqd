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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.above;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Rule;
import org.junit.Test;

/**
 * This is the {@code DeletionUITest} type. Enjoy.
 *
 * @author Christian W. Damus
 */
@ModelResource("Nudging.di")
@Maximized
public class DeletionUITest extends AbstractGraphicalEditPolicyUITest {

	@Rule
	public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture
	private EditPart async1;

	@AutoFixture
	private PointList async1Geom;

	@AutoFixture
	private EditPart request1;

	@AutoFixture
	private PointList request1Geom;

	@AutoFixture
	private EditPart async2;

	@AutoFixture
	private PointList async2Geom;

	/**
	 * Initializes me.
	 */
	public DeletionUITest() {
		super();
	}

	@Test
	public void deleteMessage() {
		EObject message = async1.getAdapter(EObject.class);

		editor.select(async1Geom.getMidpoint());
		editor.delete();

		assertThat("Message edit-part not deleted", async1.getRoot(), nullValue());
		assertThat("Message element not deleted", message.eResource(), nullValue());

		PointList newRequest1Geom = getPoints(request1);
		assertThat("Message below not pulled up", newRequest1Geom.getMidpoint(),
				above(request1Geom.getMidpoint()));
	}

	@Test
	public void deleteOther() {
		EObject message = async2.getAdapter(EObject.class);

		editor.select(async2Geom.getMidpoint());
		editor.delete();

		assertThat("Message edit-part not deleted", async2.getRoot(), nullValue());
		assertThat("Message element not deleted", message.eResource(), nullValue());
	}

}
