package model.exception;

public class VariableAlreadyDefined extends MyException {
    public VariableAlreadyDefined(String id) {
        super("Variable " + id + " already defined");
    }
}
