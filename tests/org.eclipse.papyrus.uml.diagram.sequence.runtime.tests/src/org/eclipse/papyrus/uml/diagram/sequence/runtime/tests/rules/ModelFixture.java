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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.util.UMLUtil;
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

	private static final String SEQUENCE_DIAGRAM_TYPE = "PapyrusUMLSequenceDiagram"; //$NON-NLS-1$
	// private static final String SEQUENCE_DIAGRAM_TYPE =
	// "LightweightSequenceDiagram"; //$NON-NLS-1$

	private final Class<?> testClass;
	private final String path;

	private ResourceSet rset;
	private Package model;
	private Interaction interaction;
	private Optional<Diagram> sequenceDiagram;

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
	 * @param testClass
	 *            the test class in which context to load the resource. May be
	 *            {@code null} for an ordinary {@code Rule} but required for a
	 *            {@link ClassRule}
	 */
	public ModelFixture(Class<?> testClass) {
		this(testClass, null);
	}

	/**
	 * Initializes me.
	 *
	 * @param path
	 *            the path relative to the {@code testClass} of the UML resource to
	 *            load
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
		Class<?> context = testClass;
		if (context == null) {
			context = description.getTestClass();
			if (context == null) {
				fail("Explicit test class required for @ClassRule");
			}
		}

		final String[] paths = getPaths(description);
		for (String path : paths) {
			URL resourceURL = context.getResource(path);
			if (resourceURL == null) {
				fail("Resource not found: " + path);
			}

			if (rset == null) {
				rset = new ResourceSetImpl();
				if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
					standaloneSetup(rset);
				}
			}

			Package package_ = UML2Util.load(rset, URI.createURI(resourceURL.toExternalForm(), true),
					UMLPackage.Literals.PACKAGE);
			if (model == null) {
				model = package_;
			}
		}

		assertThat("No UML package found in " + path, model, notNullValue());

		interaction = (Interaction) UML2Util.findEObject(model.eAllContents(),
				UMLPackage.Literals.INTERACTION::isInstance);
		assertThat("No UML interaction found in " + path, interaction, notNullValue());

		// Look for a sequence diagram
		sequenceDiagram = CacheAdapter.getCacheAdapter(interaction).getInverseReferences(interaction)
				.stream()
				.filter(setting -> setting
						.getEStructuralFeature() == NotationPackage.Literals.VIEW__ELEMENT)
				.map(EStructuralFeature.Setting::getEObject).filter(Diagram.class::isInstance)
				.map(Diagram.class::cast).filter(diagram -> SEQUENCE_DIAGRAM_TYPE.equals(diagram.getType()))
				.findAny();
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
			ModelResource resource = description.getAnnotation(ModelResource.class);
			if ((resource == null) && (description.getTestClass() != null)) {
				resource = description.getTestClass().getAnnotation(ModelResource.class);
			}
			if (resource == null) {
				fail("Required @ModelResource annotation is missing for " + description);
			}
			result = resource.value();
		}

		return result;
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

	/**
	 * Get the first available message between two lifelines in my
	 * {@link #getInteraction() interaction}.
	 *
	 * @param from
	 *            the sending lifeline name
	 * @param to
	 *            the receiving lifeline name
	 *
	 * @return the message
	 *
	 * @throws NoSuchElementException
	 *             if no such message exists in the interaction
	 *
	 * @see #getInteraction()
	 */
	public Message getMessage(String from, String to) {
		return getInteraction().getMessages().stream() //
				.filter(lifelines(from, to)).findAny().get();
	}

	/**
	 * Get a specific message between two lifelines in my {@link #getInteraction()
	 * interaction}. This is a useful alternative to the simple
	 * {@code #getMessage(String, String)} when there are multiple messages in the
	 * same direction between two lifelines.
	 *
	 * @param from
	 *            the sending lifeline name
	 * @param to
	 *            the receiving lifeline name
	 * @param index
	 *            which of the messages between the two lifelines, in sequence
	 *            order, to retrieve
	 *
	 * @return the message
	 *
	 * @throws NoSuchElementException
	 *             if no such message exists in the interaction
	 *
	 * @see #getInteraction()
	 * @see #getMessage(String, String)
	 */
	public Message getMessage(String from, String to, int index) {
		return getInteraction().getMessages().stream() //
				.sequential().filter(lifelines(from, to)) //
				.skip(index).findFirst().get();
	}

	/**
	 * Obtains a predicate matching elements having a particular {@code name}.
	 *
	 * @param name
	 *            the name of the element to match
	 * @return the 'named' predicate
	 */
	public static Predicate<NamedElement> named(String name) {
		return element -> Objects.equals(name, element.getName());
	}

	private static Predicate<Message> lifelines(String from, String to) {
		return message -> {
			if ((message.getSendEvent() == null) || (message.getReceiveEvent() == null)) {
				return false;
			}

			return MessageUtil.getCovered(message.getSendEvent()).filter(named(from)).isPresent()
					&& MessageUtil.getCovered(message.getReceiveEvent()).filter(named(to)).isPresent();
		};
	}

}
