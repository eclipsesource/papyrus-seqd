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
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper.ViewTypes;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;

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
		return standardXOffsets.getOrDefault(shapeCompartment.getType(), ZERO).intValue();
	}

	@Override
	public int getYOffset(Compartment shapeCompartment) {
		return standardYOffsets.getOrDefault(shapeCompartment.getType(), ZERO).intValue();
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

		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadHeights() {
		Map<String, Integer> result = new HashMap<>();

		result.put("Shape_Lifeline_Body", 400);
		result.put(applyModifier(ARROW, "Edge_Message"), 5);
		result.put("Shape_Execution_Specification", 40);
		result.put("Interaction_Contents", 180);
		result.put(applyModifier(ANCHOR, "Shape_Lifeline_Body"), 10);

		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadWidths() {
		Map<String, Integer> result = new HashMap<>();

		result.put("Shape_Lifeline_Body", 1);
		result.put(applyModifier(ARROW, "Edge_Message"), 5);
		result.put("Interaction_Contents", 45);
		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadPaddings() {
		Map<String, Integer> result = new HashMap<>();

		result.put(forOrientation(BOTTOM, "Shape_Lifeline_Body"), 10);

		return result;
	}

}
