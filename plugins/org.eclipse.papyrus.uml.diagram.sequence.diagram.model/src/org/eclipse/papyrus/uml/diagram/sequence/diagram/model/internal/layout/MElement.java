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

public abstract class MElement {

	private MPosition position;

	private int preferredHeight = 0;

	protected ILayoutPreferences getPreferences() {
		return getInteraction().getPreferences();
	}

	public abstract MInteraction getInteraction();

	public MPositionRange getYRange() {
		return new MPositionRange(getOrComputeAutoPosition().getBegin().getY(),
				getOrComputeAutoPosition().getEnd().getY());
	}

	private MPosition getOrComputeAutoPosition() {
		if (position == null) {
			return computeAutoPosition();
		}
		return position;
	}

	public int getHeight() {
		return getPosition().getHeight();
	}

	public void setPreferredHeight(int preferredHeight) {
		this.preferredHeight = preferredHeight;
	}

	public int getMinHeight() {
		return Integer.max(getInternalMinHeight(), preferredHeight);
	}

	protected abstract int getInternalMinHeight();

	public int getMinWidth() {
		return getPreferences().getDefaultWidth(this);
	}

	public void setPosition(int beginX, int beginY, int endX, int endY) {
		setPosition(new MPosition(beginX, beginY, endX, endY));
	}

	public void setPosition(MPosition position) {
		this.position = position;
	}

	public MPosition getPosition() {
		return position;
	}

	protected void resetToAutoPosition() {
		setPosition(computeAutoPosition());
	}

	protected abstract boolean isYRangeInvalid();

	public abstract void fixPosition();

	public abstract boolean hasGapToPreviousElement();

	protected abstract MPosition computeAutoPosition();

}
