/*****************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.interaction.model.spi;

import static org.eclipse.uml2.uml.UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION;

import org.eclipse.emf.ecore.EClass;

public class ExecutionCreationCommandParameter {

	private boolean createExecution = false;

	private boolean createReply = false;

	private EClass executionType = ACTION_EXECUTION_SPECIFICATION;

	public ExecutionCreationCommandParameter() {
		super();
	}

	public ExecutionCreationCommandParameter(boolean createExecution, boolean createReply, EClass executionType) {
		super();
		this.createExecution = createExecution;
		this.createReply = createReply;
		this.executionType = executionType;
	}

	public boolean isCreateExecution() {
		return createExecution;
	}

	public boolean isCreateReply() {
		return createReply;
	}

	public EClass getExecutionType() {
		return executionType;
	}

}
