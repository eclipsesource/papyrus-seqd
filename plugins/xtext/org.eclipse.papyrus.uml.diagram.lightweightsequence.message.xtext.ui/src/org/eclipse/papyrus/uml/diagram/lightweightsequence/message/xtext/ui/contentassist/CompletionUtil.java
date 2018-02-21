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

package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.ui.contentassist;

import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.OperationUtil.isInput;
import static org.eclipse.uml2.common.util.UML2Util.isEmpty;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.BooleanValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.IntegerValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.NullValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RealValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.StringValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UnlimitedNaturalValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.Value;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.WildcardMessageArgument;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.util.LwMessageSwitch;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * This is the {@code CompletionUtil} type. Enjoy.
 *
 * @author Christian W. Damus
 */
public class CompletionUtil {

	/**
	 * Not instantiable by clients.
	 */
	private CompletionUtil() {
		super();
	}

	public static Optional<Signal> findSignal(Message message, String name,
			List<MessageArgument> arguments) {

		Stream<Signal> signals = MessageUtil.getReceivableSignals(message, name);
		return signals.filter(s -> matchArguments(s, arguments)).findAny();
	}

	private static boolean matchArguments(Signal signal, List<MessageArgument> arguments) {
		final boolean byName = !arguments.isEmpty() && !isEmpty(arguments.get(0).getName());
		int index = 0;

		List<Property> attributes = signal.getAllAttributes();
		for (MessageArgument arg : arguments) {
			Property property = null;
			if (byName && !isEmpty(arg.getName())) {
				property = signal.getAttribute(arg.getName(), null);
				if (property == null) {
					property = (Property) signal.getInheritedMember(arg.getName(), false,
							UMLPackage.Literals.PROPERTY);
				}
			} else if (!byName && (index < attributes.size())) {
				property = attributes.get(index);
				index++;
			}

			if ((property == null) || (property.getType() == null)) {
				return false;
			}

			if (!matchArgument(arg, property.getType())) {
				return false;
			}
		}

		return true;
	}

	public static Optional<Operation> findOperation(Message message, String name,
			RequestMessage requestMessage) {

		Stream<Operation> operations = MessageUtil.getCallableOperations(message, name);
		return operations.filter(op -> matchInputs(op, requestMessage.getArguments())).findAny();
	}

	public static Optional<Operation> findOperation(Message message, String name,
			ReplyMessage replyMessage) {

		Stream<Operation> operations = MessageUtil.getRepliedOperations(message, name);
		return operations.filter(op -> matchOutputs(op, replyMessage.getOutputs())).findAny();
	}

	private static boolean matchInputs(Operation operation, List<MessageArgument> arguments) {
		final boolean byName = !arguments.isEmpty() && !isEmpty(arguments.get(0).getName());
		int index = 0;

		List<Parameter> parameters = operation.getOwnedParameters();
		for (MessageArgument arg : arguments) {
			Parameter parameter = null;
			if (byName) {
				parameter = isEmpty(arg.getName())
						// Must correspond to the return result
						? operation.getReturnResult()
						: operation.getOwnedParameter(arg.getName(), null);
			} else if (!byName) {
				while (index < parameters.size()) {
					parameter = parameters.get(index);
					index++;

					if (isInput(parameter)) {
						break;
					}
				}
			}

			if ((parameter == null) || (parameter.getType() == null)) {
				return false;
			}

			if (!matchArgument(arg, parameter.getType())) {
				return false;
			}
		}

		return true;
	}

	private static boolean matchArgument(MessageArgument argument, Type type) {
		if (isWildcard(argument)) {
			return true;
		}

		Type argType = getType(type, argument.getValue());
		if (argType != null) {
			if (argument.getValue() instanceof NullValue) {
				// Null conforms to all types
				return true;
			}
			if (!argType.conformsTo(type)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isWildcard(MessageArgument argument) {
		if (argument instanceof WildcardMessageArgument) {
			return true;
		}

		if (argument.getValue() == null) {
			return false;
		}

		// TODO: Support for expressions
		// Value value = argument.getValue();
		// if (value instanceof Expression) {
		// Expression expr = (Expression) value;
		// return ((expr.getSymbol() == null) || expr.getSymbol().isEmpty())
		// && (expr.getOperands().size() == 0);
		// }

		return false;
	}

	private static boolean matchOutputs(Operation operation, List<MessageReplyOutput> outputs) {
		operation.getOwnedParameters();
		for (MessageReplyOutput output : outputs) {
			Parameter parameter = output.getParameter();

			if ((parameter == null) || (parameter.getType() == null)) {
				return false;
			}

			// The value is optional; wildcard if omitted
			MessageArgument value = output.getValue();
			if ((value != null) && !matchArgument(value, parameter.getType())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Compute the type, in the reosurce-set of a {@code context} element, of a
	 * parsed message argument {@code value}.
	 *
	 * @param context
	 *            the context in which to resolve the type
	 * @param value
	 *            a parsed {@linkplain MessageArgument#getValue() message argument
	 *            value}
	 *
	 * @return the {@code value}'s type
	 */
	public static Type getType(Element context, Value value) {
		if (value == null) {
			return null;
		}

		return new LwMessageSwitch<Type>() {
			@Override
			public Type caseBooleanValue(BooleanValue booleanValue) {
				return getUMLPrimitiveType(context, TypesPackage.Literals.BOOLEAN);
			}

			@Override
			public Type caseIntegerValue(IntegerValue integerValue) {
				return getUMLPrimitiveType(context, TypesPackage.Literals.INTEGER);
			}

			@Override
			public Type caseUnlimitedNaturalValue(UnlimitedNaturalValue unlimitedNaturalValue) {
				return getUMLPrimitiveType(context, TypesPackage.Literals.UNLIMITED_NATURAL);
			}

			@Override
			public Type caseRealValue(RealValue realValue) {
				return getUMLPrimitiveType(context, TypesPackage.Literals.REAL);
			}

			@Override
			public Type caseStringValue(StringValue stringValue) {
				return getUMLPrimitiveType(context, TypesPackage.Literals.STRING);
			}

			@Override
			public Type caseNullValue(NullValue nulLValue) {
				return null;
			}
		}.doSwitch(value);
	}

	private static Type getUMLPrimitiveType(EObject context, EDataType metatype) {
		ResourceSet rset = context.eResource().getResourceSet();
		Resource resource = rset.getResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI),
				true);
		Package library = (Package) EcoreUtil.getObjectByType(resource.getContents(),
				UMLPackage.Literals.PACKAGE);
		return library.getOwnedType(metatype.getName());
	}
}
