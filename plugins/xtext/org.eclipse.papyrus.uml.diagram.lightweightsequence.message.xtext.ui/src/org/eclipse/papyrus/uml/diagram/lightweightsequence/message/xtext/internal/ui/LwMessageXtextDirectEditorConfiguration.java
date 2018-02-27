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

package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.internal.ui;

import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils.getInternationalizationPreference;
import static org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.ui.contentassist.CompletionUtil.findOperation;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.createWildcardRequestMessageArgument;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil.getReplyMessage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;
import org.eclipse.papyrus.infra.emf.requests.UnsetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.alf.naming.ALFIDConverter;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AbstractMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AnyMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.Value;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.WildcardMessageArgument;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.util.LwMessageSwitch;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.ui.contentassist.CompletionUtil;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.ui.internal.LwMessageActivator;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.parsers.MessageParser;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.NamedElementUtil;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Injector;

/**
 * Direct editor configuration for the Xtext-based message label editor.
 *
 * @author Christian W. Damus
 */
public class LwMessageXtextDirectEditorConfiguration extends DefaultXtextDirectEditorConfiguration {

	private Supplier<Message> messageContext = () -> null;

	/**
	 * Initializes me.
	 *
	 */
	public LwMessageXtextDirectEditorConfiguration() {
		super();
	}

	//
	// Context capture for context-sensitive parsing
	//

	private void captureContext(EObject context) {
		messageContext = (context instanceof Message)
				? new WeakReference<>((Message) context)::get
				: () -> null;
	}

	@Override
	public CellEditor createCellEditor(Composite parent, EObject semanticObject) {
		captureContext(semanticObject);

		return super.createCellEditor(parent, semanticObject);
	}

	@Override
	public DirectEditManager createDirectEditManager(ITextAwareEditPart host) {
		captureContext(host.getAdapter(EObject.class));

		return super.createDirectEditManager(host);
	}

	@Override
	protected ICommand createInvalidStringCommand(String newString, EObject semanticElement) {
		captureContext(semanticElement);

		return super.createInvalidStringCommand(newString, semanticElement);
	}

	@Override
	public IParser createParser(EObject semanticObject) {
		captureContext(semanticObject);

		return super.createParser(semanticObject);
	}

	@Override
	public Injector getInjector() {
		Message message = messageContext.get();

		if (message == null) {
			return LwMessageActivator.getInstance().getInjector(
					LwMessageActivator.ORG_ECLIPSE_PAPYRUS_UML_DIAGRAM_LIGHTWEIGHTSEQUENCE_MESSAGE_XTEXT_LWMESSAGE);
		}

		// Sub-language based on the message sort, to select a specific subset of the
		// grammar to parse
		switch (message.getMessageSort()) {
		case REPLY_LITERAL:
			return LwMessageActivator.getInstance().getInjector(LwMessageCustomActivator.REPLY_MESSAGE);
		default:
			return LwMessageActivator.getInstance().getInjector(LwMessageCustomActivator.REQUEST_MESSAGE);
		}
	}

	//
	// Display and parsing
	//

	@Override
	public String getTextToEdit(Object objectToEdit) {
		String result;

		if (!(objectToEdit instanceof Message)) {
			result = "<not a message>";
		} else {
			Message message = (Message) objectToEdit;

			// We support parsing the entire textual representation
			result = new MessageParser().getPrintString(new EObjectAdapter(message), 0);
		}

		return result;
	}

	@Override
	protected ICommand getParseCommand(EObject umlObject, EObject xtextObject) {
		final Message message = (Message) umlObject;
		AbstractMessage messageRule = EcoreUtil2.getContainerOfType(xtextObject, AbstractMessage.class);

		// Infer the signature of the message
		completeMessage(message, messageRule);

		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(message);
		CompositeTransactionalCommand result = new CompositeTransactionalCommand(domain, "Message update");

		// Update the message sort, if necessary
		getSetMessageSortCommand(domain, message, messageRule).ifPresent(result::add);

		// Set the signature of the message
		getSetSignatureCommand(domain, message, messageRule).ifPresent(result::add);

		// Set the name of the message
		getSetNameCommand(domain, message, messageRule).ifPresent(result::add);

		// Update message arguments
		getUpdateArgumentsCommand(domain, message, messageRule).ifPresent(result::add);

		// Look for a reply message to set its name and signature, too
		switch (message.getMessageSort()) {
		case SYNCH_CALL_LITERAL:
		case ASYNCH_CALL_LITERAL:
			Optional<Message> reply = getReplyMessage(message);
			reply.flatMap(msg -> getSetNameCommand(domain, msg, messageRule)).ifPresent(result::add);
			reply.flatMap(msg -> getSetSignatureCommand(domain, msg, messageRule)).ifPresent(result::add);
			break;
		default:
			// Other sort don't have replies
			break;
		}

		return result.reduce();
	}

