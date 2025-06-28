package mk.ukim.finki.emt.workspaces.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    List<WorkspaceMembership> memberships = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workspace_id")
    List<Content> contents = new ArrayList<>();

    public Workspace() {
    }

    public Workspace(String name, List<Content> contents) {
        this.name = name;
        this.contents = contents;
    }

    public List<WorkspaceMembership> getMemberships() {
        return memberships;
    }

    public Long getId() {
        return id;
    }

    public List<Content> getContents() {
        return contents;
    }

    public Workspace(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
