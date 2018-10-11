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

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

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
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * An execution specification creation operation.
 *
 * @author Christian W. Damus
 */
public class InsertNestedExecutionCommand extends ModelCommand<MExecutionImpl> implements CreationCommand<ExecutionSpecification> {

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

		super(owner);

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

		super(owner);

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
			referenceY = OptionalInt.of(0);
		} else { // before should be an element on the execution itself.
			referenceY = OptionalInt.of(
					layoutHelper().getBottom(reference).getAsInt() - layoutHelper().getTop(getTargetView()));
		}

		if (!referenceY.isPresent()) {
			return UnexecutableCommand.INSTANCE;
		}

		MElement<? extends Element> insertionPoint = normalizeFragmentInsertionPoint(before);
		Function<Element, CreationParameters> paramsFactory = CreationParameters::after;

		if (insertionPoint == getTarget()) {
			// Insert before the first covering fragment, if any
			Optional<MElement<? extends Element>> covering = Optional.of(((MExecution)insertionPoint));
			if (covering.isPresent()) {
				insertionPoint = covering.get();
				paramsFactory = CreationParameters::after;
			}
		}

		SemanticHelper semantics = semanticHelper();
		CreationParameters execParams = paramsFactory.apply(insertionPoint.getElement());
		execParams.setEClass(eClass);
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

		result = result.chain(diagramHelper().createNestedExecutionShape(resultCommand, getTargetView(),
				referenceY.getAsInt() + offset, height));

		// Now we have commands to add the execution specification. But, first we must make
		// room for it in the diagram. Nudge the element that will follow the new execution
		int spaceAdded = offset + height;
		Optional<Command> makeSpace = getTarget().getOwner().following(insertionPoint).map(el -> {
			return el.nudge(spaceAdded);
		});
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
}
