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

import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition.LEFT;
import static org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition.RIGHT;
import static org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes.LIFELINE_HEADER;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@SuppressWarnings("boxing")
	@Override
	protected Command createCommand() {
		SemanticHelper semantics = semanticHelper();
		CreationParameters params = CreationParameters.in(getTarget().getElement(),
				UMLPackage.Literals.INTERACTION__LIFELINE);
		CreationCommand<Lifeline> resultCommand = setResult(semantics.createLifeline(params));

		Shape frame = diagramHelper().getInteractionFrame(getTarget().getDiagramView().get());
		Compartment compartment = diagramHelper().getShapeCompartment(frame);

		List<MLifeline> leftToRight = getTarget().getLifelines().stream()//
				.sorted((ll1, ll2) -> {
					int ll1Left = ll1.getDiagramView().map(layoutHelper()::getLeft).orElse(-1);
					int ll2Left = ll2.getDiagramView().map(layoutHelper()::getLeft).orElse(-1);
					if (ll1Left == ll2Left) {
						return -1;
					}
					return ll1Left - ll2Left;
				})//
				.collect(Collectors.toList());

		int insertionAbsoluteX = layoutHelper().toAbsoluteX(null, compartment, xOffset);

		/*
		 * do we need to move our offset, because we attempt to insert on top of a lifeline starting to the
		 * left of us?
		 */
		int abosluteXWithoutPadding = insertionAbsoluteX
				- layoutHelper().getConstraints().getPadding(LEFT, LIFELINE_HEADER)
				- layoutHelper().getConstraints().getPadding(RIGHT, LIFELINE_HEADER);
		Optional<MLifeline> llUnderMe = leftToRight.stream()//
				.filter(ll -> {
					int left = ll.getDiagramView().map(layoutHelper()::getLeft)
							.orElse(abosluteXWithoutPadding);
					int right = ll.getDiagramView().map(layoutHelper()::getRight)
							.orElse(abosluteXWithoutPadding);
					return left < abosluteXWithoutPadding && right > abosluteXWithoutPadding;
				})//
				.findFirst();

		int absoluteX;
		if (llUnderMe.isPresent()) {
			absoluteX = llUnderMe.get()//
					.getDiagramView().map(layoutHelper()::getRight).orElse(insertionAbsoluteX)
					+ layoutHelper().getConstraints().getPadding(LEFT, LIFELINE_HEADER)
					+ layoutHelper().getConstraints().getPadding(RIGHT, LIFELINE_HEADER);
		} else {
			absoluteX = insertionAbsoluteX;
		}

		Command result = resultCommand.chain(diagramHelper().createLifelineShape(resultCommand,
				getTarget().getDiagramView().get(), absoluteX, height));

		/* do we need to nudge elements to the right of us? */
		Optional<MLifeline> existing = getTarget().getLifelineAt(absoluteX);
		if (existing.isPresent()) {
			// Make room for it
			int spaceRequired = layoutHelper().getConstraints().getMinimumWidth(LIFELINE_HEADER)
					+ layoutHelper().getConstraints().getPadding(LEFT, LIFELINE_HEADER)
					+ layoutHelper().getConstraints().getPadding(RIGHT, LIFELINE_HEADER);
			result = existing.get().nudgeHorizontally(spaceRequired).chain(result);
		}

		return result;
	}

}
