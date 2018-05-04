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

package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.uml2.uml.Element;

/**
 * Partial implementation of a mutation operation on the logical model.
 *
 * @author Christian W. Damus
 * @param <T>
 *            the target of the operation
 */
public abstract class ModelCommand<T extends MElementImpl<?>> extends CommandWrapper {

	private final T target;

	private final Graph graph;

	/**
	 * Initializes me.
	 * 
	 * @param target
	 *            the logical model element on which I operate
	 */
	public ModelCommand(T target) {
		super();

		MInteractionImpl interaction = target.getInteractionImpl();

		this.target = target;
		this.graph = interaction.getGraph();
	}

	protected final void checkInteraction(MElement<?> element) {
		if (element.getInteraction() != target.getInteraction()) {
			throw new IllegalArgumentException("element is not in the lifeline's interaction"); //$NON-NLS-1$
		}
	}

	protected final T getTarget() {
		return target;
	}

	protected final Graph getGraph() {
		return graph;
	}

	protected final Vertex vertex(Element element) {
		return graph.vertex(element);
	}

	protected final Vertex vertex(MElement<? extends Element> element) {
		return vertex(element.getElement());
	}

	protected final Vertex vertex() {
		return vertex(target);
	}

	protected final EditingDomain getEditingDomain() {
		return target.getEditingDomain();
	}

	protected final SemanticHelper semanticHelper() {
		return LogicalModelPlugin.getInstance().getSemanticHelper(getEditingDomain());
	}

	protected final DiagramHelper diagramHelper() {
		return LogicalModelPlugin.getInstance().getDiagramHelper(getEditingDomain());
	}

	protected final LayoutHelper layoutHelper() {
		return LogicalModelPlugin.getInstance().getLayoutHelper(getEditingDomain());
	}

	protected MElement<?> normalizeFragmentInsertionPoint(MElement<?> insertionPoint) {
		MElement<?> result = insertionPoint;

		if (insertionPoint instanceof MMessage) {
			// After the message is received, if it is received
			MMessage message = (MMessage)insertionPoint;
			Optional<MMessageEnd> end = message.getReceive();
			if (!end.isPresent()) {
				end = message.getSend();
			}
			if (end.isPresent()) {
				result = end.get();
			}
		} else if (insertionPoint instanceof MMessageEnd) {
			MMessageEnd end = (MMessageEnd)insertionPoint;
			if (end.isSend()) {
				// After the message is received, if it is received
				result = end.getOtherEnd().orElse(end);
			}
		}

		return result;
	}

	@Override
	public Command chain(Command next) {
		return CompoundModelCommand.compose(getEditingDomain(), this, next);
	}
}
