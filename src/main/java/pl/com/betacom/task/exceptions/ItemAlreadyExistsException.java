package pl.com.betacom.task.exceptions;

public class ItemAlreadyExistsException extends RuntimeException {
    private String message;

    public ItemAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
    public ItemAlreadyExistsException() {
    }
}