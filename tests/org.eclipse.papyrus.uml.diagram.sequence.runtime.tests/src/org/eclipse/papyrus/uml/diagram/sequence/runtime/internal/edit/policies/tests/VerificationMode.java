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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.hamcrest.Matcher;

/**
 * Enumeration of verifcation modes, useful for sharing fragments of
 * verification amongst multiple tests for different purposes.
 */
public enum VerificationMode {
	/** The verification is an assertion. */
	ASSERT,
	/** The verification is an assumption. */
	ASSUME,
	/** The verification is not needed. */
	IGNORE;

	public <T> void verify(String message, T actual, Matcher<? super T> test) {
		switch (this) {
		case ASSERT:
			assertThat(message, actual, test);
			break;
		case ASSUME:
			assumeThat(message, actual, test);
			break;
		default:
			// Ignore
			break;
		}
	}

	public <T> void verify(T actual, Matcher<? super T> test) {
		switch (this) {
		case ASSERT:
			assertThat(actual, test);
			break;
		case ASSUME:
			assumeThat(actual, test);
			break;
		default:
			// Ignore
			break;
		}
	}

}
