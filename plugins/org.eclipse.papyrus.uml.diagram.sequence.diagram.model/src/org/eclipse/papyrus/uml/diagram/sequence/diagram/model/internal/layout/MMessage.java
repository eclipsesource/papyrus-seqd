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

public class MMessage extends MInteractionElement {

	private MLifeline source;
	private MLifeline target;

	public MMessage(MLifeline source, MLifeline target) {
		super();
		this.source = source;
		this.target = target;
	}

	public MLifeline getSource() {
		return source;
	}

	public MLifeline getTarget() {
		return target;
	}

	@Override
	protected int getInternalMinHeight() {
		return getPreferences().getPaddingTop(this) //
				+ getPreferences().getMessageHeight() //
				+ getPreferences().getPaddingBottom(this);
	}

	@Override
	protected MPosition computeAutoPosition() {
		MPosition sourcePosition = source.computeAutoPosition();
		MPosition targetPosition = target.computeAutoPosition();

		int beginX = Integer.min(sourcePosition.getBegin().getX(), targetPosition.getBegin().getX());
		int endX = Integer.max(sourcePosition.getBegin().getX(), targetPosition.getBegin().getX());
		int beginY = getMinBeginY().orElse(source.getMinYOfFirstInteractionElement());
		int endY = beginY + getMinHeight();

		return new MPosition(new MPoint(beginX, beginY), new MPoint(endX, endY));
	}

}
