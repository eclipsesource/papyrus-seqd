package org.eclipse.papyrus.uml.interaction.model.util;

import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;

import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.PendingChildData;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.PendingVerticalExtentData;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
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

	public static Optional<Shape> executionShapeAt(MLifeline lifeline, int absoluteY) {
		Predicate<MExecution> spanning = PendingVerticalExtentData.spans(absoluteY);

		Optional<MExecution> movingToLifeline = as(PendingChildData.getPendingChild(lifeline),
				MExecution.class).filter(spanning);

		if (movingToLifeline.isPresent()) {
			// Easy case
			return movingToLifeline.flatMap(MExecution::getDiagramView);
		}

		return lifeline.getExecutions().stream().filter(spanning).max(LogicalModelOrdering.vertically())
				.flatMap(MExecution::getDiagramView);
	}

}
