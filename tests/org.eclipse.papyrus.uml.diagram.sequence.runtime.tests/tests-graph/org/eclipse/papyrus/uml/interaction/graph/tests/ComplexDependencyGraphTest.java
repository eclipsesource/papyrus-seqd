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

package org.eclipse.papyrus.uml.interaction.graph.tests;

import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelResource;
import org.eclipse.papyrus.uml.interaction.graph.Graph;
import org.eclipse.papyrus.uml.interaction.graph.Visitor;
import org.junit.Rule;
import org.junit.Test;

/**
 * This is the {@code ComplexDependencyGraphTest} type. Enjoy.
 *
 * @author Christian W. Damus
 */
@ModelResource("codereview.uml")
public class ComplexDependencyGraphTest {

	@Rule
	public final ModelFixture model = new ModelFixture();

	/**
	 * Initializes me.
	 */
	public ComplexDependencyGraphTest() {
		super();
	}

	@Test
	public void complexGraph() {
		Graph graph = model.graph();

		System.out.println("Complex Graph");
		System.out.println("======================================");
		graph.initial().walkOutgoing(Visitor.printer(System.out));
		System.out.println("======================================");
	}

}
