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

public class MRegion extends MInteractionElementContainer<MRegion> {

	public MFragment getFragment() {
		return (MFragment) getContainer().get();
	}

	@Override
	public boolean hasGapToPreviousElement() {
		if (getFragment().getElementBefore(this).isPresent()) {
			return super.hasGapToPreviousElement();
		}
		return (getMinBeginY().orElse(0) + getPaddingTopOfFragment()) < getPosition().getBegin().getY();
	}

	@Override
	protected MPosition computeAutoPosition() {
		MPosition fragmentPosition = getFragment().getPosition();
		List<MInteractionElement> elementsBefore = getFragment().getElementsBefore(this);
		int heightBefore = elementsBefore.stream().mapToInt(MInteractionElement::getMinHeight).sum();

		int beginX = fragmentPosition.getBegin().getX();
		int beginY = fragmentPosition.getBegin().getY() + getPaddingTopOfFragment() + heightBefore;

		int endX = fragmentPosition.getEnd().getX();
		int endY = beginY + getMinHeight();

		return new MPosition(new MPoint(beginX, beginY), new MPoint(endX, endY));
	}

	protected int getPaddingTopOfFragment() {
		return getPreferences().getPaddingTop(getFragment());
	}

}