	protected Optional<ICommand> getSetNameCommand(TransactionalEditingDomain domain, Message message,
			AbstractMessage source) {

		ICommand result = null;

		String newName = null;

		if (source instanceof RequestMessage) {
			RequestMessage request = (RequestMessage) source;

			newName = request.getName();
			if (newName == null) {
				if (request.getOperation() != null) {
					newName = request.getOperation().getName();
				} else if (request.getSignal() != null) {
					newName = request.getSignal().getName();
				}
			}
		} else if (source instanceof ReplyMessage) {
			ReplyMessage reply = (ReplyMessage) source;
			newName = reply.getName();
			if ((newName == null) && (reply.getOperation() != null)) {
				newName = reply.getOperation().getName();
			}
		}

		if (newName != null) {
			newName = ALFIDConverter.IDtoName(newName);
		}

		if (newName == null) {
			IElementEditService edit = ElementEditServiceUtils.getCommandProvider(message);
			UnsetRequest unsetName = new UnsetRequest(domain, message,
					UMLPackage.Literals.NAMED_ELEMENT__NAME);
			result = edit.getEditCommand(unsetName);
		} else {
			// Account for Papyrus-style i18n
			UMLLabelInternationalization i18n = UMLLabelInternationalization.getInstance();
			if (getInternationalizationPreference(message) && (i18n.getLabelWithoutUML(message) != null)) {
				result = ICommandWrapper.wrap(i18n.getSetLabelCommand(domain, message, newName, null),
						ICommand.class);
			} else {
				IElementEditService edit = ElementEditServiceUtils.getCommandProvider(message);
				final SetRequest setNameRequest = new SetRequest(domain, message,
						UMLPackage.Literals.NAMED_ELEMENT__NAME, newName);
				result = edit.getEditCommand(setNameRequest);
			}
		}

		return Optional.ofNullable(result);
	}

	protected Optional<ICommand> getSetSignatureCommand(TransactionalEditingDomain domain, Message message,
			AbstractMessage source) {

		ICommand result = null;

		NamedElement newSignature = new LwMessageSwitch<NamedElement>() {
			@Override
			public NamedElement caseAnyMessage(AnyMessage anyMessage) {
				return null;
			}

			@Override
			public NamedElement caseReplyMessage(ReplyMessage replyMessage) {
				return replyMessage.getOperation();
			}

			@Override
			public NamedElement caseRequestMessage(RequestMessage requestMessage) {
				NamedElement result = requestMessage.getOperation();
				if (result == null) {
					result = requestMessage.getSignal();
				}
				return result;
			}
		}.doSwitch(source);

		if (newSignature != message.getSignature()) {
			IElementEditService edit = ElementEditServiceUtils.getCommandProvider(message);

			if (newSignature != null) {
				final SetRequest setSignatureRequest = new SetRequest(domain, message,
						UMLPackage.Literals.MESSAGE__SIGNATURE, newSignature);
				result = edit.getEditCommand(setSignatureRequest);
			} else {
				final UnsetRequest unsetSignatureRequest = new UnsetRequest(domain, message,
						UMLPackage.Literals.MESSAGE__SIGNATURE);
				result = edit.getEditCommand(unsetSignatureRequest);
			}
		}

		return Optional.ofNullable(result);
	}

	protected Optional<ICommand> getSetMessageSortCommand(TransactionalEditingDomain domain,
			Message message, AbstractMessage source) {

		ICommand result = null;
		MessageSort newSort = new LwMessageSwitch<MessageSort>() {
			@Override
			public MessageSort caseAnyMessage(AnyMessage anyMessage) {
				// No change
				return message.getMessageSort();
			}

			@Override
			public MessageSort caseReplyMessage(ReplyMessage replyMessage) {
				return MessageSort.REPLY_LITERAL;
			}

			@Override
			public MessageSort caseRequestMessage(RequestMessage requestMessage) {
				MessageSort result = message.getMessageSort();

				if (requestMessage.getOperation() != null) {
					// See whether an async call would be preferred by the user
					switch (message.getMessageSort()) {
					case ASYNCH_CALL_LITERAL:
					case ASYNCH_SIGNAL_LITERAL:
						result = MessageSort.ASYNCH_CALL_LITERAL;
						break;
					default:
						result = MessageSort.SYNCH_CALL_LITERAL;
						break;
					}
				} else if (requestMessage.getSignal() != null) {
					result = MessageSort.ASYNCH_SIGNAL_LITERAL;
				}

				return result;
			}
		}.doSwitch(source);

		if ((newSort != null) && (newSort != message.getMessageSort())) {
			IElementEditService edit = ElementEditServiceUtils.getCommandProvider(message);
			final SetRequest setSortRequest = new SetRequest(domain, message,
					UMLPackage.Literals.MESSAGE__MESSAGE_SORT, newSort);
			result = edit.getEditCommand(setSortRequest);
		}

		return Optional.ofNullable(result);
	}

