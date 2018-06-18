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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineBodyGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;

/**
 * Integration test cases for the {@link LifelineBodyGraphicalNodeEditPolicy}
 * class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("two-lifelines.di")
@Maximized
public class LifelineBodyGraphicalNodeEditPolicyUITest extends AbstractGraphicalEditPolicyUITest {
	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	/**
	 * Initializes me.
	 *
	 */
	public LifelineBodyGraphicalNodeEditPolicyUITest() {
		super();
	}

	@Test
	public void createAsyncMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge,
				at(LIFELINE_1_BODY_X, 115), at(LIFELINE_2_BODY_X, 115));

		assertThat(messageEP, runs(LIFELINE_1_BODY_X, 115, LIFELINE_2_BODY_X, 115, 2));
	}

}
