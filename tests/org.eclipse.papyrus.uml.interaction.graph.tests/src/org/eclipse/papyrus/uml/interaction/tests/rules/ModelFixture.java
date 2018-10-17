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

package org.eclipse.papyrus.uml.interaction.tests.rules;

import static org.eclipse.uml2.uml.util.UMLUtil.getQualifiedText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain.EditingDomainProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.provider.NotationItemProviderAdapterFactory;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.ClassRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A test fixture that loads an UML model containing an {@link Interaction}.
 *
 * @author Christian W. Damus
 */
public class ModelFixture implements TestRule {

	private static final String SEQUENCE_DIAGRAM_TYPE = "LightweightSequenceDiagram"; //$NON-NLS-1$
	private static final String PAPYRUS_SEQUENCE_DIAGRAM_TYPE = "PapyrusUMLSequenceDiagram"; //$NON-NLS-1$
	private static final Set<String> SEQUENCE_DIAGRAM_TYPES = new HashSet<>(
			Arrays.asList(SEQUENCE_DIAGRAM_TYPE, PAPYRUS_SEQUENCE_DIAGRAM_TYPE));

	private final Class<?> testClass;
	private final String path;

	private ResourceSet rset;
	private Package model;
	private Interaction interaction;
	private Optional<Diagram> sequenceDiagram;

	private Graph graph;

	/**
	 * Initializes me.
	 *
	 * @param testClass
	 *            the test class in which context to load the resource. May be
	 *            {@code null} for an ordinary {@code Rule} but required for a
	 *            {@link ClassRule}
	 * @param path
	 *            the path relative to the {@code testClass} of the UML resource to
	 *            load
	 */
	public ModelFixture(Class<?> testClass, String path) {
		super();

		this.testClass = testClass;
		this.path = path;
	}

	/**
	 * Initializes me.
	 *
	 * @param path
	 *            the path relative to the host class of the UML resource to load
	 */
	public ModelFixture(String path) {
		this(null, path);
	}

	/**
	 * Initializes me.
	 */
	public ModelFixture() {
		this(null, null);
	}

