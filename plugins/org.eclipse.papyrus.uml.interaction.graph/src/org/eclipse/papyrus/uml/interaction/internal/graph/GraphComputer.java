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

import static java.lang.Math.max;
import static java.util.Collections.singleton;
import static org.eclipse.emf.ecore.util.EcoreUtil.getAllContents;
import static org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil.invertSingle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.uml.interaction.graph.GroupKind;
import org.eclipse.papyrus.uml.interaction.graph.Tag;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionUse;
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

	private final FragmentIndex fragmentIndex = new FragmentIndex();

	/**
	 * Initializes me.
	 *
	 * @param graph
	 *            the graph to build
	 */
	public GraphComputer(GraphImpl graph) {
		super();

		this.graph = graph;
		graph.setInteractionOrdering(fragmentIndex.vertexOrdering());
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
		return edge(from, to, GroupKind.NONE);
	}

	public EdgeImpl edge(VertexImpl from, Element to, GroupKind groupKind) {
		EdgeImpl result = new EdgeImpl(from, graph.vertex(to));

		EdgeImpl existing = canonicalEdges.putIfAbsent(result, result);
		if (existing != null) {
			// This edge already exists
			result.destroy();
			result = existing.setGroupKind(groupKind);
		}

		return result.setGroupKind(groupKind);
	}

	public List<EdgeImpl> edge(VertexImpl from, Collection<? extends Element> toAll) {
		return toAll.isEmpty() ? Collections.emptyList()
				: toAll.stream().distinct().map(to -> edge(from, to)).collect(Collectors.toList());
	}

	public EdgeImpl edge(Element from, Element to) {
		return edge(graph.vertex(from), to);
	}

	public EdgeImpl edge(Element from, Element to, GroupKind groupKind) {
		return edge(graph.vertex(from), to, groupKind);
	}

	public List<EdgeImpl> edge(Element from, Collection<? extends Element> toAll) {
		return edge(graph.vertex(from), toAll);
	}

	@SuppressWarnings("unchecked")
	public <E extends Element> E tag(E element, Tag tag) {
		return (E)graph.vertex(element).tag(tag).getInteractionElement();
	}

	boolean hasDiagram() {
		return graph.initial().getDiagramView() != null;
	}

	/**
	 * Get a left-to-right visual ordering of lifelines, if we have a diagram available. Otherwise, just the
	 * order in which they are defined in the interaction.
	 * 
	 * @return the lifeline ordering
	 */
	Comparator<Lifeline> lifelineOrdering() {
		Comparator<Lifeline> result;

		if (!hasDiagram()) {
			List<Lifeline> lifelines = ((Interaction)graph.initial().getInteractionElement()).getLifelines();

			result = (ll1, ll2) -> ll1 == ll2 ? 0 : lifelines.indexOf(ll1) - lifelines.indexOf(ll2);
		} else {
			// If we have a diagram, we can impose left-to-right ordering dependency on
			// the lifelines to aid in layout computations such as lifeline insertion
			final Map<Lifeline, Integer> positions = new HashMap<>();
			@SuppressWarnings("boxing")
			final Function<Lifeline, Integer> x = ll -> Optional.ofNullable(graph.vertex(ll).getDiagramView())
					.filter(Node.class::isInstance).map(Node.class::cast) //
					.map(Node::getLayoutConstraint) //
					.filter(Location.class::isInstance).map(Location.class::cast) //
					.map(Location::getX).orElse(-1);
			Comparator<Lifeline> byPosition = Comparator.comparing(ll -> positions.computeIfAbsent(ll, x));
			result = (ll1, ll2) -> ll1 == ll2 ? 0 : byPosition.compare(ll1, ll2);
		}

		return result;
	}

	//
	// Nested types
	//

	private class EdgeFactory extends UMLSwitch<Void> {

		/**
		 * The lifelines are immediate dependents of the interaction just to ensure a single root.
		 */
		@Override
		public Void caseInteraction(Interaction interaction) {
			edge(interaction, interaction.getFormalGates());

			List<Lifeline> lifelines = interaction.getLifelines();
			if (hasDiagram()) {
				// If we have a diagram, we can impose left-to-right ordering dependency on
				// the lifelines to aid in layout computations such as lifeline insertion
				lifelines = new ArrayList<>(lifelines);
				lifelines.sort(lifelineOrdering());
			}
			edge(interaction, lifelines);

			return null;
		}

		@Override
		public Void caseMessage(Message message) {
			EdgeImpl edge = edge(message, message.getReceiveEvent());

			if (message.getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL) {
				// Create message precedes the created lifeline
				if (message.getReceiveEvent() instanceof InteractionFragment) {
					edge.tag(Tag.LIFELINE_CREATION);
					InteractionFragment receive = tag((InteractionFragment)message.getReceiveEvent(),
							Tag.LIFELINE_CREATION);
					edge(message, receive.getCovereds());
				}
			} else if (message.getMessageSort() == MessageSort.DELETE_MESSAGE_LITERAL) {
				if (message.getReceiveEvent() instanceof DestructionOccurrenceSpecification) {
					edge.hasTag(Tag.LIFELINE_DESTRUCTION);
					DestructionOccurrenceSpecification receive = tag(
							(DestructionOccurrenceSpecification)message.getReceiveEvent(),
							Tag.LIFELINE_DESTRUCTION);
					edge(message, receive.getCovereds());
				}
			}

			return null;
		}

		@Override
		public Void caseMessageEnd(MessageEnd end) {
			Message message = end.getMessage();
			if ((message != null) && end.isSend()) {
				edge(end, message);
			}
			return null;
		}

		@Override
		public Void caseInteractionFragment(InteractionFragment fragment) {
			fragment.getGeneralOrderings().forEach(ord -> {
				if (ord.getBefore() != null && ord.getAfter() != null) {
					edge(ord.getBefore(), ord.getAfter());
				}
			});
			return null;
		}

		@Override
		public Void caseInteractionUse(InteractionUse interactionUse) {
			interactionUse.getActualGates().forEach(gate -> edge(interactionUse, gate));
			return null;
		}

		@Override
		public Void caseCombinedFragment(CombinedFragment combined) {
			combined.getOperands().forEach(op -> edge(combined, op, GroupKind.COMBINED_FRAGMENT));
			combined.getCfragmentGates().forEach(gate -> edge(combined, gate));
			return null;
		}

		@Override
		public Void caseInteractionOperand(InteractionOperand operand) {
			operand.getFragments().forEach(frag -> edge(operand, frag, GroupKind.OPERAND));
			return null;
		}

		@Override
		public Void caseExecutionSpecification(ExecutionSpecification exec) {
			Optional<InteractionFragment> start = Optional.ofNullable(exec.getStart());
			Optional<InteractionFragment> finish = Optional.ofNullable(exec.getFinish());

			// Incoming to me from my start, which is not a member of my group
			start.ifPresent(s -> edge(tag(s, Tag.EXECUTION_START), exec).tag(Tag.EXECUTION_START));

			if (exec.getFinish() != null) {
				edge(exec, tag(exec.getFinish(), Tag.EXECUTION_FINISH), GroupKind.EXECUTION)
						.tag(Tag.EXECUTION_FINISH);
			}

			// And all occurrences on this lifeline between my start and my end, taking
			// whichever end may be missing as myself for the purposes of determining
			// the boundaries of my span
			Lifeline ll = Optional.ofNullable(lifeline(exec)).orElseGet(
					() -> start.map(this::lifeline).orElseGet(() -> finish.map(this::lifeline).orElse(null)));
			if (ll != null) {
				InteractionFragment lowerBound = start.orElse(exec);
				InteractionFragment upperBound = finish.orElse(exec);

				// Don't go sorting lists if the range will be empty anyways
				if (lowerBound != upperBound) {
					List<InteractionFragment> fragments = covering(ll);
					Predicate<InteractionFragment> self = exec::equals;

					// The start is not a member of the group; the finish we'll get later.
					// And, of course, the exec isn't a member of itself
					fragments.subList(fragments.indexOf(lowerBound) + 1, fragments.indexOf(upperBound))
							.stream().filter(self.negate()) //
							.forEach(i -> edge(exec, i,
									isGroupableInExecution(i) ? GroupKind.EXECUTION : GroupKind.NONE));
				}
			}

			// Outgoing to my finish, if any
			finish.ifPresent(f -> edge(exec, tag(f, Tag.EXECUTION_FINISH), GroupKind.EXECUTION)
					.tag(Tag.EXECUTION_FINISH));

			return null;
		}

		@Override
		public Void caseLifeline(Lifeline lifeline) {
			// Occurrence specifications are strictly ordered as in the model. Also, don't
			// redundantly link the start and finish of an execution specification because
			// the execution will do that
			List<? extends InteractionFragment> fragments = covering(lifeline);

			if (!fragments.isEmpty()) {
				// Don't include the fragments within an execution specification because
				// they are their own groups
				int execDepth = 0;

				VertexImpl previous = null;
				for (InteractionFragment fragment : fragments) {
					// The fragments on this lifeline are strictly ordered in interaction order
					if (previous != null) {
						edge(previous, fragment).to();
					}

					if (fragment instanceof CombinedFragment) {
						// These must only group their operands, so don't make an edge here
						previous = null;
					} else {
						previous = graph.vertex(fragment);
					}

					// As long as we're not in an execution, this is a member of my group
					if (execDepth == 0 && !(fragment instanceof ExecutionSpecification)) {
						if (isGroupableInExecution(fragment)) {
							edge(lifeline, fragment, GroupKind.LIFELINE);
						} else {
							// unless it's a combined fragment or other thing that can
							// span multiple lifelines
							edge(lifeline, fragment);
						}
					}

					// Detect implement start or finish of an execution specification
					if (fragment instanceof ExecutionSpecification) {
						ExecutionSpecification exec = (ExecutionSpecification)fragment;
						if (exec.getStart() == null && exec.getFinish() != null) {
							// Implicit start
							if (execDepth == 0) {
								edge(lifeline, exec, GroupKind.LIFELINE);
							}
							execDepth++;
						}
						if (exec.getFinish() == null && exec.getStart() != null) {
							// Implicit finish. Could be the one we just implicitly started!
							execDepth = max(0, execDepth - 1);
						}
					} else {
						// Detect explicit finish/start
						ExecutionSpecification finished = finishedExecutionSpecification(fragment);
						if (finished != null) {
							execDepth = max(0, execDepth - 1);
						}
						ExecutionSpecification started = startedExecutionSpecification(fragment);
						if (started != null) {
							if (execDepth == 0) {
								edge(lifeline, started, GroupKind.LIFELINE);
							}
							execDepth++;
						}
					}
				}
			}

			return null;
		}

		private ExecutionSpecification startedExecutionSpecification(Element element) {
			return invertSingle(element, UMLPackage.Literals.EXECUTION_SPECIFICATION__START,
					ExecutionSpecification.class).orElse(null);
		}

		private ExecutionSpecification finishedExecutionSpecification(Element element) {
			return invertSingle(element, UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH,
					ExecutionSpecification.class).orElse(null);
		}

		/**
		 * Obtain the singular lifeline covered by a {@code fragment} that is semantically constrained to
		 * cover exactly one lifeline. Such would be the case for, e.g., message ends and execution
		 * specifications.
		 * 
		 * @param fragment
		 *            an interaction fragment
		 * @return its only, or first, covered lifeline, if any
		 */
		Lifeline lifeline(InteractionFragment fragment) {
			List<Lifeline> covered = fragment.getCovereds();
			return covered.isEmpty() ? null : covered.get(0);
		}

		/**
		 * Obtain the interaction fragments covering a lifeline, sorted in interaction order.
		 * 
		 * @param lifeline
		 *            a lifeline
		 * @return its covering fragments, sorted
		 */
		List<InteractionFragment> covering(Lifeline lifeline) {
			// TODO Could cache this?
			List<InteractionFragment> result = new ArrayList<>(lifeline.getCoveredBys());
			result.sort(fragmentIndex);
			return result;
		}

		boolean isGroupableInExecution(InteractionFragment fragment) {
			return fragment instanceof MessageEnd || fragment instanceof OccurrenceSpecification
					|| fragment instanceof ExecutionSpecification;
		}
	}

	private static class FragmentIndex implements Comparator<InteractionFragment> {
		private final Map<InteractionFragment, Integer> interactionIndex = new HashMap<>();

		private final Map<CombinedFragment, Map<InteractionOperand, Integer>> combfragIndices = new HashMap<>();

		private final Map<InteractionOperand, Map<InteractionFragment, Integer>> operandIndices = new HashMap<>();

		FragmentIndex() {
			super();
		}

		@SuppressWarnings("boxing")
		int index(InteractionFragment fragment) {
			// Interaction operands, themselves, are a special case because they are not in
			// the 'fragments' list of anything: they are operands of a combined fragment
			if (fragment instanceof InteractionOperand) {
				InteractionOperand operand = (InteractionOperand)fragment;
				CombinedFragment combfrag = (CombinedFragment)operand.eContainer();
				if (combfrag == null) {
					return -1;
				}
				Map<InteractionOperand, Integer> combfragIndex = combfragIndices.computeIfAbsent(combfrag,
						__ -> new HashMap<>());
				return combfragIndex.computeIfAbsent(operand, op -> combfrag.getOperands().indexOf(op));
			}

			Interaction interaction = fragment.getEnclosingInteraction();
			if (interaction != null) {
				return interactionIndex.computeIfAbsent(fragment,
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
			if (frag1 instanceof Interaction) {
				// It's always first
				return (frag2 == frag1) ? 0 : -1;
			} else if (frag2 instanceof Interaction) {
				// It's always first
				return +1;
			}

			InteractionFragment container1 = (InteractionFragment)frag1.eContainer();
			InteractionFragment container2 = (InteractionFragment)frag2.eContainer();

			if (container1 == container2) {
				// In the same operand or interaction. Easy
				return index(frag1) - index(frag2);
			}

			// Not in the same operand or interaction

			if (EcoreUtil.isAncestor(frag1, frag2)) {
				// Ancestor sorts first, although it doesn't really make sense
				return -1;
			}
			if (EcoreUtil.isAncestor(frag2, frag1)) {
				// Ancestor sorts first, although it doesn't really make sense
				return +1;
			}

			// Look for the least ancestor of each that is in the same container
			out: for (container1 = frag1; !(container1 instanceof Interaction); container1 = (InteractionFragment)container1
					.eContainer()) {
				for (container2 = frag2; !(container2 instanceof Interaction); container2 = (InteractionFragment)container2
						.eContainer()) {
					if (container2.eContainer() == container1.eContainer()) {
						// Found them
						break out;
					}
				}
			}

			if (container1 instanceof Interaction) {
				// It canonically is first
				return -1;
			} else if (container2 instanceof Interaction) {
				return +1;
			}

			return compare(container1, container2);
		}

		Comparator<Vertex> vertexOrdering() {
			return (v1, v2) -> {
				if (v1.equals(v2)) {
					return 0;
				}

				// Dependencies matter most
				if (v1.precedes(v2)) {
					return -1;
				} else if (v1.succeeds(v2)) {
					return +1;
				}

				// Less significant is the interaction ordering
				InteractionFragment f1 = getInteractionFragment(v1);
				InteractionFragment f2 = getInteractionFragment(v2);

				if (f1 != null && f2 != null) {
					return compare(f1, f2);
				}

				// Encounter order: trust the stream ordering
				return 0;
			};
		}

		private InteractionFragment getInteractionFragment(Vertex vertex) {
			InteractionFragment result = null;

			Element element = vertex.getInteractionElement();

			if (element instanceof InteractionFragment) {
				result = (InteractionFragment)element;
			} else if (element instanceof Message) {
				// Order messages by sender, except for found messages and messages from gates
				Message message = (Message)element;
				if (message.getSendEvent() instanceof InteractionFragment
						&& !(message.getSendEvent() instanceof Gate)) {
					result = (InteractionFragment)message.getSendEvent();
				} else if (message.getReceiveEvent() instanceof InteractionFragment) {
					result = (InteractionFragment)message.getReceiveEvent();
				}
			}

			return result;
		}

		private Lifeline getLifeline(Vertex vertex) {
			Element result = vertex.getInteractionElement();
			return (result instanceof Lifeline) ? (Lifeline)result : null;
		}
	}
}
