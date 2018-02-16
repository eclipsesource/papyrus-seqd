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

package org.eclipse.papyrus.uml.interaction.internal.graph;

import static java.util.Collections.singleton;
import static org.eclipse.emf.ecore.util.EcoreUtil.getAllContents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Default computer of the dependency graph for an {@link Interaction}.
 *
 * @author Christian W. Damus
 */
public class GraphComputer {
	private final GraphImpl graph;

	/** Keep track of edges to avoid creating duplicates. */
	private final Map<EdgeImpl, EdgeImpl> canonicalEdges = new HashMap<>();

	/**
	 * Initializes me.
	 *
	 * @param graph
	 *            the graph to build
	 */
	public GraphComputer(GraphImpl graph) {
		super();

		this.graph = graph;
	}

	public void compute() {
		VertexImpl initial = graph.initial();
		Interaction interaction = (Interaction)initial.getInteractionElement();

		EdgeFactory edgeFactory = new EdgeFactory();
		for (Iterator<EObject> iter = getAllContents(singleton(interaction)); iter.hasNext();) {
			edgeFactory.doSwitch(iter.next());
		}
	}

	public EdgeImpl edge(VertexImpl from, Element to) {
		EdgeImpl result = new EdgeImpl(from, graph.vertex(to));

		EdgeImpl existing = canonicalEdges.putIfAbsent(result, result);
		if (existing != null) {
			// This edge already exists
			result.destroy();
			result = existing;
		}

		return result;
	}

	public List<EdgeImpl> edge(VertexImpl from, Collection<? extends Element> toAll) {
		return toAll.isEmpty() ? Collections.emptyList()
				: toAll.stream().distinct().map(to -> edge(from, to)).collect(Collectors.toList());
	}

	public EdgeImpl edge(Element from, Element to) {
		return edge(graph.vertex(from), to);
	}

	public List<EdgeImpl> edge(Element from, Collection<? extends Element> toAll) {
		return edge(graph.vertex(from), toAll);
	}

	//
	// Nested types
	//

	private class EdgeFactory extends UMLSwitch<Void> {
		private FragmentIndex fragmentIndex = new FragmentIndex();

		/**
		 * The lifelines are immediate dependents of the interaction just to ensure a single root.
		 */
		@Override
		public Void caseInteraction(Interaction interaction) {
			edge(interaction, interaction.getLifelines());
			return null;
		}

		@Override
		public Void caseMessage(Message message) {
			edge(message, message.getReceiveEvent());

			if (message.getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL) {
				// Create message precedes the created lifeline
				if (message.getReceiveEvent() instanceof InteractionFragment) {
					InteractionFragment receive = (InteractionFragment)message.getReceiveEvent();
					edge(message, receive.getCovereds());
				}
			}

			return null;
		}

		@Override
		public Void caseMessageEnd(MessageEnd end) {
			Message message = end.getMessage();
			if ((message != null) && (message.getSendEvent() == end)) {
				edge(end, message);
			}
			return null;
		}

		@Override
		public Void caseExecutionSpecification(ExecutionSpecification exec) {
			if (exec.getStart() != null) {
				edge(exec.getStart(), exec);
			}
			if (exec.getFinish() != null) {
				edge(exec, exec.getFinish());
			}
			return null;
		}

		@Override
		public Void caseLifeline(Lifeline lifeline) {
			// Occurrence specifications are strictly ordered as in the model. Also, don't
			// redundantly link the start and finish of an execution specification because
			// the execution will do that
			List<? extends InteractionFragment> fragments = lifeline.getCoveredBys();
			if (!fragments.isEmpty()) {
				// Copy the list, filter out execution specifications (handled separately),
				// and sort the list
				fragments = new ArrayList<>(fragments);
				fragments.removeIf(ExecutionSpecification.class::isInstance);
				fragments.sort(fragmentIndex);

				Element from = lifeline;
				for (InteractionFragment next : fragments) {
					if (!isStartOfExecutionSpecification(from)) {
						edge(from, next);
					} // else break the chain here and pick it up again from the finish of the exec
					from = next;
				}
			}

			return null;
		}

		private boolean isStartOfExecutionSpecification(Element element) {
			return (element instanceof OccurrenceSpecification) && CacheAdapter.getCacheAdapter(element)
					.getNonNavigableInverseReferences(element).stream().anyMatch(setting -> setting
							.getEStructuralFeature() == UMLPackage.Literals.EXECUTION_SPECIFICATION__START);
		}
	}

	private static class FragmentIndex implements Comparator<InteractionFragment> {
		private final Map<InteractionFragment, Integer> fragmentIndex = new HashMap<>();

		private final Map<InteractionOperand, Map<InteractionFragment, Integer>> operandIndices = new HashMap<>();

		FragmentIndex() {
			super();
		}

		@SuppressWarnings("boxing")
		int index(InteractionFragment fragment) {
			Interaction interaction = fragment.getEnclosingInteraction();
			if (interaction != null) {
				return fragmentIndex.computeIfAbsent(fragment,
						frag -> interaction.getFragments().indexOf(frag));
			}
			InteractionOperand operand = fragment.getEnclosingOperand();
			if (operand != null) {
				Map<InteractionFragment, Integer> operandIndex = operandIndices.computeIfAbsent(operand,
						__ -> new HashMap<>());
				return operandIndex.computeIfAbsent(fragment, frag -> operand.getFragments().indexOf(frag));
			}
			return -1;
		}

		@Override
		public int compare(InteractionFragment frag1, InteractionFragment frag2) {
			InteractionFragment container1 = (InteractionFragment)frag1.eContainer();
			InteractionFragment container2 = (InteractionFragment)frag2.eContainer();

			if (container1 == container2) {
				// In the same operand or interaction. Easy
				return index(frag1) - index(frag2);
			}

			// Not in the same operand or interaction

			if (EcoreUtil.isAncestor(container1, container2)) {
				// Ancestor sorts first, although it doesn't really make sense
				return -1;
			}
			if (EcoreUtil.isAncestor(container2, container1)) {
				// Ancestor sorts first, although it doesn't really make sense
				return +1;
			}
			return compare(container1, container2);
		}
	}
}
