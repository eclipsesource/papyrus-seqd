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

package org.eclipse.papyrus.uml.interaction.graph;

/**
 * Protocol for a graph element that can be tagged.
 *
 * @author Christian W. Damus
 */
public interface Taggable {
	/**
	 * Queries whether I have the given {@code tag} applied to me.
	 * 
	 * @param tag
	 *            a tag
	 * @return whether it is applied to me
	 */
	boolean hasTag(Tag tag);

	/**
	 * Compose two taggables into a compound. It {@linkplain #hasTag(Tag) has a tag} if and only if all
	 * composed taggables have that tag.
	 * 
	 * @param other
	 *            another taggable object
	 * @return the composed taggable
	 */
	Taggable and(Taggable other);
}
