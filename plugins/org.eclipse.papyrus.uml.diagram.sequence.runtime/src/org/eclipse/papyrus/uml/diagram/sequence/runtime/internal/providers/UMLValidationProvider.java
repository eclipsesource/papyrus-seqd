/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;

public class UMLValidationProvider {

	public static boolean shouldConstraintsBePrivate() {
		return false;
	}

	protected static boolean constraintsActive = false;

	public static void runWithConstraints(TransactionalEditingDomain editingDomain, Runnable operation) {
		final Runnable op = operation;
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					constraintsActive = true;
					op.run();
				} finally {
					constraintsActive = false;
				}
			}
		};
		if (editingDomain != null) {
			try {
				editingDomain.runExclusive(task);
			} catch (Exception e) {
				Activator.log.error("Validation failed", e); //$NON-NLS-1$
			}
		} else {
			task.run();
		}
	}


}
