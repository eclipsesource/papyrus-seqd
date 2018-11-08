/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.interaction.internal.model.commands;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.interaction.graph.Vertex;
import org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationParameters;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredAddCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.LayoutConstraints.RelativePosition;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.papyrus.uml.interaction.model.spi.ViewTypes;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A nested execution specification creation operation.
 */
public class InsertNestedExecutionCommand extends ModelCommand.Creation<MExecutionImpl, ExecutionSpecification> {

	private final MElement<?> before;

	private final int offset;

	private final int height;

	private final Element specification;

	private final EClass eClass;

	private CreationCommand<ExecutionSpecification> resultCommand;

	/**
	 * Initializes me.
	 * 
	 * @param owner
	 *            the lifeline on which to create the execution specification
	 * @param before
	 *            the element in the sequence after which to create the new execution specification
	 * @param offset
	 *            the offset from the reference element at which to create the execution specification
	 * @param height
	 *            the height of the execution specification
	 * @param specification
	 *            the action or behavior to reference, if any
	 */
	public InsertNestedExecutionCommand(MExecutionImpl owner, MElement<?> before, int offset, int height,
			Element specification) {

		super(owner, ExecutionSpecification.class);

		checkInteraction(before);
		if (specification != null && !(specification instanceof Action)
				&& !(specification instanceof Behavior)) {
			throw new IllegalArgumentException("specification is neither action nor behavior"); //$NON-NLS-1$
		}

		this.before = before;
		this.offset = offset;
		this.height = height;
		this.specification = specification;
		this.eClass = (specification instanceof Action) ? UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION
				: UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION;
	}

	/**
	 * Initializes me.
	 * 
	 * @param owner
	 *            the lifeline on which to create the execution specification
	 * @param before
	 *            the element in the sequence after which to create the new execution specification
	 * @param offset
	 *            the offset from the reference element at which to create the execution specification
	 * @param height
	 *            the height of the execution specification
	 * @param eClass
	 *            the kind of execution specification to create
	 */
	public InsertNestedExecutionCommand(MExecutionImpl owner, MElement<?> before, int offset, int height,
			EClass eClass) {

		super(owner, ExecutionSpecification.class);

		checkInteraction(before);
		if (!UMLPackage.Literals.EXECUTION_SPECIFICATION.isSuperTypeOf(eClass) || eClass.isAbstract()) {
			throw new IllegalArgumentException("invalid execution specification type"); //$NON-NLS-1$
		}

		this.before = before;
		this.offset = offset;
		this.height = height;
		this.specification = null;
		this.eClass = eClass;
	}

	@Override
	public ExecutionSpecification getNewObject() {
		return (resultCommand == null) ? null : resultCommand.getNewObject();
	}

	@Override
	protected Command createCommand() {
		Vertex reference = vertex(before);
		if (reference == null) {
			return UnexecutableCommand.INSTANCE;
		}

		OptionalInt referenceY;
		if (before instanceof MExecution && before == getTarget()) {
			referenceY = getTarget().getTop();
		} else { // before should be an element on the execution itself.
			referenceY = OptionalInt.of(layoutHelper().getBottom(reference).getAsInt());
		}

		if (!referenceY.isPresent()) {
			return UnexecutableCommand.INSTANCE;
		}

		List<MElement<? extends Element>> timeline = getTimeline(getTarget().getInteraction());
		int absoluteExecY = referenceY.getAsInt() + offset;

		Optional<MElement<? extends Element>> insertAt = getInsertionPoint(timeline, MExecution.class,
				absoluteExecY).map(this::normalizeFragmentInsertionPoint);

		SemanticHelper semantics = semanticHelper();
		CreationParameters execParams = CreationParameters.in(getTarget().getInteraction().getElement(),
				UMLPackage.Literals.INTERACTION__FRAGMENT);
		execParams.setEClass(eClass);
		execParams.setInsertBefore(insertAt.get().getElement());
		resultCommand = semantics.createExecutionSpecification(specification, execParams);
		CreationParameters startParams = CreationParameters.before(resultCommand);
		CreationCommand<OccurrenceSpecification> start = semantics.createStart(resultCommand, startParams);
		CreationParameters finishParams = CreationParameters.after(resultCommand);
		CreationCommand<OccurrenceSpecification> finish = semantics.createFinish(resultCommand, finishParams);

		// Create these elements in the interaction
		Command result = resultCommand.chain(start).chain(finish);

		// Add them to the lifeline, too
		result = result.chain(new DeferredAddCommand(getTarget().getOwner().getElement(),
				UMLPackage.Literals.LIFELINE__COVERED_BY, start, resultCommand, finish));

		// Make sure we keep enough distance to the element before us
		int additionalOffset = getAdditionalOffset(timeline, insertAt, absoluteExecY);

		result = result.chain(diagramHelper().createNestedExecutionShape(resultCommand, getTargetView(),
				absoluteExecY - getTarget().getTop().getAsInt(), height));

		// Now we have commands to add the execution specification. But, first we must make
		// room for it in the diagram. Nudge the element that will follow the new execution
		Optional<Command> makeSpace = insertAt.flatMap(
				insertionPoint -> createNudgeCommand(insertionPoint, absoluteExecY + additionalOffset));
		if (makeSpace.isPresent()) {
			result = makeSpace.get().chain(result);
		}

		return result;
	}

