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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MFragment extends MInteractionElementContainer<MFragment> {

	private List<MLifeline> coveredLifelines = new ArrayList<>();

	public void addCoveredLifelines(MLifeline... lifelines) {
		for (MLifeline lifeline : lifelines) {
			coveredLifelines.add(lifeline);
		}
	}

	public void addContainedRegions(MRegion... regions) {
		super.addElements(regions);
	}

	public MFragment covering(MLifeline... lifelines) {
		addCoveredLifelines(lifelines);
		return this;
	}

	public MFragment with(MRegion... regions) {
		addContainedRegions(regions);
		return this;
	}

	public List<MLifeline> getCoveredLifelines() {
		return Collections.unmodifiableList(coveredLifelines);
	}
	
	@Override
	protected MPosition computeAutoPosition() {
		if (getCoveredLifelines().isEmpty())
			throw new IllegalStateException("Cannot compute position of fragments that doesn't cover lifelines.");

		int beginX = getLeftestLifelineBeginX() - getPreferences().getFragmentPaddingLeft();
		int endX = getRightestLifelineEndX() + getPreferences().getFragmentPaddingRight();
		int beginY = getMinBeginY().orElse(
				// fall back to the first position of top-most covered lifeline
				getCoveredLifelines().stream().mapToInt(MLifeline::getMinYOfFirstInteractionElement).min().getAsInt());
		int endY = beginY + getMinHeight();

		return new MPosition(new MPoint(beginX, beginY), new MPoint(endX, endY));
	}

	private int getLeftestLifelineBeginX() {
		return getCoveredLifelines().stream() //
				.map((l) -> l.computeAutoPosition().getBegin()) //
				.mapToInt((p) -> p.getX()) //
				.min().orElseThrow(IllegalStateException::new);
	}

	private int getRightestLifelineEndX() {
		return getCoveredLifelines().stream() //
				.map((l) -> l.computeAutoPosition().getEnd()) //
				.mapToInt((p) -> p.getX()) //
				.max().orElseThrow(IllegalStateException::new);
	}

}
