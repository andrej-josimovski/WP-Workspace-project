package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.Content;
import mk.ukim.finki.emt.workspaces.model.enumerations.ContentType;

import java.time.LocalDateTime;

public record CreateContentDto(
        String name,
        String description,
        ContentType type,
        LocalDateTime uploadedAt
) {
    public Content toContent() {
        return new Content(name, description, type, uploadedAt);
    }
}
