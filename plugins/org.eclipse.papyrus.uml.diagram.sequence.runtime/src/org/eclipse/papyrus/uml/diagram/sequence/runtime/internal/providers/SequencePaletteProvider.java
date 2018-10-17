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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.IPaletteProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.PaletteFactory;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.PaletteService;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.ExtendedConnectionToolEntry;
import org.eclipse.papyrus.uml.diagram.sequence.figure.magnets.IMagnetManager;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequencePaletteFactory;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.tools.SequenceSelectionTool;
import org.eclipse.ui.IEditorPart;

/**
 * A palette provider for sequence diagrams that injects custom tools, including
 * <ul>
 * <li>a {@link SelectionTool} that integrates with the {@linkplain IMagnetManager magnet manager}
 * <li>{@linkplain PaletteFactory palette factories} for {@linkplain ToolEntry tool entries} that create
 * connection creation tools that integrate with the {@linkplain IMagnetManager magnet manager}</li>
 * </ul>
 */
public class SequencePaletteProvider extends AbstractProvider implements IPaletteProvider {

	private static Consumer<ExtendedConnectionToolEntry> connectionToolEntryDelegator;

	/**
	 * Initializes me.
	 */
	public SequencePaletteProvider() {
		super();
	}

	@Override
	public boolean provides(IOperation operation) {
		// Papyrus palette service never invokes this. Only filtering in the plugin.xml is supported
		return true;
	}

	@Override
	public void contributeToPalette(IEditorPart editor, Object content, PaletteRoot root,
			@SuppressWarnings("rawtypes") Map predefinedEntries) {

		for (Iterator<PaletteEntry> iter = allContents(root); iter.hasNext();) {
			PaletteEntry next = iter.next();
			if ((next instanceof ToolEntry) && PaletteService.TOOL_SELECTION.equals(next.getId())) {
				((ToolEntry)next).setToolClass(SequenceSelectionTool.class);
			} else if (next instanceof ExtendedConnectionToolEntry) {
				ExtendedConnectionToolEntry connection = (ExtendedConnectionToolEntry)next;
				setToolFactory(connection);
			}
		}
	}

	@Override
	public void setContributions(IConfigurationElement configElement) {
		// We don't need to know this
	}

	@SuppressWarnings("serial")
	TreeIterator<PaletteEntry> allContents(PaletteRoot root) {
		return new AbstractTreeIterator<PaletteEntry>(root) {
			@Override
			protected Iterator<PaletteEntry> getChildren(@SuppressWarnings("hiding") Object object) {
				if (object instanceof PaletteContainer) {
					@SuppressWarnings("unchecked")
					List<PaletteEntry> entries = ((PaletteContainer)object).getChildren();
					return entries.iterator();
				}
				return Collections.emptyIterator();
			}
		};
	}

	private static void setToolFactory(ExtendedConnectionToolEntry connectionToolEntry) {
		getConnectionToolEntryDelegator().accept(connectionToolEntry);
	}

	private static Consumer<ExtendedConnectionToolEntry> getConnectionToolEntryDelegator() {
		if (connectionToolEntryDelegator == null) {
			try {
				@SuppressWarnings("restriction")
				Field factoryField = org.eclipse.gmf.runtime.diagram.ui.internal.services.palette.PaletteToolEntry.class
						.getDeclaredField("factory"); //$NON-NLS-1$
				factoryField.setAccessible(true);
				connectionToolEntryDelegator = tool -> {
					try {
						PaletteFactory delegate = (PaletteFactory)factoryField.get(tool);
						factoryField.set(tool, new SequencePaletteFactory(delegate));
					} catch (Exception e) {
						// Shouldn't be happening if we found the field and made it accessible
						connectionToolEntryDelegator = Objects::requireNonNull;
					}
				};
			} catch (Exception e) {
				Activator.log.error("Cannot inject magnet support into connection tools.", e); //$NON-NLS-1$
				connectionToolEntryDelegator = Objects::requireNonNull;
			}
		}

		return connectionToolEntryDelegator;
	}
}
