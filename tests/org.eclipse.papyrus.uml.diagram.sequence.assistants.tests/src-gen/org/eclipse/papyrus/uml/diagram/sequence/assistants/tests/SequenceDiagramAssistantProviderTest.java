/**
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.sequence.assistants.tests;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsFactory;
import org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider;
import org.eclipse.papyrus.uml.interaction.tests.util.AdaptableByType;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>Sequence
 * Diagram Assistant Provider</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable)
 * <em>Get Rel Types On Source</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable)
 * <em>Get Rel Types On Target</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.core.runtime.IAdaptable)
 * <em>Get Rel Types On Source And Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
@SuppressWarnings("unchecked")
public class SequenceDiagramAssistantProviderTest extends TestCase {
	// Hinted for visualization
	static final String MESSAGE_TYPE = "org.eclipse.papyrus.umldi.Message_AsynchEdge";

	// Semantic type
	static final String LIFELINE_TYPE = "org.eclipse.papyrus.uml.Lifeline";

	/**
	 * The fixture for this Sequence Diagram Assistant Provider test case. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SequenceDiagramAssistantProvider fixture = null;

	private Model model;
	private Lifeline lifeline;

	private Diagram diagram;
	private Shape lifelineView;

	private IAdaptable adaptable;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SequenceDiagramAssistantProviderTest.class);
	}

	/**
	 * Constructs a new Sequence Diagram Assistant Provider test case with the given
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public SequenceDiagramAssistantProviderTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Sequence Diagram Assistant Provider test case. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(SequenceDiagramAssistantProvider fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Sequence Diagram Assistant Provider test case.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SequenceDiagramAssistantProvider getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc --> <strong>Note</strong> that these tests must be run in
	 * an Eclipse Run-time instance because they interact with the GMF <em>Element
	 * Types Registry</em> <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DiagramAssistantsFactory.eINSTANCE.createSequenceDiagramAssistantProvider());

		getFixture().getElementTypeIDs().addAll(Arrays.asList(LIFELINE_TYPE, MESSAGE_TYPE));

		ElementTypeFilter lifelineTypeFilter = (ElementTypeFilter) getFixture().createOwnedFilter(null,
				AssistantPackage.Literals.ELEMENT_TYPE_FILTER);
		lifelineTypeFilter.setElementTypeID(LIFELINE_TYPE);

		ConnectionAssistant connection = getFixture().createConnectionAssistant();
		connection.setElementTypeID(MESSAGE_TYPE);
		connection.setSourceFilter(lifelineTypeFilter);
		connection.setTargetFilter(lifelineTypeFilter);

		model = UMLFactory.eINSTANCE.createModel();
		Interaction interaction = (Interaction) model.createOwnedType("seq",
				UMLPackage.Literals.INTERACTION);
		lifeline = interaction.createLifeline("a");

		diagram = NotationFactory.eINSTANCE.createDiagram();
		diagram.setType("LightweightSequenceDiagram");
		diagram.setElement(interaction);
		lifelineView = (Shape) diagram.createChild(NotationPackage.Literals.SHAPE);
		lifelineView.setType("Shape_Lifeline");
		lifelineView.setElement(lifeline);

		adaptable = new AdaptableByType(lifeline, lifelineView);

		// Ensure registration of the modeled element types
		ElementTypeSetConfigurationRegistry.getInstance();
		assumeThat("Modeled element types not available",
				ElementTypeRegistry.getInstance().getType(MESSAGE_TYPE), notNullValue());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	Matcher<IElementType> hasID(String id) {
		return new FeatureMatcher<IElementType, String>(is(id), "has ID", "id") {
			@Override
			protected String featureValueOf(IElementType actual) {
				return actual.getId();
			}
		};
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable)
	 * <em>Get Rel Types On Source</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesOnSource__IAdaptable() {
		List<IElementType> types = getFixture().getRelTypesOnSource(adaptable);
		assertThat(types, hasItem(hasID(MESSAGE_TYPE)));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable)
	 * <em>Get Rel Types On Target</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesOnTarget__IAdaptable() {
		List<IElementType> types = getFixture().getRelTypesOnTarget(adaptable);
		assertThat(types, hasItem(hasID(MESSAGE_TYPE)));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.core.runtime.IAdaptable)
	 * <em>Get Rel Types On Source And Target</em>}' operation. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable,
	 *      org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesOnSourceAndTarget__IAdaptable_IAdaptable() {
		List<IElementType> types = getFixture().getRelTypesOnSourceAndTarget(adaptable, adaptable);
		assertThat(types, hasItem(hasID(MESSAGE_TYPE)));
	}

} // SequenceDiagramAssistantProviderTest