	private void completeMessage(Message message, AbstractMessage messageRule) {
		if (messageRule instanceof RequestMessage) {
			completeMessage(message, (RequestMessage) messageRule);
		} else if (messageRule instanceof ReplyMessage) {
			completeMessage(message, (ReplyMessage) messageRule);
		}
	}

	private void completeMessage(Message message, RequestMessage messageRule) {
		String signatureName = messageRule.getName();

		// Look for an operation
		Optional<Operation> operation = findOperation(message, signatureName, messageRule);
		if (operation.isPresent()) {
			operation.ifPresent(messageRule::setOperation);
		} else {
			// Try a signal, then
			Optional<Signal> signal = CompletionUtil.findSignal(message, signatureName,
					messageRule.getArguments());
			signal.ifPresent(messageRule::setSignal);
		}
	}

	private void completeMessage(Message message, ReplyMessage messageRule) {
		String signatureName = messageRule.getName();

		// Look for an operation (there are no replies to signals)
		Optional<Operation> operation = findOperation(message, signatureName, messageRule);
		operation.ifPresent(messageRule::setOperation);
	}

	protected Optional<ICommand> getUpdateArgumentsCommand(TransactionalEditingDomain domain,
			Message message, AbstractMessage source) {

		ICommand result = null;

		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(message);

		// Clean slate
		ICommand destroy = message.getArguments().isEmpty()
				? null
				: destroyAll(domain, edit, message.getArguments());

		// Create arguments
		List<? extends ValueSpecification> arguments = new LwMessageSwitch<List<? extends ValueSpecification>>() {
			@Override
			public List<ValueSpecification> caseRequestMessage(RequestMessage requestMessage) {
				return createArguments(message, requestMessage.getArguments());
			}

			@Override
			public List<Expression> caseReplyMessage(ReplyMessage replyMessage) {
				return createOutputs(message, replyMessage);
			}

			@Override
			public List<ValueSpecification> defaultCase(EObject object) {
				return Collections.emptyList();
			}
		}.doSwitch(source);

		ICommand add;
		if (arguments.isEmpty()) {
			add = null;
		} else {
			SetRequest request = new SetRequest(domain, message, UMLPackage.Literals.MESSAGE__ARGUMENT,
					arguments);
			add = edit.getEditCommand(request);
		}

		if (destroy != null) {
			result = destroy;
		}
		if (result == null) {
			result = add;
		} else if (add != null) {
			result = destroy.compose(add);
		}

		return Optional.ofNullable(result).map(ICommand::reduce);
	}

	protected ICommand destroyAll(TransactionalEditingDomain domain, IElementEditService edit,
			Collection<? extends EObject> objects) {
		ICommand result = null;

		for (EObject next : objects) {
			DestroyElementRequest destroyRequest = new DestroyElementRequest(domain, next, false);
			ICommand destroy = edit.getEditCommand(destroyRequest);
			if (destroy != null) {
				result = (result == null) ? destroy : result.compose(destroy);
			}
		}

		return result == null ? null : result.reduce();
	}

	protected List<ValueSpecification> createArguments(Message message,
			List<MessageArgument> messageArguments) {
		if (messageArguments.isEmpty()) {
			return Collections.emptyList();
		}

		List<ValueSpecification> result;
		ValueSpecificationFactory valueFactory = new ValueSpecificationFactory(message);
		try {
			result = messageArguments.stream().map(valueFactory::create).collect(Collectors.toList());
		} finally {
			valueFactory.copyReferences();
		}

		return result;
	}

