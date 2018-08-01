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

package org.eclipse.papyrus.uml.interaction.internal.model.spi.impl.tests;

import static org.eclipse.papyrus.uml.interaction.tests.matchers.NotationMatchers.isBounds;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.internal.model.spi.impl.DefaultLayoutConstraints;
import org.eclipse.papyrus.uml.interaction.internal.model.spi.impl.DefaultLayoutHelper;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link DefaultLayoutHelper} class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings({"nls", "restriction" })
@ModelResource({"lifelines_layout.uml", "lifelines_layout.notation" })
public class DefaultLayoutHelperTest {

	@Rule
	public final ModelFixture.Edit model = new ModelFixture.Edit();

	private DefaultLayoutHelper helper;

	/**
	 * Initializes me.
	 */
	public DefaultLayoutHelperTest() {
		super();
	}

	@Test
	public void getAdjustedBounds_lifeline() {
		Lifeline centre = model.getElement("AnchorsModel::LifelineHeaderAnchor::CenterLine", Lifeline.class);
		Node view = require(model.vertex(centre).getDiagramView(), Node.class);

		Bounds current = bounds(view);
		Bounds bounds = helper.getAdjustedBounds(centre, view, bounds(1000, 1000, 150, 150));
		assertThat(bounds, isBounds(1000, current.getY(), 150, current.getHeight()));
	}

	//
	// Test framework
	//

	@Before
	public void createSUT() {
		helper = new DefaultLayoutHelper(model.getEditingDomain(), DefaultLayoutConstraints::new);
	}

	@After
	public void destroySUT() {
		helper = null;
	}

	static <T> T require(Object object, Class<T> type) {
		assertThat("object is not a " + type.getSimpleName(), object, instanceOf(type));
		return type.cast(object);
	}

	static Bounds bounds(View view) {
		return require(require(view, Node.class).getLayoutConstraint(), Bounds.class);
	}

	static Bounds bounds(int x, int y, int width, int height) {
		Bounds result = NotationFactory.eINSTANCE.createBounds();
		result.setX(x);
		result.setY(y);
		result.setWidth(width);
		result.setHeight(height);
		return result;
	}

	//
	// Tests
	//

	@Test
	public void test_IDENTITY_ANCHOR_PATTERN() {
		Matcher positive = DefaultLayoutHelper.IDENTITY_ANCHOR_PATTERN.matcher("left;24");
		Matcher negative = DefaultLayoutHelper.IDENTITY_ANCHOR_PATTERN.matcher("left;-24");
		assertTrue(positive.matches());
		assertTrue(negative.matches());
		assertEquals(24, Integer.parseInt(positive.group(2)));
		assertEquals(-24, Integer.parseInt(negative.group(2)));
	}
}
