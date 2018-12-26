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

package org.eclipse.papyrus.uml.interaction.tests.runners;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * Optionally annotates a {@link Filtered} test class with the delegate runner to instantiate and
 * {@linkplain FilterWith filter}.
 * 
 * @see FilterWith
 * @see Filtered
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Delegate {
	/** The delegate runner class to instantiate and filter. */
	Class<? extends Runner> value() default BlockJUnit4ClassRunner.class;
}
