package mk.ukim.finki.emt.workspaces.model.exceptions;

public class AccessDeniedException extends Exception{
    public AccessDeniedException() {
        super("You are not the OWNER of this workspace");
    }
}