	public Package getModel() {
		return model;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public Optional<Diagram> getSequenceDiagram() {
		return sequenceDiagram;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				starting(description);

				try {
					base.evaluate();
				} finally {
					finished(description);
				}
			}
		};
	}

	protected void starting(Description description) {
		final String[] paths = getPaths(description);
		for (String path : paths) {
			URL resourceURL = getResourceURL(description, path);
			if (resourceURL == null) {
				fail("Resource not found: " + path);
			}

			if (rset == null) {
				rset = createResourceSet();
			}

			Package package_ = UML2Util.load(rset, URI.createURI(resourceURL.toExternalForm(), true),
					UMLPackage.Literals.PACKAGE);
			if (model == null) {
				model = package_;
			}
		}

		String where = String.join(", ", paths);
		assertThat("No UML package found in " + where, model, notNullValue());

		interaction = (Interaction) UML2Util.findEObject(model.eAllContents(),
				UMLPackage.Literals.INTERACTION::isInstance);
		assertThat("No UML interaction found in " + where, interaction, notNullValue());

		// Look for a sequence diagram
		sequenceDiagram = CacheAdapter.getCacheAdapter(interaction).getInverseReferences(interaction)
				.stream()
				.filter(setting -> setting
						.getEStructuralFeature() == NotationPackage.Literals.VIEW__ELEMENT)
				.map(EStructuralFeature.Setting::getEObject).filter(Diagram.class::isInstance)
				.map(Diagram.class::cast)
				.filter(diagram -> SEQUENCE_DIAGRAM_TYPES.contains(diagram.getType())).findAny();
	}

	protected URL getResourceURL(Description description, String path) {
		Class<?> context = testClass;
		if (context == null) {
			context = description.getTestClass();
			if (context == null) {
				fail("Explicit test class required for @ClassRule");
			}
		}

		return context.getResource(path);
	}

	protected ResourceSet createResourceSet() {
		ResourceSet result = new ResourceSetImpl();
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			standaloneSetup(result);
		}
		return result;
	}

	protected void finished(Description description) {
		interaction = null;
		model = null;

		rset.getResources().forEach(Resource::unload);
		rset.getResources().clear();
		rset.eAdapters().clear();
		rset = null;
	}

	protected String[] getPaths(Description description) {
		String[] result;

		if (path != null) {
			result = new String[] { path };
		} else {
			result = requireAnnotation(description, ModelResource.class).value();
		}

		return result;
	}

	protected final <A extends Annotation> Optional<A> getAnnotation(Description description,
			Class<A> type) {
		A result = description.getAnnotation(type);
		if ((result == null) && (description.getTestClass() != null)) {
			result = description.getTestClass().getAnnotation(type);
		}
		return Optional.ofNullable(result);
	}

	protected final <A extends Annotation> A requireAnnotation(Description description, Class<A> type) {
		Optional<A> result = getAnnotation(description, type);
		if (!result.isPresent()) {
			fail(String.format("Required @%s annotation is missing for %s", type.getSimpleName(),
					description));
		}
		return result.get();
	}

	/**
	 * Compute the dependency graph of my default {@link #getInteraction()
	 * interaction}.
	 *
	 * @return the dependency graph of my default interaction
	 */
	public Graph graph() {
		if (graph == null) {
			graph = Graph.compute(getInteraction(), sequenceDiagram.orElse(null));
		}
		return graph;
	}

	/**
	 * Obtain an assertion utility on my {@link #graph() graph}.
	 *
	 * @return my graph's assertion utility
	 * @see #graph()
	 */
	public GraphAssertion verifyGraph() {
		return new GraphAssertion(graph());
	}

	/**
	 * Get the named element. Fails the test if the element is not found.
	 *
	 * @param qualifiedName
	 *            the qualified name of an element to get
	 * @return the element
	 */
	public NamedElement getElement(String qualifiedName) {
		Collection<NamedElement> result = UMLUtil.findNamedElements(rset, qualifiedName);

		assertThat("no such element: " + qualifiedName, result, hasItem(anything()));

		return result.iterator().next();
	}

	/**
	 * Get the named element. Fails the test if the element is not found.
	 *
	 * @param qualifiedName
	 *            the qualified name of an element to get
	 * @param type
	 *            the type of element to retrieve
	 *
	 * @return the element
	 *
	 * @param <T>
	 *            the element type to retrieve
	 */
	public <T extends NamedElement> T getElement(String qualifiedName, Class<T> type) {

		Collection<NamedElement> result = UMLUtil.findNamedElements(rset, qualifiedName, false,
				(EClass) UMLPackage.eINSTANCE.getEClassifier(type.getSimpleName()));

		assertThat("no such element: " + qualifiedName, result, hasItem(anything()));

		return type.cast(result.iterator().next());
	}

	/**
	 * Get the vertex for a named element. Fails the test if the element is not
	 * found.
	 *
	 * @param qualifiedName
	 *            the qualified name of an element which vertex to get
	 * @return the element's vertex
	 */
	public Vertex vertex(String qualifiedName) {
		return vertex(getElement(qualifiedName));
	}

	/**
	 * Get the vertex for an element. Fails the test if the vertex is not found.
	 *
	 * @param element
	 *            the element which vertex to get
	 * @return the {@code element}'s vertex
	 */
	public Vertex vertex(Element element) {
		return graph().vertex(element);
	}

	/**
	 * Configures a resource set for stand-alone test execution.
	 *
	 * @param rset
	 *            a resource set to configure
	 */
	public static void standaloneSetup(ResourceSet rset) {
		UMLResourcesUtil.init(rset);

		// Initialize the diagram notation
		NotationPackage.Literals.DIAGRAM.eClass();

		rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("notation",
				new XMIResourceFactoryImpl());
	}

	public boolean semanticallyPrecedes(NamedElement fragment, NamedElement other) {
		List<InteractionFragment> fragments = getInteraction().getFragments();
		int indexOfOne = fragments.indexOf(fragment);
		int indexOfOther = fragments.indexOf(other);
		return (indexOfOne >= 0) && (indexOfOther >= 0) && (indexOfOther > indexOfOne);
	}

	public <T extends NamedElement> Matcher<T> semanticallyFollows(NamedElement other) {
		return new TypeSafeDiagnosingMatcher<T>() {

			@Override
			protected boolean matchesSafely(T item, org.hamcrest.Description mismatchDescription) {
				boolean result = semanticallyPrecedes(other, item);
				if (!result) {
					mismatchDescription.appendText(getQualifiedText(item));
					mismatchDescription.appendText(" semantically precedes "); //$NON-NLS-1$
					mismatchDescription.appendText(getQualifiedText(other));
				}
				return result;
			}

			@Override
			public void describeTo(org.hamcrest.Description description) {
				description.appendText("semantically follows "); //$NON-NLS-1$
				description.appendText(getQualifiedText(other));
			}

		};
	}

	public <T extends NamedElement> Matcher<T> semanticallyPrecedes(NamedElement other) {
		return new TypeSafeDiagnosingMatcher<T>() {

			@Override
			protected boolean matchesSafely(T item, org.hamcrest.Description mismatchDescription) {
				boolean result = semanticallyPrecedes(item, other);
				if (!result) {
					mismatchDescription.appendText(getQualifiedText(item));
					mismatchDescription.appendText(" semantically follows "); //$NON-NLS-1$
					mismatchDescription.appendText(getQualifiedText(other));
				}
				return result;
			}

			@Override
			public void describeTo(org.hamcrest.Description description) {
				description.appendText("semantically precedes "); //$NON-NLS-1$
				description.appendText(getQualifiedText(other));
			}

		};
	}

	//
	// Nested types
	//

	/**
	 * A specialized {@link ModelFixture} that provides an {@link EditingDomain}
	 * context for the test model.
	 */
	public static class Edit extends ModelFixture {
		/**
		 * Initializes me.
		 */
		public Edit() {
			super();
		}

		/**
		 * Initializes me.
		 *
		 * @param path
		 *            the path relative to the host class of the UML resource to load
		 */
		public Edit(String path) {
			super(path);
		}

		/**
		 * Initializes me.
		 *
		 * @param testClass
		 *            the test class in which context to load the resource. May be
		 *            {@code null} for an ordinary {@code Rule} but required for a
		 *            {@link ClassRule}
		 * @param path
		 *            the path relative to the {@code testClass} of the UML resource to
		 *            load
		 */
		public Edit(Class<?> testClass, String path) {
			super(testClass, path);
		}

		@Override
		protected ResourceSet createResourceSet() {
			ResourceSet result = super.createResourceSet();

			ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
			adapterFactory.addAdapterFactory(new UMLItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new NotationItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

			EditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory,
					new BasicCommandStack(), result);
			result.eAdapters().add(new EditingDomainProvider(editingDomain));
			return result;
		}

		public EditingDomain getEditingDomain() {
			return AdapterFactoryEditingDomain.getEditingDomainFor(getModel());
		}

		public void execute(Command command) {
			assertThat("no command to execute", command, notNullValue());
			assertThat("command is not executable", command.canExecute(), is(true));
			getEditingDomain().getCommandStack().execute(command);
		}
	}
}
