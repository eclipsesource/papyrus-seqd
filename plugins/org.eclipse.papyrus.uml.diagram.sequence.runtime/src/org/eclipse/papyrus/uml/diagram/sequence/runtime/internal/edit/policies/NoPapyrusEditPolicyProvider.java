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
package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import java.lang.reflect.Field;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.Activator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.InteractionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.LifelineBodyEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.parts.RepresentationKind;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * A High-priority edit policy provider that uninstalls all Papyrus Edit Policies
 */
// XXX In Papyrus 4.x/Photon, we can use EditPolicyProviderTesters, to selectively disable policy provides
// before they have a chance to install anything.
public class NoPapyrusEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	private static final String PAPYRUS_BUNDLE_PREFIX = "org.eclipse.papyrus"; //$NON-NLS-1$

	@Override
	public boolean provides(IOperation operation) {
		if (false == operation instanceof CreateEditPoliciesOperation) {
			return false;
		}

		CreateEditPoliciesOperation op = (CreateEditPoliciesOperation)operation;
		View view = NotationHelper.findView(op.getEditPart());
		if (view == null || view.getDiagram() == null) {
			return false;
		}
		return RepresentationKind.MODEL_ID.equals(view.getDiagram().getType());
	}

	@Override
	public void createEditPolicies(EditPart editPart) {
		uninstallPapyrusEditPolicies(editPart);
		restoreCreationPolicy(editPart);
	}

	/*
	 * Papyrus replaces the CreationEditPolicy with a CustomizableDragAndDropPolicy (Which then delegates to
	 * the initial CreationEditPolicy when required). Since we uninstalled all Papyrus Policies, we need to
	 * restore the original CreationEditPolicy
	 */
	private void restoreCreationPolicy(EditPart editPart) {
		if (editPart instanceof LifelineBodyEditPart) {
			editPart.installEditPolicy(EditPolicyRoles.CREATION_ROLE, new LifelineCreationEditPolicy());
		} else if (editPart instanceof InteractionCompartmentEditPart) {
			editPart.installEditPolicy(EditPolicyRoles.CREATION_ROLE, new InteractionCreationEditPolicy());
		}
	}

	/*
	 * GEF/GMF doesn't provide any (public) way to iterate on existing policies. Even the protected edit
	 * policy iterator only provides the instance; not the role. This is not sufficient, because we can only
	 * uninstall edit policies by role.
	 */
	private void uninstallPapyrusEditPolicies(EditPart editPart) {
		if (editPart instanceof AbstractEditPart) {
			Object[] policies;
			try {
				editPart.getClass().getDeclaredFields();
				Field policiesField = AbstractEditPart.class.getDeclaredField("policies"); //$NON-NLS-1$
				policiesField.setAccessible(true);
				policies = (Object[])policiesField.get(editPart);
			} catch (Exception ex) {
				Activator.log.error(ex);
				return;
			}

			for (int i = 0; i < policies.length; i++) {
				if (policies[i] instanceof EditPolicy) {
					EditPolicy policy = (EditPolicy)policies[i];
					if (shouldRemovePolicy(policy)) {
						policies[i] = null;
					}
				}
			}
		}
	}

	private boolean shouldRemovePolicy(EditPolicy policy) {
		Bundle bundle = FrameworkUtil.getBundle(policy.getClass());
		String symbolicName = bundle.getSymbolicName();
		if (symbolicName.startsWith(PAPYRUS_BUNDLE_PREFIX) && !Activator.PLUGIN_ID.equals(symbolicName)) {
			return true;
		}

		// Keep GEF/GMF and our own policies
		return false;
	}

}
