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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.gef.EditPart;

/**
 * Annotation for fields that represent fixtures in the contextual model or editor, usually an
 * {@link EditorFixture}.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface AutoFixture {
	/** An optional element name to search for, which may be qualified or not. */
	String value() default "";

	//
	// Nested types
	//

	/**
	 * Annotation for {@link AutoFixture @AutoFixture} fields of {@link EditPart} kind to specify the visual
	 * ID of a child of the top edit-part to resolve as the fixture.
	 */
	@Retention(RUNTIME)
	@Target(FIELD)
	public @interface VisualID {
		/** The edit-part visual ID to resolve. */
		String value();
	}
}
