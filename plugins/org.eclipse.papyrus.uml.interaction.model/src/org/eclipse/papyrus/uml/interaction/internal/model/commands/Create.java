/*****************************************************************************
 * Copyright (c) 2018 Johannes Faltermeier and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Johannes Faltermeier - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MessageSort;

public final class Create {

	private Create() {
	}

	private static LayoutHelper layoutHelper(EditingDomain editingDomain) {
		return LogicalModelPlugin.getInstance().getLayoutHelper(editingDomain);
	}

	private static Vertex vertex(Graph graph, MElement<? extends Element> element) {
		return graph.vertex(element.getElement());
	}

	/**
	 * Creates the nudgecommands for this given element. If the element is a lifeline or a creation message,
	 * the children will be moved based on the deltaChildren argument.
	 * 
	 * @param graph
	 *            the {@link Graph}
	 * @param editingDomain
	 *            the {@link EditingDomain}
	 * @param delta
	 *            the y-offset
	 * @param deltaChildren
	 *            the y-offset for children
	 * @param element
	 *            the element to move
	 * @return the {@link Command}
	 */
	public static final Command nudgeCommand(//
			Graph graph, //
			EditingDomain editingDomain, //
			int delta, //
			int deltaChildren, //
			MElement<? extends Element> element) {

		/*
		 * move of lifeline will already move elements on it, so we have to pay special attention to not move
		 * twice
		 */
		if (element instanceof MExecution) {
			/* if we are an execution */
			return new NudgeCommand((MElementImpl<? extends Element>)element, delta, false);
		} else if (element instanceof MMessage) {
			/* fix one half message */
			MMessage message = (MMessage)element;
			if (message.getElement().getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL) {
				return creationMethodNudgeCommand(graph, editingDomain, delta, deltaChildren, message);
			}
			return new NudgeCommand((MElementImpl<? extends Element>)element, delta, false);
		} else if (element instanceof MLifeline) {
			List<Command> nudgeCommands = new ArrayList<>();
			nudgeCommands.add(new NudgeCommand((MElementImpl<? extends Element>)element, delta, false));
			nudgeCommands.addAll(lifelineChildren(graph, editingDomain, deltaChildren, (MLifeline)element));
			return CompoundModelCommand.compose(editingDomain, nudgeCommands);
		}
		return new NudgeCommand((MElementImpl<? extends Element>)element, delta, false);
	}

	private static Command creationMethodNudgeCommand(//
			Graph graph, //
			EditingDomain editingDomain, //
			int delta, //
			int deltaChildren, //
			MMessage message) {

		Optional<MLifeline> sender = message.getSender();
		Optional<MLifeline> receiver = message.getReceiver();
		if (!sender.isPresent() || !receiver.isPresent()) {
			return IdentityCommand.INSTANCE;
		}

		List<Command> nudgeCommands = new ArrayList<>();
		nudgeCommands.add(new NudgeCommand((MMessageImpl)message, delta, false));
		nudgeCommands.addAll(lifelineChildren(graph, editingDomain, deltaChildren, receiver.get()));
		return CompoundModelCommand.compose(editingDomain, nudgeCommands);
	}

	private static List<Command> lifelineChildren(Graph graph, EditingDomain editingDomain, int deltaChildren,
			MLifeline lifeline) {
		List<Command> nudgeCommands = new ArrayList<>();
		lifeline.getExecutions().stream()//
				.map(e -> vertex(graph, e))//
				.forEach(v -> {
					nudgeCommands.add(layoutHelper(editingDomain).setTop(v,
							layoutHelper(editingDomain).getTop(v).orElse(0) - deltaChildren));
				});
		for (MMessage m : lifeline.getInteraction().getMessages()) {
			m.getSender().ifPresent(l -> {
				if (l == lifeline) {
					Vertex v = vertex(graph, m.getSend().get());
					nudgeCommands.add(layoutHelper(editingDomain).setTop(v,
							layoutHelper(editingDomain).getTop(v).orElse(0) - deltaChildren));
				}
			});
			m.getReceiver().ifPresent(l -> {
				if (l == lifeline) {
					Vertex v = vertex(graph, m.getReceive().get());
					nudgeCommands.add(layoutHelper(editingDomain).setTop(v,
							layoutHelper(editingDomain).getTop(v).orElse(0) - deltaChildren));
				}
			});
		}
		return nudgeCommands;
	}

}
