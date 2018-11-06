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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isBounded;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.runs;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.util.Iterator;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.ExecutionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.LifelineHeaderEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.MessageEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.junit.Test;

/**
 * UI tests for Execution and nested executions move
 */
@SuppressWarnings("restriction")
@ModelResource("nested-execution.di")
@Maximized
public class ExecutionMoveUITest extends AbstractGraphicalEditPolicyUITest {

	private static final int LIFELINE_1__BODY_X = 155;
	private static final int LIFELINE_2__BODY_X = 288;
	private static final int LIFELINE_3__BODY_X = 426;

	private static final int SYNC_Y = 148;
	private static final int NESTED_EXEC_START_Y = SYNC_Y + 19;
	private static final int NESTED_EXEC_HEIGHT = 40;
	private static final int REPLY_Y = 232;

	private static final int EXEC_WIDTH = 10;

	@Test
	public void moveSyncMessageEnd() {
		/* initial state - check message end position */
		EditPart syncEP = requireMessage("nested-execution::interaction1::sync");
		assumeThat(syncEP, runs(LIFELINE_2__BODY_X, SYNC_Y, LIFELINE_3__BODY_X - (EXEC_WIDTH / 2), SYNC_Y));

		EditPart exec1EP = requireExecution("nested-execution::interaction1::exec1");
		assumeThat(exec1EP, isBounded(LIFELINE_3__BODY_X - EXEC_WIDTH / 2, SYNC_Y, EXEC_WIDTH, REPLY_Y - SYNC_Y));

		EditPart nestedExecEP = requireExecution("nested-execution::interaction1::nestedExec");
		assumeThat(nestedExecEP,
				isBounded(LIFELINE_3__BODY_X, NESTED_EXEC_START_Y, EXEC_WIDTH, NESTED_EXEC_HEIGHT));

		/* move message end on Lifeline1 */
		editor.with(editor.allowSemanticReordering(), () -> editor
				.moveSelection(at(LIFELINE_3__BODY_X - EXEC_WIDTH / 2, SYNC_Y), at(LIFELINE_1__BODY_X, SYNC_Y)));

		/*
		 * check result - execution and nested execution have moved to the right place
		 */
		exec1EP = requireExecution("nested-execution::interaction1::exec1");
		assertThat(exec1EP, isBounded(LIFELINE_1__BODY_X - EXEC_WIDTH / 2, SYNC_Y, EXEC_WIDTH, REPLY_Y - SYNC_Y));

		nestedExecEP = requireExecution("nested-execution::interaction1::nestedExec");
		assumeThat(nestedExecEP, isBounded(LIFELINE_1__BODY_X, NESTED_EXEC_START_Y, EXEC_WIDTH, NESTED_EXEC_HEIGHT));

	}

	private MessageEditPart requireMessage(String qualifiedName) {
		Message message = editor.getElement(qualifiedName, Message.class);
		Iterator<IGraphicalEditPart> it = DiagramEditPartsUtil
				.getChildrenByEObject(message, editor.getDiagramEditPart(), true).iterator();
		if(it.hasNext()) {
			IGraphicalEditPart ep = it.next();
			if (MessageEditPart.class.isInstance(ep)) {
				return MessageEditPart.class.cast(ep);
			}
		}
		fail("impossible to get Message edit part "+qualifiedName);
		return null;
	}

	private LifelineHeaderEditPart requireLifeline(String qualifiedName) {
		Lifeline lifeline = editor.getElement(qualifiedName, Lifeline.class);
		return (LifelineHeaderEditPart) editor.getDiagramEditPart().findEditPart(editor.getDiagramEditPart(), lifeline);
	}

	private ExecutionSpecificationEditPart requireExecution(String qualifiedName) {
		ExecutionSpecification spec = editor.getElement(qualifiedName, ExecutionSpecification.class);
		return (ExecutionSpecificationEditPart) editor.getDiagramEditPart().findEditPart(editor.getDiagramEditPart(),
				spec);
	}
}
