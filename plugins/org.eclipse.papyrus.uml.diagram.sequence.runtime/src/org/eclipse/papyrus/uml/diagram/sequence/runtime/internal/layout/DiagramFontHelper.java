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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.layout;

import java.util.OptionalInt;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.interaction.model.spi.FontHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * An implementation of the {@link FontHelper} protocol that understands the configuration of fonts in the
 * diagrams and has access to font metrics via SWT.
 *
 * @author Christian W. Damus
 */
public class DiagramFontHelper implements FontHelper {

	private final Dimension scratch = new Dimension();

	/**
	 * Initializes me.
	 */
	public DiagramFontHelper() {
		super();
	}

	@Override
	public String getFontName(View view) {
		return (String)ViewUtil.getPropertyValue(view, NotationPackage.Literals.FONT_STYLE__FONT_NAME,
				NotationPackage.Literals.FONT_STYLE);
	}

	@Override
	public OptionalInt getFontHeight(View view) {
		Font font = getFont(view);

		return font == null ? OptionalInt.empty() : OptionalInt.of(getHeight(view, font));
	}

	protected Font getFont(View view) {
		Font result = null;

		FontData fontData = null;
		FontStyle style = (FontStyle)view.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (style != null) {
			fontData = new FontData(style.getFontName(), style.getFontHeight(),
					(style.isBold() ? SWT.BOLD : SWT.NORMAL) | (style.isItalic() ? SWT.ITALIC : SWT.NORMAL));
		} else {
			// initialize font to defaults
			fontData = PreferenceConverter.getFontData(Activator.getDefault().getPreferenceStore(),
					IPreferenceConstants.PREF_DEFAULT_FONT);
		}

		if (fontData != null) {
			FontDescriptor desc = FontDescriptor.createFrom(fontData);
			result = (Font)JFaceResources.getResources().get(desc);
		}

		return result;
	}

	protected int getHeight(View view, Font font) {
		// Very approximate
		String text = String.valueOf(view.getElement());

		FigureUtilities.getTextExtents(text, font, scratch);
		return scratch.height();
	}
}
