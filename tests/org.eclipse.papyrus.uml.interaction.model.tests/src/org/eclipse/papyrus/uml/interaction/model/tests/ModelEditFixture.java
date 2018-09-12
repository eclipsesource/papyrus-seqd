/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.model.tests;

import java.util.List;
import java.util.Optional;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture.Edit;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Message;

@SuppressWarnings("restriction")
public class ModelEditFixture extends Edit {

	public DiagramHelper diagramHelper() {
		return LogicalModelPlugin.getInstance().getDiagramHelper(this.getEditingDomain());
	}

	public LayoutHelper layoutHelper() {
		return LogicalModelPlugin.getInstance().getLayoutHelper(this.getEditingDomain());
	}

	public int getLifelineBodyTop(MLifeline lifeline) {
		View shape = lifeline.getDiagramView().orElse(null);
		return layoutHelper().getTop(diagramHelper().getLifelineBodyShape(shape));
	}

	public MInteraction getMInteraction() {
		return MInteraction.getInstance(getInteraction(), getSequenceDiagram().get());
	}

	public Optional<MMessage> getMMessage(Message message) {
		return getMInteraction().getMessage(message);
	}

	public Optional<MExecution> getMExecution(ExecutionSpecification execution) {
		Optional<MElement<? extends ExecutionSpecification>> element = getMInteraction()
				.getElement(execution);
		if (element.isPresent() && element.get() instanceof MExecution) {
			return Optional.of((MExecution)element.get());
		}
		return Optional.empty();
	}

	public <T extends MElement<? extends Element>> T getElement(String qnFormat, String name, Class<T> type) {
		String qName = String.format(qnFormat, name);

		Element element = getElement(qName);
		return Optional.ofNullable(element).flatMap(getMInteraction()::getElement).filter(type::isInstance)
				.map(type::cast).orElseThrow(() -> new AssertionError("no such element: " + name)); //$NON-NLS-1$
	}

	@SuppressWarnings("unchecked")
	public <T extends MElement<? extends Element>> T getElement(String qnFormat, String name) {
		return (T)getElement(qnFormat, name, MElement.class);
	}

	@SuppressWarnings({"boxing" })
	public Object isSemanticallyBefore(MOccurrence<? extends Element> one,
			MOccurrence<? extends Element> other) {
		List<InteractionFragment> fragments = getMInteraction().getElement().getFragments();
		int indexOfOne = fragments.indexOf(one.getElement());
		int indexOfOther = fragments.indexOf(other.getElement());
		return indexOfOne >= 0 && indexOfOther >= 0 && indexOfOther > indexOfOne;
	}

}
