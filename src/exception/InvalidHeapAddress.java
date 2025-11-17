package exception;

public class InvalidHeapAddress extends MyException {
    public InvalidHeapAddress(int address) {
        super("Invalid heap address " + address);
    }
}
