package mk.ukim.finki.emt.workspaces.model.exceptions;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(Long id) {
        super(String.format("Content with id: %d is not found.", id));
    }
}
