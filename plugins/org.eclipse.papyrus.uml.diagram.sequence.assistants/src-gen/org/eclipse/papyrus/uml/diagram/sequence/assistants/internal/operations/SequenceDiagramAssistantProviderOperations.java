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
 */
package org.eclipse.papyrus.uml.diagram.sequence.assistants.internal.operations;

import com.google.common.base.Function;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.GetRelTypesOnSourceOperation;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.GetRelTypesOnTargetOperation;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.util.ProviderCache;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.operations.ModelingAssistantProviderOperations;
import org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider;

/**
 * <!-- begin-user-doc --> A static utility class that provides operations related to '<em><b>Sequence Diagram
 * Assistant Provider</b></em>' model objects. <!-- end-user-doc -->
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable)
 * <em>Get Rel Types On Source</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable)
 * <em>Get Rel Types On Target</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.core.runtime.IAdaptable)
 * <em>Get Rel Types On Source And Target</em>}</li>
 * </ul>
 *
 * @generated
 */
@SuppressWarnings("restriction")
public class SequenceDiagramAssistantProviderOperations extends ModelingAssistantProviderOperations {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SequenceDiagramAssistantProviderOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public static EList<IElementType> getRelTypesOnSource(
			SequenceDiagramAssistantProvider sequenceDiagramAssistantProvider, IAdaptable source) {

		ProviderCache<IAdaptable, EList<IElementType>> cache = ProviderCache
				.getCache(sequenceDiagramAssistantProvider, GetRelTypesOnSourceOperation.class);
		if (cache == null) {
			cache = ProviderCache.cache(sequenceDiagramAssistantProvider, GetRelTypesOnSourceOperation.class,
					new Function<IAdaptable, EList<IElementType>>() {
						@Override
						public EList<IElementType> apply(IAdaptable input) {
							Set<IElementType> result = new LinkedHashSet<>();

							for (ConnectionAssistant next : sequenceDiagramAssistantProvider
									.getConnectionAssistants()) {
								if ((next.getSourceFilter() == null)
										|| next.getSourceFilter().matches(input)) {
									resolveAndAppendHintedTypes(next.getElementType(),
											sequenceDiagramAssistantProvider, input, result);
								}
							}

							result.remove(null); // In case of an unresolved element type

							// Don't filter as the superclass does
							return new BasicEList.UnmodifiableEList<>(result.size(), result.toArray());
						}
					});
		}

		return cache.get(source);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public static EList<IElementType> getRelTypesOnTarget(
			SequenceDiagramAssistantProvider sequenceDiagramAssistantProvider, IAdaptable target) {

		ProviderCache<IAdaptable, EList<IElementType>> cache = ProviderCache
				.getCache(sequenceDiagramAssistantProvider, GetRelTypesOnTargetOperation.class);
		if (cache == null) {
			cache = ProviderCache.cache(sequenceDiagramAssistantProvider, GetRelTypesOnTargetOperation.class,
					new Function<IAdaptable, EList<IElementType>>() {
						@Override
						public EList<IElementType> apply(IAdaptable input) {
							Set<IElementType> result = new LinkedHashSet<>();

							for (ConnectionAssistant next : sequenceDiagramAssistantProvider
									.getConnectionAssistants()) {
								if ((next.getTargetFilter() == null)
										|| next.getTargetFilter().matches(input)) {
									resolveAndAppendHintedTypes(next.getElementType(),
											sequenceDiagramAssistantProvider, input, result);
								}
							}

							result.remove(null); // In case of an unresolved element type

							// Don't filter as the superclass does
							return new BasicEList.UnmodifiableEList<>(result.size(), result.toArray());
						}
					});
		}

		return cache.get(target);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public static EList<IElementType> getRelTypesOnSourceAndTarget(
			SequenceDiagramAssistantProvider sequenceDiagramAssistantProvider, IAdaptable source,
			IAdaptable target) {

		Set<IElementType> result = new LinkedHashSet<>();

		for (ConnectionAssistant next : sequenceDiagramAssistantProvider.getConnectionAssistants()) {
			if (((next.getSourceFilter() == null) || next.getSourceFilter().matches(source))
					&& ((next.getTargetFilter() == null) || next.getTargetFilter().matches(target))) {
				resolveAndAppendHintedTypes(next.getElementType(), sequenceDiagramAssistantProvider, source,
						result);
			}
		}

		result.remove(null); // In case of an unresolved element type

		// Don't filter as the superclass does
		return new BasicEList.UnmodifiableEList<>(result.size(), result.toArray());
	}

} // SequenceDiagramAssistantProviderOperations
