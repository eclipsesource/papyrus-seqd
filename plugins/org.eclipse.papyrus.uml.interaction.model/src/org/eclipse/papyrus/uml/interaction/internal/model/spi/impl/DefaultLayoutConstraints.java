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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Shape;
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

	private final Map<String, Integer> standardLeftInsets;

	private final Map<String, Integer> standardTopInsets;

	private final Map<String, Integer> standardRightInsets;

	private final Map<String, Integer> standardBottomInsets;

	/**
	 * Initializes me.
	 */
	public DefaultLayoutConstraints() {
		super();

		standardXOffsets = loadXOffsets();
		standardYOffsets = loadYOffsets();

		standardLeftInsets = loadLeftInsets();
		standardTopInsets = loadTopInsets();
		standardRightInsets = loadRightInsets();
		standardBottomInsets = loadBottomInsets();
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
	public int getLeftInset(Shape shape) {
		return standardLeftInsets.getOrDefault(shape.getType(), ZERO).intValue();
	}

	@Override
	public int getTopInset(Shape shape) {
		return standardTopInsets.getOrDefault(shape.getType(), ZERO).intValue();
	}

	@Override
	public int getRightInset(Shape shape) {
		return standardRightInsets.getOrDefault(shape.getType(), ZERO).intValue();
	}

	@Override
	public int getBottomInset(Shape shape) {
		return standardBottomInsets.getOrDefault(shape.getType(), ZERO).intValue();
	}

	private static Map<String, Integer> loadXOffsets() {
		Map<String, Integer> result = new HashMap<>();
		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadYOffsets() {
		Map<String, Integer> result = new HashMap<>();

		// The size of the interaction frame's pentagon label
		result.put("Interaction_Contents", 30);

		return result;
	}

	private static Map<String, Integer> loadLeftInsets() {
		Map<String, Integer> result = new HashMap<>();

		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadTopInsets() {
		Map<String, Integer> result = new HashMap<>();

		result.put("Shape_Lifeline_Header", 5);

		return result;
	}

	private static Map<String, Integer> loadRightInsets() {
		Map<String, Integer> result = new HashMap<>();

		return result;
	}

	@SuppressWarnings("boxing")
	private static Map<String, Integer> loadBottomInsets() {
		Map<String, Integer> result = new HashMap<>();

		result.put("Shape_Lifeline_Header", 5);

		return result;
	}

}
