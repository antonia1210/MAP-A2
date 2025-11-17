package exception;

public class TypeMismatch extends RuntimeException {
    public TypeMismatch(String name, String t, String type) {
        super("Type mismatch: variable " + name + " expects " + t + " but got " +  type + "instead");
    }
}
