package org.eclipse.papyrus.uml.interaction.model.tests.creation.padding;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.tests.ModelEditFixture;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@ModelResource({"two-lifelines.uml", "two-lifelines.notation" })
public class CreateLifelinePaddingTest {

	@Rule
	public final ModelEditFixture model = new ModelEditFixture();

	private MInteraction interaction;

	private int ll1Left;

	private int ll1Right;

	private int ll2Left;

	private int ll2Right;

	private MInteraction interaction() {
		if (interaction == null) {
			interaction = model.getMInteraction();
		}
		return interaction;
	}

	private void execute(Command command) {
		model.execute(command);
		/* force reinit after change */
		interaction = null;
	}

	@Before
	public void addTwoLifelines() {
		execute(interaction().getLifelines().get(0).remove());
		execute(interaction().getLifelines().get(0).remove());
		execute(interaction().addLifeline(10, 500));
		execute(interaction().addLifeline(120, 500));
		ll1Left = interaction().getLifelines().get(0).getLeft().getAsInt();
		ll1Right = interaction().getLifelines().get(0).getRight().getAsInt();
		ll2Left = interaction().getLifelines().get(1).getLeft().getAsInt();
		ll2Right = interaction().getLifelines().get(1).getRight().getAsInt();
	}

	@Test
	public void insertToTheRight_paddingFineAlready() {
		/* setup */
		CreationCommand<Lifeline> command = interaction().addLifeline(300, 500);

		/* act */
		execute(command);

		/* assert */
		assertEquals(ll1Left, interaction().getLifelines().get(0).getLeft().getAsInt());
		assertEquals(ll1Right, interaction().getLifelines().get(0).getRight().getAsInt());
		assertEquals(ll2Left, interaction().getLifelines().get(1).getLeft().getAsInt());
		assertEquals(ll2Right, interaction().getLifelines().get(1).getRight().getAsInt());
		assertEquals(ll2Left + (300 - 120), interaction().getLifelines().get(2).getLeft().getAsInt());
		assertEquals(ll2Right + (300 - 120), interaction().getLifelines().get(2).getRight().getAsInt());
	}

	@Test
	public void insertToTheRight_paddingTooSmall() {
		/* setup */
		CreationCommand<Lifeline> command = interaction().addLifeline(225, 500);

		/* act */
		execute(command);

		/* assert */
		assertEquals(ll1Left, interaction().getLifelines().get(0).getLeft().getAsInt());
		assertEquals(ll1Right, interaction().getLifelines().get(0).getRight().getAsInt());
		assertEquals(ll2Left, interaction().getLifelines().get(1).getLeft().getAsInt());
		assertEquals(ll2Right, interaction().getLifelines().get(1).getRight().getAsInt());
		assertEquals(ll2Left + (225 - 120) + 5, interaction().getLifelines().get(2).getLeft().getAsInt());
		assertEquals(ll2Right + (225 - 120) + 5, interaction().getLifelines().get(2).getRight().getAsInt());
	}

	@Test
	public void insertOnTop_secondLifeline() {
		/* setup */
		CreationCommand<Lifeline> command = interaction().addLifeline(140, 500);

		/* act */
		execute(command);

		/* assert */
		assertEquals(ll1Left, interaction().getLifelines().get(0).getLeft().getAsInt());
		assertEquals(ll1Right, interaction().getLifelines().get(0).getRight().getAsInt());
		assertEquals(ll2Left, interaction().getLifelines().get(1).getLeft().getAsInt());
		assertEquals(ll2Right, interaction().getLifelines().get(1).getRight().getAsInt());
		assertEquals(ll2Left + 110, interaction().getLifelines().get(2).getLeft().getAsInt());
		assertEquals(ll2Right + 110, interaction().getLifelines().get(2).getRight().getAsInt());
	}

	@Test
	public void insertOnTop_firstLifeline() {
		/* setup */
		CreationCommand<Lifeline> command = interaction().addLifeline(30, 500);

		/* act */
		execute(command);

		/* assert */
		assertEquals(ll1Left, interaction().getLifelines().get(0).getLeft().getAsInt());
		assertEquals(ll1Right, interaction().getLifelines().get(0).getRight().getAsInt());
		assertEquals(ll1Left + 110, interaction().getLifelines().get(2).getLeft().getAsInt());
		assertEquals(ll1Right + 110, interaction().getLifelines().get(2).getRight().getAsInt());
		assertEquals(ll2Left + 110, interaction().getLifelines().get(1).getLeft().getAsInt());
		assertEquals(ll2Right + 110, interaction().getLifelines().get(1).getRight().getAsInt());
	}

	@Test
	public void insertInBetween() {
		/* setup */
		CreationCommand<Lifeline> command = interaction().addLifeline(115, 500);

		/* act */
		execute(command);

		/* assert */
		assertEquals(ll1Left, interaction().getLifelines().get(0).getLeft().getAsInt());
		assertEquals(ll1Right, interaction().getLifelines().get(0).getRight().getAsInt());
		assertEquals(ll1Left + 110, interaction().getLifelines().get(2).getLeft().getAsInt());
		assertEquals(ll1Right + 110, interaction().getLifelines().get(2).getRight().getAsInt());
		assertEquals(ll2Left + 110, interaction().getLifelines().get(1).getLeft().getAsInt());
		assertEquals(ll2Right + 110, interaction().getLifelines().get(1).getRight().getAsInt());
	}

	@Test
	public void insertToTheLeft() {
		/* setup */
		CreationCommand<Lifeline> command = interaction().addLifeline(5, 500);

		/* act */
		execute(command);

		/* assert */
		assertEquals(ll1Left - 5, interaction().getLifelines().get(2).getLeft().getAsInt());
		assertEquals(ll1Right - 5, interaction().getLifelines().get(2).getRight().getAsInt());
		assertEquals(ll1Left + 110, interaction().getLifelines().get(0).getLeft().getAsInt());
		assertEquals(ll1Right + 110, interaction().getLifelines().get(0).getRight().getAsInt());
		assertEquals(ll2Left + 110, interaction().getLifelines().get(1).getLeft().getAsInt());
		assertEquals(ll2Right + 110, interaction().getLifelines().get(1).getRight().getAsInt());
	}

}
