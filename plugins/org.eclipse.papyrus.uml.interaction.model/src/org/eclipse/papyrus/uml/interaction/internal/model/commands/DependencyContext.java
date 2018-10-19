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
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A tracker of the current set of (inter-)dependent objects for which commands are being created. This allows
 * the commands to void redundant (and, worse, cyclic) creation of commands for dependents that have already
 * been processed.
 */
public class DependencyContext {

	private static final Object NO_KEY = new Object();

	private static final Predicate<Object> TRUE = __ -> true;

	/** A dependency context that tracks nothing and applies no guard at all. */
	private static final DependencyContext NULL_CONTEXT = new DependencyContext() {
		@Override
		public final boolean put(Object subject, Object key) {
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
		return getDynamic(null);
	}

	/**
	 * Obtain a dependency context in which to compute commands, which dynamically establishes a root context
	 * if necessary, otherwise just uses the current context. Either way, from within an
	 * {@linkplain #run(Object, Object, Runnable) action} method of the dynamic context, the real (static)
	 * context may be obtained and stored for later use via the {@link #get()} API.
	 * 
	 * @param onClose
	 *            an action to invoke when the root context is closed
	 * @return the dynamic context
	 * @see #get()
	 */
	public static DependencyContext getDynamic(Consumer<? super DependencyContext> onClose) {
		return new DependencyContext() {
			@Override
			public <T, R> Optional<R> apply(T subject, Object key, Function<? super T, ? extends R> action) {
				return withContext(ctx -> ctx.apply(subject, key, action));
			}

			@Override
			public <T> T withContext(Function<? super DependencyContext, ? extends T> action) {
				T result;

				// If there is not currently a shared context, establish it and use it. Otherwise,
				// use whatever's already there
				DependencyContext delegate = INSTANCE.get();
				if (delegate == null) {
					delegate = new DependencyContext();
					try {
						result = delegate.withContext(action);
					} finally {
						if (onClose != null) {
							onClose.accept(delegate);
						}
					}
				} else {
					result = delegate.withContext(action);
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

	/**
	 * Obtain the context key of some type associated with a given {@code subject}. In case of multiple
	 * context keys of this type associated with a subject, which key is returned is undefined.
	 * 
	 * @param subject
	 *            a subject of contextual operations
	 * @param keyType
	 *            the type of key to retrieve
	 * @return some key of the requested type
	 */
	public <T> Optional<T> get(Object subject, Class<T> keyType) {
		return get(subject, keyType, TRUE);
	}

	/**
	 * Obtain the context key of some type associated with a given {@code subject}. In case of multiple
	 * context keys of this type associated with a subject, which key is returned is determined by the
	 * {@link filter}.
	 * 
	 * @param subject
	 *            a subject of contextual operations
	 * @param keyType
	 *            the type of key to retrieve
	 * @param filter
	 *            a predicate matching the specific key to retrieve
	 * @return some key of the requested type
	 */
	public <T> Optional<T> get(Object subject, Class<T> keyType, Predicate<? super T> filter) {
		return context.get(subject).stream().filter(keyType::isInstance).map(keyType::cast).filter(filter)
				.findAny();
	}

	/**
	 * Obtain or create the context key of some type associated with a given {@code subject}. In case of
	 * multiple context keys of this type associated with a subject, which key is returned is undefined. And
	 * in the case that the key is not yet present, obtain it from the given supplier and
	 * {@link #put(Object, Object) put it}.
	 * 
	 * @param subject
	 *            a subject of contextual operations
	 * @param keyType
	 *            the type of key to retrieve
	 * @param keySupplier
	 *            a supplier of the key in case it is absent
	 * @return some key of the requested type, supplied and registered if necessary
	 */
	public <T> T get(Object subject, Class<T> keyType, Supplier<? extends T> keySupplier) {
		return get(subject, keyType, TRUE, keySupplier);
	}

	/**
	 * Obtain or create the context key of some type associated with a given {@code subject}. In case of
	 * multiple context keys of this type associated with a subject, which key is returned is determined by
	 * the {@link filter}. And in the case that the key is not yet present, obtain it from the given supplier
	 * and {@link #put(Object, Object) put it}.
	 * 
	 * @param subject
	 *            a subject of contextual operations
	 * @param keyType
	 *            the type of key to retrieve
	 * @param filter
	 *            a predicate matching the specific key to retrieve
	 * @param keySupplier
	 *            a supplier of the key in case it is absent
	 * @return some key of the requested type, supplied and registered if necessary
	 */
	public <T> T get(Object subject, Class<T> keyType, Predicate<? super T> filter,
			Supplier<? extends T> keySupplier) {
		T result;

		Optional<T> existing = get(subject, keyType, filter);
		if (existing.isPresent()) {
			result = existing.get();
		} else {
			result = keySupplier.get();
			put(subject, result);
		}

		return result;
	}

	/**
	 * Associate some context {@code key} with a {@code subject}. This is useful either intentionally to block
	 * future {@linkplain #run(Object, Object, Runnable) actions} on that {@code subject} or else to register
	 * some data to be {@linkplain #get(Object, Class) retrieved} later. Has no effect if the {@code key} is
	 * already associated with the {@code subject}.
	 * 
	 * @param subject
	 *            a subject of contextual operations
	 * @param key
	 *            a context key to associate with the {@code subject}
	 * @return {@code true} if the context was updated, which is to say that this {@code key} is a new
	 *         association with this {@code subject}; {@code false}, otherwise
	 * @see #get(Object, Class)
	 * @see #run(Object, Object, Runnable)
	 * @see #accept(Object, Object, Consumer)
	 * @see #apply(Object, Object, Function)
	 */
	public boolean put(Object subject, Object key) {
		return context.put(subject, contextKey(key));
	}

	private final boolean guardAction(Object subject, Object key) {
		return put(subject, key);
	}

	static Object contextKey(Object key) {
		return (key == null) ? NO_KEY : key;
	}
}
