/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.DiagramElementTypes;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.DiagramElementTypeImages;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper.ViewTypes;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.UMLPackage;

public final class SequenceElementTypes {

	private SequenceElementTypes() {
	}

	private static Map<IElementType, ENamedElement> elements;

	private static DiagramElementTypeImages elementTypeImages = new DiagramElementTypeImages(
			org.eclipse.papyrus.infra.gmfdiag.common.Activator.getInstance()
					.getItemProvidersAdapterFactory());

	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	public static final IElementType Package_SequenceDiagram = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.Package_SequenceDiagram"); //$NON-NLS-1$

	public static final IElementType Interaction_Shape = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.Interaction_Shape"); //$NON-NLS-1$

	public static final IElementType Lifeline_Shape = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.Lifeline_Shape"); //$NON-NLS-1$

	public static final IElementType Action_Execution_Shape = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.ActionExecutionSpecification_Shape"); //$NON-NLS-1$

	public static final IElementType Behavior_Execution_Shape = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.BehaviorExecutionSpecification_Shape"); //$NON-NLS-1$

	public static final IElementType Async_Message_Edge = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.Message_AsynchEdge"); //$NON-NLS-1$

	public static final IElementType Sync_Message_Edge = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.Message_SynchEdge"); //$NON-NLS-1$

	public static final IElementType Reply_Message_Edge = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.Message_ReplyEdge"); //$NON-NLS-1$

	public static final IElementType Create_Message_Edge = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.Message_CreateEdge"); //$NON-NLS-1$

	public static final IElementType Delete_Message_Edge = getElementTypeByUniqueId(
			"org.eclipse.papyrus.umldi.Message_DeleteEdge"); //$NON-NLS-1$

	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		return elementTypeImages.getImageDescriptor(element);
	}

	public static Image getImage(ENamedElement element) {
		return elementTypeImages.getImage(element);
	}

	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		return getImageDescriptor(getElement(hint));
	}

	public static Image getImage(IAdaptable hint) {
		return getImage(getElement(hint));
	}

	public static synchronized ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap<IElementType, ENamedElement>();

			elements.put(Package_SequenceDiagram, UMLPackage.eINSTANCE.getPackage());

			elements.put(Interaction_Shape, UMLPackage.eINSTANCE.getInteraction());

		}
		return elements.get(type);
	}

	private static IElementType getElementTypeByUniqueId(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	public static synchronized boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet<IElementType>();
			KNOWN_ELEMENT_TYPES.add(Package_SequenceDiagram);
			KNOWN_ELEMENT_TYPES.add(Interaction_Shape);
		}

		boolean result = KNOWN_ELEMENT_TYPES.contains(elementType);

		if (!result) {
			IElementType[] supertypes = elementType.getAllSuperTypes();
			for (int i = 0; !result && (i < supertypes.length); i++) {
				result = KNOWN_ELEMENT_TYPES.contains(supertypes[i]);
			}
		}

		return result;
	}

	public static IElementType getElementType(String visualID) {
		if (visualID != null) {
			switch (visualID) {
				case ViewTypes.SEQUENCE_DIAGRAM:
					return Package_SequenceDiagram;
				case ViewTypes.INTERACTION:
					return Interaction_Shape;
			}
		}
		return null;
	}

	public static final DiagramElementTypes TYPED_INSTANCE = new DiagramElementTypes(elementTypeImages) {

		@Override
		public boolean isKnownElementType(IElementType elementType) {
			return SequenceElementTypes.isKnownElementType(elementType);
		}

		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return SequenceElementTypes.getElementType(visualID);
		}

		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return SequenceElementTypes.getElement(elementTypeAdapter);
		}
	};

	public static boolean isKindOf(IElementType subtype, IElementType supertype) {
		boolean result = subtype == supertype;
		if (!result) {
			IElementType[] supertypes = subtype.getAllSuperTypes();
			for (int i = 0; !result && (i < supertypes.length); i++) {
				result = supertype == supertypes[i];
			}
		}
		return result;
	}
}
