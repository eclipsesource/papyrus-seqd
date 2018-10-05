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

package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A tracker of the current set of (inter-)dependent objects for which commands are being created. This allows
 * the commands to void redundant (and, worse, cyclic) creation of commands for dependents that have already
 * been processed.
 */
public class DependencyContext {

	private static final Object NO_KEY = new Object();

	/** A dependency context that applies no guard at all. */
	private static final DependencyContext NULL_CONTEXT = new DependencyContext() {
		@Override
		boolean guardAction(Object subject, Object key) {
			return true;
		}
	};

	private static final ThreadLocal<DependencyContext> INSTANCE = new ThreadLocal<>();

	private final Multimap<Object, Object> context = HashMultimap.create();

	/**
	 * Not instantiable by clients.
	 */
	private DependencyContext() {
		super();
	}

	/**
	 * Primitive accessor for the current (static_ context, useful to keep it for later operations in the same
	 * context.
	 * 
	 * @return the current context (never {@code null})
	 * @see #getDynamic()
	 */
	public static DependencyContext get() {
		DependencyContext result = INSTANCE.get();
		if (result == null) {
			result = NULL_CONTEXT;
		}
		return result;
	}

	/**
	 * Obtain a dependency context in which to compute commands, which dynamically establishes a root context
	 * if necessary, otherwise just uses the current context. Either way, from within an
	 * {@linkplain #run(Object, Object, Runnable) action} method of the dynamic context, the real (static)
	 * context may be obtained and stored for later use via the {@link #get()} API.
	 * 
	 * @return the dynamic context
	 * @see #get()
	 */
	public static DependencyContext getDynamic() {
		return new DependencyContext() {
			@Override
			public <T, R> Optional<R> apply(T subject, Object key, Function<? super T, ? extends R> action) {
				Optional<R> result;

				// If there is not currently a shared context, establish it and use it. Otherwise,
				// use whatever's already there
				DependencyContext delegate = INSTANCE.get();
				if (delegate == null) {
					delegate = new DependencyContext();
					result = delegate.withContext(ctx -> ctx.apply(subject, key, action));
				} else {
					result = delegate.apply(subject, key, action);
				}

				return result;
			}
		};
	}

	/**
	 * Run an {@code action} in the current dependency context. For the duration of the {@code action}. this
	 * context will be active as the {@linkplain #get() current context}.
	 * 
	 * @param action
	 *            an action to run, which takes the current context ({@code this}) as input
	 * @return the result of the action
	 */
	public <T> T withContext(Function<? super DependencyContext, ? extends T> action) {
		T result;

		if (INSTANCE.get() == this) {
			result = action.apply(this);
		} else {
			INSTANCE.set(this);
			try {
				result = action.apply(this);
			} finally {
				INSTANCE.remove();
			}
		}

		return result;
	}

	/**
	 * Run an {@code action} in the current dependency context. For the duration of the {@code action}. this
	 * context will be active as the {@linkplain #get() current context}.
	 * 
	 * @param action
	 *            an action to run
	 * @return the result of the {@code action}
	 */
	public <T> T withContext(Supplier<? extends T> action) {
		return withContext(__ -> action.get());
	}

	/**
	 * Run an {@code action} in the current dependency context. For the duration of the {@code action}. this
	 * context will be active as the {@linkplain #get() current context}.
	 * 
	 * @param action
	 *            an action to run
	 */
	public void withContext(Runnable action) {
		withContext(__ -> {
			action.run();
			return null;
		});
	}

	/**
	 * <p>
	 * If the {@code key} is not already registered for the given {@code subject}, register it now and run an
	 * {@code action}. Subsequent attempts to run any action in the same context for the same {@code subject}
	 * and {@code key} will be ignored.
	 * </p>
	 * <p>
	 * If this invocation is the first for the current thread, then a new context is created for that thread
	 * and initialized with this {@code subject} and {@code key}. Any nested actions triggered by the
	 * {@code action} will inherit this context. The context is destroyed upon return from this root
	 * invocation.
	 * </p>
	 * 
	 * @param subject
	 *            the subject of an {@code action}
	 * @param key
	 *            a descriptor of the kind of {@code action} being performed
	 * @param action
	 *            the action to (uniquely) perform on the {@code subject}
	 * @return the result of the {@code action}, if it was run, or else {@link Optional#empty()} if it was not
	 *         run because this {@code key} was previously registered with this {@code subject}
	 */
	public <T, R> Optional<R> apply(T subject, Object key, Function<? super T, ? extends R> action) {
		Optional<R> result = Optional.empty();

		if (guardAction(subject, key)) {
			result = Optional.ofNullable(action.apply(subject));
		}

		return result;
	}

	/**
	 * <p>
	 * If the {@code key} is not already registered for the given {@code subject}, register it now and run an
	 * {@code action}. Subsequent attempts to run any action in the same context for the same {@code subject}
	 * and {@code key} will be ignored.
	 * </p>
	 * <p>
	 * If this invocation is the first for the current thread, then a new context is created for that thread
	 * and initialized with this {@code subject} and {@code key}. Any nested actions triggered by the
	 * {@code action} will inherit this context. The context is destroyed upon return from this root
	 * invocation.
	 * </p>
	 * 
	 * @param subject
	 *            the subject of an {@code action}
	 * @param key
	 *            a descriptor of the kind of {@code action} being performed
	 * @param action
	 *            the action to (uniquely) perform on the {@code subject}
	 */
	public <T> void accept(T subject, Object key, Consumer<? super T> action) {
		apply(subject, key, s -> {
			action.accept(s);
			return null;
		});
	}

	/**
	 * <p>
	 * If the {@code key} is not already registered for the given {@code subject}, register it now and run an
	 * {@code action}. Subsequent attempts to run any action in the same context for the same {@code subject}
	 * and {@code key} will be ignored.
	 * </p>
	 * <p>
	 * If this invocation is the first for the current thread, then a new context is created for that thread
	 * and initialized with this {@code subject} and {@code key}. Any nested actions triggered by the
	 * {@code action} will inherit this context. The context is destroyed upon return from this root
	 * invocation.
	 * </p>
	 * 
	 * @param subject
	 *            the subject of an {@code action}
	 * @param key
	 *            a descriptor of the kind of {@code action} being performed
	 * @param action
	 *            the action to (uniquely) perform on the {@code subject}
	 */
	public void run(Object subject, Object key, Runnable action) {
		accept(subject, key, __ -> action.run());
	}

	boolean guardAction(Object subject, Object key) {
		return context.put(subject, contextKey(key));
	}

	static Object contextKey(Object key) {
		return (key == null) ? NO_KEY : key;
	}
}
