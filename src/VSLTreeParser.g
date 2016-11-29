tree grammar VSLTreeParser;

options {
  language     = Java;
  tokenVocab   = VSLParser;
  ASTLabelType = CommonTree;
}

s [SymbolTable symTab] returns [Code3a code]
  : e=expression[symTab] { code = e.code; }
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
      TypeCheck.checkFunctionDefinition((FunctionSymbol) op, functionType, $IDENT);
    }
    $code = Code3aGenerator.genFunction(label, $body.code);
  }
;

proto [SymbolTable symTab]
 : ^(PROTO_KW type {FunctionType functionType = new FunctionType($type.type, true);}
 IDENT {symTab.enterScope();} ^(PARAM paramList=param_list[symTab, functionType] {symTab.leaveScope();}))
  {
    if(ts.lookup($IDENT.text) == null) {
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
        symTab.insert($IDENT.text, vs);
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
          Type idtype = op.type;
          Type expType = exp.type;
          Type t = TypeCheck.checkAssignement(idType, expType);
          if(t.equals(Type.ERROR)){
            Errors.incompatibleTypes($IDENT, Type.INT, exp.type, null);
  					System.exit(1);
          }
          code = Code3aGenerator.genAssignement(exp, new ExpAttribute(Type.INT, new Code3a(), new VarSymbol($IDENT.text)));
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
  			$code = Code3aGenerator.genReturn($esymTab);
      }
    | ^(FCALL_S IDENT {FunctionType functionType = new FunctionType(Type.VOID, false);} argument_list[symTab, functionType]) {
  			Operand3a op = symTab.lookup($IDENT.text);
  			if(op != null && (op instanceof FunctionSymbol)) {
  				FunctionSymbol functionSymbol = (FunctionSymbol) op;
  				if (((FunctionType)functionSymbol.type).getReturnType() == Type.VOID) {
  					$code = Code3aGenerator.genCall($argument_list.code, new VarSymbol($IDENT.text));
  				} else {
  					Errors.unknownIdentifier($IDENT, $IDENT.text, null);
  					System.exit(1);
  				}
          if(!functionSymbol.type.isCompatible((Type)functionType)) {
  					Errors.incompatibleTypes($IDENT, functionSymbol.type, functionType, null);
  					System.exit(1);
  				}
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
    { expAtt = pe; }
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
  ;
