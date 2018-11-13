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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.MessageSort;
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration test cases for creation of delete messages.
 */
@ModelResource("three-lifelines.di")
@Maximized
public class DeleteMessageCreationUITest extends AbstractGraphicalEditPolicyUITest {

	@Rule
	public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture("Lifeline1")
	private Lifeline lifeline1;

	@AutoFixture
	private EditPart lifeline1EP;

	@AutoFixture("Lifeline2")
	private Lifeline lifeline2;

	@AutoFixture
	private EditPart lifeline2EP;

	@AutoFixture("Lifeline3")
	private Lifeline lifeline3;

	@AutoFixture
	private EditPart lifeline3EP;

	/**
	 * Initializes me.
	 */
	public DeleteMessageCreationUITest() {
		super();
	}

	/**
	 * Verify that we can create a delete message to delete a lifeline after that lifeline has received
	 * another message.
	 */
	@Test
	public void createDeleteAfterMessage() {
		createMessage(MessageSort.ASYNCH_CALL_LITERAL, lifeline2EP, lifeline3EP, 50);
		createMessage(MessageSort.DELETE_MESSAGE_LITERAL, lifeline2EP, lifeline3EP, 100);
	}

	/**
	 * Verify that we can send a delete message from a lifeline before that lifeline has sent another message.
	 */
	@Test
	public void createDeleteBeforeMessage() {
		createMessage(MessageSort.ASYNCH_CALL_LITERAL, lifeline2EP, lifeline3EP, 100);
		createMessage(MessageSort.DELETE_MESSAGE_LITERAL, lifeline2EP, lifeline1EP, 50);
	}

	/**
	 * Verify that we cannot create a delete message to delete a lifeline before that lifeline has received
	 * another message.
	 */
	@Test
	public void attemptDeleteBeforeMessage() {
		createMessage(MessageSort.ASYNCH_CALL_LITERAL, lifeline2EP, lifeline3EP, 100);

		EditPart delete = null;
		try {
			delete = createMessage(MessageSort.DELETE_MESSAGE_LITERAL, lifeline2EP, lifeline3EP, 50);
		} catch (AssertionError e) {
			// Expected
		}
		assertThat("Delete message was created", delete, nullValue());
	}

	/**
	 * Verify that we can move a delete message up.
	 */
	@Test
	public void moveDeleteMessageUp() {
		moveDeleteMessage(-50);
	}

	private void moveDeleteMessage(int deltaY) {
		EditPart delete = createMessage(MessageSort.DELETE_MESSAGE_LITERAL, lifeline2EP, lifeline3EP, 100);

		PointList deleteGeom = getPoints(delete);
		Point grabMessageAt = deleteGeom.getMidpoint();
		Point dropMessageAt = grabMessageAt.getTranslated(0, deltaY);

		editor.moveSelection(grabMessageAt, dropMessageAt);

		deleteGeom.translate(0, deltaY);

		assertThat("Delete message not moved", delete,
				runs(isPoint(deleteGeom.getFirstPoint()), isPoint(deleteGeom.getLastPoint())));
	}

	/**
	 * Verify that we can move a delete message down.
	 */
	@Test
	public void moveDeleteMessageDown() {
		moveDeleteMessage(+50);
	}

	//
	// Test framework
	//

	EditPart createMessage(MessageSort sort, EditPart from, EditPart to, int y) {
		IElementType type;

		switch (sort) {
			case ASYNCH_CALL_LITERAL:
				type = SequenceElementTypes.Async_Message_Edge;
				break;
			case DELETE_MESSAGE_LITERAL:
				type = SequenceElementTypes.Delete_Message_Edge;
				break;
			default:
				throw new IllegalArgumentException("Message sort " + sort);
		}

		Point send = getCenter(from).getTranslated(0, y);
		Point recv = getCenter(to).getTranslated(0, y);
		return createConnection(type, send, recv);
	}

}
