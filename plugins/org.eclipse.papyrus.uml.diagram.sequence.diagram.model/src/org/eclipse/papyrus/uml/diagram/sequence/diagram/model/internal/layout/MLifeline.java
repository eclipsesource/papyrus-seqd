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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MLifeline extends MElement {

	private int heightOfHead = -1;

	private MInteraction interaction;

	protected void setInteraction(MInteraction parent) {
		this.interaction = parent;
	}

	@Override
	public MInteraction getInteraction() {
		return interaction;
	}

	public void setHeightOfHead(int heightOfHead) {
		this.heightOfHead = heightOfHead;
	}

	public int getHeightOfHead() {
		if (heightOfHead < 0) {
			return getPreferences().getDefaultHeightOfLifelineHead();
		}
		return heightOfHead;
	}

	protected int getMinBeginY() {
		// TODO beginY must be overwritten for create messages target lifelines
		return 0;
	}

	public int getMinYOfFirstInteractionElement() {
		return getMinBeginY() + getPreferences().getPaddingTop(this) + getHeightOfHead();
	}

	@Override
	protected int getInternalMinHeight() {
		int heightWithoutPaddings = getHeightOfHead() + getMinBodyHeight();
		return getPreferences().getPaddingTop(this) + heightWithoutPaddings + getPreferences().getPaddingBottom(this);
	}

	public int getMinBodyHeight() {
		// TODO if a create message is targeting this lifelines, we have to start from
		// the height of the incoming create message and not at 0. So we shouldn't sum
		// up all messages before the last one, but only until the create message of
		// this lifeline.
		int minHeight;
		List<MInteractionElement> relatedElements = getRelatedElements();
		if (!relatedElements.isEmpty()) {
			MInteractionElement lastRelatedElement = relatedElements.get(relatedElements.size() - 1);
			List<MInteractionElement> elementsBefore = getInteraction().getElementsBefore(lastRelatedElement);
			Stream<MInteractionElement> allElementsFromLastToFirst = Stream.concat( //
					Stream.of(lastRelatedElement), elementsBefore.stream());
			minHeight = allElementsFromLastToFirst.mapToInt(MElement::getMinHeight).sum();
		} else {
			minHeight = 0;
		}
		return minHeight;
	}

	/**
	 * Returns interaction elements of this lifeline's {@link #getInteraction()
	 * interaction} that are {@link #isRelated(MInteractionElement) related} to this
	 * lifeline.
	 * <p>
	 * If a {@link MFragment fragment} covering this lifeline contains more elements
	 * that are related to this lifeline, these contained elements will <b>not</b>
	 * show up in this list, as this list only contains the root elements and
	 * doesn't recursively go into fragments.
	 * </p>
	 * 
	 * @return The list of related interaction elements on the
	 */
	public List<MInteractionElement> getRelatedElements() {
		return getRelatedInteractionElementsStream().collect(Collectors.toList());
	}

	protected Stream<MInteractionElement> getRelatedInteractionElementsStream() {
		return getInteraction().getElements().stream().filter(this::isRelated);
	}

	/**
	 * Specifies the related interaction elements among all interaction elements of
	 * {@link #getInteraction() the interaction}.
	 * <p>
	 * An interaction element is related, if it is either a fragment <i>covering</i>
	 * this lifeline or a message that has this lifeline as <i>source</i> or
	 * <i>target</i>.
	 * </p>
	 * 
	 * @param element
	 *            The element to test.
	 * @return <code>true</code> if <code>element</code> is related,
	 *         <code>false</code> otherwise.
	 */
	public boolean isRelated(MInteractionElement element) {
		final boolean isRelated;
		if (element instanceof MFragment) {
			isRelated = ((MFragment) element).getCoveredLifelines().contains(this);
		} else if (element instanceof MRegion) {
			isRelated = ((MRegion) element).getFragment().getCoveredLifelines().contains(this);
		} else if (element instanceof MMessage) {
			MMessage message = (MMessage) element;
			isRelated = message.getSource() == this || message.getTarget() == this;
		} else {
			isRelated = false;
		}
		return isRelated;
	}

	@Override
	protected boolean isYRangeInvalid() {
		// TODO implement
		return false;
	}

	@Override
	public void fixPosition() {
		// TODO implement similar to MInteractionElementGroup
	}

	@Override
	public boolean hasGapToPreviousElement() {
		// TODO might only be necessary for created lifelines (instantiated by create
		// messages)
		return false;
	}

	@Override
	protected MPosition computeAutoPosition() {
		int spaceLeft = getInteraction().getLifelinesBefore(this).stream().mapToInt(MElement::getMinWidth).sum();
		int ownHorizontalSpace = getMinWidth() / 2;
		int x = spaceLeft + ownHorizontalSpace;
		return new MPosition(new MPoint(x, getMinBeginY()), new MPoint(x, getMinHeight()));
	}

}
