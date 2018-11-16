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

import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.above;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.below;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.mapToInt;

import java.util.Comparator;
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
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelOrdering;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.uml2.uml.Element;

/**
 * A command that tracks an element that may need to be nudged and a reference element against which it may
 * need to be nudged for padding.
 */
public class DeferredPaddingCommand extends CommandWrapper {

	private static final Comparator<MElement<?>> ORDERING = LogicalModelOrdering.semantically();

	private MElement<? extends Element> referenceElement;

	private MElement<? extends Element> nudgeElement;

	private PaddingDirection direction = PaddingDirection.NONE;

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

	public DeferredPaddingCommand pad(MElement<? extends Element> from, MElement<? extends Element> to) {
		if ((from == null) || (to == null)) {
			// Have now determined that padding is not required
			referenceElement = null;
			nudgeElement = null;
			direction = PaddingDirection.NONE;
		} else if (referenceElement == null) {
			referenceElement = from;
			nudgeElement = to;

			if (before(from, nudgeElement)) {
				// Implies downward nudging
				direction = PaddingDirection.DOWN;
			} else {
				direction = PaddingDirection.UP;
			}
		} else if (direction.updateReference(referenceElement, from)) {
			referenceElement = from;
			nudgeElement = to;
		} else if (direction.updateNudge(nudgeElement, referenceElement, to)) {
			// Nudge the closer element
			nudgeElement = to;
		}
		return this;
	}

	private static boolean before(MElement<? extends Element> a, MElement<? extends Element> b) {
		return (a.getElement() != b.getElement()) && (a.precedes(b) || (ORDERING.compare(a, b) < 0));
	}

	private static boolean after(MElement<? extends Element> a, MElement<? extends Element> b) {
		return (a.getElement() != b.getElement()) && (b.precedes(a) || (ORDERING.compare(b, a) < 0));
	}

	@Override
	protected boolean prepare() {
		// Don't create the command, yet. We can always do something
		return true;
	}

	@Override
	public void execute() {
		if (super.prepare()) {
			super.execute();
		}
	}

	@Override
	protected Command createCommand() {
		int nudgeY = computeNudgeAmount();
		if (nudgeY == 0) {
			return IdentityCommand.INSTANCE;
		}

		Command result;
		switch (direction) {
			case DOWN:
				result = getPaddableElement(nudgeElement).nudge(nudgeY);
				break;
			case UP:
				// An "upward" nudge doesn't actually nudge anything up because the diagram can
				// only expand downwards (lifeline heads are fixed). Rather, we ensure space
				// by nudging down the element that was moved up, but also everything following
				// it so that the layout is consistent
				result = getPaddableElement(referenceElement).nudge(nudgeY);
				break;
			default:
				result = null;
				break;
		}

		if (result == null) {
			// Can't nudge this element
			result = UnexecutableCommand.INSTANCE;
		}

		return result;
	}

	protected int computeNudgeAmount() {
		if ((direction == PaddingDirection.NONE) || (nudgeElement == null) || (referenceElement == null)) {
			return 0;
		}

		MElement<? extends Element> padFrom = getPaddableElement(referenceElement);
		MElement<? extends Element> padElement = getPaddableElement(nudgeElement);

		if ((padFrom.getElement() == padElement.getElement())
				|| ((direction == PaddingDirection.DOWN) && above(padFrom).test(padElement))
				|| ((direction == PaddingDirection.UP) && below(padFrom).test(padElement))) {
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
		int gap;

		switch (direction) {
			case DOWN:
				gap = nudgeElement.verticalDistance(referenceElement).orElse(0);
				break;
			case UP:
				gap = referenceElement.verticalDistance(nudgeElement).orElse(0);
				break;
			default:
				throw new IllegalStateException("no nudge direction"); //$NON-NLS-1$
		}

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

	//
	// Nested types
	//

	/**
	 * Enumeration of direction in which padding may be applied.
	 */
	private static enum PaddingDirection {
		NONE, DOWN, UP;

		boolean updateReference(MElement<? extends Element> reference, MElement<? extends Element> from) {
			switch (this) {
				case DOWN:
					return before(reference, from);
				case UP:
					return after(reference, from);
				default:
					return false;
			}
		}

		boolean updateNudge(MElement<? extends Element> nudge, MElement<? extends Element> from,
				MElement<? extends Element> to) {
			switch (this) {
				case DOWN:
					return after(to, from) && before(to, nudge);
				case UP:
					return before(to, from) && after(to, nudge);
				default:
					return false;
			}
		}

	}
}
