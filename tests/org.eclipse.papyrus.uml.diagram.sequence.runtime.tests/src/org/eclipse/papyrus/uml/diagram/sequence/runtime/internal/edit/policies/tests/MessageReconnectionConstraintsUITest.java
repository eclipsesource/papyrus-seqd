package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;

/**
 * Integration test cases for the message re-connection behaviour.
 *
 * @author Antonio campesino
 */
@ModelResource("two-lifelines.di")
@Maximized
@SuppressWarnings("restriction")
public class MessageReconnectionConstraintsUITest extends AbstractGraphicalEditPolicyUITest {
	// Horizontal position of the first lifeline's body
	private static final int LIFELINE_1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LIFELINE_2_BODY_X = 281;

	// Attempting to create these at the same Y position results in padding that
	// breaks our assumptions
	private static final int MSG1_Y = 120;
	private static final int MSG2_Y = 150;

	@Test
	public void reconnectTargetToMessageNotAllowed() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge,
				at(LIFELINE_1_BODY_X, MSG1_Y), at(LIFELINE_2_BODY_X, MSG1_Y));
		assumeThat(messageEP, runs(LIFELINE_1_BODY_X, MSG1_Y, LIFELINE_2_BODY_X, MSG1_Y));

		EditPart messageEP2 = createConnection(SequenceElementTypes.Async_Message_Edge,
				at(LIFELINE_1_BODY_X, MSG2_Y), at(LIFELINE_2_BODY_X, MSG2_Y + 50)); // always sloping down,
																					// of course
		assumeThat(messageEP2, runs(LIFELINE_1_BODY_X, MSG2_Y, LIFELINE_2_BODY_X, MSG2_Y + 50));

		int middleX = (LIFELINE_2_BODY_X + LIFELINE_1_BODY_X) / 2;
		editor.moveSelection(at(LIFELINE_2_BODY_X, MSG2_Y + 50), at(middleX, MSG1_Y));

		assertThat(messageEP, not(runs(LIFELINE_1_BODY_X, MSG2_Y + 50, middleX, MSG1_Y)));
	}

	@Test
	public void reconnectSourceToMessageNotAllowed() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge,
				at(LIFELINE_1_BODY_X, MSG1_Y), at(LIFELINE_2_BODY_X, MSG1_Y));
		assumeThat(messageEP, runs(LIFELINE_1_BODY_X, MSG1_Y, LIFELINE_2_BODY_X, MSG1_Y));

		EditPart messageEP2 = createConnection(SequenceElementTypes.Async_Message_Edge,
				at(LIFELINE_1_BODY_X, MSG2_Y), at(LIFELINE_2_BODY_X, MSG2_Y + 50)); // always sloping down,
																					// of course
		assumeThat(messageEP2, runs(LIFELINE_1_BODY_X, MSG2_Y, LIFELINE_2_BODY_X, MSG2_Y + 50));

		int middleX = (LIFELINE_2_BODY_X + LIFELINE_1_BODY_X) / 2;
		editor.moveSelection(at(LIFELINE_1_BODY_X, MSG2_Y), at(middleX, MSG1_Y));

		assertThat(messageEP, not(runs(middleX, MSG1_Y, LIFELINE_2_BODY_X, MSG2_Y + 50)));
	}
}
