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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MMessage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.uml.interaction.model.MMessage} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class MMessageItemProvider extends MElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public MMessageItemProvider(AdapterFactory adapterFactory) {
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

			addSenderPropertyDescriptor(object);
			addReceiverPropertyDescriptor(object);
			addSynchronousPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Sender feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addSenderPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MMessage_sender_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MMessage_sender_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MMessage_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MMESSAGE__SENDER, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Receiver feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addReceiverPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MMessage_receiver_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MMessage_receiver_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MMessage_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MMESSAGE__RECEIVER, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Synchronous feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	protected void addSynchronousPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MMessage_synchronous_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MMessage_synchronous_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MMessage_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MMESSAGE__SYNCHRONOUS, false, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for
	 * an {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand}
	 * or {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(SequenceDiagramPackage.Literals.MMESSAGE__SEND_END);
			childrenFeatures.add(SequenceDiagramPackage.Literals.MMESSAGE__RECEIVE_END);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns MMessage.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MMessage")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((MMessage)object).getName();
		return (label == null) || (label.length() == 0) ? getString("_UI_MMessage_type") : //$NON-NLS-1$
				getString("_UI_MMessage_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(MMessage.class)) {
			case SequenceDiagramPackage.MMESSAGE__SEND:
			case SequenceDiagramPackage.MMESSAGE__RECEIVE:
			case SequenceDiagramPackage.MMESSAGE__SENDER:
			case SequenceDiagramPackage.MMESSAGE__RECEIVER:
			case SequenceDiagramPackage.MMESSAGE__SYNCHRONOUS:
				fireNotifyChanged(
						new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case SequenceDiagramPackage.MMESSAGE__SEND_END:
			case SequenceDiagramPackage.MMESSAGE__RECEIVE_END:
				fireNotifyChanged(
						new ViewerNotification(notification, notification.getNotifier(), true, false));
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
