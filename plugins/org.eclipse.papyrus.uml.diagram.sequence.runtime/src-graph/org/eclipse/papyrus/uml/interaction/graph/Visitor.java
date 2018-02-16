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

package org.eclipse.papyrus.uml.interaction.graph;

import static java.util.Collections.nCopies;

import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Protocol for vistation of objects that {@link Visitable can be visited} in some kind of order.
 *
 * @see Visitable
 * @author Christian W. Damus
 */
@FunctionalInterface
public interface Visitor<T extends Visitable<T>> {

	/**
	 * Visit an {@code element}.
	 *
	 * @param element
	 *            a visitable element to visit
	 */
	void visit(T element);

	/**
	 * Visit the entire {@linkplain Visitable#successors() successor subgraph} of an {@code element}.
	 *
	 * @param element
	 *            an element whose successor subgraph to walk
	 * @see Visitable#successors()
	 * @see #visit(Visitable)
	 */
	default void walkSuccessors(T element) {
		element.successors().forEach(e -> e.accept(this));
	}

	/**
	 * Visit the entire {@linkplain Visitable#predecessors() predecessor subgraph} of an {@code element}.
	 *
	 * @param element
	 *            an element whose predecessor subgraph to walk
	 * @see Visitable#predecessors()
	 * @see #visit(Visitable)
	 */
	default void walkPredecessors(T element) {
		element.predecessors().forEach(e -> e.accept(this));
	}

	/**
	 * Obtains a visitor that prints its visited elements to the given {@code output}.
	 *
	 * @param output
	 *            where to print the visited elements
	 * @return a printing visitor
	 */
	static <T extends Visitable<T>> Visitor<T> printer(PrintStream output) {
		return new Visitor<T>() {
			// Track the depths of visited objects
			private final Map<Object, Integer> depths = new ConcurrentHashMap<>();

			@SuppressWarnings("boxing")
			@Override
			public void visit(T element) {
				int depth = 0;

				if (element instanceof Edge) {
					// We can get depths for this
					Edge edge = (Edge)element;

					depth = depths.getOrDefault(edge.from(), 0);
					depths.put(edge.to(), depth + 1);
				}

				if (depth > 0) {
					output.print(String.join("", nCopies(depth, "  "))); //$NON-NLS-1$//$NON-NLS-2$
				}

				output.println(element);
			}
		};
	}
}
