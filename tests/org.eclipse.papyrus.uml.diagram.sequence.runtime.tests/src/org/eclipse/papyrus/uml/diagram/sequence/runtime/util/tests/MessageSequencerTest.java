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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageSequenceNumber;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.graph.Visitor;
import org.eclipse.papyrus.uml.interaction.tests.rules.GraphAssertion;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link MessageSequenceNumber} API. These are in the graph tests
 * package because the message sequence calculation is a prime example of an
 * application of the semantic dependency graph.
 *
 * @author Christian W. Damus
 */
@ModelResource("codereview.uml")
public class MessageSequencerTest {

	@Rule
	public final ModelFixture model = new ModelFixture();

	/**
	 * Initializes me.
	 */
	public MessageSequencerTest() {
		super();
	}

	@ModelResource("basic.uml")
	@Test
	public void simpleGraph() {
		new MessageSequenceCollector().collect(model.graph()).print("Simple Messages").verify(msg -> {
			// This is a simple linear sequence; no nesting
			switch (msg.getName()) {
			case "make":
				return "1";
			case "doIt":
				return "2";
			case "reply":
				return "3";
			default:
				return null;
			}
		});
	}

	@Test
	public void complexMessages() {
		new MessageSequenceCollector().collect(model.graph()).print("Complex Messages").verify(msg -> {
			// This is sequence diagram has nested execution
			switch (msg.getName()) {
			case "validate":
				return "1";
			case "post":
				return "1.1";
			case "create-proxy":
				return "1.1.1";
			case "async":
				return "1.1.2";
			case "post-reply":
				return "1.2";
			case "ajax":
				return "1.1.2.1";
			case "escape":
				return "1.1.2.1.1";
			case "errors":
				return "1.1.2.2";
			case "validate-reply":
				return "2";
			case "callback":
				// This is an interesting case because it doesn't reply back
				// into the execution specification that sent it, but to some
				// time after that execution has finished
				return "1.1.3";
			case "handle-errors":
				return "1.1.3.1";
			default:
				return null;
			}
		});
	}

	//
	// Test framework
	//

	Visitor<Vertex> messagePrinter() {
		return vertex -> {
			if (vertex.getInteractionElement() instanceof Message) {
				Message message = (Message) vertex.getInteractionElement();
				MessageSequenceNumber seqno = MessageSequenceNumber.get(message);
				System.out.printf("%s : %s%n", seqno, vertex);
			}
		};
	}

	//
	// Nested types
	//

	static class MessageSequenceCollector implements Visitor<Vertex> {
		private final SortedMap<MessageSequenceNumber, Vertex> messages = new TreeMap<>();

		@Override
		public void visit(Vertex vertex) {
			if (vertex.getInteractionElement() instanceof Message) {
				Message message = (Message) vertex.getInteractionElement();
				MessageSequenceNumber seqno = MessageSequenceNumber.get(message);
				messages.put(seqno, vertex);
			}
		}

		MessageSequenceCollector collect(Graph graph) {
			walkSuccessors(graph.initial());
			return this;
		}

		MessageSequenceCollector verify(Function<? super Message, String> expectedSeqFunction) {
			messages.forEach((seqno, vertex) -> {
				Message message = (Message) vertex.getInteractionElement();
				if (message.getName() != null) {
					String expected = expectedSeqFunction.apply(message);
					if (expected != null) {
						assertThat("Wrong sequence number", seqno.toString(), is(expected));
					}
				}
			});
			return this;
		}

		MessageSequenceCollector print(String title) {
			if (GraphAssertion.DEBUG_PRINT) {
				System.out.println(title);
				System.out.println("======================================");
				messages.forEach((seqno, vertex) -> System.out.printf("%s : %s%n", seqno, vertex));
				System.out.println("======================================");
			}

			return this;
		}
	}
}
