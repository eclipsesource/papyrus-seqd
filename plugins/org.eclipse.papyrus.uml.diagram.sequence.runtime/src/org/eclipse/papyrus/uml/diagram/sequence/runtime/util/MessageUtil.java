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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util;

import static org.eclipse.uml2.common.util.UML2Util.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Utilities (extension methods) for working with {@link Message}s.
 *
 * @author Christian W. Damus
 */
public class MessageUtil {

	private static final Set<MessageSort> SIGNATURE_SORTS = EnumSet.of( //
			MessageSort.ASYNCH_CALL_LITERAL, MessageSort.SYNCH_CALL_LITERAL, //
			MessageSort.ASYNCH_SIGNAL_LITERAL, //
			MessageSort.REPLY_LITERAL);

	private static final Set<MessageSort> CALL_SORTS = EnumSet.of( //
			MessageSort.ASYNCH_CALL_LITERAL, MessageSort.SYNCH_CALL_LITERAL);

	/**
	 * Not instantiable by clients.
	 */
	private MessageUtil() {
		super();
	}

	/**
	 * Obtains the reply message from the execution of a {@code call}.
	 * 
	 * @param call
	 *            an operation call message
	 * @return the reply
	 * @throws IllegalArgumentException
	 *             if the {@code call} is not of {@linkplain MessageSort#SYNCH_CALL_LITERAL synchronous} or
	 *             {@linkplain MessageSort#ASYNCH_CALL_LITERAL asynchronous} call sort
	 */
	public static Optional<Message> getReplyMessage(Message call) {
		if (!CALL_SORTS.contains(call.getMessageSort())) {
			throw new IllegalArgumentException("not a call message"); //$NON-NLS-1$
		}

		MessageEnd receive = call.getReceiveEvent();
		return Optional.ofNullable(receive)
				// Get the execution that it starts
				.flatMap(end -> CrossReferenceUtil.invertSingle(end,
						UMLPackage.Literals.EXECUTION_SPECIFICATION__START, ExecutionSpecification.class))
				// And that execution's finish
				.map(ExecutionSpecification::getFinish).filter(MessageEnd.class::isInstance)
				.map(MessageEnd.class::cast)
				// And its reply message
				.map(MessageEnd::getMessage).filter(msg -> msg.getMessageSort() == MessageSort.REPLY_LITERAL);
	}

	/**
	 * Queries whether a {@code message} supports a signature of some kind.
	 * 
	 * @param message
	 *            a message
	 * @return whether it may reference a {@link Message#getSignature() signature}
	 * @see Message#getSignature()
	 */
	public static boolean canHaveSignature(Message message) {
		return SIGNATURE_SORTS.contains(message.getMessageSort());
	}

	private static Stream<? extends Classifier> getSignatureOwners(Message message, boolean filtered) {
		// If it's a reply message, sender owns the signature, not the receiver
		MessageEnd owningEnd = message.getMessageSort() == MessageSort.REPLY_LITERAL ? message.getSendEvent()
				: message.getReceiveEvent();
		MessageEnd requestingEnd = message.getMessageSort() == MessageSort.REPLY_LITERAL
				? message.getReceiveEvent()
				: message.getSendEvent();

		Optional<ConnectableElement> requestee = Optional.ofNullable(owningEnd)
				.flatMap(MessageUtil::getCovered).map(Lifeline::getRepresents);
		Optional<ConnectableElement> requestor = Optional.ofNullable(requestingEnd)
				.flatMap(MessageUtil::getCovered).map(Lifeline::getRepresents);

		class OwnersSwitch extends UMLSwitch<List<? extends Classifier>> {
			private final boolean isReceiver;

			OwnersSwitch(boolean isReceiver) {
				super();

				this.isReceiver = isReceiver;
			}

			@Override
			public List<? extends Classifier> casePort(Port port) {
				return isReceiver ? port.getProvideds() : port.getRequireds();
			}

			@Override
			public List<? extends Classifier> caseTypedElement(TypedElement element) {
				Type type = element.getType();
				return (type == null) ? Collections.emptyList() : doSwitch(type);
			}

			@Override
			public List<? extends Classifier> caseComponent(Component component) {
				return isReceiver ? component.getProvideds() : component.getRequireds();
			}

			@Override
			public List<? extends Classifier> caseClassifier(Classifier classifier) {
				return Collections.singletonList(classifier);
			}

			@Override
			public List<? extends Classifier> defaultCase(EObject object) {
				return Collections.emptyList();
			}
		}

		List<? extends Classifier> result = requestee.map(new OwnersSwitch(true)::doSwitch)
				.orElseGet(Collections::emptyList);

		if (result.size() == 1) {
			// It's the only option. Return it now
			return result.stream();
		}

		// If there's not exactly one classifier, then we have a composite structure provideds situation
		List<? extends Classifier> required = requestor.map(new OwnersSwitch(false)::doSwitch)
				.orElseGet(Collections::emptyList);

		if (filtered && !result.isEmpty() && !required.isEmpty()) {
			// Only take the receiving classifiers that can also send the message
			result = new ArrayList<Classifier>(result);
			result.retainAll(required);
		}

		if (result.isEmpty()) {
			// Fall back to required classifiers on the sending end
			result = required;
		}

		return result.stream();
	}

