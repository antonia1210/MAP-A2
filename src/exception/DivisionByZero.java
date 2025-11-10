package exception;

public class DivisionByZero extends MyException {
    public DivisionByZero() {
        super("Cannot divide by zero");
    }
}