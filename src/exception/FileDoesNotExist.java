package exception;

public class FileDoesNotExist extends MyException {
    public FileDoesNotExist(String File) {
        super("File does not exist: " + File);
    }
}
