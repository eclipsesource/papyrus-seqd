package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalLwMessageLexer extends Lexer {
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

    public InternalLwMessageLexer() {;} 
    public InternalLwMessageLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalLwMessageLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalLwMessage.g"; }

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:11:7: ( '*' )
            // InternalLwMessage.g:11:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:12:7: ( 'true' )
            // InternalLwMessage.g:12:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:13:7: ( 'false' )
            // InternalLwMessage.g:13:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:14:7: ( '(' )
            // InternalLwMessage.g:14:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:15:7: ( ')' )
            // InternalLwMessage.g:15:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:16:7: ( ',' )
            // InternalLwMessage.g:16:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:17:7: ( '-' )
            // InternalLwMessage.g:17:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:18:7: ( '=' )
            // InternalLwMessage.g:18:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:19:7: ( ':' )
            // InternalLwMessage.g:19:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:20:7: ( 'null' )
            // InternalLwMessage.g:20:9: 'null'
            {
            match("null"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "RULE_DIGITS"
    public final void mRULE_DIGITS() throws RecognitionException {
        try {
            // InternalLwMessage.g:2849:22: ( RULE_DIGIT ( RULE_DIGIT0 )* )
            // InternalLwMessage.g:2849:24: RULE_DIGIT ( RULE_DIGIT0 )*
            {
            mRULE_DIGIT(); 
            // InternalLwMessage.g:2849:35: ( RULE_DIGIT0 )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalLwMessage.g:2849:35: RULE_DIGIT0
            	    {
            	    mRULE_DIGIT0(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DIGITS"

    // $ANTLR start "RULE_DIGIT"
    public final void mRULE_DIGIT() throws RecognitionException {
        try {
            // InternalLwMessage.g:2851:21: ( '1' .. '9' )
            // InternalLwMessage.g:2851:23: '1' .. '9'
            {
            matchRange('1','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DIGIT"

    // $ANTLR start "RULE_DIGIT0"
    public final void mRULE_DIGIT0() throws RecognitionException {
        try {
            // InternalLwMessage.g:2853:22: ( ( '0' | RULE_DIGIT ) )
            // InternalLwMessage.g:2853:24: ( '0' | RULE_DIGIT )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DIGIT0"

    // $ANTLR start "RULE_DECIMAL"
    public final void mRULE_DECIMAL() throws RecognitionException {
        try {
            // InternalLwMessage.g:2855:23: ( ( ( RULE_INT | RULE_NEG_INT ) | ( '-' | '+' )? ( RULE_DIGIT0 )? '.' RULE_DIGITS ) )
            // InternalLwMessage.g:2855:25: ( ( RULE_INT | RULE_NEG_INT ) | ( '-' | '+' )? ( RULE_DIGIT0 )? '.' RULE_DIGITS )
            {
            // InternalLwMessage.g:2855:25: ( ( RULE_INT | RULE_NEG_INT ) | ( '-' | '+' )? ( RULE_DIGIT0 )? '.' RULE_DIGITS )
            int alt5=2;
            switch ( input.LA(1) ) {
            case '+':
                {
                int LA5_1 = input.LA(2);

                if ( ((LA5_1>='1' && LA5_1<='9')) ) {
                    int LA5_2 = input.LA(3);

                    if ( (LA5_2=='.') ) {
                        alt5=2;
                    }
                    else {
                        alt5=1;}
                }
                else if ( (LA5_1=='.'||LA5_1=='0') ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
                }
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                {
                int LA5_2 = input.LA(2);

                if ( (LA5_2=='.') ) {
                    alt5=2;
                }
                else {
                    alt5=1;}
                }
                break;
            case '-':
                {
                int LA5_3 = input.LA(2);

                if ( ((LA5_3>='1' && LA5_3<='9')) ) {
                    int LA5_6 = input.LA(3);

                    if ( (LA5_6=='.') ) {
                        alt5=2;
                    }
                    else {
                        alt5=1;}
                }
                else if ( (LA5_3=='.'||LA5_3=='0') ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 3, input);

                    throw nvae;
                }
                }
                break;
            case '.':
            case '0':
                {
                alt5=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalLwMessage.g:2855:26: ( RULE_INT | RULE_NEG_INT )
                    {
                    // InternalLwMessage.g:2855:26: ( RULE_INT | RULE_NEG_INT )
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0=='+'||(LA2_0>='1' && LA2_0<='9')) ) {
                        alt2=1;
                    }
                    else if ( (LA2_0=='-') ) {
                        alt2=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 0, input);

                        throw nvae;
                    }
                    switch (alt2) {
                        case 1 :
                            // InternalLwMessage.g:2855:27: RULE_INT
                            {
                            mRULE_INT(); 

                            }
                            break;
                        case 2 :
                            // InternalLwMessage.g:2855:36: RULE_NEG_INT
                            {
                            mRULE_NEG_INT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:2855:50: ( '-' | '+' )? ( RULE_DIGIT0 )? '.' RULE_DIGITS
                    {
                    // InternalLwMessage.g:2855:50: ( '-' | '+' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='+'||LA3_0=='-') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalLwMessage.g:
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}


                            }
                            break;

                    }

                    // InternalLwMessage.g:2855:61: ( RULE_DIGIT0 )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalLwMessage.g:2855:61: RULE_DIGIT0
                            {
                            mRULE_DIGIT0(); 

                            }
                            break;

                    }

                    match('.'); 
                    mRULE_DIGITS(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DECIMAL"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2857:10: ( ( '+' )? RULE_DIGITS )
            // InternalLwMessage.g:2857:12: ( '+' )? RULE_DIGITS
            {
            // InternalLwMessage.g:2857:12: ( '+' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='+') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalLwMessage.g:2857:12: '+'
                    {
                    match('+'); 

                    }
                    break;

            }

            mRULE_DIGITS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_NEG_INT"
    public final void mRULE_NEG_INT() throws RecognitionException {
        try {
            int _type = RULE_NEG_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2859:14: ( '-' RULE_DIGITS )
            // InternalLwMessage.g:2859:16: '-' RULE_DIGITS
            {
            match('-'); 
            mRULE_DIGITS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NEG_INT"

    // $ANTLR start "RULE_REAL"
    public final void mRULE_REAL() throws RecognitionException {
        try {
            int _type = RULE_REAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2861:11: ( RULE_DECIMAL ( ( 'e' | 'E' ) ( '-' | '+' )? RULE_DIGITS )? )
            // InternalLwMessage.g:2861:13: RULE_DECIMAL ( ( 'e' | 'E' ) ( '-' | '+' )? RULE_DIGITS )?
            {
            mRULE_DECIMAL(); 
            // InternalLwMessage.g:2861:26: ( ( 'e' | 'E' ) ( '-' | '+' )? RULE_DIGITS )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='E'||LA8_0=='e') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalLwMessage.g:2861:27: ( 'e' | 'E' ) ( '-' | '+' )? RULE_DIGITS
                    {
                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // InternalLwMessage.g:2861:37: ( '-' | '+' )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='+'||LA7_0=='-') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // InternalLwMessage.g:
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}


                            }
                            break;

                    }

                    mRULE_DIGITS(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_REAL"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2863:9: ( ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* | '\\'' ( options {greedy=false; } : . )* '\\'' ) )
            // InternalLwMessage.g:2863:11: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* | '\\'' ( options {greedy=false; } : . )* '\\'' )
            {
            // InternalLwMessage.g:2863:11: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* | '\\'' ( options {greedy=false; } : . )* '\\'' )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0>='A' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')) ) {
                alt11=1;
            }
            else if ( (LA11_0=='\'') ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalLwMessage.g:2863:12: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
                    {
                    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // InternalLwMessage.g:2863:36: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')||(LA9_0>='A' && LA9_0<='Z')||LA9_0=='_'||(LA9_0>='a' && LA9_0<='z')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // InternalLwMessage.g:
                    	    {
                    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:2863:70: '\\'' ( options {greedy=false; } : . )* '\\''
                    {
                    match('\''); 
                    // InternalLwMessage.g:2863:75: ( options {greedy=false; } : . )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0=='\'') ) {
                            alt10=2;
                        }
                        else if ( ((LA10_0>='\u0000' && LA10_0<='&')||(LA10_0>='(' && LA10_0<='\uFFFF')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // InternalLwMessage.g:2863:103: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2865:13: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' )
            // InternalLwMessage.g:2865:15: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // InternalLwMessage.g:2865:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='\\') ) {
                    alt12=1;
                }
                else if ( ((LA12_0>='\u0000' && LA12_0<='!')||(LA12_0>='#' && LA12_0<='[')||(LA12_0>=']' && LA12_0<='\uFFFF')) ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalLwMessage.g:2865:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalLwMessage.g:2865:61: ~ ( ( '\\\\' | '\"' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2867:17: ( '/*' ~ ( '@' ) ( options {greedy=false; } : . )* '*/' )
            // InternalLwMessage.g:2867:19: '/*' ~ ( '@' ) ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<='?')||(input.LA(1)>='A' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalLwMessage.g:2867:31: ( options {greedy=false; } : . )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0=='*') ) {
                    int LA13_1 = input.LA(2);

                    if ( (LA13_1=='/') ) {
                        alt13=2;
                    }
                    else if ( ((LA13_1>='\u0000' && LA13_1<='.')||(LA13_1>='0' && LA13_1<='\uFFFF')) ) {
                        alt13=1;
                    }


                }
                else if ( ((LA13_0>='\u0000' && LA13_0<=')')||(LA13_0>='+' && LA13_0<='\uFFFF')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalLwMessage.g:2867:59: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2869:17: ( '//' (~ ( ( '\\n' | '\\r' | '@' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalLwMessage.g:2869:19: '//' (~ ( ( '\\n' | '\\r' | '@' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalLwMessage.g:2869:24: (~ ( ( '\\n' | '\\r' | '@' ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='\u0000' && LA14_0<='\t')||(LA14_0>='\u000B' && LA14_0<='\f')||(LA14_0>='\u000E' && LA14_0<='?')||(LA14_0>='A' && LA14_0<='\uFFFF')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalLwMessage.g:2869:24: ~ ( ( '\\n' | '\\r' | '@' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='?')||(input.LA(1)>='A' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            // InternalLwMessage.g:2869:44: ( ( '\\r' )? '\\n' )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='\n'||LA16_0=='\r') ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalLwMessage.g:2869:45: ( '\\r' )? '\\n'
                    {
                    // InternalLwMessage.g:2869:45: ( '\\r' )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0=='\r') ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalLwMessage.g:2869:45: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_INTEGER_VALUE"
    public final void mRULE_INTEGER_VALUE() throws RecognitionException {
        try {
            int _type = RULE_INTEGER_VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2871:20: ( ( ( '0' | '1' .. '9' ( ( '_' )? '0' .. '9' )* ) | ( '0b' | '0B' ) '0' .. '1' ( ( '_' )? '0' .. '1' )* | ( '0x' | '0X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) ( ( '_' )? ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )* | '0' ( '_' )? '0' .. '7' ( ( '_' )? '0' .. '7' )* ) )
            // InternalLwMessage.g:2871:22: ( ( '0' | '1' .. '9' ( ( '_' )? '0' .. '9' )* ) | ( '0b' | '0B' ) '0' .. '1' ( ( '_' )? '0' .. '1' )* | ( '0x' | '0X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) ( ( '_' )? ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )* | '0' ( '_' )? '0' .. '7' ( ( '_' )? '0' .. '7' )* )
            {
            // InternalLwMessage.g:2871:22: ( ( '0' | '1' .. '9' ( ( '_' )? '0' .. '9' )* ) | ( '0b' | '0B' ) '0' .. '1' ( ( '_' )? '0' .. '1' )* | ( '0x' | '0X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) ( ( '_' )? ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )* | '0' ( '_' )? '0' .. '7' ( ( '_' )? '0' .. '7' )* )
            int alt29=4;
            int LA29_0 = input.LA(1);

            if ( (LA29_0=='0') ) {
                switch ( input.LA(2) ) {
                case 'B':
                case 'b':
                    {
                    alt29=2;
                    }
                    break;
                case 'X':
                case 'x':
                    {
                    alt29=3;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '_':
                    {
                    alt29=4;
                    }
                    break;
                default:
                    alt29=1;}

            }
            else if ( ((LA29_0>='1' && LA29_0<='9')) ) {
                alt29=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // InternalLwMessage.g:2871:23: ( '0' | '1' .. '9' ( ( '_' )? '0' .. '9' )* )
                    {
                    // InternalLwMessage.g:2871:23: ( '0' | '1' .. '9' ( ( '_' )? '0' .. '9' )* )
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='0') ) {
                        alt19=1;
                    }
                    else if ( ((LA19_0>='1' && LA19_0<='9')) ) {
                        alt19=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 19, 0, input);

                        throw nvae;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalLwMessage.g:2871:24: '0'
                            {
                            match('0'); 

                            }
                            break;
                        case 2 :
                            // InternalLwMessage.g:2871:28: '1' .. '9' ( ( '_' )? '0' .. '9' )*
                            {
                            matchRange('1','9'); 
                            // InternalLwMessage.g:2871:37: ( ( '_' )? '0' .. '9' )*
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( ((LA18_0>='0' && LA18_0<='9')||LA18_0=='_') ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // InternalLwMessage.g:2871:38: ( '_' )? '0' .. '9'
                            	    {
                            	    // InternalLwMessage.g:2871:38: ( '_' )?
                            	    int alt17=2;
                            	    int LA17_0 = input.LA(1);

                            	    if ( (LA17_0=='_') ) {
                            	        alt17=1;
                            	    }
                            	    switch (alt17) {
                            	        case 1 :
                            	            // InternalLwMessage.g:2871:38: '_'
                            	            {
                            	            match('_'); 

                            	            }
                            	            break;

                            	    }

                            	    matchRange('0','9'); 

                            	    }
                            	    break;

                            	default :
                            	    break loop18;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalLwMessage.g:2871:55: ( '0b' | '0B' ) '0' .. '1' ( ( '_' )? '0' .. '1' )*
                    {
                    // InternalLwMessage.g:2871:55: ( '0b' | '0B' )
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0=='0') ) {
                        int LA20_1 = input.LA(2);

                        if ( (LA20_1=='b') ) {
                            alt20=1;
                        }
                        else if ( (LA20_1=='B') ) {
                            alt20=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 20, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 20, 0, input);

                        throw nvae;
                    }
                    switch (alt20) {
                        case 1 :
                            // InternalLwMessage.g:2871:56: '0b'
                            {
                            match("0b"); 


                            }
                            break;
                        case 2 :
                            // InternalLwMessage.g:2871:61: '0B'
                            {
                            match("0B"); 


                            }
                            break;

                    }

                    matchRange('0','1'); 
                    // InternalLwMessage.g:2871:76: ( ( '_' )? '0' .. '1' )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( ((LA22_0>='0' && LA22_0<='1')||LA22_0=='_') ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // InternalLwMessage.g:2871:77: ( '_' )? '0' .. '1'
                    	    {
                    	    // InternalLwMessage.g:2871:77: ( '_' )?
                    	    int alt21=2;
                    	    int LA21_0 = input.LA(1);

                    	    if ( (LA21_0=='_') ) {
                    	        alt21=1;
                    	    }
                    	    switch (alt21) {
                    	        case 1 :
                    	            // InternalLwMessage.g:2871:77: '_'
                    	            {
                    	            match('_'); 

                    	            }
                    	            break;

                    	    }

                    	    matchRange('0','1'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop22;
                        }
                    } while (true);


                    }
                    break;
                case 3 :
                    // InternalLwMessage.g:2871:93: ( '0x' | '0X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) ( ( '_' )? ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )*
                    {
                    // InternalLwMessage.g:2871:93: ( '0x' | '0X' )
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0=='0') ) {
                        int LA23_1 = input.LA(2);

                        if ( (LA23_1=='x') ) {
                            alt23=1;
                        }
                        else if ( (LA23_1=='X') ) {
                            alt23=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 23, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 23, 0, input);

                        throw nvae;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalLwMessage.g:2871:94: '0x'
                            {
                            match("0x"); 


                            }
                            break;
                        case 2 :
                            // InternalLwMessage.g:2871:99: '0X'
                            {
                            match("0X"); 


                            }
                            break;

                    }

                    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // InternalLwMessage.g:2871:134: ( ( '_' )? ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( ((LA25_0>='0' && LA25_0<='9')||(LA25_0>='A' && LA25_0<='F')||LA25_0=='_'||(LA25_0>='a' && LA25_0<='f')) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // InternalLwMessage.g:2871:135: ( '_' )? ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
                    	    {
                    	    // InternalLwMessage.g:2871:135: ( '_' )?
                    	    int alt24=2;
                    	    int LA24_0 = input.LA(1);

                    	    if ( (LA24_0=='_') ) {
                    	        alt24=1;
                    	    }
                    	    switch (alt24) {
                    	        case 1 :
                    	            // InternalLwMessage.g:2871:135: '_'
                    	            {
                    	            match('_'); 

                    	            }
                    	            break;

                    	    }

                    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;
                case 4 :
                    // InternalLwMessage.g:2871:171: '0' ( '_' )? '0' .. '7' ( ( '_' )? '0' .. '7' )*
                    {
                    match('0'); 
                    // InternalLwMessage.g:2871:175: ( '_' )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0=='_') ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // InternalLwMessage.g:2871:175: '_'
                            {
                            match('_'); 

                            }
                            break;

                    }

                    matchRange('0','7'); 
                    // InternalLwMessage.g:2871:189: ( ( '_' )? '0' .. '7' )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( ((LA28_0>='0' && LA28_0<='7')||LA28_0=='_') ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // InternalLwMessage.g:2871:190: ( '_' )? '0' .. '7'
                    	    {
                    	    // InternalLwMessage.g:2871:190: ( '_' )?
                    	    int alt27=2;
                    	    int LA27_0 = input.LA(1);

                    	    if ( (LA27_0=='_') ) {
                    	        alt27=1;
                    	    }
                    	    switch (alt27) {
                    	        case 1 :
                    	            // InternalLwMessage.g:2871:190: '_'
                    	            {
                    	            match('_'); 

                    	            }
                    	            break;

                    	    }

                    	    matchRange('0','7'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop28;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INTEGER_VALUE"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2873:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalLwMessage.g:2873:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalLwMessage.g:2873:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt30=0;
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>='\t' && LA30_0<='\n')||LA30_0=='\r'||LA30_0==' ') ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalLwMessage.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt30 >= 1 ) break loop30;
                        EarlyExitException eee =
                            new EarlyExitException(30, input);
                        throw eee;
                }
                cnt30++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalLwMessage.g:2875:16: ( . )
            // InternalLwMessage.g:2875:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalLwMessage.g:1:8: ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | RULE_INT | RULE_NEG_INT | RULE_REAL | RULE_ID | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_INTEGER_VALUE | RULE_WS | RULE_ANY_OTHER )
        int alt31=20;
        alt31 = dfa31.predict(input);
        switch (alt31) {
            case 1 :
                // InternalLwMessage.g:1:10: T__18
                {
                mT__18(); 

                }
                break;
            case 2 :
                // InternalLwMessage.g:1:16: T__19
                {
                mT__19(); 

                }
                break;
            case 3 :
                // InternalLwMessage.g:1:22: T__20
                {
                mT__20(); 

                }
                break;
            case 4 :
                // InternalLwMessage.g:1:28: T__21
                {
                mT__21(); 

                }
                break;
            case 5 :
                // InternalLwMessage.g:1:34: T__22
                {
                mT__22(); 

                }
                break;
            case 6 :
                // InternalLwMessage.g:1:40: T__23
                {
                mT__23(); 

                }
                break;
            case 7 :
                // InternalLwMessage.g:1:46: T__24
                {
                mT__24(); 

                }
                break;
            case 8 :
                // InternalLwMessage.g:1:52: T__25
                {
                mT__25(); 

                }
                break;
            case 9 :
                // InternalLwMessage.g:1:58: T__26
                {
                mT__26(); 

                }
                break;
            case 10 :
                // InternalLwMessage.g:1:64: T__27
                {
                mT__27(); 

                }
                break;
            case 11 :
                // InternalLwMessage.g:1:70: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 12 :
                // InternalLwMessage.g:1:79: RULE_NEG_INT
                {
                mRULE_NEG_INT(); 

                }
                break;
            case 13 :
                // InternalLwMessage.g:1:92: RULE_REAL
                {
                mRULE_REAL(); 

                }
                break;
            case 14 :
                // InternalLwMessage.g:1:102: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 15 :
                // InternalLwMessage.g:1:110: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 16 :
                // InternalLwMessage.g:1:122: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 17 :
                // InternalLwMessage.g:1:138: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 18 :
                // InternalLwMessage.g:1:154: RULE_INTEGER_VALUE
                {
                mRULE_INTEGER_VALUE(); 

                }
                break;
            case 19 :
                // InternalLwMessage.g:1:173: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 20 :
                // InternalLwMessage.g:1:181: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA31 dfa31 = new DFA31(this);
    static final String DFA31_eotS =
        "\2\uffff\2\27\3\uffff\1\35\2\uffff\1\27\1\24\1\43\1\45\1\24\1\uffff\3\24\3\uffff\1\27\1\uffff\1\27\3\uffff\1\54\4\uffff\1\27\1\43\1\uffff\1\43\5\uffff\2\27\1\uffff\1\54\1\27\1\43\1\63\1\27\1\65\1\uffff\1\66\2\uffff";
    static final String DFA31_eofS =
        "\67\uffff";
    static final String DFA31_minS =
        "\1\0\1\uffff\1\162\1\141\3\uffff\1\56\2\uffff\1\165\3\56\1\61\1\uffff\2\0\1\52\3\uffff\1\165\1\uffff\1\154\3\uffff\1\56\4\uffff\1\154\1\56\1\uffff\1\60\5\uffff\1\145\1\163\1\uffff\1\60\1\154\2\60\1\145\1\60\1\uffff\1\60\2\uffff";
    static final String DFA31_maxS =
        "\1\uffff\1\uffff\1\162\1\141\3\uffff\1\71\2\uffff\1\165\1\71\1\145\1\56\1\71\1\uffff\2\uffff\1\57\3\uffff\1\165\1\uffff\1\154\3\uffff\1\145\4\uffff\1\154\1\145\1\uffff\1\145\5\uffff\1\145\1\163\1\uffff\1\145\1\154\1\145\1\172\1\145\1\172\1\uffff\1\172\2\uffff";
    static final String DFA31_acceptS =
        "\1\uffff\1\1\2\uffff\1\4\1\5\1\6\1\uffff\1\10\1\11\5\uffff\1\16\3\uffff\1\23\1\24\1\1\1\uffff\1\16\1\uffff\1\4\1\5\1\6\1\uffff\1\7\1\15\1\10\1\11\2\uffff\1\13\1\uffff\1\22\1\17\1\20\1\21\1\23\2\uffff\1\14\6\uffff\1\2\1\uffff\1\12\1\3";
    static final String DFA31_specialS =
        "\1\0\17\uffff\1\1\1\2\45\uffff}>";
    static final String[] DFA31_transitionS = {
            "\11\24\2\23\2\24\1\23\22\24\1\23\1\24\1\21\4\24\1\20\1\4\1\5\1\1\1\13\1\6\1\7\1\16\1\22\1\15\11\14\1\11\2\24\1\10\3\24\32\17\4\24\1\17\1\24\5\17\1\3\7\17\1\12\5\17\1\2\6\17\uff85\24",
            "",
            "\1\26",
            "\1\30",
            "",
            "",
            "",
            "\1\36\1\uffff\1\36\11\34",
            "",
            "",
            "\1\41",
            "\1\36\1\uffff\1\36\11\42",
            "\1\36\1\uffff\12\44\13\uffff\1\36\31\uffff\1\45\5\uffff\1\36",
            "\1\36",
            "\11\36",
            "",
            "\0\27",
            "\0\46",
            "\1\47\4\uffff\1\50",
            "",
            "",
            "",
            "\1\52",
            "",
            "\1\53",
            "",
            "",
            "",
            "\1\36\1\uffff\12\55\13\uffff\1\36\37\uffff\1\36",
            "",
            "",
            "",
            "",
            "\1\56",
            "\1\36\1\uffff\12\57\13\uffff\1\36\37\uffff\1\36",
            "",
            "\12\44\13\uffff\1\36\31\uffff\1\45\5\uffff\1\36",
            "",
            "",
            "",
            "",
            "",
            "\1\60",
            "\1\61",
            "",
            "\12\55\13\uffff\1\36\37\uffff\1\36",
            "\1\62",
            "\12\57\13\uffff\1\36\37\uffff\1\36",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\64",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            ""
    };

    static final short[] DFA31_eot = DFA.unpackEncodedString(DFA31_eotS);
    static final short[] DFA31_eof = DFA.unpackEncodedString(DFA31_eofS);
    static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars(DFA31_minS);
    static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars(DFA31_maxS);
    static final short[] DFA31_accept = DFA.unpackEncodedString(DFA31_acceptS);
    static final short[] DFA31_special = DFA.unpackEncodedString(DFA31_specialS);
    static final short[][] DFA31_transition;

    static {
        int numStates = DFA31_transitionS.length;
        DFA31_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i]);
        }
    }

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = DFA31_eot;
            this.eof = DFA31_eof;
            this.min = DFA31_min;
            this.max = DFA31_max;
            this.accept = DFA31_accept;
            this.special = DFA31_special;
            this.transition = DFA31_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | RULE_INT | RULE_NEG_INT | RULE_REAL | RULE_ID | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_INTEGER_VALUE | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA31_0 = input.LA(1);

                        s = -1;
                        if ( (LA31_0=='*') ) {s = 1;}

                        else if ( (LA31_0=='t') ) {s = 2;}

                        else if ( (LA31_0=='f') ) {s = 3;}

                        else if ( (LA31_0=='(') ) {s = 4;}

                        else if ( (LA31_0==')') ) {s = 5;}

                        else if ( (LA31_0==',') ) {s = 6;}

                        else if ( (LA31_0=='-') ) {s = 7;}

                        else if ( (LA31_0=='=') ) {s = 8;}

                        else if ( (LA31_0==':') ) {s = 9;}

                        else if ( (LA31_0=='n') ) {s = 10;}

                        else if ( (LA31_0=='+') ) {s = 11;}

                        else if ( ((LA31_0>='1' && LA31_0<='9')) ) {s = 12;}

                        else if ( (LA31_0=='0') ) {s = 13;}

                        else if ( (LA31_0=='.') ) {s = 14;}

                        else if ( ((LA31_0>='A' && LA31_0<='Z')||LA31_0=='_'||(LA31_0>='a' && LA31_0<='e')||(LA31_0>='g' && LA31_0<='m')||(LA31_0>='o' && LA31_0<='s')||(LA31_0>='u' && LA31_0<='z')) ) {s = 15;}

                        else if ( (LA31_0=='\'') ) {s = 16;}

                        else if ( (LA31_0=='\"') ) {s = 17;}

                        else if ( (LA31_0=='/') ) {s = 18;}

                        else if ( ((LA31_0>='\t' && LA31_0<='\n')||LA31_0=='\r'||LA31_0==' ') ) {s = 19;}

                        else if ( ((LA31_0>='\u0000' && LA31_0<='\b')||(LA31_0>='\u000B' && LA31_0<='\f')||(LA31_0>='\u000E' && LA31_0<='\u001F')||LA31_0=='!'||(LA31_0>='#' && LA31_0<='&')||(LA31_0>=';' && LA31_0<='<')||(LA31_0>='>' && LA31_0<='@')||(LA31_0>='[' && LA31_0<='^')||LA31_0=='`'||(LA31_0>='{' && LA31_0<='\uFFFF')) ) {s = 20;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA31_16 = input.LA(1);

                        s = -1;
                        if ( ((LA31_16>='\u0000' && LA31_16<='\uFFFF')) ) {s = 23;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA31_17 = input.LA(1);

                        s = -1;
                        if ( ((LA31_17>='\u0000' && LA31_17<='\uFFFF')) ) {s = 38;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 31, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}