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
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture.at;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.junit.Test;

/**
 * UI tests for Execution and nested executions move
 */
@ModelResource("nested-execution.di")
@Maximized
public class ExecutionMoveUITest extends AbstractGraphicalEditPolicyUITest {

	private static final int LIFELINE_1__BODY_X = 155;

	private static final int LIFELINE_3__BODY_X = 426;

	private static final int NESTED_EXEC_OFFSET = 19;

	private static final int NESTED_EXEC_HEIGHT = 40;

	@Test
	public void moveSyncMessageEnd() {
		/* initial state - check message end position */
		EditPart syncEP = findEditPart("nested-execution::interaction1::sync", true);
		int sync_y = getTargetY(syncEP);

		EditPart replyEP = findEditPart("nested-execution::interaction1::reply", true);
		int reply_y = getSourceY(replyEP);

		EditPart exec1EP = findEditPart("nested-execution::interaction1::exec1", false);
		assumeThat(exec1EP,
				isBounded(LIFELINE_3__BODY_X - EXEC_WIDTH / 2, sync_y, EXEC_WIDTH, reply_y - sync_y));

		EditPart nestedExecEP = findEditPart("nested-execution::interaction1::nestedExec", false);
		assumeThat(nestedExecEP,
				isBounded(LIFELINE_3__BODY_X, sync_y + NESTED_EXEC_OFFSET, EXEC_WIDTH, NESTED_EXEC_HEIGHT));

		/* move message end on Lifeline1 */
		editor.with(editor.allowSemanticReordering(),
				() -> editor.moveSelection(at(LIFELINE_3__BODY_X - EXEC_WIDTH / 2, sync_y),
						at(LIFELINE_1__BODY_X, sync_y)));

		/*
		 * check result - execution and nested execution have moved to the right place
		 */
		exec1EP = findEditPart("nested-execution::interaction1::exec1", false);
		assertThat(exec1EP,
				isBounded(LIFELINE_1__BODY_X - EXEC_WIDTH / 2, sync_y, EXEC_WIDTH, reply_y - sync_y));

		nestedExecEP = findEditPart("nested-execution::interaction1::nestedExec", false);
		assumeThat(nestedExecEP,
				isBounded(LIFELINE_1__BODY_X, sync_y + NESTED_EXEC_OFFSET, EXEC_WIDTH, NESTED_EXEC_HEIGHT));

	}

}
