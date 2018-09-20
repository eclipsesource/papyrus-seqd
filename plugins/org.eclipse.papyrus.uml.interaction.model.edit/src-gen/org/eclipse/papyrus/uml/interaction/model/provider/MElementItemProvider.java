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
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MElement;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.uml.interaction.model.MElement} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class MElementItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public MElementItemProvider(AdapterFactory adapterFactory) {
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

			addInteractionPropertyDescriptor(object);
			addElementPropertyDescriptor(object);
			addTopPropertyDescriptor(object);
			addBottomPropertyDescriptor(object);
			addNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Interaction feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addInteractionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MElement_interaction_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MElement_interaction_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MElement_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MELEMENT__INTERACTION, false, false, false, null, null,
				null));
	}

	/**
	 * This adds a property descriptor for the Element feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addElementPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MElement_element_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MElement_element_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MElement_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MELEMENT__ELEMENT, false, false, false, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Top feature. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addTopPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MElement_top_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MElement_top_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MElement_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MELEMENT__TOP, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Bottom feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addBottomPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MElement_bottom_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MElement_bottom_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MElement_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MELEMENT__BOTTOM, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Name feature. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_MElement_name_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_MElement_name_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_MElement_type"), //$NON-NLS-1$
				SequenceDiagramPackage.Literals.MELEMENT__NAME, false, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an
	 * appropriate feature for an {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(SequenceDiagramPackage.Literals.MELEMENT__ELEMENT);
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
		// Check the type of the specified child object and return the proper feature to
		// use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((MElement<?>) object).getName();
		return (label == null) || (label.length() == 0) ? getString("_UI_MElement_type") : //$NON-NLS-1$
				getString("_UI_MElement_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(MElement.class)) {
		case SequenceDiagramPackage.MELEMENT__TOP:
		case SequenceDiagramPackage.MELEMENT__BOTTOM:
		case SequenceDiagramPackage.MELEMENT__NAME:
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

	@Override
	public Collection<?> getChildren(Object object) {
		@SuppressWarnings("unchecked")
		Collection<Object> result = (Collection<Object>) super.getChildren(object);

		// Don't show the diagram, which will repeat the views of everything else
		MElement<?> element = (MElement<?>) object;
		Predicate<EObject> isDiagram = Diagram.class::isInstance;
		Optional<? extends EObject> view = element.getDiagramView().filter(isDiagram.negate());
		view.ifPresent(result::add);

		return result;
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return SeqdEditPlugin.INSTANCE;
	}

	@Override
	protected ItemPropertyDescriptor createItemPropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName, String description,
			EStructuralFeature feature, boolean isSettable, boolean multiLine, boolean sortChoices,
			Object staticImage, String category, String[] filterFlags) {

		if (feature instanceof EAttribute) {
			Class<?> attrType = ((EAttribute) feature).getEAttributeType().getInstanceClass();
			if (attrType == Optional.class) {
				return new OptionalPropertyDescriptor(adapterFactory, resourceLocator, displayName,
						description, feature, isSettable, multiLine, sortChoices, staticImage, category,
						filterFlags);
			} else if (attrType == OptionalInt.class) {
				return new OptionalIntPropertyDescriptor(adapterFactory, resourceLocator, displayName,
						description, feature, isSettable, multiLine, sortChoices, staticImage, category,
						filterFlags);
			}
		}

		return super.createItemPropertyDescriptor(adapterFactory, resourceLocator, displayName, description,
				feature, isSettable, multiLine, sortChoices, staticImage, category, filterFlags);
	}

	//
	// Nested types
	//

	/**
	 * A property descriptor that unwraps {@link Optional}s.
	 */
	protected class OptionalPropertyDescriptor extends ItemPropertyDescriptor {

		protected OptionalPropertyDescriptor(AdapterFactory adapterFactory, ResourceLocator resourceLocator,
				String displayName, String description, EStructuralFeature feature, boolean isSettable,
				boolean multiLine, boolean sortChoices, Object staticImage, String category,
				String[] filterFlags) {
			super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine,
					sortChoices, null, category, filterFlags);
		}

		@Override
		protected Object createPropertyValueWrapper(Object object, Object propertyValue) {
			if (propertyValue == null) {
				return super.createPropertyValueWrapper(object, propertyValue);
			}

			Optional<?> optional = (Optional<?>) propertyValue;
			return new PropertyValueWrapper(adapterFactory, object, propertyValue, null) {
				@Override
				public Object getEditableValue(Object thisObject) {
					return optional.orElse(null);
				}

				@Override
				public String getText(Object thisObject) {
					return optional.map(o -> getLabelProvider(o).getText(o)).orElse("<nil>"); //$NON-NLS-1$
				}
			};
		}

		@Override
		public IItemLabelProvider getLabelProvider(Object object) {
			return new IItemLabelProvider() {

				@Override
				public String getText(Object object) {
					return object == null ? "<nil>" : String.valueOf(object); //$NON-NLS-1$
				}

				@Override
				public Object getImage(Object object) {
					return itemDelegator.getImage(object);
				}
			};
		}
	}

	/**
	 * A property descriptor that unwraps {@link OptionalInt}s.
	 */
	protected class OptionalIntPropertyDescriptor extends ItemPropertyDescriptor {

		protected OptionalIntPropertyDescriptor(AdapterFactory adapterFactory,
				ResourceLocator resourceLocator, String displayName, String description,
				EStructuralFeature feature, boolean isSettable, boolean multiLine, boolean sortChoices,
				Object staticImage, String category, String[] filterFlags) {
			super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine,
					sortChoices, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, category, filterFlags);
		}

		@Override
		protected Object createPropertyValueWrapper(Object object, Object propertyValue) {
			if (propertyValue == null) {
				return super.createPropertyValueWrapper(object, propertyValue);
			}

			OptionalInt optional = (OptionalInt) propertyValue;
			return new PropertyValueWrapper(adapterFactory, object, propertyValue, null) {
				@Override
				public Object getEditableValue(Object thisObject) {
					return optional.isPresent() ? optional.getAsInt() : null;
				}

				@Override
				public String getText(Object thisObject) {
					return optional.isPresent() ? String.valueOf(optional.getAsInt()) : "<nil>"; //$NON-NLS-1$
				}
			};
		}
	}
}
