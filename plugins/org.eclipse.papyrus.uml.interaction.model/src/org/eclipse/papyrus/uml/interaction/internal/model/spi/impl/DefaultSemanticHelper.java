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

package org.eclipse.papyrus.uml.interaction.internal.model.spi.impl;

import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.CompoundModelCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationCommand;
import org.eclipse.papyrus.uml.interaction.model.CreationParameters;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredAddCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredCreateCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredDeleteCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.DeferredSetCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.ElementRemovalCommandImpl;
import org.eclipse.papyrus.uml.interaction.model.spi.RemovalCommand;
import org.eclipse.papyrus.uml.interaction.model.spi.SemanticHelper;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Default implementation of the {@link SemanticHelper} SPI.
 *
 * @author Christian W. Damus
 */
public class DefaultSemanticHelper implements SemanticHelper {

	private final EditingDomain editingDomain;

	/**
	 * Initializes me with my contextual editing domain.
	 * 
	 * @param editingDomain
	 *            my contextual editing domain
	 */
	public DefaultSemanticHelper(EditingDomain editingDomain) {
		super();

		this.editingDomain = editingDomain;
	}

	@SuppressWarnings("unchecked")
	protected static <E> EList<E> elist(EObject owner, EStructuralFeature feature) {
		Object value = owner.eGet(feature);
		if (value instanceof EList<?>) {
			return (EList<E>)value;
		}
		if (value != null) {
			return ECollections.singletonEList((E)value);
		}
		return ECollections.emptyEList();
	}

	//
	// Generic commands
	//

	@Override
	public Command add(EObject owner, EStructuralFeature feature, Collection<?> values) {
		return AddCommand.create(editingDomain, owner, feature, values);
	}

	@Override
	public Command add(EObject owner, EStructuralFeature feature, Object value) {
		return add(owner, feature, singleton(value));
	}

	@Override
	public Command set(EObject owner, EStructuralFeature feature, Object value) {
		return SetCommand.create(editingDomain, owner, feature, value);
	}

	@Override
	public Command delete(EObject toDelete) {
		return DeleteCommand.create(editingDomain, toDelete);
	}

	private RemovalCommand<Element> removalCommand(Element toDelete) {
		return new ElementRemovalCommandImpl(editingDomain, delete(toDelete), toDelete);
	}

	private RemovalCommand<Element> deferredRemovalCommand(Supplier<? extends Element> toDelete) {
		return new ElementRemovalCommandImpl(editingDomain,
				new DeferredDeleteCommand(editingDomain, toDelete), toDelete.get());
	}

	@Override
	public Command insertAfter(EObject owner, EStructuralFeature feature, Object before,
			Collection<?> values) {
		int index = elist(owner, feature).indexOf(before);
		if (index >= 0) {
			// insert after
			index = index + 1;
		} // else just append (the -1 case)
		return AddCommand.create(editingDomain, owner, feature, values, index);
	}

	@Override
	public Command insertAfter(EObject owner, EStructuralFeature feature, Object before, Object value) {
		return insertAfter(owner, feature, before, singleton(value));
	}

	@Override
	public Command insertAfter(EObject before, Collection<? extends EObject> objects) {
		EObject container = before.eContainer();
		EReference containment = before.eContainmentFeature();

		EObject any = objects.isEmpty() ? null : objects.iterator().next();
		if (containment.getEReferenceType().isInstance(any)) {
			return insertAfter(container, containment, before, objects);
		}

		out: for (containment = null; container != null; container = container.eContainer()) {
			for (EReference next : container.eClass().getEAllContainments()) {
				if (next.getEReferenceType().isInstance(any)) {
					containment = next;
					break out;
				}
			}
		}

		if (containment == null) {
			// Cannot add this object to the model
			return UnexecutableCommand.INSTANCE;
		}

		return add(container, containment, objects);
	}

