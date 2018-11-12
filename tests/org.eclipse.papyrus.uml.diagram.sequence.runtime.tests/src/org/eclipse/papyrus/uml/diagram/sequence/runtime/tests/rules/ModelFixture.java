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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.ClassRule;

/**
 * A test fixture that loads an UML model containing an {@link Interaction}.
 *
 * @author Christian W. Damus
 */
public class ModelFixture extends org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture {

	private static final String SEQUENCE_DIAGRAM_TYPE = "PapyrusUMLSequenceDiagram"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 *
	 * @param testClass
	 *            the test class in which context to load the resource. May be {@code null} for an ordinary
	 *            {@code Rule} but required for a {@link ClassRule}
	 * @param path
	 *            the path relative to the {@code testClass} of the UML resource to load
	 */
	public ModelFixture(Class<?> testClass, String path) {
		super(testClass, path);
	}

	/**
	 * Initializes me.
	 *
	 * @param testClass
	 *            the test class in which context to load the resource. May be {@code null} for an ordinary
	 *            {@code Rule} but required for a {@link ClassRule}
	 */
	public ModelFixture(Class<?> testClass) {
		this(testClass, null);
	}

	/**
	 * Initializes me.
	 *
	 * @param path
	 *            the path relative to the {@code testClass} of the UML resource to load
	 */
	public ModelFixture(String path) {
		this(null, path);
	}

	/**
	 * Initializes me.
	 */
	public ModelFixture() {
		this(null, null);
	}

	@Override
	protected boolean isSequenceDiagram(Diagram diagram) {
		return SEQUENCE_DIAGRAM_TYPE.equals(diagram.getType());
	}

	/**
	 * Get the first available message between two lifelines in my {@link #getInteraction() interaction}.
	 *
	 * @param from
	 *            the sending lifeline name
	 * @param to
	 *            the receiving lifeline name
	 * @return the message
	 * @throws NoSuchElementException
	 *             if no such message exists in the interaction
	 * @see #getInteraction()
	 */
	public Message getMessage(String from, String to) {
		return getInteraction().getMessages().stream() //
				.filter(lifelines(from, to)).findAny().get();
	}

	/**
	 * Get a specific message between two lifelines in my {@link #getInteraction() interaction}. This is a
	 * useful alternative to the simple {@code #getMessage(String, String)} when there are multiple messages
	 * in the same direction between two lifelines.
	 *
	 * @param from
	 *            the sending lifeline name
	 * @param to
	 *            the receiving lifeline name
	 * @param index
	 *            which of the messages between the two lifelines, in sequence order, to retrieve
	 * @return the message
	 * @throws NoSuchElementException
	 *             if no such message exists in the interaction
	 * @see #getInteraction()
	 * @see #getMessage(String, String)
	 */
	public Message getMessage(String from, String to, int index) {
		return getInteraction().getMessages().stream() //
				.sequential().filter(lifelines(from, to)) //
				.skip(index).findFirst().get();
	}

	/**
	 * Obtains a predicate matching elements having a particular {@code name}.
	 *
	 * @param name
	 *            the name of the element to match
	 * @return the 'named' predicate
	 */
	public static Predicate<NamedElement> named(String name) {
		return element -> Objects.equals(name, element.getName());
	}

	private static Predicate<Message> lifelines(String from, String to) {
		return message -> {
			if ((message.getSendEvent() == null) || (message.getReceiveEvent() == null)) {
				return false;
			}

			return MessageUtil.getCovered(message.getSendEvent()).filter(named(from)).isPresent()
					&& MessageUtil.getCovered(message.getReceiveEvent()).filter(named(to)).isPresent();
		};
	}

}
