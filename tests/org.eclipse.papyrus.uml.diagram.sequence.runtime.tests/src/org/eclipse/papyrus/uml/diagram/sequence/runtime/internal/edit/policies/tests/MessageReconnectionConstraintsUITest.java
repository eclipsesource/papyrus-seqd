package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

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
		int msg1Y = getSourceY(messageEP);

		// always sloping down, of course
		EditPart messageEP2 = createConnection(SequenceElementTypes.Async_Message_Edge,
				at(LIFELINE_1_BODY_X, MSG2_Y), at(LIFELINE_2_BODY_X, MSG2_Y + 50));
		int msg2SendY = getSourceY(messageEP2);
		int msg2RecvY = getTargetY(messageEP2);

		int middleX = (LIFELINE_2_BODY_X + LIFELINE_1_BODY_X) / 2;
		editor.moveSelection(at(LIFELINE_2_BODY_X, msg2RecvY), at(middleX, msg1Y));

		assertThat(messageEP, not(runs(LIFELINE_1_BODY_X, msg2SendY, middleX, msg1Y)));
	}

	@Test
	public void reconnectSourceToMessageNotAllowed() {
		EditPart messageEP = createConnection(SequenceElementTypes.Async_Message_Edge,
				at(LIFELINE_1_BODY_X, MSG1_Y), at(LIFELINE_2_BODY_X, MSG1_Y));
		int msg1Y = getSourceY(messageEP);

		// always sloping down, of course
		EditPart messageEP2 = createConnection(SequenceElementTypes.Async_Message_Edge,
				at(LIFELINE_1_BODY_X, MSG2_Y), at(LIFELINE_2_BODY_X, MSG2_Y + 50));
		int msg2SendY = getSourceY(messageEP2);
		int msg2RecvY = getTargetY(messageEP2);

		int middleX = (LIFELINE_2_BODY_X + LIFELINE_1_BODY_X) / 2;
		editor.moveSelection(at(LIFELINE_1_BODY_X, msg2SendY), at(middleX, msg1Y));

		assertThat(messageEP, not(runs(middleX, msg1Y, LIFELINE_2_BODY_X, msg2RecvY)));
	}
}