	@Override
	public Command insertAfter(EObject before, EObject object) {
		EObject container = before.eContainer();
		EReference containment = before.eContainmentFeature();

		if (containment.getEReferenceType().isInstance(object)) {
			return insertAfter(container, containment, before, object);
		}

		out: for (containment = null; container != null; container = container.eContainer()) {
			for (EReference next : container.eClass().getEAllContainments()) {
				if (next.getEReferenceType().isInstance(object)) {
					containment = next;
					break out;
				}
			}
		}

		if (containment == null) {
			// Cannot add this object to the model
			return UnexecutableCommand.INSTANCE;
		}

		return add(container, containment, object);
	}

	@Override
	public Command insertBefore(EObject owner, EStructuralFeature feature, Object after,
			Collection<?> values) {

		int index = elist(owner, feature).indexOf(after); // -1 indicates to append, which we want
		return AddCommand.create(editingDomain, owner, feature, values, index);
	}

	@Override
	public Command insertBefore(EObject owner, EStructuralFeature feature, Object after, Object value) {
		return insertBefore(owner, feature, after, singleton(value));
	}

	@Override
	public Command insertBefore(EObject after, Collection<? extends EObject> objects) {
		EObject container = after.eContainer();
		EReference containment = after.eContainmentFeature();

		EObject any = objects.isEmpty() ? null : objects.iterator().next();
		if (containment.getEReferenceType().isInstance(any)) {
			return insertBefore(container, containment, after, objects);
		}

		out: for (containment = null; container != null; container = container.eContainer()) {
			for (EReference next : container.eClass().getEAllContainments()) {
				if (next.getEReferenceType().isInstance(any)) {
					containment = next;
					break out;
				}
			}
		}

		if (containment == null) {
			// Cannot add this object to the model
			return UnexecutableCommand.INSTANCE;
		}

		return add(container, containment, objects);
	}

	@Override
	public Command insertBefore(EObject after, EObject object) {
		EObject container = after.eContainer();
		EReference containment = after.eContainmentFeature();

		if (containment.getEReferenceType().isInstance(object)) {
			return insertBefore(container, containment, after, object);
		}

		out: for (containment = null; container != null; container = container.eContainer()) {
			for (EReference next : container.eClass().getEAllContainments()) {
				if (next.getEReferenceType().isInstance(object)) {
					containment = next;
					break out;
				}
			}
		}

		if (containment == null) {
			// Cannot add this object to the model
			return UnexecutableCommand.INSTANCE;
		}

		return add(container, containment, object);
	}

	//
	// Interaction commands
	//

	@Override
	public CreationCommand<Lifeline> createLifeline(CreationParameters parameters) {
		parameters.setEClass(UMLPackage.Literals.LIFELINE);
		Supplier<Lifeline> occurrence = () -> {
			Interaction interaction = (Interaction)parameters.getContainer();
			Lifeline result = UMLFactory.eINSTANCE.createLifeline();
			autoname(result, "Lifeline", interaction.getLifelines()); //$NON-NLS-1$
			return result;
		};

		return new DeferredCreateCommand<>(Lifeline.class, editingDomain, parameters, occurrence);
	}

	@Override
	public CreationCommand<ExecutionSpecification> createExecutionSpecification(Element specification,
			CreationParameters parameters) {

		Supplier<ExecutionSpecification> exec;

		if (specification == null) {
			if (parameters.getEClass() == null) {
				exec = UMLFactory.eINSTANCE::createBehaviorExecutionSpecification;
				parameters.setEClass(UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);
			} else {
				exec = () -> (ExecutionSpecification)UMLFactory.eINSTANCE.create(parameters.getEClass());
			}
		} else {
			exec = new UMLSwitch<Supplier<ExecutionSpecification>>() {
				@Override
				public Supplier<ExecutionSpecification> caseAction(Action action) {
					parameters.setEClass(UMLPackage.Literals.ACTION_EXECUTION_SPECIFICATION);
					return () -> {
						ActionExecutionSpecification result = UMLFactory.eINSTANCE
								.createActionExecutionSpecification();
						result.setAction(action);
						return result;
					};
				}

				@Override
				public Supplier<ExecutionSpecification> caseBehavior(Behavior behavior) {
					parameters.setEClass(UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);
					return () -> {
						BehaviorExecutionSpecification result = UMLFactory.eINSTANCE
								.createBehaviorExecutionSpecification();
						result.setBehavior(behavior);
						return result;
					};
				}

				@Override
				public Supplier<ExecutionSpecification> defaultCase(EObject object) {
					parameters.setEClass(UMLPackage.Literals.BEHAVIOR_EXECUTION_SPECIFICATION);
					return UMLFactory.eINSTANCE::createBehaviorExecutionSpecification;
				}
			}.doSwitch(specification);

		}

		exec = autoname(exec, "Execution", () -> ((Interaction)parameters.getContainer()).getFragments());
		parameters.setContainment(UMLPackage.Literals.INTERACTION__FRAGMENT);

		return new DeferredCreateCommand<>(ExecutionSpecification.class, editingDomain, parameters, exec);
	}

