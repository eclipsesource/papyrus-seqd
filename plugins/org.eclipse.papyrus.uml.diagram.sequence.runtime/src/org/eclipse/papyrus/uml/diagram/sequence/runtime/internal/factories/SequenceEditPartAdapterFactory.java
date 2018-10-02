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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.factories;

import static org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil.getAllContents;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.InteractionEditPart;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;

/**
 * Adapter factory for edit-parts in the sequence diagram.
 */
public class SequenceEditPartAdapterFactory implements IAdapterFactory {
	private final Class<?>[] adapters = {IMagnetManager.class };

	/**
	 * Initializes me.
	 */
	public SequenceEditPartAdapterFactory() {
		super();
	}

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		EditPart self = (EditPart)adaptableObject;
		T result = null;

		if (adapterType == IMagnetManager.class) {
			// The interaction edit-part has this
			Optional<EditPart> interactionEP = getInteractionEditPart(self);
			result = interactionEP.map(adapt(adapterType)).orElse(null);
		}

		return result;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return adapters;
	}

	Optional<EditPart> getInteractionEditPart(EditPart editPart) {
		EditPart result;

		if (editPart instanceof DiagramEditPart) {
			// Get the interaction frame from this
			result = ((DiagramEditPart)editPart).getChildBySemanticHint(ViewTypes.INTERACTION);
		} else {
			// How to step "up" the edit-part hierarchy
			UnaryOperator<EditPart> step = ep -> (ep instanceof ConnectionEditPart)
					// Connections are children of the root edit-part
					? findInteraction(ep.getRoot())
					: ep.getParent();

			for (result = editPart; result != null; result = step.apply(result)) {
				if (result instanceof InteractionEditPart) {
					break;
				}
			}
		}

		return Optional.ofNullable(result);
	}

	static EditPart findInteraction(EditPart root) {
		EditPart result = null;

		for (Iterator<EditPart> iter = getAllContents(root, false); (result == null) && iter.hasNext();) {
			EditPart next = iter.next();
			if (next instanceof InteractionEditPart) {
				result = next;
			}
		}

		return result;
	}

	static <T> Function<IAdaptable, T> adapt(Class<T> type) {
		return a -> a.getAdapter(type);
	}
}
