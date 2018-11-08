package org.eclipse.papyrus.uml.interaction.model.util;

import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;

public class Executions {

	/**
	 * Returns the view representing the lifeline (TODO body?)containing directly or indirectly the
	 * representation of this execution.
	 * 
	 * @param execution
	 *            the execution to
	 * @return
	 */
	public static Optional<View> getLifelineView(MExecution execution) {
		return parentLifelineView(as(execution.getDiagramView(), View.class));
	}

	@SuppressWarnings("boxing")
	protected static Optional<View> parentLifelineView(Optional<View> view) {
		if (!view.isPresent()) {
			return view;
		}
		if (view.map(View::getType).map(ViewTypes.LIFELINE_HEADER::equals).orElse(false)) {
			return Optionals.as(view, View.class);
		}
		return parentLifelineView(as(view.map(EObject::eContainer), View.class));
	}

}
