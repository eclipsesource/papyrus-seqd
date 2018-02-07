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

import static org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout.ListUtils.getItemsAfter;
import static org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout.ListUtils.getItemsBefore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MInteraction extends MInteractionElementContainer<MInteraction> {

	private ILayoutPreferences preferences;

	private List<MLifeline> ownedLifelines = new ArrayList<>();

	protected ILayoutPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(ILayoutPreferences preferences) {
		this.preferences = preferences;
	}

	public void clear() {
		super.clear();
		ownedLifelines.clear();
	}

	public void addLifelines(MLifeline... lifelines) {
		for (MLifeline lifeline : lifelines) {
			lifeline.setInteraction(this);
			this.ownedLifelines.add(lifeline);
		}
	}

	public MInteraction withLifelines(MLifeline... lifelines) {
		addLifelines(lifelines);
		return this;
	}

	public MInteraction using(ILayoutPreferences preferences) {
		setPreferences(preferences);
		return this;
	}

	public List<MLifeline> getLifelinesBefore(MLifeline mLifeline) {
		return getItemsBefore(ownedLifelines, mLifeline);
	}

	public List<MLifeline> getLifelinesAfter(MLifeline mLifeline) {
		return getItemsAfter(ownedLifelines, mLifeline);
	}

	public boolean areAllYRangesValid() {
		return !getAllElements().stream().anyMatch(MInteractionElement::isYRangeInvalid);
	}

	public List<MInteractionElement> getElementsWithInvalidYRange() {
		return getAllElements().stream().filter(MInteractionElement::isYRangeInvalid).collect(Collectors.toList());
	}

	public boolean isLayoutFreeOfGaps() {
		return !getAllElements().stream().anyMatch(MInteractionElement::hasGapToPreviousElement);
	}

	public List<MInteractionElement> getElementsWithGapToPreviousElement() {
		return getAllElements().stream().filter(MInteractionElement::hasGapToPreviousElement)
				.collect(Collectors.toList());
	}

	@Override
	public int getMinBeginYOfFirstChild(MInteractionElement child) {
		// elements in an interaction may not start after begin of interaction but
		// at the start of their lowest related lifeline
		return ownedLifelines.stream().filter(l -> l.isRelated(child))
				.mapToInt(l -> l.getMinYOfFirstInteractionElement()).max().orElse(0);
	}

	@Override
	public int getMinHeight() {
		return getPreferences().getPaddingTop(this) + getHeightOfHighestLifeline()
				+ getPreferences().getPaddingBottom(this);
	}

	protected int getHeightOfHighestLifeline() {
		return ownedLifelines.stream().mapToInt(MLifeline::getMinHeight).max().orElse(0);
	}

	/**
	 * Resets the layout of the entire interaction with an automatic layout.
	 * <p>
	 * This will clear all pre-set positions of this interaction and its direct and
	 * indirect children. The {@link #setPreferredHeight(int) preferred heights}
	 * will be respected, if they are larger than the minimum heights.
	 * </p>
	 */
	public void applyAutoLayout() {
		this.setPosition(computeAutoPosition());
		ownedLifelines.forEach(MElement::resetToAutoPosition);
		getElements().forEach(MElement::resetToAutoPosition);
	}

	@Override
	protected MPosition computeAutoPosition() {
		return new MPosition(new MPoint(0, 0), new MPoint(getMinWidth(), getMinHeight()));
	}

}
