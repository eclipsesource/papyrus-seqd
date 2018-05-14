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

package org.eclipse.papyrus.uml.interaction.tests.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

/**
 * A simple implementation of the {@link IAdaptable} protocol for testing APIs
 * that require adaptables. It returns the first object of a collection, in
 * priority order, that is a proper instance of the requested adapter type.
 * Non-type adaptation is not supported.
 *
 * @author Christian W. Damus
 */
public class AdaptableByType implements IAdaptable {
	private final List<?> adapters;

	/**
	 * Initializes me with adapter objects in the order that they should be tested
	 *
	 * @param adapter
	 *            the adapters
	 */
	public AdaptableByType(Object... adapter) {
		super();

		adapters = new ArrayList<>(Arrays.asList(adapter));

		assertThat("adaptable provides no adapters", adapters.isEmpty(), is(false));
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return adapters.stream().sequential().filter(adapter::isInstance).findFirst().map(adapter::cast)
				.orElse(null);
	}

}
