// $ANTLR 3.5.2 ./src/VSLTreeParser.g 2016-11-30 22:03:43

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class VSLTreeParser extends TreeParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASCII", "ASSIGN_KW", "COM", "COMMENT", 
		"DIGIT", "DIV", "DO_KW", "ELSE_KW", "FI_KW", "FUNC_KW", "IDENT", "IF_KW", 
		"INTEGER", "INT_KW", "LB", "LC", "LETTER", "LP", "MINUS", "MUL", "OD_KW", 
		"PLUS", "PRINT_KW", "PROTO_KW", "RB", "RC", "READ_KW", "RETURN_KW", "RP", 
		"TEXT", "THEN_KW", "VOID_KW", "WHILE_KW", "WS", "ARDECL", "ARELEM", "ARRAY", 
		"BLOCK", "BODY", "DECL", "FCALL", "FCALL_S", "INST", "NEGAT", "PARAM", 
		"PROG"
	};
	public static final int EOF=-1;
	public static final int ASCII=4;
	public static final int ASSIGN_KW=5;
	public static final int COM=6;
	public static final int COMMENT=7;
	public static final int DIGIT=8;
	public static final int DIV=9;
	public static final int DO_KW=10;
	public static final int ELSE_KW=11;
	public static final int FI_KW=12;
	public static final int FUNC_KW=13;
	public static final int IDENT=14;
	public static final int IF_KW=15;
	public static final int INTEGER=16;
	public static final int INT_KW=17;
	public static final int LB=18;
	public static final int LC=19;
	public static final int LETTER=20;
	public static final int LP=21;
	public static final int MINUS=22;
	public static final int MUL=23;
	public static final int OD_KW=24;
	public static final int PLUS=25;
	public static final int PRINT_KW=26;
	public static final int PROTO_KW=27;
	public static final int RB=28;
	public static final int RC=29;
	public static final int READ_KW=30;
	public static final int RETURN_KW=31;
	public static final int RP=32;
	public static final int TEXT=33;
	public static final int THEN_KW=34;
	public static final int VOID_KW=35;
	public static final int WHILE_KW=36;
	public static final int WS=37;
	public static final int ARDECL=38;
	public static final int ARELEM=39;
	public static final int ARRAY=40;
	public static final int BLOCK=41;
	public static final int BODY=42;
	public static final int DECL=43;
	public static final int FCALL=44;
	public static final int FCALL_S=45;
	public static final int INST=46;
	public static final int NEGAT=47;
	public static final int PARAM=48;
	public static final int PROG=49;

	// delegates
	public TreeParser[] getDelegates() {
		return new TreeParser[] {};
	}

	// delegators


	public VSLTreeParser(TreeNodeStream input) {
		this(input, new RecognizerSharedState());
	}
	public VSLTreeParser(TreeNodeStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return VSLTreeParser.tokenNames; }
	@Override public String getGrammarFileName() { return "./src/VSLTreeParser.g"; }



	// $ANTLR start "s"
	// ./src/VSLTreeParser.g:9:1: s[SymbolTable symTab] returns [Code3a code] : e= expression[symTab] ;
	public final Code3a s(SymbolTable symTab) throws RecognitionException {
		Code3a code = null;


		ExpAttribute e =null;

		try {
			// ./src/VSLTreeParser.g:10:3: (e= expression[symTab] )
			// ./src/VSLTreeParser.g:10:5: e= expression[symTab]
			{
			pushFollow(FOLLOW_expression_in_s60);
			e=expression(symTab);
			state._fsp--;

			 code = e.code; 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return code;
	}
	// $ANTLR end "s"



	// $ANTLR start "function"
	// ./src/VSLTreeParser.g:13:1: function[SymbolTable symTab] returns [Code3a code] : ^( FUNC_KW type IDENT paramList= param_list[symTab, functionType] ^( BODY body= statement[symTab] ) ) ;
	public final Code3a function(SymbolTable symTab) throws RecognitionException {
		Code3a code = null;


		CommonTree IDENT2=null;
		Code3a body =null;
		Type type1 =null;

		try {
			// ./src/VSLTreeParser.g:14:2: ( ^( FUNC_KW type IDENT paramList= param_list[symTab, functionType] ^( BODY body= statement[symTab] ) ) )
			// ./src/VSLTreeParser.g:14:4: ^( FUNC_KW type IDENT paramList= param_list[symTab, functionType] ^( BODY body= statement[symTab] ) )
			{
			match(input,FUNC_KW,FOLLOW_FUNC_KW_in_function82); 
			match(input, Token.DOWN, null); 
			pushFollow(FOLLOW_type_in_function84);
			type1=type();
			state._fsp--;

			FunctionType functionType = new FunctionType(type1, false);
			IDENT2=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_function89); 
			symTab.enterScope();
			pushFollow(FOLLOW_param_list_in_function95);
			param_list(symTab, functionType);
			state._fsp--;

			match(input,BODY,FOLLOW_BODY_in_function100); 
			match(input, Token.DOWN, null); 
			pushFollow(FOLLOW_statement_in_function104);
			body=statement(symTab);
			state._fsp--;

			match(input, Token.UP, null); 

			symTab.leaveScope();
			match(input, Token.UP, null); 



			    Operand3a op = symTab.lookup((IDENT2!=null?IDENT2.getText():null));
			    LabelSymbol label = new LabelSymbol((IDENT2!=null?IDENT2.getText():null));
			    if (op == null) {
			      symTab.insert((IDENT2!=null?IDENT2.getText():null), new FunctionSymbol(label, functionType));
			    } else if (op instanceof FunctionSymbol) {
			      if(TypeCheck.checkFunctionDefinition((FunctionSymbol) op, functionType) == Type.ERROR){
			        Errors.redefinedIdentifier(IDENT2, (IDENT2!=null?IDENT2.getText():null), null);
			        System.exit(1);
			      }
			      if(TypeCheck.checkFunctionDefinitionType((FunctionSymbol) op, functionType) == Type.ERROR){
			        Errors.incompatibleTypes(IDENT2, ((FunctionSymbol)op).type, functionType, null);
			        System.exit(1);
			      }
			    }
			    code = Code3aGenerator.genFunction(label, body);
			  
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return code;
	}
	// $ANTLR end "function"



	// $ANTLR start "proto"
	// ./src/VSLTreeParser.g:36:1: proto[SymbolTable symTab] : ^( PROTO_KW type IDENT ^( PARAM paramList= param_list[symTab, functionType] ) ) ;
	public final void proto(SymbolTable symTab) throws RecognitionException {
		CommonTree IDENT4=null;
		Type type3 =null;

		try {
			// ./src/VSLTreeParser.g:37:2: ( ^( PROTO_KW type IDENT ^( PARAM paramList= param_list[symTab, functionType] ) ) )
			// ./src/VSLTreeParser.g:37:4: ^( PROTO_KW type IDENT ^( PARAM paramList= param_list[symTab, functionType] ) )
			{
			match(input,PROTO_KW,FOLLOW_PROTO_KW_in_proto125); 
			match(input, Token.DOWN, null); 
			pushFollow(FOLLOW_type_in_proto127);
			type3=type();
			state._fsp--;

			FunctionType functionType = new FunctionType(type3, true);
			IDENT4=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_proto132); 
			symTab.enterScope();
			match(input,PARAM,FOLLOW_PARAM_in_proto137); 
			match(input, Token.DOWN, null); 
			pushFollow(FOLLOW_param_list_in_proto141);
			param_list(symTab, functionType);
			state._fsp--;

			symTab.leaveScope();
			match(input, Token.UP, null); 

			match(input, Token.UP, null); 


			    if(symTab.lookup((IDENT4!=null?IDENT4.getText():null)) == null) {
			      symTab.insert((IDENT4!=null?IDENT4.getText():null), new FunctionSymbol(new LabelSymbol((IDENT4!=null?IDENT4.getText():null)), functionType));
			    } else {
			      Errors.redefinedIdentifier(IDENT4, (IDENT4!=null?IDENT4.getText():null), null);
			      System.exit(1);
			    }
			  
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "proto"



	// $ANTLR start "type"
	// ./src/VSLTreeParser.g:49:1: type returns [Type type] : ( INT_KW | VOID_KW );
	public final Type type() throws RecognitionException {
		Type type = null;


		try {
			// ./src/VSLTreeParser.g:50:2: ( INT_KW | VOID_KW )
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0==INT_KW) ) {
				alt1=1;
			}
			else if ( (LA1_0==VOID_KW) ) {
				alt1=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}

			switch (alt1) {
				case 1 :
					// ./src/VSLTreeParser.g:50:4: INT_KW
					{
					match(input,INT_KW,FOLLOW_INT_KW_in_type164); 
					type = Type.INT;
					}
					break;
				case 2 :
					// ./src/VSLTreeParser.g:51:4: VOID_KW
					{
					match(input,VOID_KW,FOLLOW_VOID_KW_in_type171); 
					type = Type.VOID;
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return type;
	}
	// $ANTLR end "type"



	// $ANTLR start "param_list"
	// ./src/VSLTreeParser.g:54:1: param_list[SymbolTable symTab, FunctionType type] : ^( PARAM ( param[symTab, type] )* ) ;
	public final void param_list(SymbolTable symTab, FunctionType type) throws RecognitionException {
		try {
			// ./src/VSLTreeParser.g:55:3: ( ^( PARAM ( param[symTab, type] )* ) )
			// ./src/VSLTreeParser.g:55:5: ^( PARAM ( param[symTab, type] )* )
			{
			match(input,PARAM,FOLLOW_PARAM_in_param_list187); 
			if ( input.LA(1)==Token.DOWN ) {
				match(input, Token.DOWN, null); 
				// ./src/VSLTreeParser.g:55:13: ( param[symTab, type] )*
				loop2:
				while (true) {
					int alt2=2;
					int LA2_0 = input.LA(1);
					if ( (LA2_0==IDENT) ) {
						alt2=1;
					}

					switch (alt2) {
					case 1 :
						// ./src/VSLTreeParser.g:55:13: param[symTab, type]
						{
						pushFollow(FOLLOW_param_in_param_list189);
						param(symTab, type);
						state._fsp--;

						}
						break;

					default :
						break loop2;
					}
				}

				match(input, Token.UP, null); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "param_list"



	// $ANTLR start "param"
	// ./src/VSLTreeParser.g:58:1: param[SymbolTable symTab, FunctionType type] : IDENT ;
	public final void param(SymbolTable symTab, FunctionType type) throws RecognitionException {
		CommonTree IDENT5=null;

		try {
			// ./src/VSLTreeParser.g:59:3: ( IDENT )
			// ./src/VSLTreeParser.g:59:5: IDENT
			{
			IDENT5=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_param205); 

			      if (symTab.lookup((IDENT5!=null?IDENT5.getText():null)) == null) {
			        VarSymbol varSymbol = new VarSymbol(Type.INT, (IDENT5!=null?IDENT5.getText():null), symTab.getScope());
			        varSymbol.setParam();
			        symTab.insert((IDENT5!=null?IDENT5.getText():null), varSymbol);
			        type.extend(Type.INT);
			      } else {
			        Errors.redefinedIdentifier(IDENT5, (IDENT5!=null?IDENT5.getText():null), null);
			        System.exit(1);
			      }
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "param"



	// $ANTLR start "statement"
	// ./src/VSLTreeParser.g:72:1: statement[SymbolTable symTab] returns [Code3a code] : ( ^( ASSIGN_KW e= expression[symTab] IDENT ) |b= block[symTab] | ^( IF_KW cond= expression[symTab] stat= statement[symTab] (else_st= statement[symTab] )? ) | ^( WHILE_KW cond= expression[symTab] stat= statement[symTab] ) | ^( RETURN_KW e= expression[symTab] ) | ^( FCALL_S IDENT argument_list[symTab, functionType] ) );
	public final Code3a statement(SymbolTable symTab) throws RecognitionException {
		Code3a code = null;


		CommonTree IDENT6=null;
		CommonTree RETURN_KW7=null;
		CommonTree IDENT8=null;
		ExpAttribute e =null;
		Code3a b =null;
		ExpAttribute cond =null;
		Code3a stat =null;
		Code3a else_st =null;
		Code3a argument_list9 =null;

		try {
			// ./src/VSLTreeParser.g:73:3: ( ^( ASSIGN_KW e= expression[symTab] IDENT ) |b= block[symTab] | ^( IF_KW cond= expression[symTab] stat= statement[symTab] (else_st= statement[symTab] )? ) | ^( WHILE_KW cond= expression[symTab] stat= statement[symTab] ) | ^( RETURN_KW e= expression[symTab] ) | ^( FCALL_S IDENT argument_list[symTab, functionType] ) )
			int alt4=6;
			switch ( input.LA(1) ) {
			case ASSIGN_KW:
				{
				alt4=1;
				}
				break;
			case BLOCK:
				{
				alt4=2;
				}
				break;
			case IF_KW:
				{
				alt4=3;
				}
				break;
			case WHILE_KW:
				{
				alt4=4;
				}
				break;
			case RETURN_KW:
				{
				alt4=5;
				}
				break;
			case FCALL_S:
				{
				alt4=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}
			switch (alt4) {
				case 1 :
					// ./src/VSLTreeParser.g:73:5: ^( ASSIGN_KW e= expression[symTab] IDENT )
					{
					match(input,ASSIGN_KW,FOLLOW_ASSIGN_KW_in_statement225); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_statement229);
					e=expression(symTab);
					state._fsp--;

					IDENT6=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_statement232); 
					match(input, Token.UP, null); 


					        ExpAttribute exp = e;
					        Operand3a op = symTab.lookup((IDENT6!=null?IDENT6.getText():null));
					        if(op != null) {
					          Type idType = op.type;
					          Type expType = exp.type;
					          Type t = TypeCheck.checkAssignement(idType, expType);
					          if(t.equals(Type.ERROR)){
					            Errors.incompatibleTypes(IDENT6, Type.INT, exp.type, null);
					  					System.exit(1);
					          }
					          code = Code3aGenerator.genAssignement(exp, new ExpAttribute(Type.INT, new Code3a(), new VarSymbol((IDENT6!=null?IDENT6.getText():null))));
					        } else {
					          Errors.unknownIdentifier(IDENT6, (IDENT6!=null?IDENT6.getText():null), null);
					          System.exit(1);
					        }
					      
					}
					break;
				case 2 :
					// ./src/VSLTreeParser.g:90:7: b= block[symTab]
					{
					pushFollow(FOLLOW_block_in_statement245);
					b=block(symTab);
					state._fsp--;


					        code = b;
					      
					}
					break;
				case 3 :
					// ./src/VSLTreeParser.g:93:7: ^( IF_KW cond= expression[symTab] stat= statement[symTab] (else_st= statement[symTab] )? )
					{
					match(input,IF_KW,FOLLOW_IF_KW_in_statement257); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_statement261);
					cond=expression(symTab);
					state._fsp--;

					pushFollow(FOLLOW_statement_in_statement266);
					stat=statement(symTab);
					state._fsp--;

					// ./src/VSLTreeParser.g:93:62: (else_st= statement[symTab] )?
					int alt3=2;
					int LA3_0 = input.LA(1);
					if ( (LA3_0==ASSIGN_KW||LA3_0==IF_KW||LA3_0==RETURN_KW||LA3_0==WHILE_KW||LA3_0==BLOCK||LA3_0==FCALL_S) ) {
						alt3=1;
					}
					switch (alt3) {
						case 1 :
							// ./src/VSLTreeParser.g:93:63: else_st= statement[symTab]
							{
							pushFollow(FOLLOW_statement_in_statement272);
							else_st=statement(symTab);
							state._fsp--;

							}
							break;

					}

					match(input, Token.UP, null); 


					          if (else_st != null) {
					    				code = Code3aGenerator.genIf(cond, stat, else_st);
					    			} else {
					    				code = Code3aGenerator.genIf(cond, stat);
					          }
					        
					}
					break;
				case 4 :
					// ./src/VSLTreeParser.g:101:7: ^( WHILE_KW cond= expression[symTab] stat= statement[symTab] )
					{
					match(input,WHILE_KW,FOLLOW_WHILE_KW_in_statement295); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_statement299);
					cond=expression(symTab);
					state._fsp--;

					pushFollow(FOLLOW_statement_in_statement304);
					stat=statement(symTab);
					state._fsp--;

					match(input, Token.UP, null); 


					        code = Code3aGenerator.genWhile(cond, stat);
					      
					}
					break;
				case 5 :
					// ./src/VSLTreeParser.g:104:7: ^( RETURN_KW e= expression[symTab] )
					{
					RETURN_KW7=(CommonTree)match(input,RETURN_KW,FOLLOW_RETURN_KW_in_statement317); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_statement321);
					e=expression(symTab);
					state._fsp--;

					match(input, Token.UP, null); 


					  			if(e.type != Type.INT) {
					  				Errors.incompatibleTypes(RETURN_KW7, Type.INT, e.type, null);
					  				System.exit(1);
					  			}
					  			code = Code3aGenerator.genReturn(e);
					      
					}
					break;
				case 6 :
					// ./src/VSLTreeParser.g:111:7: ^( FCALL_S IDENT argument_list[symTab, functionType] )
					{
					match(input,FCALL_S,FOLLOW_FCALL_S_in_statement334); 
					match(input, Token.DOWN, null); 
					IDENT8=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_statement336); 
					FunctionType functionType = new FunctionType(Type.VOID, false);
					pushFollow(FOLLOW_argument_list_in_statement340);
					argument_list9=argument_list(symTab, functionType);
					state._fsp--;

					match(input, Token.UP, null); 


					  			Operand3a op = symTab.lookup((IDENT8!=null?IDENT8.getText():null));
					  			if(op != null && (op instanceof FunctionSymbol)) {
					  				FunctionSymbol functionSymbol = (FunctionSymbol) op;
					  				if (((FunctionType)functionSymbol.type).getReturnType() == Type.VOID) {
					  					code = Code3aGenerator.genCallSt(argument_list9, (IDENT8!=null?IDENT8.getText():null));
					  				} else {
					  					Errors.unknownIdentifier(IDENT8, (IDENT8!=null?IDENT8.getText():null), null);
					  					System.exit(1);
					  				}
					          if(!functionSymbol.type.isCompatible((Type)functionType)) {
					  					Errors.incompatibleTypes(IDENT8, functionSymbol.type, functionType, null);
					  					System.exit(1);
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
			// do for sure before leaving
		}
		return code;
	}
	// $ANTLR end "statement"



	// $ANTLR start "block"
	// ./src/VSLTreeParser.g:129:3: block[SymbolTable symTab] returns [Code3a code] : ( ^( BLOCK decl= declaration[symTab] il= inst_list[symTab] ) | ^( BLOCK il= inst_list[symTab] ) );
	public final Code3a block(SymbolTable symTab) throws RecognitionException {
		Code3a code = null;


		Code3a decl =null;
		Code3a il =null;

		try {
			// ./src/VSLTreeParser.g:130:5: ( ^( BLOCK decl= declaration[symTab] il= inst_list[symTab] ) | ^( BLOCK il= inst_list[symTab] ) )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==BLOCK) ) {
				int LA5_1 = input.LA(2);
				if ( (LA5_1==DOWN) ) {
					int LA5_2 = input.LA(3);
					if ( (LA5_2==DECL) ) {
						alt5=1;
					}
					else if ( (LA5_2==INST) ) {
						alt5=2;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 5, 2, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 5, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// ./src/VSLTreeParser.g:130:7: ^( BLOCK decl= declaration[symTab] il= inst_list[symTab] )
					{
					match(input,BLOCK,FOLLOW_BLOCK_in_block370); 
					symTab.enterScope();
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_declaration_in_block376);
					decl=declaration(symTab);
					state._fsp--;

					pushFollow(FOLLOW_inst_list_in_block381);
					il=inst_list(symTab);
					state._fsp--;

					match(input, Token.UP, null); 


					        code = decl;
					        code.append(il);
					        symTab.leaveScope();
					      
					}
					break;
				case 2 :
					// ./src/VSLTreeParser.g:135:7: ^( BLOCK il= inst_list[symTab] )
					{
					match(input,BLOCK,FOLLOW_BLOCK_in_block394); 
					symTab.enterScope();
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_inst_list_in_block400);
					il=inst_list(symTab);
					state._fsp--;

					match(input, Token.UP, null); 


					        code = il;
					        symTab.leaveScope();
					      
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return code;
	}
	// $ANTLR end "block"



	// $ANTLR start "declaration"
	// ./src/VSLTreeParser.g:141:3: declaration[SymbolTable symTab] returns [Code3a code] : ^( DECL (di= decl_item[symTab] )+ ) ;
	public final Code3a declaration(SymbolTable symTab) throws RecognitionException {
		Code3a code = null;


		Code3a di =null;

		try {
			// ./src/VSLTreeParser.g:142:5: ( ^( DECL (di= decl_item[symTab] )+ ) )
			// ./src/VSLTreeParser.g:142:7: ^( DECL (di= decl_item[symTab] )+ )
			{
			match(input,DECL,FOLLOW_DECL_in_declaration430); 
			code = new Code3a();
			match(input, Token.DOWN, null); 
			// ./src/VSLTreeParser.g:142:38: (di= decl_item[symTab] )+
			int cnt6=0;
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==IDENT||LA6_0==ARDECL) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// ./src/VSLTreeParser.g:142:39: di= decl_item[symTab]
					{
					pushFollow(FOLLOW_decl_item_in_declaration437);
					di=decl_item(symTab);
					state._fsp--;


					          code.append(di);
					        
					}
					break;

				default :
					if ( cnt6 >= 1 ) break loop6;
					EarlyExitException eee = new EarlyExitException(6, input);
					throw eee;
				}
				cnt6++;
			}

			match(input, Token.UP, null); 

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return code;
	}
	// $ANTLR end "declaration"



	// $ANTLR start "decl_item"
	// ./src/VSLTreeParser.g:148:3: decl_item[SymbolTable symTab] returns [Code3a code] : ( IDENT | ^( ARDECL IDENT INTEGER ) );
	public final Code3a decl_item(SymbolTable symTab) throws RecognitionException {
		Code3a code = null;


		CommonTree IDENT10=null;

		try {
			// ./src/VSLTreeParser.g:149:5: ( IDENT | ^( ARDECL IDENT INTEGER ) )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==IDENT) ) {
				alt7=1;
			}
			else if ( (LA7_0==ARDECL) ) {
				alt7=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// ./src/VSLTreeParser.g:149:7: IDENT
					{
					IDENT10=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_decl_item475); 

					        VarSymbol vs = (VarSymbol) symTab.lookup((IDENT10!=null?IDENT10.getText():null));
					        int currentScope = symTab.getScope();
					        if(vs == null){
					          vs = new VarSymbol(Type.INT, (IDENT10!=null?IDENT10.getText():null), currentScope);
					          symTab.insert((IDENT10!=null?IDENT10.getText():null), vs);
					          code = Code3aGenerator.genVar(vs);
					        } else {
					          int scopeIdent = vs.getScope();
					          if(scopeIdent == currentScope) {
					            Errors.redefinedIdentifier(IDENT10, (IDENT10!=null?IDENT10.getText():null), null);
					            System.exit(1);
					          }
					        }
					    
					}
					break;
				case 2 :
					// ./src/VSLTreeParser.g:164:7: ^( ARDECL IDENT INTEGER )
					{
					match(input,ARDECL,FOLLOW_ARDECL_in_decl_item486); 
					match(input, Token.DOWN, null); 
					match(input,IDENT,FOLLOW_IDENT_in_decl_item488); 
					match(input,INTEGER,FOLLOW_INTEGER_in_decl_item490); 
					match(input, Token.UP, null); 


					          System.out.println("Déclaration des tableaux non implémenté");
					          System.exit(1);
					      
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return code;
	}
	// $ANTLR end "decl_item"



	// $ANTLR start "inst_list"
	// ./src/VSLTreeParser.g:170:1: inst_list[SymbolTable symTab] returns [Code3a code] : ^( INST (st= statement[symTab] )+ ) ;
	public final Code3a inst_list(SymbolTable symTab) throws RecognitionException {
		Code3a code = null;


		Code3a st =null;

		try {
			// ./src/VSLTreeParser.g:171:5: ( ^( INST (st= statement[symTab] )+ ) )
			// ./src/VSLTreeParser.g:171:7: ^( INST (st= statement[symTab] )+ )
			{
			match(input,INST,FOLLOW_INST_in_inst_list517); 
			code = new Code3a();
			match(input, Token.DOWN, null); 
			// ./src/VSLTreeParser.g:172:7: (st= statement[symTab] )+
			int cnt8=0;
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( (LA8_0==ASSIGN_KW||LA8_0==IF_KW||LA8_0==RETURN_KW||LA8_0==WHILE_KW||LA8_0==BLOCK||LA8_0==FCALL_S) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// ./src/VSLTreeParser.g:172:8: st= statement[symTab]
					{
					pushFollow(FOLLOW_statement_in_inst_list530);
					st=statement(symTab);
					state._fsp--;


					          code.append(st);
					        
					}
					break;

				default :
					if ( cnt8 >= 1 ) break loop8;
					EarlyExitException eee = new EarlyExitException(8, input);
					throw eee;
				}
				cnt8++;
			}

			match(input, Token.UP, null); 

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return code;
	}
	// $ANTLR end "inst_list"



	// $ANTLR start "expression"
	// ./src/VSLTreeParser.g:179:1: expression[SymbolTable symTab] returns [ExpAttribute expAtt] : ( ^( PLUS e1= expression[symTab] e2= expression[symTab] ) | ^( MINUS e1= expression[symTab] e2= expression[symTab] ) | ^( MUL e1= expression[symTab] e2= expression[symTab] ) | ^( DIV e1= expression[symTab] e2= expression[symTab] ) |pe= primary_exp[symTab] );
	public final ExpAttribute expression(SymbolTable symTab) throws RecognitionException {
		ExpAttribute expAtt = null;


		ExpAttribute e1 =null;
		ExpAttribute e2 =null;
		ExpAttribute pe =null;

		try {
			// ./src/VSLTreeParser.g:180:3: ( ^( PLUS e1= expression[symTab] e2= expression[symTab] ) | ^( MINUS e1= expression[symTab] e2= expression[symTab] ) | ^( MUL e1= expression[symTab] e2= expression[symTab] ) | ^( DIV e1= expression[symTab] e2= expression[symTab] ) |pe= primary_exp[symTab] )
			int alt9=5;
			switch ( input.LA(1) ) {
			case PLUS:
				{
				alt9=1;
				}
				break;
			case MINUS:
				{
				alt9=2;
				}
				break;
			case MUL:
				{
				alt9=3;
				}
				break;
			case DIV:
				{
				alt9=4;
				}
				break;
			case IDENT:
			case INTEGER:
			case FCALL:
				{
				alt9=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}
			switch (alt9) {
				case 1 :
					// ./src/VSLTreeParser.g:180:5: ^( PLUS e1= expression[symTab] e2= expression[symTab] )
					{
					match(input,PLUS,FOLLOW_PLUS_in_expression573); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_expression577);
					e1=expression(symTab);
					state._fsp--;

					pushFollow(FOLLOW_expression_in_expression582);
					e2=expression(symTab);
					state._fsp--;

					match(input, Token.UP, null); 


					      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
					      VarSymbol temp = SymbDistrib.newTemp();
					      Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.ADD, temp, e1, e2);
					      expAtt = new ExpAttribute(ty, cod, temp);
					    
					}
					break;
				case 2 :
					// ./src/VSLTreeParser.g:187:5: ^( MINUS e1= expression[symTab] e2= expression[symTab] )
					{
					match(input,MINUS,FOLLOW_MINUS_in_expression597); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_expression601);
					e1=expression(symTab);
					state._fsp--;

					pushFollow(FOLLOW_expression_in_expression606);
					e2=expression(symTab);
					state._fsp--;

					match(input, Token.UP, null); 


					      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
					      VarSymbol temp = SymbDistrib.newTemp();
					      Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.SUB, temp, e1, e2);
					      expAtt = new ExpAttribute(ty, cod, temp);
					    
					}
					break;
				case 3 :
					// ./src/VSLTreeParser.g:194:5: ^( MUL e1= expression[symTab] e2= expression[symTab] )
					{
					match(input,MUL,FOLLOW_MUL_in_expression621); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_expression625);
					e1=expression(symTab);
					state._fsp--;

					pushFollow(FOLLOW_expression_in_expression630);
					e2=expression(symTab);
					state._fsp--;

					match(input, Token.UP, null); 


					      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
					      VarSymbol temp = SymbDistrib.newTemp();
					      Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.MUL, temp, e1, e2);
					      expAtt = new ExpAttribute(ty, cod, temp);
					    
					}
					break;
				case 4 :
					// ./src/VSLTreeParser.g:201:5: ^( DIV e1= expression[symTab] e2= expression[symTab] )
					{
					match(input,DIV,FOLLOW_DIV_in_expression645); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_expression649);
					e1=expression(symTab);
					state._fsp--;

					pushFollow(FOLLOW_expression_in_expression654);
					e2=expression(symTab);
					state._fsp--;

					match(input, Token.UP, null); 


					      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
					      VarSymbol temp = SymbDistrib.newTemp();
					      Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.DIV, temp, e1, e2);
					      expAtt = new ExpAttribute(ty, cod, temp);
					    
					}
					break;
				case 5 :
					// ./src/VSLTreeParser.g:208:5: pe= primary_exp[symTab]
					{
					pushFollow(FOLLOW_primary_exp_in_expression670);
					pe=primary_exp(symTab);
					state._fsp--;

					 expAtt = pe; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expAtt;
	}
	// $ANTLR end "expression"



	// $ANTLR start "primary_exp"
	// ./src/VSLTreeParser.g:212:1: primary_exp[SymbolTable symTab] returns [ExpAttribute expAtt] : ( INTEGER | IDENT | ^( FCALL IDENT argument_list[symTab, null] ) );
	public final ExpAttribute primary_exp(SymbolTable symTab) throws RecognitionException {
		ExpAttribute expAtt = null;


		CommonTree INTEGER11=null;
		CommonTree IDENT12=null;
		CommonTree IDENT13=null;
		Code3a argument_list14 =null;

		try {
			// ./src/VSLTreeParser.g:213:3: ( INTEGER | IDENT | ^( FCALL IDENT argument_list[symTab, null] ) )
			int alt10=3;
			switch ( input.LA(1) ) {
			case INTEGER:
				{
				alt10=1;
				}
				break;
			case IDENT:
				{
				alt10=2;
				}
				break;
			case FCALL:
				{
				alt10=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}
			switch (alt10) {
				case 1 :
					// ./src/VSLTreeParser.g:213:5: INTEGER
					{
					INTEGER11=(CommonTree)match(input,INTEGER,FOLLOW_INTEGER_in_primary_exp696); 

					      ConstSymbol cs = new ConstSymbol(Integer.parseInt((INTEGER11!=null?INTEGER11.getText():null)));
					      expAtt = new ExpAttribute(Type.INT, new Code3a(), cs);
					    
					}
					break;
				case 2 :
					// ./src/VSLTreeParser.g:218:5: IDENT
					{
					IDENT12=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_primary_exp708); 

					      Operand3a id = symTab.lookup((IDENT12!=null?IDENT12.getText():null));
					      expAtt = new ExpAttribute(id.type, new Code3a(), symTab.lookup((IDENT12!=null?IDENT12.getText():null)));
					    
					}
					break;
				case 3 :
					// ./src/VSLTreeParser.g:223:5: ^( FCALL IDENT argument_list[symTab, null] )
					{
					match(input,FCALL,FOLLOW_FCALL_in_primary_exp721); 
					match(input, Token.DOWN, null); 
					IDENT13=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_primary_exp723); 
					pushFollow(FOLLOW_argument_list_in_primary_exp725);
					argument_list14=argument_list(symTab, null);
					state._fsp--;

					match(input, Token.UP, null); 


					      Operand3a op = symTab.lookup((IDENT13!=null?IDENT13.getText():null));
					      if (op != null && (op instanceof FunctionSymbol)) {
					        FunctionSymbol functionSymbol = (FunctionSymbol) op;
					        if (((FunctionType)functionSymbol.type).getReturnType() != Type.VOID) {
					          VarSymbol temp = SymbDistrib.newTemp();
					          Code3a code = Code3aGenerator.genCallPe(argument_list14, (IDENT13!=null?IDENT13.getText():null), temp);
					          expAtt = new ExpAttribute(new FunctionType(functionSymbol.type), code, temp);
					        } else {
					          Errors.incompatibleTypes(IDENT13, Type.INT, ((FunctionType)functionSymbol.type).getReturnType(), null);
					          System.exit(1);
					        }
					      } else {
					        Errors.unknownIdentifier(IDENT13, (IDENT13!=null?IDENT13.getText():null), null);
					        System.exit(1);
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
			// do for sure before leaving
		}
		return expAtt;
	}
	// $ANTLR end "primary_exp"



	// $ANTLR start "argument_list"
	// ./src/VSLTreeParser.g:242:1: argument_list[SymbolTable symTab, FunctionType functionType] returns [Code3a code] : (e= expression[symTab] )* ;
	public final Code3a argument_list(SymbolTable symTab, FunctionType functionType) throws RecognitionException {
		Code3a code = null;


		ExpAttribute e =null;

		try {
			// ./src/VSLTreeParser.g:243:2: ( (e= expression[symTab] )* )
			// ./src/VSLTreeParser.g:243:4: (e= expression[symTab] )*
			{
			// ./src/VSLTreeParser.g:243:4: (e= expression[symTab] )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==DIV||LA11_0==IDENT||LA11_0==INTEGER||(LA11_0 >= MINUS && LA11_0 <= MUL)||LA11_0==PLUS||LA11_0==FCALL) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// ./src/VSLTreeParser.g:243:5: e= expression[symTab]
					{
					pushFollow(FOLLOW_expression_in_argument_list750);
					e=expression(symTab);
					state._fsp--;


								code = Code3aGenerator.genArguments(e);
					      if(functionType != null) {
									if(e.place.type == Type.I_CONST) { // isCompatible will fail if we don't do this
										functionType.extend(Type.INT);
									} else {
										functionType.extend(e.place.type);
								  }
					      }
							
					}
					break;

				default :
					break loop11;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return code;
	}
	// $ANTLR end "argument_list"

	// Delegated rules



	public static final BitSet FOLLOW_expression_in_s60 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FUNC_KW_in_function82 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_type_in_function84 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_IDENT_in_function89 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_param_list_in_function95 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_BODY_in_function100 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_statement_in_function104 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_PROTO_KW_in_proto125 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_type_in_proto127 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_IDENT_in_proto132 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_PARAM_in_proto137 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_param_list_in_proto141 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_INT_KW_in_type164 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VOID_KW_in_type171 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PARAM_in_param_list187 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_param_in_param_list189 = new BitSet(new long[]{0x0000000000004008L});
	public static final BitSet FOLLOW_IDENT_in_param205 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ASSIGN_KW_in_statement225 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_statement229 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_IDENT_in_statement232 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_block_in_statement245 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IF_KW_in_statement257 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_statement261 = new BitSet(new long[]{0x0000221080008020L});
	public static final BitSet FOLLOW_statement_in_statement266 = new BitSet(new long[]{0x0000221080008028L});
	public static final BitSet FOLLOW_statement_in_statement272 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_WHILE_KW_in_statement295 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_statement299 = new BitSet(new long[]{0x0000221080008020L});
	public static final BitSet FOLLOW_statement_in_statement304 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_RETURN_KW_in_statement317 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_statement321 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_FCALL_S_in_statement334 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_IDENT_in_statement336 = new BitSet(new long[]{0x0000100002C14208L});
	public static final BitSet FOLLOW_argument_list_in_statement340 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_BLOCK_in_block370 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_declaration_in_block376 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_inst_list_in_block381 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_BLOCK_in_block394 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_inst_list_in_block400 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_DECL_in_declaration430 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_decl_item_in_declaration437 = new BitSet(new long[]{0x0000004000004008L});
	public static final BitSet FOLLOW_IDENT_in_decl_item475 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ARDECL_in_decl_item486 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_IDENT_in_decl_item488 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_INTEGER_in_decl_item490 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_INST_in_inst_list517 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_statement_in_inst_list530 = new BitSet(new long[]{0x0000221080008028L});
	public static final BitSet FOLLOW_PLUS_in_expression573 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_expression577 = new BitSet(new long[]{0x0000100002C14200L});
	public static final BitSet FOLLOW_expression_in_expression582 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_MINUS_in_expression597 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_expression601 = new BitSet(new long[]{0x0000100002C14200L});
	public static final BitSet FOLLOW_expression_in_expression606 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_MUL_in_expression621 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_expression625 = new BitSet(new long[]{0x0000100002C14200L});
	public static final BitSet FOLLOW_expression_in_expression630 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_DIV_in_expression645 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_expression649 = new BitSet(new long[]{0x0000100002C14200L});
	public static final BitSet FOLLOW_expression_in_expression654 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_primary_exp_in_expression670 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEGER_in_primary_exp696 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_primary_exp708 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FCALL_in_primary_exp721 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_IDENT_in_primary_exp723 = new BitSet(new long[]{0x0000100002C14208L});
	public static final BitSet FOLLOW_argument_list_in_primary_exp725 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_expression_in_argument_list750 = new BitSet(new long[]{0x0000100002C14202L});
}
