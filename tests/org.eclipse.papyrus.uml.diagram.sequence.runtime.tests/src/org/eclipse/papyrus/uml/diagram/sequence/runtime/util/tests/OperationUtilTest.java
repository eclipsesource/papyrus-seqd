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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util.tests;

import static org.eclipse.uml2.uml.ParameterDirectionKind.INOUT_LITERAL;
import static org.eclipse.uml2.uml.ParameterDirectionKind.IN_LITERAL;
import static org.eclipse.uml2.uml.ParameterDirectionKind.OUT_LITERAL;
import static org.eclipse.uml2.uml.ParameterDirectionKind.RETURN_LITERAL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.OperationUtil;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@code OperationUtil} class.
 *
 * @author Christian W. Damus
 */
public class OperationUtilTest {

	private Operation op;
	private Parameter in1;
	private Parameter out;
	private Parameter return_;
	private Parameter in2;
	private Parameter inout;

	@Test
	public void isInput() {
		List<Parameter> inputs = op.getOwnedParameters().stream().filter(OperationUtil::isInput)
				.collect(Collectors.toList());
		assertThat(inputs, is(Arrays.asList(in1, in2, inout)));
	}

	@Test
	public void isOutput() {
		List<Parameter> outputs = op.getOwnedParameters().stream().filter(OperationUtil::isOutput)
				.collect(Collectors.toList());
		assertThat(outputs, is(Arrays.asList(out, return_, inout)));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		op = UMLFactory.eINSTANCE.createOperation();
		in1 = createParameter("in1", IN_LITERAL);
		out = createParameter("out", OUT_LITERAL);
		return_ = createParameter(null, RETURN_LITERAL);
		in2 = createParameter("in2", IN_LITERAL);
		inout = createParameter("inout", INOUT_LITERAL);
	}

	private Parameter createParameter(String name, ParameterDirectionKind direction) {
		Parameter result = op.createOwnedParameter(name, null);
		result.setDirection(direction);
		return result;
	}
}
