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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util;

import static org.eclipse.uml2.common.util.UML2Util.isEmpty;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Miscellaneous utilities for working with UML {@link NamedElement}s.
 *
 * @author Christian W. Damus
 */
public class NamedElementUtil {

	/**
	 * Not instantiable by clients.
	 */
	private NamedElementUtil() {
		super();
	}

	/**
	 * Compute the qualified name of an {@code element} relative to some other, in whose namespace a by-name
	 * reference is made.
	 * 
	 * @param element
	 *            a named element
	 * @param relativeTo
	 *            another named element owning a reference to the first {@code element}
	 * @return the least qualified name for the {@code element} in the context of the referring element
	 */
	public static String getQualifiedName(NamedElement element, NamedElement relativeTo) {
		String result = new UMLSwitch<String>() {
			@Override
			public String caseMessage(Message message) {
				// There's a special case for references to lifeline properties from messages
				return (element instanceof Property) ? getRelativeName(message, (Property)element) : null;
			}

			@Override
			public String defaultCase(EObject object) {
				// Assume the worst case, but it's also a basis for other cases (primes the CacheAdapter)
				return element.getQualifiedName();
			}
		}.doSwitch(relativeTo);

		if (result == null) {
			// No qualified name? No choice, then
			result = element.getName();
		} else {
			Namespace lca = getCommonNamespace(element, relativeTo);
			if (lca == element) {
				// Addressable just by name (relative to its containing namespace,
				// which is also a common namespace of it and 'relativeTo')
				result = element.getName();
			} else if (lca != null) {
				String lcaQname = lca.getQualifiedName();
				if (!isEmpty(lcaQname)) {
					String prefix = lcaQname + NamedElement.SEPARATOR;
					if (result.startsWith(prefix)) {
						result = result.substring(prefix.length());
					}
				}
			} // otherwise, the fully qualified name
		}

		return result;
	}

	public static Namespace getCommonNamespace(NamedElement element1, NamedElement element2) {
		Element result = lca(element1, element2);

		while (result != null && !(result instanceof Namespace)) {
			result = result.getOwner();
		}

		return (result instanceof Namespace) ? (Namespace)result : null;
	}

	private static Element lca(Element element1, Element element2) {
		Element result = EcoreUtil.isAncestor(element1, element2) //
				? element1
				: EcoreUtil.isAncestor(element2, element1) //
						? element2
						: null;

		if (result == null) {
			// Step up and try again
			Element owner1 = element1.getOwner();
			Element owner2 = element2.getOwner();
			if (owner1 != null && owner2 != null) {
				result = lca(owner1, owner2);
			}
		}

		return result;
	}

	private static String getRelativeName(Message message, Property lifelineAttribute) {
		Optional<Lifeline> receiving = MessageUtil.getCovered(message.getReceiveEvent());
		return receiving.flatMap(MessageUtil::getRepresentedType)
				.filter(type -> type.allAttributes().contains(lifelineAttribute))
				.map(__ -> qname(receiving.get().getName(), lifelineAttribute)).orElse(null);
	}

	private static String qname(String namespace, NamedElement member) {
		String memberName = member.getName();
		return (namespace != null && memberName != null)
				? String.format("%s%s%s", namespace, NamedElement.SEPARATOR, memberName) //$NON-NLS-1$
				: null;
	}
}
