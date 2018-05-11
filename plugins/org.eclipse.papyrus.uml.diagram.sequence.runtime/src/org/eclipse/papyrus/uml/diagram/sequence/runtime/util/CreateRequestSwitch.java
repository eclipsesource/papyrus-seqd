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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util;

import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeConnectionTool.CreateAspectUnspecifiedTypeConnectionRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool.CreateAspectUnspecifiedTypeRequest;

/**
 * An EMF-style switch over GEF {@link CreateRequest}s. It adds specific handling for {@link #nullCase() null}
 * inputs for safety and convenience.
 */
public abstract class CreateRequestSwitch<V> {

	/**
	 * Initializes me.
	 */
	public CreateRequestSwitch() {
		super();
	}

	/**
	 * Switch on the given {@code request}, calling the most specific appropriate case(s), to compute a
	 * result.
	 * 
	 * @param request
	 *            a request on which to switch
	 * @return the result of the first applicable case, in reverse specificity order, to return a
	 *         non-{@code null} result
	 */
	public V doSwitch(Object request) {
		if (request == null) {
			return nullCase();
		}

		V result = _null();

		if (result == null && request instanceof CreateAspectUnspecifiedTypeConnectionRequest) {
			result = caseCreateAspectUnspecifiedTypeConnectionRequest(
					(CreateAspectUnspecifiedTypeConnectionRequest)request);
		}
		if (result == null && request instanceof CreateUnspecifiedTypeConnectionRequest) {
			result = caseCreateUnspecifiedTypeConnectionRequest(
					(CreateUnspecifiedTypeConnectionRequest)request);
		}
		if (result == null && request instanceof CreateAspectUnspecifiedTypeRequest) {
			result = caseCreateAspectUnspecifiedTypeRequest((CreateAspectUnspecifiedTypeRequest)request);
		}
		if (result == null && request instanceof CreateUnspecifiedTypeRequest) {
			result = caseCreateUnspecifiedTypeRequest((CreateUnspecifiedTypeRequest)request);
		}
		if (result == null && request instanceof CreateConnectionViewAndElementRequest) {
			result = caseCreateConnectionViewAndElementRequest(
					(CreateConnectionViewAndElementRequest)request);
		}
		if (result == null && request instanceof CreateViewAndElementRequest) {
			result = caseCreateViewAndElementRequest((CreateViewAndElementRequest)request);
		}
		if (result == null && request instanceof CreateConnectionViewRequest) {
			result = caseCreateConnectionViewRequest((CreateConnectionViewRequest)request);
		}
		if (result == null && request instanceof CreateConnectionRequest) {
			result = caseCreateConnectionRequest((CreateConnectionRequest)request);
		}
		if (result == null && request instanceof CreateViewRequest) {
			result = caseCreateViewRequest((CreateViewRequest)request);
		}
		if (result == null && request instanceof CreateRelationshipRequest) {
			result = caseCreateRelationshipRequest((CreateRelationshipRequest)request);
		}
		if (result == null && request instanceof CreateElementRequest) {
			result = caseCreateElementRequest((CreateElementRequest)request);
		}
		if (result == null && request instanceof IEditCommandRequest) {
			result = caseEditCommandRequest((IEditCommandRequest)request);
		}
		if (result == null && request instanceof CreateRequest) {
			result = caseCreateRequest((CreateRequest)request);
		}
		if (result == null && request instanceof Request) {
			result = caseRequest((Request)request);
		}
		if (result == null) {
			result = defaultCase(request);
		}

		return result;
	}

	private static final <V> V _null() {
		return null;
	}

	//
	// Switch cases
	//

	/**
	 * Handle a create-aspect-unspecified-type connection request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateAspectUnspecifiedTypeConnectionRequest(
			CreateAspectUnspecifiedTypeConnectionRequest request) {
		return null;
	}

	/**
	 * Handle a create-unspecified-type connection request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateUnspecifiedTypeConnectionRequest(CreateUnspecifiedTypeConnectionRequest request) {
		return null;
	}

	/**
	 * Handle a create-view-and-element connection request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateConnectionViewAndElementRequest(CreateConnectionViewAndElementRequest request) {
		return null;
	}

	/**
	 * Handle a create-view connection request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateConnectionViewRequest(CreateConnectionViewRequest request) {
		return null;
	}

	/**
	 * Handle a create connection request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateConnectionRequest(CreateConnectionRequest request) {
		return null;
	}

	/**
	 * Handle a create-aspect-unspecified-type request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateAspectUnspecifiedTypeRequest(CreateAspectUnspecifiedTypeRequest request) {
		return null;
	}

	/**
	 * Handle a create-unspecified-type request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateUnspecifiedTypeRequest(CreateUnspecifiedTypeRequest request) {
		return null;
	}

	/**
	 * Handle a create-view-and-element request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateViewAndElementRequest(CreateViewAndElementRequest request) {
		return null;
	}

	/**
	 * Handle a create-view request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateViewRequest(CreateViewRequest request) {
		return null;
	}

	/**
	 * Handle a create-relationship request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateRelationshipRequest(CreateRelationshipRequest request) {
		return null;
	}

	/**
	 * Handle a create-element request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateElementRequest(CreateElementRequest request) {
		return null;
	}

	/**
	 * Handle a create request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseCreateRequest(CreateRequest request) {
		return null;
	}

	/**
	 * Handle an edit-command request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseEditCommandRequest(IEditCommandRequest request) {
		return null;
	}

	/**
	 * Handle a request.
	 * 
	 * @param request
	 *            the request
	 */
	public V caseRequest(Request request) {
		return null;
	}

	/**
	 * Handle anything else.
	 * 
	 * @param object
	 *            the object
	 */
	public V defaultCase(Object object) {
		return null;
	}

	/**
	 * Handle a {@code null} input.
	 */
	public V nullCase() {
		return null;
	}
}
