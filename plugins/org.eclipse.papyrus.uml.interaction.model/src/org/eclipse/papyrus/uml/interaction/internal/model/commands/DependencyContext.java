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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.MElement;

/**
 * A tracker of the current set of (inter-)dependent objects for which commands are being created. This allows
 * the commands to void redundant (and, worse, cyclic) creation of commands for dependents that have already
 * been processed.
 */
public class DependencyContext {

	private static final Object NO_KEY = new Object();

	private static final Object NO_SUBJECT = NO_KEY; // Don't create an unnecessary object

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

			@SuppressWarnings("boxing")
			@Override
			public boolean put(Object subject, Object key) {
				return withContext(ctx -> ctx.put(subject, key));
			}

			@Override
			public <T> T get(Object subject, Class<T> keyType, Predicate<? super T> filter,
					Supplier<? extends T> keySupplier) {

				return withContext(ctx -> ctx.get(subject, keyType, filter, keySupplier));
			}

			@Override
			<T> Optional<T> remove(Object subject, Class<T> keyType) {
				return withContext(ctx -> ctx.remove(subject, keyType));
			}

			@Override
			public <V> ChildContext<V> runNested(Supplier<? extends V> computation) {
				return withContext(ctx -> super.runNested(computation));
			}

		};
	}

	/**
	 * Run an {@code action} in the current dependency context. For the duration of the {@code action}, this
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
	 * Run an {@code action} in the current dependency context. For the duration of the {@code action}, this
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
	 * Run an {@code action} in the current dependency context. For the duration of the {@code action}, this
	 * context will be active as the {@linkplain #get() current context}. A kill-switch command that may be
	 * combined with commands produced by the {@code action} is provided, if available, to the given handler.
	 * 
	 * @param action
	 *            an action to run
	 * @param killSwitchHandler
	 *            a handler of the future kill-switch command. The handler should access the supplier only
	 *            when the result of the {@code action} is to be finalized by the client, because it may only
	 *            become ready during evaluation now or later of the {@code action} or its result. Although
	 *            the supplier provided to the handler will never be {@code null}, the kill-switch command
	 *            that it supplies may be {@code null} at any time
	 * @return the result of the {@code action}
	 */
	public <T> Optional<T> withContext(Supplier<? extends T> action,
			Consumer<? super Supplier<? extends Command>> killSwitchHandler) {

		return withContext(ctx -> {
			// Compute this result first because that computation can register kill switches
			T result = action.get();

			// Don't need to look for kill-switches if they won't be handled
			if (killSwitchHandler != null) {
				Supplier<? extends Command> killSwitchSupplier = () -> KillSwitch.check(ctx).orElse(null);
				killSwitchHandler.accept(killSwitchSupplier);
			}

			return Optional.ofNullable(result);
		});
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

	<T> Optional<T> remove(Object subject, Class<T> keyType) {
		Optional<T> result = get(subject, keyType);

		result.ifPresent(v -> context.remove(normalizeSubject(subject), v));

		return result;
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
		return context.get(normalizeSubject(subject)).stream().filter(keyType::isInstance).map(keyType::cast)
				.filter(filter).findAny();
	}

	private static Object normalizeSubject(Object subject) {
		Object result = subject;

		if (subject == null) {
			result = NO_SUBJECT;
		} else if (subject instanceof Optional<?>) {
			// Unwrap optionals
			result = normalizeSubject(((Optional<?>)subject).orElse(null));
		} else if (subject instanceof MElement<?>) {
			// The identity of a logical model element is the underlying UML element
			result = ((MElement<?>)subject).getElement();
		} else if (subject instanceof View) {
			// The identity of a notation view is its semantic element. Normalize in case of null
			result = normalizeSubject(((View)subject).getElement());
		}

		return result;
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
		return context.put(normalizeSubject(subject), contextKey(key));
	}

	private final boolean guardAction(Object subject, Object key) {
		return put(subject, key);
	}

	static Object contextKey(Object key) {
		return (key == null) ? NO_KEY : key;
	}

	/**
	 * Obtain a command that will be computed later in whatever is the current context.
	 * 
	 * @param futureCommand
	 *            a future command
	 * @return a deferred command that captures the current context and applies it in the future
	 */
	public static Command defer(Supplier<? extends Command> futureCommand) {
		// Capture the current context for later
		return getDynamic().withContext(ctx -> ctx.deferCommand(futureCommand));
	}

	/**
	 * Obtain a command that will be computed later in this current context.
	 * 
	 * @param futureCommand
	 *            a future creation command
	 * @return a deferred command that applies this context in the future
	 */
	public Command deferCommand(Supplier<? extends Command> futureCommand) {
		return new CommandWrapper() {
			@Override
			protected Command createCommand() {
				Command result = withContext(futureCommand);
				if (result == null) {
					// The deferral is optional
					result = IdentityCommand.INSTANCE;
				}
				return result;
			}
		};
	}

	/**
	 * Obtain a command that will be computed later in this current context.
	 * 
	 * @param futureCommand
	 *            a future creation command
	 * @return a deferred command that applies this context in the future
	 */
	public Command defer(Function<? super DependencyContext, ? extends Command> futureCommand) {
		return deferCommand(() -> futureCommand.apply(this));
	}

	/**
	 * Obtain a creation command that will be computed later in whatever is the current context.
	 * 
	 * @param futureCommand
	 *            a future creation command
	 * @return a deferred command that captures the current context and applies it in the future
	 */
	public static <T extends EObject> CreationCommand<T> deferCreate(
			Supplier<? extends CreationCommand<T>> futureCommand) {

		// Capture the current context for later
		return getDynamic().withContext(ctx -> ctx.deferCreateCommand(futureCommand));
	}

	/**
	 * Obtain a creation command that will be computed later in this current context.
	 * 
	 * @param futureCommand
	 *            a future creation command
	 * @return a deferred command that applies this context in the future
	 */
	public <T extends EObject> CreationCommand<T> deferCreateCommand(
			Supplier<? extends CreationCommand<T>> futureCommand) {

		return new CreationCommand.Wrapper<T>(null) {
			@Override
			public CreationCommand<T> getCommand() {
				// This cast is safe by construction
				@SuppressWarnings("cast")
				CreationCommand<T> result = (CreationCommand<T>)super.getCommand();
				if (result == null) {
					result = withContext(futureCommand);
					this.command = result;
				}
				return result;
			}
		};
	}

	/**
	 * Obtain a creation command that will be computed later in this current context.
	 * 
	 * @param futureCommand
	 *            a future creation command
	 * @return a deferred command that applies this context in the future
	 */
	public <T extends EObject> CreationCommand<T> deferCreate(
			Function<? super DependencyContext, ? extends CreationCommand<T>> futureCommand) {

		return deferCreateCommand(() -> futureCommand.apply(this));
	}

	/**
	 * Create a child context and execute the given {@code action} in it. The child context inherits all of my
	 * state and can later {@link ChildContext#commit() commit} its state back to mine for continuing
	 * calculations.
	 * 
	 * @param action
	 *            the action to run in the child context
	 * @return a token for later {@link ChildContext#commit() commit} of the child context
	 * @see ChildContext#commit()
	 */
	public ChildContext<Void> runNested(Runnable action) {
		return runNested(() -> {
			action.run();
			return null;
		});
	}

	/**
	 * Create a child context and execute the given {@code computation} in it. The child context inherits all
	 * of my state and can later {@link ChildContext#commit() commit} its state back to mine for continuing
	 * calculations.
	 * 
	 * @param action
	 *            the action to run in the child context
	 * @return a token for later {@link ChildContext#commit() commit} of the child context
	 * @see ChildContext#commit()
	 */
	public <V> ChildContext<V> runNested(Supplier<? extends V> computation) {
		return new ChildContext<V>().withContext(computation);
	}

	//
	// Nested types
	//

	/**
	 * A token representing a nested {@link DependencyContext} that inherits the state of its parent and can
	 * optionally {@link #commit() commit} its state back to that parent.
	 */
	public final class ChildContext<V> implements Supplier<V> {

		private final DependencyContext state;

		private boolean committed;

		private V value;

		ChildContext() {
			super();

			this.state = new DependencyContext() {
				// Implement inheritance of context from the parent
				@Override
				public <T> Optional<T> get(Object subject, Class<T> keyType, Predicate<? super T> filter) {

					Optional<T> result = super.get(subject, keyType, filter);

					if (!result.isPresent()) {
						// Try the parent context
						result = parent().get(subject, keyType, filter);
					}

					return result;
				}
			};
		}

		private ChildContext(DependencyContext state) {
			super();

			this.state = state;
		}

		ChildContext<V> withContext(Supplier<? extends V> action) {
			return state.withContext(() -> {
				value = action.get();
				return this;
			});
		}

		/**
		 * Commits the state of a {@linkplain DependencyContext#runNested(Runnable) child context} into its
		 * parent. Has no effect for a root context (that has no parent) or for a child that has already been
		 * committed. In the latter case, any additional state that has been accumulated will just be lost.
		 * 
		 * @throws IllegalStateException
		 *             if I represent a child of the null context
		 */
		public void commit() {
			if (!committed) {
				committed = true;

				if (parent() == NULL_CONTEXT) {
					throw new IllegalStateException("committing child of the null context"); //$NON-NLS-1$
				}

				parent().context.putAll(state.context);
			}
		}

		DependencyContext parent() {
			return DependencyContext.this;
		}

		@Override
		public V get() {
			return value;
		}

		/**
		 * Obtains a continuation of myself in the same nested context. After this, I can no longer be
		 * {@linkplain #commit() committed} but only the continuation may be. This is useful for computations
		 * in a stream or other situation where functional chaining is employed.
		 * 
		 * @param continuation
		 *            a function that transforms my value to a new result
		 * @return a new child context that replaces me and, if the context is to be committed to the parent,
		 *         is the one that would have to be committed
		 */
		public <U> ChildContext<U> andThen(Function<? super V, ? extends U> continuation) {
			committed = true; // Don't allow me to commit
			return new ChildContext<U>(state).withContext(() -> continuation.apply(get()));
		}

	}

}
