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

import static org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager.MODIFIER_NO_SNAPPING;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.sized;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.isNear;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.function.Function;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.LifelineBodyGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Integration test cases for the {@link LifelineBodyGraphicalNodeEditPolicy}
 * class's message re-connection behaviour.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("restriction")
@ModelResource("two-lifelines.di")
@Maximized
@RunWith(Parameterized.class)
public class MessageSnappingUITest extends AbstractGraphicalEditPolicyUITest {
	// Horizontal position of the first lifeline's body
	private static final int LL1_BODY_X = 121;

	// Horizontal position of the second lifeline's body
	private static final int LL2_BODY_X = 281;

	private static final boolean EXEC_START = true;
	private static final int EXEC_START_Y = 145;

	private static final boolean EXEC_FINISH = false;
	private static final int EXEC_HEIGHT = 60;

	private final EditorFixture.Modifiers modifiers;
	private final Function<Matcher<?>, Matcher<?>> modifiersMatcherFunction;
	private ExecutionSpecification exec;
	private int execTop;
	private int execBottom;

	/**
	 * Initializes me.
	 *
	 * @param withSnap
	 *            whether to allow snapping ({@code true}) or suppress it
	 *            ({@code false})
	 * @param snapString
	 *            a string representation of {@code withSnap}
	 */
	public MessageSnappingUITest(boolean withSnap, String snapString) {
		super();

		this.modifiers = withSnap ? editor.unmodified() : editor.modifierKey(MODIFIER_NO_SNAPPING);
		modifiersMatcherFunction = withSnap ? CoreMatchers::is : CoreMatchers::not;
	}

	@Test
	public void createSyncCallMessage() {
		EditPart messageEP = editor.with(modifiers,
				() -> createConnection(SequenceElementTypes.Sync_Message_Edge,
						at(LL1_BODY_X, withinMagnet(EXEC_START)),
						at(LL2_BODY_X, withinMagnet(EXEC_START))));

		// The receiving end snaps to the exec start and the sending end matches
		assertThat(messageEP, withModifiers(runs(LL1_BODY_X, execTop, LL2_BODY_X, execTop, 1)));

		// The message receive event starts the execution
		Message message = (Message) messageEP.getAdapter(EObject.class);
		assertThat(exec.getStart(), withModifiers(is(message.getReceiveEvent())));
	}

	@Test
	public void createAsyncCallMessage() {
		EditPart messageEP = editor.with(modifiers,
				() -> createConnection(SequenceElementTypes.Async_Message_Edge, at(LL1_BODY_X, 120),
						at(LL2_BODY_X, withinMagnet(EXEC_START))));

		// The receiving end snaps to the exec start. The sending end doesn't match
		assertThat(messageEP, withModifiers(runs(LL1_BODY_X, 120, LL2_BODY_X, execTop, 1)));

		// The message receive event starts the execution
		Message message = (Message) messageEP.getAdapter(EObject.class);
		assertThat(exec.getStart(), withModifiers(is(message.getReceiveEvent())));
	}

	@Test
	public void createReplyMessage() {
		EditPart messageEP = editor.with(modifiers,
				() -> createConnection(SequenceElementTypes.Reply_Message_Edge,
						at(LL2_BODY_X, withinMagnet(EXEC_FINISH)),
						at(LL1_BODY_X, withinMagnet(EXEC_FINISH))));

		// The sending end snaps to the exec start and the receiving end matches
		assertThat(messageEP, withModifiers(runs(LL2_BODY_X, execBottom, LL1_BODY_X, execBottom, 1)));

		// The message send event finishes the execution
		Message message = (Message) messageEP.getAdapter(EObject.class);
		assertThat(exec.getFinish(), withModifiers(is(message.getSendEvent())));
	}