	@Override
	public CreationCommand<OccurrenceSpecification> createStart(Supplier<ExecutionSpecification> execution,
			CreationParameters parameters) {

		parameters.setEClass(UMLPackage.Literals.EXECUTION_OCCURRENCE_SPECIFICATION);
		Supplier<OccurrenceSpecification> occurrence = () -> {
			ExecutionSpecification exec = execution.get();
			ExecutionOccurrenceSpecification result = UMLFactory.eINSTANCE
					.createExecutionOccurrenceSpecification();
			result.setExecution(exec);
			return result;
		};

		CreationCommand<OccurrenceSpecification> result = new DeferredCreateCommand<>(
				OccurrenceSpecification.class, editingDomain, parameters, occurrence);
		Command setStart = new DeferredSetCommand(editingDomain, execution,
				UMLPackage.Literals.EXECUTION_SPECIFICATION__START, result);

		return result.andThen(editingDomain, setStart);
	}

	@Override
	public CreationCommand<OccurrenceSpecification> createFinish(Supplier<ExecutionSpecification> execution,
			CreationParameters parameters) {

		parameters.setEClass(UMLPackage.Literals.EXECUTION_OCCURRENCE_SPECIFICATION);
		Supplier<OccurrenceSpecification> occurrence = () -> {
			ExecutionSpecification exec = execution.get();
			ExecutionOccurrenceSpecification result = UMLFactory.eINSTANCE
					.createExecutionOccurrenceSpecification();
			result.setExecution(exec);
			return result;
		};

		CreationCommand<OccurrenceSpecification> result = new DeferredCreateCommand<>(
				OccurrenceSpecification.class, editingDomain, parameters, occurrence);
		Command setFinish = new DeferredSetCommand(editingDomain, execution,
				UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH, result);

		return result.andThen(editingDomain, setFinish);
	}

	@Override
	public CreationCommand<MessageEnd> createMessageOccurrence(CreationParameters parameters) {
		return createMessageOccurrence(parameters, UMLPackage.Literals.MESSAGE_OCCURRENCE_SPECIFICATION);
	}

	@Override
	public CreationCommand<MessageEnd> createDestructionOccurrence(CreationParameters parameters) {
		return createMessageOccurrence(parameters, UMLPackage.Literals.DESTRUCTION_OCCURRENCE_SPECIFICATION);
	}

	private CreationCommand<MessageEnd> createMessageOccurrence(CreationParameters parameters,
			EClass eClass) {
		parameters.setEClass(eClass);
		return new DeferredCreateCommand<>(editingDomain, MessageEnd.class, parameters);
	}

	@Override
	public CreationCommand<Message> createMessage(Supplier<? extends MessageEnd> sendEvent,
			Supplier<? extends MessageEnd> recvEvent, MessageSort sort, NamedElement signature,
			CreationParameters parameters) {

		parameters.setEClass(UMLPackage.Literals.MESSAGE);
		Supplier<Message> message = () -> {
			MessageEnd send = sendEvent.get();
			MessageEnd recv = recvEvent.get();
			Message result = UMLFactory.eINSTANCE.createMessage();

			result.setSendEvent(send);
			result.setReceiveEvent(recv);

			result.setMessageSort(sort);
			result.setSignature(signature);

			return result;
		};

		CreationCommand<Message> result = new DeferredCreateCommand<>(Message.class, editingDomain,
				parameters, message);

		Command sendSetMessage = new DeferredSetCommand(editingDomain, sendEvent,
				UMLPackage.Literals.MESSAGE_END__MESSAGE, result);
		Command recvSetMessage = new DeferredSetCommand(editingDomain, recvEvent,
				UMLPackage.Literals.MESSAGE_END__MESSAGE, result);

		return result.andThen(editingDomain, sendSetMessage).andThen(editingDomain, recvSetMessage);
	}

