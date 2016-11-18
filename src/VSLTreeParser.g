tree grammar VSLTreeParser;

options {
  language     = Java;
  tokenVocab   = VSLParser;
  ASTLabelType = CommonTree;
}

s [SymbolTable symTab] returns [Code3a code]
  : e=expression[symTab] { code = e.code; }
  ;

statement [SymbolTable symTab] returns [Code3a code]
  : ^(ASSIGN_KW e=expression[symTab] IDENT) {
        ExpAttribute exp = $e.expAtt;
        Operand3a op = symTab.lookup($IDENT.text);
        if(op != null) {
          /*if(exp.type instanceof ArrayType) {
  					Errors.incompatibleTypes($IDENT, Type.INT, exp.type, null);
  					System.exit(1);
  				}
          if(op.type instanceof ArrayType){
  					Errors.incompatibleTypes($IDENT, Type.INT, op.type, null);
  					System.exit(1);
  				}*/
          code = Code3aGenerator.genAssignement(exp, new ExpAttribute(Type.INT, new Code3a(), new VarSymbol($IDENT.text)));
        } else {
          Errors.unknownIdentifier($IDENT, $IDENT.text, null);
          System.exit(1);
        }
      }
    | b=block[symTab] {
        $code = $b.code;
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

  /*decl_list [SymbolTable symTab] returns [Code3a code]
    : {$code = new Code3a();}
      (de=decl_item[symTab] {
          $code.append($de.code);
        }
      )+
    ;*/

  decl_item [SymbolTable symTab] returns [Code3a code]
    : IDENT {
        VarSymbol vs = (VarSymbol) symTab.lookup($IDENT.text);
        int scopeIdent = vs.getScope();
        int currentScope = symTab.getScope();

        if(vs != null && (scopeIdent == currentScope)) {
          Errors.redefinedIdentifier($IDENT, $IDENT.text, null);
          System.exit(1);
        }

        vs = new VarSymbol(Type.INT, $IDENT.text, symTab.getScope());
        symTab.insert($IDENT.text, vs);
        code = Code3aGenerator.genVar(vs);
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
