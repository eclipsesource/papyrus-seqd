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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.parsers.LifelineHeaderParser;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.parsers.MessageParser;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.UMLPackage;

public class SequenceParserProvider extends AbstractProvider implements IParserProvider {
	private IParser interaction_NameLabel_Parser;

	private IParser message_NameLabel_Parser;

	private IParser lifeline_NameLabel_Parser;

	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
				case ViewTypes.INTERACTION_NAME:
					return getInteraction_NameLabel_Parser();
				case ViewTypes.MESSAGE_NAME:
					return getMessage_NameLabel_Parser();
				case ViewTypes.LIFELINE_NAME:
					return getLifeline_NameLabel_Parser();
			}
		}
		return null;
	}

	private IParser getMessage_NameLabel_Parser() {
		if (message_NameLabel_Parser == null) {
			message_NameLabel_Parser = new MessageParser();
		}
		return message_NameLabel_Parser;
	}

	private IParser getInteraction_NameLabel_Parser() {
		if (interaction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {UMLPackage.eINSTANCE.getNamedElement_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("sd: {0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			interaction_NameLabel_Parser = parser;
		}
		return interaction_NameLabel_Parser;
	}

	private IParser getLifeline_NameLabel_Parser() {
		if (lifeline_NameLabel_Parser == null) {
			lifeline_NameLabel_Parser = new LifelineHeaderParser();
		}
		return lifeline_NameLabel_Parser;
	}

	public static IParser getParser(IElementType type, EObject object, String parserHint) {
		return ParserService.getInstance().getParser(new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	@Override
	public IParser getParser(IAdaptable hint) {
		String vid = hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(SequenceGraphicalTypeRegistry.getVisualID(vid));
		}
		View view = hint.getAdapter(View.class);
		if (view != null) {
			return getParser(SequenceGraphicalTypeRegistry.getVisualID(view));
		}
		return null;
	}

	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation)operation).getHint();
			if (SequenceElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	private static class HintAdapter extends ParserHintAdapter {

		private final IElementType elementType;

		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		@Override
		public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}
}
