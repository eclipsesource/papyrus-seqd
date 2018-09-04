package org.eclipse.papyrus.uml.diagram.sequence.contribution.simplifiedui;

import org.eclipse.ui.IStartup;

public class StartUp implements IStartup {

	@Override
	public void earlyStartup() {
		CreationMenuCleaner.clean();
	}

}