	/**
	 * Returns the target shape for the creation of the execution. In our case, this should be the view
	 * representing the body.
	 * 
	 * @return the view that represents the body of the lifeline.
	 */
	protected Shape getTargetView() {
		View executionView = getTarget().getDiagramView().get();
		return Shape.class.cast(executionView);
	}

	/**
	 * Creates the nudge command for the insertion of the execution.
	 * <p>
	 * If the insertion of the execution <code>isInsertBefore</code>, the <code>insertionPoint</code> also
	 * needs to be nudged, otherwise we need to nudge everything after the insertion point.
	 * </p>
	 * 
	 * @param insertionPoint
	 *            the insertion point of the execution's insert command.
	 * @param insertionYPosition
	 * @param additionalOffset
	 * @return the nudge command.
	 */
	protected Optional<Command> createNudgeCommand(MElement<? extends Element> insertionPoint,
			int insertionYPosition) {
		Optional<View> diagramView = getDiagramView(insertionPoint);
		if (!diagramView.isPresent()) {
			return Optional.of(insertionPoint.nudge(height));
		}
		int curPadding = insertionPoint.getTop().orElse(0) - insertionYPosition;
		int reqPadding = layoutHelper().getConstraints().getPadding(RelativePosition.BOTTOM,
				ViewTypes.EXECUTION_SPECIFICATION)
				+ layoutHelper().getConstraints().getPadding(RelativePosition.TOP, diagramView.get());
		if (curPadding < reqPadding) {
			return Optional.of(insertionPoint.nudge(height + (reqPadding - curPadding)));
		} else {
			return Optional.of(insertionPoint.nudge(height));
		}
	}

	private int getAdditionalOffset(List<MElement<? extends Element>> timeline,
			Optional<MElement<? extends Element>> elementAfterMe, int insertionY) {
		MElement<? extends Element> elementBeforeMe = getElementBeforeMe(timeline, elementAfterMe);
		Optional<View> diagramView = getDiagramView(elementBeforeMe);
		if (!diagramView.isPresent()) {
			return 0;
		}

		int curPadding;
		if (elementBeforeMe == getTarget()) {
			curPadding = insertionY - elementBeforeMe.getTop().orElse(0); // should be similar to offset
		} else {
			curPadding = insertionY - elementBeforeMe.getBottom().orElse(0);
		}

		int reqPadding = layoutHelper().getConstraints().getPadding(RelativePosition.BOTTOM,
				diagramView.get())
				+ layoutHelper().getConstraints().getPadding(RelativePosition.TOP,
						ViewTypes.EXECUTION_SPECIFICATION);
		if (curPadding < reqPadding) {
			return reqPadding - curPadding;
		} else {
			return 0;
		}
	}

	private MElement<? extends Element> getElementBeforeMe(List<MElement<? extends Element>> timeline,
			Optional<MElement<? extends Element>> elementAfterMe) {
		if (!elementAfterMe.isPresent()) {
			return before;
		}

		int index = timeline.indexOf(elementAfterMe.get()) - 1;
		if (index >= 0 && index < timeline.size()) {
			return timeline.get(index);
		}
		return before;
	}

}
