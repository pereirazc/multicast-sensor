package exception;

public class NoUserException extends Exception {
    public NoUserException() {
        super("no user has been given");
    }
}
