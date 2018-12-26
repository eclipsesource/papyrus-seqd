package org.eclipse.papyrus.uml.interaction.model.util;

import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.alwaysFalse;
import static org.eclipse.papyrus.uml.interaction.model.util.LogicalModelPredicates.equalTo;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.as;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.elseMaybe;
import static org.eclipse.papyrus.uml.interaction.model.util.Optionals.flatMapToObj;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.PendingChildData;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.PendingVerticalExtentData;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.Element;

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

	public static Optional<MMessage> getStartMessage(MExecution execution) {
		return execution.getStart().filter(MMessageEnd.class::isInstance).map(MMessageEnd.class::cast)
				.map(MMessageEnd::getOwner);
	}

	public static Optional<MMessage> getFinishMessage(MExecution execution) {
		return execution.getFinish().filter(MMessageEnd.class::isInstance).map(MMessageEnd.class::cast)
				.map(MMessageEnd::getOwner);
	}

	public static Optional<MExecution> executionAt(MLifeline lifeline, int absoluteY) {
		return executionAt(lifeline, absoluteY, alwaysFalse());
	}

	public static Optional<MExecution> executionAt(MLifeline lifeline, int absoluteY,
			Predicate<? super MExecution> excluding) {

		Predicate<MExecution> spanning = PendingVerticalExtentData.<MExecution> spans(absoluteY)
				.and(excluding.negate());

		Optional<MExecution> movingToLifeline = PendingChildData.getPendingChildren(lifeline).stream()
				.filter(MExecution.class::isInstance).map(MExecution.class::cast).filter(spanning)
				.max(LogicalModelOrdering.vertically());

		return elseMaybe(movingToLifeline, // Easy case
				() -> lifeline.getExecutions().stream().filter(spanning).filter(t -> {
					Optional<MElement<? extends Element>> pendingOwner = PendingChildData.getPendingOwner(t);
					return !pendingOwner.isPresent() || pendingOwner.get().equals(lifeline);
				}).max(LogicalModelOrdering.vertically()));
	}

	public static Optional<Shape> executionShapeAt(MLifeline lifeline, int absoluteY) {
		return executionAt(lifeline, absoluteY).flatMap(MExecution::getDiagramView);
	}

	public static Optional<Shape> executionShapeAt(MLifeline lifeline, int absoluteY,
			Predicate<? super MExecution> excluding) {
		return executionAt(lifeline, absoluteY, excluding).flatMap(MExecution::getDiagramView);
	}

	/**
	 * Obtain a predicate that tests whether an {@link MExecution} start or finish splits some execution on a
	 * given lifeline. An execution is split by another if exactly one end is spanned by the other execution.
	 * 
	 * @param onLifeline
	 *            the lifeline on which to look for split executions
	 * @return the execution splitting predicate
	 */
	public static Predicate<MExecution> splitsExecution(MLifeline onLifeline) {
		return exec -> {
			OptionalInt start = PendingVerticalExtentData.getPendingTop(exec);
			OptionalInt finish = PendingVerticalExtentData.getPendingBottom(exec);
			Predicate<MElement<? extends Element>> self = equalTo(exec);

			Optional<MExecution> startLandsOn = flatMapToObj(start,
					top -> executionAt(onLifeline, top, self));
			Optional<MExecution> finishLandsOn = flatMapToObj(finish,
					bottom -> executionAt(onLifeline, bottom, self));

			return (startLandsOn.isPresent() != finishLandsOn.isPresent()) //
					|| (finishLandsOn.isPresent()
							&& !startLandsOn.filter(equalTo(finishLandsOn.get())).isPresent());
		};
	}

}
