/*
 * generated by Xtext
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext;

import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.parser.antlr.LwMessageParser;
import org.eclipse.xtext.parser.IParser;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class LwMessageRuntimeModule extends
		org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.AbstractLwMessageRuntimeModule {

	private final MessageRule rule;

	/**
	 * Initializes me with the {@linkplain MessageRule#DEFAULT default} message
	 * rule.
	 */
	public LwMessageRuntimeModule() {
		this(MessageRule.DEFAULT);
	}

	/**
	 * Initializes me with the given message rule.
	 *
	 * @param rule
	 *            the top rule of the parser grammar
	 */
	public LwMessageRuntimeModule(MessageRule rule) {
		super();

		this.rule = rule;
	}

	@Override
	public Class<? extends IParser> bindIParser() {
		switch (rule) {
		case REQUEST:
			return RequestMessageParser.class;
		case REPLY:
			return ReplyMessageParser.class;
		default:
			return super.bindIParser();
		}
	}

	//
	// Nested types
	//

	private static class RequestMessageParser extends LwMessageParser {
		@Override
		protected String getDefaultRuleName() {
			return "AbstractRequestMessage";
		}
	}

	private static class ReplyMessageParser extends LwMessageParser {
		@Override
		protected String getDefaultRuleName() {
			return "ReplyMessage";
		}
	}
}
