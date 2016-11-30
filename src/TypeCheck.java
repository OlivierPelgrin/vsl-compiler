/**
 * Type checking operations (NOTE: this class must be implemented by the
 * student; the methods indicated here can be seen as suggestions; note that
 * some minor checks can still be performed directly in VSLTreeParser.g).
 *
 */
public class TypeCheck {

	// an Example
	/**
	 * Type checking for a binary operation - in VSL+: integer operations only!
	 */
	public static Type checkBinOp(Type t1, Type t2) {
		if (t1 == Type.INT && t2 == Type.INT) {
			return Type.INT;
		} else {
			return Type.ERROR;
		}
	}

	public static Type checkAssignement(Type idT, Type expT) {
		if(idT instanceof ArrayType) {
			return Type.ERROR;
		}
		if(expT instanceof ArrayType) {
			return Type.ERROR;
		}
		return Type.INT;
	}

	public static Type checkFunctionDefinition(FunctionSymbol functionSymbol, FunctionType functionType) {
		if (!((FunctionType)functionSymbol.type).prototype) {
			return Type.ERROR;
		}
		return functionType.getReturnType();
	}

	public static Type checkFunctionDefinitionType(FunctionSymbol functionSymbol, FunctionType functionType) {
		if (!functionSymbol.type.isCompatible((Type)functionType)) {
			return Type.ERROR;
		}
		return functionType.getReturnType();
	}

}
