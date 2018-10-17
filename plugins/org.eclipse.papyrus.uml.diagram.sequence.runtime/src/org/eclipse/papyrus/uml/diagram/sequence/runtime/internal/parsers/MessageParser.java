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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.parsers;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.getAssignedValue;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.getAssignmentTarget;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.isWildcardRequestMessageArgument;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.OperationUtil.isOutput;
import static org.eclipse.uml2.common.util.UML2Util.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.OperationUtil;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.ValueSpecificationUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Parser for {@link Message} labels.
 *
 * @author Christian W. Damus
 */
public class MessageParser extends MessageFormatParser implements ISemanticParser {

	/**
	 * Initializes me.
	 */
	public MessageParser() {
		super(new EAttribute[] {UMLPackage.Literals.NAMED_ELEMENT__NAME });
	}

	protected EStructuralFeature getEStructuralFeature(Object notification) {
		EStructuralFeature featureImpl = null;
		if (notification instanceof Notification) {
			Object feature = ((Notification)notification).getFeature();
			if (feature instanceof EStructuralFeature) {
				featureImpl = (EStructuralFeature)feature;
			}
		}
		return featureImpl;
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		EStructuralFeature feature = getEStructuralFeature(event);
		return isValidFeature(feature);
	}

	@Override
	public boolean areSemanticElementsAffected(EObject listener, Object notification) {
		EStructuralFeature feature = getEStructuralFeature(notification);
		return isValidFeature(feature);
	}

	/**
	 * Determines if the given feature has to be taken into account in this parser
	 *
	 * @param feature
	 *            the feature to test
	 * @return true if is valid, false otherwise
	 */
	private boolean isValidFeature(EStructuralFeature feature) {
		return UMLPackage.eINSTANCE.getNamedElement_Name().equals(feature)
				|| UMLPackage.Literals.TYPED_ELEMENT__TYPE.equals(feature)
				|| UMLPackage.eINSTANCE.getLiteralInteger_Value().equals(feature)
				|| UMLPackage.eINSTANCE.getLiteralUnlimitedNatural_Value().equals(feature)
				|| UMLPackage.eINSTANCE.getLiteralBoolean_Value().equals(feature)
				|| UMLPackage.eINSTANCE.getLiteralString_Value().equals(feature)
				|| UMLPackage.Literals.MESSAGE__SIGNATURE.equals(feature)
				|| UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER.equals(feature);
	}

	@Override
	public String getPrintString(IAdaptable adapter, int flags) {
		EObject obj = adapter.getAdapter(EObject.class);
		StringBuilder result = new StringBuilder();

		if (obj instanceof Message) {
			Message message = (Message)obj;
			NamedElement signature = message.getSignature();

			if (message.getMessageSort() == MessageSort.REPLY_LITERAL) {
				// Reply messages may have an assignment-target for the operation return result
				appendReplyResultAssignment(message, result);
			}

			if (signature != null) {
				// The message name is required to be the signature name
				result.append(UMLLabelInternationalization.getInstance().getLabel(signature));
			} else {
				// Assume the message has its own name
				result.append(UMLLabelInternationalization.getInstance().getLabel(message));
			}

			switch (message.getMessageSort()) {
				case REPLY_LITERAL:
					appendReplyOutputs(message, result);
					break;
				default:
					// Request message
					appendRequestArguments(message, result);
					break;
			}
		}

		return result.toString();
	}

	/**
	 * Append the request {@code message} arguments.
	 * 
	 * @param message
	 *            a request message
	 * @param result
	 *            the string result to append to
	 */
	protected void appendRequestArguments(Message message, StringBuilder result) {
		if (message.getArguments().isEmpty()) {
			return;
		}

		result.append('(');
		final boolean byName = !isEmpty(message.getArguments().get(0).getName());
		boolean first = true;
		for (ValueSpecification next : message.getArguments()) {
			if (first) {
				first = false;
			} else {
				result.append(", "); //$NON-NLS-1$
			}

			if (byName) {
				result.append(next.getName());
				result.append('=');
			}

			append(next, result);
		}
		result.append(')');
	}

	private static void append(ValueSpecification value, StringBuilder result) {
		if (value == null) {
			result.append(value);
			return;
		}

		if (value instanceof LiteralString) {
			result.append('"');
		}
		if (isWildcardRequestMessageArgument(value)) {
			// Wildcard. For readability, include a space before the next comma or paren
			result.append("- "); //$NON-NLS-1$
		} else {
			result.append(ValueSpecificationUtil.getSpecificationValue(value));
		}
		if (value instanceof LiteralString) {
			result.append('"');
		}
	}

