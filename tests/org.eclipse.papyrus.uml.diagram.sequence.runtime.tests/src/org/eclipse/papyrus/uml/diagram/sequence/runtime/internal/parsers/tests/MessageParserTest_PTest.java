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

package org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.parsers.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.internal.parsers.MessageParser;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelFixture;
import org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.rules.ModelResource;
import org.eclipse.uml2.uml.Message;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link MessageParser} class. The parser needs a workspace for
 * Papyrus's i18n support.
 *
 * @author Christian W. Damus
 */
@ModelResource("messages.uml")
public class MessageParserTest_PTest {

	@Rule
	public ModelFixture model = new ModelFixture();

	private final IParser parser = new MessageParser();

	/**
	 * Initializes me.
	 */
	public MessageParserTest_PTest() {
		super();
	}

	@Test
	public void requestWithWildcards() {
		Message request = model.getMessage("foo", "thing");
		assertThat(request, rendersAs("doIt(\"hello\", - , 0.5, - )"));
	}

	@Test
	public void signatureNameSupersedesMessageName() {
		Message reply = model.getMessage("thing", "foo");

		// The message's own name is "doIt-reply"
		assertThat(reply, rendersAs("doIt: \"Hello, world\""));
	}

	@Test
	public void requestNamedParameters() {
		Message request = model.getMessage("thing", "whatsIt");
		assertThat(request, rendersAs("getStuff(text=\"hello\")"));
	}

	@Test
	public void replyWithAssignedAndUnassignedOutputs() {
		Message reply = model.getMessage("whatsIt", "thing");
		assertThat(reply, rendersAs("ok=getStuff(content=text: \"Hello, world\", expiration: 60): true"));
	}

	@Test
	public void signalMessage() {
		Message signal = model.getMessage("foo", "thing", 1);
		assertThat(signal, rendersAs("Zap(message=\"good-bye\")"));
	}

	@Test
	public void destroyMessage() {
		// These cannot have signatures
		Message delete = model.getMessage("thing", "whatsIt", 1);
		assertThat(delete, rendersAs("destroy"));
	}

	@Test
	public void semanticElementsBeingParsed() {
		Message reply = model.getMessage("whatsIt", "thing");

		@SuppressWarnings("unchecked")
		Set<Object> set = new HashSet<>(((ISemanticParser) parser).getSemanticElementsBeingParsed(reply));

		Iterator<?> iter = EcoreUtil.getAllContents(Collections.singleton(reply));
		iter.forEachRemaining(next -> assertThat(set, hasItem(next)));
	}

	//
	// Test framework
	//

	Matcher<EObject> rendersAs(String printString) {
		return new FeatureMatcher<EObject, String>(equalTo(printString), "parser printString",
				"printString") {

			@Override
			protected String featureValueOf(EObject actual) {
				IAdaptable adaptable = new EObjectAdapter(actual);
				return parser.getPrintString(adaptable, ParserOptions.NONE.intValue());
			}
		};
	}
}
