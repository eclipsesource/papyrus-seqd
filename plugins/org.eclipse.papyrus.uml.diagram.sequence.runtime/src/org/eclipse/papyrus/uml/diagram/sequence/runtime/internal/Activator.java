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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutHelper;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	/** plug-in identifier */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.uml.diagram.sequence.runtime"; //$NON-NLS-1$

	public static final PreferencesHint DIAGRAM_PREFERENCES_HINT = new PreferencesHint(PLUGIN_ID);

	/** Logging helper */
	public static LogHelper log;

	private static Activator instance;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
		PreferencesHint.registerPreferenceStore(DIAGRAM_PREFERENCES_HINT, getPreferenceStore());
		log = new LogHelper(instance);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return instance;
	}

	/**
	 * Obtains the layout helper for an editing domain.
	 * 
	 * @param editingDomain
	 *            an editing domain
	 * @return its layout helper
	 */
	public LayoutHelper getLayoutHelper(EditingDomain editingDomain) {
		return LogicalModelPlugin.INSTANCE.getLayoutHelper(editingDomain);
	}
}
