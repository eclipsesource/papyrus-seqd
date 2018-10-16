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

import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.mapToInt;

import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MObjectImpl;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.uml2.uml.Element;

/**
 * A command that tracks an element that may need to be nudged and a reference element against which it may
 * need to be nudged for padding.
 */
public class DeferredPaddingCommand extends CommandWrapper {

	private MElement<? extends Element> referenceElement;

	private MElement<? extends Element> nudgeElement;

	/**
	 * Not instantiable by clients.
	 */
	private DeferredPaddingCommand() {
		super();
	}

	/**
	 * Obtain the current padding command for the context of some {@code element}.
	 * 
	 * @param element
	 *            an element in the context of which some padding may be required
	 * @return the current padding command for that context
	 */
	public static DeferredPaddingCommand get(MElement<? extends Element> element) {
		return get(DependencyContext.get(), element);
	}

	/**
	 * Obtain the current padding command for the {@code context} of some {@code element}.
	 * 
	 * @param context
	 *            the dependency context
	 * @param element
	 *            an element in the {@code context} of which some padding may be required
	 * @return the current padding command for that {@code context}
	 */
	public static DeferredPaddingCommand get(DependencyContext context, MElement<? extends Element> element) {

		// There is only one padding command ever needed
		return context.get(element.getInteraction(), DeferredPaddingCommand.class,
				DeferredPaddingCommand::new);
	}

	public DeferredPaddingCommand padFrom(MElement<? extends Element> element) {
		if (element == null) {
			// Have now determined that padding is not required
			referenceElement = element;
		} else if (referenceElement == null) {
			referenceElement = element;
		} else if ((referenceElement != element) && referenceElement.precedes(element)) {
			referenceElement = element;
		}
		return this;
	}

	public DeferredPaddingCommand nudge(MElement<? extends Element> element) {
		if (element == null) {
			// Have now determined that padding is not required
			nudgeElement = element;
		} else if (nudgeElement == null) {
			nudgeElement = element;
		} else if ((nudgeElement != element) && element.precedes(nudgeElement)) {
			nudgeElement = element;
		}
		return this;
	}

	@Override
	protected Command createCommand() {
		int nudgeY = computeNudgeAmount();
		if (nudgeY == 0) {
			return IdentityCommand.INSTANCE;
		}

		Command result = nudgeElement.nudge(nudgeY);
		if (result == null) {
			// Can't nudge this element
			result = UnexecutableCommand.INSTANCE;
		}

		return result;
	}

	protected int computeNudgeAmount() {
		if ((nudgeElement == null) || (referenceElement == null)) {
			return 0;
		}

		MElement<? extends Element> padFrom = getPaddableElement(referenceElement);
		MElement<? extends Element> padElement = getPaddableElement(nudgeElement);

		if (padFrom.getElement() == padElement.getElement()) {
			// This would happen, for example, when re-targeting a message bringins along an
			// execution specification that emits a message terminating on the re-targeted
			// message's new receiving lifeline
			return 0;
		}

		EditingDomain editingDomain = ((MObjectImpl<?>)nudgeElement).getEditingDomain();
		LayoutHelper layout = LogicalModelPlugin.INSTANCE.getLayoutHelper(editingDomain);
		LayoutConstraints constraints = layout.getConstraints();

		OptionalInt referencePadding = mapToInt(as(padFrom.getDiagramView(), View.class),
				view -> constraints.getPadding(RelativePosition.BOTTOM, view));

		OptionalInt nudgeePadding = mapToInt(as(padElement.getDiagramView(), View.class),
				view -> constraints.getPadding(RelativePosition.TOP, view));

		int requiredPadding = referencePadding.orElse(0) + nudgeePadding.orElse(0);
		int gap = nudgeElement.verticalDistance(referenceElement).orElse(0);

		return Math.max(0, requiredPadding - gap);
	}

	MElement<? extends Element> getPaddableElement(MElement<? extends Element> element) {
		return new SequenceDiagramSwitch<MElement<? extends Element>>() {
			@Override
			public MElement<? extends Element> caseMMessageEnd(MMessageEnd object) {
				return object.getOwner();
			}

			@Override
			public MElement<? extends Element> caseMExecutionOccurrence(MExecutionOccurrence object) {
				return object.getExecution().get();
			}

			@Override
			public <T extends Element> MElement<? extends Element> caseMElement(MElement<T> object) {
				return object;
			}
		}.doSwitch(element);
	}
}
