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

package org.eclipse.papyrus.uml.interaction.internal.model.spi.impl;

import static java.lang.Math.abs;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.applyModifier;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.Modifiers.ANCHOR;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.Modifiers.ARROW;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.Modifiers.NO_MODIFIER;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition.BOTTOM;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;

/**
 * Encoding of the default layout constraints for shapes, compartments, etc.
 *
 * @author Christian W. Damus
 */
public class DefaultLayoutConstraints implements LayoutConstraints {

	private static Integer ZERO = Integer.valueOf(0);

	private final Map<String, Integer> standardXOffsets;

	private final Map<String, Integer> standardYOffsets;

	private final Map<String, Integer> standardHeights;

	private final Map<String, Integer> standardWidths;

	private final Map<String, Integer> standardPaddings;

	/**
	 * Initializes me.
	 */
	public DefaultLayoutConstraints() {
		super();

		standardXOffsets = loadXOffsets();
		standardYOffsets = loadYOffsets();
		standardHeights = loadHeights();
		standardWidths = loadWidths();
		standardPaddings = loadPaddings();
	}

	@Override
	public int getXOffset(Compartment shapeCompartment) {
		return getXOffset(shapeCompartment.getType());
	}

	@Override
	public int getXOffset(String viewType) {
		return standardXOffsets.getOrDefault(viewType, ZERO).intValue();
	}

	@Override
	public int getYOffset(Compartment shapeCompartment) {
		return getYOffset(shapeCompartment.getType());
	}

	@Override
	public int getYOffset(String viewType) {
		return standardYOffsets.getOrDefault(viewType, ZERO).intValue();
	}

	@Override
	public int getMinimumHeight(View view) {
		return getMinimumHeight(view.getType());
	}

	@Override
	public int getMinimumHeight(String viewType) {
		return getMinimumHeight(viewType, NO_MODIFIER);
	}

	@Override
	public int getMinimumHeight(View view, String modifier) {
		return getMinimumHeight(view.getType(), modifier);
	}

	@Override
	public int getMinimumHeight(String viewType, String modifier) {
		return standardHeights.getOrDefault(applyModifier(modifier, viewType), ZERO).intValue();
	}

	@Override
	public int getMinimumWidth(View view) {
		return getMinimumWidth(view.getType());
	}

	@Override
	public int getMinimumWidth(String viewType) {
		return getMinimumWidth(viewType, NO_MODIFIER);
	}

	@Override
	public int getMinimumWidth(View view, String modifier) {
		return getMinimumWidth(view.getType(), modifier);
	}

	@Override
	public int getMinimumWidth(String viewType, String modifier) {
		return standardWidths.getOrDefault(applyModifier(modifier, viewType), ZERO).intValue();
	}

	@Override
	public int getPadding(RelativePosition orientation, View view) {
		return getPadding(orientation, view.getType());
	}

	@Override
	public int getPadding(RelativePosition orientation, String viewType) {
		return standardPaddings.getOrDefault(forOrientation(orientation, viewType), ZERO).intValue();
	}

	private static String forOrientation(RelativePosition orientation, String type) {
		return type + "_" + orientation.toString(); //$NON-NLS-1$
	}

	@Override
	public double getAsyncMessageSlopeThreshold() {
		return 3.0;
	}

	@Override
	public boolean isAsyncMessageSlope(double x1, double y1, double x2, double y2) {
		if (abs(x2 - x1) < 0.1) {
			return true; // Vertical is as asynchronous as it gets
		} else if (abs(y2 - y1) <= 5.0) {
			return false; // Allow for some pointer sloppiness by the user
		}

		double slope = (abs(y2 - y1) / abs(x2 - x1)) * 100.0;

		return slope >= 3.0;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadXOffsets() {
		Map<String, Integer> result = new HashMap<>();

		// Inset of the viewpoint figure
		result.put(ViewTypes.INTERACTION_CONTENTS, 5);

		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadYOffsets() {
		Map<String, Integer> result = new HashMap<>();

		// The size of the interaction frame's pentagon label
		result.put(ViewTypes.INTERACTION_CONTENTS, 30);
		result.put(ViewTypes.LIFELINE_HEADER, 25);
		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadHeights() {
		Map<String, Integer> result = new HashMap<>();

		result.put(ViewTypes.LIFELINE_BODY, 400);
		result.put(applyModifier(ARROW, ViewTypes.MESSAGE), 5);
		result.put(ViewTypes.EXECUTION_SPECIFICATION, 40);
		result.put(ViewTypes.INTERACTION_CONTENTS, 180);
		result.put(applyModifier(ANCHOR, ViewTypes.LIFELINE_BODY), 10);
		result.put(ViewTypes.LIFELINE_HEADER, 28);
		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadWidths() {
		Map<String, Integer> result = new HashMap<>();

		result.put(ViewTypes.LIFELINE_BODY, 1);
		result.put(applyModifier(ARROW, ViewTypes.MESSAGE), 5);
		result.put(ViewTypes.INTERACTION_CONTENTS, 45);
		result.put(ViewTypes.LIFELINE_HEADER, 100);
		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadPaddings() {
		Map<String, Integer> result = new HashMap<>();

		result.put(forOrientation(BOTTOM, ViewTypes.LIFELINE_BODY), 10);

		return result;
	}

}
