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

import static org.eclipse.papyrus.uml.interaction.graph.util.CrossReferenceUtil.invertSingle;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.uml2.uml.UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.UMLPackage;

public class ExecutionCreationCommandParameter {

	private boolean createExecution = false;

	private boolean createReply = false;

	private EClass executionType = ACTION_EXECUTION_SPECIFICATION;

	public ExecutionCreationCommandParameter() {
		super();
	}

	public ExecutionCreationCommandParameter(boolean createExecution, boolean createReply,
			EClass executionType) {
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

	public Optional<ExecutionSpecification> getExecution(Message request) {
		Optional<ExecutionSpecification> result;

		if (!isCreateExecution()) {
			result = Optional.empty();
		} else {
			result = invertSingle(request.getReceiveEvent(),
					UMLPackage.Literals.EXECUTION_SPECIFICATION__START, ExecutionSpecification.class);
		}

		return result;
	}

	public Optional<Message> getReply(Message request) {
		Optional<Message> result;

		if (!isCreateReply()) {
			result = Optional.empty();
		} else {
			result = as(getExecution(request).map(ExecutionSpecification::getFinish), MessageEnd.class)
					.map(MessageEnd::getMessage);
		}

		return result;
	}
}
