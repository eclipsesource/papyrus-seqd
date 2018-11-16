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

import java.util.List;
import java.util.Optional;

import org.eclipse.papyrus.uml.interaction.model.MExecution;

/**
 * A {@linkplain DependencyContext context} variable that records execution nesting change in progress.
 * 
 * @see DependencyContext
 */
public final class PendingNestedData extends PendingContainmentChangeData<MExecution, MExecution> {
	private PendingNestedData(MExecution nesting, MExecution nested) {
		super(nesting, nested);
	}

	/**
	 * Query the execution that is in process of having the given element set as its new {@code nesting}
	 * execution.
	 * 
	 * @param nesting
	 *            an execution that may or may not be getting a new nested execution
	 * @return the pending nested execution
	 */
	public static Optional<MExecution> getPendingChild(MExecution nesting) {
		return PendingContainmentChangeData.getPendingChild(PendingNestedData.class, nesting);
	}

	/**
	 * Query the executions that are in process of having the given execution set as their new
	 * {@code newsting}.
	 * 
	 * @param nesting
	 *            an execution that may or may not be getting a new nested execution
	 * @return the pending nested executions
	 */
	public static List<MExecution> getPendingNestedExecutions(MExecution nesting) {
		return PendingContainmentChangeData.getPendingChildren(PendingNestedData.class, nesting);
	}

	/**
	 * Query the execution that is in process of being made the nesting of a pending {@code nested} execution.
	 * 
	 * @param nested
	 *            an execution that may or may not be getting a new nesting execution
	 * @return the pending nesting execution
	 */
	public static Optional<MExecution> getPendingNestingExecution(MExecution nested) {
		return PendingContainmentChangeData.getPendingParent(PendingNestedData.class, nested);
	}

	/**
	 * Record an execution that is in process of having the given execution set as its new {@code nesting}
	 * execution.
	 * 
	 * @param nesting
	 *            an execution that is getting a new nested execution
	 * @param pendingNested
	 *            an execution that is getting the new {@code nesting}
	 */
	static void setPendingNested(MExecution nesting, MExecution pendingNested) {
		if (!nesting.getNestedExecutions().stream()
				.anyMatch(n -> n.getElement() == pendingNested.getElement())) {

			PendingContainmentChangeData.setPendingChild(PendingNestedData.class, nesting, pendingNested,
					PendingNestedData::new);
		}
	}

}
