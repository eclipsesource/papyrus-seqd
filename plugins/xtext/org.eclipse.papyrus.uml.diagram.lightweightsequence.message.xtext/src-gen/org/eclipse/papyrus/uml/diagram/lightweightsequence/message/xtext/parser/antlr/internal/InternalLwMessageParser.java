package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.services.LwMessageGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalLwMessageParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_REAL", "RULE_NEG_INT", "RULE_DIGIT", "RULE_DIGIT0", "RULE_DIGITS", "RULE_DECIMAL", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_INTEGER_VALUE", "RULE_WS", "RULE_ANY_OTHER", "'('", "')'", "'*'", "','", "'-'", "'='", "':'", "'null'", "'true'", "'false'"
    };
    public static final int RULE_STRING=6;
    public static final int RULE_DIGITS=11;
    public static final int RULE_SL_COMMENT=14;
    public static final int T__19=19;
    public static final int T__18=18;
    public static final int EOF=-1;
    public static final int RULE_NEG_INT=8;
    public static final int RULE_DIGIT0=10;
    public static final int RULE_ID=4;
    public static final int RULE_WS=16;
    public static final int RULE_REAL=7;
    public static final int RULE_DIGIT=9;
    public static final int RULE_ANY_OTHER=17;
    public static final int RULE_DECIMAL=12;
    public static final int T__26=26;
    public static final int T__27=27;
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
     	
        public InternalLwMessageParser(TokenStream input, LwMessageGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "AbstractMessage";	
       	}
       	
       	@Override
       	protected LwMessageGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleAbstractMessage"
    // InternalLwMessage.g:67:1: entryRuleAbstractMessage returns [EObject current=null] : iv_ruleAbstractMessage= ruleAbstractMessage EOF ;
    public final EObject entryRuleAbstractMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractMessage = null;


        try {
            // InternalLwMessage.g:68:2: (iv_ruleAbstractMessage= ruleAbstractMessage EOF )
            // InternalLwMessage.g:69:2: iv_ruleAbstractMessage= ruleAbstractMessage EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAbstractMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAbstractMessage=ruleAbstractMessage();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAbstractMessage; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAbstractMessage"


    // $ANTLR start "ruleAbstractMessage"
    // InternalLwMessage.g:76:1: ruleAbstractMessage returns [EObject current=null] : (this_AbstractRequestMessage_0= ruleAbstractRequestMessage | ( ( ( RULE_ID ) )=>this_ReplyMessage_1= ruleReplyMessage ) ) ;
    public final EObject ruleAbstractMessage() throws RecognitionException {
        EObject current = null;

        EObject this_AbstractRequestMessage_0 = null;

        EObject this_ReplyMessage_1 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:79:28: ( (this_AbstractRequestMessage_0= ruleAbstractRequestMessage | ( ( ( RULE_ID ) )=>this_ReplyMessage_1= ruleReplyMessage ) ) )
            // InternalLwMessage.g:80:1: (this_AbstractRequestMessage_0= ruleAbstractRequestMessage | ( ( ( RULE_ID ) )=>this_ReplyMessage_1= ruleReplyMessage ) )
            {
            // InternalLwMessage.g:80:1: (this_AbstractRequestMessage_0= ruleAbstractRequestMessage | ( ( ( RULE_ID ) )=>this_ReplyMessage_1= ruleReplyMessage ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==20) ) {
                alt1=1;
            }
            else if ( (LA1_0==RULE_ID) ) {
                int LA1_2 = input.LA(2);

                if ( (true) ) {
                    alt1=1;
                }
                else if ( (synpred1_InternalLwMessage()) ) {
                    alt1=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalLwMessage.g:81:5: this_AbstractRequestMessage_0= ruleAbstractRequestMessage
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAbstractMessageAccess().getAbstractRequestMessageParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_AbstractRequestMessage_0=ruleAbstractRequestMessage();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_AbstractRequestMessage_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:90:6: ( ( ( RULE_ID ) )=>this_ReplyMessage_1= ruleReplyMessage )
                    {
                    // InternalLwMessage.g:90:6: ( ( ( RULE_ID ) )=>this_ReplyMessage_1= ruleReplyMessage )
                    // InternalLwMessage.g:90:7: ( ( RULE_ID ) )=>this_ReplyMessage_1= ruleReplyMessage
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAbstractMessageAccess().getReplyMessageParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_ReplyMessage_1=ruleReplyMessage();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ReplyMessage_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAbstractMessage"


    // $ANTLR start "entryRuleAbstractRequestMessage"
    // InternalLwMessage.g:112:1: entryRuleAbstractRequestMessage returns [EObject current=null] : iv_ruleAbstractRequestMessage= ruleAbstractRequestMessage EOF ;
    public final EObject entryRuleAbstractRequestMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractRequestMessage = null;


        try {
            // InternalLwMessage.g:113:2: (iv_ruleAbstractRequestMessage= ruleAbstractRequestMessage EOF )
            // InternalLwMessage.g:114:2: iv_ruleAbstractRequestMessage= ruleAbstractRequestMessage EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAbstractRequestMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAbstractRequestMessage=ruleAbstractRequestMessage();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAbstractRequestMessage; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAbstractRequestMessage"


    // $ANTLR start "ruleAbstractRequestMessage"
    // InternalLwMessage.g:121:1: ruleAbstractRequestMessage returns [EObject current=null] : (this_AnyMessage_0= ruleAnyMessage | this_RequestMessage_1= ruleRequestMessage ) ;
    public final EObject ruleAbstractRequestMessage() throws RecognitionException {
        EObject current = null;

        EObject this_AnyMessage_0 = null;

        EObject this_RequestMessage_1 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:124:28: ( (this_AnyMessage_0= ruleAnyMessage | this_RequestMessage_1= ruleRequestMessage ) )
            // InternalLwMessage.g:125:1: (this_AnyMessage_0= ruleAnyMessage | this_RequestMessage_1= ruleRequestMessage )
            {
            // InternalLwMessage.g:125:1: (this_AnyMessage_0= ruleAnyMessage | this_RequestMessage_1= ruleRequestMessage )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==20) ) {
                alt2=1;
            }
            else if ( (LA2_0==RULE_ID) ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalLwMessage.g:126:5: this_AnyMessage_0= ruleAnyMessage
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAbstractRequestMessageAccess().getAnyMessageParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_AnyMessage_0=ruleAnyMessage();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_AnyMessage_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:136:5: this_RequestMessage_1= ruleRequestMessage
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAbstractRequestMessageAccess().getRequestMessageParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_RequestMessage_1=ruleRequestMessage();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_RequestMessage_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAbstractRequestMessage"


    // $ANTLR start "entryRuleRequestMessage"
    // InternalLwMessage.g:152:1: entryRuleRequestMessage returns [EObject current=null] : iv_ruleRequestMessage= ruleRequestMessage EOF ;
    public final EObject entryRuleRequestMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequestMessage = null;


        try {
            // InternalLwMessage.g:153:2: (iv_ruleRequestMessage= ruleRequestMessage EOF )
            // InternalLwMessage.g:154:2: iv_ruleRequestMessage= ruleRequestMessage EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRequestMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRequestMessage=ruleRequestMessage();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRequestMessage; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequestMessage"


    // $ANTLR start "ruleRequestMessage"
    // InternalLwMessage.g:161:1: ruleRequestMessage returns [EObject current=null] : ( ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageRequestArguments_4= ruleMessageRequestArguments[$current] )? otherlv_5= ')' )? ) ;
    public final EObject ruleRequestMessage() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_MessageRequestArguments_4 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:164:28: ( ( ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageRequestArguments_4= ruleMessageRequestArguments[$current] )? otherlv_5= ')' )? ) )
            // InternalLwMessage.g:165:1: ( ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageRequestArguments_4= ruleMessageRequestArguments[$current] )? otherlv_5= ')' )? )
            {
            // InternalLwMessage.g:165:1: ( ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageRequestArguments_4= ruleMessageRequestArguments[$current] )? otherlv_5= ')' )? )
            // InternalLwMessage.g:165:2: ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageRequestArguments_4= ruleMessageRequestArguments[$current] )? otherlv_5= ')' )?
            {
            // InternalLwMessage.g:165:2: ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) )
            int alt3=3;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_ID) ) {
                int LA3_1 = input.LA(2);

                if ( (synpred2_InternalLwMessage()) ) {
                    alt3=1;
                }
                else if ( (synpred3_InternalLwMessage()) ) {
                    alt3=2;
                }
                else if ( (synpred4_InternalLwMessage()) ) {
                    alt3=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalLwMessage.g:165:3: ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) )
                    {
                    // InternalLwMessage.g:165:3: ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) )
                    // InternalLwMessage.g:165:4: ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID )
                    {
                    // InternalLwMessage.g:171:1: (lv_name_0_0= RULE_ID )
                    // InternalLwMessage.g:172:3: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_name_0_0, grammarAccess.getRequestMessageAccess().getNameIDTerminalRuleCall_0_0_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getRequestMessageRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"name",
                              		lv_name_0_0, 
                              		"org.eclipse.papyrus.uml.alf.Common.ID");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:189:6: ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) )
                    {
                    // InternalLwMessage.g:189:6: ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) )
                    // InternalLwMessage.g:189:7: ( ( RULE_ID ) )=> (otherlv_1= RULE_ID )
                    {
                    // InternalLwMessage.g:195:1: (otherlv_1= RULE_ID )
                    // InternalLwMessage.g:196:3: otherlv_1= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getRequestMessageRule());
                      	        }
                              
                    }
                    otherlv_1=(Token)match(input,RULE_ID,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_1, grammarAccess.getRequestMessageAccess().getSignalSignalCrossReference_0_1_0()); 
                      	
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalLwMessage.g:208:6: ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) )
                    {
                    // InternalLwMessage.g:208:6: ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) )
                    // InternalLwMessage.g:208:7: ( ( RULE_ID ) )=> (otherlv_2= RULE_ID )
                    {
                    // InternalLwMessage.g:214:1: (otherlv_2= RULE_ID )
                    // InternalLwMessage.g:215:3: otherlv_2= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getRequestMessageRule());
                      	        }
                              
                    }
                    otherlv_2=(Token)match(input,RULE_ID,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_2, grammarAccess.getRequestMessageAccess().getOperationOperationCrossReference_0_2_0()); 
                      	
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalLwMessage.g:226:3: (otherlv_3= '(' (this_MessageRequestArguments_4= ruleMessageRequestArguments[$current] )? otherlv_5= ')' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==18) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalLwMessage.g:226:5: otherlv_3= '(' (this_MessageRequestArguments_4= ruleMessageRequestArguments[$current] )? otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,18,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getRequestMessageAccess().getLeftParenthesisKeyword_1_0());
                          
                    }
                    // InternalLwMessage.g:230:1: (this_MessageRequestArguments_4= ruleMessageRequestArguments[$current] )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>=RULE_ID && LA4_0<=RULE_NEG_INT)||LA4_0==20||LA4_0==22||(LA4_0>=25 && LA4_0<=27)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalLwMessage.g:231:5: this_MessageRequestArguments_4= ruleMessageRequestArguments[$current]
                            {
                            if ( state.backtracking==0 ) {
                               
                              		if (current==null) {
                              			current = createModelElement(grammarAccess.getRequestMessageRule());
                              		}
                                      newCompositeNode(grammarAccess.getRequestMessageAccess().getMessageRequestArgumentsParserRuleCall_1_1()); 
                                  
                            }
                            pushFollow(FOLLOW_5);
                            this_MessageRequestArguments_4=ruleMessageRequestArguments(current);

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {
                               
                                      current = this_MessageRequestArguments_4; 
                                      afterParserOrEnumRuleCall();
                                  
                            }

                            }
                            break;

                    }

                    otherlv_5=(Token)match(input,19,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getRequestMessageAccess().getRightParenthesisKeyword_1_2());
                          
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequestMessage"


    // $ANTLR start "entryRuleAnyMessage"
    // InternalLwMessage.g:254:1: entryRuleAnyMessage returns [EObject current=null] : iv_ruleAnyMessage= ruleAnyMessage EOF ;
    public final EObject entryRuleAnyMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnyMessage = null;


        try {
            // InternalLwMessage.g:255:2: (iv_ruleAnyMessage= ruleAnyMessage EOF )
            // InternalLwMessage.g:256:2: iv_ruleAnyMessage= ruleAnyMessage EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAnyMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnyMessage=ruleAnyMessage();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnyMessage; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnyMessage"


    // $ANTLR start "ruleAnyMessage"
    // InternalLwMessage.g:263:1: ruleAnyMessage returns [EObject current=null] : ( () otherlv_1= '*' ) ;
    public final EObject ruleAnyMessage() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:266:28: ( ( () otherlv_1= '*' ) )
            // InternalLwMessage.g:267:1: ( () otherlv_1= '*' )
            {
            // InternalLwMessage.g:267:1: ( () otherlv_1= '*' )
            // InternalLwMessage.g:267:2: () otherlv_1= '*'
            {
            // InternalLwMessage.g:267:2: ()
            // InternalLwMessage.g:268:5: 
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getAnyMessageAccess().getAnyMessageAction_0(),
                          current);
                  
            }

            }

            otherlv_1=(Token)match(input,20,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getAnyMessageAccess().getAsteriskKeyword_1());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnyMessage"


    // $ANTLR start "ruleMessageRequestArguments"
    // InternalLwMessage.g:286:1: ruleMessageRequestArguments[EObject in_current] returns [EObject current=in_current] : ( ( ( (lv_arguments_0_0= ruleMessageRequestArgument ) ) (otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) ) )* ) | ( ( (lv_arguments_3_0= ruleMessageRequestArgumentWithName ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) ) )* ) ) ;
    public final EObject ruleMessageRequestArguments(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_arguments_0_0 = null;

        EObject lv_arguments_2_0 = null;

        EObject lv_arguments_3_0 = null;

        EObject lv_arguments_5_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:289:28: ( ( ( ( (lv_arguments_0_0= ruleMessageRequestArgument ) ) (otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) ) )* ) | ( ( (lv_arguments_3_0= ruleMessageRequestArgumentWithName ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) ) )* ) ) )
            // InternalLwMessage.g:290:1: ( ( ( (lv_arguments_0_0= ruleMessageRequestArgument ) ) (otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) ) )* ) | ( ( (lv_arguments_3_0= ruleMessageRequestArgumentWithName ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) ) )* ) )
            {
            // InternalLwMessage.g:290:1: ( ( ( (lv_arguments_0_0= ruleMessageRequestArgument ) ) (otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) ) )* ) | ( ( (lv_arguments_3_0= ruleMessageRequestArgumentWithName ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) ) )* ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=RULE_INT && LA8_0<=RULE_NEG_INT)||LA8_0==20||LA8_0==22||(LA8_0>=25 && LA8_0<=27)) ) {
                alt8=1;
            }
            else if ( (LA8_0==RULE_ID) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalLwMessage.g:290:2: ( ( (lv_arguments_0_0= ruleMessageRequestArgument ) ) (otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) ) )* )
                    {
                    // InternalLwMessage.g:290:2: ( ( (lv_arguments_0_0= ruleMessageRequestArgument ) ) (otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) ) )* )
                    // InternalLwMessage.g:290:3: ( (lv_arguments_0_0= ruleMessageRequestArgument ) ) (otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) ) )*
                    {
                    // InternalLwMessage.g:290:3: ( (lv_arguments_0_0= ruleMessageRequestArgument ) )
                    // InternalLwMessage.g:291:1: (lv_arguments_0_0= ruleMessageRequestArgument )
                    {
                    // InternalLwMessage.g:291:1: (lv_arguments_0_0= ruleMessageRequestArgument )
                    // InternalLwMessage.g:292:3: lv_arguments_0_0= ruleMessageRequestArgument
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentParserRuleCall_0_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_6);
                    lv_arguments_0_0=ruleMessageRequestArgument();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getMessageRequestArgumentsRule());
                      	        }
                             		add(
                             			current, 
                             			"arguments",
                              		lv_arguments_0_0, 
                              		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.MessageRequestArgument");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // InternalLwMessage.g:308:2: (otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) ) )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==21) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // InternalLwMessage.g:308:4: otherlv_1= ',' ( (lv_arguments_2_0= ruleMessageRequestArgument ) )
                    	    {
                    	    otherlv_1=(Token)match(input,21,FOLLOW_7); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_1, grammarAccess.getMessageRequestArgumentsAccess().getCommaKeyword_0_1_0());
                    	          
                    	    }
                    	    // InternalLwMessage.g:312:1: ( (lv_arguments_2_0= ruleMessageRequestArgument ) )
                    	    // InternalLwMessage.g:313:1: (lv_arguments_2_0= ruleMessageRequestArgument )
                    	    {
                    	    // InternalLwMessage.g:313:1: (lv_arguments_2_0= ruleMessageRequestArgument )
                    	    // InternalLwMessage.g:314:3: lv_arguments_2_0= ruleMessageRequestArgument
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentParserRuleCall_0_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_6);
                    	    lv_arguments_2_0=ruleMessageRequestArgument();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getMessageRequestArgumentsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"arguments",
                    	              		lv_arguments_2_0, 
                    	              		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.MessageRequestArgument");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:331:6: ( ( (lv_arguments_3_0= ruleMessageRequestArgumentWithName ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) ) )* )
                    {
                    // InternalLwMessage.g:331:6: ( ( (lv_arguments_3_0= ruleMessageRequestArgumentWithName ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) ) )* )
                    // InternalLwMessage.g:331:7: ( (lv_arguments_3_0= ruleMessageRequestArgumentWithName ) ) (otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) ) )*
                    {
                    // InternalLwMessage.g:331:7: ( (lv_arguments_3_0= ruleMessageRequestArgumentWithName ) )
                    // InternalLwMessage.g:332:1: (lv_arguments_3_0= ruleMessageRequestArgumentWithName )
                    {
                    // InternalLwMessage.g:332:1: (lv_arguments_3_0= ruleMessageRequestArgumentWithName )
                    // InternalLwMessage.g:333:3: lv_arguments_3_0= ruleMessageRequestArgumentWithName
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentWithNameParserRuleCall_1_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_6);
                    lv_arguments_3_0=ruleMessageRequestArgumentWithName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getMessageRequestArgumentsRule());
                      	        }
                             		add(
                             			current, 
                             			"arguments",
                              		lv_arguments_3_0, 
                              		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.MessageRequestArgumentWithName");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // InternalLwMessage.g:349:2: (otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) ) )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==21) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // InternalLwMessage.g:349:4: otherlv_4= ',' ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) )
                    	    {
                    	    otherlv_4=(Token)match(input,21,FOLLOW_8); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_4, grammarAccess.getMessageRequestArgumentsAccess().getCommaKeyword_1_1_0());
                    	          
                    	    }
                    	    // InternalLwMessage.g:353:1: ( (lv_arguments_5_0= ruleMessageRequestArgumentWithName ) )
                    	    // InternalLwMessage.g:354:1: (lv_arguments_5_0= ruleMessageRequestArgumentWithName )
                    	    {
                    	    // InternalLwMessage.g:354:1: (lv_arguments_5_0= ruleMessageRequestArgumentWithName )
                    	    // InternalLwMessage.g:355:3: lv_arguments_5_0= ruleMessageRequestArgumentWithName
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getMessageRequestArgumentsAccess().getArgumentsMessageRequestArgumentWithNameParserRuleCall_1_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_6);
                    	    lv_arguments_5_0=ruleMessageRequestArgumentWithName();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getMessageRequestArgumentsRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"arguments",
                    	              		lv_arguments_5_0, 
                    	              		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.MessageRequestArgumentWithName");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessageRequestArguments"


    // $ANTLR start "entryRuleMessageRequestArgument"
    // InternalLwMessage.g:379:1: entryRuleMessageRequestArgument returns [EObject current=null] : iv_ruleMessageRequestArgument= ruleMessageRequestArgument EOF ;
    public final EObject entryRuleMessageRequestArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageRequestArgument = null;


        try {
            // InternalLwMessage.g:380:2: (iv_ruleMessageRequestArgument= ruleMessageRequestArgument EOF )
            // InternalLwMessage.g:381:2: iv_ruleMessageRequestArgument= ruleMessageRequestArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMessageRequestArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMessageRequestArgument=ruleMessageRequestArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMessageRequestArgument; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessageRequestArgument"


    // $ANTLR start "ruleMessageRequestArgument"
    // InternalLwMessage.g:388:1: ruleMessageRequestArgument returns [EObject current=null] : ( ( () ( ( '-' )=>otherlv_1= '-' ) ) | this_MessageRequestValue_2= ruleMessageRequestValue[$current] ) ;
    public final EObject ruleMessageRequestArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject this_MessageRequestValue_2 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:391:28: ( ( ( () ( ( '-' )=>otherlv_1= '-' ) ) | this_MessageRequestValue_2= ruleMessageRequestValue[$current] ) )
            // InternalLwMessage.g:392:1: ( ( () ( ( '-' )=>otherlv_1= '-' ) ) | this_MessageRequestValue_2= ruleMessageRequestValue[$current] )
            {
            // InternalLwMessage.g:392:1: ( ( () ( ( '-' )=>otherlv_1= '-' ) ) | this_MessageRequestValue_2= ruleMessageRequestValue[$current] )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==22) ) {
                alt9=1;
            }
            else if ( ((LA9_0>=RULE_INT && LA9_0<=RULE_NEG_INT)||LA9_0==20||(LA9_0>=25 && LA9_0<=27)) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalLwMessage.g:392:2: ( () ( ( '-' )=>otherlv_1= '-' ) )
                    {
                    // InternalLwMessage.g:392:2: ( () ( ( '-' )=>otherlv_1= '-' ) )
                    // InternalLwMessage.g:392:3: () ( ( '-' )=>otherlv_1= '-' )
                    {
                    // InternalLwMessage.g:392:3: ()
                    // InternalLwMessage.g:393:5: 
                    {
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElement(
                                  grammarAccess.getMessageRequestArgumentAccess().getWildcardMessageArgumentAction_0_0(),
                                  current);
                          
                    }

                    }

                    // InternalLwMessage.g:398:2: ( ( '-' )=>otherlv_1= '-' )
                    // InternalLwMessage.g:398:3: ( '-' )=>otherlv_1= '-'
                    {
                    otherlv_1=(Token)match(input,22,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getMessageRequestArgumentAccess().getHyphenMinusKeyword_0_1());
                          
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:405:5: this_MessageRequestValue_2= ruleMessageRequestValue[$current]
                    {
                    if ( state.backtracking==0 ) {
                       
                      		if (current==null) {
                      			current = createModelElement(grammarAccess.getMessageRequestArgumentRule());
                      		}
                              newCompositeNode(grammarAccess.getMessageRequestArgumentAccess().getMessageRequestValueParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_MessageRequestValue_2=ruleMessageRequestValue(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_MessageRequestValue_2; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessageRequestArgument"


    // $ANTLR start "entryRuleMessageRequestArgumentWithName"
    // InternalLwMessage.g:424:1: entryRuleMessageRequestArgumentWithName returns [EObject current=null] : iv_ruleMessageRequestArgumentWithName= ruleMessageRequestArgumentWithName EOF ;
    public final EObject entryRuleMessageRequestArgumentWithName() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageRequestArgumentWithName = null;


        try {
            // InternalLwMessage.g:425:2: (iv_ruleMessageRequestArgumentWithName= ruleMessageRequestArgumentWithName EOF )
            // InternalLwMessage.g:426:2: iv_ruleMessageRequestArgumentWithName= ruleMessageRequestArgumentWithName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMessageRequestArgumentWithNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMessageRequestArgumentWithName=ruleMessageRequestArgumentWithName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMessageRequestArgumentWithName; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessageRequestArgumentWithName"


    // $ANTLR start "ruleMessageRequestArgumentWithName"
    // InternalLwMessage.g:433:1: ruleMessageRequestArgumentWithName returns [EObject current=null] : this_MessageRequestNameAndValue_0= ruleMessageRequestNameAndValue ;
    public final EObject ruleMessageRequestArgumentWithName() throws RecognitionException {
        EObject current = null;

        EObject this_MessageRequestNameAndValue_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:436:28: (this_MessageRequestNameAndValue_0= ruleMessageRequestNameAndValue )
            // InternalLwMessage.g:438:5: this_MessageRequestNameAndValue_0= ruleMessageRequestNameAndValue
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getMessageRequestArgumentWithNameAccess().getMessageRequestNameAndValueParserRuleCall()); 
                  
            }
            pushFollow(FOLLOW_2);
            this_MessageRequestNameAndValue_0=ruleMessageRequestNameAndValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_MessageRequestNameAndValue_0; 
                      afterParserOrEnumRuleCall();
                  
            }

            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessageRequestArgumentWithName"


    // $ANTLR start "entryRuleMessageRequestNameAndValue"
    // InternalLwMessage.g:454:1: entryRuleMessageRequestNameAndValue returns [EObject current=null] : iv_ruleMessageRequestNameAndValue= ruleMessageRequestNameAndValue EOF ;
    public final EObject entryRuleMessageRequestNameAndValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageRequestNameAndValue = null;


        try {
            // InternalLwMessage.g:455:2: (iv_ruleMessageRequestNameAndValue= ruleMessageRequestNameAndValue EOF )
            // InternalLwMessage.g:456:2: iv_ruleMessageRequestNameAndValue= ruleMessageRequestNameAndValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMessageRequestNameAndValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMessageRequestNameAndValue=ruleMessageRequestNameAndValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMessageRequestNameAndValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessageRequestNameAndValue"


    // $ANTLR start "ruleMessageRequestNameAndValue"
    // InternalLwMessage.g:463:1: ruleMessageRequestNameAndValue returns [EObject current=null] : ( ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) otherlv_3= '=' this_MessageRequestValue_4= ruleMessageRequestValue[$current] ) ;
    public final EObject ruleMessageRequestNameAndValue() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject this_MessageRequestValue_4 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:466:28: ( ( ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) otherlv_3= '=' this_MessageRequestValue_4= ruleMessageRequestValue[$current] ) )
            // InternalLwMessage.g:467:1: ( ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) otherlv_3= '=' this_MessageRequestValue_4= ruleMessageRequestValue[$current] )
            {
            // InternalLwMessage.g:467:1: ( ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) otherlv_3= '=' this_MessageRequestValue_4= ruleMessageRequestValue[$current] )
            // InternalLwMessage.g:467:2: ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) otherlv_3= '=' this_MessageRequestValue_4= ruleMessageRequestValue[$current]
            {
            // InternalLwMessage.g:467:2: ( ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) )
            int alt10=3;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_ID) ) {
                int LA10_1 = input.LA(2);

                if ( (synpred6_InternalLwMessage()) ) {
                    alt10=1;
                }
                else if ( (synpred7_InternalLwMessage()) ) {
                    alt10=2;
                }
                else if ( (synpred8_InternalLwMessage()) ) {
                    alt10=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalLwMessage.g:467:3: ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) )
                    {
                    // InternalLwMessage.g:467:3: ( ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID ) )
                    // InternalLwMessage.g:467:4: ( ( RULE_ID ) )=> (lv_name_0_0= RULE_ID )
                    {
                    // InternalLwMessage.g:473:1: (lv_name_0_0= RULE_ID )
                    // InternalLwMessage.g:474:3: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_name_0_0, grammarAccess.getMessageRequestNameAndValueAccess().getNameIDTerminalRuleCall_0_0_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getMessageRequestNameAndValueRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"name",
                              		lv_name_0_0, 
                              		"org.eclipse.papyrus.uml.alf.Common.ID");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:491:6: ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) )
                    {
                    // InternalLwMessage.g:491:6: ( ( ( RULE_ID ) )=> (otherlv_1= RULE_ID ) )
                    // InternalLwMessage.g:491:7: ( ( RULE_ID ) )=> (otherlv_1= RULE_ID )
                    {
                    // InternalLwMessage.g:497:1: (otherlv_1= RULE_ID )
                    // InternalLwMessage.g:498:3: otherlv_1= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getMessageRequestNameAndValueRule());
                      	        }
                              
                    }
                    otherlv_1=(Token)match(input,RULE_ID,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_1, grammarAccess.getMessageRequestNameAndValueAccess().getPropertyPropertyCrossReference_0_1_0()); 
                      	
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalLwMessage.g:510:6: ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) )
                    {
                    // InternalLwMessage.g:510:6: ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) )
                    // InternalLwMessage.g:510:7: ( ( RULE_ID ) )=> (otherlv_2= RULE_ID )
                    {
                    // InternalLwMessage.g:516:1: (otherlv_2= RULE_ID )
                    // InternalLwMessage.g:517:3: otherlv_2= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getMessageRequestNameAndValueRule());
                      	        }
                              
                    }
                    otherlv_2=(Token)match(input,RULE_ID,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_2, grammarAccess.getMessageRequestNameAndValueAccess().getParameterParameterCrossReference_0_2_0()); 
                      	
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,23,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getMessageRequestNameAndValueAccess().getEqualsSignKeyword_1());
                  
            }
            if ( state.backtracking==0 ) {
               
              		if (current==null) {
              			current = createModelElement(grammarAccess.getMessageRequestNameAndValueRule());
              		}
                      newCompositeNode(grammarAccess.getMessageRequestNameAndValueAccess().getMessageRequestValueParserRuleCall_2()); 
                  
            }
            pushFollow(FOLLOW_2);
            this_MessageRequestValue_4=ruleMessageRequestValue(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_MessageRequestValue_4; 
                      afterParserOrEnumRuleCall();
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessageRequestNameAndValue"


    // $ANTLR start "ruleMessageRequestValue"
    // InternalLwMessage.g:553:1: ruleMessageRequestValue[EObject in_current] returns [EObject current=in_current] : ( (lv_value_0_0= ruleValue ) ) ;
    public final EObject ruleMessageRequestValue(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        EObject lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:556:28: ( ( (lv_value_0_0= ruleValue ) ) )
            // InternalLwMessage.g:557:1: ( (lv_value_0_0= ruleValue ) )
            {
            // InternalLwMessage.g:557:1: ( (lv_value_0_0= ruleValue ) )
            // InternalLwMessage.g:558:1: (lv_value_0_0= ruleValue )
            {
            // InternalLwMessage.g:558:1: (lv_value_0_0= ruleValue )
            // InternalLwMessage.g:559:3: lv_value_0_0= ruleValue
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getMessageRequestValueAccess().getValueValueParserRuleCall_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getMessageRequestValueRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.Value");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessageRequestValue"


    // $ANTLR start "entryRuleReplyMessage"
    // InternalLwMessage.g:583:1: entryRuleReplyMessage returns [EObject current=null] : iv_ruleReplyMessage= ruleReplyMessage EOF ;
    public final EObject entryRuleReplyMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReplyMessage = null;


        try {
            // InternalLwMessage.g:584:2: (iv_ruleReplyMessage= ruleReplyMessage EOF )
            // InternalLwMessage.g:585:2: iv_ruleReplyMessage= ruleReplyMessage EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getReplyMessageRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleReplyMessage=ruleReplyMessage();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleReplyMessage; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReplyMessage"


    // $ANTLR start "ruleReplyMessage"
    // InternalLwMessage.g:592:1: ruleReplyMessage returns [EObject current=null] : ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] )? ( ( ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current] )? otherlv_5= ')' )? ( (lv_value_6_0= ruleOutputValue ) )? ) ;
    public final EObject ruleReplyMessage() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_AssignmentTarget_0 = null;

        EObject this_MessageReplyOutputs_4 = null;

        EObject lv_value_6_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:595:28: ( ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] )? ( ( ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current] )? otherlv_5= ')' )? ( (lv_value_6_0= ruleOutputValue ) )? ) )
            // InternalLwMessage.g:596:1: ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] )? ( ( ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current] )? otherlv_5= ')' )? ( (lv_value_6_0= ruleOutputValue ) )? )
            {
            // InternalLwMessage.g:596:1: ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] )? ( ( ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current] )? otherlv_5= ')' )? ( (lv_value_6_0= ruleOutputValue ) )? )
            // InternalLwMessage.g:596:2: ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] )? ( ( ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) ) (otherlv_3= '(' (this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current] )? otherlv_5= ')' )? ( (lv_value_6_0= ruleOutputValue ) )?
            {
            // InternalLwMessage.g:596:2: ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_ID) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==23) && (synpred9_InternalLwMessage())) {
                    alt11=1;
                }
            }
            switch (alt11) {
                case 1 :
                    // InternalLwMessage.g:596:3: ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current]
                    {
                    if ( state.backtracking==0 ) {
                       
                      		if (current==null) {
                      			current = createModelElement(grammarAccess.getReplyMessageRule());
                      		}
                              newCompositeNode(grammarAccess.getReplyMessageAccess().getAssignmentTargetParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_10);
                    this_AssignmentTarget_0=ruleAssignmentTarget(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_AssignmentTarget_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }

            // InternalLwMessage.g:613:3: ( ( ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID ) ) | ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_ID) ) {
                int LA12_1 = input.LA(2);

                if ( (synpred10_InternalLwMessage()) ) {
                    alt12=1;
                }
                else if ( (synpred11_InternalLwMessage()) ) {
                    alt12=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // InternalLwMessage.g:613:4: ( ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID ) )
                    {
                    // InternalLwMessage.g:613:4: ( ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID ) )
                    // InternalLwMessage.g:613:5: ( ( RULE_ID ) )=> (lv_name_1_0= RULE_ID )
                    {
                    // InternalLwMessage.g:619:1: (lv_name_1_0= RULE_ID )
                    // InternalLwMessage.g:620:3: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(lv_name_1_0, grammarAccess.getReplyMessageAccess().getNameIDTerminalRuleCall_1_0_0()); 
                      		
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getReplyMessageRule());
                      	        }
                             		setWithLastConsumed(
                             			current, 
                             			"name",
                              		lv_name_1_0, 
                              		"org.eclipse.papyrus.uml.alf.Common.ID");
                      	    
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:637:6: ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) )
                    {
                    // InternalLwMessage.g:637:6: ( ( ( RULE_ID ) )=> (otherlv_2= RULE_ID ) )
                    // InternalLwMessage.g:637:7: ( ( RULE_ID ) )=> (otherlv_2= RULE_ID )
                    {
                    // InternalLwMessage.g:643:1: (otherlv_2= RULE_ID )
                    // InternalLwMessage.g:644:3: otherlv_2= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getReplyMessageRule());
                      	        }
                              
                    }
                    otherlv_2=(Token)match(input,RULE_ID,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_2, grammarAccess.getReplyMessageAccess().getOperationOperationCrossReference_1_1_0()); 
                      	
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalLwMessage.g:655:3: (otherlv_3= '(' (this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current] )? otherlv_5= ')' )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==18) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalLwMessage.g:655:5: otherlv_3= '(' (this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current] )? otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,18,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getReplyMessageAccess().getLeftParenthesisKeyword_2_0());
                          
                    }
                    // InternalLwMessage.g:659:1: (this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current] )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==RULE_ID) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalLwMessage.g:660:5: this_MessageReplyOutputs_4= ruleMessageReplyOutputs[$current]
                            {
                            if ( state.backtracking==0 ) {
                               
                              		if (current==null) {
                              			current = createModelElement(grammarAccess.getReplyMessageRule());
                              		}
                                      newCompositeNode(grammarAccess.getReplyMessageAccess().getMessageReplyOutputsParserRuleCall_2_1()); 
                                  
                            }
                            pushFollow(FOLLOW_5);
                            this_MessageReplyOutputs_4=ruleMessageReplyOutputs(current);

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {
                               
                                      current = this_MessageReplyOutputs_4; 
                                      afterParserOrEnumRuleCall();
                                  
                            }

                            }
                            break;

                    }

                    otherlv_5=(Token)match(input,19,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getReplyMessageAccess().getRightParenthesisKeyword_2_2());
                          
                    }

                    }
                    break;

            }

            // InternalLwMessage.g:675:3: ( (lv_value_6_0= ruleOutputValue ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==24) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalLwMessage.g:676:1: (lv_value_6_0= ruleOutputValue )
                    {
                    // InternalLwMessage.g:676:1: (lv_value_6_0= ruleOutputValue )
                    // InternalLwMessage.g:677:3: lv_value_6_0= ruleOutputValue
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getReplyMessageAccess().getValueOutputValueParserRuleCall_3_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_6_0=ruleOutputValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getReplyMessageRule());
                      	        }
                             		set(
                             			current, 
                             			"value",
                              		lv_value_6_0, 
                              		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.OutputValue");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReplyMessage"


    // $ANTLR start "ruleAssignmentTarget"
    // InternalLwMessage.g:702:1: ruleAssignmentTarget[EObject in_current] returns [EObject current=in_current] : ( ( ( ( RULE_ID ) )=> (otherlv_0= RULE_ID ) ) otherlv_1= '=' ) ;
    public final EObject ruleAssignmentTarget(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:705:28: ( ( ( ( ( RULE_ID ) )=> (otherlv_0= RULE_ID ) ) otherlv_1= '=' ) )
            // InternalLwMessage.g:706:1: ( ( ( ( RULE_ID ) )=> (otherlv_0= RULE_ID ) ) otherlv_1= '=' )
            {
            // InternalLwMessage.g:706:1: ( ( ( ( RULE_ID ) )=> (otherlv_0= RULE_ID ) ) otherlv_1= '=' )
            // InternalLwMessage.g:706:2: ( ( ( RULE_ID ) )=> (otherlv_0= RULE_ID ) ) otherlv_1= '='
            {
            // InternalLwMessage.g:706:2: ( ( ( RULE_ID ) )=> (otherlv_0= RULE_ID ) )
            // InternalLwMessage.g:706:3: ( ( RULE_ID ) )=> (otherlv_0= RULE_ID )
            {
            // InternalLwMessage.g:712:1: (otherlv_0= RULE_ID )
            // InternalLwMessage.g:713:3: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getAssignmentTargetRule());
              	        }
                      
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_0, grammarAccess.getAssignmentTargetAccess().getTargetConnectableElementCrossReference_0_0()); 
              	
            }

            }


            }

            otherlv_1=(Token)match(input,23,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getAssignmentTargetAccess().getEqualsSignKeyword_1());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAssignmentTarget"


    // $ANTLR start "ruleMessageReplyOutputs"
    // InternalLwMessage.g:737:1: ruleMessageReplyOutputs[EObject in_current] returns [EObject current=in_current] : ( ( (lv_outputs_0_0= ruleMessageReplyOutput ) ) (otherlv_1= ',' ( (lv_outputs_2_0= ruleMessageReplyOutput ) ) )* ) ;
    public final EObject ruleMessageReplyOutputs(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_1=null;
        EObject lv_outputs_0_0 = null;

        EObject lv_outputs_2_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:740:28: ( ( ( (lv_outputs_0_0= ruleMessageReplyOutput ) ) (otherlv_1= ',' ( (lv_outputs_2_0= ruleMessageReplyOutput ) ) )* ) )
            // InternalLwMessage.g:741:1: ( ( (lv_outputs_0_0= ruleMessageReplyOutput ) ) (otherlv_1= ',' ( (lv_outputs_2_0= ruleMessageReplyOutput ) ) )* )
            {
            // InternalLwMessage.g:741:1: ( ( (lv_outputs_0_0= ruleMessageReplyOutput ) ) (otherlv_1= ',' ( (lv_outputs_2_0= ruleMessageReplyOutput ) ) )* )
            // InternalLwMessage.g:741:2: ( (lv_outputs_0_0= ruleMessageReplyOutput ) ) (otherlv_1= ',' ( (lv_outputs_2_0= ruleMessageReplyOutput ) ) )*
            {
            // InternalLwMessage.g:741:2: ( (lv_outputs_0_0= ruleMessageReplyOutput ) )
            // InternalLwMessage.g:742:1: (lv_outputs_0_0= ruleMessageReplyOutput )
            {
            // InternalLwMessage.g:742:1: (lv_outputs_0_0= ruleMessageReplyOutput )
            // InternalLwMessage.g:743:3: lv_outputs_0_0= ruleMessageReplyOutput
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getMessageReplyOutputsAccess().getOutputsMessageReplyOutputParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_6);
            lv_outputs_0_0=ruleMessageReplyOutput();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getMessageReplyOutputsRule());
              	        }
                     		add(
                     			current, 
                     			"outputs",
                      		lv_outputs_0_0, 
                      		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.MessageReplyOutput");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // InternalLwMessage.g:759:2: (otherlv_1= ',' ( (lv_outputs_2_0= ruleMessageReplyOutput ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==21) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalLwMessage.g:759:4: otherlv_1= ',' ( (lv_outputs_2_0= ruleMessageReplyOutput ) )
            	    {
            	    otherlv_1=(Token)match(input,21,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_1, grammarAccess.getMessageReplyOutputsAccess().getCommaKeyword_1_0());
            	          
            	    }
            	    // InternalLwMessage.g:763:1: ( (lv_outputs_2_0= ruleMessageReplyOutput ) )
            	    // InternalLwMessage.g:764:1: (lv_outputs_2_0= ruleMessageReplyOutput )
            	    {
            	    // InternalLwMessage.g:764:1: (lv_outputs_2_0= ruleMessageReplyOutput )
            	    // InternalLwMessage.g:765:3: lv_outputs_2_0= ruleMessageReplyOutput
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getMessageReplyOutputsAccess().getOutputsMessageReplyOutputParserRuleCall_1_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_outputs_2_0=ruleMessageReplyOutput();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getMessageReplyOutputsRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"outputs",
            	              		lv_outputs_2_0, 
            	              		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.MessageReplyOutput");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessageReplyOutputs"


    // $ANTLR start "entryRuleMessageReplyOutput"
    // InternalLwMessage.g:789:1: entryRuleMessageReplyOutput returns [EObject current=null] : iv_ruleMessageReplyOutput= ruleMessageReplyOutput EOF ;
    public final EObject entryRuleMessageReplyOutput() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageReplyOutput = null;


        try {
            // InternalLwMessage.g:790:2: (iv_ruleMessageReplyOutput= ruleMessageReplyOutput EOF )
            // InternalLwMessage.g:791:2: iv_ruleMessageReplyOutput= ruleMessageReplyOutput EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMessageReplyOutputRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMessageReplyOutput=ruleMessageReplyOutput();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMessageReplyOutput; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessageReplyOutput"


    // $ANTLR start "ruleMessageReplyOutput"
    // InternalLwMessage.g:798:1: ruleMessageReplyOutput returns [EObject current=null] : ( ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] ) ( (otherlv_1= RULE_ID ) ) ( (lv_value_2_0= ruleOutputValue ) )? ) | ( ( (otherlv_3= RULE_ID ) ) ( (lv_value_4_0= ruleOutputValue ) ) ) ) ;
    public final EObject ruleMessageReplyOutput() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject this_AssignmentTarget_0 = null;

        EObject lv_value_2_0 = null;

        EObject lv_value_4_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:801:28: ( ( ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] ) ( (otherlv_1= RULE_ID ) ) ( (lv_value_2_0= ruleOutputValue ) )? ) | ( ( (otherlv_3= RULE_ID ) ) ( (lv_value_4_0= ruleOutputValue ) ) ) ) )
            // InternalLwMessage.g:802:1: ( ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] ) ( (otherlv_1= RULE_ID ) ) ( (lv_value_2_0= ruleOutputValue ) )? ) | ( ( (otherlv_3= RULE_ID ) ) ( (lv_value_4_0= ruleOutputValue ) ) ) )
            {
            // InternalLwMessage.g:802:1: ( ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] ) ( (otherlv_1= RULE_ID ) ) ( (lv_value_2_0= ruleOutputValue ) )? ) | ( ( (otherlv_3= RULE_ID ) ) ( (lv_value_4_0= ruleOutputValue ) ) ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_ID) ) {
                int LA18_1 = input.LA(2);

                if ( (LA18_1==23) && (synpred13_InternalLwMessage())) {
                    alt18=1;
                }
                else if ( (LA18_1==24) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalLwMessage.g:802:2: ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] ) ( (otherlv_1= RULE_ID ) ) ( (lv_value_2_0= ruleOutputValue ) )? )
                    {
                    // InternalLwMessage.g:802:2: ( ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] ) ( (otherlv_1= RULE_ID ) ) ( (lv_value_2_0= ruleOutputValue ) )? )
                    // InternalLwMessage.g:802:3: ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] ) ( (otherlv_1= RULE_ID ) ) ( (lv_value_2_0= ruleOutputValue ) )?
                    {
                    // InternalLwMessage.g:802:3: ( ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current] )
                    // InternalLwMessage.g:802:4: ( ( RULE_ID ) )=>this_AssignmentTarget_0= ruleAssignmentTarget[$current]
                    {
                    if ( state.backtracking==0 ) {
                       
                      		if (current==null) {
                      			current = createModelElement(grammarAccess.getMessageReplyOutputRule());
                      		}
                              newCompositeNode(grammarAccess.getMessageReplyOutputAccess().getAssignmentTargetParserRuleCall_0_0()); 
                          
                    }
                    pushFollow(FOLLOW_10);
                    this_AssignmentTarget_0=ruleAssignmentTarget(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_AssignmentTarget_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }

                    // InternalLwMessage.g:819:2: ( (otherlv_1= RULE_ID ) )
                    // InternalLwMessage.g:820:1: (otherlv_1= RULE_ID )
                    {
                    // InternalLwMessage.g:820:1: (otherlv_1= RULE_ID )
                    // InternalLwMessage.g:821:3: otherlv_1= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getMessageReplyOutputRule());
                      	        }
                              
                    }
                    otherlv_1=(Token)match(input,RULE_ID,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_1, grammarAccess.getMessageReplyOutputAccess().getParameterParameterCrossReference_0_1_0()); 
                      	
                    }

                    }


                    }

                    // InternalLwMessage.g:832:2: ( (lv_value_2_0= ruleOutputValue ) )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==24) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalLwMessage.g:833:1: (lv_value_2_0= ruleOutputValue )
                            {
                            // InternalLwMessage.g:833:1: (lv_value_2_0= ruleOutputValue )
                            // InternalLwMessage.g:834:3: lv_value_2_0= ruleOutputValue
                            {
                            if ( state.backtracking==0 ) {
                               
                              	        newCompositeNode(grammarAccess.getMessageReplyOutputAccess().getValueOutputValueParserRuleCall_0_2_0()); 
                              	    
                            }
                            pushFollow(FOLLOW_2);
                            lv_value_2_0=ruleOutputValue();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElementForParent(grammarAccess.getMessageReplyOutputRule());
                              	        }
                                     		set(
                                     			current, 
                                     			"value",
                                      		lv_value_2_0, 
                                      		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.OutputValue");
                              	        afterParserOrEnumRuleCall();
                              	    
                            }

                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:851:6: ( ( (otherlv_3= RULE_ID ) ) ( (lv_value_4_0= ruleOutputValue ) ) )
                    {
                    // InternalLwMessage.g:851:6: ( ( (otherlv_3= RULE_ID ) ) ( (lv_value_4_0= ruleOutputValue ) ) )
                    // InternalLwMessage.g:851:7: ( (otherlv_3= RULE_ID ) ) ( (lv_value_4_0= ruleOutputValue ) )
                    {
                    // InternalLwMessage.g:851:7: ( (otherlv_3= RULE_ID ) )
                    // InternalLwMessage.g:852:1: (otherlv_3= RULE_ID )
                    {
                    // InternalLwMessage.g:852:1: (otherlv_3= RULE_ID )
                    // InternalLwMessage.g:853:3: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getMessageReplyOutputRule());
                      	        }
                              
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_3, grammarAccess.getMessageReplyOutputAccess().getParameterParameterCrossReference_1_0_0()); 
                      	
                    }

                    }


                    }

                    // InternalLwMessage.g:864:2: ( (lv_value_4_0= ruleOutputValue ) )
                    // InternalLwMessage.g:865:1: (lv_value_4_0= ruleOutputValue )
                    {
                    // InternalLwMessage.g:865:1: (lv_value_4_0= ruleOutputValue )
                    // InternalLwMessage.g:866:3: lv_value_4_0= ruleOutputValue
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getMessageReplyOutputAccess().getValueOutputValueParserRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_4_0=ruleOutputValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getMessageReplyOutputRule());
                      	        }
                             		set(
                             			current, 
                             			"value",
                              		lv_value_4_0, 
                              		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.OutputValue");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessageReplyOutput"


    // $ANTLR start "entryRuleOutputValue"
    // InternalLwMessage.g:890:1: entryRuleOutputValue returns [EObject current=null] : iv_ruleOutputValue= ruleOutputValue EOF ;
    public final EObject entryRuleOutputValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOutputValue = null;


        try {
            // InternalLwMessage.g:891:2: (iv_ruleOutputValue= ruleOutputValue EOF )
            // InternalLwMessage.g:892:2: iv_ruleOutputValue= ruleOutputValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOutputValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOutputValue=ruleOutputValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOutputValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOutputValue"


    // $ANTLR start "ruleOutputValue"
    // InternalLwMessage.g:899:1: ruleOutputValue returns [EObject current=null] : (otherlv_0= ':' ( (lv_value_1_0= ruleValue ) ) ) ;
    public final EObject ruleOutputValue() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_value_1_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:902:28: ( (otherlv_0= ':' ( (lv_value_1_0= ruleValue ) ) ) )
            // InternalLwMessage.g:903:1: (otherlv_0= ':' ( (lv_value_1_0= ruleValue ) ) )
            {
            // InternalLwMessage.g:903:1: (otherlv_0= ':' ( (lv_value_1_0= ruleValue ) ) )
            // InternalLwMessage.g:903:3: otherlv_0= ':' ( (lv_value_1_0= ruleValue ) )
            {
            otherlv_0=(Token)match(input,24,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getOutputValueAccess().getColonKeyword_0());
                  
            }
            // InternalLwMessage.g:907:1: ( (lv_value_1_0= ruleValue ) )
            // InternalLwMessage.g:908:1: (lv_value_1_0= ruleValue )
            {
            // InternalLwMessage.g:908:1: (lv_value_1_0= ruleValue )
            // InternalLwMessage.g:909:3: lv_value_1_0= ruleValue
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getOutputValueAccess().getValueValueParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_value_1_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getOutputValueRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_1_0, 
                      		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.Value");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOutputValue"


    // $ANTLR start "entryRuleValue"
    // InternalLwMessage.g:933:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalLwMessage.g:934:2: (iv_ruleValue= ruleValue EOF )
            // InternalLwMessage.g:935:2: iv_ruleValue= ruleValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleValue=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // InternalLwMessage.g:942:1: ruleValue returns [EObject current=null] : (this_BooleanValue_0= ruleBooleanValue | ( ( ruleIntegerValue )=>this_IntegerValue_1= ruleIntegerValue ) | ( ( ruleUnlimitedNaturalValue )=>this_UnlimitedNaturalValue_2= ruleUnlimitedNaturalValue ) | ( ( ruleRealValue )=>this_RealValue_3= ruleRealValue ) | this_NullValue_4= ruleNullValue | this_StringValue_5= ruleStringValue ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        EObject this_BooleanValue_0 = null;

        EObject this_IntegerValue_1 = null;

        EObject this_UnlimitedNaturalValue_2 = null;

        EObject this_RealValue_3 = null;

        EObject this_NullValue_4 = null;

        EObject this_StringValue_5 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:945:28: ( (this_BooleanValue_0= ruleBooleanValue | ( ( ruleIntegerValue )=>this_IntegerValue_1= ruleIntegerValue ) | ( ( ruleUnlimitedNaturalValue )=>this_UnlimitedNaturalValue_2= ruleUnlimitedNaturalValue ) | ( ( ruleRealValue )=>this_RealValue_3= ruleRealValue ) | this_NullValue_4= ruleNullValue | this_StringValue_5= ruleStringValue ) )
            // InternalLwMessage.g:946:1: (this_BooleanValue_0= ruleBooleanValue | ( ( ruleIntegerValue )=>this_IntegerValue_1= ruleIntegerValue ) | ( ( ruleUnlimitedNaturalValue )=>this_UnlimitedNaturalValue_2= ruleUnlimitedNaturalValue ) | ( ( ruleRealValue )=>this_RealValue_3= ruleRealValue ) | this_NullValue_4= ruleNullValue | this_StringValue_5= ruleStringValue )
            {
            // InternalLwMessage.g:946:1: (this_BooleanValue_0= ruleBooleanValue | ( ( ruleIntegerValue )=>this_IntegerValue_1= ruleIntegerValue ) | ( ( ruleUnlimitedNaturalValue )=>this_UnlimitedNaturalValue_2= ruleUnlimitedNaturalValue ) | ( ( ruleRealValue )=>this_RealValue_3= ruleRealValue ) | this_NullValue_4= ruleNullValue | this_StringValue_5= ruleStringValue )
            int alt19=6;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0>=26 && LA19_0<=27)) ) {
                alt19=1;
            }
            else if ( (LA19_0==RULE_INT) ) {
                int LA19_2 = input.LA(2);

                if ( (synpred14_InternalLwMessage()) ) {
                    alt19=2;
                }
                else if ( (synpred15_InternalLwMessage()) ) {
                    alt19=3;
                }
                else if ( (synpred16_InternalLwMessage()) ) {
                    alt19=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 2, input);

                    throw nvae;
                }
            }
            else if ( (LA19_0==RULE_NEG_INT) && (synpred15_InternalLwMessage())) {
                alt19=3;
            }
            else if ( (LA19_0==20) && (synpred15_InternalLwMessage())) {
                alt19=3;
            }
            else if ( (LA19_0==RULE_REAL) && (synpred16_InternalLwMessage())) {
                alt19=4;
            }
            else if ( (LA19_0==25) ) {
                alt19=5;
            }
            else if ( (LA19_0==RULE_STRING) ) {
                alt19=6;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // InternalLwMessage.g:947:5: this_BooleanValue_0= ruleBooleanValue
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getValueAccess().getBooleanValueParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_BooleanValue_0=ruleBooleanValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_BooleanValue_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:956:6: ( ( ruleIntegerValue )=>this_IntegerValue_1= ruleIntegerValue )
                    {
                    // InternalLwMessage.g:956:6: ( ( ruleIntegerValue )=>this_IntegerValue_1= ruleIntegerValue )
                    // InternalLwMessage.g:956:7: ( ruleIntegerValue )=>this_IntegerValue_1= ruleIntegerValue
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getValueAccess().getIntegerValueParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_IntegerValue_1=ruleIntegerValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_IntegerValue_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalLwMessage.g:966:6: ( ( ruleUnlimitedNaturalValue )=>this_UnlimitedNaturalValue_2= ruleUnlimitedNaturalValue )
                    {
                    // InternalLwMessage.g:966:6: ( ( ruleUnlimitedNaturalValue )=>this_UnlimitedNaturalValue_2= ruleUnlimitedNaturalValue )
                    // InternalLwMessage.g:966:7: ( ruleUnlimitedNaturalValue )=>this_UnlimitedNaturalValue_2= ruleUnlimitedNaturalValue
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getValueAccess().getUnlimitedNaturalValueParserRuleCall_2()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_UnlimitedNaturalValue_2=ruleUnlimitedNaturalValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_UnlimitedNaturalValue_2; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalLwMessage.g:976:6: ( ( ruleRealValue )=>this_RealValue_3= ruleRealValue )
                    {
                    // InternalLwMessage.g:976:6: ( ( ruleRealValue )=>this_RealValue_3= ruleRealValue )
                    // InternalLwMessage.g:976:7: ( ruleRealValue )=>this_RealValue_3= ruleRealValue
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getValueAccess().getRealValueParserRuleCall_3()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_RealValue_3=ruleRealValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_RealValue_3; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalLwMessage.g:987:5: this_NullValue_4= ruleNullValue
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getValueAccess().getNullValueParserRuleCall_4()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_NullValue_4=ruleNullValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_NullValue_4; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 6 :
                    // InternalLwMessage.g:997:5: this_StringValue_5= ruleStringValue
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getValueAccess().getStringValueParserRuleCall_5()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_StringValue_5=ruleStringValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_StringValue_5; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleBooleanValue"
    // InternalLwMessage.g:1013:1: entryRuleBooleanValue returns [EObject current=null] : iv_ruleBooleanValue= ruleBooleanValue EOF ;
    public final EObject entryRuleBooleanValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBooleanValue = null;


        try {
            // InternalLwMessage.g:1014:2: (iv_ruleBooleanValue= ruleBooleanValue EOF )
            // InternalLwMessage.g:1015:2: iv_ruleBooleanValue= ruleBooleanValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBooleanValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBooleanValue=ruleBooleanValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBooleanValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBooleanValue"


    // $ANTLR start "ruleBooleanValue"
    // InternalLwMessage.g:1022:1: ruleBooleanValue returns [EObject current=null] : ( (lv_value_0_0= ruleBoolean ) ) ;
    public final EObject ruleBooleanValue() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:1025:28: ( ( (lv_value_0_0= ruleBoolean ) ) )
            // InternalLwMessage.g:1026:1: ( (lv_value_0_0= ruleBoolean ) )
            {
            // InternalLwMessage.g:1026:1: ( (lv_value_0_0= ruleBoolean ) )
            // InternalLwMessage.g:1027:1: (lv_value_0_0= ruleBoolean )
            {
            // InternalLwMessage.g:1027:1: (lv_value_0_0= ruleBoolean )
            // InternalLwMessage.g:1028:3: lv_value_0_0= ruleBoolean
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getBooleanValueAccess().getValueBooleanParserRuleCall_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleBoolean();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getBooleanValueRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.Boolean");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBooleanValue"


    // $ANTLR start "entryRuleIntegerValue"
    // InternalLwMessage.g:1052:1: entryRuleIntegerValue returns [EObject current=null] : iv_ruleIntegerValue= ruleIntegerValue EOF ;
    public final EObject entryRuleIntegerValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntegerValue = null;


        try {
            // InternalLwMessage.g:1053:2: (iv_ruleIntegerValue= ruleIntegerValue EOF )
            // InternalLwMessage.g:1054:2: iv_ruleIntegerValue= ruleIntegerValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIntegerValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIntegerValue=ruleIntegerValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIntegerValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIntegerValue"


    // $ANTLR start "ruleIntegerValue"
    // InternalLwMessage.g:1061:1: ruleIntegerValue returns [EObject current=null] : ( (lv_value_0_0= RULE_INT ) ) ;
    public final EObject ruleIntegerValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:1064:28: ( ( (lv_value_0_0= RULE_INT ) ) )
            // InternalLwMessage.g:1065:1: ( (lv_value_0_0= RULE_INT ) )
            {
            // InternalLwMessage.g:1065:1: ( (lv_value_0_0= RULE_INT ) )
            // InternalLwMessage.g:1066:1: (lv_value_0_0= RULE_INT )
            {
            // InternalLwMessage.g:1066:1: (lv_value_0_0= RULE_INT )
            // InternalLwMessage.g:1067:3: lv_value_0_0= RULE_INT
            {
            lv_value_0_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_0_0, grammarAccess.getIntegerValueAccess().getValueINTTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getIntegerValueRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.INT");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIntegerValue"


    // $ANTLR start "entryRuleUnlimitedNaturalValue"
    // InternalLwMessage.g:1091:1: entryRuleUnlimitedNaturalValue returns [EObject current=null] : iv_ruleUnlimitedNaturalValue= ruleUnlimitedNaturalValue EOF ;
    public final EObject entryRuleUnlimitedNaturalValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnlimitedNaturalValue = null;


        try {
            // InternalLwMessage.g:1092:2: (iv_ruleUnlimitedNaturalValue= ruleUnlimitedNaturalValue EOF )
            // InternalLwMessage.g:1093:2: iv_ruleUnlimitedNaturalValue= ruleUnlimitedNaturalValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnlimitedNaturalValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnlimitedNaturalValue=ruleUnlimitedNaturalValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnlimitedNaturalValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnlimitedNaturalValue"


    // $ANTLR start "ruleUnlimitedNaturalValue"
    // InternalLwMessage.g:1100:1: ruleUnlimitedNaturalValue returns [EObject current=null] : ( (lv_value_0_0= ruleUnlimitedNatural ) ) ;
    public final EObject ruleUnlimitedNaturalValue() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:1103:28: ( ( (lv_value_0_0= ruleUnlimitedNatural ) ) )
            // InternalLwMessage.g:1104:1: ( (lv_value_0_0= ruleUnlimitedNatural ) )
            {
            // InternalLwMessage.g:1104:1: ( (lv_value_0_0= ruleUnlimitedNatural ) )
            // InternalLwMessage.g:1105:1: (lv_value_0_0= ruleUnlimitedNatural )
            {
            // InternalLwMessage.g:1105:1: (lv_value_0_0= ruleUnlimitedNatural )
            // InternalLwMessage.g:1106:3: lv_value_0_0= ruleUnlimitedNatural
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getUnlimitedNaturalValueAccess().getValueUnlimitedNaturalParserRuleCall_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleUnlimitedNatural();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getUnlimitedNaturalValueRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.UnlimitedNatural");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnlimitedNaturalValue"


    // $ANTLR start "entryRuleRealValue"
    // InternalLwMessage.g:1130:1: entryRuleRealValue returns [EObject current=null] : iv_ruleRealValue= ruleRealValue EOF ;
    public final EObject entryRuleRealValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRealValue = null;


        try {
            // InternalLwMessage.g:1131:2: (iv_ruleRealValue= ruleRealValue EOF )
            // InternalLwMessage.g:1132:2: iv_ruleRealValue= ruleRealValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRealValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRealValue=ruleRealValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRealValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRealValue"


    // $ANTLR start "ruleRealValue"
    // InternalLwMessage.g:1139:1: ruleRealValue returns [EObject current=null] : ( (lv_value_0_0= ruleDouble ) ) ;
    public final EObject ruleRealValue() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:1142:28: ( ( (lv_value_0_0= ruleDouble ) ) )
            // InternalLwMessage.g:1143:1: ( (lv_value_0_0= ruleDouble ) )
            {
            // InternalLwMessage.g:1143:1: ( (lv_value_0_0= ruleDouble ) )
            // InternalLwMessage.g:1144:1: (lv_value_0_0= ruleDouble )
            {
            // InternalLwMessage.g:1144:1: (lv_value_0_0= ruleDouble )
            // InternalLwMessage.g:1145:3: lv_value_0_0= ruleDouble
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getRealValueAccess().getValueDoubleParserRuleCall_0()); 
              	    
            }
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleDouble();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getRealValueRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.LwMessage.Double");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRealValue"


    // $ANTLR start "entryRuleNullValue"
    // InternalLwMessage.g:1169:1: entryRuleNullValue returns [EObject current=null] : iv_ruleNullValue= ruleNullValue EOF ;
    public final EObject entryRuleNullValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNullValue = null;


        try {
            // InternalLwMessage.g:1170:2: (iv_ruleNullValue= ruleNullValue EOF )
            // InternalLwMessage.g:1171:2: iv_ruleNullValue= ruleNullValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNullValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNullValue=ruleNullValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNullValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNullValue"


    // $ANTLR start "ruleNullValue"
    // InternalLwMessage.g:1178:1: ruleNullValue returns [EObject current=null] : ( () otherlv_1= 'null' ) ;
    public final EObject ruleNullValue() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:1181:28: ( ( () otherlv_1= 'null' ) )
            // InternalLwMessage.g:1182:1: ( () otherlv_1= 'null' )
            {
            // InternalLwMessage.g:1182:1: ( () otherlv_1= 'null' )
            // InternalLwMessage.g:1182:2: () otherlv_1= 'null'
            {
            // InternalLwMessage.g:1182:2: ()
            // InternalLwMessage.g:1183:5: 
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getNullValueAccess().getNullValueAction_0(),
                          current);
                  
            }

            }

            otherlv_1=(Token)match(input,25,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getNullValueAccess().getNullKeyword_1());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNullValue"


    // $ANTLR start "entryRuleStringValue"
    // InternalLwMessage.g:1200:1: entryRuleStringValue returns [EObject current=null] : iv_ruleStringValue= ruleStringValue EOF ;
    public final EObject entryRuleStringValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringValue = null;


        try {
            // InternalLwMessage.g:1201:2: (iv_ruleStringValue= ruleStringValue EOF )
            // InternalLwMessage.g:1202:2: iv_ruleStringValue= ruleStringValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStringValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStringValue=ruleStringValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStringValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringValue"


    // $ANTLR start "ruleStringValue"
    // InternalLwMessage.g:1209:1: ruleStringValue returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleStringValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:1212:28: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // InternalLwMessage.g:1213:1: ( (lv_value_0_0= RULE_STRING ) )
            {
            // InternalLwMessage.g:1213:1: ( (lv_value_0_0= RULE_STRING ) )
            // InternalLwMessage.g:1214:1: (lv_value_0_0= RULE_STRING )
            {
            // InternalLwMessage.g:1214:1: (lv_value_0_0= RULE_STRING )
            // InternalLwMessage.g:1215:3: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_0_0, grammarAccess.getStringValueAccess().getValueSTRINGTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getStringValueRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"org.eclipse.papyrus.uml.alf.Common.STRING");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringValue"


    // $ANTLR start "entryRuleDouble"
    // InternalLwMessage.g:1241:1: entryRuleDouble returns [String current=null] : iv_ruleDouble= ruleDouble EOF ;
    public final String entryRuleDouble() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDouble = null;


        try {
            // InternalLwMessage.g:1242:2: (iv_ruleDouble= ruleDouble EOF )
            // InternalLwMessage.g:1243:2: iv_ruleDouble= ruleDouble EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDoubleRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDouble=ruleDouble();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDouble.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDouble"


    // $ANTLR start "ruleDouble"
    // InternalLwMessage.g:1250:1: ruleDouble returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT | this_REAL_1= RULE_REAL ) ;
    public final AntlrDatatypeRuleToken ruleDouble() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token this_REAL_1=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:1253:28: ( (this_INT_0= RULE_INT | this_REAL_1= RULE_REAL ) )
            // InternalLwMessage.g:1254:1: (this_INT_0= RULE_INT | this_REAL_1= RULE_REAL )
            {
            // InternalLwMessage.g:1254:1: (this_INT_0= RULE_INT | this_REAL_1= RULE_REAL )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_INT) ) {
                alt20=1;
            }
            else if ( (LA20_0==RULE_REAL) ) {
                alt20=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // InternalLwMessage.g:1254:6: this_INT_0= RULE_INT
                    {
                    this_INT_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_INT_0);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_INT_0, grammarAccess.getDoubleAccess().getINTTerminalRuleCall_0()); 
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:1262:10: this_REAL_1= RULE_REAL
                    {
                    this_REAL_1=(Token)match(input,RULE_REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_REAL_1);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_REAL_1, grammarAccess.getDoubleAccess().getREALTerminalRuleCall_1()); 
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDouble"


    // $ANTLR start "entryRuleUnlimitedNatural"
    // InternalLwMessage.g:1277:1: entryRuleUnlimitedNatural returns [String current=null] : iv_ruleUnlimitedNatural= ruleUnlimitedNatural EOF ;
    public final String entryRuleUnlimitedNatural() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUnlimitedNatural = null;


        try {
            // InternalLwMessage.g:1278:2: (iv_ruleUnlimitedNatural= ruleUnlimitedNatural EOF )
            // InternalLwMessage.g:1279:2: iv_ruleUnlimitedNatural= ruleUnlimitedNatural EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnlimitedNaturalRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnlimitedNatural=ruleUnlimitedNatural();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnlimitedNatural.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnlimitedNatural"


    // $ANTLR start "ruleUnlimitedNatural"
    // InternalLwMessage.g:1286:1: ruleUnlimitedNatural returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Integer_0= ruleInteger | kw= '*' ) ;
    public final AntlrDatatypeRuleToken ruleUnlimitedNatural() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Integer_0 = null;


         enterRule(); 
            
        try {
            // InternalLwMessage.g:1289:28: ( (this_Integer_0= ruleInteger | kw= '*' ) )
            // InternalLwMessage.g:1290:1: (this_Integer_0= ruleInteger | kw= '*' )
            {
            // InternalLwMessage.g:1290:1: (this_Integer_0= ruleInteger | kw= '*' )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_INT||LA21_0==RULE_NEG_INT) ) {
                alt21=1;
            }
            else if ( (LA21_0==20) ) {
                alt21=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalLwMessage.g:1291:5: this_Integer_0= ruleInteger
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getUnlimitedNaturalAccess().getIntegerParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_2);
                    this_Integer_0=ruleInteger();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_Integer_0);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:1303:2: kw= '*'
                    {
                    kw=(Token)match(input,20,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getUnlimitedNaturalAccess().getAsteriskKeyword_1()); 
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnlimitedNatural"


    // $ANTLR start "entryRuleInteger"
    // InternalLwMessage.g:1316:1: entryRuleInteger returns [String current=null] : iv_ruleInteger= ruleInteger EOF ;
    public final String entryRuleInteger() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleInteger = null;


        try {
            // InternalLwMessage.g:1317:2: (iv_ruleInteger= ruleInteger EOF )
            // InternalLwMessage.g:1318:2: iv_ruleInteger= ruleInteger EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIntegerRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleInteger=ruleInteger();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleInteger.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInteger"


    // $ANTLR start "ruleInteger"
    // InternalLwMessage.g:1325:1: ruleInteger returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT | this_NEG_INT_1= RULE_NEG_INT ) ;
    public final AntlrDatatypeRuleToken ruleInteger() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token this_NEG_INT_1=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:1328:28: ( (this_INT_0= RULE_INT | this_NEG_INT_1= RULE_NEG_INT ) )
            // InternalLwMessage.g:1329:1: (this_INT_0= RULE_INT | this_NEG_INT_1= RULE_NEG_INT )
            {
            // InternalLwMessage.g:1329:1: (this_INT_0= RULE_INT | this_NEG_INT_1= RULE_NEG_INT )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_INT) ) {
                alt22=1;
            }
            else if ( (LA22_0==RULE_NEG_INT) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalLwMessage.g:1329:6: this_INT_0= RULE_INT
                    {
                    this_INT_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_INT_0);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_INT_0, grammarAccess.getIntegerAccess().getINTTerminalRuleCall_0()); 
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:1337:10: this_NEG_INT_1= RULE_NEG_INT
                    {
                    this_NEG_INT_1=(Token)match(input,RULE_NEG_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_NEG_INT_1);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_NEG_INT_1, grammarAccess.getIntegerAccess().getNEG_INTTerminalRuleCall_1()); 
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInteger"


    // $ANTLR start "entryRuleBoolean"
    // InternalLwMessage.g:1352:1: entryRuleBoolean returns [String current=null] : iv_ruleBoolean= ruleBoolean EOF ;
    public final String entryRuleBoolean() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoolean = null;


        try {
            // InternalLwMessage.g:1353:2: (iv_ruleBoolean= ruleBoolean EOF )
            // InternalLwMessage.g:1354:2: iv_ruleBoolean= ruleBoolean EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBooleanRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBoolean=ruleBoolean();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBoolean.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBoolean"


    // $ANTLR start "ruleBoolean"
    // InternalLwMessage.g:1361:1: ruleBoolean returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBoolean() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:1364:28: ( (kw= 'true' | kw= 'false' ) )
            // InternalLwMessage.g:1365:1: (kw= 'true' | kw= 'false' )
            {
            // InternalLwMessage.g:1365:1: (kw= 'true' | kw= 'false' )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==26) ) {
                alt23=1;
            }
            else if ( (LA23_0==27) ) {
                alt23=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // InternalLwMessage.g:1366:2: kw= 'true'
                    {
                    kw=(Token)match(input,26,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getBooleanAccess().getTrueKeyword_0()); 
                          
                    }

                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:1373:2: kw= 'false'
                    {
                    kw=(Token)match(input,27,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getBooleanAccess().getFalseKeyword_1()); 
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBoolean"


    // $ANTLR start "entryRuleName"
    // InternalLwMessage.g:1388:1: entryRuleName returns [String current=null] : iv_ruleName= ruleName EOF ;
    public final String entryRuleName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleName = null;


        try {
            // InternalLwMessage.g:1389:2: (iv_ruleName= ruleName EOF )
            // InternalLwMessage.g:1390:2: iv_ruleName= ruleName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleName=ruleName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleName"


    // $ANTLR start "ruleName"
    // InternalLwMessage.g:1397:1: ruleName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // InternalLwMessage.g:1400:28: (this_ID_0= RULE_ID )
            // InternalLwMessage.g:1401:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_ID_0);
                  
            }
            if ( state.backtracking==0 ) {
               
                  newLeafNode(this_ID_0, grammarAccess.getNameAccess().getIDTerminalRuleCall()); 
                  
            }

            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleName"

    // $ANTLR start synpred1_InternalLwMessage
    public final void synpred1_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:90:7: ( ( RULE_ID ) )
        // InternalLwMessage.g:91:1: ( RULE_ID )
        {
        // InternalLwMessage.g:91:1: ( RULE_ID )
        // InternalLwMessage.g:92:2: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalLwMessage

    // $ANTLR start synpred2_InternalLwMessage
    public final void synpred2_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:165:4: ( ( RULE_ID ) )
        // InternalLwMessage.g:166:1: ( RULE_ID )
        {
        // InternalLwMessage.g:166:1: ( RULE_ID )
        // InternalLwMessage.g:167:1: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalLwMessage

    // $ANTLR start synpred3_InternalLwMessage
    public final void synpred3_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:189:7: ( ( RULE_ID ) )
        // InternalLwMessage.g:190:1: ( RULE_ID )
        {
        // InternalLwMessage.g:190:1: ( RULE_ID )
        // InternalLwMessage.g:191:2: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred3_InternalLwMessage

    // $ANTLR start synpred4_InternalLwMessage
    public final void synpred4_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:208:7: ( ( RULE_ID ) )
        // InternalLwMessage.g:209:1: ( RULE_ID )
        {
        // InternalLwMessage.g:209:1: ( RULE_ID )
        // InternalLwMessage.g:210:2: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred4_InternalLwMessage

    // $ANTLR start synpred6_InternalLwMessage
    public final void synpred6_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:467:4: ( ( RULE_ID ) )
        // InternalLwMessage.g:468:1: ( RULE_ID )
        {
        // InternalLwMessage.g:468:1: ( RULE_ID )
        // InternalLwMessage.g:469:1: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred6_InternalLwMessage

    // $ANTLR start synpred7_InternalLwMessage
    public final void synpred7_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:491:7: ( ( RULE_ID ) )
        // InternalLwMessage.g:492:1: ( RULE_ID )
        {
        // InternalLwMessage.g:492:1: ( RULE_ID )
        // InternalLwMessage.g:493:2: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred7_InternalLwMessage

    // $ANTLR start synpred8_InternalLwMessage
    public final void synpred8_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:510:7: ( ( RULE_ID ) )
        // InternalLwMessage.g:511:1: ( RULE_ID )
        {
        // InternalLwMessage.g:511:1: ( RULE_ID )
        // InternalLwMessage.g:512:2: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred8_InternalLwMessage

    // $ANTLR start synpred9_InternalLwMessage
    public final void synpred9_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:596:3: ( ( RULE_ID ) )
        // InternalLwMessage.g:597:1: ( RULE_ID )
        {
        // InternalLwMessage.g:597:1: ( RULE_ID )
        // InternalLwMessage.g:598:2: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred9_InternalLwMessage

    // $ANTLR start synpred10_InternalLwMessage
    public final void synpred10_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:613:5: ( ( RULE_ID ) )
        // InternalLwMessage.g:614:1: ( RULE_ID )
        {
        // InternalLwMessage.g:614:1: ( RULE_ID )
        // InternalLwMessage.g:615:1: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred10_InternalLwMessage

    // $ANTLR start synpred11_InternalLwMessage
    public final void synpred11_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:637:7: ( ( RULE_ID ) )
        // InternalLwMessage.g:638:1: ( RULE_ID )
        {
        // InternalLwMessage.g:638:1: ( RULE_ID )
        // InternalLwMessage.g:639:2: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred11_InternalLwMessage

    // $ANTLR start synpred13_InternalLwMessage
    public final void synpred13_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:802:4: ( ( RULE_ID ) )
        // InternalLwMessage.g:803:1: ( RULE_ID )
        {
        // InternalLwMessage.g:803:1: ( RULE_ID )
        // InternalLwMessage.g:804:2: RULE_ID
        {
        match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred13_InternalLwMessage

    // $ANTLR start synpred14_InternalLwMessage
    public final void synpred14_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:956:7: ( ruleIntegerValue )
        // InternalLwMessage.g:956:9: ruleIntegerValue
        {
        pushFollow(FOLLOW_2);
        ruleIntegerValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_InternalLwMessage

    // $ANTLR start synpred15_InternalLwMessage
    public final void synpred15_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:966:7: ( ruleUnlimitedNaturalValue )
        // InternalLwMessage.g:966:9: ruleUnlimitedNaturalValue
        {
        pushFollow(FOLLOW_2);
        ruleUnlimitedNaturalValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_InternalLwMessage

    // $ANTLR start synpred16_InternalLwMessage
    public final void synpred16_InternalLwMessage_fragment() throws RecognitionException {   
        // InternalLwMessage.g:976:7: ( ruleRealValue )
        // InternalLwMessage.g:976:9: ruleRealValue
        {
        pushFollow(FOLLOW_2);
        ruleRealValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_InternalLwMessage

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
    public final boolean synpred15_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalLwMessage_fragment(); // can never throw exception
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
    public final boolean synpred6_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalLwMessage_fragment(); // can never throw exception
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
    public final boolean synpred16_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalLwMessage_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_InternalLwMessage() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_InternalLwMessage_fragment(); // can never throw exception
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


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x000000000E5801F0L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000000E5001E0L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x000000000E5001F0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000001040002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000080010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000001000000L});

}