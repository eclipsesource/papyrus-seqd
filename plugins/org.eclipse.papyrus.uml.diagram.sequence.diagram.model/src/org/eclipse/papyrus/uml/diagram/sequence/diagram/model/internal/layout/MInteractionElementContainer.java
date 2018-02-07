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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class MInteractionElementContainer<T extends MInteractionElementContainer<?>>
		extends MInteractionElement {

	/** The owned interaction elements in the correct semantical order. */
	private List<MInteractionElement> ownedElements = new ArrayList<>();

	public void clear() {
		ownedElements.clear();
	}

	public void addElements(MInteractionElement... interactionElements) {
		for (MInteractionElement interactionElement : interactionElements) {
			interactionElement.setContainer(this);
			this.ownedElements.add(interactionElement);
		}
	}

	@SuppressWarnings("unchecked")
	public T withElements(MInteractionElement... interactionElements) {
		addElements(interactionElements);
		return (T) this;
	}

	public List<MInteractionElement> getElements() {
		return Collections.unmodifiableList(ownedElements);
	}

	public List<MInteractionElement> getAllElements() {
		List<MInteractionElement> elements = new ArrayList<>();
		for (MInteractionElement interactionElement : ownedElements) {
			elements.add(interactionElement);
			if (interactionElement instanceof MInteractionElementContainer<?>) {
				MInteractionElementContainer<?> elementContainer = (MInteractionElementContainer<?>) interactionElement;
				elements.addAll(elementContainer.getAllElements());
			}
		}
		return elements;
	}

	public Optional<MInteractionElement> getElementBefore(MInteractionElement element) {
		int index = ownedElements.indexOf(element) - 1;
		if (index > -1) {
			return Optional.of(ownedElements.get(index));
		}
		return Optional.empty();
	}

	public Optional<MInteractionElement> getElementAfter(MInteractionElement element) {
		int index = ownedElements.indexOf(element) + 1;
		if (index < ownedElements.size()) {
			return Optional.of(ownedElements.get(index));
		}
		return Optional.empty();
	}

	public List<MInteractionElement> getElementsBefore(MInteractionElement element) {
		return getItemsBefore(ownedElements, element);
	}

	public List<MInteractionElement> getElementsAfter(MInteractionElement element) {
		return getItemsAfter(ownedElements, element);
	}

	public List<MInteractionElement> getAllElementsAfter(MInteractionElement element) {
		return getItemsAfter(getAllElements(), element);
	}

	public int getMinBeginYOfFirstChild(MInteractionElement child) {
		return getYRange().getBegin();
	}

	@Override
	protected void adjustPosition() {
		super.adjustPosition();
		getAllElements().forEach(MInteractionElement::adjustPosition);
	}

	protected void fixHeight() {
		adjustHeight();
		getContainer().ifPresent(MInteractionElementContainer::fixHeight);
	}

	protected void adjustHeight() {
		MInteractionElement lastElement = ownedElements.get(ownedElements.size() - 1);
		MPoint currentBegin = getPosition().getBegin();
		MPoint currentEnd = getPosition().getEnd();
		MPosition newPosition = new MPosition(currentBegin.getX(), currentBegin.getY(), currentEnd.getX(),
				lastElement.getPosition().getEnd().getY() + getPreferences().getPaddingBottom(this));
		setPosition(newPosition);
	}

	@Override
	protected int getInternalMinHeight() {
		return getPreferences().getPaddingTop(this)
				+ getElements().stream().mapToInt(MInteractionElement::getMinHeight).sum()
				+ getPreferences().getPaddingBottom(this);
	}

	@Override
	protected void resetToAutoPosition() {
		super.resetToAutoPosition();
		getElements().forEach(MElement::resetToAutoPosition);
	}

}
