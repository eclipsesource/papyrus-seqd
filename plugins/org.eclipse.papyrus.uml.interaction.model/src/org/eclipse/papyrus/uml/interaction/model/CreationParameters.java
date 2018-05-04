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

package org.eclipse.papyrus.uml.interaction.model;

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.LogicalModelPlugin;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;

/**
 * Parameters for creation of a new object that support various combinations of optionality and inference. All
 * parameter values are deferred until they are actually needed to create the command to be executed, thus
 * allowing for a chain of commands to derive their inputs from the outputs of previous commands.
 *
 * @author Christian W. Damus
 */
public final class CreationParameters {

	private Supplier<? extends EObject> container;

	private Supplier<EReference> containment;

	private Supplier<EClass> eClass;

	private Supplier<? extends EObject> insertAt;

	private boolean insertBefore;

	/**
	 * Initializes me.
	 */
	private CreationParameters(Supplier<? extends EObject> container, Supplier<EReference> containment,
			Supplier<EClass> eClass, Supplier<? extends EObject> insertAt) {

		this(container, containment, eClass, insertAt, false);
	}

	/**
	 * Initializes me.
	 */
	private CreationParameters(Supplier<? extends EObject> container, Supplier<EReference> containment,
			Supplier<EClass> eClass, Supplier<? extends EObject> insertAt, boolean insertBefore) {

		super();

		this.container = container;
		this.containment = containment;
		this.eClass = eClass;
		this.insertAt = insertAt;
		this.insertBefore = insertBefore;
	}

	/**
	 * Initializes me with the {@code container} of the new object. Unless other parameters are set
	 * subsequently, the new object will be of the first containment reference type in this {@code container}
	 * that is concrete.
	 */
	public static CreationParameters in(Supplier<? extends EObject> container) {
		return new CreationParameters(container, null, null, null);
	}

	/**
	 * Initializes me with the {@code container} of the new object. Unless other parameters are set
	 * subsequently, the new object will be of the first containment reference type in this {@code container}
	 * that is concrete.
	 */
	public static CreationParameters in(EObject container) {
		return in(supplierOf(container));
	}

	/**
	 * Initializes me with the {@code container} and {@link containment} reference of the new object. Unless
	 * other parameters are set subsequently, the new object will be of the first {@code containment}
	 * reference type if it is concrete; otherwise the command will not be executable.
	 */
	public static CreationParameters in(Supplier<? extends EObject> container,
			Supplier<EReference> containment) {
		return new CreationParameters(container, containment, null, null);
	}

	/**
	 * Initializes me with the {@code container} and {@link containment} reference of the new object. Unless
	 * other parameters are set subsequently, the new object will be of the first {@code containment}
	 * reference type if it is concrete; otherwise the command will not be executable.
	 */
	public static CreationParameters in(Supplier<? extends EObject> container, EReference containment) {
		return in(container, supplierOf(containment));
	}

	/**
	 * Initializes me with the {@code container} and {@link containment} reference of the new object. Unless
	 * other parameters are set subsequently, the new object will be of the first {@code containment}
	 * reference type if it is concrete; otherwise the command will not be executable.
	 */
	public static CreationParameters in(EObject container, EReference containment) {
		return in(supplierOf(container), supplierOf(containment));
	}

	/**
	 * Initializes me with the {@code container} and {@link eClass} of the new object. Unless other parameters
	 * are set subsequently, the new object will be added to the first available {@code containment} reference
	 * type compatible with the {@code eClass}. If no such reference is available, then the command will not
	 * be executable
	 */
	public static CreationParameters ofType(Supplier<? extends EObject> container, Supplier<EClass> eClass) {
		return new CreationParameters(container, null, eClass, null);
	}

	/**
	 * Initializes me with the {@code container}, {@link containment} reference, and {@code eClass} of the new
	 * object.
	 */
	public static CreationParameters in(Supplier<? extends EObject> container,
			Supplier<EReference> containment, Supplier<EClass> eClass) {
		return new CreationParameters(container, containment, eClass, null);
	}

	/**
	 * Initializes me with a deferred {@code insertionPoint} after which the new object is to be created in
	 * the same containment reference of the same container as it. Unless other parameters are set
	 * subsequently, the new object will be of the {@code insertionPoint}'s containment reference type if it
	 * is concrete; otherwise the command will not be executable.
	 */
	public static CreationParameters after(Supplier<? extends EObject> insertionPoint) {
		return new CreationParameters(null, null, null, insertionPoint);
	}

	/**
	 * Initializes me with an {@code insertionPoint} after which the new object is to be created in the same
	 * containment reference of the same container as it. Unless other parameters are set subsequently, the
	 * new object will be of the {@code insertionPoint}'s containment reference type if it is concrete;
	 * otherwise the command will not be executable.
	 */
	public static CreationParameters after(EObject insertionPoint) {
		return after(supplierOf(insertionPoint));
	}

	/**
	 * Initializes me with a deferred {@code insertionPoint} before which the new object is to be created in
	 * the same containment reference of the same container as it. Unless other parameters are set
	 * subsequently, the new object will be of the {@code insertionPoint}'s containment reference type if it
	 * is concrete; otherwise the command will not be executable.
	 */
	public static CreationParameters before(Supplier<? extends EObject> insertionPoint) {
		return new CreationParameters(null, null, null, insertionPoint, true);
	}

