/**
 * This class implements all the methods for 3a code generation (NOTE: this
 * class must be coded by the student; the methods indicated here can be seen as
 * a suggestion, but are not actually necessary).
 *
 * @author MLB
 *
 */
public class Code3aGenerator {

	// Constructor not needed
	private Code3aGenerator() {
	}

	/**
	 * Generates the 3a statement: VAR t
	 **/
	public static Code3a genVar(Operand3a t) {
		Inst3a i = new Inst3a(Inst3a.TAC.VAR, t, null, null);
		return new Code3a(i);
	}

	/**
	 * Generate code for a binary operation
	 *
	 * @param op
	 *            must be a code op: Inst3a.TAC.XXX
	 */
	public static Code3a genBinOp(Inst3a.TAC op, Operand3a temp, ExpAttribute exp1,
			ExpAttribute exp2) {
		Code3a cod = exp1.code;
		cod.append(exp2.code);
		cod.append(genVar(temp));
		cod.append(new Inst3a(op, temp, exp1.place, exp2.place));
		return cod;
	}

	/**
	 * Generate code for assignements
	 *
	 **/

	public static Code3a genAssignement(ExpAttribute e, Operand3a op) {
		Code3a cod = e.code;
		cod.append(new Inst3a(Inst3a.TAC.COPY, op, e.place, null));
		return cod;
	}

	/**
	 * Generate code for if then
	 *
	 * @param cond ExpAttribute of condition
	 * @param thenCode Code3a of the then statement
	 **/

	public static Code3a genIf(ExpAttribute cond, Code3a thenCode) {
		LabelSymbol end = SymbDistrib.newLabel();
		Code3a code = cond.code;
		code.append(new Inst3a(Inst3a.TAC.IFZ, cond.place, end, null));
		code.append(thenCode);
		code.append(new Inst3a(Inst3a.TAC.LABEL, end, null, null));
		return code;
	}

	/**
	 * Generate code for if then else
	 *
	 * @param cond ExpAttribute of condition
	 * @param thenCode Code3a of the then statement
	 * @param elseCode Code3a of the else statement
	 **/

	public static Code3a genIf(ExpAttribute cond, Code3a thenCode, Code3a elseCode) {
		LabelSymbol elseLabel = SymbDistrib.newLabel();
		LabelSymbol end = SymbDistrib.newLabel();
		Code3a code = cond.code;
		code.append(new Inst3a(Inst3a.TAC.IFZ, cond.place, elseLabel, null));
		code.append(thenCode);
		code.append(new Inst3a(Inst3a.TAC.GOTO, end, null, null));
		code.append(new Inst3a(Inst3a.TAC.LABEL, elseLabel, null, null));
		code.append(elseCode);
		code.append(new Inst3a(Inst3a.TAC.LABEL, end, null, null));
		return code;
	}

	/**
	 * Generate code for while
	 *
	 * @param cond ExpAttribute of the condition
	 * @param dost Code3a of the do statement
	 **/

	public static Code3a genWhile(ExpAttribute cond, Code3a dost) {
		LabelSymbol start = SymbDistrib.newLabel();
		LabelSymbol end = SymbDistrib.newLabel();
		Code3a code = new Code3a(new Inst3a(Inst3a.TAC.LABEL, start, null, null));
		code.append(cond.code);
		code.append(new Inst3a(Inst3a.TAC.IFZ, cond.place, end, null));
		code.append(dost);
		code.append(new Inst3a(Inst3a.TAC.GOTO, start, null, null));
		code.append(new Inst3a(Inst3a.TAC.LABEL, end, null, null));
		return code;
	}

	/**
	 * Generate code for function definition
	 *
	 * @param label LabelSymbol label of the function (the function's name)
	 * @param body Code3a of the function's body
	 **/

	public static Code3a genFunction(LabelSymbol label, Code3a body) {
		Code3a code = new Code3a(new Inst3a(Inst3a.TAC.LABEL, label, null, null));
		code.append(new Inst3a(Inst3a.TAC.BEGINFUNC, null, null, null));
		code.append(body);
		code.append(new Inst3a(Inst3a.TAC.ENDFUNC, null, null, null));
		return code;
	}

	/**
	 * Generate code for the RETURN instruction
	 *
	 * @param exp ExpAttribute the expression the function will return
	 **/

	public static Code3a genReturn(ExpAttribute exp) {
		Code3a code = exp.code;
		code.append(new Code3a(new Inst3a(Inst3a.TAC.RETURN, exp.place, null, null)));
		return code;
	}

	/**
	 * Generate code for a function's argument
	 *
	 * @param exp ExpAttribute the expression argument
	 **/

	public static Code3a genArguments(ExpAttribute exp) {
		Code3a code = exp.code;
		code.append(new Inst3a(Inst3a.TAC.ARG, exp.place, null, null));
		return code;
	}

	//Functions call
	public static Code3a genCallSt(Code3a argCode, String functionName) {
		Code3a code = argCode;
		code.append(new Inst3a(Inst3a.TAC.CALL, null, new LabelSymbol(functionName), null));
		return code;
	}

	public static Code3a genCallPe(Code3a argCode, String functionName, VarSymbol temp) {
		Code3a code = Code3aGenerator.genVar(temp);
		code.append(argCode);
		code.append(new Inst3a(Inst3a.TAC.CALL, temp, new LabelSymbol(functionName), null));
		return code;
	}

	/*
	*
	**/
	public static Code3a genPrintItem(ExpAttribute exp) {
		Code3a code = genArguments(exp);
		if(exp.type.equals(Type.INT)) {
			code.append(new Inst3a(Inst3a.TAC.CALL, null, SymbDistrib.builtinPrintN, null));
		}
		return code;
	}

	public static Code3a genPrintItem(Data3a text) {
		Code3a code = new Code3a();
		code.appendData(text);
		code.append(new Inst3a(Inst3a.TAC.ARG, text.getLabel(), null, null));
		code.append(new Inst3a(Inst3a.TAC.CALL, null, SymbDistrib.builtinPrintS, null));
		return code;
	}


	public static Code3a genReadItem(VarSymbol id) {
		Code3a code = new Code3a(new Inst3a(Inst3a.TAC.CALL, id, SymbDistrib.builtinRead, null));
		return code;
	}



} // Code3aGenerator ***
