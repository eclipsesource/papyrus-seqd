/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.stream.Stream;

import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Encapsulation of a message sequence number.
 *
 * @author Christian W. Damus
 */
public final class MessageSequenceNumber implements Comparable<MessageSequenceNumber> {

	/**
	 * An empty message sequence number, useful for initializing new sequence number calculations.
	 */
	public static final MessageSequenceNumber EMPTY = new MessageSequenceNumber();

	private final Segment[] sequence;

	private String cachedToString;

	/**
	 * Initialize me with my segments.
	 * 
	 * @param segments
	 *            my sequence segments
	 * @throws NullPointerException
	 *             if the {@code segments} array is {@code null}
	 * @throws IllegalArgumentException
	 *             if the {@code segments} array is empty
	 */
	private MessageSequenceNumber(Segment[] segments) {
		super();

		if (requireNonNull(segments).length == 0) {
			throw new IllegalArgumentException("empty sequence number"); //$NON-NLS-1$
		}

		this.sequence = segments;
	}

	/**
	 * Initialize me as the singleton empty sequence.
	 */
	private MessageSequenceNumber() {
		super();

		this.sequence = new Segment[0];
	}

	/**
	 * Obtain the sequence number for the given {@code message}. This is calculated on the fly if necessary
	 * and cached until the model is changed in any way.
	 * 
	 * @param message
	 *            a message
	 * @return its sequence number; never {@code null}
	 */
	public static MessageSequenceNumber get(Message message) {
		MessageSequenceNumber result = (MessageSequenceNumber)CacheAdapter.getCacheAdapter(message)
				.get(message, MessageSequenceNumber.class);

		if (result == null) {
			Interaction interaction = message.getInteraction();
			if (interaction != null) { // The message could be a proxy, for example
				computeMessageSequenceNumbers(interaction);
			}
			result = (MessageSequenceNumber)CacheAdapter.getCacheAdapter(message).get(message,
					MessageSequenceNumber.class);
		}

		return result;
	}

	/**
	 * Obtain a new sequence number with a possibly {@code parallel} segment appended.
	 * 
	 * @param parallel
	 *            {@code true} to start a parallel sub-sequence; {@code false} for sequential
	 * @return the new sequence number
	 */
	public MessageSequenceNumber append(boolean parallel) {
		Segment[] result = new Segment[sequence.length + 1];
		System.arraycopy(sequence, 0, result, 0, sequence.length);
		result[sequence.length] = new Segment(parallel);
		return new MessageSequenceNumber(result);
	}

	/**
	 * Obtain a new sequence number by incrementing my last segment. The empty sequence does not have a next;
	 * it can only be {@linkplain #append(boolean) appended}.
	 * 
	 * @param parallel
	 *            {@code true} to continue or start a parallel sub-sequence; {@code false} for sequential
	 * @return the new sequence number
	 * @throws IllegalStateException
	 *             if I am the {@linkplain #EMPTY empty sequence}
	 */
	public MessageSequenceNumber next(boolean parallel) {
		if (isEmpty()) {
			throw new IllegalStateException("empty sequence"); //$NON-NLS-1$
		}

		Segment[] result = new Segment[sequence.length];
		System.arraycopy(sequence, 0, result, 0, sequence.length);
		result[sequence.length - 1] = result[sequence.length - 1].next(parallel);
		return new MessageSequenceNumber(result);
	}

	/**
	 * Obtain a new sequence number by trimming the last {@code segments}.
	 * 
	 * @param segments
	 *            the number of segments to trim from the end of me
	 * @return the new sequence number
	 * @throws IllegalArgumentException
	 *             if the number of {@code segments} is negative
	 */
	public MessageSequenceNumber trimSegments(int segments) {
		if (segments < 0) {
			throw new IllegalArgumentException("negative segments"); //$NON-NLS-1$
		}

		int retain = Math.max(0, this.sequence.length - segments);
		if (retain == 0) {
			return EMPTY;
		}

		Segment[] result = new Segment[retain];
		System.arraycopy(this.sequence, 0, result, 0, retain);
		return new MessageSequenceNumber(result);
	}

	/**
	 * Queries my last segment.
	 * 
	 * @return my last segment, or {@code null} if I am the {@linkplain #EMPTY empty sequence}
	 * @see #isEmpty()
	 */
	public String lastSegment() {
		return sequence.length == 0 ? null : sequence[sequence.length - 1].toString();
	}

