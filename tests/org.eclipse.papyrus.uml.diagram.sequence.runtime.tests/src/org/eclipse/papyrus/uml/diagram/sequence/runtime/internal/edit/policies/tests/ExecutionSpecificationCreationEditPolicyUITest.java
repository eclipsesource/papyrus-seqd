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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isAt;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isSized;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.DEFAULT_SIZE;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.isNear;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.ExecutionSpecificationCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;

/**
 * Integration tests for {@link ExecutionSpecificationCreationEditPolicy}
 */
@SuppressWarnings("restriction")
@ModelResource("one-exec.di")
public class ExecutionSpecificationCreationEditPolicyUITest extends AbstractGraphicalEditPolicyUITest {

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	// inital y position of the parent exec spec
	private static final int Y_EXEC_SPEC_POSITION = 170;

	// Vertical position at which to create the nested execution
	private static final int OFFSET = 30;

	// initial height of the exec spec
	private static final int HEIGHT_EXEC_SPEC = 82;

	/**
	 * Initializes me.
	 */
	public ExecutionSpecificationCreationEditPolicyUITest() {
		super();
	}

	@Test
	public void createNestedExecution() {
		EditPart nestedExecEP = createShape(SequenceElementTypes.Action_Execution_Shape,
				at(LIFELINE_2_BODY_X, Y_EXEC_SPEC_POSITION + OFFSET), DEFAULT_SIZE);

		// the Y position is not determined by the input
		assertThat(nestedExecEP, isAt(isNear(LIFELINE_2_BODY_X), isNear(Y_EXEC_SPEC_POSITION + OFFSET)));
		assertThat(nestedExecEP, isSized(isNear(10), isNear(40)));
		EditPart parentExecSpec = nestedExecEP.getParent();
		assertThat(parentExecSpec,
				isAt(isNear(LIFELINE_2_BODY_X - 5 /* exec width / 2 */), isNear(Y_EXEC_SPEC_POSITION)));
		assertThat(parentExecSpec,
				isSized(isNear(10), isNear(HEIGHT_EXEC_SPEC + 40 /* exec spec default height */, 2)));
	}
}
