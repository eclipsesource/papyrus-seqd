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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isAt;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.DEFAULT_SIZE;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;

/**
 * Integration test cases for the {@link LifelineCreationEditPolicy} class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("two-lifelines.di")
@Maximized
public class LifelineCreationEditPolicyUITest extends AbstractGraphicalEditPolicyUITest {
	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	/**
	 * Initializes me.
	 *
	 */
	public LifelineCreationEditPolicyUITest() {
		super();
	}

	@Test
	public void createActionExecutionSpecification() {
		EditPart executionEP = createShape(SequenceElementTypes.Action_Execution_Shape,
				at(LIFELINE_2_BODY_X, 115), DEFAULT_SIZE);

		// The 10-unit-wide shape is centred on the lifeline body
		assertThat(executionEP, isAt(LIFELINE_2_BODY_X - 5, 115, 2));
	}

	@Test
	public void createBehaviorExecutionSpecification() {
		EditPart executionEP = createShape(SequenceElementTypes.Behavior_Execution_Shape,
				at(LIFELINE_2_BODY_X, 115), DEFAULT_SIZE);

		// The 10-unit-wide shape is centred on the lifeline body
		assertThat(executionEP, isAt(LIFELINE_2_BODY_X - 5, 115, 2));
	}

}
