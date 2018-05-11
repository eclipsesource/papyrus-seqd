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

import static java.util.Collections.emptyMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
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

	private final Map<Class<? extends EditPart>, Map<String, Supplier<? extends EditPolicy>>> substitutions = new HashMap<>();

	/**
	 * Initializes me.
	 */
	public NoPapyrusEditPolicyProvider() {
		super();

		// Creation edit policies
		substitute(LifelineBodyEditPart.class, EditPolicyRoles.CREATION_ROLE,
				LifelineCreationEditPolicy::new);
		substitute(InteractionCompartmentEditPart.class, EditPolicyRoles.CREATION_ROLE,
				InteractionCreationEditPolicy::new);

		// Diagram assistant edit policies
		substitute(LifelineBodyEditPart.class, EditPolicyRoles.POPUPBAR_ROLE,
				SequenceDiagramPopupBarEditPolicy::new);
		substitute(InteractionCompartmentEditPart.class, EditPolicyRoles.POPUPBAR_ROLE,
				SequenceDiagramPopupBarEditPolicy::new);
		substitute(LifelineBodyEditPart.class, EditPolicyRoles.CONNECTION_HANDLES_ROLE,
				SequenceDiagramConnectionHandleEditPolicy::new);
	}

	private void substitute(Class<? extends EditPart> editPartType, String role,
			Supplier<? extends EditPolicy> editPolicySupplier) {
		Map<String, Supplier<? extends EditPolicy>> policies = substitutions.computeIfAbsent(editPartType,
				__ -> new HashMap<>());
		policies.put(role, editPolicySupplier);
	}

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
		// We don't create edit policies, but remove or substitute those that came from Papyrus
		substitutePapyrusEditPolicies(editPart);
	}

	/*
	 * GEF/GMF doesn't provide any (public) way to iterate on existing policies. Even the protected edit
	 * policy iterator only provides the instance; not the role. This is not sufficient, because we can only
	 * uninstall edit policies by role.
	 */
	private void substitutePapyrusEditPolicies(EditPart editPart) {
		for (EditPolicyIterator iter = EditPolicyIterator.of(editPart); iter.hasNext();) {
			EditPolicy next = iter.next();
			EditPolicy substitute = substitutions //
					.getOrDefault(editPart.getClass(), emptyMap()) //
					.getOrDefault(iter.getRole(), this::nothing) //
					.get();
			if (((next != null) && shouldRemovePolicy(next)) || (substitute != null)) {
				if (substitute == null) {
					iter.remove();
				} else {
					iter.set(substitute);
				}
			}
		}
	}

	private <T> T nothing() {
		return null;
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

	//
	// Nested types
	//

}
