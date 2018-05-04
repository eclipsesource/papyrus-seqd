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

import com.google.common.collect.MapMaker;

import java.util.Map;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.spi.impl.DefaultDiagramHelper;
import org.eclipse.papyrus.uml.interaction.internal.model.spi.impl.DefaultLayoutHelper;
import org.eclipse.papyrus.uml.interaction.internal.model.spi.impl.DefaultSemanticHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.DiagramHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.osgi.framework.BundleContext;

/**
 * The bundle activator for the <em>Logical Model Plug-in</em>.
 *
 * @author Christian W. Damus
 */
public class LogicalModelPlugin extends EMFPlugin {

	public static LogicalModelPlugin INSTANCE = new LogicalModelPlugin();

	private static Implementation plugin;

	private final Map<EditingDomain, DiagramHelper> diagramHelpers = new MapMaker().weakKeys().weakValues()
			.makeMap();

	private final Map<EditingDomain, LayoutHelper> layoutHelpers = new MapMaker().weakKeys().weakValues()
			.makeMap();

	private final Map<EditingDomain, SemanticHelper> semanticHelpers = new MapMaker().weakKeys().weakValues()
			.makeMap();

	/**
	 * Initializes me.
	 */
	public LogicalModelPlugin() {
		super(new ResourceLocator[0]);
	}

	/**
	 * Obtains the diagram helper for an editing domain.
	 * 
	 * @param editingDomain
	 *            an editing domain
	 * @return its diagram helper
	 */
	public DiagramHelper getDiagramHelper(EditingDomain editingDomain) {
		return diagramHelpers.computeIfAbsent(editingDomain, //
				domain -> new DefaultDiagramHelper(domain, //
						() -> getSemanticHelper(domain), //
						() -> getLayoutHelper(domain)));
	}

	/**
	 * Obtains the layout helper for an editing domain.
	 * 
	 * @param editingDomain
	 *            an editing domain
	 * @return its layout helper
	 */
	public LayoutHelper getLayoutHelper(EditingDomain editingDomain) {
		return layoutHelpers.computeIfAbsent(editingDomain, DefaultLayoutHelper::new);
	}

	/**
	 * Obtains the semantic helper for an editing domain.
	 * 
	 * @param editingDomain
	 *            an editing domain
	 * @return its semantic helper
	 */
	public SemanticHelper getSemanticHelper(EditingDomain editingDomain) {
		return semanticHelpers.computeIfAbsent(editingDomain, DefaultSemanticHelper::new);
	}

	public static LogicalModelPlugin getInstance() {
		return INSTANCE;
	}

	public static Implementation getPlugin() {
		return plugin;
	}

	@Override
	public ResourceLocator getPluginResourceLocator() {
		return getPlugin();
	}

	//
	// Lifecycle
	//

	public static final class Implementation extends EMFPlugin.EclipsePlugin {
		public Implementation() {
			super();
		}

		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);

			plugin = this;
		}

		@Override
		public void stop(BundleContext context) throws Exception {
			if (plugin == this) {
				plugin = null;
			}

			super.stop(context);
		}
	}
}
