tree grammar VSLTreeParser;

options {
  language     = Java;
  tokenVocab   = VSLParser;
  ASTLabelType = CommonTree;
}

s [SymbolTable symTab] returns [Code3a code]
  : p=program[symTab] { code = $p.code; }
;

program [SymbolTable symTab] returns [Code3a code]
	:	^(PROG {$code = new Code3a();} (u=unit[symTab] {$code.append($u.code);})+)
;

unit [SymbolTable symTab] returns [Code3a code]
	:	f=function[symTab] { $code = $f.code; }
	|	proto[symTab] { $code = new Code3a(); }
;

function [SymbolTable symTab] returns [Code3a code]
 : ^(FUNC_KW type {FunctionType functionType = new FunctionType($type.type, false);}
 IDENT {symTab.enterScope();} paramList=param_list[symTab, functionType]
 ^(BODY body=statement[symTab]) {symTab.leaveScope();} ) {

    Operand3a op = symTab.lookup($IDENT.text);
    LabelSymbol label = new LabelSymbol($IDENT.text);
    if (op == null) {
      symTab.insert($IDENT.text, new FunctionSymbol(label, functionType));
    } else if (op instanceof FunctionSymbol) {
      if(TypeCheck.checkFunctionDefinition((FunctionSymbol) op, functionType) == Type.ERROR){
        Errors.redefinedIdentifier($IDENT, $IDENT.text, null);
        System.exit(1);
      }
      if(TypeCheck.checkFunctionDefinitionType((FunctionSymbol) op, functionType) == Type.ERROR){
        Errors.incompatibleTypes($IDENT, ((FunctionSymbol)op).type, functionType, null);
        System.exit(1);
      }
    }
    $code = Code3aGenerator.genFunction(label, $body.code);
  }
;

proto [SymbolTable symTab]
 : ^(PROTO_KW type {FunctionType functionType = new FunctionType($type.type, true);}
 IDENT {symTab.enterScope();} paramList=param_list[symTab, functionType] {symTab.leaveScope();})
  {
    if(symTab.lookup($IDENT.text) == null) {
      symTab.insert($IDENT.text, new FunctionSymbol(new LabelSymbol($IDENT.text), functionType));
    } else {
      Errors.redefinedIdentifier($IDENT, $IDENT.text, null);
      System.exit(1);
    }
  }
;

type returns [Type type]
	: INT_KW {$type = Type.INT;}
	| VOID_KW {$type = Type.VOID;}
;

param_list [SymbolTable symTab, FunctionType type]
  : ^(PARAM param[symTab, type]*)
;

param [SymbolTable symTab, FunctionType type]
  : IDENT {
      if (symTab.lookup($IDENT.text) == null) {
        VarSymbol varSymbol = new VarSymbol(Type.INT, $IDENT.text, symTab.getScope());
        varSymbol.setParam();
        symTab.insert($IDENT.text, varSymbol);
        type.extend(Type.INT);
      } else {
        Errors.redefinedIdentifier($IDENT, $IDENT.text, null);
        System.exit(1);
      }
    }
;

statement [SymbolTable symTab] returns [Code3a code]
  : ^(ASSIGN_KW e=expression[symTab] IDENT) {
        ExpAttribute exp = $e.expAtt;
        Operand3a op = symTab.lookup($IDENT.text);
        if(op != null) {
          Type idType = op.type;
          Type expType = exp.type;
          Type t = TypeCheck.checkAssignement(idType, expType);
          if(t.equals(Type.ERROR)){
            Errors.incompatibleTypes($IDENT, Type.INT, exp.type, null);
  					System.exit(1);
          }
          code = Code3aGenerator.genAssignement(exp, op);
        } else {
          Errors.unknownIdentifier($IDENT, $IDENT.text, null);
          System.exit(1);
        }
      }
    | b=block[symTab] {
        $code = $b.code;
      }
    | ^(IF_KW cond=expression[symTab] stat=statement[symTab] (else_st=statement[symTab])?)
        {
          if ($else_st.code != null) {
    				$code = Code3aGenerator.genIf($cond.expAtt, $stat.code, $else_st.code);
    			} else {
    				$code = Code3aGenerator.genIf($cond.expAtt, $stat.code);
          }
        }
    | ^(WHILE_KW cond=expression[symTab] stat=statement[symTab]) {
        $code = Code3aGenerator.genWhile($cond.expAtt, $stat.code);
      }
    | ^(RETURN_KW e=expression[symTab])	{
  			if($e.expAtt.type != Type.INT) {
  				Errors.incompatibleTypes($RETURN_KW, Type.INT, $e.expAtt.type, null);
  				System.exit(1);
  			}
  			$code = Code3aGenerator.genReturn($e.expAtt);
      }
    | ^(FCALL_S IDENT {FunctionType functionType = new FunctionType(Type.VOID, false);} argument_list[symTab, functionType]) {
  			Operand3a op = symTab.lookup($IDENT.text);
  			if(op != null && (op instanceof FunctionSymbol)) {
  				FunctionSymbol functionSymbol = (FunctionSymbol) op;
  				if (((FunctionType)functionSymbol.type).getReturnType() == Type.VOID) {
  					$code = Code3aGenerator.genCallSt($argument_list.code, $IDENT.text);
  				} else {
  					Errors.unknownIdentifier($IDENT, $IDENT.text, null);
  					System.exit(1);
  				}
  			}
  		}
		|	^(PRINT_KW print_list[symTab]) { $code = $print_list.code; }

		| ^(READ_KW read_list[symTab]) { $code = $read_list.code; }
;

print_list[SymbolTable symTab] returns [Code3a code]
	: {$code = new Code3a();} (pi=print_item[symTab] {$code.append($pi.code);})*
