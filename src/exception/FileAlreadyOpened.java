package exception;

public class FileAlreadyOpened extends MyException {
    public FileAlreadyOpened(String File) {
        super("File " + File + " already exists");
    }
}
