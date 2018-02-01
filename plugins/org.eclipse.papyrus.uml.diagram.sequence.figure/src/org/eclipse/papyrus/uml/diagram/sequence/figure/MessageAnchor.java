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
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.figure;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

/**
 * This anchor is a bit specific, as its position depends on the previous anchor on the line and the standard owner of the anchor.
 */
public class MessageAnchor extends AbstractConnectionAnchor {

	private MessageAnchor previousAnchor;
	private int delta_y = 20;
	private String name;
	/** stores locally position. should be set to null when invalidated */
	private Point point;
	private Point referencePoint;
	
	public MessageAnchor(IFigure owner, MessageAnchor previousAnchor) {
		super(owner);
		this.previousAnchor = previousAnchor;
	}
	
	@Override
	public Point getLocation(Point reference) {
		if(point == null) {
			point = getReferencePoint().getCopy().translate(new Point(0, delta_y)); // delta y
			// System.err.print("location for "+getName()+ ": ");
			// System.err.println(point.x+", "+point.y);
		}
		return point;
	}

	public String getName() {
		return name;
	}

	public MessageAnchor getPreviousAnchor() {
		return previousAnchor;
	}

	public void setPreviousAnchor(MessageAnchor previousAnchor) {
		this.previousAnchor = previousAnchor;
	}
	
	/**
	 * Returns the point which is used as the reference by this
	 * AbstractConnectionAnchor. It is generally dependent on the Figure which
	 * is the owner of this AbstractConnectionAnchor.
	 * 
	 * @since 2.0
	 * @return The reference point of this anchor
	 * @see org.eclipse.draw2d.ConnectionAnchor#getReferencePoint()
	 */
	public Point getReferencePoint() {
		if (getOwner() == null) {
			return null;
		}
		else {
			if(referencePoint ==null) {
				if(getPreviousAnchor()!=null) {
					// get x absolute position of the owner
					Point ownerLocation = getOwner().getBounds().getCenter();
					getOwner().translateToAbsolute(ownerLocation);
					referencePoint = new Point(ownerLocation.x, getPreviousAnchor().getLocation(null).y);
					// System.err.println("ref "+getName()+": "+referencePoint);
				}
				else {
					referencePoint = getOwner().getBounds().getTop();
					getOwner().translateToAbsolute(referencePoint);
					// System.err.println("ref (no anchor)"+getName()+": "+referencePoint);
				}
			}
			return referencePoint;
		}
	}

	public void setDelta(int delta_y) {
		this.delta_y = delta_y;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
