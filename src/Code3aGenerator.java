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

	public static Code3a genAssignement(ExpAttribute e, ExpAttribute e2) {
		Code3a cod = e.code;
		cod.append(e2.code);
		cod.append(new Inst3a(Inst3a.TAC.COPY, e2.place, e.place, null));
		return cod;
	}

	/**
	 * Generate code for if then
	 *
	 * @param cond ExpAttribute of condition
	 * @param thenCode Code3a of the then statement
	 **/

	public static Code3a genIf(ExpAttribute cond, Code3a thenCode) {
		// TODO: to implements
	}

	/**
	 * Generate code for if then else
	 *
	 * @param cond ExpAttribute of condition
	 * @param thenCode Code3a of the then statement
	 * @param elseCode Code3a of the else statement
	 **/

	public static Code3a genIf(ExpAttribute cond, Code3a thenCode, Code3a elseCode) {

	}

} // Code3aGenerator ***
