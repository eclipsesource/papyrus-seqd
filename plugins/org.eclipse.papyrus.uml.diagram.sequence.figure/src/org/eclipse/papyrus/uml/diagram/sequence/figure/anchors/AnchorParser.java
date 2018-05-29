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
package org.eclipse.papyrus.uml.diagram.sequence.figure.anchors;

import java.util.Objects;

import org.eclipse.draw2d.PositionConstants;

/**
 * <p>
 * This class is used to convert Anchors from/to String.
 * </p>
 */
public class AnchorParser {

	public static final String LEFT = "left;"; //$NON-NLS-1$

	public static final String RIGHT = "right;"; //$NON-NLS-1$

	public static final String NORTH = "north;"; //$NON-NLS-1$

	public static final String SOUTH = "south;"; //$NON-NLS-1$

	public static final String EAST = "east;"; //$NON-NLS-1$

	public static final String WEST = "west;"; //$NON-NLS-1$

	public static final String START_PREFIX = "start"; //$NON-NLS-1$

	public static final String END_PREFIX = "end"; //$NON-NLS-1$

	/**
	 * Prefix for empty or null anchor terminal
	 */
	public static final String NO_PREFIX = ""; //$NON-NLS-1$

	/**
	 * Unknown prefix, or an anchor terminal that isn't prefixed (e.g. an integer)
	 */
	public static final String UNKNOWN_PREFIX = null;

	public static final String PREFIX_SEPARATOR = ";"; //$NON-NLS-1$

	private static AnchorParser instance = new AnchorParser();

	public static AnchorParser getInstance() {
		return instance;
	}

	public static enum AnchorKind {
		/** An Anchor located on one side (Left or Right) */
		SIDE,
		/** An Anchor located on the border (North/South/East/West) */
		BORDER,
		/** An Anchor located at a fixed position on a line */
		DISTANCE,
		/** A fixed location Anchor (Which doesn't require any parameter) */
		FIXED,
		/** A fixed Anchor, representing the beginning of a Connection or Node */
		START,
		/** A fixed Anchor, representing the end of a Connection or Node */
		END,
		/** An Anchor that isn't handled by this parser */
		UNKNOWN,
	}

	public AnchorKind getAnchorKind(String anchorTerminal) {
		String prefix = getPrefix(anchorTerminal);
		if (Objects.equals(prefix, NO_PREFIX)) {
			return AnchorKind.FIXED;
		} else if (Objects.equals(prefix, UNKNOWN_PREFIX)) {
			try {
				Integer.parseInt(anchorTerminal);
				return AnchorKind.DISTANCE;
			} catch (NumberFormatException ex) {
				return AnchorKind.UNKNOWN;
			}
		}
		switch (prefix) {
			case LEFT:
			case RIGHT:
				return AnchorKind.SIDE;
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
				return AnchorKind.BORDER;
			case START_PREFIX:
				return AnchorKind.START;
			case END_PREFIX:
				return AnchorKind.END;
		}

		return AnchorKind.UNKNOWN;
	}

	/**
	 * Returns the side for a {@link AnchorKind#SIDE Side Anchor}
	 * 
	 * @param anchorTerminal
	 * @return {@link PositionConstants#LEFT} or {@link PositionConstants#RIGHT}
	 */
	public int getSide(String anchorTerminal) {
		if (anchorTerminal.startsWith(LEFT)) {
			return PositionConstants.LEFT;
		} else if (anchorTerminal.startsWith(RIGHT)) {
			return PositionConstants.RIGHT;
		}
		throw new IllegalArgumentException(
				"The anchor terminal is not a valid Side Anchor: " + anchorTerminal); //$NON-NLS-1$
	}

	public int getBorder(String anchorTerminal) {
		if (anchorTerminal.startsWith(NORTH)) {
			return PositionConstants.NORTH;
		} else if (anchorTerminal.startsWith(SOUTH)) {
			return PositionConstants.SOUTH;
		} else if (anchorTerminal.startsWith(EAST)) {
			return PositionConstants.EAST;
		} else if (anchorTerminal.startsWith(WEST)) {
			return PositionConstants.WEST;
		}
		throw new IllegalArgumentException(
				"The anchor terminal is not a valid Side Anchor: " + anchorTerminal); //$NON-NLS-1$
	}

	private String getPrefix(String anchorTerminal) {
		if (anchorTerminal == null || anchorTerminal.isEmpty()) {
			return NO_PREFIX;
		}

		if (START_PREFIX.equals(anchorTerminal) || END_PREFIX.equals(anchorTerminal)) {
			return anchorTerminal;
		}

		int prefixSeparatorIndex = anchorTerminal.indexOf(PREFIX_SEPARATOR);
		if (prefixSeparatorIndex < 0) {
			return UNKNOWN_PREFIX;
		}

		String prefix = anchorTerminal.substring(0, prefixSeparatorIndex + 1);
		return prefix;
	}

	/**
	 * Returns the distance of this anchor from its reference point (Side or Border)
	 * 
	 * @param anchorTerminal
	 * @return the distance of this anchor from its reference point
	 * @see #getSide(String)
	 * @see #getBorder(String)
	 * @see AnchorKind#SIDE
	 * @see AnchorKind#BORDER
	 */
	public int getDistanceFromReference(String anchorTerminal) {
		String prefix = getPrefix(anchorTerminal);
		if (Objects.equals(prefix, UNKNOWN_PREFIX) || Objects.equals(prefix, NO_PREFIX)) {
			throw new IllegalArgumentException(
					"The specified anchor doesn't have a distance: " + anchorTerminal); //$NON-NLS-1$
		}
		switch (prefix) {
			case LEFT:
			case RIGHT:
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
				try {
					return Integer.parseInt(anchorTerminal.substring(prefix.length()));
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(
							"The specified anchor isn't recognized by this parser: " + anchorTerminal, ex); //$NON-NLS-1$
				}
			default:
				throw new IllegalArgumentException(
						"The specified anchor isn't recognized by this parser: " + anchorTerminal); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the distance for a {@link AnchorKind#DISTANCE} Anchor
	 * 
	 * @param terminal
	 * @return
	 */
	public int getDistance(String terminal) {
		try {
			return Integer.parseInt(terminal);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("The specified anchor is not a Distance anchor: " + terminal, //$NON-NLS-1$
					ex);
		}
	}

}
