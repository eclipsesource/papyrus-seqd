package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isAt;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.DEFAULT_SIZE;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.isNear;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.swt.SWT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("restriction")
@ModelResource("empty.di")
@Maximized
@RunWith(Parameterized.class)
public class LifelineMoveUITest extends AbstractGraphicalEditPolicyUITest {

	private static final int LIFELINE_1_X = 50;
	private static final int LIFELINE_2_X = 200;
	private static final int LIFELINE_3_X = 350;

	private static final int LIFELINE_HEADER_Y = 80;

	private EditorFixture.Modifiers modifiers;
	private boolean ctrlPressed;

	public LifelineMoveUITest(boolean ctrlPressed, String label) {
		super();
		this.ctrlPressed = ctrlPressed;
		this.modifiers = ctrlPressed
				? editor.modifierKey(Platform.OS_MACOSX.equals(Platform.getOS()) ? SWT.COMMAND : SWT.CTRL)
				: editor.unmodified();
	}

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] { //
				{ true, "ctrl pressed" }, //
				{ false, "no modifier pressed" }, //
		});
	}

	@Test
	public void moveLeftAllowed() {
		/* setup */
		EditPart lifelineEP1 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_1_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP2 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_2_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP3 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_3_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);

		/* act */
		editor.with(modifiers, () -> editor.moveSelection(at(LIFELINE_2_X + 50, LIFELINE_HEADER_Y),
				at(LIFELINE_2_X + 10, LIFELINE_HEADER_Y)));

		/* assert */
		assertThat(lifelineEP1, isAt(isNear(LIFELINE_1_X), not(LIFELINE_HEADER_Y)));
		assertThat(lifelineEP2, isAt(isNear(LIFELINE_2_X - 40), not(LIFELINE_HEADER_Y)));
		assertThat(lifelineEP3, isAt(isNear(LIFELINE_3_X), not(LIFELINE_HEADER_Y)));
	}

	@Test
	public void moveRightAllowed() {
		/* setup */
		EditPart lifelineEP1 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_1_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP2 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_2_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP3 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_3_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);

		/* act */
		editor.with(modifiers, () -> editor.moveSelection(at(LIFELINE_2_X + 50, LIFELINE_HEADER_Y),
				at(LIFELINE_2_X + 90, LIFELINE_HEADER_Y)));

		/* assert */
		assertThat(lifelineEP1, isAt(isNear(LIFELINE_1_X), not(LIFELINE_HEADER_Y)));
		assertThat(lifelineEP2, isAt(isNear(LIFELINE_2_X + 40), not(LIFELINE_HEADER_Y)));
		assertThat(lifelineEP3, isAt(isNear(LIFELINE_3_X), not(LIFELINE_HEADER_Y)));
	}

	@Test
	public void moveLeftPaddingTooSmall() {
		/* setup */
		EditPart lifelineEP1 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_1_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP2 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_2_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP3 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_3_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);

		/* act */
		editor.with(modifiers, () -> editor.moveSelection(at(LIFELINE_2_X + 50, LIFELINE_HEADER_Y),
				at(LIFELINE_2_X, LIFELINE_HEADER_Y)));

		/* assert */
		assertThat(lifelineEP1, isAt(isNear(LIFELINE_1_X), not(LIFELINE_HEADER_Y)));
		if (ctrlPressed) {
			assertThat(lifelineEP2, isAt(isNear(LIFELINE_2_X - 50), not(LIFELINE_HEADER_Y)));
		} else {
			assertThat(lifelineEP2, isAt(isNear(LIFELINE_2_X), not(LIFELINE_HEADER_Y)));
		}
		assertThat(lifelineEP3, isAt(isNear(LIFELINE_3_X), not(LIFELINE_HEADER_Y)));
	}

	@Test
	public void moveRightPaddingTooSmall() {
		/* setup */
		EditPart lifelineEP1 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_1_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP2 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_2_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP3 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_3_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);

		/* act */
		editor.with(modifiers, () -> editor.moveSelection(at(LIFELINE_2_X + 50, LIFELINE_HEADER_Y),
				at(LIFELINE_2_X + 100, LIFELINE_HEADER_Y)));

		/* assert */
		assertThat(lifelineEP1, isAt(isNear(LIFELINE_1_X), not(LIFELINE_HEADER_Y)));
		if (ctrlPressed) {
			assertThat(lifelineEP2, isAt(isNear(LIFELINE_2_X + 50), not(LIFELINE_HEADER_Y)));
		} else {
			assertThat(lifelineEP2, isAt(isNear(LIFELINE_2_X), not(LIFELINE_HEADER_Y)));
		}
		assertThat(lifelineEP3, isAt(isNear(LIFELINE_3_X), not(LIFELINE_HEADER_Y)));
	}

	@Test
	public void moveRightAttemptedReorder() {
		/* setup */
		EditPart lifelineEP1 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_1_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP2 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_2_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);
		EditPart lifelineEP3 = createShape(SequenceElementTypes.Lifeline_Shape, at(LIFELINE_3_X, LIFELINE_HEADER_Y),
				DEFAULT_SIZE);

		/* act */
		editor.with(modifiers, () -> editor.moveSelection(at(LIFELINE_2_X + 50, LIFELINE_HEADER_Y),
				at(LIFELINE_2_X + 350, LIFELINE_HEADER_Y)));

		/* assert */
		assertThat(lifelineEP1, isAt(isNear(LIFELINE_1_X), not(LIFELINE_HEADER_Y)));
		if (ctrlPressed) {
			assertThat(lifelineEP2, isAt(isNear(LIFELINE_2_X + 300), not(LIFELINE_HEADER_Y)));
		} else {
			assertThat(lifelineEP2, isAt(isNear(LIFELINE_2_X), not(LIFELINE_HEADER_Y)));
		}
		assertThat(lifelineEP3, isAt(isNear(LIFELINE_3_X), not(LIFELINE_HEADER_Y)));
	}

}
