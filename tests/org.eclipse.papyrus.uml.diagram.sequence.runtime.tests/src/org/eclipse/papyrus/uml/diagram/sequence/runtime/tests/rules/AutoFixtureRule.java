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

import static java.util.Collections.singleton;
import static org.eclipse.uml2.common.util.UML2Util.getValidJavaIdentifier;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.EditPartUtils;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.AutoFixture.VisualID;
import org.eclipse.papyrus.uml.interaction.tests.rules.ModelFixture;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.util.UMLSwitch;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A test rule that automatically populates fields annotated with {@link AutoFixture @AutoFixture} from the
 * test rule that is some kind of {@link ModelFixture}.
 */
public class AutoFixtureRule implements TestRule {

	private static final Pattern EDIT_PART_FIXTURE = Pattern.compile("^.*(?=EP|EditPart)");

	private static final Pattern GEOMETRY_FIXTURE = Pattern.compile("^.*(?=Geom\\w*)");

	private final Object test;

	private BiFunction<ModelFixture, FieldAccess<?>, Object> resolver;

	/**
	 * Initializes me.
	 */
	public AutoFixtureRule(Object testOrTestClass) {
		super();

		this.test = testOrTestClass;

		List<BiFunction<ModelFixture, FieldAccess<?>, Object>> resolvers = Arrays.asList(
				this::resolveQualifiedName, //
				this::resolveSimpleName, //
				this::resolveEditPart, //
				this::resolveGeometry);
		resolver = (model, spec) -> resolvers.stream().map(r -> r.apply(model, spec)).filter(Objects::nonNull)
				.findFirst().orElse(null);
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				ModelFixture model = getField(ModelFixture.class).get();

				getAutoFixtures().forEach(fixture -> {
					fixture.set(resolver.apply(model, fixture));
				});

				try {
					base.evaluate();
				} finally {
					getAutoFixtures().forEach(FieldAccess::clear);
				}
			}
		};
	}

	public void refresh() {
		ModelFixture model = getField(ModelFixture.class).get();

		getAutoFixtures().forEach(fixture -> {
			Object resolved = null;
			try {
				resolved = resolver.apply(model, fixture);
			} catch (AssertionError e) {
				// Just set it null
			}

			if (resolved == null) {
				fixture.clear();
			} else {
				fixture.set(resolved);
			}
		});
	}

	private Stream<Field> getFields() {
		return getFields(test);
	}

	private static Stream<Field> getFields(Object owner) {
		boolean isStatic = owner instanceof Class<?>;
		return isStatic ? getFields((Class<?>)owner, isStatic) : getFields(owner.getClass(), isStatic);
	}

	private static Stream<Field> getFields(Class<?> owner, boolean isStatic) {
		Stream<Field> result = Stream.of(owner.getDeclaredFields())
				.filter(f -> Modifier.isStatic(f.getModifiers()) == isStatic);

		Class<?> superClass = owner.getSuperclass();
		if ((superClass != null) && superClass != Object.class) {
			result = Stream.concat(result, getFields(superClass, isStatic));
		}

		return result;
	}

	private <T> FieldAccess<T> getField(Class<T> type) {
		return new FieldAccess<>(type, test);
	}

	private <T> FieldAccess<T> findField(Pattern regex, Class<T> type) {
		return getFields().filter(f -> type.isAssignableFrom(f.getType()))
				.filter(f -> regex.matcher(f.getName()).find())
				// FieldAccess found this way doesn't need to be set, so optionality is fine
				.map(f -> new FieldAccess<>(f, type, test, true)).findFirst().orElse(null);
	}

	private Stream<FieldAccess<?>> getAutoFixtures() {
		return getFields().filter(f -> f.isAnnotationPresent(AutoFixture.class)).map(
				f -> new FieldAccess<>(f, Object.class, test, f.getAnnotation(AutoFixture.class).optional()));
	}

	//
	// Resolvers
	//

	private Object resolveQualifiedName(ModelFixture model, FieldAccess<?> fixture) {
		return fixture.isAssignableTo(NamedElement.class) //
				? getElementByQName(model, fixture.getFixtureName())
				: null;
	}

	private NamedElement getElementByQName(ModelFixture model, String name) {
		NamedElement result = null;

		String[] parts = name.split("::");
		if ((parts.length > 0) && parts[0].equals(model.getModel().getName())) {
			NamedElement next = model.getModel();

			for (int i = 1; (next != null) && (i < parts.length); i++) {
				if (!(next instanceof Namespace)) {
					fail("Not a namespace: " + next);
				}

				next = ((Namespace)next).getMember(parts[i]);
			}

			result = next;
		}

		return result;
	}

	private Object resolveSimpleName(ModelFixture model, FieldAccess<?> fixture) {
		return fixture.isAssignableTo(NamedElement.class)
				? getElement(model, fixture.getFixtureName(), fixture::isAssignableFrom)
				: null;
	}

	private NamedElement getElement(ModelFixture model, String name, Predicate<? super NamedElement> filter) {
		NamedElement result = null;

		if (name.contains("::")) {
			result = Optional.ofNullable(getElementByQName(model, name)).filter(filter).orElse(null);
		} else {
			for (TreeIterator<EObject> iter = EcoreUtil
					.getAllContents(singleton(model.getModel())); (result == null) && iter.hasNext();) {

				EObject next = iter.next();
				if (next instanceof NamedElement) {
					NamedElement element = (NamedElement)next;
					if (name.equals(getValidJavaIdentifier(element.getName())) && filter.test(element)) {
						result = element;
					}
				} else {
					iter.prune();
				}
			}
		}

		return result;
	}

	private Object resolveEditPart(ModelFixture model, FieldAccess<?> fixture) {
		Object result = null;

		if ((model instanceof EditorFixture) && fixture.isAssignableTo(EditPart.class)) {
			EditorFixture editor = (EditorFixture)model;

			String fixtureName = fixture.getFixtureName(EDIT_PART_FIXTURE);
			Supplier<? extends EObject> element = findField(Pattern.compile("^" + Pattern.quote(fixtureName)),
					EObject.class);
			if (element == null) {
				element = () -> getElement(model, fixtureName, __ -> true);
			}

			EObject fixtureElement = element.get();
			if (fixtureElement != null) { // It could be optional
				result = DiagramEditPartsUtil.getChildByEObject(fixtureElement, editor.getDiagramEditPart(),
						isEdge(fixtureElement));

				if ((result != null) && fixture.field.isAnnotationPresent(VisualID.class)) {
					String visualID = fixture.field.getAnnotation(VisualID.class).value();
					// Prefer the child
					result = EditPartUtils.findFirstChildEditPartWithId((EditPart)result, visualID);
				}
			}
		}

		return result;
	}

	private Object resolveGeometry(ModelFixture model, FieldAccess<?> fixture) {
		Object result = null;

		if ((model instanceof EditorFixture) && fixture.isAssignableTo(Translatable.class)) {
			Pattern editPartPattern = Pattern.compile(
					"^" + Pattern.quote(fixture.getFixtureName(GEOMETRY_FIXTURE)) + "(?=EP|EditPart)?");
			FieldAccess<? extends EditPart> editPart = findField(editPartPattern, EditPart.class);
			if (editPart != null) {
				EditPart ep = editPart.get();
				if (ep != null) { // It could be optional
					Translatable translatable = (ep instanceof ConnectionEditPart)
							? ((Connection)((ConnectionEditPart)ep).getFigure()).getPoints().getCopy()
							: ((GraphicalEditPart)ep).getFigure().getBounds().getCopy();

					if (translatable != null) {
						((GraphicalEditPart)ep).getFigure().getParent().translateToAbsolute(translatable);
					}

					result = translatable;
				}
			}
		}

		return result;
	}

	@SuppressWarnings("hiding")
	static boolean isEdge(EObject object) {
		return new UMLSwitch<Boolean>() {
			@Override
			public Boolean caseMessage(Message object) {
				return true;
			}

			@Override
			public Boolean caseAssociationClass(AssociationClass object) {
				return false;
			}

			@Override
			public Boolean caseRelationship(Relationship object) {
				return true;
			}

			@Override
			public Boolean caseActivityEdge(ActivityEdge object) {
				return true;
			}

			@Override
			public Boolean caseTransition(Transition object) {
				return true;
			}

			@Override
			public Boolean defaultCase(EObject object) {
				return false;
			}
		}.doSwitch(object);
	}

	//
	// Nested types
	//

	private static final class FieldAccess<T> implements Supplier<T> {
		private final Field field;

		private final Object owner;

		private final Class<? extends T> type;

		private final boolean optional;

		FieldAccess(Class<T> type, Object owner) {
			super();

			try {
				this.field = getFields(owner).filter(f -> type.isAssignableFrom(f.getType())).findAny().get();
				setAccessible();

				this.owner = owner;
				this.type = field.getType().asSubclass(type);

				// FieldAccess constructed this way is not used as a test fixture, so optionality is fine
				this.optional = true;
			} catch (Exception e) {
				e.printStackTrace();
				fail(String.format("Cannot access fixture of type %s", type.getName()));
				throw new Error(); // Unreachable
			}
		}

		FieldAccess(Field field, Class<T> type, Object owner, boolean optional) {
			super();

			try {
				this.field = field;
				setAccessible();

				this.owner = owner;
				this.type = field.getType().asSubclass(type);
				this.optional = optional;
			} catch (Exception e) {
				e.printStackTrace();
				fail(String.format("Cannot access fixture %s", field.getName()));
				throw new Error(); // Unreachable
			}
		}

		private void setAccessible() {
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
		}

		String getName() {
			return field.getName();
		}

		String getFixtureName() {
			AutoFixture auto = getAutoFixture();
			String result = (auto == null) ? "" : auto.value();

			return result.isEmpty() ? getName() : result;
		}

		String getFixtureName(Pattern regex) {
			String result = getFixtureName();
			Matcher m = regex.matcher(result);
			if (m.find()) {
				result = m.group();
			}
			return result;
		}

		AutoFixture getAutoFixture() {
			return field.getAnnotation(AutoFixture.class);
		}

		boolean isAssignableFrom(Object object) {
			Class<?> objectType;

			if (object == null) {
				objectType = Void.class;
			} else if ((object instanceof Class<?>)) {
				objectType = (Class<?>)object;
			} else {
				objectType = object.getClass();
			}

			return field.getType().isAssignableFrom(objectType);
		}

		boolean isAssignableTo(Class<?> objectType) {
			return objectType.isAssignableFrom(field.getType());
		}

		@Override
		public T get() {
			try {
				return type.cast(field.get(owner));
			} catch (Exception e) {
				e.printStackTrace();
				fail(String.format("Could not get fixture %s of type %s", field.getName(), type.getName()));
				return null; // Unreachable
			}
		}

		public boolean isRequired() {
			return !optional;
		}

		void set(Object resolved) {
			if ((resolved == null) && isRequired()) {
				fail(String.format("Could not resolve fixture %s of type %s", field.getName(),
						type.getName()));
				return; // Unreachable
			}

			if ((resolved == null) || type.isInstance(resolved)) {
				try {
					field.set(owner, resolved);
				} catch (Exception e) {
					e.printStackTrace();
					fail(String.format("Could not set fixture %s of type %s", field.getName(),
							type.getName()));
				}
			} else {
				fail(String.format("Could not set a %s into fixture %s of type %s",
						resolved.getClass().getName(), field.getName(), type.getName()));
			}
		}

		void clear() {
			try {
				field.set(owner, null);
			} catch (Exception e) {
				e.printStackTrace();
				// Best effort. Just continue with tests
			}
		}
	}
}
