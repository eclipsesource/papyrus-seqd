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

package org.eclipse.papyrus.uml.diagram.sequence.examples.graph;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;

/**
 * Partial implementation of command handlers for the sequence diagram examples.
 *
 * @author Christian W. Damus
 */
public abstract class AbstractSequenceDiagramHandler extends AbstractHandler {

	private IAdaptable selection;

	private Element selectedElement;

	private View selectedView;

	private Map<String, String> parameters = Collections.emptyMap();

	/**
	 * Initializes me.
	 */
	public AbstractSequenceDiagramHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object selected = HandlerUtil.getCurrentStructuredSelection(event).getFirstElement();
		if (selected instanceof IAdaptable) {
			selection = (IAdaptable)selected;

			@SuppressWarnings("unchecked")
			Map<String, String> parameters_ = event.getParameters();
			parameters = parameters_;

			try {
				EObject element = selection.getAdapter(EObject.class);
				if (element instanceof Element) {
					selectedElement = (Element)element;
					selectedView = selection.getAdapter(View.class);
					execute(selectedElement, selectedView);
				}
			} finally {
				parameters = Collections.emptyMap();
				selection = null;
			}
		}
		return null;
	}

	/**
	 * Execute on the selected {@code element} and its {@code view} in the diagram.
	 * 
	 * @param element
	 *            the element in the diagram selected by the user
	 * @param view
	 *            the view in the diagram of the {@code element} selected by the user
	 * @throws ExecutionException
	 *             on execution failure
	 */
	protected void execute(Element element, View view) throws ExecutionException {
		Optional<MElement<?>> logical = getLogicalModel(element);
		logical.flatMap(this::getCommand).ifPresent(this::execute);
	}

	protected final Optional<MElement<?>> getLogicalModel(Element element) {
		Diagram diagram = Optional.ofNullable(selectedView).map(View::getDiagram).orElse(null);
		MInteraction interaction = MInteraction.getInstance(getInteraction(selectedElement), diagram);

		return interaction.getElement(element);
	}

	protected abstract Optional<Command> getCommand(MElement<?> element);

	/**
	 * Queries the control that is the context of the current selection.
	 * 
	 * @return the contextual control
	 */
	protected Control getControl() {
		Control result = null;

		if (selection instanceof EditPart) {
			result = ((EditPart)selection).getViewer().getControl();
		}

		return result;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		boolean enable = false;

		ISelection sel = (ISelection)HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection)sel;
			if (!sel.isEmpty()) {
				Object selected = ssel.getFirstElement();
				EObject element = Adapters.adapt(selected, EObject.class);
				enable = (element != null && isInInteraction(element));
			}
		}

		setBaseEnabled(enable);
	}

	Interaction getInteraction(EObject eObject) {
		for (EObject next = eObject; next != null; next = next.eContainer()) {
			if (next instanceof Interaction) {
				return (Interaction)next;
			}
		}
		return null;
	}

	boolean isInInteraction(EObject eObject) {
		return getInteraction(eObject) != null;
	}

	void execute(Command command) {
		getCommandStack(selection.getAdapter(EObject.class)).execute(command);
	}

	EditingDomain getEditingDomain(EObject eObject) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
	}

	CommandStack getCommandStack(EObject eObject) {
		return getEditingDomain(eObject).getCommandStack();
	}

	protected final String getParameter(String name) {
		return getParameter(name, null);
	}

	protected final String getParameter(String name, String defaultValue) {
		String result = getRawParameter(name);
		return result == null ? defaultValue : result;
	}

	private String getRawParameter(String nameOrID) {
		String result = parameters.get("org.eclipse.papyrus.uml.diagram.sequence.examples.graph." + nameOrID); //$NON-NLS-1$
		if (result == null) {
			result = parameters.get(nameOrID);
		}
		return result;
	}
}