	protected List<Expression> createOutputs(Message message, ReplyMessage replyMessage) {
		List<Expression> result = new ArrayList<>(replyMessage.getOutputs().size() + 1);

		ValueSpecificationFactory valueFactory = new ValueSpecificationFactory(message);

		try {
			if (!replyMessage.getOutputs().isEmpty()) {
				replyMessage.getOutputs().stream() //
						.map(output -> createOutput(message, output, valueFactory)) //
						.filter(Optional::isPresent).map(Optional::get) //
						.forEach(result::add);
			}

			// And maybe there's a return assignment
			createOutput(message, replyMessage, valueFactory).ifPresent(result::add);
		} finally {
			valueFactory.copyReferences();
		}

		return result;
	}

	private Optional<Expression> createOutput(Message owner, AssignmentTarget assignment,
			ValueSpecificationFactory valueFactory) {

		Expression result = null;

		if ((assignment.getTarget() != null) || (assignment.getValue() != null)) {
			result = UMLFactory.eINSTANCE.createExpression();

			if (assignment instanceof MessageReplyOutput) {
				Parameter parameter = ((MessageReplyOutput) assignment).getParameter();
				if ((parameter != null) && (parameter.getName() != null)) {
					result.setName(parameter.getName());
				}
			}

			if (assignment.getTarget() != null) {
				result.setSymbol(NamedElementUtil.getQualifiedName(assignment.getTarget(), owner));
			}
			if (assignment.getValue() != null) {
				result.getOperands().add(valueFactory.create(assignment.getValue()));
			}
		}

		return Optional.ofNullable(result);
	}

	//
	// Nested types
	//

	/**
	 * A custom copier that creates {@link ValueSpecification}s from
	 * {@link MessageArgument}s.
	 *
	 * @author Christian W. Damus
	 */
	@SuppressWarnings("serial")
	private static class ValueSpecificationFactory extends EcoreUtil.Copier {

		/** The contextual message of the argument copy. */
		private final Message message;

		ValueSpecificationFactory(Message message) {
			super();

			this.message = message;
		}

		ValueSpecification create(MessageArgument argument) {
			return (ValueSpecification) copy(argument);
		}

		@Override
		protected EObject createCopy(EObject eObject) {
			EObject result;

			// Map the message argument, itself, to the ValueSpecification generated
			// from its contained Value, so that we may copy attributes (e.g., name)
			if (eObject instanceof WildcardMessageArgument) {
				result = createWildcardRequestMessageArgument();
			} else if (eObject instanceof MessageArgument) {
				Value value = ((MessageArgument) eObject).getValue();
				if (value == null) {
					result = null;
				} else {
					// The full copy, not just a blank create-copy
					result = copy(value);

					// Propagate the implicit type of the value
					((ValueSpecification) result).setType(CompletionUtil.getType(message, value));
				}
			} else {
				result = super.createCopy(eObject);
			}

			return result;
		}

		@Override
		protected EClass getTarget(EClass eClass) {
			if (eClass.getEPackage() == LwMessagePackage.eINSTANCE) {
				switch (eClass.getClassifierID()) {
				case LwMessagePackage.BOOLEAN_VALUE:
					return UMLPackage.Literals.LITERAL_BOOLEAN;
				case LwMessagePackage.INTEGER_VALUE:
					return UMLPackage.Literals.LITERAL_INTEGER;
				case LwMessagePackage.UNLIMITED_NATURAL_VALUE:
					return UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL;
				case LwMessagePackage.REAL_VALUE:
					return UMLPackage.Literals.LITERAL_REAL;
				case LwMessagePackage.STRING_VALUE:
					return UMLPackage.Literals.LITERAL_STRING;
				case LwMessagePackage.NULL_VALUE:
					return UMLPackage.Literals.LITERAL_NULL;
				}
			}
			return super.getTarget(eClass);
		}

		@Override
		protected EStructuralFeature getTarget(EStructuralFeature eStructuralFeature) {
			if (eStructuralFeature.getEContainingClass() == LwMessagePackage.Literals.MESSAGE_ARGUMENT) {
				switch (eStructuralFeature.getFeatureID()) {
				case LwMessagePackage.MESSAGE_ARGUMENT__NAME:
					return UMLPackage.Literals.NAMED_ELEMENT__NAME;
				default:
					// Can only copy the name
					return null;
				}
			} else if (LwMessagePackage.Literals.VALUE
					.isSuperTypeOf(eStructuralFeature.getEContainingClass())) {
				// The only feature is 'value' anyways
				return getTarget(eStructuralFeature.getEContainingClass())
						.getEStructuralFeature(eStructuralFeature.getName());
			}

			return super.getTarget(eStructuralFeature);
		}
	};

}
