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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.util;

import com.google.common.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

/**
 * An {@link EventBus} subscriber that maintains a weak reference to the real object that handles events (this
 * delegator forwarding events to it). This enables registration of objects whose lifecycle is not tracked,
 * for which there is no "disposal" or other termination protocol at which the object can then be unregistered
 * from the event bus.
 */
public abstract class WeakEventBusDelegator<T> implements Supplier<T> {
	private final EventBus bus;

	private final WeakReference<T> delegate;

	/**
	 * Initializes me.
	 * 
	 * @param bus
	 *            the event bus to which the client will register me
	 * @param delegate
	 *            the delegate that I track, weakly, to which I will forward events from the bus
	 */
	public WeakEventBusDelegator(EventBus bus, T delegate) {
		super();

		this.bus = bus;
		this.delegate = new WeakReference<T>(delegate);
	}

	/**
	 * Obtain the delegate to which to forward an event from the bus. If the result is {@code null}, then I
	 * will already have unregistered myself because my delegate is no longer viable.
	 */
	@Override
	public final T get() {
		T result = delegate.get();

		if (result == null) {
			// The reference has been cleared, so unregister
			bus.unregister(this);
		}

		return result;
	}

}
