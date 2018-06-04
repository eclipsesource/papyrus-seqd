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

package org.eclipse.papyrus.uml.interaction.model.util;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.BasicNotifierImpl.EObservableAdapterList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MObject;
import org.eclipse.uml2.uml.Element;

/**
 * An adapter on the UML model that notifies of changes to the <em>Logical Model</em> representation of it.
 *
 * @param <T>
 *            the <em>Logical Model</em> type to be notified of
 * @author Christian W. Damus
 */
public class LogicalModelAdapter<T extends MElement<?>> {

	private Element target;

	private EObservableAdapterList.Listener listener;

	private AtomicReference<Consumer<T>> oldModelHandler = new AtomicReference<>(this::pass);

	private AtomicReference<Consumer<T>> newModelHandler = new AtomicReference<>(this::pass);

	private final Class<? extends T> modelType;

	/**
	 * Initializes me.
	 * 
	 * @param element
	 *            the interaction element on which I listen for <em>Logical Model</em> changes
	 * @param modelType
	 *            the <em>Logical Model</em> type to be notified of
	 */
	public LogicalModelAdapter(Element element, Class<? extends T> modelType) {
		super();

		target = element;
		this.modelType = modelType;
		listener = new AdapterHandler();
		EObservableAdapterList adapters = (EObservableAdapterList)target.eAdapters();
		adapters.addListener(listener);
	}

	/**
	 * Stop listening and clean up. A disposed adapter will not notify any listeners.
	 */
	public void dispose() {
		if (target != null) {
			EObservableAdapterList adapters = (EObservableAdapterList)target.eAdapters();
			adapters.removeListener(listener);

			target = null;
			listener = null;

			oldModelHandler.set(this::pass);
			newModelHandler.set(this::pass);
		}
	}

	/**
	 * Queries whether I am {@linkplain #dispose() disposed}.
	 * 
	 * @return whether I am disposed
	 * @see #dispose()
	 */
	public boolean isDisposed() {
		return target == null;
	}

	/**
	 * Obtains the UML interaction element that I adapt.
	 * 
	 * @return the UML interaction element, or {@code null} if I am {@linkplain #dispose() disposed}
	 * @see #dispose()
	 */
	public Element getInteraction() {
		return target;
	}

	/**
	 * Add a handler to be called whenever the logical model is discarded.
	 * 
	 * @param oldLogicalModelHandler
	 *            an action to apply to the logical model that was disposed
	 */
	public void onLogicalModelDisposed(Consumer<? super T> oldLogicalModelHandler) {
		if (!isDisposed()) {
			this.oldModelHandler.accumulateAndGet(safeHandler(oldLogicalModelHandler), Consumer::andThen);
		}
	}

	/**
	 * Add a handler to be called whenever the logical model is created. If at the time of this call a logical
	 * model exists for my UML interaction, then the handler is invoked immediately.
	 * 
	 * @param newLogicalModelHandler
	 *            an action to apply to the logical model that was created for my UML interaction
	 */
	public void onLogicalModelCreated(Consumer<? super T> newLogicalModelHandler) {
		if (!isDisposed()) {
			Consumer<T> newHandler = safeHandler(newLogicalModelHandler);
			this.newModelHandler.accumulateAndGet(newHandler, Consumer::andThen);

			getExistingLogicalModel().ifPresent(newHandler::accept);
		}
	}

	private void pass(@SuppressWarnings("unused") T __) {
		// Pass
	}

	private Consumer<T> safeHandler(Consumer<? super T> handler) {
		return logicalModel -> {
			try {
				handler.accept(logicalModel);
			} catch (Exception e) {
				LogicalModelPlugin.INSTANCE.log(e);
			}
		};
	}

	private Optional<T> getExistingLogicalModel() {
		return Optional.ofNullable(target)
				.map(notifier -> EcoreUtil.getExistingAdapter(notifier, MObject.class))
				.filter(modelType::isInstance).map(modelType::cast);
	}

	//
	// Nested types
	//

	private final class AdapterHandler implements EObservableAdapterList.Listener {
		@Override
		public void added(Notifier notifier, Adapter adapter) {
			if (notifier == target && modelType.isInstance(adapter)) {
				newModelHandler.get().accept(modelType.cast(adapter));
			}
		}

		@Override
		public void removed(Notifier notifier, Adapter adapter) {
			if (notifier == target && modelType.isInstance(adapter)) {
				oldModelHandler.get().accept(modelType.cast(adapter));
			}
		}
	}
}
