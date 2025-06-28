package mk.ukim.finki.emt.workspaces.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkspaceNotFoundException extends RuntimeException {
    public WorkspaceNotFoundException(Long id) {
        super(String.format("Workspace with id %s not found", id));
    }
}