;

print_item[SymbolTable symTab] returns [Code3a code]
	:	TEXT {$code = Code3aGenerator.genPrintItem(new Data3a($TEXT.text));}
  | e=expression[symTab] {$code = Code3aGenerator.genPrintItem($e.expAtt); }
;

read_list[SymbolTable symTab] returns [Code3a code]
	: {$code = new Code3a();} (ri=read_item[symTab] {$code.append($ri.code);})*
;

read_item[SymbolTable symTab] returns [Code3a code]
  : IDENT
		{
			VarSymbol varSymb = (VarSymbol) symTab.lookup($IDENT.text);
			if(varSymb != null) {
				$code = Code3aGenerator.genReadItem(varSymb);
			} else {
				Errors.unknownIdentifier($IDENT, $IDENT.text, null);
				System.exit(1);
			}
		}
;





  block [SymbolTable symTab] returns [Code3a code]
    : ^(BLOCK {$symTab.enterScope();} decl=declaration[symTab] il=inst_list[symTab]) {
        $code = $decl.code;
        $code.append($il.code);
        $symTab.leaveScope();
      }
    | ^(BLOCK {$symTab.enterScope();} il=inst_list[symTab]) {
        $code = $il.code;
        $symTab.leaveScope();
      }
    ;

  declaration [SymbolTable symTab] returns [Code3a code]
    : ^(DECL {$code = new Code3a();} (di=decl_item[symTab] {
          $code.append($di.code);
        }
      )+)
    ;

  decl_item [SymbolTable symTab] returns [Code3a code]
    : IDENT {
        VarSymbol vs = (VarSymbol) symTab.lookup($IDENT.text);
        int currentScope = symTab.getScope();
        if(vs == null){
          vs = new VarSymbol(Type.INT, $IDENT.text, currentScope);
          symTab.insert($IDENT.text, vs);
          code = Code3aGenerator.genVar(vs);
        } else {
          int scopeIdent = vs.getScope();
          if(scopeIdent == currentScope) {
            Errors.redefinedIdentifier($IDENT, $IDENT.text, null);
            System.exit(1);
          }
        }
    }
    | ^(ARDECL IDENT INTEGER) {
          System.out.println("Déclaration des tableaux non implémenté");
          System.exit(1);
      }
    ;

inst_list [SymbolTable symTab] returns [Code3a code]
    : ^(INST {$code = new Code3a();}
      (st=statement[symTab]
        {
          $code.append($st.code);
        }
      )+)
    ;

expression [SymbolTable symTab] returns [ExpAttribute expAtt]
  : ^(PLUS e1=expression[symTab] e2=expression[symTab])
    {
      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
      VarSymbol temp = SymbDistrib.newTemp();
      Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.ADD, temp, e1, e2);
      expAtt = new ExpAttribute(ty, cod, temp);
    }
  | ^(MINUS e1=expression[symTab] e2=expression[symTab])
    {
      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
      VarSymbol temp = SymbDistrib.newTemp();
      Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.SUB, temp, e1, e2);
      expAtt = new ExpAttribute(ty, cod, temp);
    }
  | ^(MUL e1=expression[symTab] e2=expression[symTab])
    {
      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
      VarSymbol temp = SymbDistrib.newTemp();
      Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.MUL, temp, e1, e2);
      expAtt = new ExpAttribute(ty, cod, temp);
    }
  | ^(DIV e1=expression[symTab] e2=expression[symTab])
    {
      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
      VarSymbol temp = SymbDistrib.newTemp();
      Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.DIV, temp, e1, e2);
      expAtt = new ExpAttribute(ty, cod, temp);
    }
  | pe=primary_exp[symTab]
    { expAtt = $pe.expAtt; }
  ;

primary_exp [SymbolTable symTab] returns [ExpAttribute expAtt]
  : INTEGER
    {
      ConstSymbol cs = new ConstSymbol(Integer.parseInt($INTEGER.text));
      expAtt = new ExpAttribute(Type.INT, new Code3a(), cs);
    }
  | IDENT
    {
      Operand3a id = symTab.lookup($IDENT.text);
      expAtt = new ExpAttribute(id.type, new Code3a(), symTab.lookup($IDENT.text));
    }
  | ^(FCALL IDENT argument_list[symTab, null]) {
      Operand3a op = symTab.lookup($IDENT.text);
      if (op != null && (op instanceof FunctionSymbol)) {
        FunctionSymbol functionSymbol = (FunctionSymbol) op;
        if (((FunctionType)functionSymbol.type).getReturnType() != Type.VOID) {
          VarSymbol temp = SymbDistrib.newTemp();
          Code3a code = Code3aGenerator.genCallPe($argument_list.code, $IDENT.text, temp);
          expAtt = new ExpAttribute(new FunctionType(functionSymbol.type), code, temp);
        } else {
          Errors.incompatibleTypes($IDENT, Type.INT, ((FunctionType)functionSymbol.type).getReturnType(), null);
          System.exit(1);
        }
      } else {
        Errors.unknownIdentifier($IDENT, $IDENT.text, null);
        System.exit(1);
      }
    }
  ;

argument_list [SymbolTable symTab, FunctionType functionType] returns [Code3a code]
	: {$code = new Code3a();} (e=expression[symTab]
		{
			$code.append(Code3aGenerator.genArguments($e.expAtt));
      if(functionType != null) {
				if($e.expAtt.place.type == Type.I_CONST) { // isCompatible will fail if we don't do this
					functionType.extend(Type.INT);
				} else {
					functionType.extend($e.expAtt.place.type);
			  }
      }
		}
	)*
	;
