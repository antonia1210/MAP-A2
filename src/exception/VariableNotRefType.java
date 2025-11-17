package exception;

public class VariableNotRefType extends MyException {
    public VariableNotRefType(String message) {
        super("Variable " + message + " is not of Ref Type");
    }
}
