package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.services.LwMessageGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalLwMessageParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_REAL", "RULE_NEG_INT", "RULE_STRING", "RULE_DIGIT", "RULE_DIGIT0", "RULE_DIGITS", "RULE_DECIMAL", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_INTEGER_VALUE", "RULE_WS", "RULE_ANY_OTHER", "'*'", "'true'", "'false'", "'('", "')'", "','", "'-'", "'='", "':'", "'null'", "'::'"
    };
    public static final int RULE_STRING=8;
    public static final int RULE_DIGITS=11;
    public static final int RULE_SL_COMMENT=14;
    public static final int T__19=19;
    public static final int T__18=18;
    public static final int EOF=-1;
    public static final int RULE_NEG_INT=7;
    public static final int RULE_DIGIT0=10;
    public static final int RULE_ID=4;
    public static final int RULE_WS=16;
    public static final int RULE_REAL=6;
    public static final int RULE_DIGIT=9;
    public static final int RULE_ANY_OTHER=17;
    public static final int RULE_DECIMAL=12;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=5;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=13;
    public static final int RULE_INTEGER_VALUE=15;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalLwMessageParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalLwMessageParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalLwMessageParser.tokenNames; }
    public String getGrammarFileName() { return "InternalLwMessage.g"; }


     
     	private LwMessageGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(LwMessageGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start "entryRuleAbstractMessage"
    // InternalLwMessage.g:61:1: entryRuleAbstractMessage : ruleAbstractMessage EOF ;
    public final void entryRuleAbstractMessage() throws RecognitionException {
        try {
            // InternalLwMessage.g:62:1: ( ruleAbstractMessage EOF )
            // InternalLwMessage.g:63:1: ruleAbstractMessage EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAbstractMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleAbstractMessage();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAbstractMessageRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAbstractMessage"


    // $ANTLR start "ruleAbstractMessage"
    // InternalLwMessage.g:70:1: ruleAbstractMessage : ( ( rule__AbstractMessage__Alternatives ) ) ;
    public final void ruleAbstractMessage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:74:2: ( ( ( rule__AbstractMessage__Alternatives ) ) )
            // InternalLwMessage.g:75:1: ( ( rule__AbstractMessage__Alternatives ) )
            {
            // InternalLwMessage.g:75:1: ( ( rule__AbstractMessage__Alternatives ) )
            // InternalLwMessage.g:76:1: ( rule__AbstractMessage__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAbstractMessageAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:77:1: ( rule__AbstractMessage__Alternatives )
            // InternalLwMessage.g:77:2: rule__AbstractMessage__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__AbstractMessage__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAbstractMessageAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAbstractMessage"


    // $ANTLR start "entryRuleAbstractRequestMessage"
    // InternalLwMessage.g:89:1: entryRuleAbstractRequestMessage : ruleAbstractRequestMessage EOF ;
    public final void entryRuleAbstractRequestMessage() throws RecognitionException {
        try {
            // InternalLwMessage.g:90:1: ( ruleAbstractRequestMessage EOF )
            // InternalLwMessage.g:91:1: ruleAbstractRequestMessage EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAbstractRequestMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleAbstractRequestMessage();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAbstractRequestMessageRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAbstractRequestMessage"


    // $ANTLR start "ruleAbstractRequestMessage"
    // InternalLwMessage.g:98:1: ruleAbstractRequestMessage : ( ( rule__AbstractRequestMessage__Alternatives ) ) ;
    public final void ruleAbstractRequestMessage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:102:2: ( ( ( rule__AbstractRequestMessage__Alternatives ) ) )
            // InternalLwMessage.g:103:1: ( ( rule__AbstractRequestMessage__Alternatives ) )
            {
            // InternalLwMessage.g:103:1: ( ( rule__AbstractRequestMessage__Alternatives ) )
            // InternalLwMessage.g:104:1: ( rule__AbstractRequestMessage__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAbstractRequestMessageAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:105:1: ( rule__AbstractRequestMessage__Alternatives )
            // InternalLwMessage.g:105:2: rule__AbstractRequestMessage__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__AbstractRequestMessage__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAbstractRequestMessageAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAbstractRequestMessage"


    // $ANTLR start "entryRuleRequestMessage"
    // InternalLwMessage.g:117:1: entryRuleRequestMessage : ruleRequestMessage EOF ;
    public final void entryRuleRequestMessage() throws RecognitionException {
        try {
            // InternalLwMessage.g:118:1: ( ruleRequestMessage EOF )
            // InternalLwMessage.g:119:1: ruleRequestMessage EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleRequestMessage();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRequestMessage"


    // $ANTLR start "ruleRequestMessage"
    // InternalLwMessage.g:126:1: ruleRequestMessage : ( ( rule__RequestMessage__Group__0 ) ) ;
    public final void ruleRequestMessage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:130:2: ( ( ( rule__RequestMessage__Group__0 ) ) )
            // InternalLwMessage.g:131:1: ( ( rule__RequestMessage__Group__0 ) )
            {
            // InternalLwMessage.g:131:1: ( ( rule__RequestMessage__Group__0 ) )
            // InternalLwMessage.g:132:1: ( rule__RequestMessage__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getGroup()); 
            }
            // InternalLwMessage.g:133:1: ( rule__RequestMessage__Group__0 )
            // InternalLwMessage.g:133:2: rule__RequestMessage__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__RequestMessage__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRequestMessage"


    // $ANTLR start "entryRuleAnyMessage"
    // InternalLwMessage.g:145:1: entryRuleAnyMessage : ruleAnyMessage EOF ;
    public final void entryRuleAnyMessage() throws RecognitionException {
        try {
            // InternalLwMessage.g:146:1: ( ruleAnyMessage EOF )
            // InternalLwMessage.g:147:1: ruleAnyMessage EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAnyMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleAnyMessage();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAnyMessageRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAnyMessage"


    // $ANTLR start "ruleAnyMessage"
    // InternalLwMessage.g:154:1: ruleAnyMessage : ( ( rule__AnyMessage__Group__0 ) ) ;
    public final void ruleAnyMessage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:158:2: ( ( ( rule__AnyMessage__Group__0 ) ) )
            // InternalLwMessage.g:159:1: ( ( rule__AnyMessage__Group__0 ) )
            {
            // InternalLwMessage.g:159:1: ( ( rule__AnyMessage__Group__0 ) )
            // InternalLwMessage.g:160:1: ( rule__AnyMessage__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAnyMessageAccess().getGroup()); 
            }
            // InternalLwMessage.g:161:1: ( rule__AnyMessage__Group__0 )
            // InternalLwMessage.g:161:2: rule__AnyMessage__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__AnyMessage__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAnyMessageAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAnyMessage"


    // $ANTLR start "ruleMessageRequestArguments"
    // InternalLwMessage.g:174:1: ruleMessageRequestArguments : ( ( rule__MessageRequestArguments__Alternatives ) ) ;
    public final void ruleMessageRequestArguments() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:178:2: ( ( ( rule__MessageRequestArguments__Alternatives ) ) )
            // InternalLwMessage.g:179:1: ( ( rule__MessageRequestArguments__Alternatives ) )
            {
            // InternalLwMessage.g:179:1: ( ( rule__MessageRequestArguments__Alternatives ) )
            // InternalLwMessage.g:180:1: ( rule__MessageRequestArguments__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:181:1: ( rule__MessageRequestArguments__Alternatives )
            // InternalLwMessage.g:181:2: rule__MessageRequestArguments__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessageRequestArguments"


    // $ANTLR start "entryRuleMessageRequestArgument"
    // InternalLwMessage.g:193:1: entryRuleMessageRequestArgument : ruleMessageRequestArgument EOF ;
    public final void entryRuleMessageRequestArgument() throws RecognitionException {
        try {
            // InternalLwMessage.g:194:1: ( ruleMessageRequestArgument EOF )
            // InternalLwMessage.g:195:1: ruleMessageRequestArgument EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleMessageRequestArgument();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMessageRequestArgument"


    // $ANTLR start "ruleMessageRequestArgument"
    // InternalLwMessage.g:202:1: ruleMessageRequestArgument : ( ( rule__MessageRequestArgument__Alternatives ) ) ;
    public final void ruleMessageRequestArgument() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:206:2: ( ( ( rule__MessageRequestArgument__Alternatives ) ) )
            // InternalLwMessage.g:207:1: ( ( rule__MessageRequestArgument__Alternatives ) )
            {
            // InternalLwMessage.g:207:1: ( ( rule__MessageRequestArgument__Alternatives ) )
            // InternalLwMessage.g:208:1: ( rule__MessageRequestArgument__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:209:1: ( rule__MessageRequestArgument__Alternatives )
            // InternalLwMessage.g:209:2: rule__MessageRequestArgument__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArgument__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessageRequestArgument"


    // $ANTLR start "entryRuleMessageRequestArgumentWithName"
    // InternalLwMessage.g:221:1: entryRuleMessageRequestArgumentWithName : ruleMessageRequestArgumentWithName EOF ;
    public final void entryRuleMessageRequestArgumentWithName() throws RecognitionException {
        try {
            // InternalLwMessage.g:222:1: ( ruleMessageRequestArgumentWithName EOF )
            // InternalLwMessage.g:223:1: ruleMessageRequestArgumentWithName EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentWithNameRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleMessageRequestArgumentWithName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentWithNameRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMessageRequestArgumentWithName"


    // $ANTLR start "ruleMessageRequestArgumentWithName"
    // InternalLwMessage.g:230:1: ruleMessageRequestArgumentWithName : ( ruleMessageRequestNameAndValue ) ;
    public final void ruleMessageRequestArgumentWithName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:234:2: ( ( ruleMessageRequestNameAndValue ) )
            // InternalLwMessage.g:235:1: ( ruleMessageRequestNameAndValue )
            {
            // InternalLwMessage.g:235:1: ( ruleMessageRequestNameAndValue )
            // InternalLwMessage.g:236:1: ruleMessageRequestNameAndValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentWithNameAccess().getMessageRequestNameAndValueParserRuleCall()); 
            }
            pushFollow(FOLLOW_2);
            ruleMessageRequestNameAndValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentWithNameAccess().getMessageRequestNameAndValueParserRuleCall()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessageRequestArgumentWithName"


    // $ANTLR start "entryRuleMessageRequestNameAndValue"
    // InternalLwMessage.g:249:1: entryRuleMessageRequestNameAndValue : ruleMessageRequestNameAndValue EOF ;
    public final void entryRuleMessageRequestNameAndValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:250:1: ( ruleMessageRequestNameAndValue EOF )
            // InternalLwMessage.g:251:1: ruleMessageRequestNameAndValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleMessageRequestNameAndValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMessageRequestNameAndValue"


    // $ANTLR start "ruleMessageRequestNameAndValue"
    // InternalLwMessage.g:258:1: ruleMessageRequestNameAndValue : ( ( rule__MessageRequestNameAndValue__Group__0 ) ) ;
    public final void ruleMessageRequestNameAndValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:262:2: ( ( ( rule__MessageRequestNameAndValue__Group__0 ) ) )
            // InternalLwMessage.g:263:1: ( ( rule__MessageRequestNameAndValue__Group__0 ) )
            {
            // InternalLwMessage.g:263:1: ( ( rule__MessageRequestNameAndValue__Group__0 ) )
            // InternalLwMessage.g:264:1: ( rule__MessageRequestNameAndValue__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getGroup()); 
            }
            // InternalLwMessage.g:265:1: ( rule__MessageRequestNameAndValue__Group__0 )
            // InternalLwMessage.g:265:2: rule__MessageRequestNameAndValue__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestNameAndValue__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessageRequestNameAndValue"


    // $ANTLR start "ruleMessageRequestValue"
    // InternalLwMessage.g:278:1: ruleMessageRequestValue : ( ( rule__MessageRequestValue__ValueAssignment ) ) ;
    public final void ruleMessageRequestValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:282:2: ( ( ( rule__MessageRequestValue__ValueAssignment ) ) )
            // InternalLwMessage.g:283:1: ( ( rule__MessageRequestValue__ValueAssignment ) )
            {
            // InternalLwMessage.g:283:1: ( ( rule__MessageRequestValue__ValueAssignment ) )
            // InternalLwMessage.g:284:1: ( rule__MessageRequestValue__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestValueAccess().getValueAssignment()); 
            }
            // InternalLwMessage.g:285:1: ( rule__MessageRequestValue__ValueAssignment )
            // InternalLwMessage.g:285:2: rule__MessageRequestValue__ValueAssignment
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestValue__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestValueAccess().getValueAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessageRequestValue"


    // $ANTLR start "entryRuleReplyMessage"
    // InternalLwMessage.g:297:1: entryRuleReplyMessage : ruleReplyMessage EOF ;
    public final void entryRuleReplyMessage() throws RecognitionException {
        try {
            // InternalLwMessage.g:298:1: ( ruleReplyMessage EOF )
            // InternalLwMessage.g:299:1: ruleReplyMessage EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleReplyMessage();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleReplyMessage"


    // $ANTLR start "ruleReplyMessage"
    // InternalLwMessage.g:306:1: ruleReplyMessage : ( ( rule__ReplyMessage__Group__0 ) ) ;
    public final void ruleReplyMessage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:310:2: ( ( ( rule__ReplyMessage__Group__0 ) ) )
            // InternalLwMessage.g:311:1: ( ( rule__ReplyMessage__Group__0 ) )
            {
            // InternalLwMessage.g:311:1: ( ( rule__ReplyMessage__Group__0 ) )
            // InternalLwMessage.g:312:1: ( rule__ReplyMessage__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getGroup()); 
            }
            // InternalLwMessage.g:313:1: ( rule__ReplyMessage__Group__0 )
            // InternalLwMessage.g:313:2: rule__ReplyMessage__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleReplyMessage"


    // $ANTLR start "ruleAssignmentTarget"
    // InternalLwMessage.g:326:1: ruleAssignmentTarget : ( ( rule__AssignmentTarget__Group__0 ) ) ;
    public final void ruleAssignmentTarget() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:330:2: ( ( ( rule__AssignmentTarget__Group__0 ) ) )
            // InternalLwMessage.g:331:1: ( ( rule__AssignmentTarget__Group__0 ) )
            {
            // InternalLwMessage.g:331:1: ( ( rule__AssignmentTarget__Group__0 ) )
            // InternalLwMessage.g:332:1: ( rule__AssignmentTarget__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentTargetAccess().getGroup()); 
            }
            // InternalLwMessage.g:333:1: ( rule__AssignmentTarget__Group__0 )
            // InternalLwMessage.g:333:2: rule__AssignmentTarget__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__AssignmentTarget__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentTargetAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAssignmentTarget"


    // $ANTLR start "ruleMessageReplyOutputs"
    // InternalLwMessage.g:346:1: ruleMessageReplyOutputs : ( ( rule__MessageReplyOutputs__Group__0 ) ) ;
    public final void ruleMessageReplyOutputs() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:350:2: ( ( ( rule__MessageReplyOutputs__Group__0 ) ) )
            // InternalLwMessage.g:351:1: ( ( rule__MessageReplyOutputs__Group__0 ) )
            {
            // InternalLwMessage.g:351:1: ( ( rule__MessageReplyOutputs__Group__0 ) )
            // InternalLwMessage.g:352:1: ( rule__MessageReplyOutputs__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputsAccess().getGroup()); 
            }
            // InternalLwMessage.g:353:1: ( rule__MessageReplyOutputs__Group__0 )
            // InternalLwMessage.g:353:2: rule__MessageReplyOutputs__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutputs__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputsAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessageReplyOutputs"


    // $ANTLR start "entryRuleMessageReplyOutput"
    // InternalLwMessage.g:365:1: entryRuleMessageReplyOutput : ruleMessageReplyOutput EOF ;
    public final void entryRuleMessageReplyOutput() throws RecognitionException {
        try {
            // InternalLwMessage.g:366:1: ( ruleMessageReplyOutput EOF )
            // InternalLwMessage.g:367:1: ruleMessageReplyOutput EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleMessageReplyOutput();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMessageReplyOutput"


    // $ANTLR start "ruleMessageReplyOutput"
    // InternalLwMessage.g:374:1: ruleMessageReplyOutput : ( ( rule__MessageReplyOutput__Alternatives ) ) ;
    public final void ruleMessageReplyOutput() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:378:2: ( ( ( rule__MessageReplyOutput__Alternatives ) ) )
            // InternalLwMessage.g:379:1: ( ( rule__MessageReplyOutput__Alternatives ) )
            {
            // InternalLwMessage.g:379:1: ( ( rule__MessageReplyOutput__Alternatives ) )
            // InternalLwMessage.g:380:1: ( rule__MessageReplyOutput__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:381:1: ( rule__MessageReplyOutput__Alternatives )
            // InternalLwMessage.g:381:2: rule__MessageReplyOutput__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessageReplyOutput"


    // $ANTLR start "entryRuleOutputValue"
    // InternalLwMessage.g:393:1: entryRuleOutputValue : ruleOutputValue EOF ;
    public final void entryRuleOutputValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:394:1: ( ruleOutputValue EOF )
            // InternalLwMessage.g:395:1: ruleOutputValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOutputValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleOutputValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getOutputValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleOutputValue"


    // $ANTLR start "ruleOutputValue"
    // InternalLwMessage.g:402:1: ruleOutputValue : ( ( rule__OutputValue__Group__0 ) ) ;
    public final void ruleOutputValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:406:2: ( ( ( rule__OutputValue__Group__0 ) ) )
            // InternalLwMessage.g:407:1: ( ( rule__OutputValue__Group__0 ) )
            {
            // InternalLwMessage.g:407:1: ( ( rule__OutputValue__Group__0 ) )
            // InternalLwMessage.g:408:1: ( rule__OutputValue__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOutputValueAccess().getGroup()); 
            }
            // InternalLwMessage.g:409:1: ( rule__OutputValue__Group__0 )
            // InternalLwMessage.g:409:2: rule__OutputValue__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__OutputValue__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getOutputValueAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOutputValue"


    // $ANTLR start "entryRuleValue"
    // InternalLwMessage.g:421:1: entryRuleValue : ruleValue EOF ;
    public final void entryRuleValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:422:1: ( ruleValue EOF )
            // InternalLwMessage.g:423:1: ruleValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // InternalLwMessage.g:430:1: ruleValue : ( ( rule__Value__Alternatives ) ) ;
    public final void ruleValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:434:2: ( ( ( rule__Value__Alternatives ) ) )
            // InternalLwMessage.g:435:1: ( ( rule__Value__Alternatives ) )
            {
            // InternalLwMessage.g:435:1: ( ( rule__Value__Alternatives ) )
            // InternalLwMessage.g:436:1: ( rule__Value__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:437:1: ( rule__Value__Alternatives )
            // InternalLwMessage.g:437:2: rule__Value__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Value__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleBooleanValue"
    // InternalLwMessage.g:449:1: entryRuleBooleanValue : ruleBooleanValue EOF ;
    public final void entryRuleBooleanValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:450:1: ( ruleBooleanValue EOF )
            // InternalLwMessage.g:451:1: ruleBooleanValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBooleanValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleBooleanValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBooleanValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBooleanValue"


    // $ANTLR start "ruleBooleanValue"
    // InternalLwMessage.g:458:1: ruleBooleanValue : ( ( rule__BooleanValue__ValueAssignment ) ) ;
    public final void ruleBooleanValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:462:2: ( ( ( rule__BooleanValue__ValueAssignment ) ) )
            // InternalLwMessage.g:463:1: ( ( rule__BooleanValue__ValueAssignment ) )
            {
            // InternalLwMessage.g:463:1: ( ( rule__BooleanValue__ValueAssignment ) )
            // InternalLwMessage.g:464:1: ( rule__BooleanValue__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBooleanValueAccess().getValueAssignment()); 
            }
            // InternalLwMessage.g:465:1: ( rule__BooleanValue__ValueAssignment )
            // InternalLwMessage.g:465:2: rule__BooleanValue__ValueAssignment
            {
            pushFollow(FOLLOW_2);
            rule__BooleanValue__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBooleanValueAccess().getValueAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBooleanValue"


    // $ANTLR start "entryRuleIntegerValue"
    // InternalLwMessage.g:477:1: entryRuleIntegerValue : ruleIntegerValue EOF ;
    public final void entryRuleIntegerValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:478:1: ( ruleIntegerValue EOF )
            // InternalLwMessage.g:479:1: ruleIntegerValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIntegerValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleIntegerValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIntegerValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleIntegerValue"


    // $ANTLR start "ruleIntegerValue"
    // InternalLwMessage.g:486:1: ruleIntegerValue : ( ( rule__IntegerValue__ValueAssignment ) ) ;
    public final void ruleIntegerValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:490:2: ( ( ( rule__IntegerValue__ValueAssignment ) ) )
            // InternalLwMessage.g:491:1: ( ( rule__IntegerValue__ValueAssignment ) )
            {
            // InternalLwMessage.g:491:1: ( ( rule__IntegerValue__ValueAssignment ) )
            // InternalLwMessage.g:492:1: ( rule__IntegerValue__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIntegerValueAccess().getValueAssignment()); 
            }
            // InternalLwMessage.g:493:1: ( rule__IntegerValue__ValueAssignment )
            // InternalLwMessage.g:493:2: rule__IntegerValue__ValueAssignment
            {
            pushFollow(FOLLOW_2);
            rule__IntegerValue__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIntegerValueAccess().getValueAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIntegerValue"


    // $ANTLR start "entryRuleUnlimitedNaturalValue"
    // InternalLwMessage.g:505:1: entryRuleUnlimitedNaturalValue : ruleUnlimitedNaturalValue EOF ;
    public final void entryRuleUnlimitedNaturalValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:506:1: ( ruleUnlimitedNaturalValue EOF )
            // InternalLwMessage.g:507:1: ruleUnlimitedNaturalValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnlimitedNaturalValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleUnlimitedNaturalValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnlimitedNaturalValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleUnlimitedNaturalValue"


    // $ANTLR start "ruleUnlimitedNaturalValue"
    // InternalLwMessage.g:514:1: ruleUnlimitedNaturalValue : ( ( rule__UnlimitedNaturalValue__ValueAssignment ) ) ;
    public final void ruleUnlimitedNaturalValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:518:2: ( ( ( rule__UnlimitedNaturalValue__ValueAssignment ) ) )
            // InternalLwMessage.g:519:1: ( ( rule__UnlimitedNaturalValue__ValueAssignment ) )
            {
            // InternalLwMessage.g:519:1: ( ( rule__UnlimitedNaturalValue__ValueAssignment ) )
            // InternalLwMessage.g:520:1: ( rule__UnlimitedNaturalValue__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnlimitedNaturalValueAccess().getValueAssignment()); 
            }
            // InternalLwMessage.g:521:1: ( rule__UnlimitedNaturalValue__ValueAssignment )
            // InternalLwMessage.g:521:2: rule__UnlimitedNaturalValue__ValueAssignment
            {
            pushFollow(FOLLOW_2);
            rule__UnlimitedNaturalValue__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnlimitedNaturalValueAccess().getValueAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleUnlimitedNaturalValue"


    // $ANTLR start "entryRuleRealValue"
    // InternalLwMessage.g:533:1: entryRuleRealValue : ruleRealValue EOF ;
    public final void entryRuleRealValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:534:1: ( ruleRealValue EOF )
            // InternalLwMessage.g:535:1: ruleRealValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRealValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleRealValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRealValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRealValue"


    // $ANTLR start "ruleRealValue"
    // InternalLwMessage.g:542:1: ruleRealValue : ( ( rule__RealValue__ValueAssignment ) ) ;
    public final void ruleRealValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:546:2: ( ( ( rule__RealValue__ValueAssignment ) ) )
            // InternalLwMessage.g:547:1: ( ( rule__RealValue__ValueAssignment ) )
            {
            // InternalLwMessage.g:547:1: ( ( rule__RealValue__ValueAssignment ) )
            // InternalLwMessage.g:548:1: ( rule__RealValue__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRealValueAccess().getValueAssignment()); 
            }
            // InternalLwMessage.g:549:1: ( rule__RealValue__ValueAssignment )
            // InternalLwMessage.g:549:2: rule__RealValue__ValueAssignment
            {
            pushFollow(FOLLOW_2);
            rule__RealValue__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRealValueAccess().getValueAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRealValue"


    // $ANTLR start "entryRuleNullValue"
    // InternalLwMessage.g:561:1: entryRuleNullValue : ruleNullValue EOF ;
    public final void entryRuleNullValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:562:1: ( ruleNullValue EOF )
            // InternalLwMessage.g:563:1: ruleNullValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNullValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleNullValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNullValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNullValue"


    // $ANTLR start "ruleNullValue"
    // InternalLwMessage.g:570:1: ruleNullValue : ( ( rule__NullValue__Group__0 ) ) ;
    public final void ruleNullValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:574:2: ( ( ( rule__NullValue__Group__0 ) ) )
            // InternalLwMessage.g:575:1: ( ( rule__NullValue__Group__0 ) )
            {
            // InternalLwMessage.g:575:1: ( ( rule__NullValue__Group__0 ) )
            // InternalLwMessage.g:576:1: ( rule__NullValue__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNullValueAccess().getGroup()); 
            }
            // InternalLwMessage.g:577:1: ( rule__NullValue__Group__0 )
            // InternalLwMessage.g:577:2: rule__NullValue__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__NullValue__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNullValueAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNullValue"


    // $ANTLR start "entryRuleStringValue"
    // InternalLwMessage.g:589:1: entryRuleStringValue : ruleStringValue EOF ;
    public final void entryRuleStringValue() throws RecognitionException {
        try {
            // InternalLwMessage.g:590:1: ( ruleStringValue EOF )
            // InternalLwMessage.g:591:1: ruleStringValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStringValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleStringValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStringValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStringValue"


    // $ANTLR start "ruleStringValue"
    // InternalLwMessage.g:598:1: ruleStringValue : ( ( rule__StringValue__ValueAssignment ) ) ;
    public final void ruleStringValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:602:2: ( ( ( rule__StringValue__ValueAssignment ) ) )
            // InternalLwMessage.g:603:1: ( ( rule__StringValue__ValueAssignment ) )
            {
            // InternalLwMessage.g:603:1: ( ( rule__StringValue__ValueAssignment ) )
            // InternalLwMessage.g:604:1: ( rule__StringValue__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStringValueAccess().getValueAssignment()); 
            }
            // InternalLwMessage.g:605:1: ( rule__StringValue__ValueAssignment )
            // InternalLwMessage.g:605:2: rule__StringValue__ValueAssignment
            {
            pushFollow(FOLLOW_2);
            rule__StringValue__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStringValueAccess().getValueAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStringValue"


    // $ANTLR start "entryRuleDouble"
    // InternalLwMessage.g:619:1: entryRuleDouble : ruleDouble EOF ;
    public final void entryRuleDouble() throws RecognitionException {
        try {
            // InternalLwMessage.g:620:1: ( ruleDouble EOF )
            // InternalLwMessage.g:621:1: ruleDouble EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDoubleRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleDouble();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDoubleRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDouble"


    // $ANTLR start "ruleDouble"
    // InternalLwMessage.g:628:1: ruleDouble : ( ( rule__Double__Alternatives ) ) ;
    public final void ruleDouble() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:632:2: ( ( ( rule__Double__Alternatives ) ) )
            // InternalLwMessage.g:633:1: ( ( rule__Double__Alternatives ) )
            {
            // InternalLwMessage.g:633:1: ( ( rule__Double__Alternatives ) )
            // InternalLwMessage.g:634:1: ( rule__Double__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDoubleAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:635:1: ( rule__Double__Alternatives )
            // InternalLwMessage.g:635:2: rule__Double__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Double__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDoubleAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDouble"


    // $ANTLR start "entryRuleUnlimitedNatural"
    // InternalLwMessage.g:647:1: entryRuleUnlimitedNatural : ruleUnlimitedNatural EOF ;
    public final void entryRuleUnlimitedNatural() throws RecognitionException {
        try {
            // InternalLwMessage.g:648:1: ( ruleUnlimitedNatural EOF )
            // InternalLwMessage.g:649:1: ruleUnlimitedNatural EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnlimitedNaturalRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleUnlimitedNatural();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnlimitedNaturalRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleUnlimitedNatural"


    // $ANTLR start "ruleUnlimitedNatural"
    // InternalLwMessage.g:656:1: ruleUnlimitedNatural : ( ( rule__UnlimitedNatural__Alternatives ) ) ;
    public final void ruleUnlimitedNatural() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:660:2: ( ( ( rule__UnlimitedNatural__Alternatives ) ) )
            // InternalLwMessage.g:661:1: ( ( rule__UnlimitedNatural__Alternatives ) )
            {
            // InternalLwMessage.g:661:1: ( ( rule__UnlimitedNatural__Alternatives ) )
            // InternalLwMessage.g:662:1: ( rule__UnlimitedNatural__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnlimitedNaturalAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:663:1: ( rule__UnlimitedNatural__Alternatives )
            // InternalLwMessage.g:663:2: rule__UnlimitedNatural__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__UnlimitedNatural__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnlimitedNaturalAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleUnlimitedNatural"


    // $ANTLR start "entryRuleInteger"
    // InternalLwMessage.g:675:1: entryRuleInteger : ruleInteger EOF ;
    public final void entryRuleInteger() throws RecognitionException {
        try {
            // InternalLwMessage.g:676:1: ( ruleInteger EOF )
            // InternalLwMessage.g:677:1: ruleInteger EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIntegerRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleInteger();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIntegerRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleInteger"


    // $ANTLR start "ruleInteger"
    // InternalLwMessage.g:684:1: ruleInteger : ( ( rule__Integer__Alternatives ) ) ;
    public final void ruleInteger() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:688:2: ( ( ( rule__Integer__Alternatives ) ) )
            // InternalLwMessage.g:689:1: ( ( rule__Integer__Alternatives ) )
            {
            // InternalLwMessage.g:689:1: ( ( rule__Integer__Alternatives ) )
            // InternalLwMessage.g:690:1: ( rule__Integer__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIntegerAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:691:1: ( rule__Integer__Alternatives )
            // InternalLwMessage.g:691:2: rule__Integer__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Integer__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIntegerAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleInteger"


    // $ANTLR start "entryRuleBoolean"
    // InternalLwMessage.g:703:1: entryRuleBoolean : ruleBoolean EOF ;
    public final void entryRuleBoolean() throws RecognitionException {
        try {
            // InternalLwMessage.g:704:1: ( ruleBoolean EOF )
            // InternalLwMessage.g:705:1: ruleBoolean EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBooleanRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleBoolean();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBooleanRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBoolean"


    // $ANTLR start "ruleBoolean"
    // InternalLwMessage.g:712:1: ruleBoolean : ( ( rule__Boolean__Alternatives ) ) ;
    public final void ruleBoolean() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:716:2: ( ( ( rule__Boolean__Alternatives ) ) )
            // InternalLwMessage.g:717:1: ( ( rule__Boolean__Alternatives ) )
            {
            // InternalLwMessage.g:717:1: ( ( rule__Boolean__Alternatives ) )
            // InternalLwMessage.g:718:1: ( rule__Boolean__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBooleanAccess().getAlternatives()); 
            }
            // InternalLwMessage.g:719:1: ( rule__Boolean__Alternatives )
            // InternalLwMessage.g:719:2: rule__Boolean__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Boolean__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBooleanAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBoolean"


    // $ANTLR start "entryRuleQName"
    // InternalLwMessage.g:731:1: entryRuleQName : ruleQName EOF ;
    public final void entryRuleQName() throws RecognitionException {
        try {
            // InternalLwMessage.g:732:1: ( ruleQName EOF )
            // InternalLwMessage.g:733:1: ruleQName EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQNameRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleQName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQNameRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleQName"


    // $ANTLR start "ruleQName"
    // InternalLwMessage.g:740:1: ruleQName : ( ( rule__QName__Group__0 ) ) ;
    public final void ruleQName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:744:2: ( ( ( rule__QName__Group__0 ) ) )
            // InternalLwMessage.g:745:1: ( ( rule__QName__Group__0 ) )
            {
            // InternalLwMessage.g:745:1: ( ( rule__QName__Group__0 ) )
            // InternalLwMessage.g:746:1: ( rule__QName__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQNameAccess().getGroup()); 
            }
            // InternalLwMessage.g:747:1: ( rule__QName__Group__0 )
            // InternalLwMessage.g:747:2: rule__QName__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__QName__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQNameAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleQName"


    // $ANTLR start "entryRuleName"
    // InternalLwMessage.g:759:1: entryRuleName : ruleName EOF ;
    public final void entryRuleName() throws RecognitionException {
        try {
            // InternalLwMessage.g:760:1: ( ruleName EOF )
            // InternalLwMessage.g:761:1: ruleName EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNameRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNameRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleName"


    // $ANTLR start "ruleName"
    // InternalLwMessage.g:768:1: ruleName : ( RULE_ID ) ;
    public final void ruleName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:772:2: ( ( RULE_ID ) )
            // InternalLwMessage.g:773:1: ( RULE_ID )
            {
            // InternalLwMessage.g:773:1: ( RULE_ID )
            // InternalLwMessage.g:774:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNameAccess().getIDTerminalRuleCall()); 
            }
            match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNameAccess().getIDTerminalRuleCall()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleName"


    // $ANTLR start "rule__AbstractMessage__Alternatives"
    // InternalLwMessage.g:787:1: rule__AbstractMessage__Alternatives : ( ( ruleAbstractRequestMessage ) | ( ( ruleReplyMessage ) ) );
    public final void rule__AbstractMessage__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:791:1: ( ( ruleAbstractRequestMessage ) | ( ( ruleReplyMessage ) ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==18) ) {
                alt1=1;
            }
            else if ( (LA1_0==RULE_ID) ) {
                int LA1_2 = input.LA(2);

                if ( (synpred1_InternalLwMessage()) ) {
                    alt1=1;
                }
                else if ( (true) ) {
                    alt1=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalLwMessage.g:792:1: ( ruleAbstractRequestMessage )
                    {
                    // InternalLwMessage.g:792:1: ( ruleAbstractRequestMessage )
                    // InternalLwMessage.g:793:1: ruleAbstractRequestMessage
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAbstractMessageAccess().getAbstractRequestMessageParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleAbstractRequestMessage();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAbstractMessageAccess().getAbstractRequestMessageParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:798:6: ( ( ruleReplyMessage ) )
                    {
                    // InternalLwMessage.g:798:6: ( ( ruleReplyMessage ) )
                    // InternalLwMessage.g:799:1: ( ruleReplyMessage )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAbstractMessageAccess().getReplyMessageParserRuleCall_1()); 
                    }
                    // InternalLwMessage.g:800:1: ( ruleReplyMessage )
                    // InternalLwMessage.g:800:3: ruleReplyMessage
                    {
                    pushFollow(FOLLOW_2);
                    ruleReplyMessage();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAbstractMessageAccess().getReplyMessageParserRuleCall_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AbstractMessage__Alternatives"


    // $ANTLR start "rule__AbstractRequestMessage__Alternatives"
    // InternalLwMessage.g:809:1: rule__AbstractRequestMessage__Alternatives : ( ( ruleAnyMessage ) | ( ruleRequestMessage ) );
    public final void rule__AbstractRequestMessage__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:813:1: ( ( ruleAnyMessage ) | ( ruleRequestMessage ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==18) ) {
                alt2=1;
            }
            else if ( (LA2_0==RULE_ID) ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalLwMessage.g:814:1: ( ruleAnyMessage )
                    {
                    // InternalLwMessage.g:814:1: ( ruleAnyMessage )
                    // InternalLwMessage.g:815:1: ruleAnyMessage
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAbstractRequestMessageAccess().getAnyMessageParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleAnyMessage();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAbstractRequestMessageAccess().getAnyMessageParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:820:6: ( ruleRequestMessage )
                    {
                    // InternalLwMessage.g:820:6: ( ruleRequestMessage )
                    // InternalLwMessage.g:821:1: ruleRequestMessage
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAbstractRequestMessageAccess().getRequestMessageParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleRequestMessage();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAbstractRequestMessageAccess().getRequestMessageParserRuleCall_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AbstractRequestMessage__Alternatives"


    // $ANTLR start "rule__RequestMessage__Alternatives_0"
    // InternalLwMessage.g:831:1: rule__RequestMessage__Alternatives_0 : ( ( ( rule__RequestMessage__NameAssignment_0_0 ) ) | ( ( rule__RequestMessage__SignalAssignment_0_1 ) ) | ( ( rule__RequestMessage__OperationAssignment_0_2 ) ) );
    public final void rule__RequestMessage__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:835:1: ( ( ( rule__RequestMessage__NameAssignment_0_0 ) ) | ( ( rule__RequestMessage__SignalAssignment_0_1 ) ) | ( ( rule__RequestMessage__OperationAssignment_0_2 ) ) )
            int alt3=3;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_ID) ) {
                int LA3_1 = input.LA(2);

                if ( (synpred3_InternalLwMessage()) ) {
                    alt3=1;
                }
                else if ( (synpred4_InternalLwMessage()) ) {
                    alt3=2;
                }
                else if ( (true) ) {
                    alt3=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalLwMessage.g:836:1: ( ( rule__RequestMessage__NameAssignment_0_0 ) )
                    {
                    // InternalLwMessage.g:836:1: ( ( rule__RequestMessage__NameAssignment_0_0 ) )
                    // InternalLwMessage.g:837:1: ( rule__RequestMessage__NameAssignment_0_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRequestMessageAccess().getNameAssignment_0_0()); 
                    }
                    // InternalLwMessage.g:838:1: ( rule__RequestMessage__NameAssignment_0_0 )
                    // InternalLwMessage.g:838:2: rule__RequestMessage__NameAssignment_0_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RequestMessage__NameAssignment_0_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRequestMessageAccess().getNameAssignment_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:842:6: ( ( rule__RequestMessage__SignalAssignment_0_1 ) )
                    {
                    // InternalLwMessage.g:842:6: ( ( rule__RequestMessage__SignalAssignment_0_1 ) )
                    // InternalLwMessage.g:843:1: ( rule__RequestMessage__SignalAssignment_0_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRequestMessageAccess().getSignalAssignment_0_1()); 
                    }
                    // InternalLwMessage.g:844:1: ( rule__RequestMessage__SignalAssignment_0_1 )
                    // InternalLwMessage.g:844:2: rule__RequestMessage__SignalAssignment_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__RequestMessage__SignalAssignment_0_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRequestMessageAccess().getSignalAssignment_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalLwMessage.g:848:6: ( ( rule__RequestMessage__OperationAssignment_0_2 ) )
                    {
                    // InternalLwMessage.g:848:6: ( ( rule__RequestMessage__OperationAssignment_0_2 ) )
                    // InternalLwMessage.g:849:1: ( rule__RequestMessage__OperationAssignment_0_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRequestMessageAccess().getOperationAssignment_0_2()); 
                    }
                    // InternalLwMessage.g:850:1: ( rule__RequestMessage__OperationAssignment_0_2 )
                    // InternalLwMessage.g:850:2: rule__RequestMessage__OperationAssignment_0_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__RequestMessage__OperationAssignment_0_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRequestMessageAccess().getOperationAssignment_0_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Alternatives_0"


    // $ANTLR start "rule__MessageRequestArguments__Alternatives"
    // InternalLwMessage.g:859:1: rule__MessageRequestArguments__Alternatives : ( ( ( rule__MessageRequestArguments__Group_0__0 ) ) | ( ( rule__MessageRequestArguments__Group_1__0 ) ) );
    public final void rule__MessageRequestArguments__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:863:1: ( ( ( rule__MessageRequestArguments__Group_0__0 ) ) | ( ( rule__MessageRequestArguments__Group_1__0 ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=RULE_INT && LA4_0<=RULE_STRING)||(LA4_0>=18 && LA4_0<=20)||LA4_0==24||LA4_0==27) ) {
                alt4=1;
            }
            else if ( (LA4_0==RULE_ID) ) {
                alt4=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalLwMessage.g:864:1: ( ( rule__MessageRequestArguments__Group_0__0 ) )
                    {
                    // InternalLwMessage.g:864:1: ( ( rule__MessageRequestArguments__Group_0__0 ) )
                    // InternalLwMessage.g:865:1: ( rule__MessageRequestArguments__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageRequestArgumentsAccess().getGroup_0()); 
                    }
                    // InternalLwMessage.g:866:1: ( rule__MessageRequestArguments__Group_0__0 )
                    // InternalLwMessage.g:866:2: rule__MessageRequestArguments__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageRequestArguments__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageRequestArgumentsAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:870:6: ( ( rule__MessageRequestArguments__Group_1__0 ) )
                    {
                    // InternalLwMessage.g:870:6: ( ( rule__MessageRequestArguments__Group_1__0 ) )
                    // InternalLwMessage.g:871:1: ( rule__MessageRequestArguments__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageRequestArgumentsAccess().getGroup_1()); 
                    }
                    // InternalLwMessage.g:872:1: ( rule__MessageRequestArguments__Group_1__0 )
                    // InternalLwMessage.g:872:2: rule__MessageRequestArguments__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageRequestArguments__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageRequestArgumentsAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Alternatives"


    // $ANTLR start "rule__MessageRequestArgument__Alternatives"
    // InternalLwMessage.g:881:1: rule__MessageRequestArgument__Alternatives : ( ( ( rule__MessageRequestArgument__Group_0__0 ) ) | ( ruleMessageRequestValue ) );
    public final void rule__MessageRequestArgument__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:885:1: ( ( ( rule__MessageRequestArgument__Group_0__0 ) ) | ( ruleMessageRequestValue ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==24) ) {
                alt5=1;
            }
            else if ( ((LA5_0>=RULE_INT && LA5_0<=RULE_STRING)||(LA5_0>=18 && LA5_0<=20)||LA5_0==27) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalLwMessage.g:886:1: ( ( rule__MessageRequestArgument__Group_0__0 ) )
                    {
                    // InternalLwMessage.g:886:1: ( ( rule__MessageRequestArgument__Group_0__0 ) )
                    // InternalLwMessage.g:887:1: ( rule__MessageRequestArgument__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageRequestArgumentAccess().getGroup_0()); 
                    }
                    // InternalLwMessage.g:888:1: ( rule__MessageRequestArgument__Group_0__0 )
                    // InternalLwMessage.g:888:2: rule__MessageRequestArgument__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageRequestArgument__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageRequestArgumentAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:892:6: ( ruleMessageRequestValue )
                    {
                    // InternalLwMessage.g:892:6: ( ruleMessageRequestValue )
                    // InternalLwMessage.g:893:1: ruleMessageRequestValue
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageRequestArgumentAccess().getMessageRequestValueParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleMessageRequestValue();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageRequestArgumentAccess().getMessageRequestValueParserRuleCall_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArgument__Alternatives"


    // $ANTLR start "rule__MessageRequestNameAndValue__Alternatives_0"
    // InternalLwMessage.g:903:1: rule__MessageRequestNameAndValue__Alternatives_0 : ( ( ( rule__MessageRequestNameAndValue__NameAssignment_0_0 ) ) | ( ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 ) ) | ( ( rule__MessageRequestNameAndValue__ParameterAssignment_0_2 ) ) );
    public final void rule__MessageRequestNameAndValue__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:907:1: ( ( ( rule__MessageRequestNameAndValue__NameAssignment_0_0 ) ) | ( ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 ) ) | ( ( rule__MessageRequestNameAndValue__ParameterAssignment_0_2 ) ) )
            int alt6=3;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_ID) ) {
                int LA6_1 = input.LA(2);

                if ( (synpred7_InternalLwMessage()) ) {
                    alt6=1;
                }
                else if ( (synpred8_InternalLwMessage()) ) {
                    alt6=2;
                }
                else if ( (true) ) {
                    alt6=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalLwMessage.g:908:1: ( ( rule__MessageRequestNameAndValue__NameAssignment_0_0 ) )
                    {
                    // InternalLwMessage.g:908:1: ( ( rule__MessageRequestNameAndValue__NameAssignment_0_0 ) )
                    // InternalLwMessage.g:909:1: ( rule__MessageRequestNameAndValue__NameAssignment_0_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageRequestNameAndValueAccess().getNameAssignment_0_0()); 
                    }
                    // InternalLwMessage.g:910:1: ( rule__MessageRequestNameAndValue__NameAssignment_0_0 )
                    // InternalLwMessage.g:910:2: rule__MessageRequestNameAndValue__NameAssignment_0_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageRequestNameAndValue__NameAssignment_0_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageRequestNameAndValueAccess().getNameAssignment_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:914:6: ( ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 ) )
                    {
                    // InternalLwMessage.g:914:6: ( ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 ) )
                    // InternalLwMessage.g:915:1: ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageRequestNameAndValueAccess().getPropertyAssignment_0_1()); 
                    }
                    // InternalLwMessage.g:916:1: ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 )
                    // InternalLwMessage.g:916:2: rule__MessageRequestNameAndValue__PropertyAssignment_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageRequestNameAndValue__PropertyAssignment_0_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageRequestNameAndValueAccess().getPropertyAssignment_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalLwMessage.g:920:6: ( ( rule__MessageRequestNameAndValue__ParameterAssignment_0_2 ) )
                    {
                    // InternalLwMessage.g:920:6: ( ( rule__MessageRequestNameAndValue__ParameterAssignment_0_2 ) )
                    // InternalLwMessage.g:921:1: ( rule__MessageRequestNameAndValue__ParameterAssignment_0_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageRequestNameAndValueAccess().getParameterAssignment_0_2()); 
                    }
                    // InternalLwMessage.g:922:1: ( rule__MessageRequestNameAndValue__ParameterAssignment_0_2 )
                    // InternalLwMessage.g:922:2: rule__MessageRequestNameAndValue__ParameterAssignment_0_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageRequestNameAndValue__ParameterAssignment_0_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageRequestNameAndValueAccess().getParameterAssignment_0_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__Alternatives_0"


    // $ANTLR start "rule__ReplyMessage__Alternatives_1"
    // InternalLwMessage.g:931:1: rule__ReplyMessage__Alternatives_1 : ( ( ( rule__ReplyMessage__NameAssignment_1_0 ) ) | ( ( rule__ReplyMessage__OperationAssignment_1_1 ) ) );
    public final void rule__ReplyMessage__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:935:1: ( ( ( rule__ReplyMessage__NameAssignment_1_0 ) ) | ( ( rule__ReplyMessage__OperationAssignment_1_1 ) ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_ID) ) {
                int LA7_1 = input.LA(2);

                if ( (synpred9_InternalLwMessage()) ) {
                    alt7=1;
                }
                else if ( (true) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalLwMessage.g:936:1: ( ( rule__ReplyMessage__NameAssignment_1_0 ) )
                    {
                    // InternalLwMessage.g:936:1: ( ( rule__ReplyMessage__NameAssignment_1_0 ) )
                    // InternalLwMessage.g:937:1: ( rule__ReplyMessage__NameAssignment_1_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getReplyMessageAccess().getNameAssignment_1_0()); 
                    }
                    // InternalLwMessage.g:938:1: ( rule__ReplyMessage__NameAssignment_1_0 )
                    // InternalLwMessage.g:938:2: rule__ReplyMessage__NameAssignment_1_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ReplyMessage__NameAssignment_1_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getReplyMessageAccess().getNameAssignment_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:942:6: ( ( rule__ReplyMessage__OperationAssignment_1_1 ) )
                    {
                    // InternalLwMessage.g:942:6: ( ( rule__ReplyMessage__OperationAssignment_1_1 ) )
                    // InternalLwMessage.g:943:1: ( rule__ReplyMessage__OperationAssignment_1_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getReplyMessageAccess().getOperationAssignment_1_1()); 
                    }
                    // InternalLwMessage.g:944:1: ( rule__ReplyMessage__OperationAssignment_1_1 )
                    // InternalLwMessage.g:944:2: rule__ReplyMessage__OperationAssignment_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__ReplyMessage__OperationAssignment_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getReplyMessageAccess().getOperationAssignment_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Alternatives_1"


    // $ANTLR start "rule__MessageReplyOutput__Alternatives"
    // InternalLwMessage.g:953:1: rule__MessageReplyOutput__Alternatives : ( ( ( rule__MessageReplyOutput__Group_0__0 ) ) | ( ( rule__MessageReplyOutput__Group_1__0 ) ) );
    public final void rule__MessageReplyOutput__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:957:1: ( ( ( rule__MessageReplyOutput__Group_0__0 ) ) | ( ( rule__MessageReplyOutput__Group_1__0 ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_ID) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==26) ) {
                    alt8=2;
                }
                else if ( (LA8_1==25||LA8_1==28) ) {
                    alt8=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalLwMessage.g:958:1: ( ( rule__MessageReplyOutput__Group_0__0 ) )
                    {
                    // InternalLwMessage.g:958:1: ( ( rule__MessageReplyOutput__Group_0__0 ) )
                    // InternalLwMessage.g:959:1: ( rule__MessageReplyOutput__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageReplyOutputAccess().getGroup_0()); 
                    }
                    // InternalLwMessage.g:960:1: ( rule__MessageReplyOutput__Group_0__0 )
                    // InternalLwMessage.g:960:2: rule__MessageReplyOutput__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageReplyOutput__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageReplyOutputAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:964:6: ( ( rule__MessageReplyOutput__Group_1__0 ) )
                    {
                    // InternalLwMessage.g:964:6: ( ( rule__MessageReplyOutput__Group_1__0 ) )
                    // InternalLwMessage.g:965:1: ( rule__MessageReplyOutput__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMessageReplyOutputAccess().getGroup_1()); 
                    }
                    // InternalLwMessage.g:966:1: ( rule__MessageReplyOutput__Group_1__0 )
                    // InternalLwMessage.g:966:2: rule__MessageReplyOutput__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageReplyOutput__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMessageReplyOutputAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Alternatives"


    // $ANTLR start "rule__Value__Alternatives"
    // InternalLwMessage.g:975:1: rule__Value__Alternatives : ( ( ruleBooleanValue ) | ( ( ruleIntegerValue ) ) | ( ( ruleUnlimitedNaturalValue ) ) | ( ( ruleRealValue ) ) | ( ruleNullValue ) | ( ruleStringValue ) );
    public final void rule__Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:979:1: ( ( ruleBooleanValue ) | ( ( ruleIntegerValue ) ) | ( ( ruleUnlimitedNaturalValue ) ) | ( ( ruleRealValue ) ) | ( ruleNullValue ) | ( ruleStringValue ) )
            int alt9=6;
            switch ( input.LA(1) ) {
            case 19:
            case 20:
                {
                alt9=1;
                }
                break;
            case RULE_INT:
                {
                int LA9_2 = input.LA(2);

                if ( (synpred12_InternalLwMessage()) ) {
                    alt9=2;
                }
                else if ( (synpred13_InternalLwMessage()) ) {
                    alt9=3;
                }
                else if ( (synpred14_InternalLwMessage()) ) {
                    alt9=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_NEG_INT:
            case 18:
                {
                alt9=3;
                }
                break;
            case RULE_REAL:
                {
                alt9=4;
                }
                break;
            case 27:
                {
                alt9=5;
                }
                break;
            case RULE_STRING:
                {
                alt9=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalLwMessage.g:980:1: ( ruleBooleanValue )
                    {
                    // InternalLwMessage.g:980:1: ( ruleBooleanValue )
                    // InternalLwMessage.g:981:1: ruleBooleanValue
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getBooleanValueParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleBooleanValue();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getBooleanValueParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:986:6: ( ( ruleIntegerValue ) )
                    {
                    // InternalLwMessage.g:986:6: ( ( ruleIntegerValue ) )
                    // InternalLwMessage.g:987:1: ( ruleIntegerValue )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getIntegerValueParserRuleCall_1()); 
                    }
                    // InternalLwMessage.g:988:1: ( ruleIntegerValue )
                    // InternalLwMessage.g:988:3: ruleIntegerValue
                    {
                    pushFollow(FOLLOW_2);
                    ruleIntegerValue();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getIntegerValueParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalLwMessage.g:992:6: ( ( ruleUnlimitedNaturalValue ) )
                    {
                    // InternalLwMessage.g:992:6: ( ( ruleUnlimitedNaturalValue ) )
                    // InternalLwMessage.g:993:1: ( ruleUnlimitedNaturalValue )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getUnlimitedNaturalValueParserRuleCall_2()); 
                    }
                    // InternalLwMessage.g:994:1: ( ruleUnlimitedNaturalValue )
                    // InternalLwMessage.g:994:3: ruleUnlimitedNaturalValue
                    {
                    pushFollow(FOLLOW_2);
                    ruleUnlimitedNaturalValue();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getUnlimitedNaturalValueParserRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalLwMessage.g:998:6: ( ( ruleRealValue ) )
                    {
                    // InternalLwMessage.g:998:6: ( ( ruleRealValue ) )
                    // InternalLwMessage.g:999:1: ( ruleRealValue )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getRealValueParserRuleCall_3()); 
                    }
                    // InternalLwMessage.g:1000:1: ( ruleRealValue )
                    // InternalLwMessage.g:1000:3: ruleRealValue
                    {
                    pushFollow(FOLLOW_2);
                    ruleRealValue();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getRealValueParserRuleCall_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalLwMessage.g:1004:6: ( ruleNullValue )
                    {
                    // InternalLwMessage.g:1004:6: ( ruleNullValue )
                    // InternalLwMessage.g:1005:1: ruleNullValue
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getNullValueParserRuleCall_4()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleNullValue();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getNullValueParserRuleCall_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalLwMessage.g:1010:6: ( ruleStringValue )
                    {
                    // InternalLwMessage.g:1010:6: ( ruleStringValue )
                    // InternalLwMessage.g:1011:1: ruleStringValue
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getStringValueParserRuleCall_5()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleStringValue();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getStringValueParserRuleCall_5()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__Alternatives"


    // $ANTLR start "rule__Double__Alternatives"
    // InternalLwMessage.g:1021:1: rule__Double__Alternatives : ( ( RULE_INT ) | ( RULE_REAL ) );
    public final void rule__Double__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1025:1: ( ( RULE_INT ) | ( RULE_REAL ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_INT) ) {
                alt10=1;
            }
            else if ( (LA10_0==RULE_REAL) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalLwMessage.g:1026:1: ( RULE_INT )
                    {
                    // InternalLwMessage.g:1026:1: ( RULE_INT )
                    // InternalLwMessage.g:1027:1: RULE_INT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDoubleAccess().getINTTerminalRuleCall_0()); 
                    }
                    match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDoubleAccess().getINTTerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:1032:6: ( RULE_REAL )
                    {
                    // InternalLwMessage.g:1032:6: ( RULE_REAL )
                    // InternalLwMessage.g:1033:1: RULE_REAL
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDoubleAccess().getREALTerminalRuleCall_1()); 
                    }
                    match(input,RULE_REAL,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDoubleAccess().getREALTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Double__Alternatives"


    // $ANTLR start "rule__UnlimitedNatural__Alternatives"
    // InternalLwMessage.g:1043:1: rule__UnlimitedNatural__Alternatives : ( ( ruleInteger ) | ( '*' ) );
    public final void rule__UnlimitedNatural__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1047:1: ( ( ruleInteger ) | ( '*' ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_INT||LA11_0==RULE_NEG_INT) ) {
                alt11=1;
            }
            else if ( (LA11_0==18) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalLwMessage.g:1048:1: ( ruleInteger )
                    {
                    // InternalLwMessage.g:1048:1: ( ruleInteger )
                    // InternalLwMessage.g:1049:1: ruleInteger
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getUnlimitedNaturalAccess().getIntegerParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleInteger();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getUnlimitedNaturalAccess().getIntegerParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:1054:6: ( '*' )
                    {
                    // InternalLwMessage.g:1054:6: ( '*' )
                    // InternalLwMessage.g:1055:1: '*'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getUnlimitedNaturalAccess().getAsteriskKeyword_1()); 
                    }
                    match(input,18,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getUnlimitedNaturalAccess().getAsteriskKeyword_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnlimitedNatural__Alternatives"


    // $ANTLR start "rule__Integer__Alternatives"
    // InternalLwMessage.g:1067:1: rule__Integer__Alternatives : ( ( RULE_INT ) | ( RULE_NEG_INT ) );
    public final void rule__Integer__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1071:1: ( ( RULE_INT ) | ( RULE_NEG_INT ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_INT) ) {
                alt12=1;
            }
            else if ( (LA12_0==RULE_NEG_INT) ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // InternalLwMessage.g:1072:1: ( RULE_INT )
                    {
                    // InternalLwMessage.g:1072:1: ( RULE_INT )
                    // InternalLwMessage.g:1073:1: RULE_INT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getIntegerAccess().getINTTerminalRuleCall_0()); 
                    }
                    match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getIntegerAccess().getINTTerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:1078:6: ( RULE_NEG_INT )
                    {
                    // InternalLwMessage.g:1078:6: ( RULE_NEG_INT )
                    // InternalLwMessage.g:1079:1: RULE_NEG_INT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getIntegerAccess().getNEG_INTTerminalRuleCall_1()); 
                    }
                    match(input,RULE_NEG_INT,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getIntegerAccess().getNEG_INTTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Integer__Alternatives"


    // $ANTLR start "rule__Boolean__Alternatives"
    // InternalLwMessage.g:1089:1: rule__Boolean__Alternatives : ( ( 'true' ) | ( 'false' ) );
    public final void rule__Boolean__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1093:1: ( ( 'true' ) | ( 'false' ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==19) ) {
                alt13=1;
            }
            else if ( (LA13_0==20) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalLwMessage.g:1094:1: ( 'true' )
                    {
                    // InternalLwMessage.g:1094:1: ( 'true' )
                    // InternalLwMessage.g:1095:1: 'true'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBooleanAccess().getTrueKeyword_0()); 
                    }
                    match(input,19,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBooleanAccess().getTrueKeyword_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:1102:6: ( 'false' )
                    {
                    // InternalLwMessage.g:1102:6: ( 'false' )
                    // InternalLwMessage.g:1103:1: 'false'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBooleanAccess().getFalseKeyword_1()); 
                    }
                    match(input,20,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBooleanAccess().getFalseKeyword_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Boolean__Alternatives"


    // $ANTLR start "rule__RequestMessage__Group__0"
    // InternalLwMessage.g:1117:1: rule__RequestMessage__Group__0 : rule__RequestMessage__Group__0__Impl rule__RequestMessage__Group__1 ;
    public final void rule__RequestMessage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1121:1: ( rule__RequestMessage__Group__0__Impl rule__RequestMessage__Group__1 )
            // InternalLwMessage.g:1122:2: rule__RequestMessage__Group__0__Impl rule__RequestMessage__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__RequestMessage__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__RequestMessage__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group__0"


    // $ANTLR start "rule__RequestMessage__Group__0__Impl"
    // InternalLwMessage.g:1129:1: rule__RequestMessage__Group__0__Impl : ( ( rule__RequestMessage__Alternatives_0 ) ) ;
    public final void rule__RequestMessage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1133:1: ( ( ( rule__RequestMessage__Alternatives_0 ) ) )
            // InternalLwMessage.g:1134:1: ( ( rule__RequestMessage__Alternatives_0 ) )
            {
            // InternalLwMessage.g:1134:1: ( ( rule__RequestMessage__Alternatives_0 ) )
            // InternalLwMessage.g:1135:1: ( rule__RequestMessage__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getAlternatives_0()); 
            }
            // InternalLwMessage.g:1136:1: ( rule__RequestMessage__Alternatives_0 )
            // InternalLwMessage.g:1136:2: rule__RequestMessage__Alternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__RequestMessage__Alternatives_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getAlternatives_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group__0__Impl"


    // $ANTLR start "rule__RequestMessage__Group__1"
    // InternalLwMessage.g:1146:1: rule__RequestMessage__Group__1 : rule__RequestMessage__Group__1__Impl ;
    public final void rule__RequestMessage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1150:1: ( rule__RequestMessage__Group__1__Impl )
            // InternalLwMessage.g:1151:2: rule__RequestMessage__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RequestMessage__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group__1"


    // $ANTLR start "rule__RequestMessage__Group__1__Impl"
    // InternalLwMessage.g:1157:1: rule__RequestMessage__Group__1__Impl : ( ( rule__RequestMessage__Group_1__0 )? ) ;
    public final void rule__RequestMessage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1161:1: ( ( ( rule__RequestMessage__Group_1__0 )? ) )
            // InternalLwMessage.g:1162:1: ( ( rule__RequestMessage__Group_1__0 )? )
            {
            // InternalLwMessage.g:1162:1: ( ( rule__RequestMessage__Group_1__0 )? )
            // InternalLwMessage.g:1163:1: ( rule__RequestMessage__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getGroup_1()); 
            }
            // InternalLwMessage.g:1164:1: ( rule__RequestMessage__Group_1__0 )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==21) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalLwMessage.g:1164:2: rule__RequestMessage__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RequestMessage__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group__1__Impl"


    // $ANTLR start "rule__RequestMessage__Group_1__0"
    // InternalLwMessage.g:1178:1: rule__RequestMessage__Group_1__0 : rule__RequestMessage__Group_1__0__Impl rule__RequestMessage__Group_1__1 ;
    public final void rule__RequestMessage__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1182:1: ( rule__RequestMessage__Group_1__0__Impl rule__RequestMessage__Group_1__1 )
            // InternalLwMessage.g:1183:2: rule__RequestMessage__Group_1__0__Impl rule__RequestMessage__Group_1__1
            {
            pushFollow(FOLLOW_4);
            rule__RequestMessage__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__RequestMessage__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group_1__0"


    // $ANTLR start "rule__RequestMessage__Group_1__0__Impl"
    // InternalLwMessage.g:1190:1: rule__RequestMessage__Group_1__0__Impl : ( '(' ) ;
    public final void rule__RequestMessage__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1194:1: ( ( '(' ) )
            // InternalLwMessage.g:1195:1: ( '(' )
            {
            // InternalLwMessage.g:1195:1: ( '(' )
            // InternalLwMessage.g:1196:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getLeftParenthesisKeyword_1_0()); 
            }
            match(input,21,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getLeftParenthesisKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group_1__0__Impl"


    // $ANTLR start "rule__RequestMessage__Group_1__1"
    // InternalLwMessage.g:1209:1: rule__RequestMessage__Group_1__1 : rule__RequestMessage__Group_1__1__Impl rule__RequestMessage__Group_1__2 ;
    public final void rule__RequestMessage__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1213:1: ( rule__RequestMessage__Group_1__1__Impl rule__RequestMessage__Group_1__2 )
            // InternalLwMessage.g:1214:2: rule__RequestMessage__Group_1__1__Impl rule__RequestMessage__Group_1__2
            {
            pushFollow(FOLLOW_4);
            rule__RequestMessage__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__RequestMessage__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group_1__1"


    // $ANTLR start "rule__RequestMessage__Group_1__1__Impl"
    // InternalLwMessage.g:1221:1: rule__RequestMessage__Group_1__1__Impl : ( ( ruleMessageRequestArguments )? ) ;
    public final void rule__RequestMessage__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1225:1: ( ( ( ruleMessageRequestArguments )? ) )
            // InternalLwMessage.g:1226:1: ( ( ruleMessageRequestArguments )? )
            {
            // InternalLwMessage.g:1226:1: ( ( ruleMessageRequestArguments )? )
            // InternalLwMessage.g:1227:1: ( ruleMessageRequestArguments )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getMessageRequestArgumentsParserRuleCall_1_1()); 
            }
            // InternalLwMessage.g:1228:1: ( ruleMessageRequestArguments )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( ((LA15_0>=RULE_ID && LA15_0<=RULE_STRING)||(LA15_0>=18 && LA15_0<=20)||LA15_0==24||LA15_0==27) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalLwMessage.g:1228:3: ruleMessageRequestArguments
                    {
                    pushFollow(FOLLOW_2);
                    ruleMessageRequestArguments();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getMessageRequestArgumentsParserRuleCall_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group_1__1__Impl"


    // $ANTLR start "rule__RequestMessage__Group_1__2"
    // InternalLwMessage.g:1238:1: rule__RequestMessage__Group_1__2 : rule__RequestMessage__Group_1__2__Impl ;
    public final void rule__RequestMessage__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1242:1: ( rule__RequestMessage__Group_1__2__Impl )
            // InternalLwMessage.g:1243:2: rule__RequestMessage__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RequestMessage__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group_1__2"


    // $ANTLR start "rule__RequestMessage__Group_1__2__Impl"
    // InternalLwMessage.g:1249:1: rule__RequestMessage__Group_1__2__Impl : ( ')' ) ;
    public final void rule__RequestMessage__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1253:1: ( ( ')' ) )
            // InternalLwMessage.g:1254:1: ( ')' )
            {
            // InternalLwMessage.g:1254:1: ( ')' )
            // InternalLwMessage.g:1255:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getRightParenthesisKeyword_1_2()); 
            }
            match(input,22,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getRightParenthesisKeyword_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__Group_1__2__Impl"


    // $ANTLR start "rule__AnyMessage__Group__0"
    // InternalLwMessage.g:1274:1: rule__AnyMessage__Group__0 : rule__AnyMessage__Group__0__Impl rule__AnyMessage__Group__1 ;
    public final void rule__AnyMessage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1278:1: ( rule__AnyMessage__Group__0__Impl rule__AnyMessage__Group__1 )
            // InternalLwMessage.g:1279:2: rule__AnyMessage__Group__0__Impl rule__AnyMessage__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__AnyMessage__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__AnyMessage__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AnyMessage__Group__0"


    // $ANTLR start "rule__AnyMessage__Group__0__Impl"
    // InternalLwMessage.g:1286:1: rule__AnyMessage__Group__0__Impl : ( () ) ;
    public final void rule__AnyMessage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1290:1: ( ( () ) )
            // InternalLwMessage.g:1291:1: ( () )
            {
            // InternalLwMessage.g:1291:1: ( () )
            // InternalLwMessage.g:1292:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAnyMessageAccess().getAnyMessageAction_0()); 
            }
            // InternalLwMessage.g:1293:1: ()
            // InternalLwMessage.g:1295:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAnyMessageAccess().getAnyMessageAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AnyMessage__Group__0__Impl"


    // $ANTLR start "rule__AnyMessage__Group__1"
    // InternalLwMessage.g:1305:1: rule__AnyMessage__Group__1 : rule__AnyMessage__Group__1__Impl ;
    public final void rule__AnyMessage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1309:1: ( rule__AnyMessage__Group__1__Impl )
            // InternalLwMessage.g:1310:2: rule__AnyMessage__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__AnyMessage__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AnyMessage__Group__1"


    // $ANTLR start "rule__AnyMessage__Group__1__Impl"
    // InternalLwMessage.g:1316:1: rule__AnyMessage__Group__1__Impl : ( '*' ) ;
    public final void rule__AnyMessage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1320:1: ( ( '*' ) )
            // InternalLwMessage.g:1321:1: ( '*' )
            {
            // InternalLwMessage.g:1321:1: ( '*' )
            // InternalLwMessage.g:1322:1: '*'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAnyMessageAccess().getAsteriskKeyword_1()); 
            }
            match(input,18,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAnyMessageAccess().getAsteriskKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AnyMessage__Group__1__Impl"


    // $ANTLR start "rule__MessageRequestArguments__Group_0__0"
    // InternalLwMessage.g:1339:1: rule__MessageRequestArguments__Group_0__0 : rule__MessageRequestArguments__Group_0__0__Impl rule__MessageRequestArguments__Group_0__1 ;
    public final void rule__MessageRequestArguments__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1343:1: ( rule__MessageRequestArguments__Group_0__0__Impl rule__MessageRequestArguments__Group_0__1 )
            // InternalLwMessage.g:1344:2: rule__MessageRequestArguments__Group_0__0__Impl rule__MessageRequestArguments__Group_0__1
            {
            pushFollow(FOLLOW_6);
            rule__MessageRequestArguments__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_0__0"


    // $ANTLR start "rule__MessageRequestArguments__Group_0__0__Impl"
    // InternalLwMessage.g:1351:1: rule__MessageRequestArguments__Group_0__0__Impl : ( ( rule__MessageRequestArguments__ArgumentsAssignment_0_0 ) ) ;
    public final void rule__MessageRequestArguments__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1355:1: ( ( ( rule__MessageRequestArguments__ArgumentsAssignment_0_0 ) ) )
            // InternalLwMessage.g:1356:1: ( ( rule__MessageRequestArguments__ArgumentsAssignment_0_0 ) )
            {
            // InternalLwMessage.g:1356:1: ( ( rule__MessageRequestArguments__ArgumentsAssignment_0_0 ) )
            // InternalLwMessage.g:1357:1: ( rule__MessageRequestArguments__ArgumentsAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsAssignment_0_0()); 
            }
            // InternalLwMessage.g:1358:1: ( rule__MessageRequestArguments__ArgumentsAssignment_0_0 )
            // InternalLwMessage.g:1358:2: rule__MessageRequestArguments__ArgumentsAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__ArgumentsAssignment_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsAssignment_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_0__0__Impl"


    // $ANTLR start "rule__MessageRequestArguments__Group_0__1"
    // InternalLwMessage.g:1368:1: rule__MessageRequestArguments__Group_0__1 : rule__MessageRequestArguments__Group_0__1__Impl ;
    public final void rule__MessageRequestArguments__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1372:1: ( rule__MessageRequestArguments__Group_0__1__Impl )
            // InternalLwMessage.g:1373:2: rule__MessageRequestArguments__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_0__1"


    // $ANTLR start "rule__MessageRequestArguments__Group_0__1__Impl"
    // InternalLwMessage.g:1379:1: rule__MessageRequestArguments__Group_0__1__Impl : ( ( rule__MessageRequestArguments__Group_0_1__0 )* ) ;
    public final void rule__MessageRequestArguments__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1383:1: ( ( ( rule__MessageRequestArguments__Group_0_1__0 )* ) )
            // InternalLwMessage.g:1384:1: ( ( rule__MessageRequestArguments__Group_0_1__0 )* )
            {
            // InternalLwMessage.g:1384:1: ( ( rule__MessageRequestArguments__Group_0_1__0 )* )
            // InternalLwMessage.g:1385:1: ( rule__MessageRequestArguments__Group_0_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getGroup_0_1()); 
            }
            // InternalLwMessage.g:1386:1: ( rule__MessageRequestArguments__Group_0_1__0 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==23) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalLwMessage.g:1386:2: rule__MessageRequestArguments__Group_0_1__0
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__MessageRequestArguments__Group_0_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getGroup_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_0__1__Impl"


    // $ANTLR start "rule__MessageRequestArguments__Group_0_1__0"
    // InternalLwMessage.g:1400:1: rule__MessageRequestArguments__Group_0_1__0 : rule__MessageRequestArguments__Group_0_1__0__Impl rule__MessageRequestArguments__Group_0_1__1 ;
    public final void rule__MessageRequestArguments__Group_0_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1404:1: ( rule__MessageRequestArguments__Group_0_1__0__Impl rule__MessageRequestArguments__Group_0_1__1 )
            // InternalLwMessage.g:1405:2: rule__MessageRequestArguments__Group_0_1__0__Impl rule__MessageRequestArguments__Group_0_1__1
            {
            pushFollow(FOLLOW_8);
            rule__MessageRequestArguments__Group_0_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Group_0_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_0_1__0"


    // $ANTLR start "rule__MessageRequestArguments__Group_0_1__0__Impl"
    // InternalLwMessage.g:1412:1: rule__MessageRequestArguments__Group_0_1__0__Impl : ( ',' ) ;
    public final void rule__MessageRequestArguments__Group_0_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1416:1: ( ( ',' ) )
            // InternalLwMessage.g:1417:1: ( ',' )
            {
            // InternalLwMessage.g:1417:1: ( ',' )
            // InternalLwMessage.g:1418:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getCommaKeyword_0_1_0()); 
            }
            match(input,23,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getCommaKeyword_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_0_1__0__Impl"


    // $ANTLR start "rule__MessageRequestArguments__Group_0_1__1"
    // InternalLwMessage.g:1431:1: rule__MessageRequestArguments__Group_0_1__1 : rule__MessageRequestArguments__Group_0_1__1__Impl ;
    public final void rule__MessageRequestArguments__Group_0_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1435:1: ( rule__MessageRequestArguments__Group_0_1__1__Impl )
            // InternalLwMessage.g:1436:2: rule__MessageRequestArguments__Group_0_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Group_0_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_0_1__1"


    // $ANTLR start "rule__MessageRequestArguments__Group_0_1__1__Impl"
    // InternalLwMessage.g:1442:1: rule__MessageRequestArguments__Group_0_1__1__Impl : ( ( rule__MessageRequestArguments__ArgumentsAssignment_0_1_1 ) ) ;
    public final void rule__MessageRequestArguments__Group_0_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1446:1: ( ( ( rule__MessageRequestArguments__ArgumentsAssignment_0_1_1 ) ) )
            // InternalLwMessage.g:1447:1: ( ( rule__MessageRequestArguments__ArgumentsAssignment_0_1_1 ) )
            {
            // InternalLwMessage.g:1447:1: ( ( rule__MessageRequestArguments__ArgumentsAssignment_0_1_1 ) )
            // InternalLwMessage.g:1448:1: ( rule__MessageRequestArguments__ArgumentsAssignment_0_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsAssignment_0_1_1()); 
            }
            // InternalLwMessage.g:1449:1: ( rule__MessageRequestArguments__ArgumentsAssignment_0_1_1 )
            // InternalLwMessage.g:1449:2: rule__MessageRequestArguments__ArgumentsAssignment_0_1_1
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__ArgumentsAssignment_0_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsAssignment_0_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_0_1__1__Impl"


    // $ANTLR start "rule__MessageRequestArguments__Group_1__0"
    // InternalLwMessage.g:1463:1: rule__MessageRequestArguments__Group_1__0 : rule__MessageRequestArguments__Group_1__0__Impl rule__MessageRequestArguments__Group_1__1 ;
    public final void rule__MessageRequestArguments__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1467:1: ( rule__MessageRequestArguments__Group_1__0__Impl rule__MessageRequestArguments__Group_1__1 )
            // InternalLwMessage.g:1468:2: rule__MessageRequestArguments__Group_1__0__Impl rule__MessageRequestArguments__Group_1__1
            {
            pushFollow(FOLLOW_6);
            rule__MessageRequestArguments__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_1__0"


    // $ANTLR start "rule__MessageRequestArguments__Group_1__0__Impl"
    // InternalLwMessage.g:1475:1: rule__MessageRequestArguments__Group_1__0__Impl : ( ( rule__MessageRequestArguments__ArgumentsAssignment_1_0 ) ) ;
    public final void rule__MessageRequestArguments__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1479:1: ( ( ( rule__MessageRequestArguments__ArgumentsAssignment_1_0 ) ) )
            // InternalLwMessage.g:1480:1: ( ( rule__MessageRequestArguments__ArgumentsAssignment_1_0 ) )
            {
            // InternalLwMessage.g:1480:1: ( ( rule__MessageRequestArguments__ArgumentsAssignment_1_0 ) )
            // InternalLwMessage.g:1481:1: ( rule__MessageRequestArguments__ArgumentsAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsAssignment_1_0()); 
            }
            // InternalLwMessage.g:1482:1: ( rule__MessageRequestArguments__ArgumentsAssignment_1_0 )
            // InternalLwMessage.g:1482:2: rule__MessageRequestArguments__ArgumentsAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__ArgumentsAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_1__0__Impl"


    // $ANTLR start "rule__MessageRequestArguments__Group_1__1"
    // InternalLwMessage.g:1492:1: rule__MessageRequestArguments__Group_1__1 : rule__MessageRequestArguments__Group_1__1__Impl ;
    public final void rule__MessageRequestArguments__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1496:1: ( rule__MessageRequestArguments__Group_1__1__Impl )
            // InternalLwMessage.g:1497:2: rule__MessageRequestArguments__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_1__1"


    // $ANTLR start "rule__MessageRequestArguments__Group_1__1__Impl"
    // InternalLwMessage.g:1503:1: rule__MessageRequestArguments__Group_1__1__Impl : ( ( rule__MessageRequestArguments__Group_1_1__0 )* ) ;
    public final void rule__MessageRequestArguments__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1507:1: ( ( ( rule__MessageRequestArguments__Group_1_1__0 )* ) )
            // InternalLwMessage.g:1508:1: ( ( rule__MessageRequestArguments__Group_1_1__0 )* )
            {
            // InternalLwMessage.g:1508:1: ( ( rule__MessageRequestArguments__Group_1_1__0 )* )
            // InternalLwMessage.g:1509:1: ( rule__MessageRequestArguments__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getGroup_1_1()); 
            }
            // InternalLwMessage.g:1510:1: ( rule__MessageRequestArguments__Group_1_1__0 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==23) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalLwMessage.g:1510:2: rule__MessageRequestArguments__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__MessageRequestArguments__Group_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getGroup_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_1__1__Impl"


    // $ANTLR start "rule__MessageRequestArguments__Group_1_1__0"
    // InternalLwMessage.g:1524:1: rule__MessageRequestArguments__Group_1_1__0 : rule__MessageRequestArguments__Group_1_1__0__Impl rule__MessageRequestArguments__Group_1_1__1 ;
    public final void rule__MessageRequestArguments__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1528:1: ( rule__MessageRequestArguments__Group_1_1__0__Impl rule__MessageRequestArguments__Group_1_1__1 )
            // InternalLwMessage.g:1529:2: rule__MessageRequestArguments__Group_1_1__0__Impl rule__MessageRequestArguments__Group_1_1__1
            {
            pushFollow(FOLLOW_9);
            rule__MessageRequestArguments__Group_1_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Group_1_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_1_1__0"


    // $ANTLR start "rule__MessageRequestArguments__Group_1_1__0__Impl"
    // InternalLwMessage.g:1536:1: rule__MessageRequestArguments__Group_1_1__0__Impl : ( ',' ) ;
    public final void rule__MessageRequestArguments__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1540:1: ( ( ',' ) )
            // InternalLwMessage.g:1541:1: ( ',' )
            {
            // InternalLwMessage.g:1541:1: ( ',' )
            // InternalLwMessage.g:1542:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getCommaKeyword_1_1_0()); 
            }
            match(input,23,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getCommaKeyword_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_1_1__0__Impl"


    // $ANTLR start "rule__MessageRequestArguments__Group_1_1__1"
    // InternalLwMessage.g:1555:1: rule__MessageRequestArguments__Group_1_1__1 : rule__MessageRequestArguments__Group_1_1__1__Impl ;
    public final void rule__MessageRequestArguments__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1559:1: ( rule__MessageRequestArguments__Group_1_1__1__Impl )
            // InternalLwMessage.g:1560:2: rule__MessageRequestArguments__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__Group_1_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_1_1__1"


    // $ANTLR start "rule__MessageRequestArguments__Group_1_1__1__Impl"
    // InternalLwMessage.g:1566:1: rule__MessageRequestArguments__Group_1_1__1__Impl : ( ( rule__MessageRequestArguments__ArgumentsAssignment_1_1_1 ) ) ;
    public final void rule__MessageRequestArguments__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1570:1: ( ( ( rule__MessageRequestArguments__ArgumentsAssignment_1_1_1 ) ) )
            // InternalLwMessage.g:1571:1: ( ( rule__MessageRequestArguments__ArgumentsAssignment_1_1_1 ) )
            {
            // InternalLwMessage.g:1571:1: ( ( rule__MessageRequestArguments__ArgumentsAssignment_1_1_1 ) )
            // InternalLwMessage.g:1572:1: ( rule__MessageRequestArguments__ArgumentsAssignment_1_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsAssignment_1_1_1()); 
            }
            // InternalLwMessage.g:1573:1: ( rule__MessageRequestArguments__ArgumentsAssignment_1_1_1 )
            // InternalLwMessage.g:1573:2: rule__MessageRequestArguments__ArgumentsAssignment_1_1_1
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArguments__ArgumentsAssignment_1_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsAssignment_1_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__Group_1_1__1__Impl"


    // $ANTLR start "rule__MessageRequestArgument__Group_0__0"
    // InternalLwMessage.g:1587:1: rule__MessageRequestArgument__Group_0__0 : rule__MessageRequestArgument__Group_0__0__Impl rule__MessageRequestArgument__Group_0__1 ;
    public final void rule__MessageRequestArgument__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1591:1: ( rule__MessageRequestArgument__Group_0__0__Impl rule__MessageRequestArgument__Group_0__1 )
            // InternalLwMessage.g:1592:2: rule__MessageRequestArgument__Group_0__0__Impl rule__MessageRequestArgument__Group_0__1
            {
            pushFollow(FOLLOW_10);
            rule__MessageRequestArgument__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageRequestArgument__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArgument__Group_0__0"


    // $ANTLR start "rule__MessageRequestArgument__Group_0__0__Impl"
    // InternalLwMessage.g:1599:1: rule__MessageRequestArgument__Group_0__0__Impl : ( () ) ;
    public final void rule__MessageRequestArgument__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1603:1: ( ( () ) )
            // InternalLwMessage.g:1604:1: ( () )
            {
            // InternalLwMessage.g:1604:1: ( () )
            // InternalLwMessage.g:1605:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentAccess().getWildcardMessageArgumentAction_0_0()); 
            }
            // InternalLwMessage.g:1606:1: ()
            // InternalLwMessage.g:1608:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentAccess().getWildcardMessageArgumentAction_0_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArgument__Group_0__0__Impl"


    // $ANTLR start "rule__MessageRequestArgument__Group_0__1"
    // InternalLwMessage.g:1618:1: rule__MessageRequestArgument__Group_0__1 : rule__MessageRequestArgument__Group_0__1__Impl ;
    public final void rule__MessageRequestArgument__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1622:1: ( rule__MessageRequestArgument__Group_0__1__Impl )
            // InternalLwMessage.g:1623:2: rule__MessageRequestArgument__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestArgument__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArgument__Group_0__1"


    // $ANTLR start "rule__MessageRequestArgument__Group_0__1__Impl"
    // InternalLwMessage.g:1629:1: rule__MessageRequestArgument__Group_0__1__Impl : ( ( '-' ) ) ;
    public final void rule__MessageRequestArgument__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1633:1: ( ( ( '-' ) ) )
            // InternalLwMessage.g:1634:1: ( ( '-' ) )
            {
            // InternalLwMessage.g:1634:1: ( ( '-' ) )
            // InternalLwMessage.g:1635:1: ( '-' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentAccess().getHyphenMinusKeyword_0_1()); 
            }
            // InternalLwMessage.g:1636:1: ( '-' )
            // InternalLwMessage.g:1637:2: '-'
            {
            match(input,24,FOLLOW_2); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentAccess().getHyphenMinusKeyword_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArgument__Group_0__1__Impl"


    // $ANTLR start "rule__MessageRequestNameAndValue__Group__0"
    // InternalLwMessage.g:1652:1: rule__MessageRequestNameAndValue__Group__0 : rule__MessageRequestNameAndValue__Group__0__Impl rule__MessageRequestNameAndValue__Group__1 ;
    public final void rule__MessageRequestNameAndValue__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1656:1: ( rule__MessageRequestNameAndValue__Group__0__Impl rule__MessageRequestNameAndValue__Group__1 )
            // InternalLwMessage.g:1657:2: rule__MessageRequestNameAndValue__Group__0__Impl rule__MessageRequestNameAndValue__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__MessageRequestNameAndValue__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageRequestNameAndValue__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__Group__0"


    // $ANTLR start "rule__MessageRequestNameAndValue__Group__0__Impl"
    // InternalLwMessage.g:1664:1: rule__MessageRequestNameAndValue__Group__0__Impl : ( ( rule__MessageRequestNameAndValue__Alternatives_0 ) ) ;
    public final void rule__MessageRequestNameAndValue__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1668:1: ( ( ( rule__MessageRequestNameAndValue__Alternatives_0 ) ) )
            // InternalLwMessage.g:1669:1: ( ( rule__MessageRequestNameAndValue__Alternatives_0 ) )
            {
            // InternalLwMessage.g:1669:1: ( ( rule__MessageRequestNameAndValue__Alternatives_0 ) )
            // InternalLwMessage.g:1670:1: ( rule__MessageRequestNameAndValue__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getAlternatives_0()); 
            }
            // InternalLwMessage.g:1671:1: ( rule__MessageRequestNameAndValue__Alternatives_0 )
            // InternalLwMessage.g:1671:2: rule__MessageRequestNameAndValue__Alternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestNameAndValue__Alternatives_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getAlternatives_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__Group__0__Impl"


    // $ANTLR start "rule__MessageRequestNameAndValue__Group__1"
    // InternalLwMessage.g:1681:1: rule__MessageRequestNameAndValue__Group__1 : rule__MessageRequestNameAndValue__Group__1__Impl rule__MessageRequestNameAndValue__Group__2 ;
    public final void rule__MessageRequestNameAndValue__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1685:1: ( rule__MessageRequestNameAndValue__Group__1__Impl rule__MessageRequestNameAndValue__Group__2 )
            // InternalLwMessage.g:1686:2: rule__MessageRequestNameAndValue__Group__1__Impl rule__MessageRequestNameAndValue__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__MessageRequestNameAndValue__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageRequestNameAndValue__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__Group__1"


    // $ANTLR start "rule__MessageRequestNameAndValue__Group__1__Impl"
    // InternalLwMessage.g:1693:1: rule__MessageRequestNameAndValue__Group__1__Impl : ( '=' ) ;
    public final void rule__MessageRequestNameAndValue__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1697:1: ( ( '=' ) )
            // InternalLwMessage.g:1698:1: ( '=' )
            {
            // InternalLwMessage.g:1698:1: ( '=' )
            // InternalLwMessage.g:1699:1: '='
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getEqualsSignKeyword_1()); 
            }
            match(input,25,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getEqualsSignKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__Group__1__Impl"


    // $ANTLR start "rule__MessageRequestNameAndValue__Group__2"
    // InternalLwMessage.g:1712:1: rule__MessageRequestNameAndValue__Group__2 : rule__MessageRequestNameAndValue__Group__2__Impl ;
    public final void rule__MessageRequestNameAndValue__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1716:1: ( rule__MessageRequestNameAndValue__Group__2__Impl )
            // InternalLwMessage.g:1717:2: rule__MessageRequestNameAndValue__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageRequestNameAndValue__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__Group__2"


    // $ANTLR start "rule__MessageRequestNameAndValue__Group__2__Impl"
    // InternalLwMessage.g:1723:1: rule__MessageRequestNameAndValue__Group__2__Impl : ( ruleMessageRequestValue ) ;
    public final void rule__MessageRequestNameAndValue__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1727:1: ( ( ruleMessageRequestValue ) )
            // InternalLwMessage.g:1728:1: ( ruleMessageRequestValue )
            {
            // InternalLwMessage.g:1728:1: ( ruleMessageRequestValue )
            // InternalLwMessage.g:1729:1: ruleMessageRequestValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getMessageRequestValueParserRuleCall_2()); 
            }
            pushFollow(FOLLOW_2);
            ruleMessageRequestValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getMessageRequestValueParserRuleCall_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__Group__2__Impl"


    // $ANTLR start "rule__ReplyMessage__Group__0"
    // InternalLwMessage.g:1746:1: rule__ReplyMessage__Group__0 : rule__ReplyMessage__Group__0__Impl rule__ReplyMessage__Group__1 ;
    public final void rule__ReplyMessage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1750:1: ( rule__ReplyMessage__Group__0__Impl rule__ReplyMessage__Group__1 )
            // InternalLwMessage.g:1751:2: rule__ReplyMessage__Group__0__Impl rule__ReplyMessage__Group__1
            {
            pushFollow(FOLLOW_12);
            rule__ReplyMessage__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group__0"


    // $ANTLR start "rule__ReplyMessage__Group__0__Impl"
    // InternalLwMessage.g:1758:1: rule__ReplyMessage__Group__0__Impl : ( ( ruleAssignmentTarget )? ) ;
    public final void rule__ReplyMessage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1762:1: ( ( ( ruleAssignmentTarget )? ) )
            // InternalLwMessage.g:1763:1: ( ( ruleAssignmentTarget )? )
            {
            // InternalLwMessage.g:1763:1: ( ( ruleAssignmentTarget )? )
            // InternalLwMessage.g:1764:1: ( ruleAssignmentTarget )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getAssignmentTargetParserRuleCall_0()); 
            }
            // InternalLwMessage.g:1765:1: ( ruleAssignmentTarget )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_ID) ) {
                int LA18_1 = input.LA(2);

                if ( (LA18_1==25||LA18_1==28) ) {
                    alt18=1;
                }
            }
            switch (alt18) {
                case 1 :
                    // InternalLwMessage.g:1765:3: ruleAssignmentTarget
                    {
                    pushFollow(FOLLOW_2);
                    ruleAssignmentTarget();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getAssignmentTargetParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group__0__Impl"


    // $ANTLR start "rule__ReplyMessage__Group__1"
    // InternalLwMessage.g:1775:1: rule__ReplyMessage__Group__1 : rule__ReplyMessage__Group__1__Impl rule__ReplyMessage__Group__2 ;
    public final void rule__ReplyMessage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1779:1: ( rule__ReplyMessage__Group__1__Impl rule__ReplyMessage__Group__2 )
            // InternalLwMessage.g:1780:2: rule__ReplyMessage__Group__1__Impl rule__ReplyMessage__Group__2
            {
            pushFollow(FOLLOW_13);
            rule__ReplyMessage__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group__1"


    // $ANTLR start "rule__ReplyMessage__Group__1__Impl"
    // InternalLwMessage.g:1787:1: rule__ReplyMessage__Group__1__Impl : ( ( rule__ReplyMessage__Alternatives_1 ) ) ;
    public final void rule__ReplyMessage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1791:1: ( ( ( rule__ReplyMessage__Alternatives_1 ) ) )
            // InternalLwMessage.g:1792:1: ( ( rule__ReplyMessage__Alternatives_1 ) )
            {
            // InternalLwMessage.g:1792:1: ( ( rule__ReplyMessage__Alternatives_1 ) )
            // InternalLwMessage.g:1793:1: ( rule__ReplyMessage__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getAlternatives_1()); 
            }
            // InternalLwMessage.g:1794:1: ( rule__ReplyMessage__Alternatives_1 )
            // InternalLwMessage.g:1794:2: rule__ReplyMessage__Alternatives_1
            {
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getAlternatives_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group__1__Impl"


    // $ANTLR start "rule__ReplyMessage__Group__2"
    // InternalLwMessage.g:1804:1: rule__ReplyMessage__Group__2 : rule__ReplyMessage__Group__2__Impl rule__ReplyMessage__Group__3 ;
    public final void rule__ReplyMessage__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1808:1: ( rule__ReplyMessage__Group__2__Impl rule__ReplyMessage__Group__3 )
            // InternalLwMessage.g:1809:2: rule__ReplyMessage__Group__2__Impl rule__ReplyMessage__Group__3
            {
            pushFollow(FOLLOW_13);
            rule__ReplyMessage__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group__2"


    // $ANTLR start "rule__ReplyMessage__Group__2__Impl"
    // InternalLwMessage.g:1816:1: rule__ReplyMessage__Group__2__Impl : ( ( rule__ReplyMessage__Group_2__0 )? ) ;
    public final void rule__ReplyMessage__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1820:1: ( ( ( rule__ReplyMessage__Group_2__0 )? ) )
            // InternalLwMessage.g:1821:1: ( ( rule__ReplyMessage__Group_2__0 )? )
            {
            // InternalLwMessage.g:1821:1: ( ( rule__ReplyMessage__Group_2__0 )? )
            // InternalLwMessage.g:1822:1: ( rule__ReplyMessage__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getGroup_2()); 
            }
            // InternalLwMessage.g:1823:1: ( rule__ReplyMessage__Group_2__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==21) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalLwMessage.g:1823:2: rule__ReplyMessage__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ReplyMessage__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group__2__Impl"


    // $ANTLR start "rule__ReplyMessage__Group__3"
    // InternalLwMessage.g:1833:1: rule__ReplyMessage__Group__3 : rule__ReplyMessage__Group__3__Impl ;
    public final void rule__ReplyMessage__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1837:1: ( rule__ReplyMessage__Group__3__Impl )
            // InternalLwMessage.g:1838:2: rule__ReplyMessage__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group__3"


    // $ANTLR start "rule__ReplyMessage__Group__3__Impl"
    // InternalLwMessage.g:1844:1: rule__ReplyMessage__Group__3__Impl : ( ( rule__ReplyMessage__ValueAssignment_3 )? ) ;
    public final void rule__ReplyMessage__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1848:1: ( ( ( rule__ReplyMessage__ValueAssignment_3 )? ) )
            // InternalLwMessage.g:1849:1: ( ( rule__ReplyMessage__ValueAssignment_3 )? )
            {
            // InternalLwMessage.g:1849:1: ( ( rule__ReplyMessage__ValueAssignment_3 )? )
            // InternalLwMessage.g:1850:1: ( rule__ReplyMessage__ValueAssignment_3 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getValueAssignment_3()); 
            }
            // InternalLwMessage.g:1851:1: ( rule__ReplyMessage__ValueAssignment_3 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==26) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalLwMessage.g:1851:2: rule__ReplyMessage__ValueAssignment_3
                    {
                    pushFollow(FOLLOW_2);
                    rule__ReplyMessage__ValueAssignment_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getValueAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group__3__Impl"


    // $ANTLR start "rule__ReplyMessage__Group_2__0"
    // InternalLwMessage.g:1869:1: rule__ReplyMessage__Group_2__0 : rule__ReplyMessage__Group_2__0__Impl rule__ReplyMessage__Group_2__1 ;
    public final void rule__ReplyMessage__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1873:1: ( rule__ReplyMessage__Group_2__0__Impl rule__ReplyMessage__Group_2__1 )
            // InternalLwMessage.g:1874:2: rule__ReplyMessage__Group_2__0__Impl rule__ReplyMessage__Group_2__1
            {
            pushFollow(FOLLOW_14);
            rule__ReplyMessage__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group_2__0"


    // $ANTLR start "rule__ReplyMessage__Group_2__0__Impl"
    // InternalLwMessage.g:1881:1: rule__ReplyMessage__Group_2__0__Impl : ( '(' ) ;
    public final void rule__ReplyMessage__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1885:1: ( ( '(' ) )
            // InternalLwMessage.g:1886:1: ( '(' )
            {
            // InternalLwMessage.g:1886:1: ( '(' )
            // InternalLwMessage.g:1887:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getLeftParenthesisKeyword_2_0()); 
            }
            match(input,21,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getLeftParenthesisKeyword_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group_2__0__Impl"


    // $ANTLR start "rule__ReplyMessage__Group_2__1"
    // InternalLwMessage.g:1900:1: rule__ReplyMessage__Group_2__1 : rule__ReplyMessage__Group_2__1__Impl rule__ReplyMessage__Group_2__2 ;
    public final void rule__ReplyMessage__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1904:1: ( rule__ReplyMessage__Group_2__1__Impl rule__ReplyMessage__Group_2__2 )
            // InternalLwMessage.g:1905:2: rule__ReplyMessage__Group_2__1__Impl rule__ReplyMessage__Group_2__2
            {
            pushFollow(FOLLOW_14);
            rule__ReplyMessage__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Group_2__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group_2__1"


    // $ANTLR start "rule__ReplyMessage__Group_2__1__Impl"
    // InternalLwMessage.g:1912:1: rule__ReplyMessage__Group_2__1__Impl : ( ( ruleMessageReplyOutputs )? ) ;
    public final void rule__ReplyMessage__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1916:1: ( ( ( ruleMessageReplyOutputs )? ) )
            // InternalLwMessage.g:1917:1: ( ( ruleMessageReplyOutputs )? )
            {
            // InternalLwMessage.g:1917:1: ( ( ruleMessageReplyOutputs )? )
            // InternalLwMessage.g:1918:1: ( ruleMessageReplyOutputs )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getMessageReplyOutputsParserRuleCall_2_1()); 
            }
            // InternalLwMessage.g:1919:1: ( ruleMessageReplyOutputs )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_ID) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalLwMessage.g:1919:3: ruleMessageReplyOutputs
                    {
                    pushFollow(FOLLOW_2);
                    ruleMessageReplyOutputs();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getMessageReplyOutputsParserRuleCall_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group_2__1__Impl"


    // $ANTLR start "rule__ReplyMessage__Group_2__2"
    // InternalLwMessage.g:1929:1: rule__ReplyMessage__Group_2__2 : rule__ReplyMessage__Group_2__2__Impl ;
    public final void rule__ReplyMessage__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1933:1: ( rule__ReplyMessage__Group_2__2__Impl )
            // InternalLwMessage.g:1934:2: rule__ReplyMessage__Group_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ReplyMessage__Group_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group_2__2"


    // $ANTLR start "rule__ReplyMessage__Group_2__2__Impl"
    // InternalLwMessage.g:1940:1: rule__ReplyMessage__Group_2__2__Impl : ( ')' ) ;
    public final void rule__ReplyMessage__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1944:1: ( ( ')' ) )
            // InternalLwMessage.g:1945:1: ( ')' )
            {
            // InternalLwMessage.g:1945:1: ( ')' )
            // InternalLwMessage.g:1946:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getRightParenthesisKeyword_2_2()); 
            }
            match(input,22,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getRightParenthesisKeyword_2_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__Group_2__2__Impl"


    // $ANTLR start "rule__AssignmentTarget__Group__0"
    // InternalLwMessage.g:1965:1: rule__AssignmentTarget__Group__0 : rule__AssignmentTarget__Group__0__Impl rule__AssignmentTarget__Group__1 ;
    public final void rule__AssignmentTarget__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1969:1: ( rule__AssignmentTarget__Group__0__Impl rule__AssignmentTarget__Group__1 )
            // InternalLwMessage.g:1970:2: rule__AssignmentTarget__Group__0__Impl rule__AssignmentTarget__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__AssignmentTarget__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__AssignmentTarget__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AssignmentTarget__Group__0"


    // $ANTLR start "rule__AssignmentTarget__Group__0__Impl"
    // InternalLwMessage.g:1977:1: rule__AssignmentTarget__Group__0__Impl : ( ( rule__AssignmentTarget__TargetAssignment_0 ) ) ;
    public final void rule__AssignmentTarget__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1981:1: ( ( ( rule__AssignmentTarget__TargetAssignment_0 ) ) )
            // InternalLwMessage.g:1982:1: ( ( rule__AssignmentTarget__TargetAssignment_0 ) )
            {
            // InternalLwMessage.g:1982:1: ( ( rule__AssignmentTarget__TargetAssignment_0 ) )
            // InternalLwMessage.g:1983:1: ( rule__AssignmentTarget__TargetAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentTargetAccess().getTargetAssignment_0()); 
            }
            // InternalLwMessage.g:1984:1: ( rule__AssignmentTarget__TargetAssignment_0 )
            // InternalLwMessage.g:1984:2: rule__AssignmentTarget__TargetAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__AssignmentTarget__TargetAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentTargetAccess().getTargetAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AssignmentTarget__Group__0__Impl"


    // $ANTLR start "rule__AssignmentTarget__Group__1"
    // InternalLwMessage.g:1994:1: rule__AssignmentTarget__Group__1 : rule__AssignmentTarget__Group__1__Impl ;
    public final void rule__AssignmentTarget__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:1998:1: ( rule__AssignmentTarget__Group__1__Impl )
            // InternalLwMessage.g:1999:2: rule__AssignmentTarget__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__AssignmentTarget__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AssignmentTarget__Group__1"


    // $ANTLR start "rule__AssignmentTarget__Group__1__Impl"
    // InternalLwMessage.g:2005:1: rule__AssignmentTarget__Group__1__Impl : ( '=' ) ;
    public final void rule__AssignmentTarget__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2009:1: ( ( '=' ) )
            // InternalLwMessage.g:2010:1: ( '=' )
            {
            // InternalLwMessage.g:2010:1: ( '=' )
            // InternalLwMessage.g:2011:1: '='
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentTargetAccess().getEqualsSignKeyword_1()); 
            }
            match(input,25,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentTargetAccess().getEqualsSignKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AssignmentTarget__Group__1__Impl"


    // $ANTLR start "rule__MessageReplyOutputs__Group__0"
    // InternalLwMessage.g:2028:1: rule__MessageReplyOutputs__Group__0 : rule__MessageReplyOutputs__Group__0__Impl rule__MessageReplyOutputs__Group__1 ;
    public final void rule__MessageReplyOutputs__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2032:1: ( rule__MessageReplyOutputs__Group__0__Impl rule__MessageReplyOutputs__Group__1 )
            // InternalLwMessage.g:2033:2: rule__MessageReplyOutputs__Group__0__Impl rule__MessageReplyOutputs__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__MessageReplyOutputs__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutputs__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__Group__0"


    // $ANTLR start "rule__MessageReplyOutputs__Group__0__Impl"
    // InternalLwMessage.g:2040:1: rule__MessageReplyOutputs__Group__0__Impl : ( ( rule__MessageReplyOutputs__OutputsAssignment_0 ) ) ;
    public final void rule__MessageReplyOutputs__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2044:1: ( ( ( rule__MessageReplyOutputs__OutputsAssignment_0 ) ) )
            // InternalLwMessage.g:2045:1: ( ( rule__MessageReplyOutputs__OutputsAssignment_0 ) )
            {
            // InternalLwMessage.g:2045:1: ( ( rule__MessageReplyOutputs__OutputsAssignment_0 ) )
            // InternalLwMessage.g:2046:1: ( rule__MessageReplyOutputs__OutputsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputsAccess().getOutputsAssignment_0()); 
            }
            // InternalLwMessage.g:2047:1: ( rule__MessageReplyOutputs__OutputsAssignment_0 )
            // InternalLwMessage.g:2047:2: rule__MessageReplyOutputs__OutputsAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutputs__OutputsAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputsAccess().getOutputsAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__Group__0__Impl"


    // $ANTLR start "rule__MessageReplyOutputs__Group__1"
    // InternalLwMessage.g:2057:1: rule__MessageReplyOutputs__Group__1 : rule__MessageReplyOutputs__Group__1__Impl ;
    public final void rule__MessageReplyOutputs__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2061:1: ( rule__MessageReplyOutputs__Group__1__Impl )
            // InternalLwMessage.g:2062:2: rule__MessageReplyOutputs__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutputs__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__Group__1"


    // $ANTLR start "rule__MessageReplyOutputs__Group__1__Impl"
    // InternalLwMessage.g:2068:1: rule__MessageReplyOutputs__Group__1__Impl : ( ( rule__MessageReplyOutputs__Group_1__0 )* ) ;
    public final void rule__MessageReplyOutputs__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2072:1: ( ( ( rule__MessageReplyOutputs__Group_1__0 )* ) )
            // InternalLwMessage.g:2073:1: ( ( rule__MessageReplyOutputs__Group_1__0 )* )
            {
            // InternalLwMessage.g:2073:1: ( ( rule__MessageReplyOutputs__Group_1__0 )* )
            // InternalLwMessage.g:2074:1: ( rule__MessageReplyOutputs__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputsAccess().getGroup_1()); 
            }
            // InternalLwMessage.g:2075:1: ( rule__MessageReplyOutputs__Group_1__0 )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==23) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalLwMessage.g:2075:2: rule__MessageReplyOutputs__Group_1__0
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__MessageReplyOutputs__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputsAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__Group__1__Impl"


    // $ANTLR start "rule__MessageReplyOutputs__Group_1__0"
    // InternalLwMessage.g:2089:1: rule__MessageReplyOutputs__Group_1__0 : rule__MessageReplyOutputs__Group_1__0__Impl rule__MessageReplyOutputs__Group_1__1 ;
    public final void rule__MessageReplyOutputs__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2093:1: ( rule__MessageReplyOutputs__Group_1__0__Impl rule__MessageReplyOutputs__Group_1__1 )
            // InternalLwMessage.g:2094:2: rule__MessageReplyOutputs__Group_1__0__Impl rule__MessageReplyOutputs__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__MessageReplyOutputs__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutputs__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__Group_1__0"


    // $ANTLR start "rule__MessageReplyOutputs__Group_1__0__Impl"
    // InternalLwMessage.g:2101:1: rule__MessageReplyOutputs__Group_1__0__Impl : ( ',' ) ;
    public final void rule__MessageReplyOutputs__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2105:1: ( ( ',' ) )
            // InternalLwMessage.g:2106:1: ( ',' )
            {
            // InternalLwMessage.g:2106:1: ( ',' )
            // InternalLwMessage.g:2107:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputsAccess().getCommaKeyword_1_0()); 
            }
            match(input,23,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputsAccess().getCommaKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__Group_1__0__Impl"


    // $ANTLR start "rule__MessageReplyOutputs__Group_1__1"
    // InternalLwMessage.g:2120:1: rule__MessageReplyOutputs__Group_1__1 : rule__MessageReplyOutputs__Group_1__1__Impl ;
    public final void rule__MessageReplyOutputs__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2124:1: ( rule__MessageReplyOutputs__Group_1__1__Impl )
            // InternalLwMessage.g:2125:2: rule__MessageReplyOutputs__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutputs__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__Group_1__1"


    // $ANTLR start "rule__MessageReplyOutputs__Group_1__1__Impl"
    // InternalLwMessage.g:2131:1: rule__MessageReplyOutputs__Group_1__1__Impl : ( ( rule__MessageReplyOutputs__OutputsAssignment_1_1 ) ) ;
    public final void rule__MessageReplyOutputs__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2135:1: ( ( ( rule__MessageReplyOutputs__OutputsAssignment_1_1 ) ) )
            // InternalLwMessage.g:2136:1: ( ( rule__MessageReplyOutputs__OutputsAssignment_1_1 ) )
            {
            // InternalLwMessage.g:2136:1: ( ( rule__MessageReplyOutputs__OutputsAssignment_1_1 ) )
            // InternalLwMessage.g:2137:1: ( rule__MessageReplyOutputs__OutputsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputsAccess().getOutputsAssignment_1_1()); 
            }
            // InternalLwMessage.g:2138:1: ( rule__MessageReplyOutputs__OutputsAssignment_1_1 )
            // InternalLwMessage.g:2138:2: rule__MessageReplyOutputs__OutputsAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutputs__OutputsAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputsAccess().getOutputsAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__Group_1__1__Impl"


    // $ANTLR start "rule__MessageReplyOutput__Group_0__0"
    // InternalLwMessage.g:2152:1: rule__MessageReplyOutput__Group_0__0 : rule__MessageReplyOutput__Group_0__0__Impl rule__MessageReplyOutput__Group_0__1 ;
    public final void rule__MessageReplyOutput__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2156:1: ( rule__MessageReplyOutput__Group_0__0__Impl rule__MessageReplyOutput__Group_0__1 )
            // InternalLwMessage.g:2157:2: rule__MessageReplyOutput__Group_0__0__Impl rule__MessageReplyOutput__Group_0__1
            {
            pushFollow(FOLLOW_12);
            rule__MessageReplyOutput__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_0__0"


    // $ANTLR start "rule__MessageReplyOutput__Group_0__0__Impl"
    // InternalLwMessage.g:2164:1: rule__MessageReplyOutput__Group_0__0__Impl : ( ( ruleAssignmentTarget ) ) ;
    public final void rule__MessageReplyOutput__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2168:1: ( ( ( ruleAssignmentTarget ) ) )
            // InternalLwMessage.g:2169:1: ( ( ruleAssignmentTarget ) )
            {
            // InternalLwMessage.g:2169:1: ( ( ruleAssignmentTarget ) )
            // InternalLwMessage.g:2170:1: ( ruleAssignmentTarget )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getAssignmentTargetParserRuleCall_0_0()); 
            }
            // InternalLwMessage.g:2171:1: ( ruleAssignmentTarget )
            // InternalLwMessage.g:2171:3: ruleAssignmentTarget
            {
            pushFollow(FOLLOW_2);
            ruleAssignmentTarget();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getAssignmentTargetParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_0__0__Impl"


    // $ANTLR start "rule__MessageReplyOutput__Group_0__1"
    // InternalLwMessage.g:2181:1: rule__MessageReplyOutput__Group_0__1 : rule__MessageReplyOutput__Group_0__1__Impl rule__MessageReplyOutput__Group_0__2 ;
    public final void rule__MessageReplyOutput__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2185:1: ( rule__MessageReplyOutput__Group_0__1__Impl rule__MessageReplyOutput__Group_0__2 )
            // InternalLwMessage.g:2186:2: rule__MessageReplyOutput__Group_0__1__Impl rule__MessageReplyOutput__Group_0__2
            {
            pushFollow(FOLLOW_15);
            rule__MessageReplyOutput__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__Group_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_0__1"


    // $ANTLR start "rule__MessageReplyOutput__Group_0__1__Impl"
    // InternalLwMessage.g:2193:1: rule__MessageReplyOutput__Group_0__1__Impl : ( ( rule__MessageReplyOutput__ParameterAssignment_0_1 ) ) ;
    public final void rule__MessageReplyOutput__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2197:1: ( ( ( rule__MessageReplyOutput__ParameterAssignment_0_1 ) ) )
            // InternalLwMessage.g:2198:1: ( ( rule__MessageReplyOutput__ParameterAssignment_0_1 ) )
            {
            // InternalLwMessage.g:2198:1: ( ( rule__MessageReplyOutput__ParameterAssignment_0_1 ) )
            // InternalLwMessage.g:2199:1: ( rule__MessageReplyOutput__ParameterAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getParameterAssignment_0_1()); 
            }
            // InternalLwMessage.g:2200:1: ( rule__MessageReplyOutput__ParameterAssignment_0_1 )
            // InternalLwMessage.g:2200:2: rule__MessageReplyOutput__ParameterAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__ParameterAssignment_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getParameterAssignment_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_0__1__Impl"


    // $ANTLR start "rule__MessageReplyOutput__Group_0__2"
    // InternalLwMessage.g:2210:1: rule__MessageReplyOutput__Group_0__2 : rule__MessageReplyOutput__Group_0__2__Impl ;
    public final void rule__MessageReplyOutput__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2214:1: ( rule__MessageReplyOutput__Group_0__2__Impl )
            // InternalLwMessage.g:2215:2: rule__MessageReplyOutput__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__Group_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_0__2"


    // $ANTLR start "rule__MessageReplyOutput__Group_0__2__Impl"
    // InternalLwMessage.g:2221:1: rule__MessageReplyOutput__Group_0__2__Impl : ( ( rule__MessageReplyOutput__ValueAssignment_0_2 )? ) ;
    public final void rule__MessageReplyOutput__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2225:1: ( ( ( rule__MessageReplyOutput__ValueAssignment_0_2 )? ) )
            // InternalLwMessage.g:2226:1: ( ( rule__MessageReplyOutput__ValueAssignment_0_2 )? )
            {
            // InternalLwMessage.g:2226:1: ( ( rule__MessageReplyOutput__ValueAssignment_0_2 )? )
            // InternalLwMessage.g:2227:1: ( rule__MessageReplyOutput__ValueAssignment_0_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getValueAssignment_0_2()); 
            }
            // InternalLwMessage.g:2228:1: ( rule__MessageReplyOutput__ValueAssignment_0_2 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==26) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalLwMessage.g:2228:2: rule__MessageReplyOutput__ValueAssignment_0_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__MessageReplyOutput__ValueAssignment_0_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getValueAssignment_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_0__2__Impl"


    // $ANTLR start "rule__MessageReplyOutput__Group_1__0"
    // InternalLwMessage.g:2244:1: rule__MessageReplyOutput__Group_1__0 : rule__MessageReplyOutput__Group_1__0__Impl rule__MessageReplyOutput__Group_1__1 ;
    public final void rule__MessageReplyOutput__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2248:1: ( rule__MessageReplyOutput__Group_1__0__Impl rule__MessageReplyOutput__Group_1__1 )
            // InternalLwMessage.g:2249:2: rule__MessageReplyOutput__Group_1__0__Impl rule__MessageReplyOutput__Group_1__1
            {
            pushFollow(FOLLOW_15);
            rule__MessageReplyOutput__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_1__0"


    // $ANTLR start "rule__MessageReplyOutput__Group_1__0__Impl"
    // InternalLwMessage.g:2256:1: rule__MessageReplyOutput__Group_1__0__Impl : ( ( rule__MessageReplyOutput__ParameterAssignment_1_0 ) ) ;
    public final void rule__MessageReplyOutput__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2260:1: ( ( ( rule__MessageReplyOutput__ParameterAssignment_1_0 ) ) )
            // InternalLwMessage.g:2261:1: ( ( rule__MessageReplyOutput__ParameterAssignment_1_0 ) )
            {
            // InternalLwMessage.g:2261:1: ( ( rule__MessageReplyOutput__ParameterAssignment_1_0 ) )
            // InternalLwMessage.g:2262:1: ( rule__MessageReplyOutput__ParameterAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getParameterAssignment_1_0()); 
            }
            // InternalLwMessage.g:2263:1: ( rule__MessageReplyOutput__ParameterAssignment_1_0 )
            // InternalLwMessage.g:2263:2: rule__MessageReplyOutput__ParameterAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__ParameterAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getParameterAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_1__0__Impl"


    // $ANTLR start "rule__MessageReplyOutput__Group_1__1"
    // InternalLwMessage.g:2273:1: rule__MessageReplyOutput__Group_1__1 : rule__MessageReplyOutput__Group_1__1__Impl ;
    public final void rule__MessageReplyOutput__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2277:1: ( rule__MessageReplyOutput__Group_1__1__Impl )
            // InternalLwMessage.g:2278:2: rule__MessageReplyOutput__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_1__1"


    // $ANTLR start "rule__MessageReplyOutput__Group_1__1__Impl"
    // InternalLwMessage.g:2284:1: rule__MessageReplyOutput__Group_1__1__Impl : ( ( rule__MessageReplyOutput__ValueAssignment_1_1 ) ) ;
    public final void rule__MessageReplyOutput__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2288:1: ( ( ( rule__MessageReplyOutput__ValueAssignment_1_1 ) ) )
            // InternalLwMessage.g:2289:1: ( ( rule__MessageReplyOutput__ValueAssignment_1_1 ) )
            {
            // InternalLwMessage.g:2289:1: ( ( rule__MessageReplyOutput__ValueAssignment_1_1 ) )
            // InternalLwMessage.g:2290:1: ( rule__MessageReplyOutput__ValueAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getValueAssignment_1_1()); 
            }
            // InternalLwMessage.g:2291:1: ( rule__MessageReplyOutput__ValueAssignment_1_1 )
            // InternalLwMessage.g:2291:2: rule__MessageReplyOutput__ValueAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__MessageReplyOutput__ValueAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getValueAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__Group_1__1__Impl"


    // $ANTLR start "rule__OutputValue__Group__0"
    // InternalLwMessage.g:2305:1: rule__OutputValue__Group__0 : rule__OutputValue__Group__0__Impl rule__OutputValue__Group__1 ;
    public final void rule__OutputValue__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2309:1: ( rule__OutputValue__Group__0__Impl rule__OutputValue__Group__1 )
            // InternalLwMessage.g:2310:2: rule__OutputValue__Group__0__Impl rule__OutputValue__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__OutputValue__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__OutputValue__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OutputValue__Group__0"


    // $ANTLR start "rule__OutputValue__Group__0__Impl"
    // InternalLwMessage.g:2317:1: rule__OutputValue__Group__0__Impl : ( ':' ) ;
    public final void rule__OutputValue__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2321:1: ( ( ':' ) )
            // InternalLwMessage.g:2322:1: ( ':' )
            {
            // InternalLwMessage.g:2322:1: ( ':' )
            // InternalLwMessage.g:2323:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOutputValueAccess().getColonKeyword_0()); 
            }
            match(input,26,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getOutputValueAccess().getColonKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OutputValue__Group__0__Impl"


    // $ANTLR start "rule__OutputValue__Group__1"
    // InternalLwMessage.g:2336:1: rule__OutputValue__Group__1 : rule__OutputValue__Group__1__Impl ;
    public final void rule__OutputValue__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2340:1: ( rule__OutputValue__Group__1__Impl )
            // InternalLwMessage.g:2341:2: rule__OutputValue__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OutputValue__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OutputValue__Group__1"


    // $ANTLR start "rule__OutputValue__Group__1__Impl"
    // InternalLwMessage.g:2347:1: rule__OutputValue__Group__1__Impl : ( ( rule__OutputValue__ValueAssignment_1 ) ) ;
    public final void rule__OutputValue__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2351:1: ( ( ( rule__OutputValue__ValueAssignment_1 ) ) )
            // InternalLwMessage.g:2352:1: ( ( rule__OutputValue__ValueAssignment_1 ) )
            {
            // InternalLwMessage.g:2352:1: ( ( rule__OutputValue__ValueAssignment_1 ) )
            // InternalLwMessage.g:2353:1: ( rule__OutputValue__ValueAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOutputValueAccess().getValueAssignment_1()); 
            }
            // InternalLwMessage.g:2354:1: ( rule__OutputValue__ValueAssignment_1 )
            // InternalLwMessage.g:2354:2: rule__OutputValue__ValueAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__OutputValue__ValueAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getOutputValueAccess().getValueAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OutputValue__Group__1__Impl"


    // $ANTLR start "rule__NullValue__Group__0"
    // InternalLwMessage.g:2368:1: rule__NullValue__Group__0 : rule__NullValue__Group__0__Impl rule__NullValue__Group__1 ;
    public final void rule__NullValue__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2372:1: ( rule__NullValue__Group__0__Impl rule__NullValue__Group__1 )
            // InternalLwMessage.g:2373:2: rule__NullValue__Group__0__Impl rule__NullValue__Group__1
            {
            pushFollow(FOLLOW_16);
            rule__NullValue__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__NullValue__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NullValue__Group__0"


    // $ANTLR start "rule__NullValue__Group__0__Impl"
    // InternalLwMessage.g:2380:1: rule__NullValue__Group__0__Impl : ( () ) ;
    public final void rule__NullValue__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2384:1: ( ( () ) )
            // InternalLwMessage.g:2385:1: ( () )
            {
            // InternalLwMessage.g:2385:1: ( () )
            // InternalLwMessage.g:2386:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNullValueAccess().getNullValueAction_0()); 
            }
            // InternalLwMessage.g:2387:1: ()
            // InternalLwMessage.g:2389:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNullValueAccess().getNullValueAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NullValue__Group__0__Impl"


    // $ANTLR start "rule__NullValue__Group__1"
    // InternalLwMessage.g:2399:1: rule__NullValue__Group__1 : rule__NullValue__Group__1__Impl ;
    public final void rule__NullValue__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2403:1: ( rule__NullValue__Group__1__Impl )
            // InternalLwMessage.g:2404:2: rule__NullValue__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__NullValue__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NullValue__Group__1"


    // $ANTLR start "rule__NullValue__Group__1__Impl"
    // InternalLwMessage.g:2410:1: rule__NullValue__Group__1__Impl : ( 'null' ) ;
    public final void rule__NullValue__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2414:1: ( ( 'null' ) )
            // InternalLwMessage.g:2415:1: ( 'null' )
            {
            // InternalLwMessage.g:2415:1: ( 'null' )
            // InternalLwMessage.g:2416:1: 'null'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNullValueAccess().getNullKeyword_1()); 
            }
            match(input,27,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNullValueAccess().getNullKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NullValue__Group__1__Impl"


    // $ANTLR start "rule__QName__Group__0"
    // InternalLwMessage.g:2433:1: rule__QName__Group__0 : rule__QName__Group__0__Impl rule__QName__Group__1 ;
    public final void rule__QName__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2437:1: ( rule__QName__Group__0__Impl rule__QName__Group__1 )
            // InternalLwMessage.g:2438:2: rule__QName__Group__0__Impl rule__QName__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__QName__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__QName__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QName__Group__0"


    // $ANTLR start "rule__QName__Group__0__Impl"
    // InternalLwMessage.g:2445:1: rule__QName__Group__0__Impl : ( ruleName ) ;
    public final void rule__QName__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2449:1: ( ( ruleName ) )
            // InternalLwMessage.g:2450:1: ( ruleName )
            {
            // InternalLwMessage.g:2450:1: ( ruleName )
            // InternalLwMessage.g:2451:1: ruleName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQNameAccess().getNameParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQNameAccess().getNameParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QName__Group__0__Impl"


    // $ANTLR start "rule__QName__Group__1"
    // InternalLwMessage.g:2462:1: rule__QName__Group__1 : rule__QName__Group__1__Impl ;
    public final void rule__QName__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2466:1: ( rule__QName__Group__1__Impl )
            // InternalLwMessage.g:2467:2: rule__QName__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QName__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QName__Group__1"


    // $ANTLR start "rule__QName__Group__1__Impl"
    // InternalLwMessage.g:2473:1: rule__QName__Group__1__Impl : ( ( rule__QName__Group_1__0 )* ) ;
    public final void rule__QName__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2477:1: ( ( ( rule__QName__Group_1__0 )* ) )
            // InternalLwMessage.g:2478:1: ( ( rule__QName__Group_1__0 )* )
            {
            // InternalLwMessage.g:2478:1: ( ( rule__QName__Group_1__0 )* )
            // InternalLwMessage.g:2479:1: ( rule__QName__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQNameAccess().getGroup_1()); 
            }
            // InternalLwMessage.g:2480:1: ( rule__QName__Group_1__0 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==28) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalLwMessage.g:2480:2: rule__QName__Group_1__0
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__QName__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQNameAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QName__Group__1__Impl"


    // $ANTLR start "rule__QName__Group_1__0"
    // InternalLwMessage.g:2494:1: rule__QName__Group_1__0 : rule__QName__Group_1__0__Impl rule__QName__Group_1__1 ;
    public final void rule__QName__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2498:1: ( rule__QName__Group_1__0__Impl rule__QName__Group_1__1 )
            // InternalLwMessage.g:2499:2: rule__QName__Group_1__0__Impl rule__QName__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__QName__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__QName__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QName__Group_1__0"


    // $ANTLR start "rule__QName__Group_1__0__Impl"
    // InternalLwMessage.g:2506:1: rule__QName__Group_1__0__Impl : ( '::' ) ;
    public final void rule__QName__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2510:1: ( ( '::' ) )
            // InternalLwMessage.g:2511:1: ( '::' )
            {
            // InternalLwMessage.g:2511:1: ( '::' )
            // InternalLwMessage.g:2512:1: '::'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQNameAccess().getColonColonKeyword_1_0()); 
            }
            match(input,28,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQNameAccess().getColonColonKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QName__Group_1__0__Impl"


    // $ANTLR start "rule__QName__Group_1__1"
    // InternalLwMessage.g:2525:1: rule__QName__Group_1__1 : rule__QName__Group_1__1__Impl ;
    public final void rule__QName__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2529:1: ( rule__QName__Group_1__1__Impl )
            // InternalLwMessage.g:2530:2: rule__QName__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QName__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QName__Group_1__1"


    // $ANTLR start "rule__QName__Group_1__1__Impl"
    // InternalLwMessage.g:2536:1: rule__QName__Group_1__1__Impl : ( ruleName ) ;
    public final void rule__QName__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2540:1: ( ( ruleName ) )
            // InternalLwMessage.g:2541:1: ( ruleName )
            {
            // InternalLwMessage.g:2541:1: ( ruleName )
            // InternalLwMessage.g:2542:1: ruleName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQNameAccess().getNameParserRuleCall_1_1()); 
            }
            pushFollow(FOLLOW_2);
            ruleName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQNameAccess().getNameParserRuleCall_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QName__Group_1__1__Impl"


    // $ANTLR start "rule__RequestMessage__NameAssignment_0_0"
    // InternalLwMessage.g:2558:1: rule__RequestMessage__NameAssignment_0_0 : ( ruleName ) ;
    public final void rule__RequestMessage__NameAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2562:1: ( ( ruleName ) )
            // InternalLwMessage.g:2563:1: ( ruleName )
            {
            // InternalLwMessage.g:2563:1: ( ruleName )
            // InternalLwMessage.g:2564:1: ruleName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getNameNameParserRuleCall_0_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getNameNameParserRuleCall_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__NameAssignment_0_0"


    // $ANTLR start "rule__RequestMessage__SignalAssignment_0_1"
    // InternalLwMessage.g:2573:1: rule__RequestMessage__SignalAssignment_0_1 : ( ( RULE_ID ) ) ;
    public final void rule__RequestMessage__SignalAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2577:1: ( ( ( RULE_ID ) ) )
            // InternalLwMessage.g:2578:1: ( ( RULE_ID ) )
            {
            // InternalLwMessage.g:2578:1: ( ( RULE_ID ) )
            // InternalLwMessage.g:2579:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getSignalSignalCrossReference_0_1_0()); 
            }
            // InternalLwMessage.g:2580:1: ( RULE_ID )
            // InternalLwMessage.g:2581:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getSignalSignalIDTerminalRuleCall_0_1_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getSignalSignalIDTerminalRuleCall_0_1_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getSignalSignalCrossReference_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__SignalAssignment_0_1"


    // $ANTLR start "rule__RequestMessage__OperationAssignment_0_2"
    // InternalLwMessage.g:2592:1: rule__RequestMessage__OperationAssignment_0_2 : ( ( RULE_ID ) ) ;
    public final void rule__RequestMessage__OperationAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2596:1: ( ( ( RULE_ID ) ) )
            // InternalLwMessage.g:2597:1: ( ( RULE_ID ) )
            {
            // InternalLwMessage.g:2597:1: ( ( RULE_ID ) )
            // InternalLwMessage.g:2598:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getOperationOperationCrossReference_0_2_0()); 
            }
            // InternalLwMessage.g:2599:1: ( RULE_ID )
            // InternalLwMessage.g:2600:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRequestMessageAccess().getOperationOperationIDTerminalRuleCall_0_2_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getOperationOperationIDTerminalRuleCall_0_2_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRequestMessageAccess().getOperationOperationCrossReference_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequestMessage__OperationAssignment_0_2"


    // $ANTLR start "rule__MessageRequestArguments__ArgumentsAssignment_0_0"
    // InternalLwMessage.g:2611:1: rule__MessageRequestArguments__ArgumentsAssignment_0_0 : ( ruleMessageRequestArgument ) ;
    public final void rule__MessageRequestArguments__ArgumentsAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2615:1: ( ( ruleMessageRequestArgument ) )
            // InternalLwMessage.g:2616:1: ( ruleMessageRequestArgument )
            {
            // InternalLwMessage.g:2616:1: ( ruleMessageRequestArgument )
            // InternalLwMessage.g:2617:1: ruleMessageRequestArgument
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentParserRuleCall_0_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMessageRequestArgument();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentParserRuleCall_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__ArgumentsAssignment_0_0"


    // $ANTLR start "rule__MessageRequestArguments__ArgumentsAssignment_0_1_1"
    // InternalLwMessage.g:2626:1: rule__MessageRequestArguments__ArgumentsAssignment_0_1_1 : ( ruleMessageRequestArgument ) ;
    public final void rule__MessageRequestArguments__ArgumentsAssignment_0_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2630:1: ( ( ruleMessageRequestArgument ) )
            // InternalLwMessage.g:2631:1: ( ruleMessageRequestArgument )
            {
            // InternalLwMessage.g:2631:1: ( ruleMessageRequestArgument )
            // InternalLwMessage.g:2632:1: ruleMessageRequestArgument
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentParserRuleCall_0_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMessageRequestArgument();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentParserRuleCall_0_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__ArgumentsAssignment_0_1_1"


    // $ANTLR start "rule__MessageRequestArguments__ArgumentsAssignment_1_0"
    // InternalLwMessage.g:2641:1: rule__MessageRequestArguments__ArgumentsAssignment_1_0 : ( ruleMessageRequestArgumentWithName ) ;
    public final void rule__MessageRequestArguments__ArgumentsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2645:1: ( ( ruleMessageRequestArgumentWithName ) )
            // InternalLwMessage.g:2646:1: ( ruleMessageRequestArgumentWithName )
            {
            // InternalLwMessage.g:2646:1: ( ruleMessageRequestArgumentWithName )
            // InternalLwMessage.g:2647:1: ruleMessageRequestArgumentWithName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentWithNameParserRuleCall_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMessageRequestArgumentWithName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentWithNameParserRuleCall_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__ArgumentsAssignment_1_0"


    // $ANTLR start "rule__MessageRequestArguments__ArgumentsAssignment_1_1_1"
    // InternalLwMessage.g:2656:1: rule__MessageRequestArguments__ArgumentsAssignment_1_1_1 : ( ruleMessageRequestArgumentWithName ) ;
    public final void rule__MessageRequestArguments__ArgumentsAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2660:1: ( ( ruleMessageRequestArgumentWithName ) )
            // InternalLwMessage.g:2661:1: ( ruleMessageRequestArgumentWithName )
            {
            // InternalLwMessage.g:2661:1: ( ruleMessageRequestArgumentWithName )
            // InternalLwMessage.g:2662:1: ruleMessageRequestArgumentWithName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentWithNameParserRuleCall_1_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMessageRequestArgumentWithName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentWithNameParserRuleCall_1_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestArguments__ArgumentsAssignment_1_1_1"


    // $ANTLR start "rule__MessageRequestNameAndValue__NameAssignment_0_0"
    // InternalLwMessage.g:2671:1: rule__MessageRequestNameAndValue__NameAssignment_0_0 : ( ruleName ) ;
    public final void rule__MessageRequestNameAndValue__NameAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2675:1: ( ( ruleName ) )
            // InternalLwMessage.g:2676:1: ( ruleName )
            {
            // InternalLwMessage.g:2676:1: ( ruleName )
            // InternalLwMessage.g:2677:1: ruleName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getNameNameParserRuleCall_0_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getNameNameParserRuleCall_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__NameAssignment_0_0"


    // $ANTLR start "rule__MessageRequestNameAndValue__PropertyAssignment_0_1"
    // InternalLwMessage.g:2686:1: rule__MessageRequestNameAndValue__PropertyAssignment_0_1 : ( ( RULE_ID ) ) ;
    public final void rule__MessageRequestNameAndValue__PropertyAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2690:1: ( ( ( RULE_ID ) ) )
            // InternalLwMessage.g:2691:1: ( ( RULE_ID ) )
            {
            // InternalLwMessage.g:2691:1: ( ( RULE_ID ) )
            // InternalLwMessage.g:2692:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getPropertyPropertyCrossReference_0_1_0()); 
            }
            // InternalLwMessage.g:2693:1: ( RULE_ID )
            // InternalLwMessage.g:2694:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getPropertyPropertyIDTerminalRuleCall_0_1_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getPropertyPropertyIDTerminalRuleCall_0_1_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getPropertyPropertyCrossReference_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__PropertyAssignment_0_1"


    // $ANTLR start "rule__MessageRequestNameAndValue__ParameterAssignment_0_2"
    // InternalLwMessage.g:2705:1: rule__MessageRequestNameAndValue__ParameterAssignment_0_2 : ( ( RULE_ID ) ) ;
    public final void rule__MessageRequestNameAndValue__ParameterAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2709:1: ( ( ( RULE_ID ) ) )
            // InternalLwMessage.g:2710:1: ( ( RULE_ID ) )
            {
            // InternalLwMessage.g:2710:1: ( ( RULE_ID ) )
            // InternalLwMessage.g:2711:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getParameterParameterCrossReference_0_2_0()); 
            }
            // InternalLwMessage.g:2712:1: ( RULE_ID )
            // InternalLwMessage.g:2713:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestNameAndValueAccess().getParameterParameterIDTerminalRuleCall_0_2_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getParameterParameterIDTerminalRuleCall_0_2_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestNameAndValueAccess().getParameterParameterCrossReference_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestNameAndValue__ParameterAssignment_0_2"


    // $ANTLR start "rule__MessageRequestValue__ValueAssignment"
    // InternalLwMessage.g:2724:1: rule__MessageRequestValue__ValueAssignment : ( ruleValue ) ;
    public final void rule__MessageRequestValue__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2728:1: ( ( ruleValue ) )
            // InternalLwMessage.g:2729:1: ( ruleValue )
            {
            // InternalLwMessage.g:2729:1: ( ruleValue )
            // InternalLwMessage.g:2730:1: ruleValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageRequestValueAccess().getValueValueParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageRequestValueAccess().getValueValueParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageRequestValue__ValueAssignment"


    // $ANTLR start "rule__ReplyMessage__NameAssignment_1_0"
    // InternalLwMessage.g:2739:1: rule__ReplyMessage__NameAssignment_1_0 : ( ruleName ) ;
    public final void rule__ReplyMessage__NameAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2743:1: ( ( ruleName ) )
            // InternalLwMessage.g:2744:1: ( ruleName )
            {
            // InternalLwMessage.g:2744:1: ( ruleName )
            // InternalLwMessage.g:2745:1: ruleName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getNameNameParserRuleCall_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getNameNameParserRuleCall_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__NameAssignment_1_0"


    // $ANTLR start "rule__ReplyMessage__OperationAssignment_1_1"
    // InternalLwMessage.g:2754:1: rule__ReplyMessage__OperationAssignment_1_1 : ( ( RULE_ID ) ) ;
    public final void rule__ReplyMessage__OperationAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2758:1: ( ( ( RULE_ID ) ) )
            // InternalLwMessage.g:2759:1: ( ( RULE_ID ) )
            {
            // InternalLwMessage.g:2759:1: ( ( RULE_ID ) )
            // InternalLwMessage.g:2760:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getOperationOperationCrossReference_1_1_0()); 
            }
            // InternalLwMessage.g:2761:1: ( RULE_ID )
            // InternalLwMessage.g:2762:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getOperationOperationIDTerminalRuleCall_1_1_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getOperationOperationIDTerminalRuleCall_1_1_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getOperationOperationCrossReference_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__OperationAssignment_1_1"


    // $ANTLR start "rule__ReplyMessage__ValueAssignment_3"
    // InternalLwMessage.g:2773:1: rule__ReplyMessage__ValueAssignment_3 : ( ruleOutputValue ) ;
    public final void rule__ReplyMessage__ValueAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2777:1: ( ( ruleOutputValue ) )
            // InternalLwMessage.g:2778:1: ( ruleOutputValue )
            {
            // InternalLwMessage.g:2778:1: ( ruleOutputValue )
            // InternalLwMessage.g:2779:1: ruleOutputValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getReplyMessageAccess().getValueOutputValueParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleOutputValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getReplyMessageAccess().getValueOutputValueParserRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ReplyMessage__ValueAssignment_3"


    // $ANTLR start "rule__AssignmentTarget__TargetAssignment_0"
    // InternalLwMessage.g:2788:1: rule__AssignmentTarget__TargetAssignment_0 : ( ( ruleQName ) ) ;
    public final void rule__AssignmentTarget__TargetAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2792:1: ( ( ( ruleQName ) ) )
            // InternalLwMessage.g:2793:1: ( ( ruleQName ) )
            {
            // InternalLwMessage.g:2793:1: ( ( ruleQName ) )
            // InternalLwMessage.g:2794:1: ( ruleQName )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentTargetAccess().getTargetConnectableElementCrossReference_0_0()); 
            }
            // InternalLwMessage.g:2795:1: ( ruleQName )
            // InternalLwMessage.g:2796:1: ruleQName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentTargetAccess().getTargetConnectableElementQNameParserRuleCall_0_0_1()); 
            }
            pushFollow(FOLLOW_2);
            ruleQName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentTargetAccess().getTargetConnectableElementQNameParserRuleCall_0_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentTargetAccess().getTargetConnectableElementCrossReference_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AssignmentTarget__TargetAssignment_0"


    // $ANTLR start "rule__MessageReplyOutputs__OutputsAssignment_0"
    // InternalLwMessage.g:2807:1: rule__MessageReplyOutputs__OutputsAssignment_0 : ( ruleMessageReplyOutput ) ;
    public final void rule__MessageReplyOutputs__OutputsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2811:1: ( ( ruleMessageReplyOutput ) )
            // InternalLwMessage.g:2812:1: ( ruleMessageReplyOutput )
            {
            // InternalLwMessage.g:2812:1: ( ruleMessageReplyOutput )
            // InternalLwMessage.g:2813:1: ruleMessageReplyOutput
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputsAccess().getOutputsMessageReplyOutputParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMessageReplyOutput();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputsAccess().getOutputsMessageReplyOutputParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__OutputsAssignment_0"


    // $ANTLR start "rule__MessageReplyOutputs__OutputsAssignment_1_1"
    // InternalLwMessage.g:2822:1: rule__MessageReplyOutputs__OutputsAssignment_1_1 : ( ruleMessageReplyOutput ) ;
    public final void rule__MessageReplyOutputs__OutputsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2826:1: ( ( ruleMessageReplyOutput ) )
            // InternalLwMessage.g:2827:1: ( ruleMessageReplyOutput )
            {
            // InternalLwMessage.g:2827:1: ( ruleMessageReplyOutput )
            // InternalLwMessage.g:2828:1: ruleMessageReplyOutput
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputsAccess().getOutputsMessageReplyOutputParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMessageReplyOutput();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputsAccess().getOutputsMessageReplyOutputParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutputs__OutputsAssignment_1_1"


    // $ANTLR start "rule__MessageReplyOutput__ParameterAssignment_0_1"
    // InternalLwMessage.g:2837:1: rule__MessageReplyOutput__ParameterAssignment_0_1 : ( ( RULE_ID ) ) ;
    public final void rule__MessageReplyOutput__ParameterAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2841:1: ( ( ( RULE_ID ) ) )
            // InternalLwMessage.g:2842:1: ( ( RULE_ID ) )
            {
            // InternalLwMessage.g:2842:1: ( ( RULE_ID ) )
            // InternalLwMessage.g:2843:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getParameterParameterCrossReference_0_1_0()); 
            }
            // InternalLwMessage.g:2844:1: ( RULE_ID )
            // InternalLwMessage.g:2845:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getParameterParameterIDTerminalRuleCall_0_1_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getParameterParameterIDTerminalRuleCall_0_1_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getParameterParameterCrossReference_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__ParameterAssignment_0_1"


    // $ANTLR start "rule__MessageReplyOutput__ValueAssignment_0_2"
    // InternalLwMessage.g:2856:1: rule__MessageReplyOutput__ValueAssignment_0_2 : ( ruleOutputValue ) ;
    public final void rule__MessageReplyOutput__ValueAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2860:1: ( ( ruleOutputValue ) )
            // InternalLwMessage.g:2861:1: ( ruleOutputValue )
            {
            // InternalLwMessage.g:2861:1: ( ruleOutputValue )
            // InternalLwMessage.g:2862:1: ruleOutputValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getValueOutputValueParserRuleCall_0_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleOutputValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getValueOutputValueParserRuleCall_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__ValueAssignment_0_2"


    // $ANTLR start "rule__MessageReplyOutput__ParameterAssignment_1_0"
    // InternalLwMessage.g:2871:1: rule__MessageReplyOutput__ParameterAssignment_1_0 : ( ( RULE_ID ) ) ;
    public final void rule__MessageReplyOutput__ParameterAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2875:1: ( ( ( RULE_ID ) ) )
            // InternalLwMessage.g:2876:1: ( ( RULE_ID ) )
            {
            // InternalLwMessage.g:2876:1: ( ( RULE_ID ) )
            // InternalLwMessage.g:2877:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getParameterParameterCrossReference_1_0_0()); 
            }
            // InternalLwMessage.g:2878:1: ( RULE_ID )
            // InternalLwMessage.g:2879:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getParameterParameterIDTerminalRuleCall_1_0_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getParameterParameterIDTerminalRuleCall_1_0_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getParameterParameterCrossReference_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__ParameterAssignment_1_0"


    // $ANTLR start "rule__MessageReplyOutput__ValueAssignment_1_1"
    // InternalLwMessage.g:2890:1: rule__MessageReplyOutput__ValueAssignment_1_1 : ( ruleOutputValue ) ;
    public final void rule__MessageReplyOutput__ValueAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2894:1: ( ( ruleOutputValue ) )
            // InternalLwMessage.g:2895:1: ( ruleOutputValue )
            {
            // InternalLwMessage.g:2895:1: ( ruleOutputValue )
            // InternalLwMessage.g:2896:1: ruleOutputValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMessageReplyOutputAccess().getValueOutputValueParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleOutputValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMessageReplyOutputAccess().getValueOutputValueParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MessageReplyOutput__ValueAssignment_1_1"


    // $ANTLR start "rule__OutputValue__ValueAssignment_1"
    // InternalLwMessage.g:2905:1: rule__OutputValue__ValueAssignment_1 : ( ruleValue ) ;
    public final void rule__OutputValue__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2909:1: ( ( ruleValue ) )
            // InternalLwMessage.g:2910:1: ( ruleValue )
            {
            // InternalLwMessage.g:2910:1: ( ruleValue )
            // InternalLwMessage.g:2911:1: ruleValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOutputValueAccess().getValueValueParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getOutputValueAccess().getValueValueParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OutputValue__ValueAssignment_1"


    // $ANTLR start "rule__BooleanValue__ValueAssignment"
    // InternalLwMessage.g:2920:1: rule__BooleanValue__ValueAssignment : ( ruleBoolean ) ;
    public final void rule__BooleanValue__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2924:1: ( ( ruleBoolean ) )
            // InternalLwMessage.g:2925:1: ( ruleBoolean )
            {
            // InternalLwMessage.g:2925:1: ( ruleBoolean )
            // InternalLwMessage.g:2926:1: ruleBoolean
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBooleanValueAccess().getValueBooleanParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBoolean();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBooleanValueAccess().getValueBooleanParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BooleanValue__ValueAssignment"


    // $ANTLR start "rule__IntegerValue__ValueAssignment"
    // InternalLwMessage.g:2935:1: rule__IntegerValue__ValueAssignment : ( RULE_INT ) ;
    public final void rule__IntegerValue__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2939:1: ( ( RULE_INT ) )
            // InternalLwMessage.g:2940:1: ( RULE_INT )
            {
            // InternalLwMessage.g:2940:1: ( RULE_INT )
            // InternalLwMessage.g:2941:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIntegerValueAccess().getValueINTTerminalRuleCall_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIntegerValueAccess().getValueINTTerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IntegerValue__ValueAssignment"


    // $ANTLR start "rule__UnlimitedNaturalValue__ValueAssignment"
    // InternalLwMessage.g:2950:1: rule__UnlimitedNaturalValue__ValueAssignment : ( ruleUnlimitedNatural ) ;
    public final void rule__UnlimitedNaturalValue__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2954:1: ( ( ruleUnlimitedNatural ) )
            // InternalLwMessage.g:2955:1: ( ruleUnlimitedNatural )
            {
            // InternalLwMessage.g:2955:1: ( ruleUnlimitedNatural )
            // InternalLwMessage.g:2956:1: ruleUnlimitedNatural
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnlimitedNaturalValueAccess().getValueUnlimitedNaturalParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleUnlimitedNatural();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnlimitedNaturalValueAccess().getValueUnlimitedNaturalParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnlimitedNaturalValue__ValueAssignment"


    // $ANTLR start "rule__RealValue__ValueAssignment"
    // InternalLwMessage.g:2965:1: rule__RealValue__ValueAssignment : ( ruleDouble ) ;
    public final void rule__RealValue__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2969:1: ( ( ruleDouble ) )
            // InternalLwMessage.g:2970:1: ( ruleDouble )
            {
            // InternalLwMessage.g:2970:1: ( ruleDouble )
            // InternalLwMessage.g:2971:1: ruleDouble
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRealValueAccess().getValueDoubleParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDouble();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRealValueAccess().getValueDoubleParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RealValue__ValueAssignment"


    // $ANTLR start "rule__StringValue__ValueAssignment"
    // InternalLwMessage.g:2980:1: rule__StringValue__ValueAssignment : ( RULE_STRING ) ;
    public final void rule__StringValue__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // InternalLwMessage.g:2984:1: ( ( RULE_STRING ) )
            // InternalLwMessage.g:2985:1: ( RULE_STRING )
            {
            // InternalLwMessage.g:2985:1: ( RULE_STRING )
            // InternalLwMessage.g:2986:1: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStringValueAccess().getValueSTRINGTerminalRuleCall_0()); 
            }
            match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStringValueAccess().getValueSTRINGTerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StringValue__ValueAssignment"

    // $ANTLR start synpred1_InternalLwMessage
    public final void synpred1_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:792:1: ( ( ruleAbstractRequestMessage ) )
        // InternalLwMessage.g:792:1: ( ruleAbstractRequestMessage )
        {
        // InternalLwMessage.g:792:1: ( ruleAbstractRequestMessage )
        // InternalLwMessage.g:793:1: ruleAbstractRequestMessage
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getAbstractMessageAccess().getAbstractRequestMessageParserRuleCall_0()); 
        }
        pushFollow(FOLLOW_2);
        ruleAbstractRequestMessage();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalLwMessage

    // $ANTLR start synpred3_InternalLwMessage
    public final void synpred3_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:836:1: ( ( ( rule__RequestMessage__NameAssignment_0_0 ) ) )
        // InternalLwMessage.g:836:1: ( ( rule__RequestMessage__NameAssignment_0_0 ) )
        {
        // InternalLwMessage.g:836:1: ( ( rule__RequestMessage__NameAssignment_0_0 ) )
        // InternalLwMessage.g:837:1: ( rule__RequestMessage__NameAssignment_0_0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getRequestMessageAccess().getNameAssignment_0_0()); 
        }
        // InternalLwMessage.g:838:1: ( rule__RequestMessage__NameAssignment_0_0 )
        // InternalLwMessage.g:838:2: rule__RequestMessage__NameAssignment_0_0
        {
        pushFollow(FOLLOW_2);
        rule__RequestMessage__NameAssignment_0_0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred3_InternalLwMessage

    // $ANTLR start synpred4_InternalLwMessage
    public final void synpred4_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:842:6: ( ( ( rule__RequestMessage__SignalAssignment_0_1 ) ) )
        // InternalLwMessage.g:842:6: ( ( rule__RequestMessage__SignalAssignment_0_1 ) )
        {
        // InternalLwMessage.g:842:6: ( ( rule__RequestMessage__SignalAssignment_0_1 ) )
        // InternalLwMessage.g:843:1: ( rule__RequestMessage__SignalAssignment_0_1 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getRequestMessageAccess().getSignalAssignment_0_1()); 
        }
        // InternalLwMessage.g:844:1: ( rule__RequestMessage__SignalAssignment_0_1 )
        // InternalLwMessage.g:844:2: rule__RequestMessage__SignalAssignment_0_1
        {
        pushFollow(FOLLOW_2);
        rule__RequestMessage__SignalAssignment_0_1();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred4_InternalLwMessage

    // $ANTLR start synpred7_InternalLwMessage
    public final void synpred7_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:908:1: ( ( ( rule__MessageRequestNameAndValue__NameAssignment_0_0 ) ) )
        // InternalLwMessage.g:908:1: ( ( rule__MessageRequestNameAndValue__NameAssignment_0_0 ) )
        {
        // InternalLwMessage.g:908:1: ( ( rule__MessageRequestNameAndValue__NameAssignment_0_0 ) )
        // InternalLwMessage.g:909:1: ( rule__MessageRequestNameAndValue__NameAssignment_0_0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getMessageRequestNameAndValueAccess().getNameAssignment_0_0()); 
        }
        // InternalLwMessage.g:910:1: ( rule__MessageRequestNameAndValue__NameAssignment_0_0 )
        // InternalLwMessage.g:910:2: rule__MessageRequestNameAndValue__NameAssignment_0_0
        {
        pushFollow(FOLLOW_2);
        rule__MessageRequestNameAndValue__NameAssignment_0_0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred7_InternalLwMessage

    // $ANTLR start synpred8_InternalLwMessage
    public final void synpred8_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:914:6: ( ( ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 ) ) )
        // InternalLwMessage.g:914:6: ( ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 ) )
        {
        // InternalLwMessage.g:914:6: ( ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 ) )
        // InternalLwMessage.g:915:1: ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getMessageRequestNameAndValueAccess().getPropertyAssignment_0_1()); 
        }
        // InternalLwMessage.g:916:1: ( rule__MessageRequestNameAndValue__PropertyAssignment_0_1 )
        // InternalLwMessage.g:916:2: rule__MessageRequestNameAndValue__PropertyAssignment_0_1
        {
        pushFollow(FOLLOW_2);
        rule__MessageRequestNameAndValue__PropertyAssignment_0_1();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred8_InternalLwMessage

    // $ANTLR start synpred9_InternalLwMessage
    public final void synpred9_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:936:1: ( ( ( rule__ReplyMessage__NameAssignment_1_0 ) ) )
        // InternalLwMessage.g:936:1: ( ( rule__ReplyMessage__NameAssignment_1_0 ) )
        {
        // InternalLwMessage.g:936:1: ( ( rule__ReplyMessage__NameAssignment_1_0 ) )
        // InternalLwMessage.g:937:1: ( rule__ReplyMessage__NameAssignment_1_0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getReplyMessageAccess().getNameAssignment_1_0()); 
        }
        // InternalLwMessage.g:938:1: ( rule__ReplyMessage__NameAssignment_1_0 )
        // InternalLwMessage.g:938:2: rule__ReplyMessage__NameAssignment_1_0
        {
        pushFollow(FOLLOW_2);
        rule__ReplyMessage__NameAssignment_1_0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred9_InternalLwMessage

    // $ANTLR start synpred12_InternalLwMessage
    public final void synpred12_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:986:6: ( ( ( ruleIntegerValue ) ) )
        // InternalLwMessage.g:986:6: ( ( ruleIntegerValue ) )
        {
        // InternalLwMessage.g:986:6: ( ( ruleIntegerValue ) )
        // InternalLwMessage.g:987:1: ( ruleIntegerValue )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getValueAccess().getIntegerValueParserRuleCall_1()); 
        }
        // InternalLwMessage.g:988:1: ( ruleIntegerValue )
        // InternalLwMessage.g:988:3: ruleIntegerValue
        {
        pushFollow(FOLLOW_2);
        ruleIntegerValue();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred12_InternalLwMessage

    // $ANTLR start synpred13_InternalLwMessage
    public final void synpred13_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:992:6: ( ( ( ruleUnlimitedNaturalValue ) ) )
        // InternalLwMessage.g:992:6: ( ( ruleUnlimitedNaturalValue ) )
        {
        // InternalLwMessage.g:992:6: ( ( ruleUnlimitedNaturalValue ) )
        // InternalLwMessage.g:993:1: ( ruleUnlimitedNaturalValue )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getValueAccess().getUnlimitedNaturalValueParserRuleCall_2()); 
        }
        // InternalLwMessage.g:994:1: ( ruleUnlimitedNaturalValue )
        // InternalLwMessage.g:994:3: ruleUnlimitedNaturalValue
        {
        pushFollow(FOLLOW_2);
        ruleUnlimitedNaturalValue();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred13_InternalLwMessage

    // $ANTLR start synpred14_InternalLwMessage
    public final void synpred14_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:998:6: ( ( ( ruleRealValue ) ) )
        // InternalLwMessage.g:998:6: ( ( ruleRealValue ) )
        {
        // InternalLwMessage.g:998:6: ( ( ruleRealValue ) )
        // InternalLwMessage.g:999:1: ( ruleRealValue )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getValueAccess().getRealValueParserRuleCall_3()); 
        }
        // InternalLwMessage.g:1000:1: ( ruleRealValue )
        // InternalLwMessage.g:1000:3: ruleRealValue
        {
        pushFollow(FOLLOW_2);
        ruleRealValue();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred14_InternalLwMessage

    // Delegated rules

    public final boolean synpred4_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x00000000095C01F0L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000000091C01E0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000000091C01F0L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000004200000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000400010L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000010000002L});

}