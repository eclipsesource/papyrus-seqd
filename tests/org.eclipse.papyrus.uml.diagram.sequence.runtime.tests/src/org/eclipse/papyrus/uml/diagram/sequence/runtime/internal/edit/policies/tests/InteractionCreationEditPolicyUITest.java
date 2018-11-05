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
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.isNear;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.InteractionCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;

/**
 * Integration test cases for the {@link InteractionCreationEditPolicy} class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("empty.di")
public class InteractionCreationEditPolicyUITest extends AbstractGraphicalEditPolicyUITest {

	/**
	 * Initializes me.
	 *
	 */
	public InteractionCreationEditPolicyUITest() {
		super();
	}

	@Test
	public void createLifeline() {
		EditPart lifelineEP = createShape(SequenceElementTypes.Lifeline_Shape, at(99, 90), DEFAULT_SIZE);

		// the Y position is not determined by the input
		assertThat(lifelineEP, isAt(isNear(99), not(90)));
	}

	@Test
	public void createLifelineOnTopOfOther() {
		/* setup */
		EditPart lifelineEP1 = createShape(SequenceElementTypes.Lifeline_Shape, at(99, 90), DEFAULT_SIZE);

		/* act */
		EditPart lifelineEP2 = createShape(SequenceElementTypes.Lifeline_Shape, at(115, 90), DEFAULT_SIZE);

		// the Y position is not determined by the input
		assertThat(lifelineEP1, isAt(isNear(99), not(90)));
		assertThat(lifelineEP2, isAt(isNear(209), not(90)));
	}

	@Test
	public void createLifelineToTheRight() {
		/* setup */
		EditPart lifelineEP1 = createShape(SequenceElementTypes.Lifeline_Shape, at(99, 90), DEFAULT_SIZE);

		/* act */
		EditPart lifelineEP2 = createShape(SequenceElementTypes.Lifeline_Shape, at(215, 90), DEFAULT_SIZE);

		// the Y position is not determined by the input
		assertThat(lifelineEP1, isAt(isNear(99), not(90)));
		assertThat(lifelineEP2, isAt(isNear(215), not(90)));
	}

	@Test
	public void createLifelineToTheLeft() {
		/* setup */
		EditPart lifelineEP1 = createShape(SequenceElementTypes.Lifeline_Shape, at(99, 90), DEFAULT_SIZE);

		/* act */
		EditPart lifelineEP2 = createShape(SequenceElementTypes.Lifeline_Shape, at(89, 90), DEFAULT_SIZE);

		// the Y position is not determined by the input
		assertThat(lifelineEP2, isAt(isNear(89), not(90)));
		assertThat(lifelineEP1, isAt(isNear(209), not(90)));
	}
}
