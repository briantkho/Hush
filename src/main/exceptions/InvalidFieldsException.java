package exceptions;

public class InvalidFieldsException extends Exception {
    protected InvalidFieldsException() {
        super("Fields are Invalid!");
    }

    public InvalidFieldsException(String message) {
        super(message);
    }
}
