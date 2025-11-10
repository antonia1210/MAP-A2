package exception;

public class ExpressionIsNotString extends MyException {
    public ExpressionIsNotString() {
        super("Operand is not a string");
    }
}
