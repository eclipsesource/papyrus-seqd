/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   R. Schnekenburger - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.SequenceTypeSwitch;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MObject;
import org.eclipse.papyrus.uml.interaction.model.util.SequenceDiagramSwitch;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;

/**
 * ExecutionSpecification creation edit policy based on the <em>Logical Model</em>.
 */
public class ExecutionSpecificationCreationEditPolicy extends LogicalModelCreationEditPolicy {

	@Override
	protected Optional<org.eclipse.emf.common.command.Command> getCreationCommand(MInteraction interaction,
			Element parentElement, View parentView, Point location, Dimension size, IElementType type) {

		Optional<MExecution> mExecution = Optional.empty();

		ExecutionSpecification exec = (ExecutionSpecification)parentElement;
		for (MLifeline lifeline : interaction.getLifelines()) {
			Optional<MExecution> execution = lifeline.getExecution(exec);
			if (execution.isPresent()) {
				mExecution = execution;
			}
		}

		if (!mExecution.isPresent()) {
			return Optional.empty();
		}

		class CommandSwitch extends SequenceDiagramSwitch<Command> {

			@Override
			@SuppressWarnings("hiding")
			public Command caseMExecution(MExecution execution) {
				return new SequenceTypeSwitch<Command>() {
					@Override
					public Command caseExecutionSpecification(IElementType type) {
						EClass eClass = type.getEClass();
						Optional<MElement<?>> before = execution.elementAt(location.y());
						int offset = location.y();

						if (before.isPresent()) {
							// Get the bottom of the before element
							OptionalInt bottom = before.get().getBottom();
							if (bottom.isPresent()) {
								int relativeBottom = bottom.getAsInt()
										- getLayoutHelper().getTop((Shape)parentView);
								offset = offset - relativeBottom;
							} else {
								// If it doesn't have a bottom, then ignore it
								before = Optional.empty();
							}
						}

						return execution.insertNestedExecutionAfter(before.orElse(execution), offset,
								size != null ? size.height : 40, eClass);
					}

					@Override
					public Command caseAsyncMessage(IHintedType type) {
						return null;
					}

					@Override
					public Command defaultCase(Object object) {
						return CommandSwitch.super.caseMExecution(execution);
					}
				}.doSwitch(type);
			}

			@Override
			public Command defaultCase(MObject object) {
				return UnexecutableCommand.INSTANCE;
			}
		}

		return mExecution.map(new CommandSwitch()::doSwitch);
	}

}
