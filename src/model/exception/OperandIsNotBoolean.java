package model.exception;

public class OperandIsNotBoolean extends MyException {
    public OperandIsNotBoolean() {
        super("Operand is not a boolean");
    }
}