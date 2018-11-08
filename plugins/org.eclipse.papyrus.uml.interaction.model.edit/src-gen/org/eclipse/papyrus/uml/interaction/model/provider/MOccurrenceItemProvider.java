/**
 * Copyright (c) 2018 Christian W. Damus and others.
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.uml.interaction.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.uml.interaction.model.MOccurrence}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class MOccurrenceItemProvider extends MElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public MOccurrenceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addCoveredPropertyDescriptor(object);
			addStartPropertyDescriptor(object);
			addStartedExecutionPropertyDescriptor(object);
			addFinishPropertyDescriptor(object);
			addFinishedExecutionPropertyDescriptor(object);
			addExecutionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Covered feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addCoveredPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MOccurrence_covered_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MOccurrence_covered_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MOccurrence_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MOCCURRENCE__COVERED, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Start feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addStartPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MOccurrence_start_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MOccurrence_start_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MOccurrence_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MOCCURRENCE__START, false, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Started Execution feature. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addStartedExecutionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MOccurrence_startedExecution_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MOccurrence_startedExecution_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MOccurrence_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MOCCURRENCE__STARTED_EXECUTION, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Finish feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addFinishPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MOccurrence_finish_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MOccurrence_finish_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MOccurrence_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MOCCURRENCE__FINISH, false, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Finished Execution feature. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addFinishedExecutionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MOccurrence_finishedExecution_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MOccurrence_finishedExecution_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MOccurrence_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MOCCURRENCE__FINISHED_EXECUTION, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Execution feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	protected void addExecutionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MOccurrence_execution_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MOccurrence_execution_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MOccurrence_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MOCCURRENCE__EXECUTION, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((MOccurrence<?>)object).getName();
		return (label == null) || (label.length() == 0) ? getString("_UI_MOccurrence_type") : //$NON-NLS-1$
				getString("_UI_MOccurrence_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached children and
	 * by creating a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(MOccurrence.class)) {
			case SequenceDiagramPackage.MOCCURRENCE__COVERED:
			case SequenceDiagramPackage.MOCCURRENCE__START:
			case SequenceDiagramPackage.MOCCURRENCE__STARTED_EXECUTION:
			case SequenceDiagramPackage.MOCCURRENCE__FINISH:
			case SequenceDiagramPackage.MOCCURRENCE__FINISHED_EXECUTION:
			case SequenceDiagramPackage.MOCCURRENCE__EXECUTION:
				fireNotifyChanged(
						new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be
	 * created under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
