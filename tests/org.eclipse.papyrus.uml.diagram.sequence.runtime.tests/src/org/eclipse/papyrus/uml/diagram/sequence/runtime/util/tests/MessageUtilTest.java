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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.util.tests;

import static java.util.stream.Collectors.toSet;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.CustomMatchers.isAbsent;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.CustomMatchers.isNamed;
import static org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers.CustomMatchers.isPresent;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelResource;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.util.MessageUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@code MessageUtil} class.
 *
 * @author Christian W. Damus
 */
@ModelResource("messages.uml")
public class MessageUtilTest {

	@Rule
	public ModelFixture model = new ModelFixture();

	private Supplier<Message> request = () -> model.getMessage("foo", "thing");
	private Supplier<Message> reply = () -> model.getMessage("thing", "foo");
	private Supplier<Message> signal = () -> model.getMessage("foo", "thing", 1);
	private Supplier<Message> delete = () -> model.getMessage("thing", "whatsIt", 1);

	@Test
	public void getReplyMessage() {
		assertThat(MessageUtil.getReplyMessage(request.get()), isPresent(reply.get()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getReplyMessage_invalid() {
		assertThat(MessageUtil.getReplyMessage(reply.get()), isAbsent());
	}

	@Test
	public void canHaveSignature() {
		assertThat(MessageUtil.canHaveSignature(request.get()), is(true));
		assertThat(MessageUtil.canHaveSignature(signal.get()), is(true));
		assertThat(MessageUtil.canHaveSignature(reply.get()), is(true));
		assertThat(MessageUtil.canHaveSignature(delete.get()), is(false));
	}

	// The getCovered() method is also tested by the this method.
	@Test
	public void getRepresentedType() {
		Optional<Lifeline> ll = MessageUtil.getCovered(request.get().getReceiveEvent());
		Optional<Classifier> represented = ll.flatMap(MessageUtil::getRepresentedType);
		assertThat(represented, isPresent(isNamed("Thing")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getCallableOperations_Message() {
		Set<Operation> callable = MessageUtil.getCallableOperations(request.get()).collect(toSet());
		assertThat(callable, hasItems(isNamed("doIt"), isNamed("cancel")));
	}

	@Test
	public void getCallableOperations_Message_String() {
		Set<Operation> callable = MessageUtil.getCallableOperations(request.get(), "cancel")
				.collect(toSet());
		assertThat(callable, hasItem(isNamed("cancel")));
		assertThat(callable, not(hasItem(isNamed("doIt"))));
	}

	@Test
	public void getReceivableSignals_Message() {
		Set<Signal> receivable = MessageUtil.getReceivableSignals(request.get()).collect(toSet());
		assertThat(receivable, hasItem(isNamed("Zap")));
	}

	@Test
	public void getReceivableSignals_Message_String() {
		Set<Signal> receivable = MessageUtil.getReceivableSignals(request.get(), "Zap").collect(toSet());
		assertThat(receivable, hasItem(isNamed("Zap")));
		receivable = MessageUtil.getReceivableSignals(request.get(), "cancel").collect(toSet());
		assertThat(receivable, not(hasItem(anything())));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getRepliedOperations_Message() {
		Set<Operation> replied = MessageUtil.getRepliedOperations(reply.get()).collect(toSet());
		// The only option is the current actual replied signature if there is one
		assertThat(replied, hasItem(isNamed("doIt")));
		assertThat(replied, not(hasItem(isNamed("cancel"))));

		request.get().setSignature(null);
		replied = MessageUtil.getRepliedOperations(reply.get()).collect(toSet());
		assertThat(replied, hasItems(isNamed("doIt"), isNamed("cancel")));
	}

	@Test
	public void getRepliedOperations_Message_String() {
		Set<Operation> replied = MessageUtil.getRepliedOperations(reply.get(), "cancel").collect(toSet());

		// The only option is the current actual replied signature if there is one
		assertThat(replied, not(hasItem(anything())));

		request.get().setSignature(null);
		replied = MessageUtil.getRepliedOperations(reply.get(), "cancel").collect(toSet());
		assertThat(replied, hasItem(isNamed("cancel")));
		assertThat(replied, not(hasItem(isNamed("doIt"))));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAssignableOutputs_Message() {
		Message getStuff = model.getMessage("thing", "whatsIt");
		Message getStuffReply = model.getMessage("whatsIt", "thing");
		Set<ConnectableElement> assignable = MessageUtil.getAssignableOutputs(getStuffReply)
				.collect(toSet());

		// With a signature set, only its outputs are considered
		assertThat(assignable, hasItem(isNamed("text")));
		assertThat(assignable, not(hasItem(isNamed("status"))));

		getStuffReply.setSignature(null);
		getStuff.setSignature(null);
		assignable = MessageUtil.getAssignableOutputs(getStuffReply).collect(toSet());
		// These are outputs of different operations
		assertThat(assignable, hasItems(isNamed("text"), isNamed("status")));
	}

	@Test
	public void getAssignableOutputs_Message_String() {
		Set<ConnectableElement> assignable = MessageUtil.getAssignableOutputs(reply.get(), "cancel")
				.collect(toSet());

		// With a signature set, only its outputs are considered
		assertThat(assignable, not(hasItem(anything())));

		reply.get().setSignature(null);
		request.get().setSignature(null);
		assignable = MessageUtil.getAssignableOutputs(reply.get(), "cancel").collect(toSet());
		assertThat(assignable, hasItem(isNamed("status")));
		assertThat(assignable, not(hasItem(isNamed("text"))));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAssignableTargets() {
		Message getStuffReply = model.getMessage("whatsIt", "thing");
		Set<ConnectableElement> assignable = MessageUtil.getAssignableTargets(getStuffReply)
				.collect(toSet());

		assertThat(assignable, hasItems( //
				isNamed("content"), // lifeline represented classifier property
				isNamed("foo"), // context classifier property
				isNamed("output")) // interaction out parameter
		);

		// But not an in parameter of the interaction
		assertThat(assignable, not(hasItem(isNamed("input"))));
	}

	@Test
	public void getRequestMessage() {
		assertThat(MessageUtil.getRequestMessage(reply.get()), isPresent(request.get()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRequestMessage_invalid() {
		assertThat(MessageUtil.getRequestMessage(request.get()), isAbsent());
	}

	@Test
	public void createWildcardRequestMessageArgument() {
		ValueSpecification wildcard = MessageUtil.createWildcardRequestMessageArgument();
		assertThat(wildcard, instanceOf(Expression.class));
		Expression expr = (Expression) wildcard;
		assertThat(expr.getSymbol(), is(""));
		assertThat(expr.getOperands(), not(hasItem(anything())));
	}

	@Test
	public void isWildcardRequestMessageArgument() {
		Message message = UMLFactory.eINSTANCE.createMessage();

		LiteralBoolean bool = (LiteralBoolean) message.createArgument(null, null,
				UMLPackage.Literals.LITERAL_BOOLEAN);
		;
		assertThat(MessageUtil.isWildcardRequestMessageArgument(bool), is(false));

		Expression expr = (Expression) message.createArgument(null, null, UMLPackage.Literals.EXPRESSION);
		assertThat(MessageUtil.isWildcardRequestMessageArgument(expr), is(false));
		expr.setSymbol("");
		assertThat(MessageUtil.isWildcardRequestMessageArgument(expr), is(true));
		expr.createOperand(null, null, UMLPackage.Literals.LITERAL_BOOLEAN);
		assertThat(MessageUtil.isWildcardRequestMessageArgument(expr), is(false));
	}

	@Test
	public void getAssignmentTarget() {
		Message getStuffReply = model.getMessage("whatsIt", "thing");
		ValueSpecification argument = getStuffReply.getArgument("text", null);
		String target = MessageUtil.getAssignmentTarget(argument);
		assertThat(target, is("content"));
	}

	@Test
	public void getAssignedValue() {
		Message getStuffReply = model.getMessage("whatsIt", "thing");
		ValueSpecification argument = getStuffReply.getArgument("text", null);
		ValueSpecification value = MessageUtil.getAssignedValue(argument);
		assertThat(value, instanceOf(LiteralString.class));
		LiteralString string = (LiteralString) value;
		assertThat(string.getValue(), is("Hello, world"));
	}

}
