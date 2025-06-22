package mk.ukim.finki.emt.workspaces.model.domain;

import jakarta.persistence.*;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class WorkspaceMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Workspace workspace;
    @Enumerated(EnumType.STRING)
    private Role role;

    public WorkspaceMembership(){}

    public WorkspaceMembership(User user, Workspace workspace, Role role) {
        this.user = user;
        this.workspace = workspace;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
