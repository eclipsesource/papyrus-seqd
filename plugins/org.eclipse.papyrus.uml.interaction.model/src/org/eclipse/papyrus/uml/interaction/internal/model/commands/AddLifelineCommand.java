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
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationParameters;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A lifeline creation operation.
 *
 * @author Christian W. Damus
 */
public class AddLifelineCommand extends ModelCommand.Creation<MInteractionImpl, Lifeline> {

	private final int xOffset;

	private final int height;

	/**
	 * Initializes me.
	 * 
	 * @param owner
	 *            the interaction in which to create the lifeline
	 * @param xOffset
	 *            the offset from the left edge of the interaction frame at which to create the lifeline
	 * @param height
	 *            the height of the lifeline, or {@code -1} for the default
	 */
	public AddLifelineCommand(MInteractionImpl owner, int xOffset, int height) {
		super(owner, Lifeline.class);

		this.xOffset = xOffset;
		this.height = height;
	}

	@Override
	protected Command createCommand() {
		SemanticHelper semantics = semanticHelper();
		CreationParameters params = CreationParameters.in(getTarget().getElement(),
				UMLPackage.Literals.INTERACTION__LIFELINE);
		CreationCommand<Lifeline> resultCommand = setResult(semantics.createLifeline(params));

		Shape frame = diagramHelper().getInteractionFrame(getTarget().getDiagramView().get());
		Compartment compartment = diagramHelper().getShapeCompartment(frame);

		int absoluteX = layoutHelper().toAbsoluteX(null, compartment, xOffset);

		Command result = resultCommand.chain(diagramHelper().createLifelineShape(resultCommand,
				getTarget().getDiagramView().get(), absoluteX, height));

		// Are we inserting this amongst existing lifelines?
		Optional<MLifeline> existing = getTarget().getLifelineAt(absoluteX);
		if (existing.isPresent()) {
			// Make room for it
			int spaceRequired = 90; // FIXME: LayoutHelper should compute space needed
			result = existing.get().nudgeHorizontally(spaceRequired).chain(result);
		}

		return result;
	}

}
