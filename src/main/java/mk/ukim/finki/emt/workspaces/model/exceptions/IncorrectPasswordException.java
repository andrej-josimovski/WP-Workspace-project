package mk.ukim.finki.emt.workspaces.model.exceptions;

public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException() {
        super("The password is incorrect.");
    }

}
