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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.edit.policies;

import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;

/**
 * Utilities for working with private {@link Request}s and/or private details of {@code Request}s.
 */
public final class PrivateRequestUtils {
	private static final String FORCE_PARAMETER = "__force__"; //$NON-NLS-1$

	private static final String ORIGINAL_MOUSE_PARAMETER = "__orig_mouse__"; //$NON-NLS-1$

	private static final String ORIGINAL_SOURCE_PARAMETER = "__orig_source__"; //$NON-NLS-1$

	private static final String ORIGINAL_TARGET_PARAMETER = "__orig_target__"; //$NON-NLS-1$

	private static final String UPDATED_SOURCE_PARAMETER = "__new_source__"; //$NON-NLS-1$

	private static final String UPDATED_TARGET_PARAMETER = "__new_target__"; //$NON-NLS-1$

	private static final String ALLOW_SEMANTIC_REORDERING_PARAMETER = "__allow_semantic_reordering__"; //$NON-NLS-1$

	private static final String CHANGE_BOUNDS_REQUEST_PARAMETER = "__change_bounds_request__"; //$NON-NLS-1$

	private static final String[] PARAMETERS = { //
			FORCE_PARAMETER, //
			ORIGINAL_MOUSE_PARAMETER, ORIGINAL_SOURCE_PARAMETER, ORIGINAL_TARGET_PARAMETER, //
			UPDATED_SOURCE_PARAMETER, UPDATED_TARGET_PARAMETER, //
			ALLOW_SEMANTIC_REORDERING_PARAMETER, CHANGE_BOUNDS_REQUEST_PARAMETER, //
	};

	/**
	 * Not instantiable by clients.
	 */
	private PrivateRequestUtils() {
		super();
	}

	/**
	 * Queries whether a {@code request} has the <em>force</em> option set.
	 * 
	 * @param request
	 *            a request
	 * @return whether it is forced
	 */
	static boolean isForce(Request request) {
		return getBoolean(request, FORCE_PARAMETER);
	}

	/**
	 * Sets the <em>force</em> option of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @param force
	 *            whether it is forced
	 */
	static void setForce(Request request, boolean force) {
		setParameter(request, FORCE_PARAMETER, Boolean.valueOf(force));
	}

	/**
	 * Queries the original mouse location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @return the original mouse location (different to the current location that is updated by the tool's
	 *         tracking)
	 */
	static Point getOriginalMouseLocation(Request request) {
		return getParameter(request, ORIGINAL_MOUSE_PARAMETER, Point.class, null);
	}

	/**
	 * Set the original mouse location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @param location
	 *            the original mouse location (different to the current location that is updated by the tool's
	 *            tracking)
	 */
	static void setOriginalMouseLocation(Request request, Point location) {
		setParameter(request, ORIGINAL_MOUSE_PARAMETER, location);
	}

	/**
	 * Queries the original source anchor location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @return the original source anchor location
	 */
	static Point getOriginalSourceLocation(Request request) {
		return getParameter(request, ORIGINAL_SOURCE_PARAMETER, Point.class, null);
	}

	/**
	 * Set the original source anchor location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @param location
	 *            the original source anchor location
	 */
	static void setOriginalSourceLocation(Request request, Point location) {
		setParameter(request, ORIGINAL_SOURCE_PARAMETER, location);
	}

	/**
	 * Queries the updated source anchor location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @return the updated source anchor location
	 */
	static Point getUpdatedSourceLocation(Request request) {
		return getParameter(request, UPDATED_SOURCE_PARAMETER, Point.class, null);
	}

	/**
	 * Set the updated source anchor location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @param location
	 *            the updated source anchor location
	 */
	static void setUpdatedSourceLocation(Request request, Point location) {
		setParameter(request, UPDATED_SOURCE_PARAMETER, location);
	}

	/**
	 * Queries the original target anchor location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @return the original target anchor location
	 */
	static Point getOriginalTargetLocation(Request request) {
		return getParameter(request, ORIGINAL_TARGET_PARAMETER, Point.class, null);
	}

