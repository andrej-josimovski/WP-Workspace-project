package mk.ukim.finki.emt.workspaces.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @OneToMany(mappedBy = "workspace")
    List<WorkspaceMembership> memberships;

    public Workspace() {}

    public Workspace(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
