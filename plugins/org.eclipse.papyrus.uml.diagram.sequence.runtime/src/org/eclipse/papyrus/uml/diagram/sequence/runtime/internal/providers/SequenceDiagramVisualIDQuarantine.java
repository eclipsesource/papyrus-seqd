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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.RepresentationKind;

/**
 * Access to the sequence diagram visual IDs targeted by label parsers and editors, isolated from the question
 * of which diagram implementation is used.
 *
 * @author Christian W. Damus
 */
public class SequenceDiagramVisualIDQuarantine {

	private final Set<String> messageLabels;

	/**
	 * Not instantiable by clients
	 */
	private SequenceDiagramVisualIDQuarantine(Collection<String> messageLabels) {
		super();

		this.messageLabels = new HashSet<String>(messageLabels);
	}

	public boolean isKnownVisualElement(IAdaptable hint) {
		boolean result = SequenceElementTypes.getElement(hint) != null;

		if (!result) {
			// Maybe it's a message label, which varies by subclass
			String visualID = hint.getAdapter(String.class);
			result = isMessageLabel(visualID);
		}

		return result;
	}

	public boolean isMessageLabel(String visualID) {
		return messageLabels.contains(visualID);
	}

	//
	// Nested types
	//

	/**
	 * Quarantine of the lightweight sequence diagram visual IDs.
	 *
	 * @author Christian W. Damus
	 */
	public static final class Lightweight extends SequenceDiagramVisualIDQuarantine {
		public Lightweight() {
			super(Arrays.asList(RepresentationKind.ASYNC_MESSAGE_NAME_ID));
		}
	}

	/**
	 * Quarantine of the Papyrus sequence diagram visual IDs for compatibility.
	 *
	 * @author Christian W. Damus
	 */
	public static final class Compatibility extends SequenceDiagramVisualIDQuarantine {
		@SuppressWarnings("nls")
		public Compatibility() {
			super(Arrays.asList("Message_AsynchNameLabel", "Message_SynchNameLabel", "Message_LostNameLabel",
					"Message_FoundNameLabel", "Message_CreateNameLabel", "Message_DeleteNameLabel",
					"Message_ReplyNameLabel"));
		}
	}
}
