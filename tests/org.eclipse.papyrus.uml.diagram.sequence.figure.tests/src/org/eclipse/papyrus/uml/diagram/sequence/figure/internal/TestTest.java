package org.eclipse.papyrus.uml.diagram.sequence.figure.internal;

import org.eclipse.ui.PlatformUI;
import org.junit.Test;

public class TestTest {

	@Test(expected = IllegalStateException.class)
	public void test() {
		PlatformUI.getWorkbench();
	}

}
