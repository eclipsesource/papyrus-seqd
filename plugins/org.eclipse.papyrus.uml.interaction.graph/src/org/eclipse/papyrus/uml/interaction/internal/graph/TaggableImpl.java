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

import java.util.function.Consumer;
import java.util.function.Function;

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

	private TaggableImpl(int tags) {
		this();

		this.tags = tags;
	}

	@Override
	final public boolean hasTag(Tag tag) {
		return (tags & (1 << tag.ordinal())) != 0;
	}

	final void copyTags(TaggableImpl<T> copyFrom) {
		this.tags = copyFrom.tags;
	}

	@SuppressWarnings("unchecked")
	T tag(Tag tag) {
		tags |= 1 << tag.ordinal();
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	T untag(Tag tag) {
		tags &= ~(1 << tag.ordinal());
		return (T)this;
	}

	@Override
	public final TaggablePair and(Taggable other) {
		return new TaggablePair(this, (TaggableImpl<?>)other);
	}

	static <T extends TaggableImpl<T>> Function<T, TaggableImpl<?>> andTag(
			Function<? super T, ? extends TaggableImpl<?>> other) {

		return taggable -> taggable.and(other.apply(taggable));
	}

	static Consumer<TaggableImpl<?>> tagging(Tag tag) {
		return taggable -> taggable.tag(tag);
	}

	static Consumer<TaggableImpl<?>> untagging(Tag tag) {
		return taggable -> taggable.untag(tag);
	}

	//
	// Nested types
	//

	static class TaggablePair extends TaggableImpl<TaggablePair> {
		private final TaggableImpl<?> first;

		private final TaggableImpl<?> second;

		TaggablePair(TaggableImpl<?> first, TaggableImpl<?> second) {
			super(first.tags & second.tags);

			this.first = first;
			this.second = second;
		}

		@Override
		TaggablePair tag(Tag tag) {
			first.tag(tag);
			second.tag(tag);
			return super.tag(tag);
		}

		@Override
		TaggablePair untag(Tag tag) {
			first.untag(tag);
			second.untag(tag);
			return super.untag(tag);
		}
	}
}
