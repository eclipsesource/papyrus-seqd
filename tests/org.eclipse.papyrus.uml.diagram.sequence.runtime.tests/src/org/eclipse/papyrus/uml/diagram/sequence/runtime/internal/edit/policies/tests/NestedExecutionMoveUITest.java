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

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.is;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.EditParts.isAt;
import static org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes.LIFELINE_BODY;
import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.gt;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture.VisualID;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration tests for the moving of nested executions to other lifelines and around the same lifeline.
 */
@ModelResource("nested-executions.di")
@Maximized
public class NestedExecutionMoveUITest extends AbstractGraphicalEditPolicyUITest {

	@Rule
	public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture("Lifeline2")
	@VisualID(LIFELINE_BODY)
	private EditPart lifeline2;

	@AutoFixture("Lifeline3")
	@VisualID(LIFELINE_BODY)
	private EditPart lifeline3;

	@AutoFixture
	private Rectangle lifeline3Geom;

	@AutoFixture
	private EditPart request;

	@AutoFixture
	private PointList requestGeom;

	@AutoFixture
	private EditPart exec1;

	@AutoFixture
	private Rectangle exec1Geom;

	@AutoFixture
	private EditPart exec2;

	@AutoFixture
	private Rectangle exec2Geom;

	@AutoFixture
	private EditPart exec3;

	@AutoFixture
	private Rectangle exec3Geom;

	@AutoFixture
	private EditPart exec4;

	@AutoFixture
	private Rectangle exec4Geom;

	/**
	 * Initializes me.
	 */
	public NestedExecutionMoveUITest() {
		super();
	}

	@Test
	public void moveExecutionToNested() {
		Point grabAt = exec4Geom.getCenter();
		Point dropAt = exec3Geom.getLeft().getTranslated(-EXEC_WIDTH, 0);

		editor.select(grabAt);
		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

		autoFixtures.refresh();

		assertThat("Wrong parent", exec4.getParent(), sameInstance(exec3));
		assertThat(exec4, isAt(is(exec3Geom.x() + EXEC_WIDTH / 2), gt(exec3Geom.y())));
	}

	@Test
	public void retargetRequest() {
		Point grabAt = requestGeom.getLastPoint();
		Point dropAt = new Point(lifeline3Geom.getCenter().x(), grabAt.y());

		editor.select(requestGeom.getMidpoint());
		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

		autoFixtures.refresh();

		assertThat("Wrong parent", exec3.getParent(), sameInstance(exec2));
		assertThat("Wrong parent", exec2.getParent(), sameInstance(exec1));
		assertThat("Wrong parent", exec1.getParent(), sameInstance(lifeline3));

		assertThat("Wrong nesting", exec3, isAt(gt(exec2Geom.x()), gt(exec2Geom.y())));
		assertThat("Wrong nesting", exec2, isAt(gt(exec1Geom.x()), gt(exec1Geom.y())));
	}

	@Test
	public void moveNestedExecutionToOtherLifeline() {
		int previousExec3Y = exec3Geom.y();
		Point grabAt = exec3Geom.getCenter();
		Point dropAt = new Point(lifeline3Geom.getCenter().x(), grabAt.y());

		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

		autoFixtures.refresh();

		assertThat("Wrong parent", exec3.getParent(), sameInstance(lifeline3));
		assertThat("Wrong nesting", exec3,
				isAt(is(lifeline3Geom.getCenter().x() - (EXEC_WIDTH / 2)), is(previousExec3Y)));
	}

	@Test
	public void moveNestedExecutionToSpanOtherExecution() {
		Point previousExec4Location = exec4Geom.getTopLeft();
		Point grabAt = exec3Geom.getCenter();
		Point dropAt = exec4Geom.getBottom(); // Optimal placement for the ratio of their sizes

		editor.with(editor.allowSemanticReordering(), () -> editor.moveSelection(grabAt, dropAt));

		autoFixtures.refresh();

		assertThat("Wrong parent", exec3.getParent(), sameInstance(lifeline2));
		assertThat("Wrong parent", exec4.getParent(), sameInstance(exec3));
		assertThat("Wrong nesting", exec4,
				isAt(gt(previousExec4Location.x()), is(previousExec4Location.y())));
	}

	@Test
	public void moveNestedExecutionToSpanExecutionOnOtherLifeline() {
		// Create a new execution on lifeline3 vertically in the middle-ish of an existing execution
		// on lifeline3
		Point newExecAt = new Point(lifeline3Geom.getCenter().x(), exec3Geom.getCenter().y());
		EditPart newExecEP = createShape(SequenceElementTypes.Behavior_Execution_Shape, newExecAt, null);
		ExecutionSpecification newExec = (ExecutionSpecification)newExecEP.getAdapter(EObject.class);

		autoFixtures.refresh(); // Account for nudging
		Point previousNewExecLocation = getBounds(newExecEP).getTopLeft();

		// Grab the middle exec of the existing stack near the bottom where the most nested one isn't
		Point grabAt = exec2Geom.getBottom().getTranslated(0, -10);
		// And drop it onto lifeline3 over top of the new exec
		Point dropAt = new Point(lifeline3Geom.getCenter().x(), grabAt.y());

		editor.with(editor.allowSemanticReordering(), () -> editor.moveSelection(grabAt, dropAt));

		// The edit-parts are re-created by the notation changes
		autoFixtures.refresh();
		newExecEP = DiagramEditPartsUtil.getChildByEObject(newExec, editor.getDiagramEditPart(), false);

		assertThat("Wrong parent", newExecEP.getParent(), sameInstance(exec3));
		assertThat("Wrong nesting", newExecEP,
				isAt(gt(previousNewExecLocation.x()), is(previousNewExecLocation.y())));
	}

}
