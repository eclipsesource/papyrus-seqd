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
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class MMessageEndItemProvider extends MOccurrenceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MMessageEndItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addSendPropertyDescriptor(object);
			addReceivePropertyDescriptor(object);
			addOtherEndPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Send feature. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addSendPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MMessageEnd_send_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MMessageEnd_send_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MMessageEnd_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MMESSAGE_END__SEND, false, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Receive feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addReceivePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MMessageEnd_receive_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MMessageEnd_receive_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MMessageEnd_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MMESSAGE_END__RECEIVE, false, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Other End feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addOtherEndPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MMessageEnd_otherEnd_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MMessageEnd_otherEnd_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MMessageEnd_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MMESSAGE_END__OTHER_END, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns MMessageEnd.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MMessageEnd")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((MMessageEnd) object).getName();
		return (label == null) || (label.length() == 0) ? getString("_UI_MMessageEnd_type") : //$NON-NLS-1$
				getString("_UI_MMessageEnd_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update
	 * any cached children and by creating a viewer notification, which it passes to
	 * {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(MMessageEnd.class)) {
		case SequenceDiagramPackage.MMESSAGE_END__SEND:
		case SequenceDiagramPackage.MMESSAGE_END__RECEIVE:
		case SequenceDiagramPackage.MMESSAGE_END__OTHER_END:
			fireNotifyChanged(
					new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing
	 * the children that can be created under this object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
