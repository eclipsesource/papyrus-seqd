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

package org.eclipse.papyrus.uml.interaction.tests.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

/**
 * A self-filtering test suite. May have a {@link Delegate} runner annotation and must have a
 * {@link FilterWith} filter annotation applied to the class.
 */
public class Filtered extends Runner {

	private final ParentRunner<Runner> delegate;

	private final Filter filter;

	/**
	 * Initializes me.
	 *
	 * @param testClass
	 * @throws InitializationError
	 */
	public Filtered(Class<?> testClass) throws InitializationError {
		super();

		try {
			delegate = instantiate(testClass, Delegate.class);
			filter = instantiate(testClass, FilterWith.class);
			delegate.filter(filter);
		} catch (Exception e) {
			throw new InitializationError(e);
		}
	}

	@Override
	public Description getDescription() {
		return delegate.getDescription();
	}

	@Override
	public void run(RunNotifier notifier) {
		delegate.run(notifier);
	}

	@SuppressWarnings("unchecked")
	private static <T, A extends Annotation> T instantiate(Class<?> suiteClass, Class<A> annotationType)
			throws InitializationError {

		Class<? extends T> target;
		A annotation = suiteClass.getAnnotation(annotationType);

		if (annotation == null) {
			if (annotationType == Delegate.class) {
				// It has a default value
				target = (Class<? extends T>)BlockJUnit4ClassRunner.class;

			} else {
				throw new InitializationError("Missing annotation " + annotationType.getSimpleName());
			}
		} else {
			target = (Class<? extends T>)getValue(annotation);
		}

		try {
			return Runner.class.isAssignableFrom(target)
					? target.getConstructor(Class.class).newInstance(suiteClass)
					: target.newInstance();
		} catch (Exception e) {
			throw new InitializationError(e);
		}
	}

	static Object getValue(Annotation annotation) throws InitializationError {
		try {
			Method value = annotation.annotationType().getDeclaredMethod("value");
			return value.invoke(annotation);
		} catch (Exception e) {
			throw new InitializationError(e);
		}
	}

}