	public static Optional<Lifeline> getCovered(MessageEnd messageEnd) {
		List<Lifeline> result;

		if (messageEnd instanceof InteractionFragment) {
			InteractionFragment fragment = (InteractionFragment)messageEnd;
			result = fragment.getCovereds();
		} else {
			result = Collections.emptyList();
		}

		return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	}

	public static Optional<Classifier> getRepresentedType(Lifeline lifeline) {
		return Optional.ofNullable(lifeline.getRepresents()).map(TypedElement::getType)
				.filter(Classifier.class::isInstance).map(Classifier.class::cast);
	}

	public static Stream<Operation> getCallableOperations(Message message) {
		return getCallableOperations(message, null);
	}

	public static Stream<Operation> getCallableOperations(Message message, String name) {
		Stream<? extends Classifier> classifiers = getSignatureOwners(message, false);
		Stream<Operation> result = classifiers.flatMap(c -> c.getAllOperations().stream());

		if (name != null) {
			// Filter by name
			result = result.filter(op -> name.equals(op.getName()));
		}

		// Don't repeat ourselves
		result = result.distinct();

		return result;
	}

	public static Stream<Signal> getReceivableSignals(Message message) {
		return getReceivableSignals(message, null);
	}

	public static Stream<Signal> getReceivableSignals(Message message, String name) {
		Stream<? extends Classifier> classifiers = getSignatureOwners(message, false);

		Stream<Signal> result = classifiers.flatMap(c -> c.allFeatures().stream())
				.filter(Reception.class::isInstance).map(Reception.class::cast).map(Reception::getSignal)
				.filter(Objects::nonNull)
				.flatMap(signal -> ClassifierUtil.subtypeHierarchy(signal, Signal.class));

		if (name != null) {
			// Filter by name
			result = result.filter(op -> name.equals(op.getName()));
		}

		// Don't repeat ourselves (signal subtype hierarchies can overlap)
		result = result.distinct();

		return result;
	}

	public static Stream<Operation> getRepliedOperations(Message message) {
		return getRepliedOperations(message, null);
	}

	public static Stream<Operation> getRepliedOperations(Message message, String name) {
		Optional<Operation> requested = getRequestMessage(message).map(Message::getSignature)
				.filter(Operation.class::isInstance).map(Operation.class::cast);
		if (requested.isPresent()) {
			// Must reply this requested operation, if it matches
			Operation result = requested.get();
			if (isEmpty(name) || name.equals(result.getName())) {
				return Stream.of(result);
			}
			return Stream.empty();
		}

		Stream<? extends Classifier> classifiers = getSignatureOwners(message, false);
		Stream<Operation> result = classifiers.flatMap(c -> c.getAllOperations().stream());

		if (name != null) {
			// Filter by name
			result = result.filter(op -> name.equals(op.getName()));
		}

		// Don't repeat ourselves
		result = result.distinct();

		return result;
	}

	public static Stream<Parameter> getAssignableOutputs(Message message) {
		return getAssignableOutputs(message, null);
	}

