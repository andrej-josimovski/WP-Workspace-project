package mk.ukim.finki.emt.workspaces.model.domain;

import jakarta.persistence.*;
import mk.ukim.finki.emt.workspaces.model.enumerations.ContentType;

import java.time.LocalDateTime;

@Entity
public class Content {

    @Id
    @GeneratedValue
    Long id;

    private String name;

    private ContentType type;
    private String description;
    private LocalDateTime uploadedAt;

    public Content(String name, String description, ContentType type, LocalDateTime uploadedAt) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.uploadedAt = uploadedAt;
    }

    public Content() {

    }

    public String getName() {
        return name;
    }

    public ContentType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }


    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}