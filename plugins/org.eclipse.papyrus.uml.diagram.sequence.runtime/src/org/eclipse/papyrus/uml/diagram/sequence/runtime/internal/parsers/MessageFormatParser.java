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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.parsers;

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.text.ParsePosition;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.parsers.AbstractElementTypeBasedAttributeParser;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Messages;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

public class MessageFormatParser extends AbstractElementTypeBasedAttributeParser {

	private String defaultPattern;

	private String defaultEditablePattern;

	private MessageFormat viewProcessor;

	private MessageFormat editorProcessor;

	private MessageFormat editProcessor;

	public MessageFormatParser(EAttribute[] features) {
		super(features);
	}

	public MessageFormatParser(EAttribute[] features, EAttribute[] editableFeatures) {
		super(features, editableFeatures);
	}

	protected String getDefaultPattern() {
		if (defaultPattern == null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < features.length; i++) {
				if (i > 0) {
					sb.append(' ');
				}
				sb.append('{');
				sb.append(i);
				sb.append('}');
			}
			defaultPattern = sb.toString();
		}
		return defaultPattern;
	}

	@Override
	public void setViewPattern(String viewPattern) {
		super.setViewPattern(viewPattern);
		viewProcessor = null;
	}

	@Override
	public void setEditorPattern(String editorPattern) {
		super.setEditorPattern(editorPattern);
		editorProcessor = null;
	}

	protected MessageFormat getViewProcessor() {
		if (viewProcessor == null) {
			viewProcessor = new MessageFormat(getViewPattern() == null ? getDefaultPattern() : getViewPattern());
		}
		return viewProcessor;
	}

	protected MessageFormat getEditorProcessor() {
		if (editorProcessor == null) {
			editorProcessor = new MessageFormat(
					getEditorPattern() == null ? getDefaultEditablePattern() : getEditorPattern());
		}
		return editorProcessor;
	}

	protected String getDefaultEditablePattern() {
		if (defaultEditablePattern == null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < editableFeatures.length; i++) {
				if (i > 0) {
					sb.append(' ');
				}
				sb.append('{');
				sb.append(i);
				sb.append('}');
			}
			defaultEditablePattern = sb.toString();
		}
		return defaultEditablePattern;
	}

	@Override
	public void setEditPattern(String editPattern) {
		super.setEditPattern(editPattern);
		editProcessor = null;
	}

	protected MessageFormat getEditProcessor() {
		if (editProcessor == null) {
			editProcessor = new MessageFormat(
					getEditPattern() == null ? getDefaultEditablePattern() : getEditPattern());
		}
		return editProcessor;
	}

	@Override
	public String getEditString(IAdaptable adapter, int flags) {
		EObject element = adapter.getAdapter(EObject.class);
		return getEditorProcessor().format(getEditableValues(element), new StringBuffer(), new FieldPosition(0))
				.toString();
	}

	@Override
	public IParserEditStatus isValidEditString(IAdaptable adapter, String editString) {
		ParsePosition pos = new ParsePosition(0);
		Object[] values = getEditProcessor().parse(editString, pos);
		if (values == null) {
			return new ParserEditStatus(Activator.PLUGIN_ID, IParserEditStatus.UNEDITABLE,
					NLS.bind(Messages.MessageFormatParser_InvalidInputError, new Integer(pos.getErrorIndex())));
		}
		return validateNewValues(values);
	}

	@Override
	public ICommand getParseCommand(IAdaptable adapter, String newString, int flags) {
		Object[] values = getEditProcessor().parse(newString, new ParsePosition(0));
		return getParseCommand(adapter, values, flags);
	}

	@Override
	public String getPrintString(IAdaptable adapter, int flags) {
		EObject element = adapter.getAdapter(EObject.class);
		return getViewProcessor().format(getValues(element), new StringBuffer(), new FieldPosition(0)).toString();
	}

	@Override
	protected ICommand getModificationCommand(final EObject element, final EStructuralFeature feature,
			final Object value) {
		ICommand result = null;

		// If the feature to edit is the name, check that this is not really the internationalization to edit and not the name
		if (feature.equals(UMLPackage.eINSTANCE.getNamedElement_Name())) {
			if (InternationalizationPreferencesUtils.getInternationalizationPreference(element)
					&& null != UMLLabelInternationalization.getInstance().getLabelWithoutUML((NamedElement) element)) {
				final ModelSet modelSet = (ModelSet) element.eResource().getResourceSet();
				if (null != modelSet) {
					result = new EMFtoGMFCommandWrapper(UMLLabelInternationalization.getInstance().getSetLabelCommand(
							modelSet.getTransactionalEditingDomain(), (NamedElement) element, (String) value, null));
				}
			}
		}

		return null != result ? result : super.getModificationCommand(element, feature, value);
	}

	@Override
	protected Object getValue(final EObject element, final EStructuralFeature feature) {
		Object result = null;

		if (element instanceof NamedElement && feature.equals(UMLPackage.eINSTANCE.getNamedElement_Name())) {
			if (InternationalizationPreferencesUtils.getInternationalizationPreference(element)
					&& null != UMLLabelInternationalization.getInstance().getLabelWithoutUML((NamedElement) element)) {
				result = UMLLabelInternationalization.getInstance().getLabelWithoutUML((NamedElement) element);
			}
		}

		return null != result ? result : super.getValue(element, feature);
	}
}
