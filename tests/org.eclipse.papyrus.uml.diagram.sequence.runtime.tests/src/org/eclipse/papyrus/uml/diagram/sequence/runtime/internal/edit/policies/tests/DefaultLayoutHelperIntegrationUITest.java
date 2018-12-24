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

import static java.util.Collections.singletonList;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.is;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers.isPoint;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;

import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.util.NotationSwitch;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies.tests.DefaultLayoutHelperIntegrationUITest.PerEditPartTests;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.GEFMatchers;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.EditorFixture;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.uml2.uml.Element;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParameters;
import org.junit.runners.parameterized.TestWithParameters;

/**
 * An integration test for the {@code DefaultLayoutHelper}, that it correctly computes the locations of
 * visuals in the diagram.
 */
@RunWith(PerEditPartTests.class)
public class DefaultLayoutHelperIntegrationUITest {

	/** Some Linux environments are off by 1 in a lot of test scenarios. */
	@ClassRule
	public static final TestRule TOLERANCE = GEFMatchers.defaultTolerance(1, Platform.OS_LINUX);

	@ClassRule
	public static final EditorFixture EDITOR = new EditorFixture(DefaultLayoutHelperIntegrationUITest.class,
			"kitchen-sink.di");

	@Parameter
	public Supplier<MElement<? extends Element>> elementSupplier;

	private MElement<? extends Element> element;

	/**
	 * Initializes me.
	 */
	public DefaultLayoutHelperIntegrationUITest() {
		super();
	}

	@Test
	public void modelComputesCorrectY() {
		// Get what the layout helper thinks the top and bottom of the element are
		OptionalInt _top = element.getTop();
		OptionalInt _bottom = element.getBottom();

		if (!_top.isPresent() || !_bottom.isPresent()) {
			if (!_top.isPresent()) {
				fail("No top");
			}
			if (!_bottom.isPresent()) {
				fail("No bottom");
			}
		}

		int top = _top.getAsInt();
		int bottom = _bottom.getAsInt();
		int height = bottom - top;

		// Get the notation view
		Optional<? extends EObject> view = element.getDiagramView();
		if (!view.isPresent()) {
			// MOccurrences (and only MOccurrences) aren't supposed to have views
			assertThat("Element that is not an MOccurrence has no view", element,
					instanceOf(MOccurrence.class));
			return;
		}

		// Compare the view's extent as GEF knows it
		String failure = (String)new NotationSwitch() {
			@Override
			public Object caseShape(Shape shape) {
				return verify(GEFMatchers.Figures.isBounded(anything(), is(top), anything(), is(height)),
						getFigure(shape));
			}

			@Override
			public Object caseEdge(Edge edge) {
				return verify(GEFMatchers.Figures.runs(isPoint(anything(), is(top)),
						isPoint(anything(), is(bottom))), getFigure(edge));
			}

			@Override
			public Object caseIdentityAnchor(IdentityAnchor anchor) {
				if (top != bottom) {
					return String.format("Different top and bottom: %d ≠ %d", top, bottom);
				}

				Edge edge = (Edge)anchor.eContainer();
				switch (anchor.eContainmentFeature().getFeatureID()) {
					case NotationPackage.EDGE__SOURCE_ANCHOR:
						return verify(GEFMatchers.Figures.runs(isPoint(anything(), is(top)), anything()),
								getFigure(edge));
					case NotationPackage.EDGE__TARGET_ANCHOR:
						return verify(GEFMatchers.Figures.runs(anything(), isPoint(anything(), is(bottom))),
								getFigure(edge));
				}

				return null;
			}
		}.doSwitch(view.get());

		if (failure != null) {
			fail(failure);
		}
	}

	//
	// Test framework
	//

	static MInteraction getInteraction() {
		return MInteraction.getInstance(EDITOR.getInteraction(), EDITOR.getSequenceDiagram().orElse(null));
	}

	@Before
	public void resolveElement() {
		element = elementSupplier.get();
	}

	@After
	public void cleanUp() {
		element = null;
		elementSupplier = null;
	}

	static <T> String verify(Matcher<? super T> matcher, T subject) {
		String result = null;

		if (!matcher.matches(subject)) {
			StringDescription mismatch = new StringDescription();
			matcher.describeMismatch(subject, mismatch);
			mismatch.appendText(" (");
			mismatch.appendDescriptionOf(matcher);
			mismatch.appendText(")");
			result = mismatch.toString();
		}

		return result;
	}

	static IFigure getFigure(View view) {
		EditPart ep = (EditPart)EDITOR.getDiagramEditPart().getViewer().getEditPartRegistry().get(view);
		return (ep instanceof GraphicalEditPart) ? ((GraphicalEditPart)ep).getFigure() : null;
	}

	//
	// Nested types
	//

	public static class PerEditPartTests extends Suite {

		private List<Runner> children = new ArrayList<>();

		public PerEditPartTests(Class<?> klass) throws InitializationError {
			super(klass, Collections.emptyList());

			ModelFixture model = new ModelFixture("kitchen-sink.uml");
			Description desc = Description.createSuiteDescription(klass);
			Statement parametersGathering = new Statement() {

				@Override
				public void evaluate() throws Throwable {
					try {
						MInteraction interaction = MInteraction.getInstance(model.getInteraction());
						createChildrenWithParameters(interaction);
					} catch (InitializationError e) {
						throw e.getCause();
					}
				}
			};

			try {
				model.apply(parametersGathering, desc).evaluate();
			} catch (Throwable e) {
				throw new InitializationError(e);
			}
		}

		@Override
		protected List<Runner> getChildren() {
			return children;
		}

		protected void createChildrenWithParameters(MInteraction interaction) throws InitializationError {
			TestClass testClass = getTestClass();

			// Don't assert the root interaction object because the diagram has no bounds
			for (Iterator<EObject> iter = ((EObject)interaction).eAllContents(); iter.hasNext();) {
				@SuppressWarnings("unchecked")
				MElement<? extends Element> next = (MElement<? extends Element>)iter.next();
				String uriFragment = EcoreUtil.getURI(next.getElement()).fragment();

				Supplier<MElement<?>> translocator = () -> {
					Element uml = (Element)EDITOR.getModel().eResource().getEObject(uriFragment);
					return getInteraction().getElement(uml).get();
				};

				String name = next.toString();
				TestWithParameters test = new TestWithParameters("[" + name + "]", testClass,
						singletonList(translocator));

				children.add(new BlockJUnit4ClassRunnerWithParameters(test));
			}
		}
	}

}