	/**
	 * Initializes me with an {@code insertionPoint} before which the new object is to be created in the same
	 * containment reference of the same container as it. Unless other parameters are set subsequently, the
	 * new object will be of the {@code insertionPoint}'s containment reference type if it is concrete;
	 * otherwise the command will not be executable.
	 */
	public static CreationParameters before(EObject insertionPoint) {
		return before(supplierOf(insertionPoint));
	}

	/**
	 * Initializes me with a deferred {@code insertionPoint} after which the new object is to be created in
	 * the same containment reference of the same container as it, and the {@code eClass} to create.
	 */
	public static CreationParameters after(Supplier<? extends EObject> insertionPoint,
			Supplier<EClass> eClass) {
		return new CreationParameters(null, null, eClass, insertionPoint);
	}

	/**
	 * @return the container
	 */
	public EObject getContainer() {
		EObject result = null;

		if (container != null) {
			result = container.get();
		} else if (insertAt != null) {
			EObject insertionPoint = insertAt.get();
			if (insertionPoint != null) {
				result = insertionPoint.eContainer();
			}
		}

		return result;
	}

	/**
	 * @param container
	 *            the container to set
	 */
	public void setContainer(Supplier<? extends EObject> container) {
		this.container = container;
	}

	public void setContainer(EObject container) {
		setContainer(supplierOf(container));
	}

	/**
	 * @return the containment
	 */
	public EReference getContainment() {
		EReference result = null;

		if (containment != null) {
			result = containment.get();
		} else if (insertAt != null) {
			EObject insertionPoint = insertAt.get();
			if (insertionPoint != null) {
				result = insertionPoint.eContainmentFeature();
			}
		} else if (container != null) {
			EObject parent = container.get();
			EList<EReference> containments = parent.eClass().getEAllContainments();

			// Look for a suitable containment reference
			EClass type = eClass == null ? null : eClass.get();

			Predicate<EReference> filter;
			if (type == null) {
				// First available concrete type
				filter = CreationParameters::isConcrete;
			} else {
				// First compatible reference
				filter = ref -> ref.getEReferenceType().isSuperTypeOf(type);
			}

			result = containments.stream().filter(filter).findFirst().orElse(null);
		}

		return result;
	}

	static boolean isConcrete(EReference reference) {
		return !reference.getEReferenceType().isAbstract() //
				&& !reference.getEReferenceType().isInterface();
	}

	/**
	 * @param containment
	 *            the containment to set
	 */
	public void setContainment(Supplier<EReference> containment) {
		this.containment = containment;
	}

	public void setContainment(EReference containment) {
		setContainment(supplierOf(containment));
	}

	/**
	 * @return the eClass
	 */
	public EClass getEClass() {
		EClass result = null;

		if (eClass != null) {
			result = eClass.get();
		} else {
			// Infer the EClass from the containment reference
			if (containment != null) {
				EReference ref = containment.get();
				if (ref != null && isConcrete(ref)) {
					result = ref.getEReferenceType();
				}
			}
			if (result == null && insertAt != null) {
				EObject insertionPoint = insertAt.get();
				EReference ref = (insertionPoint == null) ? null : insertionPoint.eContainmentFeature();
				if (ref != null && isConcrete(ref)) {
					result = ref.getEReferenceType();
				}
			}
		}

		return result;
	}

	/**
	 * @param eClass
	 *            the eClass to set
	 */
	public void setEClass(Supplier<EClass> eClass) {
		this.eClass = eClass;
	}

	public void setEClass(EClass eClass) {
		setEClass(supplierOf(eClass));
	}

	/**
	 * @return the insertionPoint
	 */
	public EObject getInsertionPoint() {
		EObject result = null;

		if (insertAt != null) {
			result = insertAt.get();
		}

		return result;
	}

	public boolean isInsertBefore() {
		return insertBefore;
	}

	/**
	 * @param insertAfter
	 *            the insertAfter to set
	 */
	public void setInsertAfter(Supplier<? extends EObject> insertAfter) {
		this.insertAt = insertAfter;
		this.insertBefore = false;
	}

	public void setInsertAfter(EObject insertAfter) {
		setInsertAfter(supplierOf(insertAfter));
	}

	/**
	 * @param insertBefore
	 *            the insertBefore to set
	 */
	public void setInsertBefore(Supplier<? extends EObject> insertBefore) {
		this.insertAt = insertBefore;
		this.insertBefore = true;
	}

	public void setInsertBefore(EObject insertBefore) {
		setInsertBefore(supplierOf(insertBefore));
	}

	public SemanticHelper getSemanticHelper() {
		EObject context = getContainer();
		EditingDomain domain = (context == null) ? null
				: AdapterFactoryEditingDomain.getEditingDomainFor(context);
		return (domain == null) ? null : LogicalModelPlugin.getInstance().getSemanticHelper(domain);
	}

	static <T> Supplier<T> supplierOf(T object) {
		return () -> object;
	}
}
