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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class MInteractionElement extends MElement {

	private Optional<MInteractionElementContainer<?>> container = Optional.empty();

	public void setContainer(MInteractionElementContainer<?> mMessageGroup) {
		this.container = Optional.of(mMessageGroup);
	}

	public Optional<MInteractionElementContainer<?>> getContainer() {
		return container;
	}

	@Override
	public MInteraction getInteraction() {
		MInteraction interaction = null;
		if (getContainer().isPresent()) {
			if (getContainer().get() instanceof MInteraction) {
				interaction = (MInteraction) getContainer().get();
			} else if (getContainer().get() instanceof MInteractionElementContainer) {
				interaction = getContainer().get().getInteraction();
			}
		}
		return interaction;
	}

	public Optional<MInteractionElement> getPreviousElement() {
		return getContainer().flatMap(container -> container.getElementBefore(this));
	}

	public Optional<MInteractionElement> getNextElement() {
		return getContainer().flatMap(container -> container.getElementAfter(this));
	}

	public List<MInteractionElement> getElementsBefore() {
		return getContainer().map(container -> container.getElementsBefore(this)).orElse(Collections.emptyList());
	}

	public List<MInteractionElement> getElementsAfter() {
		return getContainer().map(container -> container.getElementsAfter(this)).orElse(Collections.emptyList());
	}

	@Override
	protected boolean isYRangeInvalid() {
		return isLocatedBeforePreviousElement() || isOutsideOfParentRange();
	}

	private boolean isLocatedBeforePreviousElement() {
		MPositionRange previousRange = getPreviousElement().map(MInteractionElement::getYRange)
				.orElse(new MPositionRange(0, 0));
		return getYRange().getBegin() < previousRange.getEnd();
	}

	private boolean isOutsideOfParentRange() {
		return getContainer().map(c -> isOutsideOfRange(c)).orElse(false);
	}

	protected boolean isOutsideOfRange(MInteractionElementContainer<?> c) {
		return c.getYRange().getBegin() > getYRange().getBegin() || getYRange().getEnd() > c.getYRange().getEnd();
	}

	@Override
	public void fixPosition() {
		adjustPosition();
		adjustNextElementsInSameContainer();
		adjustNextElementsAfterContainer();
	}

	protected void adjustPosition() {
		getPosition().moveToY(getMinBeginY().orElse(0));

		if (isLastElementInContainer()) {
			getContainer().ifPresent(MInteractionElementContainer::fixHeight);
		}
	}

	protected boolean isLastElementInContainer() {
		return !getContainer().flatMap(c -> c.getElementAfter(this)).isPresent();
	}

	protected void adjustNextElementsInSameContainer() {
		getContainer().map(container -> container.getElementsAfter(this))
				.ifPresent(elementsAfter -> elementsAfter.forEach(MInteractionElement::adjustPosition));
	}

	protected void adjustNextElementsAfterContainer() {
		getContainer().flatMap(MInteractionElement::getContainer).map( //
				containersContainer -> containersContainer.getElementsAfter(getContainer().get())).ifPresent( //
						elementsAfter -> elementsAfter.forEach(MInteractionElement::adjustPosition));
	}

	protected Optional<Integer> getMinBeginY() {
		Optional<Integer> previousYEnd = getPreviousElement().map(previous -> previous.getYRange().getEnd());
		if (previousYEnd.isPresent()) {
			return previousYEnd;
		}
		return getContainer().map(c -> c.getMinBeginYOfFirstChild(this));
	}

	@Override
	public boolean hasGapToPreviousElement() {
		return getMinBeginY().orElse(0) < getPosition().getBegin().getY();
	}

}