	/**
	 * Append the reply {@code message} arguments, which are outputs of the operation call.
	 * 
	 * @param message
	 *            a reply message
	 * @param result
	 *            the string result to append to
	 */
	protected void appendReplyResultAssignment(Message message, StringBuilder result) {
		if (message.getArguments().isEmpty()) {
			return;
		}

		String assignmentTarget = null;

		Parameter returnResult = getReturnResult(message);
		if (returnResult == null) {
			if (!(message.getSignature() instanceof Operation) && message.getArguments().size() == 1) {
				// Signatureless message may specify a return result assignment
				assignmentTarget = getAssignmentTarget(message.getArguments().get(0));
			}
		} else {
			// Output parameters have to be named, and they correspond to output values
			// by name. But not so the return result, which may be unnamed. But in
			// that case, the corresponding value specification will also be unnamed
			// and will be the only one that is unnamed
			Optional<Expression> assignment = getOutputAssignment(message, returnResult.getName());
			assignmentTarget = assignment.map(Expression::getSymbol)
					.filter(((Predicate<String>)String::isEmpty).negate()).orElse(null);
		}

		if (assignmentTarget != null) {
			result.append(assignmentTarget);
			result.append('=');
		}
	}

	private Optional<Expression> getOutputAssignment(Message message, String outputParameterName) {
		return message.getArguments().stream().filter(Expression.class::isInstance)
				.map(Expression.class::cast)
				.filter(expr -> Objects.equals(expr.getName(), outputParameterName)).findAny();
	}

	private static Parameter getReturnResult(Message message) {
		Operation operation = message.getSignature() instanceof Operation ? (Operation)message.getSignature()
				: null;
		return (operation == null) ? null : operation.getReturnResult();
	}

	/**
	 * Append the reply {@code message} outputs, which are outputs of the operation call.
	 * 
	 * @param message
	 *            a reply message
	 * @param result
	 *            the string result to append to
	 */
	protected void appendReplyOutputs(Message message, StringBuilder result) {
		if (message.getArguments().isEmpty()) {
			return;
		}

		ValueSpecification returnResult = null;

		if (!(message.getSignature() instanceof Operation) && message.getArguments().size() == 1) {
			// Can model a return result for a signatureless message
			returnResult = getAssignedValue(message.getArguments().get(0));
		} else if (message.getSignature() instanceof Operation) {
			Operation operation = (Operation)message.getSignature();
			Parameter returnParam = operation.getReturnResult();

			// Output arguments correspond by name to operation out and in-out parameters
			boolean first = true;
			for (ValueSpecification next : message.getArguments()) {
				Parameter output = isEmpty(next.getName())
						// This must be the return result
						? output = returnParam
						: operation.getOwnedParameter(next.getName(), null);

				if (output == null && returnResult == null && returnParam != null) {
					// Assume that this is the return result, which we had not yet found
					output = returnParam;
				}

				if (output == null || !isOutput(output)) {
					// Not a well-formed reply message
					continue;
				}

				ValueSpecification value = getAssignedValue(next);

				// Is it the return result? Because that comes last
				if (output == returnParam) {
					returnResult = value;
				} else {
					if (first) {
						first = false;
						result.append('(');
					} else {
						result.append(", "); //$NON-NLS-1$
					}

					String assignmentTarget = getAssignmentTarget(next);
					if (assignmentTarget != null) {
						result.append(assignmentTarget);
						result.append('=');
						result.append(output.getName());

						if (value != null) {
							result.append(": "); //$NON-NLS-1$
							append(value, result);
						}
					} else {
						// Unassigned output value
						result.append(output.getName());
						result.append(": "); //$NON-NLS-1$
						append(value, result);
					}
				}
			}

			if (!first) {
				// We emitted at least an output
				result.append(')');
			}
		}

		if (returnResult != null) {
			result.append(": "); //$NON-NLS-1$
			append(returnResult, result);
		}
	}

	@Override
	public List<Element> getSemanticElementsBeingParsed(EObject element) {
		List<Element> result = new ArrayList<Element>();

		if (element instanceof Message) {
			Message message = (Message)element;

			// The message itself, of course
			result.add(message);

			// The message signature with its details
			NamedElement signature = message.getSignature();
			if (signature != null) {
				result.add(signature);

				result.addAll(new UMLSwitch<Collection<? extends Element>>() {
					@Override
					public Collection<? extends Parameter> caseOperation(Operation operation) {
						Predicate<Parameter> parameterFilter;
						switch (message.getMessageSort()) {
							case REPLY_LITERAL:
								parameterFilter = OperationUtil::isOutput;
								break;
							default:
								parameterFilter = OperationUtil::isInput;
								break;
						}
						return operation.getOwnedParameters().stream().filter(parameterFilter)
								.collect(Collectors.toList());
					}

					@Override
					public Collection<? extends Property> caseSignal(Signal signal) {
						return signal.allAttributes();
					}

					@Override
					public Collection<? extends Element> defaultCase(EObject object) {
						return Collections.emptyList();
					}
				}.doSwitch(signature));
			}

			// And everything comprising the message arguments
			if (!message.getArguments().isEmpty()) {
				result.addAll(message.getArguments());
				message.getArguments().stream().map(Element::allOwnedElements).forEach(result::addAll);
			}
		}

		return result;
	}

}