	@Override
	public RemovalCommand<Element> deleteMessage(Message message) {
		List<RemovalCommand<Element>> commands = new ArrayList<>(3);
		commands.add(removalCommand(message));
		deleteMessageEnd(message.getReceiveEvent()).ifPresent(c -> commands.add(c));
		deleteMessageEnd(message.getSendEvent()).ifPresent(c -> commands.add(c));
		return new ElementRemovalCommandImpl(editingDomain, commands);
	}

	private Optional<RemovalCommand<Element>> deleteMessageEnd(MessageEnd messageEnd) {
		if (messageEnd == null) {
			return Optional.empty();
		}

		if (Gate.class.isInstance(messageEnd)) {
			// TODO JF according to req docs message ends should be deleted. it does not state an exception
			// for gates
			return Optional.empty();
		}

		if (MessageOccurrenceSpecification.class.isInstance(messageEnd) && messageEnd.getMessage() != null) {
			/* find ExecutionSpecifications where this end is start or finish */
			Interaction interaction = messageEnd.getMessage().getInteraction();
			List<ExecutionSpecification> executionSpecifications = interaction.getFragments().stream()
					.filter(f -> ExecutionSpecification.class.isInstance(f))
					.map(f -> ExecutionSpecification.class.cast(f))
					.filter(es -> es.getStart() == messageEnd || es.getFinish() == messageEnd)
					.collect(Collectors.toList());

			/* if this end is neither start nor finish -> simply delete it */
			if (executionSpecifications.isEmpty()) {
				return Optional.of(removalCommand(messageEnd));
			}

			/*
			 * if this end is a start or finish is has to be replaced by an ExecutionOccurrenceSpecification
			 */
			List<Command> commandList = new ArrayList<Command>(executionSpecifications.size() + 1);
			commandList.add(delete(messageEnd));
			executionSpecifications.forEach(es -> {
				boolean isStart = es.getStart() == messageEnd;
				CreationParameters parameters = isStart ? CreationParameters.before(es)
						: CreationParameters.after(es);
				CreationCommand<OccurrenceSpecification> creationAndSet = isStart
						? createStart(() -> es, parameters)
						: createFinish(() -> es, parameters);
				commandList.add(creationAndSet);
				/* also add to lifeline */
				es.getCovereds().stream().findFirst().ifPresent(l -> commandList.add(
						new DeferredAddCommand(l, UMLPackage.Literals.LIFELINE__COVERED_BY, creationAndSet)));
			});
			return Optional.of(new ElementRemovalCommandImpl(editingDomain,
					CompoundModelCommand.compose(editingDomain, commandList)));
		}

		return Optional.of(removalCommand(messageEnd));
	}

	@Override
	public RemovalCommand<Element> deleteExecutionSpecification(ExecutionSpecification execution) {
		List<RemovalCommand<Element>> commands = new ArrayList<>(3);
		commands.add(deferredRemovalCommand(execution::getStart));
		commands.add(deferredRemovalCommand(execution::getFinish));
		commands.add(removalCommand(execution));
		return new ElementRemovalCommandImpl(editingDomain, commands);
	}

	@Override
	public RemovalCommand<Element> deleteLifeline(Lifeline lifeline) {
		return removalCommand(lifeline);
	}

	//
	// Utilities
	//

	void autoname(NamedElement element, String base, List<? extends NamedElement> others) {
		for (int i = 1;; i++) {
			String next = base + i;

			if (others.stream().allMatch(e -> !next.equals(e.getName()))) {
				element.setName(next);
				break;
			}
		}
	}

	<N extends NamedElement> Supplier<N> autoname(Supplier<N> element, String base,
			Supplier<? extends List<? extends NamedElement>> others) {
		return () -> {
			List<? extends NamedElement> peers = others.get();
			N result = element.get();
			autoname(result, base, peers);
			return result;
		};
	}

}
