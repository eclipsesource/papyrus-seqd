/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *  Atos Origin - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.part;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.ui.extension.commands.PerspectiveContextDependence;

/**
 * LightweightSequenceDiagramCreationCondition class allows to check if a Sequence diagram can be added to the
 * selected element.
 */
public class LightweightSequenceDiagramCreationCondition extends PerspectiveContextDependence {

	/**
	 * @return whether the diagram can be created.
	 */
	@Override
	public boolean create(EObject selectedElement) {
		return false;
	}
}
