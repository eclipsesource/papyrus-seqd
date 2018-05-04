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

package org.eclipse.papyrus.uml.interaction.internal.graph;

import org.eclipse.papyrus.uml.interaction.graph.Tag;
import org.eclipse.papyrus.uml.interaction.graph.Taggable;

/**
 * Abstract implementation of a taggable graph element.
 *
 * @author Christian W. Damus
 */
abstract class TaggableImpl<T extends TaggableImpl<T>> implements Taggable {

	// Will have to increase this size if we add a 17th tag to the enum
	private int tags = 0;

	/**
	 * Initializes me.
	 */
	TaggableImpl() {
		super();
	}

	@Override
	final public boolean hasTag(Tag tag) {
		return (tags & (1 << tag.ordinal())) != 0;
	}

	@SuppressWarnings("unchecked")
	final T tag(Tag tag) {
		tags |= 1 << tag.ordinal();
		return (T)this;
	}
}
