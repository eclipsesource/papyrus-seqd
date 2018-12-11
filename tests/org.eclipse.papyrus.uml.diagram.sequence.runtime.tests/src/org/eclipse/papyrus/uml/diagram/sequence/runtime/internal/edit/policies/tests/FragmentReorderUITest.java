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

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixtureRule;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.Maximized;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.util.LogicalModelOrdering;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.MessageEnd;
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration tests for the semantic reordering of fragments.
 */
@ModelResource("FragmentReorder.di")
@Maximized
public class FragmentReorderUITest extends AbstractGraphicalEditPolicyUITest {

	@Rule
	public final AutoFixtureRule autoFixtures = new AutoFixtureRule(this);

	@AutoFixture("AsyncMessage1")
	private EditPart async1;

	@AutoFixture
	private PointList async1Geom;

	@AutoFixture("AsyncMessage2")
	private EditPart async2;

	@AutoFixture
	private PointList async2Geom;

	@AutoFixture("CreateMessage1")
	private EditPart create;

	@AutoFixture
	private PointList createGeom;

	@AutoFixture("1s")
	private MessageEnd async1Send;

	@AutoFixture("1r")
	private MessageEnd async1Recv;

	@AutoFixture("2s")
	private MessageEnd async2Send;

	@AutoFixture("2r")
	private MessageEnd async2Recv;

	@AutoFixture("cs")
	private MessageEnd createSend;

	@AutoFixture("cr")
	private MessageEnd createRecv;

	/**
	 * Initializes me.
	 */
	public FragmentReorderUITest() {
		super();
	}

	/**
	 * Regression test for <a href="https://github.com/eclipsesource/papyrus-seqd/issues/427">issue #427</a>.
	 */
	@Test
	public void reorderAsyncMessageEnds() {
		Point grabAt = async1Geom.getMidpoint();
		// Drop it mid-way between the create message and AsyncMessage2
		Point dropAt = new Point(grabAt.x(),
				(createGeom.getFirstPoint().y() + async2Geom.getFirstPoint().y()) / 2);

		editor.select(grabAt);
		editor.with(editor.allowSemanticReordering(), () -> editor.drag(grabAt, dropAt));

		autoFixtures.refresh();

		// Verify graph dependencies match the new semantic order
		editor.verifyGraph().verifyDependencies(from -> {
			switch (from) {
				case "cs":
					return asList("cr", "1r", "2s");
				case "1s":
					return asList("1r", "2s");
				case "1r":
					return asList("2s");
				case "2s":
					return asList("2r");
				default:
					return null;
			}
		});

		List<Element> expectedOrder = Arrays.asList(createSend, createRecv, async1Send, async1Recv,
				async2Send, async2Recv);

		// Verify the semantic order
		MInteraction mInteraction = MInteraction.getInstance(editor.getSequenceDiagram().get());
		Function<Optional<MElement<? extends Element>>, MElement<? extends Element>> getter = Optional::get;
		Function<Element, MElement<? extends Element>> mElement = getter.compose(mInteraction::getElement);
		List<Element> semanticOrder = Stream
				.of(async2Recv, async2Send, async1Recv, async1Send, createRecv, createSend)
				.sorted(Comparator.comparing(mElement, LogicalModelOrdering.semantically()))
				.collect(Collectors.toList());
		assertThat("Wrong semantic ordering", semanticOrder, equalTo(expectedOrder));

		// Verify the element order in the model
		Interaction interaction = editor.getInteraction();
		List<Element> modelOrder = Stream
				.of((InteractionFragment)async2Recv, async2Send, async1Recv, async1Send, createRecv,
						createSend)
				.sorted(Comparator.comparing(interaction.getFragments()::indexOf))
				.collect(Collectors.toList());
		assertThat("Wrong model ordering", modelOrder, equalTo(expectedOrder));
	}

}
