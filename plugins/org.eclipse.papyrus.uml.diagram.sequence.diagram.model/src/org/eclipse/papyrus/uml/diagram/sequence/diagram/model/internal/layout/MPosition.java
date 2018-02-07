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

public class MPosition {

	private MPoint begin, end;

	public MPosition(int beginX, int beginY, int endX, int endY) {
		this(new MPoint(beginX, beginY), new MPoint(endX, endY));
	}

	public MPosition(MPoint begin, MPoint end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	public MPoint getBegin() {
		return begin;
	}

	public MPoint getEnd() {
		return end;
	}

	protected void moveToY(Integer y) {
		int height = getHeight();
		getBegin().setY(y);
		getEnd().setY(y + height);
	}

	public int getHeight() {
		return getEnd().getY() - getBegin().getY();
	}

	@Override
	public String toString() {
		return "MPosition [begin=" + begin + ", end=" + end + "]";
	}

}