	public static Stream<Parameter> getAssignableOutputs(Message message, String name) {
		Stream<Operation> operations;
		if (message.getSignature() instanceof Operation) {
			Operation signature = (Operation)message.getSignature();
			if (isEmpty(name) || name.equals(signature.getName())) {
				operations = Stream.of(signature);
			} else {
				operations = Stream.empty();
			}
		} else if (!isEmpty(name)) {
			operations = MessageUtil.getRepliedOperations(message, name);
		} else {
			operations = MessageUtil.getRepliedOperations(message);
		}

		// Don't suggest return parameters because they don't belong among the named
		// outputs
		return operations.map(Operation::getOwnedParameters).flatMap(Collection::stream)
				.filter(OperationUtil::isOutput)
				.filter(param -> param.getDirection() != ParameterDirectionKind.RETURN_LITERAL);
	}

	public static Stream<ConnectableElement> getAssignableTargets(Message message) {
		Stream<Property> lifelineProperties = MessageUtil.getCovered(message.getReceiveEvent()) //
				.flatMap(MessageUtil::getRepresentedType) //
				.map(Classifier::allAttributes).map(Collection::stream).orElseGet(Stream::empty);

		Interaction interaction = message.getInteraction();
		Stream<Parameter> interactionOutputs = interaction.getOwnedParameters().stream()
				.filter(OperationUtil::isOutput);

		Classifier context = interaction.getContext();
		if (context == null) {
			context = interaction;
		}
		Stream<Property> contextProperties = context.allAttributes().stream();

		return Stream.concat(Stream.concat(lifelineProperties, interactionOutputs), contextProperties);
	}

	/**
	 * Gets the request message that a {@code reply} message is answering.
	 * 
	 * @param reply
	 *            a reply message
	 * @return the call message received by the start of an execution specification finished by the sending of
	 *         the {@code reply}, or {@code null} otherwise
	 * @throws IllegalArgumentException
	 *             if the message is not a reply
	 */
	public static Optional<Message> getRequestMessage(Message reply) {
		if (reply.getMessageSort() != MessageSort.REPLY_LITERAL) {
			throw new IllegalArgumentException("not a reply message"); //$NON-NLS-1$
		}

		if (reply.getSendEvent() == null) {
			return Optional.empty();
		}

		Optional<ExecutionSpecification> exec = CrossReferenceUtil.invertSingle(reply.getSendEvent(),
				UMLPackage.Literals.EXECUTION_SPECIFICATION__FINISH, ExecutionSpecification.class);

		return exec.map(ExecutionSpecification::getStart).filter(MessageEnd.class::isInstance)
				.map(MessageEnd.class::cast).map(MessageEnd::getMessage)
				.filter(msg -> CALL_SORTS.contains(msg.getMessageSort()));
	}

	/**
	 * Create the UML representation of a wildcard ({@code '-'}) request message argument.
	 * 
	 * @return the wildcard
	 */
	public static ValueSpecification createWildcardRequestMessageArgument() {
		Expression result = UMLFactory.eINSTANCE.createExpression();

		// This denotes the wildcard (UML 2.5.1 §17.4.3.1)
		result.setSymbol(""); //$NON-NLS-1$

		return result;
	}

	/**
	 * Queries whether a {@code value} specification is the UML representation of a wildcard ({@code '-'})
	 * request message argument.
	 * 
	 * @param value
	 *            a value specification
	 * @return whether it is a wildcard request message argument
	 */
	public static boolean isWildcardRequestMessageArgument(ValueSpecification value) {
		boolean result = value instanceof Expression && value.getOwner() instanceof Message;

		if (result) {
			Expression expression = (Expression)value;

			// This denotes the wildcard (UML 2.5.1 §17.4.3.1)
			result = "".equals(expression.getSymbol()) && expression.getOperands().isEmpty(); //$NON-NLS-1$
		}

		return result;
	}

	public static String getAssignmentTarget(ValueSpecification replyArgument) {
		String result = null;

		if (replyArgument instanceof Expression) {
			Expression expr = (Expression)replyArgument;

			// The symbol is the assignment target name (UML 2.5.1 §17.4.3.1)
			if (!isEmpty(expr.getSymbol())) {
				result = expr.getSymbol();
			}
		}

		return result;
	}

	public static ValueSpecification getAssignedValue(ValueSpecification replyArgument) {
		ValueSpecification result = null;

		if (replyArgument instanceof Expression) {
			Expression expr = (Expression)replyArgument;

			if (!expr.getOperands().isEmpty()) {
				result = expr.getOperands().get(0);
			}
		}

		return result;
	}

}