	/**
	 * Queries whether I am the {@linkplain #EMPTY empty sequence}.
	 * 
	 * @return whether I am empty
	 */
	public boolean isEmpty() {
		return this == EMPTY;
	}

	private static void computeMessageSequenceNumbers(Interaction interaction) {
		Graph graph = Graph.compute(interaction, null);
		graph.walkVertices(UMLPackage.Literals.MESSAGE, new MessageSequencer());
	}

	static void sequence(Message message, MessageSequenceNumber sequence) {
		CacheAdapter.getCacheAdapter(message).put(message, MessageSequenceNumber.class, sequence);
	}

	@Override
	public int compareTo(MessageSequenceNumber o) {
		int result = 0;

		Segment[] other = o.sequence;
		int limit = Math.min(sequence.length, other.length);
		for (int i = 0; i < limit && result == 0; i++) {
			result = sequence[i].compareTo(other[i]);
		}

		if (result == 0) {
			// Anything left on one side or the other?
			if (other.length > limit) {
				// I am shorter, therefore, lesser
				result = -1;
			} else if (sequence.length > limit) {
				result = +1;
			}
		}

		return result;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(sequence);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof MessageSequenceNumber
				&& Arrays.equals(sequence, ((MessageSequenceNumber)obj).sequence);
	}

	@Override
	public String toString() {
		if (cachedToString == null) {
			cachedToString = sequence.length == 0 ? "<empty>" //$NON-NLS-1$
					: Stream.of(sequence).map(Object::toString).collect(joining(".")); //$NON-NLS-1$
		}

		return cachedToString;
	}

	//
	// Nested types
	//

	/**
	 * A segment of a message sequence number, having an ordinal integer at least and optionally a message
	 * name for parallel occurrences.
	 *
	 * @author Christian W. Damus
	 */
	private static final class Segment implements Comparable<Segment> {
		private final int ordinal;

		private final String name;

		Segment(boolean parallel) {
			this(1, parallel ? "a" : null); //$NON-NLS-1$
		}

		private Segment(int ordinal, String name) {
			super();

			if (ordinal <= 0) {
				throw new IllegalArgumentException("non-positive ordinal"); //$NON-NLS-1$
			}
			if (name != null && name.isEmpty()) {
				throw new IllegalArgumentException("empty name"); //$NON-NLS-1$
			}

			this.ordinal = ordinal;
			this.name = name;
		}

		int ordinal() {
			return ordinal;
		}

		String name() {
			return name;
		}

		boolean isParallel() {
			return name != null;
		}

		Segment next(boolean parallel) {
			if (parallel) {
				if (isParallel()) {
					return new Segment(ordinal, nextName(name));
				} else {
					// Switching from a sequential numbering to parallel, we increment the ordinal
					return new Segment(ordinal + 1, "a"); //$NON-NLS-1$
				}
			} else {
				// If switching from a parallel numbering to sequential, we increment the ordinal,
				// so the result doesn't depend on whether I am a parallel segment
				return new Segment(ordinal + 1, null);
			}
		}

		@Override
		public int compareTo(Segment o) {
			int result = ordinal - o.ordinal();
			if (result == 0) {
				if (isParallel()) {
					if (o.isParallel()) {
						result = name.compareTo(o.name());
					} else {
						// Parallels arbitrarily come after sequentials
						result = +1;
					}
				} else if (o.isParallel()) {
					// Parallels arbitrarily come after sequentials
					result = -1;
				}
			}

			return result;
		}

		@Override
		public String toString() {
			String result = Integer.toString(ordinal);
			if (name != null) {
				result = result + name;
			}
			return result;
		}

		private static String nextName(String name) {
			StringBuilder result = new StringBuilder(name);
			int last = result.length() - 1;

			while (last >= 0 && result.charAt(last) == 'z') {
				result.setCharAt(last, 'a');
				last = last - 1;
			}

			if (last >= 0) {
				result.setCharAt(last, (char)(result.charAt(last) + 1));
			} else {
				// We rolled the whole thing over. It's all 'a's now, so
				// just append instead of inserting at 0, which is
				// marginally less efficient because it shifts all the content
				result.append('a');
			}

			return result.toString();
		}
	}
}
