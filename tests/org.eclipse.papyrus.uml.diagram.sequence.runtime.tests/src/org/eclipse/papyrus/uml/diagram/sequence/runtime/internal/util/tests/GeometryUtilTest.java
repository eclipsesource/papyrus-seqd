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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.tests;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isRect;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isSize;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NotationMatchers.isBounds;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util.GeometryUtil;
import org.junit.Test;

/**
 * Tests for the {@link GeometryUtil} class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
public class GeometryUtilTest {

	@Test
	public void asBounds() {
		Bounds bounds = GeometryUtil.asBounds(new Rectangle(1, 2, 3, 4));
		assertThat(bounds, isBounds(1, 2, 3, 4));
	}

	@Test
	public void asRectangle() {
		Rectangle rect = GeometryUtil.asRectangle(bounds(1, 2, 3, 4));
		assertThat(rect, isRect(1, 2, 3, 4));
	}

	@Test
	public void getMoveDelta() {
		Rectangle from = new Rectangle(1, 2, 3, 4);
		Rectangle to = new Rectangle(11, 22, 33, 44);
		Point delta = GeometryUtil.getMoveDelta(from, to);
		assertThat(delta, isPoint(10, 20));
	}

	@Test
	public void getSizeDelta() {
		Rectangle from = new Rectangle(1, 2, 3, 4);
		Rectangle to = new Rectangle(11, 22, 33, 44);
		Dimension delta = GeometryUtil.getSizeDelta(from, to);
		assertThat(delta, isSize(30, 40));
	}

	//
	// Test framework
	//

	static Rectangle rect(int x, int y, int width, int height) {
		return new Rectangle(x, y, width, height);
	}

	static Bounds bounds(int x, int y, int width, int height) {
		Bounds result = NotationFactory.eINSTANCE.createBounds();
		result.setX(x);
		result.setY(y);
		result.setWidth(width);
		result.setHeight(height);
		return result;
	}

}
