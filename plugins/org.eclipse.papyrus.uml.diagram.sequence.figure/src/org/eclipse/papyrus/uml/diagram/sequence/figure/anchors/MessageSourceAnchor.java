package org.eclipse.papyrus.uml.diagram.sequence.figure.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;

/**
 * A fixed position anchor targeting the source end of a Message
 */
public class MessageSourceAnchor extends AbstractConnectionAnchor {

	private PolylineConnection connection;

	public MessageSourceAnchor(PolylineConnection figure) {
		super(figure);
		this.connection = figure;
	}

	@Override
	public Point getLocation(Point reference) {
		return connection.getStart().getCopy();
	}

}