	/**
	 * Set the original target anchor location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @param location
	 *            the original target anchor location
	 */
	static void setOriginalTargetLocation(Request request, Point location) {
		setParameter(request, ORIGINAL_TARGET_PARAMETER, location);
	}

	/**
	 * Queries the updated target anchor location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @return the updated target anchor location
	 */
	static Point getUpdatedTargetLocation(Request request) {
		return getParameter(request, UPDATED_TARGET_PARAMETER, Point.class, null);
	}

	/**
	 * Set the updated target anchor location of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @param location
	 *            the updated target anchor location
	 */
	static void setUpdatedTargetLocation(Request request, Point location) {
		setParameter(request, UPDATED_TARGET_PARAMETER, location);
	}

	/**
	 * Queries whether a {@code request} has the <em>allow semantic reordering</em> option set.
	 * 
	 * @param request
	 *            a request
	 * @return whether it allows semantic reordering
	 */
	public static boolean isAllowSemanticReordering(Request request) {
		return getBoolean(request, ALLOW_SEMANTIC_REORDERING_PARAMETER);
	}

	/**
	 * Sets the <em>allow semantic reordering</em> option of a {@code request}.
	 * 
	 * @param request
	 *            a request
	 * @param force
	 *            whether it allows semantic reordering
	 */
	public static void setAllowSemanticReordering(Request request, boolean allowSemanticReordering) {
		setParameter(request, ALLOW_SEMANTIC_REORDERING_PARAMETER, Boolean.valueOf(allowSemanticReordering));
	}

	static boolean getBoolean(Request request, Object parameterKey) {
		return getParameter(request, parameterKey, Boolean.class, Boolean.FALSE).booleanValue();
	}

	static boolean hasParameter(Request request, Object parameterKey) {
		Map<?, ?> parameters = request.getExtendedData();
		return parameters.containsKey(parameterKey);
	}

	static <T> T getParameter(Request request, Object parameterKey, Class<? extends T> type, T defaultValue) {
		Map<?, ?> parameters = request.getExtendedData();
		Object value = parameters.get(parameterKey);
		return type.isInstance(value) ? type.cast(value) : defaultValue;
	}

	static void setParameter(Request request, Object parameterKey, Object value) {
		@SuppressWarnings("unchecked")
		Map<Object, Object> parameters = request.getExtendedData();
		parameters.put(parameterKey, value);
	}

	/**
	 * Forward request parameters from one request to another. Only private parameters managed by this utility
	 * are forwarded that the "from" request actually bears, and then only those that are not already defined
	 * in the "to" request.
	 * 
	 * @param toRequest
	 *            a request to which to forward them
	 * @param fromRequest
	 *            the request bearing parameters
	 */
	public static void forwardParameters(Request toRequest, Request fromRequest) {
		Stream.of(PARAMETERS).filter(p -> hasParameter(fromRequest, p))
				.filter(p -> !hasParameter(toRequest, p))
				.forEach(p -> setParameter(toRequest, p, getParameter(fromRequest, p, Object.class, null)));
	}

	/**
	 * Records the change-bounds request that triggers a {@code drop}.
	 * 
	 * @param drop
	 *            a drop request
	 * @param changeBounds
	 *            the change-bounds request that triggered it
	 */
	public static void setChangeBoundsRequest(DropObjectsRequest drop, ChangeBoundsRequest changeBounds) {
		setParameter(drop, CHANGE_BOUNDS_REQUEST_PARAMETER, changeBounds);
	}

	/**
	 * Queries the change-bounds request that triggers a {@code drop}.
	 * 
	 * @param drop
	 *            a drop request
	 * @return the change-bounds request that triggered it
	 */
	public static ChangeBoundsRequest getChangeBoundsRequest(DropObjectsRequest drop) {
		return getParameter(drop, CHANGE_BOUNDS_REQUEST_PARAMETER, ChangeBoundsRequest.class, null);
	}
}
