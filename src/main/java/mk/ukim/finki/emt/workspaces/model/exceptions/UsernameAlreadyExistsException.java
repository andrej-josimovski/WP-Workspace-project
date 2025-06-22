package mk.ukim.finki.emt.workspaces.model.exceptions;

import mk.ukim.finki.emt.workspaces.model.domain.User;

public class UsernameAlreadyExistsException extends Exception{
    public UsernameAlreadyExistsException(String username) {
        super(String.format("User with username '%s' already exists.", username));
    }
}
