package org.eclipse.papyrus.uml.diagram.sequence.figure.internal;

import static org.junit.Assert.assertNotNull;

import org.eclipse.ui.PlatformUI;
import org.junit.Test;

public class TestUITest {

	@Test
	public void test() {
		assertNotNull(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

}
