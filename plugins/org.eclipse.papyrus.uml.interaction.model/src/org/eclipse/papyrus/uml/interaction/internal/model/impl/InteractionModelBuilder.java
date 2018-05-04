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

package org.eclipse.papyrus.uml.interaction.internal.model.impl;

import java.util.function.Consumer;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Builder of the logical interaction model.
 *
 * @author Christian W. Damus
 */
public class InteractionModelBuilder {

	private final Graph graph;

	/**
	 * Initializes me.
	 */
	InteractionModelBuilder(Interaction interaction, Diagram sequenceDiagram) {
		super();

		this.graph = Graph.compute(interaction, sequenceDiagram);
	}

	/**
	 * Obtain a builder that synthesizes the semantics and visualization of an interaction.
	 * 
	 * @param interaction
	 *            the UML interaction
	 * @param sequenceDiagram
	 *            the sequence diagram, or {@code null} if none
	 * @return the logical model builder
	 */
	public static InteractionModelBuilder getInstance(Interaction interaction, Diagram sequenceDiagram) {
		return new InteractionModelBuilder(interaction, sequenceDiagram);
	}

	/**
	 * Obtain a builder based on the semantics of an interaction alone.
	 * 
	 * @param interaction
	 *            the UML interaction
	 * @return the logical model builder
	 */
	public static InteractionModelBuilder getInstance(Interaction interaction) {
		return getInstance(interaction, null);
	}

	/**
	 * Build the logical model.
	 * 
	 * @return a new logical model
	 */
	public MInteraction build() {
		// The initial vertex is the interaction
		Interaction interaction = (Interaction)graph.initial().getInteractionElement();
		Diagram diagram = (Diagram)graph.initial().getDiagramView();

		MInteractionImpl result = new MInteractionImpl(interaction).withGraph(graph);
		result.dispose();

		build(result);
		return result;
	}

	void build(MInteractionImpl interaction) {
		Interaction uml = interaction.getElement();

		uml.getLifelines().forEach(lifelineBuilder(interaction));
		uml.getMessages().forEach(messageBuilder(interaction));

		// TODO: Gates
		// TODO: Combined fragments
		// TODO: General orderings
		// TODO: Interaction uses
		// TODO: State invariants
		// TODO: Continuations
		// TODO: Duration constraints
		// TODO: Timing constraints

	}

	Consumer<Lifeline> lifelineBuilder(MInteractionImpl interaction) {
		return lifeline -> {
			MLifelineImpl result = interaction.addLifeline(lifeline);
			lifeline.getCoveredBys().forEach(coveringBuilder(result));
		};
	}

	Consumer<Message> messageBuilder(MInteractionImpl interaction) {
		return message -> {
			MMessageImpl result = interaction.addMessage(message);
			result.setSend(message.getSendEvent());
			result.setReceive(message.getReceiveEvent());
		};
	}

	Consumer<InteractionFragment> coveringBuilder(MLifelineImpl lifeline) {
		return new UMLSwitch<MElementImpl<?>>() {
			@Override
			public MElementImpl<?> caseExecutionSpecification(ExecutionSpecification execution) {
				return lifeline.addExecution(execution);
			}

			@Override
			public MElementImpl<?> caseExecutionOccurrenceSpecification(
					ExecutionOccurrenceSpecification occurrence) {

				if (occurrence instanceof MessageEnd && ((MessageEnd)occurrence).getMessage() != null) {
					// Message ends are handled separately
					return null;
				}

				return lifeline.addExecutionOccurrence(occurrence);
			}

			// TODO: Destruction occurrences
			// TODO: Occurrences withing the scope of executions
			// TODO: Nested executions

		}::doSwitch;
	}
}
