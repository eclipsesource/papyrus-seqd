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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.LabelLocator;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.papyrus.infra.gmfdiag.common.parsers.ParserUtil;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.providers.SequenceElementTypes;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.uml2.uml.MessageSort;

public class MessageLabelEditPart extends LabelEditPart implements ITextAwareEditPart, ISequenceEditPart {
	private IParser parser;

	/** the element to listen to as suggested by the parser */
	private List<?> parserElements = null;

	private WrappingLabel label;

	static {
		registerSnapBackPosition(ViewTypes.MESSAGE_NAME, new Point(1, -13));
	}

	public MessageLabelEditPart(View view) {
		super(view);
	}

	@Override
	protected IFigure createFigure() {
		label = new WrappingLabel();
		return label;
	}

	@Override
	protected void addSemanticListeners() {
		if (getParser() instanceof ISemanticParser) {
			EObject semanticElement = resolveSemanticElement();
			parserElements = ((ISemanticParser)getParser()).getSemanticElementsBeingParsed(semanticElement);

			for (int i = 0; i < parserElements.size(); i++) {
				addListenerFilter("SemanticModel" + i, this, (EObject)parserElements.get(i)); //$NON-NLS-1$
			}

		} else {
			super.addSemanticListeners();
		}
	}

	@Override
	protected void removeSemanticListeners() {
		if (parserElements != null) {
			for (int i = 0; i < parserElements.size(); i++) {
				removeListenerFilter("SemanticModel" + i); //$NON-NLS-1$
			}
		} else {
			super.removeSemanticListeners();
		}
	}

	protected EObject getParserElement() {
		return resolveSemanticElement();
	}

	@Override
	protected void handleNotificationEvent(Notification event) {
		if (getParser() != null) {

			boolean sematicsAffected = getParser() instanceof ISemanticParser
					&& ((ISemanticParser)getParser()).areSemanticElementsAffected(null, event);

			boolean parserAffected = getParser().isAffectingEvent(event, getParserOptions().intValue());

			if (sematicsAffected) {
				removeSemanticListeners();

				if (resolveSemanticElement() != null) {
					addSemanticListeners();
				}
			}

			if (sematicsAffected || parserAffected) {
				refreshLabel();
			}
			super.handleNotificationEvent(event);
		}

	}

	private void refreshLabel() {
		label.setText(getLabelText());
	}

	@Override
	public void refresh() {
		super.refresh();
		refreshLabel();
		refreshFont();
	}

	@Override
	public void refreshBounds() {
		int dx = ((Integer)getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue();
		int dy = ((Integer)getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();

		int offsetX = dx + getLayoutConstraints().getXOffset(ViewTypes.MESSAGE_NAME);
		int offsetY = dy + getLayoutConstraints().getYOffset(ViewTypes.MESSAGE_NAME);
		Point offset = new Point(offsetX, offsetY);
		if (getParent() instanceof AbstractConnectionEditPart) {
			((AbstractGraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), new LabelLocator(
					((AbstractConnectionEditPart)getParent()).getConnectionFigure(), offset, getKeyPoint()));
		}
	}

	public String getLabelText() {
		EObject element = resolveSemanticElement();
		return (element == null) ? null
				: (getParser() == null) ? null
						: getParser().getPrintString(new EObjectAdapter(element),
								getParserOptions().intValue());
	}

	@Override
	public IParser getParser() {
		if (parser == null) {
			IElementType type = getElementType();
			if (type != null) {
				parser = ParserUtil.getParser(type, getParserElement(), this, ViewTypes.MESSAGE_NAME);
			}
		}
		return parser;
	}

	public MessageEditPart getMessageEditPart() {
		if (getParent() instanceof MessageEditPart) {
			return (MessageEditPart)getParent();
		}
		return null;
	}

	protected IElementType getElementType() {
		IElementType type = null;
		MessageEditPart editPart = getMessageEditPart();
		if (editPart != null) {
			switch (editPart.getMessage().getMessageSort().getValue()) {
				case MessageSort.ASYNCH_CALL:
					type = SequenceElementTypes.Async_Message_Edge;
					break;
				case MessageSort.CREATE_MESSAGE:
					type = SequenceElementTypes.Create_Message_Edge;
					break;
				case MessageSort.DELETE_MESSAGE:
					type = SequenceElementTypes.Delete_Message_Edge;
					break;
				case MessageSort.SYNCH_CALL:
					type = SequenceElementTypes.Sync_Message_Edge;
					break;
				case MessageSort.REPLY:
					type = SequenceElementTypes.Reply_Message_Edge;
					break;
			}
		}
		return type;
	}

	@Override
	public ParserOptions getParserOptions() {
		return ParserOptions.NONE;
	}

	public WrappingLabel getLabel() {
		return label;
	}

	@Override
	public String getEditText() {
		if (getParserElement() == null || getParser() == null) {
			return ""; //$NON-NLS-1$
		}
		return getParser().getEditString(ParserUtil.getParserAdapter(getParserElement(), this),
				getParserOptions().intValue());
	}

	@Override
	public void setLabelText(String text) {
		label.setText(text);

	}

	@Override
	public ICellEditorValidator getEditTextValidator() {
		return (value) -> {
			if (value instanceof String) {
				return validate((String)value);
			}
			return null;

		};
	}

	private String validate(String text) {
		try {
			IParserEditStatus valid = (IParserEditStatus)getEditingDomain()
					.runExclusive(new RunnableWithResult.Impl<java.lang.Object>() {

						@Override
						public void run() {
							setResult(getParser().isValidEditString(ParserUtil
									.getParserAdapter(getParserElement(), MessageLabelEditPart.this), text));
						}
					});
			return valid.getCode() == IParserEditStatus.EDITABLE ? null : valid.getMessage();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		// shouldn't get here
		return null;
	}

	@Override
	public IContentAssistProcessor getCompletionProcessor() {
		if (getParserElement() == null || getParser() == null) {
			return null;
		}
		return getParser().getCompletionProcessor(ParserUtil.getParserAdapter(getParserElement(), this));
	}

	@Override
	protected void refreshFont() {

		FontStyle style = (FontStyle)((View)getModel()).getStyle(NotationPackage.eINSTANCE.getFontStyle());
		FontData fontData = null;

		if (style != null) {
			fontData = new FontData(style.getFontName(), style.getFontHeight(),
					(style.isBold() ? SWT.BOLD : SWT.NORMAL) | (style.isItalic() ? SWT.ITALIC : SWT.NORMAL));
		} else {
			// initialize font to defaults
			fontData = PreferenceConverter.getFontData(
					(IPreferenceStore)getDiagramPreferencesHint().getPreferenceStore(),

					IPreferenceConstants.PREF_DEFAULT_FONT);
		}

		setFont(fontData);
	}

}
