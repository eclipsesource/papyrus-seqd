/**
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
  *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.part;

import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part.DefaultModelElementSelectionPage;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Messages;

/**
 * Wizard page that allows to select element from model.
 */
public class ModelElementSelectionPage extends DefaultModelElementSelectionPage {

	public ModelElementSelectionPage(String pageName) {
		super(org.eclipse.papyrus.infra.gmfdiag.common.Activator.getInstance().getItemProvidersAdapterFactory(), pageName);
	}

	@Override
	protected String getSelectionTitle() {
		return Messages.ModelElementSelectionPageMessage;
	}

}