	@Test
	public void moveSyncCallMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Sync_Message_Edge, at(LL1_BODY_X, 120),
				at(LL2_BODY_X, 120));
		assumeThat(messageEP, runs(LL1_BODY_X, 120, LL2_BODY_X, 120));

		// The receiving end snaps to the exec start and the sending end matches
		final int midMessage = (LL1_BODY_X + LL2_BODY_X) / 2;
		editor.with(modifiers,
				() -> editor.moveSelection(at(midMessage, 120), at(midMessage, withinMagnet(EXEC_START))));
		assertThat(messageEP, withModifiers(runs(LL1_BODY_X, execTop, LL2_BODY_X, execTop, 1)));
	}

	@Ignore("Logical model not calculating bottom of execution correctly.")
	@Test
	public void moveReplyMessage() {
		EditPart messageEP = createConnection(SequenceElementTypes.Reply_Message_Edge, at(LL2_BODY_X, 240),
				at(LL1_BODY_X, 240));
		assumeThat(messageEP, runs(LL2_BODY_X, 240, LL1_BODY_X, 240));

		// The sending end snaps to the exec start and the receiving end matches
		final int midMessage = (LL1_BODY_X + LL2_BODY_X) / 2;
		editor.with(modifiers,
				() -> editor.moveSelection(at(midMessage, 240), at(midMessage, withinMagnet(EXEC_FINISH))));
		assertThat(messageEP, withModifiers(runs(LL2_BODY_X, execBottom, LL1_BODY_X, execBottom, 1)));
	}

	/**
	 * Verify that magnets are updated when the size of an execution occurrence
	 * changes.
	 */
	@Test
	public void magnetUpdatesOnSize() {
		assumeThat("Only makes sense with snapping enabled", "magnets suppressed",
				withModifiers(is("magnets suppressed")));

		// First, extend the bottom edge of the execution specification. Have to
		// subtract 1 to grab on (inside-ish) the actual bottom edge
		editor.moveSelection(at(LL2_BODY_X, execBottom - 1), at(LL2_BODY_X, execBottom + 100));

		int newBottomY = getBottom(getLastCreatedEditPart());
		assumeThat("Execution not stretched", newBottomY, not(isNear(execBottom, 5)));

		EditPart messageEP = editor.with(modifiers,
				() -> createConnection(SequenceElementTypes.Reply_Message_Edge, //
						at(LL2_BODY_X, withinMagnet(newBottomY, EXEC_FINISH)),
						at(LL1_BODY_X, withinMagnet(newBottomY, EXEC_FINISH))));
		assertThat("No snap: infer that magnet not moved", messageEP,
				runs(LL2_BODY_X, newBottomY, LL1_BODY_X, newBottomY, 1));
	}

	/**
	 * Verify that magnets are updated when the location of an execution occurrence
	 * changes.
	 */
	@Test
	public void magnetUpdatesOnLocation() {
		assumeThat("Only makes sense with snapping enabled", "magnets suppressed",
				withModifiers(is("magnets suppressed")));

		// First, move the execution specification down. The edit-part doesn't (yet)
		// have a policy for moving it (only for resize) so be direct about it instead
		// of automating the selection tool
		EditPart execEP = getLastCreatedEditPart();
		Node execView = (Node) execEP.getModel();
		Location location = (Location) execView.getLayoutConstraint();
		SetBoundsCommand command = new SetBoundsCommand(editor.getDiagramEditPart().getEditingDomain(),
				"Move execution down", new EObjectAdapter(execView),
				at(location.getX(), location.getY() + 100));
		editor.getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack()
				.execute(GMFtoGEFCommandWrapper.wrap(command));

		int newTopY = execTop + 100;

		EditPart messageEP = editor.with(modifiers,
				() -> createConnection(SequenceElementTypes.Reply_Message_Edge, //
						at(LL2_BODY_X, withinMagnet(newTopY, EXEC_START)),
						at(LL1_BODY_X, withinMagnet(newTopY, EXEC_START))));
		assertThat("No snap: infer that magnet not moved", messageEP,
				runs(LL2_BODY_X, newTopY, LL1_BODY_X, newTopY, 1));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] { //
				{ true, "snap to magnet" }, //
				{ false, "suppress snapping" }, //
		});
	}

	@Before
	public void createExecutionSpecification() {
		EditPart exec = createShape(SequenceElementTypes.Behavior_Execution_Shape,
				at(LL2_BODY_X, EXEC_START_Y), sized(0, EXEC_HEIGHT));
		assumeThat("Execution specification not created", exec, notNullValue());

		this.exec = (ExecutionSpecification) exec.getAdapter(EObject.class);

		// Get the top and bottom of the execution as realized in the diagram editor,
		// which may differ slightly from the expectation in a platform-dependent way
		this.execTop = getTop(exec);
		this.execBottom = getBottom(exec);
	}

	@SuppressWarnings("unchecked")
	<T> Matcher<T> withModifiers(Matcher<T> matcher) {
		return (Matcher<T>) modifiersMatcherFunction.apply(matcher);
	}

	int withinMagnet(boolean execStart) {
		return withinMagnet(execStart ? execTop : execBottom, execStart);
	}

	int withinMagnet(int y, boolean execStart) {
		return execStart ? y - 9 : y + 9;
	}

	static int getTop(EditPart editPart) {
		IFigure figure = ((GraphicalEditPart) editPart).getFigure();
		Rectangle bounds = figure.getBounds().getCopy();
		figure.getParent().translateToAbsolute(bounds);
		return bounds.y();
	}

	static int getBottom(EditPart editPart) {
		IFigure figure = ((GraphicalEditPart) editPart).getFigure();
		Rectangle bounds = figure.getBounds().getCopy();
		figure.getParent().translateToAbsolute(bounds);
		return bounds.bottom();
	}
}
