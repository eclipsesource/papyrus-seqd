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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;

/**
 * An iterator of the edit policies of an edit-part that allows to remove or replace edit policies. Adding
 * edit policies is not supported as the main use case for this is overriding the existing edit policies of an
 * edit-part that were added by other parties.
 */
public final class EditPolicyIterator implements Iterator<EditPolicy> {
	private static final Object[] NONE = {};

	private static final EditPolicyIterator EMPTY = new EditPolicyIterator();

	private final AbstractEditPart editPart;

	private Object[] policies = NONE;

	private String role;

	private int cursor;

	/**
	 * Initializes me as the empty iterator.
	 */
	private EditPolicyIterator() {
		this.editPart = null;
	}

	/**
	 * Initializes me as an iterator over the given edit-part's policies.
	 *
	 * @param editPart
	 */
	private EditPolicyIterator(AbstractEditPart editPart) {
		this.editPart = editPart;

		try {
			editPart.getClass().getDeclaredFields();
			Field policiesField = AbstractEditPart.class.getDeclaredField("policies"); //$NON-NLS-1$
			policiesField.setAccessible(true);
			policies = (Object[])policiesField.get(editPart);
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	/**
	 * Obtain an iterator over the edit policies of the given edit-part.
	 * 
	 * @param editPart
	 *            an edit-part to iterate
	 * @return an iterator over its edit policies
	 */
	public static EditPolicyIterator of(EditPart editPart) {
		return (editPart instanceof AbstractEditPart) ? new EditPolicyIterator((AbstractEditPart)editPart)
				: EMPTY;
	}

	@Override
	public boolean hasNext() {
		return cursor < policies.length;
	}

	@Override
	public EditPolicy next() {
		if (cursor >= policies.length) {
			throw new NoSuchElementException();
		}

		// Pick up the role
		role = (String)policies[cursor++];
		EditPolicy result = (EditPolicy)policies[cursor++];
		return result;
	}

	@Override
	public void remove() {
		if (cursor <= 0) {
			throw new NoSuchElementException();
		}

		EditPolicy policy = (EditPolicy)policies[cursor - 1];
		policies[cursor - 1] = null;

		if ((policy != null) && editPart.isActive()) {
			// The edit part had activated the policy, so deactivate it
			policy.deactivate();
		}
	}

	/**
	 * Replace the edit policy for the current {@code #getRole() role}.
	 * 
	 * @param policy
	 *            the new edit policy (may be {@code null}, which would be equivalent to {@link #remove()
	 *            removing} the current policy)
	 */
	public void set(EditPolicy policy) {
		if (cursor <= 0) {
			throw new NoSuchElementException();
		}

		remove();

		policies[cursor - 1] = policy;
		if (policy != null) {
			policy.setHost(editPart);

			if (editPart.isActive()) {
				// The edit part would have activated the policy, so activate it
				policy.activate();
			}
		}
	}

	/**
	 * Query the role of the last returned edit policy.
	 * 
	 * @return the last edit policy role, or {@code null} if no edit policy has been returned
	 */
	public String getRole() {
		return role;
	}
}
