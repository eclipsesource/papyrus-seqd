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
 *
 */
package org.eclipse.papyrus.uml.interaction.model.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import junit.framework.TestCase;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>MElement</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getInteraction() <em>Interaction</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getElement() <em>Element</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getTop() <em>Top</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getBottom() <em>Bottom</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getName() <em>Name</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getOwner() <em>Get Owner</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#getDiagramView() <em>Get Diagram
 * View</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#verticalDistance(org.eclipse.papyrus.uml.interaction.model.MElement)
 * <em>Vertical Distance</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#following() <em>Following</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.model.MElement#nudge(int) <em>Nudge</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class MElementTest extends TestCase {

	/**
	 * The fixture for this MElement test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MElement<?> fixture = null;

	protected EditingDomain domain;

	protected MInteraction interaction;

	protected Interaction umlInteraction;

	protected Diagram sequenceDiagram;

	private ComposedAdapterFactory adapterFactory;

	/**
	 * Constructs a new MElement test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MElementTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this MElement test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(MElement<?> fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this MElement test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MElement<?> getFixture() {
		return fixture;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		adapterFactory = new ComposedAdapterFactory();
		adapterFactory.addAdapterFactory(new UMLItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

		domain = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack());
		ResourceSet resourceSet = domain.getResourceSet();
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			Map<String, Object> resourceFactories = resourceSet.getResourceFactoryRegistry()
					.getExtensionToFactoryMap();
			resourceFactories.put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
			resourceFactories.put("notation", new XMIResourceFactoryImpl());

			UMLPackage.eINSTANCE.eClass();
			NotationPackage.eINSTANCE.eClass();

			resourceSet.getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, true);
			resourceSet.getLoadOptions().put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, true);
		}

		URI uri = URI.createURI(MElementTest.class.getResource("AnchorsModel.uml").toExternalForm(), true);
		resourceSet.getResource(uri, true);
		Resource notation = resourceSet.getResource(uri.trimFileExtension().appendFileExtension("notation"),
				true);
		umlInteraction = findUMLElement("AnchorsModel::" + getInteractionName(), Interaction.class);
		sequenceDiagram = (Diagram)UML2Util.findEObject(notation.getContents(),
				o -> (o instanceof Diagram) && (((Diagram)o).getElement() == umlInteraction));
		interaction = MInteraction.getInstance(umlInteraction, sequenceDiagram);

		initializeFixture();
	}

	protected abstract void initializeFixture();

	protected String getInteractionName() {
		return "ExecutionSpecificationAnchors";
	}

	protected <T extends NamedElement> T findUMLElement(String qualifiedName, Class<T> type) {
		Optional<T> result = UMLUtil.findNamedElements(domain.getResourceSet(), qualifiedName).stream()
				.filter(type::isInstance).map(type::cast).findAny();
		assertThat(String.format("No element %s of type %s", qualifiedName, type.getSimpleName()), result,
				isPresent());
		return result.get();
	}
	
	protected Optional<View> findTypeInChildren(View shape, String type) {
		TreeIterator<EObject> contents = shape.eAllContents();
		while(contents.hasNext()) {
			EObject next = contents.next();
			if(View.class.isInstance(next)) {
				View view = View.class.cast(next);
				if(type.equals(view.getType())) {
					return Optional.of(view);
				}
			}
		}
		return Optional.empty();
	}
	

	protected int countTypesInChildren(View shape, String type) {
		int found = 0;
		TreeIterator<EObject> contents = shape.eAllContents();
		while(contents.hasNext()) {
			EObject next = contents.next();
			if(View.class.isInstance(next)) {
				View view = View.class.cast(next);
				if(type.equals(view.getType())) {
					found++;
				}
			}
		}
		return found;
	}

	@Override
	protected void tearDown() throws Exception {
		try {
			setFixture(null);

			interaction = null;
			sequenceDiagram = null;
			umlInteraction = null;

			adapterFactory.dispose();
			adapterFactory = null;

			ResourceSet resourceSet = domain.getResourceSet();
			for (Resource next : resourceSet.getResources()) {
				next.unload();
				next.eAdapters().clear();
			}
			resourceSet.getResources().clear();
			resourceSet.eAdapters().clear();

			domain.getCommandStack().flush();
			domain = null;
		} finally {
			super.tearDown();
		}
	}

	protected String label(EObject object) {
		if (object == null) {
			return "<null>";
		}
		IItemLabelProvider labels = (IItemLabelProvider)adapterFactory.adapt(object,
				IItemLabelProvider.class);
		return labels == null ? object.eClass().getName() : labels.getText(object);
	}

	protected Bounds getBounds(MElement<?> element) {
		assertThat(element.getDiagramView(), isPresent(instanceOf(Shape.class)));
		Shape shape = (Shape)element.getDiagramView().get();
		assertThat(shape.getLayoutConstraint(), instanceOf(Bounds.class));
		return (Bounds)shape.getLayoutConstraint();
	}

	protected <T> Matcher<Optional<T>> isPresent() {
		return new CustomTypeSafeMatcher<Optional<T>>("is present") {
			@Override
			protected boolean matchesSafely(Optional<T> item) {
				return item.isPresent();
			}
		};
	}

	protected <T> Matcher<Optional<T>> isPresent(Matcher<? super T> assertion) {
		return new TypeSafeDiagnosingMatcher<Optional<T>>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("optional that ");
				description.appendDescriptionOf(assertion);
			}

			@Override
			protected boolean matchesSafely(Optional<T> item, Description mismatchDescription) {
				boolean result = false;

				if (!item.isPresent()) {
					mismatchDescription.appendText("absent");
				} else {
					result = assertion.matches(item.get());
					if (!result) {
						assertion.describeMismatch(item.get(), mismatchDescription);
					}
				}

				return result;
			}
		};
	}

	protected Matcher<OptionalInt> isPresentInt() {
		return new CustomTypeSafeMatcher<OptionalInt>("is present") {
			@Override
			protected boolean matchesSafely(OptionalInt item) {
				return item.isPresent();
			}
		};
	}

	protected Matcher<OptionalInt> isPresent(int expected) {
		return new CustomTypeSafeMatcher<OptionalInt>("is " + expected) {
			@Override
			protected boolean matchesSafely(OptionalInt item) {
				return item.isPresent() && (item.getAsInt() == expected);
			}
		};
	}

	protected Matcher<OptionalInt> isAbsentInt() {
		return new CustomTypeSafeMatcher<OptionalInt>("is absent") {
			@Override
			protected boolean matchesSafely(OptionalInt item) {
				return !item.isPresent();
			}
		};
	}

	protected <T extends Element> Matcher<? super MElement<? extends T>> wraps(T uml) {
		return new CustomTypeSafeMatcher<MElement<? extends T>>("wraps " + label(uml)) {
			@Override
			protected boolean matchesSafely(MElement<? extends T> item) {
				return item.getElement() == uml;
			}
		};
	}

	protected Matcher<EObject> viewOf(EObject element) {
		return new CustomTypeSafeMatcher<EObject>("view of " + label(element)) {
			@Override
			protected boolean matchesSafely(EObject item) {
				return (item instanceof View) && (((View)item).getElement() == element);
			}
		};
	}

	protected Matcher<Anchor> hasId(String id) {
		return new CustomTypeSafeMatcher<Anchor>("is " + id) {
			@Override
			protected boolean matchesSafely(Anchor item) {
				return (item instanceof IdentityAnchor) && id.equals(((IdentityAnchor)item).getId());
			}
		};
	}

	protected Matcher<Command> executable() {
		return new CustomTypeSafeMatcher<Command>("executable") {
			@Override
			protected boolean matchesSafely(Command item) {
				return item.canExecute();
			}
		};
	}

	protected void execute(Command command) {
		domain.getCommandStack().execute(command);
		assertThat("Command not executed", domain.getCommandStack().getUndoCommand(), is(command));

		// Reinitialize the logical model
		interaction = MInteraction.getInstance(umlInteraction, sequenceDiagram);
		initializeFixture();
	}

	protected <T extends EObject> T create(CreationCommand<T> command) {
		execute(command);

		assertThat(command.get(), notNullValue());
		return command.get();
	}

	//
	// Tests
	//

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getInteraction()
	 * <em>Interaction</em>}' feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getInteraction()
	 * @generated NOT
	 */
	public void testGetInteraction() {
		assertThat(getFixture().getInteraction(), is(interaction));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getElement() <em>Element</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getElement()
	 * @generated NOT
	 */
	public void testGetElement() {
		assertThat(getFixture().getElement(), notNullValue());
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getTop() <em>Top</em>}' feature
	 * getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getTop()
	 * @generated NOT
	 */
	public void testGetTop() {
		fail("Must be tested by subclasses.");
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getBottom() <em>Bottom</em>}'
	 * feature getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getBottom()
	 * @generated NOT
	 */
	public void testGetBottom() {
		fail("Must be tested by subclasses.");
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getName() <em>Name</em>}' feature
	 * getter. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getName()
	 * @generated NOT
	 */
	public void testGetName() {
		Element element = getFixture().getElement();
		NamedElement named = (element instanceof NamedElement) ? (NamedElement)element : null;
		if ((named != null) && (named.getName() != null)) {
			assertThat(getFixture().getName(), is(named.getName()));
		} else {
			assertThat(getFixture().getName(), nullValue());
		}
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getOwner() <em>Get Owner</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getOwner()
	 * @generated NOT
	 */
	public void testGetOwner() {
		assertThat(getFixture().getOwner(), notNullValue());
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getDiagramView()
	 * @generated NOT
	 */
	public void testGetDiagramView() {
		// Most things have diagram views (those that don't won't call super)
		assertThat(getFixture().getDiagramView(), notNullValue());
		assertThat(getFixture().getDiagramView(), isPresent(viewOf(getFixture().getElement())));
	}

	/**
	 * Tests the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MElement#verticalDistance(org.eclipse.papyrus.uml.interaction.model.MElement)
	 * <em>Vertical Distance</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#verticalDistance(org.eclipse.papyrus.uml.interaction.model.MElement)
	 * @generated NOT
	 */
	public void testVerticalDistance__MElement() {
		fail("Must be tested by subclasses.");
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#following() <em>Following</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#following()
	 * @generated NOT
	 */
	public void testFollowing() {
		assertThat(getFixture().following(), isPresent());
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#nudge(int) <em>Nudge</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#nudge(int)
	 * @generated NOT
	 */
	public void testNudge__int() {
		Command nudge = getFixture().nudge(15);

		if (getFixture() instanceof MInteraction) {
			assertThat(nudge, not(executable()));
		} else {
			assertThat(nudge, executable());
			OptionalInt top = getFixture().getTop();
			assumeThat(top, isPresentInt());

			int oldY = top.getAsInt();
			execute(nudge);

			OptionalInt newTop = getFixture().getTop();
			assertThat("Nudge missed the mark", newTop, isPresent(oldY + 15));
		}
	}

} // MElementTest
