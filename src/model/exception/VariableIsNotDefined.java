package model.exception;

public class VariableIsNotDefined extends MyException {
    public VariableIsNotDefined(String id) {
        super("Variable " + id + " is not defined");
    }
}
