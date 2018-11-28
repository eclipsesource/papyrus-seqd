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

package org.eclipse.papyrus.uml.interaction.internal.model.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.DelegatingEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;

/**
 * A publicly unmodifiable containment list implementation.
 *
 * @author Christian W. Damus
 */
class MContainmentList<E> extends EObjectContainmentEList<E> {

	private static final long serialVersionUID = 1L;

	/**
	 * Initializes me.
	 *
	 * @param dataClass
	 * @param owner
	 * @param featureID
	 */
	public MContainmentList(Class<?> dataClass, InternalEObject owner, int featureID) {
		super(dataClass, owner, featureID);
	}

	@Override
	public boolean add(E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void move(int index, E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E move(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtain a mutable view of this list to support internal mutation operations.
	 * 
	 * @return a mutable view of me
	 */
	@SuppressWarnings("serial")
	public EList<E> mutableList() {
		return new DelegatingEList<E>() {

			@Override
			protected List<E> delegateList() {
				return MContainmentList.this;
			}

			@Override
			public boolean add(E object) {
				return MContainmentList.super.add(object);
			}

			@Override
			public void add(int index, E object) {
				MContainmentList.super.add(index, object);
			}

			@Override
			public boolean addAll(Collection<? extends E> collection) {
				return MContainmentList.super.addAll(collection);
			}

			@Override
			public boolean addAll(int index, Collection<? extends E> collection) {
				return MContainmentList.super.addAll(index, collection);
			}

			@Override
			public E remove(int index) {
				return MContainmentList.super.remove(index);
			}

			@Override
			public boolean remove(Object object) {
				return MContainmentList.super.remove(object);
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				return MContainmentList.super.removeAll(collection);
			}

			@Override
			public E set(int index, E object) {
				return MContainmentList.super.set(index, object);
			}

			@Override
			public void move(int index, E object) {
				MContainmentList.super.move(index, object);
			}

			@Override
			public E move(int targetIndex, int sourceIndex) {
				return MContainmentList.super.move(targetIndex, sourceIndex);
			}

		};
	}
}
